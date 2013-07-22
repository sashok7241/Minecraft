package net.minecraft.src;

import java.io.IOException;

public class GuiScreenInvite extends GuiScreen
{
	private GuiTextField field_96227_a;
	private McoServer field_96223_b;
	private final GuiScreen field_96224_c;
	private final GuiScreenConfigureWorld field_96222_d;
	private final int field_96228_n = 0;
	private final int field_96229_o = 1;
	private String field_101016_p = "Could not invite the provided name";
	private String field_96226_p;
	private boolean field_96225_q = false;
	
	public GuiScreenInvite(GuiScreen p_i22002_1_, GuiScreenConfigureWorld p_i22002_2_, McoServer p_i22002_3_)
	{
		field_96224_c = p_i22002_1_;
		field_96222_d = p_i22002_2_;
		field_96223_b = p_i22002_3_;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 1)
			{
				mc.displayGuiScreen(field_96222_d);
			} else if(par1GuiButton.id == 0)
			{
				McoClient var2 = new McoClient(mc.session);
				try
				{
					McoServer var3 = var2.func_96387_b(field_96223_b.field_96408_a, field_96227_a.getText());
					if(var3 != null)
					{
						field_96223_b.field_96402_f = var3.field_96402_f;
						mc.displayGuiScreen(new GuiScreenConfigureWorld(field_96224_c, field_96223_b));
					} else
					{
						func_101015_a(field_101016_p);
					}
				} catch(ExceptionMcoService var4)
				{
					func_101015_a(var4.field_96391_b);
				} catch(IOException var5)
				{
					func_101015_a(field_101016_p);
				}
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		StringTranslate var4 = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer, var4.translateKey(""), width / 2, 17, 16777215);
		drawString(fontRenderer, var4.translateKey("mco.configure.world.invite.profile.name"), width / 2 - 100, 53, 10526880);
		if(field_96225_q)
		{
			drawCenteredString(fontRenderer, field_96226_p, width / 2, 100, 16711680);
		}
		field_96227_a.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	private void func_101015_a(String par1Str)
	{
		field_96225_q = true;
		field_96226_p = par1Str;
	}
	
	@Override public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, var1.translateKey("mco.configure.world.buttons.invite")));
		buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
		field_96227_a = new GuiTextField(fontRenderer, width / 2 - 100, 66, 200, 20);
		field_96227_a.setFocused(true);
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		field_96227_a.textboxKeyTyped(par1, par2);
		if(par1 == 9)
		{
			if(field_96227_a.isFocused())
			{
				field_96227_a.setFocused(false);
			} else
			{
				field_96227_a.setFocused(true);
			}
		}
		if(par1 == 13)
		{
			actionPerformed((GuiButton) buttonList.get(0));
		}
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		field_96227_a.mouseClicked(par1, par2, par3);
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override public void updateScreen()
	{
		field_96227_a.updateCursorCounter();
	}
}
