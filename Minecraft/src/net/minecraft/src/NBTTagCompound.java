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
	
	public NBTTagCompound(String par1Str)
	{
		super(par1Str);
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
	
	private CrashReport createCrashReport(String par1Str, int par2, ClassCastException par3ClassCastException)
	{
		CrashReport var4 = CrashReport.makeCrashReport(par3ClassCastException, "Reading NBT data");
		CrashReportCategory var5 = var4.makeCategoryDepth("Corrupt NBT tag", 1);
		var5.addCrashSectionCallable("Tag type found", new CallableTagCompound1(this, par1Str));
		var5.addCrashSectionCallable("Tag type expected", new CallableTagCompound2(this, par2));
		var5.addCrashSection("Tag name", par1Str);
		if(getName() != null && getName().length() > 0)
		{
			var5.addCrashSection("Tag parent", getName());
		}
		return var4;
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		if(super.equals(par1Obj))
		{
			NBTTagCompound var2 = (NBTTagCompound) par1Obj;
			return tagMap.entrySet().equals(var2.tagMap.entrySet());
		} else return false;
	}
	
	public boolean getBoolean(String par1Str)
	{
		return getByte(par1Str) != 0;
	}
	
	public byte getByte(String par1Str)
	{
		try
		{
			return !tagMap.containsKey(par1Str) ? 0 : ((NBTTagByte) tagMap.get(par1Str)).data;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(par1Str, 1, var3));
		}
	}
	
	public byte[] getByteArray(String par1Str)
	{
		try
		{
			return !tagMap.containsKey(par1Str) ? new byte[0] : ((NBTTagByteArray) tagMap.get(par1Str)).byteArray;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(par1Str, 7, var3));
		}
	}
	
	public NBTTagCompound getCompoundTag(String par1Str)
	{
		try
		{
			return !tagMap.containsKey(par1Str) ? new NBTTagCompound(par1Str) : (NBTTagCompound) tagMap.get(par1Str);
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(par1Str, 10, var3));
		}
	}
	
	public double getDouble(String par1Str)
	{
		try
		{
			return !tagMap.containsKey(par1Str) ? 0.0D : ((NBTTagDouble) tagMap.get(par1Str)).data;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(par1Str, 6, var3));
		}
	}
	
	public float getFloat(String par1Str)
	{
		try
		{
			return !tagMap.containsKey(par1Str) ? 0.0F : ((NBTTagFloat) tagMap.get(par1Str)).data;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(par1Str, 5, var3));
		}
	}
	
	@Override public byte getId()
	{
		return (byte) 10;
	}
	
	public int[] getIntArray(String par1Str)
	{
		try
		{
			return !tagMap.containsKey(par1Str) ? new int[0] : ((NBTTagIntArray) tagMap.get(par1Str)).intArray;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(par1Str, 11, var3));
		}
	}
	
	public int getInteger(String par1Str)
	{
		try
		{
			return !tagMap.containsKey(par1Str) ? 0 : ((NBTTagInt) tagMap.get(par1Str)).data;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(par1Str, 3, var3));
		}
	}
	
	public long getLong(String par1Str)
	{
		try
		{
			return !tagMap.containsKey(par1Str) ? 0L : ((NBTTagLong) tagMap.get(par1Str)).data;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(par1Str, 4, var3));
		}
	}
	
	public short getShort(String par1Str)
	{
		try
		{
			return !tagMap.containsKey(par1Str) ? 0 : ((NBTTagShort) tagMap.get(par1Str)).data;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(par1Str, 2, var3));
		}
	}
	
	public String getString(String par1Str)
	{
		try
		{
			return !tagMap.containsKey(par1Str) ? "" : ((NBTTagString) tagMap.get(par1Str)).data;
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(par1Str, 8, var3));
		}
	}
	
	public NBTBase getTag(String par1Str)
	{
		return (NBTBase) tagMap.get(par1Str);
	}
	
	public NBTTagList getTagList(String par1Str)
	{
		try
		{
			return !tagMap.containsKey(par1Str) ? new NBTTagList(par1Str) : (NBTTagList) tagMap.get(par1Str);
		} catch(ClassCastException var3)
		{
			throw new ReportedException(createCrashReport(par1Str, 9, var3));
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
	
	public boolean hasKey(String par1Str)
	{
		return tagMap.containsKey(par1Str);
	}
	
	public boolean hasNoTags()
	{
		return tagMap.isEmpty();
	}
	
	@Override void load(DataInput par1DataInput) throws IOException
	{
		tagMap.clear();
		NBTBase var2;
		while((var2 = NBTBase.readNamedTag(par1DataInput)).getId() != 0)
		{
			tagMap.put(var2.getName(), var2);
		}
	}
	
	public void removeTag(String par1Str)
	{
		tagMap.remove(par1Str);
	}
	
	public void setBoolean(String par1Str, boolean par2)
	{
		setByte(par1Str, (byte) (par2 ? 1 : 0));
	}
	
	public void setByte(String par1Str, byte par2)
	{
		tagMap.put(par1Str, new NBTTagByte(par1Str, par2));
	}
	
	public void setByteArray(String par1Str, byte[] par2ArrayOfByte)
	{
		tagMap.put(par1Str, new NBTTagByteArray(par1Str, par2ArrayOfByte));
	}
	
	public void setCompoundTag(String par1Str, NBTTagCompound par2NBTTagCompound)
	{
		tagMap.put(par1Str, par2NBTTagCompound.setName(par1Str));
	}
	
	public void setDouble(String par1Str, double par2)
	{
		tagMap.put(par1Str, new NBTTagDouble(par1Str, par2));
	}
	
	public void setFloat(String par1Str, float par2)
	{
		tagMap.put(par1Str, new NBTTagFloat(par1Str, par2));
	}
	
	public void setIntArray(String par1Str, int[] par2ArrayOfInteger)
	{
		tagMap.put(par1Str, new NBTTagIntArray(par1Str, par2ArrayOfInteger));
	}
	
	public void setInteger(String par1Str, int par2)
	{
		tagMap.put(par1Str, new NBTTagInt(par1Str, par2));
	}
	
	public void setLong(String par1Str, long par2)
	{
		tagMap.put(par1Str, new NBTTagLong(par1Str, par2));
	}
	
	public void setShort(String par1Str, short par2)
	{
		tagMap.put(par1Str, new NBTTagShort(par1Str, par2));
	}
	
	public void setString(String par1Str, String par2Str)
	{
		tagMap.put(par1Str, new NBTTagString(par1Str, par2Str));
	}
	
	public void setTag(String par1Str, NBTBase par2NBTBase)
	{
		tagMap.put(par1Str, par2NBTBase.setName(par1Str));
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
	
	@Override void write(DataOutput par1DataOutput) throws IOException
	{
		Iterator var2 = tagMap.values().iterator();
		while(var2.hasNext())
		{
			NBTBase var3 = (NBTBase) var2.next();
			NBTBase.writeNamedTag(var3, par1DataOutput);
		}
		par1DataOutput.writeByte(0);
	}
	
	static Map getTagMap(NBTTagCompound par0NBTTagCompound)
	{
		return par0NBTTagCompound.tagMap;
	}
}
