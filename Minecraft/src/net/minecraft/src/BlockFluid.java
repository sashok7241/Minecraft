package net.minecraft.src;

import java.util.Random;

public abstract class BlockFluid extends Block
{
	private Icon[] theIcon;
	
	protected BlockFluid(int p_i3964_1_, Material p_i3964_2_)
	{
		super(p_i3964_1_, p_i3964_2_);
		float var3 = 0.0F;
		float var4 = 0.0F;
		setBlockBounds(0.0F + var4, 0.0F + var3, 0.0F + var4, 1.0F + var4, 1.0F + var3, 1.0F + var4);
		setTickRandomly(true);
	}
	
	@Override public boolean canCollideCheck(int p_71913_1_, boolean p_71913_2_)
	{
		return p_71913_2_ && p_71913_1_ == 0;
	}
	
	private void checkForHarden(World p_72200_1_, int p_72200_2_, int p_72200_3_, int p_72200_4_)
	{
		if(p_72200_1_.getBlockId(p_72200_2_, p_72200_3_, p_72200_4_) == blockID)
		{
			if(blockMaterial == Material.lava)
			{
				boolean var5 = false;
				if(var5 || p_72200_1_.getBlockMaterial(p_72200_2_, p_72200_3_, p_72200_4_ - 1) == Material.water)
				{
					var5 = true;
				}
				if(var5 || p_72200_1_.getBlockMaterial(p_72200_2_, p_72200_3_, p_72200_4_ + 1) == Material.water)
				{
					var5 = true;
				}
				if(var5 || p_72200_1_.getBlockMaterial(p_72200_2_ - 1, p_72200_3_, p_72200_4_) == Material.water)
				{
					var5 = true;
				}
				if(var5 || p_72200_1_.getBlockMaterial(p_72200_2_ + 1, p_72200_3_, p_72200_4_) == Material.water)
				{
					var5 = true;
				}
				if(var5 || p_72200_1_.getBlockMaterial(p_72200_2_, p_72200_3_ + 1, p_72200_4_) == Material.water)
				{
					var5 = true;
				}
				if(var5)
				{
					int var6 = p_72200_1_.getBlockMetadata(p_72200_2_, p_72200_3_, p_72200_4_);
					if(var6 == 0)
					{
						p_72200_1_.setBlock(p_72200_2_, p_72200_3_, p_72200_4_, Block.obsidian.blockID);
					} else if(var6 <= 4)
					{
						p_72200_1_.setBlock(p_72200_2_, p_72200_3_, p_72200_4_, Block.cobblestone.blockID);
					}
					triggerLavaMixEffects(p_72200_1_, p_72200_2_, p_72200_3_, p_72200_4_);
				}
			}
		}
	}
	
