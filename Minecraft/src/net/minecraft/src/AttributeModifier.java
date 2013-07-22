package net.minecraft.src;

import java.util.UUID;

public class AttributeModifier
{
	private final double field_111174_a;
	private final int field_111172_b;
	private final String field_111173_c;
	private final UUID field_111170_d;
	private boolean field_111171_e;
	
	public AttributeModifier(String par1Str, double par2, int par4)
	{
		this(UUID.randomUUID(), par1Str, par2, par4);
	}
	
	public AttributeModifier(UUID par1UUID, String par2Str, double par3, int par5)
	{
		field_111171_e = true;
		field_111170_d = par1UUID;
		field_111173_c = par2Str;
		field_111174_a = par3;
		field_111172_b = par5;
		Validate.notEmpty(par2Str, "Modifier name cannot be empty", new Object[0]);
		Validate.inclusiveBetween(Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(par5), "Invalid operation", new Object[0]);
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		if(this == par1Obj) return true;
		else if(par1Obj != null && this.getClass() == par1Obj.getClass())
		{
			AttributeModifier var2 = (AttributeModifier) par1Obj;
			if(field_111170_d != null)
			{
				if(!field_111170_d.equals(var2.field_111170_d)) return false;
			} else if(var2.field_111170_d != null) return false;
			return true;
		} else return false;
	}
	
	public double func_111164_d()
	{
		return field_111174_a;
	}
	
	public boolean func_111165_e()
	{
		return field_111171_e;
	}
	
	public String func_111166_b()
	{
		return field_111173_c;
	}
	
	public UUID func_111167_a()
	{
		return field_111170_d;
	}
	
	public AttributeModifier func_111168_a(boolean par1)
	{
		field_111171_e = par1;
		return this;
	}
	
	public int func_111169_c()
	{
		return field_111172_b;
	}
	
	@Override public int hashCode()
	{
		return field_111170_d != null ? field_111170_d.hashCode() : 0;
	}
	
	@Override public String toString()
	{
		return "AttributeModifier{amount=" + field_111174_a + ", operation=" + field_111172_b + ", name=\'" + field_111173_c + '\'' + ", id=" + field_111170_d + ", serialize=" + field_111171_e + '}';
	}
}
