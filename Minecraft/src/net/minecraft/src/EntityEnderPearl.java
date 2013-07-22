package net.minecraft.src;

public class EntityEnderPearl extends EntityThrowable
{
	public EntityEnderPearl(World par1World)
	{
		super(par1World);
	}
	
	public EntityEnderPearl(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}
	
	public EntityEnderPearl(World par1World, EntityLivingBase par2EntityLivingBase)
	{
		super(par1World, par2EntityLivingBase);
	}
	
	@Override protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		if(par1MovingObjectPosition.entityHit != null)
		{
			par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0.0F);
		}
		for(int var2 = 0; var2 < 32; ++var2)
		{
			worldObj.spawnParticle("portal", posX, posY + rand.nextDouble() * 2.0D, posZ, rand.nextGaussian(), 0.0D, rand.nextGaussian());
		}
		if(!worldObj.isRemote)
		{
			if(getThrower() != null && getThrower() instanceof EntityPlayerMP)
			{
				EntityPlayerMP var3 = (EntityPlayerMP) getThrower();
				if(!var3.playerNetServerHandler.connectionClosed && var3.worldObj == worldObj)
				{
					if(getThrower().isRiding())
					{
						getThrower().mountEntity((Entity) null);
					}
					getThrower().setPositionAndUpdate(posX, posY, posZ);
					getThrower().fallDistance = 0.0F;
					getThrower().attackEntityFrom(DamageSource.fall, 5.0F);
				}
			}
			setDead();
		}
	}
}
