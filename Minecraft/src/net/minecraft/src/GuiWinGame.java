package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuiWinGame extends GuiScreen
{
	private int updateCounter = 0;
	private List lines;
	private int field_73989_c = 0;
	private float field_73987_d = 0.5F;
	
	@Override public boolean doesGuiPauseGame()
	{
		return true;
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		func_73986_b(par1, par2, par3);
		Tessellator var4 = Tessellator.instance;
		short var5 = 274;
		int var6 = width / 2 - var5 / 2;
		int var7 = height + 50;
		float var8 = -(updateCounter + par3) * field_73987_d;
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, var8, 0.0F);
		mc.renderEngine.bindTexture("/title/mclogo.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(var6, var7, 0, 0, 155, 44);
		drawTexturedModalRect(var6 + 155, var7, 0, 45, 155, 44);
		var4.setColorOpaque_I(16777215);
		int var9 = var7 + 200;
		int var10;
		for(var10 = 0; var10 < lines.size(); ++var10)
		{
			if(var10 == lines.size() - 1)
			{
				float var11 = var9 + var8 - (height / 2 - 6);
				if(var11 < 0.0F)
				{
					GL11.glTranslatef(0.0F, -var11, 0.0F);
				}
			}
			if(var9 + var8 + 12.0F + 8.0F > 0.0F && var9 + var8 < height)
			{
				String var12 = (String) lines.get(var10);
				if(var12.startsWith("[C]"))
				{
					fontRenderer.drawStringWithShadow(var12.substring(3), var6 + (var5 - fontRenderer.getStringWidth(var12.substring(3))) / 2, var9, 16777215);
				} else
				{
					fontRenderer.fontRandom.setSeed(var10 * 4238972211L + updateCounter / 4);
					fontRenderer.drawStringWithShadow(var12, var6, var9, 16777215);
				}
			}
			var9 += 12;
		}
		GL11.glPopMatrix();
		mc.renderEngine.bindTexture("%blur%/misc/vignette.png");
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_COLOR);
		var4.startDrawingQuads();
		var4.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
		var10 = width;
		int var13 = height;
		var4.addVertexWithUV(0.0D, var13, zLevel, 0.0D, 1.0D);
		var4.addVertexWithUV(var10, var13, zLevel, 1.0D, 1.0D);
		var4.addVertexWithUV(var10, 0.0D, zLevel, 1.0D, 0.0D);
		var4.addVertexWithUV(0.0D, 0.0D, zLevel, 0.0D, 0.0D);
		var4.draw();
		GL11.glDisable(GL11.GL_BLEND);
		super.drawScreen(par1, par2, par3);
	}
	
	private void func_73986_b(int par1, int par2, float par3)
	{
		Tessellator var4 = Tessellator.instance;
		mc.renderEngine.bindTexture("%blur%/gui/background.png");
		var4.startDrawingQuads();
		var4.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
		int var5 = width;
		float var6 = 0.0F - (updateCounter + par3) * 0.5F * field_73987_d;
		float var7 = height - (updateCounter + par3) * 0.5F * field_73987_d;
		float var8 = 0.015625F;
		float var9 = (updateCounter + par3 - 0.0F) * 0.02F;
		float var10 = (field_73989_c + height + height + 24) / field_73987_d;
		float var11 = (var10 - 20.0F - (updateCounter + par3)) * 0.005F;
		if(var11 < var9)
		{
			var9 = var11;
		}
		if(var9 > 1.0F)
		{
			var9 = 1.0F;
		}
		var9 *= var9;
		var9 = var9 * 96.0F / 255.0F;
		var4.setColorOpaque_F(var9, var9, var9);
		var4.addVertexWithUV(0.0D, height, zLevel, 0.0D, var6 * var8);
		var4.addVertexWithUV(var5, height, zLevel, var5 * var8, var6 * var8);
		var4.addVertexWithUV(var5, 0.0D, zLevel, var5 * var8, var7 * var8);
		var4.addVertexWithUV(0.0D, 0.0D, zLevel, 0.0D, var7 * var8);
		var4.draw();
	}
	
	@Override public void initGui()
	{
		if(lines == null)
		{
			lines = new ArrayList();
			try
			{
				String var1 = "";
				String var2 = "" + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + EnumChatFormatting.GREEN + EnumChatFormatting.AQUA;
				short var3 = 274;
				BufferedReader var4 = new BufferedReader(new InputStreamReader(GuiWinGame.class.getResourceAsStream("/title/win.txt"), Charset.forName("UTF-8")));
				Random var5 = new Random(8124371L);
				int var6;
				while((var1 = var4.readLine()) != null)
				{
					String var7;
					String var8;
					for(var1 = var1.replaceAll("PLAYERNAME", mc.session.username); var1.contains(var2); var1 = var7 + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, var5.nextInt(4) + 3) + var8)
					{
						var6 = var1.indexOf(var2);
						var7 = var1.substring(0, var6);
						var8 = var1.substring(var6 + var2.length());
					}
					lines.addAll(mc.fontRenderer.listFormattedStringToWidth(var1, var3));
					lines.add("");
				}
				for(var6 = 0; var6 < 8; ++var6)
				{
					lines.add("");
				}
				var4 = new BufferedReader(new InputStreamReader(GuiWinGame.class.getResourceAsStream("/title/credits.txt"), Charset.forName("UTF-8")));
				while((var1 = var4.readLine()) != null)
				{
					var1 = var1.replaceAll("PLAYERNAME", mc.session.username);
					var1 = var1.replaceAll("\t", "    ");
					lines.addAll(mc.fontRenderer.listFormattedStringToWidth(var1, var3));
					lines.add("");
				}
				field_73989_c = lines.size() * 12;
			} catch(Exception var9)
			{
				var9.printStackTrace();
			}
		}
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		if(par2 == 1)
		{
			respawnPlayer();
		}
	}
	
	private void respawnPlayer()
	{
		mc.thePlayer.sendQueue.addToSendQueue(new Packet205ClientCommand(1));
		mc.displayGuiScreen((GuiScreen) null);
	}
	
	@Override public void updateScreen()
	{
		++updateCounter;
		float var1 = (field_73989_c + height + height + 24) / field_73987_d;
		if(updateCounter > var1)
		{
			respawnPlayer();
		}
	}
}
