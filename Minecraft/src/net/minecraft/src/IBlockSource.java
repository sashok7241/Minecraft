package net.minecraft.src;

public interface IBlockSource extends ILocatableSource
{
	int getBlockMetadata();
	
	TileEntity getBlockTileEntity();
	
	@Override double getX();
	
	int getXInt();
	
	@Override double getY();
	
	int getYInt();
	
	@Override double getZ();
	
	int getZInt();
}
