package net.minecraft.src;


public class ModelOcelot extends ModelBase
{
	ModelRenderer ocelotBackLeftLeg;
	ModelRenderer ocelotBackRightLeg;
	ModelRenderer ocelotFrontLeftLeg;
	ModelRenderer ocelotFrontRightLeg;
	ModelRenderer ocelotTail;
	ModelRenderer ocelotTail2;
	ModelRenderer ocelotHead;
	ModelRenderer ocelotBody;
	int field_78163_i = 1;
	
	public ModelOcelot()
	{
		setTextureOffset("head.main", 0, 0);
		setTextureOffset("head.nose", 0, 24);
		setTextureOffset("head.ear1", 0, 10);
		setTextureOffset("head.ear2", 6, 10);
		ocelotHead = new ModelRenderer(this, "head");
		ocelotHead.addBox("main", -2.5F, -2.0F, -3.0F, 5, 4, 5);
		ocelotHead.addBox("nose", -1.5F, 0.0F, -4.0F, 3, 2, 2);
		ocelotHead.addBox("ear1", -2.0F, -3.0F, 0.0F, 1, 1, 2);
		ocelotHead.addBox("ear2", 1.0F, -3.0F, 0.0F, 1, 1, 2);
		ocelotHead.setRotationPoint(0.0F, 15.0F, -9.0F);
		ocelotBody = new ModelRenderer(this, 20, 0);
		ocelotBody.addBox(-2.0F, 3.0F, -8.0F, 4, 16, 6, 0.0F);
		ocelotBody.setRotationPoint(0.0F, 12.0F, -10.0F);
		ocelotTail = new ModelRenderer(this, 0, 15);
		ocelotTail.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
		ocelotTail.rotateAngleX = 0.9F;
		ocelotTail.setRotationPoint(0.0F, 15.0F, 8.0F);
		ocelotTail2 = new ModelRenderer(this, 4, 15);
		ocelotTail2.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
		ocelotTail2.setRotationPoint(0.0F, 20.0F, 14.0F);
		ocelotBackLeftLeg = new ModelRenderer(this, 8, 13);
		ocelotBackLeftLeg.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
		ocelotBackLeftLeg.setRotationPoint(1.1F, 18.0F, 5.0F);
		ocelotBackRightLeg = new ModelRenderer(this, 8, 13);
		ocelotBackRightLeg.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
		ocelotBackRightLeg.setRotationPoint(-1.1F, 18.0F, 5.0F);
		ocelotFrontLeftLeg = new ModelRenderer(this, 40, 0);
		ocelotFrontLeftLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
		ocelotFrontLeftLeg.setRotationPoint(1.2F, 13.8F, -5.0F);
		ocelotFrontRightLeg = new ModelRenderer(this, 40, 0);
		ocelotFrontRightLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
		ocelotFrontRightLeg.setRotationPoint(-1.2F, 13.8F, -5.0F);
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		if(isChild)
		{
			float var8 = 2.0F;
			GL11.glPushMatrix();
			GL11.glScalef(1.5F / var8, 1.5F / var8, 1.5F / var8);
			GL11.glTranslatef(0.0F, 10.0F * par7, 4.0F * par7);
			ocelotHead.render(par7);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
			GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
			ocelotBody.render(par7);
			ocelotBackLeftLeg.render(par7);
			ocelotBackRightLeg.render(par7);
			ocelotFrontLeftLeg.render(par7);
			ocelotFrontRightLeg.render(par7);
			ocelotTail.render(par7);
			ocelotTail2.render(par7);
			GL11.glPopMatrix();
		} else
		{
			ocelotHead.render(par7);
			ocelotBody.render(par7);
			ocelotTail.render(par7);
			ocelotTail2.render(par7);
			ocelotBackLeftLeg.render(par7);
			ocelotBackRightLeg.render(par7);
			ocelotFrontLeftLeg.render(par7);
			ocelotFrontRightLeg.render(par7);
		}
	}
	
