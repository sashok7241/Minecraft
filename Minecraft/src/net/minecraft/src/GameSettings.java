package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import net.minecraft.client.Minecraft;

public class GameSettings
{
	private static final String[] RENDER_DISTANCES = new String[] { "options.renderDistance.far", "options.renderDistance.normal", "options.renderDistance.short", "options.renderDistance.tiny" };
	private static final String[] DIFFICULTIES = new String[] { "options.difficulty.peaceful", "options.difficulty.easy", "options.difficulty.normal", "options.difficulty.hard" };
	private static final String[] GUISCALES = new String[] { "options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large" };
	private static final String[] CHAT_VISIBILITIES = new String[] { "options.chat.visibility.full", "options.chat.visibility.system", "options.chat.visibility.hidden" };
	private static final String[] PARTICLES = new String[] { "options.particles.all", "options.particles.decreased", "options.particles.minimal" };
	private static final String[] LIMIT_FRAMERATES = new String[] { "performance.max", "performance.balanced", "performance.powersaver" };
	private static final String[] AMBIENT_OCCLUSIONS = new String[] { "options.ao.off", "options.ao.min", "options.ao.max" };
	public float musicVolume = 1.0F;
	public float soundVolume = 1.0F;
	public float mouseSensitivity = 0.5F;
	public boolean invertMouse = false;
	public int renderDistance = 0;
	public boolean viewBobbing = true;
	public boolean anaglyph = false;
	public boolean advancedOpengl = false;
	public int limitFramerate = 1;
	public boolean fancyGraphics = true;
	public int ambientOcclusion = 2;
	public boolean clouds = true;
	public String skin = "Default";
	public int chatVisibility = 0;
	public boolean chatColours = true;
	public boolean chatLinks = true;
	public boolean chatLinksPrompt = true;
	public float chatOpacity = 1.0F;
	public boolean serverTextures = true;
	public boolean snooperEnabled = true;
	public boolean fullScreen = false;
	public boolean enableVsync = true;
	public boolean hideServerAddress = false;
	public boolean advancedItemTooltips = false;
	public boolean pauseOnLostFocus = true;
	public boolean showCape = true;
	public boolean touchscreen = false;
	public int overrideWidth = 0;
	public int overrideHeight = 0;
	public boolean heldItemTooltips = true;
	public float chatScale = 1.0F;
	public float chatWidth = 1.0F;
	public float chatHeightUnfocused = 0.44366196F;
	public float chatHeightFocused = 1.0F;
	public KeyBinding keyBindForward = new KeyBinding("key.forward", 17);
	public KeyBinding keyBindLeft = new KeyBinding("key.left", 30);
	public KeyBinding keyBindBack = new KeyBinding("key.back", 31);
	public KeyBinding keyBindRight = new KeyBinding("key.right", 32);
	public KeyBinding keyBindJump = new KeyBinding("key.jump", 57);
	public KeyBinding keyBindInventory = new KeyBinding("key.inventory", 18);
	public KeyBinding keyBindDrop = new KeyBinding("key.drop", 16);
	public KeyBinding keyBindChat = new KeyBinding("key.chat", 20);
	public KeyBinding keyBindSneak = new KeyBinding("key.sneak", 42);
	public KeyBinding keyBindAttack = new KeyBinding("key.attack", -100);
	public KeyBinding keyBindUseItem = new KeyBinding("key.use", -99);
	public KeyBinding keyBindPlayerList = new KeyBinding("key.playerlist", 15);
	public KeyBinding keyBindPickBlock = new KeyBinding("key.pickItem", -98);
	public KeyBinding keyBindCommand = new KeyBinding("key.command", 53);
	public KeyBinding[] keyBindings;
	protected Minecraft mc;
	private File optionsFile;
	public int difficulty;
	public boolean hideGUI;
	public int thirdPersonView;
	public boolean showDebugInfo;
	public boolean showDebugProfilerChart;
	public String lastServer;
	public boolean noclip;
	public boolean smoothCamera;
	public boolean debugCamEnable;
	public float noclipRate;
	public float debugCamRate;
	public float fovSetting;
	public float gammaSetting;
	public int guiScale;
	public int particleSetting;
	public String language;
	
