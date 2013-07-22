package net.minecraft.src;

class TcpMasterThread extends Thread
{
	final TcpConnection theTcpConnection;
	
	TcpMasterThread(TcpConnection p_i3285_1_)
	{
		theTcpConnection = p_i3285_1_;
	}
	
	@Override @SuppressWarnings("deprecation") public void run()
	{
		try
		{
			Thread.sleep(5000L);
			if(TcpConnection.getReadThread(theTcpConnection).isAlive())
			{
				try
				{
					TcpConnection.getReadThread(theTcpConnection).stop();
				} catch(Throwable var3)
				{
					;
				}
			}
			if(TcpConnection.getWriteThread(theTcpConnection).isAlive())
			{
				try
				{
					TcpConnection.getWriteThread(theTcpConnection).stop();
				} catch(Throwable var2)
				{
					;
				}
			}
		} catch(InterruptedException var4)
		{
			var4.printStackTrace();
		}
	}
}
