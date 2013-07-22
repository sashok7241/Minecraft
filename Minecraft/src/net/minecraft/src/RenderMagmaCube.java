package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class RenderMagmaCube extends RenderLiving
{
	private int field_77120_a;
	
	public RenderMagmaCube()
	{
		super(new ModelMagmaCube(), 0.25F);
		field_77120_a = ((ModelMagmaCube) mainModel).func_78107_a();
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderMagmaCube((EntityMagmaCube) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderMagmaCube((EntityMagmaCube) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	@Override protected void preRenderCallback(EntityLiving par1EntityLivingBase, float par2)
	{
		scaleMagmaCube((EntityMagmaCube) par1EntityLivingBase, par2);
	}
	
	public void renderMagmaCube(EntityMagmaCube p_77119_1_, double p_77119_2_, double p_77119_4_, double p_77119_6_, float p_77119_8_, float p_77119_9_)
	{
		int var10 = ((ModelMagmaCube) mainModel).func_78107_a();
		if(var10 != field_77120_a)
		{
			field_77120_a = var10;
			mainModel = new ModelMagmaCube();
			Minecraft.getMinecraft().getLogAgent().logInfo("Loaded new lava slime model");
		}
		super.doRenderLiving(p_77119_1_, p_77119_2_, p_77119_4_, p_77119_6_, p_77119_8_, p_77119_9_);
	}
	
	protected void scaleMagmaCube(EntityMagmaCube par1EntityMagmaCube, float par2)
	{
		int var3 = par1EntityMagmaCube.getSlimeSize();
		float var4 = (par1EntityMagmaCube.field_70812_c + (par1EntityMagmaCube.field_70811_b - par1EntityMagmaCube.field_70812_c) * par2) / (var3 * 0.5F + 1.0F);
		float var5 = 1.0F / (var4 + 1.0F);
		float var6 = var3;
		GL11.glScalef(var5 * var6, 1.0F / var5 * var6, var5 * var6);
	}
}
