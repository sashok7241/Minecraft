package net.minecraft.src;

public class ModelSnowMan extends ModelBase
{
	public ModelRenderer body;
	public ModelRenderer bottomBody;
	public ModelRenderer head;
	public ModelRenderer rightHand;
	public ModelRenderer leftHand;
	
	public ModelSnowMan()
	{
		float var1 = 4.0F;
		float var2 = 0.0F;
		head = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, var2 - 0.5F);
		head.setRotationPoint(0.0F, 0.0F + var1, 0.0F);
		rightHand = new ModelRenderer(this, 32, 0).setTextureSize(64, 64);
		rightHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, var2 - 0.5F);
		rightHand.setRotationPoint(0.0F, 0.0F + var1 + 9.0F - 7.0F, 0.0F);
		leftHand = new ModelRenderer(this, 32, 0).setTextureSize(64, 64);
		leftHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, var2 - 0.5F);
		leftHand.setRotationPoint(0.0F, 0.0F + var1 + 9.0F - 7.0F, 0.0F);
		body = new ModelRenderer(this, 0, 16).setTextureSize(64, 64);
		body.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, var2 - 0.5F);
		body.setRotationPoint(0.0F, 0.0F + var1 + 9.0F, 0.0F);
		bottomBody = new ModelRenderer(this, 0, 36).setTextureSize(64, 64);
		bottomBody.addBox(-6.0F, -12.0F, -6.0F, 12, 12, 12, var2 - 0.5F);
		bottomBody.setRotationPoint(0.0F, 0.0F + var1 + 20.0F, 0.0F);
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		body.render(par7);
		bottomBody.render(par7);
		head.render(par7);
		rightHand.render(par7);
		leftHand.render(par7);
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		head.rotateAngleY = par4 / (180F / (float) Math.PI);
		head.rotateAngleX = par5 / (180F / (float) Math.PI);
		body.rotateAngleY = par4 / (180F / (float) Math.PI) * 0.25F;
		float var8 = MathHelper.sin(body.rotateAngleY);
		float var9 = MathHelper.cos(body.rotateAngleY);
		rightHand.rotateAngleZ = 1.0F;
		leftHand.rotateAngleZ = -1.0F;
		rightHand.rotateAngleY = 0.0F + body.rotateAngleY;
		leftHand.rotateAngleY = (float) Math.PI + body.rotateAngleY;
		rightHand.rotationPointX = var9 * 5.0F;
		rightHand.rotationPointZ = -var8 * 5.0F;
		leftHand.rotationPointX = -var9 * 5.0F;
		leftHand.rotationPointZ = var8 * 5.0F;
	}
}
