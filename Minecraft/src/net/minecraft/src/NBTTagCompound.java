package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NBTTagCompound extends NBTBase
{
	private Map tagMap = new HashMap();
	
	public NBTTagCompound()
	{
		super("");
	}
	
	public NBTTagCompound(String p_i3265_1_)
	{
		super(p_i3265_1_);
	}
	
	@Override public NBTBase copy()
	{
		NBTTagCompound var1 = new NBTTagCompound(getName());
		Iterator var2 = tagMap.keySet().iterator();
		while(var2.hasNext())
		{
			String var3 = (String) var2.next();
			var1.setTag(var3, ((NBTBase) tagMap.get(var3)).copy());
		}
		return var1;
	}
	
	private CrashReport createCrashReport(String p_82581_1_, int p_82581_2_, ClassCastException p_82581_3_)
	{
		CrashReport var4 = CrashReport.makeCrashReport(p_82581_3_, "Reading NBT data");
		CrashReportCategory var5 = var4.makeCategoryDepth("Corrupt NBT tag", 1);
		var5.addCrashSectionCallable("Tag type found", new CallableTagCompound1(this, p_82581_1_));
		var5.addCrashSectionCallable("Tag type expected", new CallableTagCompound2(this, p_82581_2_));
		var5.addCrashSection("Tag name", p_82581_1_);
		if(getName() != null && getName().length() > 0)
		{
			var5.addCrashSection("Tag parent", getName());
		}
		return var4;
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(super.equals(p_equals_1_))
		{
			NBTTagCompound var2 = (NBTTagCompound) p_equals_1_;
			return tagMap.entrySet().equals(var2.tagMap.entrySet());
		} else return false;
	}
	
	public boolean getBoolean(String p_74767_1_)
	{
		return getByte(p_74767_1_) != 0;
	}
	
	public byte getByte(String p_74771_1_)
	{
		try
		{
			return !tagMap.containsKey(p_74771_1_) ? 0 : ((NBTTagByte) tagMap.get(p_74771_1_)).data;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(p_74771_1_, 1, var3));
		}
	}
	
	public byte[] getByteArray(String p_74770_1_)
	{
		try
		{
			return !tagMap.containsKey(p_74770_1_) ? new byte[0] : ((NBTTagByteArray) tagMap.get(p_74770_1_)).byteArray;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(p_74770_1_, 7, var3));
		}
	}
	
	public NBTTagCompound getCompoundTag(String p_74775_1_)
	{
		try
		{
			return !tagMap.containsKey(p_74775_1_) ? new NBTTagCompound(p_74775_1_) : (NBTTagCompound) tagMap.get(p_74775_1_);
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(p_74775_1_, 10, var3));
		}
	}
	
	public double getDouble(String p_74769_1_)
	{
		try
		{
			return !tagMap.containsKey(p_74769_1_) ? 0.0D : ((NBTTagDouble) tagMap.get(p_74769_1_)).data;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(p_74769_1_, 6, var3));
		}
	}
	
	public float getFloat(String p_74760_1_)
	{
		try
		{
			return !tagMap.containsKey(p_74760_1_) ? 0.0F : ((NBTTagFloat) tagMap.get(p_74760_1_)).data;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(p_74760_1_, 5, var3));
		}
	}
	
	@Override public byte getId()
	{
		return (byte) 10;
	}
	
	public int[] getIntArray(String p_74759_1_)
	{
		try
		{
			return !tagMap.containsKey(p_74759_1_) ? new int[0] : ((NBTTagIntArray) tagMap.get(p_74759_1_)).intArray;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(p_74759_1_, 11, var3));
		}
	}
	
	public int getInteger(String p_74762_1_)
	{
		try
		{
			return !tagMap.containsKey(p_74762_1_) ? 0 : ((NBTTagInt) tagMap.get(p_74762_1_)).data;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(p_74762_1_, 3, var3));
		}
	}
	
	public long getLong(String p_74763_1_)
	{
		try
		{
			return !tagMap.containsKey(p_74763_1_) ? 0L : ((NBTTagLong) tagMap.get(p_74763_1_)).data;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(p_74763_1_, 4, var3));
		}
	}
	
	public short getShort(String p_74765_1_)
	{
		try
		{
			return !tagMap.containsKey(p_74765_1_) ? 0 : ((NBTTagShort) tagMap.get(p_74765_1_)).data;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(p_74765_1_, 2, var3));
		}
	}
	
	public String getString(String p_74779_1_)
	{
		try
		{
			return !tagMap.containsKey(p_74779_1_) ? "" : ((NBTTagString) tagMap.get(p_74779_1_)).data;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(p_74779_1_, 8, var3));
		}
	}
	
	public NBTBase getTag(String p_74781_1_)
	{
		return (NBTBase) tagMap.get(p_74781_1_);
	}
	
	public NBTTagList getTagList(String p_74761_1_)
	{
		try
		{
			return !tagMap.containsKey(p_74761_1_) ? new NBTTagList(p_74761_1_) : (NBTTagList) tagMap.get(p_74761_1_);
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(p_74761_1_, 9, var3));
		}
	}
	
	public Collection getTags()
	{
		return tagMap.values();
	}
	
	@Override public int hashCode()
	{
		return super.hashCode() ^ tagMap.hashCode();
	}
	
	public boolean hasKey(String p_74764_1_)
	{
		return tagMap.containsKey(p_74764_1_);
	}
	
	public boolean hasNoTags()
	{
		return tagMap.isEmpty();
	}
	
	@Override void load(DataInput p_74735_1_) throws IOException
	{
		tagMap.clear();
		NBTBase var2;
		while((var2 = NBTBase.readNamedTag(p_74735_1_)).getId() != 0)
		{
			tagMap.put(var2.getName(), var2);
		}
	}
	
	public void removeTag(String p_82580_1_)
	{
		tagMap.remove(p_82580_1_);
	}
	
	public void setBoolean(String p_74757_1_, boolean p_74757_2_)
	{
		setByte(p_74757_1_, (byte) (p_74757_2_ ? 1 : 0));
	}
	
	public void setByte(String p_74774_1_, byte p_74774_2_)
	{
		tagMap.put(p_74774_1_, new NBTTagByte(p_74774_1_, p_74774_2_));
	}
	
	public void setByteArray(String p_74773_1_, byte[] p_74773_2_)
	{
		tagMap.put(p_74773_1_, new NBTTagByteArray(p_74773_1_, p_74773_2_));
	}
	
	public void setCompoundTag(String p_74766_1_, NBTTagCompound p_74766_2_)
	{
		tagMap.put(p_74766_1_, p_74766_2_.setName(p_74766_1_));
	}
	
	public void setDouble(String p_74780_1_, double p_74780_2_)
	{
		tagMap.put(p_74780_1_, new NBTTagDouble(p_74780_1_, p_74780_2_));
	}
	
	public void setFloat(String p_74776_1_, float p_74776_2_)
	{
		tagMap.put(p_74776_1_, new NBTTagFloat(p_74776_1_, p_74776_2_));
	}
	
	public void setIntArray(String p_74783_1_, int[] p_74783_2_)
	{
		tagMap.put(p_74783_1_, new NBTTagIntArray(p_74783_1_, p_74783_2_));
	}
	
	public void setInteger(String p_74768_1_, int p_74768_2_)
	{
		tagMap.put(p_74768_1_, new NBTTagInt(p_74768_1_, p_74768_2_));
	}
	
	public void setLong(String p_74772_1_, long p_74772_2_)
	{
		tagMap.put(p_74772_1_, new NBTTagLong(p_74772_1_, p_74772_2_));
	}
	
	public void setShort(String p_74777_1_, short p_74777_2_)
	{
		tagMap.put(p_74777_1_, new NBTTagShort(p_74777_1_, p_74777_2_));
	}
	
	public void setString(String p_74778_1_, String p_74778_2_)
	{
		tagMap.put(p_74778_1_, new NBTTagString(p_74778_1_, p_74778_2_));
	}
	
	public void setTag(String p_74782_1_, NBTBase p_74782_2_)
	{
		tagMap.put(p_74782_1_, p_74782_2_.setName(p_74782_1_));
	}
	
	@Override public String toString()
	{
		String var1 = getName() + ":[";
		String var3;
		for(Iterator var2 = tagMap.keySet().iterator(); var2.hasNext(); var1 = var1 + var3 + ":" + tagMap.get(var3) + ",")
		{
			var3 = (String) var2.next();
		}
		return var1 + "]";
	}
	
	@Override void write(DataOutput p_74734_1_) throws IOException
	{
		Iterator var2 = tagMap.values().iterator();
		while(var2.hasNext())
		{
			NBTBase var3 = (NBTBase) var2.next();
			NBTBase.writeNamedTag(var3, p_74734_1_);
		}
		p_74734_1_.writeByte(0);
	}
	
	static Map getTagMap(NBTTagCompound p_82579_0_)
	{
		return p_82579_0_.tagMap;
	}
}
