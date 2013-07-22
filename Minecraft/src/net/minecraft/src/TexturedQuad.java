package net.minecraft.src;

public class TexturedQuad
{
	public PositionTextureVertex[] vertexPositions;
	public int nVertices;
	private boolean invertNormal;
	
	public TexturedQuad(PositionTextureVertex[] p_i3136_1_)
	{
		nVertices = 0;
		invertNormal = false;
		vertexPositions = p_i3136_1_;
		nVertices = p_i3136_1_.length;
	}
	
	public TexturedQuad(PositionTextureVertex[] p_i3137_1_, int p_i3137_2_, int p_i3137_3_, int p_i3137_4_, int p_i3137_5_, float p_i3137_6_, float p_i3137_7_)
	{
		this(p_i3137_1_);
		float var8 = 0.0F / p_i3137_6_;
		float var9 = 0.0F / p_i3137_7_;
		p_i3137_1_[0] = p_i3137_1_[0].setTexturePosition(p_i3137_4_ / p_i3137_6_ - var8, p_i3137_3_ / p_i3137_7_ + var9);
		p_i3137_1_[1] = p_i3137_1_[1].setTexturePosition(p_i3137_2_ / p_i3137_6_ + var8, p_i3137_3_ / p_i3137_7_ + var9);
		p_i3137_1_[2] = p_i3137_1_[2].setTexturePosition(p_i3137_2_ / p_i3137_6_ + var8, p_i3137_5_ / p_i3137_7_ - var9);
		p_i3137_1_[3] = p_i3137_1_[3].setTexturePosition(p_i3137_4_ / p_i3137_6_ - var8, p_i3137_5_ / p_i3137_7_ - var9);
	}
	
	public void draw(Tessellator par1Tessellator, float par2)
	{
		Vec3 var3 = vertexPositions[1].vector3D.subtract(vertexPositions[0].vector3D);
		Vec3 var4 = vertexPositions[1].vector3D.subtract(vertexPositions[2].vector3D);
		Vec3 var5 = var4.crossProduct(var3).normalize();
		par1Tessellator.startDrawingQuads();
		if(invertNormal)
		{
			par1Tessellator.setNormal(-((float) var5.xCoord), -((float) var5.yCoord), -((float) var5.zCoord));
		} else
		{
			par1Tessellator.setNormal((float) var5.xCoord, (float) var5.yCoord, (float) var5.zCoord);
		}
		for(int var6 = 0; var6 < 4; ++var6)
		{
			PositionTextureVertex var7 = vertexPositions[var6];
			par1Tessellator.addVertexWithUV((float) var7.vector3D.xCoord * par2, (float) var7.vector3D.yCoord * par2, (float) var7.vector3D.zCoord * par2, var7.texturePositionX, var7.texturePositionY);
		}
		par1Tessellator.draw();
	}
	
	public void flipFace()
	{
		PositionTextureVertex[] var1 = new PositionTextureVertex[vertexPositions.length];
		for(int var2 = 0; var2 < vertexPositions.length; ++var2)
		{
			var1[var2] = vertexPositions[vertexPositions.length - var2 - 1];
		}
		vertexPositions = var1;
	}
}
