package net.minecraft.src;

public class ChestItemRenderHelper
{
	public static ChestItemRenderHelper instance = new ChestItemRenderHelper();
	private TileEntityChest theChest = new TileEntityChest();
	private TileEntityEnderChest theEnderChest = new TileEntityEnderChest();
	
	public void renderChest(Block par1Block, int par2, float par3)
	{
		if(par1Block.blockID == Block.enderChest.blockID)
		{
			TileEntityRenderer.instance.renderTileEntityAt(theEnderChest, 0.0D, 0.0D, 0.0D, 0.0F);
		} else
		{
			TileEntityRenderer.instance.renderTileEntityAt(theChest, 0.0D, 0.0D, 0.0D, 0.0F);
		}
	}
}
