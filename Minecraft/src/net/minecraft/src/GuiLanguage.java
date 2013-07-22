package net.minecraft.src;

public class GuiLanguage extends GuiScreen
{
	protected GuiScreen parentGui;
	private GuiSlotLanguage languageList;
	private final GameSettings theGameSettings;
	private final LanguageManager field_135014_d;
	private GuiSmallButton doneButton;
	
	public GuiLanguage(GuiScreen par1GuiScreen, GameSettings par2GameSettings, LanguageManager par3LanguageManager)
	{
		parentGui = par1GuiScreen;
		theGameSettings = par2GameSettings;
		field_135014_d = par3LanguageManager;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			switch(par1GuiButton.id)
			{
				case 5:
					break;
				case 6:
					mc.displayGuiScreen(parentGui);
					break;
				default:
					languageList.actionPerformed(par1GuiButton);
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		languageList.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, I18n.func_135053_a("options.language"), width / 2, 16, 16777215);
		drawCenteredString(fontRenderer, "(" + I18n.func_135053_a("options.languageWarning") + ")", width / 2, height - 56, 8421504);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		buttonList.add(doneButton = new GuiSmallButton(6, width / 2 - 75, height - 38, I18n.func_135053_a("gui.done")));
		languageList = new GuiSlotLanguage(this);
		languageList.registerScrollButtons(7, 8);
	}
	
	static LanguageManager func_135011_a(GuiLanguage par0GuiLanguage)
	{
		return par0GuiLanguage.field_135014_d;
	}
	
	static GuiSmallButton getDoneButton(GuiLanguage par0GuiLanguage)
	{
		return par0GuiLanguage.doneButton;
	}
	
	static GameSettings getGameSettings(GuiLanguage par0GuiLanguage)
	{
		return par0GuiLanguage.theGameSettings;
	}
}
