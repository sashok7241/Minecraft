package net.minecraft.src;

import java.awt.Canvas;
import java.awt.Dimension;

class CanvasCrashReport extends Canvas
{
	private static final long serialVersionUID = 1L;
	
	public CanvasCrashReport(int par1)
	{
		setPreferredSize(new Dimension(par1, par1));
		setMinimumSize(new Dimension(par1, par1));
	}
}
