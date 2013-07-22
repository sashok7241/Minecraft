package net.minecraft.src;

import java.io.OutputStream;

public class RequestPut extends Request
{
	private byte[] field_96369_c;
	
	public RequestPut(String p_i10029_1_, byte[] p_i10029_2_, int p_i10029_3_, int p_i10029_4_)
	{
		super(p_i10029_1_, p_i10029_3_, p_i10029_4_);
		field_96369_c = p_i10029_2_;
	}
	
	@Override public Request func_96359_e()
	{
		return func_96368_f();
	}
	
	public RequestPut func_96368_f()
	{
		try
		{
			field_96367_a.setDoOutput(true);
			field_96367_a.setDoInput(true);
			field_96367_a.setRequestMethod("PUT");
			OutputStream var1 = field_96367_a.getOutputStream();
			var1.write(field_96369_c);
			var1.flush();
			return this;
		} catch(Exception var2)
		{
			throw new ExceptionMcoHttp("Failed URL: " + field_96365_b, var2);
		}
	}
}
