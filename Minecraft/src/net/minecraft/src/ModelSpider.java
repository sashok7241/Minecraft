package net.minecraft.src;

public class ModelSpider extends ModelBase
{
	public ModelRenderer spiderHead;
	public ModelRenderer spiderNeck;
	public ModelRenderer spiderBody;
	public ModelRenderer spiderLeg1;
	public ModelRenderer spiderLeg2;
	public ModelRenderer spiderLeg3;
	public ModelRenderer spiderLeg4;
	public ModelRenderer spiderLeg5;
	public ModelRenderer spiderLeg6;
	public ModelRenderer spiderLeg7;
	public ModelRenderer spiderLeg8;
	
	public ModelSpider()
	{
		float var1 = 0.0F;
		byte var2 = 15;
		spiderHead = new ModelRenderer(this, 32, 4);
		spiderHead.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, var1);
		spiderHead.setRotationPoint(0.0F, var2, -3.0F);
		spiderNeck = new ModelRenderer(this, 0, 0);
		spiderNeck.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, var1);
		spiderNeck.setRotationPoint(0.0F, var2, 0.0F);
		spiderBody = new ModelRenderer(this, 0, 12);
		spiderBody.addBox(-5.0F, -4.0F, -6.0F, 10, 8, 12, var1);
		spiderBody.setRotationPoint(0.0F, var2, 9.0F);
		spiderLeg1 = new ModelRenderer(this, 18, 0);
		spiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		spiderLeg1.setRotationPoint(-4.0F, var2, 2.0F);
		spiderLeg2 = new ModelRenderer(this, 18, 0);
		spiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		spiderLeg2.setRotationPoint(4.0F, var2, 2.0F);
		spiderLeg3 = new ModelRenderer(this, 18, 0);
		spiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		spiderLeg3.setRotationPoint(-4.0F, var2, 1.0F);
		spiderLeg4 = new ModelRenderer(this, 18, 0);
		spiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		spiderLeg4.setRotationPoint(4.0F, var2, 1.0F);
		spiderLeg5 = new ModelRenderer(this, 18, 0);
		spiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		spiderLeg5.setRotationPoint(-4.0F, var2, 0.0F);
		spiderLeg6 = new ModelRenderer(this, 18, 0);
		spiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		spiderLeg6.setRotationPoint(4.0F, var2, 0.0F);
		spiderLeg7 = new ModelRenderer(this, 18, 0);
		spiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		spiderLeg7.setRotationPoint(-4.0F, var2, -1.0F);
		spiderLeg8 = new ModelRenderer(this, 18, 0);
		spiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		spiderLeg8.setRotationPoint(4.0F, var2, -1.0F);
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		spiderHead.render(par7);
		spiderNeck.render(par7);
		spiderBody.render(par7);
		spiderLeg1.render(par7);
		spiderLeg2.render(par7);
		spiderLeg3.render(par7);
		spiderLeg4.render(par7);
		spiderLeg5.render(par7);
		spiderLeg6.render(par7);
		spiderLeg7.render(par7);
		spiderLeg8.render(par7);
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		spiderHead.rotateAngleY = par4 / (180F / (float) Math.PI);
		spiderHead.rotateAngleX = par5 / (180F / (float) Math.PI);
		float var8 = (float) Math.PI / 4F;
		spiderLeg1.rotateAngleZ = -var8;
		spiderLeg2.rotateAngleZ = var8;
		spiderLeg3.rotateAngleZ = -var8 * 0.74F;
		spiderLeg4.rotateAngleZ = var8 * 0.74F;
		spiderLeg5.rotateAngleZ = -var8 * 0.74F;
		spiderLeg6.rotateAngleZ = var8 * 0.74F;
		spiderLeg7.rotateAngleZ = -var8;
		spiderLeg8.rotateAngleZ = var8;
		float var9 = -0.0F;
		float var10 = 0.3926991F;
		spiderLeg1.rotateAngleY = var10 * 2.0F + var9;
		spiderLeg2.rotateAngleY = -var10 * 2.0F - var9;
		spiderLeg3.rotateAngleY = var10 * 1.0F + var9;
		spiderLeg4.rotateAngleY = -var10 * 1.0F - var9;
		spiderLeg5.rotateAngleY = -var10 * 1.0F + var9;
		spiderLeg6.rotateAngleY = var10 * 1.0F - var9;
		spiderLeg7.rotateAngleY = -var10 * 2.0F + var9;
		spiderLeg8.rotateAngleY = var10 * 2.0F - var9;
		float var11 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 0.0F) * 0.4F) * par2;
		float var12 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * par2;
		float var13 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + (float) Math.PI / 2F) * 0.4F) * par2;
		float var14 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + (float) Math.PI * 3F / 2F) * 0.4F) * par2;
		float var15 = Math.abs(MathHelper.sin(par1 * 0.6662F + 0.0F) * 0.4F) * par2;
		float var16 = Math.abs(MathHelper.sin(par1 * 0.6662F + (float) Math.PI) * 0.4F) * par2;
		float var17 = Math.abs(MathHelper.sin(par1 * 0.6662F + (float) Math.PI / 2F) * 0.4F) * par2;
		float var18 = Math.abs(MathHelper.sin(par1 * 0.6662F + (float) Math.PI * 3F / 2F) * 0.4F) * par2;
		spiderLeg1.rotateAngleY += var11;
		spiderLeg2.rotateAngleY += -var11;
		spiderLeg3.rotateAngleY += var12;
		spiderLeg4.rotateAngleY += -var12;
		spiderLeg5.rotateAngleY += var13;
		spiderLeg6.rotateAngleY += -var13;
		spiderLeg7.rotateAngleY += var14;
		spiderLeg8.rotateAngleY += -var14;
		spiderLeg1.rotateAngleZ += var15;
		spiderLeg2.rotateAngleZ += -var15;
		spiderLeg3.rotateAngleZ += var16;
		spiderLeg4.rotateAngleZ += -var16;
		spiderLeg5.rotateAngleZ += var17;
		spiderLeg6.rotateAngleZ += -var17;
		spiderLeg7.rotateAngleZ += var18;
		spiderLeg8.rotateAngleZ += -var18;
	}
}
