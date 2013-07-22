package net.minecraft.src;

import java.util.Comparator;

class SorterStatsItem implements Comparator
{
	final GuiStats statsGUI;
	final GuiSlotStatsItem slotStatsItemGUI;
	
	SorterStatsItem(GuiSlotStatsItem p_i3073_1_, GuiStats p_i3073_2_)
	{
		slotStatsItemGUI = p_i3073_1_;
		statsGUI = p_i3073_2_;
	}
	
	@Override public int compare(Object p_compare_1_, Object p_compare_2_)
	{
		return func_78337_a((StatCrafting) p_compare_1_, (StatCrafting) p_compare_2_);
	}
	
	public int func_78337_a(StatCrafting par1StatCrafting, StatCrafting par2StatCrafting)
	{
		int var3 = par1StatCrafting.getItemID();
		int var4 = par2StatCrafting.getItemID();
		StatBase var5 = null;
		StatBase var6 = null;
		if(slotStatsItemGUI.field_77264_j == 0)
		{
			var5 = StatList.objectBreakStats[var3];
			var6 = StatList.objectBreakStats[var4];
		} else if(slotStatsItemGUI.field_77264_j == 1)
		{
			var5 = StatList.objectCraftStats[var3];
			var6 = StatList.objectCraftStats[var4];
		} else if(slotStatsItemGUI.field_77264_j == 2)
		{
			var5 = StatList.objectUseStats[var3];
			var6 = StatList.objectUseStats[var4];
		}
		if(var5 != null || var6 != null)
		{
			if(var5 == null) return 1;
			if(var6 == null) return -1;
			int var7 = GuiStats.getStatsFileWriter(slotStatsItemGUI.slotGuiStats).writeStat(var5);
			int var8 = GuiStats.getStatsFileWriter(slotStatsItemGUI.slotGuiStats).writeStat(var6);
			if(var7 != var8) return (var7 - var8) * slotStatsItemGUI.field_77265_k;
		}
		return var3 - var4;
	}
}