	@Override public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
	{
		EntityOcelot var5 = (EntityOcelot) par1EntityLivingBase;
		ocelotBody.rotationPointY = 12.0F;
		ocelotBody.rotationPointZ = -10.0F;
		ocelotHead.rotationPointY = 15.0F;
		ocelotHead.rotationPointZ = -9.0F;
		ocelotTail.rotationPointY = 15.0F;
		ocelotTail.rotationPointZ = 8.0F;
		ocelotTail2.rotationPointY = 20.0F;
		ocelotTail2.rotationPointZ = 14.0F;
		ocelotFrontLeftLeg.rotationPointY = ocelotFrontRightLeg.rotationPointY = 13.8F;
		ocelotFrontLeftLeg.rotationPointZ = ocelotFrontRightLeg.rotationPointZ = -5.0F;
		ocelotBackLeftLeg.rotationPointY = ocelotBackRightLeg.rotationPointY = 18.0F;
		ocelotBackLeftLeg.rotationPointZ = ocelotBackRightLeg.rotationPointZ = 5.0F;
		ocelotTail.rotateAngleX = 0.9F;
		if(var5.isSneaking())
		{
			++ocelotBody.rotationPointY;
			ocelotHead.rotationPointY += 2.0F;
			++ocelotTail.rotationPointY;
			ocelotTail2.rotationPointY += -4.0F;
			ocelotTail2.rotationPointZ += 2.0F;
			ocelotTail.rotateAngleX = (float) Math.PI / 2F;
			ocelotTail2.rotateAngleX = (float) Math.PI / 2F;
			field_78163_i = 0;
		} else if(var5.isSprinting())
		{
			ocelotTail2.rotationPointY = ocelotTail.rotationPointY;
			ocelotTail2.rotationPointZ += 2.0F;
			ocelotTail.rotateAngleX = (float) Math.PI / 2F;
			ocelotTail2.rotateAngleX = (float) Math.PI / 2F;
			field_78163_i = 2;
		} else if(var5.isSitting())
		{
			ocelotBody.rotateAngleX = (float) Math.PI / 4F;
			ocelotBody.rotationPointY += -4.0F;
			ocelotBody.rotationPointZ += 5.0F;
			ocelotHead.rotationPointY += -3.3F;
			++ocelotHead.rotationPointZ;
			ocelotTail.rotationPointY += 8.0F;
			ocelotTail.rotationPointZ += -2.0F;
			ocelotTail2.rotationPointY += 2.0F;
			ocelotTail2.rotationPointZ += -0.8F;
			ocelotTail.rotateAngleX = 1.7278761F;
			ocelotTail2.rotateAngleX = 2.670354F;
			ocelotFrontLeftLeg.rotateAngleX = ocelotFrontRightLeg.rotateAngleX = -0.15707964F;
			ocelotFrontLeftLeg.rotationPointY = ocelotFrontRightLeg.rotationPointY = 15.8F;
			ocelotFrontLeftLeg.rotationPointZ = ocelotFrontRightLeg.rotationPointZ = -7.0F;
			ocelotBackLeftLeg.rotateAngleX = ocelotBackRightLeg.rotateAngleX = -((float) Math.PI / 2F);
			ocelotBackLeftLeg.rotationPointY = ocelotBackRightLeg.rotationPointY = 21.0F;
			ocelotBackLeftLeg.rotationPointZ = ocelotBackRightLeg.rotationPointZ = 1.0F;
			field_78163_i = 3;
		} else
		{
			field_78163_i = 1;
		}
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		ocelotHead.rotateAngleX = par5 / (180F / (float) Math.PI);
		ocelotHead.rotateAngleY = par4 / (180F / (float) Math.PI);
		if(field_78163_i != 3)
		{
			ocelotBody.rotateAngleX = (float) Math.PI / 2F;
			if(field_78163_i == 2)
			{
				ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.0F * par2;
				ocelotBackRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + 0.3F) * 1.0F * par2;
				ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI + 0.3F) * 1.0F * par2;
				ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.0F * par2;
				ocelotTail2.rotateAngleX = 1.7278761F + (float) Math.PI / 10F * MathHelper.cos(par1) * par2;
			} else
			{
				ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.0F * par2;
				ocelotBackRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.0F * par2;
				ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.0F * par2;
				ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.0F * par2;
				if(field_78163_i == 1)
				{
					ocelotTail2.rotateAngleX = 1.7278761F + (float) Math.PI / 4F * MathHelper.cos(par1) * par2;
				} else
				{
					ocelotTail2.rotateAngleX = 1.7278761F + 0.47123894F * MathHelper.cos(par1) * par2;
				}
			}
		}
	}
}
