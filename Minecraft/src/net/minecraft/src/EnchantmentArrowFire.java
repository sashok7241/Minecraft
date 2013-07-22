package net.minecraft.src;

public class EnchantmentArrowFire extends Enchantment
{
	public EnchantmentArrowFire(int p_i3703_1_, int p_i3703_2_)
	{
		super(p_i3703_1_, p_i3703_2_, EnumEnchantmentType.bow);
		setName("arrowFire");
	}
	
	@Override public int getMaxEnchantability(int p_77317_1_)
	{
		return 50;
	}
	
	@Override public int getMaxLevel()
	{
		return 1;
	}
	
	@Override public int getMinEnchantability(int p_77321_1_)
	{
		return 20;
	}
}
