package net.minecraft.src;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;

public class ImageBufferDownload implements IImageBuffer
{
	private int[] imageData;
	private int imageWidth;
	private int imageHeight;
	
	private boolean hasTransparency(int par1, int par2, int par3, int par4)
	{
		for(int var5 = par1; var5 < par3; ++var5)
		{
			for(int var6 = par2; var6 < par4; ++var6)
			{
				int var7 = imageData[var5 + var6 * imageWidth];
				if((var7 >> 24 & 255) < 128) return true;
			}
		}
		return false;
	}
	
	@Override public BufferedImage parseUserSkin(BufferedImage par1BufferedImage)
	{
		if(par1BufferedImage == null) return null;
		else
		{
			imageWidth = 64;
			imageHeight = 32;
			BufferedImage var2 = new BufferedImage(imageWidth, imageHeight, 2);
			Graphics var3 = var2.getGraphics();
			var3.drawImage(par1BufferedImage, 0, 0, (ImageObserver) null);
			var3.dispose();
			imageData = ((DataBufferInt) var2.getRaster().getDataBuffer()).getData();
			setAreaOpaque(0, 0, 32, 16);
			setAreaTransparent(32, 0, 64, 32);
			setAreaOpaque(0, 16, 64, 32);
			boolean var4 = false;
			int var5;
			int var6;
			int var7;
			for(var5 = 32; var5 < 64; ++var5)
			{
				for(var6 = 0; var6 < 16; ++var6)
				{
					var7 = imageData[var5 + var6 * 64];
					if((var7 >> 24 & 255) < 128)
					{
						var4 = true;
					}
				}
			}
			if(!var4)
			{
				for(var5 = 32; var5 < 64; ++var5)
				{
					for(var6 = 0; var6 < 16; ++var6)
					{
						var7 = imageData[var5 + var6 * 64];
						if((var7 >> 24 & 255) < 128)
						{
							var4 = true;
						}
					}
				}
			}
			return var2;
		}
	}
	
	private void setAreaOpaque(int par1, int par2, int par3, int par4)
	{
		for(int var5 = par1; var5 < par3; ++var5)
		{
			for(int var6 = par2; var6 < par4; ++var6)
			{
				imageData[var5 + var6 * imageWidth] |= -16777216;
			}
		}
	}
	
	private void setAreaTransparent(int par1, int par2, int par3, int par4)
	{
		if(!hasTransparency(par1, par2, par3, par4))
		{
			for(int var5 = par1; var5 < par3; ++var5)
			{
				for(int var6 = par2; var6 < par4; ++var6)
				{
					imageData[var5 + var6 * imageWidth] &= 16777215;
				}
			}
		}
	}
}
