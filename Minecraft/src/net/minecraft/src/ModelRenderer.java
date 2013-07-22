package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class ModelRenderer
{
	public float textureWidth;
	public float textureHeight;
	private int textureOffsetX;
	private int textureOffsetY;
	public float rotationPointX;
	public float rotationPointY;
	public float rotationPointZ;
	public float rotateAngleX;
	public float rotateAngleY;
	public float rotateAngleZ;
	private boolean compiled;
	private int displayList;
	public boolean mirror;
	public boolean showModel;
	public boolean isHidden;
	public List cubeList;
	public List childModels;
	public final String boxName;
	private ModelBase baseModel;
	public float offsetX;
	public float offsetY;
	public float offsetZ;
	
	public ModelRenderer(ModelBase par1ModelBase)
	{
		this(par1ModelBase, (String) null);
	}
	
	public ModelRenderer(ModelBase par1ModelBase, int par2, int par3)
	{
		this(par1ModelBase);
		setTextureOffset(par2, par3);
	}
	
	public ModelRenderer(ModelBase par1ModelBase, String par2Str)
	{
		textureWidth = 64.0F;
		textureHeight = 32.0F;
		compiled = false;
		displayList = 0;
		mirror = false;
		showModel = true;
		isHidden = false;
		cubeList = new ArrayList();
		baseModel = par1ModelBase;
		par1ModelBase.boxList.add(this);
		boxName = par2Str;
		setTextureSize(par1ModelBase.textureWidth, par1ModelBase.textureHeight);
	}
	
	public ModelRenderer addBox(float par1, float par2, float par3, int par4, int par5, int par6)
	{
		cubeList.add(new ModelBox(this, textureOffsetX, textureOffsetY, par1, par2, par3, par4, par5, par6, 0.0F));
		return this;
	}
	
	public void addBox(float par1, float par2, float par3, int par4, int par5, int par6, float par7)
	{
		cubeList.add(new ModelBox(this, textureOffsetX, textureOffsetY, par1, par2, par3, par4, par5, par6, par7));
	}
	
	public ModelRenderer addBox(String par1Str, float par2, float par3, float par4, int par5, int par6, int par7)
	{
		par1Str = boxName + "." + par1Str;
		TextureOffset var8 = baseModel.getTextureOffset(par1Str);
		setTextureOffset(var8.textureOffsetX, var8.textureOffsetY);
		cubeList.add(new ModelBox(this, textureOffsetX, textureOffsetY, par2, par3, par4, par5, par6, par7, 0.0F).func_78244_a(par1Str));
		return this;
	}
	
	public void addChild(ModelRenderer par1ModelRenderer)
	{
		if(childModels == null)
		{
			childModels = new ArrayList();
		}
		childModels.add(par1ModelRenderer);
	}
	
	private void compileDisplayList(float par1)
	{
		displayList = GLAllocation.generateDisplayLists(1);
		GL11.glNewList(displayList, GL11.GL_COMPILE);
		Tessellator var2 = Tessellator.instance;
		for(int var3 = 0; var3 < cubeList.size(); ++var3)
		{
			((ModelBox) cubeList.get(var3)).render(var2, par1);
		}
		GL11.glEndList();
		compiled = true;
	}
	
	public void postRender(float par1)
	{
		if(!isHidden)
		{
			if(showModel)
			{
				if(!compiled)
				{
					compileDisplayList(par1);
				}
				if(rotateAngleX == 0.0F && rotateAngleY == 0.0F && rotateAngleZ == 0.0F)
				{
					if(rotationPointX != 0.0F || rotationPointY != 0.0F || rotationPointZ != 0.0F)
					{
						GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);
					}
				} else
				{
					GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);
					if(rotateAngleZ != 0.0F)
					{
						GL11.glRotatef(rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
					}
					if(rotateAngleY != 0.0F)
					{
						GL11.glRotatef(rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
					}
					if(rotateAngleX != 0.0F)
					{
						GL11.glRotatef(rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
					}
				}
			}
		}
	}
	
	public void render(float par1)
	{
		if(!isHidden)
		{
			if(showModel)
			{
				if(!compiled)
				{
					compileDisplayList(par1);
				}
				GL11.glTranslatef(offsetX, offsetY, offsetZ);
				int var2;
				if(rotateAngleX == 0.0F && rotateAngleY == 0.0F && rotateAngleZ == 0.0F)
				{
					if(rotationPointX == 0.0F && rotationPointY == 0.0F && rotationPointZ == 0.0F)
					{
						GL11.glCallList(displayList);
						if(childModels != null)
						{
							for(var2 = 0; var2 < childModels.size(); ++var2)
							{
								((ModelRenderer) childModels.get(var2)).render(par1);
							}
						}
					} else
					{
						GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);
						GL11.glCallList(displayList);
						if(childModels != null)
						{
							for(var2 = 0; var2 < childModels.size(); ++var2)
							{
								((ModelRenderer) childModels.get(var2)).render(par1);
							}
						}
						GL11.glTranslatef(-rotationPointX * par1, -rotationPointY * par1, -rotationPointZ * par1);
					}
				} else
				{
					GL11.glPushMatrix();
					GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);
					if(rotateAngleZ != 0.0F)
					{
						GL11.glRotatef(rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
					}
					if(rotateAngleY != 0.0F)
					{
						GL11.glRotatef(rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
					}
					if(rotateAngleX != 0.0F)
					{
						GL11.glRotatef(rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
					}
					GL11.glCallList(displayList);
					if(childModels != null)
					{
						for(var2 = 0; var2 < childModels.size(); ++var2)
						{
							((ModelRenderer) childModels.get(var2)).render(par1);
						}
					}
					GL11.glPopMatrix();
				}
				GL11.glTranslatef(-offsetX, -offsetY, -offsetZ);
			}
		}
	}
	
	public void renderWithRotation(float par1)
	{
		if(!isHidden)
		{
			if(showModel)
			{
				if(!compiled)
				{
					compileDisplayList(par1);
				}
				GL11.glPushMatrix();
				GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);
				if(rotateAngleY != 0.0F)
				{
					GL11.glRotatef(rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
				}
				if(rotateAngleX != 0.0F)
				{
					GL11.glRotatef(rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
				}
				if(rotateAngleZ != 0.0F)
				{
					GL11.glRotatef(rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
				}
				GL11.glCallList(displayList);
				GL11.glPopMatrix();
			}
		}
	}
	
	public void setRotationPoint(float par1, float par2, float par3)
	{
		rotationPointX = par1;
		rotationPointY = par2;
		rotationPointZ = par3;
	}
	
	public ModelRenderer setTextureOffset(int par1, int par2)
	{
		textureOffsetX = par1;
		textureOffsetY = par2;
		return this;
	}
	
	public ModelRenderer setTextureSize(int par1, int par2)
	{
		textureWidth = par1;
		textureHeight = par2;
		return this;
	}
}
