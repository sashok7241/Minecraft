package net.minecraft.src;

public class Material
{
	public static final Material air = new MaterialTransparent(MapColor.airColor);
	public static final Material grass = new Material(MapColor.grassColor);
	public static final Material ground = new Material(MapColor.dirtColor);
	public static final Material wood = new Material(MapColor.woodColor).setBurning();
	public static final Material rock = new Material(MapColor.stoneColor).setRequiresTool();
	public static final Material iron = new Material(MapColor.ironColor).setRequiresTool();
	public static final Material anvil = new Material(MapColor.ironColor).setRequiresTool().setImmovableMobility();
	public static final Material water = new MaterialLiquid(MapColor.waterColor).setNoPushMobility();
	public static final Material lava = new MaterialLiquid(MapColor.tntColor).setNoPushMobility();
	public static final Material leaves = new Material(MapColor.foliageColor).setBurning().setTranslucent().setNoPushMobility();
	public static final Material plants = new MaterialLogic(MapColor.foliageColor).setNoPushMobility();
	public static final Material vine = new MaterialLogic(MapColor.foliageColor).setBurning().setNoPushMobility().setReplaceable();
	public static final Material sponge = new Material(MapColor.clothColor);
	public static final Material cloth = new Material(MapColor.clothColor).setBurning();
	public static final Material fire = new MaterialTransparent(MapColor.airColor).setNoPushMobility();
	public static final Material sand = new Material(MapColor.sandColor);
	public static final Material circuits = new MaterialLogic(MapColor.airColor).setNoPushMobility();
	public static final Material glass = new Material(MapColor.airColor).setTranslucent().setAdventureModeExempt();
	public static final Material redstoneLight = new Material(MapColor.airColor).setAdventureModeExempt();
	public static final Material tnt = new Material(MapColor.tntColor).setBurning().setTranslucent();
	public static final Material coral = new Material(MapColor.foliageColor).setNoPushMobility();
	public static final Material ice = new Material(MapColor.iceColor).setTranslucent().setAdventureModeExempt();
	public static final Material snow = new MaterialLogic(MapColor.snowColor).setReplaceable().setTranslucent().setRequiresTool().setNoPushMobility();
	public static final Material craftedSnow = new Material(MapColor.snowColor).setRequiresTool();
	public static final Material cactus = new Material(MapColor.foliageColor).setTranslucent().setNoPushMobility();
	public static final Material clay = new Material(MapColor.clayColor);
	public static final Material pumpkin = new Material(MapColor.foliageColor).setNoPushMobility();
	public static final Material dragonEgg = new Material(MapColor.foliageColor).setNoPushMobility();
	public static final Material portal = new MaterialPortal(MapColor.airColor).setImmovableMobility();
	public static final Material cake = new Material(MapColor.airColor).setNoPushMobility();
	public static final Material web = new MaterialWeb(MapColor.clothColor).setRequiresTool().setNoPushMobility();
	public static final Material piston = new Material(MapColor.stoneColor).setImmovableMobility();
	private boolean canBurn;
	private boolean replaceable;
	private boolean isTranslucent;
	public final MapColor materialMapColor;
	private boolean requiresNoTool = true;
	private int mobilityFlag;
	private boolean isAdventureModeExempt;
	
	public Material(MapColor par1MapColor)
	{
		materialMapColor = par1MapColor;
	}
	
	public boolean blocksMovement()
	{
		return true;
	}
	
	public boolean getCanBlockGrass()
	{
		return true;
	}
	
	public boolean getCanBurn()
	{
		return canBurn;
	}
	
	public int getMaterialMobility()
	{
		return mobilityFlag;
	}
	
	public boolean isAdventureModeExempt()
	{
		return isAdventureModeExempt;
	}
	
	public boolean isLiquid()
	{
		return false;
	}
	
	public boolean isOpaque()
	{
		return isTranslucent ? false : blocksMovement();
	}
	
	public boolean isReplaceable()
	{
		return replaceable;
	}
	
	public boolean isSolid()
	{
		return true;
	}
	
	public boolean isToolNotRequired()
	{
		return requiresNoTool;
	}
	
	protected Material setAdventureModeExempt()
	{
		isAdventureModeExempt = true;
		return this;
	}
	
	protected Material setBurning()
	{
		canBurn = true;
		return this;
	}
	
	protected Material setImmovableMobility()
	{
		mobilityFlag = 2;
		return this;
	}
	
	protected Material setNoPushMobility()
	{
		mobilityFlag = 1;
		return this;
	}
	
	public Material setReplaceable()
	{
		replaceable = true;
		return this;
	}
	
	protected Material setRequiresTool()
	{
		requiresNoTool = false;
		return this;
	}
	
	private Material setTranslucent()
	{
		isTranslucent = true;
		return this;
	}
}
