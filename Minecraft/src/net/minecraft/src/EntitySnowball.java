package net.minecraft.src;

public class EntitySnowball extends EntityThrowable
{
	public EntitySnowball(World par1World)
	{
		super(par1World);
	}
	
	public EntitySnowball(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}
	
	public EntitySnowball(World par1World, EntityLiving par2EntityLiving)
	{
		super(par1World, par2EntityLiving);
	}
	
	@Override protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		if(par1MovingObjectPosition.entityHit != null)
		{
			byte var2 = 0;
			if(par1MovingObjectPosition.entityHit instanceof EntityBlaze)
			{
				var2 = 3;
			}
			par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), var2);
		}
		for(int var3 = 0; var3 < 8; ++var3)
		{
			worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		}
		if(!worldObj.isRemote)
		{
			setDead();
		}
	}
}
