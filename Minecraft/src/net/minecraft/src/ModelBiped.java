package net.minecraft.src;


public class ModelBiped extends ModelBase
{
	public ModelRenderer bipedHead;
	public ModelRenderer bipedHeadwear;
	public ModelRenderer bipedBody;
	public ModelRenderer bipedRightArm;
	public ModelRenderer bipedLeftArm;
	public ModelRenderer bipedRightLeg;
	public ModelRenderer bipedLeftLeg;
	public ModelRenderer bipedEars;
	public ModelRenderer bipedCloak;
	public int heldItemLeft;
	public int heldItemRight;
	public boolean isSneak;
	public boolean aimedBow;
	
	public ModelBiped()
	{
		this(0.0F);
	}
	
	public ModelBiped(float par1)
	{
		this(par1, 0.0F, 64, 32);
	}
	
	public ModelBiped(float par1, float par2, int par3, int par4)
	{
		textureWidth = par3;
		textureHeight = par4;
		bipedCloak = new ModelRenderer(this, 0, 0);
		bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, par1);
		bipedEars = new ModelRenderer(this, 24, 0);
		bipedEars.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, par1);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1);
		bipedHead.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
		bipedHeadwear = new ModelRenderer(this, 32, 0);
		bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1 + 0.5F);
		bipedHeadwear.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
		bipedBody = new ModelRenderer(this, 16, 16);
		bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, par1);
		bipedBody.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
		bipedRightArm = new ModelRenderer(this, 40, 16);
		bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, par1);
		bipedRightArm.setRotationPoint(-5.0F, 2.0F + par2, 0.0F);
		bipedLeftArm = new ModelRenderer(this, 40, 16);
		bipedLeftArm.mirror = true;
		bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, par1);
		bipedLeftArm.setRotationPoint(5.0F, 2.0F + par2, 0.0F);
		bipedRightLeg = new ModelRenderer(this, 0, 16);
		bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, par1);
		bipedRightLeg.setRotationPoint(-1.9F, 12.0F + par2, 0.0F);
		bipedLeftLeg = new ModelRenderer(this, 0, 16);
		bipedLeftLeg.mirror = true;
		bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, par1);
		bipedLeftLeg.setRotationPoint(1.9F, 12.0F + par2, 0.0F);
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		if(isChild)
		{
			float var8 = 2.0F;
			GL11.glPushMatrix();
			GL11.glScalef(1.5F / var8, 1.5F / var8, 1.5F / var8);
			GL11.glTranslatef(0.0F, 16.0F * par7, 0.0F);
			bipedHead.render(par7);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
			GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
			bipedBody.render(par7);
			bipedRightArm.render(par7);
			bipedLeftArm.render(par7);
			bipedRightLeg.render(par7);
			bipedLeftLeg.render(par7);
			bipedHeadwear.render(par7);
			GL11.glPopMatrix();
		} else
		{
			bipedHead.render(par7);
			bipedBody.render(par7);
			bipedRightArm.render(par7);
			bipedLeftArm.render(par7);
			bipedRightLeg.render(par7);
			bipedLeftLeg.render(par7);
			bipedHeadwear.render(par7);
		}
	}
	
	public void renderCloak(float par1)
	{
		bipedCloak.render(par1);
	}
	
	public void renderEars(float par1)
	{
		bipedEars.rotateAngleY = bipedHead.rotateAngleY;
		bipedEars.rotateAngleX = bipedHead.rotateAngleX;
		bipedEars.rotationPointX = 0.0F;
		bipedEars.rotationPointY = 0.0F;
		bipedEars.render(par1);
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		bipedHead.rotateAngleY = par4 / (180F / (float) Math.PI);
		bipedHead.rotateAngleX = par5 / (180F / (float) Math.PI);
		bipedHeadwear.rotateAngleY = bipedHead.rotateAngleY;
		bipedHeadwear.rotateAngleX = bipedHead.rotateAngleX;
		bipedRightArm.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 2.0F * par2 * 0.5F;
		bipedLeftArm.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
		bipedRightArm.rotateAngleZ = 0.0F;
		bipedLeftArm.rotateAngleZ = 0.0F;
		bipedRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		bipedLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
		bipedRightLeg.rotateAngleY = 0.0F;
		bipedLeftLeg.rotateAngleY = 0.0F;
		if(isRiding)
		{
			bipedRightArm.rotateAngleX += -((float) Math.PI / 5F);
			bipedLeftArm.rotateAngleX += -((float) Math.PI / 5F);
			bipedRightLeg.rotateAngleX = -((float) Math.PI * 2F / 5F);
			bipedLeftLeg.rotateAngleX = -((float) Math.PI * 2F / 5F);
			bipedRightLeg.rotateAngleY = (float) Math.PI / 10F;
			bipedLeftLeg.rotateAngleY = -((float) Math.PI / 10F);
		}
		if(heldItemLeft != 0)
		{
			bipedLeftArm.rotateAngleX = bipedLeftArm.rotateAngleX * 0.5F - (float) Math.PI / 10F * heldItemLeft;
		}
		if(heldItemRight != 0)
		{
			bipedRightArm.rotateAngleX = bipedRightArm.rotateAngleX * 0.5F - (float) Math.PI / 10F * heldItemRight;
		}
		bipedRightArm.rotateAngleY = 0.0F;
		bipedLeftArm.rotateAngleY = 0.0F;
		float var8;
		float var9;
		if(onGround > -9990.0F)
		{
			var8 = onGround;
			bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(var8) * (float) Math.PI * 2.0F) * 0.2F;
			bipedRightArm.rotationPointZ = MathHelper.sin(bipedBody.rotateAngleY) * 5.0F;
			bipedRightArm.rotationPointX = -MathHelper.cos(bipedBody.rotateAngleY) * 5.0F;
			bipedLeftArm.rotationPointZ = -MathHelper.sin(bipedBody.rotateAngleY) * 5.0F;
			bipedLeftArm.rotationPointX = MathHelper.cos(bipedBody.rotateAngleY) * 5.0F;
			bipedRightArm.rotateAngleY += bipedBody.rotateAngleY;
			bipedLeftArm.rotateAngleY += bipedBody.rotateAngleY;
			bipedLeftArm.rotateAngleX += bipedBody.rotateAngleY;
			var8 = 1.0F - onGround;
			var8 *= var8;
			var8 *= var8;
			var8 = 1.0F - var8;
			var9 = MathHelper.sin(var8 * (float) Math.PI);
			float var10 = MathHelper.sin(onGround * (float) Math.PI) * -(bipedHead.rotateAngleX - 0.7F) * 0.75F;
			bipedRightArm.rotateAngleX = (float) (bipedRightArm.rotateAngleX - (var9 * 1.2D + var10));
			bipedRightArm.rotateAngleY += bipedBody.rotateAngleY * 2.0F;
			bipedRightArm.rotateAngleZ = MathHelper.sin(onGround * (float) Math.PI) * -0.4F;
		}
		if(isSneak)
		{
			bipedBody.rotateAngleX = 0.5F;
			bipedRightArm.rotateAngleX += 0.4F;
			bipedLeftArm.rotateAngleX += 0.4F;
			bipedRightLeg.rotationPointZ = 4.0F;
			bipedLeftLeg.rotationPointZ = 4.0F;
			bipedRightLeg.rotationPointY = 9.0F;
			bipedLeftLeg.rotationPointY = 9.0F;
			bipedHead.rotationPointY = 1.0F;
			bipedHeadwear.rotationPointY = 1.0F;
		} else
		{
			bipedBody.rotateAngleX = 0.0F;
			bipedRightLeg.rotationPointZ = 0.1F;
			bipedLeftLeg.rotationPointZ = 0.1F;
			bipedRightLeg.rotationPointY = 12.0F;
			bipedLeftLeg.rotationPointY = 12.0F;
			bipedHead.rotationPointY = 0.0F;
			bipedHeadwear.rotationPointY = 0.0F;
		}
		bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
		bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
		bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
		bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
		if(aimedBow)
		{
			var8 = 0.0F;
			var9 = 0.0F;
			bipedRightArm.rotateAngleZ = 0.0F;
			bipedLeftArm.rotateAngleZ = 0.0F;
			bipedRightArm.rotateAngleY = -(0.1F - var8 * 0.6F) + bipedHead.rotateAngleY;
			bipedLeftArm.rotateAngleY = 0.1F - var8 * 0.6F + bipedHead.rotateAngleY + 0.4F;
			bipedRightArm.rotateAngleX = -((float) Math.PI / 2F) + bipedHead.rotateAngleX;
			bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F) + bipedHead.rotateAngleX;
			bipedRightArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
			bipedLeftArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
			bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
			bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
			bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
			bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
		}
	}
}
