package net.minecraft.src;

import java.io.File;

class TexturePackDownloadSuccess implements IDownloadSuccess
{
	final TexturePackList texturePacks;
	
	TexturePackDownloadSuccess(TexturePackList par1TexturePackList)
	{
		texturePacks = par1TexturePackList;
	}
	
	@Override public void onSuccess(File par1File)
	{
		if(TexturePackList.isDownloading(texturePacks))
		{
			TexturePackList.setSelectedTexturePack(texturePacks, new TexturePackCustom(TexturePackList.generateTexturePackID(texturePacks, par1File), par1File, TexturePackList.func_98143_h()));
			TexturePackList.getMinecraft(texturePacks).scheduleTexturePackRefresh();
		}
	}
}
