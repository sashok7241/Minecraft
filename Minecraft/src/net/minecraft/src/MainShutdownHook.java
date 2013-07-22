package net.minecraft.src;

import net.minecraft.client.Minecraft;

public final class MainShutdownHook extends Thread
{
	@Override public void run()
	{
		Minecraft.stopIntegratedServer();
	}
}
