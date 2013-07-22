package net.minecraft.src;


public class RenderFireball extends Render
{
	private float field_77002_a;
	
	public RenderFireball(float p_i3204_1_)
	{
		field_77002_a = p_i3204_1_;
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		doRenderFireball((EntityFireball) par1Entity, par2, par4, par6, par8, par9);
	}
	
	public void doRenderFireball(EntityFireball par1EntityFireball, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		float var10 = field_77002_a;
		GL11.glScalef(var10 / 1.0F, var10 / 1.0F, var10 / 1.0F);
		Icon var11 = Item.fireballCharge.getIconFromDamage(0);
		loadTexture("/gui/items.png");
		Tessellator var12 = Tessellator.instance;
		float var13 = var11.getMinU();
		float var14 = var11.getMaxU();
		float var15 = var11.getMinV();
		float var16 = var11.getMaxV();
		float var17 = 1.0F;
		float var18 = 0.5F;
		float var19 = 0.25F;
		GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		var12.startDrawingQuads();
		var12.setNormal(0.0F, 1.0F, 0.0F);
		var12.addVertexWithUV(0.0F - var18, 0.0F - var19, 0.0D, var13, var16);
		var12.addVertexWithUV(var17 - var18, 0.0F - var19, 0.0D, var14, var16);
		var12.addVertexWithUV(var17 - var18, 1.0F - var19, 0.0D, var14, var15);
		var12.addVertexWithUV(0.0F - var18, 1.0F - var19, 0.0D, var13, var15);
		var12.draw();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
