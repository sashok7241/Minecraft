package net.minecraft.src;

public class GuiDownloadTerrain extends GuiScreen
{
	private NetClientHandler netHandler;
	private int updateCounter = 0;
	
	public GuiDownloadTerrain(NetClientHandler par1NetClientHandler)
	{
		netHandler = par1NetClientHandler;
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawBackground(0);
		StringTranslate var4 = StringTranslate.getInstance();
		drawCenteredString(fontRenderer, var4.translateKey("multiplayer.downloadingTerrain"), width / 2, height / 2 - 50, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		buttonList.clear();
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
	}
	
	@Override public void updateScreen()
	{
		++updateCounter;
		if(updateCounter % 20 == 0)
		{
			netHandler.addToSendQueue(new Packet0KeepAlive());
		}
		if(netHandler != null)
		{
			netHandler.processReadPackets();
		}
	}
}
