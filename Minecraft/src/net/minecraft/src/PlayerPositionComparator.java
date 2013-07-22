package net.minecraft.src;

import java.util.Comparator;

public class PlayerPositionComparator implements Comparator
{
	private final ChunkCoordinates theChunkCoordinates;
	
	public PlayerPositionComparator(ChunkCoordinates par1ChunkCoordinates)
	{
		theChunkCoordinates = par1ChunkCoordinates;
	}
	
	@Override public int compare(Object par1Obj, Object par2Obj)
	{
		return comparePlayers((EntityPlayerMP) par1Obj, (EntityPlayerMP) par2Obj);
	}
	
	public int comparePlayers(EntityPlayerMP par1EntityPlayerMP, EntityPlayerMP par2EntityPlayerMP)
	{
		double var3 = par1EntityPlayerMP.getDistanceSq(theChunkCoordinates.posX, theChunkCoordinates.posY, theChunkCoordinates.posZ);
		double var5 = par2EntityPlayerMP.getDistanceSq(theChunkCoordinates.posX, theChunkCoordinates.posY, theChunkCoordinates.posZ);
		return var3 < var5 ? -1 : var3 > var5 ? 1 : 0;
	}
}
