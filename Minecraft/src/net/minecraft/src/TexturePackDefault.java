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
	
	@Override protected InputStream func_98139_b(String p_98139_1_) throws IOException
	{
		InputStream var2 = TexturePackDefault.class.getResourceAsStream(p_98139_1_);
		if(var2 == null) throw new FileNotFoundException(p_98139_1_);
		else return var2;
	}
	
	@Override public boolean func_98140_c(String p_98140_1_)
	{
		return TexturePackDefault.class.getResourceAsStream(p_98140_1_) != null;
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
