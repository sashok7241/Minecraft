package net.minecraft.src;

public class EnchantmentOxygen extends Enchantment
{
	public EnchantmentOxygen(int p_i3717_1_, int p_i3717_2_)
	{
		super(p_i3717_1_, p_i3717_2_, EnumEnchantmentType.armor_head);
		setName("oxygen");
	}
	
	@Override public int getMaxEnchantability(int p_77317_1_)
	{
		return getMinEnchantability(p_77317_1_) + 30;
	}
	
	@Override public int getMaxLevel()
	{
		return 3;
	}
	
	@Override public int getMinEnchantability(int p_77321_1_)
	{
		return 10 * p_77321_1_;
	}
}
