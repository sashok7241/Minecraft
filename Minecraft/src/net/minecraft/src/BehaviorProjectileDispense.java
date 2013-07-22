package net.minecraft.src;

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem
{
	@Override public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
	{
		World var3 = p_82487_1_.getWorld();
		IPosition var4 = BlockDispenser.getIPositionFromBlockSource(p_82487_1_);
		EnumFacing var5 = BlockDispenser.getFacing(p_82487_1_.getBlockMetadata());
		IProjectile var6 = getProjectileEntity(var3, var4);
		var6.setThrowableHeading(var5.getFrontOffsetX(), var5.getFrontOffsetY() + 0.1F, var5.getFrontOffsetZ(), func_82500_b(), func_82498_a());
		var3.spawnEntityInWorld((Entity) var6);
		p_82487_2_.splitStack(1);
		return p_82487_2_;
	}
	
	protected float func_82498_a()
	{
		return 6.0F;
	}
	
	protected float func_82500_b()
	{
		return 1.1F;
	}
	
	protected abstract IProjectile getProjectileEntity(World var1, IPosition var2);
	
	@Override protected void playDispenseSound(IBlockSource p_82485_1_)
	{
		p_82485_1_.getWorld().playAuxSFX(1002, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
	}
}
