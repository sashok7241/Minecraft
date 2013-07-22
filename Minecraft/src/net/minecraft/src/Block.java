package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class Block
{
	private CreativeTabs displayOnCreativeTab;
	public static final StepSound soundPowderFootstep = new StepSound("stone", 1.0F, 1.0F);
	public static final StepSound soundWoodFootstep = new StepSound("wood", 1.0F, 1.0F);
	public static final StepSound soundGravelFootstep = new StepSound("gravel", 1.0F, 1.0F);
	public static final StepSound soundGrassFootstep = new StepSound("grass", 1.0F, 1.0F);
	public static final StepSound soundStoneFootstep = new StepSound("stone", 1.0F, 1.0F);
	public static final StepSound soundMetalFootstep = new StepSound("stone", 1.0F, 1.5F);
	public static final StepSound soundGlassFootstep = new StepSoundStone("stone", 1.0F, 1.0F);
	public static final StepSound soundClothFootstep = new StepSound("cloth", 1.0F, 1.0F);
	public static final StepSound soundSandFootstep = new StepSound("sand", 1.0F, 1.0F);
	public static final StepSound soundSnowFootstep = new StepSound("snow", 1.0F, 1.0F);
	public static final StepSound soundLadderFootstep = new StepSoundSand("ladder", 1.0F, 1.0F);
	public static final StepSound soundAnvilFootstep = new StepSoundAnvil("anvil", 0.3F, 1.0F);
	public static final Block[] blocksList = new Block[4096];
	public static final boolean[] opaqueCubeLookup = new boolean[4096];
	public static final int[] lightOpacity = new int[4096];
	public static final boolean[] canBlockGrass = new boolean[4096];
	public static final int[] lightValue = new int[4096];
	public static boolean[] useNeighborBrightness = new boolean[4096];
	public static final Block stone = new BlockStone(1).setHardness(1.5F).setResistance(10.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("stone");
	public static final BlockGrass grass = (BlockGrass) new BlockGrass(2).setHardness(0.6F).setStepSound(soundGrassFootstep).setUnlocalizedName("grass");
	public static final Block dirt = new BlockDirt(3).setHardness(0.5F).setStepSound(soundGravelFootstep).setUnlocalizedName("dirt");
	public static final Block cobblestone = new Block(4, Material.rock).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("stonebrick").setCreativeTab(CreativeTabs.tabBlock);
	public static final Block planks = new BlockWood(5).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("wood");
	public static final Block sapling = new BlockSapling(6).setHardness(0.0F).setStepSound(soundGrassFootstep).setUnlocalizedName("sapling");
	public static final Block bedrock = new Block(7, Material.rock).setBlockUnbreakable().setResistance(6000000.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("bedrock").disableStats().setCreativeTab(CreativeTabs.tabBlock);
	public static final BlockFluid waterMoving = (BlockFluid) new BlockFlowing(8, Material.water).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats();
	public static final Block waterStill = new BlockStationary(9, Material.water).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats();
	public static final BlockFluid lavaMoving = (BlockFluid) new BlockFlowing(10, Material.lava).setHardness(0.0F).setLightValue(1.0F).setUnlocalizedName("lava").disableStats();
	public static final Block lavaStill = new BlockStationary(11, Material.lava).setHardness(100.0F).setLightValue(1.0F).setUnlocalizedName("lava").disableStats();
	public static final Block sand = new BlockSand(12).setHardness(0.5F).setStepSound(soundSandFootstep).setUnlocalizedName("sand");
	public static final Block gravel = new BlockGravel(13).setHardness(0.6F).setStepSound(soundGravelFootstep).setUnlocalizedName("gravel");
	public static final Block oreGold = new BlockOre(14).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreGold");
	public static final Block oreIron = new BlockOre(15).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreIron");
	public static final Block oreCoal = new BlockOre(16).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreCoal");
	public static final Block wood = new BlockLog(17).setHardness(2.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("log");
	public static final BlockLeaves leaves = (BlockLeaves) new BlockLeaves(18).setHardness(0.2F).setLightOpacity(1).setStepSound(soundGrassFootstep).setUnlocalizedName("leaves");
	public static final Block sponge = new BlockSponge(19).setHardness(0.6F).setStepSound(soundGrassFootstep).setUnlocalizedName("sponge");
	public static final Block glass = new BlockGlass(20, Material.glass, false).setHardness(0.3F).setStepSound(soundGlassFootstep).setUnlocalizedName("glass");
	public static final Block oreLapis = new BlockOre(21).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreLapis");
	public static final Block blockLapis = new Block(22, Material.rock).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("blockLapis").setCreativeTab(CreativeTabs.tabBlock);
	public static final Block dispenser = new BlockDispenser(23).setHardness(3.5F).setStepSound(soundStoneFootstep).setUnlocalizedName("dispenser");
	public static final Block sandStone = new BlockSandStone(24).setStepSound(soundStoneFootstep).setHardness(0.8F).setUnlocalizedName("sandStone");
	public static final Block music = new BlockNote(25).setHardness(0.8F).setUnlocalizedName("musicBlock");
	public static final Block bed = new BlockBed(26).setHardness(0.2F).setUnlocalizedName("bed").disableStats();
	public static final Block railPowered = new BlockRailPowered(27).setHardness(0.7F).setStepSound(soundMetalFootstep).setUnlocalizedName("goldenRail");
	public static final Block railDetector = new BlockDetectorRail(28).setHardness(0.7F).setStepSound(soundMetalFootstep).setUnlocalizedName("detectorRail");
	public static final BlockPistonBase pistonStickyBase = (BlockPistonBase) new BlockPistonBase(29, true).setUnlocalizedName("pistonStickyBase");
	public static final Block web = new BlockWeb(30).setLightOpacity(1).setHardness(4.0F).setUnlocalizedName("web");
	public static final BlockTallGrass tallGrass = (BlockTallGrass) new BlockTallGrass(31).setHardness(0.0F).setStepSound(soundGrassFootstep).setUnlocalizedName("tallgrass");
	public static final BlockDeadBush deadBush = (BlockDeadBush) new BlockDeadBush(32).setHardness(0.0F).setStepSound(soundGrassFootstep).setUnlocalizedName("deadbush");
	public static final BlockPistonBase pistonBase = (BlockPistonBase) new BlockPistonBase(33, false).setUnlocalizedName("pistonBase");
	public static final BlockPistonExtension pistonExtension = new BlockPistonExtension(34);
	public static final Block cloth = new BlockCloth().setHardness(0.8F).setStepSound(soundClothFootstep).setUnlocalizedName("cloth");
	public static final BlockPistonMoving pistonMoving = new BlockPistonMoving(36);
	public static final BlockFlower plantYellow = (BlockFlower) new BlockFlower(37).setHardness(0.0F).setStepSound(soundGrassFootstep).setUnlocalizedName("flower");
	public static final BlockFlower plantRed = (BlockFlower) new BlockFlower(38).setHardness(0.0F).setStepSound(soundGrassFootstep).setUnlocalizedName("rose");
	public static final BlockFlower mushroomBrown = (BlockFlower) new BlockMushroom(39, "mushroom_brown").setHardness(0.0F).setStepSound(soundGrassFootstep).setLightValue(0.125F).setUnlocalizedName("mushroom");
	public static final BlockFlower mushroomRed = (BlockFlower) new BlockMushroom(40, "mushroom_red").setHardness(0.0F).setStepSound(soundGrassFootstep).setUnlocalizedName("mushroom");
	public static final Block blockGold = new BlockOreStorage(41).setHardness(3.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setUnlocalizedName("blockGold");
	public static final Block blockIron = new BlockOreStorage(42).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setUnlocalizedName("blockIron");
	public static final BlockHalfSlab stoneDoubleSlab = (BlockHalfSlab) new BlockStep(43, true).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("stoneSlab");
	public static final BlockHalfSlab stoneSingleSlab = (BlockHalfSlab) new BlockStep(44, false).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("stoneSlab");
	public static final Block brick = new Block(45, Material.rock).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabBlock);
	public static final Block tnt = new BlockTNT(46).setHardness(0.0F).setStepSound(soundGrassFootstep).setUnlocalizedName("tnt");
	public static final Block bookShelf = new BlockBookshelf(47).setHardness(1.5F).setStepSound(soundWoodFootstep).setUnlocalizedName("bookshelf");
	public static final Block cobblestoneMossy = new Block(48, Material.rock).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("stoneMoss").setCreativeTab(CreativeTabs.tabBlock);
	public static final Block obsidian = new BlockObsidian(49).setHardness(50.0F).setResistance(2000.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("obsidian");
	public static final Block torchWood = new BlockTorch(50).setHardness(0.0F).setLightValue(0.9375F).setStepSound(soundWoodFootstep).setUnlocalizedName("torch");
	public static final BlockFire fire = (BlockFire) new BlockFire(51).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("fire").disableStats();
	public static final Block mobSpawner = new BlockMobSpawner(52).setHardness(5.0F).setStepSound(soundMetalFootstep).setUnlocalizedName("mobSpawner").disableStats();
	public static final Block stairsWoodOak = new BlockStairs(53, planks, 0).setUnlocalizedName("stairsWood");
	public static final BlockChest chest = (BlockChest) new BlockChest(54, 0).setHardness(2.5F).setStepSound(soundWoodFootstep).setUnlocalizedName("chest");
	public static final BlockRedstoneWire redstoneWire = (BlockRedstoneWire) new BlockRedstoneWire(55).setHardness(0.0F).setStepSound(soundPowderFootstep).setUnlocalizedName("redstoneDust").disableStats();
	public static final Block oreDiamond = new BlockOre(56).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreDiamond");
	public static final Block blockDiamond = new BlockOreStorage(57).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setUnlocalizedName("blockDiamond");
	public static final Block workbench = new BlockWorkbench(58).setHardness(2.5F).setStepSound(soundWoodFootstep).setUnlocalizedName("workbench");
	public static final Block crops = new BlockCrops(59).setUnlocalizedName("crops");
	public static final Block tilledField = new BlockFarmland(60).setHardness(0.6F).setStepSound(soundGravelFootstep).setUnlocalizedName("farmland");
	public static final Block furnaceIdle = new BlockFurnace(61, false).setHardness(3.5F).setStepSound(soundStoneFootstep).setUnlocalizedName("furnace").setCreativeTab(CreativeTabs.tabDecorations);
	public static final Block furnaceBurning = new BlockFurnace(62, true).setHardness(3.5F).setStepSound(soundStoneFootstep).setLightValue(0.875F).setUnlocalizedName("furnace");
	public static final Block signPost = new BlockSign(63, TileEntitySign.class, true).setHardness(1.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("sign").disableStats();
	public static final Block doorWood = new BlockDoor(64, Material.wood).setHardness(3.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("doorWood").disableStats();
	public static final Block ladder = new BlockLadder(65).setHardness(0.4F).setStepSound(soundLadderFootstep).setUnlocalizedName("ladder");
	public static final Block rail = new BlockRail(66).setHardness(0.7F).setStepSound(soundMetalFootstep).setUnlocalizedName("rail");
	public static final Block stairsCobblestone = new BlockStairs(67, cobblestone, 0).setUnlocalizedName("stairsStone");
	public static final Block signWall = new BlockSign(68, TileEntitySign.class, false).setHardness(1.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("sign").disableStats();
	public static final Block lever = new BlockLever(69).setHardness(0.5F).setStepSound(soundWoodFootstep).setUnlocalizedName("lever");
	public static final Block pressurePlateStone = new BlockPressurePlate(70, "stone", Material.rock, EnumMobType.mobs).setHardness(0.5F).setStepSound(soundStoneFootstep).setUnlocalizedName("pressurePlate");
	public static final Block doorIron = new BlockDoor(71, Material.iron).setHardness(5.0F).setStepSound(soundMetalFootstep).setUnlocalizedName("doorIron").disableStats();
	public static final Block pressurePlatePlanks = new BlockPressurePlate(72, "wood", Material.wood, EnumMobType.everything).setHardness(0.5F).setStepSound(soundWoodFootstep).setUnlocalizedName("pressurePlate");
	public static final Block oreRedstone = new BlockRedstoneOre(73, false).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreRedstone").setCreativeTab(CreativeTabs.tabBlock);
	public static final Block oreRedstoneGlowing = new BlockRedstoneOre(74, true).setLightValue(0.625F).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreRedstone");
	public static final Block torchRedstoneIdle = new BlockRedstoneTorch(75, false).setHardness(0.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("notGate");
	public static final Block torchRedstoneActive = new BlockRedstoneTorch(76, true).setHardness(0.0F).setLightValue(0.5F).setStepSound(soundWoodFootstep).setUnlocalizedName("notGate").setCreativeTab(CreativeTabs.tabRedstone);
	public static final Block stoneButton = new BlockButtonStone(77).setHardness(0.5F).setStepSound(soundStoneFootstep).setUnlocalizedName("button");
	public static final Block snow = new BlockSnow(78).setHardness(0.1F).setStepSound(soundSnowFootstep).setUnlocalizedName("snow").setLightOpacity(0);
	public static final Block ice = new BlockIce(79).setHardness(0.5F).setLightOpacity(3).setStepSound(soundGlassFootstep).setUnlocalizedName("ice");
	public static final Block blockSnow = new BlockSnowBlock(80).setHardness(0.2F).setStepSound(soundSnowFootstep).setUnlocalizedName("snow");
	public static final Block cactus = new BlockCactus(81).setHardness(0.4F).setStepSound(soundClothFootstep).setUnlocalizedName("cactus");
	public static final Block blockClay = new BlockClay(82).setHardness(0.6F).setStepSound(soundGravelFootstep).setUnlocalizedName("clay");
	public static final Block reed = new BlockReed(83).setHardness(0.0F).setStepSound(soundGrassFootstep).setUnlocalizedName("reeds").disableStats();
	public static final Block jukebox = new BlockJukeBox(84).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("jukebox");
	public static final Block fence = new BlockFence(85, "wood", Material.wood).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("fence");
	public static final Block pumpkin = new BlockPumpkin(86, false).setHardness(1.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("pumpkin");
	public static final Block netherrack = new BlockNetherrack(87).setHardness(0.4F).setStepSound(soundStoneFootstep).setUnlocalizedName("hellrock");
	public static final Block slowSand = new BlockSoulSand(88).setHardness(0.5F).setStepSound(soundSandFootstep).setUnlocalizedName("hellsand");
	public static final Block glowStone = new BlockGlowStone(89, Material.glass).setHardness(0.3F).setStepSound(soundGlassFootstep).setLightValue(1.0F).setUnlocalizedName("lightgem");
	public static final BlockPortal portal = (BlockPortal) new BlockPortal(90).setHardness(-1.0F).setStepSound(soundGlassFootstep).setLightValue(0.75F).setUnlocalizedName("portal");
	public static final Block pumpkinLantern = new BlockPumpkin(91, true).setHardness(1.0F).setStepSound(soundWoodFootstep).setLightValue(1.0F).setUnlocalizedName("litpumpkin");
	public static final Block cake = new BlockCake(92).setHardness(0.5F).setStepSound(soundClothFootstep).setUnlocalizedName("cake").disableStats();
	public static final BlockRedstoneRepeater redstoneRepeaterIdle = (BlockRedstoneRepeater) new BlockRedstoneRepeater(93, false).setHardness(0.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("diode").disableStats();
	public static final BlockRedstoneRepeater redstoneRepeaterActive = (BlockRedstoneRepeater) new BlockRedstoneRepeater(94, true).setHardness(0.0F).setLightValue(0.625F).setStepSound(soundWoodFootstep).setUnlocalizedName("diode").disableStats();
	public static final Block lockedChest = new BlockLockedChest(95).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("lockedchest").setTickRandomly(true);
	public static final Block trapdoor = new BlockTrapDoor(96, Material.wood).setHardness(3.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("trapdoor").disableStats();
	public static final Block silverfish = new BlockSilverfish(97).setHardness(0.75F).setUnlocalizedName("monsterStoneEgg");
	public static final Block stoneBrick = new BlockStoneBrick(98).setHardness(1.5F).setResistance(10.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("stonebricksmooth");
	public static final Block mushroomCapBrown = new BlockMushroomCap(99, Material.wood, 0).setHardness(0.2F).setStepSound(soundWoodFootstep).setUnlocalizedName("mushroom");
	public static final Block mushroomCapRed = new BlockMushroomCap(100, Material.wood, 1).setHardness(0.2F).setStepSound(soundWoodFootstep).setUnlocalizedName("mushroom");
	public static final Block fenceIron = new BlockPane(101, "fenceIron", "fenceIron", Material.iron, true).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setUnlocalizedName("fenceIron");
	public static final Block thinGlass = new BlockPane(102, "glass", "thinglass_top", Material.glass, false).setHardness(0.3F).setStepSound(soundGlassFootstep).setUnlocalizedName("thinGlass");
	public static final Block melon = new BlockMelon(103).setHardness(1.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("melon");
	public static final Block pumpkinStem = new BlockStem(104, pumpkin).setHardness(0.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("pumpkinStem");
	public static final Block melonStem = new BlockStem(105, melon).setHardness(0.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("pumpkinStem");
	public static final Block vine = new BlockVine(106).setHardness(0.2F).setStepSound(soundGrassFootstep).setUnlocalizedName("vine");
	public static final Block fenceGate = new BlockFenceGate(107).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("fenceGate");
	public static final Block stairsBrick = new BlockStairs(108, brick, 0).setUnlocalizedName("stairsBrick");
	public static final Block stairsStoneBrick = new BlockStairs(109, stoneBrick, 0).setUnlocalizedName("stairsStoneBrickSmooth");
	public static final BlockMycelium mycelium = (BlockMycelium) new BlockMycelium(110).setHardness(0.6F).setStepSound(soundGrassFootstep).setUnlocalizedName("mycel");
	public static final Block waterlily = new BlockLilyPad(111).setHardness(0.0F).setStepSound(soundGrassFootstep).setUnlocalizedName("waterlily");
	public static final Block netherBrick = new Block(112, Material.rock).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("netherBrick").setCreativeTab(CreativeTabs.tabBlock);
	public static final Block netherFence = new BlockFence(113, "netherBrick", Material.rock).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("netherFence");
	public static final Block stairsNetherBrick = new BlockStairs(114, netherBrick, 0).setUnlocalizedName("stairsNetherBrick");
	public static final Block netherStalk = new BlockNetherStalk(115).setUnlocalizedName("netherStalk");
	public static final Block enchantmentTable = new BlockEnchantmentTable(116).setHardness(5.0F).setResistance(2000.0F).setUnlocalizedName("enchantmentTable");
	public static final Block brewingStand = new BlockBrewingStand(117).setHardness(0.5F).setLightValue(0.125F).setUnlocalizedName("brewingStand");
	public static final BlockCauldron cauldron = (BlockCauldron) new BlockCauldron(118).setHardness(2.0F).setUnlocalizedName("cauldron");
	public static final Block endPortal = new BlockEndPortal(119, Material.portal).setHardness(-1.0F).setResistance(6000000.0F);
	public static final Block endPortalFrame = new BlockEndPortalFrame(120).setStepSound(soundGlassFootstep).setLightValue(0.125F).setHardness(-1.0F).setUnlocalizedName("endPortalFrame").setResistance(6000000.0F).setCreativeTab(CreativeTabs.tabDecorations);
	public static final Block whiteStone = new Block(121, Material.rock).setHardness(3.0F).setResistance(15.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("whiteStone").setCreativeTab(CreativeTabs.tabBlock);
	public static final Block dragonEgg = new BlockDragonEgg(122).setHardness(3.0F).setResistance(15.0F).setStepSound(soundStoneFootstep).setLightValue(0.125F).setUnlocalizedName("dragonEgg");
	public static final Block redstoneLampIdle = new BlockRedstoneLight(123, false).setHardness(0.3F).setStepSound(soundGlassFootstep).setUnlocalizedName("redstoneLight").setCreativeTab(CreativeTabs.tabRedstone);
	public static final Block redstoneLampActive = new BlockRedstoneLight(124, true).setHardness(0.3F).setStepSound(soundGlassFootstep).setUnlocalizedName("redstoneLight");
	public static final BlockHalfSlab woodDoubleSlab = (BlockHalfSlab) new BlockWoodSlab(125, true).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("woodSlab");
	public static final BlockHalfSlab woodSingleSlab = (BlockHalfSlab) new BlockWoodSlab(126, false).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("woodSlab");
	public static final Block cocoaPlant = new BlockCocoa(127).setHardness(0.2F).setResistance(5.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("cocoa");
	public static final Block stairsSandStone = new BlockStairs(128, sandStone, 0).setUnlocalizedName("stairsSandStone");
	public static final Block oreEmerald = new BlockOre(129).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreEmerald");
	public static final Block enderChest = new BlockEnderChest(130).setHardness(22.5F).setResistance(1000.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("enderChest").setLightValue(0.5F);
	public static final BlockTripWireSource tripWireSource = (BlockTripWireSource) new BlockTripWireSource(131).setUnlocalizedName("tripWireSource");
	public static final Block tripWire = new BlockTripWire(132).setUnlocalizedName("tripWire");
	public static final Block blockEmerald = new BlockOreStorage(133).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setUnlocalizedName("blockEmerald");
	public static final Block stairsWoodSpruce = new BlockStairs(134, planks, 1).setUnlocalizedName("stairsWoodSpruce");
	public static final Block stairsWoodBirch = new BlockStairs(135, planks, 2).setUnlocalizedName("stairsWoodBirch");
	public static final Block stairsWoodJungle = new BlockStairs(136, planks, 3).setUnlocalizedName("stairsWoodJungle");
	public static final Block commandBlock = new BlockCommandBlock(137).setUnlocalizedName("commandBlock");
	public static final BlockBeacon beacon = (BlockBeacon) new BlockBeacon(138).setUnlocalizedName("beacon").setLightValue(1.0F);
	public static final Block cobblestoneWall = new BlockWall(139, cobblestone).setUnlocalizedName("cobbleWall");
	public static final Block flowerPot = new BlockFlowerPot(140).setHardness(0.0F).setStepSound(soundPowderFootstep).setUnlocalizedName("flowerPot");
	public static final Block carrot = new BlockCarrot(141).setUnlocalizedName("carrots");
	public static final Block potato = new BlockPotato(142).setUnlocalizedName("potatoes");
	public static final Block woodenButton = new BlockButtonWood(143).setHardness(0.5F).setStepSound(soundWoodFootstep).setUnlocalizedName("button");
	public static final Block skull = new BlockSkull(144).setHardness(1.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("skull");
	public static final Block anvil = new BlockAnvil(145).setHardness(5.0F).setStepSound(soundAnvilFootstep).setResistance(2000.0F).setUnlocalizedName("anvil");
	public static final Block chestTrapped = new BlockChest(146, 1).setHardness(2.5F).setStepSound(soundWoodFootstep).setUnlocalizedName("chestTrap");
	public static final Block pressurePlateGold = new BlockPressurePlateWeighted(147, "blockGold", Material.iron, 64).setHardness(0.5F).setStepSound(soundWoodFootstep).setUnlocalizedName("weightedPlate_light");
	public static final Block pressurePlateIron = new BlockPressurePlateWeighted(148, "blockIron", Material.iron, 640).setHardness(0.5F).setStepSound(soundWoodFootstep).setUnlocalizedName("weightedPlate_heavy");
	public static final BlockComparator redstoneComparatorIdle = (BlockComparator) new BlockComparator(149, false).setHardness(0.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("comparator").disableStats();
	public static final BlockComparator redstoneComparatorActive = (BlockComparator) new BlockComparator(150, true).setHardness(0.0F).setLightValue(0.625F).setStepSound(soundWoodFootstep).setUnlocalizedName("comparator").disableStats();
	public static final BlockDaylightDetector daylightSensor = (BlockDaylightDetector) new BlockDaylightDetector(151).setHardness(0.2F).setStepSound(soundWoodFootstep).setUnlocalizedName("daylightDetector");
	public static final Block blockRedstone = new BlockPoweredOre(152).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setUnlocalizedName("blockRedstone");
	public static final Block oreNetherQuartz = new BlockOre(153).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("netherquartz");
	public static final BlockHopper hopperBlock = (BlockHopper) new BlockHopper(154).setHardness(3.0F).setResistance(8.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("hopper");
	public static final Block blockNetherQuartz = new BlockQuartz(155).setStepSound(soundStoneFootstep).setHardness(0.8F).setUnlocalizedName("quartzBlock");
	public static final Block stairsNetherQuartz = new BlockStairs(156, blockNetherQuartz, 0).setUnlocalizedName("stairsQuartz");
	public static final Block railActivator = new BlockRailPowered(157).setHardness(0.7F).setStepSound(soundMetalFootstep).setUnlocalizedName("activatorRail");
	public static final Block dropper = new BlockDropper(158).setHardness(3.5F).setStepSound(soundStoneFootstep).setUnlocalizedName("dropper");
	public final int blockID;
	protected float blockHardness;
	protected float blockResistance;
	protected boolean blockConstructorCalled = true;
	protected boolean enableStats = true;
	protected boolean needsRandomTick;
	protected boolean isBlockContainer;
	protected double minX;
	protected double minY;
	protected double minZ;
	protected double maxX;
	protected double maxY;
	protected double maxZ;
	public StepSound stepSound;
	public float blockParticleGravity;
	public final Material blockMaterial;
	public float slipperiness;
	private String unlocalizedName;
	protected Icon blockIcon;
	
	protected Block(int par1, Material par2Material)
	{
		stepSound = soundPowderFootstep;
		blockParticleGravity = 1.0F;
		slipperiness = 0.6F;
		if(blocksList[par1] != null) throw new IllegalArgumentException("Slot " + par1 + " is already occupied by " + blocksList[par1] + " when adding " + this);
		else
		{
			blockMaterial = par2Material;
			blocksList[par1] = this;
			blockID = par1;
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			opaqueCubeLookup[par1] = isOpaqueCube();
			lightOpacity[par1] = isOpaqueCube() ? 255 : 0;
			canBlockGrass[par1] = !par2Material.getCanBlockGrass();
		}
	}
	
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
		AxisAlignedBB var8 = getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
		if(var8 != null && par5AxisAlignedBB.intersectsWith(var8))
		{
			par6List.add(var8);
		}
	}
	
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
	}
	
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return true;
	}
	
	public boolean canCollideCheck(int par1, boolean par2)
	{
		return isCollidable();
	}
	
	public boolean canDropFromExplosion(Explosion par1Explosion)
	{
		return true;
	}
	
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockId(par2, par3, par4);
		return var5 == 0 || blocksList[var5].blockMaterial.isReplaceable();
	}
	
	public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
	{
		return canPlaceBlockAt(par1World, par2, par3, par4);
	}
	
	public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5, ItemStack par6ItemStack)
	{
		return this.canPlaceBlockOnSide(par1World, par2, par3, par4, par5);
	}
	
	public boolean canProvidePower()
	{
		return false;
	}
	
	protected boolean canSilkHarvest()
	{
		return renderAsNormalBlock() && !isBlockContainer;
	}
	
	public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
	{
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		par5Vec3 = par5Vec3.addVector(-par2, -par3, -par4);
		par6Vec3 = par6Vec3.addVector(-par2, -par3, -par4);
		Vec3 var7 = par5Vec3.getIntermediateWithXValue(par6Vec3, minX);
		Vec3 var8 = par5Vec3.getIntermediateWithXValue(par6Vec3, maxX);
		Vec3 var9 = par5Vec3.getIntermediateWithYValue(par6Vec3, minY);
		Vec3 var10 = par5Vec3.getIntermediateWithYValue(par6Vec3, maxY);
		Vec3 var11 = par5Vec3.getIntermediateWithZValue(par6Vec3, minZ);
		Vec3 var12 = par5Vec3.getIntermediateWithZValue(par6Vec3, maxZ);
		if(!isVecInsideYZBounds(var7))
		{
			var7 = null;
		}
		if(!isVecInsideYZBounds(var8))
		{
			var8 = null;
		}
		if(!isVecInsideXZBounds(var9))
		{
			var9 = null;
		}
		if(!isVecInsideXZBounds(var10))
		{
			var10 = null;
		}
		if(!isVecInsideXYBounds(var11))
		{
			var11 = null;
		}
		if(!isVecInsideXYBounds(var12))
		{
			var12 = null;
		}
		Vec3 var13 = null;
		if(var7 != null && (var13 == null || par5Vec3.squareDistanceTo(var7) < par5Vec3.squareDistanceTo(var13)))
		{
			var13 = var7;
		}
		if(var8 != null && (var13 == null || par5Vec3.squareDistanceTo(var8) < par5Vec3.squareDistanceTo(var13)))
		{
			var13 = var8;
		}
		if(var9 != null && (var13 == null || par5Vec3.squareDistanceTo(var9) < par5Vec3.squareDistanceTo(var13)))
		{
			var13 = var9;
		}
		if(var10 != null && (var13 == null || par5Vec3.squareDistanceTo(var10) < par5Vec3.squareDistanceTo(var13)))
		{
			var13 = var10;
		}
		if(var11 != null && (var13 == null || par5Vec3.squareDistanceTo(var11) < par5Vec3.squareDistanceTo(var13)))
		{
			var13 = var11;
		}
		if(var12 != null && (var13 == null || par5Vec3.squareDistanceTo(var12) < par5Vec3.squareDistanceTo(var13)))
		{
			var13 = var12;
		}
		if(var13 == null) return null;
		else
		{
			byte var14 = -1;
			if(var13 == var7)
			{
				var14 = 4;
			}
			if(var13 == var8)
			{
				var14 = 5;
			}
			if(var13 == var9)
			{
				var14 = 0;
			}
			if(var13 == var10)
			{
				var14 = 1;
			}
			if(var13 == var11)
			{
				var14 = 2;
			}
			if(var13 == var12)
			{
				var14 = 3;
			}
			return new MovingObjectPosition(par2, par3, par4, var14, var13.addVector(par2, par3, par4));
		}
	}
	
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 16777215;
	}
	
	protected ItemStack createStackedBlock(int par1)
	{
		int var2 = 0;
		if(blockID >= 0 && blockID < Item.itemsList.length && Item.itemsList[blockID].getHasSubtypes())
		{
			var2 = par1;
		}
		return new ItemStack(blockID, 1, var2);
	}
	
	public int damageDropped(int par1)
	{
		return 0;
	}
	
	protected Block disableStats()
	{
		enableStats = false;
		return this;
	}
	
	public final void dropBlockAsItem(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, 1.0F, par6);
	}
	
	protected void dropBlockAsItem_do(World par1World, int par2, int par3, int par4, ItemStack par5ItemStack)
	{
		if(!par1World.isRemote && par1World.getGameRules().getGameRuleBooleanValue("doTileDrops"))
		{
			float var6 = 0.7F;
			double var7 = par1World.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
			double var9 = par1World.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
			double var11 = par1World.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
			EntityItem var13 = new EntityItem(par1World, par2 + var7, par3 + var9, par4 + var11, par5ItemStack);
			var13.delayBeforeCanPickup = 10;
			par1World.spawnEntityInWorld(var13);
		}
	}
	
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		if(!par1World.isRemote)
		{
			int var8 = quantityDroppedWithBonus(par7, par1World.rand);
			for(int var9 = 0; var9 < var8; ++var9)
			{
				if(par1World.rand.nextFloat() <= par6)
				{
					int var10 = idDropped(par5, par1World.rand, par7);
					if(var10 > 0)
					{
						dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(var10, 1, damageDropped(par5)));
					}
				}
			}
		}
	}
	
	protected void dropXpOnBlockBreak(World par1World, int par2, int par3, int par4, int par5)
	{
		if(!par1World.isRemote)
		{
			while(par5 > 0)
			{
				int var6 = EntityXPOrb.getXPSplit(par5);
				par5 -= var6;
				par1World.spawnEntityInWorld(new EntityXPOrb(par1World, par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, var6));
			}
		}
	}
	
	public void fillWithRain(World par1World, int par2, int par3, int par4)
	{
	}
	
	public boolean func_82506_l()
	{
		return true;
	}
	
	public float getAmbientOcclusionLightValue(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return par1IBlockAccess.isBlockNormalCube(par2, par3, par4) ? 0.2F : 1.0F;
	}
	
	public final double getBlockBoundsMaxX()
	{
		return maxX;
	}
	
	public final double getBlockBoundsMaxY()
	{
		return maxY;
	}
	
	public final double getBlockBoundsMaxZ()
	{
		return maxZ;
	}
	
	public final double getBlockBoundsMinX()
	{
		return minX;
	}
	
	public final double getBlockBoundsMinY()
	{
		return minY;
	}
	
	public final double getBlockBoundsMinZ()
	{
		return minZ;
	}
	
	public float getBlockBrightness(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return par1IBlockAccess.getBrightness(par2, par3, par4, lightValue[par1IBlockAccess.getBlockId(par2, par3, par4)]);
	}
	
	public int getBlockColor()
	{
		return 16777215;
	}
	
	public float getBlockHardness(World par1World, int par2, int par3, int par4)
	{
		return blockHardness;
	}
	
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return !blockMaterial.blocksMovement();
	}
	
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return getIcon(par5, par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}
	
	public final Icon getBlockTextureFromSide(int par1)
	{
		return getIcon(par1, 0);
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getAABBPool().getAABB(par2 + minX, par3 + minY, par4 + minZ, par2 + maxX, par3 + maxY, par4 + maxZ);
	}
	
	public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		return 0;
	}
	
	public CreativeTabs getCreativeTabToDisplayOn()
	{
		return displayOnCreativeTab;
	}
	
	public int getDamageValue(World par1World, int par2, int par3, int par4)
	{
		return damageDropped(par1World.getBlockMetadata(par2, par3, par4));
	}
	
	public boolean getEnableStats()
	{
		return enableStats;
	}
	
	public float getExplosionResistance(Entity par1Entity)
	{
		return blockResistance / 5.0F;
	}
	
	public Icon getIcon(int par1, int par2)
	{
		return blockIcon;
	}
	
	public String getItemIconName()
	{
		return null;
	}
	
	public String getLocalizedName()
	{
		return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
	}
	
	public int getMixedBrightnessForBlock(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return par1IBlockAccess.getLightBrightnessForSkyBlocks(par2, par3, par4, lightValue[par1IBlockAccess.getBlockId(par2, par3, par4)]);
	}
	
	public int getMobilityFlag()
	{
		return blockMaterial.getMaterialMobility();
	}
	
	public float getPlayerRelativeBlockHardness(EntityPlayer par1EntityPlayer, World par2World, int par3, int par4, int par5)
	{
		float var6 = getBlockHardness(par2World, par3, par4, par5);
		return var6 < 0.0F ? 0.0F : !par1EntityPlayer.canHarvestBlock(this) ? par1EntityPlayer.getCurrentPlayerStrVsBlock(this, false) / var6 / 100.0F : par1EntityPlayer.getCurrentPlayerStrVsBlock(this, true) / var6 / 30.0F;
	}
	
	public int getRenderBlockPass()
	{
		return 0;
	}
	
	public int getRenderColor(int par1)
	{
		return 16777215;
	}
	
	public int getRenderType()
	{
		return 0;
	}
	
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getAABBPool().getAABB(par2 + minX, par3 + minY, par4 + minZ, par2 + maxX, par3 + maxY, par4 + maxZ);
	}
	
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
	}
	
	public boolean getTickRandomly()
	{
		return needsRandomTick;
	}
	
	public String getUnlocalizedName()
	{
		return "tile." + unlocalizedName;
	}
	
	public String getUnlocalizedName2()
	{
		return unlocalizedName;
	}
	
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
	{
		par2EntityPlayer.addStat(StatList.mineBlockStatArray[blockID], 1);
		par2EntityPlayer.addExhaustion(0.025F);
		if(canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(par2EntityPlayer))
		{
			ItemStack var8 = createStackedBlock(par6);
			if(var8 != null)
			{
				dropBlockAsItem_do(par1World, par3, par4, par5, var8);
			}
		} else
		{
			int var7 = EnchantmentHelper.getFortuneModifier(par2EntityPlayer);
			dropBlockAsItem(par1World, par3, par4, par5, par6, var7);
		}
	}
	
	public boolean hasComparatorInputOverride()
	{
		return false;
	}
	
	public boolean hasTileEntity()
	{
		return isBlockContainer;
	}
	
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return blockID;
	}
	
	public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return blockID;
	}
	
	protected void initializeBlock()
	{
	}
	
	public boolean isAssociatedBlockID(int par1)
	{
		return blockID == par1;
	}
	
	public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par1IBlockAccess.getBlockMaterial(par2, par3, par4).isSolid();
	}
	
	public boolean isCollidable()
	{
		return true;
	}
	
	public boolean isFlowerPot()
	{
		return false;
	}
	
	public boolean isOpaqueCube()
	{
		return true;
	}
	
	public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return 0;
	}
	
	public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return 0;
	}
	
	private boolean isVecInsideXYBounds(Vec3 par1Vec3)
	{
		return par1Vec3 == null ? false : par1Vec3.xCoord >= minX && par1Vec3.xCoord <= maxX && par1Vec3.yCoord >= minY && par1Vec3.yCoord <= maxY;
	}
	
	private boolean isVecInsideXZBounds(Vec3 par1Vec3)
	{
		return par1Vec3 == null ? false : par1Vec3.xCoord >= minX && par1Vec3.xCoord <= maxX && par1Vec3.zCoord >= minZ && par1Vec3.zCoord <= maxZ;
	}
	
	private boolean isVecInsideYZBounds(Vec3 par1Vec3)
	{
		return par1Vec3 == null ? false : par1Vec3.yCoord >= minY && par1Vec3.yCoord <= maxY && par1Vec3.zCoord >= minZ && par1Vec3.zCoord <= maxZ;
	}
	
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		return false;
	}
	
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
	}
	
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
	}
	
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion)
	{
	}
	
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
	{
	}
	
	public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		return false;
	}
	
	public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
	{
	}
	
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	{
		return par9;
	}
	
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack)
	{
	}
	
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
	}
	
	public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
	}
	
	public void onFallenUpon(World par1World, int par2, int par3, int par4, Entity par5Entity, float par6)
	{
	}
	
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
	}
	
	public void onPostBlockPlaced(World par1World, int par2, int par3, int par4, int par5)
	{
	}
	
	public void onSetBlockIDWithMetaData(World par1World, int par2, int par3, int par4, int par5)
	{
	}
	
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}
	
	public int quantityDroppedWithBonus(int par1, Random par2Random)
	{
		return quantityDropped(par2Random);
	}
	
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
	}
	
	public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(unlocalizedName);
	}
	
	public boolean renderAsNormalBlock()
	{
		return true;
	}
	
	protected final void setBlockBounds(float par1, float par2, float par3, float par4, float par5, float par6)
	{
		minX = par1;
		minY = par2;
		minZ = par3;
		maxX = par4;
		maxY = par5;
		maxZ = par6;
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
	}
	
	public void setBlockBoundsForItemRender()
	{
	}
	
	protected Block setBlockUnbreakable()
	{
		setHardness(-1.0F);
		return this;
	}
	
	public Block setCreativeTab(CreativeTabs par1CreativeTabs)
	{
		displayOnCreativeTab = par1CreativeTabs;
		return this;
	}
	
	protected Block setHardness(float par1)
	{
		blockHardness = par1;
		if(blockResistance < par1 * 5.0F)
		{
			blockResistance = par1 * 5.0F;
		}
		return this;
	}
	
	protected Block setLightOpacity(int par1)
	{
		lightOpacity[blockID] = par1;
		return this;
	}
	
	protected Block setLightValue(float par1)
	{
		lightValue[blockID] = (int) (15.0F * par1);
		return this;
	}
	
	protected Block setResistance(float par1)
	{
		blockResistance = par1 * 3.0F;
		return this;
	}
	
	protected Block setStepSound(StepSound par1StepSound)
	{
		stepSound = par1StepSound;
		return this;
	}
	
	protected Block setTickRandomly(boolean par1)
	{
		needsRandomTick = par1;
		return this;
	}
	
	public Block setUnlocalizedName(String par1Str)
	{
		unlocalizedName = par1Str;
		return this;
	}
	
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par5 == 0 && minY > 0.0D ? true : par5 == 1 && maxY < 1.0D ? true : par5 == 2 && minZ > 0.0D ? true : par5 == 3 && maxZ < 1.0D ? true : par5 == 4 && minX > 0.0D ? true : par5 == 5 && maxX < 1.0D ? true : !par1IBlockAccess.isBlockOpaqueCube(par2, par3, par4);
	}
	
	public int tickRate(World par1World)
	{
		return 10;
	}
	
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
	}
	
	public void velocityToAddToEntity(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3 par6Vec3)
	{
	}
	
	public static boolean isAssociatedBlockID(int par0, int par1)
	{
		return par0 == par1 ? true : par0 != 0 && par1 != 0 && blocksList[par0] != null && blocksList[par1] != null ? blocksList[par0].isAssociatedBlockID(par1) : false;
	}
	
	public static boolean isNormalCube(int par0)
	{
		Block var1 = blocksList[par0];
		return var1 == null ? false : var1.blockMaterial.isOpaque() && var1.renderAsNormalBlock() && !var1.canProvidePower();
	}
	
	static
	{
		Item.itemsList[cloth.blockID] = new ItemCloth(cloth.blockID - 256).setUnlocalizedName("cloth");
		Item.itemsList[wood.blockID] = new ItemMultiTextureTile(wood.blockID - 256, wood, BlockLog.woodType).setUnlocalizedName("log");
		Item.itemsList[planks.blockID] = new ItemMultiTextureTile(planks.blockID - 256, planks, BlockWood.woodType).setUnlocalizedName("wood");
		Item.itemsList[silverfish.blockID] = new ItemMultiTextureTile(silverfish.blockID - 256, silverfish, BlockSilverfish.silverfishStoneTypes).setUnlocalizedName("monsterStoneEgg");
		Item.itemsList[stoneBrick.blockID] = new ItemMultiTextureTile(stoneBrick.blockID - 256, stoneBrick, BlockStoneBrick.STONE_BRICK_TYPES).setUnlocalizedName("stonebricksmooth");
		Item.itemsList[sandStone.blockID] = new ItemMultiTextureTile(sandStone.blockID - 256, sandStone, BlockSandStone.SAND_STONE_TYPES).setUnlocalizedName("sandStone");
		Item.itemsList[blockNetherQuartz.blockID] = new ItemMultiTextureTile(blockNetherQuartz.blockID - 256, blockNetherQuartz, BlockQuartz.quartzBlockTypes).setUnlocalizedName("quartzBlock");
		Item.itemsList[stoneSingleSlab.blockID] = new ItemSlab(stoneSingleSlab.blockID - 256, stoneSingleSlab, stoneDoubleSlab, false).setUnlocalizedName("stoneSlab");
		Item.itemsList[stoneDoubleSlab.blockID] = new ItemSlab(stoneDoubleSlab.blockID - 256, stoneSingleSlab, stoneDoubleSlab, true).setUnlocalizedName("stoneSlab");
		Item.itemsList[woodSingleSlab.blockID] = new ItemSlab(woodSingleSlab.blockID - 256, woodSingleSlab, woodDoubleSlab, false).setUnlocalizedName("woodSlab");
		Item.itemsList[woodDoubleSlab.blockID] = new ItemSlab(woodDoubleSlab.blockID - 256, woodSingleSlab, woodDoubleSlab, true).setUnlocalizedName("woodSlab");
		Item.itemsList[sapling.blockID] = new ItemMultiTextureTile(sapling.blockID - 256, sapling, BlockSapling.WOOD_TYPES).setUnlocalizedName("sapling");
		Item.itemsList[leaves.blockID] = new ItemLeaves(leaves.blockID - 256).setUnlocalizedName("leaves");
		Item.itemsList[vine.blockID] = new ItemColored(vine.blockID - 256, false);
		Item.itemsList[tallGrass.blockID] = new ItemColored(tallGrass.blockID - 256, true).setBlockNames(new String[] { "shrub", "grass", "fern" });
		Item.itemsList[snow.blockID] = new ItemSnow(snow.blockID - 256, snow);
		Item.itemsList[waterlily.blockID] = new ItemLilyPad(waterlily.blockID - 256);
		Item.itemsList[pistonBase.blockID] = new ItemPiston(pistonBase.blockID - 256);
		Item.itemsList[pistonStickyBase.blockID] = new ItemPiston(pistonStickyBase.blockID - 256);
		Item.itemsList[cobblestoneWall.blockID] = new ItemMultiTextureTile(cobblestoneWall.blockID - 256, cobblestoneWall, BlockWall.types).setUnlocalizedName("cobbleWall");
		Item.itemsList[anvil.blockID] = new ItemAnvilBlock(anvil).setUnlocalizedName("anvil");
		for(int var0 = 0; var0 < 256; ++var0)
		{
			if(blocksList[var0] != null)
			{
				if(Item.itemsList[var0] == null)
				{
					Item.itemsList[var0] = new ItemBlock(var0 - 256);
					blocksList[var0].initializeBlock();
				}
				boolean var1 = false;
				if(var0 > 0 && blocksList[var0].getRenderType() == 10)
				{
					var1 = true;
				}
				if(var0 > 0 && blocksList[var0] instanceof BlockHalfSlab)
				{
					var1 = true;
				}
				if(var0 == tilledField.blockID)
				{
					var1 = true;
				}
				if(canBlockGrass[var0])
				{
					var1 = true;
				}
				if(lightOpacity[var0] == 0)
				{
					var1 = true;
				}
				useNeighborBrightness[var0] = var1;
			}
		}
		canBlockGrass[0] = true;
		StatList.initBreakableStats();
	}
}
