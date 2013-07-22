package net.minecraft.src;

public class EntityMinecartFurnace extends EntityMinecart
{
	private int fuel;
	public double pushX;
	public double pushZ;
	
	public EntityMinecartFurnace(World par1World)
	{
		super(par1World);
	}
	
	public EntityMinecartFurnace(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}
	
	@Override protected void applyDrag()
	{
		double var1 = pushX * pushX + pushZ * pushZ;
		if(var1 > 1.0E-4D)
		{
			var1 = MathHelper.sqrt_double(var1);
			pushX /= var1;
			pushZ /= var1;
			double var3 = 0.05D;
			motionX *= 0.800000011920929D;
			motionY *= 0.0D;
			motionZ *= 0.800000011920929D;
			motionX += pushX * var3;
			motionZ += pushZ * var3;
		} else
		{
			motionX *= 0.9800000190734863D;
			motionY *= 0.0D;
			motionZ *= 0.9800000190734863D;
		}
		super.applyDrag();
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte) 0));
	}
	
	@Override public boolean func_130002_c(EntityPlayer par1EntityPlayer)
	{
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
		if(var2 != null && var2.itemID == Item.coal.itemID)
		{
			if(!par1EntityPlayer.capabilities.isCreativeMode && --var2.stackSize == 0)
			{
				par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack) null);
			}
			fuel += 3600;
		}
		pushX = posX - par1EntityPlayer.posX;
		pushZ = posZ - par1EntityPlayer.posZ;
		return true;
	}
	
	@Override public Block getDefaultDisplayTile()
	{
		return Block.furnaceBurning;
	}
	
	@Override public int getDefaultDisplayTileData()
	{
		return 2;
	}
	
	@Override public int getMinecartType()
	{
		return 2;
	}
	
	protected boolean isMinecartPowered()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}
	
	@Override public void killMinecart(DamageSource par1DamageSource)
	{
		super.killMinecart(par1DamageSource);
		if(!par1DamageSource.isExplosion())
		{
			entityDropItem(new ItemStack(Block.furnaceIdle, 1), 0.0F);
		}
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(fuel > 0)
		{
			--fuel;
		}
		if(fuel <= 0)
		{
			pushX = pushZ = 0.0D;
		}
		setMinecartPowered(fuel > 0);
		if(isMinecartPowered() && rand.nextInt(4) == 0)
		{
			worldObj.spawnParticle("largesmoke", posX, posY + 0.8D, posZ, 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		pushX = par1NBTTagCompound.getDouble("PushX");
		pushZ = par1NBTTagCompound.getDouble("PushZ");
		fuel = par1NBTTagCompound.getShort("Fuel");
	}
	
	protected void setMinecartPowered(boolean par1)
	{
		if(par1)
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (dataWatcher.getWatchableObjectByte(16) | 1)));
		} else
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (dataWatcher.getWatchableObjectByte(16) & -2)));
		}
	}
	
	@Override protected void updateOnTrack(int par1, int par2, int par3, double par4, double par6, int par8, int par9)
	{
		super.updateOnTrack(par1, par2, par3, par4, par6, par8, par9);
		double var10 = pushX * pushX + pushZ * pushZ;
		if(var10 > 1.0E-4D && motionX * motionX + motionZ * motionZ > 0.001D)
		{
			var10 = MathHelper.sqrt_double(var10);
			pushX /= var10;
			pushZ /= var10;
			if(pushX * motionX + pushZ * motionZ < 0.0D)
			{
				pushX = 0.0D;
				pushZ = 0.0D;
			} else
			{
				pushX = motionX;
				pushZ = motionZ;
			}
		}
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setDouble("PushX", pushX);
		par1NBTTagCompound.setDouble("PushZ", pushZ);
		par1NBTTagCompound.setShort("Fuel", (short) fuel);
	}
}
