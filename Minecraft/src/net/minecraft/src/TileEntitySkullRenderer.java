package net.minecraft.src;


public class TileEntitySkullRenderer extends TileEntitySpecialRenderer
{
	private static final ResourceLocation field_110642_c = new ResourceLocation("textures/entity/skeleton/skeleton.png");
	private static final ResourceLocation field_110640_d = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
	private static final ResourceLocation field_110641_e = new ResourceLocation("textures/entity/zombie/zombie.png");
	private static final ResourceLocation field_110639_f = new ResourceLocation("textures/entity/creeper/creeper.png");
	public static TileEntitySkullRenderer skullRenderer;
	private ModelSkeletonHead field_82396_c = new ModelSkeletonHead(0, 0, 64, 32);
	private ModelSkeletonHead field_82395_d = new ModelSkeletonHead(0, 0, 64, 64);
	
	public void func_82393_a(float par1, float par2, float par3, int par4, float par5, int par6, String par7Str)
	{
		ModelSkeletonHead var8 = field_82396_c;
		switch(par6)
		{
			case 0:
			default:
				func_110628_a(field_110642_c);
				break;
			case 1:
				func_110628_a(field_110640_d);
				break;
			case 2:
				func_110628_a(field_110641_e);
				var8 = field_82395_d;
				break;
			case 3:
				ResourceLocation var9 = AbstractClientPlayer.field_110314_b;
				if(par7Str != null && par7Str.length() > 0)
				{
					var9 = AbstractClientPlayer.func_110305_h(par7Str);
					AbstractClientPlayer.func_110304_a(var9, par7Str);
				}
				func_110628_a(var9);
				break;
			case 4:
				func_110628_a(field_110639_f);
		}
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		if(par4 != 1)
		{
			switch(par4)
			{
				case 2:
					GL11.glTranslatef(par1 + 0.5F, par2 + 0.25F, par3 + 0.74F);
					break;
				case 3:
					GL11.glTranslatef(par1 + 0.5F, par2 + 0.25F, par3 + 0.26F);
					par5 = 180.0F;
					break;
				case 4:
					GL11.glTranslatef(par1 + 0.74F, par2 + 0.25F, par3 + 0.5F);
					par5 = 270.0F;
					break;
				case 5:
				default:
					GL11.glTranslatef(par1 + 0.26F, par2 + 0.25F, par3 + 0.5F);
					par5 = 90.0F;
			}
		} else
		{
			GL11.glTranslatef(par1 + 0.5F, par2, par3 + 0.5F);
		}
		float var10 = 0.0625F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		var8.render((Entity) null, 0.0F, 0.0F, 0.0F, par5, 0.0F, var10);
		GL11.glPopMatrix();
	}
	
	@Override public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		renderTileEntitySkullAt((TileEntitySkull) par1TileEntity, par2, par4, par6, par8);
	}
	
	public void renderTileEntitySkullAt(TileEntitySkull par1TileEntitySkull, double par2, double par4, double par6, float par8)
	{
		func_82393_a((float) par2, (float) par4, (float) par6, par1TileEntitySkull.getBlockMetadata() & 7, par1TileEntitySkull.func_82119_b() * 360 / 16.0F, par1TileEntitySkull.getSkullType(), par1TileEntitySkull.getExtraType());
	}
	
	@Override public void setTileEntityRenderer(TileEntityRenderer par1TileEntityRenderer)
	{
		super.setTileEntityRenderer(par1TileEntityRenderer);
		skullRenderer = this;
	}
}
