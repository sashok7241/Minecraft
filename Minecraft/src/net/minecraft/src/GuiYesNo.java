package net.minecraft.src;

public class GuiYesNo extends GuiScreen
{
	protected GuiScreen parentScreen;
	protected String message1;
	private String message2;
	protected String buttonText1;
	protected String buttonText2;
	protected int worldNumber;
	
	public GuiYesNo(GuiScreen par1GuiScreen, String par2Str, String par3Str, int par4)
	{
		parentScreen = par1GuiScreen;
		message1 = par2Str;
		message2 = par3Str;
		worldNumber = par4;
		StringTranslate var5 = StringTranslate.getInstance();
		buttonText1 = var5.translateKey("gui.yes");
		buttonText2 = var5.translateKey("gui.no");
	}
	
	public GuiYesNo(GuiScreen par1GuiScreen, String par2Str, String par3Str, String par4Str, String par5Str, int par6)
	{
		parentScreen = par1GuiScreen;
		message1 = par2Str;
		message2 = par3Str;
		buttonText1 = par4Str;
		buttonText2 = par5Str;
		worldNumber = par6;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		parentScreen.confirmClicked(par1GuiButton.id == 0, worldNumber);
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, message1, width / 2, 70, 16777215);
		drawCenteredString(fontRenderer, message2, width / 2, 90, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		buttonList.add(new GuiSmallButton(0, width / 2 - 155, height / 6 + 96, buttonText1));
		buttonList.add(new GuiSmallButton(1, width / 2 - 155 + 160, height / 6 + 96, buttonText2));
	}
}
