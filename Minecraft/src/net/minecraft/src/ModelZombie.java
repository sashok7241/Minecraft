package net.minecraft.src;

public class ModelZombie extends ModelBiped
{
	public ModelZombie()
	{
		this(0.0F, false);
	}
	
	public ModelZombie(float p_i5018_1_, boolean p_i5018_2_)
	{
		super(p_i5018_1_, 0.0F, 64, p_i5018_2_ ? 32 : 64);
	}
	
	protected ModelZombie(float p_i5017_1_, float p_i5017_2_, int p_i5017_3_, int p_i5017_4_)
	{
		super(p_i5017_1_, p_i5017_2_, p_i5017_3_, p_i5017_4_);
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
