package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class ThreadClientSleep extends Thread
{
	final Minecraft mc;
	
	public ThreadClientSleep(Minecraft p_i3019_1_, String p_i3019_2_)
	{
		super(p_i3019_2_);
		mc = p_i3019_1_;
	}
	
	@Override public void run()
	{
		while(mc.running)
		{
			try
			{
				Thread.sleep(2147483647L);
			} catch(InterruptedException var2)
			{
				;
			}
		}
	}
}
