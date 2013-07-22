package net.minecraft.src;

final class DispenserBehaviorDye extends BehaviorDefaultDispenseItem
{
	private boolean field_96461_b = true;
	
	@Override protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
	{
		if(p_82487_2_.getItemDamage() == 15)
		{
			EnumFacing var3 = BlockDispenser.getFacing(p_82487_1_.getBlockMetadata());
			World var4 = p_82487_1_.getWorld();
			int var5 = p_82487_1_.getXInt() + var3.getFrontOffsetX();
			int var6 = p_82487_1_.getYInt() + var3.getFrontOffsetY();
			int var7 = p_82487_1_.getZInt() + var3.getFrontOffsetZ();
			if(ItemDye.func_96604_a(p_82487_2_, var4, var5, var6, var7))
			{
				if(!var4.isRemote)
				{
					var4.playAuxSFX(2005, var5, var6, var7, 0);
				}
			} else
			{
				field_96461_b = false;
			}
			return p_82487_2_;
		} else return super.dispenseStack(p_82487_1_, p_82487_2_);
	}
	
	@Override protected void playDispenseSound(IBlockSource p_82485_1_)
	{
		if(field_96461_b)
		{
			p_82485_1_.getWorld().playAuxSFX(1000, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
		} else
		{
			p_82485_1_.getWorld().playAuxSFX(1001, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
		}
	}
}
