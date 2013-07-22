package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class GuiDisconnected extends GuiScreen
{
	private String errorMessage;
	private String errorDetail;
	private Object[] field_74247_c;
	private List field_74245_d;
	private final GuiScreen field_98095_n;
	
	public GuiDisconnected(GuiScreen par1GuiScreen, String par2Str, String par3Str, Object ... par4ArrayOfObj)
	{
		field_98095_n = par1GuiScreen;
		errorMessage = I18n.func_135053_a(par2Str);
		errorDetail = par3Str;
		field_74247_c = par4ArrayOfObj;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.id == 0)
		{
			mc.displayGuiScreen(field_98095_n);
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, errorMessage, width / 2, height / 2 - 50, 11184810);
		int var4 = height / 2 - 30;
		if(field_74245_d != null)
		{
			for(Iterator var5 = field_74245_d.iterator(); var5.hasNext(); var4 += fontRenderer.FONT_HEIGHT)
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
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, I18n.func_135053_a("gui.toMenu")));
		if(field_74247_c != null)
		{
			field_74245_d = fontRenderer.listFormattedStringToWidth(I18n.func_135052_a(errorDetail, field_74247_c), width - 50);
		} else
		{
			field_74245_d = fontRenderer.listFormattedStringToWidth(I18n.func_135053_a(errorDetail), width - 50);
		}
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
	}
}
