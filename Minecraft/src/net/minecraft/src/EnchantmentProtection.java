package net.minecraft.src;

public class EnchantmentProtection extends Enchantment
{
	private static final String[] protectionName = new String[] { "all", "fire", "fall", "explosion", "projectile" };
	private static final int[] baseEnchantability = new int[] { 1, 10, 5, 5, 3 };
	private static final int[] levelEnchantability = new int[] { 11, 8, 6, 8, 6 };
	private static final int[] thresholdEnchantability = new int[] { 20, 12, 10, 12, 15 };
	public final int protectionType;
	
	public EnchantmentProtection(int par1, int par2, int par3)
	{
		super(par1, par2, EnumEnchantmentType.armor);
		protectionType = par3;
		if(par3 == 2)
		{
			type = EnumEnchantmentType.armor_feet;
		}
	}
	
	@Override public int calcModifierDamage(int par1, DamageSource par2DamageSource)
	{
		if(par2DamageSource.canHarmInCreative()) return 0;
		else
		{
			float var3 = (6 + par1 * par1) / 3.0F;
			return protectionType == 0 ? MathHelper.floor_float(var3 * 0.75F) : protectionType == 1 && par2DamageSource.isFireDamage() ? MathHelper.floor_float(var3 * 1.25F) : protectionType == 2 && par2DamageSource == DamageSource.fall ? MathHelper.floor_float(var3 * 2.5F) : protectionType == 3 && par2DamageSource.isExplosion() ? MathHelper.floor_float(var3 * 1.5F) : protectionType == 4 && par2DamageSource.isProjectile() ? MathHelper.floor_float(var3 * 1.5F) : 0;
		}
	}
	
	@Override public boolean canApplyTogether(Enchantment par1Enchantment)
	{
		if(par1Enchantment instanceof EnchantmentProtection)
		{
			EnchantmentProtection var2 = (EnchantmentProtection) par1Enchantment;
			return var2.protectionType == protectionType ? false : protectionType == 2 || var2.protectionType == 2;
		} else return super.canApplyTogether(par1Enchantment);
	}
	
	@Override public int getMaxEnchantability(int par1)
	{
		return getMinEnchantability(par1) + thresholdEnchantability[protectionType];
	}
	
	@Override public int getMaxLevel()
	{
		return 4;
	}
	
	@Override public int getMinEnchantability(int par1)
	{
		return baseEnchantability[protectionType] + (par1 - 1) * levelEnchantability[protectionType];
	}
	
	@Override public String getName()
	{
		return "enchantment.protect." + protectionName[protectionType];
	}
	
	public static double func_92092_a(Entity par0Entity, double par1)
	{
		int var3 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.blastProtection.effectId, par0Entity.getLastActiveItems());
		if(var3 > 0)
		{
			par1 -= MathHelper.floor_double(par1 * (var3 * 0.15F));
		}
		return par1;
	}
	
	public static int func_92093_a(Entity par0Entity, int par1)
	{
		int var2 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.fireProtection.effectId, par0Entity.getLastActiveItems());
		if(var2 > 0)
		{
			par1 -= MathHelper.floor_float((float) par1 * (float) var2 * 0.15F);
		}
		return par1;
	}
}
