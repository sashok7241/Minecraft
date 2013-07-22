package net.minecraft.src;

import java.util.Map;

class ThreadStatSyncherSend extends Thread
{
	final Map field_77483_a;
	final StatsSyncher syncher;
	
	ThreadStatSyncherSend(StatsSyncher par1StatsSyncher, Map par2Map)
	{
		syncher = par1StatsSyncher;
		field_77483_a = par2Map;
	}
	
	@Override public void run()
	{
		try
		{
			StatsSyncher.func_77414_a(syncher, field_77483_a, StatsSyncher.getUnsentDataFile(syncher), StatsSyncher.getUnsentTempFile(syncher), StatsSyncher.getUnsentOldFile(syncher));
		} catch(Exception var5)
		{
			var5.printStackTrace();
		} finally
		{
			StatsSyncher.setBusy(syncher, false);
		}
	}
}
