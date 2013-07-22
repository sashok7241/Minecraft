package net.minecraft.src;


public class GuiCrafting extends GuiContainer
{
	private static final ResourceLocation field_110422_t = new ResourceLocation("textures/gui/container/crafting_table.png");
	
	public GuiCrafting(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
	{
		super(new ContainerWorkbench(par1InventoryPlayer, par2World, par3, par4, par5));
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.func_110434_K().func_110577_a(field_110422_t);
		int var4 = (width - xSize) / 2;
		int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRenderer.drawString(I18n.func_135053_a("container.crafting"), 28, 6, 4210752);
		fontRenderer.drawString(I18n.func_135053_a("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}
}
