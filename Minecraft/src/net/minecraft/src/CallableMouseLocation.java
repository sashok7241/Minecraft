package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMouseLocation implements Callable
{
	final int field_90026_a;
	final int field_90024_b;
	final EntityRenderer theEntityRenderer;
	
	CallableMouseLocation(EntityRenderer par1EntityRenderer, int par2, int par3)
	{
		theEntityRenderer = par1EntityRenderer;
		field_90026_a = par2;
		field_90024_b = par3;
	}
	
	@Override public Object call()
	{
		return callMouseLocation();
	}
	
	public String callMouseLocation()
	{
		return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", new Object[] { Integer.valueOf(field_90026_a), Integer.valueOf(field_90024_b), Integer.valueOf(Mouse.getX()), Integer.valueOf(Mouse.getY()) });
	}
}
