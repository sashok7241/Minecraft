package net.minecraft.src;

public class RequestDelete extends Request
{
	public RequestDelete(String par1Str, int par2, int par3)
	{
		super(par1Str, par2, par3);
	}
	
	@Override public Request func_96359_e()
	{
		return func_96370_f();
	}
	
	public RequestDelete func_96370_f()
	{
		try
		{
			field_96367_a.setDoOutput(true);
			field_96367_a.setRequestMethod("DELETE");
			field_96367_a.connect();
			return this;
		} catch(Exception var2)
		{
			throw new ExceptionMcoHttp("Failed URL: " + field_96365_b, var2);
		}
	}
}
