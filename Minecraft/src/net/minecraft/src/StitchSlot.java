package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StitchSlot
{
	private final int originX;
	private final int originY;
	private final int width;
	private final int height;
	private List subSlots;
	private StitchHolder holder;
	
	public StitchSlot(int par1, int par2, int par3, int par4)
	{
		originX = par1;
		originY = par2;
		width = par3;
		height = par4;
	}
	
	public boolean func_94182_a(StitchHolder par1StitchHolder)
	{
		if(holder != null) return false;
		else
		{
			int var2 = par1StitchHolder.getWidth();
			int var3 = par1StitchHolder.getHeight();
			if(var2 <= width && var3 <= height)
			{
				if(var2 == width && var3 == height)
				{
					holder = par1StitchHolder;
					return true;
				} else
				{
					if(subSlots == null)
					{
						subSlots = new ArrayList(1);
						subSlots.add(new StitchSlot(originX, originY, var2, var3));
						int var4 = width - var2;
						int var5 = height - var3;
						if(var5 > 0 && var4 > 0)
						{
							int var6 = Math.max(height, var4);
							int var7 = Math.max(width, var5);
							if(var6 >= var7)
							{
								subSlots.add(new StitchSlot(originX, originY + var3, var2, var5));
								subSlots.add(new StitchSlot(originX + var2, originY, var4, height));
							} else
							{
								subSlots.add(new StitchSlot(originX + var2, originY, var4, var3));
								subSlots.add(new StitchSlot(originX, originY + var3, width, var5));
							}
						} else if(var4 == 0)
						{
							subSlots.add(new StitchSlot(originX, originY + var3, var2, var5));
						} else if(var5 == 0)
						{
							subSlots.add(new StitchSlot(originX + var2, originY, var4, var3));
						}
					}
					Iterator var8 = subSlots.iterator();
					StitchSlot var9;
					do
					{
						if(!var8.hasNext()) return false;
						var9 = (StitchSlot) var8.next();
					} while(!var9.func_94182_a(par1StitchHolder));
					return true;
				}
			} else return false;
		}
	}
	
	public void getAllStitchSlots(List par1List)
	{
		if(holder != null)
		{
			par1List.add(this);
		} else if(subSlots != null)
		{
			Iterator var2 = subSlots.iterator();
			while(var2.hasNext())
			{
				StitchSlot var3 = (StitchSlot) var2.next();
				var3.getAllStitchSlots(par1List);
			}
		}
	}
	
	public int getOriginX()
	{
		return originX;
	}
	
	public int getOriginY()
	{
		return originY;
	}
	
	public StitchHolder getStitchHolder()
	{
		return holder;
	}
	
	@Override public String toString()
	{
		return "Slot{originX=" + originX + ", originY=" + originY + ", width=" + width + ", height=" + height + ", texture=" + holder + ", subSlots=" + subSlots + '}';
	}
}
