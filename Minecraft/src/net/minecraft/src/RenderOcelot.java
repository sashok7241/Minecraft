package net.minecraft.src;


public class RenderOcelot extends RenderLiving
{
	private static final ResourceLocation field_110877_a = new ResourceLocation("textures/entity/cat/black.png");
	private static final ResourceLocation field_110875_f = new ResourceLocation("textures/entity/cat/ocelot.png");
	private static final ResourceLocation field_110876_g = new ResourceLocation("textures/entity/cat/red.png");
	private static final ResourceLocation field_110878_h = new ResourceLocation("textures/entity/cat/siamese.png");
	
	public RenderOcelot(ModelBase par1ModelBase, float par2)
	{
		super(par1ModelBase, par2);
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderLivingOcelot((EntityOcelot) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderLivingOcelot((EntityOcelot) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110874_a((EntityOcelot) par1Entity);
	}
	
	protected ResourceLocation func_110874_a(EntityOcelot par1EntityOcelot)
	{
		switch(par1EntityOcelot.getTameSkin())
		{
			case 0:
			default:
				return field_110875_f;
			case 1:
				return field_110877_a;
			case 2:
				return field_110876_g;
			case 3:
				return field_110878_h;
		}
	}
	
	@Override protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		preRenderOcelot((EntityOcelot) par1EntityLivingBase, par2);
	}
	
	protected void preRenderOcelot(EntityOcelot par1EntityOcelot, float par2)
	{
		super.preRenderCallback(par1EntityOcelot, par2);
		if(par1EntityOcelot.isTamed())
		{
			GL11.glScalef(0.8F, 0.8F, 0.8F);
		}
	}
	
	public void renderLivingOcelot(EntityOcelot par1EntityOcelot, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving(par1EntityOcelot, par2, par4, par6, par8, par9);
	}
	
	@Override public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
	{
		renderLivingOcelot((EntityOcelot) par1EntityLivingBase, par2, par4, par6, par8, par9);
	}
}
