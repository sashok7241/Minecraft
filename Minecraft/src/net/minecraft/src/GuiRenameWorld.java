package net.minecraft.src;


public class GuiRenameWorld extends GuiScreen
{
	private GuiScreen parentGuiScreen;
	private GuiTextField theGuiTextField;
	private final String worldName;
	
	public GuiRenameWorld(GuiScreen p_i3052_1_, String p_i3052_2_)
	{
		parentGuiScreen = p_i3052_1_;
		worldName = p_i3052_2_;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 1)
			{
				mc.displayGuiScreen(parentGuiScreen);
			} else if(par1GuiButton.id == 0)
			{
				ISaveFormat var2 = mc.getSaveLoader();
				var2.renameWorld(worldName, theGuiTextField.getText().trim());
				mc.displayGuiScreen(parentGuiScreen);
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		StringTranslate var4 = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer, var4.translateKey("selectWorld.renameTitle"), width / 2, height / 4 - 60 + 20, 16777215);
		drawString(fontRenderer, var4.translateKey("selectWorld.enterName"), width / 2 - 100, 47, 10526880);
		theGuiTextField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, var1.translateKey("selectWorld.renameButton")));
		buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
		ISaveFormat var2 = mc.getSaveLoader();
		WorldInfo var3 = var2.getWorldInfo(worldName);
		String var4 = var3.getWorldName();
		theGuiTextField = new GuiTextField(fontRenderer, width / 2 - 100, 60, 200, 20);
		theGuiTextField.setFocused(true);
		theGuiTextField.setText(var4);
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		theGuiTextField.textboxKeyTyped(par1, par2);
		((GuiButton) buttonList.get(0)).enabled = theGuiTextField.getText().trim().length() > 0;
		if(par1 == 13)
		{
			actionPerformed((GuiButton) buttonList.get(0));
		}
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		theGuiTextField.mouseClicked(par1, par2, par3);
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override public void updateScreen()
	{
		theGuiTextField.updateCursorCounter();
	}
}
