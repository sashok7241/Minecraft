package net.minecraft.src;

public class GuiScreenConfirmation extends GuiYesNo
{
	private String field_96288_n;
	
	public GuiScreenConfirmation(GuiScreen par1GuiScreen, String par2Str, String par3Str, String par4Str, int par5)
	{
		super(par1GuiScreen, par2Str, par3Str, par5);
		field_96288_n = par4Str;
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, field_96288_n, width / 2, 110, 16777215);
	}
	
	@Override public void initGui()
	{
		buttonList.add(new GuiSmallButton(0, width / 2 - 155, height / 6 + 112, buttonText1));
		buttonList.add(new GuiSmallButton(1, width / 2 - 155 + 160, height / 6 + 112, buttonText2));
	}
}
