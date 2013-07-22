package net.minecraft.src;

import java.util.List;

public class ItemBoat extends Item
{
	public ItemBoat(int p_i3621_1_)
	{
		super(p_i3621_1_);
		maxStackSize = 1;
		setCreativeTab(CreativeTabs.tabTransport);
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		float var4 = 1.0F;
		float var5 = p_77659_3_.prevRotationPitch + (p_77659_3_.rotationPitch - p_77659_3_.prevRotationPitch) * var4;
		float var6 = p_77659_3_.prevRotationYaw + (p_77659_3_.rotationYaw - p_77659_3_.prevRotationYaw) * var4;
		double var7 = p_77659_3_.prevPosX + (p_77659_3_.posX - p_77659_3_.prevPosX) * var4;
		double var9 = p_77659_3_.prevPosY + (p_77659_3_.posY - p_77659_3_.prevPosY) * var4 + 1.62D - p_77659_3_.yOffset;
		double var11 = p_77659_3_.prevPosZ + (p_77659_3_.posZ - p_77659_3_.prevPosZ) * var4;
		Vec3 var13 = p_77659_2_.getWorldVec3Pool().getVecFromPool(var7, var9, var11);
		float var14 = MathHelper.cos(-var6 * 0.017453292F - (float) Math.PI);
		float var15 = MathHelper.sin(-var6 * 0.017453292F - (float) Math.PI);
		float var16 = -MathHelper.cos(-var5 * 0.017453292F);
		float var17 = MathHelper.sin(-var5 * 0.017453292F);
		float var18 = var15 * var16;
		float var20 = var14 * var16;
		double var21 = 5.0D;
		Vec3 var23 = var13.addVector(var18 * var21, var17 * var21, var20 * var21);
		MovingObjectPosition var24 = p_77659_2_.clip(var13, var23, true);
		if(var24 == null) return p_77659_1_;
		else
		{
			Vec3 var25 = p_77659_3_.getLook(var4);
			boolean var26 = false;
			float var27 = 1.0F;
			List var28 = p_77659_2_.getEntitiesWithinAABBExcludingEntity(p_77659_3_, p_77659_3_.boundingBox.addCoord(var25.xCoord * var21, var25.yCoord * var21, var25.zCoord * var21).expand(var27, var27, var27));
			int var29;
			for(var29 = 0; var29 < var28.size(); ++var29)
			{
				Entity var30 = (Entity) var28.get(var29);
				if(var30.canBeCollidedWith())
				{
					float var31 = var30.getCollisionBorderSize();
					AxisAlignedBB var32 = var30.boundingBox.expand(var31, var31, var31);
					if(var32.isVecInside(var13))
					{
						var26 = true;
					}
				}
			}
			if(var26) return p_77659_1_;
			else
			{
				if(var24.typeOfHit == EnumMovingObjectType.TILE)
				{
					var29 = var24.blockX;
					int var33 = var24.blockY;
					int var34 = var24.blockZ;
					if(p_77659_2_.getBlockId(var29, var33, var34) == Block.snow.blockID)
					{
						--var33;
					}
					EntityBoat var35 = new EntityBoat(p_77659_2_, var29 + 0.5F, var33 + 1.0F, var34 + 0.5F);
					var35.rotationYaw = ((MathHelper.floor_double(p_77659_3_.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) - 1) * 90;
					if(!p_77659_2_.getCollidingBoundingBoxes(var35, var35.boundingBox.expand(-0.1D, -0.1D, -0.1D)).isEmpty()) return p_77659_1_;
					if(!p_77659_2_.isRemote)
					{
						p_77659_2_.spawnEntityInWorld(var35);
					}
					if(!p_77659_3_.capabilities.isCreativeMode)
					{
						--p_77659_1_.stackSize;
					}
				}
				return p_77659_1_;
			}
		}
	}
}
