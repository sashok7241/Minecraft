package net.minecraft.src;


public class RenderGiantZombie extends RenderLiving
{
	private static final ResourceLocation field_110871_a = new ResourceLocation("textures/entity/zombie/zombie.png");
	private float scale;
	
	public RenderGiantZombie(ModelBase par1ModelBase, float par2, float par3)
	{
		super(par1ModelBase, par2 * par3);
		scale = par3;
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110870_a((EntityGiantZombie) par1Entity);
	}
	
	protected ResourceLocation func_110870_a(EntityGiantZombie par1EntityGiantZombie)
	{
		return field_110871_a;
	}
	
	@Override protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		preRenderScale((EntityGiantZombie) par1EntityLivingBase, par2);
	}
	
	protected void preRenderScale(EntityGiantZombie par1EntityGiantZombie, float par2)
	{
		GL11.glScalef(scale, scale, scale);
	}
}
