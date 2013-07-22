package net.minecraft.src;

public class PathFinder
{
	private IBlockAccess worldMap;
	private Path path = new Path();
	private IntHashMap pointMap = new IntHashMap();
	private PathPoint[] pathOptions = new PathPoint[32];
	private boolean isWoddenDoorAllowed;
	private boolean isMovementBlockAllowed;
	private boolean isPathingInWater;
	private boolean canEntityDrown;
	
	public PathFinder(IBlockAccess p_i3903_1_, boolean p_i3903_2_, boolean p_i3903_3_, boolean p_i3903_4_, boolean p_i3903_5_)
	{
		worldMap = p_i3903_1_;
		isWoddenDoorAllowed = p_i3903_2_;
		isMovementBlockAllowed = p_i3903_3_;
		isPathingInWater = p_i3903_4_;
		canEntityDrown = p_i3903_5_;
	}
	
	private PathEntity addToPath(Entity p_75861_1_, PathPoint p_75861_2_, PathPoint p_75861_3_, PathPoint p_75861_4_, float p_75861_5_)
	{
		p_75861_2_.totalPathDistance = 0.0F;
		p_75861_2_.distanceToNext = p_75861_2_.func_75832_b(p_75861_3_);
		p_75861_2_.distanceToTarget = p_75861_2_.distanceToNext;
		path.clearPath();
		path.addPoint(p_75861_2_);
		PathPoint var6 = p_75861_2_;
		while(!path.isPathEmpty())
		{
			PathPoint var7 = path.dequeue();
			if(var7.equals(p_75861_3_)) return createEntityPath(p_75861_2_, p_75861_3_);
			if(var7.func_75832_b(p_75861_3_) < var6.func_75832_b(p_75861_3_))
			{
				var6 = var7;
			}
			var7.isFirst = true;
			int var8 = findPathOptions(p_75861_1_, var7, p_75861_4_, p_75861_3_, p_75861_5_);
			for(int var9 = 0; var9 < var8; ++var9)
			{
				PathPoint var10 = pathOptions[var9];
				float var11 = var7.totalPathDistance + var7.func_75832_b(var10);
				if(!var10.isAssigned() || var11 < var10.totalPathDistance)
				{
					var10.previous = var7;
					var10.totalPathDistance = var11;
					var10.distanceToNext = var10.func_75832_b(p_75861_3_);
					if(var10.isAssigned())
					{
						path.changeDistance(var10, var10.totalPathDistance + var10.distanceToNext);
					} else
					{
						var10.distanceToTarget = var10.totalPathDistance + var10.distanceToNext;
						path.addPoint(var10);
					}
				}
			}
		}
		if(var6 == p_75861_2_) return null;
		else return createEntityPath(p_75861_2_, var6);
	}
	
	private PathEntity createEntityPath(PathPoint p_75853_1_, PathPoint p_75853_2_)
	{
		int var3 = 1;
		PathPoint var4;
		for(var4 = p_75853_2_; var4.previous != null; var4 = var4.previous)
		{
			++var3;
		}
		PathPoint[] var5 = new PathPoint[var3];
		var4 = p_75853_2_;
		--var3;
		for(var5[var3] = p_75853_2_; var4.previous != null; var5[var3] = var4)
		{
			var4 = var4.previous;
			--var3;
		}
		return new PathEntity(var5);
	}
	
