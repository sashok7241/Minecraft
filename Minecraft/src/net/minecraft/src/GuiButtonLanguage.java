package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class GuiButtonLanguage extends GuiButton
{
	public GuiButtonLanguage(int par1, int par2, int par3)
	{
		super(par1, par2, par3, 20, 20, "");
	}
	
	@Override public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		if(drawButton)
		{
			par1Minecraft.func_110434_K().func_110577_a(GuiButton.field_110332_a);
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
