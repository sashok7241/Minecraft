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
	
	public ExtendedBlockStorage(int par1, boolean par2)
	{
		yBase = par1;
		blockLSBArray = new byte[4096];
		blockMetadataArray = new NibbleArray(blockLSBArray.length, 4);
		blocklightArray = new NibbleArray(blockLSBArray.length, 4);
		if(par2)
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
	
	public int getExtBlockID(int par1, int par2, int par3)
	{
		int var4 = blockLSBArray[par2 << 8 | par3 << 4 | par1] & 255;
		return blockMSBArray != null ? blockMSBArray.get(par1, par2, par3) << 8 | var4 : var4;
	}
	
	public int getExtBlocklightValue(int par1, int par2, int par3)
	{
		return blocklightArray.get(par1, par2, par3);
	}
	
	public int getExtBlockMetadata(int par1, int par2, int par3)
	{
		return blockMetadataArray.get(par1, par2, par3);
	}
	
	public int getExtSkylightValue(int par1, int par2, int par3)
	{
		return skylightArray.get(par1, par2, par3);
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
	
	public void setBlocklightArray(NibbleArray par1NibbleArray)
	{
		blocklightArray = par1NibbleArray;
	}
	
	public void setBlockLSBArray(byte[] par1ArrayOfByte)
	{
		blockLSBArray = par1ArrayOfByte;
	}
	
	public void setBlockMetadataArray(NibbleArray par1NibbleArray)
	{
		blockMetadataArray = par1NibbleArray;
	}
	
	public void setBlockMSBArray(NibbleArray par1NibbleArray)
	{
		blockMSBArray = par1NibbleArray;
	}
	
	public void setExtBlockID(int par1, int par2, int par3, int par4)
	{
		int var5 = blockLSBArray[par2 << 8 | par3 << 4 | par1] & 255;
		if(blockMSBArray != null)
		{
			var5 |= blockMSBArray.get(par1, par2, par3) << 8;
		}
		if(var5 == 0 && par4 != 0)
		{
			++blockRefCount;
			if(Block.blocksList[par4] != null && Block.blocksList[par4].getTickRandomly())
			{
				++tickRefCount;
			}
		} else if(var5 != 0 && par4 == 0)
		{
			--blockRefCount;
			if(Block.blocksList[var5] != null && Block.blocksList[var5].getTickRandomly())
			{
				--tickRefCount;
			}
		} else if(Block.blocksList[var5] != null && Block.blocksList[var5].getTickRandomly() && (Block.blocksList[par4] == null || !Block.blocksList[par4].getTickRandomly()))
		{
			--tickRefCount;
		} else if((Block.blocksList[var5] == null || !Block.blocksList[var5].getTickRandomly()) && Block.blocksList[par4] != null && Block.blocksList[par4].getTickRandomly())
		{
			++tickRefCount;
		}
		blockLSBArray[par2 << 8 | par3 << 4 | par1] = (byte) (par4 & 255);
		if(par4 > 255)
		{
			if(blockMSBArray == null)
			{
				blockMSBArray = new NibbleArray(blockLSBArray.length, 4);
			}
			blockMSBArray.set(par1, par2, par3, (par4 & 3840) >> 8);
		} else if(blockMSBArray != null)
		{
			blockMSBArray.set(par1, par2, par3, 0);
		}
	}
	
	public void setExtBlocklightValue(int par1, int par2, int par3, int par4)
	{
		blocklightArray.set(par1, par2, par3, par4);
	}
	
	public void setExtBlockMetadata(int par1, int par2, int par3, int par4)
	{
		blockMetadataArray.set(par1, par2, par3, par4);
	}
	
	public void setExtSkylightValue(int par1, int par2, int par3, int par4)
	{
		skylightArray.set(par1, par2, par3, par4);
	}
	
	public void setSkylightArray(NibbleArray par1NibbleArray)
	{
		skylightArray = par1NibbleArray;
	}
}
