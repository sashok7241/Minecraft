package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

public class PacketCount
{
	public static boolean allowCounting = true;
	private static final Map packetCountForID = new HashMap();
	private static final Map sizeCountForID = new HashMap();
	private static final Object lock = new Object();
	
	public static void countPacket(int p_76118_0_, long p_76118_1_)
	{
		if(allowCounting)
		{
			Object var3 = lock;
			synchronized(lock)
			{
				if(packetCountForID.containsKey(Integer.valueOf(p_76118_0_)))
				{
					packetCountForID.put(Integer.valueOf(p_76118_0_), Long.valueOf(((Long) packetCountForID.get(Integer.valueOf(p_76118_0_))).longValue() + 1L));
					sizeCountForID.put(Integer.valueOf(p_76118_0_), Long.valueOf(((Long) sizeCountForID.get(Integer.valueOf(p_76118_0_))).longValue() + p_76118_1_));
				} else
				{
					packetCountForID.put(Integer.valueOf(p_76118_0_), Long.valueOf(1L));
					sizeCountForID.put(Integer.valueOf(p_76118_0_), Long.valueOf(p_76118_1_));
				}
			}
		}
	}
}
