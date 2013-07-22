package net.minecraft.src;

public class Frustrum implements ICamera
{
	private ClippingHelper clippingHelper = ClippingHelperImpl.getInstance();
	private double xPosition;
	private double yPosition;
	private double zPosition;
	
	@Override public boolean isBoundingBoxInFrustum(AxisAlignedBB par1AxisAlignedBB)
	{
		return isBoxInFrustum(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
	}
	
	public boolean isBoxInFrustum(double par1, double par3, double par5, double par7, double par9, double par11)
	{
		return clippingHelper.isBoxInFrustum(par1 - xPosition, par3 - yPosition, par5 - zPosition, par7 - xPosition, par9 - yPosition, par11 - zPosition);
	}
	
	@Override public void setPosition(double par1, double par3, double par5)
	{
		xPosition = par1;
		yPosition = par3;
		zPosition = par5;
	}
}
