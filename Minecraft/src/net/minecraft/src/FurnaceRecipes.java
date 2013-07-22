package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

public class FurnaceRecipes
{
	private static final FurnaceRecipes smeltingBase = new FurnaceRecipes();
	private Map smeltingList = new HashMap();
	private Map experienceList = new HashMap();
	
	private FurnaceRecipes()
	{
		addSmelting(Block.oreIron.blockID, new ItemStack(Item.ingotIron), 0.7F);
		addSmelting(Block.oreGold.blockID, new ItemStack(Item.ingotGold), 1.0F);
		addSmelting(Block.oreDiamond.blockID, new ItemStack(Item.diamond), 1.0F);
		addSmelting(Block.sand.blockID, new ItemStack(Block.glass), 0.1F);
		addSmelting(Item.porkRaw.itemID, new ItemStack(Item.porkCooked), 0.35F);
		addSmelting(Item.beefRaw.itemID, new ItemStack(Item.beefCooked), 0.35F);
		addSmelting(Item.chickenRaw.itemID, new ItemStack(Item.chickenCooked), 0.35F);
		addSmelting(Item.fishRaw.itemID, new ItemStack(Item.fishCooked), 0.35F);
		addSmelting(Block.cobblestone.blockID, new ItemStack(Block.stone), 0.1F);
		addSmelting(Item.clay.itemID, new ItemStack(Item.brick), 0.3F);
		addSmelting(Block.cactus.blockID, new ItemStack(Item.dyePowder, 1, 2), 0.2F);
		addSmelting(Block.wood.blockID, new ItemStack(Item.coal, 1, 1), 0.15F);
		addSmelting(Block.oreEmerald.blockID, new ItemStack(Item.emerald), 1.0F);
		addSmelting(Item.potato.itemID, new ItemStack(Item.bakedPotato), 0.35F);
		addSmelting(Block.netherrack.blockID, new ItemStack(Item.netherrackBrick), 0.1F);
		addSmelting(Block.oreCoal.blockID, new ItemStack(Item.coal), 0.1F);
		addSmelting(Block.oreRedstone.blockID, new ItemStack(Item.redstone), 0.7F);
		addSmelting(Block.oreLapis.blockID, new ItemStack(Item.dyePowder, 1, 4), 0.2F);
		addSmelting(Block.oreNetherQuartz.blockID, new ItemStack(Item.netherQuartz), 0.2F);
	}
	
	public void addSmelting(int p_77600_1_, ItemStack p_77600_2_, float p_77600_3_)
	{
		smeltingList.put(Integer.valueOf(p_77600_1_), p_77600_2_);
		experienceList.put(Integer.valueOf(p_77600_2_.itemID), Float.valueOf(p_77600_3_));
	}
	
	public float getExperience(int p_77601_1_)
	{
		return experienceList.containsKey(Integer.valueOf(p_77601_1_)) ? ((Float) experienceList.get(Integer.valueOf(p_77601_1_))).floatValue() : 0.0F;
	}
	
	public Map getSmeltingList()
	{
		return smeltingList;
	}
	
	public ItemStack getSmeltingResult(int p_77603_1_)
	{
		return (ItemStack) smeltingList.get(Integer.valueOf(p_77603_1_));
	}
	
	public static final FurnaceRecipes smelting()
	{
		return smeltingBase;
	}
}
