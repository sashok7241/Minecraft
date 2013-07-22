package net.minecraft.src;

public interface IPlayerFileData
{
	String[] getAvailablePlayerDat();
	
	NBTTagCompound readPlayerData(EntityPlayer var1);
	
	void writePlayerData(EntityPlayer var1);
}
