package net.minecraft.src;

import java.io.File;

public class AnvilSaveHandler extends SaveHandler
{
	public AnvilSaveHandler(File p_i3908_1_, String p_i3908_2_, boolean p_i3908_3_)
	{
		super(p_i3908_1_, p_i3908_2_, p_i3908_3_);
	}
	
	@Override public void flush()
	{
		try
		{
			ThreadedFileIOBase.threadedIOInstance.waitForFinish();
		} catch(InterruptedException var2)
		{
			var2.printStackTrace();
		}
		RegionFileCache.clearRegionFileReferences();
	}
	
	@Override public IChunkLoader getChunkLoader(WorldProvider p_75763_1_)
	{
		File var2 = getWorldDirectory();
		File var3;
		if(p_75763_1_ instanceof WorldProviderHell)
		{
			var3 = new File(var2, "DIM-1");
			var3.mkdirs();
			return new AnvilChunkLoader(var3);
		} else if(p_75763_1_ instanceof WorldProviderEnd)
		{
			var3 = new File(var2, "DIM1");
			var3.mkdirs();
			return new AnvilChunkLoader(var3);
		} else return new AnvilChunkLoader(var2);
	}
	
	@Override public void saveWorldInfoWithPlayer(WorldInfo p_75755_1_, NBTTagCompound p_75755_2_)
	{
		p_75755_1_.setSaveVersion(19133);
		super.saveWorldInfoWithPlayer(p_75755_1_, p_75755_2_);
	}
}
