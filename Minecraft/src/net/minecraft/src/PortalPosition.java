package net.minecraft.src;

public class PortalPosition extends ChunkCoordinates
{
	public long lastUpdateTime;
	final Teleporter teleporterInstance;
	
	public PortalPosition(Teleporter par1Teleporter, int par2, int par3, int par4, long par5)
	{
		super(par2, par3, par4);
		teleporterInstance = par1Teleporter;
		lastUpdateTime = par5;
	}
}
