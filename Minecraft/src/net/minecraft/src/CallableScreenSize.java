package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableScreenSize implements Callable
{
	final ScaledResolution theScaledResolution;
	final EntityRenderer theEntityRenderer;
	
	CallableScreenSize(EntityRenderer p_i7004_1_, ScaledResolution p_i7004_2_)
	{
		theEntityRenderer = p_i7004_1_;
		theScaledResolution = p_i7004_2_;
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
