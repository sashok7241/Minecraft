package net.minecraft.src;

import net.minecraft.client.Minecraft;

public abstract class SelectionListBase
{
	private final Minecraft field_96622_a;
	private final int field_96619_e;
	private final int field_96616_f;
	private final int field_96617_g;
	private final int field_96627_h;
	protected final int field_96620_b;
	protected int field_96621_c;
	protected int field_96618_d;
	private float field_96628_i = -2.0F;
	private float field_96625_j;
	private float field_96626_k;
	private int field_96623_l = -1;
	private long field_96624_m = 0L;
	
	public SelectionListBase(Minecraft p_i10012_1_, int p_i10012_2_, int p_i10012_3_, int p_i10012_4_, int p_i10012_5_, int p_i10012_6_)
	{
		field_96622_a = p_i10012_1_;
		field_96616_f = p_i10012_3_;
		field_96627_h = p_i10012_3_ + p_i10012_5_;
		field_96620_b = p_i10012_6_;
		field_96619_e = p_i10012_2_;
		field_96617_g = p_i10012_2_ + p_i10012_4_;
	}
	
	protected int func_96606_e()
	{
		return field_96617_g - 8;
	}
	
	public int func_96607_d()
	{
		return func_96613_b() - (field_96627_h - field_96616_f - 4);
	}
	
	protected abstract int func_96608_a();
	
	protected abstract boolean func_96609_a(int var1);
	
	protected abstract void func_96610_a(int var1, int var2, int var3, int var4, Tessellator var5);
	
	protected abstract void func_96611_c();
	
