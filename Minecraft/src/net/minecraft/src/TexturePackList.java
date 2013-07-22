package net.minecraft.src;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;

public class TexturePackList
{
	private static final ITexturePack defaultTexturePack = new TexturePackDefault();
	private final Minecraft mc;
	private final File texturePackDir;
	private final File mpTexturePackFolder;
	private List availableTexturePacks = new ArrayList();
	private Map texturePackCache = new HashMap();
	private ITexturePack selectedTexturePack;
	private boolean isDownloading;
	
	public TexturePackList(File p_i3025_1_, Minecraft p_i3025_2_)
	{
		mc = p_i3025_2_;
		texturePackDir = new File(p_i3025_1_, "texturepacks");
		mpTexturePackFolder = new File(p_i3025_1_, "texturepacks-mp-cache");
		createTexturePackDirs();
		updateAvaliableTexturePacks();
	}
	
	public List availableTexturePacks()
	{
		return Collections.unmodifiableList(availableTexturePacks);
	}
	
	private void createTexturePackDirs()
	{
		if(!texturePackDir.isDirectory())
		{
			texturePackDir.delete();
			texturePackDir.mkdirs();
		}
		if(!mpTexturePackFolder.isDirectory())
		{
			mpTexturePackFolder.delete();
			mpTexturePackFolder.mkdirs();
		}
	}
	
	private void downloadTexture(String p_77297_1_, File p_77297_2_)
	{
		HashMap var3 = new HashMap();
		GuiProgress var4 = new GuiProgress();
		var3.put("X-Minecraft-Username", mc.session.username);
		var3.put("X-Minecraft-Version", "1.5.2");
		var3.put("X-Minecraft-Supported-Resolutions", "16");
		isDownloading = true;
		mc.displayGuiScreen(var4);
		HttpUtil.downloadTexturePack(p_77297_2_, p_77297_1_, new TexturePackDownloadSuccess(this), var3, 10000000, var4);
	}
	
	public boolean func_77300_f()
	{
		if(!mc.gameSettings.serverTextures) return false;
		else
		{
			ServerData var1 = mc.getServerData();
			return var1 == null ? true : var1.func_78840_c();
		}
	}
	
	private String generateTexturePackID(File p_77302_1_)
	{
		return p_77302_1_.isFile() && p_77302_1_.getName().toLowerCase().endsWith(".zip") ? p_77302_1_.getName() + ":" + p_77302_1_.length() + ":" + p_77302_1_.lastModified() : p_77302_1_.isDirectory() && new File(p_77302_1_, "pack.txt").exists() ? p_77302_1_.getName() + ":folder:" + p_77302_1_.lastModified() : null;
	}
	
	public boolean getAcceptsTextures()
	{
		if(!mc.gameSettings.serverTextures) return false;
		else
		{
			ServerData var1 = mc.getServerData();
			return var1 == null ? false : var1.getAcceptsTextures();
		}
	}
	
	public boolean getIsDownloading()
	{
		return isDownloading;
	}
	
	public ITexturePack getSelectedTexturePack()
	{
		return selectedTexturePack;
	}
	
	private List getTexturePackDirContents()
	{
		return texturePackDir.exists() && texturePackDir.isDirectory() ? Arrays.asList(texturePackDir.listFiles()) : Collections.emptyList();
	}
	
	public void onDownloadFinished()
	{
		isDownloading = false;
		updateAvaliableTexturePacks();
		mc.scheduleTexturePackRefresh();
	}
	
	public void requestDownloadOfTexture(String p_77296_1_)
	{
		String var2 = p_77296_1_.substring(p_77296_1_.lastIndexOf("/") + 1);
		if(var2.contains("?"))
		{
			var2 = var2.substring(0, var2.indexOf("?"));
		}
		if(var2.endsWith(".zip"))
		{
			File var3 = new File(mpTexturePackFolder, var2);
			downloadTexture(p_77296_1_, var3);
		}
	}
	
	public boolean setTexturePack(ITexturePack p_77294_1_)
	{
		if(p_77294_1_ == selectedTexturePack) return false;
		else
		{
			isDownloading = false;
			selectedTexturePack = p_77294_1_;
			mc.gameSettings.skin = p_77294_1_.getTexturePackFileName();
			mc.gameSettings.saveOptions();
			return true;
		}
	}
	
	public void updateAvaliableTexturePacks()
	{
		ArrayList var1 = new ArrayList();
		selectedTexturePack = defaultTexturePack;
		var1.add(defaultTexturePack);
		Iterator var2 = getTexturePackDirContents().iterator();
		while(var2.hasNext())
		{
			File var3 = (File) var2.next();
			String var4 = this.generateTexturePackID(var3);
			if(var4 != null)
			{
				Object var5 = texturePackCache.get(var4);
				if(var5 == null)
				{
					var5 = var3.isDirectory() ? new TexturePackFolder(var4, var3, defaultTexturePack) : new TexturePackCustom(var4, var3, defaultTexturePack);
					texturePackCache.put(var4, var5);
				}
				if(((ITexturePack) var5).getTexturePackFileName().equals(mc.gameSettings.skin))
				{
					selectedTexturePack = (ITexturePack) var5;
				}
				var1.add(var5);
			}
		}
		availableTexturePacks.removeAll(var1);
		var2 = availableTexturePacks.iterator();
		while(var2.hasNext())
		{
			ITexturePack var6 = (ITexturePack) var2.next();
			var6.deleteTexturePack(mc.renderEngine);
			texturePackCache.remove(var6.getTexturePackID());
		}
		availableTexturePacks = var1;
	}
	
	static ITexturePack func_98143_h()
	{
		return defaultTexturePack;
	}
	
	static String generateTexturePackID(TexturePackList p_77291_0_, File p_77291_1_)
	{
		return p_77291_0_.generateTexturePackID(p_77291_1_);
	}
	
	static Minecraft getMinecraft(TexturePackList p_77306_0_)
	{
		return p_77306_0_.mc;
	}
	
	static boolean isDownloading(TexturePackList p_77301_0_)
	{
		return p_77301_0_.isDownloading;
	}
	
	static ITexturePack setSelectedTexturePack(TexturePackList p_77303_0_, ITexturePack p_77303_1_)
	{
		return p_77303_0_.selectedTexturePack = p_77303_1_;
	}
}
