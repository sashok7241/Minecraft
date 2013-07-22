package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class TileEntityChest extends TileEntity implements IInventory
{
	private ItemStack[] chestContents = new ItemStack[36];
	public boolean adjacentChestChecked;
	public TileEntityChest adjacentChestZNeg;
	public TileEntityChest adjacentChestXPos;
	public TileEntityChest adjacentChestXNeg;
	public TileEntityChest adjacentChestZPosition;
	public float lidAngle;
	public float prevLidAngle;
	public int numUsingPlayers;
	private int ticksSinceSync;
	private int field_94046_i;
	private String field_94045_s;
	
	public TileEntityChest()
	{
		field_94046_i = -1;
	}
	
	public TileEntityChest(int par1)
	{
		field_94046_i = par1;
	}
	
	public void checkForAdjacentChests()
	{
		if(!adjacentChestChecked)
		{
			adjacentChestChecked = true;
			adjacentChestZNeg = null;
			adjacentChestXPos = null;
			adjacentChestXNeg = null;
			adjacentChestZPosition = null;
			if(func_94044_a(xCoord - 1, yCoord, zCoord))
			{
				adjacentChestXNeg = (TileEntityChest) worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord);
			}
			if(func_94044_a(xCoord + 1, yCoord, zCoord))
			{
				adjacentChestXPos = (TileEntityChest) worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord);
			}
			if(func_94044_a(xCoord, yCoord, zCoord - 1))
			{
				adjacentChestZNeg = (TileEntityChest) worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1);
			}
			if(func_94044_a(xCoord, yCoord, zCoord + 1))
			{
				adjacentChestZPosition = (TileEntityChest) worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1);
			}
			if(adjacentChestZNeg != null)
			{
				adjacentChestZNeg.func_90009_a(this, 0);
			}
			if(adjacentChestZPosition != null)
			{
				adjacentChestZPosition.func_90009_a(this, 2);
			}
			if(adjacentChestXPos != null)
			{
				adjacentChestXPos.func_90009_a(this, 1);
			}
			if(adjacentChestXNeg != null)
			{
				adjacentChestXNeg.func_90009_a(this, 3);
			}
		}
	}
	
	@Override public void closeChest()
	{
		if(getBlockType() != null && getBlockType() instanceof BlockChest)
		{
			--numUsingPlayers;
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType().blockID, 1, numUsingPlayers);
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType().blockID);
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, getBlockType().blockID);
		}
	}
	
	@Override public ItemStack decrStackSize(int par1, int par2)
	{
		if(chestContents[par1] != null)
		{
			ItemStack var3;
			if(chestContents[par1].stackSize <= par2)
			{
				var3 = chestContents[par1];
				chestContents[par1] = null;
				onInventoryChanged();
				return var3;
			} else
			{
				var3 = chestContents[par1].splitStack(par2);
				if(chestContents[par1].stackSize == 0)
				{
					chestContents[par1] = null;
				}
				onInventoryChanged();
				return var3;
			}
		} else return null;
	}
	
	private void func_90009_a(TileEntityChest par1TileEntityChest, int par2)
	{
		if(par1TileEntityChest.isInvalid())
		{
			adjacentChestChecked = false;
		} else if(adjacentChestChecked)
		{
			switch(par2)
			{
				case 0:
					if(adjacentChestZPosition != par1TileEntityChest)
					{
						adjacentChestChecked = false;
					}
					break;
				case 1:
					if(adjacentChestXNeg != par1TileEntityChest)
					{
						adjacentChestChecked = false;
					}
					break;
				case 2:
					if(adjacentChestZNeg != par1TileEntityChest)
					{
						adjacentChestChecked = false;
					}
					break;
				case 3:
					if(adjacentChestXPos != par1TileEntityChest)
					{
						adjacentChestChecked = false;
					}
			}
		}
	}
	
	private boolean func_94044_a(int par1, int par2, int par3)
	{
		Block var4 = Block.blocksList[worldObj.getBlockId(par1, par2, par3)];
		return var4 != null && var4 instanceof BlockChest ? ((BlockChest) var4).isTrapped == func_98041_l() : false;
	}
	
	public int func_98041_l()
	{
		if(field_94046_i == -1)
		{
			if(worldObj == null || !(getBlockType() instanceof BlockChest)) return 0;
			field_94046_i = ((BlockChest) getBlockType()).isTrapped;
		}
		return field_94046_i;
	}
	
	@Override public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override public String getInvName()
	{
		return isInvNameLocalized() ? field_94045_s : "container.chest";
	}
	
	@Override public int getSizeInventory()
	{
		return 27;
	}
	
	@Override public ItemStack getStackInSlot(int par1)
	{
		return chestContents[par1];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int par1)
	{
		if(chestContents[par1] != null)
		{
			ItemStack var2 = chestContents[par1];
			chestContents[par1] = null;
			return var2;
		} else return null;
	}
	
	@Override public void invalidate()
	{
		super.invalidate();
		updateContainingBlockInfo();
		checkForAdjacentChests();
	}
	
	@Override public boolean isInvNameLocalized()
	{
		return field_94045_s != null && field_94045_s.length() > 0;
	}
	
	@Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return true;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : par1EntityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}
	
	@Override public void openChest()
	{
		if(numUsingPlayers < 0)
		{
			numUsingPlayers = 0;
		}
		++numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType().blockID, 1, numUsingPlayers);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType().blockID);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, getBlockType().blockID);
	}
	
	@Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		chestContents = new ItemStack[getSizeInventory()];
		if(par1NBTTagCompound.hasKey("CustomName"))
		{
			field_94045_s = par1NBTTagCompound.getString("CustomName");
		}
		for(int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			int var5 = var4.getByte("Slot") & 255;
			if(var5 >= 0 && var5 < chestContents.length)
			{
				chestContents[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}
	
	@Override public boolean receiveClientEvent(int par1, int par2)
	{
		if(par1 == 1)
		{
			numUsingPlayers = par2;
			return true;
		} else return super.receiveClientEvent(par1, par2);
	}
	
	public void setChestGuiName(String par1Str)
	{
		field_94045_s = par1Str;
	}
	
	@Override public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		chestContents[par1] = par2ItemStack;
		if(par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
		{
			par2ItemStack.stackSize = getInventoryStackLimit();
		}
		onInventoryChanged();
	}
	
	@Override public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
		adjacentChestChecked = false;
	}
	
	@Override public void updateEntity()
	{
		super.updateEntity();
		checkForAdjacentChests();
		++ticksSinceSync;
		float var1;
		if(!worldObj.isRemote && numUsingPlayers != 0 && (ticksSinceSync + xCoord + yCoord + zCoord) % 200 == 0)
		{
			numUsingPlayers = 0;
			var1 = 5.0F;
			List var2 = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB(xCoord - var1, yCoord - var1, zCoord - var1, xCoord + 1 + var1, yCoord + 1 + var1, zCoord + 1 + var1));
			Iterator var3 = var2.iterator();
			while(var3.hasNext())
			{
				EntityPlayer var4 = (EntityPlayer) var3.next();
				if(var4.openContainer instanceof ContainerChest)
				{
					IInventory var5 = ((ContainerChest) var4.openContainer).getLowerChestInventory();
					if(var5 == this || var5 instanceof InventoryLargeChest && ((InventoryLargeChest) var5).isPartOfLargeChest(this))
					{
						++numUsingPlayers;
					}
				}
			}
		}
		prevLidAngle = lidAngle;
		var1 = 0.1F;
		double var11;
		if(numUsingPlayers > 0 && lidAngle == 0.0F && adjacentChestZNeg == null && adjacentChestXNeg == null)
		{
			double var8 = xCoord + 0.5D;
			var11 = zCoord + 0.5D;
			if(adjacentChestZPosition != null)
			{
				var11 += 0.5D;
			}
			if(adjacentChestXPos != null)
			{
				var8 += 0.5D;
			}
			worldObj.playSoundEffect(var8, yCoord + 0.5D, var11, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
		if(numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F)
		{
			float var9 = lidAngle;
			if(numUsingPlayers > 0)
			{
				lidAngle += var1;
			} else
			{
				lidAngle -= var1;
			}
			if(lidAngle > 1.0F)
			{
				lidAngle = 1.0F;
			}
			float var10 = 0.5F;
			if(lidAngle < var10 && var9 >= var10 && adjacentChestZNeg == null && adjacentChestXNeg == null)
			{
				var11 = xCoord + 0.5D;
				double var6 = zCoord + 0.5D;
				if(adjacentChestZPosition != null)
				{
					var6 += 0.5D;
				}
				if(adjacentChestXPos != null)
				{
					var11 += 0.5D;
				}
				worldObj.playSoundEffect(var11, yCoord + 0.5D, var6, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
			if(lidAngle < 0.0F)
			{
				lidAngle = 0.0F;
			}
		}
	}
	
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList var2 = new NBTTagList();
		for(int var3 = 0; var3 < chestContents.length; ++var3)
		{
			if(chestContents[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				chestContents[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		par1NBTTagCompound.setTag("Items", var2);
		if(isInvNameLocalized())
		{
			par1NBTTagCompound.setString("CustomName", field_94045_s);
		}
	}
}
