package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMouseLocation implements Callable
{
	final int field_90026_a;
	final int field_90024_b;
	final EntityRenderer theEntityRenderer;
	
	CallableMouseLocation(EntityRenderer p_i7005_1_, int p_i7005_2_, int p_i7005_3_)
	{
		theEntityRenderer = p_i7005_1_;
		field_90026_a = p_i7005_2_;
		field_90024_b = p_i7005_3_;
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
