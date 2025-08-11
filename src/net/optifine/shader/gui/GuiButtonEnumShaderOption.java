package net.optifine.shader.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.optifine.shader.EnumShaderOption;
import net.optifine.shader.Shaders;

public class GuiButtonEnumShaderOption extends GuiButton
{
    private EnumShaderOption enumShaderOption = null;

    public GuiButtonEnumShaderOption(EnumShaderOption enumShaderOption, int x, int y, int widthIn, int heightIn)
    {
        super(enumShaderOption.ordinal(), x, y, widthIn, heightIn, getButtonText(enumShaderOption));
        this.enumShaderOption = enumShaderOption;
    }

    public EnumShaderOption getEnumShaderOption()
    {
        return this.enumShaderOption;
    }

    private static String getButtonText(EnumShaderOption eso)
    {
        String s = I18n.format(eso.getResourceKey()) + ": ";

        switch (eso)
        {
            case ANTIALIASING:
                return s + GuiShader.toStringAa(Shaders.configAntialiasingLevel);

            case NORMAL_MAP:
                return s + GuiShader.toStringOnOff(Shaders.configNormalMap);

            case SPECULAR_MAP:
                return s + GuiShader.toStringOnOff(Shaders.configSpecularMap);

            case RENDER_RES_MUL:
                return s + GuiShader.toStringQuality(Shaders.configRenderResMul);

            case SHADOW_RES_MUL:
                return s + GuiShader.toStringQuality(Shaders.configShadowResMul);

            case HAND_DEPTH_MUL:
                return s + GuiShader.toStringHandDepth(Shaders.configHandDepthMul);

            case CLOUD_SHADOW:
                return s + GuiShader.toStringOnOff(Shaders.configCloudShadow);

            case OLD_HAND_LIGHT:
                return s + Shaders.configOldHandLight.getUserValue();

            case OLD_LIGHTING:
                return s + Shaders.configOldLighting.getUserValue();

            case SHADOW_CLIP_FRUSTRUM:
                return s + GuiShader.toStringOnOff(Shaders.configShadowClipFrustrum);

            case TWEAK_BLOCK_DAMAGE:
                return s + GuiShader.toStringOnOff(Shaders.configTweakBlockDamage);

            default:
                return s + Shaders.getEnumShaderOption(eso);
        }
    }

    public void updateButtonText()
    {
        this.displayString = getButtonText(this.enumShaderOption);
    }
}
