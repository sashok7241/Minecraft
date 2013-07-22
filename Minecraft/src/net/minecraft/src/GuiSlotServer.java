package net.minecraft.src;

import net.minecraft.client.Minecraft;

class GuiSlotServer extends GuiSlot
{
	final GuiMultiplayer parentGui;
	
	public GuiSlotServer(GuiMultiplayer par1GuiMultiplayer)
	{
		super(par1GuiMultiplayer.mc, par1GuiMultiplayer.width, par1GuiMultiplayer.height, 32, par1GuiMultiplayer.height - 64, 36);
		parentGui = par1GuiMultiplayer;
	}
	
	@Override protected void drawBackground()
	{
		parentGui.drawDefaultBackground();
	}
	
	@Override protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		if(par1 < GuiMultiplayer.getInternetServerList(parentGui).countServers())
		{
			func_77247_d(par1, par2, par3, par4, par5Tessellator);
		} else if(par1 < GuiMultiplayer.getInternetServerList(parentGui).countServers() + GuiMultiplayer.getListOfLanServers(parentGui).size())
		{
			func_77248_b(par1, par2, par3, par4, par5Tessellator);
		} else
		{
			func_77249_c(par1, par2, par3, par4, par5Tessellator);
		}
	}
	
	@Override protected void elementClicked(int par1, boolean par2)
	{
		if(par1 < GuiMultiplayer.getInternetServerList(parentGui).countServers() + GuiMultiplayer.getListOfLanServers(parentGui).size())
		{
			int var3 = GuiMultiplayer.getSelectedServer(parentGui);
			GuiMultiplayer.getAndSetSelectedServer(parentGui, par1);
			ServerData var4 = GuiMultiplayer.getInternetServerList(parentGui).countServers() > par1 ? GuiMultiplayer.getInternetServerList(parentGui).getServerData(par1) : null;
			boolean var5 = GuiMultiplayer.getSelectedServer(parentGui) >= 0 && GuiMultiplayer.getSelectedServer(parentGui) < getSize() && (var4 == null || var4.field_82821_f == 74);
			boolean var6 = GuiMultiplayer.getSelectedServer(parentGui) < GuiMultiplayer.getInternetServerList(parentGui).countServers();
			GuiMultiplayer.getButtonSelect(parentGui).enabled = var5;
			GuiMultiplayer.getButtonEdit(parentGui).enabled = var6;
			GuiMultiplayer.getButtonDelete(parentGui).enabled = var6;
			if(par2 && var5)
			{
				GuiMultiplayer.func_74008_b(parentGui, par1);
			} else if(var6 && GuiScreen.isShiftKeyDown() && var3 >= 0 && var3 < GuiMultiplayer.getInternetServerList(parentGui).countServers())
			{
				GuiMultiplayer.getInternetServerList(parentGui).swapServers(var3, GuiMultiplayer.getSelectedServer(parentGui));
			}
		}
	}
	
	private void func_77247_d(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		ServerData var6 = GuiMultiplayer.getInternetServerList(parentGui).getServerData(par1);
		synchronized(GuiMultiplayer.getLock())
		{
			if(GuiMultiplayer.getThreadsPending() < 5 && !var6.field_78841_f)
			{
				var6.field_78841_f = true;
				var6.pingToServer = -2L;
				var6.serverMOTD = "";
				var6.populationInfo = "";
				GuiMultiplayer.increaseThreadsPending();
				new ThreadPollServers(this, var6).start();
			}
		}
		boolean var7 = var6.field_82821_f > 74;
		boolean var8 = var6.field_82821_f < 74;
		boolean var9 = var7 || var8;
		parentGui.drawString(parentGui.fontRenderer, var6.serverName, par2 + 2, par3 + 1, 16777215);
		parentGui.drawString(parentGui.fontRenderer, var6.serverMOTD, par2 + 2, par3 + 12, 8421504);
		parentGui.drawString(parentGui.fontRenderer, var6.populationInfo, par2 + 215 - parentGui.fontRenderer.getStringWidth(var6.populationInfo), par3 + 12, 8421504);
		if(var9)
		{
			String var10 = EnumChatFormatting.DARK_RED + var6.gameVersion;
			parentGui.drawString(parentGui.fontRenderer, var10, par2 + 200 - parentGui.fontRenderer.getStringWidth(var10), par3 + 1, 8421504);
		}
		if(!parentGui.mc.gameSettings.hideServerAddress && !var6.isHidingAddress())
		{
			parentGui.drawString(parentGui.fontRenderer, var6.serverIP, par2 + 2, par3 + 12 + 11, 3158064);
		} else
		{
			parentGui.drawString(parentGui.fontRenderer, I18n.func_135053_a("selectServer.hiddenAddress"), par2 + 2, par3 + 12 + 11, 3158064);
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		parentGui.mc.func_110434_K().func_110577_a(Gui.field_110324_m);
		byte var16 = 0;
		boolean var11 = false;
		String var12 = "";
		int var15;
		if(var9)
		{
			var12 = var7 ? "Client out of date!" : "Server out of date!";
			var15 = 5;
		} else if(var6.field_78841_f && var6.pingToServer != -2L)
		{
			if(var6.pingToServer < 0L)
			{
				var15 = 5;
			} else if(var6.pingToServer < 150L)
			{
				var15 = 0;
			} else if(var6.pingToServer < 300L)
			{
				var15 = 1;
			} else if(var6.pingToServer < 600L)
			{
				var15 = 2;
			} else if(var6.pingToServer < 1000L)
			{
				var15 = 3;
			} else
			{
				var15 = 4;
			}
			if(var6.pingToServer < 0L)
			{
				var12 = "(no connection)";
			} else
			{
				var12 = var6.pingToServer + "ms";
			}
		} else
		{
			var16 = 1;
			var15 = (int) (Minecraft.getSystemTime() / 100L + par1 * 2 & 7L);
			if(var15 > 4)
			{
				var15 = 8 - var15;
			}
			var12 = "Polling..";
		}
		parentGui.drawTexturedModalRect(par2 + 205, par3, 0 + var16 * 10, 176 + var15 * 8, 10, 8);
		byte var13 = 4;
		if(mouseX >= par2 + 205 - var13 && mouseY >= par3 - var13 && mouseX <= par2 + 205 + 10 + var13 && mouseY <= par3 + 8 + var13)
		{
			GuiMultiplayer.getAndSetLagTooltip(parentGui, var12);
		}
	}
	
	private void func_77248_b(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		LanServer var6 = (LanServer) GuiMultiplayer.getListOfLanServers(parentGui).get(par1 - GuiMultiplayer.getInternetServerList(parentGui).countServers());
		parentGui.drawString(parentGui.fontRenderer, I18n.func_135053_a("lanServer.title"), par2 + 2, par3 + 1, 16777215);
		parentGui.drawString(parentGui.fontRenderer, var6.getServerMotd(), par2 + 2, par3 + 12, 8421504);
		if(parentGui.mc.gameSettings.hideServerAddress)
		{
			parentGui.drawString(parentGui.fontRenderer, I18n.func_135053_a("selectServer.hiddenAddress"), par2 + 2, par3 + 12 + 11, 3158064);
		} else
		{
			parentGui.drawString(parentGui.fontRenderer, var6.getServerIpPort(), par2 + 2, par3 + 12 + 11, 3158064);
		}
	}
	
	private void func_77249_c(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		parentGui.drawCenteredString(parentGui.fontRenderer, I18n.func_135053_a("lanServer.scanning"), parentGui.width / 2, par3 + 1, 16777215);
		String var6;
		switch(GuiMultiplayer.getTicksOpened(parentGui) / 3 % 4)
		{
			case 0:
			default:
				var6 = "O o o";
				break;
			case 1:
			case 3:
				var6 = "o O o";
				break;
			case 2:
				var6 = "o o O";
		}
		parentGui.drawCenteredString(parentGui.fontRenderer, var6, parentGui.width / 2, par3 + 12, 8421504);
	}
	
	@Override protected int getContentHeight()
	{
		return getSize() * 36;
	}
	
	@Override protected int getSize()
	{
		return GuiMultiplayer.getInternetServerList(parentGui).countServers() + GuiMultiplayer.getListOfLanServers(parentGui).size() + 1;
	}
	
	@Override protected boolean isSelected(int par1)
	{
		return par1 == GuiMultiplayer.getSelectedServer(parentGui);
	}
}
