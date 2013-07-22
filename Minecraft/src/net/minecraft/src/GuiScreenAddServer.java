package net.minecraft.src;


public class GuiScreenAddServer extends GuiScreen
{
	private GuiScreen parentGui;
	private GuiTextField serverAddress;
	private GuiTextField serverName;
	private ServerData newServerData;
	
	public GuiScreenAddServer(GuiScreen par1GuiScreen, ServerData par2ServerData)
	{
		parentGui = par1GuiScreen;
		newServerData = par2ServerData;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 1)
			{
				parentGui.confirmClicked(false, 0);
			} else if(par1GuiButton.id == 0)
			{
				newServerData.serverName = serverName.getText();
				newServerData.serverIP = serverAddress.getText();
				parentGui.confirmClicked(true, 0);
			} else if(par1GuiButton.id == 2)
			{
				newServerData.setHideAddress(!newServerData.isHidingAddress());
				((GuiButton) buttonList.get(2)).displayString = I18n.func_135053_a("addServer.hideAddress") + ": " + (newServerData.isHidingAddress() ? I18n.func_135053_a("gui.yes") : I18n.func_135053_a("gui.no"));
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, I18n.func_135053_a("addServer.title"), width / 2, 17, 16777215);
		drawString(fontRenderer, I18n.func_135053_a("addServer.enterName"), width / 2 - 100, 53, 10526880);
		drawString(fontRenderer, I18n.func_135053_a("addServer.enterIp"), width / 2 - 100, 94, 10526880);
		serverName.drawTextBox();
		serverAddress.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, I18n.func_135053_a("addServer.add")));
		buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, I18n.func_135053_a("gui.cancel")));
		buttonList.add(new GuiButton(2, width / 2 - 100, 142, I18n.func_135053_a("addServer.hideAddress") + ": " + (newServerData.isHidingAddress() ? I18n.func_135053_a("gui.yes") : I18n.func_135053_a("gui.no"))));
		serverName = new GuiTextField(fontRenderer, width / 2 - 100, 66, 200, 20);
		serverName.setFocused(true);
		serverName.setText(newServerData.serverName);
		serverAddress = new GuiTextField(fontRenderer, width / 2 - 100, 106, 200, 20);
		serverAddress.setMaxStringLength(128);
		serverAddress.setText(newServerData.serverIP);
		((GuiButton) buttonList.get(0)).enabled = serverAddress.getText().length() > 0 && serverAddress.getText().split(":").length > 0 && serverName.getText().length() > 0;
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		serverName.textboxKeyTyped(par1, par2);
		serverAddress.textboxKeyTyped(par1, par2);
		if(par2 == 15)
		{
			serverName.setFocused(!serverName.isFocused());
			serverAddress.setFocused(!serverAddress.isFocused());
		}
		if(par2 == 28 || par2 == 156)
		{
			actionPerformed((GuiButton) buttonList.get(0));
		}
		((GuiButton) buttonList.get(0)).enabled = serverAddress.getText().length() > 0 && serverAddress.getText().split(":").length > 0 && serverName.getText().length() > 0;
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		serverAddress.mouseClicked(par1, par2, par3);
		serverName.mouseClicked(par1, par2, par3);
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override public void updateScreen()
	{
		serverName.updateCursorCounter();
		serverAddress.updateCursorCounter();
	}
}
