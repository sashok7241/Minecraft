package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;

class GuiSlotStatsItem extends GuiSlotStats
{
	final GuiStats slotGuiStats;
	
	public GuiSlotStatsItem(GuiStats par1GuiStats)
	{
		super(par1GuiStats);
		slotGuiStats = par1GuiStats;
		field_77266_h = new ArrayList();
		Iterator var2 = StatList.itemStats.iterator();
		while(var2.hasNext())
		{
			StatCrafting var3 = (StatCrafting) var2.next();
			boolean var4 = false;
			int var5 = var3.getItemID();
			if(GuiStats.getStatsFileWriter(par1GuiStats).writeStat(var3) > 0)
			{
				var4 = true;
			} else if(StatList.objectBreakStats[var5] != null && GuiStats.getStatsFileWriter(par1GuiStats).writeStat(StatList.objectBreakStats[var5]) > 0)
			{
				var4 = true;
			} else if(StatList.objectCraftStats[var5] != null && GuiStats.getStatsFileWriter(par1GuiStats).writeStat(StatList.objectCraftStats[var5]) > 0)
			{
				var4 = true;
			}
			if(var4)
			{
				field_77266_h.add(var3);
			}
		}
		field_77267_i = new SorterStatsItem(this, par1GuiStats);
	}
	
	@Override protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		StatCrafting var6 = func_77257_d(par1);
		int var7 = var6.getItemID();
		GuiStats.drawItemSprite(slotGuiStats, par2 + 40, par3, var7);
		func_77260_a((StatCrafting) StatList.objectBreakStats[var7], par2 + 115, par3, par1 % 2 == 0);
		func_77260_a((StatCrafting) StatList.objectCraftStats[var7], par2 + 165, par3, par1 % 2 == 0);
		func_77260_a(var6, par2 + 215, par3, par1 % 2 == 0);
	}
	
	@Override protected void func_77222_a(int par1, int par2, Tessellator par3Tessellator)
	{
		super.func_77222_a(par1, par2, par3Tessellator);
		if(field_77262_g == 0)
		{
			GuiStats.drawSprite(slotGuiStats, par1 + 115 - 18 + 1, par2 + 1 + 1, 72, 18);
		} else
		{
			GuiStats.drawSprite(slotGuiStats, par1 + 115 - 18, par2 + 1, 72, 18);
		}
		if(field_77262_g == 1)
		{
			GuiStats.drawSprite(slotGuiStats, par1 + 165 - 18 + 1, par2 + 1 + 1, 18, 18);
		} else
		{
			GuiStats.drawSprite(slotGuiStats, par1 + 165 - 18, par2 + 1, 18, 18);
		}
		if(field_77262_g == 2)
		{
			GuiStats.drawSprite(slotGuiStats, par1 + 215 - 18 + 1, par2 + 1 + 1, 36, 18);
		} else
		{
			GuiStats.drawSprite(slotGuiStats, par1 + 215 - 18, par2 + 1, 36, 18);
		}
	}
	
	@Override protected String func_77258_c(int par1)
	{
		return par1 == 1 ? "stat.crafted" : par1 == 2 ? "stat.used" : "stat.depleted";
	}
}
