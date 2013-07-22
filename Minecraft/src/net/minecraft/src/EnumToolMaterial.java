package net.minecraft.src;

public enum EnumToolMaterial
{
	WOOD(0, 59, 2.0F, 0, 15), STONE(1, 131, 4.0F, 1, 5), IRON(2, 250, 6.0F, 2, 14), EMERALD(3, 1561, 8.0F, 3, 10), GOLD(0, 32, 12.0F, 0, 22);
	private final int harvestLevel;
	private final int maxUses;
	private final float efficiencyOnProperMaterial;
	private final int damageVsEntity;
	private final int enchantability;
	
	private EnumToolMaterial(int p_i3658_3_, int p_i3658_4_, float p_i3658_5_, int p_i3658_6_, int p_i3658_7_)
	{
		harvestLevel = p_i3658_3_;
		maxUses = p_i3658_4_;
		efficiencyOnProperMaterial = p_i3658_5_;
		damageVsEntity = p_i3658_6_;
		enchantability = p_i3658_7_;
	}
	
	public int getDamageVsEntity()
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
