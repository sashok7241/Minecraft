package net.minecraft.src;

public abstract class BlockContainer extends Block implements ITileEntityProvider
{
	protected BlockContainer(int p_i10059_1_, Material p_i10059_2_)
	{
		super(p_i10059_1_, p_i10059_2_);
		isBlockContainer = true;
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		super.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
		p_71852_1_.removeBlockTileEntity(p_71852_2_, p_71852_3_, p_71852_4_);
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		super.onBlockAdded(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
	}
	
	@Override public boolean onBlockEventReceived(World p_71883_1_, int p_71883_2_, int p_71883_3_, int p_71883_4_, int p_71883_5_, int p_71883_6_)
	{
		super.onBlockEventReceived(p_71883_1_, p_71883_2_, p_71883_3_, p_71883_4_, p_71883_5_, p_71883_6_);
		TileEntity var7 = p_71883_1_.getBlockTileEntity(p_71883_2_, p_71883_3_, p_71883_4_);
		return var7 != null ? var7.receiveClientEvent(p_71883_5_, p_71883_6_) : false;
	}
}
