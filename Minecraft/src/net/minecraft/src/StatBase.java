package net.minecraft.src;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class StatBase
{
	public final int statId;
	private final String statName;
	public boolean isIndependent;
	public String statGuid;
	private final IStatType type;
	private static NumberFormat numberFormat = NumberFormat.getIntegerInstance(java.util.Locale.US);
	public static IStatType simpleStatType = new StatTypeSimple();
	private static DecimalFormat decimalFormat = new DecimalFormat("########0.00");
	public static IStatType timeStatType = new StatTypeTime();
	public static IStatType distanceStatType = new StatTypeDistance();
	public static IStatType field_111202_k = new StatTypeFloat();
	
	public StatBase(int par1, String par2Str)
	{
		this(par1, par2Str, simpleStatType);
	}
	
	public StatBase(int par1, String par2Str, IStatType par3IStatType)
	{
		statId = par1;
		statName = par2Str;
		type = par3IStatType;
	}
	
	public String func_75968_a(int par1)
	{
		return type.format(par1);
	}
	
	public String getName()
	{
		return statName;
	}
	
	public StatBase initIndependentStat()
	{
		isIndependent = true;
		return this;
	}
	
	public boolean isAchievement()
	{
		return false;
	}
	
	public StatBase registerStat()
	{
		if(StatList.oneShotStats.containsKey(Integer.valueOf(statId))) throw new RuntimeException("Duplicate stat id: \"" + ((StatBase) StatList.oneShotStats.get(Integer.valueOf(statId))).statName + "\" and \"" + statName + "\" at id " + statId);
		else
		{
			StatList.allStats.add(this);
			StatList.oneShotStats.put(Integer.valueOf(statId), this);
			statGuid = AchievementMap.getGuid(statId);
			return this;
		}
	}
	
	@Override public String toString()
	{
		return StatCollector.translateToLocal(statName);
	}
	
	static DecimalFormat getDecimalFormat()
	{
		return decimalFormat;
	}
	
	static NumberFormat getNumberFormat()
	{
		return numberFormat;
	}
}
