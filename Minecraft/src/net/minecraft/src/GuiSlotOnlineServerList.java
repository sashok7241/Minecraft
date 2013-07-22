package net.minecraft.src;


class GuiSlotOnlineServerList extends GuiScreenSelectLocation
{
	final GuiScreenOnlineServers field_96294_a;
	
	public GuiSlotOnlineServerList(GuiScreenOnlineServers par1GuiScreenOnlineServers)
	{
		super(GuiScreenOnlineServers.func_140037_f(par1GuiScreenOnlineServers), par1GuiScreenOnlineServers.width, par1GuiScreenOnlineServers.height, 32, par1GuiScreenOnlineServers.height - 64, 36);
		field_96294_a = par1GuiScreenOnlineServers;
	}
	
	@Override protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		if(par1 < GuiScreenOnlineServers.func_140013_c(field_96294_a).size())
		{
			func_96292_b(par1, par2, par3, par4, par5Tessellator);
		}
	}
	
	@Override protected void elementClicked(int par1, boolean par2)
	{
		if(par1 < GuiScreenOnlineServers.func_140013_c(field_96294_a).size())
		{
			McoServer var3 = (McoServer) GuiScreenOnlineServers.func_140013_c(field_96294_a).get(par1);
			GuiScreenOnlineServers.func_140036_b(field_96294_a, var3.field_96408_a);
			if(!GuiScreenOnlineServers.func_140015_g(field_96294_a).func_110432_I().func_111285_a().equals(var3.field_96405_e))
			{
				GuiScreenOnlineServers.func_140038_h(field_96294_a).displayString = I18n.func_135053_a("mco.selectServer.leave");
			} else
			{
				GuiScreenOnlineServers.func_140038_h(field_96294_a).displayString = I18n.func_135053_a("mco.selectServer.configure");
			}
			GuiScreenOnlineServers.func_140033_i(field_96294_a).enabled = var3.field_96404_d.equals("OPEN") && !var3.field_98166_h;
			if(par2 && GuiScreenOnlineServers.func_140033_i(field_96294_a).enabled)
			{
				GuiScreenOnlineServers.func_140008_c(field_96294_a, GuiScreenOnlineServers.func_140041_a(field_96294_a));
			}
		}
	}
	
	@Override protected boolean func_104086_b(int par1)
	{
		try
		{
			return par1 >= 0 && par1 < GuiScreenOnlineServers.func_140013_c(field_96294_a).size() && ((McoServer) GuiScreenOnlineServers.func_140013_c(field_96294_a).get(par1)).field_96405_e.toLowerCase().equals(GuiScreenOnlineServers.func_104032_j(field_96294_a).func_110432_I().func_111285_a());
		} catch(Exception var3)
		{
			return false;
		}
	}
	
	@Override protected int func_130003_b()
	{
		return getSize() * 36;
	}
	
	@Override protected void func_130004_c()
	{
		field_96294_a.drawDefaultBackground();
	}
	
	private void func_96292_b(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		McoServer var6 = (McoServer) GuiScreenOnlineServers.func_140013_c(field_96294_a).get(par1);
		field_96294_a.drawString(GuiScreenOnlineServers.func_140023_k(field_96294_a), var6.func_96398_b(), par2 + 2, par3 + 1, 16777215);
		short var7 = 207;
		byte var8 = 1;
		if(var6.field_98166_h)
		{
			GuiScreenOnlineServers.func_104031_c(field_96294_a, par2 + var7, par3 + var8, field_104094_d, field_104095_e);
		} else if(var6.field_96404_d.equals("CLOSED"))
		{
			GuiScreenOnlineServers.func_140035_b(field_96294_a, par2 + var7, par3 + var8, field_104094_d, field_104095_e);
		} else if(var6.field_96405_e.equals(GuiScreenOnlineServers.func_140014_l(field_96294_a).func_110432_I().func_111285_a()) && var6.field_104063_i < 7)
		{
			func_96293_a(par1, par2 - 14, par3, var6);
			GuiScreenOnlineServers.func_140031_a(field_96294_a, par2 + var7, par3 + var8, field_104094_d, field_104095_e, var6.field_104063_i);
		} else if(var6.field_96404_d.equals("OPEN"))
		{
			GuiScreenOnlineServers.func_140020_c(field_96294_a, par2 + var7, par3 + var8, field_104094_d, field_104095_e);
			func_96293_a(par1, par2 - 14, par3, var6);
		}
		field_96294_a.drawString(GuiScreenOnlineServers.func_140039_m(field_96294_a), var6.func_96397_a(), par2 + 2, par3 + 12, 7105644);
		field_96294_a.drawString(GuiScreenOnlineServers.func_98079_k(field_96294_a), var6.field_96405_e, par2 + 2, par3 + 12 + 11, 5000268);
	}
	
	private void func_96293_a(int par1, int par2, int par3, McoServer par4McoServer)
	{
		if(par4McoServer.field_96403_g != null)
		{
			synchronized(GuiScreenOnlineServers.func_140029_i())
			{
				if(GuiScreenOnlineServers.func_140018_j() < 5 && (!par4McoServer.field_96411_l || par4McoServer.field_102022_m))
				{
					new ThreadConnectToOnlineServer(this, par4McoServer).start();
				}
			}
			if(par4McoServer.field_96414_k != null)
			{
				field_96294_a.drawString(GuiScreenOnlineServers.func_110402_q(field_96294_a), par4McoServer.field_96414_k, par2 + 215 - GuiScreenOnlineServers.func_140010_p(field_96294_a).getStringWidth(par4McoServer.field_96414_k), par3 + 1, 8421504);
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GuiScreenOnlineServers.func_142023_q(field_96294_a).func_110434_K().func_110577_a(Gui.field_110324_m);
		}
	}
	
	@Override protected int getSize()
	{
		return GuiScreenOnlineServers.func_140013_c(field_96294_a).size() + 1;
	}
	
	@Override protected boolean isSelected(int par1)
	{
		return par1 == GuiScreenOnlineServers.func_140027_d(field_96294_a, GuiScreenOnlineServers.func_140041_a(field_96294_a));
	}
}
