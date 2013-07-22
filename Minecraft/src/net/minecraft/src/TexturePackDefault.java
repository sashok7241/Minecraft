package net.minecraft.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class TexturePackDefault extends TexturePackImplementation
{
	public TexturePackDefault()
	{
		super("default", (File) null, "Default", (ITexturePack) null);
	}
	
	@Override protected InputStream func_98139_b(String par1Str) throws IOException
	{
		InputStream var2 = TexturePackDefault.class.getResourceAsStream(par1Str);
		if(var2 == null) throw new FileNotFoundException(par1Str);
		else return var2;
	}
	
	@Override public boolean func_98140_c(String par1Str)
	{
		return TexturePackDefault.class.getResourceAsStream(par1Str) != null;
	}
	
	@Override public boolean isCompatible()
	{
		return true;
	}
	
	@Override protected void loadDescription()
	{
		firstDescriptionLine = "The default look of Minecraft";
	}
}
