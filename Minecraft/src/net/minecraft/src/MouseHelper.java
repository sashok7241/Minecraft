package net.minecraft.src;

import java.awt.Component;

public class MouseHelper
{
	private final Component windowComponent;
	private final GameSettings field_85184_d;
	public int deltaX;
	public int deltaY;
	
	public MouseHelper(Component par1Component, GameSettings par2GameSettings)
	{
		windowComponent = par1Component;
		field_85184_d = par2GameSettings;
	}
	
	public void grabMouseCursor()
	{
		Mouse.setGrabbed(true);
		deltaX = 0;
		deltaY = 0;
	}
	
	public void mouseXYChange()
	{
		deltaX = Mouse.getDX();
		deltaY = Mouse.getDY();
	}
	
	public void ungrabMouseCursor()
	{
		int var1 = windowComponent.getWidth();
		int var2 = windowComponent.getHeight();
		if(windowComponent.getParent() != null)
		{
			var1 = windowComponent.getParent().getWidth();
			var2 = windowComponent.getParent().getHeight();
		}
		Mouse.setCursorPosition(var1 / 2, var2 / 2);
		Mouse.setGrabbed(false);
	}
}
