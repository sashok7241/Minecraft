package net.minecraft.src;

public class StructureBoundingBox
{
	public int minX;
	public int minY;
	public int minZ;
	public int maxX;
	public int maxY;
	public int maxZ;
	
	public StructureBoundingBox()
	{
	}
	
	public StructureBoundingBox(int par1, int par2, int par3, int par4)
	{
		minX = par1;
		minZ = par2;
		maxX = par3;
		maxZ = par4;
		minY = 1;
		maxY = 512;
	}
	
	public StructureBoundingBox(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		minX = par1;
		minY = par2;
		minZ = par3;
		maxX = par4;
		maxY = par5;
		maxZ = par6;
	}
	
	public StructureBoundingBox(StructureBoundingBox par1StructureBoundingBox)
	{
		minX = par1StructureBoundingBox.minX;
		minY = par1StructureBoundingBox.minY;
		minZ = par1StructureBoundingBox.minZ;
		maxX = par1StructureBoundingBox.maxX;
		maxY = par1StructureBoundingBox.maxY;
		maxZ = par1StructureBoundingBox.maxZ;
	}
	
	public void expandTo(StructureBoundingBox par1StructureBoundingBox)
	{
		minX = Math.min(minX, par1StructureBoundingBox.minX);
		minY = Math.min(minY, par1StructureBoundingBox.minY);
		minZ = Math.min(minZ, par1StructureBoundingBox.minZ);
		maxX = Math.max(maxX, par1StructureBoundingBox.maxX);
		maxY = Math.max(maxY, par1StructureBoundingBox.maxY);
		maxZ = Math.max(maxZ, par1StructureBoundingBox.maxZ);
	}
	
	public int getCenterX()
	{
		return minX + (maxX - minX + 1) / 2;
	}
	
	public int getCenterY()
	{
		return minY + (maxY - minY + 1) / 2;
	}
	
	public int getCenterZ()
	{
		return minZ + (maxZ - minZ + 1) / 2;
	}
	
	public int getXSize()
	{
		return maxX - minX + 1;
	}
	
	public int getYSize()
	{
		return maxY - minY + 1;
	}
	
	public int getZSize()
	{
		return maxZ - minZ + 1;
	}
	
	public boolean intersectsWith(int par1, int par2, int par3, int par4)
	{
		return maxX >= par1 && minX <= par3 && maxZ >= par2 && minZ <= par4;
	}
	
	public boolean intersectsWith(StructureBoundingBox par1StructureBoundingBox)
	{
		return maxX >= par1StructureBoundingBox.minX && minX <= par1StructureBoundingBox.maxX && maxZ >= par1StructureBoundingBox.minZ && minZ <= par1StructureBoundingBox.maxZ && maxY >= par1StructureBoundingBox.minY && minY <= par1StructureBoundingBox.maxY;
	}
	
	public boolean isVecInside(int par1, int par2, int par3)
	{
		return par1 >= minX && par1 <= maxX && par3 >= minZ && par3 <= maxZ && par2 >= minY && par2 <= maxY;
	}
	
	public void offset(int par1, int par2, int par3)
	{
		minX += par1;
		minY += par2;
		minZ += par3;
		maxX += par1;
		maxY += par2;
		maxZ += par3;
	}
	
	@Override public String toString()
	{
		return "(" + minX + ", " + minY + ", " + minZ + "; " + maxX + ", " + maxY + ", " + maxZ + ")";
	}
	
	public static StructureBoundingBox getComponentToAddBoundingBox(int par0, int par1, int par2, int par3, int par4, int par5, int par6, int par7, int par8, int par9)
	{
		switch(par9)
		{
			case 0:
				return new StructureBoundingBox(par0 + par3, par1 + par4, par2 + par5, par0 + par6 - 1 + par3, par1 + par7 - 1 + par4, par2 + par8 - 1 + par5);
			case 1:
				return new StructureBoundingBox(par0 - par8 + 1 + par5, par1 + par4, par2 + par3, par0 + par5, par1 + par7 - 1 + par4, par2 + par6 - 1 + par3);
			case 2:
				return new StructureBoundingBox(par0 + par3, par1 + par4, par2 - par8 + 1 + par5, par0 + par6 - 1 + par3, par1 + par7 - 1 + par4, par2 + par5);
			case 3:
				return new StructureBoundingBox(par0 + par5, par1 + par4, par2 + par3, par0 + par8 - 1 + par5, par1 + par7 - 1 + par4, par2 + par6 - 1 + par3);
			default:
				return new StructureBoundingBox(par0 + par3, par1 + par4, par2 + par5, par0 + par6 - 1 + par3, par1 + par7 - 1 + par4, par2 + par8 - 1 + par5);
		}
	}
	
	public static StructureBoundingBox getNewBoundingBox()
	{
		return new StructureBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
	}
}
