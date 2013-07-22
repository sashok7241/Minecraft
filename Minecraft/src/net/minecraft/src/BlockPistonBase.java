package net.minecraft.src;

import java.util.List;

public class BlockPistonBase extends Block
{
	private final boolean isSticky;
	private Icon innerTopIcon;
	private Icon bottomIcon;
	private Icon topIcon;
	
	public BlockPistonBase(int par1, boolean par2)
	{
		super(par1, Material.piston);
		isSticky = par2;
		setStepSound(soundStoneFootstep);
		setHardness(0.5F);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
	}
	
	public void func_96479_b(float par1, float par2, float par3, float par4, float par5, float par6)
	{
		setBlockBounds(par1, par2, par3, par4, par5, par6);
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		int var3 = getOrientation(par2);
		return var3 > 5 ? topIcon : par1 == var3 ? !isExtended(par2) && minX <= 0.0D && minY <= 0.0D && minZ <= 0.0D && maxX >= 1.0D && maxY >= 1.0D && maxZ >= 1.0D ? topIcon : innerTopIcon : par1 == Facing.oppositeSide[var3] ? bottomIcon : blockIcon;
	}
	
	public Icon getPistonExtensionTexture()
	{
		return topIcon;
	}
	
	@Override public int getRenderType()
	{
		return 16;
	}
	