	@Override public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		if(blockMaterial != Material.water) return 16777215;
		else
		{
			int var5 = 0;
			int var6 = 0;
			int var7 = 0;
			for(int var8 = -1; var8 <= 1; ++var8)
			{
				for(int var9 = -1; var9 <= 1; ++var9)
				{
					int var10 = par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8).waterColorMultiplier;
					var5 += (var10 & 16711680) >> 16;
					var6 += (var10 & 65280) >> 8;
					var7 += var10 & 255;
				}
			}
			return (var5 / 9 & 255) << 16 | (var6 / 9 & 255) << 8 | var7 / 9 & 255;
		}
	}
	
	@Override public float getBlockBrightness(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		float var5 = par1IBlockAccess.getLightBrightness(par2, par3, par4);
		float var6 = par1IBlockAccess.getLightBrightness(par2, par3 + 1, par4);
		return var5 > var6 ? var5 : var6;
	}
	
	@Override public int getBlockColor()
	{
		return 16777215;
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess p_71918_1_, int p_71918_2_, int p_71918_3_, int p_71918_4_)
	{
		return blockMaterial != Material.lava;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		return null;
	}
	
	protected int getEffectiveFlowDecay(IBlockAccess p_72203_1_, int p_72203_2_, int p_72203_3_, int p_72203_4_)
	{
		if(p_72203_1_.getBlockMaterial(p_72203_2_, p_72203_3_, p_72203_4_) != blockMaterial) return -1;
		else
		{
			int var5 = p_72203_1_.getBlockMetadata(p_72203_2_, p_72203_3_, p_72203_4_);
			if(var5 >= 8)
			{
				var5 = 0;
			}
			return var5;
		}
	}
	
	protected int getFlowDecay(World p_72198_1_, int p_72198_2_, int p_72198_3_, int p_72198_4_)
	{
		return p_72198_1_.getBlockMaterial(p_72198_2_, p_72198_3_, p_72198_4_) == blockMaterial ? p_72198_1_.getBlockMetadata(p_72198_2_, p_72198_3_, p_72198_4_) : -1;
	}
	
	private Vec3 getFlowVector(IBlockAccess p_72202_1_, int p_72202_2_, int p_72202_3_, int p_72202_4_)
	{
		Vec3 var5 = p_72202_1_.getWorldVec3Pool().getVecFromPool(0.0D, 0.0D, 0.0D);
		int var6 = getEffectiveFlowDecay(p_72202_1_, p_72202_2_, p_72202_3_, p_72202_4_);
		for(int var7 = 0; var7 < 4; ++var7)
		{
			int var8 = p_72202_2_;
			int var10 = p_72202_4_;
			if(var7 == 0)
			{
				var8 = p_72202_2_ - 1;
			}
			if(var7 == 1)
			{
				var10 = p_72202_4_ - 1;
			}
			if(var7 == 2)
			{
				++var8;
			}
			if(var7 == 3)
			{
				++var10;
			}
			int var11 = getEffectiveFlowDecay(p_72202_1_, var8, p_72202_3_, var10);
			int var12;
			if(var11 < 0)
			{
				if(!p_72202_1_.getBlockMaterial(var8, p_72202_3_, var10).blocksMovement())
				{
					var11 = getEffectiveFlowDecay(p_72202_1_, var8, p_72202_3_ - 1, var10);
					if(var11 >= 0)
					{
						var12 = var11 - (var6 - 8);
						var5 = var5.addVector((var8 - p_72202_2_) * var12, (p_72202_3_ - p_72202_3_) * var12, (var10 - p_72202_4_) * var12);
					}
				}
			} else if(var11 >= 0)
			{
				var12 = var11 - var6;
				var5 = var5.addVector((var8 - p_72202_2_) * var12, (p_72202_3_ - p_72202_3_) * var12, (var10 - p_72202_4_) * var12);
			}
		}
		if(p_72202_1_.getBlockMetadata(p_72202_2_, p_72202_3_, p_72202_4_) >= 8)
		{
			boolean var13 = false;
			if(var13 || isBlockSolid(p_72202_1_, p_72202_2_, p_72202_3_, p_72202_4_ - 1, 2))
			{
				var13 = true;
			}
			if(var13 || isBlockSolid(p_72202_1_, p_72202_2_, p_72202_3_, p_72202_4_ + 1, 3))
			{
				var13 = true;
			}
			if(var13 || isBlockSolid(p_72202_1_, p_72202_2_ - 1, p_72202_3_, p_72202_4_, 4))
			{
				var13 = true;
			}
			if(var13 || isBlockSolid(p_72202_1_, p_72202_2_ + 1, p_72202_3_, p_72202_4_, 5))
			{
				var13 = true;
			}
			if(var13 || isBlockSolid(p_72202_1_, p_72202_2_, p_72202_3_ + 1, p_72202_4_ - 1, 2))
			{
				var13 = true;
			}
			if(var13 || isBlockSolid(p_72202_1_, p_72202_2_, p_72202_3_ + 1, p_72202_4_ + 1, 3))
			{
				var13 = true;
			}
			if(var13 || isBlockSolid(p_72202_1_, p_72202_2_ - 1, p_72202_3_ + 1, p_72202_4_, 4))
			{
				var13 = true;
			}
			if(var13 || isBlockSolid(p_72202_1_, p_72202_2_ + 1, p_72202_3_ + 1, p_72202_4_, 5))
			{
				var13 = true;
			}
			if(var13)
			{
				var5 = var5.normalize().addVector(0.0D, -6.0D, 0.0D);
			}
		}
		var5 = var5.normalize();
		return var5;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 != 0 && par1 != 1 ? theIcon[1] : theIcon[0];
	}
	
	@Override public int getMixedBrightnessForBlock(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getLightBrightnessForSkyBlocks(par2, par3, par4, 0);
		int var6 = par1IBlockAccess.getLightBrightnessForSkyBlocks(par2, par3 + 1, par4, 0);
		int var7 = var5 & 255;
		int var8 = var6 & 255;
		int var9 = var5 >> 16 & 255;
		int var10 = var6 >> 16 & 255;
		return (var7 > var8 ? var7 : var8) | (var9 > var10 ? var9 : var10) << 16;
	}
	
	@Override public int getRenderBlockPass()
	{
		return blockMaterial == Material.water ? 1 : 0;
	}
	
	@Override public int getRenderType()
	{
		return 4;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return 0;
	}
	
	@Override public boolean isBlockSolid(IBlockAccess p_71924_1_, int p_71924_2_, int p_71924_3_, int p_71924_4_, int p_71924_5_)
	{
		Material var6 = p_71924_1_.getBlockMaterial(p_71924_2_, p_71924_3_, p_71924_4_);
		return var6 == blockMaterial ? false : p_71924_5_ == 1 ? true : var6 == Material.ice ? false : super.isBlockSolid(p_71924_1_, p_71924_2_, p_71924_3_, p_71924_4_, p_71924_5_);
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		checkForHarden(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		checkForHarden(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 0;
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		int var6;
		if(blockMaterial == Material.water)
		{
			if(par5Random.nextInt(10) == 0)
			{
				var6 = par1World.getBlockMetadata(par2, par3, par4);
				if(var6 <= 0 || var6 >= 8)
				{
					par1World.spawnParticle("suspended", par2 + par5Random.nextFloat(), par3 + par5Random.nextFloat(), par4 + par5Random.nextFloat(), 0.0D, 0.0D, 0.0D);
				}
			}
			for(var6 = 0; var6 < 0; ++var6)
			{
				int var7 = par5Random.nextInt(4);
				int var8 = par2;
				int var9 = par4;
				if(var7 == 0)
				{
					var8 = par2 - 1;
				}
				if(var7 == 1)
				{
					++var8;
				}
				if(var7 == 2)
				{
					var9 = par4 - 1;
				}
				if(var7 == 3)
				{
					++var9;
				}
				if(par1World.getBlockMaterial(var8, par3, var9) == Material.air && (par1World.getBlockMaterial(var8, par3 - 1, var9).blocksMovement() || par1World.getBlockMaterial(var8, par3 - 1, var9).isLiquid()))
				{
					float var10 = 0.0625F;
					double var11 = par2 + par5Random.nextFloat();
					double var13 = par3 + par5Random.nextFloat();
					double var15 = par4 + par5Random.nextFloat();
					if(var7 == 0)
					{
						var11 = par2 - var10;
					}
					if(var7 == 1)
					{
						var11 = par2 + 1 + var10;
					}
					if(var7 == 2)
					{
						var15 = par4 - var10;
					}
					if(var7 == 3)
					{
						var15 = par4 + 1 + var10;
					}
					double var17 = 0.0D;
					double var19 = 0.0D;
					if(var7 == 0)
					{
						var17 = -var10;
					}
					if(var7 == 1)
					{
						var17 = var10;
					}
					if(var7 == 2)
					{
						var19 = -var10;
					}
					if(var7 == 3)
					{
						var19 = var10;
					}
					par1World.spawnParticle("splash", var11, var13, var15, var17, 0.0D, var19);
				}
			}
		}
		if(blockMaterial == Material.water && par5Random.nextInt(64) == 0)
		{
			var6 = par1World.getBlockMetadata(par2, par3, par4);
			if(var6 > 0 && var6 < 8)
			{
				par1World.playSound(par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, "liquid.water", par5Random.nextFloat() * 0.25F + 0.75F, par5Random.nextFloat() * 1.0F + 0.5F, false);
			}
		}
		double var21;
		double var23;
		double var22;
		if(blockMaterial == Material.lava && par1World.getBlockMaterial(par2, par3 + 1, par4) == Material.air && !par1World.isBlockOpaqueCube(par2, par3 + 1, par4))
		{
			if(par5Random.nextInt(100) == 0)
			{
				var21 = par2 + par5Random.nextFloat();
				var22 = par3 + maxY;
				var23 = par4 + par5Random.nextFloat();
				par1World.spawnParticle("lava", var21, var22, var23, 0.0D, 0.0D, 0.0D);
				par1World.playSound(var21, var22, var23, "liquid.lavapop", 0.2F + par5Random.nextFloat() * 0.2F, 0.9F + par5Random.nextFloat() * 0.15F, false);
			}
			if(par5Random.nextInt(200) == 0)
			{
				par1World.playSound(par2, par3, par4, "liquid.lava", 0.2F + par5Random.nextFloat() * 0.2F, 0.9F + par5Random.nextFloat() * 0.15F, false);
			}
		}
		if(par5Random.nextInt(10) == 0 && par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !par1World.getBlockMaterial(par2, par3 - 2, par4).blocksMovement())
		{
			var21 = par2 + par5Random.nextFloat();
			var22 = par3 - 1.05D;
			var23 = par4 + par5Random.nextFloat();
			if(blockMaterial == Material.water)
			{
				par1World.spawnParticle("dripWater", var21, var22, var23, 0.0D, 0.0D, 0.0D);
			} else
			{
				par1World.spawnParticle("dripLava", var21, var22, var23, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		if(blockMaterial == Material.lava)
		{
			theIcon = new Icon[] { par1IconRegister.registerIcon("lava"), par1IconRegister.registerIcon("lava_flow") };
		} else
		{
			theIcon = new Icon[] { par1IconRegister.registerIcon("water"), par1IconRegister.registerIcon("water_flow") };
		}
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		Material var6 = par1IBlockAccess.getBlockMaterial(par2, par3, par4);
		return var6 == blockMaterial ? false : par5 == 1 ? true : var6 == Material.ice ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
	}
	
	@Override public int tickRate(World p_71859_1_)
	{
		return blockMaterial == Material.water ? 5 : blockMaterial == Material.lava ? p_71859_1_.provider.hasNoSky ? 10 : 30 : 0;
	}
	
	protected void triggerLavaMixEffects(World p_72201_1_, int p_72201_2_, int p_72201_3_, int p_72201_4_)
	{
		p_72201_1_.playSoundEffect(p_72201_2_ + 0.5F, p_72201_3_ + 0.5F, p_72201_4_ + 0.5F, "random.fizz", 0.5F, 2.6F + (p_72201_1_.rand.nextFloat() - p_72201_1_.rand.nextFloat()) * 0.8F);
		for(int var5 = 0; var5 < 8; ++var5)
		{
			p_72201_1_.spawnParticle("largesmoke", p_72201_2_ + Math.random(), p_72201_3_ + 1.2D, p_72201_4_ + Math.random(), 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override public void velocityToAddToEntity(World p_71901_1_, int p_71901_2_, int p_71901_3_, int p_71901_4_, Entity p_71901_5_, Vec3 p_71901_6_)
	{
		Vec3 var7 = getFlowVector(p_71901_1_, p_71901_2_, p_71901_3_, p_71901_4_);
		p_71901_6_.xCoord += var7.xCoord;
		p_71901_6_.yCoord += var7.yCoord;
		p_71901_6_.zCoord += var7.zCoord;
	}
	
	public static Icon func_94424_b(String par0Str)
	{
		return par0Str == "water" ? Block.waterMoving.theIcon[0] : par0Str == "water_flow" ? Block.waterMoving.theIcon[1] : par0Str == "lava" ? Block.lavaMoving.theIcon[0] : par0Str == "lava_flow" ? Block.lavaMoving.theIcon[1] : null;
	}
	
	public static double getFlowDirection(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, Material par4Material)
	{
		Vec3 var5 = null;
		if(par4Material == Material.water)
		{
			var5 = Block.waterMoving.getFlowVector(par0IBlockAccess, par1, par2, par3);
		}
		if(par4Material == Material.lava)
		{
			var5 = Block.lavaMoving.getFlowVector(par0IBlockAccess, par1, par2, par3);
		}
		return var5.xCoord == 0.0D && var5.zCoord == 0.0D ? -1000.0D : Math.atan2(var5.zCoord, var5.xCoord) - Math.PI / 2D;
	}
	
	public static float getFluidHeightPercent(int p_72199_0_)
	{
		if(p_72199_0_ >= 8)
		{
			p_72199_0_ = 0;
		}
		return (p_72199_0_ + 1) / 9.0F;
	}
}