	public GameSettings()
	{
		keyBindings = new KeyBinding[] { keyBindAttack, keyBindUseItem, keyBindForward, keyBindLeft, keyBindBack, keyBindRight, keyBindJump, keyBindSneak, keyBindDrop, keyBindInventory, keyBindChat, keyBindPlayerList, keyBindPickBlock, keyBindCommand };
		difficulty = 2;
		hideGUI = false;
		thirdPersonView = 0;
		showDebugInfo = false;
		showDebugProfilerChart = false;
		lastServer = "";
		noclip = false;
		smoothCamera = false;
		debugCamEnable = false;
		noclipRate = 1.0F;
		debugCamRate = 1.0F;
		fovSetting = 0.0F;
		gammaSetting = 0.0F;
		guiScale = 0;
		particleSetting = 0;
		language = "en_US";
	}
	
	public GameSettings(Minecraft par1Minecraft, File par2File)
	{
		keyBindings = new KeyBinding[] { keyBindAttack, keyBindUseItem, keyBindForward, keyBindLeft, keyBindBack, keyBindRight, keyBindJump, keyBindSneak, keyBindDrop, keyBindInventory, keyBindChat, keyBindPlayerList, keyBindPickBlock, keyBindCommand };
		difficulty = 2;
		hideGUI = false;
		thirdPersonView = 0;
		showDebugInfo = false;
		showDebugProfilerChart = false;
		lastServer = "";
		noclip = false;
		smoothCamera = false;
		debugCamEnable = false;
		noclipRate = 1.0F;
		debugCamRate = 1.0F;
		fovSetting = 0.0F;
		gammaSetting = 0.0F;
		guiScale = 0;
		particleSetting = 0;
		language = "en_US";
		mc = par1Minecraft;
		optionsFile = new File(par2File, "options.txt");
		loadOptions();
	}
	
	public String getKeyBinding(EnumOptions par1EnumOptions)
	{
		StringTranslate var2 = StringTranslate.getInstance();
		String var3 = var2.translateKey(par1EnumOptions.getEnumString()) + ": ";
		if(par1EnumOptions.getEnumFloat())
		{
			float var5 = getOptionFloatValue(par1EnumOptions);
			return par1EnumOptions == EnumOptions.SENSITIVITY ? var5 == 0.0F ? var3 + var2.translateKey("options.sensitivity.min") : var5 == 1.0F ? var3 + var2.translateKey("options.sensitivity.max") : var3 + (int) (var5 * 200.0F) + "%" : par1EnumOptions == EnumOptions.FOV ? var5 == 0.0F ? var3 + var2.translateKey("options.fov.min") : var5 == 1.0F ? var3 + var2.translateKey("options.fov.max") : var3 + (int) (70.0F + var5 * 40.0F) : par1EnumOptions == EnumOptions.GAMMA ? var5 == 0.0F ? var3 + var2.translateKey("options.gamma.min") : var5 == 1.0F ? var3 + var2.translateKey("options.gamma.max") : var3 + "+" + (int) (var5 * 100.0F) + "%" : par1EnumOptions == EnumOptions.CHAT_OPACITY ? var3 + (int) (var5 * 90.0F + 10.0F) + "%" : par1EnumOptions == EnumOptions.CHAT_HEIGHT_UNFOCUSED ? var3 + GuiNewChat.func_96130_b(var5) + "px" : par1EnumOptions == EnumOptions.CHAT_HEIGHT_FOCUSED ? var3 + GuiNewChat.func_96130_b(var5) + "px" : par1EnumOptions == EnumOptions.CHAT_WIDTH ? var3 + GuiNewChat.func_96128_a(var5) + "px" : var5 == 0.0F ? var3 + var2.translateKey("options.off") : var3 + (int) (var5 * 100.0F) + "%";
		} else if(par1EnumOptions.getEnumBoolean())
		{
			boolean var4 = getOptionOrdinalValue(par1EnumOptions);
			return var4 ? var3 + var2.translateKey("options.on") : var3 + var2.translateKey("options.off");
		} else return par1EnumOptions == EnumOptions.RENDER_DISTANCE ? var3 + getTranslation(RENDER_DISTANCES, renderDistance) : par1EnumOptions == EnumOptions.DIFFICULTY ? var3 + getTranslation(DIFFICULTIES, difficulty) : par1EnumOptions == EnumOptions.GUI_SCALE ? var3 + getTranslation(GUISCALES, guiScale) : par1EnumOptions == EnumOptions.CHAT_VISIBILITY ? var3 + getTranslation(CHAT_VISIBILITIES, chatVisibility) : par1EnumOptions == EnumOptions.PARTICLES ? var3 + getTranslation(PARTICLES, particleSetting) : par1EnumOptions == EnumOptions.FRAMERATE_LIMIT ? var3 + getTranslation(LIMIT_FRAMERATES, limitFramerate) : par1EnumOptions == EnumOptions.AMBIENT_OCCLUSION ? var3 + getTranslation(AMBIENT_OCCLUSIONS, ambientOcclusion) : par1EnumOptions == EnumOptions.GRAPHICS ? fancyGraphics ? var3 + var2.translateKey("options.graphics.fancy") : var3 + var2.translateKey("options.graphics.fast") : var3;
	}
	
