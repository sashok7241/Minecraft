package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BlockTripWire extends Block
{
	public BlockTripWire(int p_i4018_1_)
	{
		super(p_i4018_1_, Material.circuits);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
		setTickRandomly(true);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		func_72149_e(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_6_ | 1);
	}
	
	private void func_72149_e(World p_72149_1_, int p_72149_2_, int p_72149_3_, int p_72149_4_, int p_72149_5_)
	{
		int var6 = 0;
		while(var6 < 2)
		{
			int var7 = 1;
			while(true)
			{
				if(var7 < 42)
				{
					int var8 = p_72149_2_ + Direction.offsetX[var6] * var7;
					int var9 = p_72149_4_ + Direction.offsetZ[var6] * var7;
					int var10 = p_72149_1_.getBlockId(var8, p_72149_3_, var9);
					if(var10 == Block.tripWireSource.blockID)
					{
						int var11 = p_72149_1_.getBlockMetadata(var8, p_72149_3_, var9) & 3;
						if(var11 == Direction.rotateOpposite[var6])
						{
							Block.tripWireSource.func_72143_a(p_72149_1_, var8, p_72149_3_, var9, var10, p_72149_1_.getBlockMetadata(var8, p_72149_3_, var9), true, var7, p_72149_5_);
						}
					} else if(var10 == Block.tripWire.blockID)
					{
						++var7;
						continue;
					}
				}
				++var6;
				break;
			}
		}
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		return null;
	}
	
	@Override public int getRenderBlockPass()
	{
		return 1;
	}
	
	@Override public int getRenderType()
	{
		return 30;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.silk.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.silk.itemID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		int var5 = p_71861_1_.doesBlockHaveSolidTopSurface(p_71861_2_, p_71861_3_ - 1, p_71861_4_) ? 0 : 2;
		p_71861_1_.setBlockMetadataWithNotify(p_71861_2_, p_71861_3_, p_71861_4_, var5, 3);
		func_72149_e(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_, var5);
	}
	
	@Override public void onBlockHarvested(World p_71846_1_, int p_71846_2_, int p_71846_3_, int p_71846_4_, int p_71846_5_, EntityPlayer p_71846_6_)
	{
		if(!p_71846_1_.isRemote)
		{
			if(p_71846_6_.getCurrentEquippedItem() != null && p_71846_6_.getCurrentEquippedItem().itemID == Item.shears.itemID)
			{
				p_71846_1_.setBlockMetadataWithNotify(p_71846_2_, p_71846_3_, p_71846_4_, p_71846_5_ | 8, 4);
			}
		}
	}
	
	@Override public void onEntityCollidedWithBlock(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_)
	{
		if(!p_71869_1_.isRemote)
		{
			if((p_71869_1_.getBlockMetadata(p_71869_2_, p_71869_3_, p_71869_4_) & 1) != 1)
			{
				updateTripWireState(p_71869_1_, p_71869_2_, p_71869_3_, p_71869_4_);
			}
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		int var6 = p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_);
		boolean var7 = (var6 & 2) == 2;
		boolean var8 = !p_71863_1_.doesBlockHaveSolidTopSurface(p_71863_2_, p_71863_3_ - 1, p_71863_4_);
		if(var7 != var8)
		{
			dropBlockAsItem(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, var6, 0);
			p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
		}
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		int var5 = p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_);
		boolean var6 = (var5 & 4) == 4;
		boolean var7 = (var5 & 2) == 2;
		if(!var7)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
		} else if(!var6)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		} else
		{
			setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
		}
	}
	
	@Override public int tickRate(World p_71859_1_)
	{
		return 10;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(!p_71847_1_.isRemote)
		{
			if((p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_) & 1) == 1)
			{
				updateTripWireState(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
			}
		}
	}
	
	private void updateTripWireState(World p_72147_1_, int p_72147_2_, int p_72147_3_, int p_72147_4_)
	{
		int var5 = p_72147_1_.getBlockMetadata(p_72147_2_, p_72147_3_, p_72147_4_);
		boolean var6 = (var5 & 1) == 1;
		boolean var7 = false;
		List var8 = p_72147_1_.getEntitiesWithinAABBExcludingEntity((Entity) null, AxisAlignedBB.getAABBPool().getAABB(p_72147_2_ + minX, p_72147_3_ + minY, p_72147_4_ + minZ, p_72147_2_ + maxX, p_72147_3_ + maxY, p_72147_4_ + maxZ));
		if(!var8.isEmpty())
		{
			Iterator var9 = var8.iterator();
			while(var9.hasNext())
			{
				Entity var10 = (Entity) var9.next();
				if(!var10.doesEntityNotTriggerPressurePlate())
				{
					var7 = true;
					break;
				}
			}
		}
		if(var7 && !var6)
		{
			var5 |= 1;
		}
		if(!var7 && var6)
		{
			var5 &= -2;
		}
		if(var7 != var6)
		{
			p_72147_1_.setBlockMetadataWithNotify(p_72147_2_, p_72147_3_, p_72147_4_, var5, 3);
			func_72149_e(p_72147_1_, p_72147_2_, p_72147_3_, p_72147_4_, var5);
		}
		if(var7)
		{
			p_72147_1_.scheduleBlockUpdate(p_72147_2_, p_72147_3_, p_72147_4_, blockID, tickRate(p_72147_1_));
		}
	}
	
	public static boolean func_72148_a(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, int par4, int par5)
	{
		int var6 = par1 + Direction.offsetX[par5];
		int var8 = par3 + Direction.offsetZ[par5];
		int var9 = par0IBlockAccess.getBlockId(var6, par2, var8);
		boolean var10 = (par4 & 2) == 2;
		int var11;
		if(var9 == Block.tripWireSource.blockID)
		{
			var11 = par0IBlockAccess.getBlockMetadata(var6, par2, var8);
			int var13 = var11 & 3;
			return var13 == Direction.rotateOpposite[par5];
		} else if(var9 == Block.tripWire.blockID)
		{
			var11 = par0IBlockAccess.getBlockMetadata(var6, par2, var8);
			boolean var12 = (var11 & 2) == 2;
			return var10 == var12;
		} else return false;
	}
}
