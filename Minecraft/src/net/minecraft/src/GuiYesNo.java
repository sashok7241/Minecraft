package net.minecraft.src;

public class GuiYesNo extends GuiScreen
{
	protected GuiScreen parentScreen;
	protected String message1;
	private String message2;
	protected String buttonText1;
	protected String buttonText2;
	protected int worldNumber;
	
	public GuiYesNo(GuiScreen p_i3047_1_, String p_i3047_2_, String p_i3047_3_, int p_i3047_4_)
	{
		parentScreen = p_i3047_1_;
		message1 = p_i3047_2_;
		message2 = p_i3047_3_;
		worldNumber = p_i3047_4_;
		StringTranslate var5 = StringTranslate.getInstance();
		buttonText1 = var5.translateKey("gui.yes");
		buttonText2 = var5.translateKey("gui.no");
	}
	
	public GuiYesNo(GuiScreen p_i3048_1_, String p_i3048_2_, String p_i3048_3_, String p_i3048_4_, String p_i3048_5_, int p_i3048_6_)
	{
		parentScreen = p_i3048_1_;
		message1 = p_i3048_2_;
		message2 = p_i3048_3_;
		buttonText1 = p_i3048_4_;
		buttonText2 = p_i3048_5_;
		worldNumber = p_i3048_6_;
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