	public String getKeyBindingDescription(int par1)
	{
		StringTranslate var2 = StringTranslate.getInstance();
		return var2.translateKey(keyBindings[par1].keyDescription);
	}
	
	public String getOptionDisplayString(int par1)
	{
		int var2 = keyBindings[par1].keyCode;
		return getKeyDisplayString(var2);
	}
	
	public float getOptionFloatValue(EnumOptions par1EnumOptions)
	{
		return par1EnumOptions == EnumOptions.FOV ? fovSetting : par1EnumOptions == EnumOptions.GAMMA ? gammaSetting : par1EnumOptions == EnumOptions.MUSIC ? musicVolume : par1EnumOptions == EnumOptions.SOUND ? soundVolume : par1EnumOptions == EnumOptions.SENSITIVITY ? mouseSensitivity : par1EnumOptions == EnumOptions.CHAT_OPACITY ? chatOpacity : par1EnumOptions == EnumOptions.CHAT_HEIGHT_FOCUSED ? chatHeightFocused : par1EnumOptions == EnumOptions.CHAT_HEIGHT_UNFOCUSED ? chatHeightUnfocused : par1EnumOptions == EnumOptions.CHAT_SCALE ? chatScale : par1EnumOptions == EnumOptions.CHAT_WIDTH ? chatWidth : 0.0F;
	}
	
	public boolean getOptionOrdinalValue(EnumOptions par1EnumOptions)
	{
		switch(EnumOptionsHelper.enumOptionsMappingHelperArray[par1EnumOptions.ordinal()])
		{
			case 1:
				return invertMouse;
			case 2:
				return viewBobbing;
			case 3:
				return anaglyph;
			case 4:
				return advancedOpengl;
			case 5:
				return clouds;
			case 6:
				return chatColours;
			case 7:
				return chatLinks;
			case 8:
				return chatLinksPrompt;
			case 9:
				return serverTextures;
			case 10:
				return snooperEnabled;
			case 11:
				return fullScreen;
			case 12:
				return enableVsync;
			case 13:
				return showCape;
			case 14:
				return touchscreen;
			default:
				return false;
		}
	}
	
