package net.minecraft.src;

public class GuiConfirmOpenLink extends GuiYesNo
{
	private String openLinkWarning;
	private String copyLinkButtonText;
	private String field_92028_p;
	private boolean field_92027_q = true;
	
	public GuiConfirmOpenLink(GuiScreen par1GuiScreen, String par2Str, int par3, boolean par4)
	{
		super(par1GuiScreen, StringTranslate.getInstance().translateKey(par4 ? "chat.link.confirmTrusted" : "chat.link.confirm"), par2Str, par3);
		StringTranslate var5 = StringTranslate.getInstance();
		buttonText1 = var5.translateKey(par4 ? "chat.link.open" : "gui.yes");
		buttonText2 = var5.translateKey(par4 ? "gui.cancel" : "gui.no");
		copyLinkButtonText = var5.translateKey("chat.copy");
		openLinkWarning = var5.translateKey("chat.link.warning");
		field_92028_p = par2Str;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.id == 2)
		{
			copyLinkToClipboard();
		}
		parentScreen.confirmClicked(par1GuiButton.id == 0, worldNumber);
	}
	
	public void copyLinkToClipboard()
	{
		setClipboardString(field_92028_p);
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		if(field_92027_q)
		{
			drawCenteredString(fontRenderer, openLinkWarning, width / 2, 110, 16764108);
		}
	}
	
	public void func_92026_h()
	{
		field_92027_q = false;
	}
	
	@Override public void initGui()
	{
		buttonList.add(new GuiButton(0, width / 3 - 83 + 0, height / 6 + 96, 100, 20, buttonText1));
		buttonList.add(new GuiButton(2, width / 3 - 83 + 105, height / 6 + 96, 100, 20, copyLinkButtonText));
		buttonList.add(new GuiButton(1, width / 3 - 83 + 210, height / 6 + 96, 100, 20, buttonText2));
	}
}
