package net.minecraft.src;

class GuiSnooperList extends GuiSlot
{
	final GuiSnooper snooperGui;
	
	public GuiSnooperList(GuiSnooper par1GuiSnooper)
	{
		super(par1GuiSnooper.mc, par1GuiSnooper.width, par1GuiSnooper.height, 80, par1GuiSnooper.height - 40, par1GuiSnooper.fontRenderer.FONT_HEIGHT + 1);
		snooperGui = par1GuiSnooper;
	}
	
	@Override protected void drawBackground()
	{
	}
	
	@Override protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		snooperGui.fontRenderer.drawString((String) GuiSnooper.func_74095_a(snooperGui).get(par1), 10, par3, 16777215);
		snooperGui.fontRenderer.drawString((String) GuiSnooper.func_74094_b(snooperGui).get(par1), 230, par3, 16777215);
	}
	
	@Override protected void elementClicked(int par1, boolean par2)
	{
	}
	
	@Override protected int getScrollBarX()
	{
		return snooperGui.width - 10;
	}
	
	@Override protected int getSize()
	{
		return GuiSnooper.func_74095_a(snooperGui).size();
	}
	
	@Override protected boolean isSelected(int par1)
	{
		return false;
	}
}
