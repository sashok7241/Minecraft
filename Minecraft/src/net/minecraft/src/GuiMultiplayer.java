package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collections;
import java.util.List;

public class GuiMultiplayer extends GuiScreen
{
	private static int threadsPending;
	private static Object lock = new Object();
	private GuiScreen parentScreen;
	private GuiSlotServer serverSlotContainer;
	private ServerList internetServerList;
	private int selectedServer = -1;
	private GuiButton field_96289_p;
	private GuiButton buttonSelect;
	private GuiButton buttonDelete;
	private boolean deleteClicked;
	private boolean addClicked;
	private boolean editClicked;
	private boolean directClicked;
	private String lagTooltip;
	private ServerData theServerData;
	private LanServerList localNetworkServerList;
	private ThreadLanServerFind localServerFindThread;
	private int ticksOpened;
	private boolean field_74024_A;
	private List listofLanServers = Collections.emptyList();
	
	public GuiMultiplayer(GuiScreen par1GuiScreen)
	{
		parentScreen = par1GuiScreen;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 2)
			{
				String var2 = internetServerList.getServerData(selectedServer).serverName;
				if(var2 != null)
				{
					deleteClicked = true;
					String var3 = I18n.func_135053_a("selectServer.deleteQuestion");
					String var4 = "\'" + var2 + "\' " + I18n.func_135053_a("selectServer.deleteWarning");
					String var5 = I18n.func_135053_a("selectServer.deleteButton");
					String var6 = I18n.func_135053_a("gui.cancel");
					GuiYesNo var7 = new GuiYesNo(this, var3, var4, var5, var6, selectedServer);
					mc.displayGuiScreen(var7);
				}
			} else if(par1GuiButton.id == 1)
			{
				joinServer(selectedServer);
			} else if(par1GuiButton.id == 4)
			{
				directClicked = true;
				mc.displayGuiScreen(new GuiScreenServerList(this, theServerData = new ServerData(I18n.func_135053_a("selectServer.defaultName"), "")));
			} else if(par1GuiButton.id == 3)
			{
				addClicked = true;
				mc.displayGuiScreen(new GuiScreenAddServer(this, theServerData = new ServerData(I18n.func_135053_a("selectServer.defaultName"), "")));
			} else if(par1GuiButton.id == 7)
			{
				editClicked = true;
				ServerData var8 = internetServerList.getServerData(selectedServer);
				theServerData = new ServerData(var8.serverName, var8.serverIP);
				theServerData.setHideAddress(var8.isHidingAddress());
				mc.displayGuiScreen(new GuiScreenAddServer(this, theServerData));
			} else if(par1GuiButton.id == 0)
			{
				mc.displayGuiScreen(parentScreen);
			} else if(par1GuiButton.id == 8)
			{
				mc.displayGuiScreen(new GuiMultiplayer(parentScreen));
			} else
			{
				serverSlotContainer.actionPerformed(par1GuiButton);
			}
		}
	}
	
	@Override public void confirmClicked(boolean par1, int par2)
	{
		if(deleteClicked)
		{
			deleteClicked = false;
			if(par1)
			{
				internetServerList.removeServerData(par2);
				internetServerList.saveServerList();
				selectedServer = -1;
			}
			mc.displayGuiScreen(this);
		} else if(directClicked)
		{
			directClicked = false;
			if(par1)
			{
				connectToServer(theServerData);
			} else
			{
				mc.displayGuiScreen(this);
			}
		} else if(addClicked)
		{
			addClicked = false;
			if(par1)
			{
				internetServerList.addServerData(theServerData);
				internetServerList.saveServerList();
				selectedServer = -1;
			}
			mc.displayGuiScreen(this);
		} else if(editClicked)
		{
			editClicked = false;
			if(par1)
			{
				ServerData var3 = internetServerList.getServerData(selectedServer);
				var3.serverName = theServerData.serverName;
				var3.serverIP = theServerData.serverIP;
				var3.setHideAddress(theServerData.isHidingAddress());
				internetServerList.saveServerList();
			}
			mc.displayGuiScreen(this);
		}
	}
	
	private void connectToServer(ServerData par1ServerData)
	{
		mc.displayGuiScreen(new GuiConnecting(this, mc, par1ServerData));
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		lagTooltip = null;
		drawDefaultBackground();
		serverSlotContainer.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, I18n.func_135053_a("multiplayer.title"), width / 2, 20, 16777215);
		super.drawScreen(par1, par2, par3);
		if(lagTooltip != null)
		{
			func_74007_a(lagTooltip, par1, par2);
		}
	}
	
	protected void func_74007_a(String par1Str, int par2, int par3)
	{
		if(par1Str != null)
		{
			int var4 = par2 + 12;
			int var5 = par3 - 12;
			int var6 = fontRenderer.getStringWidth(par1Str);
			drawGradientRect(var4 - 3, var5 - 3, var4 + var6 + 3, var5 + 8 + 3, -1073741824, -1073741824);
			fontRenderer.drawStringWithShadow(par1Str, var4, var5, -1);
		}
	}
	
	@Override public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		if(!field_74024_A)
		{
			field_74024_A = true;
			internetServerList = new ServerList(mc);
			internetServerList.loadServerList();
			localNetworkServerList = new LanServerList();
			try
			{
				localServerFindThread = new ThreadLanServerFind(localNetworkServerList);
				localServerFindThread.start();
			} catch(Exception var2)
			{
				mc.getLogAgent().logWarning("Unable to start LAN server detection: " + var2.getMessage());
			}
			serverSlotContainer = new GuiSlotServer(this);
		} else
		{
			serverSlotContainer.func_77207_a(width, height, 32, height - 64);
		}
		initGuiControls();
	}
	
	public void initGuiControls()
	{
		buttonList.add(field_96289_p = new GuiButton(7, width / 2 - 154, height - 28, 70, 20, I18n.func_135053_a("selectServer.edit")));
		buttonList.add(buttonDelete = new GuiButton(2, width / 2 - 74, height - 28, 70, 20, I18n.func_135053_a("selectServer.delete")));
		buttonList.add(buttonSelect = new GuiButton(1, width / 2 - 154, height - 52, 100, 20, I18n.func_135053_a("selectServer.select")));
		buttonList.add(new GuiButton(4, width / 2 - 50, height - 52, 100, 20, I18n.func_135053_a("selectServer.direct")));
		buttonList.add(new GuiButton(3, width / 2 + 4 + 50, height - 52, 100, 20, I18n.func_135053_a("selectServer.add")));
		buttonList.add(new GuiButton(8, width / 2 + 4, height - 28, 70, 20, I18n.func_135053_a("selectServer.refresh")));
		buttonList.add(new GuiButton(0, width / 2 + 4 + 76, height - 28, 75, 20, I18n.func_135053_a("gui.cancel")));
		boolean var1 = selectedServer >= 0 && selectedServer < serverSlotContainer.getSize();
		buttonSelect.enabled = var1;
		field_96289_p.enabled = var1;
		buttonDelete.enabled = var1;
	}
	
	private void joinServer(int par1)
	{
		if(par1 < internetServerList.countServers())
		{
			connectToServer(internetServerList.getServerData(par1));
		} else
		{
			par1 -= internetServerList.countServers();
			if(par1 < listofLanServers.size())
			{
				LanServer var2 = (LanServer) listofLanServers.get(par1);
				connectToServer(new ServerData(var2.getServerMotd(), var2.getServerIpPort()));
			}
		}
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		int var3 = selectedServer;
		if(par2 == 59)
		{
			mc.gameSettings.hideServerAddress = !mc.gameSettings.hideServerAddress;
			mc.gameSettings.saveOptions();
		} else
		{
			if(isShiftKeyDown() && par2 == 200)
			{
				if(var3 > 0 && var3 < internetServerList.countServers())
				{
					internetServerList.swapServers(var3, var3 - 1);
					--selectedServer;
					if(var3 < internetServerList.countServers() - 1)
					{
						serverSlotContainer.func_77208_b(-serverSlotContainer.slotHeight);
					}
				}
			} else if(isShiftKeyDown() && par2 == 208)
			{
				if(var3 >= 0 & var3 < internetServerList.countServers() - 1)
				{
					internetServerList.swapServers(var3, var3 + 1);
					++selectedServer;
					if(var3 > 0)
					{
						serverSlotContainer.func_77208_b(serverSlotContainer.slotHeight);
					}
				}
			} else if(par2 != 28 && par2 != 156)
			{
				super.keyTyped(par1, par2);
			} else
			{
				actionPerformed((GuiButton) buttonList.get(2));
			}
		}
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
		if(localServerFindThread != null)
		{
			localServerFindThread.interrupt();
			localServerFindThread = null;
		}
	}
	
	@Override public void updateScreen()
	{
		super.updateScreen();
		++ticksOpened;
		if(localNetworkServerList.getWasUpdated())
		{
			listofLanServers = localNetworkServerList.getLanServers();
			localNetworkServerList.setWasNotUpdated();
		}
	}
	
	static int decreaseThreadsPending()
	{
		return threadsPending--;
	}
	
	static void func_74008_b(GuiMultiplayer par0GuiMultiplayer, int par1)
	{
		par0GuiMultiplayer.joinServer(par1);
	}
	
	private static void func_74017_b(ServerData par0ServerData) throws IOException
	{
		ServerAddress var1 = ServerAddress.func_78860_a(par0ServerData.serverIP);
		Socket var2 = null;
		DataInputStream var3 = null;
		DataOutputStream var4 = null;
		try
		{
			var2 = new Socket();
			var2.setSoTimeout(3000);
			var2.setTcpNoDelay(true);
			var2.setTrafficClass(18);
			var2.connect(new InetSocketAddress(var1.getIP(), var1.getPort()), 3000);
			var3 = new DataInputStream(var2.getInputStream());
			var4 = new DataOutputStream(var2.getOutputStream());
			Packet254ServerPing var5 = new Packet254ServerPing(74, var1.getIP(), var1.getPort());
			var4.writeByte(var5.getPacketId());
			var5.writePacketData(var4);
			if(var3.read() != 255) throw new IOException("Bad message");
			String var6 = Packet.readString(var3, 256);
			char[] var7 = var6.toCharArray();
			for(int var8 = 0; var8 < var7.length; ++var8)
			{
				if(var7[var8] != 167 && var7[var8] != 0 && ChatAllowedCharacters.allowedCharacters.indexOf(var7[var8]) < 0)
				{
					var7[var8] = 63;
				}
			}
			var6 = new String(var7);
			int var9;
			int var10;
			String[] var27;
			if(var6.startsWith("\u00a7") && var6.length() > 1)
			{
				var27 = var6.substring(1).split("\u0000");
				if(MathHelper.parseIntWithDefault(var27[0], 0) == 1)
				{
					par0ServerData.serverMOTD = var27[3];
					par0ServerData.field_82821_f = MathHelper.parseIntWithDefault(var27[1], par0ServerData.field_82821_f);
					par0ServerData.gameVersion = var27[2];
					var9 = MathHelper.parseIntWithDefault(var27[4], 0);
					var10 = MathHelper.parseIntWithDefault(var27[5], 0);
					if(var9 >= 0 && var10 >= 0)
					{
						par0ServerData.populationInfo = EnumChatFormatting.GRAY + "" + var9 + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var10;
					} else
					{
						par0ServerData.populationInfo = "" + EnumChatFormatting.DARK_GRAY + "???";
					}
				} else
				{
					par0ServerData.gameVersion = "???";
					par0ServerData.serverMOTD = "" + EnumChatFormatting.DARK_GRAY + "???";
					par0ServerData.field_82821_f = 75;
					par0ServerData.populationInfo = "" + EnumChatFormatting.DARK_GRAY + "???";
				}
			} else
			{
				var27 = var6.split("\u00a7");
				var6 = var27[0];
				var9 = -1;
				var10 = -1;
				try
				{
					var9 = Integer.parseInt(var27[1]);
					var10 = Integer.parseInt(var27[2]);
				} catch(Exception var25)
				{
					;
				}
				par0ServerData.serverMOTD = EnumChatFormatting.GRAY + var6;
				if(var9 >= 0 && var10 > 0)
				{
					par0ServerData.populationInfo = EnumChatFormatting.GRAY + "" + var9 + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var10;
				} else
				{
					par0ServerData.populationInfo = "" + EnumChatFormatting.DARK_GRAY + "???";
				}
				par0ServerData.gameVersion = "1.3";
				par0ServerData.field_82821_f = 73;
			}
		} finally
		{
			try
			{
				if(var3 != null)
				{
					var3.close();
				}
			} catch(Throwable var24)
			{
				;
			}
			try
			{
				if(var4 != null)
				{
					var4.close();
				}
			} catch(Throwable var23)
			{
				;
			}
			try
			{
				if(var2 != null)
				{
					var2.close();
				}
			} catch(Throwable var22)
			{
				;
			}
		}
	}
	
	static void func_82291_a(ServerData par0ServerData) throws IOException
	{
		func_74017_b(par0ServerData);
	}
	
	static String getAndSetLagTooltip(GuiMultiplayer par0GuiMultiplayer, String par1Str)
	{
		return par0GuiMultiplayer.lagTooltip = par1Str;
	}
	
	static int getAndSetSelectedServer(GuiMultiplayer par0GuiMultiplayer, int par1)
	{
		return par0GuiMultiplayer.selectedServer = par1;
	}
	
	static GuiButton getButtonDelete(GuiMultiplayer par0GuiMultiplayer)
	{
		return par0GuiMultiplayer.buttonDelete;
	}
	
	static GuiButton getButtonEdit(GuiMultiplayer par0GuiMultiplayer)
	{
		return par0GuiMultiplayer.field_96289_p;
	}
	
	static GuiButton getButtonSelect(GuiMultiplayer par0GuiMultiplayer)
	{
		return par0GuiMultiplayer.buttonSelect;
	}
	
	static ServerList getInternetServerList(GuiMultiplayer par0GuiMultiplayer)
	{
		return par0GuiMultiplayer.internetServerList;
	}
	
	static List getListOfLanServers(GuiMultiplayer par0GuiMultiplayer)
	{
		return par0GuiMultiplayer.listofLanServers;
	}
	
	static Object getLock()
	{
		return lock;
	}
	
	static int getSelectedServer(GuiMultiplayer par0GuiMultiplayer)
	{
		return par0GuiMultiplayer.selectedServer;
	}
	
	static int getThreadsPending()
	{
		return threadsPending;
	}
	
	static int getTicksOpened(GuiMultiplayer par0GuiMultiplayer)
	{
		return par0GuiMultiplayer.ticksOpened;
	}
	
	static int increaseThreadsPending()
	{
		return threadsPending++;
	}
}
