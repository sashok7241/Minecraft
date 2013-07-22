package net.minecraft.src;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GuiChat extends GuiScreen
{
	private String field_73898_b = "";
	private int sentHistoryCursor = -1;
	private boolean field_73897_d;
	private boolean field_73905_m;
	private int field_73903_n;
	private List field_73904_o = new ArrayList();
	private URI clickedURI;
	protected GuiTextField inputField;
	private String defaultInputFieldText = "";
	
	public GuiChat()
	{
	}
	
	public GuiChat(String par1Str)
	{
		defaultInputFieldText = par1Str;
	}
	
	public void completePlayerName()
	{
		String var3;
		if(field_73897_d)
		{
			inputField.deleteFromCursor(inputField.func_73798_a(-1, inputField.getCursorPosition(), false) - inputField.getCursorPosition());
			if(field_73903_n >= field_73904_o.size())
			{
				field_73903_n = 0;
			}
		} else
		{
			int var1 = inputField.func_73798_a(-1, inputField.getCursorPosition(), false);
			field_73904_o.clear();
			field_73903_n = 0;
			String var2 = inputField.getText().substring(var1).toLowerCase();
			var3 = inputField.getText().substring(0, inputField.getCursorPosition());
			func_73893_a(var3, var2);
			if(field_73904_o.isEmpty()) return;
			field_73897_d = true;
			inputField.deleteFromCursor(var1 - inputField.getCursorPosition());
		}
		if(field_73904_o.size() > 1)
		{
			StringBuilder var4 = new StringBuilder();
			for(Iterator var5 = field_73904_o.iterator(); var5.hasNext(); var4.append(var3))
			{
				var3 = (String) var5.next();
				if(var4.length() > 0)
				{
					var4.append(", ");
				}
			}
			mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(var4.toString(), 1);
		}
		inputField.writeText((String) field_73904_o.get(field_73903_n++));
	}
	
	@Override public void confirmClicked(boolean par1, int par2)
	{
		if(par2 == 0)
		{
			if(par1)
			{
				func_73896_a(clickedURI);
			}
			clickedURI = null;
			mc.displayGuiScreen(this);
		}
	}
	
	@Override public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawRect(2, height - 14, width - 2, height - 2, Integer.MIN_VALUE);
		inputField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	private void func_73893_a(String par1Str, String par2Str)
	{
		if(par1Str.length() >= 1)
		{
			mc.thePlayer.sendQueue.addToSendQueue(new Packet203AutoComplete(par1Str));
			field_73905_m = true;
		}
	}
	
	public void func_73894_a(String[] par1ArrayOfStr)
	{
		if(field_73905_m)
		{
			field_73904_o.clear();
			String[] var2 = par1ArrayOfStr;
			int var3 = par1ArrayOfStr.length;
			for(int var4 = 0; var4 < var3; ++var4)
			{
				String var5 = var2[var4];
				if(var5.length() > 0)
				{
					field_73904_o.add(var5);
				}
			}
			if(field_73904_o.size() > 0)
			{
				field_73897_d = true;
				completePlayerName();
			}
		}
	}
	
	private void func_73896_a(URI par1URI)
	{
		try
		{
			Class var2 = Class.forName("java.awt.Desktop");
			Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
			var2.getMethod("browse", new Class[] { URI.class }).invoke(var3, new Object[] { par1URI });
		} catch(Throwable var4)
		{
			var4.printStackTrace();
		}
	}
	
	public void getSentHistory(int par1)
	{
		int var2 = sentHistoryCursor + par1;
		int var3 = mc.ingameGUI.getChatGUI().getSentMessages().size();
		if(var2 < 0)
		{
			var2 = 0;
		}
		if(var2 > var3)
		{
			var2 = var3;
		}
		if(var2 != sentHistoryCursor)
		{
			if(var2 == var3)
			{
				sentHistoryCursor = var3;
				inputField.setText(field_73898_b);
			} else
			{
				if(sentHistoryCursor == var3)
				{
					field_73898_b = inputField.getText();
				}
				inputField.setText((String) mc.ingameGUI.getChatGUI().getSentMessages().get(var2));
				sentHistoryCursor = var2;
			}
		}
	}
	
	@Override public void handleMouseInput()
	{
		super.handleMouseInput();
		int var1 = Mouse.getEventDWheel();
		if(var1 != 0)
		{
			if(var1 > 1)
			{
				var1 = 1;
			}
			if(var1 < -1)
			{
				var1 = -1;
			}
			if(!isShiftKeyDown())
			{
				var1 *= 7;
			}
			mc.ingameGUI.getChatGUI().scroll(var1);
		}
	}
	
	@Override public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		sentHistoryCursor = mc.ingameGUI.getChatGUI().getSentMessages().size();
		inputField = new GuiTextField(fontRenderer, 4, height - 12, width - 4, 12);
		inputField.setMaxStringLength(100);
		inputField.setEnableBackgroundDrawing(false);
		inputField.setFocused(true);
		inputField.setText(defaultInputFieldText);
		inputField.setCanLoseFocus(false);
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		field_73905_m = false;
		if(par2 == 15)
		{
			completePlayerName();
		} else
		{
			field_73897_d = false;
		}
		if(par2 == 1)
		{
			mc.displayGuiScreen((GuiScreen) null);
		} else if(par2 != 28 && par2 != 156)
		{
			if(par2 == 200)
			{
				getSentHistory(-1);
			} else if(par2 == 208)
			{
				getSentHistory(1);
			} else if(par2 == 201)
			{
				mc.ingameGUI.getChatGUI().scroll(mc.ingameGUI.getChatGUI().func_96127_i() - 1);
			} else if(par2 == 209)
			{
				mc.ingameGUI.getChatGUI().scroll(-mc.ingameGUI.getChatGUI().func_96127_i() + 1);
			} else
			{
				inputField.textboxKeyTyped(par1, par2);
			}
		} else
		{
			String var3 = inputField.getText().trim();
			if(var3.length() > 0)
			{
				mc.ingameGUI.getChatGUI().addToSentMessages(var3);
				if(!mc.handleClientCommand(var3))
				{
					mc.thePlayer.sendChatMessage(var3);
				}
			}
			mc.displayGuiScreen((GuiScreen) null);
		}
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		if(par3 == 0 && mc.gameSettings.chatLinks)
		{
			ChatClickData var4 = mc.ingameGUI.getChatGUI().func_73766_a(Mouse.getX(), Mouse.getY());
			if(var4 != null)
			{
				URI var5 = var4.getURI();
				if(var5 != null)
				{
					if(mc.gameSettings.chatLinksPrompt)
					{
						clickedURI = var5;
						mc.displayGuiScreen(new GuiConfirmOpenLink(this, var4.getClickedUrl(), 0, false));
					} else
					{
						func_73896_a(var5);
					}
					return;
				}
			}
		}
		inputField.mouseClicked(par1, par2, par3);
		super.mouseClicked(par1, par2, par3);
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
		mc.ingameGUI.getChatGUI().resetScroll();
	}
	
	@Override public void updateScreen()
	{
		inputField.updateCursorCounter();
	}
}
