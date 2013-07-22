package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class TileEntityRendererPiston extends TileEntitySpecialRenderer
{
	private RenderBlocks blockRenderer;
	
	@Override public void onWorldChange(World par1World)
	{
		blockRenderer = new RenderBlocks(par1World);
	}
	
	public void renderPiston(TileEntityPiston par1TileEntityPiston, double par2, double par4, double par6, float par8)
	{
		Block var9 = Block.blocksList[par1TileEntityPiston.getStoredBlockID()];
		if(var9 != null && par1TileEntityPiston.getProgress(par8) < 1.0F)
		{
			Tessellator var10 = Tessellator.instance;
			bindTextureByName("/terrain.png");
			RenderHelper.disableStandardItemLighting();
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			if(Minecraft.isAmbientOcclusionEnabled())
			{
				GL11.glShadeModel(GL11.GL_SMOOTH);
			} else
			{
				GL11.glShadeModel(GL11.GL_FLAT);
			}
			var10.startDrawingQuads();
			var10.setTranslation((float) par2 - par1TileEntityPiston.xCoord + par1TileEntityPiston.getOffsetX(par8), (float) par4 - par1TileEntityPiston.yCoord + par1TileEntityPiston.getOffsetY(par8), (float) par6 - par1TileEntityPiston.zCoord + par1TileEntityPiston.getOffsetZ(par8));
			var10.setColorOpaque(1, 1, 1);
			if(var9 == Block.pistonExtension && par1TileEntityPiston.getProgress(par8) < 0.5F)
			{
				blockRenderer.renderPistonExtensionAllFaces(var9, par1TileEntityPiston.xCoord, par1TileEntityPiston.yCoord, par1TileEntityPiston.zCoord, false);
			} else if(par1TileEntityPiston.shouldRenderHead() && !par1TileEntityPiston.isExtending())
			{
				Block.pistonExtension.setHeadTexture(((BlockPistonBase) var9).getPistonExtensionTexture());
				blockRenderer.renderPistonExtensionAllFaces(Block.pistonExtension, par1TileEntityPiston.xCoord, par1TileEntityPiston.yCoord, par1TileEntityPiston.zCoord, par1TileEntityPiston.getProgress(par8) < 0.5F);
				Block.pistonExtension.clearHeadTexture();
				var10.setTranslation((float) par2 - par1TileEntityPiston.xCoord, (float) par4 - par1TileEntityPiston.yCoord, (float) par6 - par1TileEntityPiston.zCoord);
				blockRenderer.renderPistonBaseAllFaces(var9, par1TileEntityPiston.xCoord, par1TileEntityPiston.yCoord, par1TileEntityPiston.zCoord);
			} else
			{
				blockRenderer.renderBlockAllFaces(var9, par1TileEntityPiston.xCoord, par1TileEntityPiston.yCoord, par1TileEntityPiston.zCoord);
			}
			var10.setTranslation(0.0D, 0.0D, 0.0D);
			var10.draw();
			RenderHelper.enableStandardItemLighting();
		}
	}
	
	@Override public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		renderPiston((TileEntityPiston) par1TileEntity, par2, par4, par6, par8);
	}
}
