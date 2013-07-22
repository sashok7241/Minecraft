package net.minecraft.src;


public class GuiCrafting extends GuiContainer
{
	public GuiCrafting(InventoryPlayer p_i3090_1_, World p_i3090_2_, int p_i3090_3_, int p_i3090_4_, int p_i3090_5_)
	{
		super(new ContainerWorkbench(p_i3090_1_, p_i3090_2_, p_i3090_3_, p_i3090_4_, p_i3090_5_));
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/gui/crafting.png");
		int var4 = (width - xSize) / 2;
		int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRenderer.drawString(StatCollector.translateToLocal("container.crafting"), 28, 6, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}
}
