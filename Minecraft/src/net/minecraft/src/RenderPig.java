package net.minecraft.src;

public class RenderPig extends RenderLiving
{
	private static final ResourceLocation field_110888_a = new ResourceLocation("textures/entity/pig/pig_saddle.png");
	private static final ResourceLocation field_110887_f = new ResourceLocation("textures/entity/pig/pig.png");
	
	public RenderPig(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase, par3);
		setRenderPassModel(par2ModelBase);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110886_a((EntityPig) par1Entity);
	}
	
	protected ResourceLocation func_110886_a(EntityPig par1EntityPig)
	{
		return field_110887_f;
	}
	
	protected int renderSaddledPig(EntityPig par1EntityPig, int par2, float par3)
	{
		if(par2 == 0 && par1EntityPig.getSaddled())
		{
			func_110776_a(field_110888_a);
			return 1;
		} else return -1;
	}
	
	@Override protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return renderSaddledPig((EntityPig) par1EntityLivingBase, par2, par3);
	}
}
