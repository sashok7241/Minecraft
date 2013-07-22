package net.minecraft.src;


public class ModelDragon extends ModelBase
{
	private ModelRenderer head;
	private ModelRenderer neck;
	private ModelRenderer jaw;
	private ModelRenderer body;
	private ModelRenderer rearLeg;
	private ModelRenderer frontLeg;
	private ModelRenderer rearLegTip;
	private ModelRenderer frontLegTip;
	private ModelRenderer rearFoot;
	private ModelRenderer frontFoot;
	private ModelRenderer wing;
	private ModelRenderer wingTip;
	private float partialTicks;
	
	public ModelDragon(float par1)
	{
		textureWidth = 256;
		textureHeight = 256;
		setTextureOffset("body.body", 0, 0);
		setTextureOffset("wing.skin", -56, 88);
		setTextureOffset("wingtip.skin", -56, 144);
		setTextureOffset("rearleg.main", 0, 0);
		setTextureOffset("rearfoot.main", 112, 0);
		setTextureOffset("rearlegtip.main", 196, 0);
		setTextureOffset("head.upperhead", 112, 30);
		setTextureOffset("wing.bone", 112, 88);
		setTextureOffset("head.upperlip", 176, 44);
		setTextureOffset("jaw.jaw", 176, 65);
		setTextureOffset("frontleg.main", 112, 104);
		setTextureOffset("wingtip.bone", 112, 136);
		setTextureOffset("frontfoot.main", 144, 104);
		setTextureOffset("neck.box", 192, 104);
		setTextureOffset("frontlegtip.main", 226, 138);
		setTextureOffset("body.scale", 220, 53);
		setTextureOffset("head.scale", 0, 0);
		setTextureOffset("neck.scale", 48, 0);
		setTextureOffset("head.nostril", 112, 0);
		float var2 = -16.0F;
		head = new ModelRenderer(this, "head");
		head.addBox("upperlip", -6.0F, -1.0F, -8.0F + var2, 12, 5, 16);
		head.addBox("upperhead", -8.0F, -8.0F, 6.0F + var2, 16, 16, 16);
		head.mirror = true;
		head.addBox("scale", -5.0F, -12.0F, 12.0F + var2, 2, 4, 6);
		head.addBox("nostril", -5.0F, -3.0F, -6.0F + var2, 2, 2, 4);
		head.mirror = false;
		head.addBox("scale", 3.0F, -12.0F, 12.0F + var2, 2, 4, 6);
		head.addBox("nostril", 3.0F, -3.0F, -6.0F + var2, 2, 2, 4);
		jaw = new ModelRenderer(this, "jaw");
		jaw.setRotationPoint(0.0F, 4.0F, 8.0F + var2);
		jaw.addBox("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
		head.addChild(jaw);
		neck = new ModelRenderer(this, "neck");
		neck.addBox("box", -5.0F, -5.0F, -5.0F, 10, 10, 10);
		neck.addBox("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6);
		body = new ModelRenderer(this, "body");
		body.setRotationPoint(0.0F, 4.0F, 8.0F);
		body.addBox("body", -12.0F, 0.0F, -16.0F, 24, 24, 64);
		body.addBox("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12);
		body.addBox("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12);
		body.addBox("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12);
		wing = new ModelRenderer(this, "wing");
		wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
		wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
		wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
		wingTip = new ModelRenderer(this, "wingtip");
		wingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
		wingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
		wingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
		wing.addChild(wingTip);
		frontLeg = new ModelRenderer(this, "frontleg");
		frontLeg.setRotationPoint(-12.0F, 20.0F, 2.0F);
		frontLeg.addBox("main", -4.0F, -4.0F, -4.0F, 8, 24, 8);
		frontLegTip = new ModelRenderer(this, "frontlegtip");
		frontLegTip.setRotationPoint(0.0F, 20.0F, -1.0F);
		frontLegTip.addBox("main", -3.0F, -1.0F, -3.0F, 6, 24, 6);
		frontLeg.addChild(frontLegTip);
		frontFoot = new ModelRenderer(this, "frontfoot");
		frontFoot.setRotationPoint(0.0F, 23.0F, 0.0F);
		frontFoot.addBox("main", -4.0F, 0.0F, -12.0F, 8, 4, 16);
		frontLegTip.addChild(frontFoot);
		rearLeg = new ModelRenderer(this, "rearleg");
		rearLeg.setRotationPoint(-16.0F, 16.0F, 42.0F);
		rearLeg.addBox("main", -8.0F, -4.0F, -8.0F, 16, 32, 16);
		rearLegTip = new ModelRenderer(this, "rearlegtip");
		rearLegTip.setRotationPoint(0.0F, 32.0F, -4.0F);
		rearLegTip.addBox("main", -6.0F, -2.0F, 0.0F, 12, 32, 12);
		rearLeg.addChild(rearLegTip);
		rearFoot = new ModelRenderer(this, "rearfoot");
		rearFoot.setRotationPoint(0.0F, 31.0F, 4.0F);
		rearFoot.addBox("main", -9.0F, 0.0F, -20.0F, 18, 6, 24);
		rearLegTip.addChild(rearFoot);
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		GL11.glPushMatrix();
		EntityDragon var8 = (EntityDragon) par1Entity;
		float var9 = var8.prevAnimTime + (var8.animTime - var8.prevAnimTime) * partialTicks;
		jaw.rotateAngleX = (float) (Math.sin(var9 * (float) Math.PI * 2.0F) + 1.0D) * 0.2F;
		float var10 = (float) (Math.sin(var9 * (float) Math.PI * 2.0F - 1.0F) + 1.0D);
		var10 = (var10 * var10 * 1.0F + var10 * 2.0F) * 0.05F;
		GL11.glTranslatef(0.0F, var10 - 2.0F, -3.0F);
		GL11.glRotatef(var10 * 2.0F, 1.0F, 0.0F, 0.0F);
		float var11 = -30.0F;
		float var13 = 0.0F;
		float var14 = 1.5F;
		double[] var15 = var8.getMovementOffsets(6, partialTicks);
		float var16 = updateRotations(var8.getMovementOffsets(5, partialTicks)[0] - var8.getMovementOffsets(10, partialTicks)[0]);
		float var17 = updateRotations(var8.getMovementOffsets(5, partialTicks)[0] + var16 / 2.0F);
		var11 += 2.0F;
		float var18 = var9 * (float) Math.PI * 2.0F;
		var11 = 20.0F;
		float var12 = -12.0F;
		float var21;
		for(int var19 = 0; var19 < 5; ++var19)
		{
			double[] var20 = var8.getMovementOffsets(5 - var19, partialTicks);
			var21 = (float) Math.cos(var19 * 0.45F + var18) * 0.15F;
			neck.rotateAngleY = updateRotations(var20[0] - var15[0]) * (float) Math.PI / 180.0F * var14;
			neck.rotateAngleX = var21 + (float) (var20[1] - var15[1]) * (float) Math.PI / 180.0F * var14 * 5.0F;
			neck.rotateAngleZ = -updateRotations(var20[0] - var17) * (float) Math.PI / 180.0F * var14;
			neck.rotationPointY = var11;
			neck.rotationPointZ = var12;
			neck.rotationPointX = var13;
			var11 = (float) (var11 + Math.sin(neck.rotateAngleX) * 10.0D);
			var12 = (float) (var12 - Math.cos(neck.rotateAngleY) * Math.cos(neck.rotateAngleX) * 10.0D);
			var13 = (float) (var13 - Math.sin(neck.rotateAngleY) * Math.cos(neck.rotateAngleX) * 10.0D);
			neck.render(par7);
		}
		head.rotationPointY = var11;
		head.rotationPointZ = var12;
		head.rotationPointX = var13;
		double[] var23 = var8.getMovementOffsets(0, partialTicks);
		head.rotateAngleY = updateRotations(var23[0] - var15[0]) * (float) Math.PI / 180.0F * 1.0F;
		head.rotateAngleZ = -updateRotations(var23[0] - var17) * (float) Math.PI / 180.0F * 1.0F;
		head.render(par7);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-var16 * var14 * 1.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(0.0F, -1.0F, 0.0F);
		body.rotateAngleZ = 0.0F;
		body.render(par7);
		for(int var22 = 0; var22 < 2; ++var22)
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
			var21 = var9 * (float) Math.PI * 2.0F;
			wing.rotateAngleX = 0.125F - (float) Math.cos(var21) * 0.2F;
			wing.rotateAngleY = 0.25F;
			wing.rotateAngleZ = (float) (Math.sin(var21) + 0.125D) * 0.8F;
			wingTip.rotateAngleZ = -((float) (Math.sin(var21 + 2.0F) + 0.5D)) * 0.75F;
			rearLeg.rotateAngleX = 1.0F + var10 * 0.1F;
			rearLegTip.rotateAngleX = 0.5F + var10 * 0.1F;
			rearFoot.rotateAngleX = 0.75F + var10 * 0.1F;
			frontLeg.rotateAngleX = 1.3F + var10 * 0.1F;
			frontLegTip.rotateAngleX = -0.5F - var10 * 0.1F;
			frontFoot.rotateAngleX = 0.75F + var10 * 0.1F;
			wing.render(par7);
			frontLeg.render(par7);
			rearLeg.render(par7);
			GL11.glScalef(-1.0F, 1.0F, 1.0F);
			if(var22 == 0)
			{
				GL11.glCullFace(GL11.GL_FRONT);
			}
		}
		GL11.glPopMatrix();
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glDisable(GL11.GL_CULL_FACE);
		float var24 = -((float) Math.sin(var9 * (float) Math.PI * 2.0F)) * 0.0F;
		var18 = var9 * (float) Math.PI * 2.0F;
		var11 = 10.0F;
		var12 = 60.0F;
		var13 = 0.0F;
		var15 = var8.getMovementOffsets(11, partialTicks);
		for(int var25 = 0; var25 < 12; ++var25)
		{
			var23 = var8.getMovementOffsets(12 + var25, partialTicks);
			var24 = (float) (var24 + Math.sin(var25 * 0.45F + var18) * 0.05000000074505806D);
			neck.rotateAngleY = (updateRotations(var23[0] - var15[0]) * var14 + 180.0F) * (float) Math.PI / 180.0F;
			neck.rotateAngleX = var24 + (float) (var23[1] - var15[1]) * (float) Math.PI / 180.0F * var14 * 5.0F;
			neck.rotateAngleZ = updateRotations(var23[0] - var17) * (float) Math.PI / 180.0F * var14;
			neck.rotationPointY = var11;
			neck.rotationPointZ = var12;
			neck.rotationPointX = var13;
			var11 = (float) (var11 + Math.sin(neck.rotateAngleX) * 10.0D);
			var12 = (float) (var12 - Math.cos(neck.rotateAngleY) * Math.cos(neck.rotateAngleX) * 10.0D);
			var13 = (float) (var13 - Math.sin(neck.rotateAngleY) * Math.cos(neck.rotateAngleX) * 10.0D);
			neck.render(par7);
		}
		GL11.glPopMatrix();
	}
	
	@Override public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4)
	{
		partialTicks = par4;
	}
	
	private float updateRotations(double par1)
	{
		while(par1 >= 180.0D)
		{
			par1 -= 360.0D;
		}
		while(par1 < -180.0D)
		{
			par1 += 360.0D;
		}
		return (float) par1;
	}
}
