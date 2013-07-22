package net.minecraft.src;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class World implements IBlockAccess
{
	public boolean scheduledUpdatesAreImmediate = false;
	public List loadedEntityList = new ArrayList();
	protected List unloadedEntityList = new ArrayList();
	public List loadedTileEntityList = new ArrayList();
	private List addedTileEntityList = new ArrayList();
	private List entityRemoval = new ArrayList();
	public List playerEntities = new ArrayList();
	public List weatherEffects = new ArrayList();
	private long cloudColour = 16777215L;
	public int skylightSubtracted = 0;
	protected int updateLCG = new Random().nextInt();
	protected final int DIST_HASH_MAGIC = 1013904223;
	protected float prevRainingStrength;
	protected float rainingStrength;
	protected float prevThunderingStrength;
	protected float thunderingStrength;
	public int lastLightningBolt = 0;
	public int difficultySetting;
	public Random rand = new Random();
	public final WorldProvider provider;
	protected List worldAccesses = new ArrayList();
	protected IChunkProvider chunkProvider;
	protected final ISaveHandler saveHandler;
	protected WorldInfo worldInfo;
	public boolean findingSpawnPoint;
	public MapStorage mapStorage;
	public final VillageCollection villageCollectionObj;
	protected final VillageSiege villageSiegeObj = new VillageSiege(this);
	public final Profiler theProfiler;
	private final Vec3Pool vecPool = new Vec3Pool(300, 2000);
	private final Calendar theCalendar = Calendar.getInstance();
	protected Scoreboard worldScoreboard = new Scoreboard();
	private final ILogAgent worldLogAgent;
	private ArrayList collidingBoundingBoxes = new ArrayList();
	private boolean scanningTileEntities;
	protected boolean spawnHostileMobs = true;
	protected boolean spawnPeacefulMobs = true;
	protected Set activeChunkSet = new HashSet();
	private int ambientTickCountdown;
	int[] lightUpdateBlockList;
	public boolean isRemote;
	
	public World(ISaveHandler p_i11043_1_, String p_i11043_2_, WorldProvider p_i11043_3_, WorldSettings p_i11043_4_, Profiler p_i11043_5_, ILogAgent p_i11043_6_)
	{
		ambientTickCountdown = rand.nextInt(12000);
		lightUpdateBlockList = new int[32768];
		isRemote = false;
		saveHandler = p_i11043_1_;
		theProfiler = p_i11043_5_;
		worldInfo = new WorldInfo(p_i11043_4_, p_i11043_2_);
		provider = p_i11043_3_;
		mapStorage = new MapStorage(p_i11043_1_);
		worldLogAgent = p_i11043_6_;
		VillageCollection var7 = (VillageCollection) mapStorage.loadData(VillageCollection.class, "villages");
		if(var7 == null)
		{
			villageCollectionObj = new VillageCollection(this);
			mapStorage.setData("villages", villageCollectionObj);
		} else
		{
			villageCollectionObj = var7;
			villageCollectionObj.func_82566_a(this);
		}
		p_i11043_3_.registerWorld(this);
		chunkProvider = createChunkProvider();
		calculateInitialSkylight();
		calculateInitialWeather();
	}
	
	public World(ISaveHandler p_i11044_1_, String p_i11044_2_, WorldSettings p_i11044_3_, WorldProvider p_i11044_4_, Profiler p_i11044_5_, ILogAgent p_i11044_6_)
	{
		ambientTickCountdown = rand.nextInt(12000);
		lightUpdateBlockList = new int[32768];
		isRemote = false;
		saveHandler = p_i11044_1_;
		theProfiler = p_i11044_5_;
		mapStorage = new MapStorage(p_i11044_1_);
		worldLogAgent = p_i11044_6_;
		worldInfo = p_i11044_1_.loadWorldInfo();
		if(p_i11044_4_ != null)
		{
			provider = p_i11044_4_;
		} else if(worldInfo != null && worldInfo.getVanillaDimension() != 0)
		{
			provider = WorldProvider.getProviderForDimension(worldInfo.getVanillaDimension());
		} else
		{
			provider = WorldProvider.getProviderForDimension(0);
		}
		if(worldInfo == null)
		{
			worldInfo = new WorldInfo(p_i11044_3_, p_i11044_2_);
		} else
		{
			worldInfo.setWorldName(p_i11044_2_);
		}
		provider.registerWorld(this);
		chunkProvider = createChunkProvider();
		if(!worldInfo.isInitialized())
		{
			try
			{
				initialize(p_i11044_3_);
			} catch(Throwable var11)
			{
				CrashReport var8 = CrashReport.makeCrashReport(var11, "Exception initializing level");
				try
				{
					addWorldInfoToCrashReport(var8);
				} catch(Throwable var10)
				{
					;
				}
				throw new ReportedException(var8);
			}
			worldInfo.setServerInitialized(true);
		}
		VillageCollection var7 = (VillageCollection) mapStorage.loadData(VillageCollection.class, "villages");
		if(var7 == null)
		{
			villageCollectionObj = new VillageCollection(this);
			mapStorage.setData("villages", villageCollectionObj);
		} else
		{
			villageCollectionObj = var7;
			villageCollectionObj.func_82566_a(this);
		}
		calculateInitialSkylight();
		calculateInitialWeather();
	}
	
	public void addBlockEvent(int p_72965_1_, int p_72965_2_, int p_72965_3_, int p_72965_4_, int p_72965_5_, int p_72965_6_)
	{
		if(p_72965_4_ > 0)
		{
			Block.blocksList[p_72965_4_].onBlockEventReceived(this, p_72965_1_, p_72965_2_, p_72965_3_, p_72965_5_, p_72965_6_);
		}
	}
	
	public void addLoadedEntities(List p_72868_1_)
	{
		loadedEntityList.addAll(p_72868_1_);
		for(int var2 = 0; var2 < p_72868_1_.size(); ++var2)
		{
			onEntityAdded((Entity) p_72868_1_.get(var2));
		}
	}
	
	public void addTileEntity(Collection p_72852_1_)
	{
		if(scanningTileEntities)
		{
			addedTileEntityList.addAll(p_72852_1_);
		} else
		{
			loadedTileEntityList.addAll(p_72852_1_);
		}
	}
	
	public boolean addWeatherEffect(Entity p_72942_1_)
	{
		weatherEffects.add(p_72942_1_);
		return true;
	}
	
	public void addWorldAccess(IWorldAccess p_72954_1_)
	{
		worldAccesses.add(p_72954_1_);
	}
	
	public CrashReportCategory addWorldInfoToCrashReport(CrashReport p_72914_1_)
	{
		CrashReportCategory var2 = p_72914_1_.makeCategoryDepth("Affected level", 1);
		var2.addCrashSection("Level name", worldInfo == null ? "????" : worldInfo.getWorldName());
		var2.addCrashSectionCallable("All players", new CallableLvl2(this));
		var2.addCrashSectionCallable("Chunk stats", new CallableLvl3(this));
		try
		{
			worldInfo.addToCrashReport(var2);
		} catch(Throwable var4)
		{
			var2.addCrashSectionThrowable("Level Data Unobtainable", var4);
		}
		return var2;
	}
	
	public boolean blockExists(int p_72899_1_, int p_72899_2_, int p_72899_3_)
	{
		return p_72899_2_ >= 0 && p_72899_2_ < 256 ? chunkExists(p_72899_1_ >> 4, p_72899_3_ >> 4) : false;
	}
	
	public int blockGetRenderType(int p_85175_1_, int p_85175_2_, int p_85175_3_)
	{
		int var4 = getBlockId(p_85175_1_, p_85175_2_, p_85175_3_);
		return Block.blocksList[var4] != null ? Block.blocksList[var4].getRenderType() : -1;
	}
	
	public boolean blockHasTileEntity(int p_72927_1_, int p_72927_2_, int p_72927_3_)
	{
		int var4 = getBlockId(p_72927_1_, p_72927_2_, p_72927_3_);
		return Block.blocksList[var4] != null && Block.blocksList[var4].hasTileEntity();
	}
	
	public void calculateInitialSkylight()
	{
		int var1 = calculateSkylightSubtracted(1.0F);
		if(var1 != skylightSubtracted)
		{
			skylightSubtracted = var1;
		}
	}
	
	private void calculateInitialWeather()
	{
		if(worldInfo.isRaining())
		{
			rainingStrength = 1.0F;
			if(worldInfo.isThundering())
			{
				thunderingStrength = 1.0F;
			}
		}
	}
	
	public int calculateSkylightSubtracted(float p_72967_1_)
	{
		float var2 = getCelestialAngle(p_72967_1_);
		float var3 = 1.0F - (MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.5F);
		if(var3 < 0.0F)
		{
			var3 = 0.0F;
		}
		if(var3 > 1.0F)
		{
			var3 = 1.0F;
		}
		var3 = 1.0F - var3;
		var3 = (float) (var3 * (1.0D - getRainStrength(p_72967_1_) * 5.0F / 16.0D));
		var3 = (float) (var3 * (1.0D - getWeightedThunderStrength(p_72967_1_) * 5.0F / 16.0D));
		var3 = 1.0F - var3;
		return (int) (var3 * 11.0F);
	}
	
	public boolean canBlockFreeze(int p_72834_1_, int p_72834_2_, int p_72834_3_, boolean p_72834_4_)
	{
		BiomeGenBase var5 = getBiomeGenForCoords(p_72834_1_, p_72834_3_);
		float var6 = var5.getFloatTemperature();
		if(var6 > 0.15F) return false;
		else
		{
			if(p_72834_2_ >= 0 && p_72834_2_ < 256 && getSavedLightValue(EnumSkyBlock.Block, p_72834_1_, p_72834_2_, p_72834_3_) < 10)
			{
				int var7 = getBlockId(p_72834_1_, p_72834_2_, p_72834_3_);
				if((var7 == Block.waterStill.blockID || var7 == Block.waterMoving.blockID) && getBlockMetadata(p_72834_1_, p_72834_2_, p_72834_3_) == 0)
				{
					if(!p_72834_4_) return true;
					boolean var8 = true;
					if(var8 && getBlockMaterial(p_72834_1_ - 1, p_72834_2_, p_72834_3_) != Material.water)
					{
						var8 = false;
					}
					if(var8 && getBlockMaterial(p_72834_1_ + 1, p_72834_2_, p_72834_3_) != Material.water)
					{
						var8 = false;
					}
					if(var8 && getBlockMaterial(p_72834_1_, p_72834_2_, p_72834_3_ - 1) != Material.water)
					{
						var8 = false;
					}
					if(var8 && getBlockMaterial(p_72834_1_, p_72834_2_, p_72834_3_ + 1) != Material.water)
					{
						var8 = false;
					}
					if(!var8) return true;
				}
			}
			return false;
		}
	}
	
	public boolean canBlockSeeTheSky(int p_72937_1_, int p_72937_2_, int p_72937_3_)
	{
		return getChunkFromChunkCoords(p_72937_1_ >> 4, p_72937_3_ >> 4).canBlockSeeTheSky(p_72937_1_ & 15, p_72937_2_, p_72937_3_ & 15);
	}
	
	public boolean canLightningStrikeAt(int p_72951_1_, int p_72951_2_, int p_72951_3_)
	{
		if(!isRaining()) return false;
		else if(!canBlockSeeTheSky(p_72951_1_, p_72951_2_, p_72951_3_)) return false;
		else if(getPrecipitationHeight(p_72951_1_, p_72951_3_) > p_72951_2_) return false;
		else
		{
			BiomeGenBase var4 = getBiomeGenForCoords(p_72951_1_, p_72951_3_);
			return var4.getEnableSnow() ? false : var4.canSpawnLightningBolt();
		}
	}
	
	public boolean canMineBlock(EntityPlayer p_72962_1_, int p_72962_2_, int p_72962_3_, int p_72962_4_)
	{
		return true;
	}
	
	public boolean canPlaceEntityOnSide(int p_72931_1_, int p_72931_2_, int p_72931_3_, int p_72931_4_, boolean p_72931_5_, int p_72931_6_, Entity p_72931_7_, ItemStack p_72931_8_)
	{
		int var9 = getBlockId(p_72931_2_, p_72931_3_, p_72931_4_);
		Block var10 = Block.blocksList[var9];
		Block var11 = Block.blocksList[p_72931_1_];
		AxisAlignedBB var12 = var11.getCollisionBoundingBoxFromPool(this, p_72931_2_, p_72931_3_, p_72931_4_);
		if(p_72931_5_)
		{
			var12 = null;
		}
		if(var12 != null && !this.checkNoEntityCollision(var12, p_72931_7_)) return false;
		else
		{
			if(var10 != null && (var10 == Block.waterMoving || var10 == Block.waterStill || var10 == Block.lavaMoving || var10 == Block.lavaStill || var10 == Block.fire || var10.blockMaterial.isReplaceable()))
			{
				var10 = null;
			}
			return var10 != null && var10.blockMaterial == Material.circuits && var11 == Block.anvil ? true : p_72931_1_ > 0 && var10 == null && var11.canPlaceBlockOnSide(this, p_72931_2_, p_72931_3_, p_72931_4_, p_72931_6_, p_72931_8_);
		}
	}
	
	public boolean canSnowAt(int p_72858_1_, int p_72858_2_, int p_72858_3_)
	{
		BiomeGenBase var4 = getBiomeGenForCoords(p_72858_1_, p_72858_3_);
		float var5 = var4.getFloatTemperature();
		if(var5 > 0.15F) return false;
		else
		{
			if(p_72858_2_ >= 0 && p_72858_2_ < 256 && getSavedLightValue(EnumSkyBlock.Block, p_72858_1_, p_72858_2_, p_72858_3_) < 10)
			{
				int var6 = getBlockId(p_72858_1_, p_72858_2_ - 1, p_72858_3_);
				int var7 = getBlockId(p_72858_1_, p_72858_2_, p_72858_3_);
				if(var7 == 0 && Block.snow.canPlaceBlockAt(this, p_72858_1_, p_72858_2_, p_72858_3_) && var6 != 0 && var6 != Block.ice.blockID && Block.blocksList[var6].blockMaterial.blocksMovement()) return true;
			}
			return false;
		}
	}
	
	public boolean checkBlockCollision(AxisAlignedBB p_72829_1_)
	{
		int var2 = MathHelper.floor_double(p_72829_1_.minX);
		int var3 = MathHelper.floor_double(p_72829_1_.maxX + 1.0D);
		int var4 = MathHelper.floor_double(p_72829_1_.minY);
		int var5 = MathHelper.floor_double(p_72829_1_.maxY + 1.0D);
		int var6 = MathHelper.floor_double(p_72829_1_.minZ);
		int var7 = MathHelper.floor_double(p_72829_1_.maxZ + 1.0D);
		if(p_72829_1_.minX < 0.0D)
		{
			--var2;
		}
		if(p_72829_1_.minY < 0.0D)
		{
			--var4;
		}
		if(p_72829_1_.minZ < 0.0D)
		{
			--var6;
		}
		for(int var8 = var2; var8 < var3; ++var8)
		{
			for(int var9 = var4; var9 < var5; ++var9)
			{
				for(int var10 = var6; var10 < var7; ++var10)
				{
					Block var11 = Block.blocksList[getBlockId(var8, var9, var10)];
					if(var11 != null) return true;
				}
			}
		}
		return false;
	}
	
	public boolean checkChunksExist(int p_72904_1_, int p_72904_2_, int p_72904_3_, int p_72904_4_, int p_72904_5_, int p_72904_6_)
	{
		if(p_72904_5_ >= 0 && p_72904_2_ < 256)
		{
			p_72904_1_ >>= 4;
			p_72904_3_ >>= 4;
			p_72904_4_ >>= 4;
			p_72904_6_ >>= 4;
			for(int var7 = p_72904_1_; var7 <= p_72904_4_; ++var7)
			{
				for(int var8 = p_72904_3_; var8 <= p_72904_6_; ++var8)
				{
					if(!chunkExists(var7, var8)) return false;
				}
			}
			return true;
		} else return false;
	}
	
	public boolean checkNoEntityCollision(AxisAlignedBB p_72855_1_)
	{
		return this.checkNoEntityCollision(p_72855_1_, (Entity) null);
	}
	
	public boolean checkNoEntityCollision(AxisAlignedBB p_72917_1_, Entity p_72917_2_)
	{
		List var3 = this.getEntitiesWithinAABBExcludingEntity((Entity) null, p_72917_1_);
		for(int var4 = 0; var4 < var3.size(); ++var4)
		{
			Entity var5 = (Entity) var3.get(var4);
			if(!var5.isDead && var5.preventEntitySpawning && var5 != p_72917_2_) return false;
		}
		return true;
	}
	
	public void checkSessionLock() throws MinecraftException
	{
		saveHandler.checkSessionLock();
	}
	
	protected boolean chunkExists(int p_72916_1_, int p_72916_2_)
	{
		return chunkProvider.chunkExists(p_72916_1_, p_72916_2_);
	}
	
	public MovingObjectPosition clip(Vec3 p_72933_1_, Vec3 p_72933_2_)
	{
		return rayTraceBlocks_do_do(p_72933_1_, p_72933_2_, false, false);
	}
	
	public MovingObjectPosition clip(Vec3 p_72901_1_, Vec3 p_72901_2_, boolean p_72901_3_)
	{
		return rayTraceBlocks_do_do(p_72901_1_, p_72901_2_, p_72901_3_, false);
	}
	
	private int computeLightValue(int p_98179_1_, int p_98179_2_, int p_98179_3_, EnumSkyBlock p_98179_4_)
	{
		if(p_98179_4_ == EnumSkyBlock.Sky && canBlockSeeTheSky(p_98179_1_, p_98179_2_, p_98179_3_)) return 15;
		else
		{
			int var5 = getBlockId(p_98179_1_, p_98179_2_, p_98179_3_);
			int var6 = p_98179_4_ == EnumSkyBlock.Sky ? 0 : Block.lightValue[var5];
			int var7 = Block.lightOpacity[var5];
			if(var7 >= 15 && Block.lightValue[var5] > 0)
			{
				var7 = 1;
			}
			if(var7 < 1)
			{
				var7 = 1;
			}
			if(var7 >= 15) return 0;
			else if(var6 >= 14) return var6;
			else
			{
				for(int var8 = 0; var8 < 6; ++var8)
				{
					int var9 = p_98179_1_ + Facing.offsetsXForSide[var8];
					int var10 = p_98179_2_ + Facing.offsetsYForSide[var8];
					int var11 = p_98179_3_ + Facing.offsetsZForSide[var8];
					int var12 = getSavedLightValue(p_98179_4_, var9, var10, var11) - var7;
					if(var12 > var6)
					{
						var6 = var12;
					}
					if(var6 >= 14) return var6;
				}
				return var6;
			}
		}
	}
	
	public int countEntities(Class p_72907_1_)
	{
		int var2 = 0;
		for(int var3 = 0; var3 < loadedEntityList.size(); ++var3)
		{
			Entity var4 = (Entity) loadedEntityList.get(var3);
			if((!(var4 instanceof EntityLiving) || !((EntityLiving) var4).func_104002_bU()) && p_72907_1_.isAssignableFrom(var4.getClass()))
			{
				++var2;
			}
		}
		return var2;
	}
	
	protected abstract IChunkProvider createChunkProvider();
	
	public Explosion createExplosion(Entity p_72876_1_, double p_72876_2_, double p_72876_4_, double p_72876_6_, float p_72876_8_, boolean p_72876_9_)
	{
		return newExplosion(p_72876_1_, p_72876_2_, p_72876_4_, p_72876_6_, p_72876_8_, false, p_72876_9_);
	}
	
	public boolean destroyBlock(int p_94578_1_, int p_94578_2_, int p_94578_3_, boolean p_94578_4_)
	{
		int var5 = getBlockId(p_94578_1_, p_94578_2_, p_94578_3_);
		if(var5 > 0)
		{
			int var6 = getBlockMetadata(p_94578_1_, p_94578_2_, p_94578_3_);
			playAuxSFX(2001, p_94578_1_, p_94578_2_, p_94578_3_, var5 + (var6 << 12));
			if(p_94578_4_)
			{
				Block.blocksList[var5].dropBlockAsItem(this, p_94578_1_, p_94578_2_, p_94578_3_, var6, 0);
			}
			return this.setBlock(p_94578_1_, p_94578_2_, p_94578_3_, 0, 0, 3);
		} else return false;
	}
	
	public void destroyBlockInWorldPartially(int p_72888_1_, int p_72888_2_, int p_72888_3_, int p_72888_4_, int p_72888_5_)
	{
		for(int var6 = 0; var6 < worldAccesses.size(); ++var6)
		{
			IWorldAccess var7 = (IWorldAccess) worldAccesses.get(var6);
			var7.destroyBlockPartially(p_72888_1_, p_72888_2_, p_72888_3_, p_72888_4_, p_72888_5_);
		}
	}
	
	public boolean doChunksNearChunkExist(int p_72873_1_, int p_72873_2_, int p_72873_3_, int p_72873_4_)
	{
		return checkChunksExist(p_72873_1_ - p_72873_4_, p_72873_2_ - p_72873_4_, p_72873_3_ - p_72873_4_, p_72873_1_ + p_72873_4_, p_72873_2_ + p_72873_4_, p_72873_3_ + p_72873_4_);
	}
	
	@Override public boolean doesBlockHaveSolidTopSurface(int p_72797_1_, int p_72797_2_, int p_72797_3_)
	{
		Block var4 = Block.blocksList[getBlockId(p_72797_1_, p_72797_2_, p_72797_3_)];
		return isBlockTopFacingSurfaceSolid(var4, getBlockMetadata(p_72797_1_, p_72797_2_, p_72797_3_));
	}
	
	@Override public boolean extendedLevelsInChunkCache()
	{
		return false;
	}
	
	public boolean extinguishFire(EntityPlayer p_72886_1_, int p_72886_2_, int p_72886_3_, int p_72886_4_, int p_72886_5_)
	{
		if(p_72886_5_ == 0)
		{
			--p_72886_3_;
		}
		if(p_72886_5_ == 1)
		{
			++p_72886_3_;
		}
		if(p_72886_5_ == 2)
		{
			--p_72886_4_;
		}
		if(p_72886_5_ == 3)
		{
			++p_72886_4_;
		}
		if(p_72886_5_ == 4)
		{
			--p_72886_2_;
		}
		if(p_72886_5_ == 5)
		{
			++p_72886_2_;
		}
		if(getBlockId(p_72886_2_, p_72886_3_, p_72886_4_) == Block.fire.blockID)
		{
			playAuxSFXAtEntity(p_72886_1_, 1004, p_72886_2_, p_72886_3_, p_72886_4_, 0);
			setBlockToAir(p_72886_2_, p_72886_3_, p_72886_4_);
			return true;
		} else return false;
	}
	
	public ChunkPosition findClosestStructure(String p_72946_1_, int p_72946_2_, int p_72946_3_, int p_72946_4_)
	{
		return getChunkProvider().findClosestStructure(this, p_72946_1_, p_72946_2_, p_72946_3_, p_72946_4_);
	}
	
	public Entity findNearestEntityWithinAABB(Class p_72857_1_, AxisAlignedBB p_72857_2_, Entity p_72857_3_)
	{
		List var4 = getEntitiesWithinAABB(p_72857_1_, p_72857_2_);
		Entity var5 = null;
		double var6 = Double.MAX_VALUE;
		for(int var8 = 0; var8 < var4.size(); ++var8)
		{
			Entity var9 = (Entity) var4.get(var8);
			if(var9 != p_72857_3_)
			{
				double var10 = p_72857_3_.getDistanceSqToEntity(var9);
				if(var10 <= var6)
				{
					var5 = var9;
					var6 = var10;
				}
			}
		}
		return var5;
	}
	
	public IUpdatePlayerListBox func_82735_a(EntityMinecart p_82735_1_)
	{
		return null;
	}
	
	public void func_82738_a(long par1)
	{
		worldInfo.incrementTotalWorldTime(par1);
	}
	
	public void func_82739_e(int p_82739_1_, int p_82739_2_, int p_82739_3_, int p_82739_4_, int p_82739_5_)
	{
		for(int var6 = 0; var6 < worldAccesses.size(); ++var6)
		{
			((IWorldAccess) worldAccesses.get(var6)).broadcastSound(p_82739_1_, p_82739_2_, p_82739_3_, p_82739_4_, p_82739_5_);
		}
	}
	
	public void func_92088_a(double par1, double par3, double par5, double par7, double par9, double par11, NBTTagCompound par13NBTTagCompound)
	{
	}
	
	public void func_96440_m(int p_96440_1_, int p_96440_2_, int p_96440_3_, int p_96440_4_)
	{
		for(int var5 = 0; var5 < 4; ++var5)
		{
			int var6 = p_96440_1_ + Direction.offsetX[var5];
			int var7 = p_96440_3_ + Direction.offsetZ[var5];
			int var8 = getBlockId(var6, p_96440_2_, var7);
			if(var8 != 0)
			{
				Block var9 = Block.blocksList[var8];
				if(Block.redstoneComparatorIdle.func_94487_f(var8))
				{
					var9.onNeighborBlockChange(this, var6, p_96440_2_, var7, p_96440_4_);
				} else if(Block.isNormalCube(var8))
				{
					var6 += Direction.offsetX[var5];
					var7 += Direction.offsetZ[var5];
					var8 = getBlockId(var6, p_96440_2_, var7);
					var9 = Block.blocksList[var8];
					if(Block.redstoneComparatorIdle.func_94487_f(var8))
					{
						var9.onNeighborBlockChange(this, var6, p_96440_2_, var7, p_96440_4_);
					}
				}
			}
		}
	}
	
	public int getActualHeight()
	{
		return provider.hasNoSky ? 128 : 256;
	}
	
	@Override public BiomeGenBase getBiomeGenForCoords(int p_72807_1_, int p_72807_2_)
	{
		if(blockExists(p_72807_1_, 0, p_72807_2_))
		{
			Chunk var3 = getChunkFromBlockCoords(p_72807_1_, p_72807_2_);
			if(var3 != null) return var3.getBiomeGenForWorldCoords(p_72807_1_ & 15, p_72807_2_ & 15, provider.worldChunkMgr);
		}
		return provider.worldChunkMgr.getBiomeGenAt(p_72807_1_, p_72807_2_);
	}
	
	public float getBlockDensity(Vec3 p_72842_1_, AxisAlignedBB p_72842_2_)
	{
		double var3 = 1.0D / ((p_72842_2_.maxX - p_72842_2_.minX) * 2.0D + 1.0D);
		double var5 = 1.0D / ((p_72842_2_.maxY - p_72842_2_.minY) * 2.0D + 1.0D);
		double var7 = 1.0D / ((p_72842_2_.maxZ - p_72842_2_.minZ) * 2.0D + 1.0D);
		int var9 = 0;
		int var10 = 0;
		for(float var11 = 0.0F; var11 <= 1.0F; var11 = (float) (var11 + var3))
		{
			for(float var12 = 0.0F; var12 <= 1.0F; var12 = (float) (var12 + var5))
			{
				for(float var13 = 0.0F; var13 <= 1.0F; var13 = (float) (var13 + var7))
				{
					double var14 = p_72842_2_.minX + (p_72842_2_.maxX - p_72842_2_.minX) * var11;
					double var16 = p_72842_2_.minY + (p_72842_2_.maxY - p_72842_2_.minY) * var12;
					double var18 = p_72842_2_.minZ + (p_72842_2_.maxZ - p_72842_2_.minZ) * var13;
					if(this.clip(getWorldVec3Pool().getVecFromPool(var14, var16, var18), p_72842_1_) == null)
					{
						++var9;
					}
					++var10;
				}
			}
		}
		return (float) var9 / (float) var10;
	}
	
	@Override public int getBlockId(int p_72798_1_, int p_72798_2_, int p_72798_3_)
	{
		if(p_72798_1_ >= -30000000 && p_72798_3_ >= -30000000 && p_72798_1_ < 30000000 && p_72798_3_ < 30000000)
		{
			if(p_72798_2_ < 0) return 0;
			else if(p_72798_2_ >= 256) return 0;
			else
			{
				Chunk var4 = null;
				try
				{
					var4 = getChunkFromChunkCoords(p_72798_1_ >> 4, p_72798_3_ >> 4);
					return var4.getBlockID(p_72798_1_ & 15, p_72798_2_, p_72798_3_ & 15);
				} catch(Throwable var8)
				{
					CrashReport var6 = CrashReport.makeCrashReport(var8, "Exception getting block type in world");
					CrashReportCategory var7 = var6.makeCategory("Requested block coordinates");
					var7.addCrashSection("Found chunk", Boolean.valueOf(var4 == null));
					var7.addCrashSection("Location", CrashReportCategory.getLocationInfo(p_72798_1_, p_72798_2_, p_72798_3_));
					throw new ReportedException(var6);
				}
			}
		} else return 0;
	}
	
	public int getBlockLightValue(int p_72957_1_, int p_72957_2_, int p_72957_3_)
	{
		return getBlockLightValue_do(p_72957_1_, p_72957_2_, p_72957_3_, true);
	}
	
	public int getBlockLightValue_do(int p_72849_1_, int p_72849_2_, int p_72849_3_, boolean p_72849_4_)
	{
		if(p_72849_1_ >= -30000000 && p_72849_3_ >= -30000000 && p_72849_1_ < 30000000 && p_72849_3_ < 30000000)
		{
			if(p_72849_4_)
			{
				int var5 = getBlockId(p_72849_1_, p_72849_2_, p_72849_3_);
				if(Block.useNeighborBrightness[var5])
				{
					int var6 = getBlockLightValue_do(p_72849_1_, p_72849_2_ + 1, p_72849_3_, false);
					int var7 = getBlockLightValue_do(p_72849_1_ + 1, p_72849_2_, p_72849_3_, false);
					int var8 = getBlockLightValue_do(p_72849_1_ - 1, p_72849_2_, p_72849_3_, false);
					int var9 = getBlockLightValue_do(p_72849_1_, p_72849_2_, p_72849_3_ + 1, false);
					int var10 = getBlockLightValue_do(p_72849_1_, p_72849_2_, p_72849_3_ - 1, false);
					if(var7 > var6)
					{
						var6 = var7;
					}
					if(var8 > var6)
					{
						var6 = var8;
					}
					if(var9 > var6)
					{
						var6 = var9;
					}
					if(var10 > var6)
					{
						var6 = var10;
					}
					return var6;
				}
			}
			if(p_72849_2_ < 0) return 0;
			else
			{
				if(p_72849_2_ >= 256)
				{
					p_72849_2_ = 255;
				}
				Chunk var11 = getChunkFromChunkCoords(p_72849_1_ >> 4, p_72849_3_ >> 4);
				p_72849_1_ &= 15;
				p_72849_3_ &= 15;
				return var11.getBlockLightValue(p_72849_1_, p_72849_2_, p_72849_3_, skylightSubtracted);
			}
		} else return 15;
	}
	
	@Override public Material getBlockMaterial(int p_72803_1_, int p_72803_2_, int p_72803_3_)
	{
		int var4 = getBlockId(p_72803_1_, p_72803_2_, p_72803_3_);
		return var4 == 0 ? Material.air : Block.blocksList[var4].blockMaterial;
	}
	
	@Override public int getBlockMetadata(int p_72805_1_, int p_72805_2_, int p_72805_3_)
	{
		if(p_72805_1_ >= -30000000 && p_72805_3_ >= -30000000 && p_72805_1_ < 30000000 && p_72805_3_ < 30000000)
		{
			if(p_72805_2_ < 0) return 0;
			else if(p_72805_2_ >= 256) return 0;
			else
			{
				Chunk var4 = getChunkFromChunkCoords(p_72805_1_ >> 4, p_72805_3_ >> 4);
				p_72805_1_ &= 15;
				p_72805_3_ &= 15;
				return var4.getBlockMetadata(p_72805_1_, p_72805_2_, p_72805_3_);
			}
		} else return 0;
	}
	
	public int getBlockPowerInput(int p_94577_1_, int p_94577_2_, int p_94577_3_)
	{
		byte var4 = 0;
		int var5 = Math.max(var4, isBlockProvidingPowerTo(p_94577_1_, p_94577_2_ - 1, p_94577_3_, 0));
		if(var5 >= 15) return var5;
		else
		{
			var5 = Math.max(var5, isBlockProvidingPowerTo(p_94577_1_, p_94577_2_ + 1, p_94577_3_, 1));
			if(var5 >= 15) return var5;
			else
			{
				var5 = Math.max(var5, isBlockProvidingPowerTo(p_94577_1_, p_94577_2_, p_94577_3_ - 1, 2));
				if(var5 >= 15) return var5;
				else
				{
					var5 = Math.max(var5, isBlockProvidingPowerTo(p_94577_1_, p_94577_2_, p_94577_3_ + 1, 3));
					if(var5 >= 15) return var5;
					else
					{
						var5 = Math.max(var5, isBlockProvidingPowerTo(p_94577_1_ - 1, p_94577_2_, p_94577_3_, 4));
						if(var5 >= 15) return var5;
						else
						{
							var5 = Math.max(var5, isBlockProvidingPowerTo(p_94577_1_ + 1, p_94577_2_, p_94577_3_, 5));
							return var5 >= 15 ? var5 : var5;
						}
					}
				}
			}
		}
	}
	
	@Override public TileEntity getBlockTileEntity(int p_72796_1_, int p_72796_2_, int p_72796_3_)
	{
		if(p_72796_2_ >= 0 && p_72796_2_ < 256)
		{
			TileEntity var4 = null;
			int var5;
			TileEntity var6;
			if(scanningTileEntities)
			{
				for(var5 = 0; var5 < addedTileEntityList.size(); ++var5)
				{
					var6 = (TileEntity) addedTileEntityList.get(var5);
					if(!var6.isInvalid() && var6.xCoord == p_72796_1_ && var6.yCoord == p_72796_2_ && var6.zCoord == p_72796_3_)
					{
						var4 = var6;
						break;
					}
				}
			}
			if(var4 == null)
			{
				Chunk var7 = getChunkFromChunkCoords(p_72796_1_ >> 4, p_72796_3_ >> 4);
				if(var7 != null)
				{
					var4 = var7.getChunkBlockTileEntity(p_72796_1_ & 15, p_72796_2_, p_72796_3_ & 15);
				}
			}
			if(var4 == null)
			{
				for(var5 = 0; var5 < addedTileEntityList.size(); ++var5)
				{
					var6 = (TileEntity) addedTileEntityList.get(var5);
					if(!var6.isInvalid() && var6.xCoord == p_72796_1_ && var6.yCoord == p_72796_2_ && var6.zCoord == p_72796_3_)
					{
						var4 = var6;
						break;
					}
				}
			}
			return var4;
		} else return null;
	}
	
	@Override public float getBrightness(int par1, int par2, int par3, int par4)
	{
		int var5 = getBlockLightValue(par1, par2, par3);
		if(var5 < par4)
		{
			var5 = par4;
		}
		return provider.lightBrightnessTable[var5];
	}
	
	public float getCelestialAngle(float p_72826_1_)
	{
		return provider.calculateCelestialAngle(worldInfo.getWorldTime(), p_72826_1_);
	}
	
	public float getCelestialAngleRadians(float p_72929_1_)
	{
		float var2 = getCelestialAngle(p_72929_1_);
		return var2 * (float) Math.PI * 2.0F;
	}
	
	public Chunk getChunkFromBlockCoords(int p_72938_1_, int p_72938_2_)
	{
		return getChunkFromChunkCoords(p_72938_1_ >> 4, p_72938_2_ >> 4);
	}
	
	public Chunk getChunkFromChunkCoords(int p_72964_1_, int p_72964_2_)
	{
		return chunkProvider.provideChunk(p_72964_1_, p_72964_2_);
	}
	
	public int getChunkHeightMapMinimum(int p_82734_1_, int p_82734_2_)
	{
		if(p_82734_1_ >= -30000000 && p_82734_2_ >= -30000000 && p_82734_1_ < 30000000 && p_82734_2_ < 30000000)
		{
			if(!chunkExists(p_82734_1_ >> 4, p_82734_2_ >> 4)) return 0;
			else
			{
				Chunk var3 = getChunkFromChunkCoords(p_82734_1_ >> 4, p_82734_2_ >> 4);
				return var3.heightMapMinimum;
			}
		} else return 0;
	}
	
	public IChunkProvider getChunkProvider()
	{
		return chunkProvider;
	}
	
	public EntityPlayer getClosestPlayer(double p_72977_1_, double p_72977_3_, double p_72977_5_, double p_72977_7_)
	{
		double var9 = -1.0D;
		EntityPlayer var11 = null;
		for(int var12 = 0; var12 < playerEntities.size(); ++var12)
		{
			EntityPlayer var13 = (EntityPlayer) playerEntities.get(var12);
			double var14 = var13.getDistanceSq(p_72977_1_, p_72977_3_, p_72977_5_);
			if((p_72977_7_ < 0.0D || var14 < p_72977_7_ * p_72977_7_) && (var9 == -1.0D || var14 < var9))
			{
				var9 = var14;
				var11 = var13;
			}
		}
		return var11;
	}
	
	public EntityPlayer getClosestPlayerToEntity(Entity p_72890_1_, double p_72890_2_)
	{
		return getClosestPlayer(p_72890_1_.posX, p_72890_1_.posY, p_72890_1_.posZ, p_72890_2_);
	}
	
	public EntityPlayer getClosestVulnerablePlayer(double p_72846_1_, double p_72846_3_, double p_72846_5_, double p_72846_7_)
	{
		double var9 = -1.0D;
		EntityPlayer var11 = null;
		for(int var12 = 0; var12 < playerEntities.size(); ++var12)
		{
			EntityPlayer var13 = (EntityPlayer) playerEntities.get(var12);
			if(!var13.capabilities.disableDamage && var13.isEntityAlive())
			{
				double var14 = var13.getDistanceSq(p_72846_1_, p_72846_3_, p_72846_5_);
				double var16 = p_72846_7_;
				if(var13.isSneaking())
				{
					var16 = p_72846_7_ * 0.800000011920929D;
				}
				if(var13.isInvisible())
				{
					float var18 = var13.func_82243_bO();
					if(var18 < 0.1F)
					{
						var18 = 0.1F;
					}
					var16 *= 0.7F * var18;
				}
				if((p_72846_7_ < 0.0D || var14 < var16 * var16) && (var9 == -1.0D || var14 < var9))
				{
					var9 = var14;
					var11 = var13;
				}
			}
		}
		return var11;
	}
	
	public EntityPlayer getClosestVulnerablePlayerToEntity(Entity p_72856_1_, double p_72856_2_)
	{
		return getClosestVulnerablePlayer(p_72856_1_.posX, p_72856_1_.posY, p_72856_1_.posZ, p_72856_2_);
	}
	
	public Vec3 getCloudColour(float par1)
	{
		float var2 = getCelestialAngle(par1);
		float var3 = MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.5F;
		if(var3 < 0.0F)
		{
			var3 = 0.0F;
		}
		if(var3 > 1.0F)
		{
			var3 = 1.0F;
		}
		float var4 = (cloudColour >> 16 & 255L) / 255.0F;
		float var5 = (cloudColour >> 8 & 255L) / 255.0F;
		float var6 = (cloudColour & 255L) / 255.0F;
		float var7 = getRainStrength(par1);
		float var8;
		float var9;
		if(var7 > 0.0F)
		{
			var8 = (var4 * 0.3F + var5 * 0.59F + var6 * 0.11F) * 0.6F;
			var9 = 1.0F - var7 * 0.95F;
			var4 = var4 * var9 + var8 * (1.0F - var9);
			var5 = var5 * var9 + var8 * (1.0F - var9);
			var6 = var6 * var9 + var8 * (1.0F - var9);
		}
		var4 *= var3 * 0.9F + 0.1F;
		var5 *= var3 * 0.9F + 0.1F;
		var6 *= var3 * 0.85F + 0.15F;
		var8 = getWeightedThunderStrength(par1);
		if(var8 > 0.0F)
		{
			var9 = (var4 * 0.3F + var5 * 0.59F + var6 * 0.11F) * 0.2F;
			float var10 = 1.0F - var8 * 0.95F;
			var4 = var4 * var10 + var9 * (1.0F - var10);
			var5 = var5 * var10 + var9 * (1.0F - var10);
			var6 = var6 * var10 + var9 * (1.0F - var10);
		}
		return getWorldVec3Pool().getVecFromPool(var4, var5, var6);
	}
	
	public List getCollidingBlockBounds(AxisAlignedBB p_72840_1_)
	{
		collidingBoundingBoxes.clear();
		int var2 = MathHelper.floor_double(p_72840_1_.minX);
		int var3 = MathHelper.floor_double(p_72840_1_.maxX + 1.0D);
		int var4 = MathHelper.floor_double(p_72840_1_.minY);
		int var5 = MathHelper.floor_double(p_72840_1_.maxY + 1.0D);
		int var6 = MathHelper.floor_double(p_72840_1_.minZ);
		int var7 = MathHelper.floor_double(p_72840_1_.maxZ + 1.0D);
		for(int var8 = var2; var8 < var3; ++var8)
		{
			for(int var9 = var6; var9 < var7; ++var9)
			{
				if(blockExists(var8, 64, var9))
				{
					for(int var10 = var4 - 1; var10 < var5; ++var10)
					{
						Block var11 = Block.blocksList[getBlockId(var8, var10, var9)];
						if(var11 != null)
						{
							var11.addCollisionBoxesToList(this, var8, var10, var9, p_72840_1_, collidingBoundingBoxes, (Entity) null);
						}
					}
				}
			}
		}
		return collidingBoundingBoxes;
	}
	
	public List getCollidingBoundingBoxes(Entity p_72945_1_, AxisAlignedBB p_72945_2_)
	{
		collidingBoundingBoxes.clear();
		int var3 = MathHelper.floor_double(p_72945_2_.minX);
		int var4 = MathHelper.floor_double(p_72945_2_.maxX + 1.0D);
		int var5 = MathHelper.floor_double(p_72945_2_.minY);
		int var6 = MathHelper.floor_double(p_72945_2_.maxY + 1.0D);
		int var7 = MathHelper.floor_double(p_72945_2_.minZ);
		int var8 = MathHelper.floor_double(p_72945_2_.maxZ + 1.0D);
		for(int var9 = var3; var9 < var4; ++var9)
		{
			for(int var10 = var7; var10 < var8; ++var10)
			{
				if(blockExists(var9, 64, var10))
				{
					for(int var11 = var5 - 1; var11 < var6; ++var11)
					{
						Block var12 = Block.blocksList[getBlockId(var9, var11, var10)];
						if(var12 != null)
						{
							var12.addCollisionBoxesToList(this, var9, var11, var10, p_72945_2_, collidingBoundingBoxes, p_72945_1_);
						}
					}
				}
			}
		}
		double var14 = 0.25D;
		List var16 = this.getEntitiesWithinAABBExcludingEntity(p_72945_1_, p_72945_2_.expand(var14, var14, var14));
		for(int var15 = 0; var15 < var16.size(); ++var15)
		{
			AxisAlignedBB var13 = ((Entity) var16.get(var15)).getBoundingBox();
			if(var13 != null && var13.intersectsWith(p_72945_2_))
			{
				collidingBoundingBoxes.add(var13);
			}
			var13 = p_72945_1_.getCollisionBox((Entity) var16.get(var15));
			if(var13 != null && var13.intersectsWith(p_72945_2_))
			{
				collidingBoundingBoxes.add(var13);
			}
		}
		return collidingBoundingBoxes;
	}
	
	public Calendar getCurrentDate()
	{
		if(getTotalWorldTime() % 600L == 0L)
		{
			theCalendar.setTimeInMillis(System.currentTimeMillis());
		}
		return theCalendar;
	}
	
	public String getDebugLoadedEntities()
	{
		return "All: " + loadedEntityList.size();
	}
	
	public List getEntitiesWithinAABB(Class p_72872_1_, AxisAlignedBB p_72872_2_)
	{
		return selectEntitiesWithinAABB(p_72872_1_, p_72872_2_, (IEntitySelector) null);
	}
	
	public List getEntitiesWithinAABBExcludingEntity(Entity p_72839_1_, AxisAlignedBB p_72839_2_)
	{
		return this.getEntitiesWithinAABBExcludingEntity(p_72839_1_, p_72839_2_, (IEntitySelector) null);
	}
	
	public List getEntitiesWithinAABBExcludingEntity(Entity p_94576_1_, AxisAlignedBB p_94576_2_, IEntitySelector p_94576_3_)
	{
		ArrayList var4 = new ArrayList();
		int var5 = MathHelper.floor_double((p_94576_2_.minX - 2.0D) / 16.0D);
		int var6 = MathHelper.floor_double((p_94576_2_.maxX + 2.0D) / 16.0D);
		int var7 = MathHelper.floor_double((p_94576_2_.minZ - 2.0D) / 16.0D);
		int var8 = MathHelper.floor_double((p_94576_2_.maxZ + 2.0D) / 16.0D);
		for(int var9 = var5; var9 <= var6; ++var9)
		{
			for(int var10 = var7; var10 <= var8; ++var10)
			{
				if(chunkExists(var9, var10))
				{
					getChunkFromChunkCoords(var9, var10).getEntitiesWithinAABBForEntity(p_94576_1_, p_94576_2_, var4, p_94576_3_);
				}
			}
		}
		return var4;
	}
	
	public abstract Entity getEntityByID(int var1);
	
	public PathEntity getEntityPathToXYZ(Entity p_72844_1_, int p_72844_2_, int p_72844_3_, int p_72844_4_, float p_72844_5_, boolean p_72844_6_, boolean p_72844_7_, boolean p_72844_8_, boolean p_72844_9_)
	{
		theProfiler.startSection("pathfind");
		int var10 = MathHelper.floor_double(p_72844_1_.posX);
		int var11 = MathHelper.floor_double(p_72844_1_.posY);
		int var12 = MathHelper.floor_double(p_72844_1_.posZ);
		int var13 = (int) (p_72844_5_ + 8.0F);
		int var14 = var10 - var13;
		int var15 = var11 - var13;
		int var16 = var12 - var13;
		int var17 = var10 + var13;
		int var18 = var11 + var13;
		int var19 = var12 + var13;
		ChunkCache var20 = new ChunkCache(this, var14, var15, var16, var17, var18, var19, 0);
		PathEntity var21 = new PathFinder(var20, p_72844_6_, p_72844_7_, p_72844_8_, p_72844_9_).createEntityPathTo(p_72844_1_, p_72844_2_, p_72844_3_, p_72844_4_, p_72844_5_);
		theProfiler.endSection();
		return var21;
	}
	
	public int getFirstUncoveredBlock(int p_72922_1_, int p_72922_2_)
	{
		int var3;
		for(var3 = 63; !isAirBlock(p_72922_1_, var3 + 1, p_72922_2_); ++var3)
		{
			;
		}
		return getBlockId(p_72922_1_, var3, p_72922_2_);
	}
	
	public Vec3 getFogColor(float par1)
	{
		float var2 = getCelestialAngle(par1);
		return provider.getFogColor(var2, par1);
	}
	
	public int getFullBlockLightValue(int p_72883_1_, int p_72883_2_, int p_72883_3_)
	{
		if(p_72883_2_ < 0) return 0;
		else
		{
			if(p_72883_2_ >= 256)
			{
				p_72883_2_ = 255;
			}
			return getChunkFromChunkCoords(p_72883_1_ >> 4, p_72883_3_ >> 4).getBlockLightValue(p_72883_1_ & 15, p_72883_2_, p_72883_3_ & 15, 0);
		}
	}
	
	public GameRules getGameRules()
	{
		return worldInfo.getGameRulesInstance();
	}
	
	@Override public int getHeight()
	{
		return 256;
	}
	
	public int getHeightValue(int p_72976_1_, int p_72976_2_)
	{
		if(p_72976_1_ >= -30000000 && p_72976_2_ >= -30000000 && p_72976_1_ < 30000000 && p_72976_2_ < 30000000)
		{
			if(!chunkExists(p_72976_1_ >> 4, p_72976_2_ >> 4)) return 0;
			else
			{
				Chunk var3 = getChunkFromChunkCoords(p_72976_1_ >> 4, p_72976_2_ >> 4);
				return var3.getHeightValue(p_72976_1_ & 15, p_72976_2_ & 15);
			}
		} else return 0;
	}
	
	public double getHorizon()
	{
		return worldInfo.getTerrainType() == WorldType.FLAT ? 0.0D : 63.0D;
	}
	
	public int getIndirectPowerLevelTo(int p_72878_1_, int p_72878_2_, int p_72878_3_, int p_72878_4_)
	{
		if(isBlockNormalCube(p_72878_1_, p_72878_2_, p_72878_3_)) return getBlockPowerInput(p_72878_1_, p_72878_2_, p_72878_3_);
		else
		{
			int var5 = getBlockId(p_72878_1_, p_72878_2_, p_72878_3_);
			return var5 == 0 ? 0 : Block.blocksList[var5].isProvidingWeakPower(this, p_72878_1_, p_72878_2_, p_72878_3_, p_72878_4_);
		}
	}
	
	public boolean getIndirectPowerOutput(int p_94574_1_, int p_94574_2_, int p_94574_3_, int p_94574_4_)
	{
		return getIndirectPowerLevelTo(p_94574_1_, p_94574_2_, p_94574_3_, p_94574_4_) > 0;
	}
	
	@Override public float getLightBrightness(int p_72801_1_, int p_72801_2_, int p_72801_3_)
	{
		return provider.lightBrightnessTable[getBlockLightValue(p_72801_1_, p_72801_2_, p_72801_3_)];
	}
	
	@Override public int getLightBrightnessForSkyBlocks(int par1, int par2, int par3, int par4)
	{
		int var5 = getSkyBlockTypeBrightness(EnumSkyBlock.Sky, par1, par2, par3);
		int var6 = getSkyBlockTypeBrightness(EnumSkyBlock.Block, par1, par2, par3);
		if(var6 < par4)
		{
			var6 = par4;
		}
		return var5 << 20 | var6 << 4;
	}
	
	public List getLoadedEntityList()
	{
		return loadedEntityList;
	}
	
	public int getMoonPhase()
	{
		return provider.getMoonPhase(worldInfo.getWorldTime());
	}
	
	public PathEntity getPathEntityToEntity(Entity p_72865_1_, Entity p_72865_2_, float p_72865_3_, boolean p_72865_4_, boolean p_72865_5_, boolean p_72865_6_, boolean p_72865_7_)
	{
		theProfiler.startSection("pathfind");
		int var8 = MathHelper.floor_double(p_72865_1_.posX);
		int var9 = MathHelper.floor_double(p_72865_1_.posY + 1.0D);
		int var10 = MathHelper.floor_double(p_72865_1_.posZ);
		int var11 = (int) (p_72865_3_ + 16.0F);
		int var12 = var8 - var11;
		int var13 = var9 - var11;
		int var14 = var10 - var11;
		int var15 = var8 + var11;
		int var16 = var9 + var11;
		int var17 = var10 + var11;
		ChunkCache var18 = new ChunkCache(this, var12, var13, var14, var15, var16, var17, 0);
		PathEntity var19 = new PathFinder(var18, p_72865_4_, p_72865_5_, p_72865_6_, p_72865_7_).createEntityPathTo(p_72865_1_, p_72865_2_, p_72865_3_);
		theProfiler.endSection();
		return var19;
	}
	
	public List getPendingBlockUpdates(Chunk p_72920_1_, boolean p_72920_2_)
	{
		return null;
	}
	
	public EntityPlayer getPlayerEntityByName(String p_72924_1_)
	{
		for(int var2 = 0; var2 < playerEntities.size(); ++var2)
		{
			if(p_72924_1_.equals(((EntityPlayer) playerEntities.get(var2)).username)) return (EntityPlayer) playerEntities.get(var2);
		}
		return null;
	}
	
	public int getPrecipitationHeight(int p_72874_1_, int p_72874_2_)
	{
		return getChunkFromBlockCoords(p_72874_1_, p_72874_2_).getPrecipitationHeight(p_72874_1_ & 15, p_72874_2_ & 15);
	}
	
	public String getProviderName()
	{
		return chunkProvider.makeString();
	}
	
	public float getRainStrength(float p_72867_1_)
	{
		return prevRainingStrength + (rainingStrength - prevRainingStrength) * p_72867_1_;
	}
	
	public int getSavedLightValue(EnumSkyBlock p_72972_1_, int p_72972_2_, int p_72972_3_, int p_72972_4_)
	{
		if(p_72972_3_ < 0)
		{
			p_72972_3_ = 0;
		}
		if(p_72972_3_ >= 256)
		{
			p_72972_3_ = 255;
		}
		if(p_72972_2_ >= -30000000 && p_72972_4_ >= -30000000 && p_72972_2_ < 30000000 && p_72972_4_ < 30000000)
		{
			int var5 = p_72972_2_ >> 4;
			int var6 = p_72972_4_ >> 4;
			if(!chunkExists(var5, var6)) return p_72972_1_.defaultLightValue;
			else
			{
				Chunk var7 = getChunkFromChunkCoords(var5, var6);
				return var7.getSavedLightValue(p_72972_1_, p_72972_2_ & 15, p_72972_3_, p_72972_4_ & 15);
			}
		} else return p_72972_1_.defaultLightValue;
	}
	
	public ISaveHandler getSaveHandler()
	{
		return saveHandler;
	}
	
	public Scoreboard getScoreboard()
	{
		return worldScoreboard;
	}
	
	public long getSeed()
	{
		return worldInfo.getSeed();
	}
	
	public int getSkyBlockTypeBrightness(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
	{
		if(provider.hasNoSky && par1EnumSkyBlock == EnumSkyBlock.Sky) return 0;
		else
		{
			if(par3 < 0)
			{
				par3 = 0;
			}
			if(par3 >= 256) return par1EnumSkyBlock.defaultLightValue;
			else if(par2 >= -30000000 && par4 >= -30000000 && par2 < 30000000 && par4 < 30000000)
			{
				int var5 = par2 >> 4;
				int var6 = par4 >> 4;
				if(!chunkExists(var5, var6)) return par1EnumSkyBlock.defaultLightValue;
				else if(Block.useNeighborBrightness[getBlockId(par2, par3, par4)])
				{
					int var12 = getSavedLightValue(par1EnumSkyBlock, par2, par3 + 1, par4);
					int var8 = getSavedLightValue(par1EnumSkyBlock, par2 + 1, par3, par4);
					int var9 = getSavedLightValue(par1EnumSkyBlock, par2 - 1, par3, par4);
					int var10 = getSavedLightValue(par1EnumSkyBlock, par2, par3, par4 + 1);
					int var11 = getSavedLightValue(par1EnumSkyBlock, par2, par3, par4 - 1);
					if(var8 > var12)
					{
						var12 = var8;
					}
					if(var9 > var12)
					{
						var12 = var9;
					}
					if(var10 > var12)
					{
						var12 = var10;
					}
					if(var11 > var12)
					{
						var12 = var11;
					}
					return var12;
				} else
				{
					Chunk var7 = getChunkFromChunkCoords(var5, var6);
					return var7.getSavedLightValue(par1EnumSkyBlock, par2 & 15, par3, par4 & 15);
				}
			} else return par1EnumSkyBlock.defaultLightValue;
		}
	}
	
	public Vec3 getSkyColor(Entity par1Entity, float par2)
	{
		float var3 = getCelestialAngle(par2);
		float var4 = MathHelper.cos(var3 * (float) Math.PI * 2.0F) * 2.0F + 0.5F;
		if(var4 < 0.0F)
		{
			var4 = 0.0F;
		}
		if(var4 > 1.0F)
		{
			var4 = 1.0F;
		}
		int var5 = MathHelper.floor_double(par1Entity.posX);
		int var6 = MathHelper.floor_double(par1Entity.posZ);
		BiomeGenBase var7 = getBiomeGenForCoords(var5, var6);
		float var8 = var7.getFloatTemperature();
		int var9 = var7.getSkyColorByTemp(var8);
		float var10 = (var9 >> 16 & 255) / 255.0F;
		float var11 = (var9 >> 8 & 255) / 255.0F;
		float var12 = (var9 & 255) / 255.0F;
		var10 *= var4;
		var11 *= var4;
		var12 *= var4;
		float var13 = getRainStrength(par2);
		float var14;
		float var15;
		if(var13 > 0.0F)
		{
			var14 = (var10 * 0.3F + var11 * 0.59F + var12 * 0.11F) * 0.6F;
			var15 = 1.0F - var13 * 0.75F;
			var10 = var10 * var15 + var14 * (1.0F - var15);
			var11 = var11 * var15 + var14 * (1.0F - var15);
			var12 = var12 * var15 + var14 * (1.0F - var15);
		}
		var14 = getWeightedThunderStrength(par2);
		if(var14 > 0.0F)
		{
			var15 = (var10 * 0.3F + var11 * 0.59F + var12 * 0.11F) * 0.2F;
			float var16 = 1.0F - var14 * 0.75F;
			var10 = var10 * var16 + var15 * (1.0F - var16);
			var11 = var11 * var16 + var15 * (1.0F - var16);
			var12 = var12 * var16 + var15 * (1.0F - var16);
		}
		if(lastLightningBolt > 0)
		{
			var15 = lastLightningBolt - par2;
			if(var15 > 1.0F)
			{
				var15 = 1.0F;
			}
			var15 *= 0.45F;
			var10 = var10 * (1.0F - var15) + 0.8F * var15;
			var11 = var11 * (1.0F - var15) + 0.8F * var15;
			var12 = var12 * (1.0F - var15) + 1.0F * var15;
		}
		return getWorldVec3Pool().getVecFromPool(var10, var11, var12);
	}
	
	public ChunkCoordinates getSpawnPoint()
	{
		return new ChunkCoordinates(worldInfo.getSpawnX(), worldInfo.getSpawnY(), worldInfo.getSpawnZ());
	}
	
	public float getStarBrightness(float par1)
	{
		float var2 = getCelestialAngle(par1);
		float var3 = 1.0F - (MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);
		if(var3 < 0.0F)
		{
			var3 = 0.0F;
		}
		if(var3 > 1.0F)
		{
			var3 = 1.0F;
		}
		return var3 * var3 * 0.5F;
	}
	
	public int getStrongestIndirectPower(int p_94572_1_, int p_94572_2_, int p_94572_3_)
	{
		int var4 = 0;
		for(int var5 = 0; var5 < 6; ++var5)
		{
			int var6 = getIndirectPowerLevelTo(p_94572_1_ + Facing.offsetsXForSide[var5], p_94572_2_ + Facing.offsetsYForSide[var5], p_94572_3_ + Facing.offsetsZForSide[var5], var5);
			if(var6 >= 15) return 15;
			if(var6 > var4)
			{
				var4 = var6;
			}
		}
		return var4;
	}
	
	public float getSunBrightness(float par1)
	{
		float var2 = getCelestialAngle(par1);
		float var3 = 1.0F - (MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.2F);
		if(var3 < 0.0F)
		{
			var3 = 0.0F;
		}
		if(var3 > 1.0F)
		{
			var3 = 1.0F;
		}
		var3 = 1.0F - var3;
		var3 = (float) (var3 * (1.0D - getRainStrength(par1) * 5.0F / 16.0D));
		var3 = (float) (var3 * (1.0D - getWeightedThunderStrength(par1) * 5.0F / 16.0D));
		return var3 * 0.8F + 0.2F;
	}
	
	public int getTopSolidOrLiquidBlock(int p_72825_1_, int p_72825_2_)
	{
		Chunk var3 = getChunkFromBlockCoords(p_72825_1_, p_72825_2_);
		int var4 = var3.getTopFilledSegment() + 15;
		p_72825_1_ &= 15;
		for(p_72825_2_ &= 15; var4 > 0; --var4)
		{
			int var5 = var3.getBlockID(p_72825_1_, var4, p_72825_2_);
			if(var5 != 0 && Block.blocksList[var5].blockMaterial.blocksMovement() && Block.blocksList[var5].blockMaterial != Material.leaves) return var4 + 1;
		}
		return -1;
	}
	
	public long getTotalWorldTime()
	{
		return worldInfo.getWorldTotalTime();
	}
	
	public int getUniqueDataId(String p_72841_1_)
	{
		return mapStorage.getUniqueDataId(p_72841_1_);
	}
	
	public float getWeightedThunderStrength(float p_72819_1_)
	{
		return (prevThunderingStrength + (thunderingStrength - prevThunderingStrength) * p_72819_1_) * getRainStrength(p_72819_1_);
	}
	
	public WorldChunkManager getWorldChunkManager()
	{
		return provider.worldChunkMgr;
	}
	
	public WorldInfo getWorldInfo()
	{
		return worldInfo;
	}
	
	public ILogAgent getWorldLogAgent()
	{
		return worldLogAgent;
	}
	
	public long getWorldTime()
	{
		return worldInfo.getWorldTime();
	}
	
	@Override public Vec3Pool getWorldVec3Pool()
	{
		return vecPool;
	}
	
	public boolean handleMaterialAcceleration(AxisAlignedBB p_72918_1_, Material p_72918_2_, Entity p_72918_3_)
	{
		int var4 = MathHelper.floor_double(p_72918_1_.minX);
		int var5 = MathHelper.floor_double(p_72918_1_.maxX + 1.0D);
		int var6 = MathHelper.floor_double(p_72918_1_.minY);
		int var7 = MathHelper.floor_double(p_72918_1_.maxY + 1.0D);
		int var8 = MathHelper.floor_double(p_72918_1_.minZ);
		int var9 = MathHelper.floor_double(p_72918_1_.maxZ + 1.0D);
		if(!checkChunksExist(var4, var6, var8, var5, var7, var9)) return false;
		else
		{
			boolean var10 = false;
			Vec3 var11 = getWorldVec3Pool().getVecFromPool(0.0D, 0.0D, 0.0D);
			for(int var12 = var4; var12 < var5; ++var12)
			{
				for(int var13 = var6; var13 < var7; ++var13)
				{
					for(int var14 = var8; var14 < var9; ++var14)
					{
						Block var15 = Block.blocksList[getBlockId(var12, var13, var14)];
						if(var15 != null && var15.blockMaterial == p_72918_2_)
						{
							double var16 = var13 + 1 - BlockFluid.getFluidHeightPercent(getBlockMetadata(var12, var13, var14));
							if(var7 >= var16)
							{
								var10 = true;
								var15.velocityToAddToEntity(this, var12, var13, var14, p_72918_3_, var11);
							}
						}
					}
				}
			}
			if(var11.lengthVector() > 0.0D && p_72918_3_.func_96092_aw())
			{
				var11 = var11.normalize();
				double var18 = 0.014D;
				p_72918_3_.motionX += var11.xCoord * var18;
				p_72918_3_.motionY += var11.yCoord * var18;
				p_72918_3_.motionZ += var11.zCoord * var18;
			}
			return var10;
		}
	}
	
	protected void initialize(WorldSettings p_72963_1_)
	{
		worldInfo.setServerInitialized(true);
	}
	
	public boolean isAABBInMaterial(AxisAlignedBB p_72830_1_, Material p_72830_2_)
	{
		int var3 = MathHelper.floor_double(p_72830_1_.minX);
		int var4 = MathHelper.floor_double(p_72830_1_.maxX + 1.0D);
		int var5 = MathHelper.floor_double(p_72830_1_.minY);
		int var6 = MathHelper.floor_double(p_72830_1_.maxY + 1.0D);
		int var7 = MathHelper.floor_double(p_72830_1_.minZ);
		int var8 = MathHelper.floor_double(p_72830_1_.maxZ + 1.0D);
		for(int var9 = var3; var9 < var4; ++var9)
		{
			for(int var10 = var5; var10 < var6; ++var10)
			{
				for(int var11 = var7; var11 < var8; ++var11)
				{
					Block var12 = Block.blocksList[getBlockId(var9, var10, var11)];
					if(var12 != null && var12.blockMaterial == p_72830_2_)
					{
						int var13 = getBlockMetadata(var9, var10, var11);
						double var14 = var10 + 1;
						if(var13 < 8)
						{
							var14 = var10 + 1 - var13 / 8.0D;
						}
						if(var14 >= p_72830_1_.minY) return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override public boolean isAirBlock(int p_72799_1_, int p_72799_2_, int p_72799_3_)
	{
		return getBlockId(p_72799_1_, p_72799_2_, p_72799_3_) == 0;
	}
	
	public boolean isAnyLiquid(AxisAlignedBB p_72953_1_)
	{
		int var2 = MathHelper.floor_double(p_72953_1_.minX);
		int var3 = MathHelper.floor_double(p_72953_1_.maxX + 1.0D);
		int var4 = MathHelper.floor_double(p_72953_1_.minY);
		int var5 = MathHelper.floor_double(p_72953_1_.maxY + 1.0D);
		int var6 = MathHelper.floor_double(p_72953_1_.minZ);
		int var7 = MathHelper.floor_double(p_72953_1_.maxZ + 1.0D);
		if(p_72953_1_.minX < 0.0D)
		{
			--var2;
		}
		if(p_72953_1_.minY < 0.0D)
		{
			--var4;
		}
		if(p_72953_1_.minZ < 0.0D)
		{
			--var6;
		}
		for(int var8 = var2; var8 < var3; ++var8)
		{
			for(int var9 = var4; var9 < var5; ++var9)
			{
				for(int var10 = var6; var10 < var7; ++var10)
				{
					Block var11 = Block.blocksList[getBlockId(var8, var9, var10)];
					if(var11 != null && var11.blockMaterial.isLiquid()) return true;
				}
			}
		}
		return false;
	}
	
	public boolean isBlockFreezable(int p_72884_1_, int p_72884_2_, int p_72884_3_)
	{
		return canBlockFreeze(p_72884_1_, p_72884_2_, p_72884_3_, false);
	}
	
	public boolean isBlockFreezableNaturally(int p_72850_1_, int p_72850_2_, int p_72850_3_)
	{
		return canBlockFreeze(p_72850_1_, p_72850_2_, p_72850_3_, true);
	}
	
	public boolean isBlockFullCube(int p_85174_1_, int p_85174_2_, int p_85174_3_)
	{
		int var4 = getBlockId(p_85174_1_, p_85174_2_, p_85174_3_);
		if(var4 != 0 && Block.blocksList[var4] != null)
		{
			AxisAlignedBB var5 = Block.blocksList[var4].getCollisionBoundingBoxFromPool(this, p_85174_1_, p_85174_2_, p_85174_3_);
			return var5 != null && var5.getAverageEdgeLength() >= 1.0D;
		} else return false;
	}
	
	public boolean isBlockHighHumidity(int p_72958_1_, int p_72958_2_, int p_72958_3_)
	{
		BiomeGenBase var4 = getBiomeGenForCoords(p_72958_1_, p_72958_3_);
		return var4.isHighHumidity();
	}
	
	public boolean isBlockIndirectlyGettingPowered(int p_72864_1_, int p_72864_2_, int p_72864_3_)
	{
		return getIndirectPowerLevelTo(p_72864_1_, p_72864_2_ - 1, p_72864_3_, 0) > 0 ? true : getIndirectPowerLevelTo(p_72864_1_, p_72864_2_ + 1, p_72864_3_, 1) > 0 ? true : getIndirectPowerLevelTo(p_72864_1_, p_72864_2_, p_72864_3_ - 1, 2) > 0 ? true : getIndirectPowerLevelTo(p_72864_1_, p_72864_2_, p_72864_3_ + 1, 3) > 0 ? true : getIndirectPowerLevelTo(p_72864_1_ - 1, p_72864_2_, p_72864_3_, 4) > 0 ? true : getIndirectPowerLevelTo(p_72864_1_ + 1, p_72864_2_, p_72864_3_, 5) > 0;
	}
	
	@Override public boolean isBlockNormalCube(int p_72809_1_, int p_72809_2_, int p_72809_3_)
	{
		return Block.isNormalCube(getBlockId(p_72809_1_, p_72809_2_, p_72809_3_));
	}
	
	public boolean isBlockNormalCubeDefault(int p_72887_1_, int p_72887_2_, int p_72887_3_, boolean p_72887_4_)
	{
		if(p_72887_1_ >= -30000000 && p_72887_3_ >= -30000000 && p_72887_1_ < 30000000 && p_72887_3_ < 30000000)
		{
			Chunk var5 = chunkProvider.provideChunk(p_72887_1_ >> 4, p_72887_3_ >> 4);
			if(var5 != null && !var5.isEmpty())
			{
				Block var6 = Block.blocksList[getBlockId(p_72887_1_, p_72887_2_, p_72887_3_)];
				return var6 == null ? false : var6.blockMaterial.isOpaque() && var6.renderAsNormalBlock();
			} else return p_72887_4_;
		} else return p_72887_4_;
	}
	
	@Override public boolean isBlockOpaqueCube(int p_72804_1_, int p_72804_2_, int p_72804_3_)
	{
		Block var4 = Block.blocksList[getBlockId(p_72804_1_, p_72804_2_, p_72804_3_)];
		return var4 == null ? false : var4.isOpaqueCube();
	}
	
	@Override public int isBlockProvidingPowerTo(int p_72879_1_, int p_72879_2_, int p_72879_3_, int p_72879_4_)
	{
		int var5 = getBlockId(p_72879_1_, p_72879_2_, p_72879_3_);
		return var5 == 0 ? 0 : Block.blocksList[var5].isProvidingStrongPower(this, p_72879_1_, p_72879_2_, p_72879_3_, p_72879_4_);
	}
	
	public boolean isBlockTickScheduledThisTick(int p_94573_1_, int p_94573_2_, int p_94573_3_, int p_94573_4_)
	{
		return false;
	}
	
	public boolean isBlockTopFacingSurfaceSolid(Block p_102026_1_, int p_102026_2_)
	{
		return p_102026_1_ == null ? false : p_102026_1_.blockMaterial.isOpaque() && p_102026_1_.renderAsNormalBlock() ? true : p_102026_1_ instanceof BlockStairs ? (p_102026_2_ & 4) == 4 : p_102026_1_ instanceof BlockHalfSlab ? (p_102026_2_ & 8) == 8 : p_102026_1_ instanceof BlockHopper ? true : p_102026_1_ instanceof BlockSnow ? (p_102026_2_ & 7) == 7 : false;
	}
	
	public boolean isBoundingBoxBurning(AxisAlignedBB p_72978_1_)
	{
		int var2 = MathHelper.floor_double(p_72978_1_.minX);
		int var3 = MathHelper.floor_double(p_72978_1_.maxX + 1.0D);
		int var4 = MathHelper.floor_double(p_72978_1_.minY);
		int var5 = MathHelper.floor_double(p_72978_1_.maxY + 1.0D);
		int var6 = MathHelper.floor_double(p_72978_1_.minZ);
		int var7 = MathHelper.floor_double(p_72978_1_.maxZ + 1.0D);
		if(checkChunksExist(var2, var4, var6, var3, var5, var7))
		{
			for(int var8 = var2; var8 < var3; ++var8)
			{
				for(int var9 = var4; var9 < var5; ++var9)
				{
					for(int var10 = var6; var10 < var7; ++var10)
					{
						int var11 = getBlockId(var8, var9, var10);
						if(var11 == Block.fire.blockID || var11 == Block.lavaMoving.blockID || var11 == Block.lavaStill.blockID) return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isDaytime()
	{
		return skylightSubtracted < 4;
	}
	
	public boolean isMaterialInBB(AxisAlignedBB p_72875_1_, Material p_72875_2_)
	{
		int var3 = MathHelper.floor_double(p_72875_1_.minX);
		int var4 = MathHelper.floor_double(p_72875_1_.maxX + 1.0D);
		int var5 = MathHelper.floor_double(p_72875_1_.minY);
		int var6 = MathHelper.floor_double(p_72875_1_.maxY + 1.0D);
		int var7 = MathHelper.floor_double(p_72875_1_.minZ);
		int var8 = MathHelper.floor_double(p_72875_1_.maxZ + 1.0D);
		for(int var9 = var3; var9 < var4; ++var9)
		{
			for(int var10 = var5; var10 < var6; ++var10)
			{
				for(int var11 = var7; var11 < var8; ++var11)
				{
					Block var12 = Block.blocksList[getBlockId(var9, var10, var11)];
					if(var12 != null && var12.blockMaterial == p_72875_2_) return true;
				}
			}
		}
		return false;
	}
	
	public boolean isRaining()
	{
		return getRainStrength(1.0F) > 0.2D;
	}
	
	public boolean isThundering()
	{
		return getWeightedThunderStrength(1.0F) > 0.9D;
	}
	
	public void joinEntityInSurroundings(Entity par1Entity)
	{
		int var2 = MathHelper.floor_double(par1Entity.posX / 16.0D);
		int var3 = MathHelper.floor_double(par1Entity.posZ / 16.0D);
		byte var4 = 2;
		for(int var5 = var2 - var4; var5 <= var2 + var4; ++var5)
		{
			for(int var6 = var3 - var4; var6 <= var3 + var4; ++var6)
			{
				getChunkFromChunkCoords(var5, var6);
			}
		}
		if(!loadedEntityList.contains(par1Entity))
		{
			loadedEntityList.add(par1Entity);
		}
	}
	
	public WorldSavedData loadItemData(Class p_72943_1_, String p_72943_2_)
	{
		return mapStorage.loadData(p_72943_1_, p_72943_2_);
	}
	
	public void markBlockForRenderUpdate(int p_72902_1_, int p_72902_2_, int p_72902_3_)
	{
		for(int var4 = 0; var4 < worldAccesses.size(); ++var4)
		{
			((IWorldAccess) worldAccesses.get(var4)).markBlockForRenderUpdate(p_72902_1_, p_72902_2_, p_72902_3_);
		}
	}
	
	public void markBlockForUpdate(int p_72845_1_, int p_72845_2_, int p_72845_3_)
	{
		for(int var4 = 0; var4 < worldAccesses.size(); ++var4)
		{
			((IWorldAccess) worldAccesses.get(var4)).markBlockForUpdate(p_72845_1_, p_72845_2_, p_72845_3_);
		}
	}
	
	public void markBlockRangeForRenderUpdate(int p_72909_1_, int p_72909_2_, int p_72909_3_, int p_72909_4_, int p_72909_5_, int p_72909_6_)
	{
		for(int var7 = 0; var7 < worldAccesses.size(); ++var7)
		{
			((IWorldAccess) worldAccesses.get(var7)).markBlockRangeForRenderUpdate(p_72909_1_, p_72909_2_, p_72909_3_, p_72909_4_, p_72909_5_, p_72909_6_);
		}
	}
	
	public void markBlocksDirtyVertical(int p_72975_1_, int p_72975_2_, int p_72975_3_, int p_72975_4_)
	{
		int var5;
		if(p_72975_3_ > p_72975_4_)
		{
			var5 = p_72975_4_;
			p_72975_4_ = p_72975_3_;
			p_72975_3_ = var5;
		}
		if(!provider.hasNoSky)
		{
			for(var5 = p_72975_3_; var5 <= p_72975_4_; ++var5)
			{
				updateLightByType(EnumSkyBlock.Sky, p_72975_1_, var5, p_72975_2_);
			}
		}
		markBlockRangeForRenderUpdate(p_72975_1_, p_72975_3_, p_72975_2_, p_72975_1_, p_72975_4_, p_72975_2_);
	}
	
	public void markTileEntityForDespawn(TileEntity p_72928_1_)
	{
		entityRemoval.add(p_72928_1_);
	}
	
	protected void moodSoundAndLightCheck(int p_72941_1_, int p_72941_2_, Chunk p_72941_3_)
	{
		theProfiler.endStartSection("moodSound");
		if(ambientTickCountdown == 0 && !isRemote)
		{
			updateLCG = updateLCG * 3 + 1013904223;
			int var4 = updateLCG >> 2;
			int var5 = var4 & 15;
			int var6 = var4 >> 8 & 15;
			int var7 = var4 >> 16 & 127;
			int var8 = p_72941_3_.getBlockID(var5, var7, var6);
			var5 += p_72941_1_;
			var6 += p_72941_2_;
			if(var8 == 0 && getFullBlockLightValue(var5, var7, var6) <= rand.nextInt(8) && getSavedLightValue(EnumSkyBlock.Sky, var5, var7, var6) <= 0)
			{
				EntityPlayer var9 = getClosestPlayer(var5 + 0.5D, var7 + 0.5D, var6 + 0.5D, 8.0D);
				if(var9 != null && var9.getDistanceSq(var5 + 0.5D, var7 + 0.5D, var6 + 0.5D) > 4.0D)
				{
					playSoundEffect(var5 + 0.5D, var7 + 0.5D, var6 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + rand.nextFloat() * 0.2F);
					ambientTickCountdown = rand.nextInt(12000) + 6000;
				}
			}
		}
		theProfiler.endStartSection("checkLight");
		p_72941_3_.enqueueRelightChecks();
	}
	
	public Explosion newExplosion(Entity p_72885_1_, double p_72885_2_, double p_72885_4_, double p_72885_6_, float p_72885_8_, boolean p_72885_9_, boolean p_72885_10_)
	{
		Explosion var11 = new Explosion(this, p_72885_1_, p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_);
		var11.isFlaming = p_72885_9_;
		var11.isSmoking = p_72885_10_;
		var11.doExplosionA();
		var11.doExplosionB(true);
		return var11;
	}
	
	public void notifyBlockChange(int p_72851_1_, int p_72851_2_, int p_72851_3_, int p_72851_4_)
	{
		this.notifyBlocksOfNeighborChange(p_72851_1_, p_72851_2_, p_72851_3_, p_72851_4_);
	}
	
	public void notifyBlockOfNeighborChange(int p_72821_1_, int p_72821_2_, int p_72821_3_, int p_72821_4_)
	{
		if(!isRemote)
		{
			int var5 = getBlockId(p_72821_1_, p_72821_2_, p_72821_3_);
			Block var6 = Block.blocksList[var5];
			if(var6 != null)
			{
				try
				{
					var6.onNeighborBlockChange(this, p_72821_1_, p_72821_2_, p_72821_3_, p_72821_4_);
				} catch(Throwable var13)
				{
					CrashReport var8 = CrashReport.makeCrashReport(var13, "Exception while updating neighbours");
					CrashReportCategory var9 = var8.makeCategory("Block being updated");
					int var10;
					try
					{
						var10 = getBlockMetadata(p_72821_1_, p_72821_2_, p_72821_3_);
					} catch(Throwable var12)
					{
						var10 = -1;
					}
					var9.addCrashSectionCallable("Source block type", new CallableLvl1(this, p_72821_4_));
					CrashReportCategory.func_85068_a(var9, p_72821_1_, p_72821_2_, p_72821_3_, var5, var10);
					throw new ReportedException(var8);
				}
			}
		}
	}
	
	public void notifyBlocksOfNeighborChange(int p_72898_1_, int p_72898_2_, int p_72898_3_, int p_72898_4_)
	{
		notifyBlockOfNeighborChange(p_72898_1_ - 1, p_72898_2_, p_72898_3_, p_72898_4_);
		notifyBlockOfNeighborChange(p_72898_1_ + 1, p_72898_2_, p_72898_3_, p_72898_4_);
		notifyBlockOfNeighborChange(p_72898_1_, p_72898_2_ - 1, p_72898_3_, p_72898_4_);
		notifyBlockOfNeighborChange(p_72898_1_, p_72898_2_ + 1, p_72898_3_, p_72898_4_);
		notifyBlockOfNeighborChange(p_72898_1_, p_72898_2_, p_72898_3_ - 1, p_72898_4_);
		notifyBlockOfNeighborChange(p_72898_1_, p_72898_2_, p_72898_3_ + 1, p_72898_4_);
	}
	
	public void notifyBlocksOfNeighborChange(int p_96439_1_, int p_96439_2_, int p_96439_3_, int p_96439_4_, int p_96439_5_)
	{
		if(p_96439_5_ != 4)
		{
			notifyBlockOfNeighborChange(p_96439_1_ - 1, p_96439_2_, p_96439_3_, p_96439_4_);
		}
		if(p_96439_5_ != 5)
		{
			notifyBlockOfNeighborChange(p_96439_1_ + 1, p_96439_2_, p_96439_3_, p_96439_4_);
		}
		if(p_96439_5_ != 0)
		{
			notifyBlockOfNeighborChange(p_96439_1_, p_96439_2_ - 1, p_96439_3_, p_96439_4_);
		}
		if(p_96439_5_ != 1)
		{
			notifyBlockOfNeighborChange(p_96439_1_, p_96439_2_ + 1, p_96439_3_, p_96439_4_);
		}
		if(p_96439_5_ != 2)
		{
			notifyBlockOfNeighborChange(p_96439_1_, p_96439_2_, p_96439_3_ - 1, p_96439_4_);
		}
		if(p_96439_5_ != 3)
		{
			notifyBlockOfNeighborChange(p_96439_1_, p_96439_2_, p_96439_3_ + 1, p_96439_4_);
		}
	}
	
	protected void onEntityAdded(Entity p_72923_1_)
	{
		for(int var2 = 0; var2 < worldAccesses.size(); ++var2)
		{
			((IWorldAccess) worldAccesses.get(var2)).onEntityCreate(p_72923_1_);
		}
	}
	
	protected void onEntityRemoved(Entity p_72847_1_)
	{
		for(int var2 = 0; var2 < worldAccesses.size(); ++var2)
		{
			((IWorldAccess) worldAccesses.get(var2)).onEntityDestroy(p_72847_1_);
		}
	}
	
	public void playAuxSFX(int p_72926_1_, int p_72926_2_, int p_72926_3_, int p_72926_4_, int p_72926_5_)
	{
		playAuxSFXAtEntity((EntityPlayer) null, p_72926_1_, p_72926_2_, p_72926_3_, p_72926_4_, p_72926_5_);
	}
	
	public void playAuxSFXAtEntity(EntityPlayer p_72889_1_, int p_72889_2_, int p_72889_3_, int p_72889_4_, int p_72889_5_, int p_72889_6_)
	{
		try
		{
			for(int var7 = 0; var7 < worldAccesses.size(); ++var7)
			{
				((IWorldAccess) worldAccesses.get(var7)).playAuxSFX(p_72889_1_, p_72889_2_, p_72889_3_, p_72889_4_, p_72889_5_, p_72889_6_);
			}
		} catch(Throwable var10)
		{
			CrashReport var8 = CrashReport.makeCrashReport(var10, "Playing level event");
			CrashReportCategory var9 = var8.makeCategory("Level event being played");
			var9.addCrashSection("Block coordinates", CrashReportCategory.getLocationInfo(p_72889_3_, p_72889_4_, p_72889_5_));
			var9.addCrashSection("Event source", p_72889_1_);
			var9.addCrashSection("Event type", Integer.valueOf(p_72889_2_));
			var9.addCrashSection("Event data", Integer.valueOf(p_72889_6_));
			throw new ReportedException(var8);
		}
	}
	
	public void playRecord(String p_72934_1_, int p_72934_2_, int p_72934_3_, int p_72934_4_)
	{
		for(int var5 = 0; var5 < worldAccesses.size(); ++var5)
		{
			((IWorldAccess) worldAccesses.get(var5)).playRecord(p_72934_1_, p_72934_2_, p_72934_3_, p_72934_4_);
		}
	}
	
	public void playSound(double p_72980_1_, double p_72980_3_, double p_72980_5_, String p_72980_7_, float p_72980_8_, float p_72980_9_, boolean p_72980_10_)
	{
	}
	
	public void playSoundAtEntity(Entity p_72956_1_, String p_72956_2_, float p_72956_3_, float p_72956_4_)
	{
		if(p_72956_1_ != null && p_72956_2_ != null)
		{
			for(int var5 = 0; var5 < worldAccesses.size(); ++var5)
			{
				((IWorldAccess) worldAccesses.get(var5)).playSound(p_72956_2_, p_72956_1_.posX, p_72956_1_.posY - p_72956_1_.yOffset, p_72956_1_.posZ, p_72956_3_, p_72956_4_);
			}
		}
	}
	
	public void playSoundEffect(double p_72908_1_, double p_72908_3_, double p_72908_5_, String p_72908_7_, float p_72908_8_, float p_72908_9_)
	{
		if(p_72908_7_ != null)
		{
			for(int var10 = 0; var10 < worldAccesses.size(); ++var10)
			{
				((IWorldAccess) worldAccesses.get(var10)).playSound(p_72908_7_, p_72908_1_, p_72908_3_, p_72908_5_, p_72908_8_, p_72908_9_);
			}
		}
	}
	
	public void playSoundToNearExcept(EntityPlayer p_85173_1_, String p_85173_2_, float p_85173_3_, float p_85173_4_)
	{
		if(p_85173_1_ != null && p_85173_2_ != null)
		{
			for(int var5 = 0; var5 < worldAccesses.size(); ++var5)
			{
				((IWorldAccess) worldAccesses.get(var5)).playSoundToNearExcept(p_85173_1_, p_85173_2_, p_85173_1_.posX, p_85173_1_.posY - p_85173_1_.yOffset, p_85173_1_.posZ, p_85173_3_, p_85173_4_);
			}
		}
	}
	
	public MovingObjectPosition rayTraceBlocks_do_do(Vec3 p_72831_1_, Vec3 p_72831_2_, boolean p_72831_3_, boolean p_72831_4_)
	{
		if(!Double.isNaN(p_72831_1_.xCoord) && !Double.isNaN(p_72831_1_.yCoord) && !Double.isNaN(p_72831_1_.zCoord))
		{
			if(!Double.isNaN(p_72831_2_.xCoord) && !Double.isNaN(p_72831_2_.yCoord) && !Double.isNaN(p_72831_2_.zCoord))
			{
				int var5 = MathHelper.floor_double(p_72831_2_.xCoord);
				int var6 = MathHelper.floor_double(p_72831_2_.yCoord);
				int var7 = MathHelper.floor_double(p_72831_2_.zCoord);
				int var8 = MathHelper.floor_double(p_72831_1_.xCoord);
				int var9 = MathHelper.floor_double(p_72831_1_.yCoord);
				int var10 = MathHelper.floor_double(p_72831_1_.zCoord);
				int var11 = getBlockId(var8, var9, var10);
				int var12 = getBlockMetadata(var8, var9, var10);
				Block var13 = Block.blocksList[var11];
				if((!p_72831_4_ || var13 == null || var13.getCollisionBoundingBoxFromPool(this, var8, var9, var10) != null) && var11 > 0 && var13.canCollideCheck(var12, p_72831_3_))
				{
					MovingObjectPosition var14 = var13.collisionRayTrace(this, var8, var9, var10, p_72831_1_, p_72831_2_);
					if(var14 != null) return var14;
				}
				var11 = 200;
				while(var11-- >= 0)
				{
					if(Double.isNaN(p_72831_1_.xCoord) || Double.isNaN(p_72831_1_.yCoord) || Double.isNaN(p_72831_1_.zCoord)) return null;
					if(var8 == var5 && var9 == var6 && var10 == var7) return null;
					boolean var39 = true;
					boolean var40 = true;
					boolean var41 = true;
					double var15 = 999.0D;
					double var17 = 999.0D;
					double var19 = 999.0D;
					if(var5 > var8)
					{
						var15 = var8 + 1.0D;
					} else if(var5 < var8)
					{
						var15 = var8 + 0.0D;
					} else
					{
						var39 = false;
					}
					if(var6 > var9)
					{
						var17 = var9 + 1.0D;
					} else if(var6 < var9)
					{
						var17 = var9 + 0.0D;
					} else
					{
						var40 = false;
					}
					if(var7 > var10)
					{
						var19 = var10 + 1.0D;
					} else if(var7 < var10)
					{
						var19 = var10 + 0.0D;
					} else
					{
						var41 = false;
					}
					double var21 = 999.0D;
					double var23 = 999.0D;
					double var25 = 999.0D;
					double var27 = p_72831_2_.xCoord - p_72831_1_.xCoord;
					double var29 = p_72831_2_.yCoord - p_72831_1_.yCoord;
					double var31 = p_72831_2_.zCoord - p_72831_1_.zCoord;
					if(var39)
					{
						var21 = (var15 - p_72831_1_.xCoord) / var27;
					}
					if(var40)
					{
						var23 = (var17 - p_72831_1_.yCoord) / var29;
					}
					if(var41)
					{
						var25 = (var19 - p_72831_1_.zCoord) / var31;
					}
					boolean var33 = false;
					byte var42;
					if(var21 < var23 && var21 < var25)
					{
						if(var5 > var8)
						{
							var42 = 4;
						} else
						{
							var42 = 5;
						}
						p_72831_1_.xCoord = var15;
						p_72831_1_.yCoord += var29 * var21;
						p_72831_1_.zCoord += var31 * var21;
					} else if(var23 < var25)
					{
						if(var6 > var9)
						{
							var42 = 0;
						} else
						{
							var42 = 1;
						}
						p_72831_1_.xCoord += var27 * var23;
						p_72831_1_.yCoord = var17;
						p_72831_1_.zCoord += var31 * var23;
					} else
					{
						if(var7 > var10)
						{
							var42 = 2;
						} else
						{
							var42 = 3;
						}
						p_72831_1_.xCoord += var27 * var25;
						p_72831_1_.yCoord += var29 * var25;
						p_72831_1_.zCoord = var19;
					}
					Vec3 var34 = getWorldVec3Pool().getVecFromPool(p_72831_1_.xCoord, p_72831_1_.yCoord, p_72831_1_.zCoord);
					var8 = (int) (var34.xCoord = MathHelper.floor_double(p_72831_1_.xCoord));
					if(var42 == 5)
					{
						--var8;
						++var34.xCoord;
					}
					var9 = (int) (var34.yCoord = MathHelper.floor_double(p_72831_1_.yCoord));
					if(var42 == 1)
					{
						--var9;
						++var34.yCoord;
					}
					var10 = (int) (var34.zCoord = MathHelper.floor_double(p_72831_1_.zCoord));
					if(var42 == 3)
					{
						--var10;
						++var34.zCoord;
					}
					int var35 = getBlockId(var8, var9, var10);
					int var36 = getBlockMetadata(var8, var9, var10);
					Block var37 = Block.blocksList[var35];
					if((!p_72831_4_ || var37 == null || var37.getCollisionBoundingBoxFromPool(this, var8, var9, var10) != null) && var35 > 0 && var37.canCollideCheck(var36, p_72831_3_))
					{
						MovingObjectPosition var38 = var37.collisionRayTrace(this, var8, var9, var10, p_72831_1_, p_72831_2_);
						if(var38 != null) return var38;
					}
				}
				return null;
			} else return null;
		} else return null;
	}
	
	public void removeBlockTileEntity(int p_72932_1_, int p_72932_2_, int p_72932_3_)
	{
		TileEntity var4 = getBlockTileEntity(p_72932_1_, p_72932_2_, p_72932_3_);
		if(var4 != null && scanningTileEntities)
		{
			var4.invalidate();
			addedTileEntityList.remove(var4);
		} else
		{
			if(var4 != null)
			{
				addedTileEntityList.remove(var4);
				loadedTileEntityList.remove(var4);
			}
			Chunk var5 = getChunkFromChunkCoords(p_72932_1_ >> 4, p_72932_3_ >> 4);
			if(var5 != null)
			{
				var5.removeChunkBlockTileEntity(p_72932_1_ & 15, p_72932_2_, p_72932_3_ & 15);
			}
		}
	}
	
	public void removeEntity(Entity p_72900_1_)
	{
		if(p_72900_1_.riddenByEntity != null)
		{
			p_72900_1_.riddenByEntity.mountEntity((Entity) null);
		}
		if(p_72900_1_.ridingEntity != null)
		{
			p_72900_1_.mountEntity((Entity) null);
		}
		p_72900_1_.setDead();
		if(p_72900_1_ instanceof EntityPlayer)
		{
			playerEntities.remove(p_72900_1_);
			updateAllPlayersSleepingFlag();
		}
	}
	
	public void removePlayerEntityDangerously(Entity p_72973_1_)
	{
		p_72973_1_.setDead();
		if(p_72973_1_ instanceof EntityPlayer)
		{
			playerEntities.remove(p_72973_1_);
			updateAllPlayersSleepingFlag();
		}
		int var2 = p_72973_1_.chunkCoordX;
		int var3 = p_72973_1_.chunkCoordZ;
		if(p_72973_1_.addedToChunk && chunkExists(var2, var3))
		{
			getChunkFromChunkCoords(var2, var3).removeEntity(p_72973_1_);
		}
		loadedEntityList.remove(p_72973_1_);
		onEntityRemoved(p_72973_1_);
	}
	
	public void removeWorldAccess(IWorldAccess par1IWorldAccess)
	{
		worldAccesses.remove(par1IWorldAccess);
	}
	
	public void scheduleBlockUpdate(int p_72836_1_, int p_72836_2_, int p_72836_3_, int p_72836_4_, int p_72836_5_)
	{
	}
	
	public void scheduleBlockUpdateFromLoad(int p_72892_1_, int p_72892_2_, int p_72892_3_, int p_72892_4_, int p_72892_5_, int p_72892_6_)
	{
	}
	
	public void scheduleBlockUpdateWithPriority(int p_82740_1_, int p_82740_2_, int p_82740_3_, int p_82740_4_, int p_82740_5_, int p_82740_6_)
	{
	}
	
	public List selectEntitiesWithinAABB(Class p_82733_1_, AxisAlignedBB p_82733_2_, IEntitySelector p_82733_3_)
	{
		int var4 = MathHelper.floor_double((p_82733_2_.minX - 2.0D) / 16.0D);
		int var5 = MathHelper.floor_double((p_82733_2_.maxX + 2.0D) / 16.0D);
		int var6 = MathHelper.floor_double((p_82733_2_.minZ - 2.0D) / 16.0D);
		int var7 = MathHelper.floor_double((p_82733_2_.maxZ + 2.0D) / 16.0D);
		ArrayList var8 = new ArrayList();
		for(int var9 = var4; var9 <= var5; ++var9)
		{
			for(int var10 = var6; var10 <= var7; ++var10)
			{
				if(chunkExists(var9, var10))
				{
					getChunkFromChunkCoords(var9, var10).getEntitiesOfTypeWithinAAAB(p_82733_1_, p_82733_2_, var8, p_82733_3_);
				}
			}
		}
		return var8;
	}
	
	public void sendQuittingDisconnectingPacket()
	{
	}
	
	protected void setActivePlayerChunksAndCheckLight()
	{
		activeChunkSet.clear();
		theProfiler.startSection("buildList");
		int var1;
		EntityPlayer var2;
		int var3;
		int var4;
		for(var1 = 0; var1 < playerEntities.size(); ++var1)
		{
			var2 = (EntityPlayer) playerEntities.get(var1);
			var3 = MathHelper.floor_double(var2.posX / 16.0D);
			var4 = MathHelper.floor_double(var2.posZ / 16.0D);
			byte var5 = 7;
			for(int var6 = -var5; var6 <= var5; ++var6)
			{
				for(int var7 = -var5; var7 <= var5; ++var7)
				{
					activeChunkSet.add(new ChunkCoordIntPair(var6 + var3, var7 + var4));
				}
			}
		}
		theProfiler.endSection();
		if(ambientTickCountdown > 0)
		{
			--ambientTickCountdown;
		}
		theProfiler.startSection("playerCheckLight");
		if(!playerEntities.isEmpty())
		{
			var1 = rand.nextInt(playerEntities.size());
			var2 = (EntityPlayer) playerEntities.get(var1);
			var3 = MathHelper.floor_double(var2.posX) + rand.nextInt(11) - 5;
			var4 = MathHelper.floor_double(var2.posY) + rand.nextInt(11) - 5;
			int var8 = MathHelper.floor_double(var2.posZ) + rand.nextInt(11) - 5;
			updateAllLightTypes(var3, var4, var8);
		}
		theProfiler.endSection();
	}
	
	public void setAllowedSpawnTypes(boolean p_72891_1_, boolean p_72891_2_)
	{
		spawnHostileMobs = p_72891_1_;
		spawnPeacefulMobs = p_72891_2_;
	}
	
	public boolean setBlock(int p_94575_1_, int p_94575_2_, int p_94575_3_, int p_94575_4_)
	{
		return this.setBlock(p_94575_1_, p_94575_2_, p_94575_3_, p_94575_4_, 0, 3);
	}
	
	public boolean setBlock(int p_72832_1_, int p_72832_2_, int p_72832_3_, int p_72832_4_, int p_72832_5_, int p_72832_6_)
	{
		if(p_72832_1_ >= -30000000 && p_72832_3_ >= -30000000 && p_72832_1_ < 30000000 && p_72832_3_ < 30000000)
		{
			if(p_72832_2_ < 0) return false;
			else if(p_72832_2_ >= 256) return false;
			else
			{
				Chunk var7 = getChunkFromChunkCoords(p_72832_1_ >> 4, p_72832_3_ >> 4);
				int var8 = 0;
				if((p_72832_6_ & 1) != 0)
				{
					var8 = var7.getBlockID(p_72832_1_ & 15, p_72832_2_, p_72832_3_ & 15);
				}
				boolean var9 = var7.setBlockIDWithMetadata(p_72832_1_ & 15, p_72832_2_, p_72832_3_ & 15, p_72832_4_, p_72832_5_);
				theProfiler.startSection("checkLight");
				updateAllLightTypes(p_72832_1_, p_72832_2_, p_72832_3_);
				theProfiler.endSection();
				if(var9)
				{
					if((p_72832_6_ & 2) != 0 && (!isRemote || (p_72832_6_ & 4) == 0))
					{
						markBlockForUpdate(p_72832_1_, p_72832_2_, p_72832_3_);
					}
					if(!isRemote && (p_72832_6_ & 1) != 0)
					{
						notifyBlockChange(p_72832_1_, p_72832_2_, p_72832_3_, var8);
						Block var10 = Block.blocksList[p_72832_4_];
						if(var10 != null && var10.hasComparatorInputOverride())
						{
							func_96440_m(p_72832_1_, p_72832_2_, p_72832_3_, p_72832_4_);
						}
					}
				}
				return var9;
			}
		} else return false;
	}
	
	public boolean setBlockMetadataWithNotify(int p_72921_1_, int p_72921_2_, int p_72921_3_, int p_72921_4_, int p_72921_5_)
	{
		if(p_72921_1_ >= -30000000 && p_72921_3_ >= -30000000 && p_72921_1_ < 30000000 && p_72921_3_ < 30000000)
		{
			if(p_72921_2_ < 0) return false;
			else if(p_72921_2_ >= 256) return false;
			else
			{
				Chunk var6 = getChunkFromChunkCoords(p_72921_1_ >> 4, p_72921_3_ >> 4);
				int var7 = p_72921_1_ & 15;
				int var8 = p_72921_3_ & 15;
				boolean var9 = var6.setBlockMetadata(var7, p_72921_2_, var8, p_72921_4_);
				if(var9)
				{
					int var10 = var6.getBlockID(var7, p_72921_2_, var8);
					if((p_72921_5_ & 2) != 0 && (!isRemote || (p_72921_5_ & 4) == 0))
					{
						markBlockForUpdate(p_72921_1_, p_72921_2_, p_72921_3_);
					}
					if(!isRemote && (p_72921_5_ & 1) != 0)
					{
						notifyBlockChange(p_72921_1_, p_72921_2_, p_72921_3_, var10);
						Block var11 = Block.blocksList[var10];
						if(var11 != null && var11.hasComparatorInputOverride())
						{
							func_96440_m(p_72921_1_, p_72921_2_, p_72921_3_, var10);
						}
					}
				}
				return var9;
			}
		} else return false;
	}
	
	public void setBlockTileEntity(int p_72837_1_, int p_72837_2_, int p_72837_3_, TileEntity p_72837_4_)
	{
		if(p_72837_4_ != null && !p_72837_4_.isInvalid())
		{
			if(scanningTileEntities)
			{
				p_72837_4_.xCoord = p_72837_1_;
				p_72837_4_.yCoord = p_72837_2_;
				p_72837_4_.zCoord = p_72837_3_;
				Iterator var5 = addedTileEntityList.iterator();
				while(var5.hasNext())
				{
					TileEntity var6 = (TileEntity) var5.next();
					if(var6.xCoord == p_72837_1_ && var6.yCoord == p_72837_2_ && var6.zCoord == p_72837_3_)
					{
						var6.invalidate();
						var5.remove();
					}
				}
				addedTileEntityList.add(p_72837_4_);
			} else
			{
				loadedTileEntityList.add(p_72837_4_);
				Chunk var7 = getChunkFromChunkCoords(p_72837_1_ >> 4, p_72837_3_ >> 4);
				if(var7 != null)
				{
					var7.setChunkBlockTileEntity(p_72837_1_ & 15, p_72837_2_, p_72837_3_ & 15, p_72837_4_);
				}
			}
		}
	}
	
	public boolean setBlockToAir(int p_94571_1_, int p_94571_2_, int p_94571_3_)
	{
		return this.setBlock(p_94571_1_, p_94571_2_, p_94571_3_, 0, 0, 3);
	}
	
	public void setEntityState(Entity p_72960_1_, byte p_72960_2_)
	{
	}
	
	public void setItemData(String p_72823_1_, WorldSavedData p_72823_2_)
	{
		mapStorage.setData(p_72823_1_, p_72823_2_);
	}
	
	public void setLightValue(EnumSkyBlock p_72915_1_, int p_72915_2_, int p_72915_3_, int p_72915_4_, int p_72915_5_)
	{
		if(p_72915_2_ >= -30000000 && p_72915_4_ >= -30000000 && p_72915_2_ < 30000000 && p_72915_4_ < 30000000)
		{
			if(p_72915_3_ >= 0)
			{
				if(p_72915_3_ < 256)
				{
					if(chunkExists(p_72915_2_ >> 4, p_72915_4_ >> 4))
					{
						Chunk var6 = getChunkFromChunkCoords(p_72915_2_ >> 4, p_72915_4_ >> 4);
						var6.setLightValue(p_72915_1_, p_72915_2_ & 15, p_72915_3_, p_72915_4_ & 15, p_72915_5_);
						for(int var7 = 0; var7 < worldAccesses.size(); ++var7)
						{
							((IWorldAccess) worldAccesses.get(var7)).markBlockForRenderUpdate(p_72915_2_, p_72915_3_, p_72915_4_);
						}
					}
				}
			}
		}
	}
	
	public void setRainStrength(float par1)
	{
		prevRainingStrength = par1;
		rainingStrength = par1;
	}
	
	public Random setRandomSeed(int p_72843_1_, int p_72843_2_, int p_72843_3_)
	{
		long var4 = p_72843_1_ * 341873128712L + p_72843_2_ * 132897987541L + getWorldInfo().getSeed() + p_72843_3_;
		rand.setSeed(var4);
		return rand;
	}
	
	public void setSpawnLocation()
	{
		this.setSpawnLocation(8, 64, 8);
	}
	
	public void setSpawnLocation(int par1, int par2, int par3)
	{
		worldInfo.setSpawnPosition(par1, par2, par3);
	}
	
	public void setWorldTime(long p_72877_1_)
	{
		worldInfo.setWorldTime(p_72877_1_);
	}
	
	public boolean spawnEntityInWorld(Entity p_72838_1_)
	{
		int var2 = MathHelper.floor_double(p_72838_1_.posX / 16.0D);
		int var3 = MathHelper.floor_double(p_72838_1_.posZ / 16.0D);
		boolean var4 = p_72838_1_.field_98038_p;
		if(p_72838_1_ instanceof EntityPlayer)
		{
			var4 = true;
		}
		if(!var4 && !chunkExists(var2, var3)) return false;
		else
		{
			if(p_72838_1_ instanceof EntityPlayer)
			{
				EntityPlayer var5 = (EntityPlayer) p_72838_1_;
				playerEntities.add(var5);
				updateAllPlayersSleepingFlag();
			}
			getChunkFromChunkCoords(var2, var3).addEntity(p_72838_1_);
			loadedEntityList.add(p_72838_1_);
			onEntityAdded(p_72838_1_);
			return true;
		}
	}
	
	public void spawnParticle(String p_72869_1_, double p_72869_2_, double p_72869_4_, double p_72869_6_, double p_72869_8_, double p_72869_10_, double p_72869_12_)
	{
		for(int var14 = 0; var14 < worldAccesses.size(); ++var14)
		{
			((IWorldAccess) worldAccesses.get(var14)).spawnParticle(p_72869_1_, p_72869_2_, p_72869_4_, p_72869_6_, p_72869_8_, p_72869_10_, p_72869_12_);
		}
	}
	
	public void tick()
	{
		updateWeather();
	}
	
	protected void tickBlocksAndAmbiance()
	{
		setActivePlayerChunksAndCheckLight();
	}
	
	public boolean tickUpdates(boolean p_72955_1_)
	{
		return false;
	}
	
	public void toggleRain()
	{
		worldInfo.setRainTime(1);
	}
	
	public void unloadEntities(List p_72828_1_)
	{
		unloadedEntityList.addAll(p_72828_1_);
	}
	
	public void updateAllLightTypes(int p_72969_1_, int p_72969_2_, int p_72969_3_)
	{
		if(!provider.hasNoSky)
		{
			updateLightByType(EnumSkyBlock.Sky, p_72969_1_, p_72969_2_, p_72969_3_);
		}
		updateLightByType(EnumSkyBlock.Block, p_72969_1_, p_72969_2_, p_72969_3_);
	}
	
	public void updateAllPlayersSleepingFlag()
	{
	}
	
	public void updateEntities()
	{
		theProfiler.startSection("entities");
		theProfiler.startSection("global");
		int var1;
		Entity var2;
		CrashReport var4;
		CrashReportCategory var5;
		for(var1 = 0; var1 < weatherEffects.size(); ++var1)
		{
			var2 = (Entity) weatherEffects.get(var1);
			try
			{
				++var2.ticksExisted;
				var2.onUpdate();
			} catch(Throwable var8)
			{
				var4 = CrashReport.makeCrashReport(var8, "Ticking entity");
				var5 = var4.makeCategory("Entity being ticked");
				if(var2 == null)
				{
					var5.addCrashSection("Entity", "~~NULL~~");
				} else
				{
					var2.func_85029_a(var5);
				}
				throw new ReportedException(var4);
			}
			if(var2.isDead)
			{
				weatherEffects.remove(var1--);
			}
		}
		theProfiler.endStartSection("remove");
		loadedEntityList.removeAll(unloadedEntityList);
		int var3;
		int var13;
		for(var1 = 0; var1 < unloadedEntityList.size(); ++var1)
		{
			var2 = (Entity) unloadedEntityList.get(var1);
			var3 = var2.chunkCoordX;
			var13 = var2.chunkCoordZ;
			if(var2.addedToChunk && chunkExists(var3, var13))
			{
				getChunkFromChunkCoords(var3, var13).removeEntity(var2);
			}
		}
		for(var1 = 0; var1 < unloadedEntityList.size(); ++var1)
		{
			onEntityRemoved((Entity) unloadedEntityList.get(var1));
		}
		unloadedEntityList.clear();
		theProfiler.endStartSection("regular");
		for(var1 = 0; var1 < loadedEntityList.size(); ++var1)
		{
			var2 = (Entity) loadedEntityList.get(var1);
			if(var2.ridingEntity != null)
			{
				if(!var2.ridingEntity.isDead && var2.ridingEntity.riddenByEntity == var2)
				{
					continue;
				}
				var2.ridingEntity.riddenByEntity = null;
				var2.ridingEntity = null;
			}
			theProfiler.startSection("tick");
			if(!var2.isDead)
			{
				try
				{
					updateEntity(var2);
				} catch(Throwable var7)
				{
					var4 = CrashReport.makeCrashReport(var7, "Ticking entity");
					var5 = var4.makeCategory("Entity being ticked");
					var2.func_85029_a(var5);
					throw new ReportedException(var4);
				}
			}
			theProfiler.endSection();
			theProfiler.startSection("remove");
			if(var2.isDead)
			{
				var3 = var2.chunkCoordX;
				var13 = var2.chunkCoordZ;
				if(var2.addedToChunk && chunkExists(var3, var13))
				{
					getChunkFromChunkCoords(var3, var13).removeEntity(var2);
				}
				loadedEntityList.remove(var1--);
				onEntityRemoved(var2);
			}
			theProfiler.endSection();
		}
		theProfiler.endStartSection("tileEntities");
		scanningTileEntities = true;
		Iterator var14 = loadedTileEntityList.iterator();
		while(var14.hasNext())
		{
			TileEntity var9 = (TileEntity) var14.next();
			if(!var9.isInvalid() && var9.hasWorldObj() && blockExists(var9.xCoord, var9.yCoord, var9.zCoord))
			{
				try
				{
					var9.updateEntity();
				} catch(Throwable var6)
				{
					var4 = CrashReport.makeCrashReport(var6, "Ticking tile entity");
					var5 = var4.makeCategory("Tile entity being ticked");
					var9.func_85027_a(var5);
					throw new ReportedException(var4);
				}
			}
			if(var9.isInvalid())
			{
				var14.remove();
				if(chunkExists(var9.xCoord >> 4, var9.zCoord >> 4))
				{
					Chunk var11 = getChunkFromChunkCoords(var9.xCoord >> 4, var9.zCoord >> 4);
					if(var11 != null)
					{
						var11.removeChunkBlockTileEntity(var9.xCoord & 15, var9.yCoord, var9.zCoord & 15);
					}
				}
			}
		}
		scanningTileEntities = false;
		if(!entityRemoval.isEmpty())
		{
			loadedTileEntityList.removeAll(entityRemoval);
			entityRemoval.clear();
		}
		theProfiler.endStartSection("pendingTileEntities");
		if(!addedTileEntityList.isEmpty())
		{
			for(int var10 = 0; var10 < addedTileEntityList.size(); ++var10)
			{
				TileEntity var12 = (TileEntity) addedTileEntityList.get(var10);
				if(!var12.isInvalid())
				{
					if(!loadedTileEntityList.contains(var12))
					{
						loadedTileEntityList.add(var12);
					}
					if(chunkExists(var12.xCoord >> 4, var12.zCoord >> 4))
					{
						Chunk var15 = getChunkFromChunkCoords(var12.xCoord >> 4, var12.zCoord >> 4);
						if(var15 != null)
						{
							var15.setChunkBlockTileEntity(var12.xCoord & 15, var12.yCoord, var12.zCoord & 15, var12);
						}
					}
					markBlockForUpdate(var12.xCoord, var12.yCoord, var12.zCoord);
				}
			}
			addedTileEntityList.clear();
		}
		theProfiler.endSection();
		theProfiler.endSection();
	}
	
	public void updateEntity(Entity p_72870_1_)
	{
		updateEntityWithOptionalForce(p_72870_1_, true);
	}
	
	public void updateEntityWithOptionalForce(Entity p_72866_1_, boolean p_72866_2_)
	{
		int var3 = MathHelper.floor_double(p_72866_1_.posX);
		int var4 = MathHelper.floor_double(p_72866_1_.posZ);
		byte var5 = 32;
		if(!p_72866_2_ || checkChunksExist(var3 - var5, 0, var4 - var5, var3 + var5, 0, var4 + var5))
		{
			p_72866_1_.lastTickPosX = p_72866_1_.posX;
			p_72866_1_.lastTickPosY = p_72866_1_.posY;
			p_72866_1_.lastTickPosZ = p_72866_1_.posZ;
			p_72866_1_.prevRotationYaw = p_72866_1_.rotationYaw;
			p_72866_1_.prevRotationPitch = p_72866_1_.rotationPitch;
			if(p_72866_2_ && p_72866_1_.addedToChunk)
			{
				if(p_72866_1_.ridingEntity != null)
				{
					p_72866_1_.updateRidden();
				} else
				{
					++p_72866_1_.ticksExisted;
					p_72866_1_.onUpdate();
				}
			}
			theProfiler.startSection("chunkCheck");
			if(Double.isNaN(p_72866_1_.posX) || Double.isInfinite(p_72866_1_.posX))
			{
				p_72866_1_.posX = p_72866_1_.lastTickPosX;
			}
			if(Double.isNaN(p_72866_1_.posY) || Double.isInfinite(p_72866_1_.posY))
			{
				p_72866_1_.posY = p_72866_1_.lastTickPosY;
			}
			if(Double.isNaN(p_72866_1_.posZ) || Double.isInfinite(p_72866_1_.posZ))
			{
				p_72866_1_.posZ = p_72866_1_.lastTickPosZ;
			}
			if(Double.isNaN(p_72866_1_.rotationPitch) || Double.isInfinite(p_72866_1_.rotationPitch))
			{
				p_72866_1_.rotationPitch = p_72866_1_.prevRotationPitch;
			}
			if(Double.isNaN(p_72866_1_.rotationYaw) || Double.isInfinite(p_72866_1_.rotationYaw))
			{
				p_72866_1_.rotationYaw = p_72866_1_.prevRotationYaw;
			}
			int var6 = MathHelper.floor_double(p_72866_1_.posX / 16.0D);
			int var7 = MathHelper.floor_double(p_72866_1_.posY / 16.0D);
			int var8 = MathHelper.floor_double(p_72866_1_.posZ / 16.0D);
			if(!p_72866_1_.addedToChunk || p_72866_1_.chunkCoordX != var6 || p_72866_1_.chunkCoordY != var7 || p_72866_1_.chunkCoordZ != var8)
			{
				if(p_72866_1_.addedToChunk && chunkExists(p_72866_1_.chunkCoordX, p_72866_1_.chunkCoordZ))
				{
					getChunkFromChunkCoords(p_72866_1_.chunkCoordX, p_72866_1_.chunkCoordZ).removeEntityAtIndex(p_72866_1_, p_72866_1_.chunkCoordY);
				}
				if(chunkExists(var6, var8))
				{
					p_72866_1_.addedToChunk = true;
					getChunkFromChunkCoords(var6, var8).addEntity(p_72866_1_);
				} else
				{
					p_72866_1_.addedToChunk = false;
				}
			}
			theProfiler.endSection();
			if(p_72866_2_ && p_72866_1_.addedToChunk && p_72866_1_.riddenByEntity != null)
			{
				if(!p_72866_1_.riddenByEntity.isDead && p_72866_1_.riddenByEntity.ridingEntity == p_72866_1_)
				{
					updateEntity(p_72866_1_.riddenByEntity);
				} else
				{
					p_72866_1_.riddenByEntity.ridingEntity = null;
					p_72866_1_.riddenByEntity = null;
				}
			}
		}
	}
	
	public void updateLightByType(EnumSkyBlock p_72936_1_, int p_72936_2_, int p_72936_3_, int p_72936_4_)
	{
		if(doChunksNearChunkExist(p_72936_2_, p_72936_3_, p_72936_4_, 17))
		{
			int var5 = 0;
			int var6 = 0;
			theProfiler.startSection("getBrightness");
			int var7 = getSavedLightValue(p_72936_1_, p_72936_2_, p_72936_3_, p_72936_4_);
			int var8 = computeLightValue(p_72936_2_, p_72936_3_, p_72936_4_, p_72936_1_);
			int var9;
			int var10;
			int var11;
			int var12;
			int var13;
			int var14;
			int var15;
			int var17;
			int var16;
			if(var8 > var7)
			{
				lightUpdateBlockList[var6++] = 133152;
			} else if(var8 < var7)
			{
				lightUpdateBlockList[var6++] = 133152 | var7 << 18;
				while(var5 < var6)
				{
					var9 = lightUpdateBlockList[var5++];
					var10 = (var9 & 63) - 32 + p_72936_2_;
					var11 = (var9 >> 6 & 63) - 32 + p_72936_3_;
					var12 = (var9 >> 12 & 63) - 32 + p_72936_4_;
					var13 = var9 >> 18 & 15;
					var14 = getSavedLightValue(p_72936_1_, var10, var11, var12);
					if(var14 == var13)
					{
						setLightValue(p_72936_1_, var10, var11, var12, 0);
						if(var13 > 0)
						{
							var15 = MathHelper.abs_int(var10 - p_72936_2_);
							var16 = MathHelper.abs_int(var11 - p_72936_3_);
							var17 = MathHelper.abs_int(var12 - p_72936_4_);
							if(var15 + var16 + var17 < 17)
							{
								for(int var18 = 0; var18 < 6; ++var18)
								{
									int var19 = var10 + Facing.offsetsXForSide[var18];
									int var20 = var11 + Facing.offsetsYForSide[var18];
									int var21 = var12 + Facing.offsetsZForSide[var18];
									int var22 = Math.max(1, Block.lightOpacity[getBlockId(var19, var20, var21)]);
									var14 = getSavedLightValue(p_72936_1_, var19, var20, var21);
									if(var14 == var13 - var22 && var6 < lightUpdateBlockList.length)
									{
										lightUpdateBlockList[var6++] = var19 - p_72936_2_ + 32 | var20 - p_72936_3_ + 32 << 6 | var21 - p_72936_4_ + 32 << 12 | var13 - var22 << 18;
									}
								}
							}
						}
					}
				}
				var5 = 0;
			}
			theProfiler.endSection();
			theProfiler.startSection("checkedPosition < toCheckCount");
			while(var5 < var6)
			{
				var9 = lightUpdateBlockList[var5++];
				var10 = (var9 & 63) - 32 + p_72936_2_;
				var11 = (var9 >> 6 & 63) - 32 + p_72936_3_;
				var12 = (var9 >> 12 & 63) - 32 + p_72936_4_;
				var13 = getSavedLightValue(p_72936_1_, var10, var11, var12);
				var14 = computeLightValue(var10, var11, var12, p_72936_1_);
				if(var14 != var13)
				{
					setLightValue(p_72936_1_, var10, var11, var12, var14);
					if(var14 > var13)
					{
						var15 = Math.abs(var10 - p_72936_2_);
						var16 = Math.abs(var11 - p_72936_3_);
						var17 = Math.abs(var12 - p_72936_4_);
						boolean var23 = var6 < lightUpdateBlockList.length - 6;
						if(var15 + var16 + var17 < 17 && var23)
						{
							if(getSavedLightValue(p_72936_1_, var10 - 1, var11, var12) < var14)
							{
								lightUpdateBlockList[var6++] = var10 - 1 - p_72936_2_ + 32 + (var11 - p_72936_3_ + 32 << 6) + (var12 - p_72936_4_ + 32 << 12);
							}
							if(getSavedLightValue(p_72936_1_, var10 + 1, var11, var12) < var14)
							{
								lightUpdateBlockList[var6++] = var10 + 1 - p_72936_2_ + 32 + (var11 - p_72936_3_ + 32 << 6) + (var12 - p_72936_4_ + 32 << 12);
							}
							if(getSavedLightValue(p_72936_1_, var10, var11 - 1, var12) < var14)
							{
								lightUpdateBlockList[var6++] = var10 - p_72936_2_ + 32 + (var11 - 1 - p_72936_3_ + 32 << 6) + (var12 - p_72936_4_ + 32 << 12);
							}
							if(getSavedLightValue(p_72936_1_, var10, var11 + 1, var12) < var14)
							{
								lightUpdateBlockList[var6++] = var10 - p_72936_2_ + 32 + (var11 + 1 - p_72936_3_ + 32 << 6) + (var12 - p_72936_4_ + 32 << 12);
							}
							if(getSavedLightValue(p_72936_1_, var10, var11, var12 - 1) < var14)
							{
								lightUpdateBlockList[var6++] = var10 - p_72936_2_ + 32 + (var11 - p_72936_3_ + 32 << 6) + (var12 - 1 - p_72936_4_ + 32 << 12);
							}
							if(getSavedLightValue(p_72936_1_, var10, var11, var12 + 1) < var14)
							{
								lightUpdateBlockList[var6++] = var10 - p_72936_2_ + 32 + (var11 - p_72936_3_ + 32 << 6) + (var12 + 1 - p_72936_4_ + 32 << 12);
							}
						}
					}
				}
			}
			theProfiler.endSection();
		}
	}
	
	public void updateTileEntityChunkAndDoNothing(int p_72944_1_, int p_72944_2_, int p_72944_3_, TileEntity p_72944_4_)
	{
		if(blockExists(p_72944_1_, p_72944_2_, p_72944_3_))
		{
			getChunkFromBlockCoords(p_72944_1_, p_72944_3_).setChunkModified();
		}
	}
	
	protected void updateWeather()
	{
		if(!provider.hasNoSky)
		{
			int var1 = worldInfo.getThunderTime();
			if(var1 <= 0)
			{
				if(worldInfo.isThundering())
				{
					worldInfo.setThunderTime(rand.nextInt(12000) + 3600);
				} else
				{
					worldInfo.setThunderTime(rand.nextInt(168000) + 12000);
				}
			} else
			{
				--var1;
				worldInfo.setThunderTime(var1);
				if(var1 <= 0)
				{
					worldInfo.setThundering(!worldInfo.isThundering());
				}
			}
			int var2 = worldInfo.getRainTime();
			if(var2 <= 0)
			{
				if(worldInfo.isRaining())
				{
					worldInfo.setRainTime(rand.nextInt(12000) + 12000);
				} else
				{
					worldInfo.setRainTime(rand.nextInt(168000) + 12000);
				}
			} else
			{
				--var2;
				worldInfo.setRainTime(var2);
				if(var2 <= 0)
				{
					worldInfo.setRaining(!worldInfo.isRaining());
				}
			}
			prevRainingStrength = rainingStrength;
			if(worldInfo.isRaining())
			{
				rainingStrength = (float) (rainingStrength + 0.01D);
			} else
			{
				rainingStrength = (float) (rainingStrength - 0.01D);
			}
			if(rainingStrength < 0.0F)
			{
				rainingStrength = 0.0F;
			}
			if(rainingStrength > 1.0F)
			{
				rainingStrength = 1.0F;
			}
			prevThunderingStrength = thunderingStrength;
			if(worldInfo.isThundering())
			{
				thunderingStrength = (float) (thunderingStrength + 0.01D);
			} else
			{
				thunderingStrength = (float) (thunderingStrength - 0.01D);
			}
			if(thunderingStrength < 0.0F)
			{
				thunderingStrength = 0.0F;
			}
			if(thunderingStrength > 1.0F)
			{
				thunderingStrength = 1.0F;
			}
		}
	}
}
