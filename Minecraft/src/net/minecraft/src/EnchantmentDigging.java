package net.minecraft.src;

public class EnchantmentDigging extends Enchantment
{
	protected EnchantmentDigging(int p_i3708_1_, int p_i3708_2_)
	{
		super(p_i3708_1_, p_i3708_2_, EnumEnchantmentType.digger);
		setName("digging");
	}
	
	@Override public boolean canApply(ItemStack p_92089_1_)
	{
		return p_92089_1_.getItem().itemID == Item.shears.itemID ? true : super.canApply(p_92089_1_);
	}
	
	@Override public int getMaxEnchantability(int p_77317_1_)
	{
		return super.getMinEnchantability(p_77317_1_) + 50;
	}
	
	@Override public int getMaxLevel()
	{
		return 5;
	}
	
	@Override public int getMinEnchantability(int p_77321_1_)
	{
		return 1 + 10 * (p_77321_1_ - 1);
	}
}
