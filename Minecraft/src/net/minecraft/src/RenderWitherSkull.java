package net.minecraft.src;


public class RenderWitherSkull extends Render
{
	ModelSkeletonHead skeletonHeadModel = new ModelSkeletonHead();
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		func_82399_a((EntityWitherSkull) par1Entity, par2, par4, par6, par8, par9);
	}
	
	public void func_82399_a(EntityWitherSkull par1EntityWitherSkull, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		float var10 = func_82400_a(par1EntityWitherSkull.prevRotationYaw, par1EntityWitherSkull.rotationYaw, par9);
		float var11 = par1EntityWitherSkull.prevRotationPitch + (par1EntityWitherSkull.rotationPitch - par1EntityWitherSkull.prevRotationPitch) * par9;
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		float var12 = 0.0625F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		if(par1EntityWitherSkull.isInvulnerable())
		{
			loadTexture("/mob/wither_invul.png");
		} else
		{
			loadTexture("/mob/wither.png");
		}
		skeletonHeadModel.render(par1EntityWitherSkull, 0.0F, 0.0F, 0.0F, var10, var11, var12);
		GL11.glPopMatrix();
	}
	
	private float func_82400_a(float par1, float par2, float par3)
	{
		float var4;
		for(var4 = par2 - par1; var4 < -180.0F; var4 += 360.0F)
		{
			;
		}
		while(var4 >= 180.0F)
		{
			var4 -= 360.0F;
		}
		return par1 + par3 * var4;
	}
}
