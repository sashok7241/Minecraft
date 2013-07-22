package net.minecraft.src;

public class EntityMoveHelper
{
	private EntityLiving entity;
	private double posX;
	private double posY;
	private double posZ;
	private float speed;
	private boolean update = false;
	
	public EntityMoveHelper(EntityLiving p_i3456_1_)
	{
		entity = p_i3456_1_;
		posX = p_i3456_1_.posX;
		posY = p_i3456_1_.posY;
		posZ = p_i3456_1_.posZ;
	}
	
	public float getSpeed()
	{
		return speed;
	}
	
	public boolean isUpdating()
	{
		return update;
	}
	
	private float limitAngle(float p_75639_1_, float p_75639_2_, float p_75639_3_)
	{
		float var4 = MathHelper.wrapAngleTo180_float(p_75639_2_ - p_75639_1_);
		if(var4 > p_75639_3_)
		{
			var4 = p_75639_3_;
		}
		if(var4 < -p_75639_3_)
		{
			var4 = -p_75639_3_;
		}
		return p_75639_1_ + var4;
	}
	
	public void onUpdateMoveHelper()
	{
		entity.setMoveForward(0.0F);
		if(update)
		{
			update = false;
			int var1 = MathHelper.floor_double(entity.boundingBox.minY + 0.5D);
			double var2 = posX - entity.posX;
			double var4 = posZ - entity.posZ;
			double var6 = posY - var1;
			double var8 = var2 * var2 + var6 * var6 + var4 * var4;
			if(var8 >= 2.500000277905201E-7D)
			{
				float var10 = (float) (Math.atan2(var4, var2) * 180.0D / Math.PI) - 90.0F;
				entity.rotationYaw = limitAngle(entity.rotationYaw, var10, 30.0F);
				entity.setAIMoveSpeed(speed * entity.getSpeedModifier());
				if(var6 > 0.0D && var2 * var2 + var4 * var4 < 1.0D)
				{
					entity.getJumpHelper().setJumping();
				}
			}
		}
	}
	
	public void setMoveTo(double p_75642_1_, double p_75642_3_, double p_75642_5_, float p_75642_7_)
	{
		posX = p_75642_1_;
		posY = p_75642_3_;
		posZ = p_75642_5_;
		speed = p_75642_7_;
		update = true;
	}
}
