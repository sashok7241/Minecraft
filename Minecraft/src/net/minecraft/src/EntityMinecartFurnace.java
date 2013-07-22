package net.minecraft.src;

public class EntityMinecartFurnace extends EntityMinecart
{
	private int fuel = 0;
	public double pushX;
	public double pushZ;
	
	public EntityMinecartFurnace(World p_i9003_1_)
	{
		super(p_i9003_1_);
	}
	
	public EntityMinecartFurnace(World p_i9004_1_, double p_i9004_2_, double p_i9004_4_, double p_i9004_6_)
	{
		super(p_i9004_1_, p_i9004_2_, p_i9004_4_, p_i9004_6_);
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
	
	@Override public boolean interact(EntityPlayer p_70085_1_)
	{
		ItemStack var2 = p_70085_1_.inventory.getCurrentItem();
		if(var2 != null && var2.itemID == Item.coal.itemID)
		{
			if(--var2.stackSize == 0)
			{
				p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, (ItemStack) null);
			}
			fuel += 3600;
		}
		pushX = posX - p_70085_1_.posX;
		pushZ = posZ - p_70085_1_.posZ;
		return true;
	}
	
	protected boolean isMinecartPowered()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}
	
	@Override public void killMinecart(DamageSource p_94095_1_)
	{
		super.killMinecart(p_94095_1_);
		if(!p_94095_1_.isExplosion())
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
	
	@Override protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		pushX = p_70037_1_.getDouble("PushX");
		pushZ = p_70037_1_.getDouble("PushZ");
		fuel = p_70037_1_.getShort("Fuel");
	}
	
	protected void setMinecartPowered(boolean p_94107_1_)
	{
		if(p_94107_1_)
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (dataWatcher.getWatchableObjectByte(16) | 1)));
		} else
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (dataWatcher.getWatchableObjectByte(16) & -2)));
		}
	}
	
	@Override protected void updateOnTrack(int p_94091_1_, int p_94091_2_, int p_94091_3_, double p_94091_4_, double p_94091_6_, int p_94091_8_, int p_94091_9_)
	{
		super.updateOnTrack(p_94091_1_, p_94091_2_, p_94091_3_, p_94091_4_, p_94091_6_, p_94091_8_, p_94091_9_);
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
	
	@Override protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setDouble("PushX", pushX);
		p_70014_1_.setDouble("PushZ", pushZ);
		p_70014_1_.setShort("Fuel", (short) fuel);
	}
}
