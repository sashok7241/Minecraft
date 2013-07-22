package net.minecraft.src;

import java.util.Random;

public class SpiderEffectsGroupData implements EntityLivingData
{
	public int field_111105_a;
	
	public void func_111104_a(Random par1Random)
	{
		int var2 = par1Random.nextInt(5);
		if(var2 <= 1)
		{
			field_111105_a = Potion.moveSpeed.id;
		} else if(var2 <= 2)
		{
			field_111105_a = Potion.damageBoost.id;
		} else if(var2 <= 3)
		{
			field_111105_a = Potion.regeneration.id;
		} else if(var2 <= 4)
		{
			field_111105_a = Potion.invisibility.id;
		}
	}
}
