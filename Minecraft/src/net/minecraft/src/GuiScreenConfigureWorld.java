package net.minecraft.src;

import java.io.IOException;

import net.minecraft.client.Minecraft;

public class GuiScreenConfigureWorld extends GuiScreen
{
	private final GuiScreen field_96285_a;
	private McoServer field_96280_b;
	private SelectionListInvited field_96282_c;
	private int field_96277_d;
	private int field_96286_n;
	private int field_96287_o;
	private int field_96284_p = -1;
	private String field_96283_q;
	private GuiButton field_96281_r;
	private GuiButton field_96279_s;
	private GuiButton field_96278_t;
	private GuiButton field_96276_u;
	private GuiButton field_98128_v;
	private GuiButton field_98127_w;
	private GuiButton field_98129_x;
	private boolean field_102020_y;
	
	public GuiScreenConfigureWorld(GuiScreen par1, McoServer par2)
	{
		field_96285_a = par1;
		field_96280_b = par2;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 10)
			{
				if(field_102020_y)
				{
					((GuiScreenOnlineServers) field_96285_a).func_102018_a(field_96280_b.field_96408_a);
				}
				mc.displayGuiScreen(field_96285_a);
			} else if(par1GuiButton.id == 5)
			{
				mc.displayGuiScreen(new GuiScreenEditOnlineWorld(this, field_96285_a, field_96280_b));
			} else if(par1GuiButton.id == 1)
			{
				StringTranslate var2 = StringTranslate.getInstance();
				String var3 = var2.translateKey("mco.configure.world.close.question.line1");
				String var4 = var2.translateKey("mco.configure.world.close.question.line2");
				mc.displayGuiScreen(new GuiScreenConfirmation(this, "Warning!", var3, var4, 1));
			} else if(par1GuiButton.id == 0)
			{
				func_96268_g();
			} else if(par1GuiButton.id == 4)
			{
				mc.displayGuiScreen(new GuiScreenInvite(field_96285_a, this, field_96280_b));
			} else if(par1GuiButton.id == 3)
			{
				func_96272_i();
			} else if(par1GuiButton.id == 6)
			{
				mc.displayGuiScreen(new GuiScreenResetWorld(this, field_96280_b));
			} else if(par1GuiButton.id == 7)
			{
				mc.displayGuiScreen(new GuiScreenSubscription(this, field_96280_b));
			}
		}
	}
	
	@Override public void confirmClicked(boolean par1, int par2)
	{
		if(par2 == 3)
		{
			if(par1)
			{
				McoClient var3 = new McoClient(mc.session);
				try
				{
					var3.func_96381_a(field_96280_b.field_96408_a, field_96283_q);
				} catch(ExceptionMcoService var5)
				{
					System.err.println("Could not uninvite the selected user");
				}
				func_96267_d(field_96284_p);
			}
			mc.displayGuiScreen(new GuiScreenConfigureWorld(field_96285_a, field_96280_b));
		}
		if(par2 == 1)
		{
			if(par1)
			{
				func_96275_h();
			}
			mc.displayGuiScreen(this);
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		StringTranslate var4 = StringTranslate.getInstance();
		drawDefaultBackground();
		field_96282_c.func_96612_a(par1, par2, par3);
		drawCenteredString(fontRenderer, var4.translateKey("mco.configure.world.title"), width / 2, 17, 16777215);
		drawString(fontRenderer, var4.translateKey("mco.configure.world.name"), field_96277_d, func_96264_a(1), 10526880);
		drawString(fontRenderer, field_96280_b.func_96398_b(), field_96277_d, func_96264_a(2), 16777215);
		drawString(fontRenderer, var4.translateKey("mco.configure.world.description"), field_96277_d, func_96264_a(4), 10526880);
		drawString(fontRenderer, field_96280_b.func_96397_a(), field_96277_d, func_96264_a(5), 16777215);
		drawString(fontRenderer, var4.translateKey("mco.configure.world.status"), field_96277_d, func_96264_a(7), 10526880);
		drawString(fontRenderer, func_104045_j(), field_96277_d, func_96264_a(8), 16777215);
		drawString(fontRenderer, var4.translateKey("mco.configure.world.invited"), field_96287_o, func_96264_a(1), 10526880);
		super.drawScreen(par1, par2, par3);
	}
	
	private String func_104045_j()
	{
		if(field_96280_b.field_98166_h) return "Expired";
		else
		{
			String var1 = field_96280_b.field_96404_d.toLowerCase();
			return Character.toUpperCase(var1.charAt(0)) + var1.substring(1);
		}
	}
	
	private int func_96264_a(int par1)
	{
		return 40 + par1 * 13;
	}
	
	private void func_96267_d(int par1)
	{
		field_96280_b.field_96402_f.remove(par1);
	}
	
	private void func_96268_g()
	{
		McoClient var1 = new McoClient(mc.session);
		try
		{
			Boolean var2 = var1.func_96383_b(field_96280_b.field_96408_a);
			if(var2.booleanValue())
			{
				field_102020_y = true;
				field_96280_b.field_96404_d = "OPEN";
				initGui();
			}
		} catch(ExceptionMcoService var3)
		{
			;
		} catch(IOException var4)
		{
			;
		}
	}
	
	private void func_96272_i()
	{
		if(field_96284_p >= 0 && field_96284_p < field_96280_b.field_96402_f.size())
		{
			field_96283_q = (String) field_96280_b.field_96402_f.get(field_96284_p);
			StringTranslate var1 = StringTranslate.getInstance();
			GuiYesNo var2 = new GuiYesNo(this, "Warning!", var1.translateKey("mco.configure.world.uninvite.question") + " \'" + field_96283_q + "\'", 3);
			mc.displayGuiScreen(var2);
		}
	}
	
	private void func_96275_h()
	{
		McoClient var1 = new McoClient(mc.session);
		try
		{
			boolean var2 = var1.func_96378_c(field_96280_b.field_96408_a).booleanValue();
			if(var2)
			{
				field_102020_y = true;
				field_96280_b.field_96404_d = "CLOSED";
				initGui();
			}
		} catch(ExceptionMcoService var3)
		{
			;
		} catch(IOException var4)
		{
			;
		}
	}
	
	@Override public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		field_96277_d = width / 2 - 200;
		field_96286_n = 180;
		field_96287_o = width / 2;
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		if(field_96280_b.field_96404_d.equals("CLOSED"))
		{
			buttonList.add(field_96281_r = new GuiButton(0, field_96277_d, func_96264_a(12), field_96286_n / 2 - 2, 20, var1.translateKey("mco.configure.world.buttons.open")));
			field_96281_r.enabled = !field_96280_b.field_98166_h;
		} else
		{
			buttonList.add(field_96279_s = new GuiButton(1, field_96277_d, func_96264_a(12), field_96286_n / 2 - 2, 20, var1.translateKey("mco.configure.world.buttons.close")));
			field_96279_s.enabled = !field_96280_b.field_98166_h;
		}
		buttonList.add(field_98129_x = new GuiButton(7, field_96277_d + field_96286_n / 2 + 2, func_96264_a(12), field_96286_n / 2 - 2, 20, var1.translateKey("mco.configure.world.buttons.subscription")));
		buttonList.add(field_96278_t = new GuiButton(5, field_96277_d, func_96264_a(10), field_96286_n / 2 - 2, 20, var1.translateKey("mco.configure.world.buttons.edit")));
		buttonList.add(field_96276_u = new GuiButton(6, field_96277_d + field_96286_n / 2 + 2, func_96264_a(10), field_96286_n / 2 - 2, 20, var1.translateKey("mco.configure.world.buttons.reset")));
		buttonList.add(field_98128_v = new GuiButton(4, field_96287_o, func_96264_a(10), field_96286_n / 2 - 2, 20, var1.translateKey("mco.configure.world.buttons.invite")));
		buttonList.add(field_98127_w = new GuiButton(3, field_96287_o + field_96286_n / 2 + 2, func_96264_a(10), field_96286_n / 2 - 2, 20, var1.translateKey("mco.configure.world.buttons.uninvite")));
		buttonList.add(new GuiButton(10, field_96287_o, func_96264_a(12), field_96286_n, 20, var1.translateKey("gui.back")));
		field_96282_c = new SelectionListInvited(this);
		field_96278_t.enabled = !field_96280_b.field_98166_h;
		field_96276_u.enabled = !field_96280_b.field_98166_h;
		field_98128_v.enabled = !field_96280_b.field_98166_h;
		field_98127_w.enabled = !field_96280_b.field_98166_h;
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override public void updateScreen()
	{
	}
	
	static int func_96263_e(GuiScreenConfigureWorld par0GuiScreenConfigureWorld)
	{
		return par0GuiScreenConfigureWorld.field_96284_p;
	}
	
	static Minecraft func_96265_a(GuiScreenConfigureWorld par0GuiScreenConfigureWorld)
	{
		return par0GuiScreenConfigureWorld.mc;
	}
	
	static McoServer func_96266_d(GuiScreenConfigureWorld par0GuiScreenConfigureWorld)
	{
		return par0GuiScreenConfigureWorld.field_96280_b;
	}
	
	static int func_96269_c(GuiScreenConfigureWorld par0GuiScreenConfigureWorld)
	{
		return par0GuiScreenConfigureWorld.field_96286_n;
	}
	
	static int func_96270_b(GuiScreenConfigureWorld par0GuiScreenConfigureWorld, int par1)
	{
		return par0GuiScreenConfigureWorld.field_96284_p = par1;
	}
	
	static int func_96271_b(GuiScreenConfigureWorld par0GuiScreenConfigureWorld)
	{
		return par0GuiScreenConfigureWorld.field_96287_o;
	}
	
	static FontRenderer func_96273_f(GuiScreenConfigureWorld par0GuiScreenConfigureWorld)
	{
		return par0GuiScreenConfigureWorld.fontRenderer;
	}
	
	static int func_96274_a(GuiScreenConfigureWorld par0GuiScreenConfigureWorld, int par1)
	{
		return par0GuiScreenConfigureWorld.func_96264_a(par1);
	}
}
