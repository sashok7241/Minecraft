package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import net.minecraft.client.Minecraft;

public class GuiTexturePacks extends GuiScreen
{
	protected GuiScreen guiScreen;
	private int refreshTimer = -1;
	private String fileLocation = "";
	private GuiTexturePackSlot guiTexturePackSlot;
	private GameSettings field_96146_n;
	
	public GuiTexturePacks(GuiScreen par1, GameSettings par2)
	{
		guiScreen = par1;
		field_96146_n = par2;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 5)
			{
				if(Minecraft.getOs() == EnumOS.MACOS)
				{
					try
					{
						mc.getLogAgent().logInfo(fileLocation);
						Runtime.getRuntime().exec(new String[] { "/usr/bin/open", fileLocation });
						return;
					} catch(IOException var7)
					{
						var7.printStackTrace();
					}
				} else if(Minecraft.getOs() == EnumOS.WINDOWS)
				{
					String var2 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { fileLocation });
					try
					{
						Runtime.getRuntime().exec(var2);
						return;
					} catch(IOException var6)
					{
						var6.printStackTrace();
					}
				}
				boolean var8 = false;
				try
				{
					Class var3 = Class.forName("java.awt.Desktop");
					Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
					var3.getMethod("browse", new Class[] { URI.class }).invoke(var4, new Object[] { new File(Minecraft.getMinecraftDir(), "texturepacks").toURI() });
				} catch(Throwable var5)
				{
					var5.printStackTrace();
					var8 = true;
				}
				if(var8)
				{
					mc.getLogAgent().logInfo("Opening via system class!");
					Sys.openURL("file://" + fileLocation);
				}
			} else if(par1GuiButton.id == 6)
			{
				mc.displayGuiScreen(guiScreen);
			} else
			{
				guiTexturePackSlot.actionPerformed(par1GuiButton);
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		guiTexturePackSlot.drawScreen(par1, par2, par3);
		if(refreshTimer <= 0)
		{
			mc.texturePackList.updateAvaliableTexturePacks();
			refreshTimer += 20;
		}
		StringTranslate var4 = StringTranslate.getInstance();
		drawCenteredString(fontRenderer, var4.translateKey("texturePack.title"), width / 2, 16, 16777215);
		drawCenteredString(fontRenderer, var4.translateKey("texturePack.folderInfo"), width / 2 - 77, height - 26, 8421504);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		buttonList.add(new GuiSmallButton(5, width / 2 - 154, height - 48, var1.translateKey("texturePack.openFolder")));
		buttonList.add(new GuiSmallButton(6, width / 2 + 4, height - 48, var1.translateKey("gui.done")));
		mc.texturePackList.updateAvaliableTexturePacks();
		fileLocation = new File(Minecraft.getMinecraftDir(), "texturepacks").getAbsolutePath();
		guiTexturePackSlot = new GuiTexturePackSlot(this);
		guiTexturePackSlot.registerScrollButtons(buttonList, 7, 8);
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
	
	static Minecraft func_73950_a(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.mc;
	}
	
	static Minecraft func_73951_d(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.mc;
	}
	
	static Minecraft func_73952_e(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.mc;
	}
	
	static Minecraft func_73953_j(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.mc;
	}
	
	static FontRenderer func_73954_n(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.fontRenderer;
	}
	
	static Minecraft func_73955_b(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.mc;
	}
	
	static Minecraft func_73956_i(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.mc;
	}
	
	static Minecraft func_73957_h(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.mc;
	}
	
	static Minecraft func_73958_c(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.mc;
	}
	
	static Minecraft func_73959_g(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.mc;
	}
	
	static Minecraft func_73961_k(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.mc;
	}
	
	static Minecraft func_73962_f(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.mc;
	}
	
	static Minecraft func_96142_m(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.mc;
	}
	
	static Minecraft func_96143_l(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.mc;
	}
	
	static FontRenderer func_96144_p(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.fontRenderer;
	}
	
	static FontRenderer func_96145_o(GuiTexturePacks par0GuiTexturePacks)
	{
		return par0GuiTexturePacks.fontRenderer;
	}
}
