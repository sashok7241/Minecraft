package net.minecraft.src;

public class GuiProgress extends GuiScreen implements IProgressUpdate
{
	private String progressMessage = "";
	private String workingMessage = "";
	private int currentProgress = 0;
	private boolean noMoreProgress;
	
	@Override public void displayProgressMessage(String p_73720_1_)
	{
		resetProgressAndMessage(p_73720_1_);
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
	
	@Override public void resetProgresAndWorkingMessage(String p_73719_1_)
	{
		workingMessage = p_73719_1_;
		setLoadingProgress(0);
	}
	
	@Override public void resetProgressAndMessage(String par1Str)
	{
		progressMessage = par1Str;
		resetProgresAndWorkingMessage("Working...");
	}
	
	@Override public void setLoadingProgress(int p_73718_1_)
	{
		currentProgress = p_73718_1_;
	}
}
