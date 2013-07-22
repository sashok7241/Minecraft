package net.minecraft.src;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BiomeGenBase
{
	public static final BiomeGenBase[] biomeList = new BiomeGenBase[256];
	public static final BiomeGenBase ocean = new BiomeGenOcean(0).setColor(112).setBiomeName("Ocean").setMinMaxHeight(-1.0F, 0.4F);
	public static final BiomeGenBase plains = new BiomeGenPlains(1).setColor(9286496).setBiomeName("Plains").setTemperatureRainfall(0.8F, 0.4F);
	public static final BiomeGenBase desert = new BiomeGenDesert(2).setColor(16421912).setBiomeName("Desert").setDisableRain().setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(0.1F, 0.2F);
	public static final BiomeGenBase extremeHills = new BiomeGenHills(3).setColor(6316128).setBiomeName("Extreme Hills").setMinMaxHeight(0.3F, 1.5F).setTemperatureRainfall(0.2F, 0.3F);
	public static final BiomeGenBase forest = new BiomeGenForest(4).setColor(353825).setBiomeName("Forest").func_76733_a(5159473).setTemperatureRainfall(0.7F, 0.8F);
	public static final BiomeGenBase taiga = new BiomeGenTaiga(5).setColor(747097).setBiomeName("Taiga").func_76733_a(5159473).setEnableSnow().setTemperatureRainfall(0.05F, 0.8F).setMinMaxHeight(0.1F, 0.4F);
	public static final BiomeGenBase swampland = new BiomeGenSwamp(6).setColor(522674).setBiomeName("Swampland").func_76733_a(9154376).setMinMaxHeight(-0.2F, 0.1F).setTemperatureRainfall(0.8F, 0.9F);
	public static final BiomeGenBase river = new BiomeGenRiver(7).setColor(255).setBiomeName("River").setMinMaxHeight(-0.5F, 0.0F);
	public static final BiomeGenBase hell = new BiomeGenHell(8).setColor(16711680).setBiomeName("Hell").setDisableRain().setTemperatureRainfall(2.0F, 0.0F);
	public static final BiomeGenBase sky = new BiomeGenEnd(9).setColor(8421631).setBiomeName("Sky").setDisableRain();
	public static final BiomeGenBase frozenOcean = new BiomeGenOcean(10).setColor(9474208).setBiomeName("FrozenOcean").setEnableSnow().setMinMaxHeight(-1.0F, 0.5F).setTemperatureRainfall(0.0F, 0.5F);
	public static final BiomeGenBase frozenRiver = new BiomeGenRiver(11).setColor(10526975).setBiomeName("FrozenRiver").setEnableSnow().setMinMaxHeight(-0.5F, 0.0F).setTemperatureRainfall(0.0F, 0.5F);
	public static final BiomeGenBase icePlains = new BiomeGenSnow(12).setColor(16777215).setBiomeName("Ice Plains").setEnableSnow().setTemperatureRainfall(0.0F, 0.5F);
	public static final BiomeGenBase iceMountains = new BiomeGenSnow(13).setColor(10526880).setBiomeName("Ice Mountains").setEnableSnow().setMinMaxHeight(0.3F, 1.3F).setTemperatureRainfall(0.0F, 0.5F);
	public static final BiomeGenBase mushroomIsland = new BiomeGenMushroomIsland(14).setColor(16711935).setBiomeName("MushroomIsland").setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(0.2F, 1.0F);
	public static final BiomeGenBase mushroomIslandShore = new BiomeGenMushroomIsland(15).setColor(10486015).setBiomeName("MushroomIslandShore").setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(-1.0F, 0.1F);
	public static final BiomeGenBase beach = new BiomeGenBeach(16).setColor(16440917).setBiomeName("Beach").setTemperatureRainfall(0.8F, 0.4F).setMinMaxHeight(0.0F, 0.1F);
	public static final BiomeGenBase desertHills = new BiomeGenDesert(17).setColor(13786898).setBiomeName("DesertHills").setDisableRain().setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(0.3F, 0.8F);
	public static final BiomeGenBase forestHills = new BiomeGenForest(18).setColor(2250012).setBiomeName("ForestHills").func_76733_a(5159473).setTemperatureRainfall(0.7F, 0.8F).setMinMaxHeight(0.3F, 0.7F);
	public static final BiomeGenBase taigaHills = new BiomeGenTaiga(19).setColor(1456435).setBiomeName("TaigaHills").setEnableSnow().func_76733_a(5159473).setTemperatureRainfall(0.05F, 0.8F).setMinMaxHeight(0.3F, 0.8F);
	public static final BiomeGenBase extremeHillsEdge = new BiomeGenHills(20).setColor(7501978).setBiomeName("Extreme Hills Edge").setMinMaxHeight(0.2F, 0.8F).setTemperatureRainfall(0.2F, 0.3F);
	public static final BiomeGenBase jungle = new BiomeGenJungle(21).setColor(5470985).setBiomeName("Jungle").func_76733_a(5470985).setTemperatureRainfall(1.2F, 0.9F).setMinMaxHeight(0.2F, 0.4F);
	public static final BiomeGenBase jungleHills = new BiomeGenJungle(22).setColor(2900485).setBiomeName("JungleHills").func_76733_a(5470985).setTemperatureRainfall(1.2F, 0.9F).setMinMaxHeight(1.8F, 0.5F);
	public String biomeName;
	public int color;
	public byte topBlock;
	public byte fillerBlock;
	public int field_76754_C;
	public float minHeight;
	public float maxHeight;
	public float temperature;
	public float rainfall;
	public int waterColorMultiplier;
	public BiomeDecorator theBiomeDecorator;
	protected List spawnableMonsterList;
	protected List spawnableCreatureList;
	protected List spawnableWaterCreatureList;
	protected List spawnableCaveCreatureList;
	private boolean enableSnow;
	private boolean enableRain;
	public final int biomeID;
	protected WorldGenTrees worldGeneratorTrees;
	protected WorldGenBigTree worldGeneratorBigTree;
	protected WorldGenForest worldGeneratorForest;
	protected WorldGenSwamp worldGeneratorSwamp;
	
	protected BiomeGenBase(int par1)
	{
		topBlock = (byte) Block.grass.blockID;
		fillerBlock = (byte) Block.dirt.blockID;
		field_76754_C = 5169201;
		minHeight = 0.1F;
		maxHeight = 0.3F;
		temperature = 0.5F;
		rainfall = 0.5F;
		waterColorMultiplier = 16777215;
		spawnableMonsterList = new ArrayList();
		spawnableCreatureList = new ArrayList();
		spawnableWaterCreatureList = new ArrayList();
		spawnableCaveCreatureList = new ArrayList();
		enableRain = true;
		worldGeneratorTrees = new WorldGenTrees(false);
		worldGeneratorBigTree = new WorldGenBigTree(false);
		worldGeneratorForest = new WorldGenForest(false);
		worldGeneratorSwamp = new WorldGenSwamp();
		biomeID = par1;
		biomeList[par1] = this;
		theBiomeDecorator = createBiomeDecorator();
		spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 12, 4, 4));
		spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 10, 4, 4));
		spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
		spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 8, 4, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 10, 4, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10, 4, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 10, 4, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 10, 4, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 1, 1, 4));
		spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10, 4, 4));
		spawnableCaveCreatureList.add(new SpawnListEntry(EntityBat.class, 10, 8, 8));
	}
	
	public boolean canSpawnLightningBolt()
	{
		return enableSnow ? false : enableRain;
	}
	
	protected BiomeDecorator createBiomeDecorator()
	{
		return new BiomeDecorator(this);
	}
	
	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		theBiomeDecorator.decorate(par1World, par2Random, par3, par4);
	}
	
	protected BiomeGenBase func_76733_a(int par1)
	{
		field_76754_C = par1;
		return this;
	}
	
	public int getBiomeFoliageColor()
	{
		double var1 = MathHelper.clamp_float(getFloatTemperature(), 0.0F, 1.0F);
		double var3 = MathHelper.clamp_float(getFloatRainfall(), 0.0F, 1.0F);
		return ColorizerFoliage.getFoliageColor(var1, var3);
	}
	
	public int getBiomeGrassColor()
	{
		double var1 = MathHelper.clamp_float(getFloatTemperature(), 0.0F, 1.0F);
		double var3 = MathHelper.clamp_float(getFloatRainfall(), 0.0F, 1.0F);
		return ColorizerGrass.getGrassColor(var1, var3);
	}
	
	public boolean getEnableSnow()
	{
		return enableSnow;
	}
	
	public final float getFloatRainfall()
	{
		return rainfall;
	}
	
	public final float getFloatTemperature()
	{
		return temperature;
	}
	
	public final int getIntRainfall()
	{
		return (int) (rainfall * 65536.0F);
	}
	
	public final int getIntTemperature()
	{
		return (int) (temperature * 65536.0F);
	}
	
	public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
	{
		return new WorldGenTallGrass(Block.tallGrass.blockID, 1);
	}
	
	public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		return par1Random.nextInt(10) == 0 ? worldGeneratorBigTree : worldGeneratorTrees;
	}
	
	public int getSkyColorByTemp(float par1)
	{
		par1 /= 3.0F;
		if(par1 < -1.0F)
		{
			par1 = -1.0F;
		}
		if(par1 > 1.0F)
		{
			par1 = 1.0F;
		}
		return Color.getHSBColor(0.62222224F - par1 * 0.05F, 0.5F + par1 * 0.1F, 1.0F).getRGB();
	}
	
	public List getSpawnableList(EnumCreatureType par1EnumCreatureType)
	{
		return par1EnumCreatureType == EnumCreatureType.monster ? spawnableMonsterList : par1EnumCreatureType == EnumCreatureType.creature ? spawnableCreatureList : par1EnumCreatureType == EnumCreatureType.waterCreature ? spawnableWaterCreatureList : par1EnumCreatureType == EnumCreatureType.ambient ? spawnableCaveCreatureList : null;
	}
	
	public float getSpawningChance()
	{
		return 0.1F;
	}
	
	public boolean isHighHumidity()
	{
		return rainfall > 0.85F;
	}
	
	protected BiomeGenBase setBiomeName(String par1Str)
	{
		biomeName = par1Str;
		return this;
	}
	
	protected BiomeGenBase setColor(int par1)
	{
		color = par1;
		return this;
	}
	
	private BiomeGenBase setDisableRain()
	{
		enableRain = false;
		return this;
	}
	
	protected BiomeGenBase setEnableSnow()
	{
		enableSnow = true;
		return this;
	}
	
	private BiomeGenBase setMinMaxHeight(float par1, float par2)
	{
		minHeight = par1;
		maxHeight = par2;
		return this;
	}
	
	private BiomeGenBase setTemperatureRainfall(float par1, float par2)
	{
		if(par1 > 0.1F && par1 < 0.2F) throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
		else
		{
			temperature = par1;
			rainfall = par2;
			return this;
		}
	}
}
