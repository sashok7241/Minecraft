package net.minecraft.src;

public abstract class EntityAgeable extends EntityCreature
{
	private float field_98056_d = -1.0F;
	private float field_98057_e;
	
	public EntityAgeable(World p_i3436_1_)
	{
		super(p_i3436_1_);
	}
	
	public abstract EntityAgeable createChild(EntityAgeable var1);
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(12, new Integer(0));
	}
	
	public void func_98054_a(boolean p_98054_1_)
	{
		func_98055_j(p_98054_1_ ? 0.5F : 1.0F);
	}
	
	private void func_98055_j(float p_98055_1_)
	{
		super.setSize(field_98056_d * p_98055_1_, field_98057_e * p_98055_1_);
	}
	
	public int getGrowingAge()
	{
		return dataWatcher.getWatchableObjectInt(12);
	}
	
	@Override public boolean interact(EntityPlayer p_70085_1_)
	{
		ItemStack var2 = p_70085_1_.inventory.getCurrentItem();
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
					if(!p_70085_1_.capabilities.isCreativeMode)
					{
						--var2.stackSize;
						if(var2.stackSize <= 0)
						{
							p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, (ItemStack) null);
						}
					}
				}
			}
		}
		return super.interact(p_70085_1_);
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
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		setGrowingAge(p_70037_1_.getInteger("Age"));
	}
	
	public void setGrowingAge(int p_70873_1_)
	{
		dataWatcher.updateObject(12, Integer.valueOf(p_70873_1_));
		func_98054_a(isChild());
	}
	
	@Override protected final void setSize(float p_70105_1_, float p_70105_2_)
	{
		boolean var3 = field_98056_d > 0.0F;
		field_98056_d = p_70105_1_;
		field_98057_e = p_70105_2_;
		if(!var3)
		{
			func_98055_j(1.0F);
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setInteger("Age", getGrowingAge());
	}
}
