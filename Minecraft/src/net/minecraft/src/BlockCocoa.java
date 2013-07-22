package net.minecraft.src;

import java.util.Random;

public class BlockCocoa extends BlockDirectional
{
	public static final String[] cocoaIcons = new String[] { "cocoa_0", "cocoa_1", "cocoa_2" };
	private Icon[] iconArray;
	
	public BlockCocoa(int p_i3930_1_)
	{
		super(p_i3930_1_, Material.plants);
		setTickRandomly(true);
	}
	
	@Override public boolean canBlockStay(World p_71854_1_, int p_71854_2_, int p_71854_3_, int p_71854_4_)
	{
		int var5 = getDirection(p_71854_1_.getBlockMetadata(p_71854_2_, p_71854_3_, p_71854_4_));
		p_71854_2_ += Direction.offsetX[var5];
		p_71854_4_ += Direction.offsetZ[var5];
		int var6 = p_71854_1_.getBlockId(p_71854_2_, p_71854_3_, p_71854_4_);
		return var6 == Block.wood.blockID && BlockLog.limitToValidMetadata(p_71854_1_.getBlockMetadata(p_71854_2_, p_71854_3_, p_71854_4_)) == 3;
	}
	
	@Override public void dropBlockAsItemWithChance(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
	{
		int var8 = func_72219_c(p_71914_5_);
		byte var9 = 1;
		if(var8 >= 2)
		{
			var9 = 3;
		}
		for(int var10 = 0; var10 < var9; ++var10)
		{
			dropBlockAsItem_do(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, new ItemStack(Item.dyePowder, 1, 3));
		}
	}
	
	public Icon func_94468_i_(int par1)
	{
		if(par1 < 0 || par1 >= iconArray.length)
		{
			par1 = iconArray.length - 1;
		}
		return iconArray[par1];
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		setBlockBoundsBasedOnState(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
		return super.getCollisionBoundingBoxFromPool(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
	}
	
	@Override public int getDamageValue(World p_71873_1_, int p_71873_2_, int p_71873_3_, int p_71873_4_)
	{
		return 3;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return iconArray[2];
	}
	
	@Override public int getRenderType()
	{
		return 28;
	}
	
	@Override public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.dyePowder.itemID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public int onBlockPlaced(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
	{
		if(p_85104_5_ == 1 || p_85104_5_ == 0)
		{
			p_85104_5_ = 2;
		}
		return Direction.rotateOpposite[Direction.facingToDirection[p_85104_5_]];
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		int var7 = ((MathHelper.floor_double(p_71860_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) + 0) % 4;
		p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, var7, 2);
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!canBlockStay(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_))
		{
			dropBlockAsItem(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_), 0);
			p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray = new Icon[cocoaIcons.length];
		for(int var2 = 0; var2 < iconArray.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon(cocoaIcons[var2]);
		}
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		int var5 = p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_);
		int var6 = getDirection(var5);
		int var7 = func_72219_c(var5);
		int var8 = 4 + var7 * 2;
		int var9 = 5 + var7 * 2;
		float var10 = var8 / 2.0F;
		switch(var6)
		{
			case 0:
				setBlockBounds((8.0F - var10) / 16.0F, (12.0F - var9) / 16.0F, (15.0F - var8) / 16.0F, (8.0F + var10) / 16.0F, 0.75F, 0.9375F);
				break;
			case 1:
				setBlockBounds(0.0625F, (12.0F - var9) / 16.0F, (8.0F - var10) / 16.0F, (1.0F + var8) / 16.0F, 0.75F, (8.0F + var10) / 16.0F);
				break;
			case 2:
				setBlockBounds((8.0F - var10) / 16.0F, (12.0F - var9) / 16.0F, 0.0625F, (8.0F + var10) / 16.0F, 0.75F, (1.0F + var8) / 16.0F);
				break;
			case 3:
				setBlockBounds((15.0F - var8) / 16.0F, (12.0F - var9) / 16.0F, (8.0F - var10) / 16.0F, 0.9375F, 0.75F, (8.0F + var10) / 16.0F);
		}
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(!canBlockStay(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_))
		{
			dropBlockAsItem(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_), 0);
			p_71847_1_.setBlockToAir(p_71847_2_, p_71847_3_, p_71847_4_);
		} else if(p_71847_1_.rand.nextInt(5) == 0)
		{
			int var6 = p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_);
			int var7 = func_72219_c(var6);
			if(var7 < 2)
			{
				++var7;
				p_71847_1_.setBlockMetadataWithNotify(p_71847_2_, p_71847_3_, p_71847_4_, var7 << 2 | getDirection(var6), 2);
			}
		}
	}
	
	public static int func_72219_c(int p_72219_0_)
	{
		return (p_72219_0_ & 12) >> 2;
	}
}
