package net.minecraft.src;


public class RenderLeashKnot extends Render
{
	private static final ResourceLocation field_110802_a = new ResourceLocation("textures/entity/lead_knot.png");
	private ModelLeashKnot field_110801_f = new ModelLeashKnot();
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		func_110799_a((EntityLeashKnot) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110800_a((EntityLeashKnot) par1Entity);
	}
	
	public void func_110799_a(EntityLeashKnot par1EntityLeashKnot, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		float var10 = 0.0625F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		func_110777_b(par1EntityLeashKnot);
		field_110801_f.render(par1EntityLeashKnot, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, var10);
		GL11.glPopMatrix();
	}
	
	protected ResourceLocation func_110800_a(EntityLeashKnot par1EntityLeashKnot)
	{
		return field_110802_a;
	}
}
