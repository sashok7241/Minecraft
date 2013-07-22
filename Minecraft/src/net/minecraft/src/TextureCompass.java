package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class TextureCompass extends TextureStitched
{
	public static TextureCompass compassTexture;
	public double currentAngle;
	public double angleDelta;
	
	public TextureCompass()
	{
		super("compass");
		compassTexture = this;
	}
	
	@Override public void updateAnimation()
	{
		Minecraft var1 = Minecraft.getMinecraft();
		if(var1.theWorld != null && var1.thePlayer != null)
		{
			updateCompass(var1.theWorld, var1.thePlayer.posX, var1.thePlayer.posZ, var1.thePlayer.rotationYaw, false, false);
		} else
		{
			updateCompass((World) null, 0.0D, 0.0D, 0.0D, true, false);
		}
	}
	
	public void updateCompass(World par1World, double par2, double par4, double par6, boolean par8, boolean par9)
	{
		double var10 = 0.0D;
		if(par1World != null && !par8)
		{
			ChunkCoordinates var12 = par1World.getSpawnPoint();
			double var13 = var12.posX - par2;
			double var15 = var12.posZ - par4;
			par6 %= 360.0D;
			var10 = -((par6 - 90.0D) * Math.PI / 180.0D - Math.atan2(var15, var13));
			if(!par1World.provider.isSurfaceWorld())
			{
				var10 = Math.random() * Math.PI * 2.0D;
			}
		}
		if(par9)
		{
			currentAngle = var10;
		} else
		{
			double var17;
			for(var17 = var10 - currentAngle; var17 < -Math.PI; var17 += Math.PI * 2D)
			{
				;
			}
			while(var17 >= Math.PI)
			{
				var17 -= Math.PI * 2D;
			}
			if(var17 < -1.0D)
			{
				var17 = -1.0D;
			}
			if(var17 > 1.0D)
			{
				var17 = 1.0D;
			}
			angleDelta += var17 * 0.1D;
			angleDelta *= 0.8D;
			currentAngle += angleDelta;
		}
		int var18;
		for(var18 = (int) ((currentAngle / (Math.PI * 2D) + 1.0D) * textureList.size()) % textureList.size(); var18 < 0; var18 = (var18 + textureList.size()) % textureList.size())
		{
			;
		}
		if(var18 != frameCounter)
		{
			frameCounter = var18;
			textureSheet.copyFrom(originX, originY, (Texture) textureList.get(frameCounter), rotated);
		}
	}
}
