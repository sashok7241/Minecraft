package net.minecraft.src;


public class ResourceLocation
{
	private final String field_110626_a;
	private final String field_110625_b;
	
	public ResourceLocation(String par1Str)
	{
		String var2 = "minecraft";
		String var3 = par1Str;
		int var4 = par1Str.indexOf(58);
		if(var4 >= 0)
		{
			var3 = par1Str.substring(var4 + 1, par1Str.length());
			if(var4 > 1)
			{
				var2 = par1Str.substring(0, var4);
			}
		}
		field_110626_a = var2.toLowerCase();
		field_110625_b = var3;
	}
	
	public ResourceLocation(String par1Str, String par2Str)
	{
		Validate.notNull(par2Str);
		if(par1Str != null && par1Str.length() != 0)
		{
			field_110626_a = par1Str;
		} else
		{
			field_110626_a = "minecraft";
		}
		field_110625_b = par2Str;
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		if(this == par1Obj) return true;
		else if(!(par1Obj instanceof ResourceLocation)) return false;
		else
		{
			ResourceLocation var2 = (ResourceLocation) par1Obj;
			return field_110626_a.equals(var2.field_110626_a) && field_110625_b.equals(var2.field_110625_b);
		}
	}
	
	public String func_110623_a()
	{
		return field_110625_b;
	}
	
	public String func_110624_b()
	{
		return field_110626_a;
	}
	
	@Override public int hashCode()
	{
		return 31 * field_110626_a.hashCode() + field_110625_b.hashCode();
	}
	
	@Override public String toString()
	{
		return field_110626_a + ":" + field_110625_b;
	}
}
