package net.minecraft.src;

public class GuiVideoSettings extends GuiScreen
{
	private GuiScreen parentGuiScreen;
	protected String screenTitle = "Video Settings";
	private GameSettings guiGameSettings;
	private boolean is64bit = false;
	private static EnumOptions[] videoOptions = new EnumOptions[] { EnumOptions.GRAPHICS, EnumOptions.RENDER_DISTANCE, EnumOptions.AMBIENT_OCCLUSION, EnumOptions.FRAMERATE_LIMIT, EnumOptions.ANAGLYPH, EnumOptions.VIEW_BOBBING, EnumOptions.GUI_SCALE, EnumOptions.ADVANCED_OPENGL, EnumOptions.GAMMA, EnumOptions.RENDER_CLOUDS, EnumOptions.PARTICLES, EnumOptions.USE_SERVER_TEXTURES, EnumOptions.USE_FULLSCREEN, EnumOptions.ENABLE_VSYNC };
	
	public GuiVideoSettings(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
	{
		parentGuiScreen = par1GuiScreen;
		guiGameSettings = par2GameSettings;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			int var2 = guiGameSettings.guiScale;
			if(par1GuiButton.id < 100 && par1GuiButton instanceof GuiSmallButton)
			{
				guiGameSettings.setOptionValue(((GuiSmallButton) par1GuiButton).returnEnumOptions(), 1);
				par1GuiButton.displayString = guiGameSettings.getKeyBinding(EnumOptions.getEnumOptions(par1GuiButton.id));
			}
			if(par1GuiButton.id == 200)
			{
				mc.gameSettings.saveOptions();
				mc.displayGuiScreen(parentGuiScreen);
			}
			if(guiGameSettings.guiScale != var2)
			{
				ScaledResolution var3 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
				int var4 = var3.getScaledWidth();
				int var5 = var3.getScaledHeight();
				setWorldAndResolution(mc, var4, var5);
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, screenTitle, width / 2, is64bit ? 20 : 5, 16777215);
		if(!is64bit && guiGameSettings.renderDistance == 0)
		{
			drawCenteredString(fontRenderer, StatCollector.translateToLocal("options.farWarning1"), width / 2, height / 6 + 144 + 1, 11468800);
			drawCenteredString(fontRenderer, StatCollector.translateToLocal("options.farWarning2"), width / 2, height / 6 + 144 + 13, 11468800);
		}
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		screenTitle = var1.translateKey("options.videoTitle");
		buttonList.clear();
		buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, var1.translateKey("gui.done")));
		is64bit = false;
		String[] var2 = new String[] { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
		String[] var3 = var2;
		int var4 = var2.length;
		for(int var5 = 0; var5 < var4; ++var5)
		{
			String var6 = var3[var5];
			String var7 = System.getProperty(var6);
			if(var7 != null && var7.contains("64"))
			{
				is64bit = true;
				break;
			}
		}
		int var9 = 0;
		var4 = is64bit ? 0 : -15;
		EnumOptions[] var10 = videoOptions;
		int var11 = var10.length;
		for(int var12 = 0; var12 < var11; ++var12)
		{
			EnumOptions var8 = var10[var12];
			if(var8.getEnumFloat())
			{
				buttonList.add(new GuiSlider(var8.returnEnumOrdinal(), width / 2 - 155 + var9 % 2 * 160, height / 7 + var4 + 24 * (var9 >> 1), var8, guiGameSettings.getKeyBinding(var8), guiGameSettings.getOptionFloatValue(var8)));
			} else
			{
				buttonList.add(new GuiSmallButton(var8.returnEnumOrdinal(), width / 2 - 155 + var9 % 2 * 160, height / 7 + var4 + 24 * (var9 >> 1), var8, guiGameSettings.getKeyBinding(var8)));
			}
			++var9;
		}
	}
}
