package net.minecraft.src;


public class GuiHopper extends GuiContainer
{
	private IInventory field_94081_r;
	private IInventory field_94080_s;
	
	public GuiHopper(InventoryPlayer p_i10020_1_, IInventory p_i10020_2_)
	{
		super(new ContainerHopper(p_i10020_1_, p_i10020_2_));
		field_94081_r = p_i10020_1_;
		field_94080_s = p_i10020_2_;
		allowUserInput = false;
		ySize = 133;
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/gui/hopper.png");
		int var4 = (width - xSize) / 2;
		int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRenderer.drawString(field_94080_s.isInvNameLocalized() ? field_94080_s.getInvName() : StatCollector.translateToLocal(field_94080_s.getInvName()), 8, 6, 4210752);
		fontRenderer.drawString(field_94081_r.isInvNameLocalized() ? field_94081_r.getInvName() : StatCollector.translateToLocal(field_94081_r.getInvName()), 8, ySize - 96 + 2, 4210752);
	}
}
