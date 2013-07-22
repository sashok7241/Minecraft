package net.minecraft.src;


class GuiSlotOnlineServerList extends GuiScreenSelectLocation
{
	final GuiScreenOnlineServers field_96294_a;
	
	public GuiSlotOnlineServerList(GuiScreenOnlineServers p_i10004_1_)
	{
		super(GuiScreenOnlineServers.func_98075_b(p_i10004_1_), p_i10004_1_.width, p_i10004_1_.height, 32, p_i10004_1_.height - 64, 36);
		field_96294_a = p_i10004_1_;
	}
	
	@Override protected void drawBackground()
	{
		field_96294_a.drawDefaultBackground();
	}
	
	@Override protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		if(par1 < GuiScreenOnlineServers.func_98094_c(field_96294_a).size())
		{
			func_96292_b(par1, par2, par3, par4, par5Tessellator);
		}
	}
	
	@Override protected void elementClicked(int par1, boolean par2)
	{
		if(par1 < GuiScreenOnlineServers.func_98094_c(field_96294_a).size())
		{
			GuiScreenOnlineServers.func_98089_b(field_96294_a, par1);
			McoServer var3 = (McoServer) GuiScreenOnlineServers.func_98094_c(field_96294_a).get(GuiScreenOnlineServers.func_98072_d(field_96294_a));
			GuiScreenOnlineServers.func_96161_f(field_96294_a).enabled = GuiScreenOnlineServers.func_98076_f(field_96294_a).session.username.equals(var3.field_96405_e);
			GuiScreenOnlineServers.func_98092_g(field_96294_a).enabled = var3.field_96404_d.equals("OPEN") && !var3.field_98166_h;
			if(par2 && GuiScreenOnlineServers.func_98092_g(field_96294_a).enabled)
			{
				GuiScreenOnlineServers.func_98078_c(field_96294_a, GuiScreenOnlineServers.func_98072_d(field_96294_a));
			}
		}
	}
	
	@Override protected boolean func_104086_b(int par1)
	{
		return par1 < GuiScreenOnlineServers.func_98094_c(field_96294_a).size() && ((McoServer) GuiScreenOnlineServers.func_98094_c(field_96294_a).get(par1)).field_96405_e.toLowerCase().equals(GuiScreenOnlineServers.func_98091_h(field_96294_a).session.username);
	}
	
	private void func_96292_b(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		McoServer var6 = (McoServer) GuiScreenOnlineServers.func_98094_c(field_96294_a).get(par1);
		field_96294_a.drawString(GuiScreenOnlineServers.func_104038_i(field_96294_a), var6.func_96398_b(), par2 + 2, par3 + 1, 16777215);
		short var7 = 207;
		byte var8 = 1;
		if(var6.field_98166_h)
		{
			GuiScreenOnlineServers.func_101012_b(field_96294_a, par2 + var7, par3 + var8, field_104094_d, field_104095_e);
		} else if(var6.field_96404_d.equals("CLOSED"))
		{
			GuiScreenOnlineServers.func_101009_c(field_96294_a, par2 + var7, par3 + var8, field_104094_d, field_104095_e);
		} else if(var6.field_96405_e.equals(GuiScreenOnlineServers.func_104032_j(field_96294_a).session.username) && var6.field_104063_i < 7)
		{
			func_96293_a(par1, par2 - 14, par3, var6);
			GuiScreenOnlineServers.func_104030_a(field_96294_a, par2 + var7, par3 + var8, field_104094_d, field_104095_e, var6.field_104063_i);
		} else if(var6.field_96404_d.equals("OPEN"))
		{
			GuiScreenOnlineServers.func_104031_c(field_96294_a, par2 + var7, par3 + var8, field_104094_d, field_104095_e);
			func_96293_a(par1, par2 - 14, par3, var6);
		}
		field_96294_a.drawString(GuiScreenOnlineServers.func_98084_i(field_96294_a), var6.func_96397_a(), par2 + 2, par3 + 12, 7105644);
		field_96294_a.drawString(GuiScreenOnlineServers.func_101005_j(field_96294_a), var6.field_96405_e, par2 + 2, par3 + 12 + 11, 5000268);
	}
	
	private void func_96293_a(int par1, int par2, int par3, McoServer par4McoServer)
	{
		if(par4McoServer.field_96403_g != null)
		{
			synchronized(GuiScreenOnlineServers.func_101007_h())
			{
				if(GuiScreenOnlineServers.func_101010_i() < 5 && (!par4McoServer.field_96411_l || par4McoServer.field_102022_m))
				{
					new ThreadConnectToOnlineServer(this, par4McoServer).start();
				}
			}
			boolean var5 = par4McoServer.field_96415_h > 61;
			boolean var6 = par4McoServer.field_96415_h < 61;
			boolean var7 = var5 || var6;
			if(par4McoServer.field_96414_k != null)
			{
				field_96294_a.drawString(GuiScreenOnlineServers.func_98079_k(field_96294_a), par4McoServer.field_96414_k, par2 + 215 - GuiScreenOnlineServers.func_98087_l(field_96294_a).getStringWidth(par4McoServer.field_96414_k), par3 + 1, 8421504);
			}
			if(var7)
			{
				String var8 = EnumChatFormatting.DARK_RED + par4McoServer.field_96413_j;
				field_96294_a.drawString(GuiScreenOnlineServers.func_98074_m(field_96294_a), var8, par2 + 200 - GuiScreenOnlineServers.func_101000_n(field_96294_a).getStringWidth(var8), par3 + 1, 8421504);
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GuiScreenOnlineServers.func_101004_o(field_96294_a).renderEngine.bindTexture("/gui/icons.png");
			byte var13 = 0;
			boolean var9 = false;
			String var10 = null;
			if(var7)
			{
				var10 = var5 ? "Client out of date!" : "Server out of date!";
				byte var14 = 5;
				field_96294_a.drawTexturedModalRect(par2 + 205, par3, var13 * 10, 176 + var14 * 8, 10, 8);
			}
			byte var11 = 4;
			if(field_104094_d >= par2 + 205 - var11 && field_104095_e >= par3 - var11 && field_104094_d <= par2 + 205 + 10 + var11 && field_104095_e <= par3 + 8 + var11)
			{
				GuiScreenOnlineServers.func_101011_a(field_96294_a, var10);
			}
		}
	}
	
	@Override protected int getContentHeight()
	{
		return getSize() * 36;
	}
	
	@Override protected int getSize()
	{
		return GuiScreenOnlineServers.func_98094_c(field_96294_a).size() + 1;
	}
	
	@Override protected boolean isSelected(int par1)
	{
		return par1 == GuiScreenOnlineServers.func_98072_d(field_96294_a);
	}
}
