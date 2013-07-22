package net.minecraft.src;

final class BehaviorDispenseMinecart extends BehaviorDefaultDispenseItem
{
	private final BehaviorDefaultDispenseItem field_96465_b = new BehaviorDefaultDispenseItem();
	
	@Override public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
	{
		EnumFacing var3 = BlockDispenser.getFacing(p_82487_1_.getBlockMetadata());
		World var4 = p_82487_1_.getWorld();
		double var5 = p_82487_1_.getX() + var3.getFrontOffsetX() * 1.125F;
		double var7 = p_82487_1_.getY() + var3.getFrontOffsetY() * 1.125F;
		double var9 = p_82487_1_.getZ() + var3.getFrontOffsetZ() * 1.125F;
		int var11 = p_82487_1_.getXInt() + var3.getFrontOffsetX();
		int var12 = p_82487_1_.getYInt() + var3.getFrontOffsetY();
		int var13 = p_82487_1_.getZInt() + var3.getFrontOffsetZ();
		int var14 = var4.getBlockId(var11, var12, var13);
		double var15;
		if(BlockRailBase.isRailBlock(var14))
		{
			var15 = 0.0D;
		} else
		{
			if(var14 != 0 || !BlockRailBase.isRailBlock(var4.getBlockId(var11, var12 - 1, var13))) return field_96465_b.dispense(p_82487_1_, p_82487_2_);
			var15 = -1.0D;
		}
		EntityMinecart var17 = EntityMinecart.createMinecart(var4, var5, var7 + var15, var9, ((ItemMinecart) p_82487_2_.getItem()).minecartType);
		var4.spawnEntityInWorld(var17);
		p_82487_2_.splitStack(1);
		return p_82487_2_;
	}
	
	@Override protected void playDispenseSound(IBlockSource p_82485_1_)
	{
		p_82485_1_.getWorld().playAuxSFX(1000, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
	}
}
