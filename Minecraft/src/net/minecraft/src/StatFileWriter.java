package net.minecraft.src;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StatFileWriter
{
	private Map field_77457_a = new HashMap();
	private Map field_77455_b = new HashMap();
	private boolean field_77456_c = false;
	private StatsSyncher statsSyncher;
	
	public StatFileWriter(Session p_i3218_1_, File p_i3218_2_)
	{
		File var3 = new File(p_i3218_2_, "stats");
		if(!var3.exists())
		{
			var3.mkdir();
		}
		File[] var4 = p_i3218_2_.listFiles();
		int var5 = var4.length;
		for(int var6 = 0; var6 < var5; ++var6)
		{
			File var7 = var4[var6];
			if(var7.getName().startsWith("stats_") && var7.getName().endsWith(".dat"))
			{
				File var8 = new File(var3, var7.getName());
				if(!var8.exists())
				{
					System.out.println("Relocating " + var7.getName());
					var7.renameTo(var8);
				}
			}
		}
		statsSyncher = new StatsSyncher(p_i3218_1_, this, var3);
	}
	
	public boolean canUnlockAchievement(Achievement par1Achievement)
	{
		return par1Achievement.parentAchievement == null || hasAchievementUnlocked(par1Achievement.parentAchievement);
	}
	
	public Map func_77445_b()
	{
		return new HashMap(field_77455_b);
	}
	
	public void func_77448_c(Map par1Map)
	{
		if(par1Map != null)
		{
			field_77456_c = true;
			Iterator var2 = par1Map.keySet().iterator();
			while(var2.hasNext())
			{
				StatBase var3 = (StatBase) var2.next();
				writeStatToMap(field_77455_b, var3, ((Integer) par1Map.get(var3)).intValue());
			}
		}
	}
	
	public void func_77449_e()
	{
		if(field_77456_c && statsSyncher.func_77425_c())
		{
			statsSyncher.beginSendStats(func_77445_b());
		}
		statsSyncher.func_77422_e();
	}
	
	public void func_77452_b(Map par1Map)
	{
		if(par1Map != null)
		{
			Iterator var2 = par1Map.keySet().iterator();
			while(var2.hasNext())
			{
				StatBase var3 = (StatBase) var2.next();
				Integer var4 = (Integer) field_77455_b.get(var3);
				int var5 = var4 == null ? 0 : var4.intValue();
				field_77457_a.put(var3, Integer.valueOf(((Integer) par1Map.get(var3)).intValue() + var5));
			}
		}
	}
	
	public boolean hasAchievementUnlocked(Achievement par1Achievement)
	{
		return field_77457_a.containsKey(par1Achievement);
	}
	
	public void readStat(StatBase par1StatBase, int par2)
	{
		writeStatToMap(field_77455_b, par1StatBase, par2);
		writeStatToMap(field_77457_a, par1StatBase, par2);
		field_77456_c = true;
	}
	
	public void syncStats()
	{
		statsSyncher.syncStatsFileWithMap(func_77445_b());
	}
	
	public int writeStat(StatBase par1StatBase)
	{
		Integer var2 = (Integer) field_77457_a.get(par1StatBase);
		return var2 == null ? 0 : var2.intValue();
	}
	
	public void writeStats(Map par1Map)
	{
		if(par1Map != null)
		{
			field_77456_c = true;
			Iterator var2 = par1Map.keySet().iterator();
			while(var2.hasNext())
			{
				StatBase var3 = (StatBase) var2.next();
				writeStatToMap(field_77455_b, var3, ((Integer) par1Map.get(var3)).intValue());
				writeStatToMap(field_77457_a, var3, ((Integer) par1Map.get(var3)).intValue());
			}
		}
	}
	
	private void writeStatToMap(Map par1Map, StatBase par2StatBase, int par3)
	{
		Integer var4 = (Integer) par1Map.get(par2StatBase);
		int var5 = var4 == null ? 0 : var4.intValue();
		par1Map.put(par2StatBase, Integer.valueOf(var5 + par3));
	}
	
	public static String func_77441_a(String par0Str, String par1Str, Map par2Map)
	{
		StringBuilder var3 = new StringBuilder();
		StringBuilder var4 = new StringBuilder();
		boolean var5 = true;
		var3.append("{\r\n");
		if(par0Str != null && par1Str != null)
		{
			var3.append("  \"user\":{\r\n");
			var3.append("    \"name\":\"").append(par0Str).append("\",\r\n");
			var3.append("    \"sessionid\":\"").append(par1Str).append("\"\r\n");
			var3.append("  },\r\n");
		}
		var3.append("  \"stats-change\":[");
		Iterator var6 = par2Map.keySet().iterator();
		while(var6.hasNext())
		{
			StatBase var7 = (StatBase) var6.next();
			if(var5)
			{
				var5 = false;
			} else
			{
				var3.append("},");
			}
			var3.append("\r\n    {\"").append(var7.statId).append("\":").append(par2Map.get(var7));
			var4.append(var7.statGuid).append(",");
			var4.append(par2Map.get(var7)).append(",");
		}
		if(!var5)
		{
			var3.append("}");
		}
		MD5String var8 = new MD5String(par1Str);
		var3.append("\r\n  ],\r\n");
		var3.append("  \"checksum\":\"").append(var8.getMD5String(var4.toString())).append("\"\r\n");
		var3.append("}");
		return var3.toString();
	}
	
	public static Map func_77453_b(String par0Str)
	{
		HashMap var1 = new HashMap();
		try
		{
			String var2 = "local";
			StringBuilder var3 = new StringBuilder();
			JsonRootNode var4 = new JdomParser().parse(par0Str);
			List var5 = var4.getArrayNode(new Object[] { "stats-change" });
			Iterator var6 = var5.iterator();
			while(var6.hasNext())
			{
				JsonNode var7 = (JsonNode) var6.next();
				Map var8 = var7.getFields();
				Entry var9 = (Entry) var8.entrySet().iterator().next();
				int var10 = Integer.parseInt(((JsonStringNode) var9.getKey()).getText());
				int var11 = Integer.parseInt(((JsonNode) var9.getValue()).getText());
				StatBase var12 = StatList.getOneShotStat(var10);
				if(var12 == null)
				{
					System.out.println(var10 + " is not a valid stat, creating place-holder");
					var12 = new StatPlaceholder(var10).registerStat();
				}
				var3.append(StatList.getOneShotStat(var10).statGuid).append(",");
				var3.append(var11).append(",");
				var1.put(var12, Integer.valueOf(var11));
			}
			MD5String var14 = new MD5String(var2);
			String var15 = var14.getMD5String(var3.toString());
			if(!var15.equals(var4.getStringValue(new Object[] { "checksum" })))
			{
				System.out.println("CHECKSUM MISMATCH");
				return null;
			}
		} catch(InvalidSyntaxException var13)
		{
			var13.printStackTrace();
		}
		return var1;
	}
}
