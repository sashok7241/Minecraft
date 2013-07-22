package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadedFileIOBase implements Runnable
{
	public static final ThreadedFileIOBase threadedIOInstance = new ThreadedFileIOBase();
	private List threadedIOQueue = Collections.synchronizedList(new ArrayList());
	private volatile long writeQueuedCounter = 0L;
	private volatile long savedIOCounter = 0L;
	private volatile boolean isThreadWaiting = false;
	
	private ThreadedFileIOBase()
	{
		Thread var1 = new Thread(this, "File IO Thread");
		var1.setPriority(1);
		var1.start();
	}
	
	private void processQueue()
	{
		for(int var1 = 0; var1 < threadedIOQueue.size(); ++var1)
		{
			IThreadedFileIO var2 = (IThreadedFileIO) threadedIOQueue.get(var1);
			boolean var3 = var2.writeNextIO();
			if(!var3)
			{
				threadedIOQueue.remove(var1--);
				++savedIOCounter;
			}
			try
			{
				Thread.sleep(isThreadWaiting ? 0L : 10L);
			} catch(InterruptedException var6)
			{
				var6.printStackTrace();
			}
		}
		if(threadedIOQueue.isEmpty())
		{
			try
			{
				Thread.sleep(25L);
			} catch(InterruptedException var5)
			{
				var5.printStackTrace();
			}
		}
	}
	
	public void queueIO(IThreadedFileIO par1IThreadedFileIO)
	{
		if(!threadedIOQueue.contains(par1IThreadedFileIO))
		{
			++writeQueuedCounter;
			threadedIOQueue.add(par1IThreadedFileIO);
		}
	}
	
	@Override public void run()
	{
		while(true)
		{
			processQueue();
		}
	}
	
	public void waitForFinish() throws InterruptedException
	{
		isThreadWaiting = true;
		while(writeQueuedCounter != savedIOCounter)
		{
			Thread.sleep(10L);
		}
		isThreadWaiting = false;
	}
}
