package net.minecraft.src;

public class LongHashMap
{
	private transient LongHashMapEntry[] hashArray = new LongHashMapEntry[16];
	private transient int numHashElements;
	private int capacity = 12;
	private final float percentUseable = 0.75F;
	private transient volatile int modCount;
	
	public void add(long p_76163_1_, Object p_76163_3_)
	{
		int var4 = getHashedKey(p_76163_1_);
		int var5 = getHashIndex(var4, hashArray.length);
		for(LongHashMapEntry var6 = hashArray[var5]; var6 != null; var6 = var6.nextEntry)
		{
			if(var6.key == p_76163_1_)
			{
				var6.value = p_76163_3_;
				return;
			}
		}
		++modCount;
		createKey(var4, p_76163_1_, p_76163_3_, var5);
	}
	
	public boolean containsItem(long p_76161_1_)
	{
		return getEntry(p_76161_1_) != null;
	}
	
	private void copyHashTableTo(LongHashMapEntry[] p_76154_1_)
	{
		LongHashMapEntry[] var2 = hashArray;
		int var3 = p_76154_1_.length;
		for(int var4 = 0; var4 < var2.length; ++var4)
		{
			LongHashMapEntry var5 = var2[var4];
			if(var5 != null)
			{
				var2[var4] = null;
				LongHashMapEntry var6;
				do
				{
					var6 = var5.nextEntry;
					int var7 = getHashIndex(var5.hash, var3);
					var5.nextEntry = p_76154_1_[var7];
					p_76154_1_[var7] = var5;
					var5 = var6;
				} while(var6 != null);
			}
		}
	}
	
	private void createKey(int p_76156_1_, long p_76156_2_, Object p_76156_4_, int p_76156_5_)
	{
		LongHashMapEntry var6 = hashArray[p_76156_5_];
		hashArray[p_76156_5_] = new LongHashMapEntry(p_76156_1_, p_76156_2_, p_76156_4_, var6);
		if(numHashElements++ >= capacity)
		{
			resizeTable(2 * hashArray.length);
		}
	}
	
	final LongHashMapEntry getEntry(long p_76160_1_)
	{
		int var3 = getHashedKey(p_76160_1_);
		for(LongHashMapEntry var4 = hashArray[getHashIndex(var3, hashArray.length)]; var4 != null; var4 = var4.nextEntry)
		{
			if(var4.key == p_76160_1_) return var4;
		}
		return null;
	}
	
	public int getNumHashElements()
	{
		return numHashElements;
	}
	
	public Object getValueByKey(long p_76164_1_)
	{
		int var3 = getHashedKey(p_76164_1_);
		for(LongHashMapEntry var4 = hashArray[getHashIndex(var3, hashArray.length)]; var4 != null; var4 = var4.nextEntry)
		{
			if(var4.key == p_76164_1_) return var4.value;
		}
		return null;
	}
	
	public Object remove(long p_76159_1_)
	{
		LongHashMapEntry var3 = removeKey(p_76159_1_);
		return var3 == null ? null : var3.value;
	}
	
	final LongHashMapEntry removeKey(long p_76152_1_)
	{
		int var3 = getHashedKey(p_76152_1_);
		int var4 = getHashIndex(var3, hashArray.length);
		LongHashMapEntry var5 = hashArray[var4];
		LongHashMapEntry var6;
		LongHashMapEntry var7;
		for(var6 = var5; var6 != null; var6 = var7)
		{
			var7 = var6.nextEntry;
			if(var6.key == p_76152_1_)
			{
				++modCount;
				--numHashElements;
				if(var5 == var6)
				{
					hashArray[var4] = var7;
				} else
				{
					var5.nextEntry = var7;
				}
				return var6;
			}
			var5 = var6;
		}
		return var6;
	}
	
	private void resizeTable(int p_76153_1_)
	{
		LongHashMapEntry[] var2 = hashArray;
		int var3 = var2.length;
		if(var3 == 1073741824)
		{
			capacity = Integer.MAX_VALUE;
		} else
		{
			LongHashMapEntry[] var4 = new LongHashMapEntry[p_76153_1_];
			copyHashTableTo(var4);
			hashArray = var4;
			capacity = (int) (p_76153_1_ * percentUseable);
		}
	}
	
	static int getHashCode(long p_76151_0_)
	{
		return getHashedKey(p_76151_0_);
	}
	
	private static int getHashedKey(long p_76155_0_)
	{
		return hash((int) (p_76155_0_ ^ p_76155_0_ >>> 32));
	}
	
	private static int getHashIndex(int p_76158_0_, int p_76158_1_)
	{
		return p_76158_0_ & p_76158_1_ - 1;
	}
	
	private static int hash(int p_76157_0_)
	{
		p_76157_0_ ^= p_76157_0_ >>> 20 ^ p_76157_0_ >>> 12;
		return p_76157_0_ ^ p_76157_0_ >>> 7 ^ p_76157_0_ >>> 4;
	}
}
