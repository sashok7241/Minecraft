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
	
	@Override protected void entityInit()
	{
		getDataWatcher().addObjectByDataType(2, 5);
		getDataWatcher().addObject(3, Byte.valueOf((byte) 0));
	}
	
	@Override public void func_110128_b(Entity par1Entity)
	{
		ItemStack var2 = getDisplayedItem();
		if(par1Entity instanceof EntityPlayer)
		{
			EntityPlayer var3 = (EntityPlayer) par1Entity;
			if(var3.capabilities.isCreativeMode)
			{
				func_110131_b(var2);
				return;
			}
		}
		entityDropItem(new ItemStack(Item.itemFrame), 0.0F);
		if(var2 != null && rand.nextFloat() < itemDropChance)
		{
			var2 = var2.copy();
			func_110131_b(var2);
			entityDropItem(var2, 0.0F);
		}
	}
	
	private void func_110131_b(ItemStack par1ItemStack)
	{
		if(par1ItemStack != null)
		{
			if(par1ItemStack.itemID == Item.map.itemID)
			{
				MapData var2 = ((ItemMap) par1ItemStack.getItem()).getMapData(par1ItemStack, worldObj);
				var2.playersVisibleOnMap.remove("frame-" + entityId);
			}
			par1ItemStack.setItemFrame((EntityItemFrame) null);
		}
	}
	
	@Override public boolean func_130002_c(EntityPlayer par1EntityPlayer)
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
