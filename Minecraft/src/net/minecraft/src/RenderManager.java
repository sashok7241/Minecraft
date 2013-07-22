package net.minecraft.src;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RenderManager
{
	private Map entityRenderMap = new HashMap();
	public static RenderManager instance = new RenderManager();
	private FontRenderer fontRenderer;
	public static double renderPosX;
	public static double renderPosY;
	public static double renderPosZ;
	public TextureManager renderEngine;
	public ItemRenderer itemRenderer;
	public World worldObj;
	public EntityLivingBase livingPlayer;
	public EntityLivingBase field_96451_i;
	public float playerViewY;
	public float playerViewX;
	public GameSettings options;
	public double viewerPosX;
	public double viewerPosY;
	public double viewerPosZ;
	public static boolean field_85095_o;
	
	private RenderManager()
	{
		entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider());
		entityRenderMap.put(EntitySpider.class, new RenderSpider());
		entityRenderMap.put(EntityPig.class, new RenderPig(new ModelPig(), new ModelPig(0.5F), 0.7F));
		entityRenderMap.put(EntitySheep.class, new RenderSheep(new ModelSheep2(), new ModelSheep1(), 0.7F));
		entityRenderMap.put(EntityCow.class, new RenderCow(new ModelCow(), 0.7F));
		entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(new ModelCow(), 0.7F));
		entityRenderMap.put(EntityWolf.class, new RenderWolf(new ModelWolf(), new ModelWolf(), 0.5F));
		entityRenderMap.put(EntityChicken.class, new RenderChicken(new ModelChicken(), 0.3F));
		entityRenderMap.put(EntityOcelot.class, new RenderOcelot(new ModelOcelot(), 0.4F));
		entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish());
		entityRenderMap.put(EntityCreeper.class, new RenderCreeper());
		entityRenderMap.put(EntityEnderman.class, new RenderEnderman());
		entityRenderMap.put(EntitySnowman.class, new RenderSnowMan());
		entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton());
		entityRenderMap.put(EntityWitch.class, new RenderWitch());
		entityRenderMap.put(EntityBlaze.class, new RenderBlaze());
		entityRenderMap.put(EntityZombie.class, new RenderZombie());
		entityRenderMap.put(EntitySlime.class, new RenderSlime(new ModelSlime(16), new ModelSlime(0), 0.25F));
		entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube());
		entityRenderMap.put(EntityPlayer.class, new RenderPlayer());
		entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(new ModelZombie(), 0.5F, 6.0F));
		entityRenderMap.put(EntityGhast.class, new RenderGhast());
		entityRenderMap.put(EntitySquid.class, new RenderSquid(new ModelSquid(), 0.7F));
		entityRenderMap.put(EntityVillager.class, new RenderVillager());
		entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem());
		entityRenderMap.put(EntityBat.class, new RenderBat());
		entityRenderMap.put(EntityDragon.class, new RenderDragon());
		entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal());
		entityRenderMap.put(EntityWither.class, new RenderWither());
		entityRenderMap.put(Entity.class, new RenderEntity());
		entityRenderMap.put(EntityPainting.class, new RenderPainting());
		entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame());
		entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot());
		entityRenderMap.put(EntityArrow.class, new RenderArrow());
		entityRenderMap.put(EntitySnowball.class, new RenderSnowball(Item.snowball));
		entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball(Item.enderPearl));
		entityRenderMap.put(EntityEnderEye.class, new RenderSnowball(Item.eyeOfEnder));
		entityRenderMap.put(EntityEgg.class, new RenderSnowball(Item.egg));
		entityRenderMap.put(EntityPotion.class, new RenderSnowball(Item.potion, 16384));
		entityRenderMap.put(EntityExpBottle.class, new RenderSnowball(Item.expBottle));
		entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball(Item.firework));
		entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(2.0F));
		entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(0.5F));
		entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull());
		entityRenderMap.put(EntityItem.class, new RenderItem());
		entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb());
		entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed());
		entityRenderMap.put(EntityFallingSand.class, new RenderFallingSand());
		entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart());
		entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner());
		entityRenderMap.put(EntityMinecart.class, new RenderMinecart());
		entityRenderMap.put(EntityBoat.class, new RenderBoat());
		entityRenderMap.put(EntityFishHook.class, new RenderFish());
		entityRenderMap.put(EntityHorse.class, new RenderHorse(new ModelHorse(), 0.75F));
		entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt());
		Iterator var1 = entityRenderMap.values().iterator();
		while(var1.hasNext())
		{
			Render var2 = (Render) var1.next();
			var2.setRenderManager(this);
		}
	}
	
	public void cacheActiveRenderInfo(World par1World, TextureManager par2TextureManager, FontRenderer par3FontRenderer, EntityLivingBase par4EntityLivingBase, EntityLivingBase par5EntityLivingBase, GameSettings par6GameSettings, float par7)
	{
		worldObj = par1World;
		renderEngine = par2TextureManager;
		options = par6GameSettings;
		livingPlayer = par4EntityLivingBase;
		field_96451_i = par5EntityLivingBase;
		fontRenderer = par3FontRenderer;
		if(par4EntityLivingBase.isPlayerSleeping())
		{
			int var8 = par1World.getBlockId(MathHelper.floor_double(par4EntityLivingBase.posX), MathHelper.floor_double(par4EntityLivingBase.posY), MathHelper.floor_double(par4EntityLivingBase.posZ));
			if(var8 == Block.bed.blockID)
			{
				int var9 = par1World.getBlockMetadata(MathHelper.floor_double(par4EntityLivingBase.posX), MathHelper.floor_double(par4EntityLivingBase.posY), MathHelper.floor_double(par4EntityLivingBase.posZ));
				int var10 = var9 & 3;
				playerViewY = var10 * 90 + 180;
				playerViewX = 0.0F;
			}
		} else
		{
			playerViewY = par4EntityLivingBase.prevRotationYaw + (par4EntityLivingBase.rotationYaw - par4EntityLivingBase.prevRotationYaw) * par7;
			playerViewX = par4EntityLivingBase.prevRotationPitch + (par4EntityLivingBase.rotationPitch - par4EntityLivingBase.prevRotationPitch) * par7;
		}
		if(par6GameSettings.thirdPersonView == 2)
		{
			playerViewY += 180.0F;
		}
		viewerPosX = par4EntityLivingBase.lastTickPosX + (par4EntityLivingBase.posX - par4EntityLivingBase.lastTickPosX) * par7;
		viewerPosY = par4EntityLivingBase.lastTickPosY + (par4EntityLivingBase.posY - par4EntityLivingBase.lastTickPosY) * par7;
		viewerPosZ = par4EntityLivingBase.lastTickPosZ + (par4EntityLivingBase.posZ - par4EntityLivingBase.lastTickPosZ) * par7;
	}
	
	private void func_85094_b(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPushMatrix();
		Tessellator var10 = Tessellator.instance;
		var10.startDrawingQuads();
		var10.setColorRGBA(255, 255, 255, 32);
		double var11 = -par1Entity.width / 2.0F;
		double var13 = -par1Entity.width / 2.0F;
		double var15 = par1Entity.width / 2.0F;
		double var17 = -par1Entity.width / 2.0F;
		double var19 = -par1Entity.width / 2.0F;
		double var21 = par1Entity.width / 2.0F;
		double var23 = par1Entity.width / 2.0F;
		double var25 = par1Entity.width / 2.0F;
		double var27 = par1Entity.height;
		var10.addVertex(par2 + var11, par4 + var27, par6 + var13);
		var10.addVertex(par2 + var11, par4, par6 + var13);
		var10.addVertex(par2 + var15, par4, par6 + var17);
		var10.addVertex(par2 + var15, par4 + var27, par6 + var17);
		var10.addVertex(par2 + var23, par4 + var27, par6 + var25);
		var10.addVertex(par2 + var23, par4, par6 + var25);
		var10.addVertex(par2 + var19, par4, par6 + var21);
		var10.addVertex(par2 + var19, par4 + var27, par6 + var21);
		var10.addVertex(par2 + var15, par4 + var27, par6 + var17);
		var10.addVertex(par2 + var15, par4, par6 + var17);
		var10.addVertex(par2 + var23, par4, par6 + var25);
		var10.addVertex(par2 + var23, par4 + var27, par6 + var25);
		var10.addVertex(par2 + var19, par4 + var27, par6 + var21);
		var10.addVertex(par2 + var19, par4, par6 + var21);
		var10.addVertex(par2 + var11, par4, par6 + var13);
		var10.addVertex(par2 + var11, par4 + var27, par6 + var13);
		var10.draw();
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
	}
	
	public double getDistanceToCamera(double par1, double par3, double par5)
	{
		double var7 = par1 - viewerPosX;
		double var9 = par3 - viewerPosY;
		double var11 = par5 - viewerPosZ;
		return var7 * var7 + var9 * var9 + var11 * var11;
	}
	
	public Render getEntityClassRenderObject(Class par1Class)
	{
		Render var2 = (Render) entityRenderMap.get(par1Class);
		if(var2 == null && par1Class != Entity.class)
		{
			var2 = getEntityClassRenderObject(par1Class.getSuperclass());
			entityRenderMap.put(par1Class, var2);
		}
		return var2;
	}
	
	public Render getEntityRenderObject(Entity par1Entity)
	{
		return getEntityClassRenderObject(par1Entity.getClass());
	}
	
	public FontRenderer getFontRenderer()
	{
		return fontRenderer;
	}
	
	public void renderEntity(Entity par1Entity, float par2)
	{
		if(par1Entity.ticksExisted == 0)
		{
			par1Entity.lastTickPosX = par1Entity.posX;
			par1Entity.lastTickPosY = par1Entity.posY;
			par1Entity.lastTickPosZ = par1Entity.posZ;
		}
		double var3 = par1Entity.lastTickPosX + (par1Entity.posX - par1Entity.lastTickPosX) * par2;
		double var5 = par1Entity.lastTickPosY + (par1Entity.posY - par1Entity.lastTickPosY) * par2;
		double var7 = par1Entity.lastTickPosZ + (par1Entity.posZ - par1Entity.lastTickPosZ) * par2;
		float var9 = par1Entity.prevRotationYaw + (par1Entity.rotationYaw - par1Entity.prevRotationYaw) * par2;
		int var10 = par1Entity.getBrightnessForRender(par2);
		if(par1Entity.isBurning())
		{
			var10 = 15728880;
		}
		int var11 = var10 % 65536;
		int var12 = var10 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var11 / 1.0F, var12 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		renderEntityWithPosYaw(par1Entity, var3 - renderPosX, var5 - renderPosY, var7 - renderPosZ, var9, par2);
	}
	
	public void renderEntityWithPosYaw(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		Render var10 = null;
		try
		{
			var10 = getEntityRenderObject(par1Entity);
			if(var10 != null && renderEngine != null)
			{
				if(field_85095_o && !par1Entity.isInvisible())
				{
					try
					{
						func_85094_b(par1Entity, par2, par4, par6, par8, par9);
					} catch(Throwable var17)
					{
						throw new ReportedException(CrashReport.makeCrashReport(var17, "Rendering entity hitbox in world"));
					}
				}
				try
				{
					var10.doRender(par1Entity, par2, par4, par6, par8, par9);
				} catch(Throwable var16)
				{
					throw new ReportedException(CrashReport.makeCrashReport(var16, "Rendering entity in world"));
				}
				try
				{
					var10.doRenderShadowAndFire(par1Entity, par2, par4, par6, par8, par9);
				} catch(Throwable var15)
				{
					throw new ReportedException(CrashReport.makeCrashReport(var15, "Post-rendering entity in world"));
				}
			}
		} catch(Throwable var18)
		{
			CrashReport var12 = CrashReport.makeCrashReport(var18, "Rendering entity in world");
			CrashReportCategory var13 = var12.makeCategory("Entity being rendered");
			par1Entity.func_85029_a(var13);
			CrashReportCategory var14 = var12.makeCategory("Renderer details");
			var14.addCrashSection("Assigned renderer", var10);
			var14.addCrashSection("Location", CrashReportCategory.func_85074_a(par2, par4, par6));
			var14.addCrashSection("Rotation", Float.valueOf(par8));
			var14.addCrashSection("Delta", Float.valueOf(par9));
			throw new ReportedException(var12);
		}
	}
	
	public void set(World par1World)
	{
		worldObj = par1World;
	}
	
	public void updateIcons(IconRegister par1IconRegister)
	{
		Iterator var2 = entityRenderMap.values().iterator();
		while(var2.hasNext())
		{
			Render var3 = (Render) var2.next();
			var3.updateIcons(par1IconRegister);
		}
	}
}
