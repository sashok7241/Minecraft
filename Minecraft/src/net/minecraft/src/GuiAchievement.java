package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class GuiAchievement extends Gui
{
	private Minecraft theGame;
	private int achievementWindowWidth;
	private int achievementWindowHeight;
	private String achievementGetLocalText;
	private String achievementStatName;
	private Achievement theAchievement;
	private long achievementTime;
	private RenderItem itemRender;
	private boolean haveAchiement;
	
	public GuiAchievement(Minecraft par1Minecraft)
	{
		theGame = par1Minecraft;
		itemRender = new RenderItem();
	}
	
	public void queueAchievementInformation(Achievement par1Achievement)
	{
		achievementGetLocalText = StatCollector.translateToLocal(par1Achievement.getName());
		achievementStatName = par1Achievement.getDescription();
		achievementTime = Minecraft.getSystemTime() - 2500L;
		theAchievement = par1Achievement;
		haveAchiement = true;
	}
	
	public void queueTakenAchievement(Achievement par1Achievement)
	{
		achievementGetLocalText = StatCollector.translateToLocal("achievement.get");
		achievementStatName = StatCollector.translateToLocal(par1Achievement.getName());
		achievementTime = Minecraft.getSystemTime();
		theAchievement = par1Achievement;
		haveAchiement = false;
	}
	
	public void updateAchievementWindow()
	{
		if(theAchievement != null && achievementTime != 0L)
		{
			double var1 = (Minecraft.getSystemTime() - achievementTime) / 3000.0D;
			if(!haveAchiement && (var1 < 0.0D || var1 > 1.0D))
			{
				achievementTime = 0L;
			} else
			{
				updateAchievementWindowScale();
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glDepthMask(false);
				double var3 = var1 * 2.0D;
				if(var3 > 1.0D)
				{
					var3 = 2.0D - var3;
				}
				var3 *= 4.0D;
				var3 = 1.0D - var3;
				if(var3 < 0.0D)
				{
					var3 = 0.0D;
				}
				var3 *= var3;
				var3 *= var3;
				int var5 = achievementWindowWidth - 160;
				int var6 = 0 - (int) (var3 * 36.0D);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				theGame.renderEngine.bindTexture("/achievement/bg.png");
				GL11.glDisable(GL11.GL_LIGHTING);
				drawTexturedModalRect(var5, var6, 96, 202, 160, 32);
				if(haveAchiement)
				{
					theGame.fontRenderer.drawSplitString(achievementStatName, var5 + 30, var6 + 7, 120, -1);
				} else
				{
					theGame.fontRenderer.drawString(achievementGetLocalText, var5 + 30, var6 + 7, -256);
					theGame.fontRenderer.drawString(achievementStatName, var5 + 30, var6 + 18, -1);
				}
				RenderHelper.enableGUIStandardItemLighting();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glEnable(GL11.GL_COLOR_MATERIAL);
				GL11.glEnable(GL11.GL_LIGHTING);
				itemRender.renderItemAndEffectIntoGUI(theGame.fontRenderer, theGame.renderEngine, theAchievement.theItemStack, var5 + 8, var6 + 8);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDepthMask(true);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			}
		}
	}
	
	private void updateAchievementWindowScale()
	{
		GL11.glViewport(0, 0, theGame.displayWidth, theGame.displayHeight);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		achievementWindowWidth = theGame.displayWidth;
		achievementWindowHeight = theGame.displayHeight;
		ScaledResolution var1 = new ScaledResolution(theGame.gameSettings, theGame.displayWidth, theGame.displayHeight);
		achievementWindowWidth = var1.getScaledWidth();
		achievementWindowHeight = var1.getScaledHeight();
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, (double) achievementWindowWidth, (double) achievementWindowHeight, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
	}
}
