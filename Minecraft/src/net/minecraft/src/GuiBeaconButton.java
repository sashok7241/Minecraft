package net.minecraft.src;

import net.minecraft.client.Minecraft;

class GuiBeaconButton extends GuiButton
{
	private final String buttonTexture;
	private final int field_82257_l;
	private final int field_82258_m;
	private boolean field_82256_n;
	
	protected GuiBeaconButton(int p_i5013_1_, int p_i5013_2_, int p_i5013_3_, String p_i5013_4_, int p_i5013_5_, int p_i5013_6_)
	{
		super(p_i5013_1_, p_i5013_2_, p_i5013_3_, 22, 22, "");
		buttonTexture = p_i5013_4_;
		field_82257_l = p_i5013_5_;
		field_82258_m = p_i5013_6_;
	}
	
	@Override public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		if(drawButton)
		{
			par1Minecraft.renderEngine.bindTexture("/gui/beacon.png");
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			field_82253_i = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
			short var4 = 219;
			int var5 = 0;
			if(!enabled)
			{
				var5 += width * 2;
			} else if(field_82256_n)
			{
				var5 += width * 1;
			} else if(field_82253_i)
			{
				var5 += width * 3;
			}
			drawTexturedModalRect(xPosition, yPosition, var5, var4, width, height);
			if(!"/gui/beacon.png".equals(buttonTexture))
			{
				par1Minecraft.renderEngine.bindTexture(buttonTexture);
			}
			drawTexturedModalRect(xPosition + 2, yPosition + 2, field_82257_l, field_82258_m, 18, 18);
		}
	}
	
	public void func_82254_b(boolean par1)
	{
		field_82256_n = par1;
	}
	
	public boolean func_82255_b()
	{
		return field_82256_n;
	}
}
