package net.minecraft.src;

public class TexturedQuad
{
	public PositionTextureVertex[] vertexPositions;
	public int nVertices;
	private boolean invertNormal;
	
	public TexturedQuad(PositionTextureVertex[] par1ArrayOfPositionTextureVertex)
	{
		nVertices = 0;
		invertNormal = false;
		vertexPositions = par1ArrayOfPositionTextureVertex;
		nVertices = par1ArrayOfPositionTextureVertex.length;
	}
	
	public TexturedQuad(PositionTextureVertex[] par1ArrayOfPositionTextureVertex, int par2, int par3, int par4, int par5, float par6, float par7)
	{
		this(par1ArrayOfPositionTextureVertex);
		float var8 = 0.0F / par6;
		float var9 = 0.0F / par7;
		par1ArrayOfPositionTextureVertex[0] = par1ArrayOfPositionTextureVertex[0].setTexturePosition(par4 / par6 - var8, par3 / par7 + var9);
		par1ArrayOfPositionTextureVertex[1] = par1ArrayOfPositionTextureVertex[1].setTexturePosition(par2 / par6 + var8, par3 / par7 + var9);
		par1ArrayOfPositionTextureVertex[2] = par1ArrayOfPositionTextureVertex[2].setTexturePosition(par2 / par6 + var8, par5 / par7 - var9);
		par1ArrayOfPositionTextureVertex[3] = par1ArrayOfPositionTextureVertex[3].setTexturePosition(par4 / par6 - var8, par5 / par7 - var9);
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
