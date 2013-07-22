package net.minecraft.src;


public class RenderGiantZombie extends RenderLiving
{
	private float scale;
	
	public RenderGiantZombie(ModelBase par1ModelBase, float par2, float par3)
	{
		super(par1ModelBase, par2 * par3);
		scale = par3;
	}
	
	@Override protected void preRenderCallback(EntityLiving par1EntityLiving, float par2)
	{
		preRenderScale((EntityGiantZombie) par1EntityLiving, par2);
	}
	
	protected void preRenderScale(EntityGiantZombie par1EntityGiantZombie, float par2)
	{
		GL11.glScalef(scale, scale, scale);
	}
}
