package net.minecraft.src;

import java.util.Iterator;
import java.util.Random;

public class BlockBed extends BlockDirectional
{
	public static final int[][] footBlockToHeadBlockMap = new int[][] { { 0, 1 }, { -1, 0 }, { 0, -1 }, { 1, 0 } };
	private Icon[] field_94472_b;
	private Icon[] bedSideIcons;
	private Icon[] bedTopIcons;
	
	public BlockBed(int p_i3919_1_)
	{
		super(p_i3919_1_, Material.cloth);
		setBounds();
	}
	
	@Override public void dropBlockAsItemWithChance(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
	{
		if(!isBlockHeadOfBed(p_71914_5_))
		{
			super.dropBlockAsItemWithChance(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, p_71914_5_, p_71914_6_, 0);
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
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return isBlockHeadOfBed(p_71885_1_) ? 0 : Item.bed.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.bed.itemID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		if(p_71903_1_.isRemote) return true;
		else
		{
			int var10 = p_71903_1_.getBlockMetadata(p_71903_2_, p_71903_3_, p_71903_4_);
			if(!isBlockHeadOfBed(var10))
			{
				int var11 = getDirection(var10);
				p_71903_2_ += footBlockToHeadBlockMap[var11][0];
				p_71903_4_ += footBlockToHeadBlockMap[var11][1];
				if(p_71903_1_.getBlockId(p_71903_2_, p_71903_3_, p_71903_4_) != blockID) return true;
				var10 = p_71903_1_.getBlockMetadata(p_71903_2_, p_71903_3_, p_71903_4_);
			}
			if(p_71903_1_.provider.canRespawnHere() && p_71903_1_.getBiomeGenForCoords(p_71903_2_, p_71903_4_) != BiomeGenBase.hell)
			{
				if(isBedOccupied(var10))
				{
					EntityPlayer var20 = null;
					Iterator var12 = p_71903_1_.playerEntities.iterator();
					while(var12.hasNext())
					{
						EntityPlayer var21 = (EntityPlayer) var12.next();
						if(var21.isPlayerSleeping())
						{
							ChunkCoordinates var14 = var21.playerLocation;
							if(var14.posX == p_71903_2_ && var14.posY == p_71903_3_ && var14.posZ == p_71903_4_)
							{
								var20 = var21;
							}
						}
					}
					if(var20 != null)
					{
						p_71903_5_.addChatMessage("tile.bed.occupied");
						return true;
					}
					setBedOccupied(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_, false);
				}
				EnumStatus var19 = p_71903_5_.sleepInBedAt(p_71903_2_, p_71903_3_, p_71903_4_);
				if(var19 == EnumStatus.OK)
				{
					setBedOccupied(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_, true);
					return true;
				} else
				{
					if(var19 == EnumStatus.NOT_POSSIBLE_NOW)
					{
						p_71903_5_.addChatMessage("tile.bed.noSleep");
					} else if(var19 == EnumStatus.NOT_SAFE)
					{
						p_71903_5_.addChatMessage("tile.bed.notSafe");
					}
					return true;
				}
			} else
			{
				double var18 = p_71903_2_ + 0.5D;
				double var13 = p_71903_3_ + 0.5D;
				double var15 = p_71903_4_ + 0.5D;
				p_71903_1_.setBlockToAir(p_71903_2_, p_71903_3_, p_71903_4_);
				int var17 = getDirection(var10);
				p_71903_2_ += footBlockToHeadBlockMap[var17][0];
				p_71903_4_ += footBlockToHeadBlockMap[var17][1];
				if(p_71903_1_.getBlockId(p_71903_2_, p_71903_3_, p_71903_4_) == blockID)
				{
					p_71903_1_.setBlockToAir(p_71903_2_, p_71903_3_, p_71903_4_);
					var18 = (var18 + p_71903_2_ + 0.5D) / 2.0D;
					var13 = (var13 + p_71903_3_ + 0.5D) / 2.0D;
					var15 = (var15 + p_71903_4_ + 0.5D) / 2.0D;
				}
				p_71903_1_.newExplosion((Entity) null, p_71903_2_ + 0.5F, p_71903_3_ + 0.5F, p_71903_4_ + 0.5F, 5.0F, true, true);
				return true;
			}
		}
	}
	
	@Override public void onBlockHarvested(World p_71846_1_, int p_71846_2_, int p_71846_3_, int p_71846_4_, int p_71846_5_, EntityPlayer p_71846_6_)
	{
		if(p_71846_6_.capabilities.isCreativeMode && isBlockHeadOfBed(p_71846_5_))
		{
			int var7 = getDirection(p_71846_5_);
			p_71846_2_ -= footBlockToHeadBlockMap[var7][0];
			p_71846_4_ -= footBlockToHeadBlockMap[var7][1];
			if(p_71846_1_.getBlockId(p_71846_2_, p_71846_3_, p_71846_4_) == blockID)
			{
				p_71846_1_.setBlockToAir(p_71846_2_, p_71846_3_, p_71846_4_);
			}
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		int var6 = p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_);
		int var7 = getDirection(var6);
		if(isBlockHeadOfBed(var6))
		{
			if(p_71863_1_.getBlockId(p_71863_2_ - footBlockToHeadBlockMap[var7][0], p_71863_3_, p_71863_4_ - footBlockToHeadBlockMap[var7][1]) != blockID)
			{
				p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
			}
		} else if(p_71863_1_.getBlockId(p_71863_2_ + footBlockToHeadBlockMap[var7][0], p_71863_3_, p_71863_4_ + footBlockToHeadBlockMap[var7][1]) != blockID)
		{
			p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
			if(!p_71863_1_.isRemote)
			{
				dropBlockAsItem(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, var6, 0);
			}
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		bedTopIcons = new Icon[] { par1IconRegister.registerIcon("bed_feet_top"), par1IconRegister.registerIcon("bed_head_top") };
		field_94472_b = new Icon[] { par1IconRegister.registerIcon("bed_feet_end"), par1IconRegister.registerIcon("bed_head_end") };
		bedSideIcons = new Icon[] { par1IconRegister.registerIcon("bed_feet_side"), par1IconRegister.registerIcon("bed_head_side") };
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		setBounds();
	}
	
	private void setBounds()
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
	}
	
	public static ChunkCoordinates getNearestEmptyChunkCoordinates(World p_72226_0_, int p_72226_1_, int p_72226_2_, int p_72226_3_, int p_72226_4_)
	{
		int var5 = p_72226_0_.getBlockMetadata(p_72226_1_, p_72226_2_, p_72226_3_);
		int var6 = BlockDirectional.getDirection(var5);
		for(int var7 = 0; var7 <= 1; ++var7)
		{
			int var8 = p_72226_1_ - footBlockToHeadBlockMap[var6][0] * var7 - 1;
			int var9 = p_72226_3_ - footBlockToHeadBlockMap[var6][1] * var7 - 1;
			int var10 = var8 + 2;
			int var11 = var9 + 2;
			for(int var12 = var8; var12 <= var10; ++var12)
			{
				for(int var13 = var9; var13 <= var11; ++var13)
				{
					if(p_72226_0_.doesBlockHaveSolidTopSurface(var12, p_72226_2_ - 1, var13) && p_72226_0_.isAirBlock(var12, p_72226_2_, var13) && p_72226_0_.isAirBlock(var12, p_72226_2_ + 1, var13))
					{
						if(p_72226_4_ <= 0) return new ChunkCoordinates(var12, p_72226_2_, var13);
						--p_72226_4_;
					}
				}
			}
		}
		return null;
	}
	
	public static boolean isBedOccupied(int p_72225_0_)
	{
		return (p_72225_0_ & 4) != 0;
	}
	
	public static boolean isBlockHeadOfBed(int p_72229_0_)
	{
		return (p_72229_0_ & 8) != 0;
	}
	
	public static void setBedOccupied(World p_72228_0_, int p_72228_1_, int p_72228_2_, int p_72228_3_, boolean p_72228_4_)
	{
		int var5 = p_72228_0_.getBlockMetadata(p_72228_1_, p_72228_2_, p_72228_3_);
		if(p_72228_4_)
		{
			var5 |= 4;
		} else
		{
			var5 &= -5;
		}
		p_72228_0_.setBlockMetadataWithNotify(p_72228_1_, p_72228_2_, p_72228_3_, var5, 4);
	}
}
