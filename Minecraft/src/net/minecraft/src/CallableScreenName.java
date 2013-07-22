package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableScreenName implements Callable
{
	final EntityRenderer entityRender;
	
	CallableScreenName(EntityRenderer par1EntityRenderer)
	{
		entityRender = par1EntityRenderer;
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
