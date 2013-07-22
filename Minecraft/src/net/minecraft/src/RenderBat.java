package net.minecraft.src;


public class RenderBat extends RenderLiving
{
	private static final ResourceLocation field_110835_a = new ResourceLocation("textures/entity/bat.png");
	private int renderedBatSize;
	
	public RenderBat()
	{
		super(new ModelBat(), 0.25F);
		renderedBatSize = ((ModelBat) mainModel).getBatSize();
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		func_82443_a((EntityBat) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		func_82443_a((EntityBat) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110834_a((EntityBat) par1Entity);
	}
	
	protected ResourceLocation func_110834_a(EntityBat par1EntityBat)
	{
		return field_110835_a;
	}
	
	@Override public void func_130000_a(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
	{
		func_82443_a((EntityBat) par1EntityLivingBase, par2, par4, par6, par8, par9);
	}
	
	protected void func_82442_a(EntityBat par1EntityBat, float par2)
	{
		GL11.glScalef(0.35F, 0.35F, 0.35F);
	}
	
	public void func_82443_a(EntityBat par1EntityBat, double par2, double par4, double par6, float par8, float par9)
	{
		int var10 = ((ModelBat) mainModel).getBatSize();
		if(var10 != renderedBatSize)
		{
			renderedBatSize = var10;
			mainModel = new ModelBat();
		}
		super.doRenderLiving(par1EntityBat, par2, par4, par6, par8, par9);
	}
	
	protected void func_82444_a(EntityBat par1EntityBat, float par2, float par3, float par4)
	{
		if(!par1EntityBat.getIsBatHanging())
		{
			GL11.glTranslatef(0.0F, MathHelper.cos(par2 * 0.3F) * 0.1F, 0.0F);
		} else
		{
			GL11.glTranslatef(0.0F, -0.1F, 0.0F);
		}
		super.rotateCorpse(par1EntityBat, par2, par3, par4);
	}
	
	protected void func_82445_a(EntityBat par1EntityBat, double par2, double par4, double par6)
	{
		super.renderLivingAt(par1EntityBat, par2, par4, par6);
	}
	
	@Override protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		func_82442_a((EntityBat) par1EntityLivingBase, par2);
	}
	
	@Override protected void renderLivingAt(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6)
	{
		func_82445_a((EntityBat) par1EntityLivingBase, par2, par4, par6);
	}
	
	@Override protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
	{
		func_82444_a((EntityBat) par1EntityLivingBase, par2, par3, par4);
	}
}
