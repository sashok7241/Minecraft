package net.minecraft.src;

public class MouseFilter
{
	private float field_76336_a;
	private float field_76334_b;
	private float field_76335_c;
	
	public float smooth(float par1, float par2)
	{
		field_76336_a += par1;
		par1 = (field_76336_a - field_76334_b) * par2;
		field_76335_c += (par1 - field_76335_c) * 0.5F;
		if(par1 > 0.0F && par1 > field_76335_c || par1 < 0.0F && par1 < field_76335_c)
		{
			par1 = field_76335_c;
		}
		field_76334_b += par1;
		return par1;
	}
}
