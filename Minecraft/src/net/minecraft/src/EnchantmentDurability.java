package net.minecraft.src;

import java.util.Random;

public class EnchantmentDurability extends Enchantment
{
	protected EnchantmentDurability(int p_i3707_1_, int p_i3707_2_)
	{
		super(p_i3707_1_, p_i3707_2_, EnumEnchantmentType.digger);
		setName("durability");
	}
	
	@Override public boolean canApply(ItemStack p_92089_1_)
	{
		return p_92089_1_.isItemStackDamageable() ? true : super.canApply(p_92089_1_);
	}
	
	@Override public int getMaxEnchantability(int p_77317_1_)
	{
		return super.getMinEnchantability(p_77317_1_) + 50;
	}
	
	@Override public int getMaxLevel()
	{
		return 3;
	}
	
	@Override public int getMinEnchantability(int p_77321_1_)
	{
		return 5 + (p_77321_1_ - 1) * 8;
	}
	
	public static boolean negateDamage(ItemStack p_92097_0_, int p_92097_1_, Random p_92097_2_)
	{
		return p_92097_0_.getItem() instanceof ItemArmor && p_92097_2_.nextFloat() < 0.6F ? false : p_92097_2_.nextInt(p_92097_1_ + 1) > 0;
	}
}
