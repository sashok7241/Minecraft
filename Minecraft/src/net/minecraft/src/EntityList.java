package net.minecraft.src;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class EntityList
{
	private static Map stringToClassMapping = new HashMap();
	private static Map classToStringMapping = new HashMap();
	private static Map IDtoClassMapping = new HashMap();
	private static Map classToIDMapping = new HashMap();
	private static Map stringToIDMapping = new HashMap();
	public static HashMap entityEggs = new LinkedHashMap();
	
	private static void addMapping(Class p_75618_0_, String p_75618_1_, int p_75618_2_)
	{
		stringToClassMapping.put(p_75618_1_, p_75618_0_);
		classToStringMapping.put(p_75618_0_, p_75618_1_);
		IDtoClassMapping.put(Integer.valueOf(p_75618_2_), p_75618_0_);
		classToIDMapping.put(p_75618_0_, Integer.valueOf(p_75618_2_));
		stringToIDMapping.put(p_75618_1_, Integer.valueOf(p_75618_2_));
	}
	
	private static void addMapping(Class p_75614_0_, String p_75614_1_, int p_75614_2_, int p_75614_3_, int p_75614_4_)
	{
		addMapping(p_75614_0_, p_75614_1_, p_75614_2_);
		entityEggs.put(Integer.valueOf(p_75614_2_), new EntityEggInfo(p_75614_2_, p_75614_3_, p_75614_4_));
	}
	
	public static Entity createEntityByID(int p_75616_0_, World p_75616_1_)
	{
		Entity var2 = null;
		try
		{
			Class var3 = getClassFromID(p_75616_0_);
			if(var3 != null)
			{
				var2 = (Entity) var3.getConstructor(new Class[] { World.class }).newInstance(new Object[] { p_75616_1_ });
			}
		} catch(Exception var4)
		{
			var4.printStackTrace();
		}
		if(var2 == null)
		{
			p_75616_1_.getWorldLogAgent().logWarning("Skipping Entity with id " + p_75616_0_);
		}
		return var2;
	}
	
	public static Entity createEntityByName(String p_75620_0_, World p_75620_1_)
	{
		Entity var2 = null;
		try
		{
			Class var3 = (Class) stringToClassMapping.get(p_75620_0_);
			if(var3 != null)
			{
				var2 = (Entity) var3.getConstructor(new Class[] { World.class }).newInstance(new Object[] { p_75620_1_ });
			}
		} catch(Exception var4)
		{
			var4.printStackTrace();
		}
		return var2;
	}
	
	public static Entity createEntityFromNBT(NBTTagCompound p_75615_0_, World p_75615_1_)
	{
		Entity var2 = null;
		if("Minecart".equals(p_75615_0_.getString("id")))
		{
			switch(p_75615_0_.getInteger("Type"))
			{
				case 0:
					p_75615_0_.setString("id", "MinecartRideable");
					break;
				case 1:
					p_75615_0_.setString("id", "MinecartChest");
					break;
				case 2:
					p_75615_0_.setString("id", "MinecartFurnace");
			}
			p_75615_0_.removeTag("Type");
		}
		try
		{
			Class var3 = (Class) stringToClassMapping.get(p_75615_0_.getString("id"));
			if(var3 != null)
			{
				var2 = (Entity) var3.getConstructor(new Class[] { World.class }).newInstance(new Object[] { p_75615_1_ });
			}
		} catch(Exception var4)
		{
			var4.printStackTrace();
		}
		if(var2 != null)
		{
			var2.readFromNBT(p_75615_0_);
		} else
		{
			p_75615_1_.getWorldLogAgent().logWarning("Skipping Entity with id " + p_75615_0_.getString("id"));
		}
		return var2;
	}
	
	public static Class getClassFromID(int p_90035_0_)
	{
		return (Class) IDtoClassMapping.get(Integer.valueOf(p_90035_0_));
	}
	
	public static int getEntityID(Entity p_75619_0_)
	{
		Class var1 = p_75619_0_.getClass();
		return classToIDMapping.containsKey(var1) ? ((Integer) classToIDMapping.get(var1)).intValue() : 0;
	}
	
	public static String getEntityString(Entity p_75621_0_)
	{
		return (String) classToStringMapping.get(p_75621_0_.getClass());
	}
	
	public static String getStringFromID(int p_75617_0_)
	{
		Class var1 = getClassFromID(p_75617_0_);
		return var1 != null ? (String) classToStringMapping.get(var1) : null;
	}
	
	static
	{
		addMapping(EntityItem.class, "Item", 1);
		addMapping(EntityXPOrb.class, "XPOrb", 2);
		addMapping(EntityPainting.class, "Painting", 9);
		addMapping(EntityArrow.class, "Arrow", 10);
		addMapping(EntitySnowball.class, "Snowball", 11);
		addMapping(EntityLargeFireball.class, "Fireball", 12);
		addMapping(EntitySmallFireball.class, "SmallFireball", 13);
		addMapping(EntityEnderPearl.class, "ThrownEnderpearl", 14);
		addMapping(EntityEnderEye.class, "EyeOfEnderSignal", 15);
		addMapping(EntityPotion.class, "ThrownPotion", 16);
		addMapping(EntityExpBottle.class, "ThrownExpBottle", 17);
		addMapping(EntityItemFrame.class, "ItemFrame", 18);
		addMapping(EntityWitherSkull.class, "WitherSkull", 19);
		addMapping(EntityTNTPrimed.class, "PrimedTnt", 20);
		addMapping(EntityFallingSand.class, "FallingSand", 21);
		addMapping(EntityFireworkRocket.class, "FireworksRocketEntity", 22);
		addMapping(EntityBoat.class, "Boat", 41);
		addMapping(EntityMinecartEmpty.class, "MinecartRideable", 42);
		addMapping(EntityMinecartChest.class, "MinecartChest", 43);
		addMapping(EntityMinecartFurnace.class, "MinecartFurnace", 44);
		addMapping(EntityMinecartTNT.class, "MinecartTNT", 45);
		addMapping(EntityMinecartHopper.class, "MinecartHopper", 46);
		addMapping(EntityMinecartMobSpawner.class, "MinecartSpawner", 47);
		addMapping(EntityLiving.class, "Mob", 48);
		addMapping(EntityMob.class, "Monster", 49);
		addMapping(EntityCreeper.class, "Creeper", 50, 894731, 0);
		addMapping(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
		addMapping(EntitySpider.class, "Spider", 52, 3419431, 11013646);
		addMapping(EntityGiantZombie.class, "Giant", 53);
		addMapping(EntityZombie.class, "Zombie", 54, 44975, 7969893);
		addMapping(EntitySlime.class, "Slime", 55, 5349438, 8306542);
		addMapping(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
		addMapping(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
		addMapping(EntityEnderman.class, "Enderman", 58, 1447446, 0);
		addMapping(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
		addMapping(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
		addMapping(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
		addMapping(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
		addMapping(EntityDragon.class, "EnderDragon", 63);
		addMapping(EntityWither.class, "WitherBoss", 64);
		addMapping(EntityBat.class, "Bat", 65, 4996656, 986895);
		addMapping(EntityWitch.class, "Witch", 66, 3407872, 5349438);
		addMapping(EntityPig.class, "Pig", 90, 15771042, 14377823);
		addMapping(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
		addMapping(EntityCow.class, "Cow", 92, 4470310, 10592673);
		addMapping(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
		addMapping(EntitySquid.class, "Squid", 94, 2243405, 7375001);
		addMapping(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
		addMapping(EntityMooshroom.class, "MushroomCow", 96, 10489616, 12040119);
		addMapping(EntitySnowman.class, "SnowMan", 97);
		addMapping(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
		addMapping(EntityIronGolem.class, "VillagerGolem", 99);
		addMapping(EntityVillager.class, "Villager", 120, 5651507, 12422002);
		addMapping(EntityEnderCrystal.class, "EnderCrystal", 200);
	}
}