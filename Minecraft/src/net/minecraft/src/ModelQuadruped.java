package net.minecraft.src;


public class ModelQuadruped extends ModelBase
{
	public ModelRenderer head = new ModelRenderer(this, 0, 0);
	public ModelRenderer body;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	protected float field_78145_g = 8.0F;
	protected float field_78151_h = 4.0F;
	
	public ModelQuadruped(int p_i3129_1_, float p_i3129_2_)
	{
		head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, p_i3129_2_);
		head.setRotationPoint(0.0F, 18 - p_i3129_1_, -6.0F);
		body = new ModelRenderer(this, 28, 8);
		body.addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8, p_i3129_2_);
		body.setRotationPoint(0.0F, 17 - p_i3129_1_, 2.0F);
		leg1 = new ModelRenderer(this, 0, 16);
		leg1.addBox(-2.0F, 0.0F, -2.0F, 4, p_i3129_1_, 4, p_i3129_2_);
		leg1.setRotationPoint(-3.0F, 24 - p_i3129_1_, 7.0F);
		leg2 = new ModelRenderer(this, 0, 16);
		leg2.addBox(-2.0F, 0.0F, -2.0F, 4, p_i3129_1_, 4, p_i3129_2_);
		leg2.setRotationPoint(3.0F, 24 - p_i3129_1_, 7.0F);
		leg3 = new ModelRenderer(this, 0, 16);
		leg3.addBox(-2.0F, 0.0F, -2.0F, 4, p_i3129_1_, 4, p_i3129_2_);
		leg3.setRotationPoint(-3.0F, 24 - p_i3129_1_, -5.0F);
		leg4 = new ModelRenderer(this, 0, 16);
		leg4.addBox(-2.0F, 0.0F, -2.0F, 4, p_i3129_1_, 4, p_i3129_2_);
		leg4.setRotationPoint(3.0F, 24 - p_i3129_1_, -5.0F);
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		if(isChild)
		{
			float var8 = 2.0F;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, field_78145_g * par7, field_78151_h * par7);
			head.render(par7);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
			GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
			body.render(par7);
			leg1.render(par7);
			leg2.render(par7);
			leg3.render(par7);
			leg4.render(par7);
			GL11.glPopMatrix();
		} else
		{
			head.render(par7);
			body.render(par7);
			leg1.render(par7);
			leg2.render(par7);
			leg3.render(par7);
			leg4.render(par7);
		}
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		head.rotateAngleX = par5 / (180F / (float) Math.PI);
		head.rotateAngleY = par4 / (180F / (float) Math.PI);
		body.rotateAngleX = (float) Math.PI / 2F;
		leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
		leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
		leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
	}
}
