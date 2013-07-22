package net.minecraft.src;

import net.minecraft.client.Minecraft;

class GuiBeaconButton extends GuiButton
{
	private final ResourceLocation buttonTexture;
	private final int field_82257_l;
	private final int field_82258_m;
	private boolean field_82256_n;
	
	protected GuiBeaconButton(int par1, int par2, int par3, ResourceLocation par4ResourceLocation, int par5, int par6)
	{
		super(par1, par2, par3, 22, 22, "");
		buttonTexture = par4ResourceLocation;
		field_82257_l = par5;
		field_82258_m = par6;
	}
	
	@Override public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		if(drawButton)
		{
			par1Minecraft.func_110434_K().func_110577_a(GuiBeacon.func_110427_g());
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
			if(!GuiBeacon.func_110427_g().equals(buttonTexture))
			{
				par1Minecraft.func_110434_K().func_110577_a(buttonTexture);
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
