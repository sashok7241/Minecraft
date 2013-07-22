package net.minecraft.src;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

import net.minecraft.client.Minecraft;

class McoServerListUpdateTask extends TimerTask
{
	private McoClient field_140066_b;
	final McoServerList field_140067_a;
	
	private McoServerListUpdateTask(McoServerList par1McoServerList)
	{
		field_140067_a = par1McoServerList;
	}
	
	McoServerListUpdateTask(McoServerList par1McoServerList, McoServerListEmptyAnon par2McoServerListEmptyAnon)
	{
		this(par1McoServerList);
	}
	
	private void func_140062_a()
	{
		try
		{
			if(McoServerList.func_100014_a(field_140067_a) != null)
			{
				field_140066_b = new McoClient(McoServerList.func_100014_a(field_140067_a));
				List var1 = field_140066_b.func_96382_a().field_96430_d;
				if(var1 != null)
				{
					func_140065_a(var1);
					McoServerList.func_98247_a(field_140067_a, var1);
				}
			}
		} catch(ExceptionMcoService var2)
		{
			Minecraft.getMinecraft().getLogAgent().logSevere(var2.toString());
		} catch(IOException var3)
		{
			Minecraft.getMinecraft().getLogAgent().logWarning("Realms: could not parse response from server");
		}
	}
	
	private void func_140063_b()
	{
		try
		{
			if(McoServerList.func_100014_a(field_140067_a) != null)
			{
				int var1 = field_140066_b.func_130106_e();
				McoServerList.func_130122_a(field_140067_a, var1);
			}
		} catch(ExceptionMcoService var2)
		{
			Minecraft.getMinecraft().getLogAgent().logSevere(var2.toString());
		}
	}
	
	private void func_140064_c()
	{
		try
		{
			if(McoServerList.func_100014_a(field_140067_a) != null)
			{
				McoClient var1 = new McoClient(McoServerList.func_100014_a(field_140067_a));
				McoServerList.func_140057_b(field_140067_a, var1.func_96379_c());
			}
		} catch(ExceptionMcoService var2)
		{
			Minecraft.getMinecraft().getLogAgent().logSevere(var2.toString());
			McoServerList.func_140057_b(field_140067_a, 0);
		}
	}
	
	private void func_140065_a(List par1List)
	{
		Collections.sort(par1List, new McoServerListUpdateTaskComparator(this, McoServerList.func_100014_a(field_140067_a).func_111285_a(), (McoServerListEmptyAnon) null));
	}
	
	@Override public void run()
	{
		if(!McoServerList.func_98249_b(field_140067_a))
		{
			func_140064_c();
			func_140062_a();
			func_140063_b();
		}
	}
}
