package net.minecraft.src;

import java.util.Random;

public class BlockTNT extends Block
{
	private Icon field_94393_a;
	private Icon field_94392_b;
	
	public BlockTNT(int p_i9095_1_)
	{
		super(p_i9095_1_, Material.tnt);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override public boolean canDropFromExplosion(Explosion p_85103_1_)
	{
		return false;
	}
	
	public void func_94391_a(World p_94391_1_, int p_94391_2_, int p_94391_3_, int p_94391_4_, int p_94391_5_, EntityLiving p_94391_6_)
	{
		if(!p_94391_1_.isRemote)
		{
			if((p_94391_5_ & 1) == 1)
			{
				EntityTNTPrimed var7 = new EntityTNTPrimed(p_94391_1_, p_94391_2_ + 0.5F, p_94391_3_ + 0.5F, p_94391_4_ + 0.5F, p_94391_6_);
				p_94391_1_.spawnEntityInWorld(var7);
				p_94391_1_.playSoundAtEntity(var7, "random.fuse", 1.0F, 1.0F);
			}
		}
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 0 ? field_94392_b : par1 == 1 ? field_94393_a : blockIcon;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		if(p_71903_5_.getCurrentEquippedItem() != null && p_71903_5_.getCurrentEquippedItem().itemID == Item.flintAndSteel.itemID)
		{
			func_94391_a(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_, 1, p_71903_5_);
			p_71903_1_.setBlockToAir(p_71903_2_, p_71903_3_, p_71903_4_);
			return true;
		} else return super.onBlockActivated(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_, p_71903_5_, p_71903_6_, p_71903_7_, p_71903_8_, p_71903_9_);
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		super.onBlockAdded(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
		if(p_71861_1_.isBlockIndirectlyGettingPowered(p_71861_2_, p_71861_3_, p_71861_4_))
		{
			onBlockDestroyedByPlayer(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_, 1);
			p_71861_1_.setBlockToAir(p_71861_2_, p_71861_3_, p_71861_4_);
		}
	}
	
	@Override public void onBlockDestroyedByExplosion(World p_71867_1_, int p_71867_2_, int p_71867_3_, int p_71867_4_, Explosion p_71867_5_)
	{
		if(!p_71867_1_.isRemote)
		{
			EntityTNTPrimed var6 = new EntityTNTPrimed(p_71867_1_, p_71867_2_ + 0.5F, p_71867_3_ + 0.5F, p_71867_4_ + 0.5F, p_71867_5_.func_94613_c());
			var6.fuse = p_71867_1_.rand.nextInt(var6.fuse / 4) + var6.fuse / 8;
			p_71867_1_.spawnEntityInWorld(var6);
		}
	}
	
	@Override public void onBlockDestroyedByPlayer(World p_71898_1_, int p_71898_2_, int p_71898_3_, int p_71898_4_, int p_71898_5_)
	{
		func_94391_a(p_71898_1_, p_71898_2_, p_71898_3_, p_71898_4_, p_71898_5_, (EntityLiving) null);
	}
	
	@Override public void onEntityCollidedWithBlock(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_)
	{
		if(p_71869_5_ instanceof EntityArrow && !p_71869_1_.isRemote)
		{
			EntityArrow var6 = (EntityArrow) p_71869_5_;
			if(var6.isBurning())
			{
				func_94391_a(p_71869_1_, p_71869_2_, p_71869_3_, p_71869_4_, 1, var6.shootingEntity instanceof EntityLiving ? (EntityLiving) var6.shootingEntity : null);
				p_71869_1_.setBlockToAir(p_71869_2_, p_71869_3_, p_71869_4_);
			}
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(p_71863_1_.isBlockIndirectlyGettingPowered(p_71863_2_, p_71863_3_, p_71863_4_))
		{
			onBlockDestroyedByPlayer(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, 1);
			p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
		}
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 1;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("tnt_side");
		field_94393_a = par1IconRegister.registerIcon("tnt_top");
		field_94392_b = par1IconRegister.registerIcon("tnt_bottom");
	}
}
