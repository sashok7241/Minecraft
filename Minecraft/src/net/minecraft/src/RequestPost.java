package net.minecraft.src;

import java.io.OutputStream;

public class RequestPost extends Request
{
	private byte[] field_96373_c;
	
	public RequestPost(String p_i10027_1_, byte[] p_i10027_2_, int p_i10027_3_, int p_i10027_4_)
	{
		super(p_i10027_1_, p_i10027_3_, p_i10027_4_);
		field_96373_c = p_i10027_2_;
	}
	
	@Override public Request func_96359_e()
	{
		return func_96372_f();
	}
	
	public RequestPost func_96372_f()
	{
		try
		{
			field_96367_a.setDoInput(true);
			field_96367_a.setDoOutput(true);
			field_96367_a.setUseCaches(false);
			field_96367_a.setRequestMethod("POST");
			OutputStream var1 = field_96367_a.getOutputStream();
			var1.write(field_96373_c);
			var1.flush();
			return this;
		} catch(Exception var2)
		{
			throw new ExceptionMcoHttp("Failed URL: " + field_96365_b, var2);
		}
	}
}
