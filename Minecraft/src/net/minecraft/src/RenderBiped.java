package net.minecraft.src;


public class RenderBiped extends RenderLiving
{
	protected ModelBiped modelBipedMain;
	protected float field_77070_b;
	protected ModelBiped field_82423_g;
	protected ModelBiped field_82425_h;
	private static final String[] bipedArmorFilenamePrefix = new String[] { "cloth", "chain", "iron", "diamond", "gold" };
	
	public RenderBiped(ModelBiped p_i3202_1_, float p_i3202_2_)
	{
		this(p_i3202_1_, p_i3202_2_, 1.0F);
	}
	
	public RenderBiped(ModelBiped p_i3203_1_, float p_i3203_2_, float p_i3203_3_)
	{
		super(p_i3203_1_, p_i3203_2_);
		modelBipedMain = p_i3203_1_;
		field_77070_b = p_i3203_3_;
		func_82421_b();
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		doRenderLiving((EntityLiving) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		float var10 = 1.0F;
		GL11.glColor3f(var10, var10, var10);
		ItemStack var11 = par1EntityLiving.getHeldItem();
		func_82420_a(par1EntityLiving, var11);
		double var12 = par4 - par1EntityLiving.yOffset;
		if(par1EntityLiving.isSneaking() && !(par1EntityLiving instanceof EntityPlayerSP))
		{
			var12 -= 0.125D;
		}
		super.doRenderLiving(par1EntityLiving, par2, var12, par6, par8, par9);
		field_82423_g.aimedBow = field_82425_h.aimedBow = modelBipedMain.aimedBow = false;
		field_82423_g.isSneak = field_82425_h.isSneak = modelBipedMain.isSneak = false;
		field_82423_g.heldItemRight = field_82425_h.heldItemRight = modelBipedMain.heldItemRight = 0;
	}
	
	@Override protected void func_82408_c(EntityLiving par1EntityLivingBase, int par2, float par3)
	{
		ItemStack var4 = par1EntityLivingBase.getCurrentArmor(3 - par2);
		if(var4 != null)
		{
			Item var5 = var4.getItem();
			if(var5 instanceof ItemArmor)
			{
				ItemArmor var6 = (ItemArmor) var5;
				loadTexture("/armor/" + bipedArmorFilenamePrefix[var6.renderIndex] + "_" + (par2 == 2 ? 2 : 1) + "_b.png");
				float var7 = 1.0F;
				GL11.glColor3f(var7, var7, var7);
			}
		}
	}
	
	protected void func_82420_a(EntityLiving par1EntityLiving, ItemStack par2ItemStack)
	{
		field_82423_g.heldItemRight = field_82425_h.heldItemRight = modelBipedMain.heldItemRight = par2ItemStack != null ? 1 : 0;
		field_82423_g.isSneak = field_82425_h.isSneak = modelBipedMain.isSneak = par1EntityLiving.isSneaking();
	}
	
	protected void func_82421_b()
	{
		field_82423_g = new ModelBiped(1.0F);
		field_82425_h = new ModelBiped(0.5F);
	}
	
	protected void func_82422_c()
	{
		GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
	}
	
	@Override protected void renderEquippedItems(EntityLiving par1EntityLivingBase, float par2)
	{
		float var3 = 1.0F;
		GL11.glColor3f(var3, var3, var3);
		super.renderEquippedItems(par1EntityLivingBase, par2);
		ItemStack var4 = par1EntityLivingBase.getHeldItem();
		ItemStack var5 = par1EntityLivingBase.getCurrentArmor(3);
		float var6;
		if(var5 != null)
		{
			GL11.glPushMatrix();
			modelBipedMain.bipedHead.postRender(0.0625F);
			if(var5.getItem().itemID < 256)
			{
				if(RenderBlocks.renderItemIn3d(Block.blocksList[var5.itemID].getRenderType()))
				{
					var6 = 0.625F;
					GL11.glTranslatef(0.0F, -0.25F, 0.0F);
					GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(var6, -var6, -var6);
				}
				renderManager.itemRenderer.renderItem(par1EntityLivingBase, var5, 0);
			} else if(var5.getItem().itemID == Item.skull.itemID)
			{
				var6 = 1.0625F;
				GL11.glScalef(var6, -var6, -var6);
				String var7 = "";
				if(var5.hasTagCompound() && var5.getTagCompound().hasKey("SkullOwner"))
				{
					var7 = var5.getTagCompound().getString("SkullOwner");
				}
				TileEntitySkullRenderer.skullRenderer.func_82393_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, var5.getItemDamage(), var7);
			}
			GL11.glPopMatrix();
		}
		if(var4 != null)
		{
			GL11.glPushMatrix();
			if(mainModel.isChild)
			{
				var6 = 0.5F;
				GL11.glTranslatef(0.0F, 0.625F, 0.0F);
				GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
				GL11.glScalef(var6, var6, var6);
			}
			modelBipedMain.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			if(var4.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var4.itemID].getRenderType()))
			{
				var6 = 0.5F;
				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
				var6 *= 0.75F;
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(-var6, -var6, var6);
			} else if(var4.itemID == Item.bow.itemID)
			{
				var6 = 0.625F;
				GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
				GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(var6, -var6, var6);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else if(Item.itemsList[var4.itemID].isFull3D())
			{
				var6 = 0.625F;
				if(Item.itemsList[var4.itemID].shouldRotateAroundWhenRendering())
				{
					GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(0.0F, -0.125F, 0.0F);
				}
				func_82422_c();
				GL11.glScalef(var6, -var6, var6);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else
			{
				var6 = 0.375F;
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glScalef(var6, var6, var6);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}
			renderManager.itemRenderer.renderItem(par1EntityLivingBase, var4, 0);
			if(var4.getItem().requiresMultipleRenderPasses())
			{
				renderManager.itemRenderer.renderItem(par1EntityLivingBase, var4, 1);
			}
			GL11.glPopMatrix();
		}
	}
	
