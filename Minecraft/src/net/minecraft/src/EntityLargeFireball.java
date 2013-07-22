package net.minecraft.src;

public class EntityLargeFireball extends EntityFireball
{
	public int field_92057_e = 1;
	
	public EntityLargeFireball(World p_i5067_1_)
	{
		super(p_i5067_1_);
	}
	
	public EntityLargeFireball(World p_i5068_1_, double p_i5068_2_, double p_i5068_4_, double p_i5068_6_, double p_i5068_8_, double p_i5068_10_, double p_i5068_12_)
	{
		super(p_i5068_1_, p_i5068_2_, p_i5068_4_, p_i5068_6_, p_i5068_8_, p_i5068_10_, p_i5068_12_);
	}
	
	public EntityLargeFireball(World p_i5069_1_, EntityLiving p_i5069_2_, double p_i5069_3_, double p_i5069_5_, double p_i5069_7_)
	{
		super(p_i5069_1_, p_i5069_2_, p_i5069_3_, p_i5069_5_, p_i5069_7_);
	}
	
	@Override protected void onImpact(MovingObjectPosition p_70227_1_)
	{
		if(!worldObj.isRemote)
		{
			if(p_70227_1_.entityHit != null)
			{
				p_70227_1_.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, shootingEntity), 6);
			}
			worldObj.newExplosion((Entity) null, posX, posY, posZ, field_92057_e, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			setDead();
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		if(p_70037_1_.hasKey("ExplosionPower"))
		{
			field_92057_e = p_70037_1_.getInteger("ExplosionPower");
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setInteger("ExplosionPower", field_92057_e);
	}
}
