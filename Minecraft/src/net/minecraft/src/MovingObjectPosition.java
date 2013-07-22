package net.minecraft.src;

public class MovingObjectPosition
{
	public EnumMovingObjectType typeOfHit;
	public int blockX;
	public int blockY;
	public int blockZ;
	public int sideHit;
	public Vec3 hitVec;
	public Entity entityHit;
	
	public MovingObjectPosition(Entity p_i4033_1_)
	{
		typeOfHit = EnumMovingObjectType.ENTITY;
		entityHit = p_i4033_1_;
		hitVec = p_i4033_1_.worldObj.getWorldVec3Pool().getVecFromPool(p_i4033_1_.posX, p_i4033_1_.posY, p_i4033_1_.posZ);
	}
	
	public MovingObjectPosition(int p_i4032_1_, int p_i4032_2_, int p_i4032_3_, int p_i4032_4_, Vec3 p_i4032_5_)
	{
		typeOfHit = EnumMovingObjectType.TILE;
		blockX = p_i4032_1_;
		blockY = p_i4032_2_;
		blockZ = p_i4032_3_;
		sideHit = p_i4032_4_;
		hitVec = p_i4032_5_.myVec3LocalPool.getVecFromPool(p_i4032_5_.xCoord, p_i4032_5_.yCoord, p_i4032_5_.zCoord);
	}
}
