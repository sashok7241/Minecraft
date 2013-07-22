package net.minecraft.src;


public class RenderWolf extends RenderLiving
{
	private static final ResourceLocation field_110917_a = new ResourceLocation("textures/entity/wolf/wolf.png");
	private static final ResourceLocation field_110915_f = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
	private static final ResourceLocation field_110916_g = new ResourceLocation("textures/entity/wolf/wolf_angry.png");
	private static final ResourceLocation field_110918_h = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
	
	public RenderWolf(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase, par3);
		setRenderPassModel(par2ModelBase);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110914_a((EntityWolf) par1Entity);
	}
	
	protected ResourceLocation func_110914_a(EntityWolf par1EntityWolf)
	{
		return par1EntityWolf.isTamed() ? field_110915_f : par1EntityWolf.isAngry() ? field_110916_g : field_110917_a;
	}
	
	protected int func_82447_a(EntityWolf par1EntityWolf, int par2, float par3)
	{
		float var4;
		if(par2 == 0 && par1EntityWolf.getWolfShaking())
		{
			var4 = par1EntityWolf.getBrightness(par3) * par1EntityWolf.getShadingWhileShaking(par3);
			func_110776_a(field_110917_a);
			GL11.glColor3f(var4, var4, var4);
			return 1;
		} else if(par2 == 1 && par1EntityWolf.isTamed())
		{
			func_110776_a(field_110918_h);
			var4 = 1.0F;
			int var5 = par1EntityWolf.getCollarColor();
			GL11.glColor3f(var4 * EntitySheep.fleeceColorTable[var5][0], var4 * EntitySheep.fleeceColorTable[var5][1], var4 * EntitySheep.fleeceColorTable[var5][2]);
			return 1;
		} else return -1;
	}
	
	protected float getTailRotation(EntityWolf par1EntityWolf, float par2)
	{
		return par1EntityWolf.getTailRotation();
	}
	
	@Override protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2)
	{
		return getTailRotation((EntityWolf) par1EntityLivingBase, par2);
	}
	
	@Override protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return func_82447_a((EntityWolf) par1EntityLivingBase, par2, par3);
	}
}
