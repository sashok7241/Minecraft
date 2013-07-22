package net.minecraft.src;

final class DispenserBehaviorFire extends BehaviorDefaultDispenseItem
{
	private boolean field_96466_b = true;
	
	@Override protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
	{
		EnumFacing var3 = BlockDispenser.getFacing(p_82487_1_.getBlockMetadata());
		World var4 = p_82487_1_.getWorld();
		int var5 = p_82487_1_.getXInt() + var3.getFrontOffsetX();
		int var6 = p_82487_1_.getYInt() + var3.getFrontOffsetY();
		int var7 = p_82487_1_.getZInt() + var3.getFrontOffsetZ();
		if(var4.isAirBlock(var5, var6, var7))
		{
			var4.setBlock(var5, var6, var7, Block.fire.blockID);
			if(p_82487_2_.attemptDamageItem(1, var4.rand))
			{
				p_82487_2_.stackSize = 0;
			}
		} else if(var4.getBlockId(var5, var6, var7) == Block.tnt.blockID)
		{
			Block.tnt.onBlockDestroyedByPlayer(var4, var5, var6, var7, 1);
			var4.setBlockToAir(var5, var6, var7);
		} else
		{
			field_96466_b = false;
		}
		return p_82487_2_;
	}
	
	@Override protected void playDispenseSound(IBlockSource p_82485_1_)
	{
		if(field_96466_b)
		{
			p_82485_1_.getWorld().playAuxSFX(1000, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
		} else
		{
			p_82485_1_.getWorld().playAuxSFX(1001, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
		}
	}
}
