package net.minecraft.src;

public class RenderSilverfish extends RenderLiving
{
	public RenderSilverfish()
	{
		super(new ModelSilverfish(), 0.3F);
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderSilverfish((EntitySilverfish) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderSilverfish((EntitySilverfish) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	@Override protected float getDeathMaxRotation(EntityLiving par1EntityLiving)
	{
		return getSilverfishDeathRotation((EntitySilverfish) par1EntityLiving);
	}
	
	protected float getSilverfishDeathRotation(EntitySilverfish par1EntitySilverfish)
	{
		return 180.0F;
	}
	
	public void renderSilverfish(EntitySilverfish par1EntitySilverfish, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving(par1EntitySilverfish, par2, par4, par6, par8, par9);
	}
	
	@Override protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
	{
		return shouldSilverfishRenderPass((EntitySilverfish) par1EntityLiving, par2, par3);
	}
	
	protected int shouldSilverfishRenderPass(EntitySilverfish par1EntitySilverfish, int par2, float par3)
	{
		return -1;
	}
}
