package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public interface ResourcePack
{
	BufferedImage func_110586_a() throws IOException;
	
	Set func_110587_b();
	
	boolean func_110589_b(ResourceLocation var1);
	
	InputStream func_110590_a(ResourceLocation var1) throws IOException;
	
	String func_130077_b();
	
	MetadataSection func_135058_a(MetadataSerializer var1, String var2) throws IOException;
}
