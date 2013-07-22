package net.minecraft.src;

import java.util.List;

import net.minecraft.client.Minecraft;

public class GuiScreenPendingInvitation extends GuiScreen
{
	private final GuiScreen field_130061_a;
	private GuiScreenPendingInvitationList field_130059_b;
	private List field_130060_c = Lists.newArrayList();
	private int field_130058_d = -1;
	
	public GuiScreenPendingInvitation(GuiScreen par1GuiScreen)
	{
		field_130061_a = par1GuiScreen;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 1)
			{
				func_130051_i();
			} else if(par1GuiButton.id == 0)
			{
				mc.displayGuiScreen(field_130061_a);
			} else if(par1GuiButton.id == 2)
			{
				func_130057_h();
			} else
			{
				field_130059_b.actionPerformed(par1GuiButton);
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		field_130059_b.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, I18n.func_135053_a("mco.invites.title"), width / 2, 20, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	private void func_130047_j()
	{
		int var1 = field_130058_d;
		if(field_130060_c.size() - 1 == field_130058_d)
		{
			--field_130058_d;
		}
		field_130060_c.remove(var1);
		if(field_130060_c.size() == 0)
		{
			field_130058_d = -1;
		}
	}
	
	private void func_130050_g()
	{
		buttonList.add(new GuiButton(1, width / 2 - 154, height - 52, 153, 20, I18n.func_135053_a("mco.invites.button.accept")));
		buttonList.add(new GuiButton(2, width / 2 + 6, height - 52, 153, 20, I18n.func_135053_a("mco.invites.button.reject")));
		buttonList.add(new GuiButton(0, width / 2 - 75, height - 28, 153, 20, I18n.func_135053_a("gui.back")));
	}
	
	private void func_130051_i()
	{
		if(field_130058_d >= 0 && field_130058_d < field_130060_c.size())
		{
			new GuiScreenPendingInvitationINNER3(this).start();
		}
	}
	
	private void func_130057_h()
	{
		if(field_130058_d >= 0 && field_130058_d < field_130060_c.size())
		{
			new GuiScreenPendingInvitationINNER2(this).start();
		}
	}
	
	@Override public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		field_130059_b = new GuiScreenPendingInvitationList(this);
		new GuiScreenPendingInvitationINNER1(this).start();
		func_130050_g();
	}
	
	@Override public void updateScreen()
	{
		super.updateScreen();
	}
	
	static void func_130040_f(GuiScreenPendingInvitation par0GuiScreenPendingInvitation)
	{
		par0GuiScreenPendingInvitation.func_130047_j();
	}
	
	static Minecraft func_130041_c(GuiScreenPendingInvitation par0GuiScreenPendingInvitation)
	{
		return par0GuiScreenPendingInvitation.mc;
	}
	
	static List func_130042_e(GuiScreenPendingInvitation par0GuiScreenPendingInvitation)
	{
		return par0GuiScreenPendingInvitation.field_130060_c;
	}
	
	static List func_130043_a(GuiScreenPendingInvitation par0GuiScreenPendingInvitation, List par1List)
	{
		return par0GuiScreenPendingInvitation.field_130060_c = par1List;
	}
	
	static Minecraft func_130044_b(GuiScreenPendingInvitation par0GuiScreenPendingInvitation)
	{
		return par0GuiScreenPendingInvitation.mc;
	}
	
	static FontRenderer func_130045_k(GuiScreenPendingInvitation par0GuiScreenPendingInvitation)
	{
		return par0GuiScreenPendingInvitation.fontRenderer;
	}
	
	static Minecraft func_130046_h(GuiScreenPendingInvitation par0GuiScreenPendingInvitation)
	{
		return par0GuiScreenPendingInvitation.mc;
	}
	
	static Minecraft func_130048_a(GuiScreenPendingInvitation par0GuiScreenPendingInvitation)
	{
		return par0GuiScreenPendingInvitation.mc;
	}
	
	static int func_130049_d(GuiScreenPendingInvitation par0GuiScreenPendingInvitation)
	{
		return par0GuiScreenPendingInvitation.field_130058_d;
	}
	
	static FontRenderer func_130052_l(GuiScreenPendingInvitation par0GuiScreenPendingInvitation)
	{
		return par0GuiScreenPendingInvitation.fontRenderer;
	}
	
	static int func_130053_a(GuiScreenPendingInvitation par0GuiScreenPendingInvitation, int par1)
	{
		return par0GuiScreenPendingInvitation.field_130058_d = par1;
	}
	
	static Minecraft func_130054_j(GuiScreenPendingInvitation par0GuiScreenPendingInvitation)
	{
		return par0GuiScreenPendingInvitation.mc;
	}
	
	static Minecraft func_130055_i(GuiScreenPendingInvitation par0GuiScreenPendingInvitation)
	{
		return par0GuiScreenPendingInvitation.mc;
	}
	
	static Minecraft func_130056_g(GuiScreenPendingInvitation par0GuiScreenPendingInvitation)
	{
		return par0GuiScreenPendingInvitation.mc;
	}
}
