package net.minecraft.src;

import java.util.Map;

public class RenderBiped extends RenderLiving
{
	protected ModelBiped modelBipedMain;
	protected float field_77070_b;
	protected ModelBiped field_82423_g;
	protected ModelBiped field_82425_h;
	private static final Map field_110859_k = Maps.newHashMap();
	private static final String[] bipedArmorFilenamePrefix = new String[] { "leather", "chainmail", "iron", "diamond", "gold" };
	
	public RenderBiped(ModelBiped par1ModelBiped, float par2)
	{
		this(par1ModelBiped, par2, 1.0F);
	}
	
	public RenderBiped(ModelBiped par1ModelBiped, float par2, float par3)
	{
		super(par1ModelBiped, par2);
		modelBipedMain = par1ModelBiped;
		field_77070_b = par3;
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
		if(par1EntityLiving.isSneaking())
		{
			var12 -= 0.125D;
		}
		super.doRenderLiving(par1EntityLiving, par2, var12, par6, par8, par9);
		field_82423_g.aimedBow = field_82425_h.aimedBow = modelBipedMain.aimedBow = false;
		field_82423_g.isSneak = field_82425_h.isSneak = modelBipedMain.isSneak = false;
		field_82423_g.heldItemRight = field_82425_h.heldItemRight = modelBipedMain.heldItemRight = 0;
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110856_a((EntityLiving) par1Entity);
	}
	
	protected ResourceLocation func_110856_a(EntityLiving par1EntityLiving)
	{
		return null;
	}
	
	protected void func_130005_c(EntityLiving par1EntityLiving, float par2)
	{
		float var3 = 1.0F;
		GL11.glColor3f(var3, var3, var3);
		super.renderEquippedItems(par1EntityLiving, par2);
		ItemStack var4 = par1EntityLiving.getHeldItem();
		ItemStack var5 = par1EntityLiving.func_130225_q(3);
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
				renderManager.itemRenderer.renderItem(par1EntityLiving, var5, 0);
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
			renderManager.itemRenderer.renderItem(par1EntityLiving, var4, 0);
			if(var4.getItem().requiresMultipleRenderPasses())
			{
				renderManager.itemRenderer.renderItem(par1EntityLiving, var4, 1);
			}
			GL11.glPopMatrix();
		}
	}
	
	protected int func_130006_a(EntityLiving par1EntityLiving, int par2, float par3)
	{
		ItemStack var4 = par1EntityLiving.func_130225_q(3 - par2);
		if(var4 != null)
		{
			Item var5 = var4.getItem();
			if(var5 instanceof ItemArmor)
			{
				ItemArmor var6 = (ItemArmor) var5;
				func_110776_a(func_110857_a(var6, par2));
				ModelBiped var7 = par2 == 2 ? field_82425_h : field_82423_g;
				var7.bipedHead.showModel = par2 == 0;
				var7.bipedHeadwear.showModel = par2 == 0;
				var7.bipedBody.showModel = par2 == 1 || par2 == 2;
				var7.bipedRightArm.showModel = par2 == 1;
				var7.bipedLeftArm.showModel = par2 == 1;
				var7.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
				var7.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
				setRenderPassModel(var7);
				var7.onGround = mainModel.onGround;
				var7.isRiding = mainModel.isRiding;
				var7.isChild = mainModel.isChild;
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
	
	protected void func_130013_c(EntityLiving par1EntityLiving, int par2, float par3)
	{
		ItemStack var4 = par1EntityLiving.func_130225_q(3 - par2);
		if(var4 != null)
		{
			Item var5 = var4.getItem();
			if(var5 instanceof ItemArmor)
			{
				func_110776_a(func_110858_a((ItemArmor) var5, par2, "overlay"));
				float var6 = 1.0F;
				GL11.glColor3f(var6, var6, var6);
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
	
	protected void func_82439_b(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		func_130013_c((EntityLiving) par1EntityLivingBase, par2, par3);
	}
	
	@Override protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
	{
		func_130005_c((EntityLiving) par1EntityLivingBase, par2);
	}
	
	@Override public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
	{
		doRenderLiving((EntityLiving) par1EntityLivingBase, par2, par4, par6, par8, par9);
	}
	
	@Override protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return func_130006_a((EntityLiving) par1EntityLivingBase, par2, par3);
	}
	
	public static ResourceLocation func_110857_a(ItemArmor par0ItemArmor, int par1)
	{
		return func_110858_a(par0ItemArmor, par1, (String) null);
	}
	
	public static ResourceLocation func_110858_a(ItemArmor par0ItemArmor, int par1, String par2Str)
	{
		String var3 = String.format("textures/models/armor/%s_layer_%d%s.png", new Object[] { bipedArmorFilenamePrefix[par0ItemArmor.renderIndex], Integer.valueOf(par1 == 2 ? 2 : 1), par2Str == null ? "" : String.format("_%s", new Object[] { par2Str }) });
		ResourceLocation var4 = (ResourceLocation) field_110859_k.get(var3);
		if(var4 == null)
		{
			var4 = new ResourceLocation(var3);
			field_110859_k.put(var3, var4);
		}
		return var4;
	}
}
