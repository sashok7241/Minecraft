package net.minecraft.src;

import java.net.URI;

public class GuiScreenDemo extends GuiScreen
{
	private static final ResourceLocation field_110407_a = new ResourceLocation("textures/gui/demo_background.png");
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		switch(par1GuiButton.id)
		{
			case 1:
				par1GuiButton.enabled = false;
				try
				{
					Class var2 = Class.forName("java.awt.Desktop");
					Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
					var2.getMethod("browse", new Class[] { URI.class }).invoke(var3, new Object[] { new URI("http://www.minecraft.net/store?source=demo") });
				} catch(Throwable var4)
				{
					var4.printStackTrace();
				}
				break;
			case 2:
				mc.displayGuiScreen((GuiScreen) null);
				mc.setIngameFocus();
		}
	}
	
	@Override public void drawDefaultBackground()
	{
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.func_110434_K().func_110577_a(field_110407_a);
		int var1 = (width - 248) / 2;
		int var2 = (height - 166) / 2;
		drawTexturedModalRect(var1, var2, 0, 0, 248, 166);
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		int var4 = (width - 248) / 2 + 10;
		int var5 = (height - 166) / 2 + 8;
		fontRenderer.drawString(I18n.func_135053_a("demo.help.title"), var4, var5, 2039583);
		var5 += 12;
		GameSettings var6 = mc.gameSettings;
		fontRenderer.drawString(I18n.func_135052_a("demo.help.movementShort", new Object[] { GameSettings.getKeyDisplayString(var6.keyBindForward.keyCode), GameSettings.getKeyDisplayString(var6.keyBindLeft.keyCode), GameSettings.getKeyDisplayString(var6.keyBindBack.keyCode), GameSettings.getKeyDisplayString(var6.keyBindRight.keyCode) }), var4, var5, 5197647);
		fontRenderer.drawString(I18n.func_135053_a("demo.help.movementMouse"), var4, var5 + 12, 5197647);
		fontRenderer.drawString(I18n.func_135052_a("demo.help.jump", new Object[] { GameSettings.getKeyDisplayString(var6.keyBindJump.keyCode) }), var4, var5 + 24, 5197647);
		fontRenderer.drawString(I18n.func_135052_a("demo.help.inventory", new Object[] { GameSettings.getKeyDisplayString(var6.keyBindInventory.keyCode) }), var4, var5 + 36, 5197647);
		fontRenderer.drawSplitString(I18n.func_135053_a("demo.help.fullWrapped"), var4, var5 + 68, 218, 2039583);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		buttonList.clear();
		byte var1 = -16;
		buttonList.add(new GuiButton(1, width / 2 - 116, height / 2 + 62 + var1, 114, 20, I18n.func_135053_a("demo.help.buy")));
		buttonList.add(new GuiButton(2, width / 2 + 2, height / 2 + 62 + var1, 114, 20, I18n.func_135053_a("demo.help.later")));
	}
	
	@Override public void updateScreen()
	{
		super.updateScreen();
	}
}
