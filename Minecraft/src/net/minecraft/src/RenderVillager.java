package net.minecraft.src;


public class RenderVillager extends RenderLiving
{
	protected ModelVillager villagerModel;
	
	public RenderVillager()
	{
		super(new ModelVillager(0.0F), 0.5F);
		villagerModel = (ModelVillager) mainModel;
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderVillager((EntityVillager) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderVillager((EntityVillager) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	@Override protected void preRenderCallback(EntityLiving par1EntityLivingBase, float par2)
	{
		preRenderVillager((EntityVillager) par1EntityLivingBase, par2);
	}
	
	protected void preRenderVillager(EntityVillager par1EntityVillager, float par2)
	{
		float var3 = 0.9375F;
		if(par1EntityVillager.getGrowingAge() < 0)
		{
			var3 = (float) (var3 * 0.5D);
			shadowSize = 0.25F;
		} else
		{
			shadowSize = 0.5F;
		}
		GL11.glScalef(var3, var3, var3);
	}
	
	@Override protected void renderEquippedItems(EntityLiving par1EntityLivingBase, float par2)
	{
		renderVillagerEquipedItems((EntityVillager) par1EntityLivingBase, par2);
	}
	
	public void renderVillager(EntityVillager par1EntityVillager, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving(par1EntityVillager, par2, par4, par6, par8, par9);
	}
	
	protected void renderVillagerEquipedItems(EntityVillager par1EntityVillager, float par2)
	{
		super.renderEquippedItems(par1EntityVillager, par2);
	}
	
	@Override protected int shouldRenderPass(EntityLiving par1EntityLivingBase, int par2, float par3)
	{
		return shouldVillagerRenderPass((EntityVillager) par1EntityLivingBase, par2, par3);
	}
	
	protected int shouldVillagerRenderPass(EntityVillager par1EntityVillager, int par2, float par3)
	{
		return -1;
	}
}
