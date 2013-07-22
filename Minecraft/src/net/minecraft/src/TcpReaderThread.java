package net.minecraft.src;

class TcpReaderThread extends Thread
{
	final TcpConnection theTcpConnection;
	
	TcpReaderThread(TcpConnection par1TcpConnection, String par2Str)
	{
		super(par2Str);
		theTcpConnection = par1TcpConnection;
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
