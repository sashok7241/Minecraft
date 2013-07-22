package net.minecraft.src;

final class EnchantmentModifierDamage implements IEnchantmentModifier
{
	public int damageModifier;
	public DamageSource source;
	
	private EnchantmentModifierDamage()
	{
	}
	
	EnchantmentModifierDamage(Empty3 p_i3712_1_)
	{
		this();
	}
	
	@Override public void calculateModifier(Enchantment p_77493_1_, int p_77493_2_)
	{
		damageModifier += p_77493_1_.calcModifierDamage(p_77493_2_, source);
	}
}
