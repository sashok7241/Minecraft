package net.minecraft.src;

public class GuiMemoryErrorScreen extends GuiScreen
{
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.id == 0)
		{
			mc.displayGuiScreen(new GuiMainMenu());
		} else if(par1GuiButton.id == 1)
		{
			mc.shutdown();
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, "Out of memory!", width / 2, height / 4 - 60 + 20, 16777215);
		drawString(fontRenderer, "Minecraft has run out of memory.", width / 2 - 140, height / 4 - 60 + 60 + 0, 10526880);
		drawString(fontRenderer, "This could be caused by a bug in the game or by the", width / 2 - 140, height / 4 - 60 + 60 + 18, 10526880);
		drawString(fontRenderer, "Java Virtual Machine not being allocated enough", width / 2 - 140, height / 4 - 60 + 60 + 27, 10526880);
		drawString(fontRenderer, "memory. If you are playing in a web browser, try", width / 2 - 140, height / 4 - 60 + 60 + 36, 10526880);
		drawString(fontRenderer, "downloading the game and playing it offline.", width / 2 - 140, height / 4 - 60 + 60 + 45, 10526880);
		drawString(fontRenderer, "To prevent level corruption, the current game has quit.", width / 2 - 140, height / 4 - 60 + 60 + 63, 10526880);
		drawString(fontRenderer, "We\'ve tried to free up enough memory to let you go back to", width / 2 - 140, height / 4 - 60 + 60 + 81, 10526880);
		drawString(fontRenderer, "the main menu and back to playing, but this may not have worked.", width / 2 - 140, height / 4 - 60 + 60 + 90, 10526880);
		drawString(fontRenderer, "Please restart the game if you see this message again.", width / 2 - 140, height / 4 - 60 + 60 + 99, 10526880);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		buttonList.clear();
		buttonList.add(new GuiSmallButton(0, width / 2 - 155, height / 4 + 120 + 12, var1.translateKey("gui.toMenu")));
		buttonList.add(new GuiSmallButton(1, width / 2 - 155 + 160, height / 4 + 120 + 12, var1.translateKey("menu.quit")));
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
	}
}
