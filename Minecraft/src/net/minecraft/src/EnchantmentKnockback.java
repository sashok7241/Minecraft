package net.minecraft.src;

public class EnchantmentKnockback extends Enchantment
{
	protected EnchantmentKnockback(int p_i3715_1_, int p_i3715_2_)
	{
		super(p_i3715_1_, p_i3715_2_, EnumEnchantmentType.weapon);
		setName("knockback");
	}
	
	@Override public int getMaxEnchantability(int p_77317_1_)
	{
		return super.getMinEnchantability(p_77317_1_) + 50;
	}
	
	@Override public int getMaxLevel()
	{
		return 2;
	}
	
	@Override public int getMinEnchantability(int p_77321_1_)
	{
		return 5 + 20 * (p_77321_1_ - 1);
	}
}
