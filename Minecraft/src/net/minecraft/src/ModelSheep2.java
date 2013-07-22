package net.minecraft.src;

public class ModelSheep2 extends ModelQuadruped
{
	private float field_78153_i;
	
	public ModelSheep2()
	{
		super(12, 0.0F);
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.0F, -4.0F, -6.0F, 6, 6, 8, 0.0F);
		head.setRotationPoint(0.0F, 6.0F, -8.0F);
		body = new ModelRenderer(this, 28, 8);
		body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 0.0F);
		body.setRotationPoint(0.0F, 5.0F, 2.0F);
	}
	
	@Override public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4)
	{
		super.setLivingAnimations(par1EntityLiving, par2, par3, par4);
		head.rotationPointY = 6.0F + ((EntitySheep) par1EntityLiving).func_70894_j(par4) * 9.0F;
		field_78153_i = ((EntitySheep) par1EntityLiving).func_70890_k(par4);
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		head.rotateAngleX = field_78153_i;
	}
}
