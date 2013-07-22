package net.minecraft.src;


public class ModelChicken extends ModelBase
{
	public ModelRenderer head;
	public ModelRenderer body;
	public ModelRenderer rightLeg;
	public ModelRenderer leftLeg;
	public ModelRenderer rightWing;
	public ModelRenderer leftWing;
	public ModelRenderer bill;
	public ModelRenderer chin;
	
	public ModelChicken()
	{
		byte var1 = 16;
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
		head.setRotationPoint(0.0F, -1 + var1, -4.0F);
		bill = new ModelRenderer(this, 14, 0);
		bill.addBox(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
		bill.setRotationPoint(0.0F, -1 + var1, -4.0F);
		chin = new ModelRenderer(this, 14, 4);
		chin.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
		chin.setRotationPoint(0.0F, -1 + var1, -4.0F);
		body = new ModelRenderer(this, 0, 9);
		body.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
		body.setRotationPoint(0.0F, var1, 0.0F);
		rightLeg = new ModelRenderer(this, 26, 0);
		rightLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
		rightLeg.setRotationPoint(-2.0F, 3 + var1, 1.0F);
		leftLeg = new ModelRenderer(this, 26, 0);
		leftLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
		leftLeg.setRotationPoint(1.0F, 3 + var1, 1.0F);
		rightWing = new ModelRenderer(this, 24, 13);
		rightWing.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6);
		rightWing.setRotationPoint(-4.0F, -3 + var1, 0.0F);
		leftWing = new ModelRenderer(this, 24, 13);
		leftWing.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6);
		leftWing.setRotationPoint(4.0F, -3 + var1, 0.0F);
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		if(isChild)
		{
			float var8 = 2.0F;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 5.0F * par7, 2.0F * par7);
			head.render(par7);
			bill.render(par7);
			chin.render(par7);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
			GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
			body.render(par7);
			rightLeg.render(par7);
			leftLeg.render(par7);
			rightWing.render(par7);
			leftWing.render(par7);
			GL11.glPopMatrix();
		} else
		{
			head.render(par7);
			bill.render(par7);
			chin.render(par7);
			body.render(par7);
			rightLeg.render(par7);
			leftLeg.render(par7);
			rightWing.render(par7);
			leftWing.render(par7);
		}
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		head.rotateAngleX = par5 / (180F / (float) Math.PI);
		head.rotateAngleY = par4 / (180F / (float) Math.PI);
		bill.rotateAngleX = head.rotateAngleX;
		bill.rotateAngleY = head.rotateAngleY;
		chin.rotateAngleX = head.rotateAngleX;
		chin.rotateAngleY = head.rotateAngleY;
		body.rotateAngleX = (float) Math.PI / 2F;
		rightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		leftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
		rightWing.rotateAngleZ = par3;
		leftWing.rotateAngleZ = -par3;
	}
}
