package net.minecraft.src;

public class ModelIronGolem extends ModelBase
{
	public ModelRenderer ironGolemHead;
	public ModelRenderer ironGolemBody;
	public ModelRenderer ironGolemRightArm;
	public ModelRenderer ironGolemLeftArm;
	public ModelRenderer ironGolemLeftLeg;
	public ModelRenderer ironGolemRightLeg;
	
	public ModelIronGolem()
	{
		this(0.0F);
	}
	
	public ModelIronGolem(float p_i3130_1_)
	{
		this(p_i3130_1_, -7.0F);
	}
	
	public ModelIronGolem(float p_i3131_1_, float p_i3131_2_)
	{
		short var3 = 128;
		short var4 = 128;
		ironGolemHead = new ModelRenderer(this).setTextureSize(var3, var4);
		ironGolemHead.setRotationPoint(0.0F, 0.0F + p_i3131_2_, -2.0F);
		ironGolemHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, p_i3131_1_);
		ironGolemHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, p_i3131_1_);
		ironGolemBody = new ModelRenderer(this).setTextureSize(var3, var4);
		ironGolemBody.setRotationPoint(0.0F, 0.0F + p_i3131_2_, 0.0F);
		ironGolemBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, p_i3131_1_);
		ironGolemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, p_i3131_1_ + 0.5F);
		ironGolemRightArm = new ModelRenderer(this).setTextureSize(var3, var4);
		ironGolemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
		ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, p_i3131_1_);
		ironGolemLeftArm = new ModelRenderer(this).setTextureSize(var3, var4);
		ironGolemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
		ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, p_i3131_1_);
		ironGolemLeftLeg = new ModelRenderer(this, 0, 22).setTextureSize(var3, var4);
		ironGolemLeftLeg.setRotationPoint(-4.0F, 18.0F + p_i3131_2_, 0.0F);
		ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, p_i3131_1_);
		ironGolemRightLeg = new ModelRenderer(this, 0, 22).setTextureSize(var3, var4);
		ironGolemRightLeg.mirror = true;
		ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 18.0F + p_i3131_2_, 0.0F);
		ironGolemRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, p_i3131_1_);
	}
	
	private float func_78172_a(float par1, float par2)
	{
		return (Math.abs(par1 % par2 - par2 * 0.5F) - par2 * 0.25F) / (par2 * 0.25F);
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		ironGolemHead.render(par7);
		ironGolemBody.render(par7);
		ironGolemLeftLeg.render(par7);
		ironGolemRightLeg.render(par7);
		ironGolemRightArm.render(par7);
		ironGolemLeftArm.render(par7);
	}
	
	@Override public void setLivingAnimations(EntityLiving par1EntityLivingBase, float par2, float par3, float par4)
	{
		EntityIronGolem var5 = (EntityIronGolem) par1EntityLivingBase;
		int var6 = var5.getAttackTimer();
		if(var6 > 0)
		{
			ironGolemRightArm.rotateAngleX = -2.0F + 1.5F * func_78172_a(var6 - par4, 10.0F);
			ironGolemLeftArm.rotateAngleX = -2.0F + 1.5F * func_78172_a(var6 - par4, 10.0F);
		} else
		{
			int var7 = var5.getHoldRoseTick();
			if(var7 > 0)
			{
				ironGolemRightArm.rotateAngleX = -0.8F + 0.025F * func_78172_a(var7, 70.0F);
				ironGolemLeftArm.rotateAngleX = 0.0F;
			} else
			{
				ironGolemRightArm.rotateAngleX = (-0.2F + 1.5F * func_78172_a(par2, 13.0F)) * par3;
				ironGolemLeftArm.rotateAngleX = (-0.2F - 1.5F * func_78172_a(par2, 13.0F)) * par3;
			}
		}
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		ironGolemHead.rotateAngleY = par4 / (180F / (float) Math.PI);
		ironGolemHead.rotateAngleX = par5 / (180F / (float) Math.PI);
		ironGolemLeftLeg.rotateAngleX = -1.5F * func_78172_a(par1, 13.0F) * par2;
		ironGolemRightLeg.rotateAngleX = 1.5F * func_78172_a(par1, 13.0F) * par2;
		ironGolemLeftLeg.rotateAngleY = 0.0F;
		ironGolemRightLeg.rotateAngleY = 0.0F;
	}
}
