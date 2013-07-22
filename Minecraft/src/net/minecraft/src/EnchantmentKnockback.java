package net.minecraft.src;

public class EnchantmentKnockback extends Enchantment
{
	protected EnchantmentKnockback(int par1, int par2)
	{
		super(par1, par2, EnumEnchantmentType.weapon);
		setName("knockback");
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
		return 5 + 20 * (par1 - 1);
	}
}
