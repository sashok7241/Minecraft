package net.minecraft.src;

public class RenderPig extends RenderLiving
{
	public RenderPig(ModelBase p_i3197_1_, ModelBase p_i3197_2_, float p_i3197_3_)
	{
		super(p_i3197_1_, p_i3197_3_);
		setRenderPassModel(p_i3197_2_);
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderLivingPig((EntityPig) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderLivingPig((EntityPig) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	public void renderLivingPig(EntityPig p_77098_1_, double p_77098_2_, double p_77098_4_, double p_77098_6_, float p_77098_8_, float p_77098_9_)
	{
		super.doRenderLiving(p_77098_1_, p_77098_2_, p_77098_4_, p_77098_6_, p_77098_8_, p_77098_9_);
	}
	
	protected int renderSaddledPig(EntityPig par1EntityPig, int par2, float par3)
	{
		if(par2 == 0 && par1EntityPig.getSaddled())
		{
			loadTexture("/mob/saddle.png");
			return 1;
		} else return -1;
	}
	
	@Override protected int shouldRenderPass(EntityLiving par1EntityLivingBase, int par2, float par3)
	{
		return renderSaddledPig((EntityPig) par1EntityLivingBase, par2, par3);
	}
}
