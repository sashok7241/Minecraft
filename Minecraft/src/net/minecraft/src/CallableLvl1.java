package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLvl1 implements Callable
{
	final int field_85179_a;
	final World theWorld;
	
	CallableLvl1(World p_i6814_1_, int p_i6814_2_)
	{
		theWorld = p_i6814_1_;
		field_85179_a = p_i6814_2_;
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
