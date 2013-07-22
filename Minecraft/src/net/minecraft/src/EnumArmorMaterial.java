package net.minecraft.src;

public enum EnumArmorMaterial
{
	CLOTH(5, new int[] { 1, 3, 2, 1 }, 15), CHAIN(15, new int[] { 2, 5, 4, 1 }, 12), IRON(15, new int[] { 2, 6, 5, 2 }, 9), GOLD(7, new int[] { 2, 5, 3, 1 }, 25), DIAMOND(33, new int[] { 3, 8, 6, 3 }, 10);
	private int maxDamageFactor;
	private int[] damageReductionAmountArray;
	private int enchantability;
	
	private EnumArmorMaterial(int p_i3618_3_, int[] p_i3618_4_, int p_i3618_5_)
	{
		maxDamageFactor = p_i3618_3_;
		damageReductionAmountArray = p_i3618_4_;
		enchantability = p_i3618_5_;
	}
	
	public int getArmorCraftingMaterial()
	{
		return this == CLOTH ? Item.leather.itemID : this == CHAIN ? Item.ingotIron.itemID : this == GOLD ? Item.ingotGold.itemID : this == IRON ? Item.ingotIron.itemID : this == DIAMOND ? Item.diamond.itemID : 0;
	}
	
	public int getDamageReductionAmount(int p_78044_1_)
	{
		return damageReductionAmountArray[p_78044_1_];
	}
	
	public int getDurability(int p_78046_1_)
	{
		return ItemArmor.getMaxDamageArray()[p_78046_1_] * maxDamageFactor;
	}
	
	public int getEnchantability()
	{
		return enchantability;
	}
}
