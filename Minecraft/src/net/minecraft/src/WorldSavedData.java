package net.minecraft.src;

public abstract class WorldSavedData
{
	public final String mapName;
	private boolean dirty;
	
	public WorldSavedData(String p_i3907_1_)
	{
		mapName = p_i3907_1_;
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
	
	public void setDirty(boolean p_76186_1_)
	{
		dirty = p_76186_1_;
	}
	
	public abstract void writeToNBT(NBTTagCompound var1);
}
