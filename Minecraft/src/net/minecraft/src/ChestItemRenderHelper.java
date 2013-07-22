package net.minecraft.src;

public class ChestItemRenderHelper
{
	public static ChestItemRenderHelper instance = new ChestItemRenderHelper();
	private TileEntityChest theChest = new TileEntityChest(0);
	private TileEntityChest field_142033_c = new TileEntityChest(1);
	private TileEntityEnderChest theEnderChest = new TileEntityEnderChest();
	
	public void renderChest(Block par1Block, int par2, float par3)
	{
		if(par1Block.blockID == Block.enderChest.blockID)
		{
			TileEntityRenderer.instance.renderTileEntityAt(theEnderChest, 0.0D, 0.0D, 0.0D, 0.0F);
		} else if(par1Block.blockID == Block.chestTrapped.blockID)
		{
			TileEntityRenderer.instance.renderTileEntityAt(field_142033_c, 0.0D, 0.0D, 0.0D, 0.0F);
		} else
		{
			TileEntityRenderer.instance.renderTileEntityAt(theChest, 0.0D, 0.0D, 0.0D, 0.0F);
		}
	}
}
