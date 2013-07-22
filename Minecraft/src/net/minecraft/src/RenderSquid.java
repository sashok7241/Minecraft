package net.minecraft.src;


public class RenderSquid extends RenderLiving
{
	public RenderSquid(ModelBase par1ModelBase, float par2)
	{
		super(par1ModelBase, par2);
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderLivingSquid((EntitySquid) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderLivingSquid((EntitySquid) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	@Override protected float handleRotationFloat(EntityLiving par1EntityLiving, float par2)
	{
		return this.handleRotationFloat((EntitySquid) par1EntityLiving, par2);
	}
	
	protected float handleRotationFloat(EntitySquid par1EntitySquid, float par2)
	{
		return par1EntitySquid.prevTentacleAngle + (par1EntitySquid.tentacleAngle - par1EntitySquid.prevTentacleAngle) * par2;
	}
	
	public void renderLivingSquid(EntitySquid par1EntitySquid, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving(par1EntitySquid, par2, par4, par6, par8, par9);
	}
	
	@Override protected void rotateCorpse(EntityLiving par1EntityLiving, float par2, float par3, float par4)
	{
		rotateSquidsCorpse((EntitySquid) par1EntityLiving, par2, par3, par4);
	}
	
	protected void rotateSquidsCorpse(EntitySquid par1EntitySquid, float par2, float par3, float par4)
	{
		float var5 = par1EntitySquid.prevSquidPitch + (par1EntitySquid.squidPitch - par1EntitySquid.prevSquidPitch) * par4;
		float var6 = par1EntitySquid.prevSquidYaw + (par1EntitySquid.squidYaw - par1EntitySquid.prevSquidYaw) * par4;
		GL11.glTranslatef(0.0F, 0.5F, 0.0F);
		GL11.glRotatef(180.0F - par3, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(var5, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(var6, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.0F, -1.2F, 0.0F);
	}
}
