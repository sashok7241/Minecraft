package net.minecraft.src;

import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

public class GuiScreen extends Gui
{
	protected Minecraft mc;
	public int width;
	public int height;
	protected List buttonList = new ArrayList();
	public boolean allowUserInput;
	protected FontRenderer fontRenderer;
	private GuiButton selectedButton;
	private int eventButton;
	private long lastMouseEvent;
	private int field_92018_d;
	
	protected void actionPerformed(GuiButton par1GuiButton)
	{
	}
	
	public void confirmClicked(boolean par1, int par2)
	{
	}
	
	public boolean doesGuiPauseGame()
	{
		return true;
	}
	
	public void drawBackground(int par1)
	{
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator var2 = Tessellator.instance;
		mc.func_110434_K().func_110577_a(field_110325_k);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var3 = 32.0F;
		var2.startDrawingQuads();
		var2.setColorOpaque_I(4210752);
		var2.addVertexWithUV(0.0D, height, 0.0D, 0.0D, height / var3 + par1);
		var2.addVertexWithUV(width, height, 0.0D, width / var3, height / var3 + par1);
		var2.addVertexWithUV(width, 0.0D, 0.0D, width / var3, par1);
		var2.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, par1);
		var2.draw();
	}
	
	public void drawDefaultBackground()
	{
		drawWorldBackground(0);
	}
	
	public void drawScreen(int par1, int par2, float par3)
	{
		for(int var4 = 0; var4 < buttonList.size(); ++var4)
		{
			GuiButton var5 = (GuiButton) buttonList.get(var4);
			var5.drawButton(mc, par1, par2);
		}
	}
	
	public void drawWorldBackground(int par1)
	{
		if(mc.theWorld != null)
		{
			drawGradientRect(0, 0, width, height, -1072689136, -804253680);
		} else
		{
			drawBackground(par1);
		}
	}
	
	public void handleInput()
	{
		while(Mouse.next())
		{
			handleMouseInput();
		}
		while(Keyboard.next())
		{
			handleKeyboardInput();
		}
	}
	
	public void handleKeyboardInput()
	{
		if(Keyboard.getEventKeyState())
		{
			int var1 = Keyboard.getEventKey();
			char var2 = Keyboard.getEventCharacter();
			if(var1 == 87)
			{
				mc.toggleFullscreen();
				return;
			}
			keyTyped(var2, var1);
		}
	}
	
	public void handleMouseInput()
	{
		int var1 = Mouse.getEventX() * width / mc.displayWidth;
		int var2 = height - Mouse.getEventY() * height / mc.displayHeight - 1;
		int var3 = Mouse.getEventButton();
		if(Minecraft.field_142025_a && var3 == 0 && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)))
		{
			var3 = 1;
		}
		if(Mouse.getEventButtonState())
		{
			if(mc.gameSettings.touchscreen && field_92018_d++ > 0) return;
			eventButton = var3;
			lastMouseEvent = Minecraft.getSystemTime();
			mouseClicked(var1, var2, eventButton);
		} else if(var3 != -1)
		{
			if(mc.gameSettings.touchscreen && --field_92018_d > 0) return;
			eventButton = -1;
			mouseMovedOrUp(var1, var2, var3);
		} else if(eventButton != -1 && lastMouseEvent > 0L)
		{
			long var4 = Minecraft.getSystemTime() - lastMouseEvent;
			mouseClickMove(var1, var2, eventButton, var4);
		}
	}
	
	public void initGui()
	{
	}
	
	protected void keyTyped(char par1, int par2)
	{
		if(par2 == 1)
		{
			mc.displayGuiScreen((GuiScreen) null);
			mc.setIngameFocus();
		}
	}
	
	protected void mouseClicked(int par1, int par2, int par3)
	{
		if(par3 == 0)
		{
			for(int var4 = 0; var4 < buttonList.size(); ++var4)
			{
				GuiButton var5 = (GuiButton) buttonList.get(var4);
				if(var5.mousePressed(mc, par1, par2))
				{
					selectedButton = var5;
					mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
					actionPerformed(var5);
				}
			}
		}
	}
	
	protected void mouseClickMove(int par1, int par2, int par3, long par4)
	{
	}
	
	protected void mouseMovedOrUp(int par1, int par2, int par3)
	{
		if(selectedButton != null && par3 == 0)
		{
			selectedButton.mouseReleased(par1, par2);
			selectedButton = null;
		}
	}
	
	public void onGuiClosed()
	{
	}
	
	public void setWorldAndResolution(Minecraft par1Minecraft, int par2, int par3)
	{
		mc = par1Minecraft;
		fontRenderer = par1Minecraft.fontRenderer;
		width = par2;
		height = par3;
		buttonList.clear();
		initGui();
	}
	
	public void updateScreen()
	{
	}
	
	public static String getClipboardString()
	{
		try
		{
			Transferable var0 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents((Object) null);
			if(var0 != null && var0.isDataFlavorSupported(DataFlavor.stringFlavor)) return (String) var0.getTransferData(DataFlavor.stringFlavor);
		} catch(Exception var1)
		{
			;
		}
		return "";
	}
	
	public static boolean isCtrlKeyDown()
	{
		return Minecraft.field_142025_a ? Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220) : Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
	}
	
	public static boolean isShiftKeyDown()
	{
		return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
	}
	
	public static void setClipboardString(String par0Str)
	{
		try
		{
			StringSelection var1 = new StringSelection(par0Str);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(var1, (ClipboardOwner) null);
		} catch(Exception var2)
		{
			;
		}
	}
}
