package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase
{
	public static final String[] NBTTypes = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };
	private String name;
	
	protected NBTBase(String p_i3281_1_)
	{
		if(p_i3281_1_ == null)
		{
			name = "";
		} else
		{
			name = p_i3281_1_;
		}
	}
	
	public abstract NBTBase copy();
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(!(p_equals_1_ instanceof NBTBase)) return false;
		else
		{
			NBTBase var2 = (NBTBase) p_equals_1_;
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
	
	abstract void load(DataInput var1) throws IOException;
	
	public NBTBase setName(String p_74738_1_)
	{
		if(p_74738_1_ == null)
		{
			name = "";
		} else
		{
			name = p_74738_1_;
		}
		return this;
	}
	
	abstract void write(DataOutput var1) throws IOException;
	
	public static String getTagName(byte p_74736_0_)
	{
		switch(p_74736_0_)
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
	
	public static NBTBase newTag(byte p_74733_0_, String p_74733_1_)
	{
		switch(p_74733_0_)
		{
			case 0:
				return new NBTTagEnd();
			case 1:
				return new NBTTagByte(p_74733_1_);
			case 2:
				return new NBTTagShort(p_74733_1_);
			case 3:
				return new NBTTagInt(p_74733_1_);
			case 4:
				return new NBTTagLong(p_74733_1_);
			case 5:
				return new NBTTagFloat(p_74733_1_);
			case 6:
				return new NBTTagDouble(p_74733_1_);
			case 7:
				return new NBTTagByteArray(p_74733_1_);
			case 8:
				return new NBTTagString(p_74733_1_);
			case 9:
				return new NBTTagList(p_74733_1_);
			case 10:
				return new NBTTagCompound(p_74733_1_);
			case 11:
				return new NBTTagIntArray(p_74733_1_);
			default:
				return null;
		}
	}
	
	public static NBTBase readNamedTag(DataInput p_74739_0_) throws IOException
	{
		byte var1 = p_74739_0_.readByte();
		if(var1 == 0) return new NBTTagEnd();
		else
		{
			String var2 = p_74739_0_.readUTF();
			NBTBase var3 = newTag(var1, var2);
			try
			{
				var3.load(p_74739_0_);
				return var3;
			} catch(IOException var7)
			{
				CrashReport var5 = CrashReport.makeCrashReport(var7, "Loading NBT data");
				CrashReportCategory var6 = var5.makeCategory("NBT Tag");
				var6.addCrashSection("Tag name", var2);
				var6.addCrashSection("Tag type", Byte.valueOf(var1));
				throw new ReportedException(var5);
			}
		}
	}
	
	public static void writeNamedTag(NBTBase p_74731_0_, DataOutput p_74731_1_) throws IOException
	{
		p_74731_1_.writeByte(p_74731_0_.getId());
		if(p_74731_0_.getId() != 0)
		{
			p_74731_1_.writeUTF(p_74731_0_.getName());
			p_74731_0_.write(p_74731_1_);
		}
	}
}
