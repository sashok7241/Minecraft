package net.minecraft.src;

import java.util.Random;

public class EnchantmentNameParts
{
	public static final EnchantmentNameParts instance = new EnchantmentNameParts();
	private Random rand = new Random();
	private String[] wordList = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale ".split(" ");
	
	public String generateRandomEnchantName()
	{
		int var1 = rand.nextInt(2) + 3;
		String var2 = "";
		for(int var3 = 0; var3 < var1; ++var3)
		{
			if(var3 > 0)
			{
				var2 = var2 + " ";
			}
			var2 = var2 + wordList[rand.nextInt(wordList.length)];
		}
		return var2;
	}
	
	public void setRandSeed(long par1)
	{
		rand.setSeed(par1);
	}
}
