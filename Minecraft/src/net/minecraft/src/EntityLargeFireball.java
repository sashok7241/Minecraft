package net.minecraft.src;

public class EntityLargeFireball extends EntityFireball
{
	public int field_92057_e = 1;
	
	public EntityLargeFireball(World par1World)
	{
		super(par1World);
	}
	
	public EntityLargeFireball(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		super(par1World, par2, par4, par6, par8, par10, par12);
	}
	
	public EntityLargeFireball(World par1World, EntityLivingBase par2EntityLivingBase, double par3, double par5, double par7)
	{
		super(par1World, par2EntityLivingBase, par3, par5, par7);
	}
	
	@Override protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		if(!worldObj.isRemote)
		{
			if(par1MovingObjectPosition.entityHit != null)
			{
				par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, shootingEntity), 6.0F);
			}
			worldObj.newExplosion((Entity) null, posX, posY, posZ, field_92057_e, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			setDead();
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		if(par1NBTTagCompound.hasKey("ExplosionPower"))
		{
			field_92057_e = par1NBTTagCompound.getInteger("ExplosionPower");
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("ExplosionPower", field_92057_e);
	}
}
