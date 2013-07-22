package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public abstract class TexturePackImplementation implements ITexturePack
{
	private final String texturePackID;
	private final String texturePackFileName;
	protected final File texturePackFile;
	protected String firstDescriptionLine;
	protected String secondDescriptionLine;
	private final ITexturePack fallbackTexturePack;
	protected BufferedImage thumbnailImage;
	private int thumbnailTextureName = -1;
	
	protected TexturePackImplementation(String par1, File par2File, String par3Str, ITexturePack par4ITexturePack)
	{
		texturePackID = par1;
		texturePackFileName = par3Str;
		texturePackFile = par2File;
		fallbackTexturePack = par4ITexturePack;
		loadThumbnailImage();
		loadDescription();
	}
	
	@Override public void bindThumbnailTexture(RenderEngine par1RenderEngine)
	{
		if(thumbnailImage != null)
		{
			if(thumbnailTextureName == -1)
			{
				thumbnailTextureName = par1RenderEngine.allocateAndSetupTexture(thumbnailImage);
			}
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, thumbnailTextureName);
			par1RenderEngine.resetBoundTexture();
		} else
		{
			par1RenderEngine.bindTexture("/gui/unknown_pack.png");
		}
	}
	
	@Override public void deleteTexturePack(RenderEngine par1RenderEngine)
	{
		if(thumbnailImage != null && thumbnailTextureName != -1)
		{
			par1RenderEngine.deleteTexture(thumbnailTextureName);
		}
	}
	
	@Override public InputStream func_98137_a(String par1Str, boolean par2) throws IOException
	{
		try
		{
			return func_98139_b(par1Str);
		} catch(IOException var4)
		{
			if(fallbackTexturePack != null && par2) return fallbackTexturePack.func_98137_a(par1Str, true);
			else throw var4;
		}
	}
	
	@Override public boolean func_98138_b(String par1Str, boolean par2)
	{
		boolean var3 = func_98140_c(par1Str);
		return !var3 && par2 && fallbackTexturePack != null ? fallbackTexturePack.func_98138_b(par1Str, par2) : var3;
	}
	
	protected abstract InputStream func_98139_b(String var1) throws IOException;
	
	public abstract boolean func_98140_c(String var1);
	
	@Override public String getFirstDescriptionLine()
	{
		return firstDescriptionLine;
	}
	
	@Override public InputStream getResourceAsStream(String par1Str) throws IOException
	{
		return func_98137_a(par1Str, true);
	}
	
	@Override public String getSecondDescriptionLine()
	{
		return secondDescriptionLine;
	}
	
	@Override public String getTexturePackFileName()
	{
		return texturePackFileName;
	}
	
	@Override public String getTexturePackID()
	{
		return texturePackID;
	}
	
	protected void loadDescription()
	{
		InputStream var1 = null;
		BufferedReader var2 = null;
		try
		{
			var1 = func_98139_b("/pack.txt");
			var2 = new BufferedReader(new InputStreamReader(var1));
			firstDescriptionLine = trimStringToGUIWidth(var2.readLine());
			secondDescriptionLine = trimStringToGUIWidth(var2.readLine());
		} catch(IOException var12)
		{
			;
		} finally
		{
			try
			{
				if(var2 != null)
				{
					var2.close();
				}
				if(var1 != null)
				{
					var1.close();
				}
			} catch(IOException var11)
			{
				;
			}
		}
	}
	
	private void loadThumbnailImage()
	{
		InputStream var1 = null;
		try
		{
			var1 = func_98137_a("/pack.png", false);
			thumbnailImage = ImageIO.read(var1);
		} catch(IOException var11)
		{
			;
		} finally
		{
			try
			{
				if(var1 != null)
				{
					var1.close();
				}
			} catch(IOException var10)
			{
				;
			}
		}
	}
	
	private static String trimStringToGUIWidth(String par0Str)
	{
		if(par0Str != null && par0Str.length() > 34)
		{
			par0Str = par0Str.substring(0, 34);
		}
		return par0Str;
	}
}
