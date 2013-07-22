package net.minecraft.src;


public class RenderSheep extends RenderLiving
{
	public RenderSheep(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase, par3);
		setRenderPassModel(par2ModelBase);
	}
	
	protected int setWoolColorAndRender(EntitySheep par1EntitySheep, int par2, float par3)
	{
		if(par2 == 0 && !par1EntitySheep.getSheared())
		{
			loadTexture("/mob/sheep_fur.png");
			float var4 = 1.0F;
			int var5 = par1EntitySheep.getFleeceColor();
			GL11.glColor3f(var4 * EntitySheep.fleeceColorTable[var5][0], var4 * EntitySheep.fleeceColorTable[var5][1], var4 * EntitySheep.fleeceColorTable[var5][2]);
			return 1;
		} else return -1;
	}
	
	@Override protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
	{
		return setWoolColorAndRender((EntitySheep) par1EntityLiving, par2, par3);
	}
}
