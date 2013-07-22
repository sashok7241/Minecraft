package net.minecraft.src;

import java.util.Random;

final class DispenserBehaviorFireball extends BehaviorDefaultDispenseItem
{
	@Override public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
	{
		EnumFacing var3 = BlockDispenser.getFacing(p_82487_1_.getBlockMetadata());
		IPosition var4 = BlockDispenser.getIPositionFromBlockSource(p_82487_1_);
		double var5 = var4.getX() + var3.getFrontOffsetX() * 0.3F;
		double var7 = var4.getY() + var3.getFrontOffsetX() * 0.3F;
		double var9 = var4.getZ() + var3.getFrontOffsetZ() * 0.3F;
		World var11 = p_82487_1_.getWorld();
		Random var12 = var11.rand;
		double var13 = var12.nextGaussian() * 0.05D + var3.getFrontOffsetX();
		double var15 = var12.nextGaussian() * 0.05D + var3.getFrontOffsetY();
		double var17 = var12.nextGaussian() * 0.05D + var3.getFrontOffsetZ();
		var11.spawnEntityInWorld(new EntitySmallFireball(var11, var5, var7, var9, var13, var15, var17));
		p_82487_2_.splitStack(1);
		return p_82487_2_;
	}
	
	@Override protected void playDispenseSound(IBlockSource p_82485_1_)
	{
		p_82485_1_.getWorld().playAuxSFX(1009, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
	}
}
