package net.minecraft.src;


public class GuiChest extends GuiContainer
{
	private IInventory upperChestInventory;
	private IInventory lowerChestInventory;
	private int inventoryRows = 0;
	
	public GuiChest(IInventory p_i3078_1_, IInventory p_i3078_2_)
	{
		super(new ContainerChest(p_i3078_1_, p_i3078_2_));
		upperChestInventory = p_i3078_1_;
		lowerChestInventory = p_i3078_2_;
		allowUserInput = false;
		short var3 = 222;
		int var4 = var3 - 108;
		inventoryRows = p_i3078_2_.getSizeInventory() / 9;
		ySize = var4 + inventoryRows * 18;
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/gui/container.png");
		int var4 = (width - xSize) / 2;
		int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, inventoryRows * 18 + 17);
		drawTexturedModalRect(var4, var5 + inventoryRows * 18 + 17, 0, 126, xSize, 96);
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRenderer.drawString(lowerChestInventory.isInvNameLocalized() ? lowerChestInventory.getInvName() : StatCollector.translateToLocal(lowerChestInventory.getInvName()), 8, 6, 4210752);
		fontRenderer.drawString(upperChestInventory.isInvNameLocalized() ? upperChestInventory.getInvName() : StatCollector.translateToLocal(upperChestInventory.getInvName()), 8, ySize - 96 + 2, 4210752);
	}
}
