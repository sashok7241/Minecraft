package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ModifiableAttributeInstance implements AttributeInstance
{
	private final BaseAttributeMap field_111138_a;
	private final Attribute field_111136_b;
	private final Map field_111137_c = Maps.newHashMap();
	private final Map field_111134_d = Maps.newHashMap();
	private final Map field_111135_e = Maps.newHashMap();
	private double field_111132_f;
	private boolean field_111133_g = true;
	private double field_111139_h;
	
	public ModifiableAttributeInstance(BaseAttributeMap par1BaseAttributeMap, Attribute par2Attribute)
	{
		field_111138_a = par1BaseAttributeMap;
		field_111136_b = par2Attribute;
		field_111132_f = par2Attribute.func_111110_b();
		for(int var3 = 0; var3 < 3; ++var3)
		{
			field_111137_c.put(Integer.valueOf(var3), new HashSet());
		}
	}
	
	@Override public void func_111121_a(AttributeModifier par1AttributeModifier)
	{
		if(func_111127_a(par1AttributeModifier.func_111167_a()) != null) throw new IllegalArgumentException("Modifier is already applied on this attribute!");
		else
		{
			Object var2 = field_111134_d.get(par1AttributeModifier.func_111166_b());
			if(var2 == null)
			{
				var2 = new HashSet();
				field_111134_d.put(par1AttributeModifier.func_111166_b(), var2);
			}
			((Set) field_111137_c.get(Integer.valueOf(par1AttributeModifier.func_111169_c()))).add(par1AttributeModifier);
			((Set) var2).add(par1AttributeModifier);
			field_111135_e.put(par1AttributeModifier.func_111167_a(), par1AttributeModifier);
			func_111131_f();
		}
	}
	
	@Override public Collection func_111122_c()
	{
		HashSet var1 = new HashSet();
		for(int var2 = 0; var2 < 3; ++var2)
		{
			var1.addAll(func_111130_a(var2));
		}
		return var1;
	}
	
	@Override public Attribute func_111123_a()
	{
		return field_111136_b;
	}
	
	@Override public void func_111124_b(AttributeModifier par1AttributeModifier)
	{
		for(int var2 = 0; var2 < 3; ++var2)
		{
			Set var3 = (Set) field_111137_c.get(Integer.valueOf(var2));
			var3.remove(par1AttributeModifier);
		}
		Set var4 = (Set) field_111134_d.get(par1AttributeModifier.func_111166_b());
		if(var4 != null)
		{
			var4.remove(par1AttributeModifier);
			if(var4.isEmpty())
			{
				field_111134_d.remove(par1AttributeModifier.func_111166_b());
			}
		}
		field_111135_e.remove(par1AttributeModifier.func_111167_a());
		func_111131_f();
	}
	
	@Override public double func_111125_b()
	{
		return field_111132_f;
	}
	
	@Override public double func_111126_e()
	{
		if(field_111133_g)
		{
			field_111139_h = func_111129_g();
			field_111133_g = false;
		}
		return field_111139_h;
	}
	
	@Override public AttributeModifier func_111127_a(UUID par1UUID)
	{
		return (AttributeModifier) field_111135_e.get(par1UUID);
	}
	
	@Override public void func_111128_a(double par1)
	{
		if(par1 != func_111125_b())
		{
			field_111132_f = par1;
			func_111131_f();
		}
	}
	
	private double func_111129_g()
	{
		double var1 = func_111125_b();
		AttributeModifier var4;
		for(Iterator var3 = func_111130_a(0).iterator(); var3.hasNext(); var1 += var4.func_111164_d())
		{
			var4 = (AttributeModifier) var3.next();
		}
		double var7 = var1;
		Iterator var5;
		AttributeModifier var6;
		for(var5 = func_111130_a(1).iterator(); var5.hasNext(); var7 += var1 * var6.func_111164_d())
		{
			var6 = (AttributeModifier) var5.next();
		}
		for(var5 = func_111130_a(2).iterator(); var5.hasNext(); var7 *= 1.0D + var6.func_111164_d())
		{
			var6 = (AttributeModifier) var5.next();
		}
		return field_111136_b.func_111109_a(var7);
	}
	
	public Collection func_111130_a(int par1)
	{
		return (Collection) field_111137_c.get(Integer.valueOf(par1));
	}
	
	private void func_111131_f()
	{
		field_111133_g = true;
		field_111138_a.func_111149_a(this);
	}
	
	@Override public void func_142049_d()
	{
		Collection var1 = func_111122_c();
		if(var1 != null)
		{
			ArrayList var4 = new ArrayList(var1);
			Iterator var2 = var4.iterator();
			while(var2.hasNext())
			{
				AttributeModifier var3 = (AttributeModifier) var2.next();
				func_111124_b(var3);
			}
		}
	}
}
