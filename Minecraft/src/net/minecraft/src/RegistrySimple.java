package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

public class RegistrySimple implements IRegistry
{
	protected final Map registryObjects = new HashMap();
	
	@Override public Object func_82594_a(Object p_82594_1_)
	{
		return registryObjects.get(p_82594_1_);
	}
	
	@Override public void putObject(Object p_82595_1_, Object p_82595_2_)
	{
		registryObjects.put(p_82595_1_, p_82595_2_);
	}
}
