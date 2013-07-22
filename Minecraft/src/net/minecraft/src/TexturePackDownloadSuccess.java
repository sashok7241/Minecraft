package net.minecraft.src;

import java.io.File;

class TexturePackDownloadSuccess implements IDownloadSuccess
{
	final TexturePackList texturePacks;
	
	TexturePackDownloadSuccess(TexturePackList p_i3024_1_)
	{
		texturePacks = p_i3024_1_;
	}
	
	@Override public void onSuccess(File p_76170_1_)
	{
		if(TexturePackList.isDownloading(texturePacks))
		{
			TexturePackList.setSelectedTexturePack(texturePacks, new TexturePackCustom(TexturePackList.generateTexturePackID(texturePacks, p_76170_1_), p_76170_1_, TexturePackList.func_98143_h()));
			TexturePackList.getMinecraft(texturePacks).scheduleTexturePackRefresh();
		}
	}
}
