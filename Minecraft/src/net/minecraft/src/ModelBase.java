package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class ModelBase
{
	public float onGround;
	public boolean isRiding = false;
	public List boxList = new ArrayList();
	public boolean isChild = true;
	private Map modelTextureMap = new HashMap();
	public int textureWidth = 64;
	public int textureHeight = 32;
	
	public ModelRenderer getRandomModelBox(Random par1Random)
	{
		return (ModelRenderer) boxList.get(par1Random.nextInt(boxList.size()));
	}
	
	public TextureOffset getTextureOffset(String par1Str)
	{
		return (TextureOffset) modelTextureMap.get(par1Str);
	}
	
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
	}
	
	public void setLivingAnimations(EntityLiving par1EntityLivingBase, float par2, float par3, float par4)
	{
	}
	
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
	}
	
	protected void setTextureOffset(String par1Str, int par2, int par3)
	{
		modelTextureMap.put(par1Str, new TextureOffset(par2, par3));
	}
}
