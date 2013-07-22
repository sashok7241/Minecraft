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
	
	protected TextureStitched(String par1)
	{
		textureName = par1;
	}
	
	public void copyFrom(TextureStitched par1TextureStitched)
	{
		init(par1TextureStitched.textureSheet, par1TextureStitched.textureList, par1TextureStitched.originX, par1TextureStitched.originY, par1TextureStitched.width, par1TextureStitched.height, par1TextureStitched.rotated);
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
	
	public void init(Texture par1Texture, List par2List, int par3, int par4, int par5, int par6, boolean par7)
	{
		textureSheet = par1Texture;
		textureList = par2List;
		originX = par3;
		originY = par4;
		width = par5;
		height = par6;
		rotated = par7;
		float var8 = 0.01F / par1Texture.getWidth();
		float var9 = 0.01F / par1Texture.getHeight();
		minU = (float) par3 / (float) par1Texture.getWidth() + var8;
		maxU = (float) (par3 + par5) / (float) par1Texture.getWidth() - var8;
		minV = (float) par4 / (float) par1Texture.getHeight() + var9;
		maxV = (float) (par4 + par6) / (float) par1Texture.getHeight() - var9;
		widthNorm = par5 / 16.0F;
		heightNorm = par6 / 16.0F;
	}
	
	public void readAnimationInfo(BufferedReader par1BufferedReader)
	{
		ArrayList var2 = new ArrayList();
		try
		{
			for(String var3 = par1BufferedReader.readLine(); var3 != null; var3 = par1BufferedReader.readLine())
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
	
	public static TextureStitched makeTextureStitched(String par0Str)
	{
		return "clock".equals(par0Str) ? new TextureClock() : "compass".equals(par0Str) ? new TextureCompass() : new TextureStitched(par0Str);
	}
}
