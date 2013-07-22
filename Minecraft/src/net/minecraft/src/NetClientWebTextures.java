package net.minecraft.src;

import net.minecraft.client.Minecraft;

class NetClientWebTextures extends GuiScreen
{
	final String texturePackName;
	final NetClientHandler netClientHandlerWebTextures;
	
	NetClientWebTextures(NetClientHandler par1NetClientHandler, String par2Str)
	{
		netClientHandlerWebTextures = par1NetClientHandler;
		texturePackName = par2Str;
	}
	
	@Override public void confirmClicked(boolean par1, int par2)
	{
		mc = Minecraft.getMinecraft();
		if(mc.getServerData() != null)
		{
			mc.getServerData().setAcceptsTextures(par1);
			ServerList.func_78852_b(mc.getServerData());
		}
		if(par1)
		{
			mc.texturePackList.requestDownloadOfTexture(texturePackName);
		}
		mc.displayGuiScreen((GuiScreen) null);
	}
}
