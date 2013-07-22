package net.minecraft.src;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class TextureStitched implements Icon
{
	private final String textureName;
	protected Texture textureSheet;
	protected List textureList;
	private List listAnimationTuples;
	protected boolean rotated;
	protected int originX;
	protected int originY;
	private int width;
	private int height;
	private float minU;
	private float maxU;
	private float minV;
	private float maxV;
	private float widthNorm;
	private float heightNorm;
	protected int frameCounter = 0;
	protected int tickCounter = 0;
	
	protected TextureStitched(String p_i9013_1_)
	{
		textureName = p_i9013_1_;
	}
	
	public void copyFrom(TextureStitched par1TextureAtlasSprite)
	{
		init(par1TextureAtlasSprite.textureSheet, par1TextureAtlasSprite.textureList, par1TextureAtlasSprite.originX, par1TextureAtlasSprite.originY, par1TextureAtlasSprite.width, par1TextureAtlasSprite.height, par1TextureAtlasSprite.rotated);
	}
	
	@Override public String getIconName()
	{
		return textureName;
	}
	
	@Override public float getInterpolatedU(double par1)
	{
		float var3 = maxU - minU;
		return minU + var3 * ((float) par1 / 16.0F);
	}
	
	@Override public float getInterpolatedV(double par1)
	{
		float var3 = maxV - minV;
		return minV + var3 * ((float) par1 / 16.0F);
	}
	
	@Override public float getMaxU()
	{
		return maxU;
	}
	
	@Override public float getMaxV()
	{
		return maxV;
	}
	
	@Override public float getMinU()
	{
		return minU;
	}
	
	@Override public float getMinV()
	{
		return minV;
	}
	
	@Override public int getOriginX()
	{
		return originX;
	}
	
	@Override public int getOriginY()
	{
		return originY;
	}
	
	@Override public int getSheetHeight()
	{
		return textureSheet.getHeight();
	}
	
	@Override public int getSheetWidth()
	{
		return textureSheet.getWidth();
	}
	
	public void init(Texture p_94218_1_, List p_94218_2_, int p_94218_3_, int p_94218_4_, int p_94218_5_, int p_94218_6_, boolean p_94218_7_)
	{
		textureSheet = p_94218_1_;
		textureList = p_94218_2_;
		originX = p_94218_3_;
		originY = p_94218_4_;
		width = p_94218_5_;
		height = p_94218_6_;
		rotated = p_94218_7_;
		float var8 = 0.01F / p_94218_1_.getWidth();
		float var9 = 0.01F / p_94218_1_.getHeight();
		minU = (float) p_94218_3_ / (float) p_94218_1_.getWidth() + var8;
		maxU = (float) (p_94218_3_ + p_94218_5_) / (float) p_94218_1_.getWidth() - var8;
		minV = (float) p_94218_4_ / (float) p_94218_1_.getHeight() + var9;
		maxV = (float) (p_94218_4_ + p_94218_6_) / (float) p_94218_1_.getHeight() - var9;
		widthNorm = p_94218_5_ / 16.0F;
		heightNorm = p_94218_6_ / 16.0F;
	}
	
	public void readAnimationInfo(BufferedReader p_94221_1_)
	{
		ArrayList var2 = new ArrayList();
		try
		{
			for(String var3 = p_94221_1_.readLine(); var3 != null; var3 = p_94221_1_.readLine())
			{
				var3 = var3.trim();
				if(var3.length() > 0)
				{
					String[] var4 = var3.split(",");
					String[] var5 = var4;
					int var6 = var4.length;
					for(int var7 = 0; var7 < var6; ++var7)
					{
						String var8 = var5[var7];
						int var9 = var8.indexOf(42);
						if(var9 > 0)
						{
							Integer var10 = new Integer(var8.substring(0, var9));
							Integer var11 = new Integer(var8.substring(var9 + 1));
							var2.add(new Tuple(var10, var11));
						} else
						{
							var2.add(new Tuple(new Integer(var8), Integer.valueOf(1)));
						}
					}
				}
			}
		} catch(Exception var12)
		{
			System.err.println("Failed to read animation info for " + textureName + ": " + var12.getMessage());
		}
		if(!var2.isEmpty() && var2.size() < 600)
		{
			listAnimationTuples = var2;
		}
	}
	
	public void updateAnimation()
	{
		if(listAnimationTuples != null)
		{
			Tuple var1 = (Tuple) listAnimationTuples.get(frameCounter);
			++tickCounter;
			if(tickCounter >= ((Integer) var1.getSecond()).intValue())
			{
				int var2 = ((Integer) var1.getFirst()).intValue();
				frameCounter = (frameCounter + 1) % listAnimationTuples.size();
				tickCounter = 0;
				var1 = (Tuple) listAnimationTuples.get(frameCounter);
				int var3 = ((Integer) var1.getFirst()).intValue();
				if(var2 != var3 && var3 >= 0 && var3 < textureList.size())
				{
					textureSheet.func_104062_b(originX, originY, (Texture) textureList.get(var3));
				}
			}
		} else
		{
			int var4 = frameCounter;
			frameCounter = (frameCounter + 1) % textureList.size();
			if(var4 != frameCounter)
			{
				textureSheet.func_104062_b(originX, originY, (Texture) textureList.get(frameCounter));
			}
		}
	}
	
	public static TextureStitched makeTextureStitched(String p_94220_0_)
	{
		return "clock".equals(p_94220_0_) ? new TextureClock() : "compass".equals(p_94220_0_) ? new TextureCompass() : new TextureStitched(p_94220_0_);
	}
}
