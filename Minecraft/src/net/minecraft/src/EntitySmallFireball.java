package net.minecraft.src;

public class EntitySmallFireball extends EntityFireball
{
	public EntitySmallFireball(World p_i3577_1_)
	{
		super(p_i3577_1_);
		setSize(0.3125F, 0.3125F);
	}
	
	public EntitySmallFireball(World p_i3579_1_, double p_i3579_2_, double p_i3579_4_, double p_i3579_6_, double p_i3579_8_, double p_i3579_10_, double p_i3579_12_)
	{
		super(p_i3579_1_, p_i3579_2_, p_i3579_4_, p_i3579_6_, p_i3579_8_, p_i3579_10_, p_i3579_12_);
		setSize(0.3125F, 0.3125F);
	}
	
	public EntitySmallFireball(World p_i3578_1_, EntityLiving p_i3578_2_, double p_i3578_3_, double p_i3578_5_, double p_i3578_7_)
	{
		super(p_i3578_1_, p_i3578_2_, p_i3578_3_, p_i3578_5_, p_i3578_7_);
		setSize(0.3125F, 0.3125F);
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		return false;
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return false;
	}
	
	@Override protected void onImpact(MovingObjectPosition p_70227_1_)
	{
		if(!worldObj.isRemote)
		{
			if(p_70227_1_.entityHit != null)
			{
				if(!p_70227_1_.entityHit.isImmuneToFire() && p_70227_1_.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, shootingEntity), 5))
				{
					p_70227_1_.entityHit.setFire(5);
				}
			} else
			{
				int var2 = p_70227_1_.blockX;
				int var3 = p_70227_1_.blockY;
				int var4 = p_70227_1_.blockZ;
				switch(p_70227_1_.sideHit)
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
