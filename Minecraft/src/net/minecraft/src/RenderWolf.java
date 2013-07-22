package net.minecraft.src;


public class RenderWolf extends RenderLiving
{
	public RenderWolf(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase, par3);
		setRenderPassModel(par2ModelBase);
	}
	
	protected int func_82447_a(EntityWolf par1EntityWolf, int par2, float par3)
	{
		float var4;
		if(par2 == 0 && par1EntityWolf.getWolfShaking())
		{
			var4 = par1EntityWolf.getBrightness(par3) * par1EntityWolf.getShadingWhileShaking(par3);
			loadTexture(par1EntityWolf.getTexture());
			GL11.glColor3f(var4, var4, var4);
			return 1;
		} else if(par2 == 1 && par1EntityWolf.isTamed())
		{
			loadTexture("/mob/wolf_collar.png");
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
	
	@Override protected float handleRotationFloat(EntityLiving par1EntityLiving, float par2)
	{
		return getTailRotation((EntityWolf) par1EntityLiving, par2);
	}
	
	@Override protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
	{
		return func_82447_a((EntityWolf) par1EntityLiving, par2, par3);
	}
}
