package net.minecraft.src;

public class ItemLilyPad extends ItemColored
{
	public ItemLilyPad(int p_i3694_1_)
	{
		super(p_i3694_1_, false);
	}
	
	@Override public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		return Block.waterlily.getRenderColor(par1ItemStack.getItemDamage());
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		MovingObjectPosition var4 = getMovingObjectPositionFromPlayer(p_77659_2_, p_77659_3_, true);
		if(var4 == null) return p_77659_1_;
		else
		{
			if(var4.typeOfHit == EnumMovingObjectType.TILE)
			{
				int var5 = var4.blockX;
				int var6 = var4.blockY;
				int var7 = var4.blockZ;
				if(!p_77659_2_.canMineBlock(p_77659_3_, var5, var6, var7)) return p_77659_1_;
				if(!p_77659_3_.canPlayerEdit(var5, var6, var7, var4.sideHit, p_77659_1_)) return p_77659_1_;
				if(p_77659_2_.getBlockMaterial(var5, var6, var7) == Material.water && p_77659_2_.getBlockMetadata(var5, var6, var7) == 0 && p_77659_2_.isAirBlock(var5, var6 + 1, var7))
				{
					p_77659_2_.setBlock(var5, var6 + 1, var7, Block.waterlily.blockID);
					if(!p_77659_3_.capabilities.isCreativeMode)
					{
						--p_77659_1_.stackSize;
					}
				}
			}
			return p_77659_1_;
		}
	}
}
