package net.minecraft.src;

public class EntityEnderCrystal extends Entity
{
	public int innerRotation;
	public int health;
	
	public EntityEnderCrystal(World par1World)
	{
		super(par1World);
		preventEntitySpawning = true;
		setSize(2.0F, 2.0F);
		yOffset = height / 2.0F;
		health = 5;
		innerRotation = rand.nextInt(100000);
	}
	
	public EntityEnderCrystal(World par1World, double par2, double par4, double par6)
	{
		this(par1World);
		setPosition(par2, par4, par6);
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			if(!isDead && !worldObj.isRemote)
			{
				health = 0;
				if(health <= 0)
				{
					setDead();
					if(!worldObj.isRemote)
					{
						worldObj.createExplosion((Entity) null, posX, posY, posZ, 6.0F, true);
					}
				}
			}
			return true;
		}
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return true;
	}
	
	@Override protected boolean canTriggerWalking()
	{
		return false;
	}
	
	@Override protected void entityInit()
	{
		dataWatcher.addObject(8, Integer.valueOf(health));
	}
	
	@Override public float getShadowSize()
	{
		return 0.0F;
	}
	
	@Override public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		++innerRotation;
		dataWatcher.updateObject(8, Integer.valueOf(health));
		int var1 = MathHelper.floor_double(posX);
		int var2 = MathHelper.floor_double(posY);
		int var3 = MathHelper.floor_double(posZ);
		if(worldObj.getBlockId(var1, var2, var3) != Block.fire.blockID)
		{
			worldObj.setBlock(var1, var2, var3, Block.fire.blockID);
		}
	}
	
	@Override protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
	}
}
