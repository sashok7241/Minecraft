package net.minecraft.src;

public class ItemSeedFood extends ItemFood
{
	private int cropId;
	private int soilId;
	
	public ItemSeedFood(int p_i5086_1_, int p_i5086_2_, float p_i5086_3_, int p_i5086_4_, int p_i5086_5_)
	{
		super(p_i5086_1_, p_i5086_2_, p_i5086_3_, false);
		cropId = p_i5086_4_;
		soilId = p_i5086_5_;
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if(p_77648_7_ != 1) return false;
		else if(p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) && p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_ + 1, p_77648_6_, p_77648_7_, p_77648_1_))
		{
			int var11 = p_77648_3_.getBlockId(p_77648_4_, p_77648_5_, p_77648_6_);
			if(var11 == soilId && p_77648_3_.isAirBlock(p_77648_4_, p_77648_5_ + 1, p_77648_6_))
			{
				p_77648_3_.setBlock(p_77648_4_, p_77648_5_ + 1, p_77648_6_, cropId);
				--p_77648_1_.stackSize;
				return true;
			} else return false;
		} else return false;
	}
}
