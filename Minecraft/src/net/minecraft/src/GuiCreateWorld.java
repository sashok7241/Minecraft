package net.minecraft.src;

import java.util.Random;

public class GuiCreateWorld extends GuiScreen
{
	private GuiScreen parentGuiScreen;
	private GuiTextField textboxWorldName;
	private GuiTextField textboxSeed;
	private String folderName;
	private String gameMode = "survival";
	private boolean generateStructures = true;
	private boolean commandsAllowed;
	private boolean commandsToggled;
	private boolean bonusItems;
	private boolean isHardcore;
	private boolean createClicked;
	private boolean moreOptions;
	private GuiButton buttonGameMode;
	private GuiButton moreWorldOptions;
	private GuiButton buttonGenerateStructures;
	private GuiButton buttonBonusItems;
	private GuiButton buttonWorldType;
	private GuiButton buttonAllowCommands;
	private GuiButton buttonCustomize;
	private String gameModeDescriptionLine1;
	private String gameModeDescriptionLine2;
	private String seed;
	private String localizedNewWorldText;
	private int worldTypeId;
	public String generatorOptionsToUse = "";
	private static final String[] ILLEGAL_WORLD_NAMES = new String[] { "CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
	
	public GuiCreateWorld(GuiScreen par1GuiScreen)
	{
		parentGuiScreen = par1GuiScreen;
		seed = "";
		localizedNewWorldText = I18n.func_135053_a("selectWorld.newWorld");
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 1)
			{
				mc.displayGuiScreen(parentGuiScreen);
			} else if(par1GuiButton.id == 0)
			{
				mc.displayGuiScreen((GuiScreen) null);
				if(createClicked) return;
				createClicked = true;
				long var2 = new Random().nextLong();
				String var4 = textboxSeed.getText();
				if(!MathHelper.stringNullOrLengthZero(var4))
				{
					try
					{
						long var5 = Long.parseLong(var4);
						if(var5 != 0L)
						{
							var2 = var5;
						}
					} catch(NumberFormatException var7)
					{
						var2 = var4.hashCode();
					}
				}
				EnumGameType var8 = EnumGameType.getByName(gameMode);
				WorldSettings var6 = new WorldSettings(var2, var8, generateStructures, isHardcore, WorldType.worldTypes[worldTypeId]);
				var6.func_82750_a(generatorOptionsToUse);
				if(bonusItems && !isHardcore)
				{
					var6.enableBonusChest();
				}
				if(commandsAllowed && !isHardcore)
				{
					var6.enableCommands();
				}
				mc.launchIntegratedServer(folderName, textboxWorldName.getText().trim(), var6);
				mc.statFileWriter.readStat(StatList.createWorldStat, 1);
			} else if(par1GuiButton.id == 3)
			{
				func_82287_i();
			} else if(par1GuiButton.id == 2)
			{
				if(gameMode.equals("survival"))
				{
					if(!commandsToggled)
					{
						commandsAllowed = false;
					}
					isHardcore = false;
					gameMode = "hardcore";
					isHardcore = true;
					buttonAllowCommands.enabled = false;
					buttonBonusItems.enabled = false;
					updateButtonText();
				} else if(gameMode.equals("hardcore"))
				{
					if(!commandsToggled)
					{
						commandsAllowed = true;
					}
					isHardcore = false;
					gameMode = "creative";
					updateButtonText();
					isHardcore = false;
					buttonAllowCommands.enabled = true;
					buttonBonusItems.enabled = true;
				} else
				{
					if(!commandsToggled)
					{
						commandsAllowed = false;
					}
					gameMode = "survival";
					updateButtonText();
					buttonAllowCommands.enabled = true;
					buttonBonusItems.enabled = true;
					isHardcore = false;
				}
				updateButtonText();
			} else if(par1GuiButton.id == 4)
			{
				generateStructures = !generateStructures;
				updateButtonText();
			} else if(par1GuiButton.id == 7)
			{
				bonusItems = !bonusItems;
				updateButtonText();
			} else if(par1GuiButton.id == 5)
			{
				++worldTypeId;
				if(worldTypeId >= WorldType.worldTypes.length)
				{
					worldTypeId = 0;
				}
				while(WorldType.worldTypes[worldTypeId] == null || !WorldType.worldTypes[worldTypeId].getCanBeCreated())
				{
					++worldTypeId;
					if(worldTypeId >= WorldType.worldTypes.length)
					{
						worldTypeId = 0;
					}
				}
				generatorOptionsToUse = "";
				updateButtonText();
				func_82288_a(moreOptions);
			} else if(par1GuiButton.id == 6)
			{
				commandsToggled = true;
				commandsAllowed = !commandsAllowed;
				updateButtonText();
			} else if(par1GuiButton.id == 8)
			{
				mc.displayGuiScreen(new GuiCreateFlatWorld(this, generatorOptionsToUse));
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, I18n.func_135053_a("selectWorld.create"), width / 2, 20, 16777215);
		if(moreOptions)
		{
			drawString(fontRenderer, I18n.func_135053_a("selectWorld.enterSeed"), width / 2 - 100, 47, 10526880);
			drawString(fontRenderer, I18n.func_135053_a("selectWorld.seedInfo"), width / 2 - 100, 85, 10526880);
			drawString(fontRenderer, I18n.func_135053_a("selectWorld.mapFeatures.info"), width / 2 - 150, 122, 10526880);
			drawString(fontRenderer, I18n.func_135053_a("selectWorld.allowCommands.info"), width / 2 - 150, 172, 10526880);
			textboxSeed.drawTextBox();
		} else
		{
			drawString(fontRenderer, I18n.func_135053_a("selectWorld.enterName"), width / 2 - 100, 47, 10526880);
			drawString(fontRenderer, I18n.func_135053_a("selectWorld.resultFolder") + " " + folderName, width / 2 - 100, 85, 10526880);
			textboxWorldName.drawTextBox();
			drawString(fontRenderer, gameModeDescriptionLine1, width / 2 - 100, 137, 10526880);
			drawString(fontRenderer, gameModeDescriptionLine2, width / 2 - 100, 149, 10526880);
		}
		super.drawScreen(par1, par2, par3);
	}
	
