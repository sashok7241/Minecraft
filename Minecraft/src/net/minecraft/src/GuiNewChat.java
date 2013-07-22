package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;

public class GuiNewChat extends Gui
{
	private final Minecraft mc;
	private final List sentMessages = new ArrayList();
	private final List chatLines = new ArrayList();
	private final List field_96134_d = new ArrayList();
	private int field_73768_d;
	private boolean field_73769_e;
	
	public GuiNewChat(Minecraft par1Minecraft)
	{
		mc = par1Minecraft;
	}
	
	public void addToSentMessages(String par1Str)
	{
		if(sentMessages.isEmpty() || !((String) sentMessages.get(sentMessages.size() - 1)).equals(par1Str))
		{
			sentMessages.add(par1Str);
		}
	}
	
	public void addTranslatedMessage(String par1Str, Object ... par2ArrayOfObj)
	{
		printChatMessage(I18n.func_135052_a(par1Str, par2ArrayOfObj));
	}
	
	public void clearChatMessages()
	{
		field_96134_d.clear();
		chatLines.clear();
		sentMessages.clear();
	}
	
	public void deleteChatLine(int par1)
	{
		Iterator var2 = field_96134_d.iterator();
		ChatLine var3;
		do
		{
			if(!var2.hasNext())
			{
				var2 = chatLines.iterator();
				do
				{
					if(!var2.hasNext()) return;
					var3 = (ChatLine) var2.next();
				} while(var3.getChatLineID() != par1);
				var2.remove();
				return;
			}
			var3 = (ChatLine) var2.next();
		} while(var3.getChatLineID() != par1);
		var2.remove();
	}
	
	public void drawChat(int par1)
	{
		if(mc.gameSettings.chatVisibility != 2)
		{
			int var2 = func_96127_i();
			boolean var3 = false;
			int var4 = 0;
			int var5 = field_96134_d.size();
			float var6 = mc.gameSettings.chatOpacity * 0.9F + 0.1F;
			if(var5 > 0)
			{
				if(getChatOpen())
				{
					var3 = true;
				}
				float var7 = func_96131_h();
				int var8 = MathHelper.ceiling_float_int(func_96126_f() / var7);
				GL11.glPushMatrix();
				GL11.glTranslatef(2.0F, 20.0F, 0.0F);
				GL11.glScalef(var7, var7, 1.0F);
				int var9;
				int var11;
				int var14;
				for(var9 = 0; var9 + field_73768_d < field_96134_d.size() && var9 < var2; ++var9)
				{
					ChatLine var10 = (ChatLine) field_96134_d.get(var9 + field_73768_d);
					if(var10 != null)
					{
						var11 = par1 - var10.getUpdatedCounter();
						if(var11 < 200 || var3)
						{
							double var12 = var11 / 200.0D;
							var12 = 1.0D - var12;
							var12 *= 10.0D;
							if(var12 < 0.0D)
							{
								var12 = 0.0D;
							}
							if(var12 > 1.0D)
							{
								var12 = 1.0D;
							}
							var12 *= var12;
							var14 = (int) (255.0D * var12);
							if(var3)
							{
								var14 = 255;
							}
							var14 = (int) (var14 * var6);
							++var4;
							if(var14 > 3)
							{
								byte var15 = 0;
								int var16 = -var9 * 9;
								drawRect(var15, var16 - 9, var15 + var8 + 4, var16, var14 / 2 << 24);
								GL11.glEnable(GL11.GL_BLEND);
								String var17 = var10.getChatLineString();
								if(!mc.gameSettings.chatColours)
								{
									var17 = StringUtils.stripControlCodes(var17);
								}
								mc.fontRenderer.drawStringWithShadow(var17, var15, var16 - 8, 16777215 + (var14 << 24));
							}
						}
					}
				}
				if(var3)
				{
					var9 = mc.fontRenderer.FONT_HEIGHT;
					GL11.glTranslatef(-3.0F, 0.0F, 0.0F);
					int var18 = var5 * var9 + var5;
					var11 = var4 * var9 + var4;
					int var20 = field_73768_d * var11 / var5;
					int var13 = var11 * var11 / var18;
					if(var18 != var11)
					{
						var14 = var20 > 0 ? 170 : 96;
						int var19 = field_73769_e ? 13382451 : 3355562;
						drawRect(0, -var20, 2, -var20 - var13, var19 + (var14 << 24));
						drawRect(2, -var20, 1, -var20 - var13, 13421772 + (var14 << 24));
					}
				}
				GL11.glPopMatrix();
			}
		}
	}
	
