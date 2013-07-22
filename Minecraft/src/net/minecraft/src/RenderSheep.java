package net.minecraft.src;


public class RenderSheep extends RenderLiving
{
	private static final ResourceLocation field_110885_a = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
	private static final ResourceLocation field_110884_f = new ResourceLocation("textures/entity/sheep/sheep.png");
	
	public RenderSheep(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase, par3);
		setRenderPassModel(par2ModelBase);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110883_a((EntitySheep) par1Entity);
	}
	
	protected ResourceLocation func_110883_a(EntitySheep par1EntitySheep)
	{
		return field_110884_f;
	}
	
	protected int setWoolColorAndRender(EntitySheep par1EntitySheep, int par2, float par3)
	{
		if(par2 == 0 && !par1EntitySheep.getSheared())
		{
			func_110776_a(field_110885_a);
			float var4 = 1.0F;
			int var5 = par1EntitySheep.getFleeceColor();
			GL11.glColor3f(var4 * EntitySheep.fleeceColorTable[var5][0], var4 * EntitySheep.fleeceColorTable[var5][1], var4 * EntitySheep.fleeceColorTable[var5][2]);
			return 1;
		} else return -1;
	}
	
	@Override protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return setWoolColorAndRender((EntitySheep) par1EntityLivingBase, par2, par3);
	}
}
