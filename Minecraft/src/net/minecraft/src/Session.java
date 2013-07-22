package net.minecraft.src;

public class Session
{
	private final String username;
	private final String sessionId;
	
	public Session(String par1Str, String par2Str)
	{
		username = par1Str;
		sessionId = par2Str;
	}
	
	public String func_111285_a()
	{
		return username;
	}
	
	public String func_111286_b()
	{
		return sessionId;
	}
}
