package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public enum EnumChatFormatting
{
	BLACK('0'), DARK_BLUE('1'), DARK_GREEN('2'), DARK_AQUA('3'), DARK_RED('4'), DARK_PURPLE('5'), GOLD('6'), GRAY('7'), DARK_GRAY('8'), BLUE('9'), GREEN('a'), AQUA('b'), RED('c'), LIGHT_PURPLE('d'), YELLOW('e'), WHITE('f'), OBFUSCATED('k', true), BOLD('l', true), STRIKETHROUGH('m', true), UNDERLINE('n', true), ITALIC('o', true), RESET('r');
	private static final Map field_96321_w = new HashMap();
	private static final Map field_96331_x = new HashMap();
	private static final Pattern field_96330_y = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]");
	private final char field_96329_z;
	private final boolean field_96303_A;
	private final String field_96304_B;
	
	private EnumChatFormatting(char p_i10000_3_)
	{
		this(p_i10000_3_, false);
	}
	
	private EnumChatFormatting(char p_i10001_3_, boolean p_i10001_4_)
	{
		field_96329_z = p_i10001_3_;
		field_96303_A = p_i10001_4_;
		field_96304_B = "\u00a7" + p_i10001_3_;
	}
	
	public String func_96297_d()
	{
		return name().toLowerCase();
	}
	
	public char func_96298_a()
	{
		return field_96329_z;
	}
	
	public boolean func_96301_b()
	{
		return field_96303_A;
	}
	
	public boolean func_96302_c()
	{
		return !field_96303_A && this != RESET;
	}
	
	@Override public String toString()
	{
		return field_96304_B;
	}
	
	public static Collection func_96296_a(boolean p_96296_0_, boolean p_96296_1_)
	{
		ArrayList var2 = new ArrayList();
		EnumChatFormatting[] var3 = values();
		int var4 = var3.length;
		for(int var5 = 0; var5 < var4; ++var5)
		{
			EnumChatFormatting var6 = var3[var5];
			if((!var6.func_96302_c() || p_96296_0_) && (!var6.func_96301_b() || p_96296_1_))
			{
				var2.add(var6.func_96297_d());
			}
		}
		return var2;
	}
	
	public static EnumChatFormatting func_96300_b(String p_96300_0_)
	{
		return p_96300_0_ == null ? null : (EnumChatFormatting) field_96331_x.get(p_96300_0_.toLowerCase());
	}
	
	static
	{
		EnumChatFormatting[] var0 = values();
		int var1 = var0.length;
		for(int var2 = 0; var2 < var1; ++var2)
		{
			EnumChatFormatting var3 = var0[var2];
			field_96321_w.put(Character.valueOf(var3.func_96298_a()), var3);
			field_96331_x.put(var3.func_96297_d(), var3);
		}
	}
}
