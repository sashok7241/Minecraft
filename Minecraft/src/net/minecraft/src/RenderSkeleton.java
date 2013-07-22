package net.minecraft.src;


public class RenderSkeleton extends RenderBiped
{
	private static final ResourceLocation field_110862_k = new ResourceLocation("textures/entity/skeleton/skeleton.png");
	private static final ResourceLocation field_110861_l = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
	
	public RenderSkeleton()
	{
		super(new ModelSkeleton(), 0.5F);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110860_a((EntitySkeleton) par1Entity);
	}
	
	@Override protected ResourceLocation func_110856_a(EntityLiving par1EntityLiving)
	{
		return func_110860_a((EntitySkeleton) par1EntityLiving);
	}
	
	protected ResourceLocation func_110860_a(EntitySkeleton par1EntitySkeleton)
	{
		return par1EntitySkeleton.getSkeletonType() == 1 ? field_110861_l : field_110862_k;
	}
	
	@Override protected void func_82422_c()
	{
		GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
	}
	
	protected void func_82438_a(EntitySkeleton par1EntitySkeleton, float par2)
	{
		if(par1EntitySkeleton.getSkeletonType() == 1)
		{
			GL11.glScalef(1.2F, 1.2F, 1.2F);
		}
	}
	
	@Override protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		func_82438_a((EntitySkeleton) par1EntityLivingBase, par2);
	}
}
