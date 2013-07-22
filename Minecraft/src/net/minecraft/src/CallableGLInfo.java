package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

public class CallableGLInfo implements Callable
{
	final Minecraft mc;
	
	public CallableGLInfo(Minecraft p_i3016_1_)
	{
		mc = p_i3016_1_;
	}
	
	@Override public Object call()
	{
		return getTexturePack();
	}
	
	public String getTexturePack()
	{
		return GL11.glGetString(GL11.GL_RENDERER) + " GL version " + GL11.glGetString(GL11.GL_VERSION) + ", " + GL11.glGetString(GL11.GL_VENDOR);
	}
}
