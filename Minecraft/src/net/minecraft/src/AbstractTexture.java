package net.minecraft.src;

public abstract class AbstractTexture implements TextureObject
{
	protected int field_110553_a = -1;
	
	@Override public int func_110552_b()
	{
		if(field_110553_a == -1)
		{
			field_110553_a = TextureUtil.func_110996_a();
		}
		return field_110553_a;
	}
}
