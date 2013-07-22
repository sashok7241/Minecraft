package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class WorldServerMulti extends WorldServer
{
	public WorldServerMulti(MinecraftServer p_i11030_1_, ISaveHandler p_i11030_2_, String p_i11030_3_, int p_i11030_4_, WorldSettings p_i11030_5_, WorldServer p_i11030_6_, Profiler p_i11030_7_, ILogAgent p_i11030_8_)
	{
		super(p_i11030_1_, p_i11030_2_, p_i11030_3_, p_i11030_4_, p_i11030_5_, p_i11030_7_, p_i11030_8_);
		mapStorage = p_i11030_6_.mapStorage;
		worldScoreboard = p_i11030_6_.getScoreboard();
		worldInfo = new DerivedWorldInfo(p_i11030_6_.getWorldInfo());
	}
	
	@Override protected void saveLevel() throws MinecraftException
	{
	}
}
