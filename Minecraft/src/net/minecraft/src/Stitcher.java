package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Stitcher
{
	private final Set setStitchHolders;
	private final List stitchSlots;
	private int currentWidth;
	private int currentHeight;
	private final int maxWidth;
	private final int maxHeight;
	private final boolean forcePowerOf2;
	private final int maxTileDimension;
	
	public Stitcher(int par1, int par2, boolean par3)
	{
		this(par1, par2, par3, 0);
	}
	
	public Stitcher(int par1, int par2, boolean par3, int par4)
	{
		setStitchHolders = new HashSet(256);
		stitchSlots = new ArrayList(256);
		maxWidth = par1;
		maxHeight = par2;
		forcePowerOf2 = par3;
		maxTileDimension = par4;
	}
	
	private boolean allocateSlot(StitchHolder par1StitchHolder)
	{
		for(int var2 = 0; var2 < stitchSlots.size(); ++var2)
		{
			if(((StitchSlot) stitchSlots.get(var2)).func_94182_a(par1StitchHolder)) return true;
			par1StitchHolder.rotate();
			if(((StitchSlot) stitchSlots.get(var2)).func_94182_a(par1StitchHolder)) return true;
			par1StitchHolder.rotate();
		}
		return expandAndAllocateSlot(par1StitchHolder);
	}
	
	public void doStitch()
	{
		StitchHolder[] var1 = (StitchHolder[]) setStitchHolders.toArray(new StitchHolder[setStitchHolders.size()]);
		Arrays.sort(var1);
		StitchHolder[] var2 = var1;
		int var3 = var1.length;
		for(int var4 = 0; var4 < var3; ++var4)
		{
			StitchHolder var5 = var2[var4];
			if(!allocateSlot(var5))
			{
				String var6 = String.format("Unable to fit: %s - size: %dx%d - Maybe try a lowerresolution texturepack?", new Object[] { var5.func_98150_a().getIconName(), Integer.valueOf(var5.func_98150_a().getOriginX()), Integer.valueOf(var5.func_98150_a().getOriginY()) });
				throw new StitcherException(var5, var6);
			}
		}
		if(forcePowerOf2)
		{
			currentWidth = getCeilPowerOf2(currentWidth);
			currentHeight = getCeilPowerOf2(currentHeight);
		}
	}
	
	private boolean expandAndAllocateSlot(StitchHolder par1StitchHolder)
	{
		int var2 = Math.min(par1StitchHolder.getHeight(), par1StitchHolder.getWidth());
		boolean var3 = currentWidth == 0 && currentHeight == 0;
		boolean var4;
		if(forcePowerOf2)
		{
			int var5 = getCeilPowerOf2(currentWidth);
			int var6 = getCeilPowerOf2(currentHeight);
			int var7 = getCeilPowerOf2(currentWidth + var2);
			int var8 = getCeilPowerOf2(currentHeight + var2);
			boolean var9 = var7 <= maxWidth;
			boolean var10 = var8 <= maxHeight;
			if(!var9 && !var10) return false;
			int var11 = Math.max(par1StitchHolder.getHeight(), par1StitchHolder.getWidth());
			if(var3 && !var9 && getCeilPowerOf2(currentHeight + var11) > maxHeight) return false;
			boolean var12 = var5 != var7;
			boolean var13 = var6 != var8;
			if(var12 ^ var13)
			{
				var4 = var12 && var9;
			} else
			{
				var4 = var9 && var5 <= var6;
			}
		} else
		{
			boolean var14 = currentWidth + var2 <= maxWidth;
			boolean var16 = currentHeight + var2 <= maxHeight;
			if(!var14 && !var16) return false;
			var4 = (var3 || currentWidth <= currentHeight) && var14;
		}
		StitchSlot var15;
		if(var4)
		{
			if(par1StitchHolder.getWidth() > par1StitchHolder.getHeight())
			{
				par1StitchHolder.rotate();
			}
			if(currentHeight == 0)
			{
				currentHeight = par1StitchHolder.getHeight();
			}
			var15 = new StitchSlot(currentWidth, 0, par1StitchHolder.getWidth(), currentHeight);
			currentWidth += par1StitchHolder.getWidth();
		} else
		{
			var15 = new StitchSlot(0, currentHeight, currentWidth, par1StitchHolder.getHeight());
			currentHeight += par1StitchHolder.getHeight();
		}
		var15.func_94182_a(par1StitchHolder);
		stitchSlots.add(var15);
		return true;
	}
	
	public void func_110934_a(TextureAtlasSprite par1TextureAtlasSprite)
	{
		StitchHolder var2 = new StitchHolder(par1TextureAtlasSprite);
		if(maxTileDimension > 0)
		{
			var2.setNewDimension(maxTileDimension);
		}
		setStitchHolders.add(var2);
	}
	
	public int func_110935_a()
	{
		return currentWidth;
	}
	
	public int func_110936_b()
	{
		return currentHeight;
	}
	
	private int getCeilPowerOf2(int par1)
	{
		int var2 = par1 - 1;
		var2 |= var2 >> 1;
		var2 |= var2 >> 2;
		var2 |= var2 >> 4;
		var2 |= var2 >> 8;
		var2 |= var2 >> 16;
		return var2 + 1;
	}
	
	public List getStichSlots()
	{
		ArrayList var1 = Lists.newArrayList();
		Iterator var2 = stitchSlots.iterator();
		while(var2.hasNext())
		{
			StitchSlot var3 = (StitchSlot) var2.next();
			var3.getAllStitchSlots(var1);
		}
		ArrayList var7 = Lists.newArrayList();
		Iterator var8 = var1.iterator();
		while(var8.hasNext())
		{
			StitchSlot var4 = (StitchSlot) var8.next();
			StitchHolder var5 = var4.getStitchHolder();
			TextureAtlasSprite var6 = var5.func_98150_a();
			var6.func_110971_a(currentWidth, currentHeight, var4.getOriginX(), var4.getOriginY(), var5.isRotated());
			var7.add(var6);
		}
		return var7;
	}
}