	public void loadOptions()
	{
		try
		{
			if(!optionsFile.exists()) return;
			BufferedReader var1 = new BufferedReader(new FileReader(optionsFile));
			String var2 = "";
			while((var2 = var1.readLine()) != null)
			{
				try
				{
					String[] var3 = var2.split(":");
					if(var3[0].equals("music"))
					{
						musicVolume = parseFloat(var3[1]);
					}
					if(var3[0].equals("sound"))
					{
						soundVolume = parseFloat(var3[1]);
					}
					if(var3[0].equals("mouseSensitivity"))
					{
						mouseSensitivity = parseFloat(var3[1]);
					}
					if(var3[0].equals("fov"))
					{
						fovSetting = parseFloat(var3[1]);
					}
					if(var3[0].equals("gamma"))
					{
						gammaSetting = parseFloat(var3[1]);
					}
					if(var3[0].equals("invertYMouse"))
					{
						invertMouse = var3[1].equals("true");
					}
					if(var3[0].equals("viewDistance"))
					{
						renderDistance = Integer.parseInt(var3[1]);
					}
					if(var3[0].equals("guiScale"))
					{
						guiScale = Integer.parseInt(var3[1]);
					}
					if(var3[0].equals("particles"))
					{
						particleSetting = Integer.parseInt(var3[1]);
					}
					if(var3[0].equals("bobView"))
					{
						viewBobbing = var3[1].equals("true");
					}
					if(var3[0].equals("anaglyph3d"))
					{
						anaglyph = var3[1].equals("true");
					}
					if(var3[0].equals("advancedOpengl"))
					{
						advancedOpengl = var3[1].equals("true");
					}
					if(var3[0].equals("fpsLimit"))
					{
						limitFramerate = Integer.parseInt(var3[1]);
					}
					if(var3[0].equals("difficulty"))
					{
						difficulty = Integer.parseInt(var3[1]);
					}
					if(var3[0].equals("fancyGraphics"))
					{
						fancyGraphics = var3[1].equals("true");
					}
					if(var3[0].equals("ao"))
					{
						if(var3[1].equals("true"))
						{
							ambientOcclusion = 2;
						} else if(var3[1].equals("false"))
						{
							ambientOcclusion = 0;
						} else
						{
							ambientOcclusion = Integer.parseInt(var3[1]);
						}
					}
					if(var3[0].equals("clouds"))
					{
						clouds = var3[1].equals("true");
					}
					if(var3[0].equals("skin"))
					{
						skin = var3[1];
					}
					if(var3[0].equals("lastServer") && var3.length >= 2)
					{
						lastServer = var3[1];
					}
					if(var3[0].equals("lang") && var3.length >= 2)
					{
						language = var3[1];
					}
					if(var3[0].equals("chatVisibility"))
					{
						chatVisibility = Integer.parseInt(var3[1]);
					}
					if(var3[0].equals("chatColors"))
					{
						chatColours = var3[1].equals("true");
					}
					if(var3[0].equals("chatLinks"))
					{
						chatLinks = var3[1].equals("true");
					}
					if(var3[0].equals("chatLinksPrompt"))
					{
						chatLinksPrompt = var3[1].equals("true");
					}
					if(var3[0].equals("chatOpacity"))
					{
						chatOpacity = parseFloat(var3[1]);
					}
					if(var3[0].equals("serverTextures"))
					{
						serverTextures = var3[1].equals("true");
					}
					if(var3[0].equals("snooperEnabled"))
					{
						snooperEnabled = var3[1].equals("true");
					}
					if(var3[0].equals("fullscreen"))
					{
						fullScreen = var3[1].equals("true");
					}
					if(var3[0].equals("enableVsync"))
					{
						enableVsync = var3[1].equals("true");
					}
					if(var3[0].equals("hideServerAddress"))
					{
						hideServerAddress = var3[1].equals("true");
					}
					if(var3[0].equals("advancedItemTooltips"))
					{
						advancedItemTooltips = var3[1].equals("true");
					}
					if(var3[0].equals("pauseOnLostFocus"))
					{
						pauseOnLostFocus = var3[1].equals("true");
					}
					if(var3[0].equals("showCape"))
					{
						showCape = var3[1].equals("true");
					}
					if(var3[0].equals("touchscreen"))
					{
						touchscreen = var3[1].equals("true");
					}
					if(var3[0].equals("overrideHeight"))
					{
						overrideHeight = Integer.parseInt(var3[1]);
					}
					if(var3[0].equals("overrideWidth"))
					{
						overrideWidth = Integer.parseInt(var3[1]);
					}
					if(var3[0].equals("heldItemTooltips"))
					{
						heldItemTooltips = var3[1].equals("true");
					}
					if(var3[0].equals("chatHeightFocused"))
					{
						chatHeightFocused = parseFloat(var3[1]);
					}
					if(var3[0].equals("chatHeightUnfocused"))
					{
						chatHeightUnfocused = parseFloat(var3[1]);
					}
					if(var3[0].equals("chatScale"))
					{
						chatScale = parseFloat(var3[1]);
					}
					if(var3[0].equals("chatWidth"))
					{
						chatWidth = parseFloat(var3[1]);
					}
					for(int var4 = 0; var4 < keyBindings.length; ++var4)
					{
						if(var3[0].equals("key_" + keyBindings[var4].keyDescription))
						{
							keyBindings[var4].keyCode = Integer.parseInt(var3[1]);
						}
					}
				} catch(Exception var5)
				{
					mc.getLogAgent().logWarning("Skipping bad option: " + var2);
				}
			}
			KeyBinding.resetKeyBindingArrayAndHash();
			var1.close();
		} catch(Exception var6)
		{
			mc.getLogAgent().logWarning("Failed to load options");
			var6.printStackTrace();
		}
	}
	
	private float parseFloat(String par1Str)
	{
		return par1Str.equals("true") ? 1.0F : par1Str.equals("false") ? 0.0F : Float.parseFloat(par1Str);
	}
	
