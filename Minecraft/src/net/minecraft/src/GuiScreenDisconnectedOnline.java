package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class GuiScreenDisconnectedOnline extends GuiScreen
{
	private String field_98113_a;
	private String field_98111_b;
	private Object[] field_98112_c;
	private List field_98110_d;
	private final GuiScreen field_98114_n;
	
	public GuiScreenDisconnectedOnline(GuiScreen par1GuiScreen, String par2Str, String par3Str, Object ... par4ArrayOfObj)
	{
		field_98114_n = par1GuiScreen;
		field_98113_a = I18n.func_135053_a(par2Str);
		field_98111_b = par3Str;
		field_98112_c = par4ArrayOfObj;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.id == 0)
		{
			mc.displayGuiScreen(field_98114_n);
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, field_98113_a, width / 2, height / 2 - 50, 11184810);
		int var4 = height / 2 - 30;
		if(field_98110_d != null)
		{
			for(Iterator var5 = field_98110_d.iterator(); var5.hasNext(); var4 += fontRenderer.FONT_HEIGHT)
			{
				String var6 = (String) var5.next();
				drawCenteredString(fontRenderer, var6, width / 2, var4, 16777215);
			}
		}
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, I18n.func_135053_a("gui.back")));
		if(field_98112_c != null)
		{
			field_98110_d = fontRenderer.listFormattedStringToWidth(I18n.func_135052_a(field_98111_b, field_98112_c), width - 50);
		} else
		{
			field_98110_d = fontRenderer.listFormattedStringToWidth(I18n.func_135053_a(field_98111_b), width - 50);
		}
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
	}
}
