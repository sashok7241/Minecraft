package net.minecraft.src;

class ThreadOnlineScreen extends Thread
{
	final GuiScreenOnlineServers field_98173_a;
	
	ThreadOnlineScreen(GuiScreenOnlineServers par1GuiScreenOnlineServers)
	{
		field_98173_a = par1GuiScreenOnlineServers;
	}
	
	@Override public void run()
	{
		try
		{
			McoServer var1 = GuiScreenOnlineServers.func_140011_a(field_98173_a, GuiScreenOnlineServers.func_140041_a(field_98173_a));
			if(var1 != null)
			{
				McoClient var2 = new McoClient(GuiScreenOnlineServers.func_98075_b(field_98173_a).func_110432_I());
				GuiScreenOnlineServers.func_140040_h().func_140058_a(var1);
				GuiScreenOnlineServers.func_140013_c(field_98173_a).remove(var1);
				var2.func_140055_c(var1.field_96408_a);
				GuiScreenOnlineServers.func_140040_h().func_140058_a(var1);
				GuiScreenOnlineServers.func_140013_c(field_98173_a).remove(var1);
				GuiScreenOnlineServers.func_140017_d(field_98173_a);
			}
		} catch(ExceptionMcoService var3)
		{
			GuiScreenOnlineServers.func_98076_f(field_98173_a).getLogAgent().logSevere(var3.toString());
		}
	}
}
