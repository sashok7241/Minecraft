package net.minecraft.src;

public class BlockJukeBox extends BlockContainer
{
	private Icon theIcon;
	
	protected BlockJukeBox(int par1)
	{
		super(par1, Material.wood);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		ejectRecord(par1World, par2, par3, par4);
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	@Override public TileEntity createNewTileEntity(World par1World)
	{
		return new TileEntityRecordPlayer();
	}
	
	@Override public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		if(!par1World.isRemote)
		{
			super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, 0);
		}
	}
	
	public void ejectRecord(World par1World, int par2, int par3, int par4)
	{
		if(!par1World.isRemote)
		{
			TileEntityRecordPlayer var5 = (TileEntityRecordPlayer) par1World.getBlockTileEntity(par2, par3, par4);
			if(var5 != null)
			{
				ItemStack var6 = var5.func_96097_a();
				if(var6 != null)
				{
					par1World.playAuxSFX(1005, par2, par3, par4, 0);
					par1World.playRecord((String) null, par2, par3, par4);
					var5.func_96098_a((ItemStack) null);
					par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 2);
					float var7 = 0.7F;
					double var8 = par1World.rand.nextFloat() * var7 + (1.0F - var7) * 0.5D;
					double var10 = par1World.rand.nextFloat() * var7 + (1.0F - var7) * 0.2D + 0.6D;
					double var12 = par1World.rand.nextFloat() * var7 + (1.0F - var7) * 0.5D;
					ItemStack var14 = var6.copy();
					EntityItem var15 = new EntityItem(par1World, par2 + var8, par3 + var10, par4 + var12, var14);
					var15.delayBeforeCanPickup = 10;
					par1World.spawnEntityInWorld(var15);
				}
			}
		}
	}
	
	@Override public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		ItemStack var6 = ((TileEntityRecordPlayer) par1World.getBlockTileEntity(par2, par3, par4)).func_96097_a();
		return var6 == null ? 0 : var6.itemID + 1 - Item.record13.itemID;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? theIcon : blockIcon;
	}
	
	@Override public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	public void insertRecord(World par1World, int par2, int par3, int par4, ItemStack par5ItemStack)
	{
		if(!par1World.isRemote)
		{
			TileEntityRecordPlayer var6 = (TileEntityRecordPlayer) par1World.getBlockTileEntity(par2, par3, par4);
			if(var6 != null)
			{
				var6.func_96098_a(par5ItemStack.copy());
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 2);
			}
		}
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(par1World.getBlockMetadata(par2, par3, par4) == 0) return false;
		else
		{
			ejectRecord(par1World, par2, par3, par4);
			return true;
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("musicBlock");
		theIcon = par1IconRegister.registerIcon("jukebox_top");
	}
}
