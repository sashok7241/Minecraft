package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockStairs extends Block
{
	private static final int[][] field_72159_a = new int[][] { { 2, 6 }, { 3, 7 }, { 2, 3 }, { 6, 7 }, { 0, 4 }, { 1, 5 }, { 0, 1 }, { 4, 5 } };
	private final Block modelBlock;
	private final int modelBlockMetadata;
	private boolean field_72156_cr = false;
	private int field_72160_cs = 0;
	
	protected BlockStairs(int p_i3997_1_, Block p_i3997_2_, int p_i3997_3_)
	{
		super(p_i3997_1_, p_i3997_2_.blockMaterial);
		modelBlock = p_i3997_2_;
		modelBlockMetadata = p_i3997_3_;
		setHardness(p_i3997_2_.blockHardness);
		setResistance(p_i3997_2_.blockResistance / 3.0F);
		setStepSound(p_i3997_2_.stepSound);
		setLightOpacity(255);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public void addCollisionBoxesToList(World p_71871_1_, int p_71871_2_, int p_71871_3_, int p_71871_4_, AxisAlignedBB p_71871_5_, List p_71871_6_, Entity p_71871_7_)
	{
		func_82541_d(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_);
		super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		boolean var8 = func_82542_g(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_);
		super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		if(var8 && func_82544_h(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_))
		{
			super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		}
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		modelBlock.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
	}
	
	@Override public boolean canCollideCheck(int p_71913_1_, boolean p_71913_2_)
	{
		return modelBlock.canCollideCheck(p_71913_1_, p_71913_2_);
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return modelBlock.canPlaceBlockAt(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_);
	}
	
	@Override public MovingObjectPosition collisionRayTrace(World p_71878_1_, int p_71878_2_, int p_71878_3_, int p_71878_4_, Vec3 p_71878_5_, Vec3 p_71878_6_)
	{
		MovingObjectPosition[] var7 = new MovingObjectPosition[8];
		int var8 = p_71878_1_.getBlockMetadata(p_71878_2_, p_71878_3_, p_71878_4_);
		int var9 = var8 & 3;
		boolean var10 = (var8 & 4) == 4;
		int[] var11 = field_72159_a[var9 + (var10 ? 4 : 0)];
		field_72156_cr = true;
		int var14;
		int var15;
		int var16;
		for(int var12 = 0; var12 < 8; ++var12)
		{
			field_72160_cs = var12;
			int[] var13 = var11;
			var14 = var11.length;
			for(var15 = 0; var15 < var14; ++var15)
			{
				var16 = var13[var15];
				if(var16 == var12)
				{
					;
				}
			}
			var7[var12] = super.collisionRayTrace(p_71878_1_, p_71878_2_, p_71878_3_, p_71878_4_, p_71878_5_, p_71878_6_);
		}
		int[] var21 = var11;
		int var24 = var11.length;
		for(var14 = 0; var14 < var24; ++var14)
		{
			var15 = var21[var14];
			var7[var15] = null;
		}
		MovingObjectPosition var23 = null;
		double var22 = 0.0D;
		MovingObjectPosition[] var25 = var7;
		var16 = var7.length;
		for(int var17 = 0; var17 < var16; ++var17)
		{
			MovingObjectPosition var18 = var25[var17];
			if(var18 != null)
			{
				double var19 = var18.hitVec.squareDistanceTo(p_71878_6_);
				if(var19 > var22)
				{
					var23 = var18;
					var22 = var19;
				}
			}
		}
		return var23;
	}
	
	private boolean func_82540_f(IBlockAccess p_82540_1_, int p_82540_2_, int p_82540_3_, int p_82540_4_, int p_82540_5_)
	{
		int var6 = p_82540_1_.getBlockId(p_82540_2_, p_82540_3_, p_82540_4_);
		return isBlockStairsID(var6) && p_82540_1_.getBlockMetadata(p_82540_2_, p_82540_3_, p_82540_4_) == p_82540_5_;
	}
	
	public void func_82541_d(IBlockAccess p_82541_1_, int p_82541_2_, int p_82541_3_, int p_82541_4_)
	{
		int var5 = p_82541_1_.getBlockMetadata(p_82541_2_, p_82541_3_, p_82541_4_);
		if((var5 & 4) != 0)
		{
			setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}
	}
	
	public boolean func_82542_g(IBlockAccess p_82542_1_, int p_82542_2_, int p_82542_3_, int p_82542_4_)
	{
		int var5 = p_82542_1_.getBlockMetadata(p_82542_2_, p_82542_3_, p_82542_4_);
		int var6 = var5 & 3;
		float var7 = 0.5F;
		float var8 = 1.0F;
		if((var5 & 4) != 0)
		{
			var7 = 0.0F;
			var8 = 0.5F;
		}
		float var9 = 0.0F;
		float var10 = 1.0F;
		float var11 = 0.0F;
		float var12 = 0.5F;
		boolean var13 = true;
		int var14;
		int var15;
		int var16;
		if(var6 == 0)
		{
			var9 = 0.5F;
			var12 = 1.0F;
			var14 = p_82542_1_.getBlockId(p_82542_2_ + 1, p_82542_3_, p_82542_4_);
			var15 = p_82542_1_.getBlockMetadata(p_82542_2_ + 1, p_82542_3_, p_82542_4_);
			if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
			{
				var16 = var15 & 3;
				if(var16 == 3 && !func_82540_f(p_82542_1_, p_82542_2_, p_82542_3_, p_82542_4_ + 1, var5))
				{
					var12 = 0.5F;
					var13 = false;
				} else if(var16 == 2 && !func_82540_f(p_82542_1_, p_82542_2_, p_82542_3_, p_82542_4_ - 1, var5))
				{
					var11 = 0.5F;
					var13 = false;
				}
			}
		} else if(var6 == 1)
		{
			var10 = 0.5F;
			var12 = 1.0F;
			var14 = p_82542_1_.getBlockId(p_82542_2_ - 1, p_82542_3_, p_82542_4_);
			var15 = p_82542_1_.getBlockMetadata(p_82542_2_ - 1, p_82542_3_, p_82542_4_);
			if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
			{
				var16 = var15 & 3;
				if(var16 == 3 && !func_82540_f(p_82542_1_, p_82542_2_, p_82542_3_, p_82542_4_ + 1, var5))
				{
					var12 = 0.5F;
					var13 = false;
				} else if(var16 == 2 && !func_82540_f(p_82542_1_, p_82542_2_, p_82542_3_, p_82542_4_ - 1, var5))
				{
					var11 = 0.5F;
					var13 = false;
				}
			}
		} else if(var6 == 2)
		{
			var11 = 0.5F;
			var12 = 1.0F;
			var14 = p_82542_1_.getBlockId(p_82542_2_, p_82542_3_, p_82542_4_ + 1);
			var15 = p_82542_1_.getBlockMetadata(p_82542_2_, p_82542_3_, p_82542_4_ + 1);
			if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
			{
				var16 = var15 & 3;
				if(var16 == 1 && !func_82540_f(p_82542_1_, p_82542_2_ + 1, p_82542_3_, p_82542_4_, var5))
				{
					var10 = 0.5F;
					var13 = false;
				} else if(var16 == 0 && !func_82540_f(p_82542_1_, p_82542_2_ - 1, p_82542_3_, p_82542_4_, var5))
				{
					var9 = 0.5F;
					var13 = false;
				}
			}
		} else if(var6 == 3)
		{
			var14 = p_82542_1_.getBlockId(p_82542_2_, p_82542_3_, p_82542_4_ - 1);
			var15 = p_82542_1_.getBlockMetadata(p_82542_2_, p_82542_3_, p_82542_4_ - 1);
			if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
			{
				var16 = var15 & 3;
				if(var16 == 1 && !func_82540_f(p_82542_1_, p_82542_2_ + 1, p_82542_3_, p_82542_4_, var5))
				{
					var10 = 0.5F;
					var13 = false;
				} else if(var16 == 0 && !func_82540_f(p_82542_1_, p_82542_2_ - 1, p_82542_3_, p_82542_4_, var5))
				{
					var9 = 0.5F;
					var13 = false;
				}
			}
		}
		setBlockBounds(var9, var7, var11, var10, var8, var12);
		return var13;
	}
	
	public boolean func_82544_h(IBlockAccess p_82544_1_, int p_82544_2_, int p_82544_3_, int p_82544_4_)
	{
		int var5 = p_82544_1_.getBlockMetadata(p_82544_2_, p_82544_3_, p_82544_4_);
		int var6 = var5 & 3;
		float var7 = 0.5F;
		float var8 = 1.0F;
		if((var5 & 4) != 0)
		{
			var7 = 0.0F;
			var8 = 0.5F;
		}
		float var9 = 0.0F;
		float var10 = 0.5F;
		float var11 = 0.5F;
		float var12 = 1.0F;
		boolean var13 = false;
		int var14;
		int var15;
		int var16;
		if(var6 == 0)
		{
			var14 = p_82544_1_.getBlockId(p_82544_2_ - 1, p_82544_3_, p_82544_4_);
			var15 = p_82544_1_.getBlockMetadata(p_82544_2_ - 1, p_82544_3_, p_82544_4_);
			if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
			{
				var16 = var15 & 3;
				if(var16 == 3 && !func_82540_f(p_82544_1_, p_82544_2_, p_82544_3_, p_82544_4_ - 1, var5))
				{
					var11 = 0.0F;
					var12 = 0.5F;
					var13 = true;
				} else if(var16 == 2 && !func_82540_f(p_82544_1_, p_82544_2_, p_82544_3_, p_82544_4_ + 1, var5))
				{
					var11 = 0.5F;
					var12 = 1.0F;
					var13 = true;
				}
			}
		} else if(var6 == 1)
		{
			var14 = p_82544_1_.getBlockId(p_82544_2_ + 1, p_82544_3_, p_82544_4_);
			var15 = p_82544_1_.getBlockMetadata(p_82544_2_ + 1, p_82544_3_, p_82544_4_);
			if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
			{
				var9 = 0.5F;
				var10 = 1.0F;
				var16 = var15 & 3;
				if(var16 == 3 && !func_82540_f(p_82544_1_, p_82544_2_, p_82544_3_, p_82544_4_ - 1, var5))
				{
					var11 = 0.0F;
					var12 = 0.5F;
					var13 = true;
				} else if(var16 == 2 && !func_82540_f(p_82544_1_, p_82544_2_, p_82544_3_, p_82544_4_ + 1, var5))
				{
					var11 = 0.5F;
					var12 = 1.0F;
					var13 = true;
				}
			}
		} else if(var6 == 2)
		{
			var14 = p_82544_1_.getBlockId(p_82544_2_, p_82544_3_, p_82544_4_ - 1);
			var15 = p_82544_1_.getBlockMetadata(p_82544_2_, p_82544_3_, p_82544_4_ - 1);
			if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
			{
				var11 = 0.0F;
				var12 = 0.5F;
				var16 = var15 & 3;
				if(var16 == 1 && !func_82540_f(p_82544_1_, p_82544_2_ - 1, p_82544_3_, p_82544_4_, var5))
				{
					var13 = true;
				} else if(var16 == 0 && !func_82540_f(p_82544_1_, p_82544_2_ + 1, p_82544_3_, p_82544_4_, var5))
				{
					var9 = 0.5F;
					var10 = 1.0F;
					var13 = true;
				}
			}
		} else if(var6 == 3)
		{
			var14 = p_82544_1_.getBlockId(p_82544_2_, p_82544_3_, p_82544_4_ + 1);
			var15 = p_82544_1_.getBlockMetadata(p_82544_2_, p_82544_3_, p_82544_4_ + 1);
			if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
			{
				var16 = var15 & 3;
				if(var16 == 1 && !func_82540_f(p_82544_1_, p_82544_2_ - 1, p_82544_3_, p_82544_4_, var5))
				{
					var13 = true;
				} else if(var16 == 0 && !func_82540_f(p_82544_1_, p_82544_2_ + 1, p_82544_3_, p_82544_4_, var5))
				{
					var9 = 0.5F;
					var10 = 1.0F;
					var13 = true;
				}
			}
		}
		if(var13)
		{
			setBlockBounds(var9, var7, var11, var10, var8, var12);
		}
		return var13;
	}
	
	@Override public float getBlockBrightness(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return modelBlock.getBlockBrightness(par1IBlockAccess, par2, par3, par4);
	}
	
	@Override public float getExplosionResistance(Entity p_71904_1_)
	{
		return modelBlock.getExplosionResistance(p_71904_1_);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return modelBlock.getIcon(par1, modelBlockMetadata);
	}
	
	@Override public int getMixedBrightnessForBlock(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return modelBlock.getMixedBrightnessForBlock(par1IBlockAccess, par2, par3, par4);
	}
	
	@Override public int getRenderBlockPass()
	{
		return modelBlock.getRenderBlockPass();
	}
	
	@Override public int getRenderType()
	{
		return 10;
	}
	
	@Override public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return modelBlock.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}
	
	@Override public boolean isCollidable()
	{
		return modelBlock.isCollidable();
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		return modelBlock.onBlockActivated(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_, p_71903_5_, 0, 0.0F, 0.0F, 0.0F);
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		onNeighborBlockChange(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_, 0);
		modelBlock.onBlockAdded(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
	}
	
	@Override public void onBlockClicked(World p_71921_1_, int p_71921_2_, int p_71921_3_, int p_71921_4_, EntityPlayer p_71921_5_)
	{
		modelBlock.onBlockClicked(p_71921_1_, p_71921_2_, p_71921_3_, p_71921_4_, p_71921_5_);
	}
	
	@Override public void onBlockDestroyedByExplosion(World p_71867_1_, int p_71867_2_, int p_71867_3_, int p_71867_4_, Explosion p_71867_5_)
	{
		modelBlock.onBlockDestroyedByExplosion(p_71867_1_, p_71867_2_, p_71867_3_, p_71867_4_, p_71867_5_);
	}
	
	@Override public void onBlockDestroyedByPlayer(World p_71898_1_, int p_71898_2_, int p_71898_3_, int p_71898_4_, int p_71898_5_)
	{
		modelBlock.onBlockDestroyedByPlayer(p_71898_1_, p_71898_2_, p_71898_3_, p_71898_4_, p_71898_5_);
	}
	
	@Override public int onBlockPlaced(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
	{
		return p_85104_5_ != 0 && (p_85104_5_ == 1 || p_85104_7_ <= 0.5D) ? p_85104_9_ : p_85104_9_ | 4;
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		int var7 = MathHelper.floor_double(p_71860_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int var8 = p_71860_1_.getBlockMetadata(p_71860_2_, p_71860_3_, p_71860_4_) & 4;
		if(var7 == 0)
		{
			p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, 2 | var8, 2);
		}
		if(var7 == 1)
		{
			p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, 1 | var8, 2);
		}
		if(var7 == 2)
		{
			p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, 3 | var8, 2);
		}
		if(var7 == 3)
		{
			p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, 0 | var8, 2);
		}
	}
	
	@Override public void onEntityWalking(World p_71891_1_, int p_71891_2_, int p_71891_3_, int p_71891_4_, Entity p_71891_5_)
	{
		modelBlock.onEntityWalking(p_71891_1_, p_71891_2_, p_71891_3_, p_71891_4_, p_71891_5_);
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		modelBlock.randomDisplayTick(par1World, par2, par3, par4, par5Random);
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		if(field_72156_cr)
		{
			setBlockBounds(0.5F * (field_72160_cs % 2), 0.5F * (field_72160_cs / 2 % 2), 0.5F * (field_72160_cs / 4 % 2), 0.5F + 0.5F * (field_72160_cs % 2), 0.5F + 0.5F * (field_72160_cs / 2 % 2), 0.5F + 0.5F * (field_72160_cs / 4 % 2));
		} else
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	@Override public int tickRate(World p_71859_1_)
	{
		return modelBlock.tickRate(p_71859_1_);
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		modelBlock.updateTick(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_5_);
	}
	
	@Override public void velocityToAddToEntity(World p_71901_1_, int p_71901_2_, int p_71901_3_, int p_71901_4_, Entity p_71901_5_, Vec3 p_71901_6_)
	{
		modelBlock.velocityToAddToEntity(p_71901_1_, p_71901_2_, p_71901_3_, p_71901_4_, p_71901_5_, p_71901_6_);
	}
	
	public static boolean isBlockStairsID(int p_82543_0_)
	{
		return p_82543_0_ > 0 && Block.blocksList[p_82543_0_] instanceof BlockStairs;
	}
}
