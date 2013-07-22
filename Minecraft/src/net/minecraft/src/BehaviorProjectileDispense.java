package net.minecraft.src;

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem
{
	@Override public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
	{
		World var3 = par1IBlockSource.getWorld();
		IPosition var4 = BlockDispenser.getIPositionFromBlockSource(par1IBlockSource);
		EnumFacing var5 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
		IProjectile var6 = getProjectileEntity(var3, var4);
		var6.setThrowableHeading(var5.getFrontOffsetX(), var5.getFrontOffsetY() + 0.1F, var5.getFrontOffsetZ(), func_82500_b(), func_82498_a());
		var3.spawnEntityInWorld((Entity) var6);
		par2ItemStack.splitStack(1);
		return par2ItemStack;
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
	
	@Override protected void playDispenseSound(IBlockSource par1IBlockSource)
	{
		par1IBlockSource.getWorld().playAuxSFX(1002, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
	}
}
