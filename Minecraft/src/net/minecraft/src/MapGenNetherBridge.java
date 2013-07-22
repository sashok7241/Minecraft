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
	
	@Override protected boolean canSpawnStructureAtCoords(int par1, int par2)
	{
		int var3 = par1 >> 4;
		int var4 = par2 >> 4;
		rand.setSeed(var3 ^ var4 << 4 ^ worldObj.getSeed());
		rand.nextInt();
		return rand.nextInt(3) != 0 ? false : par1 != (var3 << 4) + 4 + rand.nextInt(8) ? false : par2 == (var4 << 4) + 4 + rand.nextInt(8);
	}
	
	public List getSpawnList()
	{
		return spawnList;
	}
	
	@Override protected StructureStart getStructureStart(int par1, int par2)
	{
		return new StructureNetherBridgeStart(worldObj, rand, par1, par2);
	}
}
