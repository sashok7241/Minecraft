package net.minecraft.src;

public abstract class BlockContainer extends Block implements ITileEntityProvider
{
	protected BlockContainer(int par1, Material par2Material)
	{
		super(par1, par2Material);
		isBlockContainer = true;
	}
	
	@Override public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
		par1World.removeBlockTileEntity(par2, par3, par4);
	}
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);
	}
	
	@Override public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
		TileEntity var7 = par1World.getBlockTileEntity(par2, par3, par4);
		return var7 != null ? var7.receiveClientEvent(par5, par6) : false;
	}
}
