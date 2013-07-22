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
	
	protected TexturePackImplementation(String p_i11000_1_, File p_i11000_2_, String p_i11000_3_, ITexturePack p_i11000_4_)
	{
		texturePackID = p_i11000_1_;
		texturePackFileName = p_i11000_3_;
		texturePackFile = p_i11000_2_;
		fallbackTexturePack = p_i11000_4_;
		loadThumbnailImage();
		loadDescription();
	}
	
	@Override public void bindThumbnailTexture(RenderEngine p_77535_1_)
	{
		if(thumbnailImage != null)
		{
			if(thumbnailTextureName == -1)
			{
				thumbnailTextureName = p_77535_1_.allocateAndSetupTexture(thumbnailImage);
			}
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, thumbnailTextureName);
			p_77535_1_.resetBoundTexture();
		} else
		{
			p_77535_1_.bindTexture("/gui/unknown_pack.png");
		}
	}
	
	@Override public void deleteTexturePack(RenderEngine p_77533_1_)
	{
		if(thumbnailImage != null && thumbnailTextureName != -1)
		{
			p_77533_1_.deleteTexture(thumbnailTextureName);
		}
	}
	
	@Override public InputStream func_98137_a(String p_98137_1_, boolean p_98137_2_) throws IOException
	{
		try
		{
			return func_98139_b(p_98137_1_);
		} catch(IOException var4)
		{
			if(fallbackTexturePack != null && p_98137_2_) return fallbackTexturePack.func_98137_a(p_98137_1_, true);
			else throw var4;
		}
	}
	
	@Override public boolean func_98138_b(String p_98138_1_, boolean p_98138_2_)
	{
		boolean var3 = func_98140_c(p_98138_1_);
		return !var3 && p_98138_2_ && fallbackTexturePack != null ? fallbackTexturePack.func_98138_b(p_98138_1_, p_98138_2_) : var3;
	}
	
	protected abstract InputStream func_98139_b(String var1) throws IOException;
	
	public abstract boolean func_98140_c(String var1);
	
	@Override public String getFirstDescriptionLine()
	{
		return firstDescriptionLine;
	}
	
	@Override public InputStream getResourceAsStream(String p_77532_1_) throws IOException
	{
		return func_98137_a(p_77532_1_, true);
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
	
	private static String trimStringToGUIWidth(String p_77541_0_)
	{
		if(p_77541_0_ != null && p_77541_0_.length() > 34)
		{
			p_77541_0_ = p_77541_0_.substring(0, 34);
		}
		return p_77541_0_;
	}
}
