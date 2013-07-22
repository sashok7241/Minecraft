package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class ItemMonsterPlacer extends Item
{
	private Icon theIcon;
	
	public ItemMonsterPlacer(int p_i3671_1_)
	{
		super(p_i3671_1_);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		EntityEggInfo var3 = (EntityEggInfo) EntityList.entityEggs.get(Integer.valueOf(par1ItemStack.getItemDamage()));
		return var3 != null ? par2 == 0 ? var3.primaryColor : var3.secondaryColor : 16777215;
	}
	
	@Override public Icon getIconFromDamageForRenderPass(int par1, int par2)
	{
		return par2 > 0 ? theIcon : super.getIconFromDamageForRenderPass(par1, par2);
	}
	
	@Override public String getItemDisplayName(ItemStack p_77628_1_)
	{
		String var2 = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
		String var3 = EntityList.getStringFromID(p_77628_1_.getItemDamage());
		if(var3 != null)
		{
			var2 = var2 + " " + StatCollector.translateToLocal("entity." + var3 + ".name");
		}
		return var2;
	}
	
	@Override public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		Iterator var4 = EntityList.entityEggs.values().iterator();
		while(var4.hasNext())
		{
			EntityEggInfo var5 = (EntityEggInfo) var4.next();
			par3List.add(new ItemStack(par1, 1, var5.spawnedID));
		}
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if(p_77648_3_.isRemote) return true;
		else
		{
			int var11 = p_77648_3_.getBlockId(p_77648_4_, p_77648_5_, p_77648_6_);
			p_77648_4_ += Facing.offsetsXForSide[p_77648_7_];
			p_77648_5_ += Facing.offsetsYForSide[p_77648_7_];
			p_77648_6_ += Facing.offsetsZForSide[p_77648_7_];
			double var12 = 0.0D;
			if(p_77648_7_ == 1 && Block.blocksList[var11] != null && Block.blocksList[var11].getRenderType() == 11)
			{
				var12 = 0.5D;
			}
			Entity var14 = spawnCreature(p_77648_3_, p_77648_1_.getItemDamage(), p_77648_4_ + 0.5D, p_77648_5_ + var12, p_77648_6_ + 0.5D);
			if(var14 != null)
			{
				if(var14 instanceof EntityLiving && p_77648_1_.hasDisplayName())
				{
					((EntityLiving) var14).func_94058_c(p_77648_1_.getDisplayName());
				}
				if(!p_77648_2_.capabilities.isCreativeMode)
				{
					--p_77648_1_.stackSize;
				}
			}
			return true;
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
		theIcon = par1IconRegister.registerIcon("monsterPlacer_overlay");
	}
	
	@Override public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	
	public static Entity spawnCreature(World p_77840_0_, int p_77840_1_, double p_77840_2_, double p_77840_4_, double p_77840_6_)
	{
		if(!EntityList.entityEggs.containsKey(Integer.valueOf(p_77840_1_))) return null;
		else
		{
			Entity var8 = null;
			for(int var9 = 0; var9 < 1; ++var9)
			{
				var8 = EntityList.createEntityByID(p_77840_1_, p_77840_0_);
				if(var8 != null && var8 instanceof EntityLiving)
				{
					EntityLiving var10 = (EntityLiving) var8;
					var8.setLocationAndAngles(p_77840_2_, p_77840_4_, p_77840_6_, MathHelper.wrapAngleTo180_float(p_77840_0_.rand.nextFloat() * 360.0F), 0.0F);
					var10.rotationYawHead = var10.rotationYaw;
					var10.renderYawOffset = var10.rotationYaw;
					var10.initCreature();
					p_77840_0_.spawnEntityInWorld(var8);
					var10.playLivingSound();
				}
			}
			return var8;
		}
	}
}
