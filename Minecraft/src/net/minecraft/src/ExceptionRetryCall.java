package net.minecraft.src;

public class ExceptionRetryCall extends ExceptionMcoService
{
	private static final long serialVersionUID = 1L;
	public final int field_96393_c;
	
	public ExceptionRetryCall(int par1)
	{
		super(503, "Retry operation", -1);
		field_96393_c = par1;
	}
}
