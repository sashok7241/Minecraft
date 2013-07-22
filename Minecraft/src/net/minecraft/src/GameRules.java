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
		addGameRule("naturalRegeneration", "true");
		addGameRule("doDaylightCycle", "true");
	}
	
	public void addGameRule(String par1Str, String par2Str)
	{
		theGameRules.put(par1Str, new GameRuleValue(par2Str));
	}
	
	public boolean getGameRuleBooleanValue(String par1Str)
	{
		GameRuleValue var2 = (GameRuleValue) theGameRules.get(par1Str);
		return var2 != null ? var2.getGameRuleBooleanValue() : false;
	}
	
	public String getGameRuleStringValue(String par1Str)
	{
		GameRuleValue var2 = (GameRuleValue) theGameRules.get(par1Str);
		return var2 != null ? var2.getGameRuleStringValue() : "";
	}
	
	public String[] getRules()
	{
		return (String[]) theGameRules.keySet().toArray(new String[0]);
	}
	
	public boolean hasRule(String par1Str)
	{
		return theGameRules.containsKey(par1Str);
	}
	
	public void readGameRulesFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		Collection var2 = par1NBTTagCompound.getTags();
		Iterator var3 = var2.iterator();
		while(var3.hasNext())
		{
			NBTBase var4 = (NBTBase) var3.next();
			String var5 = var4.getName();
			String var6 = par1NBTTagCompound.getString(var4.getName());
			setOrCreateGameRule(var5, var6);
		}
	}
	
	public void setOrCreateGameRule(String par1Str, String par2Str)
	{
		GameRuleValue var3 = (GameRuleValue) theGameRules.get(par1Str);
		if(var3 != null)
		{
			var3.setValue(par2Str);
		} else
		{
			addGameRule(par1Str, par2Str);
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
