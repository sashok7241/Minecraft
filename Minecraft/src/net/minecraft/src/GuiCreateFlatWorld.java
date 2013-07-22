package net.minecraft.src;

public class GuiCreateFlatWorld extends GuiScreen
{
	private static RenderItem theRenderItem = new RenderItem();
	private final GuiCreateWorld createWorldGui;
	private FlatGeneratorInfo theFlatGeneratorInfo = FlatGeneratorInfo.getDefaultFlatGenerator();
	private String customizationTitle;
	private String layerMaterialLabel;
	private String heightLabel;
	private GuiCreateFlatWorldListSlot createFlatWorldListSlotGui;
	private GuiButton buttonAddLayer;
	private GuiButton buttonEditLayer;
	private GuiButton buttonRemoveLayer;
	
	public GuiCreateFlatWorld(GuiCreateWorld par1GuiCreateWorld, String par2Str)
	{
		createWorldGui = par1GuiCreateWorld;
		setFlatGeneratorInfo(par2Str);
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		int var2 = theFlatGeneratorInfo.getFlatLayers().size() - createFlatWorldListSlotGui.field_82454_a - 1;
		if(par1GuiButton.id == 1)
		{
			mc.displayGuiScreen(createWorldGui);
		} else if(par1GuiButton.id == 0)
		{
			createWorldGui.generatorOptionsToUse = getFlatGeneratorInfo();
			mc.displayGuiScreen(createWorldGui);
		} else if(par1GuiButton.id == 5)
		{
			mc.displayGuiScreen(new GuiFlatPresets(this));
		} else if(par1GuiButton.id == 4 && func_82272_i())
		{
			theFlatGeneratorInfo.getFlatLayers().remove(var2);
			createFlatWorldListSlotGui.field_82454_a = Math.min(createFlatWorldListSlotGui.field_82454_a, theFlatGeneratorInfo.getFlatLayers().size() - 1);
		}
		theFlatGeneratorInfo.func_82645_d();
		func_82270_g();
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		createFlatWorldListSlotGui.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, customizationTitle, width / 2, 8, 16777215);
		int var4 = width / 2 - 92 - 16;
		drawString(fontRenderer, layerMaterialLabel, var4, 32, 16777215);
		drawString(fontRenderer, heightLabel, var4 + 2 + 213 - fontRenderer.getStringWidth(heightLabel), 32, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	public void func_82270_g()
	{
		boolean var1 = func_82272_i();
		buttonRemoveLayer.enabled = var1;
		buttonEditLayer.enabled = var1;
		buttonEditLayer.enabled = false;
		buttonAddLayer.enabled = false;
	}
	
	private boolean func_82272_i()
	{
		return createFlatWorldListSlotGui.field_82454_a > -1 && createFlatWorldListSlotGui.field_82454_a < theFlatGeneratorInfo.getFlatLayers().size();
	}
	
	public String getFlatGeneratorInfo()
	{
		return theFlatGeneratorInfo.toString();
	}
	
	@Override public void initGui()
	{
		buttonList.clear();
		customizationTitle = I18n.func_135053_a("createWorld.customize.flat.title");
		layerMaterialLabel = I18n.func_135053_a("createWorld.customize.flat.tile");
		heightLabel = I18n.func_135053_a("createWorld.customize.flat.height");
		createFlatWorldListSlotGui = new GuiCreateFlatWorldListSlot(this);
		buttonList.add(buttonAddLayer = new GuiButton(2, width / 2 - 154, height - 52, 100, 20, I18n.func_135053_a("createWorld.customize.flat.addLayer") + " (NYI)"));
		buttonList.add(buttonEditLayer = new GuiButton(3, width / 2 - 50, height - 52, 100, 20, I18n.func_135053_a("createWorld.customize.flat.editLayer") + " (NYI)"));
		buttonList.add(buttonRemoveLayer = new GuiButton(4, width / 2 - 155, height - 52, 150, 20, I18n.func_135053_a("createWorld.customize.flat.removeLayer")));
		buttonList.add(new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.func_135053_a("gui.done")));
		buttonList.add(new GuiButton(5, width / 2 + 5, height - 52, 150, 20, I18n.func_135053_a("createWorld.customize.presets")));
		buttonList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.func_135053_a("gui.cancel")));
		buttonAddLayer.drawButton = buttonEditLayer.drawButton = false;
		theFlatGeneratorInfo.func_82645_d();
		func_82270_g();
	}
	
	public void setFlatGeneratorInfo(String par1Str)
	{
		theFlatGeneratorInfo = FlatGeneratorInfo.createFlatGeneratorFromString(par1Str);
	}
	
	static FlatGeneratorInfo func_82271_a(GuiCreateFlatWorld par0GuiCreateFlatWorld)
	{
		return par0GuiCreateFlatWorld.theFlatGeneratorInfo;
	}
	
	static RenderItem getRenderItem()
	{
		return theRenderItem;
	}
}
