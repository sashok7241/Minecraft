package net.minecraft.src;

public class EntityItemFrame extends EntityHanging
{
	private float itemDropChance = 1.0F;
	
	public EntityItemFrame(World par1World)
	{
		super(par1World);
	}
	
	public EntityItemFrame(World par1World, int par2, int par3, int par4, int par5)
	{
		super(par1World, par2, par3, par4, par5);
		setDirection(par5);
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
	
	@Override public boolean interact(EntityPlayer par1EntityPlayer)
	{
		if(getDisplayedItem() == null)
		{
			ItemStack var2 = par1EntityPlayer.getHeldItem();
			if(var2 != null && !worldObj.isRemote)
			{
				setDisplayedItem(var2);
				if(!par1EntityPlayer.capabilities.isCreativeMode && --var2.stackSize <= 0)
				{
					par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack) null);
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
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Item");
		if(var2 != null && !var2.hasNoTags())
		{
			setDisplayedItem(ItemStack.loadItemStackFromNBT(var2));
			setItemRotation(par1NBTTagCompound.getByte("ItemRotation"));
			if(par1NBTTagCompound.hasKey("ItemDropChance"))
			{
				itemDropChance = par1NBTTagCompound.getFloat("ItemDropChance");
			}
		}
		super.readEntityFromNBT(par1NBTTagCompound);
	}
	
	public void setDisplayedItem(ItemStack par1ItemStack)
	{
		par1ItemStack = par1ItemStack.copy();
		par1ItemStack.stackSize = 1;
		par1ItemStack.setItemFrame(this);
		getDataWatcher().updateObject(2, par1ItemStack);
		getDataWatcher().setObjectWatched(2);
	}
	
	public void setItemRotation(int par1)
	{
		getDataWatcher().updateObject(3, Byte.valueOf((byte) (par1 % 4)));
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		if(getDisplayedItem() != null)
		{
			par1NBTTagCompound.setCompoundTag("Item", getDisplayedItem().writeToNBT(new NBTTagCompound()));
			par1NBTTagCompound.setByte("ItemRotation", (byte) getRotation());
			par1NBTTagCompound.setFloat("ItemDropChance", itemDropChance);
		}
		super.writeEntityToNBT(par1NBTTagCompound);
	}
}
