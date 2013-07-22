package net.minecraft.src;

final class DispenserBehaviorMobEgg extends BehaviorDefaultDispenseItem
{
	@Override public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
	{
		EnumFacing var3 = BlockDispenser.getFacing(p_82487_1_.getBlockMetadata());
		double var4 = p_82487_1_.getX() + var3.getFrontOffsetX();
		double var6 = p_82487_1_.getYInt() + 0.2F;
		double var8 = p_82487_1_.getZ() + var3.getFrontOffsetZ();
		Entity var10 = ItemMonsterPlacer.spawnCreature(p_82487_1_.getWorld(), p_82487_2_.getItemDamage(), var4, var6, var8);
		if(var10 instanceof EntityLiving && p_82487_2_.hasDisplayName())
		{
			((EntityLiving) var10).func_94058_c(p_82487_2_.getDisplayName());
		}
		p_82487_2_.splitStack(1);
		return p_82487_2_;
	}
}
