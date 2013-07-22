package net.minecraft.src;

public class SlotCrafting extends Slot
{
	private final IInventory craftMatrix;
	private EntityPlayer thePlayer;
	private int amountCrafted;
	
	public SlotCrafting(EntityPlayer p_i3615_1_, IInventory p_i3615_2_, IInventory p_i3615_3_, int p_i3615_4_, int p_i3615_5_, int p_i3615_6_)
	{
		super(p_i3615_3_, p_i3615_4_, p_i3615_5_, p_i3615_6_);
		thePlayer = p_i3615_1_;
		craftMatrix = p_i3615_2_;
	}
	
	@Override public ItemStack decrStackSize(int p_75209_1_)
	{
		if(getHasStack())
		{
			amountCrafted += Math.min(p_75209_1_, getStack().stackSize);
		}
		return super.decrStackSize(p_75209_1_);
	}
	
	@Override public boolean isItemValid(ItemStack p_75214_1_)
	{
		return false;
	}
	
	@Override protected void onCrafting(ItemStack p_75208_1_)
	{
		p_75208_1_.onCrafting(thePlayer.worldObj, thePlayer, amountCrafted);
		amountCrafted = 0;
		if(p_75208_1_.itemID == Block.workbench.blockID)
		{
			thePlayer.addStat(AchievementList.buildWorkBench, 1);
		} else if(p_75208_1_.itemID == Item.pickaxeWood.itemID)
		{
			thePlayer.addStat(AchievementList.buildPickaxe, 1);
		} else if(p_75208_1_.itemID == Block.furnaceIdle.blockID)
		{
			thePlayer.addStat(AchievementList.buildFurnace, 1);
		} else if(p_75208_1_.itemID == Item.hoeWood.itemID)
		{
			thePlayer.addStat(AchievementList.buildHoe, 1);
		} else if(p_75208_1_.itemID == Item.bread.itemID)
		{
			thePlayer.addStat(AchievementList.makeBread, 1);
		} else if(p_75208_1_.itemID == Item.cake.itemID)
		{
			thePlayer.addStat(AchievementList.bakeCake, 1);
		} else if(p_75208_1_.itemID == Item.pickaxeStone.itemID)
		{
			thePlayer.addStat(AchievementList.buildBetterPickaxe, 1);
		} else if(p_75208_1_.itemID == Item.swordWood.itemID)
		{
			thePlayer.addStat(AchievementList.buildSword, 1);
		} else if(p_75208_1_.itemID == Block.enchantmentTable.blockID)
		{
			thePlayer.addStat(AchievementList.enchantments, 1);
		} else if(p_75208_1_.itemID == Block.bookShelf.blockID)
		{
			thePlayer.addStat(AchievementList.bookcase, 1);
		}
	}
	
	@Override protected void onCrafting(ItemStack p_75210_1_, int p_75210_2_)
	{
		amountCrafted += p_75210_2_;
		this.onCrafting(p_75210_1_);
	}
	
	@Override public void onPickupFromSlot(EntityPlayer p_82870_1_, ItemStack p_82870_2_)
	{
		this.onCrafting(p_82870_2_);
		for(int var3 = 0; var3 < craftMatrix.getSizeInventory(); ++var3)
		{
			ItemStack var4 = craftMatrix.getStackInSlot(var3);
			if(var4 != null)
			{
				craftMatrix.decrStackSize(var3, 1);
				if(var4.getItem().hasContainerItem())
				{
					ItemStack var5 = new ItemStack(var4.getItem().getContainerItem());
					if(!var4.getItem().doesContainerItemLeaveCraftingGrid(var4) || !thePlayer.inventory.addItemStackToInventory(var5))
					{
						if(craftMatrix.getStackInSlot(var3) == null)
						{
							craftMatrix.setInventorySlotContents(var3, var5);
						} else
						{
							thePlayer.dropPlayerItem(var5);
						}
					}
				}
			}
		}
	}
}
