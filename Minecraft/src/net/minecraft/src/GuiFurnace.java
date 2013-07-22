package net.minecraft.src;


public class GuiFurnace extends GuiContainer
{
	private TileEntityFurnace furnaceInventory;
	
	public GuiFurnace(InventoryPlayer par1InventoryPlayer, TileEntityFurnace par2TileEntityFurnace)
	{
		super(new ContainerFurnace(par1InventoryPlayer, par2TileEntityFurnace));
		furnaceInventory = par2TileEntityFurnace;
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/gui/furnace.png");
		int var4 = (width - xSize) / 2;
		int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		int var6;
		if(furnaceInventory.isBurning())
		{
			var6 = furnaceInventory.getBurnTimeRemainingScaled(12);
			drawTexturedModalRect(var4 + 56, var5 + 36 + 12 - var6, 176, 12 - var6, 14, var6 + 2);
		}
		var6 = furnaceInventory.getCookProgressScaled(24);
		drawTexturedModalRect(var4 + 79, var5 + 34, 176, 14, var6 + 1, 16);
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String var3 = furnaceInventory.isInvNameLocalized() ? furnaceInventory.getInvName() : StatCollector.translateToLocal(furnaceInventory.getInvName());
		fontRenderer.drawString(var3, xSize / 2 - fontRenderer.getStringWidth(var3) / 2, 6, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}
}
