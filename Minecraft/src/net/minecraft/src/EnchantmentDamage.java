package net.minecraft.src;

public class EnchantmentDamage extends Enchantment
{
	private static final String[] protectionName = new String[] { "all", "undead", "arthropods" };
	private static final int[] baseEnchantability = new int[] { 1, 5, 5 };
	private static final int[] levelEnchantability = new int[] { 11, 8, 8 };
	private static final int[] thresholdEnchantability = new int[] { 20, 20, 20 };
	public final int damageType;
	
	public EnchantmentDamage(int p_i3706_1_, int p_i3706_2_, int p_i3706_3_)
	{
		super(p_i3706_1_, p_i3706_2_, EnumEnchantmentType.weapon);
		damageType = p_i3706_3_;
	}
	
	@Override public int calcModifierLiving(int p_77323_1_, EntityLiving p_77323_2_)
	{
		return damageType == 0 ? MathHelper.floor_float(p_77323_1_ * 2.75F) : damageType == 1 && p_77323_2_.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD ? MathHelper.floor_float(p_77323_1_ * 4.5F) : damageType == 2 && p_77323_2_.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD ? MathHelper.floor_float(p_77323_1_ * 4.5F) : 0;
	}
	
	@Override public boolean canApply(ItemStack p_92089_1_)
	{
		return p_92089_1_.getItem() instanceof ItemAxe ? true : super.canApply(p_92089_1_);
	}
	
	@Override public boolean canApplyTogether(Enchantment p_77326_1_)
	{
		return !(p_77326_1_ instanceof EnchantmentDamage);
	}
	
	@Override public int getMaxEnchantability(int p_77317_1_)
	{
		return getMinEnchantability(p_77317_1_) + thresholdEnchantability[damageType];
	}
	
	@Override public int getMaxLevel()
	{
		return 5;
	}
	
	@Override public int getMinEnchantability(int p_77321_1_)
	{
		return baseEnchantability[damageType] + (p_77321_1_ - 1) * levelEnchantability[damageType];
	}
	
	@Override public String getName()
	{
		return "enchantment.damage." + protectionName[damageType];
	}
}
