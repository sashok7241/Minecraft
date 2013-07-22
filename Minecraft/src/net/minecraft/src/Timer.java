package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class Timer
{
	float ticksPerSecond;
	private double lastHRTime;
	public int elapsedTicks;
	public float renderPartialTicks;
	public float timerSpeed = 1.0F;
	public float elapsedPartialTicks = 0.0F;
	private long lastSyncSysClock;
	private long lastSyncHRClock;
	private long field_74285_i;
	private double timeSyncAdjustment = 1.0D;
	
	public Timer(float p_i3005_1_)
	{
		ticksPerSecond = p_i3005_1_;
		lastSyncSysClock = Minecraft.getSystemTime();
		lastSyncHRClock = System.nanoTime() / 1000000L;
	}
	
	public void updateTimer()
	{
		long var1 = Minecraft.getSystemTime();
		long var3 = var1 - lastSyncSysClock;
		long var5 = System.nanoTime() / 1000000L;
		double var7 = var5 / 1000.0D;
		if(var3 <= 1000L && var3 >= 0L)
		{
			field_74285_i += var3;
			if(field_74285_i > 1000L)
			{
				long var9 = var5 - lastSyncHRClock;
				double var11 = (double) field_74285_i / (double) var9;
				timeSyncAdjustment += (var11 - timeSyncAdjustment) * 0.20000000298023224D;
				lastSyncHRClock = var5;
				field_74285_i = 0L;
			}
			if(field_74285_i < 0L)
			{
				lastSyncHRClock = var5;
			}
		} else
		{
			lastHRTime = var7;
		}
		lastSyncSysClock = var1;
		double var13 = (var7 - lastHRTime) * timeSyncAdjustment;
		lastHRTime = var7;
		if(var13 < 0.0D)
		{
			var13 = 0.0D;
		}
		if(var13 > 1.0D)
		{
			var13 = 1.0D;
		}
		elapsedPartialTicks = (float) (elapsedPartialTicks + var13 * timerSpeed * ticksPerSecond);
		elapsedTicks = (int) elapsedPartialTicks;
		elapsedPartialTicks -= elapsedTicks;
		if(elapsedTicks > 10)
		{
			elapsedTicks = 10;
		}
		renderPartialTicks = elapsedPartialTicks;
	}
}
