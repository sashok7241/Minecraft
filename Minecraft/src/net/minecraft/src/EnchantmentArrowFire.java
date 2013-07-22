package net.minecraft.src;

public class EnchantmentArrowFire extends Enchantment
{
	public EnchantmentArrowFire(int par1, int par2)
	{
		super(par1, par2, EnumEnchantmentType.bow);
		setName("arrowFire");
	}
	
	@Override public int getMaxEnchantability(int par1)
	{
		return 50;
	}
	
	@Override public int getMaxLevel()
	{
		return 1;
	}
	
	@Override public int getMinEnchantability(int par1)
	{
		return 20;
	}
}
