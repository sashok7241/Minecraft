package net.minecraft.src;

public class DestroyBlockProgress
{
	private final int miningPlayerEntId;
	private final int partialBlockX;
	private final int partialBlockY;
	private final int partialBlockZ;
	private int partialBlockProgress;
	private int createdAtCloudUpdateTick;
	
	public DestroyBlockProgress(int p_i3385_1_, int p_i3385_2_, int p_i3385_3_, int p_i3385_4_)
	{
		miningPlayerEntId = p_i3385_1_;
		partialBlockX = p_i3385_2_;
		partialBlockY = p_i3385_3_;
		partialBlockZ = p_i3385_4_;
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
