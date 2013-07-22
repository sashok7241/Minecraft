package net.minecraft.src;

public class ItemMinecart extends Item
{
	private static final IBehaviorDispenseItem dispenserMinecartBehavior = new BehaviorDispenseMinecart();
	public int minecartType;
	
	public ItemMinecart(int p_i3670_1_, int p_i3670_2_)
	{
		super(p_i3670_1_);
		maxStackSize = 1;
		minecartType = p_i3670_2_;
		setCreativeTab(CreativeTabs.tabTransport);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserMinecartBehavior);
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		int var11 = p_77648_3_.getBlockId(p_77648_4_, p_77648_5_, p_77648_6_);
		if(BlockRailBase.isRailBlock(var11))
		{
			if(!p_77648_3_.isRemote)
			{
				EntityMinecart var12 = EntityMinecart.createMinecart(p_77648_3_, p_77648_4_ + 0.5F, p_77648_5_ + 0.5F, p_77648_6_ + 0.5F, minecartType);
				if(p_77648_1_.hasDisplayName())
				{
					var12.setMinecartName(p_77648_1_.getDisplayName());
				}
				p_77648_3_.spawnEntityInWorld(var12);
			}
			--p_77648_1_.stackSize;
			return true;
		} else return false;
	}
}
