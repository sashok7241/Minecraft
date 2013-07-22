package net.minecraft.src;


public class Gui
{
	public static final ResourceLocation field_110325_k = new ResourceLocation("textures/gui/options_background.png");
	public static final ResourceLocation field_110323_l = new ResourceLocation("textures/gui/container/stats_icons.png");
	public static final ResourceLocation field_110324_m = new ResourceLocation("textures/gui/icons.png");
	protected float zLevel;
	
	public void drawCenteredString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
	{
		par1FontRenderer.drawStringWithShadow(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5);
	}
	
	protected void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		float var7 = (par5 >> 24 & 255) / 255.0F;
		float var8 = (par5 >> 16 & 255) / 255.0F;
		float var9 = (par5 >> 8 & 255) / 255.0F;
		float var10 = (par5 & 255) / 255.0F;
		float var11 = (par6 >> 24 & 255) / 255.0F;
		float var12 = (par6 >> 16 & 255) / 255.0F;
		float var13 = (par6 >> 8 & 255) / 255.0F;
		float var14 = (par6 & 255) / 255.0F;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Tessellator var15 = Tessellator.instance;
		var15.startDrawingQuads();
		var15.setColorRGBA_F(var8, var9, var10, var7);
		var15.addVertex(par3, par2, zLevel);
		var15.addVertex(par1, par2, zLevel);
		var15.setColorRGBA_F(var12, var13, var14, var11);
		var15.addVertex(par1, par4, zLevel);
		var15.addVertex(par3, par4, zLevel);
		var15.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	protected void drawHorizontalLine(int par1, int par2, int par3, int par4)
	{
		if(par2 < par1)
		{
			int var5 = par1;
			par1 = par2;
			par2 = var5;
		}
		drawRect(par1, par3, par2 + 1, par3 + 1, par4);
	}
	
	public void drawString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
	{
		par1FontRenderer.drawStringWithShadow(par2Str, par3, par4, par5);
	}
	
	public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		float var7 = 0.00390625F;
		float var8 = 0.00390625F;
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(par1 + 0, par2 + par6, zLevel, (par3 + 0) * var7, (par4 + par6) * var8);
		var9.addVertexWithUV(par1 + par5, par2 + par6, zLevel, (par3 + par5) * var7, (par4 + par6) * var8);
		var9.addVertexWithUV(par1 + par5, par2 + 0, zLevel, (par3 + par5) * var7, (par4 + 0) * var8);
		var9.addVertexWithUV(par1 + 0, par2 + 0, zLevel, (par3 + 0) * var7, (par4 + 0) * var8);
		var9.draw();
	}
	
	public void drawTexturedModelRectFromIcon(int par1, int par2, Icon par3Icon, int par4, int par5)
	{
		Tessellator var6 = Tessellator.instance;
		var6.startDrawingQuads();
		var6.addVertexWithUV(par1 + 0, par2 + par5, zLevel, par3Icon.getMinU(), par3Icon.getMaxV());
		var6.addVertexWithUV(par1 + par4, par2 + par5, zLevel, par3Icon.getMaxU(), par3Icon.getMaxV());
		var6.addVertexWithUV(par1 + par4, par2 + 0, zLevel, par3Icon.getMaxU(), par3Icon.getMinV());
		var6.addVertexWithUV(par1 + 0, par2 + 0, zLevel, par3Icon.getMinU(), par3Icon.getMinV());
		var6.draw();
	}
	
	protected void drawVerticalLine(int par1, int par2, int par3, int par4)
	{
		if(par3 < par2)
		{
			int var5 = par2;
			par2 = par3;
			par3 = var5;
		}
		drawRect(par1, par2 + 1, par1 + 1, par3, par4);
	}
	
	public static void drawRect(int par0, int par1, int par2, int par3, int par4)
	{
		int var5;
		if(par0 < par2)
		{
			var5 = par0;
			par0 = par2;
			par2 = var5;
		}
		if(par1 < par3)
		{
			var5 = par1;
			par1 = par3;
			par3 = var5;
		}
		float var10 = (par4 >> 24 & 255) / 255.0F;
		float var6 = (par4 >> 16 & 255) / 255.0F;
		float var7 = (par4 >> 8 & 255) / 255.0F;
		float var8 = (par4 & 255) / 255.0F;
		Tessellator var9 = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(var6, var7, var8, var10);
		var9.startDrawingQuads();
		var9.addVertex(par0, par3, 0.0D);
		var9.addVertex(par2, par3, 0.0D);
		var9.addVertex(par2, par1, 0.0D);
		var9.addVertex(par0, par1, 0.0D);
		var9.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
}
