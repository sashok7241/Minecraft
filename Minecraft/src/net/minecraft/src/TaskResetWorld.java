package net.minecraft.src;

import java.io.IOException;

class TaskResetWorld extends TaskLongRunning
{
	private final long field_96591_c;
	private final String field_104066_d;
	final GuiScreenResetWorld field_96592_a;
	
	public TaskResetWorld(GuiScreenResetWorld par1GuiScreenResetWorld, long par2, String par4Str)
	{
		field_96592_a = par1GuiScreenResetWorld;
		field_96591_c = par2;
		field_104066_d = par4Str;
	}
	
	@Override public void run()
	{
		McoClient var1 = new McoClient(func_96578_b().session);
		String var2 = StringTranslate.getInstance().translateKey("mco.reset.world.resetting.screen.title");
		func_96576_b(var2);
		try
		{
			var1.func_96376_d(field_96591_c, field_104066_d);
			GuiScreenResetWorld.func_96147_b(field_96592_a).displayGuiScreen(GuiScreenResetWorld.func_96148_a(field_96592_a));
		} catch(ExceptionMcoService var4)
		{
			func_96575_a(var4.field_96391_b);
		} catch(IOException var5)
		{
			;
		}
	}
}
