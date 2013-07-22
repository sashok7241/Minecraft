package net.minecraft.src;


public class RenderEnderCrystal extends Render
{
	private static final ResourceLocation field_110787_a = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
	private ModelBase field_76995_b;
	
	public RenderEnderCrystal()
	{
		shadowSize = 0.5F;
		field_76995_b = new ModelEnderCrystal(0.0F, true);
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		doRenderEnderCrystal((EntityEnderCrystal) par1Entity, par2, par4, par6, par8, par9);
	}
	
	public void doRenderEnderCrystal(EntityEnderCrystal par1EntityEnderCrystal, double par2, double par4, double par6, float par8, float par9)
	{
		float var10 = par1EntityEnderCrystal.innerRotation + par9;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		func_110776_a(field_110787_a);
		float var11 = MathHelper.sin(var10 * 0.2F) / 2.0F + 0.5F;
		var11 += var11 * var11;
		field_76995_b.render(par1EntityEnderCrystal, 0.0F, var10 * 3.0F, var11 * 0.2F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110786_a((EntityEnderCrystal) par1Entity);
	}
	
	protected ResourceLocation func_110786_a(EntityEnderCrystal par1EntityEnderCrystal)
	{
		return field_110787_a;
	}
}
