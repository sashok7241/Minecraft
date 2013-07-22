package net.minecraft.src;

import java.util.concurrent.Callable;

final class CallableBlockDataValue implements Callable
{
	final int field_85063_a;
	
	CallableBlockDataValue(int par1)
	{
		field_85063_a = par1;
	}
	
	@Override public Object call()
	{
		return callBlockDataValue();
	}
	
	public String callBlockDataValue()
	{
		if(field_85063_a < 0) return "Unknown? (Got " + field_85063_a + ")";
		else
		{
			String var1 = String.format("%4s", new Object[] { Integer.toBinaryString(field_85063_a) }).replace(" ", "0");
			return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { Integer.valueOf(field_85063_a), var1 });
		}
	}
}
