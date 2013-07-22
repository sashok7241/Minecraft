package net.minecraft.src;

import java.io.OutputStream;

public class RequestPut extends Request
{
	private byte[] field_96369_c;
	
	public RequestPut(String par1Str, byte[] par2ArrayOfByte, int par3, int par4)
	{
		super(par1Str, par3, par4);
		field_96369_c = par2ArrayOfByte;
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
