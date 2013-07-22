package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import net.minecraft.client.Minecraft;

public class GuiScreenTemporaryResourcePackSelect extends GuiScreen
{
	protected GuiScreen field_110347_a;
	private int refreshTimer = -1;
	private GuiScreenTemporaryResourcePackSelectSelectionList field_110346_c;
	private GameSettings field_96146_n;
	
	public GuiScreenTemporaryResourcePackSelect(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
	{
		field_110347_a = par1GuiScreen;
		field_96146_n = par2GameSettings;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 5)
			{
				File var2 = GuiScreenTemporaryResourcePackSelectSelectionList.func_110510_a(field_110346_c).func_110612_e();
				String var3 = var2.getAbsolutePath();
				if(Util.func_110647_a() == EnumOS.MACOS)
				{
					try
					{
						mc.getLogAgent().logInfo(var3);
						Runtime.getRuntime().exec(new String[] { "/usr/bin/open", var3 });
						return;
					} catch(IOException var9)
					{
						var9.printStackTrace();
					}
				} else if(Util.func_110647_a() == EnumOS.WINDOWS)
				{
					String var4 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { var3 });
					try
					{
						Runtime.getRuntime().exec(var4);
						return;
					} catch(IOException var8)
					{
						var8.printStackTrace();
					}
				}
				boolean var10 = false;
				try
				{
					Class var5 = Class.forName("java.awt.Desktop");
					Object var6 = var5.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
					var5.getMethod("browse", new Class[] { URI.class }).invoke(var6, new Object[] { var2.toURI() });
				} catch(Throwable var7)
				{
					var7.printStackTrace();
					var10 = true;
				}
				if(var10)
				{
					mc.getLogAgent().logInfo("Opening via system class!");
					Sys.openURL("file://" + var3);
				}
			} else if(par1GuiButton.id == 6)
			{
				mc.displayGuiScreen(field_110347_a);
			} else
			{
				field_110346_c.actionPerformed(par1GuiButton);
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		field_110346_c.drawScreen(par1, par2, par3);
		if(refreshTimer <= 0)
		{
			GuiScreenTemporaryResourcePackSelectSelectionList.func_110510_a(field_110346_c).func_110611_a();
			refreshTimer = 20;
		}
		drawCenteredString(fontRenderer, I18n.func_135053_a("resourcePack.title"), width / 2, 16, 16777215);
		drawCenteredString(fontRenderer, I18n.func_135053_a("resourcePack.folderInfo"), width / 2 - 77, height - 26, 8421504);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		buttonList.add(new GuiSmallButton(5, width / 2 - 154, height - 48, I18n.func_135053_a("resourcePack.openFolder")));
		buttonList.add(new GuiSmallButton(6, width / 2 + 4, height - 48, I18n.func_135053_a("gui.done")));
		field_110346_c = new GuiScreenTemporaryResourcePackSelectSelectionList(this, mc.func_110438_M());
		field_110346_c.registerScrollButtons(7, 8);
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
	}
	
	@Override protected void mouseMovedOrUp(int par1, int par2, int par3)
	{
		super.mouseMovedOrUp(par1, par2, par3);
	}
	
	@Override public void updateScreen()
	{
		super.updateScreen();
		--refreshTimer;
	}
	
	static Minecraft func_110334_e(GuiScreenTemporaryResourcePackSelect par0GuiScreenTemporaryResourcePackSelect)
	{
		return par0GuiScreenTemporaryResourcePackSelect.mc;
	}
	
	static FontRenderer func_110335_j(GuiScreenTemporaryResourcePackSelect par0GuiScreenTemporaryResourcePackSelect)
	{
		return par0GuiScreenTemporaryResourcePackSelect.fontRenderer;
	}
	
	static FontRenderer func_110337_i(GuiScreenTemporaryResourcePackSelect par0GuiScreenTemporaryResourcePackSelect)
	{
		return par0GuiScreenTemporaryResourcePackSelect.fontRenderer;
	}
	
	static FontRenderer func_110338_k(GuiScreenTemporaryResourcePackSelect par0GuiScreenTemporaryResourcePackSelect)
	{
		return par0GuiScreenTemporaryResourcePackSelect.fontRenderer;
	}
	
	static Minecraft func_110339_c(GuiScreenTemporaryResourcePackSelect par0GuiScreenTemporaryResourcePackSelect)
	{
		return par0GuiScreenTemporaryResourcePackSelect.mc;
	}
	
	static Minecraft func_110340_f(GuiScreenTemporaryResourcePackSelect par0GuiScreenTemporaryResourcePackSelect)
	{
		return par0GuiScreenTemporaryResourcePackSelect.mc;
	}
	
	static Minecraft func_110341_b(GuiScreenTemporaryResourcePackSelect par0GuiScreenTemporaryResourcePackSelect)
	{
		return par0GuiScreenTemporaryResourcePackSelect.mc;
	}
	
	static Minecraft func_110344_a(GuiScreenTemporaryResourcePackSelect par0GuiScreenTemporaryResourcePackSelect)
	{
		return par0GuiScreenTemporaryResourcePackSelect.mc;
	}
	
	static Minecraft func_110345_d(GuiScreenTemporaryResourcePackSelect par0GuiScreenTemporaryResourcePackSelect)
	{
		return par0GuiScreenTemporaryResourcePackSelect.mc;
	}
	
	static FontRenderer func_130016_h(GuiScreenTemporaryResourcePackSelect par0GuiScreenTemporaryResourcePackSelect)
	{
		return par0GuiScreenTemporaryResourcePackSelect.fontRenderer;
	}
	
	static FontRenderer func_130017_g(GuiScreenTemporaryResourcePackSelect par0GuiScreenTemporaryResourcePackSelect)
	{
		return par0GuiScreenTemporaryResourcePackSelect.fontRenderer;
	}
}
