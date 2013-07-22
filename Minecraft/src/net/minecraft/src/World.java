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
	
	public World(ISaveHandler par1ISaveHandler, String par2Str, WorldProvider par3WorldProvider, WorldSettings par4WorldSettings, Profiler par5Profiler, ILogAgent par6ILogAgent)
	{
		ambientTickCountdown = rand.nextInt(12000);
		lightUpdateBlockList = new int[32768];
		isRemote = false;
		saveHandler = par1ISaveHandler;
		theProfiler = par5Profiler;
		worldInfo = new WorldInfo(par4WorldSettings, par2Str);
		provider = par3WorldProvider;
		mapStorage = new MapStorage(par1ISaveHandler);
		worldLogAgent = par6ILogAgent;
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
		par3WorldProvider.registerWorld(this);
		chunkProvider = createChunkProvider();
		calculateInitialSkylight();
		calculateInitialWeather();
	}
	
	public World(ISaveHandler par1ISaveHandler, String par2Str, WorldSettings par3WorldSettings, WorldProvider par4WorldProvider, Profiler par5Profiler, ILogAgent par6ILogAgent)
	{
		ambientTickCountdown = rand.nextInt(12000);
		lightUpdateBlockList = new int[32768];
		isRemote = false;
		saveHandler = par1ISaveHandler;
		theProfiler = par5Profiler;
		mapStorage = new MapStorage(par1ISaveHandler);
		worldLogAgent = par6ILogAgent;
		worldInfo = par1ISaveHandler.loadWorldInfo();
		if(par4WorldProvider != null)
		{
			provider = par4WorldProvider;
		} else if(worldInfo != null && worldInfo.getVanillaDimension() != 0)
		{
			provider = WorldProvider.getProviderForDimension(worldInfo.getVanillaDimension());
		} else
		{
			provider = WorldProvider.getProviderForDimension(0);
		}
		if(worldInfo == null)
		{
			worldInfo = new WorldInfo(par3WorldSettings, par2Str);
		} else
		{
			worldInfo.setWorldName(par2Str);
		}
		provider.registerWorld(this);
		chunkProvider = createChunkProvider();
		if(!worldInfo.isInitialized())
		{
			try
			{
				initialize(par3WorldSettings);
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
	
	public void addBlockEvent(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		if(par4 > 0)
		{
			Block.blocksList[par4].onBlockEventReceived(this, par1, par2, par3, par5, par6);
		}
	}
	
	public void addLoadedEntities(List par1List)
	{
		loadedEntityList.addAll(par1List);
		for(int var2 = 0; var2 < par1List.size(); ++var2)
		{
			onEntityAdded((Entity) par1List.get(var2));
		}
	}
	
	public void addTileEntity(Collection par1Collection)
	{
		if(scanningTileEntities)
		{
			addedTileEntityList.addAll(par1Collection);
		} else
		{
			loadedTileEntityList.addAll(par1Collection);
		}
	}
	
	public boolean addWeatherEffect(Entity par1Entity)
	{
		weatherEffects.add(par1Entity);
		return true;
	}
	
	public void addWorldAccess(IWorldAccess par1IWorldAccess)
	{
		worldAccesses.add(par1IWorldAccess);
	}
	
	public CrashReportCategory addWorldInfoToCrashReport(CrashReport par1CrashReport)
	{
		CrashReportCategory var2 = par1CrashReport.makeCategoryDepth("Affected level", 1);
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
	
	public boolean blockExists(int par1, int par2, int par3)
	{
		return par2 >= 0 && par2 < 256 ? chunkExists(par1 >> 4, par3 >> 4) : false;
	}
	
	public int blockGetRenderType(int par1, int par2, int par3)
	{
		int var4 = getBlockId(par1, par2, par3);
		return Block.blocksList[var4] != null ? Block.blocksList[var4].getRenderType() : -1;
	}
	
	public boolean blockHasTileEntity(int par1, int par2, int par3)
	{
		int var4 = getBlockId(par1, par2, par3);
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
	
	public int calculateSkylightSubtracted(float par1)
	{
		float var2 = getCelestialAngle(par1);
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
		var3 = (float) (var3 * (1.0D - getRainStrength(par1) * 5.0F / 16.0D));
		var3 = (float) (var3 * (1.0D - getWeightedThunderStrength(par1) * 5.0F / 16.0D));
		var3 = 1.0F - var3;
		return (int) (var3 * 11.0F);
	}
	
	public boolean canBlockFreeze(int par1, int par2, int par3, boolean par4)
	{
		BiomeGenBase var5 = getBiomeGenForCoords(par1, par3);
		float var6 = var5.getFloatTemperature();
		if(var6 > 0.15F) return false;
		else
		{
			if(par2 >= 0 && par2 < 256 && getSavedLightValue(EnumSkyBlock.Block, par1, par2, par3) < 10)
			{
				int var7 = getBlockId(par1, par2, par3);
				if((var7 == Block.waterStill.blockID || var7 == Block.waterMoving.blockID) && getBlockMetadata(par1, par2, par3) == 0)
				{
					if(!par4) return true;
					boolean var8 = true;
					if(var8 && getBlockMaterial(par1 - 1, par2, par3) != Material.water)
					{
						var8 = false;
					}
					if(var8 && getBlockMaterial(par1 + 1, par2, par3) != Material.water)
					{
						var8 = false;
					}
					if(var8 && getBlockMaterial(par1, par2, par3 - 1) != Material.water)
					{
						var8 = false;
					}
					if(var8 && getBlockMaterial(par1, par2, par3 + 1) != Material.water)
					{
						var8 = false;
					}
					if(!var8) return true;
				}
			}
			return false;
		}
	}
	
	public boolean canBlockSeeTheSky(int par1, int par2, int par3)
	{
		return getChunkFromChunkCoords(par1 >> 4, par3 >> 4).canBlockSeeTheSky(par1 & 15, par2, par3 & 15);
	}
	
	public boolean canLightningStrikeAt(int par1, int par2, int par3)
	{
		if(!isRaining()) return false;
		else if(!canBlockSeeTheSky(par1, par2, par3)) return false;
		else if(getPrecipitationHeight(par1, par3) > par2) return false;
		else
		{
			BiomeGenBase var4 = getBiomeGenForCoords(par1, par3);
			return var4.getEnableSnow() ? false : var4.canSpawnLightningBolt();
		}
	}
	
	public boolean canMineBlock(EntityPlayer par1EntityPlayer, int par2, int par3, int par4)
	{
		return true;
	}
	
	public boolean canPlaceEntityOnSide(int par1, int par2, int par3, int par4, boolean par5, int par6, Entity par7Entity, ItemStack par8ItemStack)
	{
		int var9 = getBlockId(par2, par3, par4);
		Block var10 = Block.blocksList[var9];
		Block var11 = Block.blocksList[par1];
		AxisAlignedBB var12 = var11.getCollisionBoundingBoxFromPool(this, par2, par3, par4);
		if(par5)
		{
			var12 = null;
		}
		if(var12 != null && !this.checkNoEntityCollision(var12, par7Entity)) return false;
		else
		{
			if(var10 != null && (var10 == Block.waterMoving || var10 == Block.waterStill || var10 == Block.lavaMoving || var10 == Block.lavaStill || var10 == Block.fire || var10.blockMaterial.isReplaceable()))
			{
				var10 = null;
			}
			return var10 != null && var10.blockMaterial == Material.circuits && var11 == Block.anvil ? true : par1 > 0 && var10 == null && var11.canPlaceBlockOnSide(this, par2, par3, par4, par6, par8ItemStack);
		}
	}
	
	public boolean canSnowAt(int par1, int par2, int par3)
	{
		BiomeGenBase var4 = getBiomeGenForCoords(par1, par3);
		float var5 = var4.getFloatTemperature();
		if(var5 > 0.15F) return false;
		else
		{
			if(par2 >= 0 && par2 < 256 && getSavedLightValue(EnumSkyBlock.Block, par1, par2, par3) < 10)
			{
				int var6 = getBlockId(par1, par2 - 1, par3);
				int var7 = getBlockId(par1, par2, par3);
				if(var7 == 0 && Block.snow.canPlaceBlockAt(this, par1, par2, par3) && var6 != 0 && var6 != Block.ice.blockID && Block.blocksList[var6].blockMaterial.blocksMovement()) return true;
			}
			return false;
		}
	}
	
	public boolean checkBlockCollision(AxisAlignedBB par1AxisAlignedBB)
	{
		int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
		int var3 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
		int var4 = MathHelper.floor_double(par1AxisAlignedBB.minY);
		int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
		int var6 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
		int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);
		if(par1AxisAlignedBB.minX < 0.0D)
		{
			--var2;
		}
		if(par1AxisAlignedBB.minY < 0.0D)
		{
			--var4;
		}
		if(par1AxisAlignedBB.minZ < 0.0D)
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
	
	public boolean checkChunksExist(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		if(par5 >= 0 && par2 < 256)
		{
			par1 >>= 4;
			par3 >>= 4;
			par4 >>= 4;
			par6 >>= 4;
			for(int var7 = par1; var7 <= par4; ++var7)
			{
				for(int var8 = par3; var8 <= par6; ++var8)
				{
					if(!chunkExists(var7, var8)) return false;
				}
			}
			return true;
		} else return false;
	}
	
	public boolean checkNoEntityCollision(AxisAlignedBB par1AxisAlignedBB)
	{
		return this.checkNoEntityCollision(par1AxisAlignedBB, (Entity) null);
	}
	
	public boolean checkNoEntityCollision(AxisAlignedBB par1AxisAlignedBB, Entity par2Entity)
	{
		List var3 = this.getEntitiesWithinAABBExcludingEntity((Entity) null, par1AxisAlignedBB);
		for(int var4 = 0; var4 < var3.size(); ++var4)
		{
			Entity var5 = (Entity) var3.get(var4);
			if(!var5.isDead && var5.preventEntitySpawning && var5 != par2Entity) return false;
		}
		return true;
	}
	
	public void checkSessionLock() throws MinecraftException
	{
		saveHandler.checkSessionLock();
	}
	
	protected boolean chunkExists(int par1, int par2)
	{
		return chunkProvider.chunkExists(par1, par2);
	}
	
	public MovingObjectPosition clip(Vec3 par1Vec3, Vec3 par2Vec3)
	{
		return rayTraceBlocks_do_do(par1Vec3, par2Vec3, false, false);
	}
	
	public MovingObjectPosition clip(Vec3 par1Vec3, Vec3 par2Vec3, boolean par3)
	{
		return rayTraceBlocks_do_do(par1Vec3, par2Vec3, par3, false);
	}
	
	private int computeLightValue(int par1, int par2, int par3, EnumSkyBlock par4EnumSkyBlock)
	{
		if(par4EnumSkyBlock == EnumSkyBlock.Sky && canBlockSeeTheSky(par1, par2, par3)) return 15;
		else
		{
			int var5 = getBlockId(par1, par2, par3);
			int var6 = par4EnumSkyBlock == EnumSkyBlock.Sky ? 0 : Block.lightValue[var5];
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
					int var9 = par1 + Facing.offsetsXForSide[var8];
					int var10 = par2 + Facing.offsetsYForSide[var8];
					int var11 = par3 + Facing.offsetsZForSide[var8];
					int var12 = getSavedLightValue(par4EnumSkyBlock, var9, var10, var11) - var7;
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
	
	public int countEntities(Class par1Class)
	{
		int var2 = 0;
		for(int var3 = 0; var3 < loadedEntityList.size(); ++var3)
		{
			Entity var4 = (Entity) loadedEntityList.get(var3);
			if((!(var4 instanceof EntityLiving) || !((EntityLiving) var4).func_104002_bU()) && par1Class.isAssignableFrom(var4.getClass()))
			{
				++var2;
			}
		}
		return var2;
	}
	
	protected abstract IChunkProvider createChunkProvider();
	
	public Explosion createExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9)
	{
		return newExplosion(par1Entity, par2, par4, par6, par8, false, par9);
	}
	
	public boolean destroyBlock(int par1, int par2, int par3, boolean par4)
	{
		int var5 = getBlockId(par1, par2, par3);
		if(var5 > 0)
		{
			int var6 = getBlockMetadata(par1, par2, par3);
			playAuxSFX(2001, par1, par2, par3, var5 + (var6 << 12));
			if(par4)
			{
				Block.blocksList[var5].dropBlockAsItem(this, par1, par2, par3, var6, 0);
			}
			return this.setBlock(par1, par2, par3, 0, 0, 3);
		} else return false;
	}
	
	public void destroyBlockInWorldPartially(int par1, int par2, int par3, int par4, int par5)
	{
		for(int var6 = 0; var6 < worldAccesses.size(); ++var6)
		{
			IWorldAccess var7 = (IWorldAccess) worldAccesses.get(var6);
			var7.destroyBlockPartially(par1, par2, par3, par4, par5);
		}
	}
	
	public boolean doChunksNearChunkExist(int par1, int par2, int par3, int par4)
	{
		return checkChunksExist(par1 - par4, par2 - par4, par3 - par4, par1 + par4, par2 + par4, par3 + par4);
	}
	
	@Override public boolean doesBlockHaveSolidTopSurface(int par1, int par2, int par3)
	{
		Block var4 = Block.blocksList[getBlockId(par1, par2, par3)];
		return isBlockTopFacingSurfaceSolid(var4, getBlockMetadata(par1, par2, par3));
	}
	
	@Override public boolean extendedLevelsInChunkCache()
	{
		return false;
	}
	
	public boolean extinguishFire(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5)
	{
		if(par5 == 0)
		{
			--par3;
		}
		if(par5 == 1)
		{
			++par3;
		}
		if(par5 == 2)
		{
			--par4;
		}
		if(par5 == 3)
		{
			++par4;
		}
		if(par5 == 4)
		{
			--par2;
		}
		if(par5 == 5)
		{
			++par2;
		}
		if(getBlockId(par2, par3, par4) == Block.fire.blockID)
		{
			playAuxSFXAtEntity(par1EntityPlayer, 1004, par2, par3, par4, 0);
			setBlockToAir(par2, par3, par4);
			return true;
		} else return false;
	}
	
	public ChunkPosition findClosestStructure(String par1Str, int par2, int par3, int par4)
	{
		return getChunkProvider().findClosestStructure(this, par1Str, par2, par3, par4);
	}
	
	public Entity findNearestEntityWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, Entity par3Entity)
	{
		List var4 = getEntitiesWithinAABB(par1Class, par2AxisAlignedBB);
		Entity var5 = null;
		double var6 = Double.MAX_VALUE;
		for(int var8 = 0; var8 < var4.size(); ++var8)
		{
			Entity var9 = (Entity) var4.get(var8);
			if(var9 != par3Entity)
			{
				double var10 = par3Entity.getDistanceSqToEntity(var9);
				if(var10 <= var6)
				{
					var5 = var9;
					var6 = var10;
				}
			}
		}
		return var5;
	}
	
	public IUpdatePlayerListBox func_82735_a(EntityMinecart par1EntityMinecart)
	{
		return null;
	}
	
	public void func_82738_a(long par1)
	{
		worldInfo.incrementTotalWorldTime(par1);
	}
	
	public void func_82739_e(int par1, int par2, int par3, int par4, int par5)
	{
		for(int var6 = 0; var6 < worldAccesses.size(); ++var6)
		{
			((IWorldAccess) worldAccesses.get(var6)).broadcastSound(par1, par2, par3, par4, par5);
		}
	}
	
	public void func_92088_a(double par1, double par3, double par5, double par7, double par9, double par11, NBTTagCompound par13NBTTagCompound)
	{
	}
	
	public void func_96440_m(int par1, int par2, int par3, int par4)
	{
		for(int var5 = 0; var5 < 4; ++var5)
		{
			int var6 = par1 + Direction.offsetX[var5];
			int var7 = par3 + Direction.offsetZ[var5];
			int var8 = getBlockId(var6, par2, var7);
			if(var8 != 0)
			{
				Block var9 = Block.blocksList[var8];
				if(Block.redstoneComparatorIdle.func_94487_f(var8))
				{
					var9.onNeighborBlockChange(this, var6, par2, var7, par4);
				} else if(Block.isNormalCube(var8))
				{
					var6 += Direction.offsetX[var5];
					var7 += Direction.offsetZ[var5];
					var8 = getBlockId(var6, par2, var7);
					var9 = Block.blocksList[var8];
					if(Block.redstoneComparatorIdle.func_94487_f(var8))
					{
						var9.onNeighborBlockChange(this, var6, par2, var7, par4);
					}
				}
			}
		}
	}
	
	public int getActualHeight()
	{
		return provider.hasNoSky ? 128 : 256;
	}
	
	@Override public BiomeGenBase getBiomeGenForCoords(int par1, int par2)
	{
		if(blockExists(par1, 0, par2))
		{
			Chunk var3 = getChunkFromBlockCoords(par1, par2);
			if(var3 != null) return var3.getBiomeGenForWorldCoords(par1 & 15, par2 & 15, provider.worldChunkMgr);
		}
		return provider.worldChunkMgr.getBiomeGenAt(par1, par2);
	}
	
	public float getBlockDensity(Vec3 par1Vec3, AxisAlignedBB par2AxisAlignedBB)
	{
		double var3 = 1.0D / ((par2AxisAlignedBB.maxX - par2AxisAlignedBB.minX) * 2.0D + 1.0D);
		double var5 = 1.0D / ((par2AxisAlignedBB.maxY - par2AxisAlignedBB.minY) * 2.0D + 1.0D);
		double var7 = 1.0D / ((par2AxisAlignedBB.maxZ - par2AxisAlignedBB.minZ) * 2.0D + 1.0D);
		int var9 = 0;
		int var10 = 0;
		for(float var11 = 0.0F; var11 <= 1.0F; var11 = (float) (var11 + var3))
		{
			for(float var12 = 0.0F; var12 <= 1.0F; var12 = (float) (var12 + var5))
			{
				for(float var13 = 0.0F; var13 <= 1.0F; var13 = (float) (var13 + var7))
				{
					double var14 = par2AxisAlignedBB.minX + (par2AxisAlignedBB.maxX - par2AxisAlignedBB.minX) * var11;
					double var16 = par2AxisAlignedBB.minY + (par2AxisAlignedBB.maxY - par2AxisAlignedBB.minY) * var12;
					double var18 = par2AxisAlignedBB.minZ + (par2AxisAlignedBB.maxZ - par2AxisAlignedBB.minZ) * var13;
					if(this.clip(getWorldVec3Pool().getVecFromPool(var14, var16, var18), par1Vec3) == null)
					{
						++var9;
					}
					++var10;
				}
			}
		}
		return (float) var9 / (float) var10;
	}
	
	@Override public int getBlockId(int par1, int par2, int par3)
	{
		if(par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000)
		{
			if(par2 < 0) return 0;
			else if(par2 >= 256) return 0;
			else
			{
				Chunk var4 = null;
				try
				{
					var4 = getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
					return var4.getBlockID(par1 & 15, par2, par3 & 15);
				} catch(Throwable var8)
				{
					CrashReport var6 = CrashReport.makeCrashReport(var8, "Exception getting block type in world");
					CrashReportCategory var7 = var6.makeCategory("Requested block coordinates");
					var7.addCrashSection("Found chunk", Boolean.valueOf(var4 == null));
					var7.addCrashSection("Location", CrashReportCategory.getLocationInfo(par1, par2, par3));
					throw new ReportedException(var6);
				}
			}
		} else return 0;
	}
	
	public int getBlockLightValue(int par1, int par2, int par3)
	{
		return getBlockLightValue_do(par1, par2, par3, true);
	}
	
	public int getBlockLightValue_do(int par1, int par2, int par3, boolean par4)
	{
		if(par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000)
		{
			if(par4)
			{
				int var5 = getBlockId(par1, par2, par3);
				if(Block.useNeighborBrightness[var5])
				{
					int var6 = getBlockLightValue_do(par1, par2 + 1, par3, false);
					int var7 = getBlockLightValue_do(par1 + 1, par2, par3, false);
					int var8 = getBlockLightValue_do(par1 - 1, par2, par3, false);
					int var9 = getBlockLightValue_do(par1, par2, par3 + 1, false);
					int var10 = getBlockLightValue_do(par1, par2, par3 - 1, false);
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
			if(par2 < 0) return 0;
			else
			{
				if(par2 >= 256)
				{
					par2 = 255;
				}
				Chunk var11 = getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
				par1 &= 15;
				par3 &= 15;
				return var11.getBlockLightValue(par1, par2, par3, skylightSubtracted);
			}
		} else return 15;
	}
	
	@Override public Material getBlockMaterial(int par1, int par2, int par3)
	{
		int var4 = getBlockId(par1, par2, par3);
		return var4 == 0 ? Material.air : Block.blocksList[var4].blockMaterial;
	}
	
	@Override public int getBlockMetadata(int par1, int par2, int par3)
	{
		if(par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000)
		{
			if(par2 < 0) return 0;
			else if(par2 >= 256) return 0;
			else
			{
				Chunk var4 = getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
				par1 &= 15;
				par3 &= 15;
				return var4.getBlockMetadata(par1, par2, par3);
			}
		} else return 0;
	}
	
	public int getBlockPowerInput(int par1, int par2, int par3)
	{
		byte var4 = 0;
		int var5 = Math.max(var4, isBlockProvidingPowerTo(par1, par2 - 1, par3, 0));
		if(var5 >= 15) return var5;
		else
		{
			var5 = Math.max(var5, isBlockProvidingPowerTo(par1, par2 + 1, par3, 1));
			if(var5 >= 15) return var5;
			else
			{
				var5 = Math.max(var5, isBlockProvidingPowerTo(par1, par2, par3 - 1, 2));
				if(var5 >= 15) return var5;
				else
				{
					var5 = Math.max(var5, isBlockProvidingPowerTo(par1, par2, par3 + 1, 3));
					if(var5 >= 15) return var5;
					else
					{
						var5 = Math.max(var5, isBlockProvidingPowerTo(par1 - 1, par2, par3, 4));
						if(var5 >= 15) return var5;
						else
						{
							var5 = Math.max(var5, isBlockProvidingPowerTo(par1 + 1, par2, par3, 5));
							return var5 >= 15 ? var5 : var5;
						}
					}
				}
			}
		}
	}
	
	@Override public TileEntity getBlockTileEntity(int par1, int par2, int par3)
	{
		if(par2 >= 0 && par2 < 256)
		{
			TileEntity var4 = null;
			int var5;
			TileEntity var6;
			if(scanningTileEntities)
			{
				for(var5 = 0; var5 < addedTileEntityList.size(); ++var5)
				{
					var6 = (TileEntity) addedTileEntityList.get(var5);
					if(!var6.isInvalid() && var6.xCoord == par1 && var6.yCoord == par2 && var6.zCoord == par3)
					{
						var4 = var6;
						break;
					}
				}
			}
			if(var4 == null)
			{
				Chunk var7 = getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
				if(var7 != null)
				{
					var4 = var7.getChunkBlockTileEntity(par1 & 15, par2, par3 & 15);
				}
			}
			if(var4 == null)
			{
				for(var5 = 0; var5 < addedTileEntityList.size(); ++var5)
				{
					var6 = (TileEntity) addedTileEntityList.get(var5);
					if(!var6.isInvalid() && var6.xCoord == par1 && var6.yCoord == par2 && var6.zCoord == par3)
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
	
	public float getCelestialAngle(float par1)
	{
		return provider.calculateCelestialAngle(worldInfo.getWorldTime(), par1);
	}
	
	public float getCelestialAngleRadians(float par1)
	{
		float var2 = getCelestialAngle(par1);
		return var2 * (float) Math.PI * 2.0F;
	}
	
	public Chunk getChunkFromBlockCoords(int par1, int par2)
	{
		return getChunkFromChunkCoords(par1 >> 4, par2 >> 4);
	}
	
	public Chunk getChunkFromChunkCoords(int par1, int par2)
	{
		return chunkProvider.provideChunk(par1, par2);
	}
	
	public int getChunkHeightMapMinimum(int par1, int par2)
	{
		if(par1 >= -30000000 && par2 >= -30000000 && par1 < 30000000 && par2 < 30000000)
		{
			if(!chunkExists(par1 >> 4, par2 >> 4)) return 0;
			else
			{
				Chunk var3 = getChunkFromChunkCoords(par1 >> 4, par2 >> 4);
				return var3.heightMapMinimum;
			}
		} else return 0;
	}
	
	public IChunkProvider getChunkProvider()
	{
		return chunkProvider;
	}
	
	public EntityPlayer getClosestPlayer(double par1, double par3, double par5, double par7)
	{
		double var9 = -1.0D;
		EntityPlayer var11 = null;
		for(int var12 = 0; var12 < playerEntities.size(); ++var12)
		{
			EntityPlayer var13 = (EntityPlayer) playerEntities.get(var12);
			double var14 = var13.getDistanceSq(par1, par3, par5);
			if((par7 < 0.0D || var14 < par7 * par7) && (var9 == -1.0D || var14 < var9))
			{
				var9 = var14;
				var11 = var13;
			}
		}
		return var11;
	}
	
	public EntityPlayer getClosestPlayerToEntity(Entity par1Entity, double par2)
	{
		return getClosestPlayer(par1Entity.posX, par1Entity.posY, par1Entity.posZ, par2);
	}
	
	public EntityPlayer getClosestVulnerablePlayer(double par1, double par3, double par5, double par7)
	{
		double var9 = -1.0D;
		EntityPlayer var11 = null;
		for(int var12 = 0; var12 < playerEntities.size(); ++var12)
		{
			EntityPlayer var13 = (EntityPlayer) playerEntities.get(var12);
			if(!var13.capabilities.disableDamage && var13.isEntityAlive())
			{
				double var14 = var13.getDistanceSq(par1, par3, par5);
				double var16 = par7;
				if(var13.isSneaking())
				{
					var16 = par7 * 0.800000011920929D;
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
				if((par7 < 0.0D || var14 < var16 * var16) && (var9 == -1.0D || var14 < var9))
				{
					var9 = var14;
					var11 = var13;
				}
			}
		}
		return var11;
	}
	
	public EntityPlayer getClosestVulnerablePlayerToEntity(Entity par1Entity, double par2)
	{
		return getClosestVulnerablePlayer(par1Entity.posX, par1Entity.posY, par1Entity.posZ, par2);
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
	
	public List getCollidingBlockBounds(AxisAlignedBB par1AxisAlignedBB)
	{
		collidingBoundingBoxes.clear();
		int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
		int var3 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
		int var4 = MathHelper.floor_double(par1AxisAlignedBB.minY);
		int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
		int var6 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
		int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);
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
							var11.addCollisionBoxesToList(this, var8, var10, var9, par1AxisAlignedBB, collidingBoundingBoxes, (Entity) null);
						}
					}
				}
			}
		}
		return collidingBoundingBoxes;
	}
	
	public List getCollidingBoundingBoxes(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB)
	{
		collidingBoundingBoxes.clear();
		int var3 = MathHelper.floor_double(par2AxisAlignedBB.minX);
		int var4 = MathHelper.floor_double(par2AxisAlignedBB.maxX + 1.0D);
		int var5 = MathHelper.floor_double(par2AxisAlignedBB.minY);
		int var6 = MathHelper.floor_double(par2AxisAlignedBB.maxY + 1.0D);
		int var7 = MathHelper.floor_double(par2AxisAlignedBB.minZ);
		int var8 = MathHelper.floor_double(par2AxisAlignedBB.maxZ + 1.0D);
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
							var12.addCollisionBoxesToList(this, var9, var11, var10, par2AxisAlignedBB, collidingBoundingBoxes, par1Entity);
						}
					}
				}
			}
		}
		double var14 = 0.25D;
		List var16 = this.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB.expand(var14, var14, var14));
		for(int var15 = 0; var15 < var16.size(); ++var15)
		{
			AxisAlignedBB var13 = ((Entity) var16.get(var15)).getBoundingBox();
			if(var13 != null && var13.intersectsWith(par2AxisAlignedBB))
			{
				collidingBoundingBoxes.add(var13);
			}
			var13 = par1Entity.getCollisionBox((Entity) var16.get(var15));
			if(var13 != null && var13.intersectsWith(par2AxisAlignedBB))
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
	
	public List getEntitiesWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB)
	{
		return selectEntitiesWithinAABB(par1Class, par2AxisAlignedBB, (IEntitySelector) null);
	}
	
	public List getEntitiesWithinAABBExcludingEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB)
	{
		return this.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB, (IEntitySelector) null);
	}
	
	public List getEntitiesWithinAABBExcludingEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB, IEntitySelector par3IEntitySelector)
	{
		ArrayList var4 = new ArrayList();
		int var5 = MathHelper.floor_double((par2AxisAlignedBB.minX - 2.0D) / 16.0D);
		int var6 = MathHelper.floor_double((par2AxisAlignedBB.maxX + 2.0D) / 16.0D);
		int var7 = MathHelper.floor_double((par2AxisAlignedBB.minZ - 2.0D) / 16.0D);
		int var8 = MathHelper.floor_double((par2AxisAlignedBB.maxZ + 2.0D) / 16.0D);
		for(int var9 = var5; var9 <= var6; ++var9)
		{
			for(int var10 = var7; var10 <= var8; ++var10)
			{
				if(chunkExists(var9, var10))
				{
					getChunkFromChunkCoords(var9, var10).getEntitiesWithinAABBForEntity(par1Entity, par2AxisAlignedBB, var4, par3IEntitySelector);
				}
			}
		}
		return var4;
	}
	
	public abstract Entity getEntityByID(int var1);
	
	public PathEntity getEntityPathToXYZ(Entity par1Entity, int par2, int par3, int par4, float par5, boolean par6, boolean par7, boolean par8, boolean par9)
	{
		theProfiler.startSection("pathfind");
		int var10 = MathHelper.floor_double(par1Entity.posX);
		int var11 = MathHelper.floor_double(par1Entity.posY);
		int var12 = MathHelper.floor_double(par1Entity.posZ);
		int var13 = (int) (par5 + 8.0F);
		int var14 = var10 - var13;
		int var15 = var11 - var13;
		int var16 = var12 - var13;
		int var17 = var10 + var13;
		int var18 = var11 + var13;
		int var19 = var12 + var13;
		ChunkCache var20 = new ChunkCache(this, var14, var15, var16, var17, var18, var19, 0);
		PathEntity var21 = new PathFinder(var20, par6, par7, par8, par9).createEntityPathTo(par1Entity, par2, par3, par4, par5);
		theProfiler.endSection();
		return var21;
	}
	
	public int getFirstUncoveredBlock(int par1, int par2)
	{
		int var3;
		for(var3 = 63; !isAirBlock(par1, var3 + 1, par2); ++var3)
		{
			;
		}
		return getBlockId(par1, var3, par2);
	}
	
	public Vec3 getFogColor(float par1)
	{
		float var2 = getCelestialAngle(par1);
		return provider.getFogColor(var2, par1);
	}
	
	public int getFullBlockLightValue(int par1, int par2, int par3)
	{
		if(par2 < 0) return 0;
		else
		{
			if(par2 >= 256)
			{
				par2 = 255;
			}
			return getChunkFromChunkCoords(par1 >> 4, par3 >> 4).getBlockLightValue(par1 & 15, par2, par3 & 15, 0);
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
	
	public int getHeightValue(int par1, int par2)
	{
		if(par1 >= -30000000 && par2 >= -30000000 && par1 < 30000000 && par2 < 30000000)
		{
			if(!chunkExists(par1 >> 4, par2 >> 4)) return 0;
			else
			{
				Chunk var3 = getChunkFromChunkCoords(par1 >> 4, par2 >> 4);
				return var3.getHeightValue(par1 & 15, par2 & 15);
			}
		} else return 0;
	}
	
	public double getHorizon()
	{
		return worldInfo.getTerrainType() == WorldType.FLAT ? 0.0D : 63.0D;
	}
	
	public int getIndirectPowerLevelTo(int par1, int par2, int par3, int par4)
	{
		if(isBlockNormalCube(par1, par2, par3)) return getBlockPowerInput(par1, par2, par3);
		else
		{
			int var5 = getBlockId(par1, par2, par3);
			return var5 == 0 ? 0 : Block.blocksList[var5].isProvidingWeakPower(this, par1, par2, par3, par4);
		}
	}
	
	public boolean getIndirectPowerOutput(int par1, int par2, int par3, int par4)
	{
		return getIndirectPowerLevelTo(par1, par2, par3, par4) > 0;
	}
	
	@Override public float getLightBrightness(int par1, int par2, int par3)
	{
		return provider.lightBrightnessTable[getBlockLightValue(par1, par2, par3)];
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
	
	public PathEntity getPathEntityToEntity(Entity par1Entity, Entity par2Entity, float par3, boolean par4, boolean par5, boolean par6, boolean par7)
	{
		theProfiler.startSection("pathfind");
		int var8 = MathHelper.floor_double(par1Entity.posX);
		int var9 = MathHelper.floor_double(par1Entity.posY + 1.0D);
		int var10 = MathHelper.floor_double(par1Entity.posZ);
		int var11 = (int) (par3 + 16.0F);
		int var12 = var8 - var11;
		int var13 = var9 - var11;
		int var14 = var10 - var11;
		int var15 = var8 + var11;
		int var16 = var9 + var11;
		int var17 = var10 + var11;
		ChunkCache var18 = new ChunkCache(this, var12, var13, var14, var15, var16, var17, 0);
		PathEntity var19 = new PathFinder(var18, par4, par5, par6, par7).createEntityPathTo(par1Entity, par2Entity, par3);
		theProfiler.endSection();
		return var19;
	}
	
	public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2)
	{
		return null;
	}
	
	public EntityPlayer getPlayerEntityByName(String par1Str)
	{
		for(int var2 = 0; var2 < playerEntities.size(); ++var2)
		{
			if(par1Str.equals(((EntityPlayer) playerEntities.get(var2)).username)) return (EntityPlayer) playerEntities.get(var2);
		}
		return null;
	}
	
	public int getPrecipitationHeight(int par1, int par2)
	{
		return getChunkFromBlockCoords(par1, par2).getPrecipitationHeight(par1 & 15, par2 & 15);
	}
	
	public String getProviderName()
	{
		return chunkProvider.makeString();
	}
	
	public float getRainStrength(float par1)
	{
		return prevRainingStrength + (rainingStrength - prevRainingStrength) * par1;
	}
	
	public int getSavedLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
	{
		if(par3 < 0)
		{
			par3 = 0;
		}
		if(par3 >= 256)
		{
			par3 = 255;
		}
		if(par2 >= -30000000 && par4 >= -30000000 && par2 < 30000000 && par4 < 30000000)
		{
			int var5 = par2 >> 4;
			int var6 = par4 >> 4;
			if(!chunkExists(var5, var6)) return par1EnumSkyBlock.defaultLightValue;
			else
			{
				Chunk var7 = getChunkFromChunkCoords(var5, var6);
				return var7.getSavedLightValue(par1EnumSkyBlock, par2 & 15, par3, par4 & 15);
			}
		} else return par1EnumSkyBlock.defaultLightValue;
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
	
	public int getStrongestIndirectPower(int par1, int par2, int par3)
	{
		int var4 = 0;
		for(int var5 = 0; var5 < 6; ++var5)
		{
			int var6 = getIndirectPowerLevelTo(par1 + Facing.offsetsXForSide[var5], par2 + Facing.offsetsYForSide[var5], par3 + Facing.offsetsZForSide[var5], var5);
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
	
	public int getTopSolidOrLiquidBlock(int par1, int par2)
	{
		Chunk var3 = getChunkFromBlockCoords(par1, par2);
		int var4 = var3.getTopFilledSegment() + 15;
		par1 &= 15;
		for(par2 &= 15; var4 > 0; --var4)
		{
			int var5 = var3.getBlockID(par1, var4, par2);
			if(var5 != 0 && Block.blocksList[var5].blockMaterial.blocksMovement() && Block.blocksList[var5].blockMaterial != Material.leaves) return var4 + 1;
		}
		return -1;
	}
	
	public long getTotalWorldTime()
	{
		return worldInfo.getWorldTotalTime();
	}
	
	public int getUniqueDataId(String par1Str)
	{
		return mapStorage.getUniqueDataId(par1Str);
	}
	
	public float getWeightedThunderStrength(float par1)
	{
		return (prevThunderingStrength + (thunderingStrength - prevThunderingStrength) * par1) * getRainStrength(par1);
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
	
	public boolean handleMaterialAcceleration(AxisAlignedBB par1AxisAlignedBB, Material par2Material, Entity par3Entity)
	{
		int var4 = MathHelper.floor_double(par1AxisAlignedBB.minX);
		int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
		int var6 = MathHelper.floor_double(par1AxisAlignedBB.minY);
		int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
		int var8 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
		int var9 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);
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
						if(var15 != null && var15.blockMaterial == par2Material)
						{
							double var16 = var13 + 1 - BlockFluid.getFluidHeightPercent(getBlockMetadata(var12, var13, var14));
							if(var7 >= var16)
							{
								var10 = true;
								var15.velocityToAddToEntity(this, var12, var13, var14, par3Entity, var11);
							}
						}
					}
				}
			}
			if(var11.lengthVector() > 0.0D && par3Entity.func_96092_aw())
			{
				var11 = var11.normalize();
				double var18 = 0.014D;
				par3Entity.motionX += var11.xCoord * var18;
				par3Entity.motionY += var11.yCoord * var18;
				par3Entity.motionZ += var11.zCoord * var18;
			}
			return var10;
		}
	}
	
	protected void initialize(WorldSettings par1WorldSettings)
	{
		worldInfo.setServerInitialized(true);
	}
	
	public boolean isAABBInMaterial(AxisAlignedBB par1AxisAlignedBB, Material par2Material)
	{
		int var3 = MathHelper.floor_double(par1AxisAlignedBB.minX);
		int var4 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
		int var5 = MathHelper.floor_double(par1AxisAlignedBB.minY);
		int var6 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
		int var7 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
		int var8 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);
		for(int var9 = var3; var9 < var4; ++var9)
		{
			for(int var10 = var5; var10 < var6; ++var10)
			{
				for(int var11 = var7; var11 < var8; ++var11)
				{
					Block var12 = Block.blocksList[getBlockId(var9, var10, var11)];
					if(var12 != null && var12.blockMaterial == par2Material)
					{
						int var13 = getBlockMetadata(var9, var10, var11);
						double var14 = var10 + 1;
						if(var13 < 8)
						{
							var14 = var10 + 1 - var13 / 8.0D;
						}
						if(var14 >= par1AxisAlignedBB.minY) return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override public boolean isAirBlock(int par1, int par2, int par3)
	{
		return getBlockId(par1, par2, par3) == 0;
	}
	
	public boolean isAnyLiquid(AxisAlignedBB par1AxisAlignedBB)
	{
		int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
		int var3 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
		int var4 = MathHelper.floor_double(par1AxisAlignedBB.minY);
		int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
		int var6 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
		int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);
		if(par1AxisAlignedBB.minX < 0.0D)
		{
			--var2;
		}
		if(par1AxisAlignedBB.minY < 0.0D)
		{
			--var4;
		}
		if(par1AxisAlignedBB.minZ < 0.0D)
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
	
	public boolean isBlockFreezable(int par1, int par2, int par3)
	{
		return canBlockFreeze(par1, par2, par3, false);
	}
	
	public boolean isBlockFreezableNaturally(int par1, int par2, int par3)
	{
		return canBlockFreeze(par1, par2, par3, true);
	}
	
	public boolean isBlockFullCube(int par1, int par2, int par3)
	{
		int var4 = getBlockId(par1, par2, par3);
		if(var4 != 0 && Block.blocksList[var4] != null)
		{
			AxisAlignedBB var5 = Block.blocksList[var4].getCollisionBoundingBoxFromPool(this, par1, par2, par3);
			return var5 != null && var5.getAverageEdgeLength() >= 1.0D;
		} else return false;
	}
	
	public boolean isBlockHighHumidity(int par1, int par2, int par3)
	{
		BiomeGenBase var4 = getBiomeGenForCoords(par1, par3);
		return var4.isHighHumidity();
	}
	
	public boolean isBlockIndirectlyGettingPowered(int par1, int par2, int par3)
	{
		return getIndirectPowerLevelTo(par1, par2 - 1, par3, 0) > 0 ? true : getIndirectPowerLevelTo(par1, par2 + 1, par3, 1) > 0 ? true : getIndirectPowerLevelTo(par1, par2, par3 - 1, 2) > 0 ? true : getIndirectPowerLevelTo(par1, par2, par3 + 1, 3) > 0 ? true : getIndirectPowerLevelTo(par1 - 1, par2, par3, 4) > 0 ? true : getIndirectPowerLevelTo(par1 + 1, par2, par3, 5) > 0;
	}
	
	@Override public boolean isBlockNormalCube(int par1, int par2, int par3)
	{
		return Block.isNormalCube(getBlockId(par1, par2, par3));
	}
	
	public boolean isBlockNormalCubeDefault(int par1, int par2, int par3, boolean par4)
	{
		if(par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000)
		{
			Chunk var5 = chunkProvider.provideChunk(par1 >> 4, par3 >> 4);
			if(var5 != null && !var5.isEmpty())
			{
				Block var6 = Block.blocksList[getBlockId(par1, par2, par3)];
				return var6 == null ? false : var6.blockMaterial.isOpaque() && var6.renderAsNormalBlock();
			} else return par4;
		} else return par4;
	}
	
	@Override public boolean isBlockOpaqueCube(int par1, int par2, int par3)
	{
		Block var4 = Block.blocksList[getBlockId(par1, par2, par3)];
		return var4 == null ? false : var4.isOpaqueCube();
	}
	
	@Override public int isBlockProvidingPowerTo(int par1, int par2, int par3, int par4)
	{
		int var5 = getBlockId(par1, par2, par3);
		return var5 == 0 ? 0 : Block.blocksList[var5].isProvidingStrongPower(this, par1, par2, par3, par4);
	}
	
	public boolean isBlockTickScheduledThisTick(int par1, int par2, int par3, int par4)
	{
		return false;
	}
	
	public boolean isBlockTopFacingSurfaceSolid(Block par1Block, int par2)
	{
		return par1Block == null ? false : par1Block.blockMaterial.isOpaque() && par1Block.renderAsNormalBlock() ? true : par1Block instanceof BlockStairs ? (par2 & 4) == 4 : par1Block instanceof BlockHalfSlab ? (par2 & 8) == 8 : par1Block instanceof BlockHopper ? true : par1Block instanceof BlockSnow ? (par2 & 7) == 7 : false;
	}
	
	public boolean isBoundingBoxBurning(AxisAlignedBB par1AxisAlignedBB)
	{
		int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
		int var3 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
		int var4 = MathHelper.floor_double(par1AxisAlignedBB.minY);
		int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
		int var6 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
		int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);
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
	
	public boolean isMaterialInBB(AxisAlignedBB par1AxisAlignedBB, Material par2Material)
	{
		int var3 = MathHelper.floor_double(par1AxisAlignedBB.minX);
		int var4 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
		int var5 = MathHelper.floor_double(par1AxisAlignedBB.minY);
		int var6 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
		int var7 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
		int var8 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);
		for(int var9 = var3; var9 < var4; ++var9)
		{
			for(int var10 = var5; var10 < var6; ++var10)
			{
				for(int var11 = var7; var11 < var8; ++var11)
				{
					Block var12 = Block.blocksList[getBlockId(var9, var10, var11)];
					if(var12 != null && var12.blockMaterial == par2Material) return true;
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
	
	public WorldSavedData loadItemData(Class par1Class, String par2Str)
	{
		return mapStorage.loadData(par1Class, par2Str);
	}
	
	public void markBlockForRenderUpdate(int par1, int par2, int par3)
	{
		for(int var4 = 0; var4 < worldAccesses.size(); ++var4)
		{
			((IWorldAccess) worldAccesses.get(var4)).markBlockForRenderUpdate(par1, par2, par3);
		}
	}
	
	public void markBlockForUpdate(int par1, int par2, int par3)
	{
		for(int var4 = 0; var4 < worldAccesses.size(); ++var4)
		{
			((IWorldAccess) worldAccesses.get(var4)).markBlockForUpdate(par1, par2, par3);
		}
	}
	
	public void markBlockRangeForRenderUpdate(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		for(int var7 = 0; var7 < worldAccesses.size(); ++var7)
		{
			((IWorldAccess) worldAccesses.get(var7)).markBlockRangeForRenderUpdate(par1, par2, par3, par4, par5, par6);
		}
	}
	
	public void markBlocksDirtyVertical(int par1, int par2, int par3, int par4)
	{
		int var5;
		if(par3 > par4)
		{
			var5 = par4;
			par4 = par3;
			par3 = var5;
		}
		if(!provider.hasNoSky)
		{
			for(var5 = par3; var5 <= par4; ++var5)
			{
				updateLightByType(EnumSkyBlock.Sky, par1, var5, par2);
			}
		}
		markBlockRangeForRenderUpdate(par1, par3, par2, par1, par4, par2);
	}
	
	public void markTileEntityForDespawn(TileEntity par1TileEntity)
	{
		entityRemoval.add(par1TileEntity);
	}
	
	protected void moodSoundAndLightCheck(int par1, int par2, Chunk par3Chunk)
	{
		theProfiler.endStartSection("moodSound");
		if(ambientTickCountdown == 0 && !isRemote)
		{
			updateLCG = updateLCG * 3 + 1013904223;
			int var4 = updateLCG >> 2;
			int var5 = var4 & 15;
			int var6 = var4 >> 8 & 15;
			int var7 = var4 >> 16 & 127;
			int var8 = par3Chunk.getBlockID(var5, var7, var6);
			var5 += par1;
			var6 += par2;
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
		par3Chunk.enqueueRelightChecks();
	}
	
	public Explosion newExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9, boolean par10)
	{
		Explosion var11 = new Explosion(this, par1Entity, par2, par4, par6, par8);
		var11.isFlaming = par9;
		var11.isSmoking = par10;
		var11.doExplosionA();
		var11.doExplosionB(true);
		return var11;
	}
	
	public void notifyBlockChange(int par1, int par2, int par3, int par4)
	{
		this.notifyBlocksOfNeighborChange(par1, par2, par3, par4);
	}
	
	public void notifyBlockOfNeighborChange(int par1, int par2, int par3, int par4)
	{
		if(!isRemote)
		{
			int var5 = getBlockId(par1, par2, par3);
			Block var6 = Block.blocksList[var5];
			if(var6 != null)
			{
				try
				{
					var6.onNeighborBlockChange(this, par1, par2, par3, par4);
				} catch(Throwable var13)
				{
					CrashReport var8 = CrashReport.makeCrashReport(var13, "Exception while updating neighbours");
					CrashReportCategory var9 = var8.makeCategory("Block being updated");
					int var10;
					try
					{
						var10 = getBlockMetadata(par1, par2, par3);
					} catch(Throwable var12)
					{
						var10 = -1;
					}
					var9.addCrashSectionCallable("Source block type", new CallableLvl1(this, par4));
					CrashReportCategory.func_85068_a(var9, par1, par2, par3, var5, var10);
					throw new ReportedException(var8);
				}
			}
		}
	}
	
	public void notifyBlocksOfNeighborChange(int par1, int par2, int par3, int par4)
	{
		notifyBlockOfNeighborChange(par1 - 1, par2, par3, par4);
		notifyBlockOfNeighborChange(par1 + 1, par2, par3, par4);
		notifyBlockOfNeighborChange(par1, par2 - 1, par3, par4);
		notifyBlockOfNeighborChange(par1, par2 + 1, par3, par4);
		notifyBlockOfNeighborChange(par1, par2, par3 - 1, par4);
		notifyBlockOfNeighborChange(par1, par2, par3 + 1, par4);
	}
	
	public void notifyBlocksOfNeighborChange(int par1, int par2, int par3, int par4, int par5)
	{
		if(par5 != 4)
		{
			notifyBlockOfNeighborChange(par1 - 1, par2, par3, par4);
		}
		if(par5 != 5)
		{
			notifyBlockOfNeighborChange(par1 + 1, par2, par3, par4);
		}
		if(par5 != 0)
		{
			notifyBlockOfNeighborChange(par1, par2 - 1, par3, par4);
		}
		if(par5 != 1)
		{
			notifyBlockOfNeighborChange(par1, par2 + 1, par3, par4);
		}
		if(par5 != 2)
		{
			notifyBlockOfNeighborChange(par1, par2, par3 - 1, par4);
		}
		if(par5 != 3)
		{
			notifyBlockOfNeighborChange(par1, par2, par3 + 1, par4);
		}
	}
	
	protected void onEntityAdded(Entity par1Entity)
	{
		for(int var2 = 0; var2 < worldAccesses.size(); ++var2)
		{
			((IWorldAccess) worldAccesses.get(var2)).onEntityCreate(par1Entity);
		}
	}
	
	protected void onEntityRemoved(Entity par1Entity)
	{
		for(int var2 = 0; var2 < worldAccesses.size(); ++var2)
		{
			((IWorldAccess) worldAccesses.get(var2)).onEntityDestroy(par1Entity);
		}
	}
	
	public void playAuxSFX(int par1, int par2, int par3, int par4, int par5)
	{
		playAuxSFXAtEntity((EntityPlayer) null, par1, par2, par3, par4, par5);
	}
	
	public void playAuxSFXAtEntity(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6)
	{
		try
		{
			for(int var7 = 0; var7 < worldAccesses.size(); ++var7)
			{
				((IWorldAccess) worldAccesses.get(var7)).playAuxSFX(par1EntityPlayer, par2, par3, par4, par5, par6);
			}
		} catch(Throwable var10)
		{
			CrashReport var8 = CrashReport.makeCrashReport(var10, "Playing level event");
			CrashReportCategory var9 = var8.makeCategory("Level event being played");
			var9.addCrashSection("Block coordinates", CrashReportCategory.getLocationInfo(par3, par4, par5));
			var9.addCrashSection("Event source", par1EntityPlayer);
			var9.addCrashSection("Event type", Integer.valueOf(par2));
			var9.addCrashSection("Event data", Integer.valueOf(par6));
			throw new ReportedException(var8);
		}
	}
	
	public void playRecord(String par1Str, int par2, int par3, int par4)
	{
		for(int var5 = 0; var5 < worldAccesses.size(); ++var5)
		{
			((IWorldAccess) worldAccesses.get(var5)).playRecord(par1Str, par2, par3, par4);
		}
	}
	
	public void playSound(double par1, double par3, double par5, String par7Str, float par8, float par9, boolean par10)
	{
	}
	
	public void playSoundAtEntity(Entity par1Entity, String par2Str, float par3, float par4)
	{
		if(par1Entity != null && par2Str != null)
		{
			for(int var5 = 0; var5 < worldAccesses.size(); ++var5)
			{
				((IWorldAccess) worldAccesses.get(var5)).playSound(par2Str, par1Entity.posX, par1Entity.posY - par1Entity.yOffset, par1Entity.posZ, par3, par4);
			}
		}
	}
	
	public void playSoundEffect(double par1, double par3, double par5, String par7Str, float par8, float par9)
	{
		if(par7Str != null)
		{
			for(int var10 = 0; var10 < worldAccesses.size(); ++var10)
			{
				((IWorldAccess) worldAccesses.get(var10)).playSound(par7Str, par1, par3, par5, par8, par9);
			}
		}
	}
	
	public void playSoundToNearExcept(EntityPlayer par1EntityPlayer, String par2Str, float par3, float par4)
	{
		if(par1EntityPlayer != null && par2Str != null)
		{
			for(int var5 = 0; var5 < worldAccesses.size(); ++var5)
			{
				((IWorldAccess) worldAccesses.get(var5)).playSoundToNearExcept(par1EntityPlayer, par2Str, par1EntityPlayer.posX, par1EntityPlayer.posY - par1EntityPlayer.yOffset, par1EntityPlayer.posZ, par3, par4);
			}
		}
	}
	
	public MovingObjectPosition rayTraceBlocks_do_do(Vec3 par1Vec3, Vec3 par2Vec3, boolean par3, boolean par4)
	{
		if(!Double.isNaN(par1Vec3.xCoord) && !Double.isNaN(par1Vec3.yCoord) && !Double.isNaN(par1Vec3.zCoord))
		{
			if(!Double.isNaN(par2Vec3.xCoord) && !Double.isNaN(par2Vec3.yCoord) && !Double.isNaN(par2Vec3.zCoord))
			{
				int var5 = MathHelper.floor_double(par2Vec3.xCoord);
				int var6 = MathHelper.floor_double(par2Vec3.yCoord);
				int var7 = MathHelper.floor_double(par2Vec3.zCoord);
				int var8 = MathHelper.floor_double(par1Vec3.xCoord);
				int var9 = MathHelper.floor_double(par1Vec3.yCoord);
				int var10 = MathHelper.floor_double(par1Vec3.zCoord);
				int var11 = getBlockId(var8, var9, var10);
				int var12 = getBlockMetadata(var8, var9, var10);
				Block var13 = Block.blocksList[var11];
				if((!par4 || var13 == null || var13.getCollisionBoundingBoxFromPool(this, var8, var9, var10) != null) && var11 > 0 && var13.canCollideCheck(var12, par3))
				{
					MovingObjectPosition var14 = var13.collisionRayTrace(this, var8, var9, var10, par1Vec3, par2Vec3);
					if(var14 != null) return var14;
				}
				var11 = 200;
				while(var11-- >= 0)
				{
					if(Double.isNaN(par1Vec3.xCoord) || Double.isNaN(par1Vec3.yCoord) || Double.isNaN(par1Vec3.zCoord)) return null;
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
					double var27 = par2Vec3.xCoord - par1Vec3.xCoord;
					double var29 = par2Vec3.yCoord - par1Vec3.yCoord;
					double var31 = par2Vec3.zCoord - par1Vec3.zCoord;
					if(var39)
					{
						var21 = (var15 - par1Vec3.xCoord) / var27;
					}
					if(var40)
					{
						var23 = (var17 - par1Vec3.yCoord) / var29;
					}
					if(var41)
					{
						var25 = (var19 - par1Vec3.zCoord) / var31;
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
						par1Vec3.xCoord = var15;
						par1Vec3.yCoord += var29 * var21;
						par1Vec3.zCoord += var31 * var21;
					} else if(var23 < var25)
					{
						if(var6 > var9)
						{
							var42 = 0;
						} else
						{
							var42 = 1;
						}
						par1Vec3.xCoord += var27 * var23;
						par1Vec3.yCoord = var17;
						par1Vec3.zCoord += var31 * var23;
					} else
					{
						if(var7 > var10)
						{
							var42 = 2;
						} else
						{
							var42 = 3;
						}
						par1Vec3.xCoord += var27 * var25;
						par1Vec3.yCoord += var29 * var25;
						par1Vec3.zCoord = var19;
					}
					Vec3 var34 = getWorldVec3Pool().getVecFromPool(par1Vec3.xCoord, par1Vec3.yCoord, par1Vec3.zCoord);
					var8 = (int) (var34.xCoord = MathHelper.floor_double(par1Vec3.xCoord));
					if(var42 == 5)
					{
						--var8;
						++var34.xCoord;
					}
					var9 = (int) (var34.yCoord = MathHelper.floor_double(par1Vec3.yCoord));
					if(var42 == 1)
					{
						--var9;
						++var34.yCoord;
					}
					var10 = (int) (var34.zCoord = MathHelper.floor_double(par1Vec3.zCoord));
					if(var42 == 3)
					{
						--var10;
						++var34.zCoord;
					}
					int var35 = getBlockId(var8, var9, var10);
					int var36 = getBlockMetadata(var8, var9, var10);
					Block var37 = Block.blocksList[var35];
					if((!par4 || var37 == null || var37.getCollisionBoundingBoxFromPool(this, var8, var9, var10) != null) && var35 > 0 && var37.canCollideCheck(var36, par3))
					{
						MovingObjectPosition var38 = var37.collisionRayTrace(this, var8, var9, var10, par1Vec3, par2Vec3);
						if(var38 != null) return var38;
					}
				}
				return null;
			} else return null;
		} else return null;
	}
	
	public void removeBlockTileEntity(int par1, int par2, int par3)
	{
		TileEntity var4 = getBlockTileEntity(par1, par2, par3);
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
			Chunk var5 = getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
			if(var5 != null)
			{
				var5.removeChunkBlockTileEntity(par1 & 15, par2, par3 & 15);
			}
		}
	}
	
	public void removeEntity(Entity par1Entity)
	{
		if(par1Entity.riddenByEntity != null)
		{
			par1Entity.riddenByEntity.mountEntity((Entity) null);
		}
		if(par1Entity.ridingEntity != null)
		{
			par1Entity.mountEntity((Entity) null);
		}
		par1Entity.setDead();
		if(par1Entity instanceof EntityPlayer)
		{
			playerEntities.remove(par1Entity);
			updateAllPlayersSleepingFlag();
		}
	}
	
	public void removePlayerEntityDangerously(Entity par1Entity)
	{
		par1Entity.setDead();
		if(par1Entity instanceof EntityPlayer)
		{
			playerEntities.remove(par1Entity);
			updateAllPlayersSleepingFlag();
		}
		int var2 = par1Entity.chunkCoordX;
		int var3 = par1Entity.chunkCoordZ;
		if(par1Entity.addedToChunk && chunkExists(var2, var3))
		{
			getChunkFromChunkCoords(var2, var3).removeEntity(par1Entity);
		}
		loadedEntityList.remove(par1Entity);
		onEntityRemoved(par1Entity);
	}
	
	public void removeWorldAccess(IWorldAccess par1IWorldAccess)
	{
		worldAccesses.remove(par1IWorldAccess);
	}
	
	public void scheduleBlockUpdate(int par1, int par2, int par3, int par4, int par5)
	{
	}
	
	public void scheduleBlockUpdateFromLoad(int par1, int par2, int par3, int par4, int par5, int par6)
	{
	}
	
	public void scheduleBlockUpdateWithPriority(int par1, int par2, int par3, int par4, int par5, int par6)
	{
	}
	
	public List selectEntitiesWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, IEntitySelector par3IEntitySelector)
	{
		int var4 = MathHelper.floor_double((par2AxisAlignedBB.minX - 2.0D) / 16.0D);
		int var5 = MathHelper.floor_double((par2AxisAlignedBB.maxX + 2.0D) / 16.0D);
		int var6 = MathHelper.floor_double((par2AxisAlignedBB.minZ - 2.0D) / 16.0D);
		int var7 = MathHelper.floor_double((par2AxisAlignedBB.maxZ + 2.0D) / 16.0D);
		ArrayList var8 = new ArrayList();
		for(int var9 = var4; var9 <= var5; ++var9)
		{
			for(int var10 = var6; var10 <= var7; ++var10)
			{
				if(chunkExists(var9, var10))
				{
					getChunkFromChunkCoords(var9, var10).getEntitiesOfTypeWithinAAAB(par1Class, par2AxisAlignedBB, var8, par3IEntitySelector);
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
	
	public void setAllowedSpawnTypes(boolean par1, boolean par2)
	{
		spawnHostileMobs = par1;
		spawnPeacefulMobs = par2;
	}
	
	public boolean setBlock(int par1, int par2, int par3, int par4)
	{
		return this.setBlock(par1, par2, par3, par4, 0, 3);
	}
	
	public boolean setBlock(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		if(par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000)
		{
			if(par2 < 0) return false;
			else if(par2 >= 256) return false;
			else
			{
				Chunk var7 = getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
				int var8 = 0;
				if((par6 & 1) != 0)
				{
					var8 = var7.getBlockID(par1 & 15, par2, par3 & 15);
				}
				boolean var9 = var7.setBlockIDWithMetadata(par1 & 15, par2, par3 & 15, par4, par5);
				theProfiler.startSection("checkLight");
				updateAllLightTypes(par1, par2, par3);
				theProfiler.endSection();
				if(var9)
				{
					if((par6 & 2) != 0 && (!isRemote || (par6 & 4) == 0))
					{
						markBlockForUpdate(par1, par2, par3);
					}
					if(!isRemote && (par6 & 1) != 0)
					{
						notifyBlockChange(par1, par2, par3, var8);
						Block var10 = Block.blocksList[par4];
						if(var10 != null && var10.hasComparatorInputOverride())
						{
							func_96440_m(par1, par2, par3, par4);
						}
					}
				}
				return var9;
			}
		} else return false;
	}
	
	public boolean setBlockMetadataWithNotify(int par1, int par2, int par3, int par4, int par5)
	{
		if(par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000)
		{
			if(par2 < 0) return false;
			else if(par2 >= 256) return false;
			else
			{
				Chunk var6 = getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
				int var7 = par1 & 15;
				int var8 = par3 & 15;
				boolean var9 = var6.setBlockMetadata(var7, par2, var8, par4);
				if(var9)
				{
					int var10 = var6.getBlockID(var7, par2, var8);
					if((par5 & 2) != 0 && (!isRemote || (par5 & 4) == 0))
					{
						markBlockForUpdate(par1, par2, par3);
					}
					if(!isRemote && (par5 & 1) != 0)
					{
						notifyBlockChange(par1, par2, par3, var10);
						Block var11 = Block.blocksList[var10];
						if(var11 != null && var11.hasComparatorInputOverride())
						{
							func_96440_m(par1, par2, par3, var10);
						}
					}
				}
				return var9;
			}
		} else return false;
	}
	
	public void setBlockTileEntity(int par1, int par2, int par3, TileEntity par4TileEntity)
	{
		if(par4TileEntity != null && !par4TileEntity.isInvalid())
		{
			if(scanningTileEntities)
			{
				par4TileEntity.xCoord = par1;
				par4TileEntity.yCoord = par2;
				par4TileEntity.zCoord = par3;
				Iterator var5 = addedTileEntityList.iterator();
				while(var5.hasNext())
				{
					TileEntity var6 = (TileEntity) var5.next();
					if(var6.xCoord == par1 && var6.yCoord == par2 && var6.zCoord == par3)
					{
						var6.invalidate();
						var5.remove();
					}
				}
				addedTileEntityList.add(par4TileEntity);
			} else
			{
				loadedTileEntityList.add(par4TileEntity);
				Chunk var7 = getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
				if(var7 != null)
				{
					var7.setChunkBlockTileEntity(par1 & 15, par2, par3 & 15, par4TileEntity);
				}
			}
		}
	}
	
	public boolean setBlockToAir(int par1, int par2, int par3)
	{
		return this.setBlock(par1, par2, par3, 0, 0, 3);
	}
	
	public void setEntityState(Entity par1Entity, byte par2)
	{
	}
	
	public void setItemData(String par1Str, WorldSavedData par2WorldSavedData)
	{
		mapStorage.setData(par1Str, par2WorldSavedData);
	}
	
	public void setLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4, int par5)
	{
		if(par2 >= -30000000 && par4 >= -30000000 && par2 < 30000000 && par4 < 30000000)
		{
			if(par3 >= 0)
			{
				if(par3 < 256)
				{
					if(chunkExists(par2 >> 4, par4 >> 4))
					{
						Chunk var6 = getChunkFromChunkCoords(par2 >> 4, par4 >> 4);
						var6.setLightValue(par1EnumSkyBlock, par2 & 15, par3, par4 & 15, par5);
						for(int var7 = 0; var7 < worldAccesses.size(); ++var7)
						{
							((IWorldAccess) worldAccesses.get(var7)).markBlockForRenderUpdate(par2, par3, par4);
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
	
	public Random setRandomSeed(int par1, int par2, int par3)
	{
		long var4 = par1 * 341873128712L + par2 * 132897987541L + getWorldInfo().getSeed() + par3;
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
	
	public void setWorldTime(long par1)
	{
		worldInfo.setWorldTime(par1);
	}
	
	public boolean spawnEntityInWorld(Entity par1Entity)
	{
		int var2 = MathHelper.floor_double(par1Entity.posX / 16.0D);
		int var3 = MathHelper.floor_double(par1Entity.posZ / 16.0D);
		boolean var4 = par1Entity.field_98038_p;
		if(par1Entity instanceof EntityPlayer)
		{
			var4 = true;
		}
		if(!var4 && !chunkExists(var2, var3)) return false;
		else
		{
			if(par1Entity instanceof EntityPlayer)
			{
				EntityPlayer var5 = (EntityPlayer) par1Entity;
				playerEntities.add(var5);
				updateAllPlayersSleepingFlag();
			}
			getChunkFromChunkCoords(var2, var3).addEntity(par1Entity);
			loadedEntityList.add(par1Entity);
			onEntityAdded(par1Entity);
			return true;
		}
	}
	
	public void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		for(int var14 = 0; var14 < worldAccesses.size(); ++var14)
		{
			((IWorldAccess) worldAccesses.get(var14)).spawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
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
	
	public boolean tickUpdates(boolean par1)
	{
		return false;
	}
	
	public void toggleRain()
	{
		worldInfo.setRainTime(1);
	}
	
	public void unloadEntities(List par1List)
	{
		unloadedEntityList.addAll(par1List);
	}
	
	public void updateAllLightTypes(int par1, int par2, int par3)
	{
		if(!provider.hasNoSky)
		{
			updateLightByType(EnumSkyBlock.Sky, par1, par2, par3);
		}
		updateLightByType(EnumSkyBlock.Block, par1, par2, par3);
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
	
	public void updateEntity(Entity par1Entity)
	{
		updateEntityWithOptionalForce(par1Entity, true);
	}
	
	public void updateEntityWithOptionalForce(Entity par1Entity, boolean par2)
	{
		int var3 = MathHelper.floor_double(par1Entity.posX);
		int var4 = MathHelper.floor_double(par1Entity.posZ);
		byte var5 = 32;
		if(!par2 || checkChunksExist(var3 - var5, 0, var4 - var5, var3 + var5, 0, var4 + var5))
		{
			par1Entity.lastTickPosX = par1Entity.posX;
			par1Entity.lastTickPosY = par1Entity.posY;
			par1Entity.lastTickPosZ = par1Entity.posZ;
			par1Entity.prevRotationYaw = par1Entity.rotationYaw;
			par1Entity.prevRotationPitch = par1Entity.rotationPitch;
			if(par2 && par1Entity.addedToChunk)
			{
				if(par1Entity.ridingEntity != null)
				{
					par1Entity.updateRidden();
				} else
				{
					++par1Entity.ticksExisted;
					par1Entity.onUpdate();
				}
			}
			theProfiler.startSection("chunkCheck");
			if(Double.isNaN(par1Entity.posX) || Double.isInfinite(par1Entity.posX))
			{
				par1Entity.posX = par1Entity.lastTickPosX;
			}
			if(Double.isNaN(par1Entity.posY) || Double.isInfinite(par1Entity.posY))
			{
				par1Entity.posY = par1Entity.lastTickPosY;
			}
			if(Double.isNaN(par1Entity.posZ) || Double.isInfinite(par1Entity.posZ))
			{
				par1Entity.posZ = par1Entity.lastTickPosZ;
			}
			if(Double.isNaN(par1Entity.rotationPitch) || Double.isInfinite(par1Entity.rotationPitch))
			{
				par1Entity.rotationPitch = par1Entity.prevRotationPitch;
			}
			if(Double.isNaN(par1Entity.rotationYaw) || Double.isInfinite(par1Entity.rotationYaw))
			{
				par1Entity.rotationYaw = par1Entity.prevRotationYaw;
			}
			int var6 = MathHelper.floor_double(par1Entity.posX / 16.0D);
			int var7 = MathHelper.floor_double(par1Entity.posY / 16.0D);
			int var8 = MathHelper.floor_double(par1Entity.posZ / 16.0D);
			if(!par1Entity.addedToChunk || par1Entity.chunkCoordX != var6 || par1Entity.chunkCoordY != var7 || par1Entity.chunkCoordZ != var8)
			{
				if(par1Entity.addedToChunk && chunkExists(par1Entity.chunkCoordX, par1Entity.chunkCoordZ))
				{
					getChunkFromChunkCoords(par1Entity.chunkCoordX, par1Entity.chunkCoordZ).removeEntityAtIndex(par1Entity, par1Entity.chunkCoordY);
				}
				if(chunkExists(var6, var8))
				{
					par1Entity.addedToChunk = true;
					getChunkFromChunkCoords(var6, var8).addEntity(par1Entity);
				} else
				{
					par1Entity.addedToChunk = false;
				}
			}
			theProfiler.endSection();
			if(par2 && par1Entity.addedToChunk && par1Entity.riddenByEntity != null)
			{
				if(!par1Entity.riddenByEntity.isDead && par1Entity.riddenByEntity.ridingEntity == par1Entity)
				{
					updateEntity(par1Entity.riddenByEntity);
				} else
				{
					par1Entity.riddenByEntity.ridingEntity = null;
					par1Entity.riddenByEntity = null;
				}
			}
		}
	}
	
	public void updateLightByType(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
	{
		if(doChunksNearChunkExist(par2, par3, par4, 17))
		{
			int var5 = 0;
			int var6 = 0;
			theProfiler.startSection("getBrightness");
			int var7 = getSavedLightValue(par1EnumSkyBlock, par2, par3, par4);
			int var8 = computeLightValue(par2, par3, par4, par1EnumSkyBlock);
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
					var10 = (var9 & 63) - 32 + par2;
					var11 = (var9 >> 6 & 63) - 32 + par3;
					var12 = (var9 >> 12 & 63) - 32 + par4;
					var13 = var9 >> 18 & 15;
					var14 = getSavedLightValue(par1EnumSkyBlock, var10, var11, var12);
					if(var14 == var13)
					{
						setLightValue(par1EnumSkyBlock, var10, var11, var12, 0);
						if(var13 > 0)
						{
							var15 = MathHelper.abs_int(var10 - par2);
							var16 = MathHelper.abs_int(var11 - par3);
							var17 = MathHelper.abs_int(var12 - par4);
							if(var15 + var16 + var17 < 17)
							{
								for(int var18 = 0; var18 < 6; ++var18)
								{
									int var19 = var10 + Facing.offsetsXForSide[var18];
									int var20 = var11 + Facing.offsetsYForSide[var18];
									int var21 = var12 + Facing.offsetsZForSide[var18];
									int var22 = Math.max(1, Block.lightOpacity[getBlockId(var19, var20, var21)]);
									var14 = getSavedLightValue(par1EnumSkyBlock, var19, var20, var21);
									if(var14 == var13 - var22 && var6 < lightUpdateBlockList.length)
									{
										lightUpdateBlockList[var6++] = var19 - par2 + 32 | var20 - par3 + 32 << 6 | var21 - par4 + 32 << 12 | var13 - var22 << 18;
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
				var10 = (var9 & 63) - 32 + par2;
				var11 = (var9 >> 6 & 63) - 32 + par3;
				var12 = (var9 >> 12 & 63) - 32 + par4;
				var13 = getSavedLightValue(par1EnumSkyBlock, var10, var11, var12);
				var14 = computeLightValue(var10, var11, var12, par1EnumSkyBlock);
				if(var14 != var13)
				{
					setLightValue(par1EnumSkyBlock, var10, var11, var12, var14);
					if(var14 > var13)
					{
						var15 = Math.abs(var10 - par2);
						var16 = Math.abs(var11 - par3);
						var17 = Math.abs(var12 - par4);
						boolean var23 = var6 < lightUpdateBlockList.length - 6;
						if(var15 + var16 + var17 < 17 && var23)
						{
							if(getSavedLightValue(par1EnumSkyBlock, var10 - 1, var11, var12) < var14)
							{
								lightUpdateBlockList[var6++] = var10 - 1 - par2 + 32 + (var11 - par3 + 32 << 6) + (var12 - par4 + 32 << 12);
							}
							if(getSavedLightValue(par1EnumSkyBlock, var10 + 1, var11, var12) < var14)
							{
								lightUpdateBlockList[var6++] = var10 + 1 - par2 + 32 + (var11 - par3 + 32 << 6) + (var12 - par4 + 32 << 12);
							}
							if(getSavedLightValue(par1EnumSkyBlock, var10, var11 - 1, var12) < var14)
							{
								lightUpdateBlockList[var6++] = var10 - par2 + 32 + (var11 - 1 - par3 + 32 << 6) + (var12 - par4 + 32 << 12);
							}
							if(getSavedLightValue(par1EnumSkyBlock, var10, var11 + 1, var12) < var14)
							{
								lightUpdateBlockList[var6++] = var10 - par2 + 32 + (var11 + 1 - par3 + 32 << 6) + (var12 - par4 + 32 << 12);
							}
							if(getSavedLightValue(par1EnumSkyBlock, var10, var11, var12 - 1) < var14)
							{
								lightUpdateBlockList[var6++] = var10 - par2 + 32 + (var11 - par3 + 32 << 6) + (var12 - 1 - par4 + 32 << 12);
							}
							if(getSavedLightValue(par1EnumSkyBlock, var10, var11, var12 + 1) < var14)
							{
								lightUpdateBlockList[var6++] = var10 - par2 + 32 + (var11 - par3 + 32 << 6) + (var12 + 1 - par4 + 32 << 12);
							}
						}
					}
				}
			}
			theProfiler.endSection();
		}
	}
	
	public void updateTileEntityChunkAndDoNothing(int par1, int par2, int par3, TileEntity par4TileEntity)
	{
		if(blockExists(par1, par2, par3))
		{
			getChunkFromBlockCoords(par1, par3).setChunkModified();
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
