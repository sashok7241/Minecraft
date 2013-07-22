package net.minecraft.src;

public class ScreenChatOptions extends GuiScreen
{
	private static final EnumOptions[] allScreenChatOptions = new EnumOptions[] { EnumOptions.CHAT_VISIBILITY, EnumOptions.CHAT_COLOR, EnumOptions.CHAT_LINKS, EnumOptions.CHAT_OPACITY, EnumOptions.CHAT_LINKS_PROMPT, EnumOptions.CHAT_SCALE, EnumOptions.CHAT_HEIGHT_FOCUSED, EnumOptions.CHAT_HEIGHT_UNFOCUSED, EnumOptions.CHAT_WIDTH };
	private static final EnumOptions[] allMultiplayerOptions = new EnumOptions[] { EnumOptions.SHOW_CAPE };
	private final GuiScreen theGuiScreen;
	private final GameSettings theSettings;
	private String theChatOptions;
	private String field_82268_n;
	private int field_82269_o = 0;
	
	public ScreenChatOptions(GuiScreen p_i3044_1_, GameSettings p_i3044_2_)
	{
		theGuiScreen = p_i3044_1_;
		theSettings = p_i3044_2_;
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
		StringTranslate var1 = StringTranslate.getInstance();
		int var2 = 0;
		theChatOptions = var1.translateKey("options.chat.title");
		field_82268_n = var1.translateKey("options.multiplayer.title");
		EnumOptions[] var3 = allScreenChatOptions;
		int var4 = var3.length;
		int var5;
		EnumOptions var6;
		for(var5 = 0; var5 < var4; ++var5)
		{
			var6 = var3[var5];
			if(var6.getEnumFloat())
			{
				buttonList.add(new GuiSlider(var6.returnEnumOrdinal(), width / 2 - 155 + var2 % 2 * 160, height / 6 + 24 * (var2 >> 1), var6, theSettings.getKeyBinding(var6), theSettings.getOptionFloatValue(var6)));
			} else
			{
				buttonList.add(new GuiSmallButton(var6.returnEnumOrdinal(), width / 2 - 155 + var2 % 2 * 160, height / 6 + 24 * (var2 >> 1), var6, theSettings.getKeyBinding(var6)));
			}
			++var2;
		}
		if(var2 % 2 == 1)
		{
			++var2;
		}
		field_82269_o = height / 6 + 24 * (var2 >> 1);
		var2 += 2;
		var3 = allMultiplayerOptions;
		var4 = var3.length;
		for(var5 = 0; var5 < var4; ++var5)
		{
			var6 = var3[var5];
			if(var6.getEnumFloat())
			{
				buttonList.add(new GuiSlider(var6.returnEnumOrdinal(), width / 2 - 155 + var2 % 2 * 160, height / 6 + 24 * (var2 >> 1), var6, theSettings.getKeyBinding(var6), theSettings.getOptionFloatValue(var6)));
			} else
			{
				buttonList.add(new GuiSmallButton(var6.returnEnumOrdinal(), width / 2 - 155 + var2 % 2 * 160, height / 6 + 24 * (var2 >> 1), var6, theSettings.getKeyBinding(var6)));
			}
			++var2;
		}
		buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, var1.translateKey("gui.done")));
	}
}
