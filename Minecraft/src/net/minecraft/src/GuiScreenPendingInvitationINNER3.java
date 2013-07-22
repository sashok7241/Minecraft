package net.minecraft.src;

class GuiScreenPendingInvitationINNER3 extends Thread
{
	final GuiScreenPendingInvitation field_130131_a;
	
	GuiScreenPendingInvitationINNER3(GuiScreenPendingInvitation par1GuiScreenPendingInvitation)
	{
		field_130131_a = par1GuiScreenPendingInvitation;
	}
	
	@Override public void run()
	{
		try
		{
			McoClient var1 = new McoClient(GuiScreenPendingInvitation.func_130046_h(field_130131_a).func_110432_I());
			var1.func_130107_a(((PendingInvite) GuiScreenPendingInvitation.func_130042_e(field_130131_a).get(GuiScreenPendingInvitation.func_130049_d(field_130131_a))).field_130094_a);
			GuiScreenPendingInvitation.func_130040_f(field_130131_a);
		} catch(ExceptionMcoService var2)
		{
			GuiScreenPendingInvitation.func_130055_i(field_130131_a).getLogAgent().logSevere(var2.toString());
		}
	}
}
