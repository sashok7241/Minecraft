package net.minecraft.src;

public class BlockJukeBox extends BlockContainer
{
	private Icon theIcon;
	
	protected BlockJukeBox(int p_i9084_1_)
	{
		super(p_i9084_1_, Material.wood);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		ejectRecord(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_);
		super.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		return new TileEntityRecordPlayer();
	}
	
	@Override public void dropBlockAsItemWithChance(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
	{
		if(!p_71914_1_.isRemote)
		{
			super.dropBlockAsItemWithChance(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, p_71914_5_, p_71914_6_, 0);
		}
	}
	
	public void ejectRecord(World p_72276_1_, int p_72276_2_, int p_72276_3_, int p_72276_4_)
	{
		if(!p_72276_1_.isRemote)
		{
			TileEntityRecordPlayer var5 = (TileEntityRecordPlayer) p_72276_1_.getBlockTileEntity(p_72276_2_, p_72276_3_, p_72276_4_);
			if(var5 != null)
			{
				ItemStack var6 = var5.func_96097_a();
				if(var6 != null)
				{
					p_72276_1_.playAuxSFX(1005, p_72276_2_, p_72276_3_, p_72276_4_, 0);
					p_72276_1_.playRecord((String) null, p_72276_2_, p_72276_3_, p_72276_4_);
					var5.func_96098_a((ItemStack) null);
					p_72276_1_.setBlockMetadataWithNotify(p_72276_2_, p_72276_3_, p_72276_4_, 0, 2);
					float var7 = 0.7F;
					double var8 = p_72276_1_.rand.nextFloat() * var7 + (1.0F - var7) * 0.5D;
					double var10 = p_72276_1_.rand.nextFloat() * var7 + (1.0F - var7) * 0.2D + 0.6D;
					double var12 = p_72276_1_.rand.nextFloat() * var7 + (1.0F - var7) * 0.5D;
					ItemStack var14 = var6.copy();
					EntityItem var15 = new EntityItem(p_72276_1_, p_72276_2_ + var8, p_72276_3_ + var10, p_72276_4_ + var12, var14);
					var15.delayBeforeCanPickup = 10;
					p_72276_1_.spawnEntityInWorld(var15);
				}
			}
		}
	}
	
	@Override public int getComparatorInputOverride(World p_94328_1_, int p_94328_2_, int p_94328_3_, int p_94328_4_, int p_94328_5_)
	{
		ItemStack var6 = ((TileEntityRecordPlayer) p_94328_1_.getBlockTileEntity(p_94328_2_, p_94328_3_, p_94328_4_)).func_96097_a();
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
	
	public void insertRecord(World p_85106_1_, int p_85106_2_, int p_85106_3_, int p_85106_4_, ItemStack p_85106_5_)
	{
		if(!p_85106_1_.isRemote)
		{
			TileEntityRecordPlayer var6 = (TileEntityRecordPlayer) p_85106_1_.getBlockTileEntity(p_85106_2_, p_85106_3_, p_85106_4_);
			if(var6 != null)
			{
				var6.func_96098_a(p_85106_5_.copy());
				p_85106_1_.setBlockMetadataWithNotify(p_85106_2_, p_85106_3_, p_85106_4_, 1, 2);
			}
		}
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		if(p_71903_1_.getBlockMetadata(p_71903_2_, p_71903_3_, p_71903_4_) == 0) return false;
		else
		{
			ejectRecord(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_);
			return true;
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("musicBlock");
		theIcon = par1IconRegister.registerIcon("jukebox_top");
	}
}
