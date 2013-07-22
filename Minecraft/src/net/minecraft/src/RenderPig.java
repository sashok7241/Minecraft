package net.minecraft.src;

public class RenderPig extends RenderLiving
{
	public RenderPig(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase, par3);
		setRenderPassModel(par2ModelBase);
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderLivingPig((EntityPig) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderLivingPig((EntityPig) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	public void renderLivingPig(EntityPig par1EntityPig, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving(par1EntityPig, par2, par4, par6, par8, par9);
	}
	
	protected int renderSaddledPig(EntityPig par1EntityPig, int par2, float par3)
	{
		if(par2 == 0 && par1EntityPig.getSaddled())
		{
			loadTexture("/mob/saddle.png");
			return 1;
		} else return -1;
	}
	
	@Override protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
	{
		return renderSaddledPig((EntityPig) par1EntityLiving, par2, par3);
	}
}
