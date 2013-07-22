package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class LoadingScreenRenderer implements IProgressUpdate
{
	private String field_73727_a = "";
	private Minecraft mc;
	private String currentlyDisplayedText = "";
	private long field_73723_d = Minecraft.getSystemTime();
	private boolean field_73724_e;
	
	public LoadingScreenRenderer(Minecraft par1Minecraft)
	{
		mc = par1Minecraft;
	}
	
	@Override public void displayProgressMessage(String par1Str)
	{
		field_73724_e = true;
		func_73722_d(par1Str);
	}
	
	public void func_73722_d(String par1Str)
	{
		currentlyDisplayedText = par1Str;
		if(!mc.running)
		{
			if(!field_73724_e) throw new MinecraftError();
		} else
		{
			ScaledResolution var2 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0.0D, var2.getScaledWidth_double(), var2.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			GL11.glTranslatef(0.0F, 0.0F, -200.0F);
		}
	}
	
	@Override public void resetProgresAndWorkingMessage(String par1Str)
	{
		if(!mc.running)
		{
			if(!field_73724_e) throw new MinecraftError();
		} else
		{
			field_73723_d = 0L;
			field_73727_a = par1Str;
			setLoadingProgress(-1);
			field_73723_d = 0L;
		}
	}
	
	public void resetProgressAndMessage(String par1Str)
	{
		field_73724_e = false;
		func_73722_d(par1Str);
	}
	
	@Override public void setLoadingProgress(int par1)
	{
		if(!mc.running)
		{
			if(!field_73724_e) throw new MinecraftError();
		} else
		{
			long var2 = Minecraft.getSystemTime();
			if(var2 - field_73723_d >= 100L)
			{
				field_73723_d = var2;
				ScaledResolution var4 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
				int var5 = var4.getScaledWidth();
				int var6 = var4.getScaledHeight();
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glOrtho(0.0D, var4.getScaledWidth_double(), var4.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();
				GL11.glTranslatef(0.0F, 0.0F, -200.0F);
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				Tessellator var7 = Tessellator.instance;
				mc.func_110434_K().func_110577_a(Gui.field_110325_k);
				float var8 = 32.0F;
				var7.startDrawingQuads();
				var7.setColorOpaque_I(4210752);
				var7.addVertexWithUV(0.0D, var6, 0.0D, 0.0D, var6 / var8);
				var7.addVertexWithUV(var5, var6, 0.0D, var5 / var8, var6 / var8);
				var7.addVertexWithUV(var5, 0.0D, 0.0D, var5 / var8, 0.0D);
				var7.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
				var7.draw();
				if(par1 >= 0)
				{
					byte var9 = 100;
					byte var10 = 2;
					int var11 = var5 / 2 - var9 / 2;
					int var12 = var6 / 2 + 16;
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					var7.startDrawingQuads();
					var7.setColorOpaque_I(8421504);
					var7.addVertex(var11, var12, 0.0D);
					var7.addVertex(var11, var12 + var10, 0.0D);
					var7.addVertex(var11 + var9, var12 + var10, 0.0D);
					var7.addVertex(var11 + var9, var12, 0.0D);
					var7.setColorOpaque_I(8454016);
					var7.addVertex(var11, var12, 0.0D);
					var7.addVertex(var11, var12 + var10, 0.0D);
					var7.addVertex(var11 + par1, var12 + var10, 0.0D);
					var7.addVertex(var11 + par1, var12, 0.0D);
					var7.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}
				mc.fontRenderer.drawStringWithShadow(currentlyDisplayedText, (var5 - mc.fontRenderer.getStringWidth(currentlyDisplayedText)) / 2, var6 / 2 - 4 - 16, 16777215);
				mc.fontRenderer.drawStringWithShadow(field_73727_a, (var5 - mc.fontRenderer.getStringWidth(field_73727_a)) / 2, var6 / 2 - 4 + 8, 16777215);
				Display.update();
				try
				{
					Thread.yield();
				} catch(Exception var13)
				{
					;
				}
			}
		}
	}
}
