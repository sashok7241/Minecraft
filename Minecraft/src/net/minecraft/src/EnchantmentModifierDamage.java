package net.minecraft.src;

final class EnchantmentModifierDamage implements IEnchantmentModifier
{
	public int damageModifier;
	public DamageSource source;
	
	private EnchantmentModifierDamage()
	{
	}
	
	EnchantmentModifierDamage(Empty3 par1Empty3)
	{
		this();
	}
	
	@Override public void calculateModifier(Enchantment par1Enchantment, int par2)
	{
		damageModifier += par1Enchantment.calcModifierDamage(par2, source);
	}
}
