package net.minecraft.src;

public class FlatLayerInfo
{
	private int layerCount;
	private int layerFillBlock;
	private int layerFillBlockMeta;
	private int layerMinimumY;
	
	public FlatLayerInfo(int p_i5091_1_, int p_i5091_2_)
	{
		layerCount = 1;
		layerFillBlock = 0;
		layerFillBlockMeta = 0;
		layerMinimumY = 0;
		layerCount = p_i5091_1_;
		layerFillBlock = p_i5091_2_;
	}
	
	public FlatLayerInfo(int p_i5092_1_, int p_i5092_2_, int p_i5092_3_)
	{
		this(p_i5092_1_, p_i5092_2_);
		layerFillBlockMeta = p_i5092_3_;
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
	
	public void setMinY(int p_82660_1_)
	{
		layerMinimumY = p_82660_1_;
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
