package net.minecraft.src;

public class EnchantmentArrowDamage extends Enchantment
{
	public EnchantmentArrowDamage(int p_i3702_1_, int p_i3702_2_)
	{
		super(p_i3702_1_, p_i3702_2_, EnumEnchantmentType.bow);
		setName("arrowDamage");
	}
	
	@Override public int getMaxEnchantability(int p_77317_1_)
	{
		return getMinEnchantability(p_77317_1_) + 15;
	}
	
	@Override public int getMaxLevel()
	{
		return 5;
	}
	
	@Override public int getMinEnchantability(int p_77321_1_)
	{
		return 1 + (p_77321_1_ - 1) * 10;
	}
}
