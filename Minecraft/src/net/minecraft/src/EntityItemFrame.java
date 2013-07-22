package net.minecraft.src;

public class EntityItemFrame extends EntityHanging
{
	private float itemDropChance = 1.0F;
	
	public EntityItemFrame(World p_i5055_1_)
	{
		super(p_i5055_1_);
	}
	
	public EntityItemFrame(World p_i5056_1_, int p_i5056_2_, int p_i5056_3_, int p_i5056_4_, int p_i5056_5_)
	{
		super(p_i5056_1_, p_i5056_2_, p_i5056_3_, p_i5056_4_, p_i5056_5_);
		setDirection(p_i5056_5_);
	}
	
	@Override public void dropItemStack()
	{
		entityDropItem(new ItemStack(Item.itemFrame), 0.0F);
		ItemStack var1 = getDisplayedItem();
		if(var1 != null && rand.nextFloat() < itemDropChance)
		{
			var1 = var1.copy();
			var1.setItemFrame((EntityItemFrame) null);
			entityDropItem(var1, 0.0F);
		}
	}
	
	@Override protected void entityInit()
	{
		getDataWatcher().addObjectByDataType(2, 5);
		getDataWatcher().addObject(3, Byte.valueOf((byte) 0));
	}
	
	@Override public int func_82329_d()
	{
		return 9;
	}
	
	@Override public int func_82330_g()
	{
		return 9;
	}
	
	public ItemStack getDisplayedItem()
	{
		return getDataWatcher().getWatchableObjectItemStack(2);
	}
	
	public int getRotation()
	{
		return getDataWatcher().getWatchableObjectByte(3);
	}
	
	@Override public boolean interact(EntityPlayer p_70085_1_)
	{
		if(getDisplayedItem() == null)
		{
			ItemStack var2 = p_70085_1_.getHeldItem();
			if(var2 != null && !worldObj.isRemote)
			{
				setDisplayedItem(var2);
				if(!p_70085_1_.capabilities.isCreativeMode && --var2.stackSize <= 0)
				{
					p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, (ItemStack) null);
				}
			}
		} else if(!worldObj.isRemote)
		{
			setItemRotation(getRotation() + 1);
		}
		return true;
	}
	
	@Override public boolean isInRangeToRenderDist(double par1)
	{
		double var3 = 16.0D;
		var3 *= 64.0D * renderDistanceWeight;
		return par1 < var3 * var3;
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		NBTTagCompound var2 = p_70037_1_.getCompoundTag("Item");
		if(var2 != null && !var2.hasNoTags())
		{
			setDisplayedItem(ItemStack.loadItemStackFromNBT(var2));
			setItemRotation(p_70037_1_.getByte("ItemRotation"));
			if(p_70037_1_.hasKey("ItemDropChance"))
			{
				itemDropChance = p_70037_1_.getFloat("ItemDropChance");
			}
		}
		super.readEntityFromNBT(p_70037_1_);
	}
	
	public void setDisplayedItem(ItemStack p_82334_1_)
	{
		p_82334_1_ = p_82334_1_.copy();
		p_82334_1_.stackSize = 1;
		p_82334_1_.setItemFrame(this);
		getDataWatcher().updateObject(2, p_82334_1_);
		getDataWatcher().setObjectWatched(2);
	}
	
	public void setItemRotation(int p_82336_1_)
	{
		getDataWatcher().updateObject(3, Byte.valueOf((byte) (p_82336_1_ % 4)));
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		if(getDisplayedItem() != null)
		{
			p_70014_1_.setCompoundTag("Item", getDisplayedItem().writeToNBT(new NBTTagCompound()));
			p_70014_1_.setByte("ItemRotation", (byte) getRotation());
			p_70014_1_.setFloat("ItemDropChance", itemDropChance);
		}
		super.writeEntityToNBT(p_70014_1_);
	}
}
