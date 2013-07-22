package net.minecraft.src;

import java.util.Comparator;

public class PlayerPositionComparator implements Comparator
{
	private final ChunkCoordinates theChunkCoordinates;
	
	public PlayerPositionComparator(ChunkCoordinates p_i5050_1_)
	{
		theChunkCoordinates = p_i5050_1_;
	}
	
	@Override public int compare(Object p_compare_1_, Object p_compare_2_)
	{
		return comparePlayers((EntityPlayerMP) p_compare_1_, (EntityPlayerMP) p_compare_2_);
	}
	
	public int comparePlayers(EntityPlayerMP p_82547_1_, EntityPlayerMP p_82547_2_)
	{
		double var3 = p_82547_1_.getDistanceSq(theChunkCoordinates.posX, theChunkCoordinates.posY, theChunkCoordinates.posZ);
		double var5 = p_82547_2_.getDistanceSq(theChunkCoordinates.posX, theChunkCoordinates.posY, theChunkCoordinates.posZ);
		return var3 < var5 ? -1 : var3 > var5 ? 1 : 0;
	}
}
