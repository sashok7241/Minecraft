package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;

public class Texture
{
	private int glTextureId;
	private int textureId;
	private int textureType;
	private final int width;
	private final int height;
	private final int textureDepth;
	private final int textureFormat;
	private final int textureTarget;
	private final int textureMinFilter;
	private final int textureMagFilter;
	private final int textureWrap;
	private final boolean mipmapActive;
	private final String textureName;
	private Rect2i textureRect;
	private boolean transferred;
	private boolean autoUpload;
	private boolean textureUploaded;
	private ByteBuffer textureData;
	
	public Texture(String p_i11022_1_, int p_i11022_2_, int p_i11022_3_, int p_i11022_4_, int p_i11022_5_, int p_i11022_6_, int p_i11022_7_, int p_i11022_8_, BufferedImage p_i11022_9_)
	{
		this(p_i11022_1_, p_i11022_2_, p_i11022_3_, p_i11022_4_, 1, p_i11022_5_, p_i11022_6_, p_i11022_7_, p_i11022_8_, p_i11022_9_);
	}
	
	private Texture(String p_i11021_1_, int p_i11021_2_, int p_i11021_3_, int p_i11021_4_, int p_i11021_5_, int p_i11021_6_, int p_i11021_7_, int p_i11021_8_, int p_i11021_9_)
	{
		textureName = p_i11021_1_;
		textureType = p_i11021_2_;
		width = p_i11021_3_;
		height = p_i11021_4_;
		textureDepth = p_i11021_5_;
		textureFormat = p_i11021_7_;
		textureMinFilter = p_i11021_8_;
		textureMagFilter = p_i11021_9_;
		textureWrap = p_i11021_6_;
		textureRect = new Rect2i(0, 0, p_i11021_3_, p_i11021_4_);
		if(p_i11021_4_ == 1 && p_i11021_5_ == 1)
		{
			textureTarget = 3552;
		} else if(p_i11021_5_ == 1)
		{
			textureTarget = 3553;
		} else
		{
			textureTarget = 32879;
		}
		mipmapActive = p_i11021_8_ != 9728 && p_i11021_8_ != 9729 || p_i11021_9_ != 9728 && p_i11021_9_ != 9729;
		if(p_i11021_2_ != 2)
		{
			glTextureId = GL11.glGenTextures();
			GL11.glBindTexture(textureTarget, glTextureId);
			GL11.glTexParameteri(textureTarget, GL11.GL_TEXTURE_MIN_FILTER, p_i11021_8_);
			GL11.glTexParameteri(textureTarget, GL11.GL_TEXTURE_MAG_FILTER, p_i11021_9_);
			GL11.glTexParameteri(textureTarget, GL11.GL_TEXTURE_WRAP_S, p_i11021_6_);
			GL11.glTexParameteri(textureTarget, GL11.GL_TEXTURE_WRAP_T, p_i11021_6_);
		} else
		{
			glTextureId = -1;
		}
		textureId = TextureManager.instance().getNextTextureId();
	}
	
	public Texture(String p_i11023_1_, int p_i11023_2_, int p_i11023_3_, int p_i11023_4_, int p_i11023_5_, int p_i11023_6_, int p_i11023_7_, int p_i11023_8_, int p_i11023_9_, BufferedImage p_i11023_10_)
	{
		this(p_i11023_1_, p_i11023_2_, p_i11023_3_, p_i11023_4_, p_i11023_5_, p_i11023_6_, p_i11023_7_, p_i11023_8_, p_i11023_9_);
		if(p_i11023_10_ == null)
		{
			if(p_i11023_3_ != -1 && p_i11023_4_ != -1)
			{
				byte[] var11 = new byte[p_i11023_3_ * p_i11023_4_ * p_i11023_5_ * 4];
				for(int var12 = 0; var12 < var11.length; ++var12)
				{
					var11[var12] = 0;
				}
				textureData = ByteBuffer.allocateDirect(var11.length);
				textureData.clear();
				textureData.put(var11);
				textureData.position(0).limit(var11.length);
				if(autoUpload)
				{
					uploadTexture();
				} else
				{
					textureUploaded = false;
				}
			} else
			{
				transferred = false;
			}
		} else
		{
			transferred = true;
			transferFromImage(p_i11023_10_);
			if(p_i11023_2_ != 2)
			{
				uploadTexture();
				autoUpload = false;
			}
		}
	}
	
