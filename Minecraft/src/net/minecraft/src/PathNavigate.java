package net.minecraft.src;

public class PathNavigate
{
	private EntityLiving theEntity;
	private World worldObj;
	private PathEntity currentPath;
	private float speed;
	private float pathSearchRange;
	private boolean noSunPathfind = false;
	private int totalTicks;
	private int ticksAtLastPos;
	private Vec3 lastPosCheck = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
	private boolean canPassOpenWoodenDoors = true;
	private boolean canPassClosedWoodenDoors = false;
	private boolean avoidsWater = false;
	private boolean canSwim = false;
	
	public PathNavigate(EntityLiving p_i3507_1_, World p_i3507_2_, float p_i3507_3_)
	{
		theEntity = p_i3507_1_;
		worldObj = p_i3507_2_;
		pathSearchRange = p_i3507_3_;
	}
	
	private boolean canNavigate()
	{
		return theEntity.onGround || canSwim && isInFluid();
	}
	
	public void clearPathEntity()
	{
		currentPath = null;
	}
	
	public boolean getAvoidsWater()
	{
		return avoidsWater;
	}
	
	public boolean getCanBreakDoors()
	{
		return canPassClosedWoodenDoors;
	}
	
	private Vec3 getEntityPosition()
	{
		return worldObj.getWorldVec3Pool().getVecFromPool(theEntity.posX, getPathableYPos(), theEntity.posZ);
	}
	
	public PathEntity getPath()
	{
		return currentPath;
	}
	
	private int getPathableYPos()
	{
		if(theEntity.isInWater() && canSwim)
		{
			int var1 = (int) theEntity.boundingBox.minY;
			int var2 = worldObj.getBlockId(MathHelper.floor_double(theEntity.posX), var1, MathHelper.floor_double(theEntity.posZ));
			int var3 = 0;
			do
			{
				if(var2 != Block.waterMoving.blockID && var2 != Block.waterStill.blockID) return var1;
				++var1;
				var2 = worldObj.getBlockId(MathHelper.floor_double(theEntity.posX), var1, MathHelper.floor_double(theEntity.posZ));
				++var3;
			} while(var3 <= 16);
			return (int) theEntity.boundingBox.minY;
		} else return (int) (theEntity.boundingBox.minY + 0.5D);
	}
	
	public PathEntity getPathToEntityLiving(EntityLiving p_75494_1_)
	{
		return !canNavigate() ? null : worldObj.getPathEntityToEntity(theEntity, p_75494_1_, pathSearchRange, canPassOpenWoodenDoors, canPassClosedWoodenDoors, avoidsWater, canSwim);
	}
	
	public PathEntity getPathToXYZ(double p_75488_1_, double p_75488_3_, double p_75488_5_)
	{
		return !canNavigate() ? null : worldObj.getEntityPathToXYZ(theEntity, MathHelper.floor_double(p_75488_1_), (int) p_75488_3_, MathHelper.floor_double(p_75488_5_), pathSearchRange, canPassOpenWoodenDoors, canPassClosedWoodenDoors, avoidsWater, canSwim);
	}
	
	private boolean isDirectPathBetweenPoints(Vec3 p_75493_1_, Vec3 p_75493_2_, int p_75493_3_, int p_75493_4_, int p_75493_5_)
	{
		int var6 = MathHelper.floor_double(p_75493_1_.xCoord);
		int var7 = MathHelper.floor_double(p_75493_1_.zCoord);
		double var8 = p_75493_2_.xCoord - p_75493_1_.xCoord;
		double var10 = p_75493_2_.zCoord - p_75493_1_.zCoord;
		double var12 = var8 * var8 + var10 * var10;
		if(var12 < 1.0E-8D) return false;
		else
		{
			double var14 = 1.0D / Math.sqrt(var12);
			var8 *= var14;
			var10 *= var14;
			p_75493_3_ += 2;
			p_75493_5_ += 2;
			if(!isSafeToStandAt(var6, (int) p_75493_1_.yCoord, var7, p_75493_3_, p_75493_4_, p_75493_5_, p_75493_1_, var8, var10)) return false;
			else
			{
				p_75493_3_ -= 2;
				p_75493_5_ -= 2;
				double var16 = 1.0D / Math.abs(var8);
				double var18 = 1.0D / Math.abs(var10);
				double var20 = var6 * 1 - p_75493_1_.xCoord;
				double var22 = var7 * 1 - p_75493_1_.zCoord;
				if(var8 >= 0.0D)
				{
					++var20;
				}
				if(var10 >= 0.0D)
				{
					++var22;
				}
				var20 /= var8;
				var22 /= var10;
				int var24 = var8 < 0.0D ? -1 : 1;
				int var25 = var10 < 0.0D ? -1 : 1;
				int var26 = MathHelper.floor_double(p_75493_2_.xCoord);
				int var27 = MathHelper.floor_double(p_75493_2_.zCoord);
				int var28 = var26 - var6;
				int var29 = var27 - var7;
				do
				{
					if(var28 * var24 <= 0 && var29 * var25 <= 0) return true;
					if(var20 < var22)
					{
						var20 += var16;
						var6 += var24;
						var28 = var26 - var6;
					} else
					{
						var22 += var18;
						var7 += var25;
						var29 = var27 - var7;
					}
				} while(isSafeToStandAt(var6, (int) p_75493_1_.yCoord, var7, p_75493_3_, p_75493_4_, p_75493_5_, p_75493_1_, var8, var10));
				return false;
			}
		}
	}
	
