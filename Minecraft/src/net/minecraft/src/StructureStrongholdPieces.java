package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class StructureStrongholdPieces
{
	private static final StructureStrongholdPieceWeight[] pieceWeightArray = new StructureStrongholdPieceWeight[] { new StructureStrongholdPieceWeight(ComponentStrongholdStraight.class, 40, 0), new StructureStrongholdPieceWeight(ComponentStrongholdPrison.class, 5, 5), new StructureStrongholdPieceWeight(ComponentStrongholdLeftTurn.class, 20, 0), new StructureStrongholdPieceWeight(ComponentStrongholdRightTurn.class, 20, 0), new StructureStrongholdPieceWeight(ComponentStrongholdRoomCrossing.class, 10, 6), new StructureStrongholdPieceWeight(ComponentStrongholdStairsStraight.class, 5, 5), new StructureStrongholdPieceWeight(ComponentStrongholdStairs.class, 5, 5), new StructureStrongholdPieceWeight(ComponentStrongholdCrossing.class, 5, 4), new StructureStrongholdPieceWeight(ComponentStrongholdChestCorridor.class, 5, 4), new StructureStrongholdPieceWeight2(ComponentStrongholdLibrary.class, 10, 2), new StructureStrongholdPieceWeight3(ComponentStrongholdPortalRoom.class, 20, 1) };
	private static List structurePieceList;
	private static Class strongComponentType;
	static int totalWeight = 0;
	private static final StructureStrongholdStones strongholdStones = new StructureStrongholdStones((StructureStrongholdPieceWeight2) null);
	
	private static boolean canAddStructurePieces()
	{
		boolean var0 = false;
		totalWeight = 0;
		StructureStrongholdPieceWeight var2;
		for(Iterator var1 = structurePieceList.iterator(); var1.hasNext(); totalWeight += var2.pieceWeight)
		{
			var2 = (StructureStrongholdPieceWeight) var1.next();
			if(var2.instancesLimit > 0 && var2.instancesSpawned < var2.instancesLimit)
			{
				var0 = true;
			}
		}
		return var0;
	}
	
	private static ComponentStronghold getNextComponent(ComponentStrongholdStairs2 p_75201_0_, List p_75201_1_, Random p_75201_2_, int p_75201_3_, int p_75201_4_, int p_75201_5_, int p_75201_6_, int p_75201_7_)
	{
		if(!canAddStructurePieces()) return null;
		else
		{
			if(strongComponentType != null)
			{
				ComponentStronghold var8 = getStrongholdComponentFromWeightedPiece(strongComponentType, p_75201_1_, p_75201_2_, p_75201_3_, p_75201_4_, p_75201_5_, p_75201_6_, p_75201_7_);
				strongComponentType = null;
				if(var8 != null) return var8;
			}
			int var13 = 0;
			while(var13 < 5)
			{
				++var13;
				int var9 = p_75201_2_.nextInt(totalWeight);
				Iterator var10 = structurePieceList.iterator();
				while(var10.hasNext())
				{
					StructureStrongholdPieceWeight var11 = (StructureStrongholdPieceWeight) var10.next();
					var9 -= var11.pieceWeight;
					if(var9 < 0)
					{
						if(!var11.canSpawnMoreStructuresOfType(p_75201_7_) || var11 == p_75201_0_.strongholdPieceWeight)
						{
							break;
						}
						ComponentStronghold var12 = getStrongholdComponentFromWeightedPiece(var11.pieceClass, p_75201_1_, p_75201_2_, p_75201_3_, p_75201_4_, p_75201_5_, p_75201_6_, p_75201_7_);
						if(var12 != null)
						{
							++var11.instancesSpawned;
							p_75201_0_.strongholdPieceWeight = var11;
							if(!var11.canSpawnMoreStructures())
							{
								structurePieceList.remove(var11);
							}
							return var12;
						}
					}
				}
			}
			StructureBoundingBox var14 = ComponentStrongholdCorridor.func_74992_a(p_75201_1_, p_75201_2_, p_75201_3_, p_75201_4_, p_75201_5_, p_75201_6_);
			if(var14 != null && var14.minY > 1) return new ComponentStrongholdCorridor(p_75201_7_, p_75201_2_, var14, p_75201_6_);
			else return null;
		}
	}
	
	private static StructureComponent getNextValidComponent(ComponentStrongholdStairs2 p_75196_0_, List p_75196_1_, Random p_75196_2_, int p_75196_3_, int p_75196_4_, int p_75196_5_, int p_75196_6_, int p_75196_7_)
	{
		if(p_75196_7_ > 50) return null;
		else if(Math.abs(p_75196_3_ - p_75196_0_.getBoundingBox().minX) <= 112 && Math.abs(p_75196_5_ - p_75196_0_.getBoundingBox().minZ) <= 112)
		{
			ComponentStronghold var8 = getNextComponent(p_75196_0_, p_75196_1_, p_75196_2_, p_75196_3_, p_75196_4_, p_75196_5_, p_75196_6_, p_75196_7_ + 1);
			if(var8 != null)
			{
				p_75196_1_.add(var8);
				p_75196_0_.field_75026_c.add(var8);
			}
			return var8;
		} else return null;
	}
	
	static StructureComponent getNextValidComponentAccess(ComponentStrongholdStairs2 p_75195_0_, List p_75195_1_, Random p_75195_2_, int p_75195_3_, int p_75195_4_, int p_75195_5_, int p_75195_6_, int p_75195_7_)
	{
		return getNextValidComponent(p_75195_0_, p_75195_1_, p_75195_2_, p_75195_3_, p_75195_4_, p_75195_5_, p_75195_6_, p_75195_7_);
	}
	
	private static ComponentStronghold getStrongholdComponentFromWeightedPiece(Class p_75200_0_, List p_75200_1_, Random p_75200_2_, int p_75200_3_, int p_75200_4_, int p_75200_5_, int p_75200_6_, int p_75200_7_)
	{
		Object var8 = null;
		if(p_75200_0_ == ComponentStrongholdStraight.class)
		{
			var8 = ComponentStrongholdStraight.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
		} else if(p_75200_0_ == ComponentStrongholdPrison.class)
		{
			var8 = ComponentStrongholdPrison.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
		} else if(p_75200_0_ == ComponentStrongholdLeftTurn.class)
		{
			var8 = ComponentStrongholdLeftTurn.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
		} else if(p_75200_0_ == ComponentStrongholdRightTurn.class)
		{
			var8 = ComponentStrongholdLeftTurn.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
		} else if(p_75200_0_ == ComponentStrongholdRoomCrossing.class)
		{
			var8 = ComponentStrongholdRoomCrossing.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
		} else if(p_75200_0_ == ComponentStrongholdStairsStraight.class)
		{
			var8 = ComponentStrongholdStairsStraight.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
		} else if(p_75200_0_ == ComponentStrongholdStairs.class)
		{
			var8 = ComponentStrongholdStairs.getStrongholdStairsComponent(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
		} else if(p_75200_0_ == ComponentStrongholdCrossing.class)
		{
			var8 = ComponentStrongholdCrossing.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
		} else if(p_75200_0_ == ComponentStrongholdChestCorridor.class)
		{
			var8 = ComponentStrongholdChestCorridor.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
		} else if(p_75200_0_ == ComponentStrongholdLibrary.class)
		{
			var8 = ComponentStrongholdLibrary.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
		} else if(p_75200_0_ == ComponentStrongholdPortalRoom.class)
		{
			var8 = ComponentStrongholdPortalRoom.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
		}
		return (ComponentStronghold) var8;
	}
	
	static StructureStrongholdStones getStrongholdStones()
	{
		return strongholdStones;
	}
	
	public static void prepareStructurePieces()
	{
		structurePieceList = new ArrayList();
		StructureStrongholdPieceWeight[] var0 = pieceWeightArray;
		int var1 = var0.length;
		for(int var2 = 0; var2 < var1; ++var2)
		{
			StructureStrongholdPieceWeight var3 = var0[var2];
			var3.instancesSpawned = 0;
			structurePieceList.add(var3);
		}
		strongComponentType = null;
	}
	
	static Class setComponentType(Class p_75199_0_)
	{
		strongComponentType = p_75199_0_;
		return p_75199_0_;
	}
}
