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
	
	public ChunkProviderFlat(World p_i5090_1_, long p_i5090_2_, boolean p_i5090_4_, String p_i5090_5_)
	{
		worldObj = p_i5090_1_;
		random = new Random(p_i5090_2_);
		field_82699_e = FlatGeneratorInfo.createFlatGeneratorFromString(p_i5090_5_);
		if(p_i5090_4_)
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
	
	@Override public boolean chunkExists(int p_73149_1_, int p_73149_2_)
	{
		return true;
	}
	
	@Override public ChunkPosition findClosestStructure(World p_73150_1_, String p_73150_2_, int p_73150_3_, int p_73150_4_, int p_73150_5_)
	{
		if("Stronghold".equals(p_73150_2_))
		{
			Iterator var6 = structureGenerators.iterator();
			while(var6.hasNext())
			{
				MapGenStructure var7 = (MapGenStructure) var6.next();
				if(var7 instanceof MapGenStronghold) return var7.getNearestInstance(p_73150_1_, p_73150_3_, p_73150_4_, p_73150_5_);
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
	
	@Override public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_)
	{
		BiomeGenBase var5 = worldObj.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
		return var5 == null ? null : var5.getSpawnableList(p_73155_1_);
	}
	
	@Override public Chunk loadChunk(int p_73158_1_, int p_73158_2_)
	{
		return provideChunk(p_73158_1_, p_73158_2_);
	}
	
	@Override public String makeString()
	{
		return "FlatLevelSource";
	}
	
	@Override public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
	{
		int var4 = p_73153_2_ * 16;
		int var5 = p_73153_3_ * 16;
		BiomeGenBase var6 = worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
		boolean var7 = false;
		random.setSeed(worldObj.getSeed());
		long var8 = random.nextLong() / 2L * 2L + 1L;
		long var10 = random.nextLong() / 2L * 2L + 1L;
		random.setSeed(p_73153_2_ * var8 + p_73153_3_ * var10 ^ worldObj.getSeed());
		Iterator var12 = structureGenerators.iterator();
		while(var12.hasNext())
		{
			MapGenStructure var13 = (MapGenStructure) var12.next();
			boolean var14 = var13.generateStructuresInChunk(worldObj, random, p_73153_2_, p_73153_3_);
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
	
	@Override public Chunk provideChunk(int p_73154_1_, int p_73154_2_)
	{
		Chunk var3 = new Chunk(worldObj, p_73154_1_, p_73154_2_);
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
		BiomeGenBase[] var9 = worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[]) null, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
		byte[] var10 = var3.getBiomeArray();
		for(int var11 = 0; var11 < var10.length; ++var11)
		{
			var10[var11] = (byte) var9[var11].biomeID;
		}
		Iterator var12 = structureGenerators.iterator();
		while(var12.hasNext())
		{
			MapGenStructure var13 = (MapGenStructure) var12.next();
			var13.generate(this, worldObj, p_73154_1_, p_73154_2_, (byte[]) null);
		}
		var3.generateSkylightMap();
		return var3;
	}
	
	@Override public void recreateStructures(int p_82695_1_, int p_82695_2_)
	{
		Iterator var3 = structureGenerators.iterator();
		while(var3.hasNext())
		{
			MapGenStructure var4 = (MapGenStructure) var3.next();
			var4.generate(this, worldObj, p_82695_1_, p_82695_2_, (byte[]) null);
		}
	}
	
	@Override public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
	{
		return true;
	}
	
	@Override public boolean unloadQueuedChunks()
	{
		return false;
	}
}
