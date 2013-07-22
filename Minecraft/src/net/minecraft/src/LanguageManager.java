package net.minecraft.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import net.minecraft.client.Minecraft;

public class LanguageManager implements ResourceManagerReloadListener
{
	private final MetadataSerializer field_135047_b;
	private String field_135048_c;
	protected static final Locale field_135049_a = new Locale();
	private Map field_135046_d = Maps.newHashMap();
	
	public LanguageManager(MetadataSerializer par1MetadataSerializer, String par2Str)
	{
		field_135047_b = par1MetadataSerializer;
		field_135048_c = par2Str;
		I18n.func_135051_a(field_135049_a);
	}
	
	@Override public void func_110549_a(ResourceManager par1ResourceManager)
	{
		ArrayList var2 = Lists.newArrayList(new String[] { "en_US" });
		if(!"en_US".equals(field_135048_c))
		{
			var2.add(field_135048_c);
		}
		field_135049_a.func_135022_a(par1ResourceManager, var2);
		StringTranslate.func_135063_a(field_135049_a.field_135032_a);
	}
	
	public SortedSet func_135040_d()
	{
		return Sets.newTreeSet(field_135046_d.values());
	}
	
	public Language func_135041_c()
	{
		return field_135046_d.containsKey(field_135048_c) ? (Language) field_135046_d.get(field_135048_c) : (Language) field_135046_d.get("en_US");
	}
	
	public boolean func_135042_a()
	{
		return field_135049_a.func_135025_a();
	}
	
	public void func_135043_a(List par1List)
	{
		field_135046_d.clear();
		Iterator var2 = par1List.iterator();
		while(var2.hasNext())
		{
			ResourcePack var3 = (ResourcePack) var2.next();
			try
			{
				LanguageMetadataSection var4 = (LanguageMetadataSection) var3.func_135058_a(field_135047_b, "language");
				if(var4 != null)
				{
					Iterator var5 = var4.func_135018_a().iterator();
					while(var5.hasNext())
					{
						Language var6 = (Language) var5.next();
						if(!field_135046_d.containsKey(var6.func_135034_a()))
						{
							field_135046_d.put(var6.func_135034_a(), var6);
						}
					}
				}
			} catch(RuntimeException var7)
			{
				Minecraft.getMinecraft().getLogAgent().logWarningException("Unable to parse metadata section of resourcepack: " + var3.func_130077_b(), var7);
			} catch(IOException var8)
			{
				Minecraft.getMinecraft().getLogAgent().logWarningException("Unable to parse metadata section of resourcepack: " + var3.func_130077_b(), var8);
			}
		}
	}
	
	public boolean func_135044_b()
	{
		return func_135041_c().func_135035_b();
	}
	
	public void func_135045_a(Language par1Language)
	{
		field_135048_c = par1Language.func_135034_a();
	}
}
