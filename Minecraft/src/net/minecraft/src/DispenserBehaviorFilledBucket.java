package net.minecraft.src;

final class DispenserBehaviorFilledBucket extends BehaviorDefaultDispenseItem
{
	private final BehaviorDefaultDispenseItem defaultDispenserItemBehavior = new BehaviorDefaultDispenseItem();
	
	@Override public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
	{
		ItemBucket var3 = (ItemBucket) p_82487_2_.getItem();
		int var4 = p_82487_1_.getXInt();
		int var5 = p_82487_1_.getYInt();
		int var6 = p_82487_1_.getZInt();
		EnumFacing var7 = BlockDispenser.getFacing(p_82487_1_.getBlockMetadata());
		if(var3.tryPlaceContainedLiquid(p_82487_1_.getWorld(), var4, var5, var6, var4 + var7.getFrontOffsetX(), var5 + var7.getFrontOffsetY(), var6 + var7.getFrontOffsetZ()))
		{
			p_82487_2_.itemID = Item.bucketEmpty.itemID;
			p_82487_2_.stackSize = 1;
			return p_82487_2_;
		} else return defaultDispenserItemBehavior.dispense(p_82487_1_, p_82487_2_);
	}
}
