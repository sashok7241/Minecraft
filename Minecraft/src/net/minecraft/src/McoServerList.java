package net.minecraft.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class McoServerList
{
	private volatile boolean field_98259_a = false;
	private TimerTaskMcoServerListUpdate field_98257_b;
	private java.util.Timer field_98258_c = new java.util.Timer();
	private List field_98255_d = new ArrayList();
	private boolean field_98256_e = false;
	private Session field_98254_f;
	
	public McoServerList(Session par1Session)
	{
		field_98254_f = par1Session;
		field_98257_b = new TimerTaskMcoServerListUpdate(this, (McoServerListINNER1) null);
		field_98258_c.schedule(field_98257_b, 0L, 10000L);
	}
	
	private synchronized void func_96426_a(List par1List)
	{
		if(!par1List.equals(field_98255_d))
		{
			field_98255_d = par1List;
			field_98256_e = true;
		}
	}
	
	public synchronized void func_98248_d()
	{
		field_98259_a = true;
		field_98257_b.cancel();
		field_98258_c.cancel();
	}
	
	public synchronized void func_98250_b()
	{
		field_98256_e = false;
	}
	
	public synchronized boolean func_98251_a()
	{
		return field_98256_e;
	}
	
	public synchronized List func_98252_c()
	{
		return Collections.unmodifiableList(field_98255_d);
	}
	
	static Session func_100014_a(McoServerList par0McoServerList)
	{
		return par0McoServerList.field_98254_f;
	}
	
	static void func_98247_a(McoServerList par0McoServerList, List par1List) throws IOException
	{
		par0McoServerList.func_96426_a(par1List);
	}
	
	static boolean func_98249_b(McoServerList par0McoServerList)
	{
		return par0McoServerList.field_98259_a;
	}
}
