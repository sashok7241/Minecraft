package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChunkProviderFlat implements IChunkProvider
{
	private World worldObj;
	private Random random;
	private final byte[] field_82700_c = new byte[256];
	private final byte[] field_82698_d = new byte[256];
	private final FlatGeneratorInfo field_82699_e;
	private final List structureGenerators = new ArrayList();
	private final boolean field_82697_g;
	private final boolean field_82702_h;
	private WorldGenLakes waterLakeGenerator;
	private WorldGenLakes lavaLakeGenerator;
	
	public ChunkProviderFlat(World par1World, long par2, boolean par4, String par5Str)
	{
		worldObj = par1World;
		random = new Random(par2);
		field_82699_e = FlatGeneratorInfo.createFlatGeneratorFromString(par5Str);
		if(par4)
		{
			Map var6 = field_82699_e.getWorldFeatures();
			if(var6.containsKey("village"))
			{
				Map var7 = (Map) var6.get("village");
				if(!var7.containsKey("size"))
				{
					var7.put("size", "1");
				}
				structureGenerators.add(new MapGenVillage(var7));
			}
			if(var6.containsKey("biome_1"))
			{
				structureGenerators.add(new MapGenScatteredFeature((Map) var6.get("biome_1")));
			}
			if(var6.containsKey("mineshaft"))
			{
				structureGenerators.add(new MapGenMineshaft((Map) var6.get("mineshaft")));
			}
			if(var6.containsKey("stronghold"))
			{
				structureGenerators.add(new MapGenStronghold((Map) var6.get("stronghold")));
			}
		}
		field_82697_g = field_82699_e.getWorldFeatures().containsKey("decoration");
		if(field_82699_e.getWorldFeatures().containsKey("lake"))
		{
			waterLakeGenerator = new WorldGenLakes(Block.waterStill.blockID);
		}
		if(field_82699_e.getWorldFeatures().containsKey("lava_lake"))
		{
			lavaLakeGenerator = new WorldGenLakes(Block.lavaStill.blockID);
		}
		field_82702_h = field_82699_e.getWorldFeatures().containsKey("dungeon");
		Iterator var9 = field_82699_e.getFlatLayers().iterator();
		while(var9.hasNext())
		{
			FlatLayerInfo var10 = (FlatLayerInfo) var9.next();
			for(int var8 = var10.getMinY(); var8 < var10.getMinY() + var10.getLayerCount(); ++var8)
			{
				field_82700_c[var8] = (byte) (var10.getFillBlock() & 255);
				field_82698_d[var8] = (byte) var10.getFillBlockMeta();
			}
		}
	}
	
	@Override public boolean canSave()
	{
		return true;
	}
	
	@Override public boolean chunkExists(int par1, int par2)
	{
		return true;
	}
	
	@Override public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5)
	{
		if("Stronghold".equals(par2Str))
		{
			Iterator var6 = structureGenerators.iterator();
			while(var6.hasNext())
			{
				MapGenStructure var7 = (MapGenStructure) var6.next();
				if(var7 instanceof MapGenStronghold) return var7.getNearestInstance(par1World, par3, par4, par5);
			}
		}
		return null;
	}
	
	@Override public void func_104112_b()
	{
	}
	
	@Override public int getLoadedChunkCount()
	{
		return 0;
	}
	
	@Override public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
	{
		BiomeGenBase var5 = worldObj.getBiomeGenForCoords(par2, par4);
		return var5 == null ? null : var5.getSpawnableList(par1EnumCreatureType);
	}
	
	@Override public Chunk loadChunk(int par1, int par2)
	{
		return provideChunk(par1, par2);
	}
	
	@Override public String makeString()
	{
		return "FlatLevelSource";
	}
	
	@Override public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
	{
		int var4 = par2 * 16;
		int var5 = par3 * 16;
		BiomeGenBase var6 = worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
		boolean var7 = false;
		random.setSeed(worldObj.getSeed());
		long var8 = random.nextLong() / 2L * 2L + 1L;
		long var10 = random.nextLong() / 2L * 2L + 1L;
		random.setSeed(par2 * var8 + par3 * var10 ^ worldObj.getSeed());
		Iterator var12 = structureGenerators.iterator();
		while(var12.hasNext())
		{
			MapGenStructure var13 = (MapGenStructure) var12.next();
			boolean var14 = var13.generateStructuresInChunk(worldObj, random, par2, par3);
			if(var13 instanceof MapGenVillage)
			{
				var7 |= var14;
			}
		}
		int var17;
		int var16;
		int var18;
		if(waterLakeGenerator != null && !var7 && random.nextInt(4) == 0)
		{
			var16 = var4 + random.nextInt(16) + 8;
			var17 = random.nextInt(128);
			var18 = var5 + random.nextInt(16) + 8;
			waterLakeGenerator.generate(worldObj, random, var16, var17, var18);
		}
		if(lavaLakeGenerator != null && !var7 && random.nextInt(8) == 0)
		{
			var16 = var4 + random.nextInt(16) + 8;
			var17 = random.nextInt(random.nextInt(120) + 8);
			var18 = var5 + random.nextInt(16) + 8;
			if(var17 < 63 || random.nextInt(10) == 0)
			{
				lavaLakeGenerator.generate(worldObj, random, var16, var17, var18);
			}
		}
		if(field_82702_h)
		{
			for(var16 = 0; var16 < 8; ++var16)
			{
				var17 = var4 + random.nextInt(16) + 8;
				var18 = random.nextInt(128);
				int var15 = var5 + random.nextInt(16) + 8;
				new WorldGenDungeons().generate(worldObj, random, var17, var18, var15);
			}
		}
		if(field_82697_g)
		{
			var6.decorate(worldObj, random, var4, var5);
		}
	}
	
	@Override public Chunk provideChunk(int par1, int par2)
	{
		Chunk var3 = new Chunk(worldObj, par1, par2);
		for(int var4 = 0; var4 < field_82700_c.length; ++var4)
		{
			int var5 = var4 >> 4;
			ExtendedBlockStorage var6 = var3.getBlockStorageArray()[var5];
			if(var6 == null)
			{
				var6 = new ExtendedBlockStorage(var4, !worldObj.provider.hasNoSky);
				var3.getBlockStorageArray()[var5] = var6;
			}
			for(int var7 = 0; var7 < 16; ++var7)
			{
				for(int var8 = 0; var8 < 16; ++var8)
				{
					var6.setExtBlockID(var7, var4 & 15, var8, field_82700_c[var4] & 255);
					var6.setExtBlockMetadata(var7, var4 & 15, var8, field_82698_d[var4]);
				}
			}
		}
		var3.generateSkylightMap();
		BiomeGenBase[] var9 = worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[]) null, par1 * 16, par2 * 16, 16, 16);
		byte[] var10 = var3.getBiomeArray();
		for(int var11 = 0; var11 < var10.length; ++var11)
		{
			var10[var11] = (byte) var9[var11].biomeID;
		}
		Iterator var12 = structureGenerators.iterator();
		while(var12.hasNext())
		{
			MapGenStructure var13 = (MapGenStructure) var12.next();
			var13.generate(this, worldObj, par1, par2, (byte[]) null);
		}
		var3.generateSkylightMap();
		return var3;
	}
	
	@Override public void recreateStructures(int par1, int par2)
	{
		Iterator var3 = structureGenerators.iterator();
		while(var3.hasNext())
		{
			MapGenStructure var4 = (MapGenStructure) var3.next();
			var4.generate(this, worldObj, par1, par2, (byte[]) null);
		}
	}
	
	@Override public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
	{
		return true;
	}
	
	@Override public boolean unloadQueuedChunks()
	{
		return false;
	}
}