	private PathEntity createEntityPathTo(Entity p_75857_1_, double p_75857_2_, double p_75857_4_, double p_75857_6_, float p_75857_8_)
	{
		path.clearPath();
		pointMap.clearMap();
		boolean var9 = isPathingInWater;
		int var10 = MathHelper.floor_double(p_75857_1_.boundingBox.minY + 0.5D);
		if(canEntityDrown && p_75857_1_.isInWater())
		{
			var10 = (int) p_75857_1_.boundingBox.minY;
			for(int var11 = worldMap.getBlockId(MathHelper.floor_double(p_75857_1_.posX), var10, MathHelper.floor_double(p_75857_1_.posZ)); var11 == Block.waterMoving.blockID || var11 == Block.waterStill.blockID; var11 = worldMap.getBlockId(MathHelper.floor_double(p_75857_1_.posX), var10, MathHelper.floor_double(p_75857_1_.posZ)))
			{
				++var10;
			}
			var9 = isPathingInWater;
			isPathingInWater = false;
		} else
		{
			var10 = MathHelper.floor_double(p_75857_1_.boundingBox.minY + 0.5D);
		}
		PathPoint var15 = openPoint(MathHelper.floor_double(p_75857_1_.boundingBox.minX), var10, MathHelper.floor_double(p_75857_1_.boundingBox.minZ));
		PathPoint var12 = openPoint(MathHelper.floor_double(p_75857_2_ - p_75857_1_.width / 2.0F), MathHelper.floor_double(p_75857_4_), MathHelper.floor_double(p_75857_6_ - p_75857_1_.width / 2.0F));
		PathPoint var13 = new PathPoint(MathHelper.floor_float(p_75857_1_.width + 1.0F), MathHelper.floor_float(p_75857_1_.height + 1.0F), MathHelper.floor_float(p_75857_1_.width + 1.0F));
		PathEntity var14 = addToPath(p_75857_1_, var15, var12, var13, p_75857_8_);
		isPathingInWater = var9;
		return var14;
	}
	
	public PathEntity createEntityPathTo(Entity p_75856_1_, Entity p_75856_2_, float p_75856_3_)
	{
		return this.createEntityPathTo(p_75856_1_, p_75856_2_.posX, p_75856_2_.boundingBox.minY, p_75856_2_.posZ, p_75856_3_);
	}
	
	public PathEntity createEntityPathTo(Entity p_75859_1_, int p_75859_2_, int p_75859_3_, int p_75859_4_, float p_75859_5_)
	{
		return this.createEntityPathTo(p_75859_1_, p_75859_2_ + 0.5F, p_75859_3_ + 0.5F, p_75859_4_ + 0.5F, p_75859_5_);
	}
	
	private int findPathOptions(Entity p_75860_1_, PathPoint p_75860_2_, PathPoint p_75860_3_, PathPoint p_75860_4_, float p_75860_5_)
	{
		int var6 = 0;
		byte var7 = 0;
		if(getVerticalOffset(p_75860_1_, p_75860_2_.xCoord, p_75860_2_.yCoord + 1, p_75860_2_.zCoord, p_75860_3_) == 1)
		{
			var7 = 1;
		}
		PathPoint var8 = getSafePoint(p_75860_1_, p_75860_2_.xCoord, p_75860_2_.yCoord, p_75860_2_.zCoord + 1, p_75860_3_, var7);
		PathPoint var9 = getSafePoint(p_75860_1_, p_75860_2_.xCoord - 1, p_75860_2_.yCoord, p_75860_2_.zCoord, p_75860_3_, var7);
		PathPoint var10 = getSafePoint(p_75860_1_, p_75860_2_.xCoord + 1, p_75860_2_.yCoord, p_75860_2_.zCoord, p_75860_3_, var7);
		PathPoint var11 = getSafePoint(p_75860_1_, p_75860_2_.xCoord, p_75860_2_.yCoord, p_75860_2_.zCoord - 1, p_75860_3_, var7);
		if(var8 != null && !var8.isFirst && var8.distanceTo(p_75860_4_) < p_75860_5_)
		{
			pathOptions[var6++] = var8;
		}
		if(var9 != null && !var9.isFirst && var9.distanceTo(p_75860_4_) < p_75860_5_)
		{
			pathOptions[var6++] = var9;
		}
		if(var10 != null && !var10.isFirst && var10.distanceTo(p_75860_4_) < p_75860_5_)
		{
			pathOptions[var6++] = var10;
		}
		if(var11 != null && !var11.isFirst && var11.distanceTo(p_75860_4_) < p_75860_5_)
		{
			pathOptions[var6++] = var11;
		}
		return var6;
	}
	
