package net.minecraft.src;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ResourcePackRepository
{
	protected static final FileFilter field_110622_a = new ResourcePackRepositoryFilter();
	private final File field_110618_d;
	public final ResourcePack field_110620_b;
	public final MetadataSerializer field_110621_c;
	private List field_110619_e = Lists.newArrayList();
	private List field_110617_f = Lists.newArrayList();
	
	public ResourcePackRepository(File par1File, ResourcePack par2ResourcePack, MetadataSerializer par3MetadataSerializer, GameSettings par4GameSettings)
	{
		field_110618_d = par1File;
		field_110620_b = par2ResourcePack;
		field_110621_c = par3MetadataSerializer;
		func_110616_f();
		func_110611_a();
		Iterator var5 = field_110619_e.iterator();
		while(var5.hasNext())
		{
			ResourcePackRepositoryEntry var6 = (ResourcePackRepositoryEntry) var5.next();
			if(var6.func_110515_d().equals(par4GameSettings.skin))
			{
				field_110617_f.add(var6);
			}
		}
	}
	
	public List func_110609_b()
	{
		return ImmutableList.copyOf(field_110619_e);
	}
	
	public String func_110610_d()
	{
		return field_110617_f.isEmpty() ? "Default" : ((ResourcePackRepositoryEntry) field_110617_f.get(0)).func_110515_d();
	}
	
	public void func_110611_a()
	{
		ArrayList var1 = Lists.newArrayList();
		Iterator var2 = func_110614_g().iterator();
		while(var2.hasNext())
		{
			File var3 = (File) var2.next();
			ResourcePackRepositoryEntry var4 = new ResourcePackRepositoryEntry(this, var3, (ResourcePackRepositoryFilter) null);
			if(!field_110619_e.contains(var4))
			{
				try
				{
					var4.func_110516_a();
					var1.add(var4);
				} catch(Exception var6)
				{
					var1.remove(var4);
				}
			} else
			{
				var1.add(field_110619_e.get(field_110619_e.indexOf(var4)));
			}
		}
		field_110619_e.removeAll(var1);
		var2 = field_110619_e.iterator();
		while(var2.hasNext())
		{
			ResourcePackRepositoryEntry var7 = (ResourcePackRepositoryEntry) var2.next();
			var7.func_110517_b();
		}
		field_110619_e = var1;
	}
	
	public File func_110612_e()
	{
		return field_110618_d;
	}
	
	public List func_110613_c()
	{
		return ImmutableList.copyOf(field_110617_f);
	}
	
	private List func_110614_g()
	{
		return field_110618_d.isDirectory() ? Arrays.asList(field_110618_d.listFiles(field_110622_a)) : Collections.emptyList();
	}
	
	public void func_110615_a(ResourcePackRepositoryEntry ... par1ArrayOfResourcePackRepositoryEntry)
	{
		field_110617_f.clear();
		Collections.addAll(field_110617_f, par1ArrayOfResourcePackRepositoryEntry);
	}
	
	private void func_110616_f()
	{
		if(!field_110618_d.isDirectory())
		{
			field_110618_d.delete();
			field_110618_d.mkdirs();
		}
	}
}