	private boolean isInFluid()
	{
		return theEntity.isInWater() || theEntity.handleLavaMovement();
	}
	
	private boolean isPositionClear(int p_75496_1_, int p_75496_2_, int p_75496_3_, int p_75496_4_, int p_75496_5_, int p_75496_6_, Vec3 p_75496_7_, double p_75496_8_, double p_75496_10_)
	{
		for(int var12 = p_75496_1_; var12 < p_75496_1_ + p_75496_4_; ++var12)
		{
			for(int var13 = p_75496_2_; var13 < p_75496_2_ + p_75496_5_; ++var13)
			{
				for(int var14 = p_75496_3_; var14 < p_75496_3_ + p_75496_6_; ++var14)
				{
					double var15 = var12 + 0.5D - p_75496_7_.xCoord;
					double var17 = var14 + 0.5D - p_75496_7_.zCoord;
					if(var15 * p_75496_8_ + var17 * p_75496_10_ >= 0.0D)
					{
						int var19 = worldObj.getBlockId(var12, var13, var14);
						if(var19 > 0 && !Block.blocksList[var19].getBlocksMovement(worldObj, var12, var13, var14)) return false;
					}
				}
			}
		}
		return true;
	}
	
	private boolean isSafeToStandAt(int p_75483_1_, int p_75483_2_, int p_75483_3_, int p_75483_4_, int p_75483_5_, int p_75483_6_, Vec3 p_75483_7_, double p_75483_8_, double p_75483_10_)
	{
		int var12 = p_75483_1_ - p_75483_4_ / 2;
		int var13 = p_75483_3_ - p_75483_6_ / 2;
		if(!isPositionClear(var12, p_75483_2_, var13, p_75483_4_, p_75483_5_, p_75483_6_, p_75483_7_, p_75483_8_, p_75483_10_)) return false;
		else
		{
			for(int var14 = var12; var14 < var12 + p_75483_4_; ++var14)
			{
				for(int var15 = var13; var15 < var13 + p_75483_6_; ++var15)
				{
					double var16 = var14 + 0.5D - p_75483_7_.xCoord;
					double var18 = var15 + 0.5D - p_75483_7_.zCoord;
					if(var16 * p_75483_8_ + var18 * p_75483_10_ >= 0.0D)
					{
						int var20 = worldObj.getBlockId(var14, p_75483_2_ - 1, var15);
						if(var20 <= 0) return false;
						Material var21 = Block.blocksList[var20].blockMaterial;
						if(var21 == Material.water && !theEntity.isInWater()) return false;
						if(var21 == Material.lava) return false;
					}
				}
			}
			return true;
		}
	}
	
	public boolean noPath()
	{
		return currentPath == null || currentPath.isFinished();
	}
	
	public void onUpdateNavigation()
	{
		++totalTicks;
		if(!noPath())
		{
			if(canNavigate())
			{
				pathFollow();
			}
			if(!noPath())
			{
				Vec3 var1 = currentPath.getPosition(theEntity);
				if(var1 != null)
				{
					theEntity.getMoveHelper().setMoveTo(var1.xCoord, var1.yCoord, var1.zCoord, speed);
				}
			}
		}
	}
	
