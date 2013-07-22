package net.minecraft.src;

import java.util.List;

public interface ISaveFormat
{
	boolean canLoadWorld(String var1);
	
	boolean convertMapFormat(String var1, IProgressUpdate var2);
	
	boolean deleteWorldDirectory(String var1);
	
	void flushCache();
	
	List getSaveList() throws AnvilConverterException;
	
	ISaveHandler getSaveLoader(String var1, boolean var2);
	
	WorldInfo getWorldInfo(String var1);
	
	boolean isOldMapFormat(String var1);
	
	void renameWorld(String var1, String var2);
}
