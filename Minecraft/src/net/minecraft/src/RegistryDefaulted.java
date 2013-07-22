package net.minecraft.src;

public class RegistryDefaulted extends RegistrySimple
{
	private final Object defaultObject;
	
	public RegistryDefaulted(Object p_i5026_1_)
	{
		defaultObject = p_i5026_1_;
	}
	
	@Override public Object func_82594_a(Object p_82594_1_)
	{
		Object var2 = super.func_82594_a(p_82594_1_);
		return var2 == null ? defaultObject : var2;
	}
}
