package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.client.Minecraft;

public class GuiScreenLongRunningTask extends GuiScreen
{
	private final int field_96213_b = 666;
	private final GuiScreen field_96215_c;
	private final Thread field_98118_d;
	private volatile String field_96212_d = "";
	private volatile boolean field_96219_n = false;
	private volatile String field_96220_o;
	private volatile boolean field_96218_p = false;
	private int field_96216_q = 0;
	private TaskLongRunning field_96214_r;
	public static final String[] field_96217_a = new String[] { "\u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583", "_ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584", "_ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585", "_ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586", "_ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587", "_ _ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588", "_ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587", "_ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586", "_ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585", "_ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584", "\u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583", "\u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _", "\u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _", "\u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _", "\u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _", "\u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _ _", "\u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _", "\u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _", "\u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _", "\u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _" };
	
	public GuiScreenLongRunningTask(Minecraft par1Minecraft, GuiScreen par2GuiScreen, TaskLongRunning par3TaskLongRunning)
	{
		super.buttonList = Collections.synchronizedList(new ArrayList());
		mc = par1Minecraft;
		field_96215_c = par2GuiScreen;
		field_96214_r = par3TaskLongRunning;
		par3TaskLongRunning.func_96574_a(this);
		field_98118_d = new Thread(par3TaskLongRunning);
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.id == 666)
		{
			field_96218_p = true;
			mc.displayGuiScreen(field_96215_c);
		}
		field_96214_r.func_96572_a(par1GuiButton);
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, field_96212_d, width / 2, height / 2 - 50, 16777215);
		drawCenteredString(fontRenderer, "", width / 2, height / 2 - 10, 16777215);
		if(!field_96219_n)
		{
			drawCenteredString(fontRenderer, field_96217_a[field_96216_q % field_96217_a.length], width / 2, height / 2 + 15, 8421504);
		}
		if(field_96219_n)
		{
			drawCenteredString(fontRenderer, field_96220_o, width / 2, height / 2 + 15, 16711680);
		}
		super.drawScreen(par1, par2, par3);
	}
	
	public boolean func_96207_h()
	{
		return field_96218_p;
	}
	
	public Minecraft func_96208_g()
	{
		return mc;
	}
	
	public void func_96209_a(String par1Str)
	{
		field_96219_n = true;
		field_96220_o = par1Str;
		buttonList.clear();
		buttonList.add(new GuiButton(666, width / 2 - 100, height / 4 + 120 + 12, "Back"));
	}
	
	public void func_96210_b(String par1Str)
	{
		field_96212_d = par1Str;
	}
	
	public void func_98117_g()
	{
		field_98118_d.start();
	}
	
	@Override public void initGui()
	{
		field_96214_r.func_96571_d();
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
	}
	
	@Override public void updateScreen()
	{
		super.updateScreen();
		++field_96216_q;
		field_96214_r.func_96573_a();
	}
}
