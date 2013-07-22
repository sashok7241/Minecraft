package net.minecraft.src;

import java.net.ConnectException;
import java.net.UnknownHostException;

class ThreadConnectToServer extends Thread
{
	final String ip;
	final int port;
	final GuiConnecting connectingGui;
	
	ThreadConnectToServer(GuiConnecting p_i3110_1_, String p_i3110_2_, int p_i3110_3_)
	{
		connectingGui = p_i3110_1_;
		ip = p_i3110_2_;
		port = p_i3110_3_;
	}
	
	@Override public void run()
	{
		try
		{
			GuiConnecting.setNetClientHandler(connectingGui, new NetClientHandler(GuiConnecting.func_74256_a(connectingGui), ip, port));
			if(GuiConnecting.isCancelled(connectingGui)) return;
			GuiConnecting.getNetClientHandler(connectingGui).addToSendQueue(new Packet2ClientProtocol(61, GuiConnecting.func_74254_c(connectingGui).session.username, ip, port));
		} catch(UnknownHostException var2)
		{
			if(GuiConnecting.isCancelled(connectingGui)) return;
			GuiConnecting.func_74250_f(connectingGui).displayGuiScreen(new GuiDisconnected(GuiConnecting.func_98097_e(connectingGui), "connect.failed", "disconnect.genericReason", new Object[] { "Unknown host \'" + ip + "\'" }));
		} catch(ConnectException var3)
		{
			if(GuiConnecting.isCancelled(connectingGui)) return;
			GuiConnecting.func_74251_g(connectingGui).displayGuiScreen(new GuiDisconnected(GuiConnecting.func_98097_e(connectingGui), "connect.failed", "disconnect.genericReason", new Object[] { var3.getMessage() }));
		} catch(Exception var4)
		{
			if(GuiConnecting.isCancelled(connectingGui)) return;
			var4.printStackTrace();
			GuiConnecting.func_98096_h(connectingGui).displayGuiScreen(new GuiDisconnected(GuiConnecting.func_98097_e(connectingGui), "connect.failed", "disconnect.genericReason", new Object[] { var4.toString() }));
		}
	}
}
