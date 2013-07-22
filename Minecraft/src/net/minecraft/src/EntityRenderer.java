package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;

public class EntityRenderer
{
	public static boolean anaglyphEnable = false;
	public static int anaglyphField;
	private Minecraft mc;
	private float farPlaneDistance = 0.0F;
	public ItemRenderer itemRenderer;
	private int rendererUpdateCount;
	private Entity pointedEntity = null;
	private MouseFilter mouseFilterXAxis = new MouseFilter();
	private MouseFilter mouseFilterYAxis = new MouseFilter();
	private MouseFilter mouseFilterDummy1 = new MouseFilter();
	private MouseFilter mouseFilterDummy2 = new MouseFilter();
	private MouseFilter mouseFilterDummy3 = new MouseFilter();
	private MouseFilter mouseFilterDummy4 = new MouseFilter();
	private float thirdPersonDistance = 4.0F;
	private float thirdPersonDistanceTemp = 4.0F;
	private float debugCamYaw = 0.0F;
	private float prevDebugCamYaw = 0.0F;
	private float debugCamPitch = 0.0F;
	private float prevDebugCamPitch = 0.0F;
	private float smoothCamYaw;
	private float smoothCamPitch;
	private float smoothCamFilterX;
	private float smoothCamFilterY;
	private float smoothCamPartialTicks;
	private float debugCamFOV = 0.0F;
	private float prevDebugCamFOV = 0.0F;
	private float camRoll = 0.0F;
	private float prevCamRoll = 0.0F;
	public int lightmapTexture;
	private int[] lightmapColors;
	private float fovModifierHand;
	private float fovModifierHandPrev;
	private float fovMultiplierTemp;
	private float field_82831_U;
	private float field_82832_V;
	private boolean cloudFog = false;
	private double cameraZoom = 1.0D;
	private double cameraYaw = 0.0D;
	private double cameraPitch = 0.0D;
	private long prevFrameTime = Minecraft.getSystemTime();
	private long renderEndNanoTime = 0L;
	private boolean lightmapUpdateNeeded = false;
	float torchFlickerX = 0.0F;
	float torchFlickerDX = 0.0F;
	float torchFlickerY = 0.0F;
	float torchFlickerDY = 0.0F;
	private Random random = new Random();
	private int rainSoundCounter = 0;
	float[] rainXCoords;
	float[] rainYCoords;
	volatile int field_78523_k = 0;
	volatile int field_78520_l = 0;
	FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
	float fogColorRed;
	float fogColorGreen;
	float fogColorBlue;
	private float fogColor2;
	private float fogColor1;
	public int debugViewDirection;
	
	public EntityRenderer(Minecraft par1Minecraft)
	{
		mc = par1Minecraft;
		itemRenderer = new ItemRenderer(par1Minecraft);
		lightmapTexture = par1Minecraft.renderEngine.allocateAndSetupTexture(new BufferedImage(16, 16, 1));
		lightmapColors = new int[256];
	}
	