	@Override protected int shouldRenderPass(EntityLiving par1EntityLivingBase, int par2, float par3)
	{
		ItemStack var4 = par1EntityLivingBase.getCurrentArmor(3 - par2);
		if(var4 != null)
		{
			Item var5 = var4.getItem();
			if(var5 instanceof ItemArmor)
			{
				ItemArmor var6 = (ItemArmor) var5;
				loadTexture("/armor/" + bipedArmorFilenamePrefix[var6.renderIndex] + "_" + (par2 == 2 ? 2 : 1) + ".png");
				ModelBiped var7 = par2 == 2 ? field_82425_h : field_82423_g;
				var7.bipedHead.showModel = par2 == 0;
				var7.bipedHeadwear.showModel = par2 == 0;
				var7.bipedBody.showModel = par2 == 1 || par2 == 2;
				var7.bipedRightArm.showModel = par2 == 1;
				var7.bipedLeftArm.showModel = par2 == 1;
				var7.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
				var7.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
				setRenderPassModel(var7);
				if(var7 != null)
				{
					var7.onGround = mainModel.onGround;
				}
				if(var7 != null)
				{
					var7.isRiding = mainModel.isRiding;
				}
				if(var7 != null)
				{
					var7.isChild = mainModel.isChild;
				}
				float var8 = 1.0F;
				if(var6.getArmorMaterial() == EnumArmorMaterial.CLOTH)
				{
					int var9 = var6.getColor(var4);
					float var10 = (var9 >> 16 & 255) / 255.0F;
					float var11 = (var9 >> 8 & 255) / 255.0F;
					float var12 = (var9 & 255) / 255.0F;
					GL11.glColor3f(var8 * var10, var8 * var11, var8 * var12);
					if(var4.isItemEnchanted()) return 31;
					return 16;
				}
				GL11.glColor3f(var8, var8, var8);
				if(var4.isItemEnchanted()) return 15;
				return 1;
			}
		}
		return -1;
	}
}
