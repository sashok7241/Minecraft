package net.minecraft.src;

public class GuiProgress extends GuiScreen implements IProgressUpdate
{
	private String progressMessage = "";
	private String workingMessage = "";
	private int currentProgress = 0;
	private boolean noMoreProgress;
	
	@Override public void displayProgressMessage(String par1Str)
	{
		resetProgressAndMessage(par1Str);
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		if(noMoreProgress)
		{
			mc.displayGuiScreen((GuiScreen) null);
		} else
		{
			drawDefaultBackground();
			drawCenteredString(fontRenderer, progressMessage, width / 2, 70, 16777215);
			drawCenteredString(fontRenderer, workingMessage + " " + currentProgress + "%", width / 2, 90, 16777215);
			super.drawScreen(par1, par2, par3);
		}
	}
	
	@Override public void onNoMoreProgress()
	{
		noMoreProgress = true;
	}
	
	@Override public void resetProgresAndWorkingMessage(String par1Str)
	{
		workingMessage = par1Str;
		setLoadingProgress(0);
	}
	
	@Override public void resetProgressAndMessage(String par1Str)
	{
		progressMessage = par1Str;
		resetProgresAndWorkingMessage("Working...");
	}
	
	@Override public void setLoadingProgress(int par1)
	{
		currentProgress = par1;
	}
}
