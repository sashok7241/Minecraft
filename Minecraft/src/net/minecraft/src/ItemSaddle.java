package net.minecraft.src;

public class ItemSaddle extends Item
{
	public ItemSaddle(int p_i3679_1_)
	{
		super(p_i3679_1_);
		maxStackSize = 1;
		setCreativeTab(CreativeTabs.tabTransport);
	}
	
	@Override public boolean hitEntity(ItemStack p_77644_1_, EntityLiving p_77644_2_, EntityLiving p_77644_3_)
	{
		itemInteractionForEntity(p_77644_1_, p_77644_2_);
		return true;
	}
	
	@Override public boolean itemInteractionForEntity(ItemStack p_77646_1_, EntityLiving p_77646_2_)
	{
		if(p_77646_2_ instanceof EntityPig)
		{
			EntityPig var3 = (EntityPig) p_77646_2_;
			if(!var3.getSaddled() && !var3.isChild())
			{
				var3.setSaddled(true);
				--p_77646_1_.stackSize;
			}
			return true;
		} else return false;
	}
}