	private PathPoint getSafePoint(Entity p_75858_1_, int p_75858_2_, int p_75858_3_, int p_75858_4_, PathPoint p_75858_5_, int p_75858_6_)
	{
		PathPoint var7 = null;
		int var8 = getVerticalOffset(p_75858_1_, p_75858_2_, p_75858_3_, p_75858_4_, p_75858_5_);
		if(var8 == 2) return openPoint(p_75858_2_, p_75858_3_, p_75858_4_);
		else
		{
			if(var8 == 1)
			{
				var7 = openPoint(p_75858_2_, p_75858_3_, p_75858_4_);
			}
			if(var7 == null && p_75858_6_ > 0 && var8 != -3 && var8 != -4 && getVerticalOffset(p_75858_1_, p_75858_2_, p_75858_3_ + p_75858_6_, p_75858_4_, p_75858_5_) == 1)
			{
				var7 = openPoint(p_75858_2_, p_75858_3_ + p_75858_6_, p_75858_4_);
				p_75858_3_ += p_75858_6_;
			}
			if(var7 != null)
			{
				int var9 = 0;
				int var10 = 0;
				while(p_75858_3_ > 0)
				{
					var10 = getVerticalOffset(p_75858_1_, p_75858_2_, p_75858_3_ - 1, p_75858_4_, p_75858_5_);
					if(isPathingInWater && var10 == -1) return null;
					if(var10 != 1)
					{
						break;
					}
					if(var9++ >= p_75858_1_.func_82143_as()) return null;
					--p_75858_3_;
					if(p_75858_3_ > 0)
					{
						var7 = openPoint(p_75858_2_, p_75858_3_, p_75858_4_);
					}
				}
				if(var10 == -2) return null;
			}
			return var7;
		}
	}
	
	public int getVerticalOffset(Entity p_75855_1_, int p_75855_2_, int p_75855_3_, int p_75855_4_, PathPoint p_75855_5_)
	{
		return func_82565_a(p_75855_1_, p_75855_2_, p_75855_3_, p_75855_4_, p_75855_5_, isPathingInWater, isMovementBlockAllowed, isWoddenDoorAllowed);
	}
	
	private final PathPoint openPoint(int p_75854_1_, int p_75854_2_, int p_75854_3_)
	{
		int var4 = PathPoint.makeHash(p_75854_1_, p_75854_2_, p_75854_3_);
		PathPoint var5 = (PathPoint) pointMap.lookup(var4);
		if(var5 == null)
		{
			var5 = new PathPoint(p_75854_1_, p_75854_2_, p_75854_3_);
			pointMap.addKey(var4, var5);
		}
		return var5;
	}
	
	public static int func_82565_a(Entity p_82565_0_, int p_82565_1_, int p_82565_2_, int p_82565_3_, PathPoint p_82565_4_, boolean p_82565_5_, boolean p_82565_6_, boolean p_82565_7_)
	{
		boolean var8 = false;
		for(int var9 = p_82565_1_; var9 < p_82565_1_ + p_82565_4_.xCoord; ++var9)
		{
			for(int var10 = p_82565_2_; var10 < p_82565_2_ + p_82565_4_.yCoord; ++var10)
			{
				for(int var11 = p_82565_3_; var11 < p_82565_3_ + p_82565_4_.zCoord; ++var11)
				{
					int var12 = p_82565_0_.worldObj.getBlockId(var9, var10, var11);
					if(var12 > 0)
					{
						if(var12 == Block.trapdoor.blockID)
						{
							var8 = true;
						} else if(var12 != Block.waterMoving.blockID && var12 != Block.waterStill.blockID)
						{
							if(!p_82565_7_ && var12 == Block.doorWood.blockID) return 0;
						} else
						{
							if(p_82565_5_) return -1;
							var8 = true;
						}
						Block var13 = Block.blocksList[var12];
						int var14 = var13.getRenderType();
						if(p_82565_0_.worldObj.blockGetRenderType(var9, var10, var11) == 9)
						{
							int var18 = MathHelper.floor_double(p_82565_0_.posX);
							int var16 = MathHelper.floor_double(p_82565_0_.posY);
							int var17 = MathHelper.floor_double(p_82565_0_.posZ);
							if(p_82565_0_.worldObj.blockGetRenderType(var18, var16, var17) != 9 && p_82565_0_.worldObj.blockGetRenderType(var18, var16 - 1, var17) != 9) return -3;
						} else if(!var13.getBlocksMovement(p_82565_0_.worldObj, var9, var10, var11) && (!p_82565_6_ || var12 != Block.doorWood.blockID))
						{
							if(var14 == 11 || var12 == Block.fenceGate.blockID || var14 == 32) return -3;
							if(var12 == Block.trapdoor.blockID) return -4;
							Material var15 = var13.blockMaterial;
							if(var15 != Material.lava) return 0;
							if(!p_82565_0_.handleLavaMovement()) return -2;
						}
					}
				}
			}
		}
		return var8 ? 2 : 1;
	}
}
