package net.minecraft.src;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;

public class GuiScreenMcoWorldTemplate extends GuiScreen
{
	private final ScreenWithCallback field_110401_a;
	private WorldTemplate field_110398_b;
	private List field_110399_c = Collections.emptyList();
	private GuiScreenMcoWorldTemplateSelectionList field_110396_d;
	private int field_110397_e = -1;
	private GuiButton field_110400_p;
	
	public GuiScreenMcoWorldTemplate(ScreenWithCallback par1ScreenWithCallback, WorldTemplate par2WorldTemplate)
	{
		field_110401_a = par1ScreenWithCallback;
		field_110398_b = par2WorldTemplate;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 1)
			{
				func_110394_h();
			} else if(par1GuiButton.id == 0)
			{
				field_110401_a.func_110354_a((Object) null);
				mc.displayGuiScreen(field_110401_a);
			} else
			{
				field_110396_d.actionPerformed(par1GuiButton);
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		field_110396_d.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, I18n.func_135053_a("mco.template.title"), width / 2, 20, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	private void func_110385_g()
	{
		buttonList.add(new GuiButton(0, width / 2 + 6, height - 52, 153, 20, I18n.func_135053_a("gui.cancel")));
		buttonList.add(field_110400_p = new GuiButton(1, width / 2 - 154, height - 52, 153, 20, I18n.func_135053_a("mco.template.button.select")));
	}
	
	private void func_110394_h()
	{
		if(field_110397_e >= 0 && field_110397_e < field_110399_c.size())
		{
			field_110401_a.func_110354_a(field_110399_c.get(field_110397_e));
			mc.displayGuiScreen(field_110401_a);
		}
	}
	
	@Override public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		field_110396_d = new GuiScreenMcoWorldTemplateSelectionList(this);
		new GuiScreenMcoWorldTemplateDownloadThread(this).start();
		func_110385_g();
	}
	
	@Override public void updateScreen()
	{
		super.updateScreen();
	}
	
	static Minecraft func_110382_a(GuiScreenMcoWorldTemplate par0GuiScreenMcoWorldTemplate)
	{
		return par0GuiScreenMcoWorldTemplate.mc;
	}
	
	static FontRenderer func_110384_i(GuiScreenMcoWorldTemplate par0GuiScreenMcoWorldTemplate)
	{
		return par0GuiScreenMcoWorldTemplate.fontRenderer;
	}
	
	static FontRenderer func_110387_h(GuiScreenMcoWorldTemplate par0GuiScreenMcoWorldTemplate)
	{
		return par0GuiScreenMcoWorldTemplate.fontRenderer;
	}
	
	static List func_110388_a(GuiScreenMcoWorldTemplate par0GuiScreenMcoWorldTemplate, List par1List)
	{
		return par0GuiScreenMcoWorldTemplate.field_110399_c = par1List;
	}
	
	static FontRenderer func_110389_g(GuiScreenMcoWorldTemplate par0GuiScreenMcoWorldTemplate)
	{
		return par0GuiScreenMcoWorldTemplate.fontRenderer;
	}
	
	static Minecraft func_110392_b(GuiScreenMcoWorldTemplate par0GuiScreenMcoWorldTemplate)
	{
		return par0GuiScreenMcoWorldTemplate.mc;
	}
	
	static List func_110395_c(GuiScreenMcoWorldTemplate par0GuiScreenMcoWorldTemplate)
	{
		return par0GuiScreenMcoWorldTemplate.field_110399_c;
	}
	
	static int func_130062_f(GuiScreenMcoWorldTemplate par0GuiScreenMcoWorldTemplate)
	{
		return par0GuiScreenMcoWorldTemplate.field_110397_e;
	}
	
	static FontRenderer func_130063_j(GuiScreenMcoWorldTemplate par0GuiScreenMcoWorldTemplate)
	{
		return par0GuiScreenMcoWorldTemplate.fontRenderer;
	}
	
	static int func_130064_a(GuiScreenMcoWorldTemplate par0GuiScreenMcoWorldTemplate, int par1)
	{
		return par0GuiScreenMcoWorldTemplate.field_110397_e = par1;
	}
	
	static WorldTemplate func_130065_a(GuiScreenMcoWorldTemplate par0GuiScreenMcoWorldTemplate, WorldTemplate par1WorldTemplate)
	{
		return par0GuiScreenMcoWorldTemplate.field_110398_b = par1WorldTemplate;
	}
	
	static Minecraft func_130066_c(GuiScreenMcoWorldTemplate par0GuiScreenMcoWorldTemplate)
	{
		return par0GuiScreenMcoWorldTemplate.mc;
	}
	
	static WorldTemplate func_130067_e(GuiScreenMcoWorldTemplate par0GuiScreenMcoWorldTemplate)
	{
		return par0GuiScreenMcoWorldTemplate.field_110398_b;
	}
}
