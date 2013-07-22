package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class GuiSnooper extends GuiScreen
{
	private final GuiScreen snooperGuiScreen;
	private final GameSettings snooperGameSettings;
	private final List field_74098_c = new ArrayList();
	private final List field_74096_d = new ArrayList();
	private String snooperTitle;
	private String[] field_74101_n;
	private GuiSnooperList snooperList;
	private GuiButton buttonAllowSnooping;
	
	public GuiSnooper(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
	{
		snooperGuiScreen = par1GuiScreen;
		snooperGameSettings = par2GameSettings;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 2)
			{
				snooperGameSettings.saveOptions();
				snooperGameSettings.saveOptions();
				mc.displayGuiScreen(snooperGuiScreen);
			}
			if(par1GuiButton.id == 1)
			{
				snooperGameSettings.setOptionValue(EnumOptions.SNOOPER_ENABLED, 1);
				buttonAllowSnooping.displayString = snooperGameSettings.getKeyBinding(EnumOptions.SNOOPER_ENABLED);
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		snooperList.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, snooperTitle, width / 2, 8, 16777215);
		int var4 = 22;
		String[] var5 = field_74101_n;
		int var6 = var5.length;
		for(int var7 = 0; var7 < var6; ++var7)
		{
			String var8 = var5[var7];
			drawCenteredString(fontRenderer, var8, width / 2, var4, 8421504);
			var4 += fontRenderer.FONT_HEIGHT;
		}
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		snooperTitle = StatCollector.translateToLocal("options.snooper.title");
		String var1 = StatCollector.translateToLocal("options.snooper.desc");
		ArrayList var2 = new ArrayList();
		Iterator var3 = fontRenderer.listFormattedStringToWidth(var1, width - 30).iterator();
		while(var3.hasNext())
		{
			String var4 = (String) var3.next();
			var2.add(var4);
		}
		field_74101_n = (String[]) var2.toArray(new String[0]);
		field_74098_c.clear();
		field_74096_d.clear();
		buttonList.add(buttonAllowSnooping = new GuiButton(1, width / 2 - 152, height - 30, 150, 20, snooperGameSettings.getKeyBinding(EnumOptions.SNOOPER_ENABLED)));
		buttonList.add(new GuiButton(2, width / 2 + 2, height - 30, 150, 20, StatCollector.translateToLocal("gui.done")));
		boolean var6 = mc.getIntegratedServer() != null && mc.getIntegratedServer().getPlayerUsageSnooper() != null;
		Iterator var7 = new TreeMap(mc.getPlayerUsageSnooper().getCurrentStats()).entrySet().iterator();
		Entry var5;
		while(var7.hasNext())
		{
			var5 = (Entry) var7.next();
			field_74098_c.add((var6 ? "C " : "") + (String) var5.getKey());
			field_74096_d.add(fontRenderer.trimStringToWidth((String) var5.getValue(), width - 220));
		}
		if(var6)
		{
			var7 = new TreeMap(mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats()).entrySet().iterator();
			while(var7.hasNext())
			{
				var5 = (Entry) var7.next();
				field_74098_c.add("S " + (String) var5.getKey());
				field_74096_d.add(fontRenderer.trimStringToWidth((String) var5.getValue(), width - 220));
			}
		}
		snooperList = new GuiSnooperList(this);
	}
	
	static List func_74094_b(GuiSnooper par0GuiSnooper)
	{
		return par0GuiSnooper.field_74096_d;
	}
	
	static List func_74095_a(GuiSnooper par0GuiSnooper)
	{
		return par0GuiSnooper.field_74098_c;
	}
}
