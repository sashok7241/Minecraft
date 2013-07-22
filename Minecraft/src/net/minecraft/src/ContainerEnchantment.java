package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ContainerEnchantment extends Container
{
	public IInventory tableInventory = new SlotEnchantmentTable(this, "Enchant", true, 1);
	private World worldPointer;
	private int posX;
	private int posY;
	private int posZ;
	private Random rand = new Random();
	public long nameSeed;
	public int[] enchantLevels = new int[3];
	
	public ContainerEnchantment(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
	{
		worldPointer = par2World;
		posX = par3;
		posY = par4;
		posZ = par5;
		addSlotToContainer(new SlotEnchantment(this, tableInventory, 0, 25, 47));
		int var6;
		for(var6 = 0; var6 < 3; ++var6)
		{
			for(int var7 = 0; var7 < 9; ++var7)
			{
				addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
			}
		}
		for(var6 = 0; var6 < 9; ++var6)
		{
			addSlotToContainer(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 142));
		}
	}
	
	@Override public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, enchantLevels[0]);
		par1ICrafting.sendProgressBarUpdate(this, 1, enchantLevels[1]);
		par1ICrafting.sendProgressBarUpdate(this, 2, enchantLevels[2]);
	}
	
	@Override public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return worldPointer.getBlockId(posX, posY, posZ) != Block.enchantmentTable.blockID ? false : par1EntityPlayer.getDistanceSq(posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64.0D;
	}
	
	@Override public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int var1 = 0; var1 < crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting) crafters.get(var1);
			var2.sendProgressBarUpdate(this, 0, enchantLevels[0]);
			var2.sendProgressBarUpdate(this, 1, enchantLevels[1]);
			var2.sendProgressBarUpdate(this, 2, enchantLevels[2]);
		}
	}
	
	@Override public boolean enchantItem(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack var3 = tableInventory.getStackInSlot(0);
		if(enchantLevels[par2] > 0 && var3 != null && (par1EntityPlayer.experienceLevel >= enchantLevels[par2] || par1EntityPlayer.capabilities.isCreativeMode))
		{
			if(!worldPointer.isRemote)
			{
				List var4 = EnchantmentHelper.buildEnchantmentList(rand, var3, enchantLevels[par2]);
				boolean var5 = var3.itemID == Item.book.itemID;
				if(var4 != null)
				{
					par1EntityPlayer.addExperienceLevel(-enchantLevels[par2]);
					if(var5)
					{
						var3.itemID = Item.enchantedBook.itemID;
					}
					int var6 = var5 ? rand.nextInt(var4.size()) : -1;
					for(int var7 = 0; var7 < var4.size(); ++var7)
					{
						EnchantmentData var8 = (EnchantmentData) var4.get(var7);
						if(!var5 || var7 == var6)
						{
							if(var5)
							{
								Item.enchantedBook.getEnchantedItemStack_do(var3, var8);
							} else
							{
								var3.addEnchantment(var8.enchantmentobj, var8.enchantmentLevel);
							}
						}
					}
					onCraftMatrixChanged(tableInventory);
				}
			}
			return true;
		} else return false;
	}
	
	@Override public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		if(!worldPointer.isRemote)
		{
			ItemStack var2 = tableInventory.getStackInSlotOnClosing(0);
			if(var2 != null)
			{
				par1EntityPlayer.dropPlayerItem(var2);
			}
		}
	}
	
	@Override public void onCraftMatrixChanged(IInventory par1IInventory)
	{
		if(par1IInventory == tableInventory)
		{
			ItemStack var2 = par1IInventory.getStackInSlot(0);
			int var3;
			if(var2 != null && var2.isItemEnchantable())
			{
				nameSeed = rand.nextLong();
				if(!worldPointer.isRemote)
				{
					var3 = 0;
					int var4;
					for(var4 = -1; var4 <= 1; ++var4)
					{
						for(int var5 = -1; var5 <= 1; ++var5)
						{
							if((var4 != 0 || var5 != 0) && worldPointer.isAirBlock(posX + var5, posY, posZ + var4) && worldPointer.isAirBlock(posX + var5, posY + 1, posZ + var4))
							{
								if(worldPointer.getBlockId(posX + var5 * 2, posY, posZ + var4 * 2) == Block.bookShelf.blockID)
								{
									++var3;
								}
								if(worldPointer.getBlockId(posX + var5 * 2, posY + 1, posZ + var4 * 2) == Block.bookShelf.blockID)
								{
									++var3;
								}
								if(var5 != 0 && var4 != 0)
								{
									if(worldPointer.getBlockId(posX + var5 * 2, posY, posZ + var4) == Block.bookShelf.blockID)
									{
										++var3;
									}
									if(worldPointer.getBlockId(posX + var5 * 2, posY + 1, posZ + var4) == Block.bookShelf.blockID)
									{
										++var3;
									}
									if(worldPointer.getBlockId(posX + var5, posY, posZ + var4 * 2) == Block.bookShelf.blockID)
									{
										++var3;
									}
									if(worldPointer.getBlockId(posX + var5, posY + 1, posZ + var4 * 2) == Block.bookShelf.blockID)
									{
										++var3;
									}
								}
							}
						}
					}
					for(var4 = 0; var4 < 3; ++var4)
					{
						enchantLevels[var4] = EnchantmentHelper.calcItemStackEnchantability(rand, var4, var3, var2);
					}
					detectAndSendChanges();
				}
			} else
			{
				for(var3 = 0; var3 < 3; ++var3)
				{
					enchantLevels[var3] = 0;
				}
			}
		}
	}
	
	@Override public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot) inventorySlots.get(par2);
		if(var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			if(par2 == 0)
			{
				if(!mergeItemStack(var5, 1, 37, true)) return null;
			} else
			{
				if(((Slot) inventorySlots.get(0)).getHasStack() || !((Slot) inventorySlots.get(0)).isItemValid(var5)) return null;
				if(var5.hasTagCompound() && var5.stackSize == 1)
				{
					((Slot) inventorySlots.get(0)).putStack(var5.copy());
					var5.stackSize = 0;
				} else if(var5.stackSize >= 1)
				{
					((Slot) inventorySlots.get(0)).putStack(new ItemStack(var5.itemID, 1, var5.getItemDamage()));
					--var5.stackSize;
				}
			}
			if(var5.stackSize == 0)
			{
				var4.putStack((ItemStack) null);
			} else
			{
				var4.onSlotChanged();
			}
			if(var5.stackSize == var3.stackSize) return null;
			var4.onPickupFromSlot(par1EntityPlayer, var5);
		}
		return var3;
	}
	
	@Override public void updateProgressBar(int par1, int par2)
	{
		if(par1 >= 0 && par1 <= 2)
		{
			enchantLevels[par1] = par2;
		} else
		{
			super.updateProgressBar(par1, par2);
		}
	}
}
