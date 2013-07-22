package net.minecraft.src;

public class StitcherException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	private final StitchHolder field_98149_a;
	
	public StitcherException(StitchHolder par1StitchHolder, String par2Str)
	{
		super(par2Str);
		field_98149_a = par1StitchHolder;
	}
}
