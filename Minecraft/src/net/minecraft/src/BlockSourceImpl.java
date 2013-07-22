package net.minecraft.src;

public class BlockSourceImpl implements IBlockSource
{
	private final World worldObj;
	private final int xPos;
	private final int yPos;
	private final int zPos;
	
	public BlockSourceImpl(World par1World, int par2, int par3, int par4)
	{
		worldObj = par1World;
		xPos = par2;
		yPos = par3;
		zPos = par4;
	}
	
	@Override public int getBlockMetadata()
	{
		return worldObj.getBlockMetadata(xPos, yPos, zPos);
	}
	
	@Override public TileEntity getBlockTileEntity()
	{
		return worldObj.getBlockTileEntity(xPos, yPos, zPos);
	}
	
	@Override public World getWorld()
	{
		return worldObj;
	}
	
	@Override public double getX()
	{
		return xPos + 0.5D;
	}
	
	@Override public int getXInt()
	{
		return xPos;
	}
	
	@Override public double getY()
	{
		return yPos + 0.5D;
	}
	
	@Override public int getYInt()
	{
		return yPos;
	}
	
	@Override public double getZ()
	{
		return zPos + 0.5D;
	}
	
	@Override public int getZInt()
	{
		return zPos;
	}
}
