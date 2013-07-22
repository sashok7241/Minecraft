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
	
	public TileEntityPiston(int p_i4028_1_, int p_i4028_2_, int p_i4028_3_, boolean p_i4028_4_, boolean p_i4028_5_)
	{
		storedBlockID = p_i4028_1_;
		storedMetadata = p_i4028_2_;
		storedOrientation = p_i4028_3_;
		extending = p_i4028_4_;
		shouldHeadBeRendered = p_i4028_5_;
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
	
	public float getProgress(float p_70333_1_)
	{
		if(p_70333_1_ > 1.0F)
		{
			p_70333_1_ = 1.0F;
		}
		return lastProgress + (progress - lastProgress) * p_70333_1_;
	}
	
	public int getStoredBlockID()
	{
		return storedBlockID;
	}
	
	public boolean isExtending()
	{
		return extending;
	}
	
	@Override public void readFromNBT(NBTTagCompound p_70307_1_)
	{
		super.readFromNBT(p_70307_1_);
		storedBlockID = p_70307_1_.getInteger("blockId");
		storedMetadata = p_70307_1_.getInteger("blockData");
		storedOrientation = p_70307_1_.getInteger("facing");
		lastProgress = progress = p_70307_1_.getFloat("progress");
		extending = p_70307_1_.getBoolean("extending");
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
	
	private void updatePushedObjects(float p_70335_1_, float p_70335_2_)
	{
		if(extending)
		{
			p_70335_1_ = 1.0F - p_70335_1_;
		} else
		{
			--p_70335_1_;
		}
		AxisAlignedBB var3 = Block.pistonMoving.getAxisAlignedBB(worldObj, xCoord, yCoord, zCoord, storedBlockID, p_70335_1_, storedOrientation);
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
					var6.moveEntity(p_70335_2_ * Facing.offsetsXForSide[storedOrientation], p_70335_2_ * Facing.offsetsYForSide[storedOrientation], p_70335_2_ * Facing.offsetsZForSide[storedOrientation]);
				}
				pushedObjects.clear();
			}
		}
	}
	
	@Override public void writeToNBT(NBTTagCompound p_70310_1_)
	{
		super.writeToNBT(p_70310_1_);
		p_70310_1_.setInteger("blockId", storedBlockID);
		p_70310_1_.setInteger("blockData", storedMetadata);
		p_70310_1_.setInteger("facing", storedOrientation);
		p_70310_1_.setFloat("progress", lastProgress);
		p_70310_1_.setBoolean("extending", extending);
	}
}
