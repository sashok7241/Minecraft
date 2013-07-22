package net.minecraft.src;

public interface Icon
{
	String getIconName();
	
	float getInterpolatedU(double var1);
	
	float getInterpolatedV(double var1);
	
	float getMaxU();
	
	float getMaxV();
	
	float getMinU();
	
	float getMinV();
	
	int getOriginX();
	
	int getOriginY();
	
	int getSheetHeight();
	
	int getSheetWidth();
}