	public void saveOptions()
	{
		try
		{
			PrintWriter var1 = new PrintWriter(new FileWriter(optionsFile));
			var1.println("music:" + musicVolume);
			var1.println("sound:" + soundVolume);
			var1.println("invertYMouse:" + invertMouse);
			var1.println("mouseSensitivity:" + mouseSensitivity);
			var1.println("fov:" + fovSetting);
			var1.println("gamma:" + gammaSetting);
			var1.println("viewDistance:" + renderDistance);
			var1.println("guiScale:" + guiScale);
			var1.println("particles:" + particleSetting);
			var1.println("bobView:" + viewBobbing);
			var1.println("anaglyph3d:" + anaglyph);
			var1.println("advancedOpengl:" + advancedOpengl);
			var1.println("fpsLimit:" + limitFramerate);
			var1.println("difficulty:" + difficulty);
			var1.println("fancyGraphics:" + fancyGraphics);
			var1.println("ao:" + ambientOcclusion);
			var1.println("clouds:" + clouds);
			var1.println("skin:" + skin);
			var1.println("lastServer:" + lastServer);
			var1.println("lang:" + language);
			var1.println("chatVisibility:" + chatVisibility);
			var1.println("chatColors:" + chatColours);
			var1.println("chatLinks:" + chatLinks);
			var1.println("chatLinksPrompt:" + chatLinksPrompt);
			var1.println("chatOpacity:" + chatOpacity);
			var1.println("serverTextures:" + serverTextures);
			var1.println("snooperEnabled:" + snooperEnabled);
			var1.println("fullscreen:" + fullScreen);
			var1.println("enableVsync:" + enableVsync);
			var1.println("hideServerAddress:" + hideServerAddress);
			var1.println("advancedItemTooltips:" + advancedItemTooltips);
			var1.println("pauseOnLostFocus:" + pauseOnLostFocus);
			var1.println("showCape:" + showCape);
			var1.println("touchscreen:" + touchscreen);
			var1.println("overrideWidth:" + overrideWidth);
			var1.println("overrideHeight:" + overrideHeight);
			var1.println("heldItemTooltips:" + heldItemTooltips);
			var1.println("chatHeightFocused:" + chatHeightFocused);
			var1.println("chatHeightUnfocused:" + chatHeightUnfocused);
			var1.println("chatScale:" + chatScale);
			var1.println("chatWidth:" + chatWidth);
			for(KeyBinding keyBinding : keyBindings)
			{
				var1.println("key_" + keyBinding.keyDescription + ":" + keyBinding.keyCode);
			}
			var1.close();
		} catch(Exception var3)
		{
			mc.getLogAgent().logWarning("Failed to save options");
			var3.printStackTrace();
		}
		sendSettingsToServer();
	}
	
	public void sendSettingsToServer()
	{
		if(mc.thePlayer != null)
		{
			mc.thePlayer.sendQueue.addToSendQueue(new Packet204ClientInfo(language, renderDistance, chatVisibility, chatColours, difficulty, showCape));
		}
	}
	
	public void setKeyBinding(int par1, int par2)
	{
		keyBindings[par1].keyCode = par2;
		saveOptions();
	}
	
	public void setOptionFloatValue(EnumOptions par1EnumOptions, float par2)
	{
		if(par1EnumOptions == EnumOptions.MUSIC)
		{
			musicVolume = par2;
			mc.sndManager.onSoundOptionsChanged();
		}
		if(par1EnumOptions == EnumOptions.SOUND)
		{
			soundVolume = par2;
			mc.sndManager.onSoundOptionsChanged();
		}
		if(par1EnumOptions == EnumOptions.SENSITIVITY)
		{
			mouseSensitivity = par2;
		}
		if(par1EnumOptions == EnumOptions.FOV)
		{
			fovSetting = par2;
		}
		if(par1EnumOptions == EnumOptions.GAMMA)
		{
			gammaSetting = par2;
		}
		if(par1EnumOptions == EnumOptions.CHAT_OPACITY)
		{
			chatOpacity = par2;
			mc.ingameGUI.getChatGUI().func_96132_b();
		}
		if(par1EnumOptions == EnumOptions.CHAT_HEIGHT_FOCUSED)
		{
			chatHeightFocused = par2;
			mc.ingameGUI.getChatGUI().func_96132_b();
		}
		if(par1EnumOptions == EnumOptions.CHAT_HEIGHT_UNFOCUSED)
		{
			chatHeightUnfocused = par2;
			mc.ingameGUI.getChatGUI().func_96132_b();
		}
		if(par1EnumOptions == EnumOptions.CHAT_WIDTH)
		{
			chatWidth = par2;
			mc.ingameGUI.getChatGUI().func_96132_b();
		}
		if(par1EnumOptions == EnumOptions.CHAT_SCALE)
		{
			chatScale = par2;
			mc.ingameGUI.getChatGUI().func_96132_b();
		}
	}
	
