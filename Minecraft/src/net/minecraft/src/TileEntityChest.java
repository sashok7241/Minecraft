package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class TileEntityChest extends TileEntity implements IInventory
{
	private ItemStack[] chestContents = new ItemStack[36];
	public boolean adjacentChestChecked = false;
	public TileEntityChest adjacentChestZNeg;
	public TileEntityChest adjacentChestXPos;
	public TileEntityChest adjacentChestXNeg;
	public TileEntityChest adjacentChestZPosition;
	public float lidAngle;
	public float prevLidAngle;
	public int numUsingPlayers;
	private int ticksSinceSync;
	private int field_94046_i = -1;
	private String field_94045_s;
	
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
	
	@Override public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	{
		if(chestContents[p_70298_1_] != null)
		{
			ItemStack var3;
			if(chestContents[p_70298_1_].stackSize <= p_70298_2_)
			{
				var3 = chestContents[p_70298_1_];
				chestContents[p_70298_1_] = null;
				onInventoryChanged();
				return var3;
			} else
			{
				var3 = chestContents[p_70298_1_].splitStack(p_70298_2_);
				if(chestContents[p_70298_1_].stackSize == 0)
				{
					chestContents[p_70298_1_] = null;
				}
				onInventoryChanged();
				return var3;
			}
		} else return null;
	}
	
	private void func_90009_a(TileEntityChest p_90009_1_, int p_90009_2_)
	{
		if(p_90009_1_.isInvalid())
		{
			adjacentChestChecked = false;
		} else if(adjacentChestChecked)
		{
			switch(p_90009_2_)
			{
				case 0:
					if(adjacentChestZPosition != p_90009_1_)
					{
						adjacentChestChecked = false;
					}
					break;
				case 1:
					if(adjacentChestXNeg != p_90009_1_)
					{
						adjacentChestChecked = false;
					}
					break;
				case 2:
					if(adjacentChestZNeg != p_90009_1_)
					{
						adjacentChestChecked = false;
					}
					break;
				case 3:
					if(adjacentChestXPos != p_90009_1_)
					{
						adjacentChestChecked = false;
					}
			}
		}
	}
	
	private boolean func_94044_a(int p_94044_1_, int p_94044_2_, int p_94044_3_)
	{
		Block var4 = Block.blocksList[worldObj.getBlockId(p_94044_1_, p_94044_2_, p_94044_3_)];
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
	
	@Override public ItemStack getStackInSlot(int p_70301_1_)
	{
		return chestContents[p_70301_1_];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		if(chestContents[p_70304_1_] != null)
		{
			ItemStack var2 = chestContents[p_70304_1_];
			chestContents[p_70304_1_] = null;
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
	
	@Override public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return true;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : p_70300_1_.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
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
	
	@Override public void readFromNBT(NBTTagCompound p_70307_1_)
	{
		super.readFromNBT(p_70307_1_);
		NBTTagList var2 = p_70307_1_.getTagList("Items");
		chestContents = new ItemStack[getSizeInventory()];
		if(p_70307_1_.hasKey("CustomName"))
		{
			field_94045_s = p_70307_1_.getString("CustomName");
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
	
	@Override public boolean receiveClientEvent(int p_70315_1_, int p_70315_2_)
	{
		if(p_70315_1_ == 1)
		{
			numUsingPlayers = p_70315_2_;
			return true;
		} else return super.receiveClientEvent(p_70315_1_, p_70315_2_);
	}
	
	public void setChestGuiName(String p_94043_1_)
	{
		field_94045_s = p_94043_1_;
	}
	
	@Override public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		chestContents[p_70299_1_] = p_70299_2_;
		if(p_70299_2_ != null && p_70299_2_.stackSize > getInventoryStackLimit())
		{
			p_70299_2_.stackSize = getInventoryStackLimit();
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
	
	@Override public void writeToNBT(NBTTagCompound p_70310_1_)
	{
		super.writeToNBT(p_70310_1_);
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
		p_70310_1_.setTag("Items", var2);
		if(isInvNameLocalized())
		{
			p_70310_1_.setString("CustomName", field_94045_s);
		}
	}
}
