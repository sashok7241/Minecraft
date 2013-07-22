package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BlockTripWire extends Block
{
	public BlockTripWire(int par1)
	{
		super(par1, Material.circuits);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
		setTickRandomly(true);
	}
	
	@Override public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		func_72149_e(par1World, par2, par3, par4, par6 | 1);
	}
	
	private void func_72149_e(World par1World, int par2, int par3, int par4, int par5)
	{
		int var6 = 0;
		while(var6 < 2)
		{
			int var7 = 1;
			while(true)
			{
				if(var7 < 42)
				{
					int var8 = par2 + Direction.offsetX[var6] * var7;
					int var9 = par4 + Direction.offsetZ[var6] * var7;
					int var10 = par1World.getBlockId(var8, par3, var9);
					if(var10 == Block.tripWireSource.blockID)
					{
						int var11 = par1World.getBlockMetadata(var8, par3, var9) & 3;
						if(var11 == Direction.rotateOpposite[var6])
						{
							Block.tripWireSource.func_72143_a(par1World, var8, par3, var9, var10, par1World.getBlockMetadata(var8, par3, var9), true, var7, par5);
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
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
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
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
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
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) ? 0 : 2;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, var5, 3);
		func_72149_e(par1World, par2, par3, par4, var5);
	}
	
	@Override public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
	{
		if(!par1World.isRemote)
		{
			if(par6EntityPlayer.getCurrentEquippedItem() != null && par6EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.itemID)
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, par5 | 8, 4);
			}
		}
	}
	
	@Override public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		if(!par1World.isRemote)
		{
			if((par1World.getBlockMetadata(par2, par3, par4) & 1) != 1)
			{
				updateTripWireState(par1World, par2, par3, par4);
			}
		}
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4);
		boolean var7 = (var6 & 2) == 2;
		boolean var8 = !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
		if(var7 != var8)
		{
			dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
			par1World.setBlockToAir(par2, par3, par4);
		}
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
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
	
	@Override public int tickRate(World par1World)
	{
		return 10;
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(!par1World.isRemote)
		{
			if((par1World.getBlockMetadata(par2, par3, par4) & 1) == 1)
			{
				updateTripWireState(par1World, par2, par3, par4);
			}
		}
	}
	
	private void updateTripWireState(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		boolean var6 = (var5 & 1) == 1;
		boolean var7 = false;
		List var8 = par1World.getEntitiesWithinAABBExcludingEntity((Entity) null, AxisAlignedBB.getAABBPool().getAABB(par2 + minX, par3 + minY, par4 + minZ, par2 + maxX, par3 + maxY, par4 + maxZ));
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
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var5, 3);
			func_72149_e(par1World, par2, par3, par4, var5);
		}
		if(var7)
		{
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
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
