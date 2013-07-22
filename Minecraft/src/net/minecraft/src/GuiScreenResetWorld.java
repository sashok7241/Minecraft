package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class GuiScreenResetWorld extends ScreenWithCallback
{
	private GuiScreen field_96152_a;
	private McoServer field_96150_b;
	private GuiTextField field_96151_c;
	private final int field_96149_d = 1;
	private final int field_96153_n = 2;
	private static int field_110360_p = 3;
	private WorldTemplate field_110359_q;
	private GuiButton field_96154_o;
	
	public GuiScreenResetWorld(GuiScreen par1GuiScreen, McoServer par2McoServer)
	{
		field_96152_a = par1GuiScreen;
		field_96150_b = par2McoServer;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 2)
			{
				mc.displayGuiScreen(field_96152_a);
			} else if(par1GuiButton.id == 1)
			{
				String var2 = I18n.func_135053_a("mco.configure.world.reset.question.line1");
				String var3 = I18n.func_135053_a("mco.configure.world.reset.question.line2");
				mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmationType.Warning, var2, var3, 1));
			} else if(par1GuiButton.id == field_110360_p)
			{
				mc.displayGuiScreen(new GuiScreenMcoWorldTemplate(this, field_110359_q));
			}
		}
	}
	
	@Override public void confirmClicked(boolean par1, int par2)
	{
		if(par1 && par2 == 1)
		{
			func_140006_g();
		} else
		{
			mc.displayGuiScreen(this);
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, I18n.func_135053_a("mco.reset.world.title"), width / 2, 17, 16777215);
		drawCenteredString(fontRenderer, I18n.func_135053_a("mco.reset.world.warning"), width / 2, 56, 16711680);
		drawString(fontRenderer, I18n.func_135053_a("mco.reset.world.seed"), width / 2 - 100, 86, 10526880);
		field_96151_c.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	@Override void func_110354_a(Object par1Obj)
	{
		func_110358_a((WorldTemplate) par1Obj);
	}
	
	void func_110358_a(WorldTemplate par1WorldTemplate)
	{
		field_110359_q = par1WorldTemplate;
	}
	
	private void func_140006_g()
	{
		TaskResetWorld var1 = new TaskResetWorld(this, field_96150_b.field_96408_a, field_96151_c.getText(), field_110359_q);
		GuiScreenLongRunningTask var2 = new GuiScreenLongRunningTask(mc, field_96152_a, var1);
		var2.func_98117_g();
		mc.displayGuiScreen(var2);
	}
	
	@Override public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(field_96154_o = new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, 97, 20, I18n.func_135053_a("mco.configure.world.buttons.reset")));
		buttonList.add(new GuiButton(2, width / 2 + 5, height / 4 + 120 + 12, 97, 20, I18n.func_135053_a("gui.cancel")));
		field_96151_c = new GuiTextField(fontRenderer, width / 2 - 100, 99, 200, 20);
		field_96151_c.setFocused(true);
		field_96151_c.setMaxStringLength(32);
		field_96151_c.setText("");
		if(field_110359_q == null)
		{
			buttonList.add(new GuiButton(field_110360_p, width / 2 - 100, 125, 200, 20, I18n.func_135053_a("mco.template.default.name")));
		} else
		{
			field_96151_c.setText("");
			field_96151_c.setEnabled(false);
			field_96151_c.setFocused(false);
			buttonList.add(new GuiButton(field_110360_p, width / 2 - 100, 125, 200, 20, I18n.func_135053_a("mco.template.name") + ": " + field_110359_q.field_110732_b));
		}
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		field_96151_c.textboxKeyTyped(par1, par2);
		if(par2 == 28 || par2 == 156)
		{
			actionPerformed(field_96154_o);
		}
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		field_96151_c.mouseClicked(par1, par2, par3);
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override public void updateScreen()
	{
		field_96151_c.updateCursorCounter();
	}
	
	static Minecraft func_130024_d(GuiScreenResetWorld par0GuiScreenResetWorld)
	{
		return par0GuiScreenResetWorld.mc;
	}
	
	static Minecraft func_130025_c(GuiScreenResetWorld par0GuiScreenResetWorld)
	{
		return par0GuiScreenResetWorld.mc;
	}
	
	static Minecraft func_96147_b(GuiScreenResetWorld par0GuiScreenResetWorld)
	{
		return par0GuiScreenResetWorld.mc;
	}
	
	static GuiScreen func_96148_a(GuiScreenResetWorld par0GuiScreenResetWorld)
	{
		return par0GuiScreenResetWorld.field_96152_a;
	}
}
