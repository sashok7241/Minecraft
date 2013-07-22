package net.minecraft.src;


public class GuiBrewingStand extends GuiContainer
{
	private static final ResourceLocation field_110420_t = new ResourceLocation("textures/gui/container/brewing_stand.png");
	private TileEntityBrewingStand brewingStand;
	
	public GuiBrewingStand(InventoryPlayer par1InventoryPlayer, TileEntityBrewingStand par2TileEntityBrewingStand)
	{
		super(new ContainerBrewingStand(par1InventoryPlayer, par2TileEntityBrewingStand));
		brewingStand = par2TileEntityBrewingStand;
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.func_110434_K().func_110577_a(field_110420_t);
		int var4 = (width - xSize) / 2;
		int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		int var6 = brewingStand.getBrewTime();
		if(var6 > 0)
		{
			int var7 = (int) (28.0F * (1.0F - var6 / 400.0F));
			if(var7 > 0)
			{
				drawTexturedModalRect(var4 + 97, var5 + 16, 176, 0, 9, var7);
			}
			int var8 = var6 / 2 % 7;
			switch(var8)
			{
				case 0:
					var7 = 29;
					break;
				case 1:
					var7 = 24;
					break;
				case 2:
					var7 = 20;
					break;
				case 3:
					var7 = 16;
					break;
				case 4:
					var7 = 11;
					break;
				case 5:
					var7 = 6;
					break;
				case 6:
					var7 = 0;
			}
			if(var7 > 0)
			{
				drawTexturedModalRect(var4 + 65, var5 + 14 + 29 - var7, 185, 29 - var7, 12, var7);
			}
		}
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String var3 = brewingStand.isInvNameLocalized() ? brewingStand.getInvName() : I18n.func_135053_a(brewingStand.getInvName());
		fontRenderer.drawString(var3, xSize / 2 - fontRenderer.getStringWidth(var3) / 2, 6, 4210752);
		fontRenderer.drawString(I18n.func_135053_a("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}
}
