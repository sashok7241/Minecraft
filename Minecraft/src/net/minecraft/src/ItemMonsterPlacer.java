package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class ItemMonsterPlacer extends Item
{
	private Icon theIcon;
	
	public ItemMonsterPlacer(int par1)
	{
		super(par1);
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
	
	@Override public String getItemDisplayName(ItemStack par1ItemStack)
	{
		String var2 = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
		String var3 = EntityList.getStringFromID(par1ItemStack.getItemDamage());
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
	
	@Override public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if(par3World.isRemote) return true;
		else
		{
			int var11 = par3World.getBlockId(par4, par5, par6);
			par4 += Facing.offsetsXForSide[par7];
			par5 += Facing.offsetsYForSide[par7];
			par6 += Facing.offsetsZForSide[par7];
			double var12 = 0.0D;
			if(par7 == 1 && Block.blocksList[var11] != null && Block.blocksList[var11].getRenderType() == 11)
			{
				var12 = 0.5D;
			}
			Entity var14 = spawnCreature(par3World, par1ItemStack.getItemDamage(), par4 + 0.5D, par5 + var12, par6 + 0.5D);
			if(var14 != null)
			{
				if(var14 instanceof EntityLiving && par1ItemStack.hasDisplayName())
				{
					((EntityLiving) var14).func_94058_c(par1ItemStack.getDisplayName());
				}
				if(!par2EntityPlayer.capabilities.isCreativeMode)
				{
					--par1ItemStack.stackSize;
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
	
	public static Entity spawnCreature(World par0World, int par1, double par2, double par4, double par6)
	{
		if(!EntityList.entityEggs.containsKey(Integer.valueOf(par1))) return null;
		else
		{
			Entity var8 = null;
			for(int var9 = 0; var9 < 1; ++var9)
			{
				var8 = EntityList.createEntityByID(par1, par0World);
				if(var8 != null && var8 instanceof EntityLiving)
				{
					EntityLiving var10 = (EntityLiving) var8;
					var8.setLocationAndAngles(par2, par4, par6, MathHelper.wrapAngleTo180_float(par0World.rand.nextFloat() * 360.0F), 0.0F);
					var10.rotationYawHead = var10.rotationYaw;
					var10.renderYawOffset = var10.rotationYaw;
					var10.initCreature();
					par0World.spawnEntityInWorld(var8);
					var10.playLivingSound();
				}
			}
			return var8;
		}
	}
}
