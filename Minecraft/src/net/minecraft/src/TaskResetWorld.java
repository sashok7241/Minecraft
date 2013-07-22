package net.minecraft.src;

class TaskResetWorld extends TaskLongRunning
{
	private final long field_96591_c;
	private final String field_104066_d;
	private final WorldTemplate field_111252_e;
	final GuiScreenResetWorld field_96592_a;
	
	public TaskResetWorld(GuiScreenResetWorld par1GuiScreenResetWorld, long par2, String par4Str, WorldTemplate par5WorldTemplate)
	{
		field_96592_a = par1GuiScreenResetWorld;
		field_96591_c = par2;
		field_104066_d = par4Str;
		field_111252_e = par5WorldTemplate;
	}
	
	@Override public void run()
	{
		McoClient var1 = new McoClient(func_96578_b().func_110432_I());
		String var2 = I18n.func_135053_a("mco.reset.world.resetting.screen.title");
		func_96576_b(var2);
		try
		{
			if(field_111252_e != null)
			{
				var1.func_111233_e(field_96591_c, field_111252_e.field_110734_a);
			} else
			{
				var1.func_96376_d(field_96591_c, field_104066_d);
			}
			GuiScreenResetWorld.func_96147_b(field_96592_a).displayGuiScreen(GuiScreenResetWorld.func_96148_a(field_96592_a));
		} catch(ExceptionMcoService var4)
		{
			GuiScreenResetWorld.func_130025_c(field_96592_a).getLogAgent().logSevere(var4.toString());
			func_96575_a(var4.toString());
		} catch(Exception var5)
		{
			GuiScreenResetWorld.func_130024_d(field_96592_a).getLogAgent().logWarning("Realms: ");
			func_96575_a(var5.toString());
		}
	}
}
