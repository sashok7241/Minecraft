package net.minecraft.src;

public class ModelSkeleton extends ModelZombie
{
	public ModelSkeleton()
	{
		this(0.0F);
	}
	
	public ModelSkeleton(float p_i5016_1_)
	{
		super(p_i5016_1_, 0.0F, 64, 32);
		bipedRightArm = new ModelRenderer(this, 40, 16);
		bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, p_i5016_1_);
		bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		bipedLeftArm = new ModelRenderer(this, 40, 16);
		bipedLeftArm.mirror = true;
		bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, p_i5016_1_);
		bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		bipedRightLeg = new ModelRenderer(this, 0, 16);
		bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, p_i5016_1_);
		bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		bipedLeftLeg = new ModelRenderer(this, 0, 16);
		bipedLeftLeg.mirror = true;
		bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, p_i5016_1_);
		bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
	}
	
	@Override public void setLivingAnimations(EntityLiving par1EntityLivingBase, float par2, float par3, float par4)
	{
		aimedBow = ((EntitySkeleton) par1EntityLivingBase).getSkeletonType() == 1;
		super.setLivingAnimations(par1EntityLivingBase, par2, par3, par4);
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
	}
}
