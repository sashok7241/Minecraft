package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

class GuiSlotLanguage extends GuiSlot
{
	private ArrayList field_77251_g;
	private TreeMap field_77253_h;
	final GuiLanguage languageGui;
	
	public GuiSlotLanguage(GuiLanguage par1GuiLanguage)
	{
		super(par1GuiLanguage.mc, par1GuiLanguage.width, par1GuiLanguage.height, 32, par1GuiLanguage.height - 65 + 4, 18);
		languageGui = par1GuiLanguage;
		field_77253_h = StringTranslate.getInstance().getLanguageList();
		field_77251_g = new ArrayList();
		Iterator var2 = field_77253_h.keySet().iterator();
		while(var2.hasNext())
		{
			String var3 = (String) var2.next();
			field_77251_g.add(var3);
		}
	}
	
	@Override protected void drawBackground()
	{
		languageGui.drawDefaultBackground();
	}
	
	@Override protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		languageGui.fontRenderer.setBidiFlag(true);
		languageGui.drawCenteredString(languageGui.fontRenderer, (String) field_77253_h.get(field_77251_g.get(par1)), languageGui.width / 2, par3 + 1, 16777215);
		languageGui.fontRenderer.setBidiFlag(StringTranslate.isBidirectional(GuiLanguage.getGameSettings(languageGui).language));
	}
	
	@Override protected void elementClicked(int par1, boolean par2)
	{
		StringTranslate.getInstance().setLanguage((String) field_77251_g.get(par1), false);
		languageGui.mc.fontRenderer.setUnicodeFlag(StringTranslate.getInstance().isUnicode());
		GuiLanguage.getGameSettings(languageGui).language = (String) field_77251_g.get(par1);
		languageGui.fontRenderer.setBidiFlag(StringTranslate.isBidirectional(GuiLanguage.getGameSettings(languageGui).language));
		GuiLanguage.getDoneButton(languageGui).displayString = StringTranslate.getInstance().translateKey("gui.done");
		GuiLanguage.getGameSettings(languageGui).saveOptions();
	}
	
	@Override protected int getContentHeight()
	{
		return getSize() * 18;
	}
	
	@Override protected int getSize()
	{
		return field_77251_g.size();
	}
	
	@Override protected boolean isSelected(int par1)
	{
		return ((String) field_77251_g.get(par1)).equals(StringTranslate.getInstance().getCurrentLanguage());
	}
}
