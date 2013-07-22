package net.minecraft.src;

public class FlatLayerInfo
{
	private int layerCount;
	private int layerFillBlock;
	private int layerFillBlockMeta;
	private int layerMinimumY;
	
	public FlatLayerInfo(int par1, int par2)
	{
		layerCount = 1;
		layerCount = par1;
		layerFillBlock = par2;
	}
	
	public FlatLayerInfo(int par1, int par2, int par3)
	{
		this(par1, par2);
		layerFillBlockMeta = par3;
	}
	
	public int getFillBlock()
	{
		return layerFillBlock;
	}
	
	public int getFillBlockMeta()
	{
		return layerFillBlockMeta;
	}
	
	public int getLayerCount()
	{
		return layerCount;
	}
	
	public int getMinY()
	{
		return layerMinimumY;
	}
	
	public void setMinY(int par1)
	{
		layerMinimumY = par1;
	}
	
	@Override public String toString()
	{
		String var1 = Integer.toString(layerFillBlock);
		if(layerCount > 1)
		{
			var1 = layerCount + "x" + var1;
		}
		if(layerFillBlockMeta > 0)
		{
			var1 = var1 + ":" + layerFillBlockMeta;
		}
		return var1;
	}
}
