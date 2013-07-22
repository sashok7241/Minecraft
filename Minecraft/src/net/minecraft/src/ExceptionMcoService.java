package net.minecraft.src;

public class ExceptionMcoService extends Exception
{
	private static final long serialVersionUID = 1L;
	public final int field_96392_a;
	public final String field_96391_b;
	
	public ExceptionMcoService(int par1, String par2Str)
	{
		super(par2Str);
		field_96392_a = par1;
		field_96391_b = par2Str;
	}
}
