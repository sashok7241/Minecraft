package net.minecraft.src;


public class GuiScreenServerList extends GuiScreen
{
	private final GuiScreen guiScreen;
	private final ServerData theServerData;
	private GuiTextField serverTextField;
	
	public GuiScreenServerList(GuiScreen par1GuiScreen, ServerData par2ServerData)
	{
		guiScreen = par1GuiScreen;
		theServerData = par2ServerData;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 1)
			{
				guiScreen.confirmClicked(false, 0);
			} else if(par1GuiButton.id == 0)
			{
				theServerData.serverIP = serverTextField.getText();
				guiScreen.confirmClicked(true, 0);
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		StringTranslate var4 = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer, var4.translateKey("selectServer.direct"), width / 2, height / 4 - 60 + 20, 16777215);
		drawString(fontRenderer, var4.translateKey("addServer.enterIp"), width / 2 - 100, 100, 10526880);
		serverTextField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, var1.translateKey("selectServer.select")));
		buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
		serverTextField = new GuiTextField(fontRenderer, width / 2 - 100, 116, 200, 20);
		serverTextField.setMaxStringLength(128);
		serverTextField.setFocused(true);
		serverTextField.setText(mc.gameSettings.lastServer);
		((GuiButton) buttonList.get(0)).enabled = serverTextField.getText().length() > 0 && serverTextField.getText().split(":").length > 0;
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		if(serverTextField.textboxKeyTyped(par1, par2))
		{
			((GuiButton) buttonList.get(0)).enabled = serverTextField.getText().length() > 0 && serverTextField.getText().split(":").length > 0;
		} else if(par2 == 28)
		{
			actionPerformed((GuiButton) buttonList.get(0));
		}
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		serverTextField.mouseClicked(par1, par2, par3);
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
		mc.gameSettings.lastServer = serverTextField.getText();
		mc.gameSettings.saveOptions();
	}
	
	@Override public void updateScreen()
	{
		serverTextField.updateCursorCounter();
	}
}
