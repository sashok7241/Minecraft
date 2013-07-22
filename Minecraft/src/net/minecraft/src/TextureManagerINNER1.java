package net.minecraft.src;

import java.util.concurrent.Callable;

class TextureManagerINNER1 implements Callable
{
	final TextureObject field_135062_a;
	final TextureManager field_135061_b;
	
	TextureManagerINNER1(TextureManager par1TextureManager, TextureObject par2TextureObject)
	{
		field_135061_b = par1TextureManager;
		field_135062_a = par2TextureObject;
	}
	
	@Override public Object call()
	{
		return func_135060_a();
	}
	
	public String func_135060_a()
	{
		return field_135062_a.getClass().getName();
	}
}
