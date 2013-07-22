package net.minecraft.src;

public class ItemCarrotOnAStick extends Item
{
	public ItemCarrotOnAStick(int p_i5082_1_)
	{
		super(p_i5082_1_);
		setCreativeTab(CreativeTabs.tabTransport);
		setMaxStackSize(1);
		setMaxDamage(25);
	}
	
	@Override public boolean isFull3D()
	{
		return true;
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		if(p_77659_3_.isRiding() && p_77659_3_.ridingEntity instanceof EntityPig)
		{
			EntityPig var4 = (EntityPig) p_77659_3_.ridingEntity;
			if(var4.getAIControlledByPlayer().isControlledByPlayer() && p_77659_1_.getMaxDamage() - p_77659_1_.getItemDamage() >= 7)
			{
				var4.getAIControlledByPlayer().boostSpeed();
				p_77659_1_.damageItem(7, p_77659_3_);
				if(p_77659_1_.stackSize == 0)
				{
					ItemStack var5 = new ItemStack(Item.fishingRod);
					var5.setTagCompound(p_77659_1_.stackTagCompound);
					return var5;
				}
			}
		}
		return p_77659_1_;
	}
	
	@Override public boolean shouldRotateAroundWhenRendering()
	{
		return true;
	}
}
