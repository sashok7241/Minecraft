package net.minecraft.src;

import net.minecraft.client.Minecraft;

public abstract class GuiScreenSelectLocation
{
	private final Minecraft field_104092_f;
	private int field_104093_g;
	private int field_104105_h;
	protected int field_104098_a;
	protected int field_104096_b;
	private int field_104106_i;
	private int field_104103_j;
	protected final int field_104097_c;
	private int field_104104_k;
	private int field_104101_l;
	protected int field_104094_d;
	protected int field_104095_e;
	private float field_104102_m = -2.0F;
	private float field_104099_n;
	private float field_104100_o;
	private int field_104111_p = -1;
	private long field_104110_q;
	private boolean field_104109_r = true;
	private boolean field_104108_s;
	private int field_104107_t;
	
	public GuiScreenSelectLocation(Minecraft par1Minecraft, int par2, int par3, int par4, int par5, int par6)
	{
		field_104092_f = par1Minecraft;
		field_104093_g = par2;
		field_104105_h = par3;
		field_104098_a = par4;
		field_104096_b = par5;
		field_104097_c = par6;
		field_104103_j = 0;
		field_104106_i = par2;
	}
	
	public void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == field_104104_k)
			{
				field_104100_o -= field_104097_c * 2 / 3;
				field_104102_m = -2.0F;
				func_104091_h();
			} else if(par1GuiButton.id == field_104101_l)
			{
				field_104100_o += field_104097_c * 2 / 3;
				field_104102_m = -2.0F;
				func_104091_h();
			}
		}
	}
	
	public void drawScreen(int par1, int par2, float par3)
	{
		field_104094_d = par1;
		field_104095_e = par2;
		func_130004_c();
		int var4 = getSize();
		int var5 = func_104090_g();
		int var6 = var5 + 6;
		int var9;
		int var10;
		int var11;
		int var13;
		int var19;
		if(Mouse.isButtonDown(0))
		{
			if(field_104102_m == -1.0F)
			{
				boolean var7 = true;
				if(par2 >= field_104098_a && par2 <= field_104096_b)
				{
					int var8 = field_104093_g / 2 - 110;
					var9 = field_104093_g / 2 + 110;
					var10 = par2 - field_104098_a - field_104107_t + (int) field_104100_o - 4;
					var11 = var10 / field_104097_c;
					if(par1 >= var8 && par1 <= var9 && var11 >= 0 && var10 >= 0 && var11 < var4)
					{
						boolean var12 = var11 == field_104111_p && Minecraft.getSystemTime() - field_104110_q < 250L;
						elementClicked(var11, var12);
						field_104111_p = var11;
						field_104110_q = Minecraft.getSystemTime();
					} else if(par1 >= var8 && par1 <= var9 && var10 < 0)
					{
						func_104089_a(par1 - var8, par2 - field_104098_a + (int) field_104100_o - 4);
						var7 = false;
					}
					if(par1 >= var5 && par1 <= var6)
					{
						field_104099_n = -1.0F;
						var19 = func_104085_d();
						if(var19 < 1)
						{
							var19 = 1;
						}
						var13 = (int) ((float) ((field_104096_b - field_104098_a) * (field_104096_b - field_104098_a)) / (float) func_130003_b());
						if(var13 < 32)
						{
							var13 = 32;
						}
						if(var13 > field_104096_b - field_104098_a - 8)
						{
							var13 = field_104096_b - field_104098_a - 8;
						}
						field_104099_n /= (float) (field_104096_b - field_104098_a - var13) / (float) var19;
					} else
					{
						field_104099_n = 1.0F;
					}
					if(var7)
					{
						field_104102_m = par2;
					} else
					{
						field_104102_m = -2.0F;
					}
				} else
				{
					field_104102_m = -2.0F;
				}
			} else if(field_104102_m >= 0.0F)
			{
				field_104100_o -= (par2 - field_104102_m) * field_104099_n;
				field_104102_m = par2;
			}
		} else
		{
			while(!field_104092_f.gameSettings.touchscreen && Mouse.next())
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
					field_104100_o += var16 * field_104097_c / 2;
				}
			}
			field_104102_m = -1.0F;
		}
		func_104091_h();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator var18 = Tessellator.instance;
		field_104092_f.func_110434_K().func_110577_a(Gui.field_110325_k);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var17 = 32.0F;
		var18.startDrawingQuads();
		var18.setColorOpaque_I(2105376);
		var18.addVertexWithUV(field_104103_j, field_104096_b, 0.0D, field_104103_j / var17, (field_104096_b + (int) field_104100_o) / var17);
		var18.addVertexWithUV(field_104106_i, field_104096_b, 0.0D, field_104106_i / var17, (field_104096_b + (int) field_104100_o) / var17);
		var18.addVertexWithUV(field_104106_i, field_104098_a, 0.0D, field_104106_i / var17, (field_104098_a + (int) field_104100_o) / var17);
		var18.addVertexWithUV(field_104103_j, field_104098_a, 0.0D, field_104103_j / var17, (field_104098_a + (int) field_104100_o) / var17);
		var18.draw();
		var9 = field_104093_g / 2 - 92 - 16;
		var10 = field_104098_a + 4 - (int) field_104100_o;
		if(field_104108_s)
		{
			func_104088_a(var9, var10, var18);
		}
		int var14;
		for(var11 = 0; var11 < var4; ++var11)
		{
			var19 = var10 + var11 * field_104097_c + field_104107_t;
			var13 = field_104097_c - 4;
			if(var19 <= field_104096_b && var19 + var13 >= field_104098_a)
			{
				int var15;
				if(field_104109_r && func_104086_b(var11))
				{
					var14 = field_104093_g / 2 - 110;
					var15 = field_104093_g / 2 + 110;
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					var18.startDrawingQuads();
					var18.setColorOpaque_I(0);
					var18.addVertexWithUV(var14, var19 + var13 + 2, 0.0D, 0.0D, 1.0D);
					var18.addVertexWithUV(var15, var19 + var13 + 2, 0.0D, 1.0D, 1.0D);
					var18.addVertexWithUV(var15, var19 - 2, 0.0D, 1.0D, 0.0D);
					var18.addVertexWithUV(var14, var19 - 2, 0.0D, 0.0D, 0.0D);
					var18.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}
				if(field_104109_r && isSelected(var11))
				{
					var14 = field_104093_g / 2 - 110;
					var15 = field_104093_g / 2 + 110;
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
		func_104083_b(0, field_104098_a, 255, 255);
		func_104083_b(field_104096_b, field_104105_h, 255, 255);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		var18.startDrawingQuads();
		var18.setColorRGBA_I(0, 0);
		var18.addVertexWithUV(field_104103_j, field_104098_a + var20, 0.0D, 0.0D, 1.0D);
		var18.addVertexWithUV(field_104106_i, field_104098_a + var20, 0.0D, 1.0D, 1.0D);
		var18.setColorRGBA_I(0, 255);
		var18.addVertexWithUV(field_104106_i, field_104098_a, 0.0D, 1.0D, 0.0D);
		var18.addVertexWithUV(field_104103_j, field_104098_a, 0.0D, 0.0D, 0.0D);
		var18.draw();
		var18.startDrawingQuads();
		var18.setColorRGBA_I(0, 255);
		var18.addVertexWithUV(field_104103_j, field_104096_b, 0.0D, 0.0D, 1.0D);
		var18.addVertexWithUV(field_104106_i, field_104096_b, 0.0D, 1.0D, 1.0D);
		var18.setColorRGBA_I(0, 0);
		var18.addVertexWithUV(field_104106_i, field_104096_b - var20, 0.0D, 1.0D, 0.0D);
		var18.addVertexWithUV(field_104103_j, field_104096_b - var20, 0.0D, 0.0D, 0.0D);
		var18.draw();
		var19 = func_104085_d();
		if(var19 > 0)
		{
			var13 = (field_104096_b - field_104098_a) * (field_104096_b - field_104098_a) / func_130003_b();
			if(var13 < 32)
			{
				var13 = 32;
			}
			if(var13 > field_104096_b - field_104098_a - 8)
			{
				var13 = field_104096_b - field_104098_a - 8;
			}
			var14 = (int) field_104100_o * (field_104096_b - field_104098_a - var13) / var19 + field_104098_a;
			if(var14 < field_104098_a)
			{
				var14 = field_104098_a;
			}
			var18.startDrawingQuads();
			var18.setColorRGBA_I(0, 255);
			var18.addVertexWithUV(var5, field_104096_b, 0.0D, 0.0D, 1.0D);
			var18.addVertexWithUV(var6, field_104096_b, 0.0D, 1.0D, 1.0D);
			var18.addVertexWithUV(var6, field_104098_a, 0.0D, 1.0D, 0.0D);
			var18.addVertexWithUV(var5, field_104098_a, 0.0D, 0.0D, 0.0D);
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
		func_104087_b(par1, par2);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	protected abstract void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5);
	
	protected abstract void elementClicked(int var1, boolean var2);
	
	private void func_104083_b(int par1, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		field_104092_f.func_110434_K().func_110577_a(Gui.field_110325_k);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var6 = 32.0F;
		var5.startDrawingQuads();
		var5.setColorRGBA_I(4210752, par4);
		var5.addVertexWithUV(0.0D, par2, 0.0D, 0.0D, par2 / var6);
		var5.addVertexWithUV(field_104093_g, par2, 0.0D, field_104093_g / var6, par2 / var6);
		var5.setColorRGBA_I(4210752, par3);
		var5.addVertexWithUV(field_104093_g, par1, 0.0D, field_104093_g / var6, par1 / var6);
		var5.addVertexWithUV(0.0D, par1, 0.0D, 0.0D, par1 / var6);
		var5.draw();
	}
	
	public void func_104084_a(int par1, int par2, int par3, int par4)
	{
		field_104093_g = par1;
		field_104105_h = par2;
		field_104098_a = par3;
		field_104096_b = par4;
		field_104103_j = 0;
		field_104106_i = par1;
	}
	
	public int func_104085_d()
	{
		return func_130003_b() - (field_104096_b - field_104098_a - 4);
	}
	
	protected abstract boolean func_104086_b(int var1);
	
	protected void func_104087_b(int par1, int par2)
	{
	}
	
	protected void func_104088_a(int par1, int par2, Tessellator par3Tessellator)
	{
	}
	
	protected void func_104089_a(int par1, int par2)
	{
	}
	
	protected int func_104090_g()
	{
		return field_104093_g / 2 + 124;
	}
	
	private void func_104091_h()
	{
		int var1 = func_104085_d();
		if(var1 < 0)
		{
			var1 /= 2;
		}
		if(field_104100_o < 0.0F)
		{
			field_104100_o = 0.0F;
		}
		if(field_104100_o > var1)
		{
			field_104100_o = var1;
		}
	}
	
	protected int func_130003_b()
	{
		return getSize() * field_104097_c + field_104107_t;
	}
	
	protected abstract void func_130004_c();
	
	protected abstract int getSize();
	
	protected abstract boolean isSelected(int var1);
}
