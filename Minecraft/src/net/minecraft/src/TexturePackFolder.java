package net.minecraft.src;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class TexturePackFolder extends TexturePackImplementation
{
	public TexturePackFolder(String p_i11001_1_, File p_i11001_2_, ITexturePack p_i11001_3_)
	{
		super(p_i11001_1_, p_i11001_2_, p_i11001_2_.getName(), p_i11001_3_);
	}
	
	@Override protected InputStream func_98139_b(String p_98139_1_) throws IOException
	{
		File var2 = new File(texturePackFile, p_98139_1_.substring(1));
		if(!var2.exists()) throw new FileNotFoundException(p_98139_1_);
		else return new BufferedInputStream(new FileInputStream(var2));
	}
	
	@Override public boolean func_98140_c(String p_98140_1_)
	{
		File var2 = new File(texturePackFile, p_98140_1_);
		return var2.exists() && var2.isFile();
	}
	
	@Override public boolean isCompatible()
	{
		File var1 = new File(texturePackFile, "textures/");
		boolean var2 = var1.exists() && var1.isDirectory();
		boolean var3 = func_98140_c("terrain.png") || func_98140_c("gui/items.png");
		return var2 || !var3;
	}
}
