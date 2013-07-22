package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;

public class GuiMainMenu extends GuiScreen
{
	private static final Random rand = new Random();
	private float updateCounter = 0.0F;
	private String splashText = "missingno";
	private GuiButton buttonResetDemo;
	private int panoramaTimer = 0;
	private int viewportTexture;
	private boolean field_96141_q = true;
	private static boolean field_96140_r = false;
	private static boolean field_96139_s = false;
	private final Object field_104025_t = new Object();
	private String field_92025_p;
	private String field_104024_v;
	private static final String[] titlePanoramaPaths = new String[] { "/title/bg/panorama0.png", "/title/bg/panorama1.png", "/title/bg/panorama2.png", "/title/bg/panorama3.png", "/title/bg/panorama4.png", "/title/bg/panorama5.png" };
	public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
	private int field_92024_r;
	private int field_92023_s;
	private int field_92022_t;
	private int field_92021_u;
	private int field_92020_v;
	private int field_92019_w;
	
	public GuiMainMenu()
	{
		BufferedReader var1 = null;
		String var3;
		try
		{
			ArrayList var2 = new ArrayList();
			var1 = new BufferedReader(new InputStreamReader(GuiMainMenu.class.getResourceAsStream("/title/splashes.txt"), Charset.forName("UTF-8")));
			while((var3 = var1.readLine()) != null)
			{
				var3 = var3.trim();
				if(var3.length() > 0)
				{
					var2.add(var3);
				}
			}
			do
			{
				splashText = (String) var2.get(rand.nextInt(var2.size()));
			} while(splashText.hashCode() == 125780783);
		} catch(IOException var12)
		{
			;
		} finally
		{
			if(var1 != null)
			{
				try
				{
					var1.close();
				} catch(IOException var11)
				{
					;
				}
			}
		}
		updateCounter = rand.nextFloat();
		field_92025_p = "";
		String var14 = System.getProperty("os_architecture");
		var3 = System.getProperty("java_version");
		if("ppc".equalsIgnoreCase(var14))
		{
			field_92025_p = "" + EnumChatFormatting.BOLD + "Notice!" + EnumChatFormatting.RESET + " PowerPC compatibility will be dropped in Minecraft 1.6";
			field_104024_v = "http://tinyurl.com/javappc";
		} else if(var3 != null && var3.startsWith("1.5"))
		{
			field_92025_p = "" + EnumChatFormatting.BOLD + "Notice!" + EnumChatFormatting.RESET + " Java 1.5 compatibility will be dropped in Minecraft 1.6";
			field_104024_v = "http://tinyurl.com/javappc";
		}
		if(field_92025_p.length() == 0)
		{
			new Thread(new RunnableTitleScreen(this), "1.6 Update Check Thread").start();
		}
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.id == 0)
		{
			mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
		}
		if(par1GuiButton.id == 5)
		{
			mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings));
		}
		if(par1GuiButton.id == 1)
		{
			mc.displayGuiScreen(new GuiSelectWorld(this));
		}
		if(par1GuiButton.id == 2)
		{
			mc.displayGuiScreen(new GuiMultiplayer(this));
		}
		if(par1GuiButton.id == 3)
		{
			mc.displayGuiScreen(new GuiScreenOnlineServers(this));
		}
		if(par1GuiButton.id == 4)
		{
			mc.shutdown();
		}
		if(par1GuiButton.id == 11)
		{
			mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
		}
		if(par1GuiButton.id == 12)
		{
			ISaveFormat var2 = mc.getSaveLoader();
			WorldInfo var3 = var2.getWorldInfo("Demo_World");
			if(var3 != null)
			{
				GuiYesNo var4 = GuiSelectWorld.getDeleteWorldScreen(this, var3.getWorldName(), 12);
				mc.displayGuiScreen(var4);
			}
		}
	}
	
	private void addDemoButtons(int par1, int par2, StringTranslate p_73972_3_)
	{
		buttonList.add(new GuiButton(11, width / 2 - 100, par1, p_73972_3_.translateKey("menu.playdemo")));
		buttonList.add(buttonResetDemo = new GuiButton(12, width / 2 - 100, par1 + par2 * 1, p_73972_3_.translateKey("menu.resetdemo")));
		ISaveFormat var4 = mc.getSaveLoader();
		WorldInfo var5 = var4.getWorldInfo("Demo_World");
		if(var5 == null)
		{
			buttonResetDemo.enabled = false;
		}
	}
	
	private void addSingleplayerMultiplayerButtons(int par1, int par2, StringTranslate p_73969_3_)
	{
		buttonList.add(new GuiButton(1, width / 2 - 100, par1, p_73969_3_.translateKey("menu.singleplayer")));
		buttonList.add(new GuiButton(2, width / 2 - 100, par1 + par2 * 1, p_73969_3_.translateKey("menu.multiplayer")));
	}
	
	@Override public void confirmClicked(boolean par1, int par2)
	{
		if(par1 && par2 == 12)
		{
			ISaveFormat var6 = mc.getSaveLoader();
			var6.flushCache();
			var6.deleteWorldDirectory("Demo_World");
			mc.displayGuiScreen(this);
		} else if(par2 == 13)
		{
			if(par1)
			{
				try
				{
					Class var3 = Class.forName("java.awt.Desktop");
					Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
					var3.getMethod("browse", new Class[] { URI.class }).invoke(var4, new Object[] { new URI(field_104024_v) });
				} catch(Throwable var5)
				{
					var5.printStackTrace();
				}
			}
			mc.displayGuiScreen(this);
		}
	}
	
	@Override public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	private void drawPanorama(int par1, int par2, float par3)
	{
		Tessellator var4 = Tessellator.instance;
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GLU.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		byte var5 = 8;
		for(int var6 = 0; var6 < var5 * var5; ++var6)
		{
			GL11.glPushMatrix();
			float var7 = ((float) (var6 % var5) / (float) var5 - 0.5F) / 64.0F;
			float var8 = ((float) (var6 / var5) / (float) var5 - 0.5F) / 64.0F;
			float var9 = 0.0F;
			GL11.glTranslatef(var7, var8, var9);
			GL11.glRotatef(MathHelper.sin((panoramaTimer + par3) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-(panoramaTimer + par3) * 0.1F, 0.0F, 1.0F, 0.0F);
			for(int var10 = 0; var10 < 6; ++var10)
			{
				GL11.glPushMatrix();
				if(var10 == 1)
				{
					GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				}
				if(var10 == 2)
				{
					GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
				}
				if(var10 == 3)
				{
					GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
				}
				if(var10 == 4)
				{
					GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				}
				if(var10 == 5)
				{
					GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				}
				mc.renderEngine.bindTexture(titlePanoramaPaths[var10]);
				var4.startDrawingQuads();
				var4.setColorRGBA_I(16777215, 255 / (var6 + 1));
				float var11 = 0.0F;
				var4.addVertexWithUV(-1.0D, -1.0D, 1.0D, 0.0F + var11, 0.0F + var11);
				var4.addVertexWithUV(1.0D, -1.0D, 1.0D, 1.0F - var11, 0.0F + var11);
				var4.addVertexWithUV(1.0D, 1.0D, 1.0D, 1.0F - var11, 1.0F - var11);
				var4.addVertexWithUV(-1.0D, 1.0D, 1.0D, 0.0F + var11, 1.0F - var11);
				var4.draw();
				GL11.glPopMatrix();
			}
			GL11.glPopMatrix();
			GL11.glColorMask(true, true, true, false);
		}
		var4.setTranslation(0.0D, 0.0D, 0.0D);
		GL11.glColorMask(true, true, true, true);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		renderSkybox(par1, par2, par3);
		Tessellator var4 = Tessellator.instance;
		short var5 = 274;
		int var6 = width / 2 - var5 / 2;
		byte var7 = 30;
		drawGradientRect(0, 0, width, height, -2130706433, 16777215);
		drawGradientRect(0, 0, width, height, 0, Integer.MIN_VALUE);
		mc.renderEngine.bindTexture("/title/mclogo.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if(updateCounter < 1.0E-4D)
		{
			drawTexturedModalRect(var6 + 0, var7 + 0, 0, 0, 99, 44);
			drawTexturedModalRect(var6 + 99, var7 + 0, 129, 0, 27, 44);
			drawTexturedModalRect(var6 + 99 + 26, var7 + 0, 126, 0, 3, 44);
			drawTexturedModalRect(var6 + 99 + 26 + 3, var7 + 0, 99, 0, 26, 44);
			drawTexturedModalRect(var6 + 155, var7 + 0, 0, 45, 155, 44);
		} else
		{
			drawTexturedModalRect(var6 + 0, var7 + 0, 0, 0, 155, 44);
			drawTexturedModalRect(var6 + 155, var7 + 0, 0, 45, 155, 44);
		}
		var4.setColorOpaque_I(16777215);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) (width / 2 + 90), 70.0F, 0.0F);
		GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
		float var8 = 1.8F - MathHelper.abs(MathHelper.sin(Minecraft.getSystemTime() % 1000L / 1000.0F * (float) Math.PI * 2.0F) * 0.1F);
		var8 = var8 * 100.0F / (fontRenderer.getStringWidth(splashText) + 32);
		GL11.glScalef(var8, var8, var8);
		drawCenteredString(fontRenderer, splashText, 0, -8, 16776960);
		GL11.glPopMatrix();
		String var9 = "Minecraft 1.5.2";
		if(mc.isDemo())
		{
			var9 = var9 + " Demo";
		}
		drawString(fontRenderer, var9, 2, height - 10, 16777215);
		String var10 = "Copyright Mojang AB. Do not distribute!";
		drawString(fontRenderer, var10, width - fontRenderer.getStringWidth(var10) - 2, height - 10, 16777215);
		if(field_92025_p != null && field_92025_p.length() > 0)
		{
			drawRect(field_92022_t - 2, field_92021_u - 2, field_92020_v + 2, field_92019_w - 1, 1428160512);
			drawString(fontRenderer, field_92025_p, field_92022_t, field_92021_u, 16777215);
			drawString(fontRenderer, field_96138_a, (width - field_92024_r) / 2, ((GuiButton) buttonList.get(0)).yPosition - 12, 16777215);
		}
		super.drawScreen(par1, par2, par3);
	}
	
	private void func_96137_a(StringTranslate p_96137_1_, int p_96137_2_, int p_96137_3_)
	{
		if(field_96141_q)
		{
			if(!field_96140_r)
			{
				field_96140_r = true;
				new ThreadTitleScreen(this, p_96137_1_, p_96137_2_, p_96137_3_).start();
			} else if(field_96139_s)
			{
				func_98060_b(p_96137_1_, p_96137_2_, p_96137_3_);
			}
		}
	}
	
	private void func_98060_b(StringTranslate p_98060_1_, int p_98060_2_, int p_98060_3_)
	{
		buttonList.add(new GuiButton(3, width / 2 - 100, p_98060_2_ + p_98060_3_ * 2, p_98060_1_.translateKey("menu.online")));
	}
	
	@Override public void initGui()
	{
		viewportTexture = mc.renderEngine.allocateAndSetupTexture(new BufferedImage(256, 256, 2));
		Calendar var1 = Calendar.getInstance();
		var1.setTime(new Date());
		if(var1.get(2) + 1 == 11 && var1.get(5) == 9)
		{
			splashText = "Happy birthday, ez!";
		} else if(var1.get(2) + 1 == 6 && var1.get(5) == 1)
		{
			splashText = "Happy birthday, Notch!";
		} else if(var1.get(2) + 1 == 12 && var1.get(5) == 24)
		{
			splashText = "Merry X-mas!";
		} else if(var1.get(2) + 1 == 1 && var1.get(5) == 1)
		{
			splashText = "Happy new year!";
		} else if(var1.get(2) + 1 == 10 && var1.get(5) == 31)
		{
			splashText = "OOoooOOOoooo! Spooky!";
		}
		StringTranslate var2 = StringTranslate.getInstance();
		int var4 = height / 4 + 48;
		if(mc.isDemo())
		{
			addDemoButtons(var4, 24, var2);
		} else
		{
			addSingleplayerMultiplayerButtons(var4, 24, var2);
		}
		func_96137_a(var2, var4, 24);
		if(mc.hideQuitButton)
		{
			buttonList.add(new GuiButton(0, width / 2 - 100, var4 + 72, var2.translateKey("menu.options")));
		} else
		{
			buttonList.add(new GuiButton(0, width / 2 - 100, var4 + 72 + 12, 98, 20, var2.translateKey("menu.options")));
			buttonList.add(new GuiButton(4, width / 2 + 2, var4 + 72 + 12, 98, 20, var2.translateKey("menu.quit")));
		}
		buttonList.add(new GuiButtonLanguage(5, width / 2 - 124, var4 + 72 + 12));
		Object var5 = field_104025_t;
		synchronized(field_104025_t)
		{
			field_92023_s = fontRenderer.getStringWidth(field_92025_p);
			field_92024_r = fontRenderer.getStringWidth(field_96138_a);
			int var6 = Math.max(field_92023_s, field_92024_r);
			field_92022_t = (width - var6) / 2;
			field_92021_u = ((GuiButton) buttonList.get(0)).yPosition - 24;
			field_92020_v = field_92022_t + var6;
			field_92019_w = field_92021_u + 24;
		}
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		Object var4 = field_104025_t;
		synchronized(field_104025_t)
		{
			if(field_92025_p.length() > 0 && par1 >= field_92022_t && par1 <= field_92020_v && par2 >= field_92021_u && par2 <= field_92019_w)
			{
				GuiConfirmOpenLink var5 = new GuiConfirmOpenLink(this, field_104024_v, 13, true);
				var5.func_92026_h();
				mc.displayGuiScreen(var5);
			}
		}
	}
	
	private void renderSkybox(int par1, int par2, float par3)
	{
		GL11.glViewport(0, 0, 256, 256);
		drawPanorama(par1, par2, par3);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		rotateAndBlurSkybox(par3);
		rotateAndBlurSkybox(par3);
		rotateAndBlurSkybox(par3);
		rotateAndBlurSkybox(par3);
		rotateAndBlurSkybox(par3);
		rotateAndBlurSkybox(par3);
		rotateAndBlurSkybox(par3);
		rotateAndBlurSkybox(par3);
		GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
		Tessellator var4 = Tessellator.instance;
		var4.startDrawingQuads();
		float var5 = width > height ? 120.0F / width : 120.0F / height;
		float var6 = height * var5 / 256.0F;
		float var7 = width * var5 / 256.0F;
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		var4.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
		int var8 = width;
		int var9 = height;
		var4.addVertexWithUV(0.0D, var9, zLevel, 0.5F - var6, 0.5F + var7);
		var4.addVertexWithUV(var8, var9, zLevel, 0.5F - var6, 0.5F - var7);
		var4.addVertexWithUV(var8, 0.0D, zLevel, 0.5F + var6, 0.5F - var7);
		var4.addVertexWithUV(0.0D, 0.0D, zLevel, 0.5F + var6, 0.5F + var7);
		var4.draw();
	}
	
	private void rotateAndBlurSkybox(float par1)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, viewportTexture);
		mc.renderEngine.resetBoundTexture();
		GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColorMask(true, true, true, false);
		Tessellator var2 = Tessellator.instance;
		var2.startDrawingQuads();
		byte var3 = 3;
		for(int var4 = 0; var4 < var3; ++var4)
		{
			var2.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F / (var4 + 1));
			int var5 = width;
			int var6 = height;
			float var7 = (var4 - var3 / 2) / 256.0F;
			var2.addVertexWithUV(var5, var6, zLevel, 0.0F + var7, 0.0D);
			var2.addVertexWithUV(var5, 0.0D, zLevel, 1.0F + var7, 0.0D);
			var2.addVertexWithUV(0.0D, 0.0D, zLevel, 1.0F + var7, 1.0D);
			var2.addVertexWithUV(0.0D, var6, zLevel, 0.0F + var7, 1.0D);
		}
		var2.draw();
		GL11.glColorMask(true, true, true, true);
		mc.renderEngine.resetBoundTexture();
	}
	
	@Override public void updateScreen()
	{
		++panoramaTimer;
	}
	
	static Object func_104004_a(GuiMainMenu p_104004_0_)
	{
		return p_104004_0_.field_104025_t;
	}
	
	static String func_104005_a(GuiMainMenu p_104005_0_, String p_104005_1_)
	{
		return p_104005_0_.field_92025_p = p_104005_1_;
	}
	
	static int func_104006_a(GuiMainMenu p_104006_0_, int p_104006_1_)
	{
		return p_104006_0_.field_92023_s = p_104006_1_;
	}
	
	static FontRenderer func_104007_d(GuiMainMenu p_104007_0_)
	{
		return p_104007_0_.fontRenderer;
	}
	
	static int func_104008_c(GuiMainMenu p_104008_0_, int p_104008_1_)
	{
		return p_104008_0_.field_92022_t = p_104008_1_;
	}
	
	static int func_104009_d(GuiMainMenu p_104009_0_, int p_104009_1_)
	{
		return p_104009_0_.field_92021_u = p_104009_1_;
	}
	
	static int func_104011_e(GuiMainMenu p_104011_0_, int p_104011_1_)
	{
		return p_104011_0_.field_92020_v = p_104011_1_;
	}
	
	static int func_104012_f(GuiMainMenu p_104012_0_, int p_104012_1_)
	{
		return p_104012_0_.field_92019_w = p_104012_1_;
	}
	
	static String func_104013_b(GuiMainMenu p_104013_0_, String p_104013_1_)
	{
		return p_104013_0_.field_104024_v = p_104013_1_;
	}
	
	static int func_104014_b(GuiMainMenu p_104014_0_, int p_104014_1_)
	{
		return p_104014_0_.field_92024_r = p_104014_1_;
	}
	
	static int func_104015_f(GuiMainMenu p_104015_0_)
	{
		return p_104015_0_.field_92024_r;
	}
	
	static int func_104016_e(GuiMainMenu p_104016_0_)
	{
		return p_104016_0_.field_92023_s;
	}
	
	static int func_104018_h(GuiMainMenu p_104018_0_)
	{
		return p_104018_0_.field_92022_t;
	}
	
	static List func_104019_g(GuiMainMenu p_104019_0_)
	{
		return p_104019_0_.buttonList;
	}
	
	static int func_104020_i(GuiMainMenu p_104020_0_)
	{
		return p_104020_0_.field_92021_u;
	}
	
	static FontRenderer func_104022_c(GuiMainMenu p_104022_0_)
	{
		return p_104022_0_.fontRenderer;
	}
	
	static String func_104023_b(GuiMainMenu p_104023_0_)
	{
		return p_104023_0_.field_92025_p;
	}
	
	static Minecraft func_98058_a(GuiMainMenu p_98058_0_)
	{
		return p_98058_0_.mc;
	}
	
	static boolean func_98059_a(boolean p_98059_0_)
	{
		field_96139_s = p_98059_0_;
		return p_98059_0_;
	}
	
	static void func_98061_a(GuiMainMenu p_98061_0_, StringTranslate p_98061_1_, int p_98061_2_, int p_98061_3_)
	{
		p_98061_0_.func_98060_b(p_98061_1_, p_98061_2_, p_98061_3_);
	}
}
