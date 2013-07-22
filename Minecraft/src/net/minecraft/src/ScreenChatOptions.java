package net.minecraft.src;

public class ScreenChatOptions extends GuiScreen
{
	private static final EnumOptions[] allScreenChatOptions = new EnumOptions[] { EnumOptions.CHAT_VISIBILITY, EnumOptions.CHAT_COLOR, EnumOptions.CHAT_LINKS, EnumOptions.CHAT_OPACITY, EnumOptions.CHAT_LINKS_PROMPT, EnumOptions.CHAT_SCALE, EnumOptions.CHAT_HEIGHT_FOCUSED, EnumOptions.CHAT_HEIGHT_UNFOCUSED, EnumOptions.CHAT_WIDTH };
	private static final EnumOptions[] allMultiplayerOptions = new EnumOptions[] { EnumOptions.SHOW_CAPE };
	private final GuiScreen theGuiScreen;
	private final GameSettings theSettings;
	private String theChatOptions;
	private String field_82268_n;
	private int field_82269_o;
	
	public ScreenChatOptions(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
	{
		theGuiScreen = par1GuiScreen;
		theSettings = par2GameSettings;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id < 100 && par1GuiButton instanceof GuiSmallButton)
			{
				theSettings.setOptionValue(((GuiSmallButton) par1GuiButton).returnEnumOptions(), 1);
				par1GuiButton.displayString = theSettings.getKeyBinding(EnumOptions.getEnumOptions(par1GuiButton.id));
			}
			if(par1GuiButton.id == 200)
			{
				mc.gameSettings.saveOptions();
				mc.displayGuiScreen(theGuiScreen);
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, theChatOptions, width / 2, 20, 16777215);
		drawCenteredString(fontRenderer, field_82268_n, width / 2, field_82269_o + 7, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		int var1 = 0;
		theChatOptions = I18n.func_135053_a("options.chat.title");
		field_82268_n = I18n.func_135053_a("options.multiplayer.title");
		EnumOptions[] var2 = allScreenChatOptions;
		int var3 = var2.length;
		int var4;
		EnumOptions var5;
		for(var4 = 0; var4 < var3; ++var4)
		{
			var5 = var2[var4];
			if(var5.getEnumFloat())
			{
				buttonList.add(new GuiSlider(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 + 24 * (var1 >> 1), var5, theSettings.getKeyBinding(var5), theSettings.getOptionFloatValue(var5)));
			} else
			{
				buttonList.add(new GuiSmallButton(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 + 24 * (var1 >> 1), var5, theSettings.getKeyBinding(var5)));
			}
			++var1;
		}
		if(var1 % 2 == 1)
		{
			++var1;
		}
		field_82269_o = height / 6 + 24 * (var1 >> 1);
		var1 += 2;
		var2 = allMultiplayerOptions;
		var3 = var2.length;
		for(var4 = 0; var4 < var3; ++var4)
		{
			var5 = var2[var4];
			if(var5.getEnumFloat())
			{
				buttonList.add(new GuiSlider(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 + 24 * (var1 >> 1), var5, theSettings.getKeyBinding(var5), theSettings.getOptionFloatValue(var5)));
			} else
			{
				buttonList.add(new GuiSmallButton(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 + 24 * (var1 >> 1), var5, theSettings.getKeyBinding(var5)));
			}
			++var1;
		}
		buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, I18n.func_135053_a("gui.done")));
	}
}
