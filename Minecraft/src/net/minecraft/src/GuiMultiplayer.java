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
	private static int threadsPending = 0;
	private static Object lock = new Object();
	private GuiScreen parentScreen;
	private GuiSlotServer serverSlotContainer;
	private ServerList internetServerList;
	private int selectedServer = -1;
	private GuiButton field_96289_p;
	private GuiButton buttonSelect;
	private GuiButton buttonDelete;
	private boolean deleteClicked = false;
	private boolean addClicked = false;
	private boolean editClicked = false;
	private boolean directClicked = false;
	private String lagTooltip = null;
	private ServerData theServerData = null;
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
					StringTranslate var3 = StringTranslate.getInstance();
					String var4 = var3.translateKey("selectServer.deleteQuestion");
					String var5 = "\'" + var2 + "\' " + var3.translateKey("selectServer.deleteWarning");
					String var6 = var3.translateKey("selectServer.deleteButton");
					String var7 = var3.translateKey("gui.cancel");
					GuiYesNo var8 = new GuiYesNo(this, var4, var5, var6, var7, selectedServer);
					mc.displayGuiScreen(var8);
				}
			} else if(par1GuiButton.id == 1)
			{
				joinServer(selectedServer);
			} else if(par1GuiButton.id == 4)
			{
				directClicked = true;
				mc.displayGuiScreen(new GuiScreenServerList(this, theServerData = new ServerData(StatCollector.translateToLocal("selectServer.defaultName"), "")));
			} else if(par1GuiButton.id == 3)
			{
				addClicked = true;
				mc.displayGuiScreen(new GuiScreenAddServer(this, theServerData = new ServerData(StatCollector.translateToLocal("selectServer.defaultName"), "")));
			} else if(par1GuiButton.id == 7)
			{
				editClicked = true;
				ServerData var9 = internetServerList.getServerData(selectedServer);
				theServerData = new ServerData(var9.serverName, var9.serverIP);
				theServerData.setHideAddress(var9.isHidingAddress());
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
		StringTranslate var4 = StringTranslate.getInstance();
		drawDefaultBackground();
		serverSlotContainer.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, var4.translateKey("multiplayer.title"), width / 2, 20, 16777215);
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
		StringTranslate var1 = StringTranslate.getInstance();
		buttonList.add(field_96289_p = new GuiButton(7, width / 2 - 154, height - 28, 70, 20, var1.translateKey("selectServer.edit")));
		buttonList.add(buttonDelete = new GuiButton(2, width / 2 - 74, height - 28, 70, 20, var1.translateKey("selectServer.delete")));
		buttonList.add(buttonSelect = new GuiButton(1, width / 2 - 154, height - 52, 100, 20, var1.translateKey("selectServer.select")));
		buttonList.add(new GuiButton(4, width / 2 - 50, height - 52, 100, 20, var1.translateKey("selectServer.direct")));
		buttonList.add(new GuiButton(3, width / 2 + 4 + 50, height - 52, 100, 20, var1.translateKey("selectServer.add")));
		buttonList.add(new GuiButton(8, width / 2 + 4, height - 28, 70, 20, var1.translateKey("selectServer.refresh")));
		buttonList.add(new GuiButton(0, width / 2 + 4 + 76, height - 28, 75, 20, var1.translateKey("gui.cancel")));
		boolean var2 = selectedServer >= 0 && selectedServer < serverSlotContainer.getSize();
		buttonSelect.enabled = var2;
		field_96289_p.enabled = var2;
		buttonDelete.enabled = var2;
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
				if(var3 < internetServerList.countServers() - 1)
				{
					internetServerList.swapServers(var3, var3 + 1);
					++selectedServer;
					if(var3 > 0)
					{
						serverSlotContainer.func_77208_b(serverSlotContainer.slotHeight);
					}
				}
			} else if(par1 == 13)
			{
				actionPerformed((GuiButton) buttonList.get(2));
			} else
			{
				super.keyTyped(par1, par2);
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
	
	private static void func_74017_b(ServerData par1ServerData) throws IOException
	{
		ServerAddress var1 = ServerAddress.func_78860_a(par1ServerData.serverIP);
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
			var4.write(254);
			var4.write(1);
			if(var3.read() != 255) throw new IOException("Bad message");
			String var5 = Packet.readString(var3, 256);
			char[] var6 = var5.toCharArray();
			for(int var7 = 0; var7 < var6.length; ++var7)
			{
				if(var6[var7] != 167 && var6[var7] != 0 && ChatAllowedCharacters.allowedCharacters.indexOf(var6[var7]) < 0)
				{
					var6[var7] = 63;
				}
			}
			var5 = new String(var6);
			int var8;
			int var9;
			String[] var26;
			if(var5.startsWith("\u00a7") && var5.length() > 1)
			{
				var26 = var5.substring(1).split("\u0000");
				if(MathHelper.parseIntWithDefault(var26[0], 0) == 1)
				{
					par1ServerData.serverMOTD = var26[3];
					par1ServerData.field_82821_f = MathHelper.parseIntWithDefault(var26[1], par1ServerData.field_82821_f);
					par1ServerData.gameVersion = var26[2];
					var8 = MathHelper.parseIntWithDefault(var26[4], 0);
					var9 = MathHelper.parseIntWithDefault(var26[5], 0);
					if(var8 >= 0 && var9 >= 0)
					{
						par1ServerData.populationInfo = EnumChatFormatting.GRAY + "" + var8 + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var9;
					} else
					{
						par1ServerData.populationInfo = "" + EnumChatFormatting.DARK_GRAY + "???";
					}
				} else
				{
					par1ServerData.gameVersion = "???";
					par1ServerData.serverMOTD = "" + EnumChatFormatting.DARK_GRAY + "???";
					par1ServerData.field_82821_f = 62;
					par1ServerData.populationInfo = "" + EnumChatFormatting.DARK_GRAY + "???";
				}
			} else
			{
				var26 = var5.split("\u00a7");
				var5 = var26[0];
				var8 = -1;
				var9 = -1;
				try
				{
					var8 = Integer.parseInt(var26[1]);
					var9 = Integer.parseInt(var26[2]);
				} catch(Exception var24)
				{
					;
				}
				par1ServerData.serverMOTD = EnumChatFormatting.GRAY + var5;
				if(var8 >= 0 && var9 > 0)
				{
					par1ServerData.populationInfo = EnumChatFormatting.GRAY + "" + var8 + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var9;
				} else
				{
					par1ServerData.populationInfo = "" + EnumChatFormatting.DARK_GRAY + "???";
				}
				par1ServerData.gameVersion = "1.3";
				par1ServerData.field_82821_f = 60;
			}
		} finally
		{
			try
			{
				if(var3 != null)
				{
					var3.close();
				}
			} catch(Throwable var23)
			{
				;
			}
			try
			{
				if(var4 != null)
				{
					var4.close();
				}
			} catch(Throwable var22)
			{
				;
			}
			try
			{
				if(var2 != null)
				{
					var2.close();
				}
			} catch(Throwable var21)
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
