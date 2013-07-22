package net.minecraft.src;

public enum EnumToolMaterial
{
	WOOD(0, 59, 2.0F, 0.0F, 15), STONE(1, 131, 4.0F, 1.0F, 5), IRON(2, 250, 6.0F, 2.0F, 14), EMERALD(3, 1561, 8.0F, 3.0F, 10), GOLD(0, 32, 12.0F, 0.0F, 22);
	private final int harvestLevel;
	private final int maxUses;
	private final float efficiencyOnProperMaterial;
	private final float damageVsEntity;
	private final int enchantability;
	
	private EnumToolMaterial(int par3, int par4, float par5, float par6, int par7)
	{
		harvestLevel = par3;
		maxUses = par4;
		efficiencyOnProperMaterial = par5;
		damageVsEntity = par6;
		enchantability = par7;
	}
	
	public float getDamageVsEntity()
	{
		return damageVsEntity;
	}
	
	public float getEfficiencyOnProperMaterial()
	{
		return efficiencyOnProperMaterial;
	}
	
	public int getEnchantability()
	{
		return enchantability;
	}
	
	public int getHarvestLevel()
	{
		return harvestLevel;
	}
	
	public int getMaxUses()
	{
		return maxUses;
	}
	
	public int getToolCraftingMaterial()
	{
		return this == WOOD ? Block.planks.blockID : this == STONE ? Block.cobblestone.blockID : this == GOLD ? Item.ingotGold.itemID : this == IRON ? Item.ingotIron.itemID : this == EMERALD ? Item.diamond.itemID : 0;
	}
}
