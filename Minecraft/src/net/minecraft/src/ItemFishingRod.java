package net.minecraft.src;

public class ItemFishingRod extends Item
{
	private Icon theIcon;
	
	public ItemFishingRod(int p_i3651_1_)
	{
		super(p_i3651_1_);
		setMaxDamage(64);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabTools);
	}
	
	public Icon func_94597_g()
	{
		return theIcon;
	}
	
	@Override public boolean isFull3D()
	{
		return true;
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		if(p_77659_3_.fishEntity != null)
		{
			int var4 = p_77659_3_.fishEntity.catchFish();
			p_77659_1_.damageItem(var4, p_77659_3_);
			p_77659_3_.swingItem();
		} else
		{
			p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if(!p_77659_2_.isRemote)
			{
				p_77659_2_.spawnEntityInWorld(new EntityFishHook(p_77659_2_, p_77659_3_));
			}
			p_77659_3_.swingItem();
		}
		return p_77659_1_;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
		theIcon = par1IconRegister.registerIcon("fishingRod_empty");
	}
	
	@Override public boolean shouldRotateAroundWhenRendering()
	{
		return true;
	}
}
