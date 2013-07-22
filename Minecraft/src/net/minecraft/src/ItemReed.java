package net.minecraft.src;

public class ItemReed extends Item
{
	private int spawnID;
	
	public ItemReed(int p_i3691_1_, Block p_i3691_2_)
	{
		super(p_i3691_1_);
		spawnID = p_i3691_2_.blockID;
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		int var11 = p_77648_3_.getBlockId(p_77648_4_, p_77648_5_, p_77648_6_);
		if(var11 == Block.snow.blockID && (p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_) & 7) < 1)
		{
			p_77648_7_ = 1;
		} else if(var11 != Block.vine.blockID && var11 != Block.tallGrass.blockID && var11 != Block.deadBush.blockID)
		{
			if(p_77648_7_ == 0)
			{
				--p_77648_5_;
			}
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
		}
		if(!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) return false;
		else if(p_77648_1_.stackSize == 0) return false;
		else
		{
			if(p_77648_3_.canPlaceEntityOnSide(spawnID, p_77648_4_, p_77648_5_, p_77648_6_, false, p_77648_7_, (Entity) null, p_77648_1_))
			{
				Block var12 = Block.blocksList[spawnID];
				int var13 = var12.onBlockPlaced(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, 0);
				if(p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, spawnID, var13, 3))
				{
					if(p_77648_3_.getBlockId(p_77648_4_, p_77648_5_, p_77648_6_) == spawnID)
					{
						Block.blocksList[spawnID].onBlockPlacedBy(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_2_, p_77648_1_);
						Block.blocksList[spawnID].onPostBlockPlaced(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, var13);
					}
					p_77648_3_.playSoundEffect(p_77648_4_ + 0.5F, p_77648_5_ + 0.5F, p_77648_6_ + 0.5F, var12.stepSound.getPlaceSound(), (var12.stepSound.getVolume() + 1.0F) / 2.0F, var12.stepSound.getPitch() * 0.8F);
					--p_77648_1_.stackSize;
				}
			}
			return true;
		}
	}
}
