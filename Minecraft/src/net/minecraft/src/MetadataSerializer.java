package net.minecraft.src;


public class MetadataSerializer
{
	private final IRegistry field_110508_a = new RegistrySimple();
	private final GsonBuilder field_110506_b = new GsonBuilder();
	private Gson field_110507_c;
	
	public MetadataSection func_110503_a(String par1Str, JsonObject par2JsonObject)
	{
		if(par1Str == null) throw new IllegalArgumentException("Metadata section name cannot be null");
		else if(!par2JsonObject.has(par1Str)) return null;
		else if(!par2JsonObject.get(par1Str).isJsonObject()) throw new IllegalArgumentException("Invalid metadata for \'" + par1Str + "\' - expected object, found " + par2JsonObject.get(par1Str));
		else
		{
			MetadataSerializerRegistration var3 = (MetadataSerializerRegistration) field_110508_a.func_82594_a(par1Str);
			if(var3 == null) throw new IllegalArgumentException("Don\'t know how to handle metadata section \'" + par1Str + "\'");
			else return (MetadataSection) func_110505_a().fromJson(par2JsonObject.getAsJsonObject(par1Str), var3.field_110500_b);
		}
	}
	
	public void func_110504_a(MetadataSectionSerializer par1MetadataSectionSerializer, Class par2Class)
	{
		field_110508_a.putObject(par1MetadataSectionSerializer.func_110483_a(), new MetadataSerializerRegistration(this, par1MetadataSectionSerializer, par2Class, (MetadataSerializerEmptyAnon) null));
		field_110506_b.registerTypeAdapter(par2Class, par1MetadataSectionSerializer);
		field_110507_c = null;
	}
	
	private Gson func_110505_a()
	{
		if(field_110507_c == null)
		{
			field_110507_c = field_110506_b.create();
		}
		return field_110507_c;
	}
}
