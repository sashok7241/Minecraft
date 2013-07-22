package net.minecraft.src;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

class TimerTaskMcoServerListUpdate extends TimerTask
{
	McoClient field_98262_a;
	final McoServerList field_98261_b;
	
	private TimerTaskMcoServerListUpdate(McoServerList par1)
	{
		field_98261_b = par1;
		field_98262_a = new McoClient(McoServerList.func_100014_a(field_98261_b));
	}
	
	TimerTaskMcoServerListUpdate(McoServerList par1, McoServerListINNER1 par2)
	{
		this(par1);
	}
	
	private void func_101018_a(List par1List)
	{
		Collections.sort(par1List, new TimerTaskMcoServerListUpdateComparator(this, McoServerList.func_100014_a(field_98261_b).username, (McoServerListINNER1) null));
	}
	
	private void func_98260_a()
	{
		try
		{
			List var1 = field_98262_a.func_96382_a().field_96430_d;
			func_101018_a(var1);
			McoServerList.func_98247_a(field_98261_b, var1);
		} catch(ExceptionMcoService var2)
		{
			;
		} catch(IOException var3)
		{
			System.err.println(var3);
		}
	}
	
	@Override public void run()
	{
		if(!McoServerList.func_98249_b(field_98261_b))
		{
			func_98260_a();
		}
	}
}
