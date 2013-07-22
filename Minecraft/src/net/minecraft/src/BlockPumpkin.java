package net.minecraft.src;

public class BlockPumpkin extends BlockDirectional
{
	private boolean blockType;
	private Icon field_94474_b;
	private Icon field_94475_c;
	
	protected BlockPumpkin(int p_i9081_1_, boolean p_i9081_2_)
	{
		super(p_i9081_1_, Material.pumpkin);
		setTickRandomly(true);
		blockType = p_i9081_2_;
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		int var5 = p_71930_1_.getBlockId(p_71930_2_, p_71930_3_, p_71930_4_);
		return (var5 == 0 || Block.blocksList[var5].blockMaterial.isReplaceable()) && p_71930_1_.doesBlockHaveSolidTopSurface(p_71930_2_, p_71930_3_ - 1, p_71930_4_);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? field_94474_b : par1 == 0 ? field_94474_b : par2 == 2 && par1 == 2 ? field_94475_c : par2 == 3 && par1 == 5 ? field_94475_c : par2 == 0 && par1 == 3 ? field_94475_c : par2 == 1 && par1 == 4 ? field_94475_c : blockIcon;
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		super.onBlockAdded(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
		if(p_71861_1_.getBlockId(p_71861_2_, p_71861_3_ - 1, p_71861_4_) == Block.blockSnow.blockID && p_71861_1_.getBlockId(p_71861_2_, p_71861_3_ - 2, p_71861_4_) == Block.blockSnow.blockID)
		{
			if(!p_71861_1_.isRemote)
			{
				p_71861_1_.setBlock(p_71861_2_, p_71861_3_, p_71861_4_, 0, 0, 2);
				p_71861_1_.setBlock(p_71861_2_, p_71861_3_ - 1, p_71861_4_, 0, 0, 2);
				p_71861_1_.setBlock(p_71861_2_, p_71861_3_ - 2, p_71861_4_, 0, 0, 2);
				EntitySnowman var9 = new EntitySnowman(p_71861_1_);
				var9.setLocationAndAngles(p_71861_2_ + 0.5D, p_71861_3_ - 1.95D, p_71861_4_ + 0.5D, 0.0F, 0.0F);
				p_71861_1_.spawnEntityInWorld(var9);
				p_71861_1_.notifyBlockChange(p_71861_2_, p_71861_3_, p_71861_4_, 0);
				p_71861_1_.notifyBlockChange(p_71861_2_, p_71861_3_ - 1, p_71861_4_, 0);
				p_71861_1_.notifyBlockChange(p_71861_2_, p_71861_3_ - 2, p_71861_4_, 0);
			}
			for(int var10 = 0; var10 < 120; ++var10)
			{
				p_71861_1_.spawnParticle("snowshovel", p_71861_2_ + p_71861_1_.rand.nextDouble(), p_71861_3_ - 2 + p_71861_1_.rand.nextDouble() * 2.5D, p_71861_4_ + p_71861_1_.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
			}
		} else if(p_71861_1_.getBlockId(p_71861_2_, p_71861_3_ - 1, p_71861_4_) == Block.blockIron.blockID && p_71861_1_.getBlockId(p_71861_2_, p_71861_3_ - 2, p_71861_4_) == Block.blockIron.blockID)
		{
			boolean var5 = p_71861_1_.getBlockId(p_71861_2_ - 1, p_71861_3_ - 1, p_71861_4_) == Block.blockIron.blockID && p_71861_1_.getBlockId(p_71861_2_ + 1, p_71861_3_ - 1, p_71861_4_) == Block.blockIron.blockID;
			boolean var6 = p_71861_1_.getBlockId(p_71861_2_, p_71861_3_ - 1, p_71861_4_ - 1) == Block.blockIron.blockID && p_71861_1_.getBlockId(p_71861_2_, p_71861_3_ - 1, p_71861_4_ + 1) == Block.blockIron.blockID;
			if(var5 || var6)
			{
				p_71861_1_.setBlock(p_71861_2_, p_71861_3_, p_71861_4_, 0, 0, 2);
				p_71861_1_.setBlock(p_71861_2_, p_71861_3_ - 1, p_71861_4_, 0, 0, 2);
				p_71861_1_.setBlock(p_71861_2_, p_71861_3_ - 2, p_71861_4_, 0, 0, 2);
				if(var5)
				{
					p_71861_1_.setBlock(p_71861_2_ - 1, p_71861_3_ - 1, p_71861_4_, 0, 0, 2);
					p_71861_1_.setBlock(p_71861_2_ + 1, p_71861_3_ - 1, p_71861_4_, 0, 0, 2);
				} else
				{
					p_71861_1_.setBlock(p_71861_2_, p_71861_3_ - 1, p_71861_4_ - 1, 0, 0, 2);
					p_71861_1_.setBlock(p_71861_2_, p_71861_3_ - 1, p_71861_4_ + 1, 0, 0, 2);
				}
				EntityIronGolem var7 = new EntityIronGolem(p_71861_1_);
				var7.setPlayerCreated(true);
				var7.setLocationAndAngles(p_71861_2_ + 0.5D, p_71861_3_ - 1.95D, p_71861_4_ + 0.5D, 0.0F, 0.0F);
				p_71861_1_.spawnEntityInWorld(var7);
				for(int var8 = 0; var8 < 120; ++var8)
				{
					p_71861_1_.spawnParticle("snowballpoof", p_71861_2_ + p_71861_1_.rand.nextDouble(), p_71861_3_ - 2 + p_71861_1_.rand.nextDouble() * 3.9D, p_71861_4_ + p_71861_1_.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
				}
				p_71861_1_.notifyBlockChange(p_71861_2_, p_71861_3_, p_71861_4_, 0);
				p_71861_1_.notifyBlockChange(p_71861_2_, p_71861_3_ - 1, p_71861_4_, 0);
				p_71861_1_.notifyBlockChange(p_71861_2_, p_71861_3_ - 2, p_71861_4_, 0);
				if(var5)
				{
					p_71861_1_.notifyBlockChange(p_71861_2_ - 1, p_71861_3_ - 1, p_71861_4_, 0);
					p_71861_1_.notifyBlockChange(p_71861_2_ + 1, p_71861_3_ - 1, p_71861_4_, 0);
				} else
				{
					p_71861_1_.notifyBlockChange(p_71861_2_, p_71861_3_ - 1, p_71861_4_ - 1, 0);
					p_71861_1_.notifyBlockChange(p_71861_2_, p_71861_3_ - 1, p_71861_4_ + 1, 0);
				}
			}
		}
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		int var7 = MathHelper.floor_double(p_71860_5_.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
		p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, var7, 2);
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		field_94475_c = par1IconRegister.registerIcon(blockType ? "pumpkin_jack" : "pumpkin_face");
		field_94474_b = par1IconRegister.registerIcon("pumpkin_top");
		blockIcon = par1IconRegister.registerIcon("pumpkin_side");
	}
}
