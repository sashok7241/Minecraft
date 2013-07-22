package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;

public class McoServerList
{
	private volatile boolean field_98259_a;
	private McoServerListUpdateTask field_98257_b = new McoServerListUpdateTask(this, (McoServerListEmptyAnon) null);
	private java.util.Timer field_98258_c = new java.util.Timer();
	private Set field_140060_d = Sets.newHashSet();
	private List field_98255_d = Lists.newArrayList();
	private int field_130130_e;
	private boolean field_140059_g;
	private Session field_98254_f;
	private int field_140061_i;
	
	public McoServerList()
	{
		field_98258_c.schedule(field_98257_b, 0L, 10000L);
		field_98254_f = Minecraft.getMinecraft().func_110432_I();
	}
	
	private void func_130123_a(int par1)
	{
		field_130130_e = par1;
	}
	
	public int func_130124_d()
	{
		return field_130130_e;
	}
	
	public synchronized boolean func_130127_a()
	{
		return field_140059_g;
	}
	
	public synchronized void func_130129_a(Session par1Session)
	{
		field_98254_f = par1Session;
		if(field_98259_a)
		{
			field_98259_a = false;
			field_98257_b = new McoServerListUpdateTask(this, (McoServerListEmptyAnon) null);
			field_98258_c = new java.util.Timer();
			field_98258_c.schedule(field_98257_b, 0L, 10000L);
		}
	}
	
	public int func_140056_e()
	{
		return field_140061_i;
	}
	
	public synchronized void func_140058_a(McoServer par1McoServer)
	{
		field_98255_d.remove(par1McoServer);
		field_140060_d.add(par1McoServer);
	}
	
	private synchronized void func_96426_a(List par1List)
	{
		int var2 = 0;
		Iterator var3 = field_140060_d.iterator();
		while(var3.hasNext())
		{
			McoServer var4 = (McoServer) var3.next();
			if(par1List.remove(var4))
			{
				++var2;
			}
		}
		if(var2 == 0)
		{
			field_140060_d.clear();
		}
		field_98255_d = par1List;
		field_140059_g = true;
	}
	
	public synchronized void func_98248_d()
	{
		field_98259_a = true;
		field_98257_b.cancel();
		field_98258_c.cancel();
	}
	
	public synchronized void func_98250_b()
	{
		field_140059_g = false;
	}
	
	public synchronized List func_98252_c()
	{
		return Lists.newArrayList(field_98255_d);
	}
	
	static Session func_100014_a(McoServerList par0McoServerList)
	{
		return par0McoServerList.field_98254_f;
	}
	
	static void func_130122_a(McoServerList par0McoServerList, int par1)
	{
		par0McoServerList.func_130123_a(par1);
	}
	
	static int func_140057_b(McoServerList par0McoServerList, int par1)
	{
		return par0McoServerList.field_140061_i = par1;
	}
	
	static void func_98247_a(McoServerList par0McoServerList, List par1List)
	{
		par0McoServerList.func_96426_a(par1List);
	}
	
	static boolean func_98249_b(McoServerList par0McoServerList)
	{
		return par0McoServerList.field_98259_a;
	}
}
