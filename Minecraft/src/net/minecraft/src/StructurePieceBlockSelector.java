package net.minecraft.src;

import java.util.Random;

public abstract class StructurePieceBlockSelector
{
	protected int selectedBlockId;
	protected int selectedBlockMetaData;
	
	public int getSelectedBlockId()
	{
		return selectedBlockId;
	}
	
	public int getSelectedBlockMetaData()
	{
		return selectedBlockMetaData;
	}
	
	public abstract void selectBlocks(Random var1, int var2, int var3, int var4, boolean var5);
}
