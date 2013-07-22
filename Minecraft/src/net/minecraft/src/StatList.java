package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StatList
{
	protected static Map oneShotStats = new HashMap();
	public static List allStats = new ArrayList();
	public static List generalStats = new ArrayList();
	public static List itemStats = new ArrayList();
	public static List objectMineStats = new ArrayList();
	public static StatBase startGameStat = new StatBasic(1000, "stat.startGame").initIndependentStat().registerStat();
	public static StatBase createWorldStat = new StatBasic(1001, "stat.createWorld").initIndependentStat().registerStat();
	public static StatBase loadWorldStat = new StatBasic(1002, "stat.loadWorld").initIndependentStat().registerStat();
	public static StatBase joinMultiplayerStat = new StatBasic(1003, "stat.joinMultiplayer").initIndependentStat().registerStat();
	public static StatBase leaveGameStat = new StatBasic(1004, "stat.leaveGame").initIndependentStat().registerStat();
	public static StatBase minutesPlayedStat = new StatBasic(1100, "stat.playOneMinute", StatBase.timeStatType).initIndependentStat().registerStat();
	public static StatBase distanceWalkedStat = new StatBasic(2000, "stat.walkOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
	public static StatBase distanceSwumStat = new StatBasic(2001, "stat.swimOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
	public static StatBase distanceFallenStat = new StatBasic(2002, "stat.fallOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
	public static StatBase distanceClimbedStat = new StatBasic(2003, "stat.climbOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
	public static StatBase distanceFlownStat = new StatBasic(2004, "stat.flyOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
	public static StatBase distanceDoveStat = new StatBasic(2005, "stat.diveOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
	public static StatBase distanceByMinecartStat = new StatBasic(2006, "stat.minecartOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
	public static StatBase distanceByBoatStat = new StatBasic(2007, "stat.boatOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
	public static StatBase distanceByPigStat = new StatBasic(2008, "stat.pigOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
	public static StatBase jumpStat = new StatBasic(2010, "stat.jump").initIndependentStat().registerStat();
	public static StatBase dropStat = new StatBasic(2011, "stat.drop").initIndependentStat().registerStat();
	public static StatBase damageDealtStat = new StatBasic(2020, "stat.damageDealt").registerStat();
	public static StatBase damageTakenStat = new StatBasic(2021, "stat.damageTaken").registerStat();
	public static StatBase deathsStat = new StatBasic(2022, "stat.deaths").registerStat();
	public static StatBase mobKillsStat = new StatBasic(2023, "stat.mobKills").registerStat();
	public static StatBase playerKillsStat = new StatBasic(2024, "stat.playerKills").registerStat();
	public static StatBase fishCaughtStat = new StatBasic(2025, "stat.fishCaught").registerStat();
	public static StatBase[] mineBlockStatArray = initMinableStats("stat.mineBlock", 16777216);
	public static StatBase[] objectCraftStats;
	public static StatBase[] objectUseStats;
	public static StatBase[] objectBreakStats;
	private static boolean blockStatsInitialized;
	private static boolean itemStatsInitialized;
	
	public static StatBase getOneShotStat(int par0)
	{
		return (StatBase) oneShotStats.get(Integer.valueOf(par0));
	}
	
	public static void initBreakableStats()
	{
		objectUseStats = initUsableStats(objectUseStats, "stat.useItem", 16908288, 0, 256);
		objectBreakStats = initBreakStats(objectBreakStats, "stat.breakItem", 16973824, 0, 256);
		blockStatsInitialized = true;
		initCraftableStats();
	}
	
	private static StatBase[] initBreakStats(StatBase[] p_75926_0_, String p_75926_1_, int p_75926_2_, int p_75926_3_, int p_75926_4_)
	{
		if(p_75926_0_ == null)
		{
			p_75926_0_ = new StatBase[32000];
		}
		for(int var5 = p_75926_3_; var5 < p_75926_4_; ++var5)
		{
			if(Item.itemsList[var5] != null && Item.itemsList[var5].isDamageable())
			{
				String var6 = StatCollector.translateToLocalFormatted(p_75926_1_, new Object[] { Item.itemsList[var5].getStatName() });
				p_75926_0_[var5] = new StatCrafting(p_75926_2_ + var5, var6, var5).registerStat();
			}
		}
		replaceAllSimilarBlocks(p_75926_0_);
		return p_75926_0_;
	}
	
	public static void initCraftableStats()
	{
		if(blockStatsInitialized && itemStatsInitialized)
		{
			HashSet var0 = new HashSet();
			Iterator var1 = CraftingManager.getInstance().getRecipeList().iterator();
			while(var1.hasNext())
			{
				IRecipe var2 = (IRecipe) var1.next();
				if(var2.getRecipeOutput() != null)
				{
					var0.add(Integer.valueOf(var2.getRecipeOutput().itemID));
				}
			}
			var1 = FurnaceRecipes.smelting().getSmeltingList().values().iterator();
			while(var1.hasNext())
			{
				ItemStack var4 = (ItemStack) var1.next();
				var0.add(Integer.valueOf(var4.itemID));
			}
			objectCraftStats = new StatBase[32000];
			var1 = var0.iterator();
			while(var1.hasNext())
			{
				Integer var5 = (Integer) var1.next();
				if(Item.itemsList[var5.intValue()] != null)
				{
					String var3 = StatCollector.translateToLocalFormatted("stat.craftItem", new Object[] { Item.itemsList[var5.intValue()].getStatName() });
					objectCraftStats[var5.intValue()] = new StatCrafting(16842752 + var5.intValue(), var3, var5.intValue()).registerStat();
				}
			}
			replaceAllSimilarBlocks(objectCraftStats);
		}
	}
	
	private static StatBase[] initMinableStats(String p_75921_0_, int p_75921_1_)
	{
		StatBase[] var2 = new StatBase[256];
		for(int var3 = 0; var3 < 256; ++var3)
		{
			if(Block.blocksList[var3] != null && Block.blocksList[var3].getEnableStats())
			{
				String var4 = StatCollector.translateToLocalFormatted(p_75921_0_, new Object[] { Block.blocksList[var3].getLocalizedName() });
				var2[var3] = new StatCrafting(p_75921_1_ + var3, var4, var3).registerStat();
				objectMineStats.add(var2[var3]);
			}
		}
		replaceAllSimilarBlocks(var2);
		return var2;
	}
	
	public static void initStats()
	{
		objectUseStats = initUsableStats(objectUseStats, "stat.useItem", 16908288, 256, 32000);
		objectBreakStats = initBreakStats(objectBreakStats, "stat.breakItem", 16973824, 256, 32000);
		itemStatsInitialized = true;
		initCraftableStats();
	}
	
	private static StatBase[] initUsableStats(StatBase[] p_75920_0_, String p_75920_1_, int p_75920_2_, int p_75920_3_, int p_75920_4_)
	{
		if(p_75920_0_ == null)
		{
			p_75920_0_ = new StatBase[32000];
		}
		for(int var5 = p_75920_3_; var5 < p_75920_4_; ++var5)
		{
			if(Item.itemsList[var5] != null)
			{
				String var6 = StatCollector.translateToLocalFormatted(p_75920_1_, new Object[] { Item.itemsList[var5].getStatName() });
				p_75920_0_[var5] = new StatCrafting(p_75920_2_ + var5, var6, var5).registerStat();
				if(var5 >= 256)
				{
					itemStats.add(p_75920_0_[var5]);
				}
			}
		}
		replaceAllSimilarBlocks(p_75920_0_);
		return p_75920_0_;
	}
	
	public static void nopInit()
	{
	}
	
	private static void replaceAllSimilarBlocks(StatBase[] p_75924_0_)
	{
		replaceSimilarBlocks(p_75924_0_, Block.waterStill.blockID, Block.waterMoving.blockID);
		replaceSimilarBlocks(p_75924_0_, Block.lavaStill.blockID, Block.lavaStill.blockID);
		replaceSimilarBlocks(p_75924_0_, Block.pumpkinLantern.blockID, Block.pumpkin.blockID);
		replaceSimilarBlocks(p_75924_0_, Block.furnaceBurning.blockID, Block.furnaceIdle.blockID);
		replaceSimilarBlocks(p_75924_0_, Block.oreRedstoneGlowing.blockID, Block.oreRedstone.blockID);
		replaceSimilarBlocks(p_75924_0_, Block.redstoneRepeaterActive.blockID, Block.redstoneRepeaterIdle.blockID);
		replaceSimilarBlocks(p_75924_0_, Block.torchRedstoneActive.blockID, Block.torchRedstoneIdle.blockID);
		replaceSimilarBlocks(p_75924_0_, Block.mushroomRed.blockID, Block.mushroomBrown.blockID);
		replaceSimilarBlocks(p_75924_0_, Block.stoneDoubleSlab.blockID, Block.stoneSingleSlab.blockID);
		replaceSimilarBlocks(p_75924_0_, Block.woodDoubleSlab.blockID, Block.woodSingleSlab.blockID);
		replaceSimilarBlocks(p_75924_0_, Block.grass.blockID, Block.dirt.blockID);
		replaceSimilarBlocks(p_75924_0_, Block.tilledField.blockID, Block.dirt.blockID);
	}
	
	private static void replaceSimilarBlocks(StatBase[] p_75927_0_, int p_75927_1_, int p_75927_2_)
	{
		if(p_75927_0_[p_75927_1_] != null && p_75927_0_[p_75927_2_] == null)
		{
			p_75927_0_[p_75927_2_] = p_75927_0_[p_75927_1_];
		} else
		{
			allStats.remove(p_75927_0_[p_75927_1_]);
			objectMineStats.remove(p_75927_0_[p_75927_1_]);
			generalStats.remove(p_75927_0_[p_75927_1_]);
			p_75927_0_[p_75927_1_] = p_75927_0_[p_75927_2_];
		}
	}
	
	static
	{
		AchievementList.init();
		blockStatsInitialized = false;
		itemStatsInitialized = false;
	}
}
