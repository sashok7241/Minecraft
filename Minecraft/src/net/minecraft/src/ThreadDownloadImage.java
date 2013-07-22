package net.minecraft.src;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

class ThreadDownloadImage extends Thread
{
	final String location;
	final IImageBuffer buffer;
	final ThreadDownloadImageData imageData;
	
	ThreadDownloadImage(ThreadDownloadImageData p_i5000_1_, String p_i5000_2_, IImageBuffer p_i5000_3_)
	{
		imageData = p_i5000_1_;
		location = p_i5000_2_;
		buffer = p_i5000_3_;
	}
	
	@Override public void run()
	{
		HttpURLConnection var1 = null;
		try
		{
			URL var2 = new URL(location);
			var1 = (HttpURLConnection) var2.openConnection();
			var1.setDoInput(true);
			var1.setDoOutput(false);
			var1.connect();
			if(var1.getResponseCode() / 100 == 4) return;
			if(buffer == null)
			{
				imageData.image = ImageIO.read(var1.getInputStream());
			} else
			{
				imageData.image = buffer.parseUserSkin(ImageIO.read(var1.getInputStream()));
			}
		} catch(Exception var6)
		{
			var6.printStackTrace();
		} finally
		{
			var1.disconnect();
		}
	}
}
