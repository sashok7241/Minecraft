package net.minecraft.src;

public class SaveFormatComparator implements Comparable
{
	private final String fileName;
	private final String displayName;
	private final long lastTimePlayed;
	private final long sizeOnDisk;
	private final boolean requiresConversion;
	private final EnumGameType theEnumGameType;
	private final boolean hardcore;
	private final boolean cheatsEnabled;
	
	public SaveFormatComparator(String par1Str, String par2Str, long par3, long par5, EnumGameType par7EnumGameType, boolean par8, boolean par9, boolean par10)
	{
		fileName = par1Str;
		displayName = par2Str;
		lastTimePlayed = par3;
		sizeOnDisk = par5;
		theEnumGameType = par7EnumGameType;
		requiresConversion = par8;
		hardcore = par9;
		cheatsEnabled = par10;
	}
	
	@Override public int compareTo(Object par1Obj)
	{
		return this.compareTo((SaveFormatComparator) par1Obj);
	}
	
	public int compareTo(SaveFormatComparator par1SaveFormatComparator)
	{
		return lastTimePlayed < par1SaveFormatComparator.lastTimePlayed ? 1 : lastTimePlayed > par1SaveFormatComparator.lastTimePlayed ? -1 : fileName.compareTo(par1SaveFormatComparator.fileName);
	}
	
	public boolean getCheatsEnabled()
	{
		return cheatsEnabled;
	}
	
	public String getDisplayName()
	{
		return displayName;
	}
	
	public EnumGameType getEnumGameType()
	{
		return theEnumGameType;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public long getLastTimePlayed()
	{
		return lastTimePlayed;
	}
	
	public boolean isHardcoreModeEnabled()
	{
		return hardcore;
	}
	
	public boolean requiresConversion()
	{
		return requiresConversion;
	}
}
