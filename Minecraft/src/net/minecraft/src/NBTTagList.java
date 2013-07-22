package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NBTTagList extends NBTBase
{
	private List tagList = new ArrayList();
	private byte tagType;
	
	public NBTTagList()
	{
		super("");
	}
	
	public NBTTagList(String p_i3274_1_)
	{
		super(p_i3274_1_);
	}
	
	public void appendTag(NBTBase p_74742_1_)
	{
		tagType = p_74742_1_.getId();
		tagList.add(p_74742_1_);
	}
	
	@Override public NBTBase copy()
	{
		NBTTagList var1 = new NBTTagList(getName());
		var1.tagType = tagType;
		Iterator var2 = tagList.iterator();
		while(var2.hasNext())
		{
			NBTBase var3 = (NBTBase) var2.next();
			NBTBase var4 = var3.copy();
			var1.tagList.add(var4);
		}
		return var1;
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(super.equals(p_equals_1_))
		{
			NBTTagList var2 = (NBTTagList) p_equals_1_;
			if(tagType == var2.tagType) return tagList.equals(var2.tagList);
		}
		return false;
	}
	
	@Override public byte getId()
	{
		return (byte) 9;
	}
	
	@Override public int hashCode()
	{
		return super.hashCode() ^ tagList.hashCode();
	}
	
	@Override void load(DataInput p_74735_1_) throws IOException
	{
		tagType = p_74735_1_.readByte();
		int var2 = p_74735_1_.readInt();
		tagList = new ArrayList();
		for(int var3 = 0; var3 < var2; ++var3)
		{
			NBTBase var4 = NBTBase.newTag(tagType, (String) null);
			var4.load(p_74735_1_);
			tagList.add(var4);
		}
	}
	
	public NBTBase removeTag(int par1)
	{
		return (NBTBase) tagList.remove(par1);
	}
	
	public NBTBase tagAt(int p_74743_1_)
	{
		return (NBTBase) tagList.get(p_74743_1_);
	}
	
	public int tagCount()
	{
		return tagList.size();
	}
	
	@Override public String toString()
	{
		return "" + tagList.size() + " entries of type " + NBTBase.getTagName(tagType);
	}
	
	@Override void write(DataOutput p_74734_1_) throws IOException
	{
		if(!tagList.isEmpty())
		{
			tagType = ((NBTBase) tagList.get(0)).getId();
		} else
		{
			tagType = 1;
		}
		p_74734_1_.writeByte(tagType);
		p_74734_1_.writeInt(tagList.size());
		for(int var2 = 0; var2 < tagList.size(); ++var2)
		{
			((NBTBase) tagList.get(var2)).write(p_74734_1_);
		}
	}
}
