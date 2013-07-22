package net.minecraft.src;

public abstract class EntityAgeable extends EntityCreature
{
	private float field_98056_d = -1.0F;
	private float field_98057_e;
	
	public EntityAgeable(World par1World)
	{
		super(par1World);
	}
	
	public abstract EntityAgeable createChild(EntityAgeable var1);
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(12, new Integer(0));
	}
	
	public void func_98054_a(boolean par1)
	{
		func_98055_j(par1 ? 0.5F : 1.0F);
	}
	
	private void func_98055_j(float par1)
	{
		super.setSize(field_98056_d * par1, field_98057_e * par1);
	}
	
	public int getGrowingAge()
	{
		return dataWatcher.getWatchableObjectInt(12);
	}
	
	@Override public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
		if(var2 != null && var2.itemID == Item.monsterPlacer.itemID && !worldObj.isRemote)
		{
			Class var3 = EntityList.getClassFromID(var2.getItemDamage());
			if(var3 != null && var3.isAssignableFrom(this.getClass()))
			{
				EntityAgeable var4 = createChild(this);
				if(var4 != null)
				{
					var4.setGrowingAge(-24000);
					var4.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
					worldObj.spawnEntityInWorld(var4);
					if(var2.hasDisplayName())
					{
						var4.func_94058_c(var2.getDisplayName());
					}
					if(!par1EntityPlayer.capabilities.isCreativeMode)
					{
						--var2.stackSize;
						if(var2.stackSize <= 0)
						{
							par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack) null);
						}
					}
				}
			}
		}
		return super.interact(par1EntityPlayer);
	}
	
	@Override public boolean isChild()
	{
		return getGrowingAge() < 0;
	}
	
	@Override public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(worldObj.isRemote)
		{
			func_98054_a(isChild());
		} else
		{
			int var1 = getGrowingAge();
			if(var1 < 0)
			{
				++var1;
				setGrowingAge(var1);
			} else if(var1 > 0)
			{
				--var1;
				setGrowingAge(var1);
			}
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		setGrowingAge(par1NBTTagCompound.getInteger("Age"));
	}
	
	public void setGrowingAge(int par1)
	{
		dataWatcher.updateObject(12, Integer.valueOf(par1));
		func_98054_a(isChild());
	}
	
	@Override protected final void setSize(float par1, float par2)
	{
		boolean var3 = field_98056_d > 0.0F;
		field_98056_d = par1;
		field_98057_e = par2;
		if(!var3)
		{
			func_98055_j(1.0F);
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("Age", getGrowingAge());
	}
}
