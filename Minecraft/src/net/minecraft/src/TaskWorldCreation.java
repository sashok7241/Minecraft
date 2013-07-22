package net.minecraft.src;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

class TaskWorldCreation extends TaskLongRunning
{
	private final String field_96589_c;
	private final String field_96587_d;
	private final String field_96588_e;
	private final String field_104065_f;
	final GuiScreenCreateOnlineWorld field_96590_a;
	
	public TaskWorldCreation(GuiScreenCreateOnlineWorld p_i23003_1_, String p_i23003_2_, String p_i23003_3_, String p_i23003_4_, String p_i23003_5_)
	{
		field_96590_a = p_i23003_1_;
		field_96589_c = p_i23003_2_;
		field_96587_d = p_i23003_3_;
		field_96588_e = p_i23003_4_;
		field_104065_f = p_i23003_5_;
	}
	
	@Override public void run()
	{
		String var1 = StringTranslate.getInstance().translateKey("mco.create.world.wait");
		func_96576_b(var1);
		McoClient var2 = new McoClient(GuiScreenCreateOnlineWorld.func_96248_a(field_96590_a).session);
		try
		{
			var2.func_96386_a(field_96589_c, field_96587_d, field_96588_e, field_104065_f);
			GuiScreenCreateOnlineWorld.func_96246_c(field_96590_a).displayGuiScreen(GuiScreenCreateOnlineWorld.func_96247_b(field_96590_a));
		} catch(ExceptionMcoService var4)
		{
			func_96575_a(var4.field_96391_b);
		} catch(UnsupportedEncodingException var5)
		{
			;
		} catch(IOException var6)
		{
			;
		} catch(Exception var7)
		{
			func_96575_a("Failed");
		}
	}
}
