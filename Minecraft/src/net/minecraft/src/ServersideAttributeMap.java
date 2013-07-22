package net.minecraft.src;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ServersideAttributeMap extends BaseAttributeMap
{
	private final Set field_111162_d = Sets.newHashSet();
	protected final Map field_111163_c = new LowerStringMap();
	
	@Override public void func_111149_a(ModifiableAttributeInstance par1ModifiableAttributeInstance)
	{
		if(par1ModifiableAttributeInstance.func_111123_a().func_111111_c())
		{
			field_111162_d.add(par1ModifiableAttributeInstance);
		}
	}
	
	@Override public AttributeInstance func_111150_b(Attribute par1Attribute)
	{
		if(field_111153_b.containsKey(par1Attribute.func_111108_a())) throw new IllegalArgumentException("Attribute is already registered!");
		else
		{
			ModifiableAttributeInstance var2 = new ModifiableAttributeInstance(this, par1Attribute);
			field_111153_b.put(par1Attribute.func_111108_a(), var2);
			if(par1Attribute instanceof RangedAttribute && ((RangedAttribute) par1Attribute).func_111116_f() != null)
			{
				field_111163_c.put(((RangedAttribute) par1Attribute).func_111116_f(), var2);
			}
			field_111154_a.put(par1Attribute, var2);
			return var2;
		}
	}
	
	@Override public AttributeInstance func_111151_a(Attribute par1Attribute)
	{
		return func_111159_c(par1Attribute);
	}
	
	@Override public AttributeInstance func_111152_a(String par1Str)
	{
		return func_111158_b(par1Str);
	}
	
	public ModifiableAttributeInstance func_111158_b(String par1Str)
	{
		AttributeInstance var2 = super.func_111152_a(par1Str);
		if(var2 == null)
		{
			var2 = (AttributeInstance) field_111163_c.get(par1Str);
		}
		return (ModifiableAttributeInstance) var2;
	}
	
	public ModifiableAttributeInstance func_111159_c(Attribute par1Attribute)
	{
		return (ModifiableAttributeInstance) super.func_111151_a(par1Attribute);
	}
	
	public Collection func_111160_c()
	{
		HashSet var1 = Sets.newHashSet();
		Iterator var2 = func_111146_a().iterator();
		while(var2.hasNext())
		{
			AttributeInstance var3 = (AttributeInstance) var2.next();
			if(var3.func_111123_a().func_111111_c())
			{
				var1.add(var3);
			}
		}
		return var1;
	}
	
	public Set func_111161_b()
	{
		return field_111162_d;
	}
}
