package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.minecraft.client.Minecraft;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ThreadDownloadResources extends Thread
{
	public File resourcesFolder;
	private Minecraft mc;
	private boolean closing = false;
	
	public ThreadDownloadResources(File par1File, Minecraft par2Minecraft)
	{
		mc = par2Minecraft;
		setName("Resource download thread");
		setDaemon(true);
		resourcesFolder = new File(par1File, "resources/");
		if(!resourcesFolder.exists() && !resourcesFolder.mkdirs()) throw new RuntimeException("The working directory could not be created: " + resourcesFolder);
	}
	
	public void closeMinecraft()
	{
		closing = true;
	}
	
	private void downloadAndInstallResource(URL par1URL, String par2Str, long par3, int par5)
	{
		try
		{
			int var6 = par2Str.indexOf("/");
			String var7 = par2Str.substring(0, var6);
			if(var7.equalsIgnoreCase("sound3"))
			{
				if(par5 != 0) return;
			} else if(par5 != 1) return;
			File var8 = new File(resourcesFolder, par2Str);
			if(!var8.exists() || var8.length() != par3)
			{
				var8.getParentFile().mkdirs();
				String var9 = par2Str.replaceAll(" ", "%20");
				downloadResource(new URL(par1URL, var9), var8, par3);
				if(closing) return;
			}
			mc.installResource(par2Str, var8);
		} catch(Exception var10)
		{
			var10.printStackTrace();
		}
	}
	
	private void downloadResource(URL par1URL, File par2File, long par3) throws IOException
	{
		byte[] var5 = new byte[4096];
		DataInputStream var6 = new DataInputStream(par1URL.openStream());
		DataOutputStream var7 = new DataOutputStream(new FileOutputStream(par2File));
		boolean var8 = false;
		do
		{
			int var9;
			if((var9 = var6.read(var5)) < 0)
			{
				var6.close();
				var7.close();
				return;
			}
			var7.write(var5, 0, var9);
		} while(!closing);
	}
	
	private void loadResource(File par1File, String par2Str)
	{
		File[] var3 = par1File.listFiles();
		for(File element : var3)
		{
			if(element.isDirectory())
			{
				loadResource(element, par2Str + element.getName() + "/");
			} else
			{
				try
				{
					mc.installResource(par2Str + element.getName(), element);
				} catch(Exception var6)
				{
					mc.getLogAgent().logWarning("Failed to add " + par2Str + element.getName() + " in resources");
				}
			}
		}
	}
	
	public void reloadResources()
	{
		loadResource(resourcesFolder, "");
	}
	
	@Override public void run()
	{
		try
		{
			URL var1 = new URL("http://s3.amazonaws.com/MinecraftResources/");
			DocumentBuilderFactory var2 = DocumentBuilderFactory.newInstance();
			DocumentBuilder var3 = var2.newDocumentBuilder();
			Document var4 = var3.parse(var1.openStream());
			NodeList var5 = var4.getElementsByTagName("Contents");
			for(int var6 = 0; var6 < 2; ++var6)
			{
				for(int var7 = 0; var7 < var5.getLength(); ++var7)
				{
					Node var8 = var5.item(var7);
					if(var8.getNodeType() == 1)
					{
						Element var9 = (Element) var8;
						String var10 = var9.getElementsByTagName("Key").item(0).getChildNodes().item(0).getNodeValue();
						long var11 = Long.parseLong(var9.getElementsByTagName("Size").item(0).getChildNodes().item(0).getNodeValue());
						if(var11 > 0L)
						{
							downloadAndInstallResource(var1, var10, var11, var6);
							if(closing) return;
						}
					}
				}
			}
		} catch(Exception var13)
		{
			loadResource(resourcesFolder, "");
			var13.printStackTrace();
		}
	}
}
