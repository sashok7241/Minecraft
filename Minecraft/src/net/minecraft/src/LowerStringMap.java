package net.minecraft.src;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LowerStringMap implements Map
{
	private final Map internalMap = new LinkedHashMap();
	
	@Override public void clear()
	{
		internalMap.clear();
	}
	
	@Override public boolean containsKey(Object par1Obj)
	{
		return internalMap.containsKey(par1Obj.toString().toLowerCase());
	}
	
	@Override public boolean containsValue(Object par1Obj)
	{
		return internalMap.containsKey(par1Obj);
	}
	
	@Override public Set entrySet()
	{
		return internalMap.entrySet();
	}
	
	@Override public Object get(Object par1Obj)
	{
		return internalMap.get(par1Obj.toString().toLowerCase());
	}
	
	@Override public boolean isEmpty()
	{
		return internalMap.isEmpty();
	}
	
	@Override public Set keySet()
	{
		return internalMap.keySet();
	}
	
	@Override public Object put(Object par1Obj, Object par2Obj)
	{
		return putLower((String) par1Obj, par2Obj);
	}
	
	@Override public void putAll(Map par1Map)
	{
		Iterator var2 = par1Map.entrySet().iterator();
		while(var2.hasNext())
		{
			Entry var3 = (Entry) var2.next();
			putLower((String) var3.getKey(), var3.getValue());
		}
	}
	
	public Object putLower(String par1Str, Object par2Obj)
	{
		return internalMap.put(par1Str.toLowerCase(), par2Obj);
	}
	
	@Override public Object remove(Object par1Obj)
	{
		return internalMap.remove(par1Obj.toString().toLowerCase());
	}
	
	@Override public int size()
	{
		return internalMap.size();
	}
	
	@Override public Collection values()
	{
		return internalMap.values();
	}
}
