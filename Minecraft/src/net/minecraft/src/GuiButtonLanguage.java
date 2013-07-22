package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class GuiButtonLanguage extends GuiButton
{
	public GuiButtonLanguage(int p_i3038_1_, int p_i3038_2_, int p_i3038_3_)
	{
		super(p_i3038_1_, p_i3038_2_, p_i3038_3_, 20, 20, "");
	}
	
	@Override public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		if(drawButton)
		{
			par1Minecraft.renderEngine.bindTexture("/gui/gui.png");
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			boolean var4 = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
			int var5 = 106;
			if(var4)
			{
				var5 += height;
			}
			drawTexturedModalRect(xPosition, yPosition, 0, var5, width, height);
		}
	}
}
