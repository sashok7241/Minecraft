package net.minecraft.src;


class SimpleReloadableResourceManagerINNER1 implements Function
{
	final SimpleReloadableResourceManager field_130076_a;
	
	SimpleReloadableResourceManagerINNER1(SimpleReloadableResourceManager par1SimpleReloadableResourceManager)
	{
		field_130076_a = par1SimpleReloadableResourceManager;
	}
	
	public Object apply(Object par1Obj)
	{
		return func_130075_a((ResourcePack) par1Obj);
	}
	
	public String func_130075_a(ResourcePack par1ResourcePack)
	{
		return par1ResourcePack.func_130077_b();
	}
}
