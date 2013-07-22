package net.minecraft.src;

public class EnchantmentData extends WeightedRandomItem
{
	public final Enchantment enchantmentobj;
	public final int enchantmentLevel;
	
	public EnchantmentData(Enchantment p_i3713_1_, int p_i3713_2_)
	{
		super(p_i3713_1_.getWeight());
		enchantmentobj = p_i3713_1_;
		enchantmentLevel = p_i3713_2_;
	}
	
	public EnchantmentData(int p_i8014_1_, int p_i8014_2_)
	{
		this(Enchantment.enchantmentsList[p_i8014_1_], p_i8014_2_);
	}
}
