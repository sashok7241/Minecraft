package net.minecraft.src;

public class WatchableObject
{
	private final int objectType;
	private final int dataValueId;
	private Object watchedObject;
	private boolean watched;
	
	public WatchableObject(int p_i3451_1_, int p_i3451_2_, Object p_i3451_3_)
	{
		dataValueId = p_i3451_2_;
		watchedObject = p_i3451_3_;
		objectType = p_i3451_1_;
		watched = true;
	}
	
	public int getDataValueId()
	{
		return dataValueId;
	}
	
	public Object getObject()
	{
		return watchedObject;
	}
	
	public int getObjectType()
	{
		return objectType;
	}
	
	public boolean isWatched()
	{
		return watched;
	}
	
	public void setObject(Object p_75673_1_)
	{
		watchedObject = p_75673_1_;
	}
	
	public void setWatched(boolean p_75671_1_)
	{
		watched = p_75671_1_;
	}
	
	static boolean setWatchableObjectWatched(WatchableObject p_82711_0_, boolean p_82711_1_)
	{
		return p_82711_0_.watched = p_82711_1_;
	}
}
