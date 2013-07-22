package net.minecraft.src;

public abstract class EntityFlying extends EntityLiving
{
	public EntityFlying(World p_i3442_1_)
	{
		super(p_i3442_1_);
	}
	
	@Override protected void fall(float p_70069_1_)
	{
	}
	
	@Override public boolean isOnLadder()
	{
		return false;
	}
	
	@Override public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		if(isInWater())
		{
			moveFlying(p_70612_1_, p_70612_2_, 0.02F);
			moveEntity(motionX, motionY, motionZ);
			motionX *= 0.800000011920929D;
			motionY *= 0.800000011920929D;
			motionZ *= 0.800000011920929D;
		} else if(handleLavaMovement())
		{
			moveFlying(p_70612_1_, p_70612_2_, 0.02F);
			moveEntity(motionX, motionY, motionZ);
			motionX *= 0.5D;
			motionY *= 0.5D;
			motionZ *= 0.5D;
		} else
		{
			float var3 = 0.91F;
			if(onGround)
			{
				var3 = 0.54600006F;
				int var4 = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
				if(var4 > 0)
				{
					var3 = Block.blocksList[var4].slipperiness * 0.91F;
				}
			}
			float var8 = 0.16277136F / (var3 * var3 * var3);
			moveFlying(p_70612_1_, p_70612_2_, onGround ? 0.1F * var8 : 0.02F);
			var3 = 0.91F;
			if(onGround)
			{
				var3 = 0.54600006F;
				int var5 = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
				if(var5 > 0)
				{
					var3 = Block.blocksList[var5].slipperiness * 0.91F;
				}
			}
			moveEntity(motionX, motionY, motionZ);
			motionX *= var3;
			motionY *= var3;
			motionZ *= var3;
		}
		prevLimbYaw = limbYaw;
		double var10 = posX - prevPosX;
		double var9 = posZ - prevPosZ;
		float var7 = MathHelper.sqrt_double(var10 * var10 + var9 * var9) * 4.0F;
		if(var7 > 1.0F)
		{
			var7 = 1.0F;
		}
		limbYaw += (var7 - limbYaw) * 0.4F;
		limbSwing += limbYaw;
	}
	
	@Override protected void updateFallState(double p_70064_1_, boolean p_70064_3_)
	{
	}
}
