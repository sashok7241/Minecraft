package net.minecraft.src;

class DedicatedServerSleepThread extends Thread
{
	final DedicatedServer theDecitatedServer;
	
	DedicatedServerSleepThread(DedicatedServer p_i3379_1_)
	{
		theDecitatedServer = p_i3379_1_;
		setDaemon(true);
		start();
	}
	
	@Override public void run()
	{
		while(true)
		{
			try
			{
				while(true)
				{
					Thread.sleep(2147483647L);
				}
			} catch(InterruptedException var2)
			{
				;
			}
		}
	}
}
