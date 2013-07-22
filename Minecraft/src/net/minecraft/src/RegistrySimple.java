package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

public class RegistrySimple implements IRegistry
{
	protected final Map registryObjects = func_111054_a();
	
	protected HashMap func_111054_a()
	{
		return Maps.newHashMap();
	}
	
	@Override public Object func_82594_a(Object par1Obj)
	{
		return registryObjects.get(par1Obj);
	}
	
	@Override public void putObject(Object par1Obj, Object par2Obj)
	{
		registryObjects.put(par1Obj, par2Obj);
	}
}
