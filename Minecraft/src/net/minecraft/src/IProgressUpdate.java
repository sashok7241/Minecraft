package net.minecraft.src;

public interface IProgressUpdate
{
	void displayProgressMessage(String var1);
	
	void onNoMoreProgress();
	
	void resetProgresAndWorkingMessage(String var1);
	
	void resetProgressAndMessage(String var1);
	
	void setLoadingProgress(int var1);
}
