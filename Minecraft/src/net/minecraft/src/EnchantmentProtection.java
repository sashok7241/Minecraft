package net.minecraft.src;

public class EnchantmentProtection extends Enchantment
{
	private static final String[] protectionName = new String[] { "all", "fire", "fall", "explosion", "projectile" };
	private static final int[] baseEnchantability = new int[] { 1, 10, 5, 5, 3 };
	private static final int[] levelEnchantability = new int[] { 11, 8, 6, 8, 6 };
	private static final int[] thresholdEnchantability = new int[] { 20, 12, 10, 12, 15 };
	public final int protectionType;
	
	public EnchantmentProtection(int p_i3718_1_, int p_i3718_2_, int p_i3718_3_)
	{
		super(p_i3718_1_, p_i3718_2_, EnumEnchantmentType.armor);
		protectionType = p_i3718_3_;
		if(p_i3718_3_ == 2)
		{
			type = EnumEnchantmentType.armor_feet;
		}
	}
	
	@Override public int calcModifierDamage(int p_77318_1_, DamageSource p_77318_2_)
	{
		if(p_77318_2_.canHarmInCreative()) return 0;
		else
		{
			float var3 = (6 + p_77318_1_ * p_77318_1_) / 3.0F;
			return protectionType == 0 ? MathHelper.floor_float(var3 * 0.75F) : protectionType == 1 && p_77318_2_.isFireDamage() ? MathHelper.floor_float(var3 * 1.25F) : protectionType == 2 && p_77318_2_ == DamageSource.fall ? MathHelper.floor_float(var3 * 2.5F) : protectionType == 3 && p_77318_2_.isExplosion() ? MathHelper.floor_float(var3 * 1.5F) : protectionType == 4 && p_77318_2_.isProjectile() ? MathHelper.floor_float(var3 * 1.5F) : 0;
		}
	}
	
	@Override public boolean canApplyTogether(Enchantment p_77326_1_)
	{
		if(p_77326_1_ instanceof EnchantmentProtection)
		{
			EnchantmentProtection var2 = (EnchantmentProtection) p_77326_1_;
			return var2.protectionType == protectionType ? false : protectionType == 2 || var2.protectionType == 2;
		} else return super.canApplyTogether(p_77326_1_);
	}
	
	@Override public int getMaxEnchantability(int p_77317_1_)
	{
		return getMinEnchantability(p_77317_1_) + thresholdEnchantability[protectionType];
	}
	
	@Override public int getMaxLevel()
	{
		return 4;
	}
	
	@Override public int getMinEnchantability(int p_77321_1_)
	{
		return baseEnchantability[protectionType] + (p_77321_1_ - 1) * levelEnchantability[protectionType];
	}
	
	@Override public String getName()
	{
		return "enchantment.protect." + protectionName[protectionType];
	}
	
	public static double func_92092_a(Entity p_92092_0_, double p_92092_1_)
	{
		int var3 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.blastProtection.effectId, p_92092_0_.getLastActiveItems());
		if(var3 > 0)
		{
			p_92092_1_ -= MathHelper.floor_double(p_92092_1_ * (var3 * 0.15F));
		}
		return p_92092_1_;
	}
	
	public static int func_92093_a(Entity p_92093_0_, int p_92093_1_)
	{
		int var2 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.fireProtection.effectId, p_92093_0_.getLastActiveItems());
		if(var2 > 0)
		{
			p_92093_1_ -= MathHelper.floor_float((float) p_92093_1_ * (float) var2 * 0.15F);
		}
		return p_92093_1_;
	}
}
