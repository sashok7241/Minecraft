package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class BlockBaseRailLogic
{
	private World logicWorld;
	private int railX;
	private int railY;
	private int railZ;
	private final boolean isStraightRail;
	private List railChunkPosition;
	final BlockRailBase theRail;
	
	public BlockBaseRailLogic(BlockRailBase p_i9010_1_, World p_i9010_2_, int p_i9010_3_, int p_i9010_4_, int p_i9010_5_)
	{
		theRail = p_i9010_1_;
		railChunkPosition = new ArrayList();
		logicWorld = p_i9010_2_;
		railX = p_i9010_3_;
		railY = p_i9010_4_;
		railZ = p_i9010_5_;
		int var6 = p_i9010_2_.getBlockId(p_i9010_3_, p_i9010_4_, p_i9010_5_);
		int var7 = p_i9010_2_.getBlockMetadata(p_i9010_3_, p_i9010_4_, p_i9010_5_);
		if(((BlockRailBase) Block.blocksList[var6]).isPowered)
		{
			isStraightRail = true;
			var7 &= -9;
		} else
		{
			isStraightRail = false;
		}
		setBasicRail(var7);
	}
	
	private boolean canConnectFrom(int p_94503_1_, int p_94503_2_, int p_94503_3_)
	{
		BlockBaseRailLogic var4 = getRailLogic(new ChunkPosition(p_94503_1_, p_94503_2_, p_94503_3_));
		if(var4 == null) return false;
		else
		{
			var4.refreshConnectedTracks();
			return var4.canConnectTo(this);
		}
	}
	
	private boolean canConnectTo(BlockBaseRailLogic p_94507_1_)
	{
		return isRailChunkPositionCorrect(p_94507_1_) ? true : railChunkPosition.size() == 2 ? false : railChunkPosition.isEmpty() ? true : true;
	}
	
	private void connectToNeighbor(BlockBaseRailLogic p_94506_1_)
	{
		railChunkPosition.add(new ChunkPosition(p_94506_1_.railX, p_94506_1_.railY, p_94506_1_.railZ));
		boolean var2 = isPartOfTrack(railX, railY, railZ - 1);
		boolean var3 = isPartOfTrack(railX, railY, railZ + 1);
		boolean var4 = isPartOfTrack(railX - 1, railY, railZ);
		boolean var5 = isPartOfTrack(railX + 1, railY, railZ);
		byte var6 = -1;
		if(var2 || var3)
		{
			var6 = 0;
		}
		if(var4 || var5)
		{
			var6 = 1;
		}
		if(!isStraightRail)
		{
			if(var3 && var5 && !var2 && !var4)
			{
				var6 = 6;
			}
			if(var3 && var4 && !var2 && !var5)
			{
				var6 = 7;
			}
			if(var2 && var4 && !var3 && !var5)
			{
				var6 = 8;
			}
			if(var2 && var5 && !var3 && !var4)
			{
				var6 = 9;
			}
		}
		if(var6 == 0)
		{
			if(BlockRailBase.isRailBlockAt(logicWorld, railX, railY + 1, railZ - 1))
			{
				var6 = 4;
			}
			if(BlockRailBase.isRailBlockAt(logicWorld, railX, railY + 1, railZ + 1))
			{
				var6 = 5;
			}
		}
		if(var6 == 1)
		{
			if(BlockRailBase.isRailBlockAt(logicWorld, railX + 1, railY + 1, railZ))
			{
				var6 = 2;
			}
			if(BlockRailBase.isRailBlockAt(logicWorld, railX - 1, railY + 1, railZ))
			{
				var6 = 3;
			}
		}
		if(var6 < 0)
		{
			var6 = 0;
		}
		int var7 = var6;
		if(isStraightRail)
		{
			var7 = logicWorld.getBlockMetadata(railX, railY, railZ) & 8 | var6;
		}
		logicWorld.setBlockMetadataWithNotify(railX, railY, railZ, var7, 3);
	}
	
	public void func_94511_a(boolean p_94511_1_, boolean p_94511_2_)
	{
		boolean var3 = canConnectFrom(railX, railY, railZ - 1);
		boolean var4 = canConnectFrom(railX, railY, railZ + 1);
		boolean var5 = canConnectFrom(railX - 1, railY, railZ);
		boolean var6 = canConnectFrom(railX + 1, railY, railZ);
		byte var7 = -1;
		if((var3 || var4) && !var5 && !var6)
		{
			var7 = 0;
		}
		if((var5 || var6) && !var3 && !var4)
		{
			var7 = 1;
		}
		if(!isStraightRail)
		{
			if(var4 && var6 && !var3 && !var5)
			{
				var7 = 6;
			}
			if(var4 && var5 && !var3 && !var6)
			{
				var7 = 7;
			}
			if(var3 && var5 && !var4 && !var6)
			{
				var7 = 8;
			}
			if(var3 && var6 && !var4 && !var5)
			{
				var7 = 9;
			}
		}
		if(var7 == -1)
		{
			if(var3 || var4)
			{
				var7 = 0;
			}
			if(var5 || var6)
			{
				var7 = 1;
			}
			if(!isStraightRail)
			{
				if(p_94511_1_)
				{
					if(var4 && var6)
					{
						var7 = 6;
					}
					if(var5 && var4)
					{
						var7 = 7;
					}
					if(var6 && var3)
					{
						var7 = 9;
					}
					if(var3 && var5)
					{
						var7 = 8;
					}
				} else
				{
					if(var3 && var5)
					{
						var7 = 8;
					}
					if(var6 && var3)
					{
						var7 = 9;
					}
					if(var5 && var4)
					{
						var7 = 7;
					}
					if(var4 && var6)
					{
						var7 = 6;
					}
				}
			}
		}
		if(var7 == 0)
		{
			if(BlockRailBase.isRailBlockAt(logicWorld, railX, railY + 1, railZ - 1))
			{
				var7 = 4;
			}
			if(BlockRailBase.isRailBlockAt(logicWorld, railX, railY + 1, railZ + 1))
			{
				var7 = 5;
			}
		}
		if(var7 == 1)
		{
			if(BlockRailBase.isRailBlockAt(logicWorld, railX + 1, railY + 1, railZ))
			{
				var7 = 2;
			}
			if(BlockRailBase.isRailBlockAt(logicWorld, railX - 1, railY + 1, railZ))
			{
				var7 = 3;
			}
		}
		if(var7 < 0)
		{
			var7 = 0;
		}
		setBasicRail(var7);
		int var8 = var7;
		if(isStraightRail)
		{
			var8 = logicWorld.getBlockMetadata(railX, railY, railZ) & 8 | var7;
		}
		if(p_94511_2_ || logicWorld.getBlockMetadata(railX, railY, railZ) != var8)
		{
			logicWorld.setBlockMetadataWithNotify(railX, railY, railZ, var8, 3);
			for(int var9 = 0; var9 < railChunkPosition.size(); ++var9)
			{
				BlockBaseRailLogic var10 = getRailLogic((ChunkPosition) railChunkPosition.get(var9));
				if(var10 != null)
				{
					var10.refreshConnectedTracks();
					if(var10.canConnectTo(this))
					{
						var10.connectToNeighbor(this);
					}
				}
			}
		}
	}
	
	protected int getNumberOfAdjacentTracks()
	{
		int var1 = 0;
		if(isMinecartTrack(railX, railY, railZ - 1))
		{
			++var1;
		}
		if(isMinecartTrack(railX, railY, railZ + 1))
		{
			++var1;
		}
		if(isMinecartTrack(railX - 1, railY, railZ))
		{
			++var1;
		}
		if(isMinecartTrack(railX + 1, railY, railZ))
		{
			++var1;
		}
		return var1;
	}
	
	private BlockBaseRailLogic getRailLogic(ChunkPosition p_94501_1_)
	{
		return BlockRailBase.isRailBlockAt(logicWorld, p_94501_1_.x, p_94501_1_.y, p_94501_1_.z) ? new BlockBaseRailLogic(theRail, logicWorld, p_94501_1_.x, p_94501_1_.y, p_94501_1_.z) : BlockRailBase.isRailBlockAt(logicWorld, p_94501_1_.x, p_94501_1_.y + 1, p_94501_1_.z) ? new BlockBaseRailLogic(theRail, logicWorld, p_94501_1_.x, p_94501_1_.y + 1, p_94501_1_.z) : BlockRailBase.isRailBlockAt(logicWorld, p_94501_1_.x, p_94501_1_.y - 1, p_94501_1_.z) ? new BlockBaseRailLogic(theRail, logicWorld, p_94501_1_.x, p_94501_1_.y - 1, p_94501_1_.z) : null;
	}
	
	private boolean isMinecartTrack(int p_94502_1_, int p_94502_2_, int p_94502_3_)
	{
		return BlockRailBase.isRailBlockAt(logicWorld, p_94502_1_, p_94502_2_, p_94502_3_) ? true : BlockRailBase.isRailBlockAt(logicWorld, p_94502_1_, p_94502_2_ + 1, p_94502_3_) ? true : BlockRailBase.isRailBlockAt(logicWorld, p_94502_1_, p_94502_2_ - 1, p_94502_3_);
	}
	
	private boolean isPartOfTrack(int p_94510_1_, int p_94510_2_, int p_94510_3_)
	{
		for(int var4 = 0; var4 < railChunkPosition.size(); ++var4)
		{
			ChunkPosition var5 = (ChunkPosition) railChunkPosition.get(var4);
			if(var5.x == p_94510_1_ && var5.z == p_94510_3_) return true;
		}
		return false;
	}
	
	private boolean isRailChunkPositionCorrect(BlockBaseRailLogic p_94508_1_)
	{
		for(int var2 = 0; var2 < railChunkPosition.size(); ++var2)
		{
			ChunkPosition var3 = (ChunkPosition) railChunkPosition.get(var2);
			if(var3.x == p_94508_1_.railX && var3.z == p_94508_1_.railZ) return true;
		}
		return false;
	}
	
	private void refreshConnectedTracks()
	{
		for(int var1 = 0; var1 < railChunkPosition.size(); ++var1)
		{
			BlockBaseRailLogic var2 = getRailLogic((ChunkPosition) railChunkPosition.get(var1));
			if(var2 != null && var2.isRailChunkPositionCorrect(this))
			{
				railChunkPosition.set(var1, new ChunkPosition(var2.railX, var2.railY, var2.railZ));
			} else
			{
				railChunkPosition.remove(var1--);
			}
		}
	}
	
	private void setBasicRail(int p_94504_1_)
	{
		railChunkPosition.clear();
		if(p_94504_1_ == 0)
		{
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ - 1));
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ + 1));
		} else if(p_94504_1_ == 1)
		{
			railChunkPosition.add(new ChunkPosition(railX - 1, railY, railZ));
			railChunkPosition.add(new ChunkPosition(railX + 1, railY, railZ));
		} else if(p_94504_1_ == 2)
		{
			railChunkPosition.add(new ChunkPosition(railX - 1, railY, railZ));
			railChunkPosition.add(new ChunkPosition(railX + 1, railY + 1, railZ));
		} else if(p_94504_1_ == 3)
		{
			railChunkPosition.add(new ChunkPosition(railX - 1, railY + 1, railZ));
			railChunkPosition.add(new ChunkPosition(railX + 1, railY, railZ));
		} else if(p_94504_1_ == 4)
		{
			railChunkPosition.add(new ChunkPosition(railX, railY + 1, railZ - 1));
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ + 1));
		} else if(p_94504_1_ == 5)
		{
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ - 1));
			railChunkPosition.add(new ChunkPosition(railX, railY + 1, railZ + 1));
		} else if(p_94504_1_ == 6)
		{
			railChunkPosition.add(new ChunkPosition(railX + 1, railY, railZ));
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ + 1));
		} else if(p_94504_1_ == 7)
		{
			railChunkPosition.add(new ChunkPosition(railX - 1, railY, railZ));
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ + 1));
		} else if(p_94504_1_ == 8)
		{
			railChunkPosition.add(new ChunkPosition(railX - 1, railY, railZ));
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ - 1));
		} else if(p_94504_1_ == 9)
		{
			railChunkPosition.add(new ChunkPosition(railX + 1, railY, railZ));
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ - 1));
		}
	}
}
