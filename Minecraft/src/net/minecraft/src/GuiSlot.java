package net.minecraft.src;

import java.util.List;

import net.minecraft.client.Minecraft;

public abstract class GuiSlot
{
	private final Minecraft mc;
	private int width;
	private int height;
	protected int top;
	protected int bottom;
	private int right;
	private int left;
	protected final int slotHeight;
	private int scrollUpButtonID;
	private int scrollDownButtonID;
	protected int mouseX;
	protected int mouseY;
	private float initialClickY = -2.0F;
	private float scrollMultiplier;
	private float amountScrolled;
	private int selectedElement = -1;
	private long lastClicked = 0L;
	private boolean showSelectionBox = true;
	private boolean field_77243_s;
	private int field_77242_t;
	
	public GuiSlot(Minecraft p_i3060_1_, int p_i3060_2_, int p_i3060_3_, int p_i3060_4_, int p_i3060_5_, int p_i3060_6_)
	{
		mc = p_i3060_1_;
		width = p_i3060_2_;
		height = p_i3060_3_;
		top = p_i3060_4_;
		bottom = p_i3060_5_;
		slotHeight = p_i3060_6_;
		left = 0;
		right = p_i3060_2_;
	}
	
	public void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == scrollUpButtonID)
			{
				amountScrolled -= slotHeight * 2 / 3;
				initialClickY = -2.0F;
				bindAmountScrolled();
			} else if(par1GuiButton.id == scrollDownButtonID)
			{
				amountScrolled += slotHeight * 2 / 3;
				initialClickY = -2.0F;
				bindAmountScrolled();
			}
		}
	}
	
	private void bindAmountScrolled()
	{
		int var1 = func_77209_d();
		if(var1 < 0)
		{
			var1 /= 2;
		}
		if(amountScrolled < 0.0F)
		{
			amountScrolled = 0.0F;
		}
		if(amountScrolled > var1)
		{
			amountScrolled = var1;
		}
	}
	
	protected abstract void drawBackground();
	
	public void drawScreen(int par1, int par2, float par3)
	{
		mouseX = par1;
		mouseY = par2;
		drawBackground();
		int var4 = getSize();
		int var5 = getScrollBarX();
		int var6 = var5 + 6;
		int var9;
		int var10;
		int var11;
		int var13;
		int var19;
		if(Mouse.isButtonDown(0))
		{
			if(initialClickY == -1.0F)
			{
				boolean var7 = true;
				if(par2 >= top && par2 <= bottom)
				{
					int var8 = width / 2 - 110;
					var9 = width / 2 + 110;
					var10 = par2 - top - field_77242_t + (int) amountScrolled - 4;
					var11 = var10 / slotHeight;
					if(par1 >= var8 && par1 <= var9 && var11 >= 0 && var10 >= 0 && var11 < var4)
					{
						boolean var12 = var11 == selectedElement && Minecraft.getSystemTime() - lastClicked < 250L;
						elementClicked(var11, var12);
						selectedElement = var11;
						lastClicked = Minecraft.getSystemTime();
					} else if(par1 >= var8 && par1 <= var9 && var10 < 0)
					{
						func_77224_a(par1 - var8, par2 - top + (int) amountScrolled - 4);
						var7 = false;
					}
					if(par1 >= var5 && par1 <= var6)
					{
						scrollMultiplier = -1.0F;
						var19 = func_77209_d();
						if(var19 < 1)
						{
							var19 = 1;
						}
						var13 = (int) ((float) ((bottom - top) * (bottom - top)) / (float) getContentHeight());
						if(var13 < 32)
						{
							var13 = 32;
						}
						if(var13 > bottom - top - 8)
						{
							var13 = bottom - top - 8;
						}
						scrollMultiplier /= (float) (bottom - top - var13) / (float) var19;
					} else
					{
						scrollMultiplier = 1.0F;
					}
					if(var7)
					{
						initialClickY = par2;
					} else
					{
						initialClickY = -2.0F;
					}
				} else
				{
					initialClickY = -2.0F;
				}
			} else if(initialClickY >= 0.0F)
			{
				amountScrolled -= (par2 - initialClickY) * scrollMultiplier;
				initialClickY = par2;
			}
		} else
		{
			while(!mc.gameSettings.touchscreen && Mouse.next())
			{
				int var16 = Mouse.getEventDWheel();
				if(var16 != 0)
				{
					if(var16 > 0)
					{
						var16 = -1;
					} else if(var16 < 0)
					{
						var16 = 1;
					}
					amountScrolled += var16 * slotHeight / 2;
				}
			}
			initialClickY = -1.0F;
		}
		bindAmountScrolled();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator var18 = Tessellator.instance;
		mc.renderEngine.bindTexture("/gui/background.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var17 = 32.0F;
		var18.startDrawingQuads();
		var18.setColorOpaque_I(2105376);
		var18.addVertexWithUV(left, bottom, 0.0D, left / var17, (bottom + (int) amountScrolled) / var17);
		var18.addVertexWithUV(right, bottom, 0.0D, right / var17, (bottom + (int) amountScrolled) / var17);
		var18.addVertexWithUV(right, top, 0.0D, right / var17, (top + (int) amountScrolled) / var17);
		var18.addVertexWithUV(left, top, 0.0D, left / var17, (top + (int) amountScrolled) / var17);
		var18.draw();
		var9 = width / 2 - 92 - 16;
		var10 = top + 4 - (int) amountScrolled;
		if(field_77243_s)
		{
			func_77222_a(var9, var10, var18);
		}
		int var14;
		for(var11 = 0; var11 < var4; ++var11)
		{
			var19 = var10 + var11 * slotHeight + field_77242_t;
			var13 = slotHeight - 4;
			if(var19 <= bottom && var19 + var13 >= top)
			{
				if(showSelectionBox && isSelected(var11))
				{
					var14 = width / 2 - 110;
					int var15 = width / 2 + 110;
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					var18.startDrawingQuads();
					var18.setColorOpaque_I(8421504);
					var18.addVertexWithUV(var14, var19 + var13 + 2, 0.0D, 0.0D, 1.0D);
					var18.addVertexWithUV(var15, var19 + var13 + 2, 0.0D, 1.0D, 1.0D);
					var18.addVertexWithUV(var15, var19 - 2, 0.0D, 1.0D, 0.0D);
					var18.addVertexWithUV(var14, var19 - 2, 0.0D, 0.0D, 0.0D);
					var18.setColorOpaque_I(0);
					var18.addVertexWithUV(var14 + 1, var19 + var13 + 1, 0.0D, 0.0D, 1.0D);
					var18.addVertexWithUV(var15 - 1, var19 + var13 + 1, 0.0D, 1.0D, 1.0D);
					var18.addVertexWithUV(var15 - 1, var19 - 1, 0.0D, 1.0D, 0.0D);
					var18.addVertexWithUV(var14 + 1, var19 - 1, 0.0D, 0.0D, 0.0D);
					var18.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}
				drawSlot(var11, var9, var19, var13, var18);
			}
		}
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		byte var20 = 4;
		overlayBackground(0, top, 255, 255);
		overlayBackground(bottom, height, 255, 255);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		var18.startDrawingQuads();
		var18.setColorRGBA_I(0, 0);
		var18.addVertexWithUV(left, top + var20, 0.0D, 0.0D, 1.0D);
		var18.addVertexWithUV(right, top + var20, 0.0D, 1.0D, 1.0D);
		var18.setColorRGBA_I(0, 255);
		var18.addVertexWithUV(right, top, 0.0D, 1.0D, 0.0D);
		var18.addVertexWithUV(left, top, 0.0D, 0.0D, 0.0D);
		var18.draw();
		var18.startDrawingQuads();
		var18.setColorRGBA_I(0, 255);
		var18.addVertexWithUV(left, bottom, 0.0D, 0.0D, 1.0D);
		var18.addVertexWithUV(right, bottom, 0.0D, 1.0D, 1.0D);
		var18.setColorRGBA_I(0, 0);
		var18.addVertexWithUV(right, bottom - var20, 0.0D, 1.0D, 0.0D);
		var18.addVertexWithUV(left, bottom - var20, 0.0D, 0.0D, 0.0D);
		var18.draw();
		var19 = func_77209_d();
		if(var19 > 0)
		{
			var13 = (bottom - top) * (bottom - top) / getContentHeight();
			if(var13 < 32)
			{
				var13 = 32;
			}
			if(var13 > bottom - top - 8)
			{
				var13 = bottom - top - 8;
			}
			var14 = (int) amountScrolled * (bottom - top - var13) / var19 + top;
			if(var14 < top)
			{
				var14 = top;
			}
			var18.startDrawingQuads();
			var18.setColorRGBA_I(0, 255);
			var18.addVertexWithUV(var5, bottom, 0.0D, 0.0D, 1.0D);
			var18.addVertexWithUV(var6, bottom, 0.0D, 1.0D, 1.0D);
			var18.addVertexWithUV(var6, top, 0.0D, 1.0D, 0.0D);
			var18.addVertexWithUV(var5, top, 0.0D, 0.0D, 0.0D);
			var18.draw();
			var18.startDrawingQuads();
			var18.setColorRGBA_I(8421504, 255);
			var18.addVertexWithUV(var5, var14 + var13, 0.0D, 0.0D, 1.0D);
			var18.addVertexWithUV(var6, var14 + var13, 0.0D, 1.0D, 1.0D);
			var18.addVertexWithUV(var6, var14, 0.0D, 1.0D, 0.0D);
			var18.addVertexWithUV(var5, var14, 0.0D, 0.0D, 0.0D);
			var18.draw();
			var18.startDrawingQuads();
			var18.setColorRGBA_I(12632256, 255);
			var18.addVertexWithUV(var5, var14 + var13 - 1, 0.0D, 0.0D, 1.0D);
			var18.addVertexWithUV(var6 - 1, var14 + var13 - 1, 0.0D, 1.0D, 1.0D);
			var18.addVertexWithUV(var6 - 1, var14, 0.0D, 1.0D, 0.0D);
			var18.addVertexWithUV(var5, var14, 0.0D, 0.0D, 0.0D);
			var18.draw();
		}
		func_77215_b(par1, par2);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	protected abstract void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5);
	
	protected abstract void elementClicked(int var1, boolean var2);
	
	public void func_77207_a(int par1, int par2, int par3, int par4)
	{
		width = par1;
		height = par2;
		top = par3;
		bottom = par4;
		left = 0;
		right = par1;
	}
	
	public void func_77208_b(int par1)
	{
		amountScrolled += par1;
		bindAmountScrolled();
		initialClickY = -2.0F;
	}
	
	public int func_77209_d()
	{
		return getContentHeight() - (bottom - top - 4);
	}
	
	public int func_77210_c(int par1, int par2)
	{
		int var3 = width / 2 - 110;
		int var4 = width / 2 + 110;
		int var5 = par2 - top - field_77242_t + (int) amountScrolled - 4;
		int var6 = var5 / slotHeight;
		return par1 >= var3 && par1 <= var4 && var6 >= 0 && var5 >= 0 && var6 < getSize() ? var6 : -1;
	}
	
	protected void func_77215_b(int par1, int par2)
	{
	}
	
	protected void func_77222_a(int par1, int par2, Tessellator par3Tessellator)
	{
	}
	
	protected void func_77223_a(boolean par1, int par2)
	{
		field_77243_s = par1;
		field_77242_t = par2;
		if(!par1)
		{
			field_77242_t = 0;
		}
	}
	
	protected void func_77224_a(int par1, int par2)
	{
	}
	
	protected int getContentHeight()
	{
		return getSize() * slotHeight + field_77242_t;
	}
	
	protected int getScrollBarX()
	{
		return width / 2 + 124;
	}
	
	protected abstract int getSize();
	
	protected abstract boolean isSelected(int var1);
	
	private void overlayBackground(int par1, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		mc.renderEngine.bindTexture("/gui/background.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var6 = 32.0F;
		var5.startDrawingQuads();
		var5.setColorRGBA_I(4210752, par4);
		var5.addVertexWithUV(0.0D, par2, 0.0D, 0.0D, par2 / var6);
		var5.addVertexWithUV(width, par2, 0.0D, width / var6, par2 / var6);
		var5.setColorRGBA_I(4210752, par3);
		var5.addVertexWithUV(width, par1, 0.0D, width / var6, par1 / var6);
		var5.addVertexWithUV(0.0D, par1, 0.0D, 0.0D, par1 / var6);
		var5.draw();
	}
	
	public void registerScrollButtons(List par1, int par2, int p_77220_3_)
	{
		scrollUpButtonID = par2;
		scrollDownButtonID = p_77220_3_;
	}
	
	public void setShowSelectionBox(boolean par1)
	{
		showSelectionBox = par1;
	}
}
