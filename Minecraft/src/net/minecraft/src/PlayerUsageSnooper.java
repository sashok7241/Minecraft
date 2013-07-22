package net.minecraft.src;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class PlayerUsageSnooper
{
	private Map dataMap = new HashMap();
	private final String uniqueID = UUID.randomUUID().toString();
	private final URL serverUrl;
	private final IPlayerUsage playerStatsCollector;
	private final java.util.Timer threadTrigger = new java.util.Timer("Snooper Timer", true);
	private final Object syncLock = new Object();
	private final long field_98224_g;
	private boolean isRunning;
	private int selfCounter;
	
	public PlayerUsageSnooper(String par1Str, IPlayerUsage par2IPlayerUsage, long par3)
	{
		try
		{
			serverUrl = new URL("http://snoop.minecraft.net/" + par1Str + "?version=" + 1);
		} catch(MalformedURLException var6)
		{
			throw new IllegalArgumentException();
		}
		playerStatsCollector = par2IPlayerUsage;
		field_98224_g = par3;
	}
	
	private void addBaseDataToSnooper()
	{
		addJvmArgsToSnooper();
		addData("snooper_token", uniqueID);
		addData("os_name", System.getProperty("os.name"));
		addData("os_version", System.getProperty("os.version"));
		addData("os_architecture", System.getProperty("os.arch"));
		addData("java_version", System.getProperty("java.version"));
		addData("version", "1.6.2");
		playerStatsCollector.addServerTypeToSnooper(this);
	}
	
	public void addData(String par1Str, Object par2Obj)
	{
		Object var3 = syncLock;
		synchronized(syncLock)
		{
			dataMap.put(par1Str, par2Obj);
		}
	}
	
	private void addJvmArgsToSnooper()
	{
		RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
		List var2 = var1.getInputArguments();
		int var3 = 0;
		Iterator var4 = var2.iterator();
		while(var4.hasNext())
		{
			String var5 = (String) var4.next();
			if(var5.startsWith("-X"))
			{
				addData("jvm_arg[" + var3++ + "]", var5);
			}
		}
		addData("jvm_args", Integer.valueOf(var3));
	}
	
	public void addMemoryStatsToSnooper()
	{
		addData("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
		addData("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
		addData("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
		addData("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
		playerStatsCollector.addServerStatsToSnooper(this);
	}
	
	public long func_130105_g()
	{
		return field_98224_g;
	}
	
	public Map getCurrentStats()
	{
		LinkedHashMap var1 = new LinkedHashMap();
		Object var2 = syncLock;
		synchronized(syncLock)
		{
			addMemoryStatsToSnooper();
			Iterator var3 = dataMap.entrySet().iterator();
			while(var3.hasNext())
			{
				Entry var4 = (Entry) var3.next();
				var1.put(var4.getKey(), var4.getValue().toString());
			}
			return var1;
		}
	}
	
	public String getUniqueID()
	{
		return uniqueID;
	}
	
	public boolean isSnooperRunning()
	{
		return isRunning;
	}
	
	public void startSnooper()
	{
		if(!isRunning)
		{
			isRunning = true;
			addBaseDataToSnooper();
			threadTrigger.schedule(new PlayerUsageSnooperThread(this), 0L, 900000L);
		}
	}
	
	public void stopSnooper()
	{
		threadTrigger.cancel();
	}
	
	static Map getDataMapFor(PlayerUsageSnooper par0PlayerUsageSnooper)
	{
		return par0PlayerUsageSnooper.dataMap;
	}
	
	static int getSelfCounterFor(PlayerUsageSnooper par0PlayerUsageSnooper)
	{
		return par0PlayerUsageSnooper.selfCounter++;
	}
	
	static URL getServerUrlFor(PlayerUsageSnooper par0PlayerUsageSnooper)
	{
		return par0PlayerUsageSnooper.serverUrl;
	}
	
	static IPlayerUsage getStatsCollectorFor(PlayerUsageSnooper par0PlayerUsageSnooper)
	{
		return par0PlayerUsageSnooper.playerStatsCollector;
	}
	
	static Object getSyncLockFor(PlayerUsageSnooper par0PlayerUsageSnooper)
	{
		return par0PlayerUsageSnooper.syncLock;
	}
}
