package net.minecraft.src;

public class Rect2i
{
	private int rectX;
	private int rectY;
	private int rectWidth;
	private int rectHeight;
	
	public Rect2i(int p_i9006_1_, int p_i9006_2_, int p_i9006_3_, int p_i9006_4_)
	{
		rectX = p_i9006_1_;
		rectY = p_i9006_2_;
		rectWidth = p_i9006_3_;
		rectHeight = p_i9006_4_;
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
	
	public Rect2i intersection(Rect2i p_94156_1_)
	{
		int var2 = rectX;
		int var3 = rectY;
		int var4 = rectX + rectWidth;
		int var5 = rectY + rectHeight;
		int var6 = p_94156_1_.getRectX();
		int var7 = p_94156_1_.getRectY();
		int var8 = var6 + p_94156_1_.getRectWidth();
		int var9 = var7 + p_94156_1_.getRectHeight();
		rectX = Math.max(var2, var6);
		rectY = Math.max(var3, var7);
		rectWidth = Math.max(0, Math.min(var4, var8) - rectX);
		rectHeight = Math.max(0, Math.min(var5, var9) - rectY);
		return this;
	}
}
