package net.minecraft.src;

import java.awt.image.BufferedImage;

public class ThreadDownloadImageData
{
	public BufferedImage image;
	public int referenceCount = 1;
	public int textureName = -1;
	public boolean textureSetupComplete = false;
	
	public ThreadDownloadImageData(String par1, IImageBuffer par2IImageBuffer)
	{
		new ThreadDownloadImage(this, par1, par2IImageBuffer).start();
	}
}