	public void setOptionValue(EnumOptions par1EnumOptions, int par2)
	{
		if(par1EnumOptions == EnumOptions.INVERT_MOUSE)
		{
			invertMouse = !invertMouse;
		}
		if(par1EnumOptions == EnumOptions.RENDER_DISTANCE)
		{
			renderDistance = renderDistance + par2 & 3;
		}
		if(par1EnumOptions == EnumOptions.GUI_SCALE)
		{
			guiScale = guiScale + par2 & 3;
		}
		if(par1EnumOptions == EnumOptions.PARTICLES)
		{
			particleSetting = (particleSetting + par2) % 3;
		}
		if(par1EnumOptions == EnumOptions.VIEW_BOBBING)
		{
			viewBobbing = !viewBobbing;
		}
		if(par1EnumOptions == EnumOptions.RENDER_CLOUDS)
		{
			clouds = !clouds;
		}
		if(par1EnumOptions == EnumOptions.ADVANCED_OPENGL)
		{
			advancedOpengl = !advancedOpengl;
			mc.renderGlobal.loadRenderers();
		}
		if(par1EnumOptions == EnumOptions.ANAGLYPH)
		{
			anaglyph = !anaglyph;
			mc.renderEngine.refreshTextures();
		}
		if(par1EnumOptions == EnumOptions.FRAMERATE_LIMIT)
		{
			limitFramerate = (limitFramerate + par2 + 3) % 3;
		}
		if(par1EnumOptions == EnumOptions.DIFFICULTY)
		{
			difficulty = difficulty + par2 & 3;
		}
		if(par1EnumOptions == EnumOptions.GRAPHICS)
		{
			fancyGraphics = !fancyGraphics;
			mc.renderGlobal.loadRenderers();
		}
		if(par1EnumOptions == EnumOptions.AMBIENT_OCCLUSION)
		{
			ambientOcclusion = (ambientOcclusion + par2) % 3;
			mc.renderGlobal.loadRenderers();
		}
		if(par1EnumOptions == EnumOptions.CHAT_VISIBILITY)
		{
			chatVisibility = (chatVisibility + par2) % 3;
		}
		if(par1EnumOptions == EnumOptions.CHAT_COLOR)
		{
			chatColours = !chatColours;
		}
		if(par1EnumOptions == EnumOptions.CHAT_LINKS)
		{
			chatLinks = !chatLinks;
		}
		if(par1EnumOptions == EnumOptions.CHAT_LINKS_PROMPT)
		{
			chatLinksPrompt = !chatLinksPrompt;
		}
		if(par1EnumOptions == EnumOptions.USE_SERVER_TEXTURES)
		{
			serverTextures = !serverTextures;
		}
		if(par1EnumOptions == EnumOptions.SNOOPER_ENABLED)
		{
			snooperEnabled = !snooperEnabled;
		}
		if(par1EnumOptions == EnumOptions.SHOW_CAPE)
		{
			showCape = !showCape;
		}
		if(par1EnumOptions == EnumOptions.TOUCHSCREEN)
		{
			touchscreen = !touchscreen;
		}
		if(par1EnumOptions == EnumOptions.USE_FULLSCREEN)
		{
			fullScreen = !fullScreen;
			if(mc.isFullScreen() != fullScreen)
			{
				mc.toggleFullscreen();
			}
		}
		if(par1EnumOptions == EnumOptions.ENABLE_VSYNC)
		{
			enableVsync = !enableVsync;
			Display.setVSyncEnabled(enableVsync);
		}
		saveOptions();
	}
	
	public boolean shouldRenderClouds()
	{
		return renderDistance < 2 && clouds;
	}
	
	public static String getKeyDisplayString(int par0)
	{
		return par0 < 0 ? StatCollector.translateToLocalFormatted("key.mouseButton", new Object[] { Integer.valueOf(par0 + 101) }) : Keyboard.getKeyName(par0);
	}
	
	private static String getTranslation(String[] par0ArrayOfStr, int par1)
	{
		if(par1 < 0 || par1 >= par0ArrayOfStr.length)
		{
			par1 = 0;
		}
		StringTranslate var2 = StringTranslate.getInstance();
		return var2.translateKey(par0ArrayOfStr[par1]);
	}
	
	public static boolean isKeyDown(KeyBinding par0KeyBinding)
	{
		return par0KeyBinding.keyCode < 0 ? Mouse.isButtonDown(par0KeyBinding.keyCode + 100) : Keyboard.isKeyDown(par0KeyBinding.keyCode);
	}
}
