package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

public class GuiParticle extends Gui
{
	private List particles = new ArrayList();
	private Minecraft mc;
	
	public GuiParticle(Minecraft par1Minecraft)
	{
		mc = par1Minecraft;
	}
	
	public void draw(float par1)
	{
		mc.renderEngine.bindTexture("/gui/particles.png");
		for(int var2 = 0; var2 < particles.size(); ++var2)
		{
			Particle var3 = (Particle) particles.get(var2);
			int var4 = (int) (var3.prevPosX + (var3.posX - var3.prevPosX) * par1 - 4.0D);
			int var5 = (int) (var3.prevPosY + (var3.posY - var3.prevPosY) * par1 - 4.0D);
			float var6 = (float) (var3.prevTintAlpha + (var3.tintAlpha - var3.prevTintAlpha) * par1);
			float var7 = (float) (var3.prevTintRed + (var3.tintRed - var3.prevTintRed) * par1);
			float var8 = (float) (var3.prevTintGreen + (var3.tintGreen - var3.prevTintGreen) * par1);
			float var9 = (float) (var3.prevTintBlue + (var3.tintBlue - var3.prevTintBlue) * par1);
			GL11.glColor4f(var7, var8, var9, var6);
			drawTexturedModalRect(var4, var5, 40, 0, 8, 8);
		}
	}
	
	public void update()
	{
		for(int var1 = 0; var1 < particles.size(); ++var1)
		{
			Particle var2 = (Particle) particles.get(var1);
			var2.preUpdate();
			var2.update(this);
			if(var2.isDead)
			{
				particles.remove(var1--);
			}
		}
	}
}
