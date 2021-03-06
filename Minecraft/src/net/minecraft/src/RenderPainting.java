package net.minecraft.src;


public class RenderPainting extends Render
{
	private static final ResourceLocation field_110807_a = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderThePainting((EntityPainting) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110806_a((EntityPainting) par1Entity);
	}
	
	protected ResourceLocation func_110806_a(EntityPainting par1EntityPainting)
	{
		return field_110807_a;
	}
	
	private void func_77008_a(EntityPainting par1EntityPainting, float par2, float par3)
	{
		int var4 = MathHelper.floor_double(par1EntityPainting.posX);
		int var5 = MathHelper.floor_double(par1EntityPainting.posY + par3 / 16.0F);
		int var6 = MathHelper.floor_double(par1EntityPainting.posZ);
		if(par1EntityPainting.hangingDirection == 2)
		{
			var4 = MathHelper.floor_double(par1EntityPainting.posX + par2 / 16.0F);
		}
		if(par1EntityPainting.hangingDirection == 1)
		{
			var6 = MathHelper.floor_double(par1EntityPainting.posZ - par2 / 16.0F);
		}
		if(par1EntityPainting.hangingDirection == 0)
		{
			var4 = MathHelper.floor_double(par1EntityPainting.posX - par2 / 16.0F);
		}
		if(par1EntityPainting.hangingDirection == 3)
		{
			var6 = MathHelper.floor_double(par1EntityPainting.posZ + par2 / 16.0F);
		}
		int var7 = renderManager.worldObj.getLightBrightnessForSkyBlocks(var4, var5, var6, 0);
		int var8 = var7 % 65536;
		int var9 = var7 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var8, var9);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
	}
	
	private void func_77010_a(EntityPainting par1EntityPainting, int par2, int par3, int par4, int par5)
	{
		float var6 = -par2 / 2.0F;
		float var7 = -par3 / 2.0F;
		float var8 = 0.5F;
		float var9 = 0.75F;
		float var10 = 0.8125F;
		float var11 = 0.0F;
		float var12 = 0.0625F;
		float var13 = 0.75F;
		float var14 = 0.8125F;
		float var15 = 0.001953125F;
		float var16 = 0.001953125F;
		float var17 = 0.7519531F;
		float var18 = 0.7519531F;
		float var19 = 0.0F;
		float var20 = 0.0625F;
		for(int var21 = 0; var21 < par2 / 16; ++var21)
		{
			for(int var22 = 0; var22 < par3 / 16; ++var22)
			{
				float var23 = var6 + (var21 + 1) * 16;
				float var24 = var6 + var21 * 16;
				float var25 = var7 + (var22 + 1) * 16;
				float var26 = var7 + var22 * 16;
				func_77008_a(par1EntityPainting, (var23 + var24) / 2.0F, (var25 + var26) / 2.0F);
				float var27 = (par4 + par2 - var21 * 16) / 256.0F;
				float var28 = (par4 + par2 - (var21 + 1) * 16) / 256.0F;
				float var29 = (par5 + par3 - var22 * 16) / 256.0F;
				float var30 = (par5 + par3 - (var22 + 1) * 16) / 256.0F;
				Tessellator var31 = Tessellator.instance;
				var31.startDrawingQuads();
				var31.setNormal(0.0F, 0.0F, -1.0F);
				var31.addVertexWithUV(var23, var26, -var8, var28, var29);
				var31.addVertexWithUV(var24, var26, -var8, var27, var29);
				var31.addVertexWithUV(var24, var25, -var8, var27, var30);
				var31.addVertexWithUV(var23, var25, -var8, var28, var30);
				var31.setNormal(0.0F, 0.0F, 1.0F);
				var31.addVertexWithUV(var23, var25, var8, var9, var11);
				var31.addVertexWithUV(var24, var25, var8, var10, var11);
				var31.addVertexWithUV(var24, var26, var8, var10, var12);
				var31.addVertexWithUV(var23, var26, var8, var9, var12);
				var31.setNormal(0.0F, 1.0F, 0.0F);
				var31.addVertexWithUV(var23, var25, -var8, var13, var15);
				var31.addVertexWithUV(var24, var25, -var8, var14, var15);
				var31.addVertexWithUV(var24, var25, var8, var14, var16);
				var31.addVertexWithUV(var23, var25, var8, var13, var16);
				var31.setNormal(0.0F, -1.0F, 0.0F);
				var31.addVertexWithUV(var23, var26, var8, var13, var15);
				var31.addVertexWithUV(var24, var26, var8, var14, var15);
				var31.addVertexWithUV(var24, var26, -var8, var14, var16);
				var31.addVertexWithUV(var23, var26, -var8, var13, var16);
				var31.setNormal(-1.0F, 0.0F, 0.0F);
				var31.addVertexWithUV(var23, var25, var8, var18, var19);
				var31.addVertexWithUV(var23, var26, var8, var18, var20);
				var31.addVertexWithUV(var23, var26, -var8, var17, var20);
				var31.addVertexWithUV(var23, var25, -var8, var17, var19);
				var31.setNormal(1.0F, 0.0F, 0.0F);
				var31.addVertexWithUV(var24, var25, -var8, var18, var19);
				var31.addVertexWithUV(var24, var26, -var8, var18, var20);
				var31.addVertexWithUV(var24, var26, var8, var17, var20);
				var31.addVertexWithUV(var24, var25, var8, var17, var19);
				var31.draw();
			}
		}
	}
	
	public void renderThePainting(EntityPainting par1EntityPainting, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		GL11.glRotatef(par8, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		func_110777_b(par1EntityPainting);
		EnumArt var10 = par1EntityPainting.art;
		float var11 = 0.0625F;
		GL11.glScalef(var11, var11, var11);
		func_77010_a(par1EntityPainting, var10.sizeX, var10.sizeY, var10.offsetX, var10.offsetY);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
