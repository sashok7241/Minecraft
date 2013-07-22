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
	
	public void addSmelting(int par1, ItemStack par2ItemStack, float par3)
	{
		smeltingList.put(Integer.valueOf(par1), par2ItemStack);
		experienceList.put(Integer.valueOf(par2ItemStack.itemID), Float.valueOf(par3));
	}
	
	public float getExperience(int par1)
	{
		return experienceList.containsKey(Integer.valueOf(par1)) ? ((Float) experienceList.get(Integer.valueOf(par1))).floatValue() : 0.0F;
	}
	
	public Map getSmeltingList()
	{
		return smeltingList;
	}
	
	public ItemStack getSmeltingResult(int par1)
	{
		return (ItemStack) smeltingList.get(Integer.valueOf(par1));
	}
	
	public static final FurnaceRecipes smelting()
	{
		return smeltingBase;
	}
}
