package net.minecraft.src;

public class GuiShareToLan extends GuiScreen
{
	private final GuiScreen parentScreen;
	private GuiButton buttonAllowCommandsToggle;
	private GuiButton buttonGameMode;
	private String gameMode = "survival";
	private boolean allowCommands;
	
	public GuiShareToLan(GuiScreen par1GuiScreen)
	{
		parentScreen = par1GuiScreen;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.id == 102)
		{
			mc.displayGuiScreen(parentScreen);
		} else if(par1GuiButton.id == 104)
		{
			if(gameMode.equals("survival"))
			{
				gameMode = "creative";
			} else if(gameMode.equals("creative"))
			{
				gameMode = "adventure";
			} else
			{
				gameMode = "survival";
			}
			func_74088_g();
		} else if(par1GuiButton.id == 103)
		{
			allowCommands = !allowCommands;
			func_74088_g();
		} else if(par1GuiButton.id == 101)
		{
			mc.displayGuiScreen((GuiScreen) null);
			String var2 = mc.getIntegratedServer().shareToLAN(EnumGameType.getByName(gameMode), allowCommands);
			ChatMessageComponent var3;
			if(var2 != null)
			{
				var3 = ChatMessageComponent.func_111082_b("commands.publish.started", new Object[] { var2 });
			} else
			{
				var3 = ChatMessageComponent.func_111066_d("commands.publish.failed");
			}
			mc.ingameGUI.getChatGUI().printChatMessage(var3.func_111068_a(true));
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, I18n.func_135053_a("lanServer.title"), width / 2, 50, 16777215);
		drawCenteredString(fontRenderer, I18n.func_135053_a("lanServer.otherPlayers"), width / 2, 82, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	private void func_74088_g()
	{
		buttonGameMode.displayString = I18n.func_135053_a("selectWorld.gameMode") + " " + I18n.func_135053_a("selectWorld.gameMode." + gameMode);
		buttonAllowCommandsToggle.displayString = I18n.func_135053_a("selectWorld.allowCommands") + " ";
		if(allowCommands)
		{
			buttonAllowCommandsToggle.displayString = buttonAllowCommandsToggle.displayString + I18n.func_135053_a("options.on");
		} else
		{
			buttonAllowCommandsToggle.displayString = buttonAllowCommandsToggle.displayString + I18n.func_135053_a("options.off");
		}
	}
	
	@Override public void initGui()
	{
		buttonList.clear();
		buttonList.add(new GuiButton(101, width / 2 - 155, height - 28, 150, 20, I18n.func_135053_a("lanServer.start")));
		buttonList.add(new GuiButton(102, width / 2 + 5, height - 28, 150, 20, I18n.func_135053_a("gui.cancel")));
		buttonList.add(buttonGameMode = new GuiButton(104, width / 2 - 155, 100, 150, 20, I18n.func_135053_a("selectWorld.gameMode")));
		buttonList.add(buttonAllowCommandsToggle = new GuiButton(103, width / 2 + 5, 100, 150, 20, I18n.func_135053_a("selectWorld.allowCommands")));
		func_74088_g();
	}
}
