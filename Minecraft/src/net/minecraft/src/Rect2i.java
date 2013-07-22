package net.minecraft.src;

public class Rect2i
{
	private int rectX;
	private int rectY;
	private int rectWidth;
	private int rectHeight;
	
	public Rect2i(int par1, int par2, int par3, int par4)
	{
		rectX = par1;
		rectY = par2;
		rectWidth = par3;
		rectHeight = par4;
	}
	
	public int getRectHeight()
	{
		return rectHeight;
	}
	
	public int getRectWidth()
	{
		return rectWidth;
	}
	
	public int getRectX()
	{
		return rectX;
	}
	
	public int getRectY()
	{
		return rectY;
	}
	
	public Rect2i intersection(Rect2i par1Rect2i)
	{
		int var2 = rectX;
		int var3 = rectY;
		int var4 = rectX + rectWidth;
		int var5 = rectY + rectHeight;
		int var6 = par1Rect2i.getRectX();
		int var7 = par1Rect2i.getRectY();
		int var8 = var6 + par1Rect2i.getRectWidth();
		int var9 = var7 + par1Rect2i.getRectHeight();
		rectX = Math.max(var2, var6);
		rectY = Math.max(var3, var7);
		rectWidth = Math.max(0, Math.min(var4, var8) - rectX);
		rectHeight = Math.max(0, Math.min(var5, var9) - rectY);
		return this;
	}
}
