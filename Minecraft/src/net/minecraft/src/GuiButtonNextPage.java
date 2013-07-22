package net.minecraft.src;

import net.minecraft.client.Minecraft;

class GuiButtonNextPage extends GuiButton
{
	private final boolean nextPage;
	
	public GuiButtonNextPage(int p_i3087_1_, int p_i3087_2_, int p_i3087_3_, boolean p_i3087_4_)
	{
		super(p_i3087_1_, p_i3087_2_, p_i3087_3_, 23, 13, "");
		nextPage = p_i3087_4_;
	}
	
	@Override public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		if(drawButton)
		{
			boolean var4 = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			par1Minecraft.renderEngine.bindTexture("/gui/book.png");
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
