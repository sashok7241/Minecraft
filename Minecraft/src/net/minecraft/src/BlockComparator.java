package net.minecraft.src;

import java.util.Random;

public class BlockComparator extends BlockRedstoneLogic implements ITileEntityProvider
{
	public BlockComparator(int p_i9047_1_, boolean p_i9047_2_)
	{
		super(p_i9047_1_, p_i9047_2_);
		isBlockContainer = true;
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		super.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
		p_71852_1_.removeBlockTileEntity(p_71852_2_, p_71852_3_, p_71852_4_);
		func_94483_i_(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_);
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		return new TileEntityComparator();
	}
	
	@Override protected void func_94479_f(World p_94479_1_, int p_94479_2_, int p_94479_3_, int p_94479_4_, int p_94479_5_)
	{
		if(!p_94479_1_.isBlockTickScheduledThisTick(p_94479_2_, p_94479_3_, p_94479_4_, blockID))
		{
			int var6 = p_94479_1_.getBlockMetadata(p_94479_2_, p_94479_3_, p_94479_4_);
			int var7 = getOutputStrength(p_94479_1_, p_94479_2_, p_94479_3_, p_94479_4_, var6);
			int var8 = getTileEntityComparator(p_94479_1_, p_94479_2_, p_94479_3_, p_94479_4_).getOutputSignal();
			if(var7 != var8 || func_96470_c(var6) != isGettingInput(p_94479_1_, p_94479_2_, p_94479_3_, p_94479_4_, var6))
			{
				if(func_83011_d(p_94479_1_, p_94479_2_, p_94479_3_, p_94479_4_, var6))
				{
					p_94479_1_.scheduleBlockUpdateWithPriority(p_94479_2_, p_94479_3_, p_94479_4_, blockID, func_94481_j_(0), -1);
				} else
				{
					p_94479_1_.scheduleBlockUpdateWithPriority(p_94479_2_, p_94479_3_, p_94479_4_, blockID, func_94481_j_(0), 0);
				}
			}
		}
	}
	
	@Override protected int func_94480_d(IBlockAccess p_94480_1_, int p_94480_2_, int p_94480_3_, int p_94480_4_, int p_94480_5_)
	{
		return getTileEntityComparator(p_94480_1_, p_94480_2_, p_94480_3_, p_94480_4_).getOutputSignal();
	}
	
	@Override protected int func_94481_j_(int p_94481_1_)
	{
		return 2;
	}
	
	@Override protected BlockRedstoneLogic func_94484_i()
	{
		return Block.redstoneComparatorIdle;
	}
	
	@Override protected BlockRedstoneLogic func_94485_e()
	{
		return Block.redstoneComparatorActive;
	}
	
	public boolean func_94490_c(int p_94490_1_)
	{
		return (p_94490_1_ & 4) == 4;
	}
	
	@Override protected boolean func_96470_c(int p_96470_1_)
	{
		return isRepeaterPowered || (p_96470_1_ & 8) != 0;
	}
	
	private void func_96476_c(World p_96476_1_, int p_96476_2_, int p_96476_3_, int p_96476_4_, Random p_96476_5_)
	{
		int var6 = p_96476_1_.getBlockMetadata(p_96476_2_, p_96476_3_, p_96476_4_);
		int var7 = getOutputStrength(p_96476_1_, p_96476_2_, p_96476_3_, p_96476_4_, var6);
		int var8 = getTileEntityComparator(p_96476_1_, p_96476_2_, p_96476_3_, p_96476_4_).getOutputSignal();
		getTileEntityComparator(p_96476_1_, p_96476_2_, p_96476_3_, p_96476_4_).setOutputSignal(var7);
		if(var8 != var7 || !func_94490_c(var6))
		{
			boolean var9 = isGettingInput(p_96476_1_, p_96476_2_, p_96476_3_, p_96476_4_, var6);
			boolean var10 = isRepeaterPowered || (var6 & 8) != 0;
			if(var10 && !var9)
			{
				p_96476_1_.setBlockMetadataWithNotify(p_96476_2_, p_96476_3_, p_96476_4_, var6 & -9, 2);
			} else if(!var10 && var9)
			{
				p_96476_1_.setBlockMetadataWithNotify(p_96476_2_, p_96476_3_, p_96476_4_, var6 | 8, 2);
			}
			func_94483_i_(p_96476_1_, p_96476_2_, p_96476_3_, p_96476_4_);
		}
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		boolean var3 = isRepeaterPowered || (par2 & 8) != 0;
		return par1 == 0 ? var3 ? Block.torchRedstoneActive.getBlockTextureFromSide(par1) : Block.torchRedstoneIdle.getBlockTextureFromSide(par1) : par1 == 1 ? var3 ? Block.redstoneComparatorActive.blockIcon : blockIcon : Block.stoneDoubleSlab.getBlockTextureFromSide(1);
	}
	
