package net.minecraft.src;

class LongHashMapEntry
{
	final long key;
	Object value;
	LongHashMapEntry nextEntry;
	final int hash;
	
	LongHashMapEntry(int p_i3420_1_, long p_i3420_2_, Object p_i3420_4_, LongHashMapEntry p_i3420_5_)
	{
		value = p_i3420_4_;
		nextEntry = p_i3420_5_;
		key = p_i3420_2_;
		hash = p_i3420_1_;
	}
	
	@Override public final boolean equals(Object p_equals_1_)
	{
		if(!(p_equals_1_ instanceof LongHashMapEntry)) return false;
		else
		{
			LongHashMapEntry var2 = (LongHashMapEntry) p_equals_1_;
			Long var3 = Long.valueOf(getKey());
			Long var4 = Long.valueOf(var2.getKey());
			if(var3 == var4 || var3 != null && var3.equals(var4))
			{
				Object var5 = getValue();
				Object var6 = var2.getValue();
				if(var5 == var6 || var5 != null && var5.equals(var6)) return true;
			}
			return false;
		}
	}
	
	public final long getKey()
	{
		return key;
	}
	
	public final Object getValue()
	{
		return value;
	}
	
	@Override public final int hashCode()
	{
		return LongHashMap.getHashCode(key);
	}
	
	@Override public final String toString()
	{
		return getKey() + "=" + getValue();
	}
}
