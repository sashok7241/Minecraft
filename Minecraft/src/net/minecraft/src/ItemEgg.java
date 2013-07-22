package net.minecraft.src;

public class ItemEgg extends Item
{
	public ItemEgg(int p_i3646_1_)
	{
		super(p_i3646_1_);
		maxStackSize = 16;
		setCreativeTab(CreativeTabs.tabMaterials);
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
			p_77659_2_.spawnEntityInWorld(new EntityEgg(p_77659_2_, p_77659_3_));
		}
		return p_77659_1_;
	}
}
