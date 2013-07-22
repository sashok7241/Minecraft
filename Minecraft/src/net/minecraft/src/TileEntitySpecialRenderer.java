package net.minecraft.src;

public abstract class TileEntitySpecialRenderer
{
	protected TileEntityRenderer tileEntityRenderer;
	
	protected void func_110628_a(ResourceLocation par1ResourceLocation)
	{
		TextureManager var2 = tileEntityRenderer.renderEngine;
		if(var2 != null)
		{
			var2.func_110577_a(par1ResourceLocation);
		}
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
