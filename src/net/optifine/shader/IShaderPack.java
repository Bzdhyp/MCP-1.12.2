package net.optifine.shader;

import java.io.InputStream;

public interface IShaderPack
{
    String getName();

    InputStream getResourceAsStream(String var1);

    boolean hasDirectory(String var1);

    void close();
}
