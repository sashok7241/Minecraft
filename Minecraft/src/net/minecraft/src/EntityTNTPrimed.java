package net.minecraft.src;

public class EntityTNTPrimed extends Entity
{
	public int fuse;
	private EntityLiving tntPlacedBy;
	
	public EntityTNTPrimed(World par1World)
	{
		super(par1World);
		fuse = 0;
		preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
		yOffset = height / 2.0F;
	}
	
	public EntityTNTPrimed(World par1World, double par2, double par4, double par6, EntityLiving par8EntityLiving)
	{
		this(par1World);
		setPosition(par2, par4, par6);
		float var9 = (float) (Math.random() * Math.PI * 2.0D);
		motionX = -((float) Math.sin(var9)) * 0.02F;
		motionY = 0.20000000298023224D;
		motionZ = -((float) Math.cos(var9)) * 0.02F;
		fuse = 80;
		prevPosX = par2;
		prevPosY = par4;
		prevPosZ = par6;
		tntPlacedBy = par8EntityLiving;
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
	
	@Override protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		fuse = par1NBTTagCompound.getByte("Fuse");
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setByte("Fuse", (byte) fuse);
	}
}
