package net.minecraft.src;

public class GuiControls extends GuiScreen
{
	private GuiScreen parentScreen;
	protected String screenTitle = "Controls";
	private GameSettings options;
	private int buttonId = -1;
	
	public GuiControls(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
	{
		parentScreen = par1GuiScreen;
		options = par2GameSettings;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		for(int var2 = 0; var2 < options.keyBindings.length; ++var2)
		{
			((GuiButton) buttonList.get(var2)).displayString = options.getOptionDisplayString(var2);
		}
		if(par1GuiButton.id == 200)
		{
			mc.displayGuiScreen(parentScreen);
		} else
		{
			buttonId = par1GuiButton.id;
			par1GuiButton.displayString = "> " + options.getOptionDisplayString(par1GuiButton.id) + " <";
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, screenTitle, width / 2, 20, 16777215);
		int var4 = getLeftBorder();
		int var5 = 0;
		while(var5 < options.keyBindings.length)
		{
			boolean var6 = false;
			int var7 = 0;
			while(true)
			{
				if(var7 < options.keyBindings.length)
				{
					if(var7 == var5 || options.keyBindings[var5].keyCode != options.keyBindings[var7].keyCode)
					{
						++var7;
						continue;
					}
					var6 = true;
				}
				if(buttonId == var5)
				{
					((GuiButton) buttonList.get(var5)).displayString = "" + EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + "??? " + EnumChatFormatting.WHITE + "<";
				} else if(var6)
				{
					((GuiButton) buttonList.get(var5)).displayString = EnumChatFormatting.RED + options.getOptionDisplayString(var5);
				} else
				{
					((GuiButton) buttonList.get(var5)).displayString = options.getOptionDisplayString(var5);
				}
				drawString(fontRenderer, options.getKeyBindingDescription(var5), var4 + var5 % 2 * 160 + 70 + 6, height / 6 + 24 * (var5 >> 1) + 7, -1);
				++var5;
				break;
			}
		}
		super.drawScreen(par1, par2, par3);
	}
	
	private int getLeftBorder()
	{
		return width / 2 - 155;
	}
	
	@Override public void initGui()
	{
		int var1 = getLeftBorder();
		for(int var2 = 0; var2 < options.keyBindings.length; ++var2)
		{
			buttonList.add(new GuiSmallButton(var2, var1 + var2 % 2 * 160, height / 6 + 24 * (var2 >> 1), 70, 20, options.getOptionDisplayString(var2)));
		}
		buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, I18n.func_135053_a("gui.done")));
		screenTitle = I18n.func_135053_a("controls.title");
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		if(buttonId >= 0)
		{
			options.setKeyBinding(buttonId, par2);
			((GuiButton) buttonList.get(buttonId)).displayString = options.getOptionDisplayString(buttonId);
			buttonId = -1;
			KeyBinding.resetKeyBindingArrayAndHash();
		} else
		{
			super.keyTyped(par1, par2);
		}
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		if(buttonId >= 0)
		{
			options.setKeyBinding(buttonId, -100 + par3);
			((GuiButton) buttonList.get(buttonId)).displayString = options.getOptionDisplayString(buttonId);
			buttonId = -1;
			KeyBinding.resetKeyBindingArrayAndHash();
		} else
		{
			super.mouseClicked(par1, par2, par3);
		}
	}
}
