package net.minecraft.src;

public class RenderChicken extends RenderLiving
{
	public RenderChicken(ModelBase p_i3200_1_, float p_i3200_2_)
	{
		super(p_i3200_1_, p_i3200_2_);
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderChicken((EntityChicken) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderChicken((EntityChicken) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	protected float getWingRotation(EntityChicken par1EntityChicken, float par2)
	{
		float var3 = par1EntityChicken.field_70888_h + (par1EntityChicken.field_70886_e - par1EntityChicken.field_70888_h) * par2;
		float var4 = par1EntityChicken.field_70884_g + (par1EntityChicken.destPos - par1EntityChicken.field_70884_g) * par2;
		return (MathHelper.sin(var3) + 1.0F) * var4;
	}
	
	@Override protected float handleRotationFloat(EntityLiving par1EntityLivingBase, float par2)
	{
		return getWingRotation((EntityChicken) par1EntityLivingBase, par2);
	}
	
	public void renderChicken(EntityChicken par1EntityChicken, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving(par1EntityChicken, par2, par4, par6, par8, par9);
	}
}
