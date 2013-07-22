package net.minecraft.src;

final class DispenserBehaviorTNT extends BehaviorDefaultDispenseItem
{
	@Override protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
	{
		EnumFacing var3 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
		World var4 = par1IBlockSource.getWorld();
		int var5 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
		int var6 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
		int var7 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
		EntityTNTPrimed var8 = new EntityTNTPrimed(var4, var5 + 0.5F, var6 + 0.5F, var7 + 0.5F, (EntityLiving) null);
		var4.spawnEntityInWorld(var8);
		--par2ItemStack.stackSize;
		return par2ItemStack;
	}
}
