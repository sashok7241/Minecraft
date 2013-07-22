package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class WorldServerMulti extends WorldServer
{
	public WorldServerMulti(MinecraftServer par1MinecraftServer, ISaveHandler par2ISaveHandler, String par3Str, int par4, WorldSettings par5WorldSettings, WorldServer par6WorldServer, Profiler par7Profiler, ILogAgent par8ILogAgent)
	{
		super(par1MinecraftServer, par2ISaveHandler, par3Str, par4, par5WorldSettings, par7Profiler, par8ILogAgent);
		mapStorage = par6WorldServer.mapStorage;
		worldScoreboard = par6WorldServer.getScoreboard();
		worldInfo = new DerivedWorldInfo(par6WorldServer.getWorldInfo());
	}
	
	@Override protected void saveLevel() throws MinecraftException
	{
	}
}
