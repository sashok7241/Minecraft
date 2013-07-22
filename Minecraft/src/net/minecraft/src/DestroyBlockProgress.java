package net.minecraft.src;

public class DestroyBlockProgress
{
	private final int miningPlayerEntId;
	private final int partialBlockX;
	private final int partialBlockY;
	private final int partialBlockZ;
	private int partialBlockProgress;
	private int createdAtCloudUpdateTick;
	
	public DestroyBlockProgress(int par1, int par2, int par3, int par4)
	{
		miningPlayerEntId = par1;
		partialBlockX = par2;
		partialBlockY = par3;
		partialBlockZ = par4;
	}
	
	public int getCreationCloudUpdateTick()
	{
		return createdAtCloudUpdateTick;
	}
	
	public int getPartialBlockDamage()
	{
		return partialBlockProgress;
	}
	
	public int getPartialBlockX()
	{
		return partialBlockX;
	}
	
	public int getPartialBlockY()
	{
		return partialBlockY;
	}
	
	public int getPartialBlockZ()
	{
		return partialBlockZ;
	}
	
	public void setCloudUpdateTick(int par1)
	{
		createdAtCloudUpdateTick = par1;
	}
	
	public void setPartialBlockDamage(int par1)
	{
		if(par1 > 10)
		{
			par1 = 10;
		}
		partialBlockProgress = par1;
	}
}
