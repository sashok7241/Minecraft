package net.minecraft.src;

public class ModelBook extends ModelBase
{
	public ModelRenderer coverRight = new ModelRenderer(this).setTextureOffset(0, 0).addBox(-6.0F, -5.0F, 0.0F, 6, 10, 0);
	public ModelRenderer coverLeft = new ModelRenderer(this).setTextureOffset(16, 0).addBox(0.0F, -5.0F, 0.0F, 6, 10, 0);
	public ModelRenderer pagesRight = new ModelRenderer(this).setTextureOffset(0, 10).addBox(0.0F, -4.0F, -0.99F, 5, 8, 1);
	public ModelRenderer pagesLeft = new ModelRenderer(this).setTextureOffset(12, 10).addBox(0.0F, -4.0F, -0.01F, 5, 8, 1);
	public ModelRenderer flippingPageRight = new ModelRenderer(this).setTextureOffset(24, 10).addBox(0.0F, -4.0F, 0.0F, 5, 8, 0);
	public ModelRenderer flippingPageLeft = new ModelRenderer(this).setTextureOffset(24, 10).addBox(0.0F, -4.0F, 0.0F, 5, 8, 0);
	public ModelRenderer bookSpine = new ModelRenderer(this).setTextureOffset(12, 0).addBox(-1.0F, -5.0F, 0.0F, 2, 10, 0);
	
	public ModelBook()
	{
		coverRight.setRotationPoint(0.0F, 0.0F, -1.0F);
		coverLeft.setRotationPoint(0.0F, 0.0F, 1.0F);
		bookSpine.rotateAngleY = (float) Math.PI / 2F;
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		coverRight.render(par7);
		coverLeft.render(par7);
		bookSpine.render(par7);
		pagesRight.render(par7);
		pagesLeft.render(par7);
		flippingPageRight.render(par7);
		flippingPageLeft.render(par7);
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		float var8 = (MathHelper.sin(par1 * 0.02F) * 0.1F + 1.25F) * par4;
		coverRight.rotateAngleY = (float) Math.PI + var8;
		coverLeft.rotateAngleY = -var8;
		pagesRight.rotateAngleY = var8;
		pagesLeft.rotateAngleY = -var8;
		flippingPageRight.rotateAngleY = var8 - var8 * 2.0F * par2;
		flippingPageLeft.rotateAngleY = var8 - var8 * 2.0F * par3;
		pagesRight.rotationPointX = MathHelper.sin(var8);
		pagesLeft.rotationPointX = MathHelper.sin(var8);
		flippingPageRight.rotationPointX = MathHelper.sin(var8);
		flippingPageLeft.rotationPointX = MathHelper.sin(var8);
	}
}
