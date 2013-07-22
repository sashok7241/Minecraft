package net.minecraft.src;

public class RequestGet extends Request
{
	public RequestGet(String p_i10030_1_, int p_i10030_2_, int p_i10030_3_)
	{
		super(p_i10030_1_, p_i10030_2_, p_i10030_3_);
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
