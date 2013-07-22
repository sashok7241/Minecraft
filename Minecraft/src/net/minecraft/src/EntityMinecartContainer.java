package net.minecraft.src;

public abstract class EntityMinecartContainer extends EntityMinecart implements IInventory
{
	private ItemStack[] minecartContainerItems = new ItemStack[36];
	private boolean dropContentsWhenDead = true;
	
	public EntityMinecartContainer(World p_i10051_1_)
	{
		super(p_i10051_1_);
	}
	
	public EntityMinecartContainer(World p_i10052_1_, double p_i10052_2_, double p_i10052_4_, double p_i10052_6_)
	{
		super(p_i10052_1_, p_i10052_2_, p_i10052_4_, p_i10052_6_);
	}
	
	@Override protected void applyDrag()
	{
		int var1 = 15 - Container.calcRedstoneFromInventory(this);
		float var2 = 0.98F + var1 * 0.001F;
		motionX *= var2;
		motionY *= 0.0D;
		motionZ *= var2;
	}
	
	@Override public void closeChest()
	{
	}
	
	@Override public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	{
		if(minecartContainerItems[p_70298_1_] != null)
		{
			ItemStack var3;
			if(minecartContainerItems[p_70298_1_].stackSize <= p_70298_2_)
			{
				var3 = minecartContainerItems[p_70298_1_];
				minecartContainerItems[p_70298_1_] = null;
				return var3;
			} else
			{
				var3 = minecartContainerItems[p_70298_1_].splitStack(p_70298_2_);
				if(minecartContainerItems[p_70298_1_].stackSize == 0)
				{
					minecartContainerItems[p_70298_1_] = null;
				}
				return var3;
			}
		} else return null;
	}
	
	@Override public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override public String getInvName()
	{
		return isInvNameLocalized() ? func_95999_t() : "container.minecart";
	}
	
	@Override public ItemStack getStackInSlot(int p_70301_1_)
	{
		return minecartContainerItems[p_70301_1_];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		if(minecartContainerItems[p_70304_1_] != null)
		{
			ItemStack var2 = minecartContainerItems[p_70304_1_];
			minecartContainerItems[p_70304_1_] = null;
			return var2;
		} else return null;
	}
	
	@Override public boolean interact(EntityPlayer p_70085_1_)
	{
		if(!worldObj.isRemote)
		{
			p_70085_1_.displayGUIChest(this);
		}
		return true;
	}
	
	@Override public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return true;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return isDead ? false : p_70300_1_.getDistanceSqToEntity(this) <= 64.0D;
	}
	
	@Override public void killMinecart(DamageSource p_94095_1_)
	{
		super.killMinecart(p_94095_1_);
		for(int var2 = 0; var2 < getSizeInventory(); ++var2)
		{
			ItemStack var3 = getStackInSlot(var2);
			if(var3 != null)
			{
				float var4 = rand.nextFloat() * 0.8F + 0.1F;
				float var5 = rand.nextFloat() * 0.8F + 0.1F;
				float var6 = rand.nextFloat() * 0.8F + 0.1F;
				while(var3.stackSize > 0)
				{
					int var7 = rand.nextInt(21) + 10;
					if(var7 > var3.stackSize)
					{
						var7 = var3.stackSize;
					}
					var3.stackSize -= var7;
					EntityItem var8 = new EntityItem(worldObj, posX + var4, posY + var5, posZ + var6, new ItemStack(var3.itemID, var7, var3.getItemDamage()));
					float var9 = 0.05F;
					var8.motionX = (float) rand.nextGaussian() * var9;
					var8.motionY = (float) rand.nextGaussian() * var9 + 0.2F;
					var8.motionZ = (float) rand.nextGaussian() * var9;
					worldObj.spawnEntityInWorld(var8);
				}
			}
		}
	}
	
	@Override public void onInventoryChanged()
	{
	}
	
	@Override public void openChest()
	{
	}
	
	@Override protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		NBTTagList var2 = p_70037_1_.getTagList("Items");
		minecartContainerItems = new ItemStack[getSizeInventory()];
		for(int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			int var5 = var4.getByte("Slot") & 255;
			if(var5 >= 0 && var5 < minecartContainerItems.length)
			{
				minecartContainerItems[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}
	
	@Override public void setDead()
	{
		if(dropContentsWhenDead)
		{
			for(int var1 = 0; var1 < getSizeInventory(); ++var1)
			{
				ItemStack var2 = getStackInSlot(var1);
				if(var2 != null)
				{
					float var3 = rand.nextFloat() * 0.8F + 0.1F;
					float var4 = rand.nextFloat() * 0.8F + 0.1F;
					float var5 = rand.nextFloat() * 0.8F + 0.1F;
					while(var2.stackSize > 0)
					{
						int var6 = rand.nextInt(21) + 10;
						if(var6 > var2.stackSize)
						{
							var6 = var2.stackSize;
						}
						var2.stackSize -= var6;
						EntityItem var7 = new EntityItem(worldObj, posX + var3, posY + var4, posZ + var5, new ItemStack(var2.itemID, var6, var2.getItemDamage()));
						if(var2.hasTagCompound())
						{
							var7.getEntityItem().setTagCompound((NBTTagCompound) var2.getTagCompound().copy());
						}
						float var8 = 0.05F;
						var7.motionX = (float) rand.nextGaussian() * var8;
						var7.motionY = (float) rand.nextGaussian() * var8 + 0.2F;
						var7.motionZ = (float) rand.nextGaussian() * var8;
						worldObj.spawnEntityInWorld(var7);
					}
				}
			}
		}
		super.setDead();
	}
	
	@Override public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		minecartContainerItems[p_70299_1_] = p_70299_2_;
		if(p_70299_2_ != null && p_70299_2_.stackSize > getInventoryStackLimit())
		{
			p_70299_2_.stackSize = getInventoryStackLimit();
		}
	}
	
	@Override public void travelToDimension(int p_71027_1_)
	{
		dropContentsWhenDead = false;
		super.travelToDimension(p_71027_1_);
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		NBTTagList var2 = new NBTTagList();
		for(int var3 = 0; var3 < minecartContainerItems.length; ++var3)
		{
			if(minecartContainerItems[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				minecartContainerItems[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		p_70014_1_.setTag("Items", var2);
	}
}
