package net.minecraft.src;


public class RenderSpider extends RenderLiving
{
	private static final ResourceLocation field_110891_a = new ResourceLocation("textures/entity/spider_eyes.png");
	private static final ResourceLocation field_110890_f = new ResourceLocation("textures/entity/spider/spider.png");
	
	public RenderSpider()
	{
		super(new ModelSpider(), 1.0F);
		setRenderPassModel(new ModelSpider());
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110889_a((EntitySpider) par1Entity);
	}
	
	protected ResourceLocation func_110889_a(EntitySpider par1EntitySpider)
	{
		return field_110890_f;
	}
	
	@Override protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase)
	{
		return setSpiderDeathMaxRotation((EntitySpider) par1EntityLivingBase);
	}
	
	protected float setSpiderDeathMaxRotation(EntitySpider par1EntitySpider)
	{
		return 180.0F;
	}
	
	protected int setSpiderEyeBrightness(EntitySpider par1EntitySpider, int par2, float par3)
	{
		if(par2 != 0) return -1;
		else
		{
			func_110776_a(field_110891_a);
			float var4 = 1.0F;
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			if(par1EntitySpider.isInvisible())
			{
				GL11.glDepthMask(false);
			} else
			{
				GL11.glDepthMask(true);
			}
			char var5 = 61680;
			int var6 = var5 % 65536;
			int var7 = var5 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
			return 1;
		}
	}
	
	@Override protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return setSpiderEyeBrightness((EntitySpider) par1EntityLivingBase, par2, par3);
	}
}
