package net.minecraft.src;

import java.util.Random;

public class BlockComparator extends BlockRedstoneLogic implements ITileEntityProvider
{
	public BlockComparator(int par1, boolean par2)
	{
		super(par1, par2);
		isBlockContainer = true;
	}
	
	@Override public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
		par1World.removeBlockTileEntity(par2, par3, par4);
		func_94483_i_(par1World, par2, par3, par4);
	}
	
	@Override public TileEntity createNewTileEntity(World par1World)
	{
		return new TileEntityComparator();
	}
	
	@Override protected void func_94479_f(World par1World, int par2, int par3, int par4, int par5)
	{
		if(!par1World.isBlockTickScheduledThisTick(par2, par3, par4, blockID))
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			int var7 = getOutputStrength(par1World, par2, par3, par4, var6);
			int var8 = getTileEntityComparator(par1World, par2, par3, par4).getOutputSignal();
			if(var7 != var8 || func_96470_c(var6) != isGettingInput(par1World, par2, par3, par4, var6))
			{
				if(func_83011_d(par1World, par2, par3, par4, var6))
				{
					par1World.scheduleBlockUpdateWithPriority(par2, par3, par4, blockID, func_94481_j_(0), -1);
				} else
				{
					par1World.scheduleBlockUpdateWithPriority(par2, par3, par4, blockID, func_94481_j_(0), 0);
				}
			}
		}
	}
	
	@Override protected int func_94480_d(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return getTileEntityComparator(par1IBlockAccess, par2, par3, par4).getOutputSignal();
	}
	
	@Override protected int func_94481_j_(int par1)
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
	
	public boolean func_94490_c(int par1)
	{
		return (par1 & 4) == 4;
	}
	
	@Override protected boolean func_96470_c(int par1)
	{
		return isRepeaterPowered || (par1 & 8) != 0;
	}
	
	private void func_96476_c(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4);
		int var7 = getOutputStrength(par1World, par2, par3, par4, var6);
		int var8 = getTileEntityComparator(par1World, par2, par3, par4).getOutputSignal();
		getTileEntityComparator(par1World, par2, par3, par4).setOutputSignal(var7);
		if(var8 != var7 || !func_94490_c(var6))
		{
			boolean var9 = isGettingInput(par1World, par2, par3, par4, var6);
			boolean var10 = isRepeaterPowered || (var6 & 8) != 0;
			if(var10 && !var9)
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 & -9, 2);
			} else if(!var10 && var9)
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | 8, 2);
			}
			func_94483_i_(par1World, par2, par3, par4);
		}
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		boolean var3 = isRepeaterPowered || (par2 & 8) != 0;
		return par1 == 0 ? var3 ? Block.torchRedstoneActive.getBlockTextureFromSide(par1) : Block.torchRedstoneIdle.getBlockTextureFromSide(par1) : par1 == 1 ? var3 ? Block.redstoneComparatorActive.blockIcon : blockIcon : Block.stoneDoubleSlab.getBlockTextureFromSide(1);
	}
	
	@Override protected int getInputStrength(World par1World, int par2, int par3, int par4, int par5)
	{
		int var6 = super.getInputStrength(par1World, par2, par3, par4, par5);
		int var7 = getDirection(par5);
		int var8 = par2 + Direction.offsetX[var7];
		int var9 = par4 + Direction.offsetZ[var7];
		int var10 = par1World.getBlockId(var8, par3, var9);
		if(var10 > 0)
		{
			if(Block.blocksList[var10].hasComparatorInputOverride())
			{
				var6 = Block.blocksList[var10].getComparatorInputOverride(par1World, var8, par3, var9, Direction.rotateOpposite[var7]);
			} else if(var6 < 15 && Block.isNormalCube(var10))
			{
				var8 += Direction.offsetX[var7];
				var9 += Direction.offsetZ[var7];
				var10 = par1World.getBlockId(var8, par3, var9);
				if(var10 > 0 && Block.blocksList[var10].hasComparatorInputOverride())
				{
					var6 = Block.blocksList[var10].getComparatorInputOverride(par1World, var8, par3, var9, Direction.rotateOpposite[var7]);
				}
			}
		}
		return var6;
	}
	
	private int getOutputStrength(World par1World, int par2, int par3, int par4, int par5)
	{
		return !func_94490_c(par5) ? getInputStrength(par1World, par2, par3, par4, par5) : Math.max(getInputStrength(par1World, par2, par3, par4, par5) - func_94482_f(par1World, par2, par3, par4, par5), 0);
	}
	
	@Override public int getRenderType()
	{
		return 37;
	}
	
	public TileEntityComparator getTileEntityComparator(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return (TileEntityComparator) par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.comparator.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.comparator.itemID;
	}
	
	@Override protected boolean isGettingInput(World par1World, int par2, int par3, int par4, int par5)
	{
		int var6 = getInputStrength(par1World, par2, par3, par4, par5);
		if(var6 >= 15) return true;
		else if(var6 == 0) return false;
		else
		{
			int var7 = func_94482_f(par1World, par2, par3, par4, par5);
			return var7 == 0 ? true : var6 >= var7;
		}
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		int var10 = par1World.getBlockMetadata(par2, par3, par4);
		boolean var11 = isRepeaterPowered | (var10 & 8) != 0;
		boolean var12 = !func_94490_c(var10);
		int var13 = var12 ? 4 : 0;
		var13 |= var11 ? 8 : 0;
		par1World.playSoundEffect(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, "random.click", 0.3F, var12 ? 0.55F : 0.5F);
		par1World.setBlockMetadataWithNotify(par2, par3, par4, var13 | var10 & 3, 2);
		func_96476_c(par1World, par2, par3, par4, par1World.rand);
		return true;
	}
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);
		par1World.setBlockTileEntity(par2, par3, par4, createNewTileEntity(par1World));
	}
	
	@Override public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
		TileEntity var7 = par1World.getBlockTileEntity(par2, par3, par4);
		return var7 != null ? var7.receiveClientEvent(par5, par6) : false;
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(isRepeaterPowered)
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			par1World.setBlock(par2, par3, par4, func_94484_i().blockID, var6 | 8, 4);
		}
		func_96476_c(par1World, par2, par3, par4, par5Random);
	}
}
