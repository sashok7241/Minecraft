package net.minecraft.src;

public class RenderCow extends RenderLiving
{
	public RenderCow(ModelBase p_i3210_1_, float p_i3210_2_)
	{
		super(p_i3210_1_, p_i3210_2_);
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderCow((EntityCow) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderCow((EntityCow) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	public void renderCow(EntityCow p_77059_1_, double p_77059_2_, double p_77059_4_, double p_77059_6_, float p_77059_8_, float p_77059_9_)
	{
		super.doRenderLiving(p_77059_1_, p_77059_2_, p_77059_4_, p_77059_6_, p_77059_8_, p_77059_9_);
	}
}
