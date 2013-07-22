package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

public class CallableGLInfo implements Callable
{
	final Minecraft mc;
	
	public CallableGLInfo(Minecraft par1Minecraft)
	{
		mc = par1Minecraft;
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
