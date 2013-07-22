package net.minecraft.src;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class Request
{
	protected HttpURLConnection field_96367_a;
	private boolean field_96366_c;
	protected String field_96365_b;
	
	public Request(String par1Str, int par2, int par3)
	{
		try
		{
			field_96365_b = par1Str;
			field_96367_a = (HttpURLConnection) new URL(par1Str).openConnection();
			field_96367_a.setConnectTimeout(par2);
			field_96367_a.setReadTimeout(par3);
		} catch(Exception var5)
		{
			throw new ExceptionMcoHttp("Failed URL: " + par1Str, var5);
		}
	}
	
	public void func_100006_a(String par1Str, String par2Str)
	{
		String var3 = field_96367_a.getRequestProperty("Cookie");
		if(var3 == null)
		{
			field_96367_a.setRequestProperty("Cookie", par1Str + "=" + par2Str);
		} else
		{
			field_96367_a.setRequestProperty("Cookie", var3 + ";" + par1Str + "=" + par2Str);
		}
	}
	
	private String func_96352_a(InputStream par1InputStream) throws IOException
	{
		if(par1InputStream == null) throw new IllegalArgumentException("input stream cannot be null");
		else
		{
			StringBuilder var2 = new StringBuilder();
			for(int var3 = par1InputStream.read(); var3 != -1; var3 = par1InputStream.read())
			{
				var2.append((char) var3);
			}
			return var2.toString();
		}
	}
	
	protected Request func_96354_d()
	{
		if(!field_96366_c)
		{
			Request var1 = func_96359_e();
			field_96366_c = true;
			return var1;
		} else return this;
	}
	
	protected abstract Request func_96359_e();
	
	private void func_96360_f()
	{
		byte[] var1 = new byte[1024];
		InputStream var3;
		try
		{
			boolean var2 = false;
			var3 = field_96367_a.getInputStream();
			while(true)
			{
				if(var3.read(var1) <= 0)
				{
					var3.close();
					break;
				}
			}
		} catch(Exception var6)
		{
			try
			{
				var3 = field_96367_a.getErrorStream();
				boolean var4 = false;
				while(true)
				{
					if(var3.read(var1) <= 0)
					{
						var3.close();
						break;
					}
				}
			} catch(IOException var5)
			{
				;
			}
		}
	}
	
	public int func_96362_a()
	{
		try
		{
			func_96354_d();
			return field_96367_a.getResponseCode();
		} catch(Exception var2)
		{
			throw new ExceptionMcoHttp("Failed URL: " + field_96365_b, var2);
		}
	}
	
	public String func_96364_c()
	{
		try
		{
			func_96354_d();
			String var1 = func_96362_a() >= 400 ? func_96352_a(field_96367_a.getErrorStream()) : func_96352_a(field_96367_a.getInputStream());
			func_96360_f();
			return var1;
		} catch(IOException var2)
		{
			throw new ExceptionMcoHttp("Failed URL: " + field_96365_b, var2);
		}
	}
	
	public McoOption func_98175_b()
	{
		String var1 = field_96367_a.getHeaderField("Set-Cookie");
		if(var1 != null)
		{
			String var2 = var1.substring(0, var1.indexOf("="));
			String var3 = var1.substring(var1.indexOf("=") + 1, var1.indexOf(";"));
			return McoOption.func_98153_a(McoPair.func_98157_a(var2, var3));
		} else return McoOption.func_98154_b();
	}
	
	public static Request func_104064_a(String par0Str, String par1Str, int par2, int par3)
	{
		return new RequestPost(par0Str, par1Str.getBytes(), par2, par3);
	}
	
	public static Request func_96353_a(String par0Str, String par1Str, int par2, int par3)
	{
		return new RequestPut(par0Str, par1Str.getBytes(), par2, par3);
	}
	
	public static Request func_96355_b(String par0Str)
	{
		return new RequestDelete(par0Str, 5000, 10000);
	}
	
	public static Request func_96358_a(String par0Str)
	{
		return new RequestGet(par0Str, 5000, 10000);
	}
	
	public static Request func_96361_b(String par0Str, String par1Str)
	{
		return new RequestPost(par0Str, par1Str.getBytes(), 5000, 10000);
	}
	
	public static Request func_96363_c(String par0Str, String par1Str)
	{
		return new RequestPut(par0Str, par1Str.getBytes(), 5000, 10000);
	}
}
