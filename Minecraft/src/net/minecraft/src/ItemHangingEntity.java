package net.minecraft.src;

public class ItemHangingEntity extends Item
{
	private final Class hangingEntityClass;
	
	public ItemHangingEntity(int p_i5084_1_, Class p_i5084_2_)
	{
		super(p_i5084_1_);
		hangingEntityClass = p_i5084_2_;
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	private EntityHanging createHangingEntity(World p_82810_1_, int p_82810_2_, int p_82810_3_, int p_82810_4_, int p_82810_5_)
	{
		return hangingEntityClass == EntityPainting.class ? new EntityPainting(p_82810_1_, p_82810_2_, p_82810_3_, p_82810_4_, p_82810_5_) : hangingEntityClass == EntityItemFrame.class ? new EntityItemFrame(p_82810_1_, p_82810_2_, p_82810_3_, p_82810_4_, p_82810_5_) : null;
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if(p_77648_7_ == 0) return false;
		else if(p_77648_7_ == 1) return false;
		else
		{
			int var11 = Direction.facingToDirection[p_77648_7_];
			EntityHanging var12 = createHangingEntity(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, var11);
			if(!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) return false;
			else
			{
				if(var12 != null && var12.onValidSurface())
				{
					if(!p_77648_3_.isRemote)
					{
						p_77648_3_.spawnEntityInWorld(var12);
					}
					--p_77648_1_.stackSize;
				}
				return true;
			}
		}
	}
}
