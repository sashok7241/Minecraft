package net.minecraft.client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import net.minecraft.src.AchievementList;
import net.minecraft.src.AnvilSaveConverter;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.CallableClientMemoryStats;
import net.minecraft.src.CallableClientProfiler;
import net.minecraft.src.CallableGLInfo;
import net.minecraft.src.CallableLWJGLVersion;
import net.minecraft.src.CallableModded;
import net.minecraft.src.CallableParticleScreenName;
import net.minecraft.src.CallableTexturePack;
import net.minecraft.src.CallableTickingScreenName;
import net.minecraft.src.CallableType2;
import net.minecraft.src.CallableUpdatingScreenName;
import net.minecraft.src.ColorizerFoliage;
import net.minecraft.src.ColorizerGrass;
import net.minecraft.src.CrashReport;
import net.minecraft.src.CrashReportCategory;
import net.minecraft.src.EffectRenderer;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityItemFrame;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.EnumOS;
import net.minecraft.src.EnumOSHelper;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GameWindowListener;
import net.minecraft.src.GuiAchievement;
import net.minecraft.src.GuiChat;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiGameOver;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiMemoryErrorScreen;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSleepMP;
import net.minecraft.src.HttpUtil;
import net.minecraft.src.ILogAgent;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.IPlayerUsage;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.IntegratedServer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.LoadingScreenRenderer;
import net.minecraft.src.LogAgent;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MemoryConnection;
import net.minecraft.src.MinecraftError;
import net.minecraft.src.MinecraftFakeLauncher;
import net.minecraft.src.MouseHelper;
import net.minecraft.src.MovementInputFromOptions;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.OpenGlHelper;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.PlayerControllerMP;
import net.minecraft.src.PlayerUsageSnooper;
import net.minecraft.src.Profiler;
import net.minecraft.src.ProfilerResult;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderManager;
import net.minecraft.src.ReportedException;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.ScreenShotHelper;
import net.minecraft.src.ServerData;
import net.minecraft.src.Session;
import net.minecraft.src.SoundManager;
import net.minecraft.src.StatCollector;
import net.minecraft.src.StatFileWriter;
import net.minecraft.src.StatList;
import net.minecraft.src.StatStringFormatKeyInv;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TextureManager;
import net.minecraft.src.TexturePackList;
import net.minecraft.src.ThreadClientSleep;
import net.minecraft.src.ThreadDownloadResources;
import net.minecraft.src.ThreadShutdown;
import net.minecraft.src.Timer;
import net.minecraft.src.WorldClient;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldRenderer;
import net.minecraft.src.WorldSettings;

public abstract class Minecraft implements Runnable, IPlayerUsage
{
	public static byte[] memoryReserve = new byte[10485760];
	private final ILogAgent field_94139_O = new LogAgent("Minecraft-Client", " [CLIENT]", new File(getMinecraftDir(), "output-client.log").getAbsolutePath());
	private ServerData currentServerData;
	private static Minecraft theMinecraft;
	public PlayerControllerMP playerController;
	private boolean fullscreen = false;
	private boolean hasCrashed = false;
	private CrashReport crashReporter;
	public int displayWidth;
	public int displayHeight;
	private Timer timer = new Timer(20.0F);
	private PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("client", this);
	public WorldClient theWorld;
	public RenderGlobal renderGlobal;
	public EntityClientPlayerMP thePlayer;
	public EntityLiving renderViewEntity;
	public EntityLiving pointedEntityLiving;
	public EffectRenderer effectRenderer;
	public Session session = null;
	public String minecraftUri;
	public Canvas mcCanvas;
	public boolean hideQuitButton = false;
	public volatile boolean isGamePaused = false;
	public RenderEngine renderEngine;
	public FontRenderer fontRenderer;
	public FontRenderer standardGalacticFontRenderer;
	public GuiScreen currentScreen = null;
	public LoadingScreenRenderer loadingScreen;
	public EntityRenderer entityRenderer;
	private ThreadDownloadResources downloadResourcesThread;
	private int leftClickCounter = 0;
	private int tempDisplayWidth;
	private int tempDisplayHeight;
	private IntegratedServer theIntegratedServer;
	public GuiAchievement guiAchievement;
	public GuiIngame ingameGUI;
	public boolean skipRenderWorld = false;
	public MovingObjectPosition objectMouseOver = null;
	public GameSettings gameSettings;
	protected MinecraftApplet mcApplet;
	public SoundManager sndManager = new SoundManager();
	public MouseHelper mouseHelper;
	public TexturePackList texturePackList;
	public File mcDataDir;
	private ISaveFormat saveLoader;
	private static int debugFPS;
	private int rightClickDelayTimer = 0;
	private boolean refreshTexturePacksScheduled;
	public StatFileWriter statFileWriter;
	private String serverName;
	private int serverPort;
	boolean isTakingScreenshot = false;
	public boolean inGameHasFocus = false;
	long systemTime = getSystemTime();
	private int joinPlayerCounter = 0;
	private boolean isDemo;
	private INetworkManager myNetworkManager;
	private boolean integratedServerIsRunning;
	public final Profiler mcProfiler = new Profiler();
	private long field_83002_am = -1L;
	private static File minecraftDir = null;
	public volatile boolean running = true;
	public String debug = "";
	long debugUpdateTime = getSystemTime();
	int fpsCounter = 0;
	long prevFrameTime = -1L;
	private String debugProfilerName = "root";
	
	public Minecraft(Canvas p_i3022_1_, MinecraftApplet p_i3022_2_, int p_i3022_3_, int p_i3022_4_, boolean p_i3022_5_)
	{
		StatList.nopInit();
		tempDisplayHeight = p_i3022_4_;
		fullscreen = p_i3022_5_;
		mcApplet = p_i3022_2_;
		Packet3Chat.maxChatLength = 32767;
		startTimerHackThread();
		mcCanvas = p_i3022_1_;
		displayWidth = p_i3022_3_;
		displayHeight = p_i3022_4_;
		fullscreen = p_i3022_5_;
		theMinecraft = this;
		TextureManager.init();
		guiAchievement = new GuiAchievement(this);
	}
	
