package net.minecraft.src;

public class EntitySmallFireball extends EntityFireball
{
	public EntitySmallFireball(World par1World)
	{
		super(par1World);
		setSize(0.3125F, 0.3125F);
	}
	
	public EntitySmallFireball(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		super(par1World, par2, par4, par6, par8, par10, par12);
		setSize(0.3125F, 0.3125F);
	}
	
	public EntitySmallFireball(World par1World, EntityLiving par2EntityLiving, double par3, double par5, double par7)
	{
		super(par1World, par2EntityLiving, par3, par5, par7);
		setSize(0.3125F, 0.3125F);
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		return false;
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return false;
	}
	
	@Override protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		if(!worldObj.isRemote)
		{
			if(par1MovingObjectPosition.entityHit != null)
			{
				if(!par1MovingObjectPosition.entityHit.isImmuneToFire() && par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, shootingEntity), 5))
				{
					par1MovingObjectPosition.entityHit.setFire(5);
				}
			} else
			{
				int var2 = par1MovingObjectPosition.blockX;
				int var3 = par1MovingObjectPosition.blockY;
				int var4 = par1MovingObjectPosition.blockZ;
				switch(par1MovingObjectPosition.sideHit)
				{
					case 0:
						--var3;
						break;
					case 1:
						++var3;
						break;
					case 2:
						--var4;
						break;
					case 3:
						++var4;
						break;
					case 4:
						--var2;
						break;
					case 5:
						++var2;
				}
				if(worldObj.isAirBlock(var2, var3, var4))
				{
					worldObj.setBlock(var2, var3, var4, Block.fire.blockID);
				}
			}
			setDead();
		}
	}
}
