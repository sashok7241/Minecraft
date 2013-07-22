package net.minecraft.src;

class GuiScreenMcoWorldTemplateSelectionList extends GuiScreenSelectLocation
{
	final GuiScreenMcoWorldTemplate field_111245_a;
	
	public GuiScreenMcoWorldTemplateSelectionList(GuiScreenMcoWorldTemplate par1GuiScreenMcoWorldTemplate)
	{
		super(GuiScreenMcoWorldTemplate.func_130066_c(par1GuiScreenMcoWorldTemplate), par1GuiScreenMcoWorldTemplate.width, par1GuiScreenMcoWorldTemplate.height, 32, par1GuiScreenMcoWorldTemplate.height - 64, 36);
		field_111245_a = par1GuiScreenMcoWorldTemplate;
	}
	
	@Override protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		if(par1 < GuiScreenMcoWorldTemplate.func_110395_c(field_111245_a).size())
		{
			func_111244_b(par1, par2, par3, par4, par5Tessellator);
		}
	}
	
	@Override protected void elementClicked(int par1, boolean par2)
	{
		if(par1 < GuiScreenMcoWorldTemplate.func_110395_c(field_111245_a).size())
		{
			GuiScreenMcoWorldTemplate.func_130064_a(field_111245_a, par1);
			GuiScreenMcoWorldTemplate.func_130065_a(field_111245_a, (WorldTemplate) null);
		}
	}
	
	@Override protected boolean func_104086_b(int par1)
	{
		return false;
	}
	
	private void func_111244_b(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		WorldTemplate var6 = (WorldTemplate) GuiScreenMcoWorldTemplate.func_110395_c(field_111245_a).get(par1);
		field_111245_a.drawString(GuiScreenMcoWorldTemplate.func_110389_g(field_111245_a), var6.field_110732_b, par2 + 2, par3 + 1, 16777215);
		field_111245_a.drawString(GuiScreenMcoWorldTemplate.func_110387_h(field_111245_a), var6.field_110731_d, par2 + 2, par3 + 12, 7105644);
		field_111245_a.drawString(GuiScreenMcoWorldTemplate.func_110384_i(field_111245_a), var6.field_110733_c, par2 + 2 + 207 - GuiScreenMcoWorldTemplate.func_130063_j(field_111245_a).getStringWidth(var6.field_110733_c), par3 + 1, 5000268);
	}
	
	@Override protected int func_130003_b()
	{
		return getSize() * 36;
	}
	
	@Override protected void func_130004_c()
	{
		field_111245_a.drawDefaultBackground();
	}
	
	@Override protected int getSize()
	{
		return GuiScreenMcoWorldTemplate.func_110395_c(field_111245_a).size() + 1;
	}
	
	@Override protected boolean isSelected(int par1)
	{
		return GuiScreenMcoWorldTemplate.func_110395_c(field_111245_a).size() == 0 ? false : par1 >= GuiScreenMcoWorldTemplate.func_110395_c(field_111245_a).size() ? false : GuiScreenMcoWorldTemplate.func_130067_e(field_111245_a) != null ? GuiScreenMcoWorldTemplate.func_130067_e(field_111245_a).field_110732_b.equals(((WorldTemplate) GuiScreenMcoWorldTemplate.func_110395_c(field_111245_a).get(par1)).field_110732_b) : par1 == GuiScreenMcoWorldTemplate.func_130062_f(field_111245_a);
	}
}
