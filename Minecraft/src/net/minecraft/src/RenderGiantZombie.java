package net.minecraft.src;


public class RenderGiantZombie extends RenderLiving
{
	private float scale;
	
	public RenderGiantZombie(ModelBase p_i3205_1_, float p_i3205_2_, float p_i3205_3_)
	{
		super(p_i3205_1_, p_i3205_2_ * p_i3205_3_);
		scale = p_i3205_3_;
	}
	
	@Override protected void preRenderCallback(EntityLiving par1EntityLivingBase, float par2)
	{
		preRenderScale((EntityGiantZombie) par1EntityLivingBase, par2);
	}
	
	protected void preRenderScale(EntityGiantZombie par1EntityGiantZombie, float par2)
	{
		GL11.glScalef(scale, scale, scale);
	}
}
