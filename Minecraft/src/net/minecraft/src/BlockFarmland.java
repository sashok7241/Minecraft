package net.minecraft.src;

import java.util.Random;

public class BlockFarmland extends Block
{
	private Icon field_94441_a;
	private Icon field_94440_b;
	
	protected BlockFarmland(int p_i3945_1_)
	{
		super(p_i3945_1_, Material.ground);
		setTickRandomly(true);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
		setLightOpacity(255);
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		return AxisAlignedBB.getAABBPool().getAABB(p_71872_2_ + 0, p_71872_3_ + 0, p_71872_4_ + 0, p_71872_2_ + 1, p_71872_3_ + 1, p_71872_4_ + 1);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? par2 > 0 ? field_94441_a : field_94440_b : Block.dirt.getBlockTextureFromSide(par1);
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Block.dirt.idDropped(0, p_71885_2_, p_71885_3_);
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Block.dirt.blockID;
	}
	
	private boolean isCropsNearby(World p_72248_1_, int p_72248_2_, int p_72248_3_, int p_72248_4_)
	{
		byte var5 = 0;
		for(int var6 = p_72248_2_ - var5; var6 <= p_72248_2_ + var5; ++var6)
		{
			for(int var7 = p_72248_4_ - var5; var7 <= p_72248_4_ + var5; ++var7)
			{
				int var8 = p_72248_1_.getBlockId(var6, p_72248_3_ + 1, var7);
				if(var8 == Block.crops.blockID || var8 == Block.melonStem.blockID || var8 == Block.pumpkinStem.blockID || var8 == Block.potato.blockID || var8 == Block.carrot.blockID) return true;
			}
		}
		return false;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	private boolean isWaterNearby(World p_72247_1_, int p_72247_2_, int p_72247_3_, int p_72247_4_)
	{
		for(int var5 = p_72247_2_ - 4; var5 <= p_72247_2_ + 4; ++var5)
		{
			for(int var6 = p_72247_3_; var6 <= p_72247_3_ + 1; ++var6)
			{
				for(int var7 = p_72247_4_ - 4; var7 <= p_72247_4_ + 4; ++var7)
				{
					if(p_72247_1_.getBlockMaterial(var5, var6, var7) == Material.water) return true;
				}
			}
		}
		return false;
	}
	
	@Override public void onFallenUpon(World p_71866_1_, int p_71866_2_, int p_71866_3_, int p_71866_4_, Entity p_71866_5_, float p_71866_6_)
	{
		if(!p_71866_1_.isRemote && p_71866_1_.rand.nextFloat() < p_71866_6_ - 0.5F)
		{
			if(!(p_71866_5_ instanceof EntityPlayer) && !p_71866_1_.getGameRules().getGameRuleBooleanValue("mobGriefing")) return;
			p_71866_1_.setBlock(p_71866_2_, p_71866_3_, p_71866_4_, Block.dirt.blockID);
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		super.onNeighborBlockChange(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_5_);
		Material var6 = p_71863_1_.getBlockMaterial(p_71863_2_, p_71863_3_ + 1, p_71863_4_);
		if(var6.isSolid())
		{
			p_71863_1_.setBlock(p_71863_2_, p_71863_3_, p_71863_4_, Block.dirt.blockID);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		field_94441_a = par1IconRegister.registerIcon("farmland_wet");
		field_94440_b = par1IconRegister.registerIcon("farmland_dry");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(!isWaterNearby(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_) && !p_71847_1_.canLightningStrikeAt(p_71847_2_, p_71847_3_ + 1, p_71847_4_))
		{
			int var6 = p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_);
			if(var6 > 0)
			{
				p_71847_1_.setBlockMetadataWithNotify(p_71847_2_, p_71847_3_, p_71847_4_, var6 - 1, 2);
			} else if(!isCropsNearby(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_))
			{
				p_71847_1_.setBlock(p_71847_2_, p_71847_3_, p_71847_4_, Block.dirt.blockID);
			}
		} else
		{
			p_71847_1_.setBlockMetadataWithNotify(p_71847_2_, p_71847_3_, p_71847_4_, 7, 2);
		}
	}
}
