package net.minecraft.src;


public class RenderVillager extends RenderLiving
{
	private static final ResourceLocation field_110903_f = new ResourceLocation("textures/entity/villager/villager.png");
	private static final ResourceLocation field_110904_g = new ResourceLocation("textures/entity/villager/farmer.png");
	private static final ResourceLocation field_110908_h = new ResourceLocation("textures/entity/villager/librarian.png");
	private static final ResourceLocation field_110907_k = new ResourceLocation("textures/entity/villager/priest.png");
	private static final ResourceLocation field_110905_l = new ResourceLocation("textures/entity/villager/smith.png");
	private static final ResourceLocation field_110906_m = new ResourceLocation("textures/entity/villager/butcher.png");
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
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110902_a((EntityVillager) par1Entity);
	}
	
	protected ResourceLocation func_110902_a(EntityVillager par1EntityVillager)
	{
		switch(par1EntityVillager.getProfession())
		{
			case 0:
				return field_110904_g;
			case 1:
				return field_110908_h;
			case 2:
				return field_110907_k;
			case 3:
				return field_110905_l;
			case 4:
				return field_110906_m;
			default:
				return field_110903_f;
		}
	}
	
	@Override protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
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
	
	@Override protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
	{
		renderVillagerEquipedItems((EntityVillager) par1EntityLivingBase, par2);
	}
	
	@Override public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
	{
		renderVillager((EntityVillager) par1EntityLivingBase, par2, par4, par6, par8, par9);
	}
	
	public void renderVillager(EntityVillager par1EntityVillager, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving(par1EntityVillager, par2, par4, par6, par8, par9);
	}
	
	protected void renderVillagerEquipedItems(EntityVillager par1EntityVillager, float par2)
	{
		super.renderEquippedItems(par1EntityVillager, par2);
	}
	
	@Override protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return shouldVillagerRenderPass((EntityVillager) par1EntityLivingBase, par2, par3);
	}
	
	protected int shouldVillagerRenderPass(EntityVillager par1EntityVillager, int par2, float par3)
	{
		return -1;
	}
}
