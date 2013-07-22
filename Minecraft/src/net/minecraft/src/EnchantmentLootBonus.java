package net.minecraft.src;

public class EnchantmentLootBonus extends Enchantment
{
	protected EnchantmentLootBonus(int p_i3716_1_, int p_i3716_2_, EnumEnchantmentType p_i3716_3_)
	{
		super(p_i3716_1_, p_i3716_2_, p_i3716_3_);
		setName("lootBonus");
		if(p_i3716_3_ == EnumEnchantmentType.digger)
		{
			setName("lootBonusDigger");
		}
	}
	
	@Override public boolean canApplyTogether(Enchantment p_77326_1_)
	{
		return super.canApplyTogether(p_77326_1_) && p_77326_1_.effectId != silkTouch.effectId;
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
		return 15 + (p_77321_1_ - 1) * 9;
	}
}
