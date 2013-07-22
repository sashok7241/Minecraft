package net.minecraft.src;

public class TileEntityDaylightDetector extends TileEntity
{
	@Override public void updateEntity()
	{
		if(worldObj != null && !worldObj.isRemote && worldObj.getTotalWorldTime() % 20L == 0L)
		{
			blockType = getBlockType();
			if(blockType != null && blockType instanceof BlockDaylightDetector)
			{
				((BlockDaylightDetector) blockType).updateLightLevel(worldObj, xCoord, yCoord, zCoord);
			}
		}
	}
}
