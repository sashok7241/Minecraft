package net.minecraft.src;

import java.util.Random;

public class BlockCocoa extends BlockDirectional
{
	public static final String[] cocoaIcons = new String[] { "cocoa_0", "cocoa_1", "cocoa_2" };
	private Icon[] iconArray;
	
	public BlockCocoa(int par1)
	{
		super(par1, Material.plants);
		setTickRandomly(true);
	}
	
	@Override public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		int var5 = getDirection(par1World.getBlockMetadata(par2, par3, par4));
		par2 += Direction.offsetX[var5];
		par4 += Direction.offsetZ[var5];
		int var6 = par1World.getBlockId(par2, par3, par4);
		return var6 == Block.wood.blockID && BlockLog.limitToValidMetadata(par1World.getBlockMetadata(par2, par3, par4)) == 3;
	}
	
	@Override public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		int var8 = func_72219_c(par5);
		byte var9 = 1;
		if(var8 >= 2)
		{
			var9 = 3;
		}
		for(int var10 = 0; var10 < var9; ++var10)
		{
			dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(Item.dyePowder, 1, 3));
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
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}
	
	@Override public int getDamageValue(World par1World, int par2, int par3, int par4)
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
	
	@Override public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	{
		if(par5 == 1 || par5 == 0)
		{
			par5 = 2;
		}
		return Direction.rotateOpposite[Direction.facingToDirection[par5]];
	}
	
	@Override public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack)
	{
		int var7 = ((MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) + 0) % 4;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(!canBlockStay(par1World, par2, par3, par4))
		{
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
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
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
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
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(!canBlockStay(par1World, par2, par3, par4))
		{
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
		} else if(par1World.rand.nextInt(5) == 0)
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			int var7 = func_72219_c(var6);
			if(var7 < 2)
			{
				++var7;
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 << 2 | getDirection(var6), 2);
			}
		}
	}
	
	public static int func_72219_c(int par0)
	{
		return (par0 & 12) >> 2;
	}
}
