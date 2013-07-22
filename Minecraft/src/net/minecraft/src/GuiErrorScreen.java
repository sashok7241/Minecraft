package net.minecraft.src;

public class GuiErrorScreen extends GuiScreen
{
	private String message1;
	private String message2;
	
	public GuiErrorScreen(String par1Str, String par2Str)
	{
		message1 = par1Str;
		message2 = par2Str;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		mc.displayGuiScreen((GuiScreen) null);
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawGradientRect(0, 0, width, height, -12574688, -11530224);
		drawCenteredString(fontRenderer, message1, width / 2, 90, 16777215);
		drawCenteredString(fontRenderer, message2, width / 2, 110, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		super.initGui();
		buttonList.add(new GuiButton(0, width / 2 - 100, 140, I18n.func_135053_a("gui.cancel")));
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
	}
}
