package net.minecraft.src;

public class VillageDoorInfo
{
	public final int posX;
	public final int posY;
	public final int posZ;
	public final int insideDirectionX;
	public final int insideDirectionZ;
	public int lastActivityTimestamp;
	public boolean isDetachedFromVillageFlag = false;
	private int doorOpeningRestrictionCounter = 0;
	
	public VillageDoorInfo(int p_i3509_1_, int p_i3509_2_, int p_i3509_3_, int p_i3509_4_, int p_i3509_5_, int p_i3509_6_)
	{
		posX = p_i3509_1_;
		posY = p_i3509_2_;
		posZ = p_i3509_3_;
		insideDirectionX = p_i3509_4_;
		insideDirectionZ = p_i3509_5_;
		lastActivityTimestamp = p_i3509_6_;
	}
	
	public int getDistanceSquared(int p_75474_1_, int p_75474_2_, int p_75474_3_)
	{
		int var4 = p_75474_1_ - posX;
		int var5 = p_75474_2_ - posY;
		int var6 = p_75474_3_ - posZ;
		return var4 * var4 + var5 * var5 + var6 * var6;
	}
	
	public int getDoorOpeningRestrictionCounter()
	{
		return doorOpeningRestrictionCounter;
	}
	
	public int getInsideDistanceSquare(int p_75469_1_, int p_75469_2_, int p_75469_3_)
	{
		int var4 = p_75469_1_ - posX - insideDirectionX;
		int var5 = p_75469_2_ - posY;
		int var6 = p_75469_3_ - posZ - insideDirectionZ;
		return var4 * var4 + var5 * var5 + var6 * var6;
	}
	
	public int getInsidePosX()
	{
		return posX + insideDirectionX;
	}
	
	public int getInsidePosY()
	{
		return posY;
	}
	
	public int getInsidePosZ()
	{
		return posZ + insideDirectionZ;
	}
	
	public void incrementDoorOpeningRestrictionCounter()
	{
		++doorOpeningRestrictionCounter;
	}
	
	public boolean isInside(int p_75467_1_, int p_75467_2_)
	{
		int var3 = p_75467_1_ - posX;
		int var4 = p_75467_2_ - posZ;
		return var3 * insideDirectionX + var4 * insideDirectionZ >= 0;
	}
	
	public void resetDoorOpeningRestrictionCounter()
	{
		doorOpeningRestrictionCounter = 0;
	}
}
