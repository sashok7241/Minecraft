package net.minecraft.src;

public class BlockBeacon extends BlockContainer
{
	private Icon theIcon;
	
	public BlockBeacon(int par1)
	{
		super(par1, Material.glass);
		setHardness(3.0F);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override public TileEntity createNewTileEntity(World par1World)
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
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(par1World.isRemote) return true;
		else
		{
			TileEntityBeacon var10 = (TileEntityBeacon) par1World.getBlockTileEntity(par2, par3, par4);
			if(var10 != null)
			{
				par5EntityPlayer.displayGUIBeacon(var10);
			}
			return true;
		}
	}
	
	@Override public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack)
	{
		super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving, par6ItemStack);
		if(par6ItemStack.hasDisplayName())
		{
			((TileEntityBeacon) par1World.getBlockTileEntity(par2, par3, par4)).func_94047_a(par6ItemStack.getDisplayName());
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
