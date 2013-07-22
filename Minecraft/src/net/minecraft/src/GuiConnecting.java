package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class GuiConnecting extends GuiScreen
{
	private NetClientHandler clientHandler;
	private boolean cancelled = false;
	private final GuiScreen field_98098_c;
	
	public GuiConnecting(GuiScreen p_i11013_1_, Minecraft p_i11013_2_, ServerData p_i11013_3_)
	{
		mc = p_i11013_2_;
		field_98098_c = p_i11013_1_;
		ServerAddress var4 = ServerAddress.func_78860_a(p_i11013_3_.serverIP);
		p_i11013_2_.loadWorld((WorldClient) null);
		p_i11013_2_.setServerData(p_i11013_3_);
		spawnNewServerThread(var4.getIP(), var4.getPort());
	}
	
	public GuiConnecting(GuiScreen p_i11014_1_, Minecraft p_i11014_2_, String p_i11014_3_, int p_i11014_4_)
	{
		mc = p_i11014_2_;
		field_98098_c = p_i11014_1_;
		p_i11014_2_.loadWorld((WorldClient) null);
		spawnNewServerThread(p_i11014_3_, p_i11014_4_);
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.id == 0)
		{
			cancelled = true;
			if(clientHandler != null)
			{
				clientHandler.disconnect();
			}
			mc.displayGuiScreen(field_98098_c);
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		StringTranslate var4 = StringTranslate.getInstance();
		if(clientHandler == null)
		{
			drawCenteredString(fontRenderer, var4.translateKey("connect.connecting"), width / 2, height / 2 - 50, 16777215);
			drawCenteredString(fontRenderer, "", width / 2, height / 2 - 10, 16777215);
		} else
		{
			drawCenteredString(fontRenderer, var4.translateKey("connect.authorizing"), width / 2, height / 2 - 50, 16777215);
			drawCenteredString(fontRenderer, clientHandler.field_72560_a, width / 2, height / 2 - 10, 16777215);
		}
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
	}
	
	private void spawnNewServerThread(String par1Str, int par2)
	{
		mc.getLogAgent().logInfo("Connecting to " + par1Str + ", " + par2);
		new ThreadConnectToServer(this, par1Str, par2).start();
	}
	
	@Override public void updateScreen()
	{
		if(clientHandler != null)
		{
			clientHandler.processReadPackets();
		}
	}
	
	static Minecraft func_74250_f(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.mc;
	}
	
	static Minecraft func_74251_g(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.mc;
	}
	
	static Minecraft func_74254_c(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.mc;
	}
	
	static Minecraft func_74256_a(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.mc;
	}
	
	static Minecraft func_98096_h(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.mc;
	}
	
	static GuiScreen func_98097_e(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.field_98098_c;
	}
	
	static NetClientHandler getNetClientHandler(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.clientHandler;
	}
	
	static boolean isCancelled(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.cancelled;
	}
	
	static NetClientHandler setNetClientHandler(GuiConnecting par0GuiConnecting, NetClientHandler par1NetClientHandler)
	{
		return par0GuiConnecting.clientHandler = par1NetClientHandler;
	}
}
