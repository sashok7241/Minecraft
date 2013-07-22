package net.minecraft.src;

public class ItemSnow extends ItemBlockWithMetadata
{
	public ItemSnow(int p_i10058_1_, Block p_i10058_2_)
	{
		super(p_i10058_1_, p_i10058_2_);
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if(p_77648_1_.stackSize == 0) return false;
		else if(!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) return false;
		else
		{
			int var11 = p_77648_3_.getBlockId(p_77648_4_, p_77648_5_, p_77648_6_);
			if(var11 == Block.snow.blockID)
			{
				Block var12 = Block.blocksList[getBlockID()];
				int var13 = p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_);
				int var14 = var13 & 7;
				if(var14 <= 6 && p_77648_3_.checkNoEntityCollision(var12.getCollisionBoundingBoxFromPool(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) && p_77648_3_.setBlockMetadataWithNotify(p_77648_4_, p_77648_5_, p_77648_6_, var14 + 1 | var13 & -8, 2))
				{
					p_77648_3_.playSoundEffect(p_77648_4_ + 0.5F, p_77648_5_ + 0.5F, p_77648_6_ + 0.5F, var12.stepSound.getPlaceSound(), (var12.stepSound.getVolume() + 1.0F) / 2.0F, var12.stepSound.getPitch() * 0.8F);
					--p_77648_1_.stackSize;
					return true;
				}
			}
			return super.onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
		}
	}
}
