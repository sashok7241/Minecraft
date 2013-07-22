package net.minecraft.src;

import java.util.Random;

public class BlockCommandBlock extends BlockContainer
{
	public BlockCommandBlock(int p_i5102_1_)
	{
		super(p_i5102_1_, Material.iron);
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		return new TileEntityCommandBlock();
	}
	
	@Override public int getComparatorInputOverride(World p_94328_1_, int p_94328_2_, int p_94328_3_, int p_94328_4_, int p_94328_5_)
	{
		TileEntity var6 = p_94328_1_.getBlockTileEntity(p_94328_2_, p_94328_3_, p_94328_4_);
		return var6 != null && var6 instanceof TileEntityCommandBlock ? ((TileEntityCommandBlock) var6).func_96103_d() : 0;
	}
	
	@Override public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		TileEntityCommandBlock var10 = (TileEntityCommandBlock) p_71903_1_.getBlockTileEntity(p_71903_2_, p_71903_3_, p_71903_4_);
		if(var10 != null)
		{
			p_71903_5_.displayGUIEditSign(var10);
		}
		return true;
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		TileEntityCommandBlock var7 = (TileEntityCommandBlock) p_71860_1_.getBlockTileEntity(p_71860_2_, p_71860_3_, p_71860_4_);
		if(p_71860_6_.hasDisplayName())
		{
			var7.setCommandSenderName(p_71860_6_.getDisplayName());
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!p_71863_1_.isRemote)
		{
			boolean var6 = p_71863_1_.isBlockIndirectlyGettingPowered(p_71863_2_, p_71863_3_, p_71863_4_);
			int var7 = p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_);
			boolean var8 = (var7 & 1) != 0;
			if(var6 && !var8)
			{
				p_71863_1_.setBlockMetadataWithNotify(p_71863_2_, p_71863_3_, p_71863_4_, var7 | 1, 4);
				p_71863_1_.scheduleBlockUpdate(p_71863_2_, p_71863_3_, p_71863_4_, blockID, tickRate(p_71863_1_));
			} else if(!var6 && var8)
			{
				p_71863_1_.setBlockMetadataWithNotify(p_71863_2_, p_71863_3_, p_71863_4_, var7 & -2, 4);
			}
		}
	}
	
	@Override public int tickRate(World p_71859_1_)
	{
		return 1;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		TileEntity var6 = p_71847_1_.getBlockTileEntity(p_71847_2_, p_71847_3_, p_71847_4_);
		if(var6 != null && var6 instanceof TileEntityCommandBlock)
		{
			TileEntityCommandBlock var7 = (TileEntityCommandBlock) var6;
			var7.func_96102_a(var7.executeCommandOnPowered(p_71847_1_));
			p_71847_1_.func_96440_m(p_71847_2_, p_71847_3_, p_71847_4_, blockID);
		}
	}
}
