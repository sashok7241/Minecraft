package net.minecraft.src;

final class StatTypeFloat implements IStatType
{
	@Override public String format(int par1)
	{
		return StatBase.getDecimalFormat().format(par1 * 0.1D);
	}
}
