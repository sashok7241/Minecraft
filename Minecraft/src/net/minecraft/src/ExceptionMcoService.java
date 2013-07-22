package net.minecraft.src;

public class ExceptionMcoService extends Exception
{
	private static final long serialVersionUID = 1L;
	public final int field_96392_a;
	public final String field_96391_b;
	
	public ExceptionMcoService(int p_i10025_1_, String p_i10025_2_)
	{
		super(p_i10025_2_);
		field_96392_a = p_i10025_1_;
		field_96391_b = p_i10025_2_;
	}
}
