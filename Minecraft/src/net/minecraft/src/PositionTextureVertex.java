package net.minecraft.src;

public class PositionTextureVertex
{
	public Vec3 vector3D;
	public float texturePositionX;
	public float texturePositionY;
	
	public PositionTextureVertex(float p_i3140_1_, float p_i3140_2_, float p_i3140_3_, float p_i3140_4_, float p_i3140_5_)
	{
		this(Vec3.createVectorHelper(p_i3140_1_, p_i3140_2_, p_i3140_3_), p_i3140_4_, p_i3140_5_);
	}
	
	public PositionTextureVertex(PositionTextureVertex p_i3141_1_, float p_i3141_2_, float p_i3141_3_)
	{
		vector3D = p_i3141_1_.vector3D;
		texturePositionX = p_i3141_2_;
		texturePositionY = p_i3141_3_;
	}
	
	public PositionTextureVertex(Vec3 p_i3142_1_, float p_i3142_2_, float p_i3142_3_)
	{
		vector3D = p_i3142_1_;
		texturePositionX = p_i3142_2_;
		texturePositionY = p_i3142_3_;
	}
	
	public PositionTextureVertex setTexturePosition(float par1, float par2)
	{
		return new PositionTextureVertex(this, par1, par2);
	}
}
