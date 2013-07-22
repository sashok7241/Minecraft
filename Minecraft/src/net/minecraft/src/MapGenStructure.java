package net.minecraft.src;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class MapGenStructure extends MapGenBase
{
	protected Map structureMap = new HashMap();
	
	protected abstract boolean canSpawnStructureAtCoords(int var1, int var2);
	
	public boolean generateStructuresInChunk(World p_75051_1_, Random p_75051_2_, int p_75051_3_, int p_75051_4_)
	{
		int var5 = (p_75051_3_ << 4) + 8;
		int var6 = (p_75051_4_ << 4) + 8;
		boolean var7 = false;
		Iterator var8 = structureMap.values().iterator();
		while(var8.hasNext())
		{
			StructureStart var9 = (StructureStart) var8.next();
			if(var9.isSizeableStructure() && var9.getBoundingBox().intersectsWith(var5, var6, var5 + 15, var6 + 15))
			{
				var9.generateStructure(p_75051_1_, p_75051_2_, new StructureBoundingBox(var5, var6, var5 + 15, var6 + 15));
				var7 = true;
			}
		}
		return var7;
	}
	
	protected List getCoordList()
	{
		return null;
	}
	
	public ChunkPosition getNearestInstance(World p_75050_1_, int p_75050_2_, int p_75050_3_, int p_75050_4_)
	{
		worldObj = p_75050_1_;
		rand.setSeed(p_75050_1_.getSeed());
		long var5 = rand.nextLong();
		long var7 = rand.nextLong();
		long var9 = (p_75050_2_ >> 4) * var5;
		long var11 = (p_75050_4_ >> 4) * var7;
		rand.setSeed(var9 ^ var11 ^ p_75050_1_.getSeed());
		recursiveGenerate(p_75050_1_, p_75050_2_ >> 4, p_75050_4_ >> 4, 0, 0, (byte[]) null);
		double var13 = Double.MAX_VALUE;
		ChunkPosition var15 = null;
		Iterator var16 = structureMap.values().iterator();
		ChunkPosition var19;
		int var21;
		int var20;
		double var23;
		int var22;
		while(var16.hasNext())
		{
			StructureStart var17 = (StructureStart) var16.next();
			if(var17.isSizeableStructure())
			{
				StructureComponent var18 = (StructureComponent) var17.getComponents().get(0);
				var19 = var18.getCenter();
				var20 = var19.x - p_75050_2_;
				var21 = var19.y - p_75050_3_;
				var22 = var19.z - p_75050_4_;
				var23 = var20 + var20 * var21 * var21 + var22 * var22;
				if(var23 < var13)
				{
					var13 = var23;
					var15 = var19;
				}
			}
		}
		if(var15 != null) return var15;
		else
		{
			List var25 = getCoordList();
			if(var25 != null)
			{
				ChunkPosition var26 = null;
				Iterator var27 = var25.iterator();
				while(var27.hasNext())
				{
					var19 = (ChunkPosition) var27.next();
					var20 = var19.x - p_75050_2_;
					var21 = var19.y - p_75050_3_;
					var22 = var19.z - p_75050_4_;
					var23 = var20 + var20 * var21 * var21 + var22 * var22;
					if(var23 < var13)
					{
						var13 = var23;
						var26 = var19;
					}
				}
				return var26;
			} else return null;
		}
	}
	
	protected abstract StructureStart getStructureStart(int var1, int var2);
	
	public boolean hasStructureAt(int p_75048_1_, int p_75048_2_, int p_75048_3_)
	{
		Iterator var4 = structureMap.values().iterator();
		while(var4.hasNext())
		{
			StructureStart var5 = (StructureStart) var4.next();
			if(var5.isSizeableStructure() && var5.getBoundingBox().intersectsWith(p_75048_1_, p_75048_3_, p_75048_1_, p_75048_3_))
			{
				Iterator var6 = var5.getComponents().iterator();
				while(var6.hasNext())
				{
					StructureComponent var7 = (StructureComponent) var6.next();
					if(var7.getBoundingBox().isVecInside(p_75048_1_, p_75048_2_, p_75048_3_)) return true;
				}
			}
		}
		return false;
	}
	
	@Override protected void recursiveGenerate(World p_75037_1_, int p_75037_2_, int p_75037_3_, int p_75037_4_, int p_75037_5_, byte[] p_75037_6_)
	{
		if(!structureMap.containsKey(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(p_75037_2_, p_75037_3_))))
		{
			rand.nextInt();
			try
			{
				if(canSpawnStructureAtCoords(p_75037_2_, p_75037_3_))
				{
					StructureStart var7 = getStructureStart(p_75037_2_, p_75037_3_);
					structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(p_75037_2_, p_75037_3_)), var7);
				}
			} catch(Throwable var10)
			{
				CrashReport var8 = CrashReport.makeCrashReport(var10, "Exception preparing structure feature");
				CrashReportCategory var9 = var8.makeCategory("Feature being prepared");
				var9.addCrashSectionCallable("Is feature chunk", new CallableIsFeatureChunk(this, p_75037_2_, p_75037_3_));
				var9.addCrashSection("Chunk location", String.format("%d,%d", new Object[] { Integer.valueOf(p_75037_2_), Integer.valueOf(p_75037_3_) }));
				var9.addCrashSectionCallable("Chunk pos hash", new CallableChunkPosHash(this, p_75037_2_, p_75037_3_));
				var9.addCrashSectionCallable("Structure type", new CallableStructureType(this));
				throw new ReportedException(var8);
			}
		}
	}
}
