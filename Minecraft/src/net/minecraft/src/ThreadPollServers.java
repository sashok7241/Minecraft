package net.minecraft.src;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

class ThreadPollServers extends Thread
{
	final ServerData pollServersServerData;
	final GuiSlotServer serverSlotContainer;
	
	ThreadPollServers(GuiSlotServer par1GuiSlotServer, ServerData par2ServerData)
	{
		serverSlotContainer = par1GuiSlotServer;
		pollServersServerData = par2ServerData;
	}
	
	@Override public void run()
	{
		boolean var27 = false;
		label183:
		{
			label184:
			{
				label185:
				{
					label186:
					{
						label187:
						{
							try
							{
								var27 = true;
								pollServersServerData.serverMOTD = EnumChatFormatting.DARK_GRAY + "Polling..";
								long var1 = System.nanoTime();
								GuiMultiplayer.func_82291_a(pollServersServerData);
								long var3 = System.nanoTime();
								pollServersServerData.pingToServer = (var3 - var1) / 1000000L;
								var27 = false;
								break label183;
							} catch(UnknownHostException var35)
							{
								pollServersServerData.pingToServer = -1L;
								pollServersServerData.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t resolve hostname";
								var27 = false;
							} catch(SocketTimeoutException var36)
							{
								pollServersServerData.pingToServer = -1L;
								pollServersServerData.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t reach server";
								var27 = false;
								break label187;
							} catch(ConnectException var37)
							{
								pollServersServerData.pingToServer = -1L;
								pollServersServerData.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t reach server";
								var27 = false;
								break label186;
							} catch(IOException var38)
							{
								pollServersServerData.pingToServer = -1L;
								pollServersServerData.serverMOTD = EnumChatFormatting.DARK_RED + "Communication error";
								var27 = false;
								break label185;
							} catch(Exception var39)
							{
								pollServersServerData.pingToServer = -1L;
								pollServersServerData.serverMOTD = "ERROR: " + var39.getClass();
								var27 = false;
								break label184;
							} finally
							{
								if(var27)
								{
									synchronized(GuiMultiplayer.getLock())
									{
										GuiMultiplayer.decreaseThreadsPending();
									}
								}
							}
							synchronized(GuiMultiplayer.getLock())
							{
								GuiMultiplayer.decreaseThreadsPending();
								return;
							}
						}
						synchronized(GuiMultiplayer.getLock())
						{
							GuiMultiplayer.decreaseThreadsPending();
							return;
						}
					}
					synchronized(GuiMultiplayer.getLock())
					{
						GuiMultiplayer.decreaseThreadsPending();
						return;
					}
				}
				synchronized(GuiMultiplayer.getLock())
				{
					GuiMultiplayer.decreaseThreadsPending();
					return;
				}
			}
			synchronized(GuiMultiplayer.getLock())
			{
				GuiMultiplayer.decreaseThreadsPending();
				return;
			}
		}
		synchronized(GuiMultiplayer.getLock())
		{
			GuiMultiplayer.decreaseThreadsPending();
		}
	}
}
