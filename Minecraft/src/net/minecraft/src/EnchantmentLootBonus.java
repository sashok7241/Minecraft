package net.minecraft.src;

public class EnchantmentLootBonus extends Enchantment
{
	protected EnchantmentLootBonus(int par1, int par2, EnumEnchantmentType par3EnumEnchantmentType)
	{
		super(par1, par2, par3EnumEnchantmentType);
		setName("lootBonus");
		if(par3EnumEnchantmentType == EnumEnchantmentType.digger)
		{
			setName("lootBonusDigger");
		}
	}
	
	@Override public boolean canApplyTogether(Enchantment par1Enchantment)
	{
		return super.canApplyTogether(par1Enchantment) && par1Enchantment.effectId != silkTouch.effectId;
	}
	
	@Override public int getMaxEnchantability(int par1)
	{
		return super.getMinEnchantability(par1) + 50;
	}
	
	@Override public int getMaxLevel()
	{
		return 3;
	}
	
	@Override public int getMinEnchantability(int par1)
	{
		return 15 + (par1 - 1) * 9;
	}
}
