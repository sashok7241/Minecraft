package net.minecraft.src;

import java.util.Iterator;
import java.util.Random;

public class BlockBed extends BlockDirectional
{
	public static final int[][] footBlockToHeadBlockMap = new int[][] { { 0, 1 }, { -1, 0 }, { 0, -1 }, { 1, 0 } };
	private Icon[] field_94472_b;
	private Icon[] bedSideIcons;
	private Icon[] bedTopIcons;
	
	public BlockBed(int par1)
	{
		super(par1, Material.cloth);
		setBounds();
	}
	
	@Override public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		if(!isBlockHeadOfBed(par5))
		{
			super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, 0);
		}
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		if(par1 == 0) return Block.planks.getBlockTextureFromSide(par1);
		else
		{
			int var3 = getDirection(par2);
			int var4 = Direction.bedDirection[var3][par1];
			int var5 = isBlockHeadOfBed(par2) ? 1 : 0;
			return (var5 != 1 || var4 != 2) && (var5 != 0 || var4 != 3) ? var4 != 5 && var4 != 4 ? bedTopIcons[var5] : bedSideIcons[var5] : field_94472_b[var5];
		}
	}
	
	@Override public int getMobilityFlag()
	{
		return 1;
	}
	
	@Override public int getRenderType()
	{
		return 14;
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return isBlockHeadOfBed(par1) ? 0 : Item.bed.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.bed.itemID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(par1World.isRemote) return true;
		else
		{
			int var10 = par1World.getBlockMetadata(par2, par3, par4);
			if(!isBlockHeadOfBed(var10))
			{
				int var11 = getDirection(var10);
				par2 += footBlockToHeadBlockMap[var11][0];
				par4 += footBlockToHeadBlockMap[var11][1];
				if(par1World.getBlockId(par2, par3, par4) != blockID) return true;
				var10 = par1World.getBlockMetadata(par2, par3, par4);
			}
			if(par1World.provider.canRespawnHere() && par1World.getBiomeGenForCoords(par2, par4) != BiomeGenBase.hell)
			{
				if(isBedOccupied(var10))
				{
					EntityPlayer var20 = null;
					Iterator var12 = par1World.playerEntities.iterator();
					while(var12.hasNext())
					{
						EntityPlayer var21 = (EntityPlayer) var12.next();
						if(var21.isPlayerSleeping())
						{
							ChunkCoordinates var14 = var21.playerLocation;
							if(var14.posX == par2 && var14.posY == par3 && var14.posZ == par4)
							{
								var20 = var21;
							}
						}
					}
					if(var20 != null)
					{
						par5EntityPlayer.addChatMessage("tile.bed.occupied");
						return true;
					}
					setBedOccupied(par1World, par2, par3, par4, false);
				}
				EnumStatus var19 = par5EntityPlayer.sleepInBedAt(par2, par3, par4);
				if(var19 == EnumStatus.OK)
				{
					setBedOccupied(par1World, par2, par3, par4, true);
					return true;
				} else
				{
					if(var19 == EnumStatus.NOT_POSSIBLE_NOW)
					{
						par5EntityPlayer.addChatMessage("tile.bed.noSleep");
					} else if(var19 == EnumStatus.NOT_SAFE)
					{
						par5EntityPlayer.addChatMessage("tile.bed.notSafe");
					}
					return true;
				}
			} else
			{
				double var18 = par2 + 0.5D;
				double var13 = par3 + 0.5D;
				double var15 = par4 + 0.5D;
				par1World.setBlockToAir(par2, par3, par4);
				int var17 = getDirection(var10);
				par2 += footBlockToHeadBlockMap[var17][0];
				par4 += footBlockToHeadBlockMap[var17][1];
				if(par1World.getBlockId(par2, par3, par4) == blockID)
				{
					par1World.setBlockToAir(par2, par3, par4);
					var18 = (var18 + par2 + 0.5D) / 2.0D;
					var13 = (var13 + par3 + 0.5D) / 2.0D;
					var15 = (var15 + par4 + 0.5D) / 2.0D;
				}
				par1World.newExplosion((Entity) null, par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, 5.0F, true, true);
				return true;
			}
		}
	}
	
	@Override public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
	{
		if(par6EntityPlayer.capabilities.isCreativeMode && isBlockHeadOfBed(par5))
		{
			int var7 = getDirection(par5);
			par2 -= footBlockToHeadBlockMap[var7][0];
			par4 -= footBlockToHeadBlockMap[var7][1];
			if(par1World.getBlockId(par2, par3, par4) == blockID)
			{
				par1World.setBlockToAir(par2, par3, par4);
			}
		}
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4);
		int var7 = getDirection(var6);
		if(isBlockHeadOfBed(var6))
		{
			if(par1World.getBlockId(par2 - footBlockToHeadBlockMap[var7][0], par3, par4 - footBlockToHeadBlockMap[var7][1]) != blockID)
			{
				par1World.setBlockToAir(par2, par3, par4);
			}
		} else if(par1World.getBlockId(par2 + footBlockToHeadBlockMap[var7][0], par3, par4 + footBlockToHeadBlockMap[var7][1]) != blockID)
		{
			par1World.setBlockToAir(par2, par3, par4);
			if(!par1World.isRemote)
			{
				dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
			}
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		bedTopIcons = new Icon[] { par1IconRegister.registerIcon(func_111023_E() + "_feet_top"), par1IconRegister.registerIcon(func_111023_E() + "_head_top") };
		field_94472_b = new Icon[] { par1IconRegister.registerIcon(func_111023_E() + "_feet_end"), par1IconRegister.registerIcon(func_111023_E() + "_head_end") };
		bedSideIcons = new Icon[] { par1IconRegister.registerIcon(func_111023_E() + "_feet_side"), par1IconRegister.registerIcon(func_111023_E() + "_head_side") };
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		setBounds();
	}
	
	private void setBounds()
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
	}
	
	public static ChunkCoordinates getNearestEmptyChunkCoordinates(World par0World, int par1, int par2, int par3, int par4)
	{
		int var5 = par0World.getBlockMetadata(par1, par2, par3);
		int var6 = BlockDirectional.getDirection(var5);
		for(int var7 = 0; var7 <= 1; ++var7)
		{
			int var8 = par1 - footBlockToHeadBlockMap[var6][0] * var7 - 1;
			int var9 = par3 - footBlockToHeadBlockMap[var6][1] * var7 - 1;
			int var10 = var8 + 2;
			int var11 = var9 + 2;
			for(int var12 = var8; var12 <= var10; ++var12)
			{
				for(int var13 = var9; var13 <= var11; ++var13)
				{
					if(par0World.doesBlockHaveSolidTopSurface(var12, par2 - 1, var13) && !par0World.getBlockMaterial(var12, par2, var13).isOpaque() && !par0World.getBlockMaterial(var12, par2 + 1, var13).isOpaque())
					{
						if(par4 <= 0) return new ChunkCoordinates(var12, par2, var13);
						--par4;
					}
				}
			}
		}
		return null;
	}
	
	public static boolean isBedOccupied(int par0)
	{
		return (par0 & 4) != 0;
	}
	
	public static boolean isBlockHeadOfBed(int par0)
	{
		return (par0 & 8) != 0;
	}
	
	public static void setBedOccupied(World par0World, int par1, int par2, int par3, boolean par4)
	{
		int var5 = par0World.getBlockMetadata(par1, par2, par3);
		if(par4)
		{
			var5 |= 4;
		} else
		{
			var5 &= -5;
		}
		par0World.setBlockMetadataWithNotify(par1, par2, par3, var5, 4);
	}
}
