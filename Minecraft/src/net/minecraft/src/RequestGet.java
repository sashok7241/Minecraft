package net.minecraft.src;

public class RequestGet extends Request
{
	public RequestGet(String par1Str, int par2, int par3)
	{
		super(par1Str, par2, par3);
	}
	
	@Override public Request func_96359_e()
	{
		return func_96371_f();
	}
	
	public RequestGet func_96371_f()
	{
		try
		{
			field_96367_a.setDoInput(true);
			field_96367_a.setDoOutput(true);
			field_96367_a.setUseCaches(false);
			field_96367_a.setRequestMethod("GET");
			return this;
		} catch(Exception var2)
		{
			throw new ExceptionMcoHttp("Failed URL: " + field_96365_b, var2);
		}
	}
}
