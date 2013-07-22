package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.client.Minecraft;

public class GuiScreenCreateOnlineWorld extends ScreenWithCallback
{
	private GuiScreen field_96260_a;
	private GuiTextField field_96257_c;
	private GuiTextField field_96255_b;
	private String field_98108_c;
	private String field_98109_n;
	private static int field_96253_d;
	private static int field_96261_n = 1;
	private static int field_110357_r = 2;
	private boolean field_96256_r;
	private String field_96254_s = "You must enter a name!";
	private WorldTemplate field_110356_u;
	
	public GuiScreenCreateOnlineWorld(GuiScreen par1GuiScreen)
	{
		super.buttonList = Collections.synchronizedList(new ArrayList());
		field_96260_a = par1GuiScreen;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == field_96261_n)
			{
				mc.displayGuiScreen(field_96260_a);
			} else if(par1GuiButton.id == field_96253_d)
			{
				func_96252_h();
			} else if(par1GuiButton.id == field_110357_r)
			{
				mc.displayGuiScreen(new GuiScreenMcoWorldTemplate(this, field_110356_u));
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, I18n.func_135053_a("mco.selectServer.create"), width / 2, 11, 16777215);
		drawString(fontRenderer, I18n.func_135053_a("mco.configure.world.name"), width / 2 - 100, 52, 10526880);
		drawString(fontRenderer, I18n.func_135053_a("mco.create.world.seed"), width / 2 - 100, 98, 10526880);
		if(field_96256_r)
		{
			drawCenteredString(fontRenderer, field_96254_s, width / 2, 167, 16711680);
		}
		field_96257_c.drawTextBox();
		field_96255_b.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void func_110354_a(Object par1Obj)
	{
		func_110355_a((WorldTemplate) par1Obj);
	}
	
	public void func_110355_a(WorldTemplate par1WorldTemplate)
	{
		field_110356_u = par1WorldTemplate;
	}
	
	private boolean func_96249_i()
	{
		field_96256_r = field_96257_c.getText() == null || field_96257_c.getText().trim().equals("");
		return !field_96256_r;
	}
	
	private void func_96252_h()
	{
		if(func_96249_i())
		{
			TaskWorldCreation var1 = new TaskWorldCreation(this, field_96257_c.getText(), "Minecraft Realms Server", field_98109_n, field_110356_u);
			GuiScreenLongRunningTask var2 = new GuiScreenLongRunningTask(mc, field_96260_a, var1);
			var2.func_98117_g();
			mc.displayGuiScreen(var2);
		}
	}
	
	@Override public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(field_96253_d, width / 2 - 100, height / 4 + 120 + 17, 97, 20, I18n.func_135053_a("mco.create.world")));
		buttonList.add(new GuiButton(field_96261_n, width / 2 + 5, height / 4 + 120 + 17, 95, 20, I18n.func_135053_a("gui.cancel")));
		field_96257_c = new GuiTextField(fontRenderer, width / 2 - 100, 65, 200, 20);
		field_96257_c.setFocused(true);
		if(field_98108_c != null)
		{
			field_96257_c.setText(field_98108_c);
		}
		field_96255_b = new GuiTextField(fontRenderer, width / 2 - 100, 111, 200, 20);
		if(field_98109_n != null)
		{
			field_96255_b.setText(field_98109_n);
		}
		if(field_110356_u == null)
		{
			buttonList.add(new GuiButton(field_110357_r, width / 2 - 100, 147, 200, 20, I18n.func_135053_a("mco.template.default.name")));
		} else
		{
			field_96255_b.setText("");
			field_96255_b.setEnabled(false);
			field_96255_b.setFocused(false);
			buttonList.add(new GuiButton(field_110357_r, width / 2 - 100, 147, 200, 20, I18n.func_135053_a("mco.template.name") + ": " + field_110356_u.field_110732_b));
		}
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		field_96257_c.textboxKeyTyped(par1, par2);
		field_96255_b.textboxKeyTyped(par1, par2);
		if(par2 == 15)
		{
			field_96257_c.setFocused(!field_96257_c.isFocused());
			field_96255_b.setFocused(!field_96255_b.isFocused());
		}
		if(par2 == 28 || par2 == 156)
		{
			actionPerformed((GuiButton) buttonList.get(0));
		}
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		field_96257_c.mouseClicked(par1, par2, par3);
		field_96255_b.mouseClicked(par1, par2, par3);
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override public void updateScreen()
	{
		field_96257_c.updateCursorCounter();
		field_98108_c = field_96257_c.getText();
		field_96255_b.updateCursorCounter();
		field_98109_n = field_96255_b.getText();
	}
	
	static Minecraft func_130026_d(GuiScreenCreateOnlineWorld par0GuiScreenCreateOnlineWorld)
	{
		return par0GuiScreenCreateOnlineWorld.mc;
	}
	
	static Minecraft func_130027_e(GuiScreenCreateOnlineWorld par0GuiScreenCreateOnlineWorld)
	{
		return par0GuiScreenCreateOnlineWorld.mc;
	}
	
	static Minecraft func_130028_f(GuiScreenCreateOnlineWorld par0GuiScreenCreateOnlineWorld)
	{
		return par0GuiScreenCreateOnlineWorld.mc;
	}
	
	static Minecraft func_96246_c(GuiScreenCreateOnlineWorld par0GuiScreenCreateOnlineWorld)
	{
		return par0GuiScreenCreateOnlineWorld.mc;
	}
	
	static GuiScreen func_96247_b(GuiScreenCreateOnlineWorld par0GuiScreenCreateOnlineWorld)
	{
		return par0GuiScreenCreateOnlineWorld.field_96260_a;
	}
	
	static Minecraft func_96248_a(GuiScreenCreateOnlineWorld par0GuiScreenCreateOnlineWorld)
	{
		return par0GuiScreenCreateOnlineWorld.mc;
	}
}
