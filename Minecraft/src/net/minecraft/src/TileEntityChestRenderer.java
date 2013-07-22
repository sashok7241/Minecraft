package net.minecraft.src;

import java.util.Calendar;

public class TileEntityChestRenderer extends TileEntitySpecialRenderer
{
	private ModelChest chestModel = new ModelChest();
	private ModelChest largeChestModel = new ModelLargeChest();
	private boolean isChristmas;
	
	public TileEntityChestRenderer()
	{
		Calendar var1 = Calendar.getInstance();
		if(var1.get(2) + 1 == 12 && var1.get(5) >= 24 && var1.get(5) <= 26)
		{
			isChristmas = true;
		}
	}
	
	@Override public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		renderTileEntityChestAt((TileEntityChest) par1TileEntity, par2, par4, par6, par8);
	}
	
	public void renderTileEntityChestAt(TileEntityChest par1TileEntityChest, double par2, double par4, double par6, float par8)
	{
		int var9;
		if(!par1TileEntityChest.hasWorldObj())
		{
			var9 = 0;
		} else
		{
			Block var10 = par1TileEntityChest.getBlockType();
			var9 = par1TileEntityChest.getBlockMetadata();
			if(var10 instanceof BlockChest && var9 == 0)
			{
				((BlockChest) var10).unifyAdjacentChests(par1TileEntityChest.getWorldObj(), par1TileEntityChest.xCoord, par1TileEntityChest.yCoord, par1TileEntityChest.zCoord);
				var9 = par1TileEntityChest.getBlockMetadata();
			}
			par1TileEntityChest.checkForAdjacentChests();
		}
		if(par1TileEntityChest.adjacentChestZNeg == null && par1TileEntityChest.adjacentChestXNeg == null)
		{
			ModelChest var14;
			if(par1TileEntityChest.adjacentChestXPos == null && par1TileEntityChest.adjacentChestZPosition == null)
			{
				var14 = chestModel;
				if(par1TileEntityChest.func_98041_l() == 1)
				{
					bindTextureByName("/item/chests/trap_small.png");
				} else if(isChristmas)
				{
					bindTextureByName("/item/xmaschest.png");
				} else
				{
					bindTextureByName("/item/chest.png");
				}
			} else
			{
				var14 = largeChestModel;
				if(par1TileEntityChest.func_98041_l() == 1)
				{
					bindTextureByName("/item/chests/trap_large.png");
				} else if(isChristmas)
				{
					bindTextureByName("/item/largexmaschest.png");
				} else
				{
					bindTextureByName("/item/largechest.png");
				}
			}
			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float) par2, (float) par4 + 1.0F, (float) par6 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			short var11 = 0;
			if(var9 == 2)
			{
				var11 = 180;
			}
			if(var9 == 3)
			{
				var11 = 0;
			}
			if(var9 == 4)
			{
				var11 = 90;
			}
			if(var9 == 5)
			{
				var11 = -90;
			}
			if(var9 == 2 && par1TileEntityChest.adjacentChestXPos != null)
			{
				GL11.glTranslatef(1.0F, 0.0F, 0.0F);
			}
			if(var9 == 5 && par1TileEntityChest.adjacentChestZPosition != null)
			{
				GL11.glTranslatef(0.0F, 0.0F, -1.0F);
			}
			GL11.glRotatef((float) var11, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			float var12 = par1TileEntityChest.prevLidAngle + (par1TileEntityChest.lidAngle - par1TileEntityChest.prevLidAngle) * par8;
			float var13;
			if(par1TileEntityChest.adjacentChestZNeg != null)
			{
				var13 = par1TileEntityChest.adjacentChestZNeg.prevLidAngle + (par1TileEntityChest.adjacentChestZNeg.lidAngle - par1TileEntityChest.adjacentChestZNeg.prevLidAngle) * par8;
				if(var13 > var12)
				{
					var12 = var13;
				}
			}
			if(par1TileEntityChest.adjacentChestXNeg != null)
			{
				var13 = par1TileEntityChest.adjacentChestXNeg.prevLidAngle + (par1TileEntityChest.adjacentChestXNeg.lidAngle - par1TileEntityChest.adjacentChestXNeg.prevLidAngle) * par8;
				if(var13 > var12)
				{
					var12 = var13;
				}
			}
			var12 = 1.0F - var12;
			var12 = 1.0F - var12 * var12 * var12;
			var14.chestLid.rotateAngleX = -(var12 * (float) Math.PI / 2.0F);
			var14.renderAll();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
