package net.minecraft.src;


public class GuiDispenser extends GuiContainer
{
	private static final ResourceLocation field_110419_u = new ResourceLocation("textures/gui/container/dispenser.png");
	public TileEntityDispenser field_94078_r;
	
	public GuiDispenser(InventoryPlayer par1InventoryPlayer, TileEntityDispenser par2TileEntityDispenser)
	{
		super(new ContainerDispenser(par1InventoryPlayer, par2TileEntityDispenser));
		field_94078_r = par2TileEntityDispenser;
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.func_110434_K().func_110577_a(field_110419_u);
		int var4 = (width - xSize) / 2;
		int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String var3 = field_94078_r.isInvNameLocalized() ? field_94078_r.getInvName() : I18n.func_135053_a(field_94078_r.getInvName());
		fontRenderer.drawString(var3, xSize / 2 - fontRenderer.getStringWidth(var3) / 2, 6, 4210752);
		fontRenderer.drawString(I18n.func_135053_a("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}
}