	public void func_96612_a(int par1, int par2, float par3)
	{
		field_96621_c = par1;
		field_96618_d = par2;
		func_96611_c();
		int var4 = func_96608_a();
		int var5 = func_96606_e();
		int var6 = var5 + 6;
		int var9;
		int var10;
		int var11;
		int var13;
		int var19;
		if(Mouse.isButtonDown(0))
		{
			if(field_96628_i == -1.0F)
			{
				boolean var7 = true;
				if(par2 >= field_96616_f && par2 <= field_96627_h)
				{
					int var8 = field_96619_e + 2;
					var9 = field_96617_g - 2;
					var10 = par2 - field_96616_f + (int) field_96626_k - 4;
					var11 = var10 / field_96620_b;
					if(par1 >= var8 && par1 <= var9 && var11 >= 0 && var10 >= 0 && var11 < var4)
					{
						boolean var12 = var11 == field_96623_l && Minecraft.getSystemTime() - field_96624_m < 250L;
						func_96615_a(var11, var12);
						field_96623_l = var11;
						field_96624_m = Minecraft.getSystemTime();
					} else if(par1 >= var8 && par1 <= var9 && var10 < 0)
					{
						var7 = false;
					}
					if(par1 >= var5 && par1 <= var6)
					{
						field_96625_j = -1.0F;
						var19 = func_96607_d();
						if(var19 < 1)
						{
							var19 = 1;
						}
						var13 = (int) ((float) ((field_96627_h - field_96616_f) * (field_96627_h - field_96616_f)) / (float) func_96613_b());
						if(var13 < 32)
						{
							var13 = 32;
						}
						if(var13 > field_96627_h - field_96616_f - 8)
						{
							var13 = field_96627_h - field_96616_f - 8;
						}
						field_96625_j /= (float) (field_96627_h - field_96616_f - var13) / (float) var19;
					} else
					{
						field_96625_j = 1.0F;
					}
					if(var7)
					{
						field_96628_i = par2;
					} else
					{
						field_96628_i = -2.0F;
					}
				} else
				{
					field_96628_i = -2.0F;
				}
			} else if(field_96628_i >= 0.0F)
			{
				field_96626_k -= (par2 - field_96628_i) * field_96625_j;
				field_96628_i = par2;
			}
		} else
		{
			while(!field_96622_a.gameSettings.touchscreen && Mouse.next())
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
					field_96626_k += var16 * field_96620_b / 2;
				}
			}
			field_96628_i = -1.0F;
		}
		func_96614_f();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator var18 = Tessellator.instance;
		field_96622_a.renderEngine.bindTexture("/gui/background.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var17 = 32.0F;
		var18.startDrawingQuads();
		var18.setColorOpaque_I(2105376);
		var18.addVertexWithUV(field_96619_e, field_96627_h, 0.0D, field_96619_e / var17, (field_96627_h + (int) field_96626_k) / var17);
		var18.addVertexWithUV(field_96617_g, field_96627_h, 0.0D, field_96617_g / var17, (field_96627_h + (int) field_96626_k) / var17);
		var18.addVertexWithUV(field_96617_g, field_96616_f, 0.0D, field_96617_g / var17, (field_96616_f + (int) field_96626_k) / var17);
		var18.addVertexWithUV(field_96619_e, field_96616_f, 0.0D, field_96619_e / var17, (field_96616_f + (int) field_96626_k) / var17);
		var18.draw();
		var9 = field_96619_e + 2;
		var10 = field_96616_f + 4 - (int) field_96626_k;
		int var14;
		for(var11 = 0; var11 < var4; ++var11)
		{
			var19 = var10 + var11 * field_96620_b;
			var13 = field_96620_b - 4;
			if(var19 + field_96620_b <= field_96627_h && var19 - 4 >= field_96616_f)
			{
				if(func_96609_a(var11))
				{
					var14 = field_96619_e + 2;
					int var15 = field_96617_g - 2;
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
				func_96610_a(var11, var9, var19, var13, var18);
			}
		}
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		byte var20 = 4;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		var18.startDrawingQuads();
		var18.setColorRGBA_I(0, 0);
		var18.addVertexWithUV(field_96619_e, field_96616_f + var20, 0.0D, 0.0D, 1.0D);
		var18.addVertexWithUV(field_96617_g, field_96616_f + var20, 0.0D, 1.0D, 1.0D);
		var18.setColorRGBA_I(0, 255);
		var18.addVertexWithUV(field_96617_g, field_96616_f, 0.0D, 1.0D, 0.0D);
		var18.addVertexWithUV(field_96619_e, field_96616_f, 0.0D, 0.0D, 0.0D);
		var18.draw();
		var18.startDrawingQuads();
		var18.setColorRGBA_I(0, 255);
		var18.addVertexWithUV(field_96619_e, field_96627_h, 0.0D, 0.0D, 1.0D);
		var18.addVertexWithUV(field_96617_g, field_96627_h, 0.0D, 1.0D, 1.0D);
		var18.setColorRGBA_I(0, 0);
		var18.addVertexWithUV(field_96617_g, field_96627_h - var20, 0.0D, 1.0D, 0.0D);
		var18.addVertexWithUV(field_96619_e, field_96627_h - var20, 0.0D, 0.0D, 0.0D);
		var18.draw();
		var19 = func_96607_d();
		if(var19 > 0)
		{
			var13 = (field_96627_h - field_96616_f) * (field_96627_h - field_96616_f) / func_96613_b();
			if(var13 < 32)
			{
				var13 = 32;
			}
			if(var13 > field_96627_h - field_96616_f - 8)
			{
				var13 = field_96627_h - field_96616_f - 8;
			}
			var14 = (int) field_96626_k * (field_96627_h - field_96616_f - var13) / var19 + field_96616_f;
			if(var14 < field_96616_f)
			{
				var14 = field_96616_f;
			}
			var18.startDrawingQuads();
			var18.setColorRGBA_I(0, 255);
			var18.addVertexWithUV(var5, field_96627_h, 0.0D, 0.0D, 1.0D);
			var18.addVertexWithUV(var6, field_96627_h, 0.0D, 1.0D, 1.0D);
			var18.addVertexWithUV(var6, field_96616_f, 0.0D, 1.0D, 0.0D);
			var18.addVertexWithUV(var5, field_96616_f, 0.0D, 0.0D, 0.0D);
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
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	protected int func_96613_b()
	{
		return func_96608_a() * field_96620_b;
	}
	
	private void func_96614_f()
	{
		int var1 = func_96607_d();
		if(var1 < 0)
		{
			var1 = 0;
		}
		if(field_96626_k < 0.0F)
		{
			field_96626_k = 0.0F;
		}
		if(field_96626_k > var1)
		{
			field_96626_k = var1;
		}
	}
	
	protected abstract void func_96615_a(int var1, boolean var2);
}
