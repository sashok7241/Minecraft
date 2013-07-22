package net.minecraft.client;

import java.awt.DisplayMode;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.AchievementList;
import net.minecraft.src.AnimationMetadataSection;
import net.minecraft.src.AnimationMetadataSectionSerializer;
import net.minecraft.src.AnvilSaveConverter;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.CrashReport;
import net.minecraft.src.CrashReportCategory;
import net.minecraft.src.DefaultResourcePack;
import net.minecraft.src.EffectRenderer;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityItemFrame;
import net.minecraft.src.EntityLeashKnot;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.EnumOS;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.FoliageColorReloadListener;
import net.minecraft.src.FontMetadataSection;
import net.minecraft.src.FontMetadataSectionSerializer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GrassColorReloadListener;
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
import net.minecraft.src.I18n;
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
import net.minecraft.src.LanguageManager;
import net.minecraft.src.LanguageMetadataSection;
import net.minecraft.src.LanguageMetadataSectionSerializer;
import net.minecraft.src.LoadingScreenRenderer;
import net.minecraft.src.LogAgent;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MemoryConnection;
import net.minecraft.src.MetadataSerializer;
import net.minecraft.src.MinecraftError;
import net.minecraft.src.MouseHelper;
import net.minecraft.src.MovementInputFromOptions;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.OpenGlHelper;
import net.minecraft.src.PackMetadataSection;
import net.minecraft.src.PackMetadataSectionSerializer;
import net.minecraft.src.PlayerControllerMP;
import net.minecraft.src.PlayerUsageSnooper;
import net.minecraft.src.Profiler;
import net.minecraft.src.ProfilerResult;
import net.minecraft.src.ReloadableResourceManager;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderManager;
import net.minecraft.src.ReportedException;
import net.minecraft.src.ResourceLocation;
import net.minecraft.src.ResourceManager;
import net.minecraft.src.ResourcePackRepository;
import net.minecraft.src.ResourcePackRepositoryEntry;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.ScreenShotHelper;
import net.minecraft.src.ServerData;
import net.minecraft.src.Session;
import net.minecraft.src.SimpleReloadableResourceManager;
import net.minecraft.src.SoundManager;
import net.minecraft.src.StatFileWriter;
import net.minecraft.src.StatList;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TextureManager;
import net.minecraft.src.TextureMap;
import net.minecraft.src.TextureMetadataSection;
import net.minecraft.src.TextureMetadataSectionSerializer;
import net.minecraft.src.Timer;
import net.minecraft.src.Util;
import net.minecraft.src.WorldClient;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldRenderer;
import net.minecraft.src.WorldSettings;

