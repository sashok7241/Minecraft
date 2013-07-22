package net.minecraft.src;

import java.util.HashSet;
import java.util.Set;

public class IntHashMap
{
	private transient IntHashMapEntry[] slots = new IntHashMapEntry[16];
	private transient int count;
	private int threshold = 12;
	private final float growFactor = 0.75F;
	private transient volatile int versionStamp;
	private Set keySet = new HashSet();
	
	public void addKey(int p_76038_1_, Object p_76038_2_)
	{
		keySet.add(Integer.valueOf(p_76038_1_));
		int var3 = computeHash(p_76038_1_);
		int var4 = getSlotIndex(var3, slots.length);
		for(IntHashMapEntry var5 = slots[var4]; var5 != null; var5 = var5.nextEntry)
		{
			if(var5.hashEntry == p_76038_1_)
			{
				var5.valueEntry = p_76038_2_;
				return;
			}
		}
		++versionStamp;
		insert(var3, p_76038_1_, p_76038_2_, var4);
	}
	
	public void clearMap()
	{
		++versionStamp;
		IntHashMapEntry[] var1 = slots;
		for(int var2 = 0; var2 < var1.length; ++var2)
		{
			var1[var2] = null;
		}
		count = 0;
	}
	
	public boolean containsItem(int p_76037_1_)
	{
		return lookupEntry(p_76037_1_) != null;
	}
	
	private void copyTo(IntHashMapEntry[] p_76048_1_)
	{
		IntHashMapEntry[] var2 = slots;
		int var3 = p_76048_1_.length;
		for(int var4 = 0; var4 < var2.length; ++var4)
		{
			IntHashMapEntry var5 = var2[var4];
			if(var5 != null)
			{
				var2[var4] = null;
				IntHashMapEntry var6;
				do
				{
					var6 = var5.nextEntry;
					int var7 = getSlotIndex(var5.slotHash, var3);
					var5.nextEntry = p_76048_1_[var7];
					p_76048_1_[var7] = var5;
					var5 = var6;
				} while(var6 != null);
			}
		}
	}
	
	public Set getKeySet()
	{
		return keySet;
	}
	
	private void grow(int p_76047_1_)
	{
		IntHashMapEntry[] var2 = slots;
		int var3 = var2.length;
		if(var3 == 1073741824)
		{
			threshold = Integer.MAX_VALUE;
		} else
		{
			IntHashMapEntry[] var4 = new IntHashMapEntry[p_76047_1_];
			copyTo(var4);
			slots = var4;
			threshold = (int) (p_76047_1_ * growFactor);
		}
	}
	
	private void insert(int p_76040_1_, int p_76040_2_, Object p_76040_3_, int p_76040_4_)
	{
		IntHashMapEntry var5 = slots[p_76040_4_];
		slots[p_76040_4_] = new IntHashMapEntry(p_76040_1_, p_76040_2_, p_76040_3_, var5);
		if(count++ >= threshold)
		{
			grow(2 * slots.length);
		}
	}
	
	public Object lookup(int p_76041_1_)
	{
		int var2 = computeHash(p_76041_1_);
		for(IntHashMapEntry var3 = slots[getSlotIndex(var2, slots.length)]; var3 != null; var3 = var3.nextEntry)
		{
			if(var3.hashEntry == p_76041_1_) return var3.valueEntry;
		}
		return null;
	}
	
	final IntHashMapEntry lookupEntry(int p_76045_1_)
	{
		int var2 = computeHash(p_76045_1_);
		for(IntHashMapEntry var3 = slots[getSlotIndex(var2, slots.length)]; var3 != null; var3 = var3.nextEntry)
		{
			if(var3.hashEntry == p_76045_1_) return var3;
		}
		return null;
	}
	
	final IntHashMapEntry removeEntry(int p_76036_1_)
	{
		int var2 = computeHash(p_76036_1_);
		int var3 = getSlotIndex(var2, slots.length);
		IntHashMapEntry var4 = slots[var3];
		IntHashMapEntry var5;
		IntHashMapEntry var6;
		for(var5 = var4; var5 != null; var5 = var6)
		{
			var6 = var5.nextEntry;
			if(var5.hashEntry == p_76036_1_)
			{
				++versionStamp;
				--count;
				if(var4 == var5)
				{
					slots[var3] = var6;
				} else
				{
					var4.nextEntry = var6;
				}
				return var5;
			}
			var4 = var5;
		}
		return var5;
	}
	
	public Object removeObject(int p_76049_1_)
	{
		keySet.remove(Integer.valueOf(p_76049_1_));
		IntHashMapEntry var2 = removeEntry(p_76049_1_);
		return var2 == null ? null : var2.valueEntry;
	}
	
	private static int computeHash(int p_76044_0_)
	{
		p_76044_0_ ^= p_76044_0_ >>> 20 ^ p_76044_0_ >>> 12;
		return p_76044_0_ ^ p_76044_0_ >>> 7 ^ p_76044_0_ >>> 4;
	}
	
	static int getHash(int p_76042_0_)
	{
		return computeHash(p_76042_0_);
	}
	
	private static int getSlotIndex(int p_76043_0_, int p_76043_1_)
	{
		return p_76043_0_ & p_76043_1_ - 1;
	}
}
