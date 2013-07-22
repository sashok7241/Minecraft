package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class SimpleResource implements Resource
{
	private final Map field_110535_a = Maps.newHashMap();
	private final ResourceLocation field_110533_b;
	private final InputStream field_110534_c;
	private final InputStream field_110531_d;
	private final MetadataSerializer field_110532_e;
	private boolean field_110529_f;
	private JsonObject field_110530_g;
	
	public SimpleResource(ResourceLocation par1ResourceLocation, InputStream par2InputStream, InputStream par3InputStream, MetadataSerializer par4MetadataSerializer)
	{
		field_110533_b = par1ResourceLocation;
		field_110534_c = par2InputStream;
		field_110531_d = par3InputStream;
		field_110532_e = par4MetadataSerializer;
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		if(this == par1Obj) return true;
		else if(par1Obj instanceof SimpleResource)
		{
			SimpleResource var2 = (SimpleResource) par1Obj;
			return field_110533_b != null ? field_110533_b.equals(var2.field_110533_b) : var2.field_110533_b == null;
		} else return false;
	}
	
	@Override public MetadataSection func_110526_a(String par1Str)
	{
		if(!func_110528_c()) return null;
		else
		{
			if(field_110530_g == null && !field_110529_f)
			{
				field_110529_f = true;
				BufferedReader var2 = null;
				try
				{
					var2 = new BufferedReader(new InputStreamReader(field_110531_d));
					field_110530_g = new JsonParser().parse(var2).getAsJsonObject();
				} finally
				{
					IOUtils.closeQuietly(var2);
				}
			}
			MetadataSection var6 = (MetadataSection) field_110535_a.get(par1Str);
			if(var6 == null)
			{
				var6 = field_110532_e.func_110503_a(par1Str, field_110530_g);
			}
			return var6;
		}
	}
	
	@Override public InputStream func_110527_b()
	{
		return field_110534_c;
	}
	
	@Override public boolean func_110528_c()
	{
		return field_110531_d != null;
	}
	
	@Override public int hashCode()
	{
		return field_110533_b == null ? 0 : field_110533_b.hashCode();
	}
}
