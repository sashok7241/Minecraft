package net.minecraft.src;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class StatBase
{
	public final int statId;
	private final String statName;
	public boolean isIndependent;
	public String statGuid;
	private final IStatType type;
	private static NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.US);
	public static IStatType simpleStatType = new StatTypeSimple();
	private static DecimalFormat decimalFormat = new DecimalFormat("########0.00");
	public static IStatType timeStatType = new StatTypeTime();
	public static IStatType distanceStatType = new StatTypeDistance();
	
	public StatBase(int p_i3416_1_, String p_i3416_2_)
	{
		this(p_i3416_1_, p_i3416_2_, simpleStatType);
	}
	
	public StatBase(int p_i3415_1_, String p_i3415_2_, IStatType p_i3415_3_)
	{
		isIndependent = false;
		statId = p_i3415_1_;
		statName = p_i3415_2_;
		type = p_i3415_3_;
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
