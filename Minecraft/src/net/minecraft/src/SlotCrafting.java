package net.minecraft.src;

public class SlotCrafting extends Slot
{
	private final IInventory craftMatrix;
	private EntityPlayer thePlayer;
	private int amountCrafted;
	
	public SlotCrafting(EntityPlayer par1EntityPlayer, IInventory par2IInventory, IInventory par3IInventory, int par4, int par5, int par6)
	{
		super(par3IInventory, par4, par5, par6);
		thePlayer = par1EntityPlayer;
		craftMatrix = par2IInventory;
	}
	
	@Override public ItemStack decrStackSize(int par1)
	{
		if(getHasStack())
		{
			amountCrafted += Math.min(par1, getStack().stackSize);
		}
		return super.decrStackSize(par1);
	}
	
	@Override public boolean isItemValid(ItemStack par1ItemStack)
	{
		return false;
	}
	
	@Override protected void onCrafting(ItemStack par1ItemStack)
	{
		par1ItemStack.onCrafting(thePlayer.worldObj, thePlayer, amountCrafted);
		amountCrafted = 0;
		if(par1ItemStack.itemID == Block.workbench.blockID)
		{
			thePlayer.addStat(AchievementList.buildWorkBench, 1);
		} else if(par1ItemStack.itemID == Item.pickaxeWood.itemID)
		{
			thePlayer.addStat(AchievementList.buildPickaxe, 1);
		} else if(par1ItemStack.itemID == Block.furnaceIdle.blockID)
		{
			thePlayer.addStat(AchievementList.buildFurnace, 1);
		} else if(par1ItemStack.itemID == Item.hoeWood.itemID)
		{
			thePlayer.addStat(AchievementList.buildHoe, 1);
		} else if(par1ItemStack.itemID == Item.bread.itemID)
		{
			thePlayer.addStat(AchievementList.makeBread, 1);
		} else if(par1ItemStack.itemID == Item.cake.itemID)
		{
			thePlayer.addStat(AchievementList.bakeCake, 1);
		} else if(par1ItemStack.itemID == Item.pickaxeStone.itemID)
		{
			thePlayer.addStat(AchievementList.buildBetterPickaxe, 1);
		} else if(par1ItemStack.itemID == Item.swordWood.itemID)
		{
			thePlayer.addStat(AchievementList.buildSword, 1);
		} else if(par1ItemStack.itemID == Block.enchantmentTable.blockID)
		{
			thePlayer.addStat(AchievementList.enchantments, 1);
		} else if(par1ItemStack.itemID == Block.bookShelf.blockID)
		{
			thePlayer.addStat(AchievementList.bookcase, 1);
		}
	}
	
	@Override protected void onCrafting(ItemStack par1ItemStack, int par2)
	{
		amountCrafted += par2;
		this.onCrafting(par1ItemStack);
	}
	
	@Override public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
	{
		this.onCrafting(par2ItemStack);
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
