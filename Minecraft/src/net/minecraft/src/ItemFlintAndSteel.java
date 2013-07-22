package net.minecraft.src;

public class ItemFlintAndSteel extends Item
{
	public ItemFlintAndSteel(int p_i3652_1_)
	{
		super(p_i3652_1_);
		maxStackSize = 1;
		setMaxDamage(64);
		setCreativeTab(CreativeTabs.tabTools);
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
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
		if(!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) return false;
		else
		{
			int var11 = p_77648_3_.getBlockId(p_77648_4_, p_77648_5_, p_77648_6_);
			if(var11 == 0)
			{
				p_77648_3_.playSoundEffect(p_77648_4_ + 0.5D, p_77648_5_ + 0.5D, p_77648_6_ + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, Block.fire.blockID);
			}
			p_77648_1_.damageItem(1, p_77648_2_);
			return true;
		}
	}
}
