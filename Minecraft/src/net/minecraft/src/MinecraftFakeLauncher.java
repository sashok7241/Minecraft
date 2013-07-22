package net.minecraft.src;

import java.applet.Applet;
import java.applet.AppletStub;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class MinecraftFakeLauncher extends Applet implements AppletStub
{
	private static final long serialVersionUID = 1L;
	final Map arguments;
	
	public MinecraftFakeLauncher(Map p_i3001_1_)
	{
		arguments = p_i3001_1_;
	}
	
	@Override public void appletResize(int p_appletResize_1_, int p_appletResize_2_)
	{
	}
	
	@Override public URL getDocumentBase()
	{
		try
		{
			return new URL("http://www.minecraft.net/game/");
		} catch(MalformedURLException var2)
		{
			var2.printStackTrace();
			return null;
		}
	}
	
	@Override public String getParameter(String p_getParameter_1_)
	{
		if(arguments.containsKey(p_getParameter_1_)) return (String) arguments.get(p_getParameter_1_);
		else
		{
			System.err.println("Client asked for parameter: " + p_getParameter_1_);
			return null;
		}
	}
	
	@Override public boolean isActive()
	{
		return true;
	}
}
