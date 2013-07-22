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
	
	@Override public boolean containsKey(Object p_containsKey_1_)
	{
		return internalMap.containsKey(p_containsKey_1_.toString().toLowerCase());
	}
	
	@Override public boolean containsValue(Object p_containsValue_1_)
	{
		return internalMap.containsKey(p_containsValue_1_);
	}
	
	@Override public Set entrySet()
	{
		return internalMap.entrySet();
	}
	
	@Override public Object get(Object p_get_1_)
	{
		return internalMap.get(p_get_1_.toString().toLowerCase());
	}
	
	@Override public boolean isEmpty()
	{
		return internalMap.isEmpty();
	}
	
	@Override public Set keySet()
	{
		return internalMap.keySet();
	}
	
	@Override public Object put(Object p_put_1_, Object p_put_2_)
	{
		return putLower((String) p_put_1_, p_put_2_);
	}
	
	@Override public void putAll(Map p_putAll_1_)
	{
		Iterator var2 = p_putAll_1_.entrySet().iterator();
		while(var2.hasNext())
		{
			Entry var3 = (Entry) var2.next();
			putLower((String) var3.getKey(), var3.getValue());
		}
	}
	
	public Object putLower(String p_76116_1_, Object p_76116_2_)
	{
		return internalMap.put(p_76116_1_.toLowerCase(), p_76116_2_);
	}
	
	@Override public Object remove(Object p_remove_1_)
	{
		return internalMap.remove(p_remove_1_.toString().toLowerCase());
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
