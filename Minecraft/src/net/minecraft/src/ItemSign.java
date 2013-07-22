package net.minecraft.src;

public class ItemSign extends Item
{
	public ItemSign(int p_i3685_1_)
	{
		super(p_i3685_1_);
		maxStackSize = 16;
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if(p_77648_7_ == 0) return false;
		else if(!p_77648_3_.getBlockMaterial(p_77648_4_, p_77648_5_, p_77648_6_).isSolid()) return false;
		else
		{
			if(p_77648_7_ == 1)
			{
				++p_77648_5_;
			}
			if(p_77648_7_ == 2)
			{
				--p_77648_6_;
			}
			if(p_77648_7_ == 3)
			{
				++p_77648_6_;
			}
			if(p_77648_7_ == 4)
			{
				--p_77648_4_;
			}
			if(p_77648_7_ == 5)
			{
				++p_77648_4_;
			}
			if(!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) return false;
			else if(!Block.signPost.canPlaceBlockAt(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) return false;
			else
			{
				if(p_77648_7_ == 1)
				{
					int var11 = MathHelper.floor_double((p_77648_2_.rotationYaw + 180.0F) * 16.0F / 360.0F + 0.5D) & 15;
					p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, Block.signPost.blockID, var11, 2);
				} else
				{
					p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, Block.signWall.blockID, p_77648_7_, 2);
				}
				--p_77648_1_.stackSize;
				TileEntitySign var12 = (TileEntitySign) p_77648_3_.getBlockTileEntity(p_77648_4_, p_77648_5_, p_77648_6_);
				if(var12 != null)
				{
					p_77648_2_.displayGUIEditSign(var12);
				}
				return true;
			}
		}
	}
}
