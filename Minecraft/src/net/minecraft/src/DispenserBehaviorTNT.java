package net.minecraft.src;

final class DispenserBehaviorTNT extends BehaviorDefaultDispenseItem
{
	@Override protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
	{
		EnumFacing var3 = BlockDispenser.getFacing(p_82487_1_.getBlockMetadata());
		World var4 = p_82487_1_.getWorld();
		int var5 = p_82487_1_.getXInt() + var3.getFrontOffsetX();
		int var6 = p_82487_1_.getYInt() + var3.getFrontOffsetY();
		int var7 = p_82487_1_.getZInt() + var3.getFrontOffsetZ();
		EntityTNTPrimed var8 = new EntityTNTPrimed(var4, var5 + 0.5F, var6 + 0.5F, var7 + 0.5F, (EntityLiving) null);
		var4.spawnEntityInWorld(var8);
		--p_82487_2_.stackSize;
		return p_82487_2_;
	}
}
