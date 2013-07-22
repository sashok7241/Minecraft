package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

class GuiSlotLanguage extends GuiSlot
{
	private final List field_77251_g;
	private final Map field_77253_h;
	final GuiLanguage languageGui;
	
	public GuiSlotLanguage(GuiLanguage par1GuiLanguage)
	{
		super(par1GuiLanguage.mc, par1GuiLanguage.width, par1GuiLanguage.height, 32, par1GuiLanguage.height - 65 + 4, 18);
		languageGui = par1GuiLanguage;
		field_77251_g = Lists.newArrayList();
		field_77253_h = Maps.newHashMap();
		Iterator var2 = GuiLanguage.func_135011_a(par1GuiLanguage).func_135040_d().iterator();
		while(var2.hasNext())
		{
			Language var3 = (Language) var2.next();
			field_77253_h.put(var3.func_135034_a(), var3);
			field_77251_g.add(var3.func_135034_a());
		}
	}
	
	@Override protected void drawBackground()
	{
		languageGui.drawDefaultBackground();
	}
	
	@Override protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		languageGui.fontRenderer.setBidiFlag(true);
		languageGui.drawCenteredString(languageGui.fontRenderer, ((Language) field_77253_h.get(field_77251_g.get(par1))).toString(), languageGui.width / 2, par3 + 1, 16777215);
		languageGui.fontRenderer.setBidiFlag(GuiLanguage.func_135011_a(languageGui).func_135041_c().func_135035_b());
	}
	
	@Override protected void elementClicked(int par1, boolean par2)
	{
		Language var3 = (Language) field_77253_h.get(field_77251_g.get(par1));
		GuiLanguage.func_135011_a(languageGui).func_135045_a(var3);
		GuiLanguage.getGameSettings(languageGui).language = var3.func_135034_a();
		languageGui.mc.func_110436_a();
		languageGui.fontRenderer.setUnicodeFlag(GuiLanguage.func_135011_a(languageGui).func_135042_a());
		languageGui.fontRenderer.setBidiFlag(GuiLanguage.func_135011_a(languageGui).func_135044_b());
		GuiLanguage.getDoneButton(languageGui).displayString = I18n.func_135053_a("gui.done");
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
		return ((String) field_77251_g.get(par1)).equals(GuiLanguage.func_135011_a(languageGui).func_135041_c().func_135034_a());
	}
}
