package net.minecraft.src;

public class ExceptionMcoService extends Exception
{
	private static final long serialVersionUID = 1L;
	public final int field_96392_a;
	public final String field_96391_b;
	public final int field_130097_c;
	
	public ExceptionMcoService(int par1, String par2Str, int par3)
	{
		super(par2Str);
		field_96392_a = par1;
		field_96391_b = par2Str;
		field_130097_c = par3;
	}
	
	@Override public String toString()
	{
		return field_130097_c != -1 ? "Realms ( ErrorCode: " + field_130097_c + " )" : "Realms: " + field_96391_b;
	}
}
