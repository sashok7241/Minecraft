package net.minecraft.src;

public class StitchHolder implements Comparable
{
	private final Texture theTexture;
	private final int width;
	private final int height;
	private boolean rotated;
	private float scaleFactor = 1.0F;
	
	public StitchHolder(Texture p_i11024_1_)
	{
		theTexture = p_i11024_1_;
		width = p_i11024_1_.getWidth();
		height = p_i11024_1_.getHeight();
		rotated = ceil16(height) > ceil16(width);
	}
	
	private int ceil16(int par1)
	{
		return (par1 >> 0) + ((par1 & 0) == 0 ? 0 : 1) << 0;
	}
	
	@Override public int compareTo(Object p_compareTo_1_)
	{
		return compareToStitchHolder((StitchHolder) p_compareTo_1_);
	}
	
	public int compareToStitchHolder(StitchHolder par1StitchHolder)
	{
		int var2;
		if(getHeight() == par1StitchHolder.getHeight())
		{
			if(getWidth() == par1StitchHolder.getWidth())
			{
				if(theTexture.getTextureName() == null) return par1StitchHolder.theTexture.getTextureName() == null ? 0 : -1;
				return theTexture.getTextureName().compareTo(par1StitchHolder.theTexture.getTextureName());
			}
			var2 = getWidth() < par1StitchHolder.getWidth() ? 1 : -1;
		} else
		{
			var2 = getHeight() < par1StitchHolder.getHeight() ? 1 : -1;
		}
		return var2;
	}
	
	public Texture func_98150_a()
	{
		return theTexture;
	}
	
	public int getHeight()
	{
		return rotated ? ceil16((int) (width * scaleFactor)) : ceil16((int) (height * scaleFactor));
	}
	
	public int getWidth()
	{
		return rotated ? ceil16((int) (height * scaleFactor)) : ceil16((int) (width * scaleFactor));
	}
	
	public boolean isRotated()
	{
		return rotated;
	}
	
	public void rotate()
	{
		rotated = !rotated;
	}
	
	public void setNewDimension(int par1)
	{
		if(width > par1 && height > par1)
		{
			scaleFactor = (float) par1 / (float) Math.min(width, height);
		}
	}
	
	@Override public String toString()
	{
		return "TextureHolder{width=" + width + ", height=" + height + '}';
	}
}
