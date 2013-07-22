package net.minecraft.src;

import java.util.Random;

public class EnchantmentThorns extends Enchantment
{
	public EnchantmentThorns(int p_i8015_1_, int p_i8015_2_)
	{
		super(p_i8015_1_, p_i8015_2_, EnumEnchantmentType.armor_torso);
		setName("thorns");
	}
	
	@Override public boolean canApply(ItemStack p_92089_1_)
	{
		return p_92089_1_.getItem() instanceof ItemArmor ? true : super.canApply(p_92089_1_);
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
		return 10 + 20 * (p_77321_1_ - 1);
	}
	
	public static boolean func_92094_a(int p_92094_0_, Random p_92094_1_)
	{
		return p_92094_0_ <= 0 ? false : p_92094_1_.nextFloat() < 0.15F * p_92094_0_;
	}
	
	public static int func_92095_b(int p_92095_0_, Random p_92095_1_)
	{
		return p_92095_0_ > 10 ? p_92095_0_ - 10 : 1 + p_92095_1_.nextInt(4);
	}
	
	public static void func_92096_a(Entity p_92096_0_, EntityLiving p_92096_1_, Random p_92096_2_)
	{
		int var3 = EnchantmentHelper.func_92098_i(p_92096_1_);
		ItemStack var4 = EnchantmentHelper.func_92099_a(Enchantment.thorns, p_92096_1_);
		if(func_92094_a(var3, p_92096_2_))
		{
			p_92096_0_.attackEntityFrom(DamageSource.causeThornsDamage(p_92096_1_), func_92095_b(var3, p_92096_2_));
			p_92096_0_.playSound("damage.thorns", 0.5F, 1.0F);
			if(var4 != null)
			{
				var4.damageItem(3, p_92096_1_);
			}
		} else if(var4 != null)
		{
			var4.damageItem(1, p_92096_1_);
		}
	}
}
