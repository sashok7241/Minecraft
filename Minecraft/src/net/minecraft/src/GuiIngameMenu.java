package net.minecraft.src;

public class GuiIngameMenu extends GuiScreen
{
	private int updateCounter2 = 0;
	private int updateCounter = 0;
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		switch(par1GuiButton.id)
		{
			case 0:
				mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
				break;
			case 1:
				par1GuiButton.enabled = false;
				mc.statFileWriter.readStat(StatList.leaveGameStat, 1);
				mc.theWorld.sendQuittingDisconnectingPacket();
				mc.loadWorld((WorldClient) null);
				mc.displayGuiScreen(new GuiMainMenu());
			case 2:
			case 3:
			default:
				break;
			case 4:
				mc.displayGuiScreen((GuiScreen) null);
				mc.setIngameFocus();
				mc.sndManager.resumeAllSounds();
				break;
			case 5:
				mc.displayGuiScreen(new GuiAchievements(mc.statFileWriter));
				break;
			case 6:
				mc.displayGuiScreen(new GuiStats(this, mc.statFileWriter));
				break;
			case 7:
				mc.displayGuiScreen(new GuiShareToLan(this));
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, "Game menu", width / 2, 40, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		updateCounter2 = 0;
		buttonList.clear();
		byte var1 = -16;
		buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + var1, StatCollector.translateToLocal("menu.returnToMenu")));
		if(!mc.isIntegratedServerRunning())
		{
			((GuiButton) buttonList.get(0)).displayString = StatCollector.translateToLocal("menu.disconnect");
		}
		buttonList.add(new GuiButton(4, width / 2 - 100, height / 4 + 24 + var1, StatCollector.translateToLocal("menu.returnToGame")));
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + var1, 98, 20, StatCollector.translateToLocal("menu.options")));
		GuiButton var3;
		buttonList.add(var3 = new GuiButton(7, width / 2 + 2, height / 4 + 96 + var1, 98, 20, StatCollector.translateToLocal("menu.shareToLan")));
		buttonList.add(new GuiButton(5, width / 2 - 100, height / 4 + 48 + var1, 98, 20, StatCollector.translateToLocal("gui.achievements")));
		buttonList.add(new GuiButton(6, width / 2 + 2, height / 4 + 48 + var1, 98, 20, StatCollector.translateToLocal("gui.stats")));
		var3.enabled = mc.isSingleplayer() && !mc.getIntegratedServer().getPublic();
	}
	
	@Override public void updateScreen()
	{
		super.updateScreen();
		++updateCounter;
	}
}
