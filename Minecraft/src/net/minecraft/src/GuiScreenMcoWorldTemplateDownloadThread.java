package net.minecraft.src;

class GuiScreenMcoWorldTemplateDownloadThread extends Thread
{
	final GuiScreenMcoWorldTemplate field_111256_a;
	
	GuiScreenMcoWorldTemplateDownloadThread(GuiScreenMcoWorldTemplate par1GuiScreenMcoWorldTemplate)
	{
		field_111256_a = par1GuiScreenMcoWorldTemplate;
	}
	
	@Override public void run()
	{
		McoClient var1 = new McoClient(GuiScreenMcoWorldTemplate.func_110382_a(field_111256_a).func_110432_I());
		try
		{
			GuiScreenMcoWorldTemplate.func_110388_a(field_111256_a, var1.func_111231_d().field_110736_a);
		} catch(ExceptionMcoService var3)
		{
			GuiScreenMcoWorldTemplate.func_110392_b(field_111256_a).getLogAgent().logSevere(var3.toString());
		}
	}
}
