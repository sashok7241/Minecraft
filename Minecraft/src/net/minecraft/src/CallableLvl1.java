package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLvl1 implements Callable
{
	final int field_85179_a;
	final World theWorld;
	
	CallableLvl1(World par1World, int par2)
	{
		theWorld = par1World;
		field_85179_a = par2;
	}
	
	@Override public Object call()
	{
		return getWorldEntitiesAsString();
	}
	
	public String getWorldEntitiesAsString()
	{
		try
		{
			return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(field_85179_a), Block.blocksList[field_85179_a].getUnlocalizedName(), Block.blocksList[field_85179_a].getClass().getCanonicalName() });
		} catch(Throwable var2)
		{
			return "ID #" + field_85179_a;
		}
	}
}
