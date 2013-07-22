package net.minecraft.src;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

class ThreadConnectToOnlineServer extends Thread
{
	final McoServer field_96597_a;
	final GuiSlotOnlineServerList field_96596_b;
	
	ThreadConnectToOnlineServer(GuiSlotOnlineServerList par1GuiSlotOnlineServerList, McoServer par2McoServer)
	{
		field_96596_b = par1GuiSlotOnlineServerList;
		field_96597_a = par2McoServer;
	}
	
	@Override public void run()
	{
		boolean var27 = false;
		label194:
		{
			label195:
			{
				label196:
				{
					label197:
					{
						label198:
						{
							try
							{
								var27 = true;
								if(!field_96597_a.field_96411_l)
								{
									field_96597_a.field_96411_l = true;
									field_96597_a.field_96412_m = -2L;
									field_96597_a.field_96414_k = "";
									GuiScreenOnlineServers.func_140016_k();
									long var1 = System.nanoTime();
									GuiScreenOnlineServers.func_140024_a(field_96596_b.field_96294_a, field_96597_a);
									long var3 = System.nanoTime();
									field_96597_a.field_96412_m = (var3 - var1) / 1000000L;
									var27 = false;
								} else if(field_96597_a.field_102022_m)
								{
									field_96597_a.field_102022_m = false;
									GuiScreenOnlineServers.func_140024_a(field_96596_b.field_96294_a, field_96597_a);
									var27 = false;
								} else
								{
									var27 = false;
								}
								break label194;
							} catch(UnknownHostException var35)
							{
								field_96597_a.field_96412_m = -1L;
								var27 = false;
								break label195;
							} catch(SocketTimeoutException var36)
							{
								field_96597_a.field_96412_m = -1L;
								var27 = false;
								break label196;
							} catch(ConnectException var37)
							{
								field_96597_a.field_96412_m = -1L;
								var27 = false;
								break label198;
							} catch(IOException var38)
							{
								field_96597_a.field_96412_m = -1L;
								var27 = false;
							} catch(Exception var39)
							{
								field_96597_a.field_96412_m = -1L;
								var27 = false;
								break label197;
							} finally
							{
								if(var27)
								{
									synchronized(GuiScreenOnlineServers.func_140029_i())
									{
										GuiScreenOnlineServers.func_140021_r();
									}
								}
							}
							synchronized(GuiScreenOnlineServers.func_140029_i())
							{
								GuiScreenOnlineServers.func_140021_r();
								return;
							}
						}
						synchronized(GuiScreenOnlineServers.func_140029_i())
						{
							GuiScreenOnlineServers.func_140021_r();
							return;
						}
					}
					synchronized(GuiScreenOnlineServers.func_140029_i())
					{
						GuiScreenOnlineServers.func_140021_r();
						return;
					}
				}
				synchronized(GuiScreenOnlineServers.func_140029_i())
				{
					GuiScreenOnlineServers.func_140021_r();
					return;
				}
			}
			synchronized(GuiScreenOnlineServers.func_140029_i())
			{
				GuiScreenOnlineServers.func_140021_r();
				return;
			}
		}
		synchronized(GuiScreenOnlineServers.func_140029_i())
		{
			GuiScreenOnlineServers.func_140021_r();
		}
	}
}
