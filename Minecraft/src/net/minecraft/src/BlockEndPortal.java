package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockEndPortal extends BlockContainer
{
	public static boolean bossDefeated;
	
	protected BlockEndPortal(int par1, Material par2Material)
	{
		super(par1, par2Material);
		setLightValue(1.0F);
	}
	
	@Override public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
	}
	
	@Override public TileEntity createNewTileEntity(World par1World)
	{
		return new TileEntityEndPortal();
	}
	
	@Override public int getRenderType()
	{
		return -1;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return 0;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		if(!bossDefeated)
		{
			if(par1World.provider.dimensionId != 0)
			{
				par1World.setBlockToAir(par2, par3, par4);
			}
		}
	}
	
	@Override public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		if(par5Entity.ridingEntity == null && par5Entity.riddenByEntity == null && !par1World.isRemote)
		{
			par5Entity.travelToDimension(1);
		}
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return 0;
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		double var6 = par2 + par5Random.nextFloat();
		double var8 = par3 + 0.8F;
		double var10 = par4 + par5Random.nextFloat();
		double var12 = 0.0D;
		double var14 = 0.0D;
		double var16 = 0.0D;
		par1World.spawnParticle("smoke", var6, var8, var10, var12, var14, var16);
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("portal");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		float var5 = 0.0625F;
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var5, 1.0F);
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par5 != 0 ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
	}
}
