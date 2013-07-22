package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockEndPortal extends BlockContainer
{
	public static boolean bossDefeated = false;
	
	protected BlockEndPortal(int p_i4003_1_, Material p_i4003_2_)
	{
		super(p_i4003_1_, p_i4003_2_);
		setLightValue(1.0F);
	}
	
	@Override public void addCollisionBoxesToList(World p_71871_1_, int p_71871_2_, int p_71871_3_, int p_71871_4_, AxisAlignedBB p_71871_5_, List p_71871_6_, Entity p_71871_7_)
	{
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
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
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		if(!bossDefeated)
		{
			if(p_71861_1_.provider.dimensionId != 0)
			{
				p_71861_1_.setBlockToAir(p_71861_2_, p_71861_3_, p_71861_4_);
			}
		}
	}
	
	@Override public void onEntityCollidedWithBlock(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_)
	{
		if(p_71869_5_.ridingEntity == null && p_71869_5_.riddenByEntity == null && !p_71869_1_.isRemote)
		{
			p_71869_5_.travelToDimension(1);
		}
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
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
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		float var5 = 0.0625F;
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var5, 1.0F);
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par5 != 0 ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
	}
}
