package net.minecraft.src;

public abstract class WorldSavedData
{
	public final String mapName;
	private boolean dirty;
	
	public WorldSavedData(String par1Str)
	{
		mapName = par1Str;
	}
	
	public boolean isDirty()
	{
		return dirty;
	}
	
	public void markDirty()
	{
		setDirty(true);
	}
	
	public abstract void readFromNBT(NBTTagCompound var1);
	
	public void setDirty(boolean par1)
	{
		dirty = par1;
	}
	
	public abstract void writeToNBT(NBTTagCompound var1);
}
