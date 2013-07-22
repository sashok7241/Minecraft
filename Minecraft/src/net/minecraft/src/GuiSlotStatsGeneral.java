package net.minecraft.src;

class GuiSlotStatsGeneral extends GuiSlot
{
	final GuiStats statsGui;
	
	public GuiSlotStatsGeneral(GuiStats p_i3076_1_)
	{
		super(GuiStats.getMinecraft(p_i3076_1_), p_i3076_1_.width, p_i3076_1_.height, 32, p_i3076_1_.height - 64, 10);
		statsGui = p_i3076_1_;
		setShowSelectionBox(false);
	}
	
	@Override protected void drawBackground()
	{
		statsGui.drawDefaultBackground();
	}
	
	@Override protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		StatBase var6 = (StatBase) StatList.generalStats.get(par1);
		statsGui.drawString(GuiStats.getFontRenderer1(statsGui), StatCollector.translateToLocal(var6.getName()), par2 + 2, par3 + 1, par1 % 2 == 0 ? 16777215 : 9474192);
		String var7 = var6.func_75968_a(GuiStats.getStatsFileWriter(statsGui).writeStat(var6));
		statsGui.drawString(GuiStats.getFontRenderer2(statsGui), var7, par2 + 2 + 213 - GuiStats.getFontRenderer3(statsGui).getStringWidth(var7), par3 + 1, par1 % 2 == 0 ? 16777215 : 9474192);
	}
	
	@Override protected void elementClicked(int par1, boolean par2)
	{
	}
	
	@Override protected int getContentHeight()
	{
		return getSize() * 10;
	}
	
	@Override protected int getSize()
	{
		return StatList.generalStats.size();
	}
	
	@Override protected boolean isSelected(int par1)
	{
		return false;
	}
}