	public void func_82286_a(WorldInfo par1WorldInfo)
	{
		localizedNewWorldText = I18n.func_135052_a("selectWorld.newWorld.copyOf", new Object[] { par1WorldInfo.getWorldName() });
		seed = par1WorldInfo.getSeed() + "";
		worldTypeId = par1WorldInfo.getTerrainType().getWorldTypeID();
		generatorOptionsToUse = par1WorldInfo.getGeneratorOptions();
		generateStructures = par1WorldInfo.isMapFeaturesEnabled();
		commandsAllowed = par1WorldInfo.areCommandsAllowed();
		if(par1WorldInfo.isHardcoreModeEnabled())
		{
			gameMode = "hardcore";
		} else if(par1WorldInfo.getGameType().isSurvivalOrAdventure())
		{
			gameMode = "survival";
		} else if(par1WorldInfo.getGameType().isCreative())
		{
			gameMode = "creative";
		}
	}
	
	private void func_82287_i()
	{
		func_82288_a(!moreOptions);
	}
	
	private void func_82288_a(boolean par1)
	{
		moreOptions = par1;
		buttonGameMode.drawButton = !moreOptions;
		buttonGenerateStructures.drawButton = moreOptions;
		buttonBonusItems.drawButton = moreOptions;
		buttonWorldType.drawButton = moreOptions;
		buttonAllowCommands.drawButton = moreOptions;
		buttonCustomize.drawButton = moreOptions && WorldType.worldTypes[worldTypeId] == WorldType.FLAT;
		if(moreOptions)
		{
			moreWorldOptions.displayString = I18n.func_135053_a("gui.done");
		} else
		{
			moreWorldOptions.displayString = I18n.func_135053_a("selectWorld.moreWorldOptions");
		}
	}
	