	public void bindTexture(int p_94277_1_)
	{
		if(textureDepth == 1)
		{
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		} else
		{
			GL11.glEnable(GL12.GL_TEXTURE_3D);
		}
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit + p_94277_1_);
		GL11.glBindTexture(textureTarget, glTextureId);
		if(!textureUploaded)
		{
			uploadTexture();
		}
	}
	
	public void copyFrom(int p_94281_1_, int p_94281_2_, Texture p_94281_3_, boolean p_94281_4_)
	{
		if(textureTarget != 32879)
		{
			ByteBuffer var5 = p_94281_3_.getTextureData();
			textureData.position(0);
			var5.position(0);
			for(int var6 = 0; var6 < p_94281_3_.getHeight(); ++var6)
			{
				int var7 = p_94281_2_ + var6;
				int var8 = var6 * p_94281_3_.getWidth() * 4;
				int var9 = var7 * width * 4;
				if(p_94281_4_)
				{
					var7 = p_94281_2_ + p_94281_3_.getHeight() - var6;
				}
				for(int var10 = 0; var10 < p_94281_3_.getWidth(); ++var10)
				{
					int var11 = var9 + (var10 + p_94281_1_) * 4;
					int var12 = var8 + var10 * 4;
					if(p_94281_4_)
					{
						var11 = p_94281_1_ + var10 * width * 4 + var7 * 4;
					}
					textureData.put(var11 + 0, var5.get(var12 + 0));
					textureData.put(var11 + 1, var5.get(var12 + 1));
					textureData.put(var11 + 2, var5.get(var12 + 2));
					textureData.put(var11 + 3, var5.get(var12 + 3));
				}
			}
			textureData.position(width * height * 4);
			if(autoUpload)
			{
				uploadTexture();
			} else
			{
				textureUploaded = false;
			}
		}
	}
	
	public void fillRect(Rect2i p_94272_1_, int p_94272_2_)
	{
		if(textureTarget != 32879)
		{
			Rect2i var3 = new Rect2i(0, 0, width, height);
			var3.intersection(p_94272_1_);
			textureData.position(0);
			for(int var4 = var3.getRectY(); var4 < var3.getRectY() + var3.getRectHeight(); ++var4)
			{
				int var5 = var4 * width * 4;
				for(int var6 = var3.getRectX(); var6 < var3.getRectX() + var3.getRectWidth(); ++var6)
				{
					textureData.put(var5 + var6 * 4 + 0, (byte) (p_94272_2_ >> 24 & 255));
					textureData.put(var5 + var6 * 4 + 1, (byte) (p_94272_2_ >> 16 & 255));
					textureData.put(var5 + var6 * 4 + 2, (byte) (p_94272_2_ >> 8 & 255));
					textureData.put(var5 + var6 * 4 + 3, (byte) (p_94272_2_ >> 0 & 255));
				}
			}
			if(autoUpload)
			{
				uploadTexture();
			} else
			{
				textureUploaded = false;
			}
		}
	}
	
	public void func_104062_b(int p_104062_1_, int p_104062_2_, Texture p_104062_3_)
	{
		GL11.glBindTexture(textureTarget, glTextureId);
		GL11.glTexSubImage2D(textureTarget, 0, p_104062_1_, p_104062_2_, p_104062_3_.getWidth(), p_104062_3_.getHeight(), textureFormat, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) p_104062_3_.getTextureData().position(0));
		textureUploaded = true;
	}
	
	public int getGlTextureId()
	{
		return glTextureId;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public ByteBuffer getTextureData()
	{
		return textureData;
	}
	
	public int getTextureId()
	{
		return textureId;
	}
	
	public String getTextureName()
	{
		return textureName;
	}
	
	public final Rect2i getTextureRect()
	{
		return textureRect;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void transferFromImage(BufferedImage p_94278_1_)
	{
		if(textureTarget != 32879)
		{
			int var2 = p_94278_1_.getWidth();
			int var3 = p_94278_1_.getHeight();
			if(var2 <= width && var3 <= height)
			{
				int[] var4 = new int[] { 3, 0, 1, 2 };
				int[] var5 = new int[] { 3, 2, 1, 0 };
				int[] var6 = textureFormat == 32993 ? var5 : var4;
				int[] var7 = new int[width * height];
				int var8 = p_94278_1_.getTransparency();
				p_94278_1_.getRGB(0, 0, width, height, var7, 0, var2);
				byte[] var9 = new byte[width * height * 4];
				for(int var10 = 0; var10 < height; ++var10)
				{
					for(int var11 = 0; var11 < width; ++var11)
					{
						int var12 = var10 * width + var11;
						int var13 = var12 * 4;
						var9[var13 + var6[0]] = (byte) (var7[var12] >> 24 & 255);
						var9[var13 + var6[1]] = (byte) (var7[var12] >> 16 & 255);
						var9[var13 + var6[2]] = (byte) (var7[var12] >> 8 & 255);
						var9[var13 + var6[3]] = (byte) (var7[var12] >> 0 & 255);
					}
				}
				textureData = ByteBuffer.allocateDirect(var9.length);
				textureData.clear();
				textureData.put(var9);
				textureData.limit(var9.length);
				if(autoUpload)
				{
					uploadTexture();
				} else
				{
					textureUploaded = false;
				}
			} else
			{
				Minecraft.getMinecraft().getLogAgent().logWarning("transferFromImage called with a BufferedImage with dimensions (" + var2 + ", " + var3 + ") larger than the Texture dimensions (" + width + ", " + height + "). Ignoring.");
			}
		}
	}
	
	public void uploadTexture()
	{
		textureData.flip();
		if(height != 1 && textureDepth != 1)
		{
			GL12.glTexImage3D(textureTarget, 0, textureFormat, width, height, textureDepth, 0, textureFormat, GL11.GL_UNSIGNED_BYTE, textureData);
		} else if(height != 1)
		{
			GL11.glTexImage2D(textureTarget, 0, textureFormat, width, height, 0, textureFormat, GL11.GL_UNSIGNED_BYTE, textureData);
		} else
		{
			GL11.glTexImage1D(textureTarget, 0, textureFormat, width, 0, textureFormat, GL11.GL_UNSIGNED_BYTE, textureData);
		}
		textureUploaded = true;
	}
	
	public void writeImage(String p_94279_1_)
	{
		BufferedImage var2 = new BufferedImage(width, height, 2);
		ByteBuffer var3 = getTextureData();
		byte[] var4 = new byte[width * height * 4];
		var3.position(0);
		var3.get(var4);
		for(int var5 = 0; var5 < width; ++var5)
		{
			for(int var6 = 0; var6 < height; ++var6)
			{
				int var7 = var6 * width * 4 + var5 * 4;
				byte var8 = 0;
				int var10 = var8 | (var4[var7 + 2] & 255) << 0;
				var10 |= (var4[var7 + 1] & 255) << 8;
				var10 |= (var4[var7 + 0] & 255) << 16;
				var10 |= (var4[var7 + 3] & 255) << 24;
				var2.setRGB(var5, var6, var10);
			}
		}
		textureData.position(width * height * 4);
		try
		{
			ImageIO.write(var2, "png", new File(Minecraft.getMinecraftDir(), p_94279_1_));
		} catch(IOException var9)
		{
			var9.printStackTrace();
		}
	}
}