	private void pathFollow()
	{
		Vec3 var1 = getEntityPosition();
		int var2 = currentPath.getCurrentPathLength();
		for(int var3 = currentPath.getCurrentPathIndex(); var3 < currentPath.getCurrentPathLength(); ++var3)
		{
			if(currentPath.getPathPointFromIndex(var3).yCoord != (int) var1.yCoord)
			{
				var2 = var3;
				break;
			}
		}
		float var8 = theEntity.width * theEntity.width;
		int var4;
		for(var4 = currentPath.getCurrentPathIndex(); var4 < var2; ++var4)
		{
			if(var1.squareDistanceTo(currentPath.getVectorFromIndex(theEntity, var4)) < var8)
			{
				currentPath.setCurrentPathIndex(var4 + 1);
			}
		}
		var4 = MathHelper.ceiling_float_int(theEntity.width);
		int var5 = (int) theEntity.height + 1;
		int var6 = var4;
		for(int var7 = var2 - 1; var7 >= currentPath.getCurrentPathIndex(); --var7)
		{
			if(isDirectPathBetweenPoints(var1, currentPath.getVectorFromIndex(theEntity, var7), var4, var5, var6))
			{
				currentPath.setCurrentPathIndex(var7);
				break;
			}
		}
		if(totalTicks - ticksAtLastPos > 100)
		{
			if(var1.squareDistanceTo(lastPosCheck) < 2.25D)
			{
				clearPathEntity();
			}
			ticksAtLastPos = totalTicks;
			lastPosCheck.xCoord = var1.xCoord;
			lastPosCheck.yCoord = var1.yCoord;
			lastPosCheck.zCoord = var1.zCoord;
		}
	}
	
	private void removeSunnyPath()
	{
		if(!worldObj.canBlockSeeTheSky(MathHelper.floor_double(theEntity.posX), (int) (theEntity.boundingBox.minY + 0.5D), MathHelper.floor_double(theEntity.posZ)))
		{
			for(int var1 = 0; var1 < currentPath.getCurrentPathLength(); ++var1)
			{
				PathPoint var2 = currentPath.getPathPointFromIndex(var1);
				if(worldObj.canBlockSeeTheSky(var2.xCoord, var2.yCoord, var2.zCoord))
				{
					currentPath.setCurrentPathLength(var1 - 1);
					return;
				}
			}
		}
	}
	
	public void setAvoidSun(boolean p_75504_1_)
	{
		noSunPathfind = p_75504_1_;
	}
	
	public void setAvoidsWater(boolean p_75491_1_)
	{
		avoidsWater = p_75491_1_;
	}
	
	public void setBreakDoors(boolean p_75498_1_)
	{
		canPassClosedWoodenDoors = p_75498_1_;
	}
	
	public void setCanSwim(boolean p_75495_1_)
	{
		canSwim = p_75495_1_;
	}
	
	public void setEnterDoors(boolean p_75490_1_)
	{
		canPassOpenWoodenDoors = p_75490_1_;
	}
	
	public boolean setPath(PathEntity p_75484_1_, float p_75484_2_)
	{
		if(p_75484_1_ == null)
		{
			currentPath = null;
			return false;
		} else
		{
			if(!p_75484_1_.isSamePath(currentPath))
			{
				currentPath = p_75484_1_;
			}
			if(noSunPathfind)
			{
				removeSunnyPath();
			}
			if(currentPath.getCurrentPathLength() == 0) return false;
			else
			{
				speed = p_75484_2_;
				Vec3 var3 = getEntityPosition();
				ticksAtLastPos = totalTicks;
				lastPosCheck.xCoord = var3.xCoord;
				lastPosCheck.yCoord = var3.yCoord;
				lastPosCheck.zCoord = var3.zCoord;
				return true;
			}
		}
	}
	
	public void setSpeed(float p_75489_1_)
	{
		speed = p_75489_1_;
	}
	
	public boolean tryMoveToEntityLiving(EntityLiving p_75497_1_, float p_75497_2_)
	{
		PathEntity var3 = getPathToEntityLiving(p_75497_1_);
		return var3 != null ? setPath(var3, p_75497_2_) : false;
	}
	
	public boolean tryMoveToXYZ(double p_75492_1_, double p_75492_3_, double p_75492_5_, float p_75492_7_)
	{
		PathEntity var8 = getPathToXYZ(MathHelper.floor_double(p_75492_1_), (int) p_75492_3_, MathHelper.floor_double(p_75492_5_));
		return setPath(var8, p_75492_7_);
	}
}
