package net.minecraft.src;

class LongHashMapEntry
{
	final long key;
	Object value;
	LongHashMapEntry nextEntry;
	final int hash;
	
	LongHashMapEntry(int par1, long par2, Object par4Obj, LongHashMapEntry par5LongHashMapEntry)
	{
		value = par4Obj;
		nextEntry = par5LongHashMapEntry;
		key = par2;
		hash = par1;
	}
	
	@Override public final boolean equals(Object par1Obj)
	{
		if(!(par1Obj instanceof LongHashMapEntry)) return false;
		else
		{
			LongHashMapEntry var2 = (LongHashMapEntry) par1Obj;
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
