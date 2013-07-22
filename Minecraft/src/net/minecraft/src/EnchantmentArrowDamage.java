package net.minecraft.src;

public class EnchantmentArrowDamage extends Enchantment
{
	public EnchantmentArrowDamage(int par1, int par2)
	{
		super(par1, par2, EnumEnchantmentType.bow);
		setName("arrowDamage");
	}
	
	@Override public int getMaxEnchantability(int par1)
	{
		return getMinEnchantability(par1) + 15;
	}
	
	@Override public int getMaxLevel()
	{
		return 5;
	}
	
	@Override public int getMinEnchantability(int par1)
	{
		return 1 + (par1 - 1) * 10;
	}
}
