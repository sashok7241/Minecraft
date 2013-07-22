package net.minecraft.src;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

public class GameRules
{
	private TreeMap theGameRules = new TreeMap();
	
	public GameRules()
	{
		addGameRule("doFireTick", "true");
		addGameRule("mobGriefing", "true");
		addGameRule("keepInventory", "false");
		addGameRule("doMobSpawning", "true");
		addGameRule("doMobLoot", "true");
		addGameRule("doTileDrops", "true");
		addGameRule("commandBlockOutput", "true");
	}
	
	public void addGameRule(String p_82769_1_, String p_82769_2_)
	{
		theGameRules.put(p_82769_1_, new GameRuleValue(p_82769_2_));
	}
	
	public boolean getGameRuleBooleanValue(String p_82766_1_)
	{
		GameRuleValue var2 = (GameRuleValue) theGameRules.get(p_82766_1_);
		return var2 != null ? var2.getGameRuleBooleanValue() : false;
	}
	
	public String getGameRuleStringValue(String p_82767_1_)
	{
		GameRuleValue var2 = (GameRuleValue) theGameRules.get(p_82767_1_);
		return var2 != null ? var2.getGameRuleStringValue() : "";
	}
	
	public String[] getRules()
	{
		return (String[]) theGameRules.keySet().toArray(new String[0]);
	}
	
	public boolean hasRule(String p_82765_1_)
	{
		return theGameRules.containsKey(p_82765_1_);
	}
	
	public void readGameRulesFromNBT(NBTTagCompound p_82768_1_)
	{
		Collection var2 = p_82768_1_.getTags();
		Iterator var3 = var2.iterator();
		while(var3.hasNext())
		{
			NBTBase var4 = (NBTBase) var3.next();
			String var5 = var4.getName();
			String var6 = p_82768_1_.getString(var4.getName());
			setOrCreateGameRule(var5, var6);
		}
	}
	
	public void setOrCreateGameRule(String p_82764_1_, String p_82764_2_)
	{
		GameRuleValue var3 = (GameRuleValue) theGameRules.get(p_82764_1_);
		if(var3 != null)
		{
			var3.setValue(p_82764_2_);
		} else
		{
			addGameRule(p_82764_1_, p_82764_2_);
		}
	}
	
	public NBTTagCompound writeGameRulesToNBT()
	{
		NBTTagCompound var1 = new NBTTagCompound("GameRules");
		Iterator var2 = theGameRules.keySet().iterator();
		while(var2.hasNext())
		{
			String var3 = (String) var2.next();
			GameRuleValue var4 = (GameRuleValue) theGameRules.get(var3);
			var1.setString(var3, var4.getGameRuleStringValue());
		}
		return var1;
	}
}
