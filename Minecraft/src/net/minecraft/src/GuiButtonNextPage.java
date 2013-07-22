package net.minecraft.src;

import net.minecraft.client.Minecraft;

class GuiButtonNextPage extends GuiButton
{
	private final boolean nextPage;
	
	public GuiButtonNextPage(int par1, int par2, int par3, boolean par4)
	{
		super(par1, par2, par3, 23, 13, "");
		nextPage = par4;
	}
	
	@Override public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		if(drawButton)
		{
			boolean var4 = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			par1Minecraft.func_110434_K().func_110577_a(GuiScreenBook.func_110404_g());
			int var5 = 0;
			int var6 = 192;
			if(var4)
			{
				var5 += 23;
			}
			if(!nextPage)
			{
				var6 += 13;
			}
			drawTexturedModalRect(xPosition, yPosition, var5, var6, 23, 13);
		}
	}
}
