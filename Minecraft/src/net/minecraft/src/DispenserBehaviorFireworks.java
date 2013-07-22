package net.minecraft.src;

final class DispenserBehaviorFireworks extends BehaviorDefaultDispenseItem
{
	@Override public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
	{
		EnumFacing var3 = BlockDispenser.getFacing(p_82487_1_.getBlockMetadata());
		double var4 = p_82487_1_.getX() + var3.getFrontOffsetX();
		double var6 = p_82487_1_.getYInt() + 0.2F;
		double var8 = p_82487_1_.getZ() + var3.getFrontOffsetZ();
		EntityFireworkRocket var10 = new EntityFireworkRocket(p_82487_1_.getWorld(), var4, var6, var8, p_82487_2_);
		p_82487_1_.getWorld().spawnEntityInWorld(var10);
		p_82487_2_.splitStack(1);
		return p_82487_2_;
	}
	
	@Override protected void playDispenseSound(IBlockSource p_82485_1_)
	{
		p_82485_1_.getWorld().playAuxSFX(1002, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
	}
}
