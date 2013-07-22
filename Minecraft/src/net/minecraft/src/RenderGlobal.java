package net.minecraft.src;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.client.Minecraft;

public class RenderGlobal implements IWorldAccess
{
	private static final ResourceLocation field_110927_h = new ResourceLocation("textures/environment/moon_phases.png");
	private static final ResourceLocation field_110928_i = new ResourceLocation("textures/environment/sun.png");
	private static final ResourceLocation field_110925_j = new ResourceLocation("textures/environment/clouds.png");
	private static final ResourceLocation field_110926_k = new ResourceLocation("textures/environment/end_sky.png");
	public List tileEntities = new ArrayList();
	private WorldClient theWorld;
	private final TextureManager renderEngine;
	private List worldRenderersToUpdate = new ArrayList();
	private WorldRenderer[] sortedWorldRenderers;
	private WorldRenderer[] worldRenderers;
	private int renderChunksWide;
	private int renderChunksTall;
	private int renderChunksDeep;
	private int glRenderListBase;
	private Minecraft mc;
	private RenderBlocks globalRenderBlocks;
	private IntBuffer glOcclusionQueryBase;
	private boolean occlusionEnabled;
	private int cloudTickCounter;
	private int starGLCallList;
	private int glSkyList;
	private int glSkyList2;
	private int minBlockX;
	private int minBlockY;
	private int minBlockZ;
	private int maxBlockX;
	private int maxBlockY;
	private int maxBlockZ;
	private Map damagedBlocks = new HashMap();
	private Icon[] destroyBlockIcons;
	private int renderDistance = -1;
	private int renderEntitiesStartupCounter = 2;
	private int countEntitiesTotal;
	private int countEntitiesRendered;
	private int countEntitiesHidden;
	IntBuffer occlusionResult = GLAllocation.createDirectIntBuffer(64);
	private int renderersLoaded;
	private int renderersBeingClipped;
	private int renderersBeingOccluded;
	private int renderersBeingRendered;
	private int renderersSkippingRenderPass;
	private int dummyRenderInt;
	private int worldRenderersCheckIndex;
	private List glRenderLists = new ArrayList();
	private RenderList[] allRenderLists = new RenderList[] { new RenderList(), new RenderList(), new RenderList(), new RenderList() };
	double prevSortX = -9999.0D;
	double prevSortY = -9999.0D;
	double prevSortZ = -9999.0D;
	int frustumCheckOffset;
	
	public RenderGlobal(Minecraft par1Minecraft)
	{
		mc = par1Minecraft;
		renderEngine = par1Minecraft.func_110434_K();
		byte var2 = 34;
		byte var3 = 32;
		glRenderListBase = GLAllocation.generateDisplayLists(var2 * var2 * var3 * 3);
		occlusionEnabled = OpenGlCapsChecker.checkARBOcclusion();
		if(occlusionEnabled)
		{
			occlusionResult.clear();
			glOcclusionQueryBase = GLAllocation.createDirectIntBuffer(var2 * var2 * var3);
			glOcclusionQueryBase.clear();
			glOcclusionQueryBase.position(0);
			glOcclusionQueryBase.limit(var2 * var2 * var3);
			ARBOcclusionQuery.glGenQueriesARB(glOcclusionQueryBase);
		}
		starGLCallList = GLAllocation.generateDisplayLists(3);
		GL11.glPushMatrix();
		GL11.glNewList(starGLCallList, GL11.GL_COMPILE);
		renderStars();
		GL11.glEndList();
		GL11.glPopMatrix();
		Tessellator var4 = Tessellator.instance;
		glSkyList = starGLCallList + 1;
		GL11.glNewList(glSkyList, GL11.GL_COMPILE);
		byte var6 = 64;
		int var7 = 256 / var6 + 2;
		float var5 = 16.0F;
		int var8;
		int var9;
		for(var8 = -var6 * var7; var8 <= var6 * var7; var8 += var6)
		{
			for(var9 = -var6 * var7; var9 <= var6 * var7; var9 += var6)
			{
				var4.startDrawingQuads();
				var4.addVertex(var8 + 0, var5, var9 + 0);
				var4.addVertex(var8 + var6, var5, var9 + 0);
				var4.addVertex(var8 + var6, var5, var9 + var6);
				var4.addVertex(var8 + 0, var5, var9 + var6);
				var4.draw();
			}
		}
		GL11.glEndList();
		glSkyList2 = starGLCallList + 2;
		GL11.glNewList(glSkyList2, GL11.GL_COMPILE);
		var5 = -16.0F;
		var4.startDrawingQuads();
		for(var8 = -var6 * var7; var8 <= var6 * var7; var8 += var6)
		{
			for(var9 = -var6 * var7; var9 <= var6 * var7; var9 += var6)
			{
				var4.addVertex(var8 + var6, var5, var9 + 0);
				var4.addVertex(var8 + 0, var5, var9 + 0);
				var4.addVertex(var8 + 0, var5, var9 + var6);
				var4.addVertex(var8 + var6, var5, var9 + var6);
			}
		}
		var4.draw();
		GL11.glEndList();
	}
	
	@Override public void broadcastSound(int par1, int par2, int par3, int par4, int par5)
	{
		Random var6 = theWorld.rand;
		switch(par1)
		{
			case 1013:
			case 1018:
				if(mc.renderViewEntity != null)
				{
					double var7 = par2 - mc.renderViewEntity.posX;
					double var9 = par3 - mc.renderViewEntity.posY;
					double var11 = par4 - mc.renderViewEntity.posZ;
					double var13 = Math.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
					double var15 = mc.renderViewEntity.posX;
					double var17 = mc.renderViewEntity.posY;
					double var19 = mc.renderViewEntity.posZ;
					if(var13 > 0.0D)
					{
						var15 += var7 / var13 * 2.0D;
						var17 += var9 / var13 * 2.0D;
						var19 += var11 / var13 * 2.0D;
					}
					if(par1 == 1013)
					{
						theWorld.playSound(var15, var17, var19, "mob.wither.spawn", 1.0F, 1.0F, false);
					} else if(par1 == 1018)
					{
						theWorld.playSound(var15, var17, var19, "mob.enderdragon.end", 5.0F, 1.0F, false);
					}
				}
			default:
		}
	}
	
	private void checkOcclusionQueryResult(int par1, int par2)
	{
		for(int var3 = par1; var3 < par2; ++var3)
		{
			if(sortedWorldRenderers[var3].isWaitingOnOcclusionQuery)
			{
				occlusionResult.clear();
				ARBOcclusionQuery.glGetQueryObjectuARB(sortedWorldRenderers[var3].glOcclusionQuery, ARBOcclusionQuery.GL_QUERY_RESULT_AVAILABLE_ARB, occlusionResult);
				if(occlusionResult.get(0) != 0)
				{
					sortedWorldRenderers[var3].isWaitingOnOcclusionQuery = false;
					occlusionResult.clear();
					ARBOcclusionQuery.glGetQueryObjectuARB(sortedWorldRenderers[var3].glOcclusionQuery, ARBOcclusionQuery.GL_QUERY_RESULT_ARB, occlusionResult);
					sortedWorldRenderers[var3].isVisible = occlusionResult.get(0) != 0;
				}
			}
		}
	}
	
	public void clipRenderersByFrustum(ICamera par1ICamera, float par2)
	{
		for(int var3 = 0; var3 < worldRenderers.length; ++var3)
		{
			if(!worldRenderers[var3].skipAllRenderPasses() && (!worldRenderers[var3].isInFrustum || (var3 + frustumCheckOffset & 15) == 0))
			{
				worldRenderers[var3].updateInFrustum(par1ICamera);
			}
		}
		++frustumCheckOffset;
	}
	
	public void deleteAllDisplayLists()
	{
		GLAllocation.deleteDisplayLists(glRenderListBase);
	}
	
	@Override public void destroyBlockPartially(int par1, int par2, int par3, int par4, int par5)
	{
		if(par5 >= 0 && par5 < 10)
		{
			DestroyBlockProgress var6 = (DestroyBlockProgress) damagedBlocks.get(Integer.valueOf(par1));
			if(var6 == null || var6.getPartialBlockX() != par2 || var6.getPartialBlockY() != par3 || var6.getPartialBlockZ() != par4)
			{
				var6 = new DestroyBlockProgress(par1, par2, par3, par4);
				damagedBlocks.put(Integer.valueOf(par1), var6);
			}
			var6.setPartialBlockDamage(par5);
			var6.setCloudUpdateTick(cloudTickCounter);
		} else
		{
			damagedBlocks.remove(Integer.valueOf(par1));
		}
	}
	
