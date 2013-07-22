package net.minecraft.src;

class GuiScreenBackupDownloadThread extends Thread
{
	final GuiScreenBackup field_111250_a;
	
	GuiScreenBackupDownloadThread(GuiScreenBackup par1GuiScreenBackup)
	{
		field_111250_a = par1GuiScreenBackup;
	}
	
	@Override public void run()
	{
		McoClient var1 = new McoClient(GuiScreenBackup.func_110366_a(field_111250_a).func_110432_I());
		try
		{
			GuiScreenBackup.func_110373_a(field_111250_a, var1.func_111232_c(GuiScreenBackup.func_110367_b(field_111250_a)).field_111223_a);
		} catch(ExceptionMcoService var3)
		{
			GuiScreenBackup.func_130030_c(field_111250_a).getLogAgent().logSevere(var3.toString());
		}
	}
}