	public ChatClickData func_73766_a(int par1, int par2)
	{
		if(!getChatOpen()) return null;
		else
		{
			ScaledResolution var3 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			int var4 = var3.getScaleFactor();
			float var5 = func_96131_h();
			int var6 = par1 / var4 - 3;
			int var7 = par2 / var4 - 25;
			var6 = MathHelper.floor_float(var6 / var5);
			var7 = MathHelper.floor_float(var7 / var5);
			if(var6 >= 0 && var7 >= 0)
			{
				int var8 = Math.min(func_96127_i(), field_96134_d.size());
				if(var6 <= MathHelper.floor_float(func_96126_f() / func_96131_h()) && var7 < mc.fontRenderer.FONT_HEIGHT * var8 + var8)
				{
					int var9 = var7 / (mc.fontRenderer.FONT_HEIGHT + 1) + field_73768_d;
					return new ChatClickData(mc.fontRenderer, (ChatLine) field_96134_d.get(var9), var6, var7 - (var9 - field_73768_d) * mc.fontRenderer.FONT_HEIGHT + var9);
				} else return null;
			} else return null;
		}
	}
	
	public int func_96126_f()
	{
		return func_96128_a(mc.gameSettings.chatWidth);
	}
	
	public int func_96127_i()
	{
		return func_96133_g() / 9;
	}
	
	private void func_96129_a(String par1Str, int par2, int par3, boolean par4)
	{
		boolean var5 = getChatOpen();
		boolean var6 = true;
		if(par2 != 0)
		{
			deleteChatLine(par2);
		}
		Iterator var7 = mc.fontRenderer.listFormattedStringToWidth(par1Str, MathHelper.floor_float(func_96126_f() / func_96131_h())).iterator();
		while(var7.hasNext())
		{
			String var8 = (String) var7.next();
			if(var5 && field_73768_d > 0)
			{
				field_73769_e = true;
				scroll(1);
			}
			if(!var6)
			{
				var8 = " " + var8;
			}
			var6 = false;
			field_96134_d.add(0, new ChatLine(par3, var8, par2));
		}
		while(field_96134_d.size() > 100)
		{
			field_96134_d.remove(field_96134_d.size() - 1);
		}
		if(!par4)
		{
			chatLines.add(0, new ChatLine(par3, par1Str.trim(), par2));
			while(chatLines.size() > 100)
			{
				chatLines.remove(chatLines.size() - 1);
			}
		}
	}
	
	public float func_96131_h()
	{
		return mc.gameSettings.chatScale;
	}
	
	public void func_96132_b()
	{
		field_96134_d.clear();
		resetScroll();
		for(int var1 = chatLines.size() - 1; var1 >= 0; --var1)
		{
			ChatLine var2 = (ChatLine) chatLines.get(var1);
			func_96129_a(var2.getChatLineString(), var2.getChatLineID(), var2.getUpdatedCounter(), true);
		}
	}
	
	public int func_96133_g()
	{
		return func_96130_b(getChatOpen() ? mc.gameSettings.chatHeightFocused : mc.gameSettings.chatHeightUnfocused);
	}
	
	public boolean getChatOpen()
	{
		return mc.currentScreen instanceof GuiChat;
	}
	
	public List getSentMessages()
	{
		return sentMessages;
	}
	
	public void printChatMessage(String par1Str)
	{
		printChatMessageWithOptionalDeletion(par1Str, 0);
	}
	
	public void printChatMessageWithOptionalDeletion(String par1Str, int par2)
	{
		func_96129_a(par1Str, par2, mc.ingameGUI.getUpdateCounter(), false);
		mc.getLogAgent().logInfo("[CHAT] " + EnumChatFormatting.func_110646_a(par1Str));
	}
	
	public void resetScroll()
	{
		field_73768_d = 0;
		field_73769_e = false;
	}
	
	public void scroll(int par1)
	{
		field_73768_d += par1;
		int var2 = field_96134_d.size();
		if(field_73768_d > var2 - func_96127_i())
		{
			field_73768_d = var2 - func_96127_i();
		}
		if(field_73768_d <= 0)
		{
			field_73768_d = 0;
			field_73769_e = false;
		}
	}
	
	public static final int func_96128_a(float par0)
	{
		short var1 = 320;
		byte var2 = 40;
		return MathHelper.floor_float(par0 * (var1 - var2) + var2);
	}
	
	public static final int func_96130_b(float par0)
	{
		short var1 = 180;
		byte var2 = 20;
		return MathHelper.floor_float(par0 * (var1 - var2) + var2);
	}
}
