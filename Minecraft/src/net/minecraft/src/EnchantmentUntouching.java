package net.minecraft.src;

public class EnchantmentUntouching extends Enchantment
{
	protected EnchantmentUntouching(int p_i3719_1_, int p_i3719_2_)
	{
		super(p_i3719_1_, p_i3719_2_, EnumEnchantmentType.digger);
		setName("untouching");
	}
	
	@Override public boolean canApply(ItemStack p_92089_1_)
	{
		return p_92089_1_.getItem().itemID == Item.shears.itemID ? true : super.canApply(p_92089_1_);
	}
	
	@Override public boolean canApplyTogether(Enchantment p_77326_1_)
	{
		return super.canApplyTogether(p_77326_1_) && p_77326_1_.effectId != fortune.effectId;
	}
	
	@Override public int getMaxEnchantability(int p_77317_1_)
	{
		return super.getMinEnchantability(p_77317_1_) + 50;
	}
	
	@Override public int getMaxLevel()
	{
		return 1;
	}
	
	@Override public int getMinEnchantability(int p_77321_1_)
	{
		return 15;
	}
}
