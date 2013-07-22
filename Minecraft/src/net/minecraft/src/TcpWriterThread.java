package net.minecraft.src;

import java.io.IOException;

class TcpWriterThread extends Thread
{
	final TcpConnection theTcpConnection;
	
	TcpWriterThread(TcpConnection p_i3284_1_, String p_i3284_2_)
	{
		super(p_i3284_2_);
		theTcpConnection = p_i3284_1_;
	}
	
	@Override public void run()
	{
		TcpConnection.field_74469_b.getAndIncrement();
		try
		{
			while(TcpConnection.isRunning(theTcpConnection))
			{
				boolean var1;
				for(var1 = false; TcpConnection.sendNetworkPacket(theTcpConnection); var1 = true)
				{
					;
				}
				try
				{
					if(var1 && TcpConnection.getOutputStream(theTcpConnection) != null)
					{
						TcpConnection.getOutputStream(theTcpConnection).flush();
					}
				} catch(IOException var8)
				{
					if(!TcpConnection.isTerminating(theTcpConnection))
					{
						TcpConnection.sendError(theTcpConnection, var8);
					}
					var8.printStackTrace();
				}
				try
				{
					sleep(2L);
				} catch(InterruptedException var7)
				{
					;
				}
			}
		} finally
		{
			TcpConnection.field_74469_b.getAndDecrement();
		}
	}
}
