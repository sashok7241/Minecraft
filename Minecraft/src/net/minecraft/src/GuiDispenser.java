package net.minecraft.src;


public class GuiDispenser extends GuiContainer
{
	public TileEntityDispenser field_94078_r;
	
	public GuiDispenser(InventoryPlayer p_i3091_1_, TileEntityDispenser p_i3091_2_)
	{
		super(new ContainerDispenser(p_i3091_1_, p_i3091_2_));
		field_94078_r = p_i3091_2_;
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/gui/trap.png");
		int var4 = (width - xSize) / 2;
		int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String var3 = field_94078_r.isInvNameLocalized() ? field_94078_r.getInvName() : StatCollector.translateToLocal(field_94078_r.getInvName());
		fontRenderer.drawString(var3, xSize / 2 - fontRenderer.getStringWidth(var3) / 2, 6, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}
}
