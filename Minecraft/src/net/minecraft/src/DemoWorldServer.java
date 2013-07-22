package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class DemoWorldServer extends WorldServer
{
	private static final long demoWorldSeed = "North Carolina".hashCode();
	public static final WorldSettings demoWorldSettings = new WorldSettings(demoWorldSeed, EnumGameType.SURVIVAL, true, false, WorldType.DEFAULT).enableBonusChest();
	
	public DemoWorldServer(MinecraftServer p_i11029_1_, ISaveHandler p_i11029_2_, String p_i11029_3_, int p_i11029_4_, Profiler p_i11029_5_, ILogAgent p_i11029_6_)
	{
		super(p_i11029_1_, p_i11029_2_, p_i11029_3_, p_i11029_4_, demoWorldSettings, p_i11029_5_, p_i11029_6_);
	}
}
