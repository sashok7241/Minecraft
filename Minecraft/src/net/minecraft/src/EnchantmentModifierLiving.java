package net.minecraft.src;

final class EnchantmentModifierLiving implements IEnchantmentModifier
{
	public int livingModifier;
	public EntityLiving entityLiving;
	
	private EnchantmentModifierLiving()
	{
	}
	
	EnchantmentModifierLiving(Empty3 p_i3711_1_)
	{
		this();
	}
	
	@Override public void calculateModifier(Enchantment p_77493_1_, int p_77493_2_)
	{
		livingModifier += p_77493_1_.calcModifierLiving(p_77493_2_, entityLiving);
	}
}
