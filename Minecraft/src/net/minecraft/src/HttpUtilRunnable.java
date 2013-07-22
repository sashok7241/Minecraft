package net.minecraft.src;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

final class HttpUtilRunnable implements Runnable
{
	final IProgressUpdate feedbackHook;
	final String sourceURL;
	final Map field_76177_c;
	final File destinationFile;
	final IDownloadSuccess downloadSuccess;
	final int maxFileSize;
	
	HttpUtilRunnable(IProgressUpdate p_i3418_1_, String p_i3418_2_, Map p_i3418_3_, File p_i3418_4_, IDownloadSuccess p_i3418_5_, int p_i3418_6_)
	{
		feedbackHook = p_i3418_1_;
		sourceURL = p_i3418_2_;
		field_76177_c = p_i3418_3_;
		destinationFile = p_i3418_4_;
		downloadSuccess = p_i3418_5_;
		maxFileSize = p_i3418_6_;
	}
	
	@Override public void run()
	{
		URLConnection var1 = null;
		InputStream var2 = null;
		DataOutputStream var3 = null;
		if(feedbackHook != null)
		{
			feedbackHook.resetProgressAndMessage("Downloading Texture Pack");
			feedbackHook.resetProgresAndWorkingMessage("Making Request...");
		}
		try
		{
			byte[] var4 = new byte[4096];
			URL var5 = new URL(sourceURL);
			var1 = var5.openConnection();
			float var6 = 0.0F;
			float var7 = field_76177_c.entrySet().size();
			Iterator var8 = field_76177_c.entrySet().iterator();
			while(var8.hasNext())
			{
				Entry var9 = (Entry) var8.next();
				var1.setRequestProperty((String) var9.getKey(), (String) var9.getValue());
				if(feedbackHook != null)
				{
					feedbackHook.setLoadingProgress((int) (++var6 / var7 * 100.0F));
				}
			}
			var2 = var1.getInputStream();
			var7 = var1.getContentLength();
			int var28 = var1.getContentLength();
			if(feedbackHook != null)
			{
				feedbackHook.resetProgresAndWorkingMessage(String.format("Downloading file (%.2f MB)...", new Object[] { Float.valueOf(var7 / 1000.0F / 1000.0F) }));
			}
			if(destinationFile.exists())
			{
				long var29 = destinationFile.length();
				if(var29 == var28)
				{
					downloadSuccess.onSuccess(destinationFile);
					if(feedbackHook != null)
					{
						feedbackHook.onNoMoreProgress();
					}
					return;
				}
				System.out.println("Deleting " + destinationFile + " as it does not match what we currently have (" + var28 + " vs our " + var29 + ").");
				destinationFile.delete();
			}
			var3 = new DataOutputStream(new FileOutputStream(destinationFile));
			if(maxFileSize > 0 && var7 > maxFileSize)
			{
				if(feedbackHook != null)
				{
					feedbackHook.onNoMoreProgress();
				}
				throw new IOException("Filesize is bigger than maximum allowed (file is " + var6 + ", limit is " + maxFileSize + ")");
			}
			boolean var31 = false;
			int var30;
			while((var30 = var2.read(var4)) >= 0)
			{
				var6 += var30;
				if(feedbackHook != null)
				{
					feedbackHook.setLoadingProgress((int) (var6 / var7 * 100.0F));
				}
				if(maxFileSize > 0 && var6 > maxFileSize)
				{
					if(feedbackHook != null)
					{
						feedbackHook.onNoMoreProgress();
					}
					throw new IOException("Filesize was bigger than maximum allowed (got >= " + var6 + ", limit was " + maxFileSize + ")");
				}
				var3.write(var4, 0, var30);
			}
			downloadSuccess.onSuccess(destinationFile);
			if(feedbackHook != null)
			{
				feedbackHook.onNoMoreProgress();
			}
		} catch(Throwable var26)
		{
			var26.printStackTrace();
		} finally
		{
			try
			{
				if(var2 != null)
				{
					var2.close();
				}
			} catch(IOException var25)
			{
				;
			}
			try
			{
				if(var3 != null)
				{
					var3.close();
				}
			} catch(IOException var24)
			{
				;
			}
		}
	}
}
