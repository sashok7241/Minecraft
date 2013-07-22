package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatAllowedCharacters
{
	public static final String allowedCharacters = getAllowedCharacters();
	public static final char[] allowedCharactersArray = new char[] { '/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
	
	public static String filerAllowedCharacters(String p_71565_0_)
	{
		StringBuilder var1 = new StringBuilder();
		char[] var2 = p_71565_0_.toCharArray();
		int var3 = var2.length;
		for(int var4 = 0; var4 < var3; ++var4)
		{
			char var5 = var2[var4];
			if(isAllowedCharacter(var5))
			{
				var1.append(var5);
			}
		}
		return var1.toString();
	}
	
	private static String getAllowedCharacters()
	{
		String var0 = "";
		try
		{
			BufferedReader var1 = new BufferedReader(new InputStreamReader(ChatAllowedCharacters.class.getResourceAsStream("/font.txt"), "UTF-8"));
			String var2 = "";
			while((var2 = var1.readLine()) != null)
			{
				if(!var2.startsWith("#"))
				{
					var0 = var0 + var2;
				}
			}
			var1.close();
		} catch(Exception var3)
		{
			;
		}
		return var0;
	}
	
	public static final boolean isAllowedCharacter(char p_71566_0_)
	{
		return p_71566_0_ != 167 && (allowedCharacters.indexOf(p_71566_0_) >= 0 || p_71566_0_ > 32);
	}
}
