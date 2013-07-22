package net.minecraft.src;

public class EnchantmentArrowKnockback extends Enchantment
{
	public EnchantmentArrowKnockback(int par1, int par2)
	{
		super(par1, par2, EnumEnchantmentType.bow);
		setName("arrowKnockback");
	}
	
	@Override public int getMaxEnchantability(int par1)
	{
		return getMinEnchantability(par1) + 25;
	}
	
	@Override public int getMaxLevel()
	{
		return 2;
	}
	
	@Override public int getMinEnchantability(int par1)
	{
		return 12 + (par1 - 1) * 20;
	}
}
