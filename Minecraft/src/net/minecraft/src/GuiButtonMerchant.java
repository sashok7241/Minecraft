package net.minecraft.src;

import net.minecraft.client.Minecraft;

class GuiButtonMerchant extends GuiButton
{
	private final boolean mirrored;
	
	public GuiButtonMerchant(int par1, int par2, int par3, boolean par4)
	{
		super(par1, par2, par3, 12, 19, "");
		mirrored = par4;
	}
	
	@Override public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		if(drawButton)
		{
			par1Minecraft.renderEngine.bindTexture("/gui/trading.png");
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			boolean var4 = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
			int var5 = 0;
			int var6 = 176;
			if(!enabled)
			{
				var6 += width * 2;
			} else if(var4)
			{
				var6 += width;
			}
			if(!mirrored)
			{
				var5 += height;
			}
			drawTexturedModalRect(xPosition, yPosition, var6, var5, width, height);
		}
	}
}
