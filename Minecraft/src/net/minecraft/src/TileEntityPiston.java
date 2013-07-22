package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TileEntityPiston extends TileEntity
{
	private int storedBlockID;
	private int storedMetadata;
	private int storedOrientation;
	private boolean extending;
	private boolean shouldHeadBeRendered;
	private float progress;
	private float lastProgress;
	private List pushedObjects = new ArrayList();
	
	public TileEntityPiston()
	{
	}
	
	public TileEntityPiston(int par1, int par2, int par3, boolean par4, boolean par5)
	{
		storedBlockID = par1;
		storedMetadata = par2;
		storedOrientation = par3;
		extending = par4;
		shouldHeadBeRendered = par5;
	}
	
	public void clearPistonTileEntity()
	{
		if(lastProgress < 1.0F && worldObj != null)
		{
			lastProgress = progress = 1.0F;
			worldObj.removeBlockTileEntity(xCoord, yCoord, zCoord);
			invalidate();
			if(worldObj.getBlockId(xCoord, yCoord, zCoord) == Block.pistonMoving.blockID)
			{
				worldObj.setBlock(xCoord, yCoord, zCoord, storedBlockID, storedMetadata, 3);
				worldObj.notifyBlockOfNeighborChange(xCoord, yCoord, zCoord, storedBlockID);
			}
		}
	}
	
	@Override public int getBlockMetadata()
	{
		return storedMetadata;
	}
	
	public float getOffsetX(float par1)
	{
		return extending ? (getProgress(par1) - 1.0F) * Facing.offsetsXForSide[storedOrientation] : (1.0F - getProgress(par1)) * Facing.offsetsXForSide[storedOrientation];
	}
	
	public float getOffsetY(float par1)
	{
		return extending ? (getProgress(par1) - 1.0F) * Facing.offsetsYForSide[storedOrientation] : (1.0F - getProgress(par1)) * Facing.offsetsYForSide[storedOrientation];
	}
	
	public float getOffsetZ(float par1)
	{
		return extending ? (getProgress(par1) - 1.0F) * Facing.offsetsZForSide[storedOrientation] : (1.0F - getProgress(par1)) * Facing.offsetsZForSide[storedOrientation];
	}
	
	public int getPistonOrientation()
	{
		return storedOrientation;
	}
	
	public float getProgress(float par1)
	{
		if(par1 > 1.0F)
		{
			par1 = 1.0F;
		}
		return lastProgress + (progress - lastProgress) * par1;
	}
	
	public int getStoredBlockID()
	{
		return storedBlockID;
	}
	
	public boolean isExtending()
	{
		return extending;
	}
	
	@Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		storedBlockID = par1NBTTagCompound.getInteger("blockId");
		storedMetadata = par1NBTTagCompound.getInteger("blockData");
		storedOrientation = par1NBTTagCompound.getInteger("facing");
		lastProgress = progress = par1NBTTagCompound.getFloat("progress");
		extending = par1NBTTagCompound.getBoolean("extending");
	}
	
	public boolean shouldRenderHead()
	{
		return shouldHeadBeRendered;
	}
	
	@Override public void updateEntity()
	{
		lastProgress = progress;
		if(lastProgress >= 1.0F)
		{
			updatePushedObjects(1.0F, 0.25F);
			worldObj.removeBlockTileEntity(xCoord, yCoord, zCoord);
			invalidate();
			if(worldObj.getBlockId(xCoord, yCoord, zCoord) == Block.pistonMoving.blockID)
			{
				worldObj.setBlock(xCoord, yCoord, zCoord, storedBlockID, storedMetadata, 3);
				worldObj.notifyBlockOfNeighborChange(xCoord, yCoord, zCoord, storedBlockID);
			}
		} else
		{
			progress += 0.5F;
			if(progress >= 1.0F)
			{
				progress = 1.0F;
			}
			if(extending)
			{
				updatePushedObjects(progress, progress - lastProgress + 0.0625F);
			}
		}
	}
	
	private void updatePushedObjects(float par1, float par2)
	{
		if(extending)
		{
			par1 = 1.0F - par1;
		} else
		{
			--par1;
		}
		AxisAlignedBB var3 = Block.pistonMoving.getAxisAlignedBB(worldObj, xCoord, yCoord, zCoord, storedBlockID, par1, storedOrientation);
		if(var3 != null)
		{
			List var4 = worldObj.getEntitiesWithinAABBExcludingEntity((Entity) null, var3);
			if(!var4.isEmpty())
			{
				pushedObjects.addAll(var4);
				Iterator var5 = pushedObjects.iterator();
				while(var5.hasNext())
				{
					Entity var6 = (Entity) var5.next();
					var6.moveEntity(par2 * Facing.offsetsXForSide[storedOrientation], par2 * Facing.offsetsYForSide[storedOrientation], par2 * Facing.offsetsZForSide[storedOrientation]);
				}
				pushedObjects.clear();
			}
		}
	}
	
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("blockId", storedBlockID);
		par1NBTTagCompound.setInteger("blockData", storedMetadata);
		par1NBTTagCompound.setInteger("facing", storedOrientation);
		par1NBTTagCompound.setFloat("progress", lastProgress);
		par1NBTTagCompound.setBoolean("extending", extending);
	}
}
