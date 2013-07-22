package net.minecraft.src;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public final class SpawnerAnimals
{
	private static HashMap eligibleChunksForSpawning = new HashMap();
	protected static final Class[] nightSpawnEntities = new Class[] { EntitySpider.class, EntityZombie.class, EntitySkeleton.class };
	
	public static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType p_77190_0_, World p_77190_1_, int p_77190_2_, int p_77190_3_, int p_77190_4_)
	{
		if(p_77190_0_.getCreatureMaterial() == Material.water) return p_77190_1_.getBlockMaterial(p_77190_2_, p_77190_3_, p_77190_4_).isLiquid() && p_77190_1_.getBlockMaterial(p_77190_2_, p_77190_3_ - 1, p_77190_4_).isLiquid() && !p_77190_1_.isBlockNormalCube(p_77190_2_, p_77190_3_ + 1, p_77190_4_);
		else if(!p_77190_1_.doesBlockHaveSolidTopSurface(p_77190_2_, p_77190_3_ - 1, p_77190_4_)) return false;
		else
		{
			int var5 = p_77190_1_.getBlockId(p_77190_2_, p_77190_3_ - 1, p_77190_4_);
			return var5 != Block.bedrock.blockID && !p_77190_1_.isBlockNormalCube(p_77190_2_, p_77190_3_, p_77190_4_) && !p_77190_1_.getBlockMaterial(p_77190_2_, p_77190_3_, p_77190_4_).isLiquid() && !p_77190_1_.isBlockNormalCube(p_77190_2_, p_77190_3_ + 1, p_77190_4_);
		}
	}
	
	private static void creatureSpecificInit(EntityLiving p_77188_0_, World p_77188_1_, float p_77188_2_, float p_77188_3_, float p_77188_4_)
	{
		p_77188_0_.initCreature();
	}
	
	public static final int findChunksForSpawning(WorldServer p_77192_0_, boolean p_77192_1_, boolean p_77192_2_, boolean p_77192_3_)
	{
		if(!p_77192_1_ && !p_77192_2_) return 0;
		else
		{
			eligibleChunksForSpawning.clear();
			int var4;
			int var7;
			for(var4 = 0; var4 < p_77192_0_.playerEntities.size(); ++var4)
			{
				EntityPlayer var5 = (EntityPlayer) p_77192_0_.playerEntities.get(var4);
				int var6 = MathHelper.floor_double(var5.posX / 16.0D);
				var7 = MathHelper.floor_double(var5.posZ / 16.0D);
				byte var8 = 8;
				for(int var9 = -var8; var9 <= var8; ++var9)
				{
					for(int var10 = -var8; var10 <= var8; ++var10)
					{
						boolean var11 = var9 == -var8 || var9 == var8 || var10 == -var8 || var10 == var8;
						ChunkCoordIntPair var12 = new ChunkCoordIntPair(var9 + var6, var10 + var7);
						if(!var11)
						{
							eligibleChunksForSpawning.put(var12, Boolean.valueOf(false));
						} else if(!eligibleChunksForSpawning.containsKey(var12))
						{
							eligibleChunksForSpawning.put(var12, Boolean.valueOf(true));
						}
					}
				}
			}
			var4 = 0;
			ChunkCoordinates var32 = p_77192_0_.getSpawnPoint();
			EnumCreatureType[] var33 = EnumCreatureType.values();
			var7 = var33.length;
			for(int var34 = 0; var34 < var7; ++var34)
			{
				EnumCreatureType var35 = var33[var34];
				if((!var35.getPeacefulCreature() || p_77192_2_) && (var35.getPeacefulCreature() || p_77192_1_) && (!var35.getAnimal() || p_77192_3_) && p_77192_0_.countEntities(var35.getCreatureClass()) <= var35.getMaxNumberOfCreature() * eligibleChunksForSpawning.size() / 256)
				{
					Iterator var37 = eligibleChunksForSpawning.keySet().iterator();
					label110: while(var37.hasNext())
					{
						ChunkCoordIntPair var36 = (ChunkCoordIntPair) var37.next();
						if(!((Boolean) eligibleChunksForSpawning.get(var36)).booleanValue())
						{
							ChunkPosition var38 = getRandomSpawningPointInChunk(p_77192_0_, var36.chunkXPos, var36.chunkZPos);
							int var13 = var38.x;
							int var14 = var38.y;
							int var15 = var38.z;
							if(!p_77192_0_.isBlockNormalCube(var13, var14, var15) && p_77192_0_.getBlockMaterial(var13, var14, var15) == var35.getCreatureMaterial())
							{
								int var16 = 0;
								int var17 = 0;
								while(var17 < 3)
								{
									int var18 = var13;
									int var19 = var14;
									int var20 = var15;
									byte var21 = 6;
									SpawnListEntry var22 = null;
									int var23 = 0;
									while(true)
									{
										if(var23 < 4)
										{
											label103:
											{
												var18 += p_77192_0_.rand.nextInt(var21) - p_77192_0_.rand.nextInt(var21);
												var19 += p_77192_0_.rand.nextInt(1) - p_77192_0_.rand.nextInt(1);
												var20 += p_77192_0_.rand.nextInt(var21) - p_77192_0_.rand.nextInt(var21);
												if(canCreatureTypeSpawnAtLocation(var35, p_77192_0_, var18, var19, var20))
												{
													float var24 = var18 + 0.5F;
													float var25 = var19;
													float var26 = var20 + 0.5F;
													if(p_77192_0_.getClosestPlayer(var24, var25, var26, 24.0D) == null)
													{
														float var27 = var24 - var32.posX;
														float var28 = var25 - var32.posY;
														float var29 = var26 - var32.posZ;
														float var30 = var27 * var27 + var28 * var28 + var29 * var29;
														if(var30 >= 576.0F)
														{
															if(var22 == null)
															{
																var22 = p_77192_0_.spawnRandomCreature(var35, var18, var19, var20);
																if(var22 == null)
																{
																	break label103;
																}
															}
															EntityLiving var39;
															try
															{
																var39 = (EntityLiving) var22.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { p_77192_0_ });
															} catch(Exception var31)
															{
																var31.printStackTrace();
																return var4;
															}
															var39.setLocationAndAngles(var24, var25, var26, p_77192_0_.rand.nextFloat() * 360.0F, 0.0F);
															if(var39.getCanSpawnHere())
															{
																++var16;
																p_77192_0_.spawnEntityInWorld(var39);
																creatureSpecificInit(var39, p_77192_0_, var24, var25, var26);
																if(var16 >= var39.getMaxSpawnedInChunk())
																{
																	continue label110;
																}
															}
															var4 += var16;
														}
													}
												}
												++var23;
												continue;
											}
										}
										++var17;
										break;
									}
								}
							}
						}
					}
				}
			}
			return var4;
		}
	}
	
	protected static ChunkPosition getRandomSpawningPointInChunk(World p_77189_0_, int p_77189_1_, int p_77189_2_)
	{
		Chunk var3 = p_77189_0_.getChunkFromChunkCoords(p_77189_1_, p_77189_2_);
		int var4 = p_77189_1_ * 16 + p_77189_0_.rand.nextInt(16);
		int var5 = p_77189_2_ * 16 + p_77189_0_.rand.nextInt(16);
		int var6 = p_77189_0_.rand.nextInt(var3 == null ? p_77189_0_.getActualHeight() : var3.getTopFilledSegment() + 16 - 1);
		return new ChunkPosition(var4, var6, var5);
	}
	
	public static void performWorldGenSpawning(World p_77191_0_, BiomeGenBase p_77191_1_, int p_77191_2_, int p_77191_3_, int p_77191_4_, int p_77191_5_, Random p_77191_6_)
	{
		List var7 = p_77191_1_.getSpawnableList(EnumCreatureType.creature);
		if(!var7.isEmpty())
		{
			while(p_77191_6_.nextFloat() < p_77191_1_.getSpawningChance())
			{
				SpawnListEntry var8 = (SpawnListEntry) WeightedRandom.getRandomItem(p_77191_0_.rand, var7);
				int var9 = var8.minGroupCount + p_77191_6_.nextInt(1 + var8.maxGroupCount - var8.minGroupCount);
				int var10 = p_77191_2_ + p_77191_6_.nextInt(p_77191_4_);
				int var11 = p_77191_3_ + p_77191_6_.nextInt(p_77191_5_);
				int var12 = var10;
				int var13 = var11;
				for(int var14 = 0; var14 < var9; ++var14)
				{
					boolean var15 = false;
					for(int var16 = 0; !var15 && var16 < 4; ++var16)
					{
						int var17 = p_77191_0_.getTopSolidOrLiquidBlock(var10, var11);
						if(canCreatureTypeSpawnAtLocation(EnumCreatureType.creature, p_77191_0_, var10, var17, var11))
						{
							float var18 = var10 + 0.5F;
							float var19 = var17;
							float var20 = var11 + 0.5F;
							EntityLiving var21;
							try
							{
								var21 = (EntityLiving) var8.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { p_77191_0_ });
							} catch(Exception var23)
							{
								var23.printStackTrace();
								continue;
							}
							var21.setLocationAndAngles(var18, var19, var20, p_77191_6_.nextFloat() * 360.0F, 0.0F);
							p_77191_0_.spawnEntityInWorld(var21);
							creatureSpecificInit(var21, p_77191_0_, var18, var19, var20);
							var15 = true;
						}
						var10 += p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5);
						for(var11 += p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5); var10 < p_77191_2_ || var10 >= p_77191_2_ + p_77191_4_ || var11 < p_77191_3_ || var11 >= p_77191_3_ + p_77191_4_; var11 = var13 + p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5))
						{
							var10 = var12 + p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5);
						}
					}
				}
			}
		}
	}
}
