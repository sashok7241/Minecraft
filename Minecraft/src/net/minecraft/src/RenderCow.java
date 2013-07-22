package net.minecraft.src;

public class RenderCow extends RenderLiving
{
	public RenderCow(ModelBase par1ModelBase, float par2)
	{
		super(par1ModelBase, par2);
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderCow((EntityCow) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderCow((EntityCow) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	public void renderCow(EntityCow par1EntityCow, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving(par1EntityCow, par2, par4, par6, par8, par9);
	}
}
