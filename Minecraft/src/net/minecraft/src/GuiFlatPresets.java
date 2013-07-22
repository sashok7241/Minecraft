package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class GuiFlatPresets extends GuiScreen
{
	private static RenderItem presetIconRenderer = new RenderItem();
	private static final List presets = new ArrayList();
	private final GuiCreateFlatWorld createFlatWorldGui;
	private String field_82300_d;
	private String field_82308_m;
	private String field_82306_n;
	private GuiFlatPresetsListSlot theFlatPresetsListSlot;
	private GuiButton theButton;
	private GuiTextField theTextField;
	
	public GuiFlatPresets(GuiCreateFlatWorld par1GuiCreateFlatWorld)
	{
		createFlatWorldGui = par1GuiCreateFlatWorld;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.id == 0 && func_82293_j())
		{
			createFlatWorldGui.setFlatGeneratorInfo(theTextField.getText());
			mc.displayGuiScreen(createFlatWorldGui);
		} else if(par1GuiButton.id == 1)
		{
			mc.displayGuiScreen(createFlatWorldGui);
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		theFlatPresetsListSlot.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, field_82300_d, width / 2, 8, 16777215);
		drawString(fontRenderer, field_82308_m, 50, 30, 10526880);
		drawString(fontRenderer, field_82306_n, 50, 70, 10526880);
		theTextField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	private boolean func_82293_j()
	{
		return theFlatPresetsListSlot.field_82459_a > -1 && theFlatPresetsListSlot.field_82459_a < presets.size() || theTextField.getText().length() > 1;
	}
	
	public void func_82296_g()
	{
		boolean var1 = func_82293_j();
		theButton.enabled = var1;
	}
	
	@Override public void initGui()
	{
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		field_82300_d = I18n.func_135053_a("createWorld.customize.presets.title");
		field_82308_m = I18n.func_135053_a("createWorld.customize.presets.share");
		field_82306_n = I18n.func_135053_a("createWorld.customize.presets.list");
		theTextField = new GuiTextField(fontRenderer, 50, 40, width - 100, 20);
		theFlatPresetsListSlot = new GuiFlatPresetsListSlot(this);
		theTextField.setMaxStringLength(1230);
		theTextField.setText(createFlatWorldGui.getFlatGeneratorInfo());
		buttonList.add(theButton = new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.func_135053_a("createWorld.customize.presets.select")));
		buttonList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.func_135053_a("gui.cancel")));
		func_82296_g();
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		if(!theTextField.textboxKeyTyped(par1, par2))
		{
			super.keyTyped(par1, par2);
		}
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		theTextField.mouseClicked(par1, par2, par3);
		super.mouseClicked(par1, par2, par3);
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override public void updateScreen()
	{
		theTextField.updateCursorCounter();
		super.updateScreen();
	}
	
	private static void addPreset(String par0Str, int par1, BiomeGenBase par2BiomeGenBase, List par3List, FlatLayerInfo ... par4ArrayOfFlatLayerInfo)
	{
		FlatGeneratorInfo var5 = new FlatGeneratorInfo();
		for(int var6 = par4ArrayOfFlatLayerInfo.length - 1; var6 >= 0; --var6)
		{
			var5.getFlatLayers().add(par4ArrayOfFlatLayerInfo[var6]);
		}
		var5.setBiome(par2BiomeGenBase.biomeID);
		var5.func_82645_d();
		if(par3List != null)
		{
			Iterator var8 = par3List.iterator();
			while(var8.hasNext())
			{
				String var7 = (String) var8.next();
				var5.getWorldFeatures().put(var7, new HashMap());
			}
		}
		presets.add(new GuiFlatPresetsItem(par1, par0Str, var5.toString()));
	}
	
	private static void addPresetNoFeatures(String par0Str, int par1, BiomeGenBase par2BiomeGenBase, FlatLayerInfo ... par3ArrayOfFlatLayerInfo)
	{
		addPreset(par0Str, par1, par2BiomeGenBase, (List) null, par3ArrayOfFlatLayerInfo);
	}
	
	static GuiFlatPresetsListSlot func_82292_a(GuiFlatPresets par0GuiFlatPresets)
	{
		return par0GuiFlatPresets.theFlatPresetsListSlot;
	}
	
	static GuiTextField func_82298_b(GuiFlatPresets par0GuiFlatPresets)
	{
		return par0GuiFlatPresets.theTextField;
	}
	
	static RenderItem getPresetIconRenderer()
	{
		return presetIconRenderer;
	}
	
	static List getPresets()
	{
		return presets;
	}
	
	static
	{
		addPreset("Classic Flat", Block.grass.blockID, BiomeGenBase.plains, Arrays.asList(new String[] { "village" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(2, Block.dirt.blockID), new FlatLayerInfo(1, Block.bedrock.blockID) });
		addPreset("Tunnelers\' Dream", Block.stone.blockID, BiomeGenBase.extremeHills, Arrays.asList(new String[] { "biome_1", "dungeon", "decoration", "stronghold", "mineshaft" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(5, Block.dirt.blockID), new FlatLayerInfo(230, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID) });
		addPreset("Water World", Block.waterMoving.blockID, BiomeGenBase.plains, Arrays.asList(new String[] { "village", "biome_1" }), new FlatLayerInfo[] { new FlatLayerInfo(90, Block.waterStill.blockID), new FlatLayerInfo(5, Block.sand.blockID), new FlatLayerInfo(5, Block.dirt.blockID), new FlatLayerInfo(5, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID) });
		addPreset("Overworld", Block.tallGrass.blockID, BiomeGenBase.plains, Arrays.asList(new String[] { "village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(3, Block.dirt.blockID), new FlatLayerInfo(59, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID) });
		addPreset("Snowy Kingdom", Block.snow.blockID, BiomeGenBase.icePlains, Arrays.asList(new String[] { "village", "biome_1" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Block.snow.blockID), new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(3, Block.dirt.blockID), new FlatLayerInfo(59, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID) });
		addPreset("Bottomless Pit", Item.feather.itemID, BiomeGenBase.plains, Arrays.asList(new String[] { "village", "biome_1" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(3, Block.dirt.blockID), new FlatLayerInfo(2, Block.cobblestone.blockID) });
		addPreset("Desert", Block.sand.blockID, BiomeGenBase.desert, Arrays.asList(new String[] { "village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon" }), new FlatLayerInfo[] { new FlatLayerInfo(8, Block.sand.blockID), new FlatLayerInfo(52, Block.sandStone.blockID), new FlatLayerInfo(3, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID) });
		addPresetNoFeatures("Redstone Ready", Item.redstone.itemID, BiomeGenBase.desert, new FlatLayerInfo[] { new FlatLayerInfo(52, Block.sandStone.blockID), new FlatLayerInfo(3, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID) });
	}
}
