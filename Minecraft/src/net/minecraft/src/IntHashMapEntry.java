package net.minecraft.src;

class IntHashMapEntry
{
	final int hashEntry;
	Object valueEntry;
	IntHashMapEntry nextEntry;
	final int slotHash;
	
	IntHashMapEntry(int p_i3419_1_, int p_i3419_2_, Object p_i3419_3_, IntHashMapEntry p_i3419_4_)
	{
		valueEntry = p_i3419_3_;
		nextEntry = p_i3419_4_;
		hashEntry = p_i3419_2_;
		slotHash = p_i3419_1_;
	}
	
	@Override public final boolean equals(Object p_equals_1_)
	{
		if(!(p_equals_1_ instanceof IntHashMapEntry)) return false;
		else
		{
			IntHashMapEntry var2 = (IntHashMapEntry) p_equals_1_;
			Integer var3 = Integer.valueOf(getHash());
			Integer var4 = Integer.valueOf(var2.getHash());
			if(var3 == var4 || var3 != null && var3.equals(var4))
			{
				Object var5 = getValue();
				Object var6 = var2.getValue();
				if(var5 == var6 || var5 != null && var5.equals(var6)) return true;
			}
			return false;
		}
	}
	
	public final int getHash()
	{
		return hashEntry;
	}
	
	public final Object getValue()
	{
		return valueEntry;
	}
	
	@Override public final int hashCode()
	{
		return IntHashMap.getHash(hashEntry);
	}
	
	@Override public final String toString()
	{
		return getHash() + "=" + getValue();
	}
}
