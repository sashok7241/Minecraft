package net.minecraft.src;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class GuiSelectWorld extends GuiScreen
{
	private final DateFormat dateFormatter = new SimpleDateFormat();
	protected GuiScreen parentScreen;
	protected String screenTitle = "Select world";
	private boolean selected;
	private int selectedWorld;
	private List saveList;
	private GuiWorldSlot worldSlotContainer;
	private String localizedWorldText;
	private String localizedMustConvertText;
	private String[] localizedGameModeText = new String[3];
	private boolean deleting;
	private GuiButton buttonDelete;
	private GuiButton buttonSelect;
	private GuiButton buttonRename;
	private GuiButton buttonRecreate;
	
	public GuiSelectWorld(GuiScreen par1GuiScreen)
	{
		parentScreen = par1GuiScreen;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 2)
			{
				String var2 = getSaveName(selectedWorld);
				if(var2 != null)
				{
					deleting = true;
					GuiYesNo var3 = getDeleteWorldScreen(this, var2, selectedWorld);
					mc.displayGuiScreen(var3);
				}
			} else if(par1GuiButton.id == 1)
			{
				selectWorld(selectedWorld);
			} else if(par1GuiButton.id == 3)
			{
				mc.displayGuiScreen(new GuiCreateWorld(this));
			} else if(par1GuiButton.id == 6)
			{
				mc.displayGuiScreen(new GuiRenameWorld(this, getSaveFileName(selectedWorld)));
			} else if(par1GuiButton.id == 0)
			{
				mc.displayGuiScreen(parentScreen);
			} else if(par1GuiButton.id == 7)
			{
				GuiCreateWorld var5 = new GuiCreateWorld(this);
				ISaveHandler var6 = mc.getSaveLoader().getSaveLoader(getSaveFileName(selectedWorld), false);
				WorldInfo var4 = var6.loadWorldInfo();
				var6.flush();
				var5.func_82286_a(var4);
				mc.displayGuiScreen(var5);
			} else
			{
				worldSlotContainer.actionPerformed(par1GuiButton);
			}
		}
	}
	
	@Override public void confirmClicked(boolean par1, int par2)
	{
		if(deleting)
		{
			deleting = false;
			if(par1)
			{
				ISaveFormat var3 = mc.getSaveLoader();
				var3.flushCache();
				var3.deleteWorldDirectory(getSaveFileName(par2));
				try
				{
					loadSaves();
				} catch(AnvilConverterException var5)
				{
					var5.printStackTrace();
				}
			}
			mc.displayGuiScreen(this);
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		worldSlotContainer.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, screenTitle, width / 2, 20, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	protected String getSaveFileName(int par1)
	{
		return ((SaveFormatComparator) saveList.get(par1)).getFileName();
	}
	
	protected String getSaveName(int par1)
	{
		String var2 = ((SaveFormatComparator) saveList.get(par1)).getDisplayName();
		if(var2 == null || MathHelper.stringNullOrLengthZero(var2))
		{
			var2 = I18n.func_135053_a("selectWorld.world") + " " + (par1 + 1);
		}
		return var2;
	}
	
	public void initButtons()
	{
		buttonList.add(buttonSelect = new GuiButton(1, width / 2 - 154, height - 52, 150, 20, I18n.func_135053_a("selectWorld.select")));
		buttonList.add(new GuiButton(3, width / 2 + 4, height - 52, 150, 20, I18n.func_135053_a("selectWorld.create")));
		buttonList.add(buttonRename = new GuiButton(6, width / 2 - 154, height - 28, 72, 20, I18n.func_135053_a("selectWorld.rename")));
		buttonList.add(buttonDelete = new GuiButton(2, width / 2 - 76, height - 28, 72, 20, I18n.func_135053_a("selectWorld.delete")));
		buttonList.add(buttonRecreate = new GuiButton(7, width / 2 + 4, height - 28, 72, 20, I18n.func_135053_a("selectWorld.recreate")));
		buttonList.add(new GuiButton(0, width / 2 + 82, height - 28, 72, 20, I18n.func_135053_a("gui.cancel")));
		buttonSelect.enabled = false;
		buttonDelete.enabled = false;
		buttonRename.enabled = false;
		buttonRecreate.enabled = false;
	}
	
	@Override public void initGui()
	{
		screenTitle = I18n.func_135053_a("selectWorld.title");
		try
		{
			loadSaves();
		} catch(AnvilConverterException var2)
		{
			var2.printStackTrace();
			mc.displayGuiScreen(new GuiErrorScreen("Unable to load words", var2.getMessage()));
			return;
		}
		localizedWorldText = I18n.func_135053_a("selectWorld.world");
		localizedMustConvertText = I18n.func_135053_a("selectWorld.conversion");
		localizedGameModeText[EnumGameType.SURVIVAL.getID()] = I18n.func_135053_a("gameMode.survival");
		localizedGameModeText[EnumGameType.CREATIVE.getID()] = I18n.func_135053_a("gameMode.creative");
		localizedGameModeText[EnumGameType.ADVENTURE.getID()] = I18n.func_135053_a("gameMode.adventure");
		worldSlotContainer = new GuiWorldSlot(this);
		worldSlotContainer.registerScrollButtons(4, 5);
		initButtons();
	}
	
	private void loadSaves() throws AnvilConverterException
	{
		ISaveFormat var1 = mc.getSaveLoader();
		saveList = var1.getSaveList();
		Collections.sort(saveList);
		selectedWorld = -1;
	}
	
	public void selectWorld(int par1)
	{
		mc.displayGuiScreen((GuiScreen) null);
		if(!selected)
		{
			selected = true;
			String var2 = getSaveFileName(par1);
			if(var2 == null)
			{
				var2 = "World" + par1;
			}
			String var3 = getSaveName(par1);
			if(var3 == null)
			{
				var3 = "World" + par1;
			}
			if(mc.getSaveLoader().canLoadWorld(var2))
			{
				mc.launchIntegratedServer(var2, var3, (WorldSettings) null);
				mc.statFileWriter.readStat(StatList.loadWorldStat, 1);
			}
		}
	}
	
	static String func_82311_i(GuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.localizedMustConvertText;
	}
	
	static GuiButton func_82312_f(GuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.buttonRecreate;
	}
	
	static String func_82313_g(GuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.localizedWorldText;
	}
	
	static String[] func_82314_j(GuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.localizedGameModeText;
	}
	
	static DateFormat func_82315_h(GuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.dateFormatter;
	}
	
	static GuiButton getDeleteButton(GuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.buttonRename;
	}
	
	public static GuiYesNo getDeleteWorldScreen(GuiScreen par0GuiScreen, String par1Str, int par2)
	{
		String var3 = I18n.func_135053_a("selectWorld.deleteQuestion");
		String var4 = "\'" + par1Str + "\' " + I18n.func_135053_a("selectWorld.deleteWarning");
		String var5 = I18n.func_135053_a("selectWorld.deleteButton");
		String var6 = I18n.func_135053_a("gui.cancel");
		GuiYesNo var7 = new GuiYesNo(par0GuiScreen, var3, var4, var5, var6, par2);
		return var7;
	}
	
	static GuiButton getRenameButton(GuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.buttonDelete;
	}
	
	static GuiButton getSelectButton(GuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.buttonSelect;
	}
	
	static int getSelectedWorld(GuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.selectedWorld;
	}
	
	static List getSize(GuiSelectWorld par0GuiSelectWorld)
	{
		return par0GuiSelectWorld.saveList;
	}
	
	static int onElementSelected(GuiSelectWorld par0GuiSelectWorld, int par1)
	{
		return par0GuiSelectWorld.selectedWorld = par1;
	}
}
