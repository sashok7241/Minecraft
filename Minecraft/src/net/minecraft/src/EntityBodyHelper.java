package net.minecraft.src;

public class EntityBodyHelper
{
	private EntityLivingBase theLiving;
	private int field_75666_b;
	private float field_75667_c;
	
	public EntityBodyHelper(EntityLivingBase par1EntityLivingBase)
	{
		theLiving = par1EntityLivingBase;
	}
	
	public void func_75664_a()
	{
		double var1 = theLiving.posX - theLiving.prevPosX;
		double var3 = theLiving.posZ - theLiving.prevPosZ;
		if(var1 * var1 + var3 * var3 > 2.500000277905201E-7D)
		{
			theLiving.renderYawOffset = theLiving.rotationYaw;
			theLiving.rotationYawHead = func_75665_a(theLiving.renderYawOffset, theLiving.rotationYawHead, 75.0F);
			field_75667_c = theLiving.rotationYawHead;
			field_75666_b = 0;
		} else
		{
			float var5 = 75.0F;
			if(Math.abs(theLiving.rotationYawHead - field_75667_c) > 15.0F)
			{
				field_75666_b = 0;
				field_75667_c = theLiving.rotationYawHead;
			} else
			{
				++field_75666_b;
				boolean var6 = true;
				if(field_75666_b > 10)
				{
					var5 = Math.max(1.0F - (field_75666_b - 10) / 10.0F, 0.0F) * 75.0F;
				}
			}
			theLiving.renderYawOffset = func_75665_a(theLiving.rotationYawHead, theLiving.renderYawOffset, var5);
		}
	}
	
	private float func_75665_a(float par1, float par2, float par3)
	{
		float var4 = MathHelper.wrapAngleTo180_float(par1 - par2);
		if(var4 < -par3)
		{
			var4 = -par3;
		}
		if(var4 >= par3)
		{
			var4 = par3;
		}
		return par1 - var4;
	}
}
