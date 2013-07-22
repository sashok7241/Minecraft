package net.minecraft.src;

public class EnchantmentUntouching extends Enchantment
{
	protected EnchantmentUntouching(int par1, int par2)
	{
		super(par1, par2, EnumEnchantmentType.digger);
		setName("untouching");
	}
	
	@Override public boolean canApply(ItemStack par1ItemStack)
	{
		return par1ItemStack.getItem().itemID == Item.shears.itemID ? true : super.canApply(par1ItemStack);
	}
	
	@Override public boolean canApplyTogether(Enchantment par1Enchantment)
	{
		return super.canApplyTogether(par1Enchantment) && par1Enchantment.effectId != fortune.effectId;
	}
	
	@Override public int getMaxEnchantability(int par1)
	{
		return super.getMinEnchantability(par1) + 50;
	}
	
	@Override public int getMaxLevel()
	{
		return 1;
	}
	
	@Override public int getMinEnchantability(int par1)
	{
		return 15;
	}
}
