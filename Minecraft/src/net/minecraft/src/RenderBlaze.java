package net.minecraft.src;

public class RenderBlaze extends RenderLiving
{
	private int field_77068_a;
	
	public RenderBlaze()
	{
		super(new ModelBlaze(), 0.5F);
		field_77068_a = ((ModelBlaze) mainModel).func_78104_a();
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderBlaze((EntityBlaze) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderBlaze((EntityBlaze) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	public void renderBlaze(EntityBlaze par1EntityBlaze, double par2, double par4, double par6, float par8, float par9)
	{
		int var10 = ((ModelBlaze) mainModel).func_78104_a();
		if(var10 != field_77068_a)
		{
			field_77068_a = var10;
			mainModel = new ModelBlaze();
		}
		super.doRenderLiving(par1EntityBlaze, par2, par4, par6, par8, par9);
	}
}
