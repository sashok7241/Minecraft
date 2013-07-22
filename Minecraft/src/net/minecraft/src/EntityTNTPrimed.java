package net.minecraft.src;

public class EntityTNTPrimed extends Entity
{
	public int fuse;
	private EntityLiving tntPlacedBy;
	
	public EntityTNTPrimed(World p_i3543_1_)
	{
		super(p_i3543_1_);
		fuse = 0;
		preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
		yOffset = height / 2.0F;
	}
	
	public EntityTNTPrimed(World p_i9030_1_, double p_i9030_2_, double p_i9030_4_, double p_i9030_6_, EntityLiving p_i9030_8_)
	{
		this(p_i9030_1_);
		setPosition(p_i9030_2_, p_i9030_4_, p_i9030_6_);
		float var9 = (float) (Math.random() * Math.PI * 2.0D);
		motionX = -((float) Math.sin(var9)) * 0.02F;
		motionY = 0.20000000298023224D;
		motionZ = -((float) Math.cos(var9)) * 0.02F;
		fuse = 80;
		prevPosX = p_i9030_2_;
		prevPosY = p_i9030_4_;
		prevPosZ = p_i9030_6_;
		tntPlacedBy = p_i9030_8_;
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return !isDead;
	}
	
	@Override protected boolean canTriggerWalking()
	{
		return false;
	}
	
	@Override protected void entityInit()
	{
	}
	
	private void explode()
	{
		float var1 = 4.0F;
		worldObj.createExplosion(this, posX, posY, posZ, var1, true);
	}
	
	@Override public float getShadowSize()
	{
		return 0.0F;
	}
	
	public EntityLiving getTntPlacedBy()
	{
		return tntPlacedBy;
	}
	
	@Override public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.03999999910593033D;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9800000190734863D;
		motionY *= 0.9800000190734863D;
		motionZ *= 0.9800000190734863D;
		if(onGround)
		{
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
			motionY *= -0.5D;
		}
		if(fuse-- <= 0)
		{
			setDead();
			if(!worldObj.isRemote)
			{
				explode();
			}
		} else
		{
			worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		fuse = p_70037_1_.getByte("Fuse");
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		p_70014_1_.setByte("Fuse", (byte) fuse);
	}
}
