package net.minecraft.src;

public class EntityEnderPearl extends EntityThrowable
{
	public EntityEnderPearl(World p_i3589_1_)
	{
		super(p_i3589_1_);
	}
	
	public EntityEnderPearl(World p_i3591_1_, double p_i3591_2_, double p_i3591_4_, double p_i3591_6_)
	{
		super(p_i3591_1_, p_i3591_2_, p_i3591_4_, p_i3591_6_);
	}
	
	public EntityEnderPearl(World p_i3590_1_, EntityLiving p_i3590_2_)
	{
		super(p_i3590_1_, p_i3590_2_);
	}
	
	@Override protected void onImpact(MovingObjectPosition p_70184_1_)
	{
		if(p_70184_1_.entityHit != null)
		{
			p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0);
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
					getThrower().setPositionAndUpdate(posX, posY, posZ);
					getThrower().fallDistance = 0.0F;
					getThrower().attackEntityFrom(DamageSource.fall, 5);
				}
			}
			setDead();
		}
	}
}
