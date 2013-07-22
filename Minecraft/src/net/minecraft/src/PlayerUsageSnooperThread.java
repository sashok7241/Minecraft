package net.minecraft.src;

import java.util.HashMap;
import java.util.TimerTask;

class PlayerUsageSnooperThread extends TimerTask
{
	final PlayerUsageSnooper snooper;
	
	PlayerUsageSnooperThread(PlayerUsageSnooper p_i3427_1_)
	{
		snooper = p_i3427_1_;
	}
	
	@Override public void run()
	{
		if(PlayerUsageSnooper.getStatsCollectorFor(snooper).isSnooperEnabled())
		{
			HashMap var1;
			synchronized(PlayerUsageSnooper.getSyncLockFor(snooper))
			{
				var1 = new HashMap(PlayerUsageSnooper.getDataMapFor(snooper));
				var1.put("snooper_count", Integer.valueOf(PlayerUsageSnooper.getSelfCounterFor(snooper)));
			}
			HttpUtil.sendPost(PlayerUsageSnooper.getStatsCollectorFor(snooper).getLogAgent(), PlayerUsageSnooper.getServerUrlFor(snooper), var1, true);
		}
	}
}
