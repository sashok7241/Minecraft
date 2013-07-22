package net.minecraft.src;

import java.io.IOException;
import java.io.InputStream;

public interface ITexturePack
{
	void bindThumbnailTexture(RenderEngine var1);
	
	void deleteTexturePack(RenderEngine var1);
	
	InputStream func_98137_a(String var1, boolean var2) throws IOException;
	
	boolean func_98138_b(String var1, boolean var2);
	
	String getFirstDescriptionLine();
	
	InputStream getResourceAsStream(String var1) throws IOException;
	
	String getSecondDescriptionLine();
	
	String getTexturePackFileName();
	
	String getTexturePackID();
	
	boolean isCompatible();
}
