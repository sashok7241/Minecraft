package net.minecraft.src;

public class ModelEnderman extends ModelBiped
{
	public boolean isCarrying = false;
	public boolean isAttacking = false;
	
	public ModelEnderman()
	{
		super(0.0F, -14.0F, 64, 32);
		float var1 = -14.0F;
		float var2 = 0.0F;
		bipedHeadwear = new ModelRenderer(this, 0, 16);
		bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, var2 - 0.5F);
		bipedHeadwear.setRotationPoint(0.0F, 0.0F + var1, 0.0F);
		bipedBody = new ModelRenderer(this, 32, 16);
		bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, var2);
		bipedBody.setRotationPoint(0.0F, 0.0F + var1, 0.0F);
		bipedRightArm = new ModelRenderer(this, 56, 0);
		bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, var2);
		bipedRightArm.setRotationPoint(-3.0F, 2.0F + var1, 0.0F);
		bipedLeftArm = new ModelRenderer(this, 56, 0);
		bipedLeftArm.mirror = true;
		bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, var2);
		bipedLeftArm.setRotationPoint(5.0F, 2.0F + var1, 0.0F);
		bipedRightLeg = new ModelRenderer(this, 56, 0);
		bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, var2);
		bipedRightLeg.setRotationPoint(-2.0F, 12.0F + var1, 0.0F);
		bipedLeftLeg = new ModelRenderer(this, 56, 0);
		bipedLeftLeg.mirror = true;
		bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, var2);
		bipedLeftLeg.setRotationPoint(2.0F, 12.0F + var1, 0.0F);
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		bipedHead.showModel = true;
		float var8 = -14.0F;
		bipedBody.rotateAngleX = 0.0F;
		bipedBody.rotationPointY = var8;
		bipedBody.rotationPointZ = -0.0F;
		bipedRightLeg.rotateAngleX -= 0.0F;
		bipedLeftLeg.rotateAngleX -= 0.0F;
		bipedRightArm.rotateAngleX = (float) (bipedRightArm.rotateAngleX * 0.5D);
		bipedLeftArm.rotateAngleX = (float) (bipedLeftArm.rotateAngleX * 0.5D);
		bipedRightLeg.rotateAngleX = (float) (bipedRightLeg.rotateAngleX * 0.5D);
		bipedLeftLeg.rotateAngleX = (float) (bipedLeftLeg.rotateAngleX * 0.5D);
		float var9 = 0.4F;
		if(bipedRightArm.rotateAngleX > var9)
		{
			bipedRightArm.rotateAngleX = var9;
		}
		if(bipedLeftArm.rotateAngleX > var9)
		{
			bipedLeftArm.rotateAngleX = var9;
		}
		if(bipedRightArm.rotateAngleX < -var9)
		{
			bipedRightArm.rotateAngleX = -var9;
		}
		if(bipedLeftArm.rotateAngleX < -var9)
		{
			bipedLeftArm.rotateAngleX = -var9;
		}
		if(bipedRightLeg.rotateAngleX > var9)
		{
			bipedRightLeg.rotateAngleX = var9;
		}
		if(bipedLeftLeg.rotateAngleX > var9)
		{
			bipedLeftLeg.rotateAngleX = var9;
		}
		if(bipedRightLeg.rotateAngleX < -var9)
		{
			bipedRightLeg.rotateAngleX = -var9;
		}
		if(bipedLeftLeg.rotateAngleX < -var9)
		{
			bipedLeftLeg.rotateAngleX = -var9;
		}
		if(isCarrying)
		{
			bipedRightArm.rotateAngleX = -0.5F;
			bipedLeftArm.rotateAngleX = -0.5F;
			bipedRightArm.rotateAngleZ = 0.05F;
			bipedLeftArm.rotateAngleZ = -0.05F;
		}
		bipedRightArm.rotationPointZ = 0.0F;
		bipedLeftArm.rotationPointZ = 0.0F;
		bipedRightLeg.rotationPointZ = 0.0F;
		bipedLeftLeg.rotationPointZ = 0.0F;
		bipedRightLeg.rotationPointY = 9.0F + var8;
		bipedLeftLeg.rotationPointY = 9.0F + var8;
		bipedHead.rotationPointZ = -0.0F;
		bipedHead.rotationPointY = var8 + 1.0F;
		bipedHeadwear.rotationPointX = bipedHead.rotationPointX;
		bipedHeadwear.rotationPointY = bipedHead.rotationPointY;
		bipedHeadwear.rotationPointZ = bipedHead.rotationPointZ;
		bipedHeadwear.rotateAngleX = bipedHead.rotateAngleX;
		bipedHeadwear.rotateAngleY = bipedHead.rotateAngleY;
		bipedHeadwear.rotateAngleZ = bipedHead.rotateAngleZ;
		if(isAttacking)
		{
			float var10 = 1.0F;
			bipedHead.rotationPointY -= var10 * 5.0F;
		}
	}
}
