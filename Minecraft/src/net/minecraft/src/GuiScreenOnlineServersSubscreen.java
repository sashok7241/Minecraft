package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class GuiScreenOnlineServersSubscreen
{
	private final int field_104074_g;
	private final int field_104081_h;
	private final int field_104082_i;
	private final int field_104080_j;
	List field_104079_a = new ArrayList();
	String[] field_104077_b;
	String[] field_104078_c;
	String[][] field_104075_d;
	int field_104076_e;
	int field_104073_f;
	
	public GuiScreenOnlineServersSubscreen(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		field_104074_g = par1;
		field_104081_h = par2;
		field_104082_i = par3;
		field_104080_j = par4;
		field_104076_e = par5;
		field_104073_f = par6;
		func_104068_a();
	}
	
	private String func_104067_d()
	{
		String var1 = I18n.func_135053_a("selectWorld.gameMode");
		return var1 + ": " + field_104078_c[field_104073_f];
	}
	
	private void func_104068_a()
	{
		func_104070_b();
		field_104079_a.add(new GuiButton(5005, field_104082_i, field_104080_j + 1, 212, 20, func_104072_c()));
		field_104079_a.add(new GuiButton(5006, field_104082_i, field_104080_j + 25, 212, 20, func_104067_d()));
	}
	
	void func_104069_a(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 5005)
			{
				field_104076_e = (field_104076_e + 1) % field_104077_b.length;
				par1GuiButton.displayString = func_104072_c();
			} else if(par1GuiButton.id == 5006)
			{
				field_104073_f = (field_104073_f + 1) % field_104078_c.length;
				par1GuiButton.displayString = func_104067_d();
			}
		}
	}
	
	private void func_104070_b()
	{
		field_104077_b = new String[] { I18n.func_135053_a("options.difficulty.peaceful"), I18n.func_135053_a("options.difficulty.easy"), I18n.func_135053_a("options.difficulty.normal"), I18n.func_135053_a("options.difficulty.hard") };
		field_104078_c = new String[] { I18n.func_135053_a("selectWorld.gameMode.survival"), I18n.func_135053_a("selectWorld.gameMode.creative"), I18n.func_135053_a("selectWorld.gameMode.adventure") };
		field_104075_d = new String[][] { { I18n.func_135053_a("selectWorld.gameMode.survival.line1"), I18n.func_135053_a("selectWorld.gameMode.survival.line2") }, { I18n.func_135053_a("selectWorld.gameMode.creative.line1"), I18n.func_135053_a("selectWorld.gameMode.creative.line2") }, { I18n.func_135053_a("selectWorld.gameMode.adventure.line1"), I18n.func_135053_a("selectWorld.gameMode.adventure.line2") } };
	}
	
	public void func_104071_a(GuiScreen par1GuiScreen, FontRenderer par2FontRenderer)
	{
		par1GuiScreen.drawString(par2FontRenderer, field_104075_d[field_104073_f][0], field_104082_i, field_104080_j + 50, 10526880);
		par1GuiScreen.drawString(par2FontRenderer, field_104075_d[field_104073_f][1], field_104082_i, field_104080_j + 60, 10526880);
	}
	
	private String func_104072_c()
	{
		String var1 = I18n.func_135053_a("options.difficulty");
		return var1 + ": " + field_104077_b[field_104076_e];
	}
}
