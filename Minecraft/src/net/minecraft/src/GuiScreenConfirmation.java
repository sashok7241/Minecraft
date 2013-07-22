package net.minecraft.src;

public class GuiScreenConfirmation extends GuiYesNo
{
	private String field_96288_n;
	
	public GuiScreenConfirmation(GuiScreen p_i10019_1_, String p_i10019_2_, String p_i10019_3_, String p_i10019_4_, int p_i10019_5_)
	{
		super(p_i10019_1_, p_i10019_2_, p_i10019_3_, p_i10019_5_);
		field_96288_n = p_i10019_4_;
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
