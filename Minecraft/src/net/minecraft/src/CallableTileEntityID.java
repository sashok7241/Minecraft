package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableTileEntityID implements Callable
{
	final TileEntity theTileEntity;
	
	CallableTileEntityID(TileEntity par1TileEntity)
	{
		theTileEntity = par1TileEntity;
	}
	
	@Override public Object call()
	{
		return callTileEntityID();
	}
	
	public String callTileEntityID()
	{
		int var1 = theTileEntity.worldObj.getBlockId(theTileEntity.xCoord, theTileEntity.yCoord, theTileEntity.zCoord);
		try
		{
			return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(var1), Block.blocksList[var1].getUnlocalizedName(), Block.blocksList[var1].getClass().getCanonicalName() });
		} catch(Throwable var3)
		{
			return "ID #" + var1;
		}
	}
}
