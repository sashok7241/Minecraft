package net.minecraft.src;

public class GuiVideoSettings extends GuiScreen
{
	private GuiScreen parentGuiScreen;
	protected String screenTitle = "Video Settings";
	private GameSettings guiGameSettings;
	private boolean is64bit;
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
			drawCenteredString(fontRenderer, I18n.func_135053_a("options.farWarning1"), width / 2, height / 6 + 144 + 1, 11468800);
			drawCenteredString(fontRenderer, I18n.func_135053_a("options.farWarning2"), width / 2, height / 6 + 144 + 13, 11468800);
		}
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		screenTitle = I18n.func_135053_a("options.videoTitle");
		buttonList.clear();
		buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, I18n.func_135053_a("gui.done")));
		is64bit = false;
		String[] var1 = new String[] { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
		String[] var2 = var1;
		int var3 = var1.length;
		for(int var4 = 0; var4 < var3; ++var4)
		{
			String var5 = var2[var4];
			String var6 = System.getProperty(var5);
			if(var6 != null && var6.contains("64"))
			{
				is64bit = true;
				break;
			}
		}
		int var8 = 0;
		var3 = is64bit ? 0 : -15;
		EnumOptions[] var9 = videoOptions;
		int var10 = var9.length;
		for(int var11 = 0; var11 < var10; ++var11)
		{
			EnumOptions var7 = var9[var11];
			if(var7.getEnumFloat())
			{
				buttonList.add(new GuiSlider(var7.returnEnumOrdinal(), width / 2 - 155 + var8 % 2 * 160, height / 7 + var3 + 24 * (var8 >> 1), var7, guiGameSettings.getKeyBinding(var7), guiGameSettings.getOptionFloatValue(var7)));
			} else
			{
				buttonList.add(new GuiSmallButton(var7.returnEnumOrdinal(), width / 2 - 155 + var8 % 2 * 160, height / 7 + var3 + 24 * (var8 >> 1), var7, guiGameSettings.getKeyBinding(var7)));
			}
			++var8;
		}
	}
}
