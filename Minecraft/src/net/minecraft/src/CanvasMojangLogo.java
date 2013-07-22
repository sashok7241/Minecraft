package net.minecraft.src;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;

class CanvasMojangLogo extends Canvas
{
	private static final long serialVersionUID = 1L;
	private BufferedImage logo;
	
	public CanvasMojangLogo()
	{
		try
		{
			logo = ImageIO.read(PanelCrashReport.class.getResource("/gui/crash_logo.png"));
		} catch(IOException var2)
		{
			;
		}
		byte var1 = 100;
		setPreferredSize(new Dimension(var1, var1));
		setMinimumSize(new Dimension(var1, var1));
	}
	
	@Override public void paint(Graphics par1Graphics)
	{
		super.paint(par1Graphics);
		par1Graphics.drawImage(logo, getWidth() / 2 - logo.getWidth() / 2, 32, (ImageObserver) null);
	}
}
