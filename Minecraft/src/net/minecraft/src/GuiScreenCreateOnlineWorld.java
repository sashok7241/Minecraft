package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.client.Minecraft;

public class GuiScreenCreateOnlineWorld extends GuiScreen
{
	private GuiScreen field_96260_a;
	private GuiTextField field_96257_c;
	private GuiTextField field_96255_b;
	private String field_98108_c;
	private String field_98109_n;
	private static int field_96253_d = 0;
	private static int field_96261_n = 1;
	private boolean field_96256_r = false;
	private String field_96254_s = "You must enter a name!";
	
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
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		StringTranslate var4 = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer, var4.translateKey("mco.selectServer.create"), width / 2, 11, 16777215);
		drawString(fontRenderer, var4.translateKey("mco.configure.world.name"), width / 2 - 100, 52, 10526880);
		drawString(fontRenderer, var4.translateKey("mco.configure.world.seed"), width / 2 - 100, 98, 10526880);
		if(field_96256_r)
		{
			drawCenteredString(fontRenderer, field_96254_s, width / 2, 167, 16711680);
		}
		field_96257_c.drawTextBox();
		field_96255_b.drawTextBox();
		super.drawScreen(par1, par2, par3);
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
			TaskWorldCreation var1 = new TaskWorldCreation(this, field_96257_c.getText(), "Minecraft Realms Server", "NO_LOCATION", field_98109_n);
			GuiScreenLongRunningTask var2 = new GuiScreenLongRunningTask(mc, field_96260_a, var1);
			var2.func_98117_g();
			mc.displayGuiScreen(var2);
		}
	}
	
	@Override public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(field_96253_d, width / 2 - 100, height / 4 + 120 + 17, 97, 20, var1.translateKey("mco.create.world")));
		buttonList.add(new GuiButton(field_96261_n, width / 2 + 5, height / 4 + 120 + 17, 95, 20, var1.translateKey("gui.cancel")));
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
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		field_96257_c.textboxKeyTyped(par1, par2);
		field_96255_b.textboxKeyTyped(par1, par2);
		if(par1 == 9)
		{
			field_96257_c.setFocused(!field_96257_c.isFocused());
			field_96255_b.setFocused(!field_96255_b.isFocused());
		}
		if(par1 == 13)
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
