package net.minecraft.src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;

public class RenderEngine
{
	private HashMap textureMap = new HashMap();
	private HashMap textureContentsMap = new HashMap();
	private IntHashMap textureNameToImageMap = new IntHashMap();
	private IntBuffer imageData = GLAllocation.createDirectIntBuffer(4194304);
	private Map urlToImageDataMap = new HashMap();
	private GameSettings options;
	private TexturePackList texturePack;
	private BufferedImage missingTextureImage = new BufferedImage(64, 64, 2);
	private final TextureMap textureMapBlocks;
	private final TextureMap textureMapItems;
	private int boundTexture;
	
	public RenderEngine(TexturePackList p_i3192_1_, GameSettings p_i3192_2_)
	{
		texturePack = p_i3192_1_;
		options = p_i3192_2_;
		Graphics var3 = missingTextureImage.getGraphics();
		var3.setColor(Color.WHITE);
		var3.fillRect(0, 0, 64, 64);
		var3.setColor(Color.BLACK);
		int var4 = 10;
		int var5 = 0;
		while(var4 < 64)
		{
			String var6 = var5++ % 2 == 0 ? "missing" : "texture";
			var3.drawString(var6, 1, var4);
			var4 += var3.getFont().getSize();
			if(var5 % 2 == 0)
			{
				var4 += 5;
			}
		}
		var3.dispose();
		textureMapBlocks = new TextureMap(0, "terrain", "textures/blocks/", missingTextureImage);
		textureMapItems = new TextureMap(1, "items", "textures/items/", missingTextureImage);
	}
	
	public int allocateAndSetupTexture(BufferedImage p_78353_1_)
	{
		int var2 = GLAllocation.generateTextureNames();
		setupTexture(p_78353_1_, var2);
		textureNameToImageMap.addKey(var2, p_78353_1_);
		return var2;
	}
	
