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
	
	public TexturePackCustom(String par1Str, File par2File, ITexturePack par3ITexturePack)
	{
		super(par1Str, par2File, par2File.getName(), par3ITexturePack);
	}
	
	@Override public void deleteTexturePack(RenderEngine par1RenderEngine)
	{
		super.deleteTexturePack(par1RenderEngine);
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
	
	@Override protected InputStream func_98139_b(String par1Str) throws IOException
	{
		openTexturePackFile();
		ZipEntry var2 = texturePackZipFile.getEntry(par1Str.substring(1));
		if(var2 == null) throw new FileNotFoundException(par1Str);
		else return texturePackZipFile.getInputStream(var2);
	}
	
	@Override public boolean func_98140_c(String par1Str)
	{
		try
		{
			openTexturePackFile();
			return texturePackZipFile.getEntry(par1Str.substring(1)) != null;
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
