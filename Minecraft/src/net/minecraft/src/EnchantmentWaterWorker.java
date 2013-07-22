package net.minecraft.src;

public class EnchantmentWaterWorker extends Enchantment
{
	public EnchantmentWaterWorker(int p_i3720_1_, int p_i3720_2_)
	{
		super(p_i3720_1_, p_i3720_2_, EnumEnchantmentType.armor_head);
		setName("waterWorker");
	}
	
	@Override public int getMaxEnchantability(int p_77317_1_)
	{
		return getMinEnchantability(p_77317_1_) + 40;
	}
	
	@Override public int getMaxLevel()
	{
		return 1;
	}
	
	@Override public int getMinEnchantability(int p_77321_1_)
	{
		return 1;
	}
}
