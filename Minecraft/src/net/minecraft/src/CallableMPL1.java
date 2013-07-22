package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMPL1 implements Callable
{
	final WorldClient theWorldClient;
	
	CallableMPL1(WorldClient p_i3098_1_)
	{
		theWorldClient = p_i3098_1_;
	}
	
	@Override public Object call()
	{
		return getEntityCountAndList();
	}
	
	public String getEntityCountAndList()
	{
		return WorldClient.getEntityList(theWorldClient).size() + " total; " + WorldClient.getEntityList(theWorldClient).toString();
	}
}
