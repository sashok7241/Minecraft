package net.minecraft.src;


public class MouseHelper
{
	public int deltaX;
	public int deltaY;
	
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
		Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
		Mouse.setGrabbed(false);
	}
}
