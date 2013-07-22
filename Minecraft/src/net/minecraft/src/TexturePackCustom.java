package net.minecraft.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class TexturePackCustom extends TexturePackImplementation
{
	private ZipFile texturePackZipFile;
	
	public TexturePackCustom(String p_i11002_1_, File p_i11002_2_, ITexturePack p_i11002_3_)
	{
		super(p_i11002_1_, p_i11002_2_, p_i11002_2_.getName(), p_i11002_3_);
	}
	
	@Override public void deleteTexturePack(RenderEngine p_77533_1_)
	{
		super.deleteTexturePack(p_77533_1_);
		try
		{
			if(texturePackZipFile != null)
			{
				texturePackZipFile.close();
			}
		} catch(IOException var3)
		{
			;
		}
		texturePackZipFile = null;
	}
	
	@Override protected InputStream func_98139_b(String p_98139_1_) throws IOException
	{
		openTexturePackFile();
		ZipEntry var2 = texturePackZipFile.getEntry(p_98139_1_.substring(1));
		if(var2 == null) throw new FileNotFoundException(p_98139_1_);
		else return texturePackZipFile.getInputStream(var2);
	}
	
	@Override public boolean func_98140_c(String p_98140_1_)
	{
		try
		{
			openTexturePackFile();
			return texturePackZipFile.getEntry(p_98140_1_.substring(1)) != null;
		} catch(Exception var3)
		{
			return false;
		}
	}
	
	@Override public boolean isCompatible()
	{
		try
		{
			openTexturePackFile();
			Enumeration var1 = texturePackZipFile.entries();
			while(var1.hasMoreElements())
			{
				ZipEntry var2 = (ZipEntry) var1.nextElement();
				if(var2.getName().startsWith("textures/")) return true;
			}
		} catch(Exception var3)
		{
			;
		}
		boolean var4 = func_98140_c("terrain.png") || func_98140_c("gui/items.png");
		return !var4;
	}
	
	private void openTexturePackFile() throws IOException, ZipException
	{
		if(texturePackZipFile == null)
		{
			texturePackZipFile = new ZipFile(texturePackFile);
		}
	}
}
