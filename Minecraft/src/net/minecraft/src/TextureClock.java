package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class TextureClock extends TextureStitched
{
	private double field_94239_h;
	private double field_94240_i;
	
	public TextureClock()
	{
		super("compass");
	}
	
	@Override public void updateAnimation()
	{
		Minecraft var1 = Minecraft.getMinecraft();
		double var2 = 0.0D;
		if(var1.theWorld != null && var1.thePlayer != null)
		{
			float var4 = var1.theWorld.getCelestialAngle(1.0F);
			var2 = var4;
			if(!var1.theWorld.provider.isSurfaceWorld())
			{
				var2 = Math.random();
			}
		}
		double var7;
		for(var7 = var2 - field_94239_h; var7 < -0.5D; ++var7)
		{
			;
		}
		while(var7 >= 0.5D)
		{
			--var7;
		}
		if(var7 < -1.0D)
		{
			var7 = -1.0D;
		}
		if(var7 > 1.0D)
		{
			var7 = 1.0D;
		}
		field_94240_i += var7 * 0.1D;
		field_94240_i *= 0.8D;
		field_94239_h += field_94240_i;
		int var6;
		for(var6 = (int) ((field_94239_h + 1.0D) * textureList.size()) % textureList.size(); var6 < 0; var6 = (var6 + textureList.size()) % textureList.size())
		{
			;
		}
		if(var6 != frameCounter)
		{
			frameCounter = var6;
			textureSheet.copyFrom(originX, originY, (Texture) textureList.get(frameCounter), rotated);
		}
	}
}
