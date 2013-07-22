package net.minecraft.src;


public abstract class TileEntitySpecialRenderer
{
	protected TileEntityRenderer tileEntityRenderer;
	
	protected void bindTextureByName(String p_76897_1_)
	{
		RenderEngine var2 = tileEntityRenderer.renderEngine;
		if(var2 != null)
		{
			var2.bindTexture(p_76897_1_);
		}
	}
	
	protected void bindTextureByURL(String p_82392_1_, String p_82392_2_)
	{
		RenderEngine var3 = tileEntityRenderer.renderEngine;
		if(var3 != null)
		{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, var3.getTextureForDownloadableImage(p_82392_1_, p_82392_2_));
		}
		var3.resetBoundTexture();
	}
	
	public FontRenderer getFontRenderer()
	{
		return tileEntityRenderer.getFontRenderer();
	}
	
	public void onWorldChange(World par1World)
	{
	}
	
	public abstract void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8);
	
	public void setTileEntityRenderer(TileEntityRenderer par1TileEntityRenderer)
	{
		tileEntityRenderer = par1TileEntityRenderer;
	}
}
