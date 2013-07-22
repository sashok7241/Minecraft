package net.minecraft.src;

public class GuiSleepMP extends GuiChat
{
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.id == 1)
		{
			wakeEntity();
		} else
		{
			super.actionPerformed(par1GuiButton);
		}
	}
	
	@Override public void initGui()
	{
		super.initGui();
		buttonList.add(new GuiButton(1, width / 2 - 100, height - 40, I18n.func_135053_a("multiplayer.stopSleeping")));
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		if(par2 == 1)
		{
			wakeEntity();
		} else if(par2 != 28 && par2 != 156)
		{
			super.keyTyped(par1, par2);
		} else
		{
			String var3 = inputField.getText().trim();
			if(var3.length() > 0)
			{
				mc.thePlayer.sendChatMessage(var3);
			}
			inputField.setText("");
			mc.ingameGUI.getChatGUI().resetScroll();
		}
	}
	
	private void wakeEntity()
	{
		NetClientHandler var1 = mc.thePlayer.sendQueue;
		var1.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 3));
	}
}
