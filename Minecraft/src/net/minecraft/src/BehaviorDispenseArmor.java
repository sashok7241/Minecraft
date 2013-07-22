package net.minecraft.src;

import java.util.List;

final class BehaviorDispenseArmor extends BehaviorDefaultDispenseItem
{
	@Override protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
	{
		EnumFacing var3 = BlockDispenser.getFacing(p_82487_1_.getBlockMetadata());
		int var4 = p_82487_1_.getXInt() + var3.getFrontOffsetX();
		int var5 = p_82487_1_.getYInt() + var3.getFrontOffsetY();
		int var6 = p_82487_1_.getZInt() + var3.getFrontOffsetZ();
		AxisAlignedBB var7 = AxisAlignedBB.getAABBPool().getAABB(var4, var5, var6, var4 + 1, var5 + 1, var6 + 1);
		List var8 = p_82487_1_.getWorld().selectEntitiesWithinAABB(EntityLiving.class, var7, new EntitySelectorArmoredMob(p_82487_2_));
		if(var8.size() > 0)
		{
			EntityLiving var9 = (EntityLiving) var8.get(0);
			int var10 = var9 instanceof EntityPlayer ? 1 : 0;
			int var11 = EntityLiving.getArmorPosition(p_82487_2_);
			ItemStack var12 = p_82487_2_.copy();
			var12.stackSize = 1;
			var9.setCurrentItemOrArmor(var11 - var10, var12);
			var9.setEquipmentDropChance(var11, 2.0F);
			--p_82487_2_.stackSize;
			return p_82487_2_;
		} else return super.dispenseStack(p_82487_1_, p_82487_2_);
	}
}
