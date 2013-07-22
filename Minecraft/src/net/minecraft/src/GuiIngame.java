package net.minecraft.src;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;

public class GuiIngame extends Gui
{
	private static final RenderItem itemRenderer = new RenderItem();
	private final Random rand = new Random();
	private final Minecraft mc;
	private final GuiNewChat persistantChatGUI;
	private int updateCounter = 0;
	private String recordPlaying = "";
	private int recordPlayingUpFor = 0;
	private boolean recordIsPlaying = false;
	public float prevVignetteBrightness = 1.0F;
	private int remainingHighlightTicks;
	private ItemStack highlightingItemStack;
	
	public GuiIngame(Minecraft p_i3037_1_)
	{
		mc = p_i3037_1_;
		persistantChatGUI = new GuiNewChat(p_i3037_1_);
	}
	
	private void func_96136_a(ScoreObjective par1ScoreObjective, int par2, int par3, FontRenderer par4FontRenderer)
	{
		Scoreboard var5 = par1ScoreObjective.getScoreboard();
		Collection var6 = var5.func_96534_i(par1ScoreObjective);
		if(var6.size() <= 15)
		{
			int var7 = par4FontRenderer.getStringWidth(par1ScoreObjective.getDisplayName());
			String var11;
			for(Iterator var8 = var6.iterator(); var8.hasNext(); var7 = Math.max(var7, par4FontRenderer.getStringWidth(var11)))
			{
				Score var9 = (Score) var8.next();
				ScorePlayerTeam var10 = var5.getPlayersTeam(var9.getPlayerName());
				var11 = ScorePlayerTeam.formatPlayerName(var10, var9.getPlayerName()) + ": " + EnumChatFormatting.RED + var9.getScorePoints();
			}
			int var22 = var6.size() * par4FontRenderer.FONT_HEIGHT;
			int var23 = par2 / 2 + var22 / 3;
			byte var25 = 3;
			int var24 = par3 - var7 - var25;
			int var12 = 0;
			Iterator var13 = var6.iterator();
			while(var13.hasNext())
			{
				Score var14 = (Score) var13.next();
				++var12;
				ScorePlayerTeam var15 = var5.getPlayersTeam(var14.getPlayerName());
				String var16 = ScorePlayerTeam.formatPlayerName(var15, var14.getPlayerName());
				String var17 = EnumChatFormatting.RED + "" + var14.getScorePoints();
				int var19 = var23 - var12 * par4FontRenderer.FONT_HEIGHT;
				int var20 = par3 - var25 + 2;
				drawRect(var24 - 2, var19, var20, var19 + par4FontRenderer.FONT_HEIGHT, 1342177280);
				par4FontRenderer.drawString(var16, var24, var19, 553648127);
				par4FontRenderer.drawString(var17, var20 - par4FontRenderer.getStringWidth(var17), var19, 553648127);
				if(var12 == var6.size())
				{
					String var21 = par1ScoreObjective.getDisplayName();
					drawRect(var24 - 2, var19 - par4FontRenderer.FONT_HEIGHT - 1, var20, var19 - 1, 1610612736);
					drawRect(var24 - 2, var19 - 1, var20, var19, 1342177280);
					par4FontRenderer.drawString(var21, var24 + var7 / 2 - par4FontRenderer.getStringWidth(var21) / 2, var19 - par4FontRenderer.FONT_HEIGHT, 553648127);
				}
			}
		}
	}
	
	public GuiNewChat getChatGUI()
	{
		return persistantChatGUI;
	}
	
	public int getUpdateCounter()
	{
		return updateCounter;
	}
	
	private void renderBossHealth()
	{
		if(BossStatus.bossName != null && BossStatus.statusBarLength > 0)
		{
			--BossStatus.statusBarLength;
			FontRenderer var1 = mc.fontRenderer;
			ScaledResolution var2 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			int var3 = var2.getScaledWidth();
			short var4 = 182;
			int var5 = var3 / 2 - var4 / 2;
			int var6 = (int) (BossStatus.healthScale * (var4 + 1));
			byte var7 = 12;
			drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
			drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
			if(var6 > 0)
			{
				drawTexturedModalRect(var5, var7, 0, 79, var6, 5);
			}
			String var8 = BossStatus.bossName;
			var1.drawStringWithShadow(var8, var3 / 2 - var1.getStringWidth(var8) / 2, var7 - 10, 16777215);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture("/gui/icons.png");
		}
	}
	
