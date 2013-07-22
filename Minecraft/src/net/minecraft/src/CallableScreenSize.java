package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableScreenSize implements Callable
{
	final ScaledResolution theScaledResolution;
	final EntityRenderer theEntityRenderer;
	
	CallableScreenSize(EntityRenderer par1EntityRenderer, ScaledResolution par2ScaledResolution)
	{
		theEntityRenderer = par1EntityRenderer;
		theScaledResolution = par2ScaledResolution;
	}
	
	@Override public Object call()
	{
		return callScreenSize();
	}
	
	public String callScreenSize()
	{
		return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", new Object[] { Integer.valueOf(theScaledResolution.getScaledWidth()), Integer.valueOf(theScaledResolution.getScaledHeight()), Integer.valueOf(EntityRenderer.getRendererMinecraft(theEntityRenderer).displayWidth), Integer.valueOf(EntityRenderer.getRendererMinecraft(theEntityRenderer).displayHeight), Integer.valueOf(theScaledResolution.getScaleFactor()) });
	}
}
