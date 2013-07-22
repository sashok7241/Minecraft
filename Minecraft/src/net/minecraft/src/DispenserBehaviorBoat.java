package net.minecraft.src;

final class DispenserBehaviorBoat extends BehaviorDefaultDispenseItem
{
	private final BehaviorDefaultDispenseItem defaultDispenserItemBehavior = new BehaviorDefaultDispenseItem();
	
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
		Material var14 = var4.getBlockMaterial(var11, var12, var13);
		double var15;
		if(Material.water.equals(var14))
		{
			var15 = 1.0D;
		} else
		{
			if(!Material.air.equals(var14) || !Material.water.equals(var4.getBlockMaterial(var11, var12 - 1, var13))) return defaultDispenserItemBehavior.dispense(p_82487_1_, p_82487_2_);
			var15 = 0.0D;
		}
		EntityBoat var17 = new EntityBoat(var4, var5, var7 + var15, var9);
		var4.spawnEntityInWorld(var17);
		p_82487_2_.splitStack(1);
		return p_82487_2_;
	}
	
	@Override protected void playDispenseSound(IBlockSource p_82485_1_)
	{
		p_82485_1_.getWorld().playAuxSFX(1000, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
	}
}
