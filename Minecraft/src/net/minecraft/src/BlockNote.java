package net.minecraft.src;

public class BlockNote extends BlockContainer
{
	public BlockNote(int p_i3972_1_)
	{
		super(p_i3972_1_, Material.wood);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		return new TileEntityNote();
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		if(p_71903_1_.isRemote) return true;
		else
		{
			TileEntityNote var10 = (TileEntityNote) p_71903_1_.getBlockTileEntity(p_71903_2_, p_71903_3_, p_71903_4_);
			if(var10 != null)
			{
				var10.changePitch();
				var10.triggerNote(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_);
			}
			return true;
		}
	}
	
	@Override public void onBlockClicked(World p_71921_1_, int p_71921_2_, int p_71921_3_, int p_71921_4_, EntityPlayer p_71921_5_)
	{
		if(!p_71921_1_.isRemote)
		{
			TileEntityNote var6 = (TileEntityNote) p_71921_1_.getBlockTileEntity(p_71921_2_, p_71921_3_, p_71921_4_);
			if(var6 != null)
			{
				var6.triggerNote(p_71921_1_, p_71921_2_, p_71921_3_, p_71921_4_);
			}
		}
	}
	
	@Override public boolean onBlockEventReceived(World p_71883_1_, int p_71883_2_, int p_71883_3_, int p_71883_4_, int p_71883_5_, int p_71883_6_)
	{
		float var7 = (float) Math.pow(2.0D, (p_71883_6_ - 12) / 12.0D);
		String var8 = "harp";
		if(p_71883_5_ == 1)
		{
			var8 = "bd";
		}
		if(p_71883_5_ == 2)
		{
			var8 = "snare";
		}
		if(p_71883_5_ == 3)
		{
			var8 = "hat";
		}
		if(p_71883_5_ == 4)
		{
			var8 = "bassattack";
		}
		p_71883_1_.playSoundEffect(p_71883_2_ + 0.5D, p_71883_3_ + 0.5D, p_71883_4_ + 0.5D, "note." + var8, 3.0F, var7);
		p_71883_1_.spawnParticle("note", p_71883_2_ + 0.5D, p_71883_3_ + 1.2D, p_71883_4_ + 0.5D, p_71883_6_ / 24.0D, 0.0D, 0.0D);
		return true;
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		boolean var6 = p_71863_1_.isBlockIndirectlyGettingPowered(p_71863_2_, p_71863_3_, p_71863_4_);
		TileEntityNote var7 = (TileEntityNote) p_71863_1_.getBlockTileEntity(p_71863_2_, p_71863_3_, p_71863_4_);
		if(var7 != null && var7.previousRedstoneState != var6)
		{
			if(var6)
			{
				var7.triggerNote(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);
			}
			var7.previousRedstoneState = var6;
		}
	}
}
