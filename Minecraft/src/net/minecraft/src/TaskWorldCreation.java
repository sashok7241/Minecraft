package net.minecraft.src;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

class TaskWorldCreation extends TaskLongRunning
{
	private final String field_96589_c;
	private final String field_96587_d;
	private final String field_104065_f;
	private final WorldTemplate field_111253_f;
	final GuiScreenCreateOnlineWorld field_96590_a;
	
	public TaskWorldCreation(GuiScreenCreateOnlineWorld par1GuiScreenCreateOnlineWorld, String par2Str, String par3Str, String par4Str, WorldTemplate par5WorldTemplate)
	{
		field_96590_a = par1GuiScreenCreateOnlineWorld;
		field_96589_c = par2Str;
		field_96587_d = par3Str;
		field_104065_f = par4Str;
		field_111253_f = par5WorldTemplate;
	}
	
	@Override public void run()
	{
		String var1 = I18n.func_135053_a("mco.create.world.wait");
		func_96576_b(var1);
		McoClient var2 = new McoClient(GuiScreenCreateOnlineWorld.func_96248_a(field_96590_a).func_110432_I());
		try
		{
			if(field_111253_f != null)
			{
				var2.func_96386_a(field_96589_c, field_96587_d, field_104065_f, field_111253_f.field_110734_a);
			} else
			{
				var2.func_96386_a(field_96589_c, field_96587_d, field_104065_f, "-1");
			}
			GuiScreenCreateOnlineWorld.func_96246_c(field_96590_a).displayGuiScreen(GuiScreenCreateOnlineWorld.func_96247_b(field_96590_a));
		} catch(ExceptionMcoService var4)
		{
			GuiScreenCreateOnlineWorld.func_130026_d(field_96590_a).getLogAgent().logSevere(var4.toString());
			func_96575_a(var4.toString());
		} catch(UnsupportedEncodingException var5)
		{
			GuiScreenCreateOnlineWorld.func_130027_e(field_96590_a).getLogAgent().logWarning("Realms: " + var5.getLocalizedMessage());
			func_96575_a(var5.getLocalizedMessage());
		} catch(IOException var6)
		{
			GuiScreenCreateOnlineWorld.func_130028_f(field_96590_a).getLogAgent().logWarning("Realms: could not parse response");
			func_96575_a(var6.getLocalizedMessage());
		} catch(Exception var7)
		{
			func_96575_a(var7.getLocalizedMessage());
		}
	}
}
