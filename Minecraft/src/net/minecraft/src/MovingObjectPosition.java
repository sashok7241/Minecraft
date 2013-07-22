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
	
	public MovingObjectPosition(Entity par1Entity)
	{
		typeOfHit = EnumMovingObjectType.ENTITY;
		entityHit = par1Entity;
		hitVec = par1Entity.worldObj.getWorldVec3Pool().getVecFromPool(par1Entity.posX, par1Entity.posY, par1Entity.posZ);
	}
	
	public MovingObjectPosition(int par1, int par2, int par3, int par4, Vec3 par5Vec3)
	{
		typeOfHit = EnumMovingObjectType.TILE;
		blockX = par1;
		blockY = par2;
		blockZ = par3;
		sideHit = par4;
		hitVec = par5Vec3.myVec3LocalPool.getVecFromPool(par5Vec3.xCoord, par5Vec3.yCoord, par5Vec3.zCoord);
	}
}
