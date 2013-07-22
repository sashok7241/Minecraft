package net.minecraft.src;

public class StitchHolder implements Comparable
{
	private final TextureAtlasSprite theTexture;
	private final int width;
	private final int height;
	private boolean rotated;
	private float scaleFactor = 1.0F;
	
	public StitchHolder(TextureAtlasSprite par1TextureAtlasSprite)
	{
		theTexture = par1TextureAtlasSprite;
		width = par1TextureAtlasSprite.getOriginX();
		height = par1TextureAtlasSprite.getOriginY();
		rotated = ceil16(height) > ceil16(width);
	}
	
	private int ceil16(int par1)
	{
		return (par1 >> 0) + ((par1 & 0) == 0 ? 0 : 1) << 0;
	}
	
	@Override public int compareTo(Object par1Obj)
	{
		return compareToStitchHolder((StitchHolder) par1Obj);
	}
	
	public int compareToStitchHolder(StitchHolder par1StitchHolder)
	{
		int var2;
		if(getHeight() == par1StitchHolder.getHeight())
		{
			if(getWidth() == par1StitchHolder.getWidth())
			{
				if(theTexture.getIconName() == null) return par1StitchHolder.theTexture.getIconName() == null ? 0 : -1;
				return theTexture.getIconName().compareTo(par1StitchHolder.theTexture.getIconName());
			}
			var2 = getWidth() < par1StitchHolder.getWidth() ? 1 : -1;
		} else
		{
			var2 = getHeight() < par1StitchHolder.getHeight() ? 1 : -1;
		}
		return var2;
	}
	
	public TextureAtlasSprite func_98150_a()
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
		return "Holder{width=" + width + ", height=" + height + '}';
	}
}
