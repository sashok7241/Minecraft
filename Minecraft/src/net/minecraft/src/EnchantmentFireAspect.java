package net.minecraft.src;

public class EnchantmentFireAspect extends Enchantment
{
	protected EnchantmentFireAspect(int p_i3714_1_, int p_i3714_2_)
	{
		super(p_i3714_1_, p_i3714_2_, EnumEnchantmentType.weapon);
		setName("fire");
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
		return 10 + 20 * (p_77321_1_ - 1);
	}
}
