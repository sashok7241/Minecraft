package net.minecraft.src;


public class GuiScreenAddServer extends GuiScreen
{
	private GuiScreen parentGui;
	private GuiTextField serverAddress;
	private GuiTextField serverName;
	private ServerData newServerData;
	
	public GuiScreenAddServer(GuiScreen p_i3049_1_, ServerData p_i3049_2_)
	{
		parentGui = p_i3049_1_;
		newServerData = p_i3049_2_;
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
				StringTranslate var2 = StringTranslate.getInstance();
				newServerData.setHideAddress(!newServerData.isHidingAddress());
				((GuiButton) buttonList.get(2)).displayString = var2.translateKey("addServer.hideAddress") + ": " + (newServerData.isHidingAddress() ? var2.translateKey("gui.yes") : var2.translateKey("gui.no"));
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		StringTranslate var4 = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer, var4.translateKey("addServer.title"), width / 2, 17, 16777215);
		drawString(fontRenderer, var4.translateKey("addServer.enterName"), width / 2 - 100, 53, 10526880);
		drawString(fontRenderer, var4.translateKey("addServer.enterIp"), width / 2 - 100, 94, 10526880);
		serverName.drawTextBox();
		serverAddress.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, var1.translateKey("addServer.add")));
		buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
		buttonList.add(new GuiButton(2, width / 2 - 100, 142, var1.translateKey("addServer.hideAddress") + ": " + (newServerData.isHidingAddress() ? var1.translateKey("gui.yes") : var1.translateKey("gui.no"))));
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
		if(par1 == 9)
		{
			if(serverName.isFocused())
			{
				serverName.setFocused(false);
				serverAddress.setFocused(true);
			} else
			{
				serverName.setFocused(true);
				serverAddress.setFocused(false);
			}
		}
		if(par1 == 13)
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