	private void addRainParticles()
	{
		float var1 = mc.theWorld.getRainStrength(1.0F);
		if(!mc.gameSettings.fancyGraphics)
		{
			var1 /= 2.0F;
		}
		if(var1 != 0.0F)
		{
			random.setSeed(rendererUpdateCount * 312987231L);
			EntityLiving var2 = mc.renderViewEntity;
			WorldClient var3 = mc.theWorld;
			int var4 = MathHelper.floor_double(var2.posX);
			int var5 = MathHelper.floor_double(var2.posY);
			int var6 = MathHelper.floor_double(var2.posZ);
			byte var7 = 10;
			double var8 = 0.0D;
			double var10 = 0.0D;
			double var12 = 0.0D;
			int var14 = 0;
			int var15 = (int) (100.0F * var1 * var1);
			if(mc.gameSettings.particleSetting == 1)
			{
				var15 >>= 1;
			} else if(mc.gameSettings.particleSetting == 2)
			{
				var15 = 0;
			}
			for(int var16 = 0; var16 < var15; ++var16)
			{
				int var17 = var4 + random.nextInt(var7) - random.nextInt(var7);
				int var18 = var6 + random.nextInt(var7) - random.nextInt(var7);
				int var19 = var3.getPrecipitationHeight(var17, var18);
				int var20 = var3.getBlockId(var17, var19 - 1, var18);
				BiomeGenBase var21 = var3.getBiomeGenForCoords(var17, var18);
				if(var19 <= var5 + var7 && var19 >= var5 - var7 && var21.canSpawnLightningBolt() && var21.getFloatTemperature() >= 0.2F)
				{
					float var22 = random.nextFloat();
					float var23 = random.nextFloat();
					if(var20 > 0)
					{
						if(Block.blocksList[var20].blockMaterial == Material.lava)
						{
							mc.effectRenderer.addEffect(new EntitySmokeFX(var3, var17 + var22, var19 + 0.1F - Block.blocksList[var20].getBlockBoundsMinY(), var18 + var23, 0.0D, 0.0D, 0.0D));
						} else
						{
							++var14;
							if(random.nextInt(var14) == 0)
							{
								var8 = var17 + var22;
								var10 = var19 + 0.1F - Block.blocksList[var20].getBlockBoundsMinY();
								var12 = var18 + var23;
							}
							mc.effectRenderer.addEffect(new EntityRainFX(var3, var17 + var22, var19 + 0.1F - Block.blocksList[var20].getBlockBoundsMinY(), var18 + var23));
						}
					}
				}
			}
			if(var14 > 0 && random.nextInt(3) < rainSoundCounter++)
			{
				rainSoundCounter = 0;
				if(var10 > var2.posY + 1.0D && var3.getPrecipitationHeight(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posZ)) > MathHelper.floor_double(var2.posY))
				{
					mc.theWorld.playSound(var8, var10, var12, "ambient.weather.rain", 0.1F, 0.5F, false);
				} else
				{
					mc.theWorld.playSound(var8, var10, var12, "ambient.weather.rain", 0.2F, 1.0F, false);
				}
			}
		}
	}
	
	public void disableLightmap(double par1)
	{
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
	
	public void enableLightmap(double par1)
	{
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		float var3 = 0.00390625F;
		GL11.glScalef(var3, var3, var3);
		GL11.glTranslatef(8.0F, 8.0F, 8.0F);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, lightmapTexture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		mc.renderEngine.resetBoundTexture();
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
	
	private float getFOVModifier(float par1, boolean par2)
	{
		if(debugViewDirection > 0) return 90.0F;
		else
		{
			EntityPlayer var3 = (EntityPlayer) mc.renderViewEntity;
			float var4 = 70.0F;
			if(par2)
			{
				var4 += mc.gameSettings.fovSetting * 40.0F;
				var4 *= fovModifierHandPrev + (fovModifierHand - fovModifierHandPrev) * par1;
			}
			if(var3.getHealth() <= 0)
			{
				float var5 = var3.deathTime + par1;
				var4 /= (1.0F - 500.0F / (var5 + 500.0F)) * 2.0F + 1.0F;
			}
			int var6 = ActiveRenderInfo.getBlockIdAtEntityViewpoint(mc.theWorld, var3, par1);
			if(var6 != 0 && Block.blocksList[var6].blockMaterial == Material.water)
			{
				var4 = var4 * 60.0F / 70.0F;
			}
			return var4 + prevDebugCamFOV + (debugCamFOV - prevDebugCamFOV) * par1;
		}
	}
	
	public void getMouseOver(float par1)
	{
		if(mc.renderViewEntity != null)
		{
			if(mc.theWorld != null)
			{
				mc.pointedEntityLiving = null;
				double var2 = mc.playerController.getBlockReachDistance();
				mc.objectMouseOver = mc.renderViewEntity.rayTrace(var2, par1);
				double var4 = var2;
				Vec3 var6 = mc.renderViewEntity.getPosition(par1);
				if(mc.playerController.extendedReach())
				{
					var2 = 6.0D;
					var4 = 6.0D;
				} else
				{
					if(var2 > 3.0D)
					{
						var4 = 3.0D;
					}
					var2 = var4;
				}
				if(mc.objectMouseOver != null)
				{
					var4 = mc.objectMouseOver.hitVec.distanceTo(var6);
				}
				Vec3 var7 = mc.renderViewEntity.getLook(par1);
				Vec3 var8 = var6.addVector(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2);
				pointedEntity = null;
				float var9 = 1.0F;
				List var10 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.renderViewEntity, mc.renderViewEntity.boundingBox.addCoord(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2).expand(var9, var9, var9));
				double var11 = var4;
				for(int var13 = 0; var13 < var10.size(); ++var13)
				{
					Entity var14 = (Entity) var10.get(var13);
					if(var14.canBeCollidedWith())
					{
						float var15 = var14.getCollisionBorderSize();
						AxisAlignedBB var16 = var14.boundingBox.expand(var15, var15, var15);
						MovingObjectPosition var17 = var16.calculateIntercept(var6, var8);
						if(var16.isVecInside(var6))
						{
							if(0.0D < var11 || var11 == 0.0D)
							{
								pointedEntity = var14;
								var11 = 0.0D;
							}
						} else if(var17 != null)
						{
							double var18 = var6.distanceTo(var17.hitVec);
							if(var18 < var11 || var11 == 0.0D)
							{
								pointedEntity = var14;
								var11 = var18;
							}
						}
					}
				}
				if(pointedEntity != null && (var11 < var4 || mc.objectMouseOver == null))
				{
					mc.objectMouseOver = new MovingObjectPosition(pointedEntity);
					if(pointedEntity instanceof EntityLiving)
					{
						mc.pointedEntityLiving = (EntityLiving) pointedEntity;
					}
				}
			}
		}
	}
	
	private float getNightVisionBrightness(EntityPlayer par1EntityPlayer, float par2)
	{
		int var3 = par1EntityPlayer.getActivePotionEffect(Potion.nightVision).getDuration();
		return var3 > 200 ? 1.0F : 0.7F + MathHelper.sin((var3 - par2) * (float) Math.PI * 0.2F) * 0.3F;
	}
	
	private void hurtCameraEffect(float par1)
	{
		EntityLiving var2 = mc.renderViewEntity;
		float var3 = var2.hurtTime - par1;
		float var4;
		if(var2.getHealth() <= 0)
		{
			var4 = var2.deathTime + par1;
			GL11.glRotatef(40.0F - 8000.0F / (var4 + 200.0F), 0.0F, 0.0F, 1.0F);
		}
		if(var3 >= 0.0F)
		{
			var3 /= var2.maxHurtTime;
			var3 = MathHelper.sin(var3 * var3 * var3 * var3 * (float) Math.PI);
			var4 = var2.attackedAtYaw;
			GL11.glRotatef(-var4, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-var3 * 14.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(var4, 0.0F, 1.0F, 0.0F);
		}
	}
	
	private void orientCamera(float par1)
	{
		EntityLiving var2 = mc.renderViewEntity;
		float var3 = var2.yOffset - 1.62F;
		double var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * par1;
		double var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * par1 - var3;
		double var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * par1;
		GL11.glRotatef(prevCamRoll + (camRoll - prevCamRoll) * par1, 0.0F, 0.0F, 1.0F);
		if(var2.isPlayerSleeping())
		{
			var3 = (float) (var3 + 1.0D);
			GL11.glTranslatef(0.0F, 0.3F, 0.0F);
			if(!mc.gameSettings.debugCamEnable)
			{
				int var10 = mc.theWorld.getBlockId(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
				if(var10 == Block.bed.blockID)
				{
					int var11 = mc.theWorld.getBlockMetadata(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
					int var12 = var11 & 3;
					GL11.glRotatef((float) (var12 * 90), 0.0F, 1.0F, 0.0F);
				}
				GL11.glRotatef(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * par1 + 180.0F, 0.0F, -1.0F, 0.0F);
				GL11.glRotatef(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * par1, -1.0F, 0.0F, 0.0F);
			}
		} else if(mc.gameSettings.thirdPersonView > 0)
		{
			double var27 = thirdPersonDistanceTemp + (thirdPersonDistance - thirdPersonDistanceTemp) * par1;
			float var13;
			float var28;
			if(mc.gameSettings.debugCamEnable)
			{
				var28 = prevDebugCamYaw + (debugCamYaw - prevDebugCamYaw) * par1;
				var13 = prevDebugCamPitch + (debugCamPitch - prevDebugCamPitch) * par1;
				GL11.glTranslatef(0.0F, 0.0F, (float) -var27);
				GL11.glRotatef(var13, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(var28, 0.0F, 1.0F, 0.0F);
			} else
			{
				var28 = var2.rotationYaw;
				var13 = var2.rotationPitch;
				if(mc.gameSettings.thirdPersonView == 2)
				{
					var13 += 180.0F;
				}
				double var14 = -MathHelper.sin(var28 / 180.0F * (float) Math.PI) * MathHelper.cos(var13 / 180.0F * (float) Math.PI) * var27;
				double var16 = MathHelper.cos(var28 / 180.0F * (float) Math.PI) * MathHelper.cos(var13 / 180.0F * (float) Math.PI) * var27;
				double var18 = -MathHelper.sin(var13 / 180.0F * (float) Math.PI) * var27;
				for(int var20 = 0; var20 < 8; ++var20)
				{
					float var21 = (var20 & 1) * 2 - 1;
					float var22 = (var20 >> 1 & 1) * 2 - 1;
					float var23 = (var20 >> 2 & 1) * 2 - 1;
					var21 *= 0.1F;
					var22 *= 0.1F;
					var23 *= 0.1F;
					MovingObjectPosition var24 = mc.theWorld.clip(mc.theWorld.getWorldVec3Pool().getVecFromPool(var4 + var21, var6 + var22, var8 + var23), mc.theWorld.getWorldVec3Pool().getVecFromPool(var4 - var14 + var21 + var23, var6 - var18 + var22, var8 - var16 + var23));
					if(var24 != null)
					{
						double var25 = var24.hitVec.distanceTo(mc.theWorld.getWorldVec3Pool().getVecFromPool(var4, var6, var8));
						if(var25 < var27)
						{
							var27 = var25;
						}
					}
				}
				if(mc.gameSettings.thirdPersonView == 2)
				{
					GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
				}
				GL11.glRotatef(var2.rotationPitch - var13, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(var2.rotationYaw - var28, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(0.0F, 0.0F, (float) -var27);
				GL11.glRotatef(var28 - var2.rotationYaw, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(var13 - var2.rotationPitch, 1.0F, 0.0F, 0.0F);
			}
		} else
		{
			GL11.glTranslatef(0.0F, 0.0F, -0.1F);
		}
		if(!mc.gameSettings.debugCamEnable)
		{
			GL11.glRotatef(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * par1, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * par1 + 180.0F, 0.0F, 1.0F, 0.0F);
		}
		GL11.glTranslatef(0.0F, var3, 0.0F);
		var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * par1;
		var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * par1 - var3;
		var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * par1;
		cloudFog = mc.renderGlobal.hasCloudFog(var4, var6, var8, par1);
	}
	
	private void renderCloudsCheck(RenderGlobal par1RenderGlobal, float par2)
	{
		if(mc.gameSettings.shouldRenderClouds())
		{
			mc.mcProfiler.endStartSection("clouds");
			GL11.glPushMatrix();
			setupFog(0, par2);
			GL11.glEnable(GL11.GL_FOG);
			par1RenderGlobal.renderClouds(par2);
			GL11.glDisable(GL11.GL_FOG);
			setupFog(1, par2);
			GL11.glPopMatrix();
		}
	}
	
	private void renderHand(float par1, int par2)
	{
		if(debugViewDirection <= 0)
		{
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			float var3 = 0.07F;
			if(mc.gameSettings.anaglyph)
			{
				GL11.glTranslatef(-(par2 * 2 - 1) * var3, 0.0F, 0.0F);
			}
			if(cameraZoom != 1.0D)
			{
				GL11.glTranslatef((float) cameraYaw, (float) -cameraPitch, 0.0F);
				GL11.glScaled(cameraZoom, cameraZoom, 1.0D);
			}
			GLU.gluPerspective(getFOVModifier(par1, false), (float) mc.displayWidth / (float) mc.displayHeight, 0.05F, farPlaneDistance * 2.0F);
			if(mc.playerController.enableEverythingIsScrewedUpMode())
			{
				float var4 = 0.6666667F;
				GL11.glScalef(1.0F, var4, 1.0F);
			}
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			if(mc.gameSettings.anaglyph)
			{
				GL11.glTranslatef((par2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
			}
			GL11.glPushMatrix();
			hurtCameraEffect(par1);
			if(mc.gameSettings.viewBobbing)
			{
				setupViewBobbing(par1);
			}
			if(mc.gameSettings.thirdPersonView == 0 && !mc.renderViewEntity.isPlayerSleeping() && !mc.gameSettings.hideGUI && !mc.playerController.enableEverythingIsScrewedUpMode())
			{
				enableLightmap(par1);
				itemRenderer.renderItemInFirstPerson(par1);
				disableLightmap(par1);
			}
			GL11.glPopMatrix();
			if(mc.gameSettings.thirdPersonView == 0 && !mc.renderViewEntity.isPlayerSleeping())
			{
				itemRenderer.renderOverlays(par1);
				hurtCameraEffect(par1);
			}
			if(mc.gameSettings.viewBobbing)
			{
				setupViewBobbing(par1);
			}
		}
	}
	
	protected void renderRainSnow(float par1)
	{
		float var2 = mc.theWorld.getRainStrength(par1);
		if(var2 > 0.0F)
		{
			enableLightmap(par1);
			if(rainXCoords == null)
			{
				rainXCoords = new float[1024];
				rainYCoords = new float[1024];
				for(int var3 = 0; var3 < 32; ++var3)
				{
					for(int var4 = 0; var4 < 32; ++var4)
					{
						float var5 = var4 - 16;
						float var6 = var3 - 16;
						float var7 = MathHelper.sqrt_float(var5 * var5 + var6 * var6);
						rainXCoords[var3 << 5 | var4] = -var6 / var7;
						rainYCoords[var3 << 5 | var4] = var5 / var7;
					}
				}
			}
			EntityLiving var41 = mc.renderViewEntity;
			WorldClient var42 = mc.theWorld;
			int var43 = MathHelper.floor_double(var41.posX);
			int var44 = MathHelper.floor_double(var41.posY);
			int var45 = MathHelper.floor_double(var41.posZ);
			Tessellator var8 = Tessellator.instance;
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.01F);
			mc.renderEngine.bindTexture("/environment/snow.png");
			double var9 = var41.lastTickPosX + (var41.posX - var41.lastTickPosX) * par1;
			double var11 = var41.lastTickPosY + (var41.posY - var41.lastTickPosY) * par1;
			double var13 = var41.lastTickPosZ + (var41.posZ - var41.lastTickPosZ) * par1;
			int var15 = MathHelper.floor_double(var11);
			byte var16 = 5;
			if(mc.gameSettings.fancyGraphics)
			{
				var16 = 10;
			}
			boolean var17 = false;
			byte var18 = -1;
			float var19 = rendererUpdateCount + par1;
			if(mc.gameSettings.fancyGraphics)
			{
				var16 = 10;
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			var17 = false;
			for(int var20 = var45 - var16; var20 <= var45 + var16; ++var20)
			{
				for(int var21 = var43 - var16; var21 <= var43 + var16; ++var21)
				{
					int var22 = (var20 - var45 + 16) * 32 + var21 - var43 + 16;
					float var23 = rainXCoords[var22] * 0.5F;
					float var24 = rainYCoords[var22] * 0.5F;
					BiomeGenBase var25 = var42.getBiomeGenForCoords(var21, var20);
					if(var25.canSpawnLightningBolt() || var25.getEnableSnow())
					{
						int var26 = var42.getPrecipitationHeight(var21, var20);
						int var27 = var44 - var16;
						int var28 = var44 + var16;
						if(var27 < var26)
						{
							var27 = var26;
						}
						if(var28 < var26)
						{
							var28 = var26;
						}
						float var29 = 1.0F;
						int var30 = var26;
						if(var26 < var15)
						{
							var30 = var15;
						}
						if(var27 != var28)
						{
							random.setSeed(var21 * var21 * 3121 + var21 * 45238971 ^ var20 * var20 * 418711 + var20 * 13761);
							float var31 = var25.getFloatTemperature();
							double var35;
							float var32;
							if(var42.getWorldChunkManager().getTemperatureAtHeight(var31, var26) >= 0.15F)
							{
								if(var18 != 0)
								{
									if(var18 >= 0)
									{
										var8.draw();
									}
									var18 = 0;
									mc.renderEngine.bindTexture("/environment/rain.png");
									var8.startDrawingQuads();
								}
								var32 = ((rendererUpdateCount + var21 * var21 * 3121 + var21 * 45238971 + var20 * var20 * 418711 + var20 * 13761 & 31) + par1) / 32.0F * (3.0F + random.nextFloat());
								double var33 = var21 + 0.5F - var41.posX;
								var35 = var20 + 0.5F - var41.posZ;
								float var37 = MathHelper.sqrt_double(var33 * var33 + var35 * var35) / var16;
								float var38 = 1.0F;
								var8.setBrightness(var42.getLightBrightnessForSkyBlocks(var21, var30, var20, 0));
								var8.setColorRGBA_F(var38, var38, var38, ((1.0F - var37 * var37) * 0.5F + 0.5F) * var2);
								var8.setTranslation(-var9 * 1.0D, -var11 * 1.0D, -var13 * 1.0D);
								var8.addVertexWithUV(var21 - var23 + 0.5D, var27, var20 - var24 + 0.5D, 0.0F * var29, var27 * var29 / 4.0F + var32 * var29);
								var8.addVertexWithUV(var21 + var23 + 0.5D, var27, var20 + var24 + 0.5D, 1.0F * var29, var27 * var29 / 4.0F + var32 * var29);
								var8.addVertexWithUV(var21 + var23 + 0.5D, var28, var20 + var24 + 0.5D, 1.0F * var29, var28 * var29 / 4.0F + var32 * var29);
								var8.addVertexWithUV(var21 - var23 + 0.5D, var28, var20 - var24 + 0.5D, 0.0F * var29, var28 * var29 / 4.0F + var32 * var29);
								var8.setTranslation(0.0D, 0.0D, 0.0D);
							} else
							{
								if(var18 != 1)
								{
									if(var18 >= 0)
									{
										var8.draw();
									}
									var18 = 1;
									mc.renderEngine.bindTexture("/environment/snow.png");
									var8.startDrawingQuads();
								}
								var32 = ((rendererUpdateCount & 511) + par1) / 512.0F;
								float var46 = random.nextFloat() + var19 * 0.01F * (float) random.nextGaussian();
								float var34 = random.nextFloat() + var19 * (float) random.nextGaussian() * 0.001F;
								var35 = var21 + 0.5F - var41.posX;
								double var47 = var20 + 0.5F - var41.posZ;
								float var39 = MathHelper.sqrt_double(var35 * var35 + var47 * var47) / var16;
								float var40 = 1.0F;
								var8.setBrightness((var42.getLightBrightnessForSkyBlocks(var21, var30, var20, 0) * 3 + 15728880) / 4);
								var8.setColorRGBA_F(var40, var40, var40, ((1.0F - var39 * var39) * 0.3F + 0.5F) * var2);
								var8.setTranslation(-var9 * 1.0D, -var11 * 1.0D, -var13 * 1.0D);
								var8.addVertexWithUV(var21 - var23 + 0.5D, var27, var20 - var24 + 0.5D, 0.0F * var29 + var46, var27 * var29 / 4.0F + var32 * var29 + var34);
								var8.addVertexWithUV(var21 + var23 + 0.5D, var27, var20 + var24 + 0.5D, 1.0F * var29 + var46, var27 * var29 / 4.0F + var32 * var29 + var34);
								var8.addVertexWithUV(var21 + var23 + 0.5D, var28, var20 + var24 + 0.5D, 1.0F * var29 + var46, var28 * var29 / 4.0F + var32 * var29 + var34);
								var8.addVertexWithUV(var21 - var23 + 0.5D, var28, var20 - var24 + 0.5D, 0.0F * var29 + var46, var28 * var29 / 4.0F + var32 * var29 + var34);
								var8.setTranslation(0.0D, 0.0D, 0.0D);
							}
						}
					}
				}
			}
			if(var18 >= 0)
			{
				var8.draw();
			}
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			disableLightmap(par1);
		}
	}
	
	public void renderWorld(float par1, long par2)
	{
		mc.mcProfiler.startSection("lightTex");
		if(lightmapUpdateNeeded)
		{
			updateLightmap(par1);
		}
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		if(mc.renderViewEntity == null)
		{
			mc.renderViewEntity = mc.thePlayer;
		}
		mc.mcProfiler.endStartSection("pick");
		getMouseOver(par1);
		EntityLiving var4 = mc.renderViewEntity;
		RenderGlobal var5 = mc.renderGlobal;
		EffectRenderer var6 = mc.effectRenderer;
		double var7 = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * par1;
		double var9 = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * par1;
		double var11 = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * par1;
		mc.mcProfiler.endStartSection("center");
		for(int var13 = 0; var13 < 2; ++var13)
		{
			if(mc.gameSettings.anaglyph)
			{
				anaglyphField = var13;
				if(anaglyphField == 0)
				{
					GL11.glColorMask(false, true, true, false);
				} else
				{
					GL11.glColorMask(true, false, false, false);
				}
			}
			mc.mcProfiler.endStartSection("clear");
			GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
			updateFogColor(par1);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glEnable(GL11.GL_CULL_FACE);
			mc.mcProfiler.endStartSection("camera");
			setupCameraTransform(par1, var13);
			ActiveRenderInfo.updateRenderInfo(mc.thePlayer, mc.gameSettings.thirdPersonView == 2);
			mc.mcProfiler.endStartSection("frustrum");
			ClippingHelperImpl.getInstance();
			if(mc.gameSettings.renderDistance < 2)
			{
				setupFog(-1, par1);
				mc.mcProfiler.endStartSection("sky");
				var5.renderSky(par1);
			}
			GL11.glEnable(GL11.GL_FOG);
			setupFog(1, par1);
			if(mc.gameSettings.ambientOcclusion != 0)
			{
				GL11.glShadeModel(GL11.GL_SMOOTH);
			}
			mc.mcProfiler.endStartSection("culling");
			Frustrum var14 = new Frustrum();
			var14.setPosition(var7, var9, var11);
			mc.renderGlobal.clipRenderersByFrustum(var14, par1);
			if(var13 == 0)
			{
				mc.mcProfiler.endStartSection("updatechunks");
				while(!mc.renderGlobal.updateRenderers(var4, false) && par2 != 0L)
				{
					long var15 = par2 - System.nanoTime();
					if(var15 < 0L || var15 > 1000000000L)
					{
						break;
					}
				}
			}
			if(var4.posY < 128.0D)
			{
				renderCloudsCheck(var5, par1);
			}
			mc.mcProfiler.endStartSection("prepareterrain");
			setupFog(0, par1);
			GL11.glEnable(GL11.GL_FOG);
			mc.renderEngine.bindTexture("/terrain.png");
			RenderHelper.disableStandardItemLighting();
			mc.mcProfiler.endStartSection("terrain");
			var5.sortAndRender(var4, 0, par1);
			GL11.glShadeModel(GL11.GL_FLAT);
			EntityPlayer var17;
			if(debugViewDirection == 0)
			{
				RenderHelper.enableStandardItemLighting();
				mc.mcProfiler.endStartSection("entities");
				var5.renderEntities(var4.getPosition(par1), var14, par1);
				enableLightmap(par1);
				mc.mcProfiler.endStartSection("litParticles");
				var6.renderLitParticles(var4, par1);
				RenderHelper.disableStandardItemLighting();
				setupFog(0, par1);
				mc.mcProfiler.endStartSection("particles");
				var6.renderParticles(var4, par1);
				disableLightmap(par1);
				if(mc.objectMouseOver != null && var4.isInsideOfMaterial(Material.water) && var4 instanceof EntityPlayer && !mc.gameSettings.hideGUI)
				{
					var17 = (EntityPlayer) var4;
					GL11.glDisable(GL11.GL_ALPHA_TEST);
					mc.mcProfiler.endStartSection("outline");
					var5.drawBlockBreaking(var17, mc.objectMouseOver, 0, var17.inventory.getCurrentItem(), par1);
					var5.drawSelectionBox(var17, mc.objectMouseOver, 0, var17.inventory.getCurrentItem(), par1);
					GL11.glEnable(GL11.GL_ALPHA_TEST);
				}
			}
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDepthMask(true);
			setupFog(0, par1);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			mc.renderEngine.bindTexture("/terrain.png");
			if(mc.gameSettings.fancyGraphics)
			{
				mc.mcProfiler.endStartSection("water");
				if(mc.gameSettings.ambientOcclusion != 0)
				{
					GL11.glShadeModel(GL11.GL_SMOOTH);
				}
				GL11.glColorMask(false, false, false, false);
				int var18 = var5.sortAndRender(var4, 1, par1);
				if(mc.gameSettings.anaglyph)
				{
					if(anaglyphField == 0)
					{
						GL11.glColorMask(false, true, true, true);
					} else
					{
						GL11.glColorMask(true, false, false, true);
					}
				} else
				{
					GL11.glColorMask(true, true, true, true);
				}
				if(var18 > 0)
				{
					var5.renderAllRenderLists(1, par1);
				}
				GL11.glShadeModel(GL11.GL_FLAT);
			} else
			{
				mc.mcProfiler.endStartSection("water");
				var5.sortAndRender(var4, 1, par1);
			}
			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			if(cameraZoom == 1.0D && var4 instanceof EntityPlayer && !mc.gameSettings.hideGUI && mc.objectMouseOver != null && !var4.isInsideOfMaterial(Material.water))
			{
				var17 = (EntityPlayer) var4;
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				mc.mcProfiler.endStartSection("outline");
				var5.drawBlockBreaking(var17, mc.objectMouseOver, 0, var17.inventory.getCurrentItem(), par1);
				var5.drawSelectionBox(var17, mc.objectMouseOver, 0, var17.inventory.getCurrentItem(), par1);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
			}
			mc.mcProfiler.endStartSection("destroyProgress");
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			var5.drawBlockDamageTexture(Tessellator.instance, (EntityPlayer) var4, par1);
			GL11.glDisable(GL11.GL_BLEND);
			mc.mcProfiler.endStartSection("weather");
			renderRainSnow(par1);
			GL11.glDisable(GL11.GL_FOG);
			if(var4.posY >= 128.0D)
			{
				renderCloudsCheck(var5, par1);
			}
			mc.mcProfiler.endStartSection("hand");
			if(cameraZoom == 1.0D)
			{
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
				renderHand(par1, var13);
			}
			if(!mc.gameSettings.anaglyph)
			{
				mc.mcProfiler.endSection();
				return;
			}
		}
		GL11.glColorMask(true, true, true, false);
		mc.mcProfiler.endSection();
	}
	
	private FloatBuffer setFogColorBuffer(float par1, float par2, float par3, float par4)
	{
		fogColorBuffer.clear();
		fogColorBuffer.put(par1).put(par2).put(par3).put(par4);
		fogColorBuffer.flip();
		return fogColorBuffer;
	}
	
	private void setupCameraTransform(float par1, int par2)
	{
		farPlaneDistance = 256 >> mc.gameSettings.renderDistance;
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		float var3 = 0.07F;
		if(mc.gameSettings.anaglyph)
		{
			GL11.glTranslatef(-(par2 * 2 - 1) * var3, 0.0F, 0.0F);
		}
		if(cameraZoom != 1.0D)
		{
			GL11.glTranslatef((float) cameraYaw, (float) -cameraPitch, 0.0F);
			GL11.glScaled(cameraZoom, cameraZoom, 1.0D);
		}
		GLU.gluPerspective(getFOVModifier(par1, true), (float) mc.displayWidth / (float) mc.displayHeight, 0.05F, farPlaneDistance * 2.0F);
		float var4;
		if(mc.playerController.enableEverythingIsScrewedUpMode())
		{
			var4 = 0.6666667F;
			GL11.glScalef(1.0F, var4, 1.0F);
		}
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		if(mc.gameSettings.anaglyph)
		{
			GL11.glTranslatef((par2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
		}
		hurtCameraEffect(par1);
		if(mc.gameSettings.viewBobbing)
		{
			setupViewBobbing(par1);
		}
		var4 = mc.thePlayer.prevTimeInPortal + (mc.thePlayer.timeInPortal - mc.thePlayer.prevTimeInPortal) * par1;
		if(var4 > 0.0F)
		{
			byte var5 = 20;
			if(mc.thePlayer.isPotionActive(Potion.confusion))
			{
				var5 = 7;
			}
			float var6 = 5.0F / (var4 * var4 + 5.0F) - var4 * 0.04F;
			var6 *= var6;
			GL11.glRotatef((rendererUpdateCount + par1) * var5, 0.0F, 1.0F, 1.0F);
			GL11.glScalef(1.0F / var6, 1.0F, 1.0F);
			GL11.glRotatef(-(rendererUpdateCount + par1) * var5, 0.0F, 1.0F, 1.0F);
		}
		orientCamera(par1);
		if(debugViewDirection > 0)
		{
			int var7 = debugViewDirection - 1;
			if(var7 == 1)
			{
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			}
			if(var7 == 2)
			{
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			}
			if(var7 == 3)
			{
				GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			}
			if(var7 == 4)
			{
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			}
			if(var7 == 5)
			{
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
			}
		}
	}
	
	private void setupFog(int par1, float par2)
	{
		EntityLiving var3 = mc.renderViewEntity;
		boolean var4 = false;
		if(var3 instanceof EntityPlayer)
		{
			var4 = ((EntityPlayer) var3).capabilities.isCreativeMode;
		}
		if(par1 == 999)
		{
			GL11.glFog(GL11.GL_FOG_COLOR, setFogColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
			GL11.glFogf(GL11.GL_FOG_START, 0.0F);
			GL11.glFogf(GL11.GL_FOG_END, 8.0F);
			if(GLContext.getCapabilities().GL_NV_fog_distance)
			{
				GL11.glFogi(34138, 34139);
			}
			GL11.glFogf(GL11.GL_FOG_START, 0.0F);
		} else
		{
			GL11.glFog(GL11.GL_FOG_COLOR, setFogColorBuffer(fogColorRed, fogColorGreen, fogColorBlue, 1.0F));
			GL11.glNormal3f(0.0F, -1.0F, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			int var5 = ActiveRenderInfo.getBlockIdAtEntityViewpoint(mc.theWorld, var3, par2);
			float var6;
			if(var3.isPotionActive(Potion.blindness))
			{
				var6 = 5.0F;
				int var7 = var3.getActivePotionEffect(Potion.blindness).getDuration();
				if(var7 < 20)
				{
					var6 = 5.0F + (farPlaneDistance - 5.0F) * (1.0F - var7 / 20.0F);
				}
				GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
				if(par1 < 0)
				{
					GL11.glFogf(GL11.GL_FOG_START, 0.0F);
					GL11.glFogf(GL11.GL_FOG_END, var6 * 0.8F);
				} else
				{
					GL11.glFogf(GL11.GL_FOG_START, var6 * 0.25F);
					GL11.glFogf(GL11.GL_FOG_END, var6);
				}
				if(GLContext.getCapabilities().GL_NV_fog_distance)
				{
					GL11.glFogi(34138, 34139);
				}
			} else
			{
				float var8;
				float var9;
				float var10;
				float var11;
				float var12;
				if(cloudFog)
				{
					GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
					GL11.glFogf(GL11.GL_FOG_DENSITY, 0.1F);
					var6 = 1.0F;
					var12 = 1.0F;
					var8 = 1.0F;
					if(mc.gameSettings.anaglyph)
					{
						var9 = (var6 * 30.0F + var12 * 59.0F + var8 * 11.0F) / 100.0F;
						var10 = (var6 * 30.0F + var12 * 70.0F) / 100.0F;
						var11 = (var6 * 30.0F + var8 * 70.0F) / 100.0F;
					}
				} else if(var5 > 0 && Block.blocksList[var5].blockMaterial == Material.water)
				{
					GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
					if(var3.isPotionActive(Potion.waterBreathing))
					{
						GL11.glFogf(GL11.GL_FOG_DENSITY, 0.05F);
					} else
					{
						GL11.glFogf(GL11.GL_FOG_DENSITY, 0.1F);
					}
					var6 = 0.4F;
					var12 = 0.4F;
					var8 = 0.9F;
					if(mc.gameSettings.anaglyph)
					{
						var9 = (var6 * 30.0F + var12 * 59.0F + var8 * 11.0F) / 100.0F;
						var10 = (var6 * 30.0F + var12 * 70.0F) / 100.0F;
						var11 = (var6 * 30.0F + var8 * 70.0F) / 100.0F;
					}
				} else if(var5 > 0 && Block.blocksList[var5].blockMaterial == Material.lava)
				{
					GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
					GL11.glFogf(GL11.GL_FOG_DENSITY, 2.0F);
					var6 = 0.4F;
					var12 = 0.3F;
					var8 = 0.3F;
					if(mc.gameSettings.anaglyph)
					{
						var9 = (var6 * 30.0F + var12 * 59.0F + var8 * 11.0F) / 100.0F;
						var10 = (var6 * 30.0F + var12 * 70.0F) / 100.0F;
						var11 = (var6 * 30.0F + var8 * 70.0F) / 100.0F;
					}
				} else
				{
					var6 = farPlaneDistance;
					if(mc.theWorld.provider.getWorldHasVoidParticles() && !var4)
					{
						double var13 = ((var3.getBrightnessForRender(par2) & 15728640) >> 20) / 16.0D + (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * par2 + 4.0D) / 32.0D;
						if(var13 < 1.0D)
						{
							if(var13 < 0.0D)
							{
								var13 = 0.0D;
							}
							var13 *= var13;
							var9 = 100.0F * (float) var13;
							if(var9 < 5.0F)
							{
								var9 = 5.0F;
							}
							if(var6 > var9)
							{
								var6 = var9;
							}
						}
					}
					GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
					if(par1 < 0)
					{
						GL11.glFogf(GL11.GL_FOG_START, 0.0F);
						GL11.glFogf(GL11.GL_FOG_END, var6 * 0.8F);
					} else
					{
						GL11.glFogf(GL11.GL_FOG_START, var6 * 0.25F);
						GL11.glFogf(GL11.GL_FOG_END, var6);
					}
					if(GLContext.getCapabilities().GL_NV_fog_distance)
					{
						GL11.glFogi(34138, 34139);
					}
					if(mc.theWorld.provider.doesXZShowFog((int) var3.posX, (int) var3.posZ))
					{
						GL11.glFogf(GL11.GL_FOG_START, var6 * 0.05F);
						GL11.glFogf(GL11.GL_FOG_END, Math.min(var6, 192.0F) * 0.5F);
					}
				}
			}
			GL11.glEnable(GL11.GL_COLOR_MATERIAL);
			GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT);
		}
	}
	
	public void setupOverlayRendering()
	{
		ScaledResolution var1 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, var1.getScaledWidth_double(), var1.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
	}
	
	private void setupViewBobbing(float par1)
	{
		if(mc.renderViewEntity instanceof EntityPlayer)
		{
			EntityPlayer var2 = (EntityPlayer) mc.renderViewEntity;
			float var3 = var2.distanceWalkedModified - var2.prevDistanceWalkedModified;
			float var4 = -(var2.distanceWalkedModified + var3 * par1);
			float var5 = var2.prevCameraYaw + (var2.cameraYaw - var2.prevCameraYaw) * par1;
			float var6 = var2.prevCameraPitch + (var2.cameraPitch - var2.prevCameraPitch) * par1;
			GL11.glTranslatef(MathHelper.sin(var4 * (float) Math.PI) * var5 * 0.5F, -Math.abs(MathHelper.cos(var4 * (float) Math.PI) * var5), 0.0F);
			GL11.glRotatef(MathHelper.sin(var4 * (float) Math.PI) * var5 * 3.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(Math.abs(MathHelper.cos(var4 * (float) Math.PI - 0.2F) * var5) * 5.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(var6, 1.0F, 0.0F, 0.0F);
		}
	}
	
	public void updateCameraAndRender(float par1)
	{
		mc.mcProfiler.startSection("lightTex");
		if(lightmapUpdateNeeded)
		{
			updateLightmap(par1);
		}
		mc.mcProfiler.endSection();
		boolean var2 = Display.isActive();
		if(!var2 && mc.gameSettings.pauseOnLostFocus && (!mc.gameSettings.touchscreen || !Mouse.isButtonDown(1)))
		{
			if(Minecraft.getSystemTime() - prevFrameTime > 500L)
			{
				mc.displayInGameMenu();
			}
		} else
		{
			prevFrameTime = Minecraft.getSystemTime();
		}
		mc.mcProfiler.startSection("mouse");
		if(mc.inGameHasFocus && var2)
		{
			mc.mouseHelper.mouseXYChange();
			float var3 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
			float var4 = var3 * var3 * var3 * 8.0F;
			float var5 = mc.mouseHelper.deltaX * var4;
			float var6 = mc.mouseHelper.deltaY * var4;
			byte var7 = 1;
			if(mc.gameSettings.invertMouse)
			{
				var7 = -1;
			}
			if(mc.gameSettings.smoothCamera)
			{
				smoothCamYaw += var5;
				smoothCamPitch += var6;
				float var8 = par1 - smoothCamPartialTicks;
				smoothCamPartialTicks = par1;
				var5 = smoothCamFilterX * var8;
				var6 = smoothCamFilterY * var8;
				mc.thePlayer.setAngles(var5, var6 * var7);
			} else
			{
				mc.thePlayer.setAngles(var5, var6 * var7);
			}
		}
		mc.mcProfiler.endSection();
		if(!mc.skipRenderWorld)
		{
			anaglyphEnable = mc.gameSettings.anaglyph;
			ScaledResolution var13 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			int var14 = var13.getScaledWidth();
			int var15 = var13.getScaledHeight();
			int var16 = Mouse.getX() * var14 / mc.displayWidth;
			int var17 = var15 - Mouse.getY() * var15 / mc.displayHeight - 1;
			int var18 = performanceToFps(mc.gameSettings.limitFramerate);
			if(mc.theWorld != null)
			{
				mc.mcProfiler.startSection("level");
				if(mc.gameSettings.limitFramerate == 0)
				{
					renderWorld(par1, 0L);
				} else
				{
					renderWorld(par1, renderEndNanoTime + 1000000000 / var18);
				}
				renderEndNanoTime = System.nanoTime();
				mc.mcProfiler.endStartSection("gui");
				if(!mc.gameSettings.hideGUI || mc.currentScreen != null)
				{
					mc.ingameGUI.renderGameOverlay(par1, mc.currentScreen != null, var16, var17);
				}
				mc.mcProfiler.endSection();
			} else
			{
				GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();
				setupOverlayRendering();
				renderEndNanoTime = System.nanoTime();
			}
			if(mc.currentScreen != null)
			{
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
				try
				{
					mc.currentScreen.drawScreen(var16, var17, par1);
				} catch(Throwable var12)
				{
					CrashReport var10 = CrashReport.makeCrashReport(var12, "Rendering screen");
					CrashReportCategory var11 = var10.makeCategory("Screen render details");
					var11.addCrashSectionCallable("Screen name", new CallableScreenName(this));
					var11.addCrashSectionCallable("Mouse location", new CallableMouseLocation(this, var16, var17));
					var11.addCrashSectionCallable("Screen size", new CallableScreenSize(this, var13));
					throw new ReportedException(var10);
				}
				if(mc.currentScreen != null && mc.currentScreen.guiParticles != null)
				{
					mc.currentScreen.guiParticles.draw(par1);
				}
			}
		}
	}
	
	private void updateFogColor(float par1)
	{
		WorldClient var2 = mc.theWorld;
		EntityLiving var3 = mc.renderViewEntity;
		float var4 = 1.0F / (4 - mc.gameSettings.renderDistance);
		var4 = 1.0F - (float) Math.pow(var4, 0.25D);
		Vec3 var5 = var2.getSkyColor(mc.renderViewEntity, par1);
		float var6 = (float) var5.xCoord;
		float var7 = (float) var5.yCoord;
		float var8 = (float) var5.zCoord;
		Vec3 var9 = var2.getFogColor(par1);
		fogColorRed = (float) var9.xCoord;
		fogColorGreen = (float) var9.yCoord;
		fogColorBlue = (float) var9.zCoord;
		float var11;
		if(mc.gameSettings.renderDistance < 2)
		{
			Vec3 var10 = MathHelper.sin(var2.getCelestialAngleRadians(par1)) > 0.0F ? var2.getWorldVec3Pool().getVecFromPool(-1.0D, 0.0D, 0.0D) : var2.getWorldVec3Pool().getVecFromPool(1.0D, 0.0D, 0.0D);
			var11 = (float) var3.getLook(par1).dotProduct(var10);
			if(var11 < 0.0F)
			{
				var11 = 0.0F;
			}
			if(var11 > 0.0F)
			{
				float[] var12 = var2.provider.calcSunriseSunsetColors(var2.getCelestialAngle(par1), par1);
				if(var12 != null)
				{
					var11 *= var12[3];
					fogColorRed = fogColorRed * (1.0F - var11) + var12[0] * var11;
					fogColorGreen = fogColorGreen * (1.0F - var11) + var12[1] * var11;
					fogColorBlue = fogColorBlue * (1.0F - var11) + var12[2] * var11;
				}
			}
		}
		fogColorRed += (var6 - fogColorRed) * var4;
		fogColorGreen += (var7 - fogColorGreen) * var4;
		fogColorBlue += (var8 - fogColorBlue) * var4;
		float var19 = var2.getRainStrength(par1);
		float var20;
		if(var19 > 0.0F)
		{
			var11 = 1.0F - var19 * 0.5F;
			var20 = 1.0F - var19 * 0.4F;
			fogColorRed *= var11;
			fogColorGreen *= var11;
			fogColorBlue *= var20;
		}
		var11 = var2.getWeightedThunderStrength(par1);
		if(var11 > 0.0F)
		{
			var20 = 1.0F - var11 * 0.5F;
			fogColorRed *= var20;
			fogColorGreen *= var20;
			fogColorBlue *= var20;
		}
		int var21 = ActiveRenderInfo.getBlockIdAtEntityViewpoint(mc.theWorld, var3, par1);
		if(cloudFog)
		{
			Vec3 var13 = var2.getCloudColour(par1);
			fogColorRed = (float) var13.xCoord;
			fogColorGreen = (float) var13.yCoord;
			fogColorBlue = (float) var13.zCoord;
		} else if(var21 != 0 && Block.blocksList[var21].blockMaterial == Material.water)
		{
			fogColorRed = 0.02F;
			fogColorGreen = 0.02F;
			fogColorBlue = 0.2F;
		} else if(var21 != 0 && Block.blocksList[var21].blockMaterial == Material.lava)
		{
			fogColorRed = 0.6F;
			fogColorGreen = 0.1F;
			fogColorBlue = 0.0F;
		}
		float var22 = fogColor2 + (fogColor1 - fogColor2) * par1;
		fogColorRed *= var22;
		fogColorGreen *= var22;
		fogColorBlue *= var22;
		double var14 = (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * par1) * var2.provider.getVoidFogYFactor();
		if(var3.isPotionActive(Potion.blindness))
		{
			int var16 = var3.getActivePotionEffect(Potion.blindness).getDuration();
			if(var16 < 20)
			{
				var14 *= 1.0F - var16 / 20.0F;
			} else
			{
				var14 = 0.0D;
			}
		}
		if(var14 < 1.0D)
		{
			if(var14 < 0.0D)
			{
				var14 = 0.0D;
			}
			var14 *= var14;
			fogColorRed = (float) (fogColorRed * var14);
			fogColorGreen = (float) (fogColorGreen * var14);
			fogColorBlue = (float) (fogColorBlue * var14);
		}
		float var23;
		if(field_82831_U > 0.0F)
		{
			var23 = field_82832_V + (field_82831_U - field_82832_V) * par1;
			fogColorRed = fogColorRed * (1.0F - var23) + fogColorRed * 0.7F * var23;
			fogColorGreen = fogColorGreen * (1.0F - var23) + fogColorGreen * 0.6F * var23;
			fogColorBlue = fogColorBlue * (1.0F - var23) + fogColorBlue * 0.6F * var23;
		}
		float var17;
		if(var3.isPotionActive(Potion.nightVision))
		{
			var23 = getNightVisionBrightness(mc.thePlayer, par1);
			var17 = 1.0F / fogColorRed;
			if(var17 > 1.0F / fogColorGreen)
			{
				var17 = 1.0F / fogColorGreen;
			}
			if(var17 > 1.0F / fogColorBlue)
			{
				var17 = 1.0F / fogColorBlue;
			}
			fogColorRed = fogColorRed * (1.0F - var23) + fogColorRed * var17 * var23;
			fogColorGreen = fogColorGreen * (1.0F - var23) + fogColorGreen * var17 * var23;
			fogColorBlue = fogColorBlue * (1.0F - var23) + fogColorBlue * var17 * var23;
		}
		if(mc.gameSettings.anaglyph)
		{
			var23 = (fogColorRed * 30.0F + fogColorGreen * 59.0F + fogColorBlue * 11.0F) / 100.0F;
			var17 = (fogColorRed * 30.0F + fogColorGreen * 70.0F) / 100.0F;
			float var18 = (fogColorRed * 30.0F + fogColorBlue * 70.0F) / 100.0F;
			fogColorRed = var23;
			fogColorGreen = var17;
			fogColorBlue = var18;
		}
		GL11.glClearColor(fogColorRed, fogColorGreen, fogColorBlue, 0.0F);
	}
	
	private void updateFovModifierHand()
	{
		EntityPlayerSP var1 = (EntityPlayerSP) mc.renderViewEntity;
		fovMultiplierTemp = var1.getFOVMultiplier();
		fovModifierHandPrev = fovModifierHand;
		fovModifierHand += (fovMultiplierTemp - fovModifierHand) * 0.5F;
		if(fovModifierHand > 1.5F)
		{
			fovModifierHand = 1.5F;
		}
		if(fovModifierHand < 0.1F)
		{
			fovModifierHand = 0.1F;
		}
	}
	
	private void updateLightmap(float par1)
	{
		WorldClient var2 = mc.theWorld;
		if(var2 != null)
		{
			for(int var3 = 0; var3 < 256; ++var3)
			{
				float var4 = var2.getSunBrightness(1.0F) * 0.95F + 0.05F;
				float var5 = var2.provider.lightBrightnessTable[var3 / 16] * var4;
				float var6 = var2.provider.lightBrightnessTable[var3 % 16] * (torchFlickerX * 0.1F + 1.5F);
				if(var2.lastLightningBolt > 0)
				{
					var5 = var2.provider.lightBrightnessTable[var3 / 16];
				}
				float var7 = var5 * (var2.getSunBrightness(1.0F) * 0.65F + 0.35F);
				float var8 = var5 * (var2.getSunBrightness(1.0F) * 0.65F + 0.35F);
				float var11 = var6 * ((var6 * 0.6F + 0.4F) * 0.6F + 0.4F);
				float var12 = var6 * (var6 * var6 * 0.6F + 0.4F);
				float var13 = var7 + var6;
				float var14 = var8 + var11;
				float var15 = var5 + var12;
				var13 = var13 * 0.96F + 0.03F;
				var14 = var14 * 0.96F + 0.03F;
				var15 = var15 * 0.96F + 0.03F;
				float var16;
				if(field_82831_U > 0.0F)
				{
					var16 = field_82832_V + (field_82831_U - field_82832_V) * par1;
					var13 = var13 * (1.0F - var16) + var13 * 0.7F * var16;
					var14 = var14 * (1.0F - var16) + var14 * 0.6F * var16;
					var15 = var15 * (1.0F - var16) + var15 * 0.6F * var16;
				}
				if(var2.provider.dimensionId == 1)
				{
					var13 = 0.22F + var6 * 0.75F;
					var14 = 0.28F + var11 * 0.75F;
					var15 = 0.25F + var12 * 0.75F;
				}
				float var17;
				if(mc.thePlayer.isPotionActive(Potion.nightVision))
				{
					var16 = getNightVisionBrightness(mc.thePlayer, par1);
					var17 = 1.0F / var13;
					if(var17 > 1.0F / var14)
					{
						var17 = 1.0F / var14;
					}
					if(var17 > 1.0F / var15)
					{
						var17 = 1.0F / var15;
					}
					var13 = var13 * (1.0F - var16) + var13 * var17 * var16;
					var14 = var14 * (1.0F - var16) + var14 * var17 * var16;
					var15 = var15 * (1.0F - var16) + var15 * var17 * var16;
				}
				if(var13 > 1.0F)
				{
					var13 = 1.0F;
				}
				if(var14 > 1.0F)
				{
					var14 = 1.0F;
				}
				if(var15 > 1.0F)
				{
					var15 = 1.0F;
				}
				var16 = mc.gameSettings.gammaSetting;
				var17 = 1.0F - var13;
				float var18 = 1.0F - var14;
				float var19 = 1.0F - var15;
				var17 = 1.0F - var17 * var17 * var17 * var17;
				var18 = 1.0F - var18 * var18 * var18 * var18;
				var19 = 1.0F - var19 * var19 * var19 * var19;
				var13 = var13 * (1.0F - var16) + var17 * var16;
				var14 = var14 * (1.0F - var16) + var18 * var16;
				var15 = var15 * (1.0F - var16) + var19 * var16;
				var13 = var13 * 0.96F + 0.03F;
				var14 = var14 * 0.96F + 0.03F;
				var15 = var15 * 0.96F + 0.03F;
				if(var13 > 1.0F)
				{
					var13 = 1.0F;
				}
				if(var14 > 1.0F)
				{
					var14 = 1.0F;
				}
				if(var15 > 1.0F)
				{
					var15 = 1.0F;
				}
				if(var13 < 0.0F)
				{
					var13 = 0.0F;
				}
				if(var14 < 0.0F)
				{
					var14 = 0.0F;
				}
				if(var15 < 0.0F)
				{
					var15 = 0.0F;
				}
				short var20 = 255;
				int var21 = (int) (var13 * 255.0F);
				int var22 = (int) (var14 * 255.0F);
				int var23 = (int) (var15 * 255.0F);
				lightmapColors[var3] = var20 << 24 | var21 << 16 | var22 << 8 | var23;
			}
			mc.renderEngine.createTextureFromBytes(lightmapColors, 16, 16, lightmapTexture);
		}
	}
	
	public void updateRenderer()
	{
		updateFovModifierHand();
		updateTorchFlicker();
		fogColor2 = fogColor1;
		thirdPersonDistanceTemp = thirdPersonDistance;
		prevDebugCamYaw = debugCamYaw;
		prevDebugCamPitch = debugCamPitch;
		prevDebugCamFOV = debugCamFOV;
		prevCamRoll = camRoll;
		float var1;
		float var2;
		if(mc.gameSettings.smoothCamera)
		{
			var1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
			var2 = var1 * var1 * var1 * 8.0F;
			smoothCamFilterX = mouseFilterXAxis.smooth(smoothCamYaw, 0.05F * var2);
			smoothCamFilterY = mouseFilterYAxis.smooth(smoothCamPitch, 0.05F * var2);
			smoothCamPartialTicks = 0.0F;
			smoothCamYaw = 0.0F;
			smoothCamPitch = 0.0F;
		}
		if(mc.renderViewEntity == null)
		{
			mc.renderViewEntity = mc.thePlayer;
		}
		var1 = mc.theWorld.getLightBrightness(MathHelper.floor_double(mc.renderViewEntity.posX), MathHelper.floor_double(mc.renderViewEntity.posY), MathHelper.floor_double(mc.renderViewEntity.posZ));
		var2 = (3 - mc.gameSettings.renderDistance) / 3.0F;
		float var3 = var1 * (1.0F - var2) + var2;
		fogColor1 += (var3 - fogColor1) * 0.1F;
		++rendererUpdateCount;
		itemRenderer.updateEquippedItem();
		addRainParticles();
		field_82832_V = field_82831_U;
		if(BossStatus.field_82825_d)
		{
			field_82831_U += 0.05F;
			if(field_82831_U > 1.0F)
			{
				field_82831_U = 1.0F;
			}
			BossStatus.field_82825_d = false;
		} else if(field_82831_U > 0.0F)
		{
			field_82831_U -= 0.0125F;
		}
	}
	
	private void updateTorchFlicker()
	{
		torchFlickerDX = (float) (torchFlickerDX + (Math.random() - Math.random()) * Math.random() * Math.random());
		torchFlickerDY = (float) (torchFlickerDY + (Math.random() - Math.random()) * Math.random() * Math.random());
		torchFlickerDX = (float) (torchFlickerDX * 0.9D);
		torchFlickerDY = (float) (torchFlickerDY * 0.9D);
		torchFlickerX += (torchFlickerDX - torchFlickerX) * 1.0F;
		torchFlickerY += (torchFlickerDY - torchFlickerY) * 1.0F;
		lightmapUpdateNeeded = true;
	}
	
	static Minecraft getRendererMinecraft(EntityRenderer par0EntityRenderer)
	{
		return par0EntityRenderer.mc;
	}
	
	public static int performanceToFps(int par0)
	{
		short var1 = 200;
		if(par0 == 1)
		{
			var1 = 120;
		}
		if(par0 == 2)
		{
			var1 = 35;
		}
		return var1;
	}
}
