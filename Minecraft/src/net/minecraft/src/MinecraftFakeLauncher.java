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
	
	public MinecraftFakeLauncher(Map par1Map)
	{
		arguments = par1Map;
	}
	
	@Override public void appletResize(int par1, int par2)
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
	
	@Override public String getParameter(String par1Str)
	{
		if(arguments.containsKey(par1Str)) return (String) arguments.get(par1Str);
		else
		{
			System.err.println("Client asked for parameter: " + par1Str);
			return null;
		}
	}
	
	@Override public boolean isActive()
	{
		return true;
	}
}
