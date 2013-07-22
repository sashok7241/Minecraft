package net.minecraft.src;

public class GuiScreenClientOutdated extends GuiScreen
{
	private final GuiScreen field_140007_a;
	
	public GuiScreenClientOutdated(GuiScreen par1GuiScreen)
	{
		field_140007_a = par1GuiScreen;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.id == 0)
		{
			mc.displayGuiScreen(field_140007_a);
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		String var4 = I18n.func_135053_a("mco.client.outdated.title");
		String var5 = I18n.func_135053_a("mco.client.outdated.msg");
		drawCenteredString(fontRenderer, var4, width / 2, height / 2 - 50, 16711680);
		drawCenteredString(fontRenderer, var5, width / 2, height / 2 - 30, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, "Back"));
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		if(par2 == 28 || par2 == 156)
		{
			mc.displayGuiScreen(field_140007_a);
		}
	}
}
