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
				mc.displayGuiScreen(new GuiLanguage(this, options, mc.func_135016_M()));
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
				mc.displayGuiScreen(new GuiScreenTemporaryResourcePackSelect(this, options));
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
		int var1 = 0;
		screenTitle = I18n.func_135053_a("options.title");
		EnumOptions[] var2 = relevantOptions;
		int var3 = var2.length;
		for(int var4 = 0; var4 < var3; ++var4)
		{
			EnumOptions var5 = var2[var4];
			if(var5.getEnumFloat())
			{
				buttonList.add(new GuiSlider(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 - 12 + 24 * (var1 >> 1), var5, options.getKeyBinding(var5), options.getOptionFloatValue(var5)));
			} else
			{
				GuiSmallButton var6 = new GuiSmallButton(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 - 12 + 24 * (var1 >> 1), var5, options.getKeyBinding(var5));
				if(var5 == EnumOptions.DIFFICULTY && mc.theWorld != null && mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
				{
					var6.enabled = false;
					var6.displayString = I18n.func_135053_a("options.difficulty") + ": " + I18n.func_135053_a("options.difficulty.hardcore");
				}
				buttonList.add(var6);
			}
			++var1;
		}
		buttonList.add(new GuiButton(101, width / 2 - 152, height / 6 + 96 - 6, 150, 20, I18n.func_135053_a("options.video")));
		buttonList.add(new GuiButton(100, width / 2 + 2, height / 6 + 96 - 6, 150, 20, I18n.func_135053_a("options.controls")));
		buttonList.add(new GuiButton(102, width / 2 - 152, height / 6 + 120 - 6, 150, 20, I18n.func_135053_a("options.language")));
		buttonList.add(new GuiButton(103, width / 2 + 2, height / 6 + 120 - 6, 150, 20, I18n.func_135053_a("options.multiplayer.title")));
		buttonList.add(new GuiButton(105, width / 2 - 152, height / 6 + 144 - 6, 150, 20, I18n.func_135053_a("options.resourcepack")));
		buttonList.add(new GuiButton(104, width / 2 + 2, height / 6 + 144 - 6, 150, 20, I18n.func_135053_a("options.snooper.view")));
		buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, I18n.func_135053_a("gui.done")));
	}
}
