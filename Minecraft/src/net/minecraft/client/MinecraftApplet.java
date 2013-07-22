package net.minecraft.client;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;

import net.minecraft.src.CanvasMinecraftApplet;
import net.minecraft.src.MinecraftAppletImpl;
import net.minecraft.src.Session;

public class MinecraftApplet extends Applet
{
	private static final long serialVersionUID = 1L;
	private Canvas mcCanvas;
	private Minecraft mc;
	private Thread mcThread = null;
	
	@Override public void destroy()
	{
		shutdown();
	}
	
	@Override public void init()
	{
		mcCanvas = new CanvasMinecraftApplet(this);
		boolean var1 = "true".equalsIgnoreCase(getParameter("fullscreen"));
		mc = new MinecraftAppletImpl(this, mcCanvas, this, getWidth(), getHeight(), var1);
		mc.minecraftUri = getDocumentBase().getHost();
		if(getDocumentBase().getPort() > 0)
		{
			mc.minecraftUri = mc.minecraftUri + ":" + getDocumentBase().getPort();
		}
		if(getParameter("username") != null && getParameter("sessionid") != null)
		{
			mc.session = new Session(getParameter("username"), getParameter("sessionid"));
			mc.getLogAgent().logInfo("Setting user: " + mc.session.username);
			System.out.println("(Session ID is " + mc.session.sessionId + ")");
		} else
		{
			mc.session = new Session("Player", "");
		}
		mc.setDemo("true".equals(getParameter("demo")));
		if(getParameter("server") != null && getParameter("port") != null)
		{
			mc.setServer(getParameter("server"), Integer.parseInt(getParameter("port")));
		}
		mc.hideQuitButton = !"true".equals(getParameter("stand-alone"));
		setLayout(new BorderLayout());
		this.add(mcCanvas, "Center");
		mcCanvas.setFocusable(true);
		mcCanvas.setFocusTraversalKeysEnabled(false);
		validate();
	}
	
	public void shutdown()
	{
		if(mcThread != null)
		{
			mc.shutdown();
			try
			{
				mcThread.join(10000L);
			} catch(InterruptedException var4)
			{
				try
				{
					mc.shutdownMinecraftApplet();
				} catch(Exception var3)
				{
					var3.printStackTrace();
				}
			}
			mcThread = null;
		}
	}
	
	@Override public void start()
	{
		if(mc != null)
		{
			mc.isGamePaused = false;
		}
	}
	
	public void startMainThread()
	{
		if(mcThread == null)
		{
			mcThread = new Thread(mc, "Minecraft main thread");
			mcThread.start();
		}
	}
	
	@Override public void stop()
	{
		if(mc != null)
		{
			mc.isGamePaused = true;
		}
	}
}
