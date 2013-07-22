package net.minecraft.src;

class GuiScreenBackupRestoreTask extends TaskLongRunning
{
	private final Backup field_111254_c;
	final GuiScreenBackup field_111255_a;
	
	private GuiScreenBackupRestoreTask(GuiScreenBackup par1GuiScreenBackup, Backup par2Backup)
	{
		field_111255_a = par1GuiScreenBackup;
		field_111254_c = par2Backup;
	}
	
	GuiScreenBackupRestoreTask(GuiScreenBackup par1GuiScreenBackup, Backup par2Backup, GuiScreenBackupDownloadThread par3GuiScreenBackupDownloadThread)
	{
		this(par1GuiScreenBackup, par2Backup);
	}
	
	@Override public void run()
	{
		func_96576_b(I18n.func_135053_a("mco.backup.restoring"));
		try
		{
			McoClient var1 = new McoClient(func_96578_b().func_110432_I());
			var1.func_111235_c(GuiScreenBackup.func_110367_b(field_111255_a), field_111254_c.field_110727_a);
			try
			{
				Thread.sleep(1000L);
			} catch(InterruptedException var3)
			{
				Thread.currentThread().interrupt();
			}
			func_96578_b().displayGuiScreen(GuiScreenBackup.func_130031_d(field_111255_a));
		} catch(ExceptionMcoService var4)
		{
			GuiScreenBackup.func_130035_e(field_111255_a).getLogAgent().logSevere(var4.toString());
			func_96575_a(var4.toString());
		} catch(Exception var5)
		{
			func_96575_a(var5.getLocalizedMessage());
		}
	}
}