public class Minecraft implements IPlayerUsage
{
	private static final ResourceLocation field_110444_H = new ResourceLocation("textures/gui/title/mojang.png");
	public static final boolean field_142025_a = Util.func_110647_a() == EnumOS.MACOS;
	public static byte[] memoryReserve = new byte[10485760];
	private static final List field_110445_I = Lists.newArrayList(new DisplayMode[] { new DisplayMode(2560, 1600), new DisplayMode(2880, 1800) });
	private final ILogAgent field_94139_O;
	private final File field_130070_K;
	private ServerData currentServerData;
	private TextureManager renderEngine;
	private static Minecraft theMinecraft;
	public PlayerControllerMP playerController;
	private boolean fullscreen;
	private boolean hasCrashed;
	private CrashReport crashReporter;
	public int displayWidth;
	public int displayHeight;
	private Timer timer = new Timer(20.0F);
	private PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.func_130071_aq());
	public WorldClient theWorld;
	public RenderGlobal renderGlobal;
	public EntityClientPlayerMP thePlayer;
	public EntityLivingBase renderViewEntity;
	public EntityLivingBase pointedEntityLiving;
	public EffectRenderer effectRenderer;
	private final Session session;
	private boolean isGamePaused;
	public FontRenderer fontRenderer;
	public FontRenderer standardGalacticFontRenderer;
	public GuiScreen currentScreen;
	public LoadingScreenRenderer loadingScreen;
	public EntityRenderer entityRenderer;
	private int leftClickCounter;
	private int tempDisplayWidth;
	private int tempDisplayHeight;
	private IntegratedServer theIntegratedServer;
	public GuiAchievement guiAchievement;
	public GuiIngame ingameGUI;
	public boolean skipRenderWorld;
	public MovingObjectPosition objectMouseOver;
	public GameSettings gameSettings;
	public SoundManager sndManager;
	public MouseHelper mouseHelper;
	public final File mcDataDir;
	private final File field_110446_Y;
	private final String field_110447_Z;
	private final Proxy field_110453_aa;
	private ISaveFormat saveLoader;
	private static int debugFPS;
	private int rightClickDelayTimer;
	private boolean refreshTexturePacksScheduled;
	public StatFileWriter statFileWriter;
	private String serverName;
	private int serverPort;
	boolean isTakingScreenshot;
	public boolean inGameHasFocus;
	long systemTime = getSystemTime();
	private int joinPlayerCounter;
	private final boolean isDemo;
	private INetworkManager myNetworkManager;
	private boolean integratedServerIsRunning;
	public final Profiler mcProfiler = new Profiler();
	private long field_83002_am = -1L;
	private ReloadableResourceManager field_110451_am;
	private final MetadataSerializer field_110452_an = new MetadataSerializer();
	private List field_110449_ao = Lists.newArrayList();
	private DefaultResourcePack field_110450_ap;
	private ResourcePackRepository field_110448_aq;
	private LanguageManager field_135017_as;
	volatile boolean running = true;
	public String debug = "";
	long debugUpdateTime = getSystemTime();
	int fpsCounter;
	long prevFrameTime = -1L;
	private String debugProfilerName = "root";
	
	public Minecraft(Session par1Session, int par2, int par3, boolean par4, boolean par5, File par6File, File par7File, File par8File, Proxy par9Proxy, String par10Str)
	{
		theMinecraft = this;
		field_94139_O = new LogAgent("Minecraft-Client", " [CLIENT]", new File(par6File, "output-client.log").getAbsolutePath());
		mcDataDir = par6File;
		field_110446_Y = par7File;
		field_130070_K = par8File;
		field_110447_Z = par10Str;
		field_110450_ap = new DefaultResourcePack(field_110446_Y);
		func_110435_P();
		field_110453_aa = par9Proxy;
		startTimerHackThread();
		session = par1Session;
		field_94139_O.logInfo("Setting user: " + par1Session.func_111285_a());
		field_94139_O.logInfo("(Session ID is " + par1Session.func_111286_b() + ")");
		isDemo = par5;
		displayWidth = par2;
		displayHeight = par3;
		tempDisplayWidth = par2;
		tempDisplayHeight = par3;
		fullscreen = par4;
		ImageIO.setUseCache(false);
		StatList.nopInit();
	}
	
	public CrashReport addGraphicsAndWorldToCrashReport(CrashReport par1CrashReport)
	{
		par1CrashReport.func_85056_g().addCrashSectionCallable("Launched Version", new CallableLaunchedVersion(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("LWJGL", new CallableLWJGLVersion(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("OpenGL", new CallableGLInfo(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("Is Modded", new CallableModded(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("Type", new CallableType2(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("Resource Pack", new CallableTexturePack(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("Current Language", new CallableClientProfiler(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("Profiler Position", new CallableClientMemoryStats(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("Vec3 Pool Size", new MinecraftINNER13(this));
		if(theWorld != null)
		{
			theWorld.addWorldInfoToCrashReport(par1CrashReport);
		}
		return par1CrashReport;
	}
	
	@Override public void addServerStatsToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper)
	{
		par1PlayerUsageSnooper.addData("fps", Integer.valueOf(debugFPS));
		par1PlayerUsageSnooper.addData("texpack_name", field_110448_aq.func_110610_d());
		par1PlayerUsageSnooper.addData("vsync_enabled", Boolean.valueOf(gameSettings.enableVsync));
		par1PlayerUsageSnooper.addData("display_frequency", Integer.valueOf(Display.getDisplayMode().getFrequency()));
		par1PlayerUsageSnooper.addData("display_type", fullscreen ? "fullscreen" : "windowed");
		par1PlayerUsageSnooper.addData("run_time", Long.valueOf((MinecraftServer.func_130071_aq() - par1PlayerUsageSnooper.func_130105_g()) / 60L * 1000L));
		if(theIntegratedServer != null && theIntegratedServer.getPlayerUsageSnooper() != null)
		{
			par1PlayerUsageSnooper.addData("snooper_partner", theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
		}
	}
	
	@Override public void addServerTypeToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper)
	{
		par1PlayerUsageSnooper.addData("opengl_version", GL11.glGetString(GL11.GL_VERSION));
		par1PlayerUsageSnooper.addData("opengl_vendor", GL11.glGetString(GL11.GL_VENDOR));
		par1PlayerUsageSnooper.addData("client_brand", ClientBrandRetriever.getClientModName());
		par1PlayerUsageSnooper.addData("launched_version", field_110447_Z);
		ContextCapabilities var2 = GLContext.getCapabilities();
		par1PlayerUsageSnooper.addData("gl_caps[ARB_multitexture]", Boolean.valueOf(var2.GL_ARB_multitexture));
		par1PlayerUsageSnooper.addData("gl_caps[ARB_multisample]", Boolean.valueOf(var2.GL_ARB_multisample));
		par1PlayerUsageSnooper.addData("gl_caps[ARB_texture_cube_map]", Boolean.valueOf(var2.GL_ARB_texture_cube_map));
		par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_blend]", Boolean.valueOf(var2.GL_ARB_vertex_blend));
		par1PlayerUsageSnooper.addData("gl_caps[ARB_matrix_palette]", Boolean.valueOf(var2.GL_ARB_matrix_palette));
		par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_program]", Boolean.valueOf(var2.GL_ARB_vertex_program));
		par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_shader]", Boolean.valueOf(var2.GL_ARB_vertex_shader));
		par1PlayerUsageSnooper.addData("gl_caps[ARB_fragment_program]", Boolean.valueOf(var2.GL_ARB_fragment_program));
		par1PlayerUsageSnooper.addData("gl_caps[ARB_fragment_shader]", Boolean.valueOf(var2.GL_ARB_fragment_shader));
		par1PlayerUsageSnooper.addData("gl_caps[ARB_shader_objects]", Boolean.valueOf(var2.GL_ARB_shader_objects));
		par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_buffer_object]", Boolean.valueOf(var2.GL_ARB_vertex_buffer_object));
		par1PlayerUsageSnooper.addData("gl_caps[ARB_framebuffer_object]", Boolean.valueOf(var2.GL_ARB_framebuffer_object));
		par1PlayerUsageSnooper.addData("gl_caps[ARB_pixel_buffer_object]", Boolean.valueOf(var2.GL_ARB_pixel_buffer_object));
		par1PlayerUsageSnooper.addData("gl_caps[ARB_uniform_buffer_object]", Boolean.valueOf(var2.GL_ARB_uniform_buffer_object));
		par1PlayerUsageSnooper.addData("gl_caps[ARB_texture_non_power_of_two]", Boolean.valueOf(var2.GL_ARB_texture_non_power_of_two));
		par1PlayerUsageSnooper.addData("gl_caps[gl_max_vertex_uniforms]", Integer.valueOf(GL11.glGetInteger(GL20.GL_MAX_VERTEX_UNIFORM_COMPONENTS)));
		par1PlayerUsageSnooper.addData("gl_caps[gl_max_fragment_uniforms]", Integer.valueOf(GL11.glGetInteger(GL20.GL_MAX_FRAGMENT_UNIFORM_COMPONENTS)));
		par1PlayerUsageSnooper.addData("gl_max_texture_size", Integer.valueOf(getGLMaximumTextureSize()));
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
				} else if(objectMouseOver.entityHit instanceof EntityLeashKnot)
				{
					var2 = Item.field_111214_ch.itemID;
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
		File var2 = new File(getMinecraft().mcDataDir, "crash-reports");
		File var3 = new File(var2, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
		System.out.println(par1CrashReport.getCompleteReport());
		if(par1CrashReport.getFile() != null)
		{
			System.out.println("#@!@# Game crashed! Crash report saved to: #@!@# " + par1CrashReport.getFile());
			System.exit(-1);
		} else if(par1CrashReport.saveToFile(var3, getLogAgent()))
		{
			System.out.println("#@!@# Game crashed! Crash report saved to: #@!@# " + var3.getAbsolutePath());
			System.exit(-1);
		} else
		{
			System.out.println("#@?@# Game crashed! Crash report could not be saved. #@?@#");
			System.exit(-2);
		}
	}
	
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
		} else if(par1GuiScreen == null && thePlayer.func_110143_aJ() <= 0.0F)
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
	
	public Session func_110432_I()
	{
		return session;
	}
	
	public TextureManager func_110434_K()
	{
		return renderEngine;
	}
	
	private void func_110435_P()
	{
		field_110449_ao.add(field_110450_ap);
	}
	
	public void func_110436_a()
	{
		ArrayList var1 = Lists.newArrayList(field_110449_ao);
		Iterator var2 = field_110448_aq.func_110613_c().iterator();
		while(var2.hasNext())
		{
			ResourcePackRepositoryEntry var3 = (ResourcePackRepositoryEntry) var2.next();
			var1.add(var3.func_110514_c());
		}
		field_135017_as.func_135043_a(var1);
		field_110451_am.func_110541_a(var1);
		if(renderGlobal != null)
		{
			renderGlobal.loadRenderers();
		}
	}
	
	public Proxy func_110437_J()
	{
		return field_110453_aa;
	}
	
	public ResourcePackRepository func_110438_M()
	{
		return field_110448_aq;
	}
	
	private ByteBuffer func_110439_b(File par1File) throws IOException
	{
		BufferedImage var2 = ImageIO.read(par1File);
		int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), (int[]) null, 0, var2.getWidth());
		ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
		int[] var5 = var3;
		int var6 = var3.length;
		for(int var7 = 0; var7 < var6; ++var7)
		{
			int var8 = var5[var7];
			var4.putInt(var8 << 8 | var8 >> 24 & 255);
		}
		var4.flip();
		return var4;
	}
	
	private void func_110441_Q() throws LWJGLException
	{
		HashSet var1 = new HashSet();
		Collections.addAll(var1, Display.getAvailableDisplayModes());
		DisplayMode var2 = Display.getDesktopDisplayMode();
		if(!var1.contains(var2) && Util.func_110647_a() == EnumOS.MACOS)
		{
			Iterator var3 = field_110445_I.iterator();
			while(var3.hasNext())
			{
				DisplayMode var4 = (DisplayMode) var3.next();
				boolean var5 = true;
				Iterator var6 = var1.iterator();
				DisplayMode var7;
				while(var6.hasNext())
				{
					var7 = (DisplayMode) var6.next();
					if(var7.getBitsPerPixel() == 32 && var7.getWidth() == var4.getWidth() && var7.getHeight() == var4.getHeight())
					{
						var5 = false;
						break;
					}
				}
				if(!var5)
				{
					var6 = var1.iterator();
					while(var6.hasNext())
					{
						var7 = (DisplayMode) var6.next();
						if(var7.getBitsPerPixel() == 32 && var7.getWidth() == var4.getWidth() / 2 && var7.getHeight() == var4.getHeight() / 2)
						{
							var2 = var7;
							break;
						}
					}
				}
			}
		}
		Display.setDisplayMode(var2);
		displayWidth = var2.getWidth();
		displayHeight = var2.getHeight();
	}
	
	public ResourceManager func_110442_L()
	{
		return field_110451_am;
	}
	
	public LanguageManager func_135016_M()
	{
		return field_135017_as;
	}
	
	private int func_90020_K()
	{
		return currentScreen != null && currentScreen instanceof GuiMainMenu ? 2 : gameSettings.limitFramerate;
	}
	
	public void func_99999_d()
	{
		running = true;
		CrashReport var2;
		try
		{
			startGame();
		} catch(Throwable var11)
		{
			var2 = CrashReport.makeCrashReport(var11, "Initializing game");
			var2.makeCategory("Initialization");
			displayCrashReport(addGraphicsAndWorldToCrashReport(var2));
			return;
		}
		try
		{
			while(running)
			{
				if(running)
				{
					if(hasCrashed && crashReporter != null)
					{
						displayCrashReport(crashReporter);
						return;
					}
					if(refreshTexturePacksScheduled)
					{
						refreshTexturePacksScheduled = false;
						func_110436_a();
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
					continue;
				}
			}
		} catch(MinecraftError var12)
		{
		} catch(ReportedException var13)
		{
			addGraphicsAndWorldToCrashReport(var13.getCrashReport());
			freeMemory();
			var13.printStackTrace();
			displayCrashReport(var13.getCrashReport());
		} catch(Throwable var14)
		{
			var2 = addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", var14));
			freeMemory();
			var14.printStackTrace();
			displayCrashReport(var2);
		} finally
		{
			shutdownMinecraftApplet();
		}
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
	
	public String getWorldProviderName()
	{
		return theWorld.getProviderName();
	}
	
	public boolean handleClientCommand(String par1Str)
	{
		return false;
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
		loadingScreen.displayProgressMessage(I18n.func_135053_a("menu.loadingLevel"));
		while(!theIntegratedServer.serverIsInRunLoop())
		{
			String var6 = theIntegratedServer.getUserMessage();
			if(var6 != null)
			{
				loadingScreen.resetProgresAndWorkingMessage(I18n.func_135053_a(var6));
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
		renderEngine.func_110577_a(field_110444_H);
		Tessellator var2 = Tessellator.instance;
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
		Display.update();
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
	
	private void runGameLoop()
	{
		AxisAlignedBB.getAABBPool().cleanPool();
		if(theWorld != null)
		{
			theWorld.getWorldVec3Pool().clear();
		}
		mcProfiler.startSection("root");
		if(Display.isCloseRequested())
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
		if(!fullscreen && Display.wasResized())
		{
			displayWidth = Display.getWidth();
			displayHeight = Display.getHeight();
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
		mcProfiler.endStartSection("textures");
		if(!isGamePaused)
		{
			renderEngine.func_110550_d();
		}
		if(currentScreen == null && thePlayer != null)
		{
			if(thePlayer.func_110143_aJ() <= 0.0F)
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
					currentScreen.updateScreen();
				} catch(Throwable var5)
				{
					var2 = CrashReport.makeCrashReport(var5, "Ticking screen");
					var3 = var2.makeCategory("Affected screen");
					var3.addCrashSectionCallable("Screen name", new CallableParticleScreenName(this));
					throw new ReportedException(var2);
				}
			}
		}
		if(currentScreen == null || currentScreen.allowUserInput)
		{
			mcProfiler.endStartSection("mouse");
			int var1;
			while(Mouse.next())
			{
				var1 = Mouse.getEventButton();
				if(field_142025_a && var1 == 0 && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)))
				{
					var1 = 1;
				}
				KeyBinding.setKeyBindState(var1 - 100, Mouse.getEventButtonState());
				if(Mouse.getEventButtonState())
				{
					KeyBinding.onTick(var1 - 100);
				}
				long var9 = getSystemTime() - systemTime;
				if(var9 <= 200L)
				{
					int var4 = Mouse.getEventDWheel();
					if(var4 != 0)
					{
						thePlayer.inventory.changeCurrentItem(var4);
						if(gameSettings.noclip)
						{
							if(var4 > 0)
							{
								var4 = 1;
							}
							if(var4 < 0)
							{
								var4 = -1;
							}
							gameSettings.noclipRate += var4 * 0.25F;
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
								func_110436_a();
							}
							if(Keyboard.getEventKey() == 20 && Keyboard.isKeyDown(61))
							{
								func_110436_a();
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
						for(var1 = 0; var1 < 9; ++var1)
						{
							if(Keyboard.getEventKey() == 2 + var1)
							{
								thePlayer.inventory.currentItem = var1;
							}
						}
						if(gameSettings.showDebugInfo && gameSettings.showDebugProfilerChart)
						{
							if(Keyboard.getEventKey() == 11)
							{
								updateDebugProfilerName(0);
							}
							for(var1 = 0; var1 < 9; ++var1)
							{
								if(Keyboard.getEventKey() == 2 + var1)
								{
									updateDebugProfilerName(var1 + 1);
								}
							}
						}
					}
				}
			}
			var8 = gameSettings.chatVisibility != 2;
			while(gameSettings.keyBindInventory.isPressed())
			{
				if(playerController.func_110738_j())
				{
					thePlayer.func_110322_i();
				} else
				{
					displayGuiScreen(new GuiInventory(thePlayer));
				}
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
				label381: while(true)
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
							break label381;
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
	
	private void screenshotListener()
	{
		if(Keyboard.isKeyDown(60))
		{
			if(!isTakingScreenshot)
			{
				isTakingScreenshot = true;
				ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(mcDataDir, displayWidth, displayHeight));
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
	
	public void setDimensionAndSpawnPlayer(int par1)
	{
		theWorld.setSpawnLocation();
		theWorld.removeAllEntities();
		int var2 = 0;
		String var3 = null;
		if(thePlayer != null)
		{
			var2 = thePlayer.entityId;
			theWorld.removeEntity(thePlayer);
			var3 = thePlayer.func_142021_k();
		}
		renderViewEntity = null;
		thePlayer = playerController.func_78754_a(theWorld);
		thePlayer.dimension = par1;
		renderViewEntity = thePlayer;
		thePlayer.preparePlayerToSpawn();
		thePlayer.func_142020_c(var3);
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
			getLogAgent().logInfo("Stopping!");
			try
			{
				this.loadWorld((WorldClient) null);
			} catch(Throwable var7)
			{
				;
			}
			try
			{
				GLAllocation.deleteTexturesAndDisplayLists();
			} catch(Throwable var6)
			{
				;
			}
			sndManager.closeMinecraft();
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
	
	private void startGame() throws LWJGLException
	{
		gameSettings = new GameSettings(this, mcDataDir);
		if(gameSettings.overrideHeight > 0 && gameSettings.overrideWidth > 0)
		{
			displayWidth = gameSettings.overrideWidth;
			displayHeight = gameSettings.overrideHeight;
		}
		if(fullscreen)
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
		Display.setResizable(true);
		Display.setTitle("Minecraft 1.6.2");
		getLogAgent().logInfo("LWJGL Version: " + Sys.getVersion());
		if(Util.func_110647_a() != EnumOS.MACOS)
		{
			try
			{
				Display.setIcon(new ByteBuffer[] { func_110439_b(new File(field_110446_Y, "/icons/icon_16x16.png")), func_110439_b(new File(field_110446_Y, "/icons/icon_32x32.png")) });
			} catch(IOException var5)
			{
				var5.printStackTrace();
			}
		}
		try
		{
			Display.create(new PixelFormat().withDepthBits(24));
		} catch(LWJGLException var4)
		{
			var4.printStackTrace();
			try
			{
				Thread.sleep(1000L);
			} catch(InterruptedException var3)
			{
				;
			}
			if(fullscreen)
			{
				func_110441_Q();
			}
			Display.create();
		}
		OpenGlHelper.initializeTextures();
		guiAchievement = new GuiAchievement(this);
		field_110452_an.func_110504_a(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
		field_110452_an.func_110504_a(new FontMetadataSectionSerializer(), FontMetadataSection.class);
		field_110452_an.func_110504_a(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
		field_110452_an.func_110504_a(new PackMetadataSectionSerializer(), PackMetadataSection.class);
		field_110452_an.func_110504_a(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
		saveLoader = new AnvilSaveConverter(new File(mcDataDir, "saves"));
		field_110448_aq = new ResourcePackRepository(field_130070_K, field_110450_ap, field_110452_an, gameSettings);
		field_110451_am = new SimpleReloadableResourceManager(field_110452_an);
		field_135017_as = new LanguageManager(field_110452_an, gameSettings.language);
		field_110451_am.func_110542_a(field_135017_as);
		func_110436_a();
		renderEngine = new TextureManager(field_110451_am);
		field_110451_am.func_110542_a(renderEngine);
		sndManager = new SoundManager(field_110451_am, gameSettings, field_110446_Y);
		field_110451_am.func_110542_a(sndManager);
		loadScreen();
		fontRenderer = new FontRenderer(gameSettings, new ResourceLocation("textures/font/ascii.png"), renderEngine, false);
		if(gameSettings.language != null)
		{
			fontRenderer.setUnicodeFlag(field_135017_as.func_135042_a());
			fontRenderer.setBidiFlag(field_135017_as.func_135044_b());
		}
		standardGalacticFontRenderer = new FontRenderer(gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), renderEngine, false);
		field_110451_am.func_110542_a(fontRenderer);
		field_110451_am.func_110542_a(standardGalacticFontRenderer);
		field_110451_am.func_110542_a(new GrassColorReloadListener());
		field_110451_am.func_110542_a(new FoliageColorReloadListener());
		RenderManager.instance.itemRenderer = new ItemRenderer(this);
		entityRenderer = new EntityRenderer(this);
		statFileWriter = new StatFileWriter(session, mcDataDir);
		AchievementList.openInventory.setStatStringFormatter(new StatStringFormatKeyInv(this));
		mouseHelper = new MouseHelper();
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
		renderGlobal = new RenderGlobal(this);
		renderEngine.func_130088_a(TextureMap.field_110575_b, new TextureMap(0, "textures/blocks"));
		renderEngine.func_130088_a(TextureMap.field_110576_c, new TextureMap(1, "textures/items"));
		GL11.glViewport(0, 0, displayWidth, displayHeight);
		effectRenderer = new EffectRenderer(theWorld, renderEngine);
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
				func_110441_Q();
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
				Display.setDisplayMode(new DisplayMode(tempDisplayWidth, tempDisplayHeight));
				displayWidth = tempDisplayWidth;
				displayHeight = tempDisplayHeight;
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
	
	static String func_110431_a(Minecraft par0Minecraft)
	{
		return par0Minecraft.field_110447_Z;
	}
	
	static LanguageManager func_142024_b(Minecraft par0Minecraft)
	{
		return par0Minecraft.field_135017_as;
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
