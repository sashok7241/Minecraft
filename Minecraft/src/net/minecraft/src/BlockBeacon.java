package net.minecraft.src;

public class BlockBeacon extends BlockContainer
{
	private Icon theIcon;
	
	public BlockBeacon(int p_i5099_1_)
	{
		super(p_i5099_1_, Material.glass);
		setHardness(3.0F);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		return new TileEntityBeacon();
	}
	
	public Icon getBeaconIcon()
	{
		return theIcon;
	}
	
	@Override public int getRenderType()
	{
		return 34;
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
			TileEntityBeacon var10 = (TileEntityBeacon) p_71903_1_.getBlockTileEntity(p_71903_2_, p_71903_3_, p_71903_4_);
			if(var10 != null)
			{
				p_71903_5_.displayGUIBeacon(var10);
			}
			return true;
		}
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		super.onBlockPlacedBy(p_71860_1_, p_71860_2_, p_71860_3_, p_71860_4_, p_71860_5_, p_71860_6_);
		if(p_71860_6_.hasDisplayName())
		{
			((TileEntityBeacon) p_71860_1_.getBlockTileEntity(p_71860_2_, p_71860_3_, p_71860_4_)).func_94047_a(p_71860_6_.getDisplayName());
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
		theIcon = par1IconRegister.registerIcon("beacon");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
}
