package net.minecraft.src;

import java.util.Random;

public class BlockDaylightDetector extends BlockContainer
{
	private Icon[] iconArray = new Icon[2];
	
	public BlockDaylightDetector(int p_i9049_1_)
	{
		super(p_i9049_1_, Material.wood);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override public boolean canProvidePower()
	{
		return true;
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
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
	
	@Override public int isProvidingWeakPower(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
	{
		return p_71865_1_.getBlockMetadata(p_71865_2_, p_71865_3_, p_71865_4_);
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray[0] = par1IconRegister.registerIcon("daylightDetector_top");
		iconArray[1] = par1IconRegister.registerIcon("daylightDetector_side");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
	}
	
	public void updateLightLevel(World p_94444_1_, int p_94444_2_, int p_94444_3_, int p_94444_4_)
	{
		if(!p_94444_1_.provider.hasNoSky)
		{
			int var5 = p_94444_1_.getBlockMetadata(p_94444_2_, p_94444_3_, p_94444_4_);
			int var6 = p_94444_1_.getSavedLightValue(EnumSkyBlock.Sky, p_94444_2_, p_94444_3_, p_94444_4_) - p_94444_1_.skylightSubtracted;
			float var7 = p_94444_1_.getCelestialAngleRadians(1.0F);
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
				p_94444_1_.setBlockMetadataWithNotify(p_94444_2_, p_94444_3_, p_94444_4_, var6, 3);
			}
		}
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
	}
}
