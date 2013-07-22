package net.minecraft.src;

public class ItemExpBottle extends Item
{
	public ItemExpBottle(int p_i3649_1_)
	{
		super(p_i3649_1_);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override public boolean hasEffect(ItemStack par1ItemStack)
	{
		return true;
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		if(!p_77659_3_.capabilities.isCreativeMode)
		{
			--p_77659_1_.stackSize;
		}
		p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if(!p_77659_2_.isRemote)
		{
			p_77659_2_.spawnEntityInWorld(new EntityExpBottle(p_77659_2_, p_77659_3_));
		}
		return p_77659_1_;
	}
}
