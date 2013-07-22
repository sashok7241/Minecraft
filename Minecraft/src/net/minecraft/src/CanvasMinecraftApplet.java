package net.minecraft.src;

import java.awt.Canvas;

import net.minecraft.client.MinecraftApplet;

public class CanvasMinecraftApplet extends Canvas
{
	private static final long serialVersionUID = 1L;
	final MinecraftApplet mcApplet;
	
	public CanvasMinecraftApplet(MinecraftApplet p_i3020_1_)
	{
		mcApplet = p_i3020_1_;
	}
	
	@Override public synchronized void addNotify()
	{
		super.addNotify();
		mcApplet.startMainThread();
	}
	
	@Override public synchronized void removeNotify()
	{
		mcApplet.shutdown();
		super.removeNotify();
	}
}
