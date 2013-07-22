package net.minecraft.src;

import java.io.UnsupportedEncodingException;

public class GuiScreenEditOnlineWorld extends GuiScreen
{
	private GuiScreen field_96204_a;
	private GuiScreen field_96202_b;
	private GuiTextField field_96203_c;
	private GuiTextField field_96201_d;
	private McoServer field_96205_n;
	private GuiButton field_96206_o;
	private int field_104054_p;
	private int field_104053_q;
	private int field_104052_r;
	private GuiScreenOnlineServersSubscreen field_104051_s;
	
	public GuiScreenEditOnlineWorld(GuiScreen par1GuiScreen, GuiScreen par2GuiScreen, McoServer par3McoServer)
	{
		field_96204_a = par1GuiScreen;
		field_96202_b = par2GuiScreen;
		field_96205_n = par3McoServer;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 1)
			{
				mc.displayGuiScreen(field_96204_a);
			} else if(par1GuiButton.id == 0)
			{
				func_96200_g();
			} else if(par1GuiButton.id == 2)
			{
				mc.displayGuiScreen(new GuiScreenResetWorld(this, field_96205_n));
			} else
			{
				field_104051_s.func_104069_a(par1GuiButton);
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, I18n.func_135053_a("mco.configure.world.edit.title"), width / 2, 17, 16777215);
		drawString(fontRenderer, I18n.func_135053_a("mco.configure.world.name"), field_104054_p, 43, 10526880);
		drawString(fontRenderer, I18n.func_135053_a("mco.configure.world.description"), field_104054_p, 84, 10526880);
		field_96201_d.drawTextBox();
		field_96203_c.drawTextBox();
		field_104051_s.func_104071_a(this, fontRenderer);
		super.drawScreen(par1, par2, par3);
	}
	
	private void func_96200_g()
	{
		McoClient var1 = new McoClient(mc.func_110432_I());
		try
		{
			String var2 = field_96203_c.getText() != null && !field_96203_c.getText().trim().equals("") ? field_96203_c.getText() : "";
			var1.func_96384_a(field_96205_n.field_96408_a, field_96201_d.getText(), var2, field_104051_s.field_104076_e, field_104051_s.field_104073_f);
			field_96205_n.func_96399_a(field_96201_d.getText());
			field_96205_n.func_96400_b(field_96203_c.getText());
			field_96205_n.field_110729_i = field_104051_s.field_104076_e;
			field_96205_n.field_110728_j = field_104051_s.field_104073_f;
			mc.displayGuiScreen(new GuiScreenConfigureWorld(field_96202_b, field_96205_n));
		} catch(ExceptionMcoService var3)
		{
			mc.getLogAgent().logSevere(var3.toString());
		} catch(UnsupportedEncodingException var4)
		{
			mc.getLogAgent().logWarning("Realms: " + var4.getLocalizedMessage());
		}
	}
	
	@Override public void initGui()
	{
		field_104054_p = width / 4;
		field_104053_q = width / 4 - 2;
		field_104052_r = width / 2 + 4;
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(field_96206_o = new GuiButton(0, field_104054_p, height / 4 + 120 + 22, field_104053_q, 20, I18n.func_135053_a("mco.configure.world.buttons.done")));
		buttonList.add(new GuiButton(1, field_104052_r, height / 4 + 120 + 22, field_104053_q, 20, I18n.func_135053_a("gui.cancel")));
		field_96201_d = new GuiTextField(fontRenderer, field_104054_p, 56, 212, 20);
		field_96201_d.setFocused(true);
		field_96201_d.setMaxStringLength(32);
		field_96201_d.setText(field_96205_n.func_96398_b());
		field_96203_c = new GuiTextField(fontRenderer, field_104054_p, 96, 212, 20);
		field_96203_c.setMaxStringLength(32);
		field_96203_c.setText(field_96205_n.func_96397_a());
		field_104051_s = new GuiScreenOnlineServersSubscreen(width, height, field_104054_p, 122, field_96205_n.field_110729_i, field_96205_n.field_110728_j);
		buttonList.addAll(field_104051_s.field_104079_a);
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		field_96201_d.textboxKeyTyped(par1, par2);
		field_96203_c.textboxKeyTyped(par1, par2);
		if(par2 == 15)
		{
			field_96201_d.setFocused(!field_96201_d.isFocused());
			field_96203_c.setFocused(!field_96203_c.isFocused());
		}
		if(par2 == 28 || par2 == 156)
		{
			func_96200_g();
		}
		field_96206_o.enabled = field_96201_d.getText() != null && !field_96201_d.getText().trim().equals("");
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		field_96203_c.mouseClicked(par1, par2, par3);
		field_96201_d.mouseClicked(par1, par2, par3);
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override public void updateScreen()
	{
		field_96201_d.updateCursorCounter();
		field_96203_c.updateCursorCounter();
	}
}
