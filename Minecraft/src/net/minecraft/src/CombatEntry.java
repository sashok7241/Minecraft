package net.minecraft.src;

public class CombatEntry
{
	private final DamageSource field_94569_a;
	private final int field_94567_b;
	private final int field_94568_c;
	private final int field_94565_d;
	private final String field_94566_e;
	private final float field_94564_f;
	
	public CombatEntry(DamageSource par1DamageSource, int par2, int par3, int par4, String par5Str, float par6)
	{
		field_94569_a = par1DamageSource;
		field_94567_b = par2;
		field_94568_c = par4;
		field_94565_d = par3;
		field_94566_e = par5Str;
		field_94564_f = par6;
	}
	
	public String func_94558_h()
	{
		return func_94560_a().getEntity() == null ? null : func_94560_a().getEntity().getTranslatedEntityName();
	}
	
	public boolean func_94559_f()
	{
		return field_94569_a.getEntity() instanceof EntityLiving;
	}
	
	public DamageSource func_94560_a()
	{
		return field_94569_a;
	}
	
	public float func_94561_i()
	{
		return field_94569_a == DamageSource.outOfWorld ? Float.MAX_VALUE : field_94564_f;
	}
	
	public String func_94562_g()
	{
		return field_94566_e;
	}
	
	public int func_94563_c()
	{
		return field_94568_c;
	}
}
