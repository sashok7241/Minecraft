package net.minecraft.src;


public class RenderXPOrb extends Render
{
	private static final ResourceLocation field_110785_a = new ResourceLocation("textures/entity/experience_orb.png");
	
	public RenderXPOrb()
	{
		shadowSize = 0.15F;
		shadowOpaque = 0.75F;
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderTheXPOrb((EntityXPOrb) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110784_a((EntityXPOrb) par1Entity);
	}
	
	protected ResourceLocation func_110784_a(EntityXPOrb par1EntityXPOrb)
	{
		return field_110785_a;
	}
	
	public void renderTheXPOrb(EntityXPOrb par1EntityXPOrb, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		func_110777_b(par1EntityXPOrb);
		int var10 = par1EntityXPOrb.getTextureByXP();
		float var11 = (var10 % 4 * 16 + 0) / 64.0F;
		float var12 = (var10 % 4 * 16 + 16) / 64.0F;
		float var13 = (var10 / 4 * 16 + 0) / 64.0F;
		float var14 = (var10 / 4 * 16 + 16) / 64.0F;
		float var15 = 1.0F;
		float var16 = 0.5F;
		float var17 = 0.25F;
		int var18 = par1EntityXPOrb.getBrightnessForRender(par9);
		int var19 = var18 % 65536;
		int var20 = var18 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var19 / 1.0F, var20 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var27 = 255.0F;
		float var26 = (par1EntityXPOrb.xpColor + par9) / 2.0F;
		var20 = (int) ((MathHelper.sin(var26 + 0.0F) + 1.0F) * 0.5F * var27);
		int var21 = (int) var27;
		int var22 = (int) ((MathHelper.sin(var26 + 4.1887903F) + 1.0F) * 0.1F * var27);
		int var23 = var20 << 16 | var21 << 8 | var22;
		GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		float var24 = 0.3F;
		GL11.glScalef(var24, var24, var24);
		Tessellator var25 = Tessellator.instance;
		var25.startDrawingQuads();
		var25.setColorRGBA_I(var23, 128);
		var25.setNormal(0.0F, 1.0F, 0.0F);
		var25.addVertexWithUV(0.0F - var16, 0.0F - var17, 0.0D, var11, var14);
		var25.addVertexWithUV(var15 - var16, 0.0F - var17, 0.0D, var12, var14);
		var25.addVertexWithUV(var15 - var16, 1.0F - var17, 0.0D, var12, var13);
		var25.addVertexWithUV(0.0F - var16, 1.0F - var17, 0.0D, var11, var13);
		var25.draw();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
