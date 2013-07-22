package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class GuiButton extends Gui
{
	protected static final ResourceLocation field_110332_a = new ResourceLocation("textures/gui/widgets.png");
	protected int width;
	protected int height;
	public int xPosition;
	public int yPosition;
	public String displayString;
	public int id;
	public boolean enabled;
	public boolean drawButton;
	protected boolean field_82253_i;
	
	public GuiButton(int par1, int par2, int par3, int par4, int par5, String par6Str)
	{
		width = 200;
		height = 20;
		enabled = true;
		drawButton = true;
		id = par1;
		xPosition = par2;
		yPosition = par3;
		width = par4;
		height = par5;
		displayString = par6Str;
	}
	
	public GuiButton(int par1, int par2, int par3, String par4Str)
	{
		this(par1, par2, par3, 200, 20, par4Str);
	}
	
	public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		if(drawButton)
		{
			FontRenderer var4 = par1Minecraft.fontRenderer;
			par1Minecraft.func_110434_K().func_110577_a(field_110332_a);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			field_82253_i = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
			int var5 = getHoverState(field_82253_i);
			drawTexturedModalRect(xPosition, yPosition, 0, 46 + var5 * 20, width / 2, height);
			drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + var5 * 20, width / 2, height);
			mouseDragged(par1Minecraft, par2, par3);
			int var6 = 14737632;
			if(!enabled)
			{
				var6 = -6250336;
			} else if(field_82253_i)
			{
				var6 = 16777120;
			}
			drawCenteredString(var4, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, var6);
		}
	}
	
	public void func_82251_b(int par1, int par2)
	{
	}
	
	public boolean func_82252_a()
	{
		return field_82253_i;
	}
	
	protected int getHoverState(boolean par1)
	{
		byte var2 = 1;
		if(!enabled)
		{
			var2 = 0;
		} else if(par1)
		{
			var2 = 2;
		}
		return var2;
	}
	
	protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3)
	{
	}
	
	public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
	{
		return enabled && drawButton && par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
	}
	
	public void mouseReleased(int par1, int par2)
	{
	}
}
