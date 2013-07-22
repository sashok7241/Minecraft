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
	int field_104076_e = 0;
	int field_104073_f = 0;
	
	public GuiScreenOnlineServersSubscreen(int p_i23000_1_, int p_i23000_2_, int p_i23000_3_, int p_i23000_4_)
	{
		field_104074_g = p_i23000_1_;
		field_104081_h = p_i23000_2_;
		field_104082_i = p_i23000_3_;
		field_104080_j = p_i23000_4_;
		func_104068_a();
	}
	
	private String func_104067_d()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		String var2 = var1.translateKey("mco.gameMode");
		return var2 + ": " + field_104078_c[field_104073_f];
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
		StringTranslate var1 = StringTranslate.getInstance();
		field_104077_b = new String[] { var1.translateKey("options.difficulty.normal"), var1.translateKey("options.difficulty.hard"), var1.translateKey("options.difficulty.peaceful"), var1.translateKey("options.difficulty.easy") };
		field_104078_c = new String[] { var1.translateKey("mco.gameMode.survival"), var1.translateKey("mco.gameMode.creative"), var1.translateKey("mco.gameMode.adventure") };
		field_104075_d = new String[][] { { var1.translateKey("mco.gameMode.survival.line1"), var1.translateKey("mco.gameMode.survival.line2") }, { var1.translateKey("mco.gameMode.creative.line1"), var1.translateKey("mco.gameMode.creative.line2") }, { var1.translateKey("mco.gameMode.adventure.line1"), var1.translateKey("mco.gameMode.adventure.line2") } };
	}
	
	public void func_104071_a(GuiScreen par1GuiScreen, FontRenderer par2FontRenderer)
	{
		par1GuiScreen.drawString(par2FontRenderer, field_104075_d[field_104073_f][0], field_104082_i, field_104080_j + 50, 10526880);
		par1GuiScreen.drawString(par2FontRenderer, field_104075_d[field_104073_f][1], field_104082_i, field_104080_j + 60, 10526880);
	}
	
	private String func_104072_c()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		String var2 = var1.translateKey("options.difficulty");
		return var2 + ": " + field_104077_b[field_104076_e];
	}
}
