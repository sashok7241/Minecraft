package net.minecraft.src;

import java.awt.image.BufferedImage;

public class ThreadDownloadImageData
{
	public BufferedImage image;
	public int referenceCount = 1;
	public int textureName = -1;
	public boolean textureSetupComplete = false;
	
	public ThreadDownloadImageData(String p_i5001_1_, IImageBuffer p_i5001_2_)
	{
		new ThreadDownloadImage(this, p_i5001_1_, p_i5001_2_).start();
	}
}
