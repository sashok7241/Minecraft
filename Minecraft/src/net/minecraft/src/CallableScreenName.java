package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableScreenName implements Callable
{
	final EntityRenderer entityRender;
	
	CallableScreenName(EntityRenderer p_i7006_1_)
	{
		entityRender = p_i7006_1_;
	}
	
	@Override public Object call()
	{
		return callScreenName();
	}
	
	public String callScreenName()
	{
		return EntityRenderer.getRendererMinecraft(entityRender).currentScreen.getClass().getCanonicalName();
	}
}
