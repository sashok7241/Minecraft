package net.minecraft.src;


public class RenderWitch extends RenderLiving
{
	private static final ResourceLocation field_110910_a = new ResourceLocation("textures/entity/witch.png");
	private final ModelWitch field_82414_a;
	
	public RenderWitch()
	{
		super(new ModelWitch(0.0F), 0.5F);
		field_82414_a = (ModelWitch) mainModel;
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		func_82412_a((EntityWitch) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		func_82412_a((EntityWitch) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110909_a((EntityWitch) par1Entity);
	}
	
	protected ResourceLocation func_110909_a(EntityWitch par1EntityWitch)
	{
		return field_110910_a;
	}
	
	protected void func_82409_b(EntityWitch par1EntityWitch, float par2)
	{
		float var3 = 0.9375F;
		GL11.glScalef(var3, var3, var3);
	}
	
	protected void func_82410_b()
	{
		GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
	}
	
	protected void func_82411_a(EntityWitch par1EntityWitch, float par2)
	{
		float var3 = 1.0F;
		GL11.glColor3f(var3, var3, var3);
		super.renderEquippedItems(par1EntityWitch, par2);
		ItemStack var4 = par1EntityWitch.getHeldItem();
		if(var4 != null)
		{
			GL11.glPushMatrix();
			float var5;
			if(mainModel.isChild)
			{
				var5 = 0.5F;
				GL11.glTranslatef(0.0F, 0.625F, 0.0F);
				GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
				GL11.glScalef(var5, var5, var5);
			}
			field_82414_a.villagerNose.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.53125F, 0.21875F);
			if(var4.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var4.itemID].getRenderType()))
			{
				var5 = 0.5F;
				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
				var5 *= 0.75F;
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(var5, -var5, var5);
			} else if(var4.itemID == Item.bow.itemID)
			{
				var5 = 0.625F;
				GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
				GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(var5, -var5, var5);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else if(Item.itemsList[var4.itemID].isFull3D())
			{
				var5 = 0.625F;
				if(Item.itemsList[var4.itemID].shouldRotateAroundWhenRendering())
				{
					GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(0.0F, -0.125F, 0.0F);
				}
				func_82410_b();
				GL11.glScalef(var5, -var5, var5);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else
			{
				var5 = 0.375F;
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glScalef(var5, var5, var5);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}
			GL11.glRotatef(-15.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(40.0F, 0.0F, 0.0F, 1.0F);
			renderManager.itemRenderer.renderItem(par1EntityWitch, var4, 0);
			if(var4.getItem().requiresMultipleRenderPasses())
			{
				renderManager.itemRenderer.renderItem(par1EntityWitch, var4, 1);
			}
			GL11.glPopMatrix();
		}
	}
	
	public void func_82412_a(EntityWitch par1EntityWitch, double par2, double par4, double par6, float par8, float par9)
	{
		ItemStack var10 = par1EntityWitch.getHeldItem();
		field_82414_a.field_82900_g = var10 != null;
		super.doRenderLiving(par1EntityWitch, par2, par4, par6, par8, par9);
	}
	
	@Override protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		func_82409_b((EntityWitch) par1EntityLivingBase, par2);
	}
	
	@Override protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
	{
		func_82411_a((EntityWitch) par1EntityLivingBase, par2);
	}
	
	@Override public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
	{
		func_82412_a((EntityWitch) par1EntityLivingBase, par2, par4, par6, par8, par9);
	}
}
