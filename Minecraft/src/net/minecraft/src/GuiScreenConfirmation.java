package net.minecraft.src;

public class GuiScreenConfirmation extends GuiScreen
{
	private final GuiScreenConfirmationType field_140045_e;
	private final String field_140049_p;
	private final String field_96288_n;
	protected final GuiScreen field_140048_a;
	protected final String field_140046_b;
	protected final String field_140047_c;
	protected final int field_140044_d;
	
	public GuiScreenConfirmation(GuiScreen par1GuiScreen, GuiScreenConfirmationType par2GuiScreenConfirmationType, String par3Str, String par4Str, int par5)
	{
		field_140048_a = par1GuiScreen;
		field_140044_d = par5;
		field_140045_e = par2GuiScreenConfirmationType;
		field_140049_p = par3Str;
		field_96288_n = par4Str;
		field_140046_b = I18n.func_135053_a("gui.yes");
		field_140047_c = I18n.func_135053_a("gui.no");
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		field_140048_a.confirmClicked(par1GuiButton.id == 0, field_140044_d);
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, field_140045_e.field_140072_d, width / 2, 70, field_140045_e.field_140075_c);
		drawCenteredString(fontRenderer, field_140049_p, width / 2, 90, 16777215);
		drawCenteredString(fontRenderer, field_96288_n, width / 2, 110, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		buttonList.add(new GuiSmallButton(0, width / 2 - 155, height / 6 + 112, field_140046_b));
		buttonList.add(new GuiSmallButton(1, width / 2 - 155 + 160, height / 6 + 112, field_140047_c));
	}
}
