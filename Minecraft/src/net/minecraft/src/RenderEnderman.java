package net.minecraft.src;

import java.util.Random;

public class RenderEnderman extends RenderLiving
{
	private static final ResourceLocation field_110840_a = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
	private static final ResourceLocation field_110839_f = new ResourceLocation("textures/entity/enderman/enderman.png");
	private ModelEnderman endermanModel;
	private Random rnd = new Random();
	
	public RenderEnderman()
	{
		super(new ModelEnderman(), 0.5F);
		endermanModel = (ModelEnderman) super.mainModel;
		setRenderPassModel(endermanModel);
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderEnderman((EntityEnderman) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderEnderman((EntityEnderman) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110838_a((EntityEnderman) par1Entity);
	}
	
	protected ResourceLocation func_110838_a(EntityEnderman par1EntityEnderman)
	{
		return field_110839_f;
	}
	
	protected void renderCarrying(EntityEnderman par1EntityEnderman, float par2)
	{
		super.renderEquippedItems(par1EntityEnderman, par2);
		if(par1EntityEnderman.getCarried() > 0)
		{
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glPushMatrix();
			float var3 = 0.5F;
			GL11.glTranslatef(0.0F, 0.6875F, -0.75F);
			var3 *= 1.0F;
			GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(-var3, -var3, var3);
			int var4 = par1EntityEnderman.getBrightnessForRender(par2);
			int var5 = var4 % 65536;
			int var6 = var4 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5 / 1.0F, var6 / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			func_110776_a(TextureMap.field_110575_b);
			renderBlocks.renderBlockAsItem(Block.blocksList[par1EntityEnderman.getCarried()], par1EntityEnderman.getCarryingData(), 1.0F);
			GL11.glPopMatrix();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
	}
	
	public void renderEnderman(EntityEnderman par1EntityEnderman, double par2, double par4, double par6, float par8, float par9)
	{
		endermanModel.isCarrying = par1EntityEnderman.getCarried() > 0;
		endermanModel.isAttacking = par1EntityEnderman.isScreaming();
		if(par1EntityEnderman.isScreaming())
		{
			double var10 = 0.02D;
			par2 += rnd.nextGaussian() * var10;
			par6 += rnd.nextGaussian() * var10;
		}
		super.doRenderLiving(par1EntityEnderman, par2, par4, par6, par8, par9);
	}
	
	@Override protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
	{
		renderCarrying((EntityEnderman) par1EntityLivingBase, par2);
	}
	
	protected int renderEyes(EntityEnderman par1EntityEnderman, int par2, float par3)
	{
		if(par2 != 0) return -1;
		else
		{
			func_110776_a(field_110840_a);
			float var4 = 1.0F;
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			GL11.glDisable(GL11.GL_LIGHTING);
			if(par1EntityEnderman.isInvisible())
			{
				GL11.glDepthMask(false);
			} else
			{
				GL11.glDepthMask(true);
			}
			char var5 = 61680;
			int var6 = var5 % 65536;
			int var7 = var5 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
			return 1;
		}
	}
	
	@Override public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
	{
		renderEnderman((EntityEnderman) par1EntityLivingBase, par2, par4, par6, par8, par9);
	}
	
	@Override protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return renderEyes((EntityEnderman) par1EntityLivingBase, par2, par3);
	}
}