	private void bindTexture(int p_78342_1_)
	{
		if(p_78342_1_ != boundTexture)
		{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, p_78342_1_);
			boundTexture = p_78342_1_;
		}
	}
	
	public void bindTexture(String p_98187_1_)
	{
		this.bindTexture(getTexture(p_98187_1_));
	}
	
	private int[] colorToAnaglyph(int[] p_98186_1_)
	{
		int[] var2 = new int[p_98186_1_.length];
		for(int var3 = 0; var3 < p_98186_1_.length; ++var3)
		{
			int var4 = p_98186_1_[var3] >> 24 & 255;
			int var5 = p_98186_1_[var3] >> 16 & 255;
			int var6 = p_98186_1_[var3] >> 8 & 255;
			int var7 = p_98186_1_[var3] & 255;
			int var8 = (var5 * 30 + var6 * 59 + var7 * 11) / 100;
			int var9 = (var5 * 30 + var6 * 70) / 100;
			int var10 = (var5 * 30 + var7 * 70) / 100;
			var2[var3] = var4 << 24 | var8 << 16 | var9 << 8 | var10;
		}
		return var2;
	}
	
	public void createTextureFromBytes(int[] p_78349_1_, int p_78349_2_, int p_78349_3_, int p_78349_4_)
	{
		this.bindTexture(p_78349_4_);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		if(options != null && options.anaglyph)
		{
			p_78349_1_ = colorToAnaglyph(p_78349_1_);
		}
		imageData.clear();
		imageData.put(p_78349_1_);
		imageData.position(0).limit(p_78349_1_.length);
		GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, p_78349_2_, p_78349_3_, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, imageData);
	}
	
	public void deleteTexture(int p_78344_1_)
	{
		textureNameToImageMap.removeObject(p_78344_1_);
		GL11.glDeleteTextures(p_78344_1_);
	}
	
	private int[] getImageContents(BufferedImage p_78340_1_, int[] p_78340_2_)
	{
		int var3 = p_78340_1_.getWidth();
		int var4 = p_78340_1_.getHeight();
		p_78340_1_.getRGB(0, 0, var3, var4, p_78340_2_, 0, var3);
		return p_78340_2_;
	}
	
	private int[] getImageContentsAndAllocate(BufferedImage p_78348_1_)
	{
		return getImageContents(p_78348_1_, new int[p_78348_1_.getWidth() * p_78348_1_.getHeight()]);
	}
	
	public Icon getMissingIcon(int p_96448_1_)
	{
		switch(p_96448_1_)
		{
			case 0:
				return textureMapBlocks.getMissingIcon();
			case 1:
			default:
				return textureMapItems.getMissingIcon();
		}
	}
	
	private int getTexture(String p_78341_1_)
	{
		if(p_78341_1_.equals("/terrain.png"))
		{
			textureMapBlocks.getTexture().bindTexture(0);
			return textureMapBlocks.getTexture().getGlTextureId();
		} else if(p_78341_1_.equals("/gui/items.png"))
		{
			textureMapItems.getTexture().bindTexture(0);
			return textureMapItems.getTexture().getGlTextureId();
		} else
		{
			Integer var2 = (Integer) textureMap.get(p_78341_1_);
			if(var2 != null) return var2.intValue();
			else
			{
				String var8 = p_78341_1_;
				try
				{
					int var3 = GLAllocation.generateTextureNames();
					boolean var9 = p_78341_1_.startsWith("%blur%");
					if(var9)
					{
						p_78341_1_ = p_78341_1_.substring(6);
					}
					boolean var5 = p_78341_1_.startsWith("%clamp%");
					if(var5)
					{
						p_78341_1_ = p_78341_1_.substring(7);
					}
					InputStream var6 = texturePack.getSelectedTexturePack().getResourceAsStream(p_78341_1_);
					if(var6 == null)
					{
						setupTextureExt(missingTextureImage, var3, var9, var5);
					} else
					{
						setupTextureExt(readTextureImage(var6), var3, var9, var5);
					}
					textureMap.put(var8, Integer.valueOf(var3));
					return var3;
				} catch(Exception var7)
				{
					var7.printStackTrace();
					int var4 = GLAllocation.generateTextureNames();
					setupTexture(missingTextureImage, var4);
					textureMap.put(p_78341_1_, Integer.valueOf(var4));
					return var4;
				}
			}
		}
	}
	
	public int[] getTextureContents(String p_78346_1_)
	{
		ITexturePack var2 = texturePack.getSelectedTexturePack();
		int[] var3 = (int[]) textureContentsMap.get(p_78346_1_);
		if(var3 != null) return var3;
		else
		{
			try
			{
				InputStream var7 = var2.getResourceAsStream(p_78346_1_);
				int[] var4;
				if(var7 == null)
				{
					var4 = getImageContentsAndAllocate(missingTextureImage);
				} else
				{
					var4 = getImageContentsAndAllocate(readTextureImage(var7));
				}
				textureContentsMap.put(p_78346_1_, var4);
				return var4;
			} catch(IOException var6)
			{
				var6.printStackTrace();
				int[] var5 = getImageContentsAndAllocate(missingTextureImage);
				textureContentsMap.put(p_78346_1_, var5);
				return var5;
			}
		}
	}
	
	public int getTextureForDownloadableImage(String p_78350_1_, String p_78350_2_)
	{
		ThreadDownloadImageData var3 = (ThreadDownloadImageData) urlToImageDataMap.get(p_78350_1_);
		if(var3 != null && var3.image != null && !var3.textureSetupComplete)
		{
			if(var3.textureName < 0)
			{
				var3.textureName = allocateAndSetupTexture(var3.image);
			} else
			{
				setupTexture(var3.image, var3.textureName);
			}
			var3.textureSetupComplete = true;
		}
		return var3 != null && var3.textureName >= 0 ? var3.textureName : p_78350_2_ == null ? -1 : getTexture(p_78350_2_);
	}
	
	public boolean hasImageData(String p_82773_1_)
	{
		return urlToImageDataMap.containsKey(p_82773_1_);
	}
	
	public ThreadDownloadImageData obtainImageData(String p_78356_1_, IImageBuffer p_78356_2_)
	{
		ThreadDownloadImageData var3 = (ThreadDownloadImageData) urlToImageDataMap.get(p_78356_1_);
		if(var3 == null)
		{
			urlToImageDataMap.put(p_78356_1_, new ThreadDownloadImageData(p_78356_1_, p_78356_2_));
		} else
		{
			++var3.referenceCount;
		}
		return var3;
	}
	
	private BufferedImage readTextureImage(InputStream p_78345_1_) throws IOException
	{
		BufferedImage var2 = ImageIO.read(p_78345_1_);
		p_78345_1_.close();
		return var2;
	}
	
	public void refreshTextureMaps()
	{
		textureMapBlocks.refreshTextures();
		textureMapItems.refreshTextures();
	}
	
	public void refreshTextures()
	{
		ITexturePack var1 = texturePack.getSelectedTexturePack();
		refreshTextureMaps();
		Iterator var2 = textureNameToImageMap.getKeySet().iterator();
		BufferedImage var4;
		while(var2.hasNext())
		{
			int var3 = ((Integer) var2.next()).intValue();
			var4 = (BufferedImage) textureNameToImageMap.lookup(var3);
			setupTexture(var4, var3);
		}
		ThreadDownloadImageData var10;
		for(var2 = urlToImageDataMap.values().iterator(); var2.hasNext(); var10.textureSetupComplete = false)
		{
			var10 = (ThreadDownloadImageData) var2.next();
		}
		var2 = textureMap.keySet().iterator();
		String var11;
		while(var2.hasNext())
		{
			var11 = (String) var2.next();
			try
			{
				int var12 = ((Integer) textureMap.get(var11)).intValue();
				boolean var6 = var11.startsWith("%blur%");
				if(var6)
				{
					var11 = var11.substring(6);
				}
				boolean var7 = var11.startsWith("%clamp%");
				if(var7)
				{
					var11 = var11.substring(7);
				}
				BufferedImage var5 = readTextureImage(var1.getResourceAsStream(var11));
				setupTextureExt(var5, var12, var6, var7);
			} catch(IOException var9)
			{
				var9.printStackTrace();
			}
		}
		var2 = textureContentsMap.keySet().iterator();
		while(var2.hasNext())
		{
			var11 = (String) var2.next();
			try
			{
				var4 = readTextureImage(var1.getResourceAsStream(var11));
				getImageContents(var4, (int[]) textureContentsMap.get(var11));
			} catch(IOException var8)
			{
				var8.printStackTrace();
			}
		}
		Minecraft.getMinecraft().fontRenderer.readFontData();
		Minecraft.getMinecraft().standardGalacticFontRenderer.readFontData();
	}
	
	public void releaseImageData(String p_78347_1_)
	{
		ThreadDownloadImageData var2 = (ThreadDownloadImageData) urlToImageDataMap.get(p_78347_1_);
		if(var2 != null)
		{
			--var2.referenceCount;
			if(var2.referenceCount == 0)
			{
				if(var2.textureName >= 0)
				{
					deleteTexture(var2.textureName);
				}
				urlToImageDataMap.remove(p_78347_1_);
			}
		}
	}
	
	public void resetBoundTexture()
	{
		boundTexture = -1;
	}
	
	public void setupTexture(BufferedImage p_78351_1_, int p_78351_2_)
	{
		setupTextureExt(p_78351_1_, p_78351_2_, false, false);
	}
	
	public void setupTextureExt(BufferedImage p_98184_1_, int p_98184_2_, boolean p_98184_3_, boolean p_98184_4_)
	{
		this.bindTexture(p_98184_2_);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		if(p_98184_3_)
		{
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		}
		if(p_98184_4_)
		{
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		} else
		{
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		}
		int var5 = p_98184_1_.getWidth();
		int var6 = p_98184_1_.getHeight();
		int[] var7 = new int[var5 * var6];
		p_98184_1_.getRGB(0, 0, var5, var6, var7, 0, var5);
		if(options != null && options.anaglyph)
		{
			var7 = colorToAnaglyph(var7);
		}
		imageData.clear();
		imageData.put(var7);
		imageData.position(0).limit(var7.length);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, var5, var6, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, imageData);
	}
	
	public void updateDynamicTextures()
	{
		textureMapBlocks.updateAnimations();
		textureMapItems.updateAnimations();
	}
}