	public void renderGameOverlay(float par1, boolean par2, int par3, int par4)
	{
		ScaledResolution var5 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		int var6 = var5.getScaledWidth();
		int var7 = var5.getScaledHeight();
		FontRenderer var8 = mc.fontRenderer;
		mc.entityRenderer.setupOverlayRendering();
		GL11.glEnable(GL11.GL_BLEND);
		if(Minecraft.isFancyGraphicsEnabled())
		{
			renderVignette(mc.thePlayer.getBrightness(par1), var6, var7);
		} else
		{
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
		ItemStack var9 = mc.thePlayer.inventory.armorItemInSlot(3);
		if(mc.gameSettings.thirdPersonView == 0 && var9 != null && var9.itemID == Block.pumpkin.blockID)
		{
			renderPumpkinBlur(var6, var7);
		}
		if(!mc.thePlayer.isPotionActive(Potion.confusion))
		{
			float var10 = mc.thePlayer.prevTimeInPortal + (mc.thePlayer.timeInPortal - mc.thePlayer.prevTimeInPortal) * par1;
			if(var10 > 0.0F)
			{
				renderPortalOverlay(var10, var6, var7);
			}
		}
		boolean var11;
		int var12;
		int var13;
		int var17;
		int var16;
		int var18;
		int var20;
		int var23;
		int var22;
		int var24;
		byte var27;
		int var26;
		int var47;
		int var50;
		if(!mc.playerController.enableEverythingIsScrewedUpMode())
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture("/gui/gui.png");
			InventoryPlayer var31 = mc.thePlayer.inventory;
			zLevel = -90.0F;
			drawTexturedModalRect(var6 / 2 - 91, var7 - 22, 0, 0, 182, 22);
			drawTexturedModalRect(var6 / 2 - 91 - 1 + var31.currentItem * 20, var7 - 22 - 1, 0, 22, 24, 22);
			mc.renderEngine.bindTexture("/gui/icons.png");
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
			drawTexturedModalRect(var6 / 2 - 7, var7 / 2 - 7, 0, 0, 16, 16);
			GL11.glDisable(GL11.GL_BLEND);
			var11 = mc.thePlayer.hurtResistantTime / 3 % 2 == 1;
			if(mc.thePlayer.hurtResistantTime < 10)
			{
				var11 = false;
			}
			var12 = mc.thePlayer.getHealth();
			var13 = mc.thePlayer.prevHealth;
			rand.setSeed(updateCounter * 312871);
			boolean var14 = false;
			FoodStats var15 = mc.thePlayer.getFoodStats();
			var16 = var15.getFoodLevel();
			var17 = var15.getPrevFoodLevel();
			mc.mcProfiler.startSection("bossHealth");
			renderBossHealth();
			mc.mcProfiler.endSection();
			int var19;
			if(mc.playerController.shouldDrawHUD())
			{
				var18 = var6 / 2 - 91;
				var19 = var6 / 2 + 91;
				mc.mcProfiler.startSection("expBar");
				var20 = mc.thePlayer.xpBarCap();
				if(var20 > 0)
				{
					short var21 = 182;
					var22 = (int) (mc.thePlayer.experience * (var21 + 1));
					var23 = var7 - 32 + 3;
					drawTexturedModalRect(var18, var23, 0, 64, var21, 5);
					if(var22 > 0)
					{
						drawTexturedModalRect(var18, var23, 0, 69, var22, 5);
					}
				}
				var47 = var7 - 39;
				var22 = var47 - 10;
				var23 = mc.thePlayer.getTotalArmorValue();
				var24 = -1;
				if(mc.thePlayer.isPotionActive(Potion.regeneration))
				{
					var24 = updateCounter % 25;
				}
				mc.mcProfiler.endStartSection("healthArmor");
				int var25;
				int var29;
				int var28;
				for(var25 = 0; var25 < 10; ++var25)
				{
					if(var23 > 0)
					{
						var26 = var18 + var25 * 8;
						if(var25 * 2 + 1 < var23)
						{
							drawTexturedModalRect(var26, var22, 34, 9, 9, 9);
						}
						if(var25 * 2 + 1 == var23)
						{
							drawTexturedModalRect(var26, var22, 25, 9, 9, 9);
						}
						if(var25 * 2 + 1 > var23)
						{
							drawTexturedModalRect(var26, var22, 16, 9, 9, 9);
						}
					}
					var26 = 16;
					if(mc.thePlayer.isPotionActive(Potion.poison))
					{
						var26 += 36;
					} else if(mc.thePlayer.isPotionActive(Potion.wither))
					{
						var26 += 72;
					}
					var27 = 0;
					if(var11)
					{
						var27 = 1;
					}
					var28 = var18 + var25 * 8;
					var29 = var47;
					if(var12 <= 4)
					{
						var29 = var47 + rand.nextInt(2);
					}
					if(var25 == var24)
					{
						var29 -= 2;
					}
					byte var30 = 0;
					if(mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
					{
						var30 = 5;
					}
					drawTexturedModalRect(var28, var29, 16 + var27 * 9, 9 * var30, 9, 9);
					if(var11)
					{
						if(var25 * 2 + 1 < var13)
						{
							drawTexturedModalRect(var28, var29, var26 + 54, 9 * var30, 9, 9);
						}
						if(var25 * 2 + 1 == var13)
						{
							drawTexturedModalRect(var28, var29, var26 + 63, 9 * var30, 9, 9);
						}
					}
					if(var25 * 2 + 1 < var12)
					{
						drawTexturedModalRect(var28, var29, var26 + 36, 9 * var30, 9, 9);
					}
					if(var25 * 2 + 1 == var12)
					{
						drawTexturedModalRect(var28, var29, var26 + 45, 9 * var30, 9, 9);
					}
				}
				mc.mcProfiler.endStartSection("food");
				for(var25 = 0; var25 < 10; ++var25)
				{
					var26 = var47;
					var50 = 16;
					byte var51 = 0;
					if(mc.thePlayer.isPotionActive(Potion.hunger))
					{
						var50 += 36;
						var51 = 13;
					}
					if(mc.thePlayer.getFoodStats().getSaturationLevel() <= 0.0F && updateCounter % (var16 * 3 + 1) == 0)
					{
						var26 = var47 + rand.nextInt(3) - 1;
					}
					if(var14)
					{
						var51 = 1;
					}
					var29 = var19 - var25 * 8 - 9;
					drawTexturedModalRect(var29, var26, 16 + var51 * 9, 27, 9, 9);
					if(var14)
					{
						if(var25 * 2 + 1 < var17)
						{
							drawTexturedModalRect(var29, var26, var50 + 54, 27, 9, 9);
						}
						if(var25 * 2 + 1 == var17)
						{
							drawTexturedModalRect(var29, var26, var50 + 63, 27, 9, 9);
						}
					}
					if(var25 * 2 + 1 < var16)
					{
						drawTexturedModalRect(var29, var26, var50 + 36, 27, 9, 9);
					}
					if(var25 * 2 + 1 == var16)
					{
						drawTexturedModalRect(var29, var26, var50 + 45, 27, 9, 9);
					}
				}
				mc.mcProfiler.endStartSection("air");
				if(mc.thePlayer.isInsideOfMaterial(Material.water))
				{
					var25 = mc.thePlayer.getAir();
					var26 = MathHelper.ceiling_double_int((var25 - 2) * 10.0D / 300.0D);
					var50 = MathHelper.ceiling_double_int(var25 * 10.0D / 300.0D) - var26;
					for(var28 = 0; var28 < var26 + var50; ++var28)
					{
						if(var28 < var26)
						{
							drawTexturedModalRect(var19 - var28 * 8 - 9, var22, 16, 18, 9, 9);
						} else
						{
							drawTexturedModalRect(var19 - var28 * 8 - 9, var22, 25, 18, 9, 9);
						}
					}
				}
				mc.mcProfiler.endSection();
			}
			GL11.glDisable(GL11.GL_BLEND);
			mc.mcProfiler.startSection("actionBar");
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.enableGUIStandardItemLighting();
			for(var18 = 0; var18 < 9; ++var18)
			{
				var19 = var6 / 2 - 90 + var18 * 20 + 2;
				var20 = var7 - 16 - 3;
				renderInventorySlot(var18, var19, var20, par1);
			}
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			mc.mcProfiler.endSection();
		}
		float var33;
		if(mc.thePlayer.getSleepTimer() > 0)
		{
			mc.mcProfiler.startSection("sleep");
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			int var32 = mc.thePlayer.getSleepTimer();
			var33 = var32 / 100.0F;
			if(var33 > 1.0F)
			{
				var33 = 1.0F - (var32 - 100) / 10.0F;
			}
			var12 = (int) (220.0F * var33) << 24 | 1052704;
			drawRect(0, 0, var6, var7, var12);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			mc.mcProfiler.endSection();
		}
		int var38;
		int var37;
		if(mc.playerController.func_78763_f() && mc.thePlayer.experienceLevel > 0)
		{
			mc.mcProfiler.startSection("expLevel");
			var11 = false;
			var12 = var11 ? 16777215 : 8453920;
			String var34 = "" + mc.thePlayer.experienceLevel;
			var38 = (var6 - var8.getStringWidth(var34)) / 2;
			var37 = var7 - 31 - 4;
			var8.drawString(var34, var38 + 1, var37, 0);
			var8.drawString(var34, var38 - 1, var37, 0);
			var8.drawString(var34, var38, var37 + 1, 0);
			var8.drawString(var34, var38, var37 - 1, 0);
			var8.drawString(var34, var38, var37, var12);
			mc.mcProfiler.endSection();
		}
		String var35;
		if(mc.gameSettings.heldItemTooltips)
		{
			mc.mcProfiler.startSection("toolHighlight");
			if(remainingHighlightTicks > 0 && highlightingItemStack != null)
			{
				var35 = highlightingItemStack.getDisplayName();
				var12 = (var6 - var8.getStringWidth(var35)) / 2;
				var13 = var7 - 59;
				if(!mc.playerController.shouldDrawHUD())
				{
					var13 += 14;
				}
				var38 = (int) (remainingHighlightTicks * 256.0F / 10.0F);
				if(var38 > 255)
				{
					var38 = 255;
				}
				if(var38 > 0)
				{
					GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					var8.drawStringWithShadow(var35, var12, var13, 16777215 + (var38 << 24));
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
				}
			}
			mc.mcProfiler.endSection();
		}
		if(mc.isDemo())
		{
			mc.mcProfiler.startSection("demo");
			var35 = "";
			if(mc.theWorld.getTotalWorldTime() >= 120500L)
			{
				var35 = StatCollector.translateToLocal("demo.demoExpired");
			} else
			{
				var35 = String.format(StatCollector.translateToLocal("demo.remainingTime"), new Object[] { StringUtils.ticksToElapsedTime((int) (120500L - mc.theWorld.getTotalWorldTime())) });
			}
			var12 = var8.getStringWidth(var35);
			var8.drawStringWithShadow(var35, var6 - var12 - 10, 5, 16777215);
			mc.mcProfiler.endSection();
		}
		if(mc.gameSettings.showDebugInfo)
		{
			mc.mcProfiler.startSection("debug");
			GL11.glPushMatrix();
			var8.drawStringWithShadow("Minecraft 1.5.2 (" + mc.debug + ")", 2, 2, 16777215);
			var8.drawStringWithShadow(mc.debugInfoRenders(), 2, 12, 16777215);
			var8.drawStringWithShadow(mc.getEntityDebug(), 2, 22, 16777215);
			var8.drawStringWithShadow(mc.debugInfoEntities(), 2, 32, 16777215);
			var8.drawStringWithShadow(mc.getWorldProviderName(), 2, 42, 16777215);
			long var36 = Runtime.getRuntime().maxMemory();
			long var40 = Runtime.getRuntime().totalMemory();
			long var43 = Runtime.getRuntime().freeMemory();
			long var44 = var40 - var43;
			String var46 = "Used memory: " + var44 * 100L / var36 + "% (" + var44 / 1024L / 1024L + "MB) of " + var36 / 1024L / 1024L + "MB";
			drawString(var8, var46, var6 - var8.getStringWidth(var46) - 2, 2, 14737632);
			var46 = "Allocated memory: " + var40 * 100L / var36 + "% (" + var40 / 1024L / 1024L + "MB)";
			drawString(var8, var46, var6 - var8.getStringWidth(var46) - 2, 12, 14737632);
			var47 = MathHelper.floor_double(mc.thePlayer.posX);
			var22 = MathHelper.floor_double(mc.thePlayer.posY);
			var23 = MathHelper.floor_double(mc.thePlayer.posZ);
			drawString(var8, String.format("x: %.5f (%d) // c: %d (%d)", new Object[] { Double.valueOf(mc.thePlayer.posX), Integer.valueOf(var47), Integer.valueOf(var47 >> 4), Integer.valueOf(var47 & 15) }), 2, 64, 14737632);
			drawString(var8, String.format("y: %.3f (feet pos, %.3f eyes pos)", new Object[] { Double.valueOf(mc.thePlayer.boundingBox.minY), Double.valueOf(mc.thePlayer.posY) }), 2, 72, 14737632);
			drawString(var8, String.format("z: %.5f (%d) // c: %d (%d)", new Object[] { Double.valueOf(mc.thePlayer.posZ), Integer.valueOf(var23), Integer.valueOf(var23 >> 4), Integer.valueOf(var23 & 15) }), 2, 80, 14737632);
			var24 = MathHelper.floor_double(mc.thePlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			drawString(var8, "f: " + var24 + " (" + Direction.directions[var24] + ") / " + MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw), 2, 88, 14737632);
			if(mc.theWorld != null && mc.theWorld.blockExists(var47, var22, var23))
			{
				Chunk var52 = mc.theWorld.getChunkFromBlockCoords(var47, var23);
				drawString(var8, "lc: " + (var52.getTopFilledSegment() + 15) + " b: " + var52.getBiomeGenForWorldCoords(var47 & 15, var23 & 15, mc.theWorld.getWorldChunkManager()).biomeName + " bl: " + var52.getSavedLightValue(EnumSkyBlock.Block, var47 & 15, var22, var23 & 15) + " sl: " + var52.getSavedLightValue(EnumSkyBlock.Sky, var47 & 15, var22, var23 & 15) + " rl: " + var52.getBlockLightValue(var47 & 15, var22, var23 & 15, 0), 2, 96, 14737632);
			}
			drawString(var8, String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", new Object[] { Float.valueOf(mc.thePlayer.capabilities.getWalkSpeed()), Float.valueOf(mc.thePlayer.capabilities.getFlySpeed()), Boolean.valueOf(mc.thePlayer.onGround), Integer.valueOf(mc.theWorld.getHeightValue(var47, var23)) }), 2, 104, 14737632);
			GL11.glPopMatrix();
			mc.mcProfiler.endSection();
		}
		if(recordPlayingUpFor > 0)
		{
			mc.mcProfiler.startSection("overlayMessage");
			var33 = recordPlayingUpFor - par1;
			var12 = (int) (var33 * 256.0F / 20.0F);
			if(var12 > 255)
			{
				var12 = 255;
			}
			if(var12 > 0)
			{
				GL11.glPushMatrix();
				GL11.glTranslatef((float) (var6 / 2), (float) (var7 - 48), 0.0F);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				var13 = 16777215;
				if(recordIsPlaying)
				{
					var13 = Color.HSBtoRGB(var33 / 50.0F, 0.7F, 0.6F) & 16777215;
				}
				var8.drawString(recordPlaying, -var8.getStringWidth(recordPlaying) / 2, -4, var13 + (var12 << 24));
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
			mc.mcProfiler.endSection();
		}
		ScoreObjective var42 = mc.theWorld.getScoreboard().func_96539_a(1);
		if(var42 != null)
		{
			func_96136_a(var42, var7, var6, var8);
		}
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, (float) (var7 - 48), 0.0F);
		mc.mcProfiler.startSection("chat");
		persistantChatGUI.drawChat(updateCounter);
		mc.mcProfiler.endSection();
		GL11.glPopMatrix();
		var42 = mc.theWorld.getScoreboard().func_96539_a(0);
		if(mc.gameSettings.keyBindPlayerList.pressed && (!mc.isIntegratedServerRunning() || mc.thePlayer.sendQueue.playerInfoList.size() > 1 || var42 != null))
		{
			mc.mcProfiler.startSection("playerList");
			NetClientHandler var39 = mc.thePlayer.sendQueue;
			List var41 = var39.playerInfoList;
			var38 = var39.currentServerMaxPlayers;
			var37 = var38;
			for(var16 = 1; var37 > 20; var37 = (var38 + var16 - 1) / var16)
			{
				++var16;
			}
			var17 = 300 / var16;
			if(var17 > 150)
			{
				var17 = 150;
			}
			var18 = (var6 - var16 * var17) / 2;
			byte var45 = 10;
			drawRect(var18 - 1, var45 - 1, var18 + var17 * var16, var45 + 9 * var37, Integer.MIN_VALUE);
			for(var20 = 0; var20 < var38; ++var20)
			{
				var47 = var18 + var20 % var16 * var17;
				var22 = var45 + var20 / var16 * 9;
				drawRect(var47, var22, var47 + var17 - 1, var22 + 8, 553648127);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				if(var20 < var41.size())
				{
					GuiPlayerInfo var49 = (GuiPlayerInfo) var41.get(var20);
					ScorePlayerTeam var48 = mc.theWorld.getScoreboard().getPlayersTeam(var49.name);
					String var53 = ScorePlayerTeam.formatPlayerName(var48, var49.name);
					var8.drawStringWithShadow(var53, var47, var22, 16777215);
					if(var42 != null)
					{
						var26 = var47 + var8.getStringWidth(var53) + 5;
						var50 = var47 + var17 - 12 - 5;
						if(var50 - var26 > 5)
						{
							Score var56 = var42.getScoreboard().func_96529_a(var49.name, var42);
							String var57 = EnumChatFormatting.YELLOW + "" + var56.getScorePoints();
							var8.drawStringWithShadow(var57, var50 - var8.getStringWidth(var57), var22, 16777215);
						}
					}
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					mc.renderEngine.bindTexture("/gui/icons.png");
					byte var55 = 0;
					boolean var54 = false;
					if(var49.responseTime < 0)
					{
						var27 = 5;
					} else if(var49.responseTime < 150)
					{
						var27 = 0;
					} else if(var49.responseTime < 300)
					{
						var27 = 1;
					} else if(var49.responseTime < 600)
					{
						var27 = 2;
					} else if(var49.responseTime < 1000)
					{
						var27 = 3;
					} else
					{
						var27 = 4;
					}
					zLevel += 100.0F;
					drawTexturedModalRect(var47 + var17 - 12, var22, 0 + var55 * 10, 176 + var27 * 8, 10, 8);
					zLevel -= 100.0F;
				}
			}
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
	}
	
	private void renderInventorySlot(int par1, int par2, int par3, float par4)
	{
		ItemStack var5 = mc.thePlayer.inventory.mainInventory[par1];
		if(var5 != null)
		{
			float var6 = var5.animationsToGo - par4;
			if(var6 > 0.0F)
			{
				GL11.glPushMatrix();
				float var7 = 1.0F + var6 / 5.0F;
				GL11.glTranslatef((float) (par2 + 8), (float) (par3 + 12), 0.0F);
				GL11.glScalef(1.0F / var7, (var7 + 1.0F) / 2.0F, 1.0F);
				GL11.glTranslatef((float) -(par2 + 8), (float) -(par3 + 12), 0.0F);
			}
			itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, var5, par2, par3);
			if(var6 > 0.0F)
			{
				GL11.glPopMatrix();
			}
			itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, var5, par2, par3);
		}
	}
	
	private void renderPortalOverlay(float p_73835_1_, int p_73835_2_, int p_73835_3_)
	{
		if(p_73835_1_ < 1.0F)
		{
			p_73835_1_ *= p_73835_1_;
			p_73835_1_ *= p_73835_1_;
			p_73835_1_ = p_73835_1_ * 0.8F + 0.2F;
		}
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, p_73835_1_);
		mc.renderEngine.bindTexture("/terrain.png");
		Icon var4 = Block.portal.getBlockTextureFromSide(1);
		float var5 = var4.getMinU();
		float var6 = var4.getMinV();
		float var7 = var4.getMaxU();
		float var8 = var4.getMaxV();
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(0.0D, p_73835_3_, -90.0D, var5, var8);
		var9.addVertexWithUV(p_73835_2_, p_73835_3_, -90.0D, var7, var8);
		var9.addVertexWithUV(p_73835_2_, 0.0D, -90.0D, var7, var6);
		var9.addVertexWithUV(0.0D, 0.0D, -90.0D, var5, var6);
		var9.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	private void renderPumpkinBlur(int par1, int par2)
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		mc.renderEngine.bindTexture("%blur%/misc/pumpkinblur.png");
		Tessellator var3 = Tessellator.instance;
		var3.startDrawingQuads();
		var3.addVertexWithUV(0.0D, par2, -90.0D, 0.0D, 1.0D);
		var3.addVertexWithUV(par1, par2, -90.0D, 1.0D, 1.0D);
		var3.addVertexWithUV(par1, 0.0D, -90.0D, 1.0D, 0.0D);
		var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		var3.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	private void renderVignette(float par1, int par2, int par3)
	{
		par1 = 1.0F - par1;
		if(par1 < 0.0F)
		{
			par1 = 0.0F;
		}
		if(par1 > 1.0F)
		{
			par1 = 1.0F;
		}
		prevVignetteBrightness = (float) (prevVignetteBrightness + (par1 - prevVignetteBrightness) * 0.01D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_COLOR);
		GL11.glColor4f(prevVignetteBrightness, prevVignetteBrightness, prevVignetteBrightness, 1.0F);
		mc.renderEngine.bindTexture("%blur%/misc/vignette.png");
		Tessellator var4 = Tessellator.instance;
		var4.startDrawingQuads();
		var4.addVertexWithUV(0.0D, par3, -90.0D, 0.0D, 1.0D);
		var4.addVertexWithUV(par2, par3, -90.0D, 1.0D, 1.0D);
		var4.addVertexWithUV(par2, 0.0D, -90.0D, 1.0D, 0.0D);
		var4.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		var4.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void setRecordPlayingMessage(String par1Str)
	{
		recordPlaying = "Now playing: " + par1Str;
		recordPlayingUpFor = 60;
		recordIsPlaying = true;
	}
	
	public void updateTick()
	{
		if(recordPlayingUpFor > 0)
		{
			--recordPlayingUpFor;
		}
		++updateCounter;
		if(mc.thePlayer != null)
		{
			ItemStack var1 = mc.thePlayer.inventory.getCurrentItem();
			if(var1 == null)
			{
				remainingHighlightTicks = 0;
			} else if(highlightingItemStack != null && var1.itemID == highlightingItemStack.itemID && ItemStack.areItemStackTagsEqual(var1, highlightingItemStack) && (var1.isItemStackDamageable() || var1.getItemDamage() == highlightingItemStack.getItemDamage()))
			{
				if(remainingHighlightTicks > 0)
				{
					--remainingHighlightTicks;
				}
			} else
			{
				remainingHighlightTicks = 40;
			}
			highlightingItemStack = var1;
		}
	}
}
