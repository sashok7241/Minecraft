package net.minecraft.src;

public class EnchantmentArrowKnockback extends Enchantment
{
	public EnchantmentArrowKnockback(int p_i3705_1_, int p_i3705_2_)
	{
		super(p_i3705_1_, p_i3705_2_, EnumEnchantmentType.bow);
		setName("arrowKnockback");
	}
	
	@Override public int getMaxEnchantability(int p_77317_1_)
	{
		return getMinEnchantability(p_77317_1_) + 25;
	}
	
	@Override public int getMaxLevel()
	{
		return 2;
	}
	
	@Override public int getMinEnchantability(int p_77321_1_)
	{
		return 12 + (p_77321_1_ - 1) * 20;
	}
}
