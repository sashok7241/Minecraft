package net.minecraft.src;

import java.util.Random;

public class BlockDaylightDetector extends BlockContainer
{
	private Icon[] iconArray = new Icon[2];
	
	public BlockDaylightDetector(int par1)
	{
		super(par1, Material.wood);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override public boolean canProvidePower()
	{
		return true;
	}
	
	@Override public TileEntity createNewTileEntity(World par1World)
	{
		return new TileEntityDaylightDetector();
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? iconArray[0] : iconArray[1];
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par1IBlockAccess.getBlockMetadata(par2, par3, par4);
	}
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray[0] = par1IconRegister.registerIcon(func_111023_E() + "_top");
		iconArray[1] = par1IconRegister.registerIcon(func_111023_E() + "_side");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
	}
	
	public void updateLightLevel(World par1World, int par2, int par3, int par4)
	{
		if(!par1World.provider.hasNoSky)
		{
			int var5 = par1World.getBlockMetadata(par2, par3, par4);
			int var6 = par1World.getSavedLightValue(EnumSkyBlock.Sky, par2, par3, par4) - par1World.skylightSubtracted;
			float var7 = par1World.getCelestialAngleRadians(1.0F);
			if(var7 < (float) Math.PI)
			{
				var7 += (0.0F - var7) * 0.2F;
			} else
			{
				var7 += ((float) Math.PI * 2F - var7) * 0.2F;
			}
			var6 = Math.round(var6 * MathHelper.cos(var7));
			if(var6 < 0)
			{
				var6 = 0;
			}
			if(var6 > 15)
			{
				var6 = 15;
			}
			if(var5 != var6)
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 3);
			}
		}
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
	}
}
