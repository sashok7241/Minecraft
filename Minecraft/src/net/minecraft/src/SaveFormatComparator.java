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
	
	public SaveFormatComparator(String p_i3917_1_, String p_i3917_2_, long p_i3917_3_, long p_i3917_5_, EnumGameType p_i3917_7_, boolean p_i3917_8_, boolean p_i3917_9_, boolean p_i3917_10_)
	{
		fileName = p_i3917_1_;
		displayName = p_i3917_2_;
		lastTimePlayed = p_i3917_3_;
		sizeOnDisk = p_i3917_5_;
		theEnumGameType = p_i3917_7_;
		requiresConversion = p_i3917_8_;
		hardcore = p_i3917_9_;
		cheatsEnabled = p_i3917_10_;
	}
	
	@Override public int compareTo(Object p_compareTo_1_)
	{
		return this.compareTo((SaveFormatComparator) p_compareTo_1_);
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
