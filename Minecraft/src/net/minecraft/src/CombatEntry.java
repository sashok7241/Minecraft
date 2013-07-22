package net.minecraft.src;

public class CombatEntry
{
	private final DamageSource field_94569_a;
	private final int field_94567_b;
	private final int field_94568_c;
	private final int field_94565_d;
	private final String field_94566_e;
	private final float field_94564_f;
	
	public CombatEntry(DamageSource p_i9019_1_, int p_i9019_2_, int p_i9019_3_, int p_i9019_4_, String p_i9019_5_, float p_i9019_6_)
	{
		field_94569_a = p_i9019_1_;
		field_94567_b = p_i9019_2_;
		field_94568_c = p_i9019_4_;
		field_94565_d = p_i9019_3_;
		field_94566_e = p_i9019_5_;
		field_94564_f = p_i9019_6_;
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
