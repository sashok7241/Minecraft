package net.minecraft.src;

import java.awt.Component;

public class MouseHelper
{
	private final Component windowComponent;
	private final GameSettings field_85184_d;
	public int deltaX;
	public int deltaY;
	
	public MouseHelper(Component p_i6800_1_, GameSettings p_i6800_2_)
	{
		windowComponent = p_i6800_1_;
		field_85184_d = p_i6800_2_;
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
