package net.minecraft.src;


public class RenderCaveSpider extends RenderSpider
{
	private static final ResourceLocation field_110893_a = new ResourceLocation("textures/entity/spider/cave_spider.png");
	
	public RenderCaveSpider()
	{
		shadowSize *= 0.7F;
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110892_a((EntityCaveSpider) par1Entity);
	}
	
	@Override protected ResourceLocation func_110889_a(EntitySpider par1EntitySpider)
	{
		return func_110892_a((EntityCaveSpider) par1EntitySpider);
	}
	
	protected ResourceLocation func_110892_a(EntityCaveSpider par1EntityCaveSpider)
	{
		return field_110893_a;
	}
	
	@Override protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		scaleSpider((EntityCaveSpider) par1EntityLivingBase, par2);
	}
	
	protected void scaleSpider(EntityCaveSpider par1EntityCaveSpider, float par2)
	{
		GL11.glScalef(0.7F, 0.7F, 0.7F);
	}
}
