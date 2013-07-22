package net.minecraft.src;

public class EnchantmentOxygen extends Enchantment
{
	public EnchantmentOxygen(int par1, int par2)
	{
		super(par1, par2, EnumEnchantmentType.armor_head);
		setName("oxygen");
	}
	
	@Override public int getMaxEnchantability(int par1)
	{
		return getMinEnchantability(par1) + 30;
	}
	
	@Override public int getMaxLevel()
	{
		return 3;
	}
	
	@Override public int getMinEnchantability(int par1)
	{
		return 10 * par1;
	}
}
