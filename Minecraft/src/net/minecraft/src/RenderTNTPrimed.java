package net.minecraft.src;


public class RenderTNTPrimed extends Render
{
	private RenderBlocks blockRenderer = new RenderBlocks();
	
	public RenderTNTPrimed()
	{
		shadowSize = 0.5F;
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderPrimedTNT((EntityTNTPrimed) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110808_a((EntityTNTPrimed) par1Entity);
	}
	
	protected ResourceLocation func_110808_a(EntityTNTPrimed par1EntityTNTPrimed)
	{
		return TextureMap.field_110575_b;
	}
	
	public void renderPrimedTNT(EntityTNTPrimed par1EntityTNTPrimed, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		float var10;
		if(par1EntityTNTPrimed.fuse - par9 + 1.0F < 10.0F)
		{
			var10 = 1.0F - (par1EntityTNTPrimed.fuse - par9 + 1.0F) / 10.0F;
			if(var10 < 0.0F)
			{
				var10 = 0.0F;
			}
			if(var10 > 1.0F)
			{
				var10 = 1.0F;
			}
			var10 *= var10;
			var10 *= var10;
			float var11 = 1.0F + var10 * 0.3F;
			GL11.glScalef(var11, var11, var11);
		}
		var10 = (1.0F - (par1EntityTNTPrimed.fuse - par9 + 1.0F) / 100.0F) * 0.8F;
		func_110777_b(par1EntityTNTPrimed);
		blockRenderer.renderBlockAsItem(Block.tnt, 0, par1EntityTNTPrimed.getBrightness(par9));
		if(par1EntityTNTPrimed.fuse / 5 % 2 == 0)
		{
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, var10);
			blockRenderer.renderBlockAsItem(Block.tnt, 0, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
		GL11.glPopMatrix();
	}
}