	private boolean isIndirectlyPowered(World par1World, int par2, int par3, int par4, int par5)
	{
		return par5 != 0 && par1World.getIndirectPowerOutput(par2, par3 - 1, par4, 0) ? true : par5 != 1 && par1World.getIndirectPowerOutput(par2, par3 + 1, par4, 1) ? true : par5 != 2 && par1World.getIndirectPowerOutput(par2, par3, par4 - 1, 2) ? true : par5 != 3 && par1World.getIndirectPowerOutput(par2, par3, par4 + 1, 3) ? true : par5 != 5 && par1World.getIndirectPowerOutput(par2 + 1, par3, par4, 5) ? true : par5 != 4 && par1World.getIndirectPowerOutput(par2 - 1, par3, par4, 4) ? true : par1World.getIndirectPowerOutput(par2, par3, par4, 0) ? true : par1World.getIndirectPowerOutput(par2, par3 + 2, par4, 1) ? true : par1World.getIndirectPowerOutput(par2, par3 + 1, par4 - 1, 2) ? true : par1World.getIndirectPowerOutput(par2, par3 + 1, par4 + 1, 3) ? true : par1World.getIndirectPowerOutput(par2 - 1, par3 + 1, par4, 4) ? true : par1World.getIndirectPowerOutput(par2 + 1, par3 + 1, par4, 5);
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		return false;
	}
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		if(!par1World.isRemote && par1World.getBlockTileEntity(par2, par3, par4) == null)
		{
			updatePistonState(par1World, par2, par3, par4);
		}
	}
	
	@Override public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		if(!par1World.isRemote)
		{
			boolean var7 = isIndirectlyPowered(par1World, par2, par3, par4, par6);
			if(var7 && par5 == 1)
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, par6 | 8, 2);
				return false;
			}
			if(!var7 && par5 == 0) return false;
		}
		if(par5 == 0)
		{
			if(!tryExtend(par1World, par2, par3, par4, par6)) return false;
			par1World.setBlockMetadataWithNotify(par2, par3, par4, par6 | 8, 2);
			par1World.playSoundEffect(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, "tile.piston.out", 0.5F, par1World.rand.nextFloat() * 0.25F + 0.6F);
		} else if(par5 == 1)
		{
			TileEntity var16 = par1World.getBlockTileEntity(par2 + Facing.offsetsXForSide[par6], par3 + Facing.offsetsYForSide[par6], par4 + Facing.offsetsZForSide[par6]);
			if(var16 instanceof TileEntityPiston)
			{
				((TileEntityPiston) var16).clearPistonTileEntity();
			}
			par1World.setBlock(par2, par3, par4, Block.pistonMoving.blockID, par6, 3);
			par1World.setBlockTileEntity(par2, par3, par4, BlockPistonMoving.getTileEntity(blockID, par6, par6, false, true));
			if(isSticky)
			{
				int var8 = par2 + Facing.offsetsXForSide[par6] * 2;
				int var9 = par3 + Facing.offsetsYForSide[par6] * 2;
				int var10 = par4 + Facing.offsetsZForSide[par6] * 2;
				int var11 = par1World.getBlockId(var8, var9, var10);
				int var12 = par1World.getBlockMetadata(var8, var9, var10);
				boolean var13 = false;
				if(var11 == Block.pistonMoving.blockID)
				{
					TileEntity var14 = par1World.getBlockTileEntity(var8, var9, var10);
					if(var14 instanceof TileEntityPiston)
					{
						TileEntityPiston var15 = (TileEntityPiston) var14;
						if(var15.getPistonOrientation() == par6 && var15.isExtending())
						{
							var15.clearPistonTileEntity();
							var11 = var15.getStoredBlockID();
							var12 = var15.getBlockMetadata();
							var13 = true;
						}
					}
				}
				if(!var13 && var11 > 0 && canPushBlock(var11, par1World, var8, var9, var10, false) && (Block.blocksList[var11].getMobilityFlag() == 0 || var11 == Block.pistonBase.blockID || var11 == Block.pistonStickyBase.blockID))
				{
					par2 += Facing.offsetsXForSide[par6];
					par3 += Facing.offsetsYForSide[par6];
					par4 += Facing.offsetsZForSide[par6];
					par1World.setBlock(par2, par3, par4, Block.pistonMoving.blockID, var12, 3);
					par1World.setBlockTileEntity(par2, par3, par4, BlockPistonMoving.getTileEntity(var11, var12, par6, false, false));
					par1World.setBlockToAir(var8, var9, var10);
				} else if(!var13)
				{
					par1World.setBlockToAir(par2 + Facing.offsetsXForSide[par6], par3 + Facing.offsetsYForSide[par6], par4 + Facing.offsetsZForSide[par6]);
				}
			} else
			{
				par1World.setBlockToAir(par2 + Facing.offsetsXForSide[par6], par3 + Facing.offsetsYForSide[par6], par4 + Facing.offsetsZForSide[par6]);
			}
			par1World.playSoundEffect(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, "tile.piston.in", 0.5F, par1World.rand.nextFloat() * 0.15F + 0.6F);
		}
		return true;
	}
	
	@Override public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack)
	{
		int var7 = determineOrientation(par1World, par2, par3, par4, par5EntityLiving);
		par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
		if(!par1World.isRemote)
		{
			updatePistonState(par1World, par2, par3, par4);
		}
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(!par1World.isRemote)
		{
			updatePistonState(par1World, par2, par3, par4);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("piston_side");
		topIcon = par1IconRegister.registerIcon(isSticky ? "piston_top_sticky" : "piston_top");
		innerTopIcon = par1IconRegister.registerIcon("piston_inner_top");
		bottomIcon = par1IconRegister.registerIcon("piston_bottom");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		if(isExtended(var5))
		{
			switch(getOrientation(var5))
			{
				case 0:
					setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
					break;
				case 1:
					setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
					break;
				case 2:
					setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
					break;
				case 3:
					setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
					break;
				case 4:
					setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
					break;
				case 5:
					setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
			}
		} else
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	private boolean tryExtend(World par1World, int par2, int par3, int par4, int par5)
	{
		int var6 = par2 + Facing.offsetsXForSide[par5];
		int var7 = par3 + Facing.offsetsYForSide[par5];
		int var8 = par4 + Facing.offsetsZForSide[par5];
		int var9 = 0;
		while(true)
		{
			int var10;
			if(var9 < 13)
			{
				if(var7 <= 0 || var7 >= 255) return false;
				var10 = par1World.getBlockId(var6, var7, var8);
				if(var10 != 0)
				{
					if(!canPushBlock(var10, par1World, var6, var7, var8, true)) return false;
					if(Block.blocksList[var10].getMobilityFlag() != 1)
					{
						if(var9 == 12) return false;
						var6 += Facing.offsetsXForSide[par5];
						var7 += Facing.offsetsYForSide[par5];
						var8 += Facing.offsetsZForSide[par5];
						++var9;
						continue;
					}
					Block.blocksList[var10].dropBlockAsItem(par1World, var6, var7, var8, par1World.getBlockMetadata(var6, var7, var8), 0);
					par1World.setBlockToAir(var6, var7, var8);
				}
			}
			var9 = var6;
			var10 = var7;
			int var11 = var8;
			int var12 = 0;
			int[] var13;
			int var14;
			int var15;
			int var16;
			for(var13 = new int[13]; var6 != par2 || var7 != par3 || var8 != par4; var8 = var16)
			{
				var14 = var6 - Facing.offsetsXForSide[par5];
				var15 = var7 - Facing.offsetsYForSide[par5];
				var16 = var8 - Facing.offsetsZForSide[par5];
				int var17 = par1World.getBlockId(var14, var15, var16);
				int var18 = par1World.getBlockMetadata(var14, var15, var16);
				if(var17 == blockID && var14 == par2 && var15 == par3 && var16 == par4)
				{
					par1World.setBlock(var6, var7, var8, Block.pistonMoving.blockID, par5 | (isSticky ? 8 : 0), 4);
					par1World.setBlockTileEntity(var6, var7, var8, BlockPistonMoving.getTileEntity(Block.pistonExtension.blockID, par5 | (isSticky ? 8 : 0), par5, true, false));
				} else
				{
					par1World.setBlock(var6, var7, var8, Block.pistonMoving.blockID, var18, 4);
					par1World.setBlockTileEntity(var6, var7, var8, BlockPistonMoving.getTileEntity(var17, var18, par5, true, false));
				}
				var13[var12++] = var17;
				var6 = var14;
				var7 = var15;
			}
			var6 = var9;
			var7 = var10;
			var8 = var11;
			for(var12 = 0; var6 != par2 || var7 != par3 || var8 != par4; var8 = var16)
			{
				var14 = var6 - Facing.offsetsXForSide[par5];
				var15 = var7 - Facing.offsetsYForSide[par5];
				var16 = var8 - Facing.offsetsZForSide[par5];
				par1World.notifyBlocksOfNeighborChange(var14, var15, var16, var13[var12++]);
				var6 = var14;
				var7 = var15;
			}
			return true;
		}
	}
	
	private void updatePistonState(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		int var6 = getOrientation(var5);
		if(var6 != 7)
		{
			boolean var7 = isIndirectlyPowered(par1World, par2, par3, par4, var6);
			if(var7 && !isExtended(var5))
			{
				if(canExtend(par1World, par2, par3, par4, var6))
				{
					par1World.addBlockEvent(par2, par3, par4, blockID, 0, var6);
				}
			} else if(!var7 && isExtended(var5))
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
				par1World.addBlockEvent(par2, par3, par4, blockID, 1, var6);
			}
		}
	}
	
	private static boolean canExtend(World par0World, int par1, int par2, int par3, int par4)
	{
		int var5 = par1 + Facing.offsetsXForSide[par4];
		int var6 = par2 + Facing.offsetsYForSide[par4];
		int var7 = par3 + Facing.offsetsZForSide[par4];
		int var8 = 0;
		while(true)
		{
			if(var8 < 13)
			{
				if(var6 <= 0 || var6 >= 255) return false;
				int var9 = par0World.getBlockId(var5, var6, var7);
				if(var9 != 0)
				{
					if(!canPushBlock(var9, par0World, var5, var6, var7, true)) return false;
					if(Block.blocksList[var9].getMobilityFlag() != 1)
					{
						if(var8 == 12) return false;
						var5 += Facing.offsetsXForSide[par4];
						var6 += Facing.offsetsYForSide[par4];
						var7 += Facing.offsetsZForSide[par4];
						++var8;
						continue;
					}
				}
			}
			return true;
		}
	}
	
	private static boolean canPushBlock(int par0, World par1World, int par2, int par3, int par4, boolean par5)
	{
		if(par0 == Block.obsidian.blockID) return false;
		else
		{
			if(par0 != Block.pistonBase.blockID && par0 != Block.pistonStickyBase.blockID)
			{
				if(Block.blocksList[par0].getBlockHardness(par1World, par2, par3, par4) == -1.0F) return false;
				if(Block.blocksList[par0].getMobilityFlag() == 2) return false;
				if(Block.blocksList[par0].getMobilityFlag() == 1)
				{
					if(!par5) return false;
					return true;
				}
			} else if(isExtended(par1World.getBlockMetadata(par2, par3, par4))) return false;
			return !(Block.blocksList[par0] instanceof ITileEntityProvider);
		}
	}
	
	public static int determineOrientation(World par0World, int par1, int par2, int par3, EntityLiving par4EntityLiving)
	{
		if(MathHelper.abs((float) par4EntityLiving.posX - par1) < 2.0F && MathHelper.abs((float) par4EntityLiving.posZ - par3) < 2.0F)
		{
			double var5 = par4EntityLiving.posY + 1.82D - par4EntityLiving.yOffset;
			if(var5 - par2 > 2.0D) return 1;
			if(par2 - var5 > 0.0D) return 0;
		}
		int var7 = MathHelper.floor_double(par4EntityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		return var7 == 0 ? 2 : var7 == 1 ? 5 : var7 == 2 ? 3 : var7 == 3 ? 4 : 0;
	}
	
	public static Icon func_94496_b(String par0Str)
	{
		return par0Str == "piston_side" ? Block.pistonBase.blockIcon : par0Str == "piston_top" ? Block.pistonBase.topIcon : par0Str == "piston_top_sticky" ? Block.pistonStickyBase.topIcon : par0Str == "piston_inner_top" ? Block.pistonBase.innerTopIcon : null;
	}
	
	public static int getOrientation(int par0)
	{
		return par0 & 7;
	}
	
	public static boolean isExtended(int par0)
	{
		return (par0 & 8) != 0;
	}
}
