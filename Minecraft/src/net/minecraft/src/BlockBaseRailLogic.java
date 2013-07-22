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
	
	public BlockBaseRailLogic(BlockRailBase par1, World par2, int par3, int par4, int par5)
	{
		theRail = par1;
		railChunkPosition = new ArrayList();
		logicWorld = par2;
		railX = par3;
		railY = par4;
		railZ = par5;
		int var6 = par2.getBlockId(par3, par4, par5);
		int var7 = par2.getBlockMetadata(par3, par4, par5);
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
	
	private boolean canConnectFrom(int par1, int par2, int par3)
	{
		BlockBaseRailLogic var4 = getRailLogic(new ChunkPosition(par1, par2, par3));
		if(var4 == null) return false;
		else
		{
			var4.refreshConnectedTracks();
			return var4.canConnectTo(this);
		}
	}
	
	private boolean canConnectTo(BlockBaseRailLogic par1BlockBaseRailLogic)
	{
		return isRailChunkPositionCorrect(par1BlockBaseRailLogic) ? true : railChunkPosition.size() == 2 ? false : railChunkPosition.isEmpty() ? true : true;
	}
	
	private void connectToNeighbor(BlockBaseRailLogic par1BlockBaseRailLogic)
	{
		railChunkPosition.add(new ChunkPosition(par1BlockBaseRailLogic.railX, par1BlockBaseRailLogic.railY, par1BlockBaseRailLogic.railZ));
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
	
	public void func_94511_a(boolean par1, boolean par2)
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
				if(par1)
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
		if(par2 || logicWorld.getBlockMetadata(railX, railY, railZ) != var8)
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
	
	private BlockBaseRailLogic getRailLogic(ChunkPosition par1ChunkPosition)
	{
		return BlockRailBase.isRailBlockAt(logicWorld, par1ChunkPosition.x, par1ChunkPosition.y, par1ChunkPosition.z) ? new BlockBaseRailLogic(theRail, logicWorld, par1ChunkPosition.x, par1ChunkPosition.y, par1ChunkPosition.z) : BlockRailBase.isRailBlockAt(logicWorld, par1ChunkPosition.x, par1ChunkPosition.y + 1, par1ChunkPosition.z) ? new BlockBaseRailLogic(theRail, logicWorld, par1ChunkPosition.x, par1ChunkPosition.y + 1, par1ChunkPosition.z) : BlockRailBase.isRailBlockAt(logicWorld, par1ChunkPosition.x, par1ChunkPosition.y - 1, par1ChunkPosition.z) ? new BlockBaseRailLogic(theRail, logicWorld, par1ChunkPosition.x, par1ChunkPosition.y - 1, par1ChunkPosition.z) : null;
	}
	
	private boolean isMinecartTrack(int par1, int par2, int par3)
	{
		return BlockRailBase.isRailBlockAt(logicWorld, par1, par2, par3) ? true : BlockRailBase.isRailBlockAt(logicWorld, par1, par2 + 1, par3) ? true : BlockRailBase.isRailBlockAt(logicWorld, par1, par2 - 1, par3);
	}
	
	private boolean isPartOfTrack(int par1, int par2, int par3)
	{
		for(int var4 = 0; var4 < railChunkPosition.size(); ++var4)
		{
			ChunkPosition var5 = (ChunkPosition) railChunkPosition.get(var4);
			if(var5.x == par1 && var5.z == par3) return true;
		}
		return false;
	}
	
	private boolean isRailChunkPositionCorrect(BlockBaseRailLogic par1BlockBaseRailLogic)
	{
		for(int var2 = 0; var2 < railChunkPosition.size(); ++var2)
		{
			ChunkPosition var3 = (ChunkPosition) railChunkPosition.get(var2);
			if(var3.x == par1BlockBaseRailLogic.railX && var3.z == par1BlockBaseRailLogic.railZ) return true;
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
	
	private void setBasicRail(int par1)
	{
		railChunkPosition.clear();
		if(par1 == 0)
		{
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ - 1));
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ + 1));
		} else if(par1 == 1)
		{
			railChunkPosition.add(new ChunkPosition(railX - 1, railY, railZ));
			railChunkPosition.add(new ChunkPosition(railX + 1, railY, railZ));
		} else if(par1 == 2)
		{
			railChunkPosition.add(new ChunkPosition(railX - 1, railY, railZ));
			railChunkPosition.add(new ChunkPosition(railX + 1, railY + 1, railZ));
		} else if(par1 == 3)
		{
			railChunkPosition.add(new ChunkPosition(railX - 1, railY + 1, railZ));
			railChunkPosition.add(new ChunkPosition(railX + 1, railY, railZ));
		} else if(par1 == 4)
		{
			railChunkPosition.add(new ChunkPosition(railX, railY + 1, railZ - 1));
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ + 1));
		} else if(par1 == 5)
		{
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ - 1));
			railChunkPosition.add(new ChunkPosition(railX, railY + 1, railZ + 1));
		} else if(par1 == 6)
		{
			railChunkPosition.add(new ChunkPosition(railX + 1, railY, railZ));
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ + 1));
		} else if(par1 == 7)
		{
			railChunkPosition.add(new ChunkPosition(railX - 1, railY, railZ));
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ + 1));
		} else if(par1 == 8)
		{
			railChunkPosition.add(new ChunkPosition(railX - 1, railY, railZ));
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ - 1));
		} else if(par1 == 9)
		{
			railChunkPosition.add(new ChunkPosition(railX + 1, railY, railZ));
			railChunkPosition.add(new ChunkPosition(railX, railY, railZ - 1));
		}
	}
}
