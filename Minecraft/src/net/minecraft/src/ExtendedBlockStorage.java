package net.minecraft.src;

public class ExtendedBlockStorage
{
	private int yBase;
	private int blockRefCount;
	private int tickRefCount;
	private byte[] blockLSBArray;
	private NibbleArray blockMSBArray;
	private NibbleArray blockMetadataArray;
	private NibbleArray blocklightArray;
	private NibbleArray skylightArray;
	
	public ExtendedBlockStorage(int p_i8016_1_, boolean p_i8016_2_)
	{
		yBase = p_i8016_1_;
		blockLSBArray = new byte[4096];
		blockMetadataArray = new NibbleArray(blockLSBArray.length, 4);
		blocklightArray = new NibbleArray(blockLSBArray.length, 4);
		if(p_i8016_2_)
		{
			skylightArray = new NibbleArray(blockLSBArray.length, 4);
		}
	}
	
	public void clearMSBArray()
	{
		blockMSBArray = null;
	}
	
	public NibbleArray createBlockMSBArray()
	{
		blockMSBArray = new NibbleArray(blockLSBArray.length, 4);
		return blockMSBArray;
	}
	
	public NibbleArray getBlocklightArray()
	{
		return blocklightArray;
	}
	
	public byte[] getBlockLSBArray()
	{
		return blockLSBArray;
	}
	
	public NibbleArray getBlockMSBArray()
	{
		return blockMSBArray;
	}
	
	public int getExtBlockID(int p_76656_1_, int p_76656_2_, int p_76656_3_)
	{
		int var4 = blockLSBArray[p_76656_2_ << 8 | p_76656_3_ << 4 | p_76656_1_] & 255;
		return blockMSBArray != null ? blockMSBArray.get(p_76656_1_, p_76656_2_, p_76656_3_) << 8 | var4 : var4;
	}
	
	public int getExtBlocklightValue(int p_76674_1_, int p_76674_2_, int p_76674_3_)
	{
		return blocklightArray.get(p_76674_1_, p_76674_2_, p_76674_3_);
	}
	
	public int getExtBlockMetadata(int p_76665_1_, int p_76665_2_, int p_76665_3_)
	{
		return blockMetadataArray.get(p_76665_1_, p_76665_2_, p_76665_3_);
	}
	
	public int getExtSkylightValue(int p_76670_1_, int p_76670_2_, int p_76670_3_)
	{
		return skylightArray.get(p_76670_1_, p_76670_2_, p_76670_3_);
	}
	
	public NibbleArray getMetadataArray()
	{
		return blockMetadataArray;
	}
	
	public boolean getNeedsRandomTick()
	{
		return tickRefCount > 0;
	}
	
	public NibbleArray getSkylightArray()
	{
		return skylightArray;
	}
	
	public int getYLocation()
	{
		return yBase;
	}
	
	public boolean isEmpty()
	{
		return blockRefCount == 0;
	}
	
	public void removeInvalidBlocks()
	{
		blockRefCount = 0;
		tickRefCount = 0;
		for(int var1 = 0; var1 < 16; ++var1)
		{
			for(int var2 = 0; var2 < 16; ++var2)
			{
				for(int var3 = 0; var3 < 16; ++var3)
				{
					int var4 = getExtBlockID(var1, var2, var3);
					if(var4 > 0)
					{
						if(Block.blocksList[var4] == null)
						{
							blockLSBArray[var2 << 8 | var3 << 4 | var1] = 0;
							if(blockMSBArray != null)
							{
								blockMSBArray.set(var1, var2, var3, 0);
							}
						} else
						{
							++blockRefCount;
							if(Block.blocksList[var4].getTickRandomly())
							{
								++tickRefCount;
							}
						}
					}
				}
			}
		}
	}
	
	public void setBlocklightArray(NibbleArray p_76659_1_)
	{
		blocklightArray = p_76659_1_;
	}
	
	public void setBlockLSBArray(byte[] p_76664_1_)
	{
		blockLSBArray = p_76664_1_;
	}
	
	public void setBlockMetadataArray(NibbleArray p_76668_1_)
	{
		blockMetadataArray = p_76668_1_;
	}
	
	public void setBlockMSBArray(NibbleArray p_76673_1_)
	{
		blockMSBArray = p_76673_1_;
	}
	
	public void setExtBlockID(int p_76655_1_, int p_76655_2_, int p_76655_3_, int p_76655_4_)
	{
		int var5 = blockLSBArray[p_76655_2_ << 8 | p_76655_3_ << 4 | p_76655_1_] & 255;
		if(blockMSBArray != null)
		{
			var5 |= blockMSBArray.get(p_76655_1_, p_76655_2_, p_76655_3_) << 8;
		}
		if(var5 == 0 && p_76655_4_ != 0)
		{
			++blockRefCount;
			if(Block.blocksList[p_76655_4_] != null && Block.blocksList[p_76655_4_].getTickRandomly())
			{
				++tickRefCount;
			}
		} else if(var5 != 0 && p_76655_4_ == 0)
		{
			--blockRefCount;
			if(Block.blocksList[var5] != null && Block.blocksList[var5].getTickRandomly())
			{
				--tickRefCount;
			}
		} else if(Block.blocksList[var5] != null && Block.blocksList[var5].getTickRandomly() && (Block.blocksList[p_76655_4_] == null || !Block.blocksList[p_76655_4_].getTickRandomly()))
		{
			--tickRefCount;
		} else if((Block.blocksList[var5] == null || !Block.blocksList[var5].getTickRandomly()) && Block.blocksList[p_76655_4_] != null && Block.blocksList[p_76655_4_].getTickRandomly())
		{
			++tickRefCount;
		}
		blockLSBArray[p_76655_2_ << 8 | p_76655_3_ << 4 | p_76655_1_] = (byte) (p_76655_4_ & 255);
		if(p_76655_4_ > 255)
		{
			if(blockMSBArray == null)
			{
				blockMSBArray = new NibbleArray(blockLSBArray.length, 4);
			}
			blockMSBArray.set(p_76655_1_, p_76655_2_, p_76655_3_, (p_76655_4_ & 3840) >> 8);
		} else if(blockMSBArray != null)
		{
			blockMSBArray.set(p_76655_1_, p_76655_2_, p_76655_3_, 0);
		}
	}
	
	public void setExtBlocklightValue(int p_76677_1_, int p_76677_2_, int p_76677_3_, int p_76677_4_)
	{
		blocklightArray.set(p_76677_1_, p_76677_2_, p_76677_3_, p_76677_4_);
	}
	
	public void setExtBlockMetadata(int p_76654_1_, int p_76654_2_, int p_76654_3_, int p_76654_4_)
	{
		blockMetadataArray.set(p_76654_1_, p_76654_2_, p_76654_3_, p_76654_4_);
	}
	
	public void setExtSkylightValue(int p_76657_1_, int p_76657_2_, int p_76657_3_, int p_76657_4_)
	{
		skylightArray.set(p_76657_1_, p_76657_2_, p_76657_3_, p_76657_4_);
	}
	
	public void setSkylightArray(NibbleArray p_76666_1_)
	{
		skylightArray = p_76666_1_;
	}
}
