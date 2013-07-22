package net.minecraft.src;

public class ModelZombieVillager extends ModelBiped
{
	public ModelZombieVillager()
	{
		this(0.0F, 0.0F, false);
	}
	
	public ModelZombieVillager(float p_i5020_1_, float p_i5020_2_, boolean p_i5020_3_)
	{
		super(p_i5020_1_, 0.0F, 64, p_i5020_3_ ? 32 : 64);
		if(p_i5020_3_)
		{
			bipedHead = new ModelRenderer(this, 0, 0);
			bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8, 6, 8, p_i5020_1_);
			bipedHead.setRotationPoint(0.0F, 0.0F + p_i5020_2_, 0.0F);
		} else
		{
			bipedHead = new ModelRenderer(this);
			bipedHead.setRotationPoint(0.0F, 0.0F + p_i5020_2_, 0.0F);
			bipedHead.setTextureOffset(0, 32).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i5020_1_);
			bipedHead.setTextureOffset(24, 32).addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2, p_i5020_1_);
		}
	}
	
	public int func_82897_a()
	{
		return 10;
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
