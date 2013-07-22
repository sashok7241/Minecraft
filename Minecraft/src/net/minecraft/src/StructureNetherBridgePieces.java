package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class StructureNetherBridgePieces
{
	private static final StructureNetherBridgePieceWeight[] primaryComponents = new StructureNetherBridgePieceWeight[] { new StructureNetherBridgePieceWeight(ComponentNetherBridgeStraight.class, 30, 0, true), new StructureNetherBridgePieceWeight(ComponentNetherBridgeCrossing3.class, 10, 4), new StructureNetherBridgePieceWeight(ComponentNetherBridgeCrossing.class, 10, 4), new StructureNetherBridgePieceWeight(ComponentNetherBridgeStairs.class, 10, 3), new StructureNetherBridgePieceWeight(ComponentNetherBridgeThrone.class, 5, 2), new StructureNetherBridgePieceWeight(ComponentNetherBridgeEntrance.class, 5, 1) };
	private static final StructureNetherBridgePieceWeight[] secondaryComponents = new StructureNetherBridgePieceWeight[] { new StructureNetherBridgePieceWeight(ComponentNetherBridgeCorridor5.class, 25, 0, true), new StructureNetherBridgePieceWeight(ComponentNetherBridgeCrossing2.class, 15, 5), new StructureNetherBridgePieceWeight(ComponentNetherBridgeCorridor2.class, 5, 10), new StructureNetherBridgePieceWeight(ComponentNetherBridgeCorridor.class, 5, 10), new StructureNetherBridgePieceWeight(ComponentNetherBridgeCorridor3.class, 10, 3, true), new StructureNetherBridgePieceWeight(ComponentNetherBridgeCorridor4.class, 7, 2), new StructureNetherBridgePieceWeight(ComponentNetherBridgeNetherStalkRoom.class, 5, 2) };
	
	static ComponentNetherBridgePiece createNextComponent(StructureNetherBridgePieceWeight p_78740_0_, List p_78740_1_, Random p_78740_2_, int p_78740_3_, int p_78740_4_, int p_78740_5_, int p_78740_6_, int p_78740_7_)
	{
		return createNextComponentRandom(p_78740_0_, p_78740_1_, p_78740_2_, p_78740_3_, p_78740_4_, p_78740_5_, p_78740_6_, p_78740_7_);
	}
	
	private static ComponentNetherBridgePiece createNextComponentRandom(StructureNetherBridgePieceWeight p_78738_0_, List p_78738_1_, Random p_78738_2_, int p_78738_3_, int p_78738_4_, int p_78738_5_, int p_78738_6_, int p_78738_7_)
	{
		Class var8 = p_78738_0_.weightClass;
		Object var9 = null;
		if(var8 == ComponentNetherBridgeStraight.class)
		{
			var9 = ComponentNetherBridgeStraight.createValidComponent(p_78738_1_, p_78738_2_, p_78738_3_, p_78738_4_, p_78738_5_, p_78738_6_, p_78738_7_);
		} else if(var8 == ComponentNetherBridgeCrossing3.class)
		{
			var9 = ComponentNetherBridgeCrossing3.createValidComponent(p_78738_1_, p_78738_2_, p_78738_3_, p_78738_4_, p_78738_5_, p_78738_6_, p_78738_7_);
		} else if(var8 == ComponentNetherBridgeCrossing.class)
		{
			var9 = ComponentNetherBridgeCrossing.createValidComponent(p_78738_1_, p_78738_2_, p_78738_3_, p_78738_4_, p_78738_5_, p_78738_6_, p_78738_7_);
		} else if(var8 == ComponentNetherBridgeStairs.class)
		{
			var9 = ComponentNetherBridgeStairs.createValidComponent(p_78738_1_, p_78738_2_, p_78738_3_, p_78738_4_, p_78738_5_, p_78738_6_, p_78738_7_);
		} else if(var8 == ComponentNetherBridgeThrone.class)
		{
			var9 = ComponentNetherBridgeThrone.createValidComponent(p_78738_1_, p_78738_2_, p_78738_3_, p_78738_4_, p_78738_5_, p_78738_6_, p_78738_7_);
		} else if(var8 == ComponentNetherBridgeEntrance.class)
		{
			var9 = ComponentNetherBridgeEntrance.createValidComponent(p_78738_1_, p_78738_2_, p_78738_3_, p_78738_4_, p_78738_5_, p_78738_6_, p_78738_7_);
		} else if(var8 == ComponentNetherBridgeCorridor5.class)
		{
			var9 = ComponentNetherBridgeCorridor5.createValidComponent(p_78738_1_, p_78738_2_, p_78738_3_, p_78738_4_, p_78738_5_, p_78738_6_, p_78738_7_);
		} else if(var8 == ComponentNetherBridgeCorridor2.class)
		{
			var9 = ComponentNetherBridgeCorridor2.createValidComponent(p_78738_1_, p_78738_2_, p_78738_3_, p_78738_4_, p_78738_5_, p_78738_6_, p_78738_7_);
		} else if(var8 == ComponentNetherBridgeCorridor.class)
		{
			var9 = ComponentNetherBridgeCorridor.createValidComponent(p_78738_1_, p_78738_2_, p_78738_3_, p_78738_4_, p_78738_5_, p_78738_6_, p_78738_7_);
		} else if(var8 == ComponentNetherBridgeCorridor3.class)
		{
			var9 = ComponentNetherBridgeCorridor3.createValidComponent(p_78738_1_, p_78738_2_, p_78738_3_, p_78738_4_, p_78738_5_, p_78738_6_, p_78738_7_);
		} else if(var8 == ComponentNetherBridgeCorridor4.class)
		{
			var9 = ComponentNetherBridgeCorridor4.createValidComponent(p_78738_1_, p_78738_2_, p_78738_3_, p_78738_4_, p_78738_5_, p_78738_6_, p_78738_7_);
		} else if(var8 == ComponentNetherBridgeCrossing2.class)
		{
			var9 = ComponentNetherBridgeCrossing2.createValidComponent(p_78738_1_, p_78738_2_, p_78738_3_, p_78738_4_, p_78738_5_, p_78738_6_, p_78738_7_);
		} else if(var8 == ComponentNetherBridgeNetherStalkRoom.class)
		{
			var9 = ComponentNetherBridgeNetherStalkRoom.createValidComponent(p_78738_1_, p_78738_2_, p_78738_3_, p_78738_4_, p_78738_5_, p_78738_6_, p_78738_7_);
		}
		return (ComponentNetherBridgePiece) var9;
	}
	
	static StructureNetherBridgePieceWeight[] getPrimaryComponents()
	{
		return primaryComponents;
	}
	
	static StructureNetherBridgePieceWeight[] getSecondaryComponents()
	{
		return secondaryComponents;
	}
}
