package net.minecraft.src;

import java.util.Random;

import net.minecraft.client.Minecraft;

public class GuiAchievements extends GuiScreen
{
	private static final int guiMapTop = AchievementList.minDisplayColumn * 24 - 112;
	private static final int guiMapLeft = AchievementList.minDisplayRow * 24 - 112;
	private static final int guiMapBottom = AchievementList.maxDisplayColumn * 24 - 77;
	private static final int guiMapRight = AchievementList.maxDisplayRow * 24 - 77;
	protected int achievementsPaneWidth = 256;
	protected int achievementsPaneHeight = 202;
	protected int mouseX = 0;
	protected int mouseY = 0;
	protected double field_74117_m;
	protected double field_74115_n;
	protected double guiMapX;
	protected double guiMapY;
	protected double field_74124_q;
	protected double field_74123_r;
	private int isMouseButtonDown = 0;
	private StatFileWriter statFileWriter;
	
	public GuiAchievements(StatFileWriter p_i3070_1_)
	{
		statFileWriter = p_i3070_1_;
		short var2 = 141;
		short var3 = 141;
		field_74117_m = guiMapX = field_74124_q = AchievementList.openInventory.displayColumn * 24 - var2 / 2 - 12;
		field_74115_n = guiMapY = field_74123_r = AchievementList.openInventory.displayRow * 24 - var3 / 2;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.id == 1)
		{
			mc.displayGuiScreen((GuiScreen) null);
			mc.setIngameFocus();
		}
		super.actionPerformed(par1GuiButton);
	}
	
	@Override public boolean doesGuiPauseGame()
	{
		return true;
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		if(Mouse.isButtonDown(0))
		{
			int var4 = (width - achievementsPaneWidth) / 2;
			int var5 = (height - achievementsPaneHeight) / 2;
			int var6 = var4 + 8;
			int var7 = var5 + 17;
			if((isMouseButtonDown == 0 || isMouseButtonDown == 1) && par1 >= var6 && par1 < var6 + 224 && par2 >= var7 && par2 < var7 + 155)
			{
				if(isMouseButtonDown == 0)
				{
					isMouseButtonDown = 1;
				} else
				{
					guiMapX -= par1 - mouseX;
					guiMapY -= par2 - mouseY;
					field_74124_q = field_74117_m = guiMapX;
					field_74123_r = field_74115_n = guiMapY;
				}
				mouseX = par1;
				mouseY = par2;
			}
			if(field_74124_q < guiMapTop)
			{
				field_74124_q = guiMapTop;
			}
			if(field_74123_r < guiMapLeft)
			{
				field_74123_r = guiMapLeft;
			}
			if(field_74124_q >= guiMapBottom)
			{
				field_74124_q = guiMapBottom - 1;
			}
			if(field_74123_r >= guiMapRight)
			{
				field_74123_r = guiMapRight - 1;
			}
		} else
		{
			isMouseButtonDown = 0;
		}
		drawDefaultBackground();
		genAchievementBackground(par1, par2, par3);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		drawTitle();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	protected void drawTitle()
	{
		int var1 = (width - achievementsPaneWidth) / 2;
		int var2 = (height - achievementsPaneHeight) / 2;
		fontRenderer.drawString("Achievements", var1 + 15, var2 + 5, 4210752);
	}
	
	protected void genAchievementBackground(int par1, int par2, float par3)
	{
		int var4 = MathHelper.floor_double(field_74117_m + (guiMapX - field_74117_m) * par3);
		int var5 = MathHelper.floor_double(field_74115_n + (guiMapY - field_74115_n) * par3);
		if(var4 < guiMapTop)
		{
			var4 = guiMapTop;
		}
		if(var5 < guiMapLeft)
		{
			var5 = guiMapLeft;
		}
		if(var4 >= guiMapBottom)
		{
			var4 = guiMapBottom - 1;
		}
		if(var5 >= guiMapRight)
		{
			var5 = guiMapRight - 1;
		}
		int var6 = (width - achievementsPaneWidth) / 2;
		int var7 = (height - achievementsPaneHeight) / 2;
		int var8 = var6 + 16;
		int var9 = var7 + 17;
		zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_GEQUAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, -200.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		mc.renderEngine.bindTexture("/terrain.png");
		int var10 = var4 + 288 >> 4;
		int var11 = var5 + 288 >> 4;
		int var12 = (var4 + 288) % 16;
		int var13 = (var5 + 288) % 16;
		Random var19 = new Random();
		int var20;
		int var23;
		int var22;
		for(var20 = 0; var20 * 16 - var13 < 155; ++var20)
		{
			float var21 = 0.6F - (var11 + var20) / 25.0F * 0.3F;
			GL11.glColor4f(var21, var21, var21, 1.0F);
			for(var22 = 0; var22 * 16 - var12 < 224; ++var22)
			{
				var19.setSeed(1234 + var10 + var22);
				var19.nextInt();
				var23 = var19.nextInt(1 + var11 + var20) + (var11 + var20) / 2;
				Icon var24 = Block.sand.getIcon(0, 0);
				if(var23 <= 37 && var11 + var20 != 35)
				{
					if(var23 == 22)
					{
						if(var19.nextInt(2) == 0)
						{
							var24 = Block.oreDiamond.getIcon(0, 0);
						} else
						{
							var24 = Block.oreRedstone.getIcon(0, 0);
						}
					} else if(var23 == 10)
					{
						var24 = Block.oreIron.getIcon(0, 0);
					} else if(var23 == 8)
					{
						var24 = Block.oreCoal.getIcon(0, 0);
					} else if(var23 > 4)
					{
						var24 = Block.stone.getIcon(0, 0);
					} else if(var23 > 0)
					{
						var24 = Block.dirt.getIcon(0, 0);
					}
				} else
				{
					var24 = Block.bedrock.getIcon(0, 0);
				}
				drawTexturedModelRectFromIcon(var8 + var22 * 16 - var12, var9 + var20 * 16 - var13, var24, 16, 16);
			}
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		int var25;
		int var28;
		int var41;
		for(var20 = 0; var20 < AchievementList.achievementList.size(); ++var20)
		{
			Achievement var31 = (Achievement) AchievementList.achievementList.get(var20);
			if(var31.parentAchievement != null)
			{
				var22 = var31.displayColumn * 24 - var4 + 11 + var8;
				var23 = var31.displayRow * 24 - var5 + 11 + var9;
				var41 = var31.parentAchievement.displayColumn * 24 - var4 + 11 + var8;
				var25 = var31.parentAchievement.displayRow * 24 - var5 + 11 + var9;
				boolean var26 = statFileWriter.hasAchievementUnlocked(var31);
				boolean var27 = statFileWriter.canUnlockAchievement(var31);
				var28 = Math.sin(Minecraft.getSystemTime() % 600L / 600.0D * Math.PI * 2.0D) > 0.6D ? 255 : 130;
				int var29 = -16777216;
				if(var26)
				{
					var29 = -9408400;
				} else if(var27)
				{
					var29 = 65280 + (var28 << 24);
				}
				drawHorizontalLine(var22, var41, var23, var29);
				drawVerticalLine(var41, var23, var25, var29);
			}
		}
		Achievement var30 = null;
		RenderItem var32 = new RenderItem();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		int var39;
		int var40;
		for(var22 = 0; var22 < AchievementList.achievementList.size(); ++var22)
		{
			Achievement var34 = (Achievement) AchievementList.achievementList.get(var22);
			var41 = var34.displayColumn * 24 - var4;
			var25 = var34.displayRow * 24 - var5;
			if(var41 >= -24 && var25 >= -24 && var41 <= 224 && var25 <= 155)
			{
				float var38;
				if(statFileWriter.hasAchievementUnlocked(var34))
				{
					var38 = 1.0F;
					GL11.glColor4f(var38, var38, var38, 1.0F);
				} else if(statFileWriter.canUnlockAchievement(var34))
				{
					var38 = Math.sin(Minecraft.getSystemTime() % 600L / 600.0D * Math.PI * 2.0D) < 0.6D ? 0.6F : 0.8F;
					GL11.glColor4f(var38, var38, var38, 1.0F);
				} else
				{
					var38 = 0.3F;
					GL11.glColor4f(var38, var38, var38, 1.0F);
				}
				mc.renderEngine.bindTexture("/achievement/bg.png");
				var40 = var8 + var41;
				var39 = var9 + var25;
				if(var34.getSpecial())
				{
					drawTexturedModalRect(var40 - 2, var39 - 2, 26, 202, 26, 26);
				} else
				{
					drawTexturedModalRect(var40 - 2, var39 - 2, 0, 202, 26, 26);
				}
				if(!statFileWriter.canUnlockAchievement(var34))
				{
					float var37 = 0.1F;
					GL11.glColor4f(var37, var37, var37, 1.0F);
					var32.renderWithColor = false;
				}
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_CULL_FACE);
				var32.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, var34.theItemStack, var40 + 3, var39 + 3);
				GL11.glDisable(GL11.GL_LIGHTING);
				if(!statFileWriter.canUnlockAchievement(var34))
				{
					var32.renderWithColor = true;
				}
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				if(par1 >= var8 && par2 >= var9 && par1 < var8 + 224 && par2 < var9 + 155 && par1 >= var40 && par1 <= var40 + 22 && par2 >= var39 && par2 <= var39 + 22)
				{
					var30 = var34;
				}
			}
		}
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/achievement/bg.png");
		drawTexturedModalRect(var6, var7, 0, 0, achievementsPaneWidth, achievementsPaneHeight);
		GL11.glPopMatrix();
		zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		super.drawScreen(par1, par2, par3);
		if(var30 != null)
		{
			String var33 = StatCollector.translateToLocal(var30.getName());
			String var35 = var30.getDescription();
			var41 = par1 + 12;
			var25 = par2 - 4;
			if(statFileWriter.canUnlockAchievement(var30))
			{
				var40 = Math.max(fontRenderer.getStringWidth(var33), 120);
				var39 = fontRenderer.splitStringWidth(var35, var40);
				if(statFileWriter.hasAchievementUnlocked(var30))
				{
					var39 += 12;
				}
				drawGradientRect(var41 - 3, var25 - 3, var41 + var40 + 3, var25 + var39 + 3 + 12, -1073741824, -1073741824);
				fontRenderer.drawSplitString(var35, var41, var25 + 12, var40, -6250336);
				if(statFileWriter.hasAchievementUnlocked(var30))
				{
					fontRenderer.drawStringWithShadow(StatCollector.translateToLocal("achievement.taken"), var41, var25 + var39 + 4, -7302913);
				}
			} else
			{
				var40 = Math.max(fontRenderer.getStringWidth(var33), 120);
				String var36 = StatCollector.translateToLocalFormatted("achievement.requires", new Object[] { StatCollector.translateToLocal(var30.parentAchievement.getName()) });
				var28 = fontRenderer.splitStringWidth(var36, var40);
				drawGradientRect(var41 - 3, var25 - 3, var41 + var40 + 3, var25 + var28 + 12 + 3, -1073741824, -1073741824);
				fontRenderer.drawSplitString(var36, var41, var25 + 12, var40, -9416624);
			}
			fontRenderer.drawStringWithShadow(var33, var41, var25, statFileWriter.canUnlockAchievement(var30) ? var30.getSpecial() ? -128 : -1 : var30.getSpecial() ? -8355776 : -8355712);
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
		RenderHelper.disableStandardItemLighting();
	}
	
	@Override public void initGui()
	{
		buttonList.clear();
		buttonList.add(new GuiSmallButton(1, width / 2 + 24, height / 2 + 74, 80, 20, StatCollector.translateToLocal("gui.done")));
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		if(par2 == mc.gameSettings.keyBindInventory.keyCode)
		{
			mc.displayGuiScreen((GuiScreen) null);
			mc.setIngameFocus();
		} else
		{
			super.keyTyped(par1, par2);
		}
	}
	
	@Override public void updateScreen()
	{
		field_74117_m = guiMapX;
		field_74115_n = guiMapY;
		double var1 = field_74124_q - guiMapX;
		double var3 = field_74123_r - guiMapY;
		if(var1 * var1 + var3 * var3 < 4.0D)
		{
			guiMapX += var1;
			guiMapY += var3;
		} else
		{
			guiMapX += var1 * 0.85D;
			guiMapY += var3 * 0.85D;
		}
	}
}
