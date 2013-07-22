package net.minecraft.src;

final class DispenserBehaviorEmptyBucket extends BehaviorDefaultDispenseItem
{
	private final BehaviorDefaultDispenseItem defaultDispenserItemBehavior = new BehaviorDefaultDispenseItem();
	
	@Override public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
	{
		EnumFacing var3 = BlockDispenser.getFacing(p_82487_1_.getBlockMetadata());
		World var4 = p_82487_1_.getWorld();
		int var5 = p_82487_1_.getXInt() + var3.getFrontOffsetX();
		int var6 = p_82487_1_.getYInt() + var3.getFrontOffsetY();
		int var7 = p_82487_1_.getZInt() + var3.getFrontOffsetZ();
		Material var8 = var4.getBlockMaterial(var5, var6, var7);
		int var9 = var4.getBlockMetadata(var5, var6, var7);
		Item var10;
		if(Material.water.equals(var8) && var9 == 0)
		{
			var10 = Item.bucketWater;
		} else
		{
			if(!Material.lava.equals(var8) || var9 != 0) return super.dispenseStack(p_82487_1_, p_82487_2_);
			var10 = Item.bucketLava;
		}
		var4.setBlockToAir(var5, var6, var7);
		if(--p_82487_2_.stackSize == 0)
		{
			p_82487_2_.itemID = var10.itemID;
			p_82487_2_.stackSize = 1;
		} else if(((TileEntityDispenser) p_82487_1_.getBlockTileEntity()).addItem(new ItemStack(var10)) < 0)
		{
			defaultDispenserItemBehavior.dispense(p_82487_1_, new ItemStack(var10));
		}
		return p_82487_2_;
	}
}
