package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase
{
	public static final String[] NBTTypes = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };
	private String name;
	
	protected NBTBase(String par1Str)
	{
		if(par1Str == null)
		{
			name = "";
		} else
		{
			name = par1Str;
		}
	}
	
	public abstract NBTBase copy();
	
	@Override public boolean equals(Object par1Obj)
	{
		if(!(par1Obj instanceof NBTBase)) return false;
		else
		{
			NBTBase var2 = (NBTBase) par1Obj;
			return getId() != var2.getId() ? false : (name != null || var2.name == null) && (name == null || var2.name != null) ? name == null || name.equals(var2.name) : false;
		}
	}
	
	public abstract byte getId();
	
	public String getName()
	{
		return name == null ? "" : name;
	}
	
	@Override public int hashCode()
	{
		return name.hashCode() ^ getId();
	}
	
	abstract void load(DataInput var1, int var2) throws IOException;
	
	public NBTBase setName(String par1Str)
	{
		if(par1Str == null)
		{
			name = "";
		} else
		{
			name = par1Str;
		}
		return this;
	}
	
	abstract void write(DataOutput var1) throws IOException;
	
	public static NBTBase func_130104_b(DataInput par0DataInput, int par1) throws IOException
	{
		byte var2 = par0DataInput.readByte();
		if(var2 == 0) return new NBTTagEnd();
		else
		{
			String var3 = par0DataInput.readUTF();
			NBTBase var4 = newTag(var2, var3);
			try
			{
				var4.load(par0DataInput, par1);
				return var4;
			} catch(IOException var8)
			{
				CrashReport var6 = CrashReport.makeCrashReport(var8, "Loading NBT data");
				CrashReportCategory var7 = var6.makeCategory("NBT Tag");
				var7.addCrashSection("Tag name", var3);
				var7.addCrashSection("Tag type", Byte.valueOf(var2));
				throw new ReportedException(var6);
			}
		}
	}
	
	public static String getTagName(byte par0)
	{
		switch(par0)
		{
			case 0:
				return "TAG_End";
			case 1:
				return "TAG_Byte";
			case 2:
				return "TAG_Short";
			case 3:
				return "TAG_Int";
			case 4:
				return "TAG_Long";
			case 5:
				return "TAG_Float";
			case 6:
				return "TAG_Double";
			case 7:
				return "TAG_Byte_Array";
			case 8:
				return "TAG_String";
			case 9:
				return "TAG_List";
			case 10:
				return "TAG_Compound";
			case 11:
				return "TAG_Int_Array";
			default:
				return "UNKNOWN";
		}
	}
	
	public static NBTBase newTag(byte par0, String par1Str)
	{
		switch(par0)
		{
			case 0:
				return new NBTTagEnd();
			case 1:
				return new NBTTagByte(par1Str);
			case 2:
				return new NBTTagShort(par1Str);
			case 3:
				return new NBTTagInt(par1Str);
			case 4:
				return new NBTTagLong(par1Str);
			case 5:
				return new NBTTagFloat(par1Str);
			case 6:
				return new NBTTagDouble(par1Str);
			case 7:
				return new NBTTagByteArray(par1Str);
			case 8:
				return new NBTTagString(par1Str);
			case 9:
				return new NBTTagList(par1Str);
			case 10:
				return new NBTTagCompound(par1Str);
			case 11:
				return new NBTTagIntArray(par1Str);
			default:
				return null;
		}
	}
	
	public static NBTBase readNamedTag(DataInput par0DataInput) throws IOException
	{
		return func_130104_b(par0DataInput, 0);
	}
	
	public static void writeNamedTag(NBTBase par0NBTBase, DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeByte(par0NBTBase.getId());
		if(par0NBTBase.getId() != 0)
		{
			par1DataOutput.writeUTF(par0NBTBase.getName());
			par0NBTBase.write(par1DataOutput);
		}
	}
}