	public CrashReport addGraphicsAndWorldToCrashReport(CrashReport par1CrashReport)
	{
		par1CrashReport.func_85056_g().addCrashSectionCallable("LWJGL", new CallableLWJGLVersion(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("OpenGL", new CallableGLInfo(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("Is Modded", new CallableModded(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("Type", new CallableType2(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("Texture Pack", new CallableTexturePack(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("Profiler Position", new CallableClientProfiler(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("Vec3 Pool Size", new CallableClientMemoryStats(this));
		if(theWorld != null)
		{
			theWorld.addWorldInfoToCrashReport(par1CrashReport);
		}
		return par1CrashReport;
	}
	
	@Override public void addServerStatsToSnooper(PlayerUsageSnooper p_70000_1_)
	{
		p_70000_1_.addData("fps", Integer.valueOf(debugFPS));
		p_70000_1_.addData("texpack_name", texturePackList.getSelectedTexturePack().getTexturePackFileName());
		p_70000_1_.addData("vsync_enabled", Boolean.valueOf(gameSettings.enableVsync));
		p_70000_1_.addData("display_frequency", Integer.valueOf(Display.getDisplayMode().getFrequency()));
		p_70000_1_.addData("display_type", fullscreen ? "fullscreen" : "windowed");
		if(theIntegratedServer != null && theIntegratedServer.getPlayerUsageSnooper() != null)
		{
			p_70000_1_.addData("snooper_partner", theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
		}
	}
	
	@Override public void addServerTypeToSnooper(PlayerUsageSnooper p_70001_1_)
	{
		p_70001_1_.addData("opengl_version", GL11.glGetString(GL11.GL_VERSION));
		p_70001_1_.addData("opengl_vendor", GL11.glGetString(GL11.GL_VENDOR));
		p_70001_1_.addData("client_brand", ClientBrandRetriever.getClientModName());
		p_70001_1_.addData("applet", Boolean.valueOf(hideQuitButton));
		ContextCapabilities var2 = GLContext.getCapabilities();
		p_70001_1_.addData("gl_caps[ARB_multitexture]", Boolean.valueOf(var2.GL_ARB_multitexture));
		p_70001_1_.addData("gl_caps[ARB_multisample]", Boolean.valueOf(var2.GL_ARB_multisample));
		p_70001_1_.addData("gl_caps[ARB_texture_cube_map]", Boolean.valueOf(var2.GL_ARB_texture_cube_map));
		p_70001_1_.addData("gl_caps[ARB_vertex_blend]", Boolean.valueOf(var2.GL_ARB_vertex_blend));
		p_70001_1_.addData("gl_caps[ARB_matrix_palette]", Boolean.valueOf(var2.GL_ARB_matrix_palette));
		p_70001_1_.addData("gl_caps[ARB_vertex_program]", Boolean.valueOf(var2.GL_ARB_vertex_program));
		p_70001_1_.addData("gl_caps[ARB_vertex_shader]", Boolean.valueOf(var2.GL_ARB_vertex_shader));
		p_70001_1_.addData("gl_caps[ARB_fragment_program]", Boolean.valueOf(var2.GL_ARB_fragment_program));
		p_70001_1_.addData("gl_caps[ARB_fragment_shader]", Boolean.valueOf(var2.GL_ARB_fragment_shader));
		p_70001_1_.addData("gl_caps[ARB_shader_objects]", Boolean.valueOf(var2.GL_ARB_shader_objects));
		p_70001_1_.addData("gl_caps[ARB_vertex_buffer_object]", Boolean.valueOf(var2.GL_ARB_vertex_buffer_object));
		p_70001_1_.addData("gl_caps[ARB_framebuffer_object]", Boolean.valueOf(var2.GL_ARB_framebuffer_object));
		p_70001_1_.addData("gl_caps[ARB_pixel_buffer_object]", Boolean.valueOf(var2.GL_ARB_pixel_buffer_object));
		p_70001_1_.addData("gl_caps[ARB_uniform_buffer_object]", Boolean.valueOf(var2.GL_ARB_uniform_buffer_object));
		p_70001_1_.addData("gl_caps[ARB_texture_non_power_of_two]", Boolean.valueOf(var2.GL_ARB_texture_non_power_of_two));
		p_70001_1_.addData("gl_caps[gl_max_vertex_uniforms]", Integer.valueOf(GL11.glGetInteger(GL20.GL_MAX_VERTEX_UNIFORM_COMPONENTS)));
		p_70001_1_.addData("gl_caps[gl_max_fragment_uniforms]", Integer.valueOf(GL11.glGetInteger(GL20.GL_MAX_FRAGMENT_UNIFORM_COMPONENTS)));
		p_70001_1_.addData("gl_max_texture_size", Integer.valueOf(getGLMaximumTextureSize()));
	}
	
	private void checkGLError(String par1Str)
	{
		int var2 = GL11.glGetError();
		if(var2 != 0)
		{
			String var3 = GLU.gluErrorString(var2);
			getLogAgent().logSevere("########## GL ERROR ##########");
			getLogAgent().logSevere("@ " + par1Str);
			getLogAgent().logSevere(var2 + ": " + var3);
		}
	}
	
	private void clickMiddleMouseButton()
	{
		if(objectMouseOver != null)
		{
			boolean var1 = thePlayer.capabilities.isCreativeMode;
			int var3 = 0;
			boolean var4 = false;
			int var2;
			int var5;
			if(objectMouseOver.typeOfHit == EnumMovingObjectType.TILE)
			{
				var5 = objectMouseOver.blockX;
				int var6 = objectMouseOver.blockY;
				int var7 = objectMouseOver.blockZ;
				Block var8 = Block.blocksList[theWorld.getBlockId(var5, var6, var7)];
				if(var8 == null) return;
				var2 = var8.idPicked(theWorld, var5, var6, var7);
				if(var2 == 0) return;
				var4 = Item.itemsList[var2].getHasSubtypes();
				int var9 = var2 < 256 && !Block.blocksList[var8.blockID].isFlowerPot() ? var2 : var8.blockID;
				var3 = Block.blocksList[var9].getDamageValue(theWorld, var5, var6, var7);
			} else
			{
				if(objectMouseOver.typeOfHit != EnumMovingObjectType.ENTITY || objectMouseOver.entityHit == null || !var1) return;
				if(objectMouseOver.entityHit instanceof EntityPainting)
				{
					var2 = Item.painting.itemID;
				} else if(objectMouseOver.entityHit instanceof EntityItemFrame)
				{
					EntityItemFrame var10 = (EntityItemFrame) objectMouseOver.entityHit;
					if(var10.getDisplayedItem() == null)
					{
						var2 = Item.itemFrame.itemID;
					} else
					{
						var2 = var10.getDisplayedItem().itemID;
						var3 = var10.getDisplayedItem().getItemDamage();
						var4 = true;
					}
				} else if(objectMouseOver.entityHit instanceof EntityMinecart)
				{
					EntityMinecart var11 = (EntityMinecart) objectMouseOver.entityHit;
					if(var11.getMinecartType() == 2)
					{
						var2 = Item.minecartPowered.itemID;
					} else if(var11.getMinecartType() == 1)
					{
						var2 = Item.minecartCrate.itemID;
					} else if(var11.getMinecartType() == 3)
					{
						var2 = Item.minecartTnt.itemID;
					} else if(var11.getMinecartType() == 5)
					{
						var2 = Item.minecartHopper.itemID;
					} else
					{
						var2 = Item.minecartEmpty.itemID;
					}
				} else if(objectMouseOver.entityHit instanceof EntityBoat)
				{
					var2 = Item.boat.itemID;
				} else
				{
					var2 = Item.monsterPlacer.itemID;
					var3 = EntityList.getEntityID(objectMouseOver.entityHit);
					var4 = true;
					if(var3 <= 0 || !EntityList.entityEggs.containsKey(Integer.valueOf(var3))) return;
				}
			}
			thePlayer.inventory.setCurrentItem(var2, var3, var4, var1);
			if(var1)
			{
				var5 = thePlayer.inventoryContainer.inventorySlots.size() - 9 + thePlayer.inventory.currentItem;
				playerController.sendSlotPacket(thePlayer.inventory.getStackInSlot(thePlayer.inventory.currentItem), var5);
			}
		}
	}
	
	private void clickMouse(int par1)
	{
		if(par1 != 0 || leftClickCounter <= 0)
		{
			if(par1 == 0)
			{
				thePlayer.swingItem();
			}
			if(par1 == 1)
			{
				rightClickDelayTimer = 4;
			}
			boolean var2 = true;
			ItemStack var3 = thePlayer.inventory.getCurrentItem();
			if(objectMouseOver == null)
			{
				if(par1 == 0 && playerController.isNotCreative())
				{
					leftClickCounter = 10;
				}
			} else if(objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY)
			{
				if(par1 == 0)
				{
					playerController.attackEntity(thePlayer, objectMouseOver.entityHit);
				}
				if(par1 == 1 && playerController.func_78768_b(thePlayer, objectMouseOver.entityHit))
				{
					var2 = false;
				}
			} else if(objectMouseOver.typeOfHit == EnumMovingObjectType.TILE)
			{
				int var4 = objectMouseOver.blockX;
				int var5 = objectMouseOver.blockY;
				int var6 = objectMouseOver.blockZ;
				int var7 = objectMouseOver.sideHit;
				if(par1 == 0)
				{
					playerController.clickBlock(var4, var5, var6, objectMouseOver.sideHit);
				} else
				{
					int var8 = var3 != null ? var3.stackSize : 0;
					if(playerController.onPlayerRightClick(thePlayer, theWorld, var3, var4, var5, var6, var7, objectMouseOver.hitVec))
					{
						var2 = false;
						thePlayer.swingItem();
					}
					if(var3 == null) return;
					if(var3.stackSize == 0)
					{
						thePlayer.inventory.mainInventory[thePlayer.inventory.currentItem] = null;
					} else if(var3.stackSize != var8 || playerController.isInCreativeMode())
					{
						entityRenderer.itemRenderer.resetEquippedProgress();
					}
				}
			}
			if(var2 && par1 == 1)
			{
				ItemStack var9 = thePlayer.inventory.getCurrentItem();
				if(var9 != null && playerController.sendUseItem(thePlayer, theWorld, var9))
				{
					entityRenderer.itemRenderer.resetEquippedProgress2();
				}
			}
		}
	}
	
	public void crashed(CrashReport par1CrashReport)
	{
		hasCrashed = true;
		crashReporter = par1CrashReport;
	}
	
	public String debugInfoEntities()
	{
		return "P: " + effectRenderer.getStatistics() + ". T: " + theWorld.getDebugLoadedEntities();
	}
	
	public String debugInfoRenders()
	{
		return renderGlobal.getDebugInfoRenders();
	}
	
	public void displayCrashReport(CrashReport par1CrashReport)
	{
		hasCrashed = true;
		displayCrashReportInternal(par1CrashReport);
	}
	
	public abstract void displayCrashReportInternal(CrashReport var1);
	
	private void displayDebugInfo(long par1)
	{
		if(mcProfiler.profilingEnabled)
		{
			List var3 = mcProfiler.getProfilingData(debugProfilerName);
			ProfilerResult var4 = (ProfilerResult) var3.remove(0);
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glEnable(GL11.GL_COLOR_MATERIAL);
			GL11.glLoadIdentity();
			GL11.glOrtho(0.0D, (double) displayWidth, (double) displayHeight, 0.0D, 1000.0D, 3000.0D);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
			GL11.glLineWidth(1.0F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			Tessellator var5 = Tessellator.instance;
			short var6 = 160;
			int var7 = displayWidth - var6 - 10;
			int var8 = displayHeight - var6 * 2;
			GL11.glEnable(GL11.GL_BLEND);
			var5.startDrawingQuads();
			var5.setColorRGBA_I(0, 200);
			var5.addVertex(var7 - var6 * 1.1F, var8 - var6 * 0.6F - 16.0F, 0.0D);
			var5.addVertex(var7 - var6 * 1.1F, var8 + var6 * 2, 0.0D);
			var5.addVertex(var7 + var6 * 1.1F, var8 + var6 * 2, 0.0D);
			var5.addVertex(var7 + var6 * 1.1F, var8 - var6 * 0.6F - 16.0F, 0.0D);
			var5.draw();
			GL11.glDisable(GL11.GL_BLEND);
			double var9 = 0.0D;
			int var13;
			for(int var11 = 0; var11 < var3.size(); ++var11)
			{
				ProfilerResult var12 = (ProfilerResult) var3.get(var11);
				var13 = MathHelper.floor_double(var12.field_76332_a / 4.0D) + 1;
				var5.startDrawing(6);
				var5.setColorOpaque_I(var12.func_76329_a());
				var5.addVertex(var7, var8, 0.0D);
				int var14;
				float var15;
				float var17;
				float var16;
				for(var14 = var13; var14 >= 0; --var14)
				{
					var15 = (float) ((var9 + var12.field_76332_a * var14 / var13) * Math.PI * 2.0D / 100.0D);
					var16 = MathHelper.sin(var15) * var6;
					var17 = MathHelper.cos(var15) * var6 * 0.5F;
					var5.addVertex(var7 + var16, var8 - var17, 0.0D);
				}
				var5.draw();
				var5.startDrawing(5);
				var5.setColorOpaque_I((var12.func_76329_a() & 16711422) >> 1);
				for(var14 = var13; var14 >= 0; --var14)
				{
					var15 = (float) ((var9 + var12.field_76332_a * var14 / var13) * Math.PI * 2.0D / 100.0D);
					var16 = MathHelper.sin(var15) * var6;
					var17 = MathHelper.cos(var15) * var6 * 0.5F;
					var5.addVertex(var7 + var16, var8 - var17, 0.0D);
					var5.addVertex(var7 + var16, var8 - var17 + 10.0F, 0.0D);
				}
				var5.draw();
				var9 += var12.field_76332_a;
			}
			DecimalFormat var19 = new DecimalFormat("##0.00");
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			String var18 = "";
			if(!var4.field_76331_c.equals("unspecified"))
			{
				var18 = var18 + "[0] ";
			}
			if(var4.field_76331_c.length() == 0)
			{
				var18 = var18 + "ROOT ";
			} else
			{
				var18 = var18 + var4.field_76331_c + " ";
			}
			var13 = 16777215;
			fontRenderer.drawStringWithShadow(var18, var7 - var6, var8 - var6 / 2 - 16, var13);
			fontRenderer.drawStringWithShadow(var18 = var19.format(var4.field_76330_b) + "%", var7 + var6 - fontRenderer.getStringWidth(var18), var8 - var6 / 2 - 16, var13);
			for(int var21 = 0; var21 < var3.size(); ++var21)
			{
				ProfilerResult var20 = (ProfilerResult) var3.get(var21);
				String var22 = "";
				if(var20.field_76331_c.equals("unspecified"))
				{
					var22 = var22 + "[?] ";
				} else
				{
					var22 = var22 + "[" + (var21 + 1) + "] ";
				}
				var22 = var22 + var20.field_76331_c;
				fontRenderer.drawStringWithShadow(var22, var7 - var6, var8 + var6 / 2 + var21 * 8 + 20, var20.func_76329_a());
				fontRenderer.drawStringWithShadow(var22 = var19.format(var20.field_76332_a) + "%", var7 + var6 - 50 - fontRenderer.getStringWidth(var22), var8 + var6 / 2 + var21 * 8 + 20, var20.func_76329_a());
				fontRenderer.drawStringWithShadow(var22 = var19.format(var20.field_76330_b) + "%", var7 + var6 - fontRenderer.getStringWidth(var22), var8 + var6 / 2 + var21 * 8 + 20, var20.func_76329_a());
			}
		}
	}
	
	public void displayGuiScreen(GuiScreen par1GuiScreen)
	{
		if(currentScreen != null)
		{
			currentScreen.onGuiClosed();
		}
		statFileWriter.syncStats();
		if(par1GuiScreen == null && theWorld == null)
		{
			par1GuiScreen = new GuiMainMenu();
		} else if(par1GuiScreen == null && thePlayer.getHealth() <= 0)
		{
			par1GuiScreen = new GuiGameOver();
		}
		if(par1GuiScreen instanceof GuiMainMenu)
		{
			gameSettings.showDebugInfo = false;
			ingameGUI.getChatGUI().clearChatMessages();
		}
		currentScreen = par1GuiScreen;
		if(par1GuiScreen != null)
		{
			setIngameNotInFocus();
			ScaledResolution var2 = new ScaledResolution(gameSettings, displayWidth, displayHeight);
			int var3 = var2.getScaledWidth();
			int var4 = var2.getScaledHeight();
			par1GuiScreen.setWorldAndResolution(this, var3, var4);
			skipRenderWorld = false;
		} else
		{
			setIngameFocus();
		}
	}
	
	public void displayInGameMenu()
	{
		if(currentScreen == null)
		{
			displayGuiScreen(new GuiIngameMenu());
			if(isSingleplayer() && !theIntegratedServer.getPublic())
			{
				sndManager.pauseAllSounds();
			}
		}
	}
	
	private void forceReload()
	{
		getLogAgent().logInfo("FORCING RELOAD!");
		if(sndManager != null)
		{
			sndManager.stopAllSounds();
		}
		sndManager = new SoundManager();
		sndManager.loadSoundSettings(gameSettings);
		downloadResourcesThread.reloadResources();
	}
	
	public void freeMemory()
	{
		try
		{
			memoryReserve = new byte[0];
			renderGlobal.deleteAllDisplayLists();
		} catch(Throwable var4)
		{
			;
		}
		try
		{
			System.gc();
			AxisAlignedBB.getAABBPool().clearPool();
			theWorld.getWorldVec3Pool().clearAndFreeCache();
		} catch(Throwable var3)
		{
			;
		}
		try
		{
			System.gc();
			this.loadWorld((WorldClient) null);
		} catch(Throwable var2)
		{
			;
		}
		System.gc();
	}
	
	private int func_90020_K()
	{
		return currentScreen != null && currentScreen instanceof GuiMainMenu ? 2 : gameSettings.limitFramerate;
	}
	
	public String getEntityDebug()
	{
		return renderGlobal.getDebugInfoEntities();
	}
	
	public IntegratedServer getIntegratedServer()
	{
		return theIntegratedServer;
	}
	
	@Override public ILogAgent getLogAgent()
	{
		return field_94139_O;
	}
	
	public NetClientHandler getNetHandler()
	{
		return thePlayer != null ? thePlayer.sendQueue : null;
	}
	
	public PlayerUsageSnooper getPlayerUsageSnooper()
	{
		return usageSnooper;
	}
	
	public ISaveFormat getSaveLoader()
	{
		return saveLoader;
	}
	
	public ServerData getServerData()
	{
		return currentServerData;
	}
	
	public String getWorldProviderName()
	{
		return theWorld.getProviderName();
	}
	
	public boolean handleClientCommand(String par1Str)
	{
		return !par1Str.startsWith("/") ? false : false;
	}
	
	public void installResource(String p_71360_1_, File p_71360_2_)
	{
		int var3 = p_71360_1_.indexOf("/");
		String var4 = p_71360_1_.substring(0, var3);
		p_71360_1_ = p_71360_1_.substring(var3 + 1);
		if(var4.equalsIgnoreCase("sound3"))
		{
			sndManager.addSound(p_71360_1_, p_71360_2_);
		} else if(var4.equalsIgnoreCase("streaming"))
		{
			sndManager.addStreaming(p_71360_1_, p_71360_2_);
		} else if(!var4.equalsIgnoreCase("music") && !var4.equalsIgnoreCase("newmusic"))
		{
			if(var4.equalsIgnoreCase("lang"))
			{
				StringTranslate.getInstance().func_94519_a(p_71360_1_, p_71360_2_);
			}
		} else
		{
			sndManager.addMusic(p_71360_1_, p_71360_2_);
		}
	}
	
	public final boolean isDemo()
	{
		return isDemo;
	}
	
	public boolean isFullScreen()
	{
		return fullscreen;
	}
	
	public boolean isIntegratedServerRunning()
	{
		return integratedServerIsRunning;
	}
	
	public boolean isSingleplayer()
	{
		return integratedServerIsRunning && theIntegratedServer != null;
	}
	
	@Override public boolean isSnooperEnabled()
	{
		return gameSettings.snooperEnabled;
	}
	
	public void launchIntegratedServer(String par1Str, String par2Str, WorldSettings par3WorldSettings)
	{
		this.loadWorld((WorldClient) null);
		System.gc();
		ISaveHandler var4 = saveLoader.getSaveLoader(par1Str, false);
		WorldInfo var5 = var4.loadWorldInfo();
		if(var5 == null && par3WorldSettings != null)
		{
			statFileWriter.readStat(StatList.createWorldStat, 1);
			var5 = new WorldInfo(par3WorldSettings, par1Str);
			var4.saveWorldInfo(var5);
		}
		if(par3WorldSettings == null)
		{
			par3WorldSettings = new WorldSettings(var5);
		}
		statFileWriter.readStat(StatList.startGameStat, 1);
		theIntegratedServer = new IntegratedServer(this, par1Str, par2Str, par3WorldSettings);
		theIntegratedServer.startServerThread();
		integratedServerIsRunning = true;
		loadingScreen.displayProgressMessage(StatCollector.translateToLocal("menu.loadingLevel"));
		while(!theIntegratedServer.serverIsInRunLoop())
		{
			String var6 = theIntegratedServer.getUserMessage();
			if(var6 != null)
			{
				loadingScreen.resetProgresAndWorkingMessage(StatCollector.translateToLocal(var6));
			} else
			{
				loadingScreen.resetProgresAndWorkingMessage("");
			}
			try
			{
				Thread.sleep(200L);
			} catch(InterruptedException var9)
			{
				;
			}
		}
		displayGuiScreen((GuiScreen) null);
		try
		{
			NetClientHandler var10 = new NetClientHandler(this, theIntegratedServer);
			myNetworkManager = var10.getNetManager();
		} catch(IOException var8)
		{
			displayCrashReport(addGraphicsAndWorldToCrashReport(new CrashReport("Connecting to integrated server", var8)));
		}
	}
	
	private void loadScreen() throws LWJGLException
	{
		ScaledResolution var1 = new ScaledResolution(gameSettings, displayWidth, displayHeight);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, var1.getScaledWidth_double(), var1.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
		GL11.glViewport(0, 0, displayWidth, displayHeight);
		GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator var2 = Tessellator.instance;
		renderEngine.bindTexture("/title/mojang.png");
		var2.startDrawingQuads();
		var2.setColorOpaque_I(16777215);
		var2.addVertexWithUV(0.0D, displayHeight, 0.0D, 0.0D, 0.0D);
		var2.addVertexWithUV(displayWidth, displayHeight, 0.0D, 0.0D, 0.0D);
		var2.addVertexWithUV(displayWidth, 0.0D, 0.0D, 0.0D, 0.0D);
		var2.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		var2.draw();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		var2.setColorOpaque_I(16777215);
		short var3 = 256;
		short var4 = 256;
		scaledTessellator((var1.getScaledWidth() - var3) / 2, (var1.getScaledHeight() - var4) / 2, 0, 0, var3, var4);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		Display.swapBuffers();
	}
	
	public void loadWorld(WorldClient par1WorldClient)
	{
		this.loadWorld(par1WorldClient, "");
	}
	
	public void loadWorld(WorldClient par1WorldClient, String par2Str)
	{
		statFileWriter.syncStats();
		if(par1WorldClient == null)
		{
			NetClientHandler var3 = getNetHandler();
			if(var3 != null)
			{
				var3.cleanup();
			}
			if(myNetworkManager != null)
			{
				myNetworkManager.closeConnections();
			}
			if(theIntegratedServer != null)
			{
				theIntegratedServer.initiateShutdown();
			}
			theIntegratedServer = null;
		}
		renderViewEntity = null;
		myNetworkManager = null;
		if(loadingScreen != null)
		{
			loadingScreen.resetProgressAndMessage(par2Str);
			loadingScreen.resetProgresAndWorkingMessage("");
		}
		if(par1WorldClient == null && theWorld != null)
		{
			if(texturePackList.getIsDownloading())
			{
				texturePackList.onDownloadFinished();
			}
			setServerData((ServerData) null);
			integratedServerIsRunning = false;
		}
		sndManager.playStreaming((String) null, 0.0F, 0.0F, 0.0F);
		sndManager.stopAllSounds();
		theWorld = par1WorldClient;
		if(par1WorldClient != null)
		{
			if(renderGlobal != null)
			{
				renderGlobal.setWorldAndLoadRenderers(par1WorldClient);
			}
			if(effectRenderer != null)
			{
				effectRenderer.clearEffects(par1WorldClient);
			}
			if(thePlayer == null)
			{
				thePlayer = playerController.func_78754_a(par1WorldClient);
				playerController.flipPlayer(thePlayer);
			}
			thePlayer.preparePlayerToSpawn();
			par1WorldClient.spawnEntityInWorld(thePlayer);
			thePlayer.movementInput = new MovementInputFromOptions(gameSettings);
			playerController.setPlayerCapabilities(thePlayer);
			renderViewEntity = thePlayer;
		} else
		{
			saveLoader.flushCache();
			thePlayer = null;
		}
		System.gc();
		systemTime = 0L;
	}
	
	private void resize(int par1, int par2)
	{
		displayWidth = par1 <= 0 ? 1 : par1;
		displayHeight = par2 <= 0 ? 1 : par2;
		if(currentScreen != null)
		{
			ScaledResolution var3 = new ScaledResolution(gameSettings, par1, par2);
			int var4 = var3.getScaledWidth();
			int var5 = var3.getScaledHeight();
			currentScreen.setWorldAndResolution(this, var4, var5);
		}
	}
	
	@Override public void run()
	{
		running = true;
		try
		{
			startGame();
		} catch(Exception var11)
		{
			var11.printStackTrace();
			displayCrashReport(addGraphicsAndWorldToCrashReport(new CrashReport("Failed to start game", var11)));
			return;
		}
		try
		{
			while(running)
			{
				if(hasCrashed && crashReporter != null)
				{
					displayCrashReport(crashReporter);
					return;
				}
				if(refreshTexturePacksScheduled)
				{
					refreshTexturePacksScheduled = false;
					renderEngine.refreshTextures();
				}
				try
				{
					runGameLoop();
				} catch(OutOfMemoryError var10)
				{
					freeMemory();
					displayGuiScreen(new GuiMemoryErrorScreen());
					System.gc();
				}
			}
		} catch(MinecraftError var12)
		{
			;
		} catch(ReportedException var13)
		{
			addGraphicsAndWorldToCrashReport(var13.getCrashReport());
			freeMemory();
			var13.printStackTrace();
			displayCrashReport(var13.getCrashReport());
		} catch(Throwable var14)
		{
			CrashReport var2 = addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", var14));
			freeMemory();
			var14.printStackTrace();
			displayCrashReport(var2);
		} finally
		{
			shutdownMinecraftApplet();
		}
	}
	
	private void runGameLoop()
	{
		if(mcApplet != null && !mcApplet.isActive())
		{
			running = false;
		} else
		{
			AxisAlignedBB.getAABBPool().cleanPool();
			if(theWorld != null)
			{
				theWorld.getWorldVec3Pool().clear();
			}
			mcProfiler.startSection("root");
			if(mcCanvas == null && Display.isCloseRequested())
			{
				shutdown();
			}
			if(isGamePaused && theWorld != null)
			{
				float var1 = timer.renderPartialTicks;
				timer.updateTimer();
				timer.renderPartialTicks = var1;
			} else
			{
				timer.updateTimer();
			}
			long var6 = System.nanoTime();
			mcProfiler.startSection("tick");
			for(int var3 = 0; var3 < timer.elapsedTicks; ++var3)
			{
				runTick();
			}
			mcProfiler.endStartSection("preRenderErrors");
			long var7 = System.nanoTime() - var6;
			checkGLError("Pre render");
			RenderBlocks.fancyGrass = gameSettings.fancyGraphics;
			mcProfiler.endStartSection("sound");
			sndManager.setListener(thePlayer, timer.renderPartialTicks);
			if(!isGamePaused)
			{
				sndManager.func_92071_g();
			}
			mcProfiler.endSection();
			mcProfiler.startSection("render");
			mcProfiler.startSection("display");
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			if(!Keyboard.isKeyDown(65))
			{
				Display.update();
			}
			if(thePlayer != null && thePlayer.isEntityInsideOpaqueBlock())
			{
				gameSettings.thirdPersonView = 0;
			}
			mcProfiler.endSection();
			if(!skipRenderWorld)
			{
				mcProfiler.endStartSection("gameRenderer");
				entityRenderer.updateCameraAndRender(timer.renderPartialTicks);
				mcProfiler.endSection();
			}
			GL11.glFlush();
			mcProfiler.endSection();
			if(!Display.isActive() && fullscreen)
			{
				toggleFullscreen();
			}
			if(gameSettings.showDebugInfo && gameSettings.showDebugProfilerChart)
			{
				if(!mcProfiler.profilingEnabled)
				{
					mcProfiler.clearProfiling();
				}
				mcProfiler.profilingEnabled = true;
				displayDebugInfo(var7);
			} else
			{
				mcProfiler.profilingEnabled = false;
				prevFrameTime = System.nanoTime();
			}
			guiAchievement.updateAchievementWindow();
			mcProfiler.startSection("root");
			Thread.yield();
			if(Keyboard.isKeyDown(65))
			{
				Display.update();
			}
			screenshotListener();
			if(mcCanvas != null && !fullscreen && (mcCanvas.getWidth() != displayWidth || mcCanvas.getHeight() != displayHeight))
			{
				displayWidth = mcCanvas.getWidth();
				displayHeight = mcCanvas.getHeight();
				if(displayWidth <= 0)
				{
					displayWidth = 1;
				}
				if(displayHeight <= 0)
				{
					displayHeight = 1;
				}
				resize(displayWidth, displayHeight);
			}
			checkGLError("Post render");
			++fpsCounter;
			boolean var5 = isGamePaused;
			isGamePaused = isSingleplayer() && currentScreen != null && currentScreen.doesGuiPauseGame() && !theIntegratedServer.getPublic();
			if(isIntegratedServerRunning() && thePlayer != null && thePlayer.sendQueue != null && isGamePaused != var5)
			{
				((MemoryConnection) thePlayer.sendQueue.getNetManager()).setGamePaused(isGamePaused);
			}
			while(getSystemTime() >= debugUpdateTime + 1000L)
			{
				debugFPS = fpsCounter;
				debug = debugFPS + " fps, " + WorldRenderer.chunksUpdated + " chunk updates";
				WorldRenderer.chunksUpdated = 0;
				debugUpdateTime += 1000L;
				fpsCounter = 0;
				usageSnooper.addMemoryStatsToSnooper();
				if(!usageSnooper.isSnooperRunning())
				{
					usageSnooper.startSnooper();
				}
			}
			mcProfiler.endSection();
			if(func_90020_K() > 0)
			{
				Display.sync(EntityRenderer.performanceToFps(func_90020_K()));
			}
		}
	}
	
	public void runTick()
	{
		if(rightClickDelayTimer > 0)
		{
			--rightClickDelayTimer;
		}
		mcProfiler.startSection("stats");
		statFileWriter.func_77449_e();
		mcProfiler.endStartSection("gui");
		if(!isGamePaused)
		{
			ingameGUI.updateTick();
		}
		mcProfiler.endStartSection("pick");
		entityRenderer.getMouseOver(1.0F);
		mcProfiler.endStartSection("gameMode");
		if(!isGamePaused && theWorld != null)
		{
			playerController.updateController();
		}
		renderEngine.bindTexture("/terrain.png");
		mcProfiler.endStartSection("textures");
		if(!isGamePaused)
		{
			renderEngine.updateDynamicTextures();
		}
		if(currentScreen == null && thePlayer != null)
		{
			if(thePlayer.getHealth() <= 0)
			{
				displayGuiScreen((GuiScreen) null);
			} else if(thePlayer.isPlayerSleeping() && theWorld != null)
			{
				displayGuiScreen(new GuiSleepMP());
			}
		} else if(currentScreen != null && currentScreen instanceof GuiSleepMP && !thePlayer.isPlayerSleeping())
		{
			displayGuiScreen((GuiScreen) null);
		}
		if(currentScreen != null)
		{
			leftClickCounter = 10000;
		}
		CrashReport var2;
		CrashReportCategory var3;
		if(currentScreen != null)
		{
			try
			{
				currentScreen.handleInput();
			} catch(Throwable var6)
			{
				var2 = CrashReport.makeCrashReport(var6, "Updating screen events");
				var3 = var2.makeCategory("Affected screen");
				var3.addCrashSectionCallable("Screen name", new CallableUpdatingScreenName(this));
				throw new ReportedException(var2);
			}
			if(currentScreen != null)
			{
				try
				{
					currentScreen.guiParticles.update();
				} catch(Throwable var5)
				{
					var2 = CrashReport.makeCrashReport(var5, "Ticking screen particles");
					var3 = var2.makeCategory("Affected screen");
					var3.addCrashSectionCallable("Screen name", new CallableParticleScreenName(this));
					throw new ReportedException(var2);
				}
				try
				{
					currentScreen.updateScreen();
				} catch(Throwable var4)
				{
					var2 = CrashReport.makeCrashReport(var4, "Ticking screen");
					var3 = var2.makeCategory("Affected screen");
					var3.addCrashSectionCallable("Screen name", new CallableTickingScreenName(this));
					throw new ReportedException(var2);
				}
			}
		}
		if(currentScreen == null || currentScreen.allowUserInput)
		{
			mcProfiler.endStartSection("mouse");
			while(Mouse.next())
			{
				KeyBinding.setKeyBindState(Mouse.getEventButton() - 100, Mouse.getEventButtonState());
				if(Mouse.getEventButtonState())
				{
					KeyBinding.onTick(Mouse.getEventButton() - 100);
				}
				long var1 = getSystemTime() - systemTime;
				if(var1 <= 200L)
				{
					int var10 = Mouse.getEventDWheel();
					if(var10 != 0)
					{
						thePlayer.inventory.changeCurrentItem(var10);
						if(gameSettings.noclip)
						{
							if(var10 > 0)
							{
								var10 = 1;
							}
							if(var10 < 0)
							{
								var10 = -1;
							}
							gameSettings.noclipRate += var10 * 0.25F;
						}
					}
					if(currentScreen == null)
					{
						if(!inGameHasFocus && Mouse.getEventButtonState())
						{
							setIngameFocus();
						}
					} else if(currentScreen != null)
					{
						currentScreen.handleMouseInput();
					}
				}
			}
			if(leftClickCounter > 0)
			{
				--leftClickCounter;
			}
			mcProfiler.endStartSection("keyboard");
			boolean var8;
			while(Keyboard.next())
			{
				KeyBinding.setKeyBindState(Keyboard.getEventKey(), Keyboard.getEventKeyState());
				if(Keyboard.getEventKeyState())
				{
					KeyBinding.onTick(Keyboard.getEventKey());
				}
				if(field_83002_am > 0L)
				{
					if(getSystemTime() - field_83002_am >= 6000L) throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
					if(!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61))
					{
						field_83002_am = -1L;
					}
				} else if(Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61))
				{
					field_83002_am = getSystemTime();
				}
				if(Keyboard.getEventKeyState())
				{
					if(Keyboard.getEventKey() == 87)
					{
						toggleFullscreen();
					} else
					{
						if(currentScreen != null)
						{
							currentScreen.handleKeyboardInput();
						} else
						{
							if(Keyboard.getEventKey() == 1)
							{
								displayInGameMenu();
							}
							if(Keyboard.getEventKey() == 31 && Keyboard.isKeyDown(61))
							{
								forceReload();
							}
							if(Keyboard.getEventKey() == 20 && Keyboard.isKeyDown(61))
							{
								renderEngine.refreshTextures();
								renderGlobal.loadRenderers();
							}
							if(Keyboard.getEventKey() == 33 && Keyboard.isKeyDown(61))
							{
								var8 = Keyboard.isKeyDown(42) | Keyboard.isKeyDown(54);
								gameSettings.setOptionValue(EnumOptions.RENDER_DISTANCE, var8 ? -1 : 1);
							}
							if(Keyboard.getEventKey() == 30 && Keyboard.isKeyDown(61))
							{
								renderGlobal.loadRenderers();
							}
							if(Keyboard.getEventKey() == 35 && Keyboard.isKeyDown(61))
							{
								gameSettings.advancedItemTooltips = !gameSettings.advancedItemTooltips;
								gameSettings.saveOptions();
							}
							if(Keyboard.getEventKey() == 48 && Keyboard.isKeyDown(61))
							{
								RenderManager.field_85095_o = !RenderManager.field_85095_o;
							}
							if(Keyboard.getEventKey() == 25 && Keyboard.isKeyDown(61))
							{
								gameSettings.pauseOnLostFocus = !gameSettings.pauseOnLostFocus;
								gameSettings.saveOptions();
							}
							if(Keyboard.getEventKey() == 59)
							{
								gameSettings.hideGUI = !gameSettings.hideGUI;
							}
							if(Keyboard.getEventKey() == 61)
							{
								gameSettings.showDebugInfo = !gameSettings.showDebugInfo;
								gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
							}
							if(Keyboard.getEventKey() == 63)
							{
								++gameSettings.thirdPersonView;
								if(gameSettings.thirdPersonView > 2)
								{
									gameSettings.thirdPersonView = 0;
								}
							}
							if(Keyboard.getEventKey() == 66)
							{
								gameSettings.smoothCamera = !gameSettings.smoothCamera;
							}
						}
						int var9;
						for(var9 = 0; var9 < 9; ++var9)
						{
							if(Keyboard.getEventKey() == 2 + var9)
							{
								thePlayer.inventory.currentItem = var9;
							}
						}
						if(gameSettings.showDebugInfo && gameSettings.showDebugProfilerChart)
						{
							if(Keyboard.getEventKey() == 11)
							{
								updateDebugProfilerName(0);
							}
							for(var9 = 0; var9 < 9; ++var9)
							{
								if(Keyboard.getEventKey() == 2 + var9)
								{
									updateDebugProfilerName(var9 + 1);
								}
							}
						}
					}
				}
			}
			var8 = gameSettings.chatVisibility != 2;
			while(gameSettings.keyBindInventory.isPressed())
			{
				displayGuiScreen(new GuiInventory(thePlayer));
			}
			while(gameSettings.keyBindDrop.isPressed())
			{
				thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
			}
			while(gameSettings.keyBindChat.isPressed() && var8)
			{
				displayGuiScreen(new GuiChat());
			}
			if(currentScreen == null && gameSettings.keyBindCommand.isPressed() && var8)
			{
				displayGuiScreen(new GuiChat("/"));
			}
			if(thePlayer.isUsingItem())
			{
				if(!gameSettings.keyBindUseItem.pressed)
				{
					playerController.onStoppedUsingItem(thePlayer);
				}
				label379: while(true)
				{
					if(!gameSettings.keyBindAttack.isPressed())
					{
						while(gameSettings.keyBindUseItem.isPressed())
						{
							;
						}
						while(true)
						{
							if(gameSettings.keyBindPickBlock.isPressed())
							{
								continue;
							}
							break label379;
						}
					}
				}
			} else
			{
				while(gameSettings.keyBindAttack.isPressed())
				{
					clickMouse(0);
				}
				while(gameSettings.keyBindUseItem.isPressed())
				{
					clickMouse(1);
				}
				while(gameSettings.keyBindPickBlock.isPressed())
				{
					clickMiddleMouseButton();
				}
			}
			if(gameSettings.keyBindUseItem.pressed && rightClickDelayTimer == 0 && !thePlayer.isUsingItem())
			{
				clickMouse(1);
			}
			sendClickBlockToController(0, currentScreen == null && gameSettings.keyBindAttack.pressed && inGameHasFocus);
		}
		if(theWorld != null)
		{
			if(thePlayer != null)
			{
				++joinPlayerCounter;
				if(joinPlayerCounter == 30)
				{
					joinPlayerCounter = 0;
					theWorld.joinEntityInSurroundings(thePlayer);
				}
			}
			mcProfiler.endStartSection("gameRenderer");
			if(!isGamePaused)
			{
				entityRenderer.updateRenderer();
			}
			mcProfiler.endStartSection("levelRenderer");
			if(!isGamePaused)
			{
				renderGlobal.updateClouds();
			}
			mcProfiler.endStartSection("level");
			if(!isGamePaused)
			{
				if(theWorld.lastLightningBolt > 0)
				{
					--theWorld.lastLightningBolt;
				}
				theWorld.updateEntities();
			}
			if(!isGamePaused)
			{
				theWorld.setAllowedSpawnTypes(theWorld.difficultySetting > 0, true);
				try
				{
					theWorld.tick();
				} catch(Throwable var7)
				{
					var2 = CrashReport.makeCrashReport(var7, "Exception in world tick");
					if(theWorld == null)
					{
						var3 = var2.makeCategory("Affected level");
						var3.addCrashSection("Problem", "Level is null!");
					} else
					{
						theWorld.addWorldInfoToCrashReport(var2);
					}
					throw new ReportedException(var2);
				}
			}
			mcProfiler.endStartSection("animateTick");
			if(!isGamePaused && theWorld != null)
			{
				theWorld.doVoidFogParticles(MathHelper.floor_double(thePlayer.posX), MathHelper.floor_double(thePlayer.posY), MathHelper.floor_double(thePlayer.posZ));
			}
			mcProfiler.endStartSection("particles");
			if(!isGamePaused)
			{
				effectRenderer.updateEffects();
			}
		} else if(myNetworkManager != null)
		{
			mcProfiler.endStartSection("pendingConnection");
			myNetworkManager.processReadPackets();
		}
		mcProfiler.endSection();
		systemTime = getSystemTime();
	}
	
	public void scaledTessellator(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		float var7 = 0.00390625F;
		float var8 = 0.00390625F;
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(par1 + 0, par2 + par6, 0.0D, (par3 + 0) * var7, (par4 + par6) * var8);
		var9.addVertexWithUV(par1 + par5, par2 + par6, 0.0D, (par3 + par5) * var7, (par4 + par6) * var8);
		var9.addVertexWithUV(par1 + par5, par2 + 0, 0.0D, (par3 + par5) * var7, (par4 + 0) * var8);
		var9.addVertexWithUV(par1 + 0, par2 + 0, 0.0D, (par3 + 0) * var7, (par4 + 0) * var8);
		var9.draw();
	}
	
	public void scheduleTexturePackRefresh()
	{
		refreshTexturePacksScheduled = true;
	}
	
	private void screenshotListener()
	{
		if(Keyboard.isKeyDown(60))
		{
			if(!isTakingScreenshot)
			{
				isTakingScreenshot = true;
				ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(minecraftDir, displayWidth, displayHeight));
			}
		} else
		{
			isTakingScreenshot = false;
		}
	}
	
	private void sendClickBlockToController(int par1, boolean par2)
	{
		if(!par2)
		{
			leftClickCounter = 0;
		}
		if(par1 != 0 || leftClickCounter <= 0)
		{
			if(par2 && objectMouseOver != null && objectMouseOver.typeOfHit == EnumMovingObjectType.TILE && par1 == 0)
			{
				int var3 = objectMouseOver.blockX;
				int var4 = objectMouseOver.blockY;
				int var5 = objectMouseOver.blockZ;
				playerController.onPlayerDamageBlock(var3, var4, var5, objectMouseOver.sideHit);
				if(thePlayer.isCurrentToolAdventureModeExempt(var3, var4, var5))
				{
					effectRenderer.addBlockHitEffects(var3, var4, var5, objectMouseOver.sideHit);
					thePlayer.swingItem();
				}
			} else
			{
				playerController.resetBlockRemoving();
			}
		}
	}
	
	void setDemo(boolean p_71390_1_)
	{
		isDemo = p_71390_1_;
	}
	
	public void setDimensionAndSpawnPlayer(int par1)
	{
		theWorld.setSpawnLocation();
		theWorld.removeAllEntities();
		int var2 = 0;
		if(thePlayer != null)
		{
			var2 = thePlayer.entityId;
			theWorld.removeEntity(thePlayer);
		}
		renderViewEntity = null;
		thePlayer = playerController.func_78754_a(theWorld);
		thePlayer.dimension = par1;
		renderViewEntity = thePlayer;
		thePlayer.preparePlayerToSpawn();
		theWorld.spawnEntityInWorld(thePlayer);
		playerController.flipPlayer(thePlayer);
		thePlayer.movementInput = new MovementInputFromOptions(gameSettings);
		thePlayer.entityId = var2;
		playerController.setPlayerCapabilities(thePlayer);
		if(currentScreen instanceof GuiGameOver)
		{
			displayGuiScreen((GuiScreen) null);
		}
	}
	
	public void setIngameFocus()
	{
		if(Display.isActive())
		{
			if(!inGameHasFocus)
			{
				inGameHasFocus = true;
				mouseHelper.grabMouseCursor();
				displayGuiScreen((GuiScreen) null);
				leftClickCounter = 10000;
			}
		}
	}
	
	public void setIngameNotInFocus()
	{
		if(inGameHasFocus)
		{
			KeyBinding.unPressAllKeys();
			inGameHasFocus = false;
			mouseHelper.ungrabMouseCursor();
		}
	}
	
	public void setServer(String par1Str, int par2)
	{
		serverName = par1Str;
		serverPort = par2;
	}
	
	public void setServerData(ServerData par1ServerData)
	{
		currentServerData = par1ServerData;
	}
	
	public void shutdown()
	{
		running = false;
	}
	
	public void shutdownMinecraftApplet()
	{
		try
		{
			statFileWriter.syncStats();
			try
			{
				if(downloadResourcesThread != null)
				{
					downloadResourcesThread.closeMinecraft();
				}
			} catch(Exception var9)
			{
				;
			}
			getLogAgent().logInfo("Stopping!");
			try
			{
				this.loadWorld((WorldClient) null);
			} catch(Throwable var8)
			{
				;
			}
			try
			{
				GLAllocation.deleteTexturesAndDisplayLists();
			} catch(Throwable var7)
			{
				;
			}
			sndManager.closeMinecraft();
			Mouse.destroy();
			Keyboard.destroy();
		} finally
		{
			Display.destroy();
			if(!hasCrashed)
			{
				System.exit(0);
			}
		}
		System.gc();
	}
	
	public void startGame() throws LWJGLException
	{
		if(mcCanvas != null)
		{
			Graphics var1 = mcCanvas.getGraphics();
			if(var1 != null)
			{
				var1.setColor(Color.BLACK);
				var1.fillRect(0, 0, displayWidth, displayHeight);
				var1.dispose();
			}
			Display.setParent(mcCanvas);
		} else if(fullscreen)
		{
			Display.setFullscreen(true);
			displayWidth = Display.getDisplayMode().getWidth();
			displayHeight = Display.getDisplayMode().getHeight();
			if(displayWidth <= 0)
			{
				displayWidth = 1;
			}
			if(displayHeight <= 0)
			{
				displayHeight = 1;
			}
		} else
		{
			Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
		}
		Display.setTitle("Minecraft Minecraft 1.5.2");
		getLogAgent().logInfo("LWJGL Version: " + Sys.getVersion());
		try
		{
			Display.create(new PixelFormat().withDepthBits(24));
		} catch(LWJGLException var5)
		{
			var5.printStackTrace();
			try
			{
				Thread.sleep(1000L);
			} catch(InterruptedException var4)
			{
				;
			}
			Display.create();
		}
		OpenGlHelper.initializeTextures();
		mcDataDir = getMinecraftDir();
		saveLoader = new AnvilSaveConverter(new File(mcDataDir, "saves"));
		gameSettings = new GameSettings(this, mcDataDir);
		texturePackList = new TexturePackList(mcDataDir, this);
		renderEngine = new RenderEngine(texturePackList, gameSettings);
		loadScreen();
		fontRenderer = new FontRenderer(gameSettings, "/font/default.png", renderEngine, false);
		standardGalacticFontRenderer = new FontRenderer(gameSettings, "/font/alternate.png", renderEngine, false);
		if(gameSettings.language != null)
		{
			StringTranslate.getInstance().setLanguage(gameSettings.language, false);
			fontRenderer.setUnicodeFlag(StringTranslate.getInstance().isUnicode());
			fontRenderer.setBidiFlag(StringTranslate.isBidirectional(gameSettings.language));
		}
		ColorizerGrass.setGrassBiomeColorizer(renderEngine.getTextureContents("/misc/grasscolor.png"));
		ColorizerFoliage.setFoliageBiomeColorizer(renderEngine.getTextureContents("/misc/foliagecolor.png"));
		entityRenderer = new EntityRenderer(this);
		RenderManager.instance.itemRenderer = new ItemRenderer(this);
		statFileWriter = new StatFileWriter(session, mcDataDir);
		AchievementList.openInventory.setStatStringFormatter(new StatStringFormatKeyInv(this));
		loadScreen();
		Mouse.create();
		mouseHelper = new MouseHelper(mcCanvas, gameSettings);
		checkGLError("Pre startup");
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearDepth(1.0D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		checkGLError("Startup");
		sndManager.loadSoundSettings(gameSettings);
		renderGlobal = new RenderGlobal(this, renderEngine);
		renderEngine.refreshTextureMaps();
		GL11.glViewport(0, 0, displayWidth, displayHeight);
		effectRenderer = new EffectRenderer(theWorld, renderEngine);
		try
		{
			downloadResourcesThread = new ThreadDownloadResources(mcDataDir, this);
			downloadResourcesThread.start();
		} catch(Exception var3)
		{
			;
		}
		checkGLError("Post startup");
		ingameGUI = new GuiIngame(this);
		if(serverName != null)
		{
			displayGuiScreen(new GuiConnecting(new GuiMainMenu(), this, serverName, serverPort));
		} else
		{
			displayGuiScreen(new GuiMainMenu());
		}
		loadingScreen = new LoadingScreenRenderer(this);
		if(gameSettings.fullScreen && !fullscreen)
		{
			toggleFullscreen();
		}
	}
	
	private void startTimerHackThread()
	{
		ThreadClientSleep var1 = new ThreadClientSleep(this, "Timer hack thread");
		var1.setDaemon(true);
		var1.start();
	}
	
	public void toggleFullscreen()
	{
		try
		{
			fullscreen = !fullscreen;
			if(fullscreen)
			{
				Display.setDisplayMode(Display.getDesktopDisplayMode());
				displayWidth = Display.getDisplayMode().getWidth();
				displayHeight = Display.getDisplayMode().getHeight();
				if(displayWidth <= 0)
				{
					displayWidth = 1;
				}
				if(displayHeight <= 0)
				{
					displayHeight = 1;
				}
			} else
			{
				if(mcCanvas != null)
				{
					displayWidth = mcCanvas.getWidth();
					displayHeight = mcCanvas.getHeight();
				} else
				{
					displayWidth = tempDisplayWidth;
					displayHeight = tempDisplayHeight;
				}
				if(displayWidth <= 0)
				{
					displayWidth = 1;
				}
				if(displayHeight <= 0)
				{
					displayHeight = 1;
				}
			}
			if(currentScreen != null)
			{
				resize(displayWidth, displayHeight);
			}
			Display.setFullscreen(fullscreen);
			Display.setVSyncEnabled(gameSettings.enableVsync);
			Display.update();
		} catch(Exception var2)
		{
			var2.printStackTrace();
		}
	}
	
	private void updateDebugProfilerName(int par1)
	{
		List var2 = mcProfiler.getProfilingData(debugProfilerName);
		if(var2 != null && !var2.isEmpty())
		{
			ProfilerResult var3 = (ProfilerResult) var2.remove(0);
			if(par1 == 0)
			{
				if(var3.field_76331_c.length() > 0)
				{
					int var4 = debugProfilerName.lastIndexOf(".");
					if(var4 >= 0)
					{
						debugProfilerName = debugProfilerName.substring(0, var4);
					}
				}
			} else
			{
				--par1;
				if(par1 < var2.size() && !((ProfilerResult) var2.get(par1)).field_76331_c.equals("unspecified"))
				{
					if(debugProfilerName.length() > 0)
					{
						debugProfilerName = debugProfilerName + ".";
					}
					debugProfilerName = debugProfilerName + ((ProfilerResult) var2.get(par1)).field_76331_c;
				}
			}
		}
	}
	
	public static File getAppDir(String p_71394_0_)
	{
		String var1 = System.getProperty("user.home", ".");
		File var2;
		switch(EnumOSHelper.field_90049_a[getOs().ordinal()])
		{
			case 1:
			case 2:
				var2 = new File(var1, '.' + p_71394_0_ + '/');
				break;
			case 3:
				String var3 = System.getenv("APPDATA");
				if(var3 != null)
				{
					var2 = new File(var3, "." + p_71394_0_ + '/');
				} else
				{
					var2 = new File(var1, '.' + p_71394_0_ + '/');
				}
				break;
			case 4:
				var2 = new File(var1, "Library/Application Support/" + p_71394_0_);
				break;
			default:
				var2 = new File(var1, p_71394_0_ + '/');
		}
		if(!var2.exists() && !var2.mkdirs()) throw new RuntimeException("The working directory could not be created: " + var2);
		else return var2;
	}
	
	public static int getGLMaximumTextureSize()
	{
		for(int var0 = 16384; var0 > 0; var0 >>= 1)
		{
			GL11.glTexImage2D(GL11.GL_PROXY_TEXTURE_2D, 0, GL11.GL_RGBA, var0, var0, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
			int var1 = GL11.glGetTexLevelParameteri(GL11.GL_PROXY_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
			if(var1 != 0) return var0;
		}
		return -1;
	}
	
	public static Minecraft getMinecraft()
	{
		return theMinecraft;
	}
	
	public static File getMinecraftDir()
	{
		if(minecraftDir == null)
		{
			minecraftDir = getAppDir("minecraft");
		}
		return minecraftDir;
	}
	
	public static EnumOS getOs()
	{
		String var0 = System.getProperty("os.name").toLowerCase();
		return var0.contains("win") ? EnumOS.WINDOWS : var0.contains("mac") ? EnumOS.MACOS : var0.contains("solaris") ? EnumOS.SOLARIS : var0.contains("sunos") ? EnumOS.SOLARIS : var0.contains("linux") ? EnumOS.LINUX : var0.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN;
	}
	
	public static long getSystemTime()
	{
		return Sys.getTime() * 1000L / Sys.getTimerResolution();
	}
	
	public static boolean isAmbientOcclusionEnabled()
	{
		return theMinecraft != null && theMinecraft.gameSettings.ambientOcclusion != 0;
	}
	
	public static boolean isFancyGraphicsEnabled()
	{
		return theMinecraft != null && theMinecraft.gameSettings.fancyGraphics;
	}
	
	public static boolean isGuiEnabled()
	{
		return theMinecraft == null || !theMinecraft.gameSettings.hideGUI;
	}
	
	public static void main(String[] p_main_0_)
	{
		HashMap var1 = new HashMap();
		boolean var2 = false;
		boolean var3 = true;
		boolean var4 = false;
		String var5 = "Player" + getSystemTime() % 1000L;
		String var6 = var5;
		if(p_main_0_.length > 0)
		{
			var6 = p_main_0_[0];
		}
		String var7 = "-";
		if(p_main_0_.length > 1)
		{
			var7 = p_main_0_[1];
		}
		ArrayList var8 = new ArrayList();
		for(int var9 = 2; var9 < p_main_0_.length; ++var9)
		{
			String var10 = p_main_0_[var9];
			String var11 = var9 == p_main_0_.length - 1 ? null : p_main_0_[var9 + 1];
			boolean var12 = false;
			if(!var10.equals("-demo") && !var10.equals("--demo"))
			{
				if(var10.equals("--applet"))
				{
					var3 = false;
				} else if(var10.equals("--password") && var11 != null)
				{
					String[] var13 = HttpUtil.loginToMinecraft((ILogAgent) null, var6, var11);
					if(var13 != null)
					{
						var6 = var13[0];
						var7 = var13[1];
						var8.add("Logged in insecurely as " + var6);
					} else
					{
						var8.add("Could not log in as " + var6 + " with given password");
					}
					var12 = true;
				}
			} else
			{
				var2 = true;
			}
			if(var12)
			{
				++var9;
			}
		}
		if(var6.contains("@") && var7.length() <= 1)
		{
			var6 = var5;
		}
		var1.put("demo", "" + var2);
		var1.put("stand-alone", "" + var3);
		var1.put("username", var6);
		var1.put("fullscreen", "" + var4);
		var1.put("sessionid", var7);
		Frame var16 = new Frame();
		var16.setTitle("Minecraft");
		var16.setBackground(Color.BLACK);
		JPanel var17 = new JPanel();
		var16.setLayout(new BorderLayout());
		var17.setPreferredSize(new Dimension(854, 480));
		var16.add(var17, "Center");
		var16.pack();
		var16.setLocationRelativeTo((Component) null);
		var16.setVisible(true);
		var16.addWindowListener(new GameWindowListener());
		MinecraftFakeLauncher var15 = new MinecraftFakeLauncher(var1);
		MinecraftApplet var18 = new MinecraftApplet();
		var18.setStub(var15);
		var15.setLayout(new BorderLayout());
		var15.add(var18, "Center");
		var15.validate();
		var16.removeAll();
		var16.setLayout(new BorderLayout());
		var16.add(var15, "Center");
		var16.validate();
		var18.init();
		Iterator var19 = var8.iterator();
		while(var19.hasNext())
		{
			String var14 = (String) var19.next();
			getMinecraft().getLogAgent().logInfo(var14);
		}
		var18.start();
		Runtime.getRuntime().addShutdownHook(new ThreadShutdown());
	}
	
	public static void stopIntegratedServer()
	{
		if(theMinecraft != null)
		{
			IntegratedServer var0 = theMinecraft.getIntegratedServer();
			if(var0 != null)
			{
				var0.stopServer();
			}
		}
	}
}
