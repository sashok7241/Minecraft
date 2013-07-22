package net.minecraft.src;

public class EnchantmentFireAspect extends Enchantment
{
	protected EnchantmentFireAspect(int par1, int par2)
	{
		super(par1, par2, EnumEnchantmentType.weapon);
		setName("fire");
	}
	
	@Override public int getMaxEnchantability(int par1)
	{
		return super.getMinEnchantability(par1) + 50;
	}
	
	@Override public int getMaxLevel()
	{
		return 2;
	}
	
	@Override public int getMinEnchantability(int par1)
	{
		return 10 + 20 * (par1 - 1);
	}
}
