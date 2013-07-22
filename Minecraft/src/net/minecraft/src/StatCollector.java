package net.minecraft.src;

public class StatCollector
{
	private static StringTranslate localizedName = StringTranslate.getInstance();
	
	public static boolean func_94522_b(String p_94522_0_)
	{
		return localizedName.containsTranslateKey(p_94522_0_);
	}
	
	public static String translateToLocal(String p_74838_0_)
	{
		return localizedName.translateKey(p_74838_0_);
	}
	
	public static String translateToLocalFormatted(String p_74837_0_, Object ... p_74837_1_)
	{
		return localizedName.translateKeyFormat(p_74837_0_, p_74837_1_);
	}
}
