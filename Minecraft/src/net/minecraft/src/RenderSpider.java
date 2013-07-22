package net.minecraft.src;


public class RenderSpider extends RenderLiving
{
	public RenderSpider()
	{
		super(new ModelSpider(), 1.0F);
		setRenderPassModel(new ModelSpider());
	}
	
	@Override protected float getDeathMaxRotation(EntityLiving par1EntityLivingBase)
	{
		return setSpiderDeathMaxRotation((EntitySpider) par1EntityLivingBase);
	}
	
	@Override protected void preRenderCallback(EntityLiving par1EntityLivingBase, float par2)
	{
		scaleSpider((EntitySpider) par1EntityLivingBase, par2);
	}
	
	protected void scaleSpider(EntitySpider par1EntityCaveSpider, float par2)
	{
		float var3 = par1EntityCaveSpider.spiderScaleAmount();
		GL11.glScalef(var3, var3, var3);
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
			loadTexture("/mob/spider_eyes.png");
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
	
	@Override protected int shouldRenderPass(EntityLiving par1EntityLivingBase, int par2, float par3)
	{
		return setSpiderEyeBrightness((EntitySpider) par1EntityLivingBase, par2, par3);
	}
}
