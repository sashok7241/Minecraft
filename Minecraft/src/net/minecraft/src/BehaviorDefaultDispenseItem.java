package net.minecraft.src;

public class BehaviorDefaultDispenseItem implements IBehaviorDispenseItem
{
	@Override public final ItemStack dispense(IBlockSource p_82482_1_, ItemStack p_82482_2_)
	{
		ItemStack var3 = dispenseStack(p_82482_1_, p_82482_2_);
		playDispenseSound(p_82482_1_);
		spawnDispenseParticles(p_82482_1_, BlockDispenser.getFacing(p_82482_1_.getBlockMetadata()));
		return var3;
	}
	
	protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
	{
		EnumFacing var3 = BlockDispenser.getFacing(p_82487_1_.getBlockMetadata());
		IPosition var4 = BlockDispenser.getIPositionFromBlockSource(p_82487_1_);
		ItemStack var5 = p_82487_2_.splitStack(1);
		doDispense(p_82487_1_.getWorld(), var5, 6, var3, var4);
		return p_82487_2_;
	}
	
	private int func_82488_a(EnumFacing p_82488_1_)
	{
		return p_82488_1_.getFrontOffsetX() + 1 + (p_82488_1_.getFrontOffsetZ() + 1) * 3;
	}
	
	protected void playDispenseSound(IBlockSource p_82485_1_)
	{
		p_82485_1_.getWorld().playAuxSFX(1000, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
	}
	
	protected void spawnDispenseParticles(IBlockSource p_82489_1_, EnumFacing p_82489_2_)
	{
		p_82489_1_.getWorld().playAuxSFX(2000, p_82489_1_.getXInt(), p_82489_1_.getYInt(), p_82489_1_.getZInt(), func_82488_a(p_82489_2_));
	}
	
	public static void doDispense(World p_82486_0_, ItemStack p_82486_1_, int p_82486_2_, EnumFacing p_82486_3_, IPosition p_82486_4_)
	{
		double var5 = p_82486_4_.getX();
		double var7 = p_82486_4_.getY();
		double var9 = p_82486_4_.getZ();
		EntityItem var11 = new EntityItem(p_82486_0_, var5, var7 - 0.3D, var9, p_82486_1_);
		double var12 = p_82486_0_.rand.nextDouble() * 0.1D + 0.2D;
		var11.motionX = p_82486_3_.getFrontOffsetX() * var12;
		var11.motionY = 0.20000000298023224D;
		var11.motionZ = p_82486_3_.getFrontOffsetZ() * var12;
		var11.motionX += p_82486_0_.rand.nextGaussian() * 0.007499999832361937D * p_82486_2_;
		var11.motionY += p_82486_0_.rand.nextGaussian() * 0.007499999832361937D * p_82486_2_;
		var11.motionZ += p_82486_0_.rand.nextGaussian() * 0.007499999832361937D * p_82486_2_;
		p_82486_0_.spawnEntityInWorld(var11);
	}
}
