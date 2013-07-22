package net.minecraft.src;

public class StatBasic extends StatBase
{
	public StatBasic(int p_i3413_1_, String p_i3413_2_)
	{
		super(p_i3413_1_, p_i3413_2_);
	}
	
	public StatBasic(int p_i3412_1_, String p_i3412_2_, IStatType p_i3412_3_)
	{
		super(p_i3412_1_, p_i3412_2_, p_i3412_3_);
	}
	
	@Override public StatBase registerStat()
	{
		super.registerStat();
		StatList.generalStats.add(this);
		return this;
	}
}
