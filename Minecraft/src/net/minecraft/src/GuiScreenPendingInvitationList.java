package net.minecraft.src;

class GuiScreenPendingInvitationList extends GuiScreenSelectLocation
{
	final GuiScreenPendingInvitation field_130120_a;
	
	public GuiScreenPendingInvitationList(GuiScreenPendingInvitation par1GuiScreenPendingInvitation)
	{
		super(GuiScreenPendingInvitation.func_130054_j(par1GuiScreenPendingInvitation), par1GuiScreenPendingInvitation.width, par1GuiScreenPendingInvitation.height, 32, par1GuiScreenPendingInvitation.height - 64, 36);
		field_130120_a = par1GuiScreenPendingInvitation;
	}
	
	@Override protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		if(par1 < GuiScreenPendingInvitation.func_130042_e(field_130120_a).size())
		{
			func_130119_b(par1, par2, par3, par4, par5Tessellator);
		}
	}
	
	@Override protected void elementClicked(int par1, boolean par2)
	{
		if(par1 < GuiScreenPendingInvitation.func_130042_e(field_130120_a).size())
		{
			GuiScreenPendingInvitation.func_130053_a(field_130120_a, par1);
		}
	}
	
	@Override protected boolean func_104086_b(int par1)
	{
		return false;
	}
	
	@Override protected int func_130003_b()
	{
		return getSize() * 36;
	}
	
	@Override protected void func_130004_c()
	{
		field_130120_a.drawDefaultBackground();
	}
	
	private void func_130119_b(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		PendingInvite var6 = (PendingInvite) GuiScreenPendingInvitation.func_130042_e(field_130120_a).get(par1);
		field_130120_a.drawString(GuiScreenPendingInvitation.func_130045_k(field_130120_a), var6.field_130092_b, par2 + 2, par3 + 1, 16777215);
		field_130120_a.drawString(GuiScreenPendingInvitation.func_130052_l(field_130120_a), var6.field_130093_c, par2 + 2, par3 + 12, 7105644);
	}
	
	@Override protected int getSize()
	{
		return GuiScreenPendingInvitation.func_130042_e(field_130120_a).size() + 1;
	}
	
	@Override protected boolean isSelected(int par1)
	{
		return par1 == GuiScreenPendingInvitation.func_130049_d(field_130120_a);
	}
}
