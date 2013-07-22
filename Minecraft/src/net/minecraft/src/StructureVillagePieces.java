package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class StructureVillagePieces
{
	private static int func_75079_a(List p_75079_0_)
	{
		boolean var1 = false;
		int var2 = 0;
		StructureVillagePieceWeight var4;
		for(Iterator var3 = p_75079_0_.iterator(); var3.hasNext(); var2 += var4.villagePieceWeight)
		{
			var4 = (StructureVillagePieceWeight) var3.next();
			if(var4.villagePiecesLimit > 0 && var4.villagePiecesSpawned < var4.villagePiecesLimit)
			{
				var1 = true;
			}
		}
		return var1 ? var2 : -1;
	}
	
	private static ComponentVillage func_75083_a(ComponentVillageStartPiece p_75083_0_, StructureVillagePieceWeight p_75083_1_, List p_75083_2_, Random p_75083_3_, int p_75083_4_, int p_75083_5_, int p_75083_6_, int p_75083_7_, int p_75083_8_)
	{
		Class var9 = p_75083_1_.villagePieceClass;
		Object var10 = null;
		if(var9 == ComponentVillageHouse4_Garden.class)
		{
			var10 = ComponentVillageHouse4_Garden.func_74912_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
		} else if(var9 == ComponentVillageChurch.class)
		{
			var10 = ComponentVillageChurch.func_74919_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
		} else if(var9 == ComponentVillageHouse1.class)
		{
			var10 = ComponentVillageHouse1.func_74898_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
		} else if(var9 == ComponentVillageWoodHut.class)
		{
			var10 = ComponentVillageWoodHut.func_74908_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
		} else if(var9 == ComponentVillageHall.class)
		{
			var10 = ComponentVillageHall.func_74906_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
		} else if(var9 == ComponentVillageField.class)
		{
			var10 = ComponentVillageField.func_74900_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
		} else if(var9 == ComponentVillageField2.class)
		{
			var10 = ComponentVillageField2.func_74902_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
		} else if(var9 == ComponentVillageHouse2.class)
		{
			var10 = ComponentVillageHouse2.func_74915_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
		} else if(var9 == ComponentVillageHouse3.class)
		{
			var10 = ComponentVillageHouse3.func_74921_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
		}
		return (ComponentVillage) var10;
	}
	
	private static StructureComponent getNextComponentVillagePath(ComponentVillageStartPiece p_75080_0_, List p_75080_1_, Random p_75080_2_, int p_75080_3_, int p_75080_4_, int p_75080_5_, int p_75080_6_, int p_75080_7_)
	{
		if(p_75080_7_ > 3 + p_75080_0_.terrainType) return null;
		else if(Math.abs(p_75080_3_ - p_75080_0_.getBoundingBox().minX) <= 112 && Math.abs(p_75080_5_ - p_75080_0_.getBoundingBox().minZ) <= 112)
		{
			StructureBoundingBox var8 = ComponentVillagePathGen.func_74933_a(p_75080_0_, p_75080_1_, p_75080_2_, p_75080_3_, p_75080_4_, p_75080_5_, p_75080_6_);
			if(var8 != null && var8.minY > 10)
			{
				ComponentVillagePathGen var9 = new ComponentVillagePathGen(p_75080_0_, p_75080_7_, p_75080_2_, var8, p_75080_6_);
				int var10 = (var9.boundingBox.minX + var9.boundingBox.maxX) / 2;
				int var11 = (var9.boundingBox.minZ + var9.boundingBox.maxZ) / 2;
				int var12 = var9.boundingBox.maxX - var9.boundingBox.minX;
				int var13 = var9.boundingBox.maxZ - var9.boundingBox.minZ;
				int var14 = var12 > var13 ? var12 : var13;
				if(p_75080_0_.getWorldChunkManager().areBiomesViable(var10, var11, var14 / 2 + 4, MapGenVillage.villageSpawnBiomes))
				{
					p_75080_1_.add(var9);
					p_75080_0_.field_74930_j.add(var9);
					return var9;
				}
			}
			return null;
		} else return null;
	}
	
	static StructureComponent getNextStructureComponent(ComponentVillageStartPiece p_75078_0_, List p_75078_1_, Random p_75078_2_, int p_75078_3_, int p_75078_4_, int p_75078_5_, int p_75078_6_, int p_75078_7_)
	{
		return getNextVillageStructureComponent(p_75078_0_, p_75078_1_, p_75078_2_, p_75078_3_, p_75078_4_, p_75078_5_, p_75078_6_, p_75078_7_);
	}
	
	static StructureComponent getNextStructureComponentVillagePath(ComponentVillageStartPiece p_75082_0_, List p_75082_1_, Random p_75082_2_, int p_75082_3_, int p_75082_4_, int p_75082_5_, int p_75082_6_, int p_75082_7_)
	{
		return getNextComponentVillagePath(p_75082_0_, p_75082_1_, p_75082_2_, p_75082_3_, p_75082_4_, p_75082_5_, p_75082_6_, p_75082_7_);
	}
	
	private static ComponentVillage getNextVillageComponent(ComponentVillageStartPiece p_75081_0_, List p_75081_1_, Random p_75081_2_, int p_75081_3_, int p_75081_4_, int p_75081_5_, int p_75081_6_, int p_75081_7_)
	{
		int var8 = func_75079_a(p_75081_0_.structureVillageWeightedPieceList);
		if(var8 <= 0) return null;
		else
		{
			int var9 = 0;
			while(var9 < 5)
			{
				++var9;
				int var10 = p_75081_2_.nextInt(var8);
				Iterator var11 = p_75081_0_.structureVillageWeightedPieceList.iterator();
				while(var11.hasNext())
				{
					StructureVillagePieceWeight var12 = (StructureVillagePieceWeight) var11.next();
					var10 -= var12.villagePieceWeight;
					if(var10 < 0)
					{
						if(!var12.canSpawnMoreVillagePiecesOfType(p_75081_7_) || var12 == p_75081_0_.structVillagePieceWeight && p_75081_0_.structureVillageWeightedPieceList.size() > 1)
						{
							break;
						}
						ComponentVillage var13 = func_75083_a(p_75081_0_, var12, p_75081_1_, p_75081_2_, p_75081_3_, p_75081_4_, p_75081_5_, p_75081_6_, p_75081_7_);
						if(var13 != null)
						{
							++var12.villagePiecesSpawned;
							p_75081_0_.structVillagePieceWeight = var12;
							if(!var12.canSpawnMoreVillagePieces())
							{
								p_75081_0_.structureVillageWeightedPieceList.remove(var12);
							}
							return var13;
						}
					}
				}
			}
			StructureBoundingBox var14 = ComponentVillageTorch.func_74904_a(p_75081_0_, p_75081_1_, p_75081_2_, p_75081_3_, p_75081_4_, p_75081_5_, p_75081_6_);
			if(var14 != null) return new ComponentVillageTorch(p_75081_0_, p_75081_7_, p_75081_2_, var14, p_75081_6_);
			else return null;
		}
	}
	
	private static StructureComponent getNextVillageStructureComponent(ComponentVillageStartPiece p_75077_0_, List p_75077_1_, Random p_75077_2_, int p_75077_3_, int p_75077_4_, int p_75077_5_, int p_75077_6_, int p_75077_7_)
	{
		if(p_75077_7_ > 50) return null;
		else if(Math.abs(p_75077_3_ - p_75077_0_.getBoundingBox().minX) <= 112 && Math.abs(p_75077_5_ - p_75077_0_.getBoundingBox().minZ) <= 112)
		{
			ComponentVillage var8 = getNextVillageComponent(p_75077_0_, p_75077_1_, p_75077_2_, p_75077_3_, p_75077_4_, p_75077_5_, p_75077_6_, p_75077_7_ + 1);
			if(var8 != null)
			{
				int var9 = (var8.boundingBox.minX + var8.boundingBox.maxX) / 2;
				int var10 = (var8.boundingBox.minZ + var8.boundingBox.maxZ) / 2;
				int var11 = var8.boundingBox.maxX - var8.boundingBox.minX;
				int var12 = var8.boundingBox.maxZ - var8.boundingBox.minZ;
				int var13 = var11 > var12 ? var11 : var12;
				if(p_75077_0_.getWorldChunkManager().areBiomesViable(var9, var10, var13 / 2 + 4, MapGenVillage.villageSpawnBiomes))
				{
					p_75077_1_.add(var8);
					p_75077_0_.field_74932_i.add(var8);
					return var8;
				}
			}
			return null;
		} else return null;
	}
	
	public static ArrayList getStructureVillageWeightedPieceList(Random p_75084_0_, int p_75084_1_)
	{
		ArrayList var2 = new ArrayList();
		var2.add(new StructureVillagePieceWeight(ComponentVillageHouse4_Garden.class, 4, MathHelper.getRandomIntegerInRange(p_75084_0_, 2 + p_75084_1_, 4 + p_75084_1_ * 2)));
		var2.add(new StructureVillagePieceWeight(ComponentVillageChurch.class, 20, MathHelper.getRandomIntegerInRange(p_75084_0_, 0 + p_75084_1_, 1 + p_75084_1_)));
		var2.add(new StructureVillagePieceWeight(ComponentVillageHouse1.class, 20, MathHelper.getRandomIntegerInRange(p_75084_0_, 0 + p_75084_1_, 2 + p_75084_1_)));
		var2.add(new StructureVillagePieceWeight(ComponentVillageWoodHut.class, 3, MathHelper.getRandomIntegerInRange(p_75084_0_, 2 + p_75084_1_, 5 + p_75084_1_ * 3)));
		var2.add(new StructureVillagePieceWeight(ComponentVillageHall.class, 15, MathHelper.getRandomIntegerInRange(p_75084_0_, 0 + p_75084_1_, 2 + p_75084_1_)));
		var2.add(new StructureVillagePieceWeight(ComponentVillageField.class, 3, MathHelper.getRandomIntegerInRange(p_75084_0_, 1 + p_75084_1_, 4 + p_75084_1_)));
		var2.add(new StructureVillagePieceWeight(ComponentVillageField2.class, 3, MathHelper.getRandomIntegerInRange(p_75084_0_, 2 + p_75084_1_, 4 + p_75084_1_ * 2)));
		var2.add(new StructureVillagePieceWeight(ComponentVillageHouse2.class, 15, MathHelper.getRandomIntegerInRange(p_75084_0_, 0, 1 + p_75084_1_)));
		var2.add(new StructureVillagePieceWeight(ComponentVillageHouse3.class, 8, MathHelper.getRandomIntegerInRange(p_75084_0_, 0 + p_75084_1_, 3 + p_75084_1_ * 2)));
		Iterator var3 = var2.iterator();
		while(var3.hasNext())
		{
			if(((StructureVillagePieceWeight) var3.next()).villagePiecesLimit == 0)
			{
				var3.remove();
			}
		}
		return var2;
	}
}