	public EntityFX doSpawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		if(mc != null && mc.renderViewEntity != null && mc.effectRenderer != null)
		{
			int var14 = mc.gameSettings.particleSetting;
			if(var14 == 1 && theWorld.rand.nextInt(3) == 0)
			{
				var14 = 2;
			}
			double var15 = mc.renderViewEntity.posX - par2;
			double var17 = mc.renderViewEntity.posY - par4;
			double var19 = mc.renderViewEntity.posZ - par6;
			EntityFX var21 = null;
			if(par1Str.equals("hugeexplosion"))
			{
				mc.effectRenderer.addEffect(var21 = new EntityHugeExplodeFX(theWorld, par2, par4, par6, par8, par10, par12));
			} else if(par1Str.equals("largeexplode"))
			{
				mc.effectRenderer.addEffect(var21 = new EntityLargeExplodeFX(renderEngine, theWorld, par2, par4, par6, par8, par10, par12));
			} else if(par1Str.equals("fireworksSpark"))
			{
				mc.effectRenderer.addEffect(var21 = new EntityFireworkSparkFX(theWorld, par2, par4, par6, par8, par10, par12, mc.effectRenderer));
			}
			if(var21 != null) return var21;
			else
			{
				double var22 = 16.0D;
				if(var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22) return null;
				else if(var14 > 1) return null;
				else
				{
					if(par1Str.equals("bubble"))
					{
						var21 = new EntityBubbleFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("suspended"))
					{
						var21 = new EntitySuspendFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("depthsuspend"))
					{
						var21 = new EntityAuraFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("townaura"))
					{
						var21 = new EntityAuraFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("crit"))
					{
						var21 = new EntityCritFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("magicCrit"))
					{
						var21 = new EntityCritFX(theWorld, par2, par4, par6, par8, par10, par12);
						var21.setRBGColorF(var21.getRedColorF() * 0.3F, var21.getGreenColorF() * 0.8F, var21.getBlueColorF());
						var21.nextTextureIndexX();
					} else if(par1Str.equals("smoke"))
					{
						var21 = new EntitySmokeFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("mobSpell"))
					{
						var21 = new EntitySpellParticleFX(theWorld, par2, par4, par6, 0.0D, 0.0D, 0.0D);
						var21.setRBGColorF((float) par8, (float) par10, (float) par12);
					} else if(par1Str.equals("mobSpellAmbient"))
					{
						var21 = new EntitySpellParticleFX(theWorld, par2, par4, par6, 0.0D, 0.0D, 0.0D);
						var21.setAlphaF(0.15F);
						var21.setRBGColorF((float) par8, (float) par10, (float) par12);
					} else if(par1Str.equals("spell"))
					{
						var21 = new EntitySpellParticleFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("instantSpell"))
					{
						var21 = new EntitySpellParticleFX(theWorld, par2, par4, par6, par8, par10, par12);
						((EntitySpellParticleFX) var21).setBaseSpellTextureIndex(144);
					} else if(par1Str.equals("witchMagic"))
					{
						var21 = new EntitySpellParticleFX(theWorld, par2, par4, par6, par8, par10, par12);
						((EntitySpellParticleFX) var21).setBaseSpellTextureIndex(144);
						float var24 = theWorld.rand.nextFloat() * 0.5F + 0.35F;
						var21.setRBGColorF(1.0F * var24, 0.0F * var24, 1.0F * var24);
					} else if(par1Str.equals("note"))
					{
						var21 = new EntityNoteFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("portal"))
					{
						var21 = new EntityPortalFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("enchantmenttable"))
					{
						var21 = new EntityEnchantmentTableParticleFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("explode"))
					{
						var21 = new EntityExplodeFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("flame"))
					{
						var21 = new EntityFlameFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("lava"))
					{
						var21 = new EntityLavaFX(theWorld, par2, par4, par6);
					} else if(par1Str.equals("footstep"))
					{
						var21 = new EntityFootStepFX(renderEngine, theWorld, par2, par4, par6);
					} else if(par1Str.equals("splash"))
					{
						var21 = new EntitySplashFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("largesmoke"))
					{
						var21 = new EntitySmokeFX(theWorld, par2, par4, par6, par8, par10, par12, 2.5F);
					} else if(par1Str.equals("cloud"))
					{
						var21 = new EntityCloudFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("reddust"))
					{
						var21 = new EntityReddustFX(theWorld, par2, par4, par6, (float) par8, (float) par10, (float) par12);
					} else if(par1Str.equals("snowballpoof"))
					{
						var21 = new EntityBreakingFX(theWorld, par2, par4, par6, Item.snowball);
					} else if(par1Str.equals("dripWater"))
					{
						var21 = new EntityDropParticleFX(theWorld, par2, par4, par6, Material.water);
					} else if(par1Str.equals("dripLava"))
					{
						var21 = new EntityDropParticleFX(theWorld, par2, par4, par6, Material.lava);
					} else if(par1Str.equals("snowshovel"))
					{
						var21 = new EntitySnowShovelFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("slime"))
					{
						var21 = new EntityBreakingFX(theWorld, par2, par4, par6, Item.slimeBall);
					} else if(par1Str.equals("heart"))
					{
						var21 = new EntityHeartFX(theWorld, par2, par4, par6, par8, par10, par12);
					} else if(par1Str.equals("angryVillager"))
					{
						var21 = new EntityHeartFX(theWorld, par2, par4 + 0.5D, par6, par8, par10, par12);
						var21.setParticleTextureIndex(81);
						var21.setRBGColorF(1.0F, 1.0F, 1.0F);
					} else if(par1Str.equals("happyVillager"))
					{
						var21 = new EntityAuraFX(theWorld, par2, par4, par6, par8, par10, par12);
						var21.setParticleTextureIndex(82);
						var21.setRBGColorF(1.0F, 1.0F, 1.0F);
					} else
					{
						int var25;
						String[] var27;
						int var26;
						if(par1Str.startsWith("iconcrack_"))
						{
							var27 = par1Str.split("_", 3);
							var25 = Integer.parseInt(var27[1]);
							if(var27.length > 2)
							{
								var26 = Integer.parseInt(var27[2]);
								var21 = new EntityBreakingFX(theWorld, par2, par4, par6, par8, par10, par12, Item.itemsList[var25], var26);
							} else
							{
								var21 = new EntityBreakingFX(theWorld, par2, par4, par6, par8, par10, par12, Item.itemsList[var25], 0);
							}
						} else if(par1Str.startsWith("tilecrack_"))
						{
							var27 = par1Str.split("_", 3);
							var25 = Integer.parseInt(var27[1]);
							var26 = Integer.parseInt(var27[2]);
							var21 = new EntityDiggingFX(theWorld, par2, par4, par6, par8, par10, par12, Block.blocksList[var25], var26).applyRenderColor(var26);
						}
					}
					if(var21 != null)
					{
						mc.effectRenderer.addEffect(var21);
					}
					return var21;
				}
			}
		} else return null;
	}
	
	public void drawBlockDamageTexture(Tessellator par1Tessellator, EntityPlayer par2EntityPlayer, float par3)
	{
		double var4 = par2EntityPlayer.lastTickPosX + (par2EntityPlayer.posX - par2EntityPlayer.lastTickPosX) * par3;
		double var6 = par2EntityPlayer.lastTickPosY + (par2EntityPlayer.posY - par2EntityPlayer.lastTickPosY) * par3;
		double var8 = par2EntityPlayer.lastTickPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.lastTickPosZ) * par3;
		if(!damagedBlocks.isEmpty())
		{
			GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_SRC_COLOR);
			renderEngine.func_110577_a(TextureMap.field_110575_b);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glPolygonOffset(-3.0F, -3.0F);
			GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			par1Tessellator.startDrawingQuads();
			par1Tessellator.setTranslation(-var4, -var6, -var8);
			par1Tessellator.disableColor();
			Iterator var10 = damagedBlocks.values().iterator();
			while(var10.hasNext())
			{
				DestroyBlockProgress var11 = (DestroyBlockProgress) var10.next();
				double var12 = var11.getPartialBlockX() - var4;
				double var14 = var11.getPartialBlockY() - var6;
				double var16 = var11.getPartialBlockZ() - var8;
				if(var12 * var12 + var14 * var14 + var16 * var16 > 1024.0D)
				{
					var10.remove();
				} else
				{
					int var18 = theWorld.getBlockId(var11.getPartialBlockX(), var11.getPartialBlockY(), var11.getPartialBlockZ());
					Block var19 = var18 > 0 ? Block.blocksList[var18] : null;
					if(var19 == null)
					{
						var19 = Block.stone;
					}
					globalRenderBlocks.renderBlockUsingTexture(var19, var11.getPartialBlockX(), var11.getPartialBlockY(), var11.getPartialBlockZ(), destroyBlockIcons[var11.getPartialBlockDamage()]);
				}
			}
			par1Tessellator.draw();
			par1Tessellator.setTranslation(0.0D, 0.0D, 0.0D);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glPolygonOffset(0.0F, 0.0F);
			GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDepthMask(true);
			GL11.glPopMatrix();
		}
	}
	
	private void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB)
	{
		Tessellator var2 = Tessellator.instance;
		var2.startDrawing(3);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();
		var2.startDrawing(3);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.draw();
		var2.startDrawing(1);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.draw();
	}
	
	public void drawSelectionBox(EntityPlayer par1EntityPlayer, MovingObjectPosition par2MovingObjectPosition, int par3, float par4)
	{
		if(par3 == 0 && par2MovingObjectPosition.typeOfHit == EnumMovingObjectType.TILE)
		{
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
			GL11.glLineWidth(2.0F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(false);
			float var5 = 0.002F;
			int var6 = theWorld.getBlockId(par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
			if(var6 > 0)
			{
				Block.blocksList[var6].setBlockBoundsBasedOnState(theWorld, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
				double var7 = par1EntityPlayer.lastTickPosX + (par1EntityPlayer.posX - par1EntityPlayer.lastTickPosX) * par4;
				double var9 = par1EntityPlayer.lastTickPosY + (par1EntityPlayer.posY - par1EntityPlayer.lastTickPosY) * par4;
				double var11 = par1EntityPlayer.lastTickPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.lastTickPosZ) * par4;
				drawOutlinedBoundingBox(Block.blocksList[var6].getSelectedBoundingBoxFromPool(theWorld, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ).expand(var5, var5, var5).getOffsetBoundingBox(-var7, -var9, -var11));
			}
			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}
	
	public String getDebugInfoEntities()
	{
		return "E: " + countEntitiesRendered + "/" + countEntitiesTotal + ". B: " + countEntitiesHidden + ", I: " + (countEntitiesTotal - countEntitiesHidden - countEntitiesRendered);
	}
	
	public String getDebugInfoRenders()
	{
		return "C: " + renderersBeingRendered + "/" + renderersLoaded + ". F: " + renderersBeingClipped + ", O: " + renderersBeingOccluded + ", E: " + renderersSkippingRenderPass;
	}
	
	public boolean hasCloudFog(double par1, double par3, double par5, float par7)
	{
		return false;
	}
	
	public void loadRenderers()
	{
		if(theWorld != null)
		{
			Block.leaves.setGraphicsLevel(mc.gameSettings.fancyGraphics);
			renderDistance = mc.gameSettings.renderDistance;
			int var1;
			if(worldRenderers != null)
			{
				for(var1 = 0; var1 < worldRenderers.length; ++var1)
				{
					worldRenderers[var1].stopRendering();
				}
			}
			var1 = 64 << 3 - renderDistance;
			if(var1 > 400)
			{
				var1 = 400;
			}
			renderChunksWide = var1 / 16 + 1;
			renderChunksTall = 16;
			renderChunksDeep = var1 / 16 + 1;
			worldRenderers = new WorldRenderer[renderChunksWide * renderChunksTall * renderChunksDeep];
			sortedWorldRenderers = new WorldRenderer[renderChunksWide * renderChunksTall * renderChunksDeep];
			int var2 = 0;
			int var3 = 0;
			minBlockX = 0;
			minBlockY = 0;
			minBlockZ = 0;
			maxBlockX = renderChunksWide;
			maxBlockY = renderChunksTall;
			maxBlockZ = renderChunksDeep;
			int var4;
			for(var4 = 0; var4 < worldRenderersToUpdate.size(); ++var4)
			{
				((WorldRenderer) worldRenderersToUpdate.get(var4)).needsUpdate = false;
			}
			worldRenderersToUpdate.clear();
			tileEntities.clear();
			for(var4 = 0; var4 < renderChunksWide; ++var4)
			{
				for(int var5 = 0; var5 < renderChunksTall; ++var5)
				{
					for(int var6 = 0; var6 < renderChunksDeep; ++var6)
					{
						worldRenderers[(var6 * renderChunksTall + var5) * renderChunksWide + var4] = new WorldRenderer(theWorld, tileEntities, var4 * 16, var5 * 16, var6 * 16, glRenderListBase + var2);
						if(occlusionEnabled)
						{
							worldRenderers[(var6 * renderChunksTall + var5) * renderChunksWide + var4].glOcclusionQuery = glOcclusionQueryBase.get(var3);
						}
						worldRenderers[(var6 * renderChunksTall + var5) * renderChunksWide + var4].isWaitingOnOcclusionQuery = false;
						worldRenderers[(var6 * renderChunksTall + var5) * renderChunksWide + var4].isVisible = true;
						worldRenderers[(var6 * renderChunksTall + var5) * renderChunksWide + var4].isInFrustum = true;
						worldRenderers[(var6 * renderChunksTall + var5) * renderChunksWide + var4].chunkIndex = var3++;
						worldRenderers[(var6 * renderChunksTall + var5) * renderChunksWide + var4].markDirty();
						sortedWorldRenderers[(var6 * renderChunksTall + var5) * renderChunksWide + var4] = worldRenderers[(var6 * renderChunksTall + var5) * renderChunksWide + var4];
						worldRenderersToUpdate.add(worldRenderers[(var6 * renderChunksTall + var5) * renderChunksWide + var4]);
						var2 += 3;
					}
				}
			}
			if(theWorld != null)
			{
				EntityLivingBase var7 = mc.renderViewEntity;
				if(var7 != null)
				{
					markRenderersForNewPosition(MathHelper.floor_double(var7.posX), MathHelper.floor_double(var7.posY), MathHelper.floor_double(var7.posZ));
					Arrays.sort(sortedWorldRenderers, new EntitySorter(var7));
				}
			}
			renderEntitiesStartupCounter = 2;
		}
	}
	
	@Override public void markBlockForRenderUpdate(int par1, int par2, int par3)
	{
		markBlocksForUpdate(par1 - 1, par2 - 1, par3 - 1, par1 + 1, par2 + 1, par3 + 1);
	}
	
	@Override public void markBlockForUpdate(int par1, int par2, int par3)
	{
		markBlocksForUpdate(par1 - 1, par2 - 1, par3 - 1, par1 + 1, par2 + 1, par3 + 1);
	}
	
	@Override public void markBlockRangeForRenderUpdate(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		markBlocksForUpdate(par1 - 1, par2 - 1, par3 - 1, par4 + 1, par5 + 1, par6 + 1);
	}
	
	public void markBlocksForUpdate(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		int var7 = MathHelper.bucketInt(par1, 16);
		int var8 = MathHelper.bucketInt(par2, 16);
		int var9 = MathHelper.bucketInt(par3, 16);
		int var10 = MathHelper.bucketInt(par4, 16);
		int var11 = MathHelper.bucketInt(par5, 16);
		int var12 = MathHelper.bucketInt(par6, 16);
		for(int var13 = var7; var13 <= var10; ++var13)
		{
			int var14 = var13 % renderChunksWide;
			if(var14 < 0)
			{
				var14 += renderChunksWide;
			}
			for(int var15 = var8; var15 <= var11; ++var15)
			{
				int var16 = var15 % renderChunksTall;
				if(var16 < 0)
				{
					var16 += renderChunksTall;
				}
				for(int var17 = var9; var17 <= var12; ++var17)
				{
					int var18 = var17 % renderChunksDeep;
					if(var18 < 0)
					{
						var18 += renderChunksDeep;
					}
					int var19 = (var18 * renderChunksTall + var16) * renderChunksWide + var14;
					WorldRenderer var20 = worldRenderers[var19];
					if(var20 != null && !var20.needsUpdate)
					{
						worldRenderersToUpdate.add(var20);
						var20.markDirty();
					}
				}
			}
		}
	}
	
	private void markRenderersForNewPosition(int par1, int par2, int par3)
	{
		par1 -= 8;
		par2 -= 8;
		par3 -= 8;
		minBlockX = Integer.MAX_VALUE;
		minBlockY = Integer.MAX_VALUE;
		minBlockZ = Integer.MAX_VALUE;
		maxBlockX = Integer.MIN_VALUE;
		maxBlockY = Integer.MIN_VALUE;
		maxBlockZ = Integer.MIN_VALUE;
		int var4 = renderChunksWide * 16;
		int var5 = var4 / 2;
		for(int var6 = 0; var6 < renderChunksWide; ++var6)
		{
			int var7 = var6 * 16;
			int var8 = var7 + var5 - par1;
			if(var8 < 0)
			{
				var8 -= var4 - 1;
			}
			var8 /= var4;
			var7 -= var8 * var4;
			if(var7 < minBlockX)
			{
				minBlockX = var7;
			}
			if(var7 > maxBlockX)
			{
				maxBlockX = var7;
			}
			for(int var9 = 0; var9 < renderChunksDeep; ++var9)
			{
				int var10 = var9 * 16;
				int var11 = var10 + var5 - par3;
				if(var11 < 0)
				{
					var11 -= var4 - 1;
				}
				var11 /= var4;
				var10 -= var11 * var4;
				if(var10 < minBlockZ)
				{
					minBlockZ = var10;
				}
				if(var10 > maxBlockZ)
				{
					maxBlockZ = var10;
				}
				for(int var12 = 0; var12 < renderChunksTall; ++var12)
				{
					int var13 = var12 * 16;
					if(var13 < minBlockY)
					{
						minBlockY = var13;
					}
					if(var13 > maxBlockY)
					{
						maxBlockY = var13;
					}
					WorldRenderer var14 = worldRenderers[(var9 * renderChunksTall + var12) * renderChunksWide + var6];
					boolean var15 = var14.needsUpdate;
					var14.setPosition(var7, var13, var10);
					if(!var15 && var14.needsUpdate)
					{
						worldRenderersToUpdate.add(var14);
					}
				}
			}
		}
	}
	
	@Override public void onEntityCreate(Entity par1Entity)
	{
	}
	
	@Override public void onEntityDestroy(Entity par1Entity)
	{
	}
	
	@Override public void playAuxSFX(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6)
	{
		Random var7 = theWorld.rand;
		double var8;
		double var10;
		double var12;
		String var14;
		int var15;
		int var20;
		double var23;
		double var25;
		double var27;
		double var29;
		double var39;
		switch(par2)
		{
			case 1000:
				theWorld.playSound(par3, par4, par5, "random.click", 1.0F, 1.0F, false);
				break;
			case 1001:
				theWorld.playSound(par3, par4, par5, "random.click", 1.0F, 1.2F, false);
				break;
			case 1002:
				theWorld.playSound(par3, par4, par5, "random.bow", 1.0F, 1.2F, false);
				break;
			case 1003:
				if(Math.random() < 0.5D)
				{
					theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "random.door_open", 1.0F, theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
				} else
				{
					theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "random.door_close", 1.0F, theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
				}
				break;
			case 1004:
				theWorld.playSound(par3 + 0.5F, par4 + 0.5F, par5 + 0.5F, "random.fizz", 0.5F, 2.6F + (var7.nextFloat() - var7.nextFloat()) * 0.8F, false);
				break;
			case 1005:
				if(Item.itemsList[par6] instanceof ItemRecord)
				{
					theWorld.playRecord(((ItemRecord) Item.itemsList[par6]).recordName, par3, par4, par5);
				} else
				{
					theWorld.playRecord((String) null, par3, par4, par5);
				}
				break;
			case 1007:
				theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.ghast.charge", 10.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
				break;
			case 1008:
				theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.ghast.fireball", 10.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
				break;
			case 1009:
				theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.ghast.fireball", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
				break;
			case 1010:
				theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.zombie.wood", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
				break;
			case 1011:
				theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.zombie.metal", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
				break;
			case 1012:
				theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.zombie.woodbreak", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
				break;
			case 1014:
				theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.wither.shoot", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
				break;
			case 1015:
				theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.bat.takeoff", 0.05F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
				break;
			case 1016:
				theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.zombie.infect", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
				break;
			case 1017:
				theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.zombie.unfect", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
				break;
			case 1020:
				theWorld.playSound(par3 + 0.5F, par4 + 0.5F, par5 + 0.5F, "random.anvil_break", 1.0F, theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
				break;
			case 1021:
				theWorld.playSound(par3 + 0.5F, par4 + 0.5F, par5 + 0.5F, "random.anvil_use", 1.0F, theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
				break;
			case 1022:
				theWorld.playSound(par3 + 0.5F, par4 + 0.5F, par5 + 0.5F, "random.anvil_land", 0.3F, theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
				break;
			case 2000:
				int var33 = par6 % 3 - 1;
				int var9 = par6 / 3 % 3 - 1;
				var10 = par3 + var33 * 0.6D + 0.5D;
				var12 = par4 + 0.5D;
				double var34 = par5 + var9 * 0.6D + 0.5D;
				for(int var35 = 0; var35 < 10; ++var35)
				{
					double var37 = var7.nextDouble() * 0.2D + 0.01D;
					double var38 = var10 + var33 * 0.01D + (var7.nextDouble() - 0.5D) * var9 * 0.5D;
					var39 = var12 + (var7.nextDouble() - 0.5D) * 0.5D;
					var23 = var34 + var9 * 0.01D + (var7.nextDouble() - 0.5D) * var33 * 0.5D;
					var25 = var33 * var37 + var7.nextGaussian() * 0.01D;
					var27 = -0.03D + var7.nextGaussian() * 0.01D;
					var29 = var9 * var37 + var7.nextGaussian() * 0.01D;
					spawnParticle("smoke", var38, var39, var23, var25, var27, var29);
				}
				return;
			case 2001:
				var20 = par6 & 4095;
				if(var20 > 0)
				{
					Block var40 = Block.blocksList[var20];
					mc.sndManager.playSound(var40.stepSound.getBreakSound(), par3 + 0.5F, par4 + 0.5F, par5 + 0.5F, (var40.stepSound.getVolume() + 1.0F) / 2.0F, var40.stepSound.getPitch() * 0.8F);
				}
				mc.effectRenderer.addBlockDestroyEffects(par3, par4, par5, par6 & 4095, par6 >> 12 & 255);
				break;
			case 2002:
				var8 = par3;
				var10 = par4;
				var12 = par5;
				var14 = "iconcrack_" + Item.potion.itemID + "_" + par6;
				for(var15 = 0; var15 < 8; ++var15)
				{
					spawnParticle(var14, var8, var10, var12, var7.nextGaussian() * 0.15D, var7.nextDouble() * 0.2D, var7.nextGaussian() * 0.15D);
				}
				var15 = Item.potion.getColorFromDamage(par6);
				float var16 = (var15 >> 16 & 255) / 255.0F;
				float var17 = (var15 >> 8 & 255) / 255.0F;
				float var18 = (var15 >> 0 & 255) / 255.0F;
				String var19 = "spell";
				if(Item.potion.isEffectInstant(par6))
				{
					var19 = "instantSpell";
				}
				for(var20 = 0; var20 < 100; ++var20)
				{
					var39 = var7.nextDouble() * 4.0D;
					var23 = var7.nextDouble() * Math.PI * 2.0D;
					var25 = Math.cos(var23) * var39;
					var27 = 0.01D + var7.nextDouble() * 0.5D;
					var29 = Math.sin(var23) * var39;
					EntityFX var31 = doSpawnParticle(var19, var8 + var25 * 0.1D, var10 + 0.3D, var12 + var29 * 0.1D, var25, var27, var29);
					if(var31 != null)
					{
						float var32 = 0.75F + var7.nextFloat() * 0.25F;
						var31.setRBGColorF(var16 * var32, var17 * var32, var18 * var32);
						var31.multiplyVelocity((float) var39);
					}
				}
				theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "random.glass", 1.0F, theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
				break;
			case 2003:
				var8 = par3 + 0.5D;
				var10 = par4;
				var12 = par5 + 0.5D;
				var14 = "iconcrack_" + Item.eyeOfEnder.itemID;
				for(var15 = 0; var15 < 8; ++var15)
				{
					spawnParticle(var14, var8, var10, var12, var7.nextGaussian() * 0.15D, var7.nextDouble() * 0.2D, var7.nextGaussian() * 0.15D);
				}
				for(double var36 = 0.0D; var36 < Math.PI * 2D; var36 += 0.15707963267948966D)
				{
					spawnParticle("portal", var8 + Math.cos(var36) * 5.0D, var10 - 0.4D, var12 + Math.sin(var36) * 5.0D, Math.cos(var36) * -5.0D, 0.0D, Math.sin(var36) * -5.0D);
					spawnParticle("portal", var8 + Math.cos(var36) * 5.0D, var10 - 0.4D, var12 + Math.sin(var36) * 5.0D, Math.cos(var36) * -7.0D, 0.0D, Math.sin(var36) * -7.0D);
				}
				return;
			case 2004:
				for(int var21 = 0; var21 < 20; ++var21)
				{
					double var22 = par3 + 0.5D + (theWorld.rand.nextFloat() - 0.5D) * 2.0D;
					double var24 = par4 + 0.5D + (theWorld.rand.nextFloat() - 0.5D) * 2.0D;
					double var26 = par5 + 0.5D + (theWorld.rand.nextFloat() - 0.5D) * 2.0D;
					theWorld.spawnParticle("smoke", var22, var24, var26, 0.0D, 0.0D, 0.0D);
					theWorld.spawnParticle("flame", var22, var24, var26, 0.0D, 0.0D, 0.0D);
				}
				return;
			case 2005:
				ItemDye.func_96603_a(theWorld, par3, par4, par5, par6);
		}
	}
	
	@Override public void playRecord(String par1Str, int par2, int par3, int par4)
	{
		ItemRecord var5 = ItemRecord.getRecord(par1Str);
		if(par1Str != null && var5 != null)
		{
			mc.ingameGUI.setRecordPlayingMessage(var5.getRecordTitle());
		}
		mc.sndManager.playStreaming(par1Str, par2, par3, par4);
	}
	
	@Override public void playSound(String par1Str, double par2, double par4, double par6, float par8, float par9)
	{
	}
	
	@Override public void playSoundToNearExcept(EntityPlayer par1EntityPlayer, String par2Str, double par3, double par5, double par7, float par9, float par10)
	{
	}
	
	public void registerDestroyBlockIcons(IconRegister par1IconRegister)
	{
		destroyBlockIcons = new Icon[10];
		for(int var2 = 0; var2 < destroyBlockIcons.length; ++var2)
		{
			destroyBlockIcons[var2] = par1IconRegister.registerIcon("destroy_stage_" + var2);
		}
	}
	
	public void renderAllRenderLists(int par1, double par2)
	{
		mc.entityRenderer.enableLightmap(par2);
		for(RenderList allRenderList : allRenderLists)
		{
			allRenderList.func_78419_a();
		}
		mc.entityRenderer.disableLightmap(par2);
	}
	
	public void renderClouds(float par1)
	{
		if(mc.theWorld.provider.isSurfaceWorld())
		{
			if(mc.gameSettings.fancyGraphics)
			{
				renderCloudsFancy(par1);
			} else
			{
				GL11.glDisable(GL11.GL_CULL_FACE);
				float var2 = (float) (mc.renderViewEntity.lastTickPosY + (mc.renderViewEntity.posY - mc.renderViewEntity.lastTickPosY) * par1);
				byte var3 = 32;
				int var4 = 256 / var3;
				Tessellator var5 = Tessellator.instance;
				renderEngine.func_110577_a(field_110925_j);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				Vec3 var6 = theWorld.getCloudColour(par1);
				float var7 = (float) var6.xCoord;
				float var8 = (float) var6.yCoord;
				float var9 = (float) var6.zCoord;
				float var10;
				if(mc.gameSettings.anaglyph)
				{
					var10 = (var7 * 30.0F + var8 * 59.0F + var9 * 11.0F) / 100.0F;
					float var11 = (var7 * 30.0F + var8 * 70.0F) / 100.0F;
					float var12 = (var7 * 30.0F + var9 * 70.0F) / 100.0F;
					var7 = var10;
					var8 = var11;
					var9 = var12;
				}
				var10 = 4.8828125E-4F;
				double var24 = cloudTickCounter + par1;
				double var13 = mc.renderViewEntity.prevPosX + (mc.renderViewEntity.posX - mc.renderViewEntity.prevPosX) * par1 + var24 * 0.029999999329447746D;
				double var15 = mc.renderViewEntity.prevPosZ + (mc.renderViewEntity.posZ - mc.renderViewEntity.prevPosZ) * par1;
				int var17 = MathHelper.floor_double(var13 / 2048.0D);
				int var18 = MathHelper.floor_double(var15 / 2048.0D);
				var13 -= var17 * 2048;
				var15 -= var18 * 2048;
				float var19 = theWorld.provider.getCloudHeight() - var2 + 0.33F;
				float var20 = (float) (var13 * var10);
				float var21 = (float) (var15 * var10);
				var5.startDrawingQuads();
				var5.setColorRGBA_F(var7, var8, var9, 0.8F);
				for(int var22 = -var3 * var4; var22 < var3 * var4; var22 += var3)
				{
					for(int var23 = -var3 * var4; var23 < var3 * var4; var23 += var3)
					{
						var5.addVertexWithUV(var22 + 0, var19, var23 + var3, (var22 + 0) * var10 + var20, (var23 + var3) * var10 + var21);
						var5.addVertexWithUV(var22 + var3, var19, var23 + var3, (var22 + var3) * var10 + var20, (var23 + var3) * var10 + var21);
						var5.addVertexWithUV(var22 + var3, var19, var23 + 0, (var22 + var3) * var10 + var20, (var23 + 0) * var10 + var21);
						var5.addVertexWithUV(var22 + 0, var19, var23 + 0, (var22 + 0) * var10 + var20, (var23 + 0) * var10 + var21);
					}
				}
				var5.draw();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_CULL_FACE);
			}
		}
	}
	
	public void renderCloudsFancy(float par1)
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
		float var2 = (float) (mc.renderViewEntity.lastTickPosY + (mc.renderViewEntity.posY - mc.renderViewEntity.lastTickPosY) * par1);
		Tessellator var3 = Tessellator.instance;
		float var4 = 12.0F;
		float var5 = 4.0F;
		double var6 = cloudTickCounter + par1;
		double var8 = (mc.renderViewEntity.prevPosX + (mc.renderViewEntity.posX - mc.renderViewEntity.prevPosX) * par1 + var6 * 0.029999999329447746D) / var4;
		double var10 = (mc.renderViewEntity.prevPosZ + (mc.renderViewEntity.posZ - mc.renderViewEntity.prevPosZ) * par1) / var4 + 0.33000001311302185D;
		float var12 = theWorld.provider.getCloudHeight() - var2 + 0.33F;
		int var13 = MathHelper.floor_double(var8 / 2048.0D);
		int var14 = MathHelper.floor_double(var10 / 2048.0D);
		var8 -= var13 * 2048;
		var10 -= var14 * 2048;
		renderEngine.func_110577_a(field_110925_j);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Vec3 var15 = theWorld.getCloudColour(par1);
		float var16 = (float) var15.xCoord;
		float var17 = (float) var15.yCoord;
		float var18 = (float) var15.zCoord;
		float var19;
		float var21;
		float var20;
		if(mc.gameSettings.anaglyph)
		{
			var19 = (var16 * 30.0F + var17 * 59.0F + var18 * 11.0F) / 100.0F;
			var20 = (var16 * 30.0F + var17 * 70.0F) / 100.0F;
			var21 = (var16 * 30.0F + var18 * 70.0F) / 100.0F;
			var16 = var19;
			var17 = var20;
			var18 = var21;
		}
		var19 = (float) (var8 * 0.0D);
		var20 = (float) (var10 * 0.0D);
		var21 = 0.00390625F;
		var19 = MathHelper.floor_double(var8) * var21;
		var20 = MathHelper.floor_double(var10) * var21;
		float var22 = (float) (var8 - MathHelper.floor_double(var8));
		float var23 = (float) (var10 - MathHelper.floor_double(var10));
		byte var24 = 8;
		byte var25 = 4;
		float var26 = 9.765625E-4F;
		GL11.glScalef(var4, 1.0F, var4);
		for(int var27 = 0; var27 < 2; ++var27)
		{
			if(var27 == 0)
			{
				GL11.glColorMask(false, false, false, false);
			} else if(mc.gameSettings.anaglyph)
			{
				if(EntityRenderer.anaglyphField == 0)
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
			for(int var28 = -var25 + 1; var28 <= var25; ++var28)
			{
				for(int var29 = -var25 + 1; var29 <= var25; ++var29)
				{
					var3.startDrawingQuads();
					float var30 = var28 * var24;
					float var31 = var29 * var24;
					float var32 = var30 - var22;
					float var33 = var31 - var23;
					if(var12 > -var5 - 1.0F)
					{
						var3.setColorRGBA_F(var16 * 0.7F, var17 * 0.7F, var18 * 0.7F, 0.8F);
						var3.setNormal(0.0F, -1.0F, 0.0F);
						var3.addVertexWithUV(var32 + 0.0F, var12 + 0.0F, var33 + var24, (var30 + 0.0F) * var21 + var19, (var31 + var24) * var21 + var20);
						var3.addVertexWithUV(var32 + var24, var12 + 0.0F, var33 + var24, (var30 + var24) * var21 + var19, (var31 + var24) * var21 + var20);
						var3.addVertexWithUV(var32 + var24, var12 + 0.0F, var33 + 0.0F, (var30 + var24) * var21 + var19, (var31 + 0.0F) * var21 + var20);
						var3.addVertexWithUV(var32 + 0.0F, var12 + 0.0F, var33 + 0.0F, (var30 + 0.0F) * var21 + var19, (var31 + 0.0F) * var21 + var20);
					}
					if(var12 <= var5 + 1.0F)
					{
						var3.setColorRGBA_F(var16, var17, var18, 0.8F);
						var3.setNormal(0.0F, 1.0F, 0.0F);
						var3.addVertexWithUV(var32 + 0.0F, var12 + var5 - var26, var33 + var24, (var30 + 0.0F) * var21 + var19, (var31 + var24) * var21 + var20);
						var3.addVertexWithUV(var32 + var24, var12 + var5 - var26, var33 + var24, (var30 + var24) * var21 + var19, (var31 + var24) * var21 + var20);
						var3.addVertexWithUV(var32 + var24, var12 + var5 - var26, var33 + 0.0F, (var30 + var24) * var21 + var19, (var31 + 0.0F) * var21 + var20);
						var3.addVertexWithUV(var32 + 0.0F, var12 + var5 - var26, var33 + 0.0F, (var30 + 0.0F) * var21 + var19, (var31 + 0.0F) * var21 + var20);
					}
					var3.setColorRGBA_F(var16 * 0.9F, var17 * 0.9F, var18 * 0.9F, 0.8F);
					int var34;
					if(var28 > -1)
					{
						var3.setNormal(-1.0F, 0.0F, 0.0F);
						for(var34 = 0; var34 < var24; ++var34)
						{
							var3.addVertexWithUV(var32 + var34 + 0.0F, var12 + 0.0F, var33 + var24, (var30 + var34 + 0.5F) * var21 + var19, (var31 + var24) * var21 + var20);
							var3.addVertexWithUV(var32 + var34 + 0.0F, var12 + var5, var33 + var24, (var30 + var34 + 0.5F) * var21 + var19, (var31 + var24) * var21 + var20);
							var3.addVertexWithUV(var32 + var34 + 0.0F, var12 + var5, var33 + 0.0F, (var30 + var34 + 0.5F) * var21 + var19, (var31 + 0.0F) * var21 + var20);
							var3.addVertexWithUV(var32 + var34 + 0.0F, var12 + 0.0F, var33 + 0.0F, (var30 + var34 + 0.5F) * var21 + var19, (var31 + 0.0F) * var21 + var20);
						}
					}
					if(var28 <= 1)
					{
						var3.setNormal(1.0F, 0.0F, 0.0F);
						for(var34 = 0; var34 < var24; ++var34)
						{
							var3.addVertexWithUV(var32 + var34 + 1.0F - var26, var12 + 0.0F, var33 + var24, (var30 + var34 + 0.5F) * var21 + var19, (var31 + var24) * var21 + var20);
							var3.addVertexWithUV(var32 + var34 + 1.0F - var26, var12 + var5, var33 + var24, (var30 + var34 + 0.5F) * var21 + var19, (var31 + var24) * var21 + var20);
							var3.addVertexWithUV(var32 + var34 + 1.0F - var26, var12 + var5, var33 + 0.0F, (var30 + var34 + 0.5F) * var21 + var19, (var31 + 0.0F) * var21 + var20);
							var3.addVertexWithUV(var32 + var34 + 1.0F - var26, var12 + 0.0F, var33 + 0.0F, (var30 + var34 + 0.5F) * var21 + var19, (var31 + 0.0F) * var21 + var20);
						}
					}
					var3.setColorRGBA_F(var16 * 0.8F, var17 * 0.8F, var18 * 0.8F, 0.8F);
					if(var29 > -1)
					{
						var3.setNormal(0.0F, 0.0F, -1.0F);
						for(var34 = 0; var34 < var24; ++var34)
						{
							var3.addVertexWithUV(var32 + 0.0F, var12 + var5, var33 + var34 + 0.0F, (var30 + 0.0F) * var21 + var19, (var31 + var34 + 0.5F) * var21 + var20);
							var3.addVertexWithUV(var32 + var24, var12 + var5, var33 + var34 + 0.0F, (var30 + var24) * var21 + var19, (var31 + var34 + 0.5F) * var21 + var20);
							var3.addVertexWithUV(var32 + var24, var12 + 0.0F, var33 + var34 + 0.0F, (var30 + var24) * var21 + var19, (var31 + var34 + 0.5F) * var21 + var20);
							var3.addVertexWithUV(var32 + 0.0F, var12 + 0.0F, var33 + var34 + 0.0F, (var30 + 0.0F) * var21 + var19, (var31 + var34 + 0.5F) * var21 + var20);
						}
					}
					if(var29 <= 1)
					{
						var3.setNormal(0.0F, 0.0F, 1.0F);
						for(var34 = 0; var34 < var24; ++var34)
						{
							var3.addVertexWithUV(var32 + 0.0F, var12 + var5, var33 + var34 + 1.0F - var26, (var30 + 0.0F) * var21 + var19, (var31 + var34 + 0.5F) * var21 + var20);
							var3.addVertexWithUV(var32 + var24, var12 + var5, var33 + var34 + 1.0F - var26, (var30 + var24) * var21 + var19, (var31 + var34 + 0.5F) * var21 + var20);
							var3.addVertexWithUV(var32 + var24, var12 + 0.0F, var33 + var34 + 1.0F - var26, (var30 + var24) * var21 + var19, (var31 + var34 + 0.5F) * var21 + var20);
							var3.addVertexWithUV(var32 + 0.0F, var12 + 0.0F, var33 + var34 + 1.0F - var26, (var30 + 0.0F) * var21 + var19, (var31 + var34 + 0.5F) * var21 + var20);
						}
					}
					var3.draw();
				}
			}
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
	
	public void renderEntities(Vec3 par1Vec3, ICamera par2ICamera, float par3)
	{
		if(renderEntitiesStartupCounter > 0)
		{
			--renderEntitiesStartupCounter;
		} else
		{
			theWorld.theProfiler.startSection("prepare");
			TileEntityRenderer.instance.cacheActiveRenderInfo(theWorld, mc.func_110434_K(), mc.fontRenderer, mc.renderViewEntity, par3);
			RenderManager.instance.cacheActiveRenderInfo(theWorld, mc.func_110434_K(), mc.fontRenderer, mc.renderViewEntity, mc.pointedEntityLiving, mc.gameSettings, par3);
			countEntitiesTotal = 0;
			countEntitiesRendered = 0;
			countEntitiesHidden = 0;
			EntityLivingBase var4 = mc.renderViewEntity;
			RenderManager.renderPosX = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * par3;
			RenderManager.renderPosY = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * par3;
			RenderManager.renderPosZ = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * par3;
			TileEntityRenderer.staticPlayerX = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * par3;
			TileEntityRenderer.staticPlayerY = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * par3;
			TileEntityRenderer.staticPlayerZ = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * par3;
			mc.entityRenderer.enableLightmap(par3);
			theWorld.theProfiler.endStartSection("global");
			List var5 = theWorld.getLoadedEntityList();
			countEntitiesTotal = var5.size();
			int var6;
			Entity var7;
			for(var6 = 0; var6 < theWorld.weatherEffects.size(); ++var6)
			{
				var7 = (Entity) theWorld.weatherEffects.get(var6);
				++countEntitiesRendered;
				if(var7.isInRangeToRenderVec3D(par1Vec3))
				{
					RenderManager.instance.renderEntity(var7, par3);
				}
			}
			theWorld.theProfiler.endStartSection("entities");
			for(var6 = 0; var6 < var5.size(); ++var6)
			{
				var7 = (Entity) var5.get(var6);
				boolean var8 = var7.isInRangeToRenderVec3D(par1Vec3) && (var7.ignoreFrustumCheck || par2ICamera.isBoundingBoxInFrustum(var7.boundingBox) || var7.riddenByEntity == mc.thePlayer);
				if(!var8 && var7 instanceof EntityLiving)
				{
					EntityLiving var9 = (EntityLiving) var7;
					if(var9.func_110167_bD() && var9.func_110166_bE() != null)
					{
						Entity var10 = var9.func_110166_bE();
						var8 = par2ICamera.isBoundingBoxInFrustum(var10.boundingBox);
					}
				}
				if(var8 && (var7 != mc.renderViewEntity || mc.gameSettings.thirdPersonView != 0 || mc.renderViewEntity.isPlayerSleeping()) && theWorld.blockExists(MathHelper.floor_double(var7.posX), 0, MathHelper.floor_double(var7.posZ)))
				{
					++countEntitiesRendered;
					RenderManager.instance.renderEntity(var7, par3);
				}
			}
			theWorld.theProfiler.endStartSection("tileentities");
			RenderHelper.enableStandardItemLighting();
			for(var6 = 0; var6 < tileEntities.size(); ++var6)
			{
				TileEntityRenderer.instance.renderTileEntity((TileEntity) tileEntities.get(var6), par3);
			}
			mc.entityRenderer.disableLightmap(par3);
			theWorld.theProfiler.endSection();
		}
	}
	
	public void renderSky(float par1)
	{
		if(mc.theWorld.provider.dimensionId == 1)
		{
			GL11.glDisable(GL11.GL_FOG);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			RenderHelper.disableStandardItemLighting();
			GL11.glDepthMask(false);
			renderEngine.func_110577_a(field_110926_k);
			Tessellator var21 = Tessellator.instance;
			for(int var22 = 0; var22 < 6; ++var22)
			{
				GL11.glPushMatrix();
				if(var22 == 1)
				{
					GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				}
				if(var22 == 2)
				{
					GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				}
				if(var22 == 3)
				{
					GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				}
				if(var22 == 4)
				{
					GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
				}
				if(var22 == 5)
				{
					GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
				}
				var21.startDrawingQuads();
				var21.setColorOpaque_I(2631720);
				var21.addVertexWithUV(-100.0D, -100.0D, -100.0D, 0.0D, 0.0D);
				var21.addVertexWithUV(-100.0D, -100.0D, 100.0D, 0.0D, 16.0D);
				var21.addVertexWithUV(100.0D, -100.0D, 100.0D, 16.0D, 16.0D);
				var21.addVertexWithUV(100.0D, -100.0D, -100.0D, 16.0D, 0.0D);
				var21.draw();
				GL11.glPopMatrix();
			}
			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
		} else if(mc.theWorld.provider.isSurfaceWorld())
		{
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			Vec3 var2 = theWorld.getSkyColor(mc.renderViewEntity, par1);
			float var3 = (float) var2.xCoord;
			float var4 = (float) var2.yCoord;
			float var5 = (float) var2.zCoord;
			float var8;
			if(mc.gameSettings.anaglyph)
			{
				float var6 = (var3 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
				float var7 = (var3 * 30.0F + var4 * 70.0F) / 100.0F;
				var8 = (var3 * 30.0F + var5 * 70.0F) / 100.0F;
				var3 = var6;
				var4 = var7;
				var5 = var8;
			}
			GL11.glColor3f(var3, var4, var5);
			Tessellator var23 = Tessellator.instance;
			GL11.glDepthMask(false);
			GL11.glEnable(GL11.GL_FOG);
			GL11.glColor3f(var3, var4, var5);
			GL11.glCallList(glSkyList);
			GL11.glDisable(GL11.GL_FOG);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			RenderHelper.disableStandardItemLighting();
			float[] var24 = theWorld.provider.calcSunriseSunsetColors(theWorld.getCelestialAngle(par1), par1);
			float var9;
			float var10;
			float var11;
			float var12;
			if(var24 != null)
			{
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				GL11.glPushMatrix();
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(MathHelper.sin(theWorld.getCelestialAngleRadians(par1)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
				var8 = var24[0];
				var9 = var24[1];
				var10 = var24[2];
				float var13;
				if(mc.gameSettings.anaglyph)
				{
					var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
					var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
					var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
					var8 = var11;
					var9 = var12;
					var10 = var13;
				}
				var23.startDrawing(6);
				var23.setColorRGBA_F(var8, var9, var10, var24[3]);
				var23.addVertex(0.0D, 100.0D, 0.0D);
				byte var26 = 16;
				var23.setColorRGBA_F(var24[0], var24[1], var24[2], 0.0F);
				for(int var27 = 0; var27 <= var26; ++var27)
				{
					var13 = var27 * (float) Math.PI * 2.0F / var26;
					float var14 = MathHelper.sin(var13);
					float var15 = MathHelper.cos(var13);
					var23.addVertex(var14 * 120.0F, var15 * 120.0F, -var15 * 40.0F * var24[3]);
				}
				var23.draw();
				GL11.glPopMatrix();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glPushMatrix();
			var8 = 1.0F - theWorld.getRainStrength(par1);
			var9 = 0.0F;
			var10 = 0.0F;
			var11 = 0.0F;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, var8);
			GL11.glTranslatef(var9, var10, var11);
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(theWorld.getCelestialAngle(par1) * 360.0F, 1.0F, 0.0F, 0.0F);
			var12 = 30.0F;
			renderEngine.func_110577_a(field_110928_i);
			var23.startDrawingQuads();
			var23.addVertexWithUV(-var12, 100.0D, -var12, 0.0D, 0.0D);
			var23.addVertexWithUV(var12, 100.0D, -var12, 1.0D, 0.0D);
			var23.addVertexWithUV(var12, 100.0D, var12, 1.0D, 1.0D);
			var23.addVertexWithUV(-var12, 100.0D, var12, 0.0D, 1.0D);
			var23.draw();
			var12 = 20.0F;
			renderEngine.func_110577_a(field_110927_h);
			int var28 = theWorld.getMoonPhase();
			int var30 = var28 % 4;
			int var29 = var28 / 4 % 2;
			float var16 = (var30 + 0) / 4.0F;
			float var17 = (var29 + 0) / 2.0F;
			float var18 = (var30 + 1) / 4.0F;
			float var19 = (var29 + 1) / 2.0F;
			var23.startDrawingQuads();
			var23.addVertexWithUV(-var12, -100.0D, var12, var18, var19);
			var23.addVertexWithUV(var12, -100.0D, var12, var16, var19);
			var23.addVertexWithUV(var12, -100.0D, -var12, var16, var17);
			var23.addVertexWithUV(-var12, -100.0D, -var12, var18, var17);
			var23.draw();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			float var20 = theWorld.getStarBrightness(par1) * var8;
			if(var20 > 0.0F)
			{
				GL11.glColor4f(var20, var20, var20, var20);
				GL11.glCallList(starGLCallList);
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_FOG);
			GL11.glPopMatrix();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(0.0F, 0.0F, 0.0F);
			double var25 = mc.thePlayer.getPosition(par1).yCoord - theWorld.getHorizon();
			if(var25 < 0.0D)
			{
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, 12.0F, 0.0F);
				GL11.glCallList(glSkyList2);
				GL11.glPopMatrix();
				var10 = 1.0F;
				var11 = -((float) (var25 + 65.0D));
				var12 = -var10;
				var23.startDrawingQuads();
				var23.setColorRGBA_I(0, 255);
				var23.addVertex(-var10, var11, var10);
				var23.addVertex(var10, var11, var10);
				var23.addVertex(var10, var12, var10);
				var23.addVertex(-var10, var12, var10);
				var23.addVertex(-var10, var12, -var10);
				var23.addVertex(var10, var12, -var10);
				var23.addVertex(var10, var11, -var10);
				var23.addVertex(-var10, var11, -var10);
				var23.addVertex(var10, var12, -var10);
				var23.addVertex(var10, var12, var10);
				var23.addVertex(var10, var11, var10);
				var23.addVertex(var10, var11, -var10);
				var23.addVertex(-var10, var11, -var10);
				var23.addVertex(-var10, var11, var10);
				var23.addVertex(-var10, var12, var10);
				var23.addVertex(-var10, var12, -var10);
				var23.addVertex(-var10, var12, -var10);
				var23.addVertex(-var10, var12, var10);
				var23.addVertex(var10, var12, var10);
				var23.addVertex(var10, var12, -var10);
				var23.draw();
			}
			if(theWorld.provider.isSkyColored())
			{
				GL11.glColor3f(var3 * 0.2F + 0.04F, var4 * 0.2F + 0.04F, var5 * 0.6F + 0.1F);
			} else
			{
				GL11.glColor3f(var3, var4, var5);
			}
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, -((float) (var25 - 16.0D)), 0.0F);
			GL11.glCallList(glSkyList2);
			GL11.glPopMatrix();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(true);
		}
	}
	
	private int renderSortedRenderers(int par1, int par2, int par3, double par4)
	{
		glRenderLists.clear();
		int var6 = 0;
		for(int var7 = par1; var7 < par2; ++var7)
		{
			if(par3 == 0)
			{
				++renderersLoaded;
				if(sortedWorldRenderers[var7].skipRenderPass[par3])
				{
					++renderersSkippingRenderPass;
				} else if(!sortedWorldRenderers[var7].isInFrustum)
				{
					++renderersBeingClipped;
				} else if(occlusionEnabled && !sortedWorldRenderers[var7].isVisible)
				{
					++renderersBeingOccluded;
				} else
				{
					++renderersBeingRendered;
				}
			}
			if(!sortedWorldRenderers[var7].skipRenderPass[par3] && sortedWorldRenderers[var7].isInFrustum && (!occlusionEnabled || sortedWorldRenderers[var7].isVisible))
			{
				int var8 = sortedWorldRenderers[var7].getGLCallListForPass(par3);
				if(var8 >= 0)
				{
					glRenderLists.add(sortedWorldRenderers[var7]);
					++var6;
				}
			}
		}
		EntityLivingBase var19 = mc.renderViewEntity;
		double var20 = var19.lastTickPosX + (var19.posX - var19.lastTickPosX) * par4;
		double var10 = var19.lastTickPosY + (var19.posY - var19.lastTickPosY) * par4;
		double var12 = var19.lastTickPosZ + (var19.posZ - var19.lastTickPosZ) * par4;
		int var14 = 0;
		int var15;
		for(var15 = 0; var15 < allRenderLists.length; ++var15)
		{
			allRenderLists[var15].func_78421_b();
		}
		for(var15 = 0; var15 < glRenderLists.size(); ++var15)
		{
			WorldRenderer var16 = (WorldRenderer) glRenderLists.get(var15);
			int var17 = -1;
			for(int var18 = 0; var18 < var14; ++var18)
			{
				if(allRenderLists[var18].func_78418_a(var16.posXMinus, var16.posYMinus, var16.posZMinus))
				{
					var17 = var18;
				}
			}
			if(var17 < 0)
			{
				var17 = var14++;
				allRenderLists[var17].func_78422_a(var16.posXMinus, var16.posYMinus, var16.posZMinus, var20, var10, var12);
			}
			allRenderLists[var17].func_78420_a(var16.getGLCallListForPass(par3));
		}
		renderAllRenderLists(par3, par4);
		return var6;
	}
	
	private void renderStars()
	{
		Random var1 = new Random(10842L);
		Tessellator var2 = Tessellator.instance;
		var2.startDrawingQuads();
		for(int var3 = 0; var3 < 1500; ++var3)
		{
			double var4 = var1.nextFloat() * 2.0F - 1.0F;
			double var6 = var1.nextFloat() * 2.0F - 1.0F;
			double var8 = var1.nextFloat() * 2.0F - 1.0F;
			double var10 = 0.15F + var1.nextFloat() * 0.1F;
			double var12 = var4 * var4 + var6 * var6 + var8 * var8;
			if(var12 < 1.0D && var12 > 0.01D)
			{
				var12 = 1.0D / Math.sqrt(var12);
				var4 *= var12;
				var6 *= var12;
				var8 *= var12;
				double var14 = var4 * 100.0D;
				double var16 = var6 * 100.0D;
				double var18 = var8 * 100.0D;
				double var20 = Math.atan2(var4, var8);
				double var22 = Math.sin(var20);
				double var24 = Math.cos(var20);
				double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
				double var28 = Math.sin(var26);
				double var30 = Math.cos(var26);
				double var32 = var1.nextDouble() * Math.PI * 2.0D;
				double var34 = Math.sin(var32);
				double var36 = Math.cos(var32);
				for(int var38 = 0; var38 < 4; ++var38)
				{
					double var39 = 0.0D;
					double var41 = ((var38 & 2) - 1) * var10;
					double var43 = ((var38 + 1 & 2) - 1) * var10;
					double var47 = var41 * var36 - var43 * var34;
					double var49 = var43 * var36 + var41 * var34;
					double var53 = var47 * var28 + var39 * var30;
					double var55 = var39 * var28 - var47 * var30;
					double var57 = var55 * var22 - var49 * var24;
					double var61 = var49 * var22 + var55 * var24;
					var2.addVertex(var14 + var57, var16 + var53, var18 + var61);
				}
			}
		}
		var2.draw();
	}
	
	public void setWorldAndLoadRenderers(WorldClient par1WorldClient)
	{
		if(theWorld != null)
		{
			theWorld.removeWorldAccess(this);
		}
		prevSortX = -9999.0D;
		prevSortY = -9999.0D;
		prevSortZ = -9999.0D;
		RenderManager.instance.set(par1WorldClient);
		theWorld = par1WorldClient;
		globalRenderBlocks = new RenderBlocks(par1WorldClient);
		if(par1WorldClient != null)
		{
			par1WorldClient.addWorldAccess(this);
			loadRenderers();
		}
	}
	
	public int sortAndRender(EntityLivingBase par1EntityLivingBase, int par2, double par3)
	{
		theWorld.theProfiler.startSection("sortchunks");
		for(int var5 = 0; var5 < 10; ++var5)
		{
			worldRenderersCheckIndex = (worldRenderersCheckIndex + 1) % worldRenderers.length;
			WorldRenderer var6 = worldRenderers[worldRenderersCheckIndex];
			if(var6.needsUpdate && !worldRenderersToUpdate.contains(var6))
			{
				worldRenderersToUpdate.add(var6);
			}
		}
		if(mc.gameSettings.renderDistance != renderDistance)
		{
			loadRenderers();
		}
		if(par2 == 0)
		{
			renderersLoaded = 0;
			dummyRenderInt = 0;
			renderersBeingClipped = 0;
			renderersBeingOccluded = 0;
			renderersBeingRendered = 0;
			renderersSkippingRenderPass = 0;
		}
		double var33 = par1EntityLivingBase.lastTickPosX + (par1EntityLivingBase.posX - par1EntityLivingBase.lastTickPosX) * par3;
		double var7 = par1EntityLivingBase.lastTickPosY + (par1EntityLivingBase.posY - par1EntityLivingBase.lastTickPosY) * par3;
		double var9 = par1EntityLivingBase.lastTickPosZ + (par1EntityLivingBase.posZ - par1EntityLivingBase.lastTickPosZ) * par3;
		double var11 = par1EntityLivingBase.posX - prevSortX;
		double var13 = par1EntityLivingBase.posY - prevSortY;
		double var15 = par1EntityLivingBase.posZ - prevSortZ;
		if(var11 * var11 + var13 * var13 + var15 * var15 > 16.0D)
		{
			prevSortX = par1EntityLivingBase.posX;
			prevSortY = par1EntityLivingBase.posY;
			prevSortZ = par1EntityLivingBase.posZ;
			markRenderersForNewPosition(MathHelper.floor_double(par1EntityLivingBase.posX), MathHelper.floor_double(par1EntityLivingBase.posY), MathHelper.floor_double(par1EntityLivingBase.posZ));
			Arrays.sort(sortedWorldRenderers, new EntitySorter(par1EntityLivingBase));
		}
		RenderHelper.disableStandardItemLighting();
		byte var17 = 0;
		int var34;
		if(occlusionEnabled && mc.gameSettings.advancedOpengl && !mc.gameSettings.anaglyph && par2 == 0)
		{
			byte var18 = 0;
			int var19 = 16;
			checkOcclusionQueryResult(var18, var19);
			for(int var20 = var18; var20 < var19; ++var20)
			{
				sortedWorldRenderers[var20].isVisible = true;
			}
			theWorld.theProfiler.endStartSection("render");
			var34 = var17 + renderSortedRenderers(var18, var19, par2, par3);
			do
			{
				theWorld.theProfiler.endStartSection("occ");
				int var35 = var19;
				var19 *= 2;
				if(var19 > sortedWorldRenderers.length)
				{
					var19 = sortedWorldRenderers.length;
				}
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				GL11.glDisable(GL11.GL_FOG);
				GL11.glColorMask(false, false, false, false);
				GL11.glDepthMask(false);
				theWorld.theProfiler.startSection("check");
				checkOcclusionQueryResult(var35, var19);
				theWorld.theProfiler.endSection();
				GL11.glPushMatrix();
				float var36 = 0.0F;
				float var21 = 0.0F;
				float var22 = 0.0F;
				for(int var23 = var35; var23 < var19; ++var23)
				{
					if(sortedWorldRenderers[var23].skipAllRenderPasses())
					{
						sortedWorldRenderers[var23].isInFrustum = false;
					} else
					{
						if(!sortedWorldRenderers[var23].isInFrustum)
						{
							sortedWorldRenderers[var23].isVisible = true;
						}
						if(sortedWorldRenderers[var23].isInFrustum && !sortedWorldRenderers[var23].isWaitingOnOcclusionQuery)
						{
							float var24 = MathHelper.sqrt_float(sortedWorldRenderers[var23].distanceToEntitySquared(par1EntityLivingBase));
							int var25 = (int) (1.0F + var24 / 128.0F);
							if(cloudTickCounter % var25 == var23 % var25)
							{
								WorldRenderer var26 = sortedWorldRenderers[var23];
								float var27 = (float) (var26.posXMinus - var33);
								float var28 = (float) (var26.posYMinus - var7);
								float var29 = (float) (var26.posZMinus - var9);
								float var30 = var27 - var36;
								float var31 = var28 - var21;
								float var32 = var29 - var22;
								if(var30 != 0.0F || var31 != 0.0F || var32 != 0.0F)
								{
									GL11.glTranslatef(var30, var31, var32);
									var36 += var30;
									var21 += var31;
									var22 += var32;
								}
								theWorld.theProfiler.startSection("bb");
								ARBOcclusionQuery.glBeginQueryARB(ARBOcclusionQuery.GL_SAMPLES_PASSED_ARB, sortedWorldRenderers[var23].glOcclusionQuery);
								sortedWorldRenderers[var23].callOcclusionQueryList();
								ARBOcclusionQuery.glEndQueryARB(ARBOcclusionQuery.GL_SAMPLES_PASSED_ARB);
								theWorld.theProfiler.endSection();
								sortedWorldRenderers[var23].isWaitingOnOcclusionQuery = true;
							}
						}
					}
				}
				GL11.glPopMatrix();
				if(mc.gameSettings.anaglyph)
				{
					if(EntityRenderer.anaglyphField == 0)
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
				GL11.glDepthMask(true);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				GL11.glEnable(GL11.GL_FOG);
				theWorld.theProfiler.endStartSection("render");
				var34 += renderSortedRenderers(var35, var19, par2, par3);
			} while(var19 < sortedWorldRenderers.length);
		} else
		{
			theWorld.theProfiler.endStartSection("render");
			var34 = var17 + renderSortedRenderers(0, sortedWorldRenderers.length, par2, par3);
		}
		theWorld.theProfiler.endSection();
		return var34;
	}
	
	@Override public void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		try
		{
			doSpawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
		} catch(Throwable var17)
		{
			CrashReport var15 = CrashReport.makeCrashReport(var17, "Exception while adding particle");
			CrashReportCategory var16 = var15.makeCategory("Particle being added");
			var16.addCrashSection("Name", par1Str);
			var16.addCrashSectionCallable("Position", new CallableParticlePositionInfo(this, par2, par4, par6));
			throw new ReportedException(var15);
		}
	}
	
	public void updateClouds()
	{
		++cloudTickCounter;
		if(cloudTickCounter % 20 == 0)
		{
			Iterator var1 = damagedBlocks.values().iterator();
			while(var1.hasNext())
			{
				DestroyBlockProgress var2 = (DestroyBlockProgress) var1.next();
				int var3 = var2.getCreationCloudUpdateTick();
				if(cloudTickCounter - var3 > 400)
				{
					var1.remove();
				}
			}
		}
	}
	
	public boolean updateRenderers(EntityLivingBase par1EntityLivingBase, boolean par2)
	{
		byte var3 = 2;
		RenderSorter var4 = new RenderSorter(par1EntityLivingBase);
		WorldRenderer[] var5 = new WorldRenderer[var3];
		ArrayList var6 = null;
		int var7 = worldRenderersToUpdate.size();
		int var8 = 0;
		theWorld.theProfiler.startSection("nearChunksSearch");
		int var9;
		WorldRenderer var10;
		int var11;
		int var12;
		label136: for(var9 = 0; var9 < var7; ++var9)
		{
			var10 = (WorldRenderer) worldRenderersToUpdate.get(var9);
			if(var10 != null)
			{
				if(!par2)
				{
					if(var10.distanceToEntitySquared(par1EntityLivingBase) > 256.0F)
					{
						for(var11 = 0; var11 < var3 && (var5[var11] == null || var4.doCompare(var5[var11], var10) <= 0); ++var11)
						{
							;
						}
						--var11;
						if(var11 > 0)
						{
							var12 = var11;
							while(true)
							{
								--var12;
								if(var12 == 0)
								{
									var5[var11] = var10;
									continue label136;
								}
								var5[var12 - 1] = var5[var12];
							}
						}
						continue;
					}
				} else if(!var10.isInFrustum)
				{
					continue;
				}
				if(var6 == null)
				{
					var6 = new ArrayList();
				}
				++var8;
				var6.add(var10);
				worldRenderersToUpdate.set(var9, (Object) null);
			}
		}
		theWorld.theProfiler.endSection();
		theWorld.theProfiler.startSection("sort");
		if(var6 != null)
		{
			if(var6.size() > 1)
			{
				Collections.sort(var6, var4);
			}
			for(var9 = var6.size() - 1; var9 >= 0; --var9)
			{
				var10 = (WorldRenderer) var6.get(var9);
				var10.updateRenderer();
				var10.needsUpdate = false;
			}
		}
		theWorld.theProfiler.endSection();
		var9 = 0;
		theWorld.theProfiler.startSection("rebuild");
		int var16;
		for(var16 = var3 - 1; var16 >= 0; --var16)
		{
			WorldRenderer var17 = var5[var16];
			if(var17 != null)
			{
				if(!var17.isInFrustum && var16 != var3 - 1)
				{
					var5[var16] = null;
					var5[0] = null;
					break;
				}
				var5[var16].updateRenderer();
				var5[var16].needsUpdate = false;
				++var9;
			}
		}
		theWorld.theProfiler.endSection();
		theWorld.theProfiler.startSection("cleanup");
		var16 = 0;
		var11 = 0;
		for(var12 = worldRenderersToUpdate.size(); var16 != var12; ++var16)
		{
			WorldRenderer var13 = (WorldRenderer) worldRenderersToUpdate.get(var16);
			if(var13 != null)
			{
				boolean var14 = false;
				for(int var15 = 0; var15 < var3 && !var14; ++var15)
				{
					if(var13 == var5[var15])
					{
						var14 = true;
					}
				}
				if(!var14)
				{
					if(var11 != var16)
					{
						worldRenderersToUpdate.set(var11, var13);
					}
					++var11;
				}
			}
		}
		theWorld.theProfiler.endSection();
		theWorld.theProfiler.startSection("trim");
		while(true)
		{
			--var16;
			if(var16 < var11)
			{
				theWorld.theProfiler.endSection();
				return var7 == var8 + var9;
			}
			worldRenderersToUpdate.remove(var16);
		}
	}
}
