package net.minecraft.src;

public class Tuple
{
	private Object first;
	private Object second;
	
	public Tuple(Object par1Obj, Object par2Obj)
	{
		first = par1Obj;
		second = par2Obj;
	}
	
	public Object getFirst()
	{
		return first;
	}
	
	public Object getSecond()
	{
		return second;
	}
}
