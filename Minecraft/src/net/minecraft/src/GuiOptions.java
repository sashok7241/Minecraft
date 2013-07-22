package net.minecraft.src;

public class GuiOptions extends GuiScreen
{
	private static final EnumOptions[] relevantOptions = new EnumOptions[] { EnumOptions.MUSIC, EnumOptions.SOUND, EnumOptions.INVERT_MOUSE, EnumOptions.SENSITIVITY, EnumOptions.FOV, EnumOptions.DIFFICULTY, EnumOptions.TOUCHSCREEN };
	private final GuiScreen parentScreen;
	private final GameSettings options;
	protected String screenTitle = "Options";
	
	public GuiOptions(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
	{
		parentScreen = par1GuiScreen;
		options = par2GameSettings;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id < 100 && par1GuiButton instanceof GuiSmallButton)
			{
				options.setOptionValue(((GuiSmallButton) par1GuiButton).returnEnumOptions(), 1);
				par1GuiButton.displayString = options.getKeyBinding(EnumOptions.getEnumOptions(par1GuiButton.id));
			}
			if(par1GuiButton.id == 101)
			{
				mc.gameSettings.saveOptions();
				mc.displayGuiScreen(new GuiVideoSettings(this, options));
			}
			if(par1GuiButton.id == 100)
			{
				mc.gameSettings.saveOptions();
				mc.displayGuiScreen(new GuiControls(this, options));
			}
			if(par1GuiButton.id == 102)
			{
				mc.gameSettings.saveOptions();
				mc.displayGuiScreen(new GuiLanguage(this, options));
			}
			if(par1GuiButton.id == 103)
			{
				mc.gameSettings.saveOptions();
				mc.displayGuiScreen(new ScreenChatOptions(this, options));
			}
			if(par1GuiButton.id == 104)
			{
				mc.gameSettings.saveOptions();
				mc.displayGuiScreen(new GuiSnooper(this, options));
			}
			if(par1GuiButton.id == 200)
			{
				mc.gameSettings.saveOptions();
				mc.displayGuiScreen(parentScreen);
			}
			if(par1GuiButton.id == 105)
			{
				mc.gameSettings.saveOptions();
				mc.displayGuiScreen(new GuiTexturePacks(this, options));
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, screenTitle, width / 2, 15, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		int var2 = 0;
		screenTitle = var1.translateKey("options.title");
		EnumOptions[] var3 = relevantOptions;
		int var4 = var3.length;
		for(int var5 = 0; var5 < var4; ++var5)
		{
			EnumOptions var6 = var3[var5];
			if(var6.getEnumFloat())
			{
				buttonList.add(new GuiSlider(var6.returnEnumOrdinal(), width / 2 - 155 + var2 % 2 * 160, height / 6 - 12 + 24 * (var2 >> 1), var6, options.getKeyBinding(var6), options.getOptionFloatValue(var6)));
			} else
			{
				GuiSmallButton var7 = new GuiSmallButton(var6.returnEnumOrdinal(), width / 2 - 155 + var2 % 2 * 160, height / 6 - 12 + 24 * (var2 >> 1), var6, options.getKeyBinding(var6));
				if(var6 == EnumOptions.DIFFICULTY && mc.theWorld != null && mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
				{
					var7.enabled = false;
					var7.displayString = StatCollector.translateToLocal("options.difficulty") + ": " + StatCollector.translateToLocal("options.difficulty.hardcore");
				}
				buttonList.add(var7);
			}
			++var2;
		}
		buttonList.add(new GuiButton(101, width / 2 - 152, height / 6 + 96 - 6, 150, 20, var1.translateKey("options.video")));
		buttonList.add(new GuiButton(100, width / 2 + 2, height / 6 + 96 - 6, 150, 20, var1.translateKey("options.controls")));
		buttonList.add(new GuiButton(102, width / 2 - 152, height / 6 + 120 - 6, 150, 20, var1.translateKey("options.language")));
		buttonList.add(new GuiButton(103, width / 2 + 2, height / 6 + 120 - 6, 150, 20, var1.translateKey("options.multiplayer.title")));
		buttonList.add(new GuiButton(105, width / 2 - 152, height / 6 + 144 - 6, 150, 20, var1.translateKey("options.texture.pack")));
		buttonList.add(new GuiButton(104, width / 2 + 2, height / 6 + 144 - 6, 150, 20, var1.translateKey("options.snooper.view")));
		buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, var1.translateKey("gui.done")));
	}
}
