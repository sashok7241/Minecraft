package net.minecraft.src;

public class EntityMoveHelper
{
	private EntityLiving entity;
	private double posX;
	private double posY;
	private double posZ;
	private double speed;
	private boolean update;
	
	public EntityMoveHelper(EntityLiving par1EntityLiving)
	{
		entity = par1EntityLiving;
		posX = par1EntityLiving.posX;
		posY = par1EntityLiving.posY;
		posZ = par1EntityLiving.posZ;
	}
	
	public double getSpeed()
	{
		return speed;
	}
	
	public boolean isUpdating()
	{
		return update;
	}
	
	private float limitAngle(float par1, float par2, float par3)
	{
		float var4 = MathHelper.wrapAngleTo180_float(par2 - par1);
		if(var4 > par3)
		{
			var4 = par3;
		}
		if(var4 < -par3)
		{
			var4 = -par3;
		}
		return par1 + var4;
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
				entity.setAIMoveSpeed((float) (speed * entity.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e()));
				if(var6 > 0.0D && var2 * var2 + var4 * var4 < 1.0D)
				{
					entity.getJumpHelper().setJumping();
				}
			}
		}
	}
	
	public void setMoveTo(double par1, double par3, double par5, double par7)
	{
		posX = par1;
		posY = par3;
		posZ = par5;
		speed = par7;
		update = true;
	}
}
