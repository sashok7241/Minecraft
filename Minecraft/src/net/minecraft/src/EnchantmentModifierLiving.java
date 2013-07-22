package net.minecraft.src;

final class EnchantmentModifierLiving implements IEnchantmentModifier
{
	public int livingModifier;
	public EntityLiving entityLiving;
	
	private EnchantmentModifierLiving()
	{
	}
	
	EnchantmentModifierLiving(Empty3 par1Empty3)
	{
		this();
	}
	
	@Override public void calculateModifier(Enchantment par1Enchantment, int par2)
	{
		livingModifier += par1Enchantment.calcModifierLiving(par2, entityLiving);
	}
}
