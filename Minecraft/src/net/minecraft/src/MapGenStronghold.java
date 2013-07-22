package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class MapGenStronghold extends MapGenStructure
{
	private BiomeGenBase[] allowedBiomeGenBases;
	private boolean ranBiomeCheck;
	private ChunkCoordIntPair[] structureCoords;
	private double field_82671_h;
	private int field_82672_i;
	
	public MapGenStronghold()
	{
		allowedBiomeGenBases = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.icePlains, BiomeGenBase.iceMountains, BiomeGenBase.desertHills, BiomeGenBase.forestHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.jungle, BiomeGenBase.jungleHills };
		structureCoords = new ChunkCoordIntPair[3];
		field_82671_h = 32.0D;
		field_82672_i = 3;
	}
	
	public MapGenStronghold(Map par1Map)
	{
		allowedBiomeGenBases = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.icePlains, BiomeGenBase.iceMountains, BiomeGenBase.desertHills, BiomeGenBase.forestHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.jungle, BiomeGenBase.jungleHills };
		structureCoords = new ChunkCoordIntPair[3];
		field_82671_h = 32.0D;
		field_82672_i = 3;
		Iterator var2 = par1Map.entrySet().iterator();
		while(var2.hasNext())
		{
			Entry var3 = (Entry) var2.next();
			if(((String) var3.getKey()).equals("distance"))
			{
				field_82671_h = MathHelper.func_82713_a((String) var3.getValue(), field_82671_h, 1.0D);
			} else if(((String) var3.getKey()).equals("count"))
			{
				structureCoords = new ChunkCoordIntPair[MathHelper.parseIntWithDefaultAndMax((String) var3.getValue(), structureCoords.length, 1)];
			} else if(((String) var3.getKey()).equals("spread"))
			{
				field_82672_i = MathHelper.parseIntWithDefaultAndMax((String) var3.getValue(), field_82672_i, 1);
			}
		}
	}
	
	@Override protected boolean canSpawnStructureAtCoords(int par1, int par2)
	{
		if(!ranBiomeCheck)
		{
			Random var3 = new Random();
			var3.setSeed(worldObj.getSeed());
			double var4 = var3.nextDouble() * Math.PI * 2.0D;
			int var6 = 1;
			for(int var7 = 0; var7 < structureCoords.length; ++var7)
			{
				double var8 = (1.25D * var6 + var3.nextDouble()) * field_82671_h * var6;
				int var10 = (int) Math.round(Math.cos(var4) * var8);
				int var11 = (int) Math.round(Math.sin(var4) * var8);
				ArrayList var12 = new ArrayList();
				Collections.addAll(var12, allowedBiomeGenBases);
				ChunkPosition var13 = worldObj.getWorldChunkManager().findBiomePosition((var10 << 4) + 8, (var11 << 4) + 8, 112, var12, var3);
				if(var13 != null)
				{
					var10 = var13.x >> 4;
					var11 = var13.z >> 4;
				}
				structureCoords[var7] = new ChunkCoordIntPair(var10, var11);
				var4 += Math.PI * 2D * var6 / field_82672_i;
				if(var7 == field_82672_i)
				{
					var6 += 2 + var3.nextInt(5);
					field_82672_i += 1 + var3.nextInt(2);
				}
			}
			ranBiomeCheck = true;
		}
		ChunkCoordIntPair[] var14 = structureCoords;
		int var15 = var14.length;
		for(int var5 = 0; var5 < var15; ++var5)
		{
			ChunkCoordIntPair var16 = var14[var5];
			if(par1 == var16.chunkXPos && par2 == var16.chunkZPos) return true;
		}
		return false;
	}
	
	@Override protected List getCoordList()
	{
		ArrayList var1 = new ArrayList();
		ChunkCoordIntPair[] var2 = structureCoords;
		int var3 = var2.length;
		for(int var4 = 0; var4 < var3; ++var4)
		{
			ChunkCoordIntPair var5 = var2[var4];
			if(var5 != null)
			{
				var1.add(var5.getChunkPosition(64));
			}
		}
		return var1;
	}
	
	@Override protected StructureStart getStructureStart(int par1, int par2)
	{
		StructureStrongholdStart var3;
		for(var3 = new StructureStrongholdStart(worldObj, rand, par1, par2); var3.getComponents().isEmpty() || ((ComponentStrongholdStairs2) var3.getComponents().get(0)).strongholdPortalRoom == null; var3 = new StructureStrongholdStart(worldObj, rand, par1, par2))
		{
			;
		}
		return var3;
	}
}
