package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableTileEntityName implements Callable
{
	final TileEntity theTileEntity;
	
	CallableTileEntityName(TileEntity par1TileEntity)
	{
		theTileEntity = par1TileEntity;
	}
	
	@Override public Object call()
	{
		return callTileEntityName();
	}
	
	public String callTileEntityName()
	{
		return (String) TileEntity.getClassToNameMap().get(theTileEntity.getClass()) + " // " + theTileEntity.getClass().getCanonicalName();
	}
}
