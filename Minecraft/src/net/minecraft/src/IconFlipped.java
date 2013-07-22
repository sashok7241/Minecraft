package net.minecraft.src;

public class IconFlipped implements Icon
{
	private final Icon baseIcon;
	private final boolean flipU;
	private final boolean flipV;
	
	public IconFlipped(Icon p_i10047_1_, boolean p_i10047_2_, boolean p_i10047_3_)
	{
		baseIcon = p_i10047_1_;
		flipU = p_i10047_2_;
		flipV = p_i10047_3_;
	}
	
	@Override public String getIconName()
	{
		return baseIcon.getIconName();
	}
	
	@Override public float getInterpolatedU(double par1)
	{
		float var3 = getMaxU() - getMinU();
		return getMinU() + var3 * ((float) par1 / 16.0F);
	}
	
	@Override public float getInterpolatedV(double par1)
	{
		float var3 = getMaxV() - getMinV();
		return getMinV() + var3 * ((float) par1 / 16.0F);
	}
	
	@Override public float getMaxU()
	{
		return flipU ? baseIcon.getMinU() : baseIcon.getMaxU();
	}
	
	@Override public float getMaxV()
	{
		return flipV ? baseIcon.getMinV() : baseIcon.getMaxV();
	}
	
	@Override public float getMinU()
	{
		return flipU ? baseIcon.getMaxU() : baseIcon.getMinU();
	}
	
	@Override public float getMinV()
	{
		return flipV ? baseIcon.getMinV() : baseIcon.getMinV();
	}
	
	@Override public int getOriginX()
	{
		return baseIcon.getOriginX();
	}
	
	@Override public int getOriginY()
	{
		return baseIcon.getOriginY();
	}
	
	@Override public int getSheetHeight()
	{
		return baseIcon.getSheetHeight();
	}
	
	@Override public int getSheetWidth()
	{
		return baseIcon.getSheetWidth();
	}
}
