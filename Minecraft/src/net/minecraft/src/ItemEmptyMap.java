package net.minecraft.src;

public class ItemEmptyMap extends ItemMapBase
{
	protected ItemEmptyMap(int p_i5083_1_)
	{
		super(p_i5083_1_);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		ItemStack var4 = new ItemStack(Item.map, 1, p_77659_2_.getUniqueDataId("map"));
		String var5 = "map_" + var4.getItemDamage();
		MapData var6 = new MapData(var5);
		p_77659_2_.setItemData(var5, var6);
		var6.scale = 0;
		int var7 = 128 * (1 << var6.scale);
		var6.xCenter = (int) (Math.round(p_77659_3_.posX / var7) * var7);
		var6.zCenter = (int) (Math.round(p_77659_3_.posZ / var7) * var7);
		var6.dimension = (byte) p_77659_2_.provider.dimensionId;
		var6.markDirty();
		--p_77659_1_.stackSize;
		if(p_77659_1_.stackSize <= 0) return var4;
		else
		{
			if(!p_77659_3_.inventory.addItemStackToInventory(var4.copy()))
			{
				p_77659_3_.dropPlayerItem(var4);
			}
			return p_77659_1_;
		}
	}
}
