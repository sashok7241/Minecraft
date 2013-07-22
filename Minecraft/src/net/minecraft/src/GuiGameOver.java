package net.minecraft.src;

import java.util.Iterator;

public class GuiGameOver extends GuiScreen
{
	private int cooldownTimer;
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		switch(par1GuiButton.id)
		{
			case 1:
				mc.thePlayer.respawnPlayer();
				mc.displayGuiScreen((GuiScreen) null);
				break;
			case 2:
				mc.theWorld.sendQuittingDisconnectingPacket();
				mc.loadWorld((WorldClient) null);
				mc.displayGuiScreen(new GuiMainMenu());
		}
	}
	
	@Override public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawGradientRect(0, 0, width, height, 1615855616, -1602211792);
		GL11.glPushMatrix();
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		boolean var4 = mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
		String var5 = var4 ? StatCollector.translateToLocal("deathScreen.title.hardcore") : StatCollector.translateToLocal("deathScreen.title");
		drawCenteredString(fontRenderer, var5, width / 2 / 2, 30, 16777215);
		GL11.glPopMatrix();
		if(var4)
		{
			drawCenteredString(fontRenderer, StatCollector.translateToLocal("deathScreen.hardcoreInfo"), width / 2, 144, 16777215);
		}
		drawCenteredString(fontRenderer, StatCollector.translateToLocal("deathScreen.score") + ": " + EnumChatFormatting.YELLOW + mc.thePlayer.getScore(), width / 2, 100, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		buttonList.clear();
		if(mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
		{
			if(mc.isIntegratedServerRunning())
			{
				buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96, StatCollector.translateToLocal("deathScreen.deleteWorld")));
			} else
			{
				buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96, StatCollector.translateToLocal("deathScreen.leaveServer")));
			}
		} else
		{
			buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 72, StatCollector.translateToLocal("deathScreen.respawn")));
			buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 96, StatCollector.translateToLocal("deathScreen.titleScreen")));
			if(mc.session == null)
			{
				((GuiButton) buttonList.get(1)).enabled = false;
			}
		}
		GuiButton var2;
		for(Iterator var1 = buttonList.iterator(); var1.hasNext(); var2.enabled = false)
		{
			var2 = (GuiButton) var1.next();
		}
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
	}
	
	@Override public void updateScreen()
	{
		super.updateScreen();
		++cooldownTimer;
		GuiButton var2;
		if(cooldownTimer == 20)
		{
			for(Iterator var1 = buttonList.iterator(); var1.hasNext(); var2.enabled = true)
			{
				var2 = (GuiButton) var1.next();
			}
		}
	}
}
