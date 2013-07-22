package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class MapGenNetherBridge extends MapGenStructure
{
	private List spawnList = new ArrayList();
	
	public MapGenNetherBridge()
	{
		spawnList.add(new SpawnListEntry(EntityBlaze.class, 10, 2, 3));
		spawnList.add(new SpawnListEntry(EntityPigZombie.class, 5, 4, 4));
		spawnList.add(new SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
		spawnList.add(new SpawnListEntry(EntityMagmaCube.class, 3, 4, 4));
	}
	
	@Override protected boolean canSpawnStructureAtCoords(int p_75047_1_, int p_75047_2_)
	{
		int var3 = p_75047_1_ >> 4;
		int var4 = p_75047_2_ >> 4;
		rand.setSeed(var3 ^ var4 << 4 ^ worldObj.getSeed());
		rand.nextInt();
		return rand.nextInt(3) != 0 ? false : p_75047_1_ != (var3 << 4) + 4 + rand.nextInt(8) ? false : p_75047_2_ == (var4 << 4) + 4 + rand.nextInt(8);
	}
	
	public List getSpawnList()
	{
		return spawnList;
	}
	
	@Override protected StructureStart getStructureStart(int p_75049_1_, int p_75049_2_)
	{
		return new StructureNetherBridgeStart(worldObj, rand, p_75049_1_, p_75049_2_);
	}
}
