package net.minecraft.src;

import java.util.Random;

public class EnchantmentDurability extends Enchantment
{
	protected EnchantmentDurability(int par1, int par2)
	{
		super(par1, par2, EnumEnchantmentType.digger);
		setName("durability");
	}
	
	@Override public boolean canApply(ItemStack par1ItemStack)
	{
		return par1ItemStack.isItemStackDamageable() ? true : super.canApply(par1ItemStack);
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
		return 5 + (par1 - 1) * 8;
	}
	
	public static boolean negateDamage(ItemStack par0ItemStack, int par1, Random par2Random)
	{
		return par0ItemStack.getItem() instanceof ItemArmor && par2Random.nextFloat() < 0.6F ? false : par2Random.nextInt(par1 + 1) > 0;
	}
}