	@Override protected int getInputStrength(World p_72220_1_, int p_72220_2_, int p_72220_3_, int p_72220_4_, int p_72220_5_)
	{
		int var6 = super.getInputStrength(p_72220_1_, p_72220_2_, p_72220_3_, p_72220_4_, p_72220_5_);
		int var7 = getDirection(p_72220_5_);
		int var8 = p_72220_2_ + Direction.offsetX[var7];
		int var9 = p_72220_4_ + Direction.offsetZ[var7];
		int var10 = p_72220_1_.getBlockId(var8, p_72220_3_, var9);
		if(var10 > 0)
		{
			if(Block.blocksList[var10].hasComparatorInputOverride())
			{
				var6 = Block.blocksList[var10].getComparatorInputOverride(p_72220_1_, var8, p_72220_3_, var9, Direction.rotateOpposite[var7]);
			} else if(var6 < 15 && Block.isNormalCube(var10))
			{
				var8 += Direction.offsetX[var7];
				var9 += Direction.offsetZ[var7];
				var10 = p_72220_1_.getBlockId(var8, p_72220_3_, var9);
				if(var10 > 0 && Block.blocksList[var10].hasComparatorInputOverride())
				{
					var6 = Block.blocksList[var10].getComparatorInputOverride(p_72220_1_, var8, p_72220_3_, var9, Direction.rotateOpposite[var7]);
				}
			}
		}
		return var6;
	}
	
	private int getOutputStrength(World p_94491_1_, int p_94491_2_, int p_94491_3_, int p_94491_4_, int p_94491_5_)
	{
		return !func_94490_c(p_94491_5_) ? getInputStrength(p_94491_1_, p_94491_2_, p_94491_3_, p_94491_4_, p_94491_5_) : Math.max(getInputStrength(p_94491_1_, p_94491_2_, p_94491_3_, p_94491_4_, p_94491_5_) - func_94482_f(p_94491_1_, p_94491_2_, p_94491_3_, p_94491_4_, p_94491_5_), 0);
	}
	
	@Override public int getRenderType()
	{
		return 37;
	}
	
	public TileEntityComparator getTileEntityComparator(IBlockAccess p_96475_1_, int p_96475_2_, int p_96475_3_, int p_96475_4_)
	{
		return (TileEntityComparator) p_96475_1_.getBlockTileEntity(p_96475_2_, p_96475_3_, p_96475_4_);
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.comparator.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.comparator.itemID;
	}
	
	@Override protected boolean isGettingInput(World p_94478_1_, int p_94478_2_, int p_94478_3_, int p_94478_4_, int p_94478_5_)
	{
		int var6 = getInputStrength(p_94478_1_, p_94478_2_, p_94478_3_, p_94478_4_, p_94478_5_);
		if(var6 >= 15) return true;
		else if(var6 == 0) return false;
		else
		{
			int var7 = func_94482_f(p_94478_1_, p_94478_2_, p_94478_3_, p_94478_4_, p_94478_5_);
			return var7 == 0 ? true : var6 >= var7;
		}
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		int var10 = p_71903_1_.getBlockMetadata(p_71903_2_, p_71903_3_, p_71903_4_);
		boolean var11 = isRepeaterPowered | (var10 & 8) != 0;
		boolean var12 = !func_94490_c(var10);
		int var13 = var12 ? 4 : 0;
		var13 |= var11 ? 8 : 0;
		p_71903_1_.playSoundEffect(p_71903_2_ + 0.5D, p_71903_3_ + 0.5D, p_71903_4_ + 0.5D, "random.click", 0.3F, var12 ? 0.55F : 0.5F);
		p_71903_1_.setBlockMetadataWithNotify(p_71903_2_, p_71903_3_, p_71903_4_, var13 | var10 & 3, 2);
		func_96476_c(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_, p_71903_1_.rand);
		return true;
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		super.onBlockAdded(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
		p_71861_1_.setBlockTileEntity(p_71861_2_, p_71861_3_, p_71861_4_, createNewTileEntity(p_71861_1_));
	}
	
	@Override public boolean onBlockEventReceived(World p_71883_1_, int p_71883_2_, int p_71883_3_, int p_71883_4_, int p_71883_5_, int p_71883_6_)
	{
		super.onBlockEventReceived(p_71883_1_, p_71883_2_, p_71883_3_, p_71883_4_, p_71883_5_, p_71883_6_);
		TileEntity var7 = p_71883_1_.getBlockTileEntity(p_71883_2_, p_71883_3_, p_71883_4_);
		return var7 != null ? var7.receiveClientEvent(p_71883_5_, p_71883_6_) : false;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(isRepeaterPowered ? "comparator_lit" : "comparator");
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(isRepeaterPowered)
		{
			int var6 = p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_);
			p_71847_1_.setBlock(p_71847_2_, p_71847_3_, p_71847_4_, func_94484_i().blockID, var6 | 8, 4);
		}
		func_96476_c(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_5_);
	}
}
