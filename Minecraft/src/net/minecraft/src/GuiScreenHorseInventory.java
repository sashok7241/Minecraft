package net.minecraft.src;


public class GuiScreenHorseInventory extends GuiContainer
{
	private static final ResourceLocation field_110414_t = new ResourceLocation("textures/gui/container/horse.png");
	private IInventory field_110413_u;
	private IInventory field_110412_v;
	private EntityHorse field_110411_w;
	private float field_110416_x;
	private float field_110415_y;
	
	public GuiScreenHorseInventory(IInventory par1IInventory, IInventory par2IInventory, EntityHorse par3EntityHorse)
	{
		super(new ContainerHorseInventory(par1IInventory, par2IInventory, par3EntityHorse));
		field_110413_u = par1IInventory;
		field_110412_v = par2IInventory;
		field_110411_w = par3EntityHorse;
		allowUserInput = false;
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.func_110434_K().func_110577_a(field_110414_t);
		int var4 = (width - xSize) / 2;
		int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		if(field_110411_w.func_110261_ca())
		{
			drawTexturedModalRect(var4 + 79, var5 + 17, 0, ySize, 90, 54);
		}
		if(field_110411_w.func_110259_cr())
		{
			drawTexturedModalRect(var4 + 7, var5 + 35, 0, ySize + 54, 18, 18);
		}
		GuiInventory.func_110423_a(var4 + 51, var5 + 60, 17, var4 + 51 - field_110416_x, var5 + 75 - 50 - field_110415_y, field_110411_w);
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRenderer.drawString(field_110412_v.isInvNameLocalized() ? field_110412_v.getInvName() : I18n.func_135053_a(field_110412_v.getInvName()), 8, 6, 4210752);
		fontRenderer.drawString(field_110413_u.isInvNameLocalized() ? field_110413_u.getInvName() : I18n.func_135053_a(field_110413_u.getInvName()), 8, ySize - 96 + 2, 4210752);
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		field_110416_x = par1;
		field_110415_y = par2;
		super.drawScreen(par1, par2, par3);
	}
}
