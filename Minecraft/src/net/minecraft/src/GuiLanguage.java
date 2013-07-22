package net.minecraft.src;

public class GuiLanguage extends GuiScreen
{
	protected GuiScreen parentGui;
	private int updateTimer = -1;
	private GuiSlotLanguage languageList;
	private final GameSettings theGameSettings;
	private GuiSmallButton doneButton;
	
	public GuiLanguage(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
	{
		parentGui = par1GuiScreen;
		theGameSettings = par2GameSettings;
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
		if(updateTimer <= 0)
		{
			mc.texturePackList.updateAvaliableTexturePacks();
			updateTimer += 20;
		}
		StringTranslate var4 = StringTranslate.getInstance();
		drawCenteredString(fontRenderer, var4.translateKey("options.language"), width / 2, 16, 16777215);
		drawCenteredString(fontRenderer, "(" + var4.translateKey("options.languageWarning") + ")", width / 2, height - 56, 8421504);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		buttonList.add(doneButton = new GuiSmallButton(6, width / 2 - 75, height - 38, var1.translateKey("gui.done")));
		languageList = new GuiSlotLanguage(this);
		languageList.registerScrollButtons(buttonList, 7, 8);
	}
	
	@Override public void updateScreen()
	{
		super.updateScreen();
		--updateTimer;
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
