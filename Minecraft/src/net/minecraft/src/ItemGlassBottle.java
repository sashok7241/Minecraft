package net.minecraft.src;

public class ItemGlassBottle extends Item
{
	public ItemGlassBottle(int p_i3622_1_)
	{
		super(p_i3622_1_);
		setCreativeTab(CreativeTabs.tabBrewing);
	}
	
	@Override public Icon getIconFromDamage(int par1)
	{
		return Item.potion.getIconFromDamage(0);
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		MovingObjectPosition var4 = getMovingObjectPositionFromPlayer(p_77659_2_, p_77659_3_, true);
		if(var4 == null) return p_77659_1_;
		else
		{
			if(var4.typeOfHit == EnumMovingObjectType.TILE)
			{
				int var5 = var4.blockX;
				int var6 = var4.blockY;
				int var7 = var4.blockZ;
				if(!p_77659_2_.canMineBlock(p_77659_3_, var5, var6, var7)) return p_77659_1_;
				if(!p_77659_3_.canPlayerEdit(var5, var6, var7, var4.sideHit, p_77659_1_)) return p_77659_1_;
				if(p_77659_2_.getBlockMaterial(var5, var6, var7) == Material.water)
				{
					--p_77659_1_.stackSize;
					if(p_77659_1_.stackSize <= 0) return new ItemStack(Item.potion);
					if(!p_77659_3_.inventory.addItemStackToInventory(new ItemStack(Item.potion)))
					{
						p_77659_3_.dropPlayerItem(new ItemStack(Item.potion.itemID, 1, 0));
					}
				}
			}
			return p_77659_1_;
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
	}
}
