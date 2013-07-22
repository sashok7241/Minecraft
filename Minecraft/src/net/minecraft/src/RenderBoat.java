package net.minecraft.src;


public class RenderBoat extends Render
{
	private static final ResourceLocation field_110782_f = new ResourceLocation("textures/entity/boat.png");
	protected ModelBase modelBoat;
	
	public RenderBoat()
	{
		shadowSize = 0.5F;
		modelBoat = new ModelBoat();
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderBoat((EntityBoat) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110781_a((EntityBoat) par1Entity);
	}
	
	protected ResourceLocation func_110781_a(EntityBoat par1EntityBoat)
	{
		return field_110782_f;
	}
	
	public void renderBoat(EntityBoat par1EntityBoat, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		GL11.glRotatef(180.0F - par8, 0.0F, 1.0F, 0.0F);
		float var10 = par1EntityBoat.getTimeSinceHit() - par9;
		float var11 = par1EntityBoat.getDamageTaken() - par9;
		if(var11 < 0.0F)
		{
			var11 = 0.0F;
		}
		if(var10 > 0.0F)
		{
			GL11.glRotatef(MathHelper.sin(var10) * var10 * var11 / 10.0F * par1EntityBoat.getForwardDirection(), 1.0F, 0.0F, 0.0F);
		}
		float var12 = 0.75F;
		GL11.glScalef(var12, var12, var12);
		GL11.glScalef(1.0F / var12, 1.0F / var12, 1.0F / var12);
		func_110777_b(par1EntityBoat);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		modelBoat.render(par1EntityBoat, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
}
