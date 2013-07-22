package net.minecraft.src;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public final class MainProxyAuthenticator extends Authenticator
{
	final String field_111237_a;
	final String field_111236_b;
	
	public MainProxyAuthenticator(String par1Str, String par2Str)
	{
		field_111237_a = par1Str;
		field_111236_b = par2Str;
	}
	
	@Override protected PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(field_111237_a, field_111236_b.toCharArray());
	}
}