	@Override public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.func_135053_a("selectWorld.create")));
		buttonList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.func_135053_a("gui.cancel")));
		buttonList.add(buttonGameMode = new GuiButton(2, width / 2 - 75, 115, 150, 20, I18n.func_135053_a("selectWorld.gameMode")));
		buttonList.add(moreWorldOptions = new GuiButton(3, width / 2 - 75, 187, 150, 20, I18n.func_135053_a("selectWorld.moreWorldOptions")));
		buttonList.add(buttonGenerateStructures = new GuiButton(4, width / 2 - 155, 100, 150, 20, I18n.func_135053_a("selectWorld.mapFeatures")));
		buttonGenerateStructures.drawButton = false;
		buttonList.add(buttonBonusItems = new GuiButton(7, width / 2 + 5, 151, 150, 20, I18n.func_135053_a("selectWorld.bonusItems")));
		buttonBonusItems.drawButton = false;
		buttonList.add(buttonWorldType = new GuiButton(5, width / 2 + 5, 100, 150, 20, I18n.func_135053_a("selectWorld.mapType")));
		buttonWorldType.drawButton = false;
		buttonList.add(buttonAllowCommands = new GuiButton(6, width / 2 - 155, 151, 150, 20, I18n.func_135053_a("selectWorld.allowCommands")));
		buttonAllowCommands.drawButton = false;
		buttonList.add(buttonCustomize = new GuiButton(8, width / 2 + 5, 120, 150, 20, I18n.func_135053_a("selectWorld.customizeType")));
		buttonCustomize.drawButton = false;
		textboxWorldName = new GuiTextField(fontRenderer, width / 2 - 100, 60, 200, 20);
		textboxWorldName.setFocused(true);
		textboxWorldName.setText(localizedNewWorldText);
		textboxSeed = new GuiTextField(fontRenderer, width / 2 - 100, 60, 200, 20);
		textboxSeed.setText(seed);
		func_82288_a(moreOptions);
		makeUseableName();
		updateButtonText();
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		if(textboxWorldName.isFocused() && !moreOptions)
		{
			textboxWorldName.textboxKeyTyped(par1, par2);
			localizedNewWorldText = textboxWorldName.getText();
		} else if(textboxSeed.isFocused() && moreOptions)
		{
			textboxSeed.textboxKeyTyped(par1, par2);
			seed = textboxSeed.getText();
		}
		if(par2 == 28 || par2 == 156)
		{
			actionPerformed((GuiButton) buttonList.get(0));
		}
		((GuiButton) buttonList.get(0)).enabled = textboxWorldName.getText().length() > 0;
		makeUseableName();
	}
	
	private void makeUseableName()
	{
		folderName = textboxWorldName.getText().trim();
		char[] var1 = ChatAllowedCharacters.allowedCharactersArray;
		int var2 = var1.length;
		for(int var3 = 0; var3 < var2; ++var3)
		{
			char var4 = var1[var3];
			folderName = folderName.replace(var4, '_');
		}
		if(MathHelper.stringNullOrLengthZero(folderName))
		{
			folderName = "World";
		}
		folderName = func_73913_a(mc.getSaveLoader(), folderName);
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		if(moreOptions)
		{
			textboxSeed.mouseClicked(par1, par2, par3);
		} else
		{
			textboxWorldName.mouseClicked(par1, par2, par3);
		}
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	private void updateButtonText()
	{
		buttonGameMode.displayString = I18n.func_135053_a("selectWorld.gameMode") + " " + I18n.func_135053_a("selectWorld.gameMode." + gameMode);
		gameModeDescriptionLine1 = I18n.func_135053_a("selectWorld.gameMode." + gameMode + ".line1");
		gameModeDescriptionLine2 = I18n.func_135053_a("selectWorld.gameMode." + gameMode + ".line2");
		buttonGenerateStructures.displayString = I18n.func_135053_a("selectWorld.mapFeatures") + " ";
		if(generateStructures)
		{
			buttonGenerateStructures.displayString = buttonGenerateStructures.displayString + I18n.func_135053_a("options.on");
		} else
		{
			buttonGenerateStructures.displayString = buttonGenerateStructures.displayString + I18n.func_135053_a("options.off");
		}
		buttonBonusItems.displayString = I18n.func_135053_a("selectWorld.bonusItems") + " ";
		if(bonusItems && !isHardcore)
		{
			buttonBonusItems.displayString = buttonBonusItems.displayString + I18n.func_135053_a("options.on");
		} else
		{
			buttonBonusItems.displayString = buttonBonusItems.displayString + I18n.func_135053_a("options.off");
		}
		buttonWorldType.displayString = I18n.func_135053_a("selectWorld.mapType") + " " + I18n.func_135053_a(WorldType.worldTypes[worldTypeId].getTranslateName());
		buttonAllowCommands.displayString = I18n.func_135053_a("selectWorld.allowCommands") + " ";
		if(commandsAllowed && !isHardcore)
		{
			buttonAllowCommands.displayString = buttonAllowCommands.displayString + I18n.func_135053_a("options.on");
		} else
		{
			buttonAllowCommands.displayString = buttonAllowCommands.displayString + I18n.func_135053_a("options.off");
		}
	}
	
	@Override public void updateScreen()
	{
		textboxWorldName.updateCursorCounter();
		textboxSeed.updateCursorCounter();
	}
	
	public static String func_73913_a(ISaveFormat par0ISaveFormat, String par1Str)
	{
		par1Str = par1Str.replaceAll("[\\./\"]", "_");
		String[] var2 = ILLEGAL_WORLD_NAMES;
		int var3 = var2.length;
		for(int var4 = 0; var4 < var3; ++var4)
		{
			String var5 = var2[var4];
			if(par1Str.equalsIgnoreCase(var5))
			{
				par1Str = "_" + par1Str + "_";
			}
		}
		while(par0ISaveFormat.getWorldInfo(par1Str) != null)
		{
			par1Str = par1Str + "-";
		}
		return par1Str;
	}
}
