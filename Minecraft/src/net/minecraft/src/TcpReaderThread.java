package net.minecraft.src;

class TcpReaderThread extends Thread
{
	final TcpConnection theTcpConnection;
	
	TcpReaderThread(TcpConnection p_i3283_1_, String p_i3283_2_)
	{
		super(p_i3283_2_);
		theTcpConnection = p_i3283_1_;
	}
	
	@Override public void run()
	{
		TcpConnection.field_74471_a.getAndIncrement();
		try
		{
			while(TcpConnection.isRunning(theTcpConnection) && !TcpConnection.isServerTerminating(theTcpConnection))
			{
				while(true)
				{
					if(!TcpConnection.readNetworkPacket(theTcpConnection))
					{
						try
						{
							sleep(2L);
						} catch(InterruptedException var5)
						{
							;
						}
					}
				}
			}
		} finally
		{
			TcpConnection.field_74471_a.getAndDecrement();
		}
	}
}
