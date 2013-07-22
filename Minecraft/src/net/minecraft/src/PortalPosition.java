package net.minecraft.src;

public class PortalPosition extends ChunkCoordinates
{
	public long lastUpdateTime;
	final Teleporter teleporterInstance;
	
	public PortalPosition(Teleporter p_i6815_1_, int p_i6815_2_, int p_i6815_3_, int p_i6815_4_, long p_i6815_5_)
	{
		super(p_i6815_2_, p_i6815_3_, p_i6815_4_);
		teleporterInstance = p_i6815_1_;
		lastUpdateTime = p_i6815_5_;
	}
}
