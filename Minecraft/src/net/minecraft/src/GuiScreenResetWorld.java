package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class GuiScreenResetWorld extends GuiScreen
{
	private GuiScreen field_96152_a;
	private McoServer field_96150_b;
	private GuiTextField field_96151_c;
	private final int field_96149_d = 1;
	private final int field_96153_n = 2;
	private GuiButton field_96154_o;
	
	public GuiScreenResetWorld(GuiScreen p_i10003_1_, McoServer p_i10003_2_)
	{
		field_96152_a = p_i10003_1_;
		field_96150_b = p_i10003_2_;
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
				TaskResetWorld var2 = new TaskResetWorld(this, field_96150_b.field_96408_a, field_96151_c.getText());
				GuiScreenLongRunningTask var3 = new GuiScreenLongRunningTask(mc, field_96152_a, var2);
				var3.func_98117_g();
				mc.displayGuiScreen(var3);
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		StringTranslate var4 = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer, var4.translateKey("mco.reset.world.title"), width / 2, 17, 16777215);
		drawCenteredString(fontRenderer, var4.translateKey("mco.reset.world.warning"), width / 2, 66, 16711680);
		drawString(fontRenderer, var4.translateKey("mco.reset.world.seed"), width / 2 - 100, 96, 10526880);
		field_96151_c.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(field_96154_o = new GuiButton(1, width / 2 - 100, height / 4 + 96 + 12, var1.translateKey("mco.configure.world.buttons.reset")));
		buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
		field_96151_c = new GuiTextField(fontRenderer, width / 2 - 100, 109, 200, 20);
		field_96151_c.setFocused(true);
		field_96151_c.setMaxStringLength(32);
		field_96151_c.setText("");
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		field_96151_c.textboxKeyTyped(par1, par2);
		if(par1 == 13)
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
	
	static Minecraft func_96147_b(GuiScreenResetWorld par0GuiScreenResetWorld)
	{
		return par0GuiScreenResetWorld.mc;
	}
	
	static GuiScreen func_96148_a(GuiScreenResetWorld par0GuiScreenResetWorld)
	{
		return par0GuiScreenResetWorld.field_96152_a;
	}
}
