package net.minecraft.src;

public class ModelZombie extends ModelBiped
{
	public ModelZombie()
	{
		this(0.0F, false);
	}
	
	public ModelZombie(float par1, boolean par2)
	{
		super(par1, 0.0F, 64, par2 ? 32 : 64);
	}
	
	protected ModelZombie(float par1, float par2, int par3, int par4)
	{
		super(par1, par2, par3, par4);
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		float var8 = MathHelper.sin(onGround * (float) Math.PI);
		float var9 = MathHelper.sin((1.0F - (1.0F - onGround) * (1.0F - onGround)) * (float) Math.PI);
		bipedRightArm.rotateAngleZ = 0.0F;
		bipedLeftArm.rotateAngleZ = 0.0F;
		bipedRightArm.rotateAngleY = -(0.1F - var8 * 0.6F);
		bipedLeftArm.rotateAngleY = 0.1F - var8 * 0.6F;
		bipedRightArm.rotateAngleX = -((float) Math.PI / 2F);
		bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F);
		bipedRightArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
		bipedLeftArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
		bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
		bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
		bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
		bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
	}
}
