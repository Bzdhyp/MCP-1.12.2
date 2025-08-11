package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.util.List;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.src.Config;
import net.optifine.shader.SVertexBuilder;

public class WorldVertexBufferUploader {
    /**
     * Improves speed by tracking the index in the post-draw loop making it O(n) instead of O(n^2).
     * Using @Overwrite to have no overhead, I assume that nearly no mods will Mixin it anyway, this could easily be an injection if incompatibilities are found.
     *
     * @author Desoroxxx
     */
    public void draw(BufferBuilder vertexBufferIn) {
        if (vertexBufferIn.getVertexCount() > 0) {
            VertexFormat vertexformat = vertexBufferIn.getVertexFormat();
            int i = vertexformat.getNextOffset();
            ByteBuffer bytebuffer = vertexBufferIn.getByteBuffer();
            List<VertexFormatElement> list = vertexformat.getElements();

            for (int j = 0; j < list.size(); ++j) {
                VertexFormatElement vertexformatelement = list.get(j);
                VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();

                int k = vertexformatelement.getType().getGlConstant();
                int l = vertexformatelement.getIndex();
                bytebuffer.position(vertexformat.getOffset(j));

                switch (vertexformatelement$enumusage) {
                    case POSITION:
                        GlStateManager.glVertexPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
                        GlStateManager.glEnableClientState(32884);
                        break;

                    case UV:
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + l);
                        GlStateManager.glTexCoordPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
                        GlStateManager.glEnableClientState(32888);
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                        break;

                    case COLOR:
                        GlStateManager.glColorPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
                        GlStateManager.glEnableClientState(32886);
                        break;

                    case NORMAL:
                        GlStateManager.glNormalPointer(k, i, bytebuffer);
                        GlStateManager.glEnableClientState(32885);
                }

            }

            if (vertexBufferIn.isMultiTexture()) {
                vertexBufferIn.drawMultiTexture();
            } else if (Config.isShaders()) {
                SVertexBuilder.drawArrays(vertexBufferIn.getDrawMode(), 0, vertexBufferIn.getVertexCount(), vertexBufferIn);
            } else {
                GlStateManager.glDrawArrays(vertexBufferIn.getDrawMode(), 0, vertexBufferIn.getVertexCount());
            }

            int j1 = 0;

            for (int k1 = list.size(); j1 < k1; ++j1) {
                VertexFormatElement vertexformatelement1 = list.get(j1);
                VertexFormatElement.EnumUsage vertexformatelement$enumusage1 = vertexformatelement1.getUsage();

                int i1 = vertexformatelement1.getIndex();

                switch (vertexformatelement$enumusage1) {
                    case POSITION:
                        GlStateManager.glDisableClientState(32884);
                        break;

                    case UV:
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + i1);
                        GlStateManager.glDisableClientState(32888);
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                        break;

                    case COLOR:
                        GlStateManager.glDisableClientState(32886);
                        GlStateManager.resetColor();
                        break;

                    case NORMAL:
                        GlStateManager.glDisableClientState(32885);
                }

            }
        }

        vertexBufferIn.reset();
    }
}
