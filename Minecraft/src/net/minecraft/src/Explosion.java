package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Explosion
{
	public boolean isFlaming = false;
	public boolean isSmoking = true;
	private int field_77289_h = 16;
	private Random explosionRNG = new Random();
	private World worldObj;
	public double explosionX;
	public double explosionY;
	public double explosionZ;
	public Entity exploder;
	public float explosionSize;
	public List affectedBlockPositions = new ArrayList();
	private Map field_77288_k = new HashMap();
	
	public Explosion(World p_i3727_1_, Entity p_i3727_2_, double p_i3727_3_, double p_i3727_5_, double p_i3727_7_, float p_i3727_9_)
	{
		worldObj = p_i3727_1_;
		exploder = p_i3727_2_;
		explosionSize = p_i3727_9_;
		explosionX = p_i3727_3_;
		explosionY = p_i3727_5_;
		explosionZ = p_i3727_7_;
	}
	
	public void doExplosionA()
	{
		float var1 = explosionSize;
		HashSet var2 = new HashSet();
		int var3;
		int var4;
		int var5;
		double var15;
		double var17;
		double var19;
		for(var3 = 0; var3 < field_77289_h; ++var3)
		{
			for(var4 = 0; var4 < field_77289_h; ++var4)
			{
				for(var5 = 0; var5 < field_77289_h; ++var5)
				{
					if(var3 == 0 || var3 == field_77289_h - 1 || var4 == 0 || var4 == field_77289_h - 1 || var5 == 0 || var5 == field_77289_h - 1)
					{
						double var6 = var3 / (field_77289_h - 1.0F) * 2.0F - 1.0F;
						double var8 = var4 / (field_77289_h - 1.0F) * 2.0F - 1.0F;
						double var10 = var5 / (field_77289_h - 1.0F) * 2.0F - 1.0F;
						double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
						var6 /= var12;
						var8 /= var12;
						var10 /= var12;
						float var14 = explosionSize * (0.7F + worldObj.rand.nextFloat() * 0.6F);
						var15 = explosionX;
						var17 = explosionY;
						var19 = explosionZ;
						for(float var21 = 0.3F; var14 > 0.0F; var14 -= var21 * 0.75F)
						{
							int var22 = MathHelper.floor_double(var15);
							int var23 = MathHelper.floor_double(var17);
							int var24 = MathHelper.floor_double(var19);
							int var25 = worldObj.getBlockId(var22, var23, var24);
							if(var25 > 0)
							{
								Block var26 = Block.blocksList[var25];
								float var27 = exploder != null ? exploder.func_82146_a(this, worldObj, var22, var23, var24, var26) : var26.getExplosionResistance(exploder);
								var14 -= (var27 + 0.3F) * var21;
							}
							if(var14 > 0.0F && (exploder == null || exploder.func_96091_a(this, worldObj, var22, var23, var24, var25, var14)))
							{
								var2.add(new ChunkPosition(var22, var23, var24));
							}
							var15 += var6 * var21;
							var17 += var8 * var21;
							var19 += var10 * var21;
						}
					}
				}
			}
		}
		affectedBlockPositions.addAll(var2);
		explosionSize *= 2.0F;
		var3 = MathHelper.floor_double(explosionX - explosionSize - 1.0D);
		var4 = MathHelper.floor_double(explosionX + explosionSize + 1.0D);
		var5 = MathHelper.floor_double(explosionY - explosionSize - 1.0D);
		int var29 = MathHelper.floor_double(explosionY + explosionSize + 1.0D);
		int var7 = MathHelper.floor_double(explosionZ - explosionSize - 1.0D);
		int var30 = MathHelper.floor_double(explosionZ + explosionSize + 1.0D);
		List var9 = worldObj.getEntitiesWithinAABBExcludingEntity(exploder, AxisAlignedBB.getAABBPool().getAABB(var3, var5, var7, var4, var29, var30));
		Vec3 var31 = worldObj.getWorldVec3Pool().getVecFromPool(explosionX, explosionY, explosionZ);
		for(int var11 = 0; var11 < var9.size(); ++var11)
		{
			Entity var32 = (Entity) var9.get(var11);
			double var13 = var32.getDistance(explosionX, explosionY, explosionZ) / explosionSize;
			if(var13 <= 1.0D)
			{
				var15 = var32.posX - explosionX;
				var17 = var32.posY + var32.getEyeHeight() - explosionY;
				var19 = var32.posZ - explosionZ;
				double var34 = MathHelper.sqrt_double(var15 * var15 + var17 * var17 + var19 * var19);
				if(var34 != 0.0D)
				{
					var15 /= var34;
					var17 /= var34;
					var19 /= var34;
					double var33 = worldObj.getBlockDensity(var31, var32.boundingBox);
					double var35 = (1.0D - var13) * var33;
					var32.attackEntityFrom(DamageSource.setExplosionSource(this), (int) ((var35 * var35 + var35) / 2.0D * 8.0D * explosionSize + 1.0D));
					double var36 = EnchantmentProtection.func_92092_a(var32, var35);
					var32.motionX += var15 * var36;
					var32.motionY += var17 * var36;
					var32.motionZ += var19 * var36;
					if(var32 instanceof EntityPlayer)
					{
						field_77288_k.put(var32, worldObj.getWorldVec3Pool().getVecFromPool(var15 * var35, var17 * var35, var19 * var35));
					}
				}
			}
		}
		explosionSize = var1;
	}
	
	public void doExplosionB(boolean p_77279_1_)
	{
		worldObj.playSoundEffect(explosionX, explosionY, explosionZ, "random.explode", 4.0F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		if(explosionSize >= 2.0F && isSmoking)
		{
			worldObj.spawnParticle("hugeexplosion", explosionX, explosionY, explosionZ, 1.0D, 0.0D, 0.0D);
		} else
		{
			worldObj.spawnParticle("largeexplode", explosionX, explosionY, explosionZ, 1.0D, 0.0D, 0.0D);
		}
		Iterator var2;
		ChunkPosition var3;
		int var4;
		int var5;
		int var6;
		int var7;
		if(isSmoking)
		{
			var2 = affectedBlockPositions.iterator();
			while(var2.hasNext())
			{
				var3 = (ChunkPosition) var2.next();
				var4 = var3.x;
				var5 = var3.y;
				var6 = var3.z;
				var7 = worldObj.getBlockId(var4, var5, var6);
				if(p_77279_1_)
				{
					double var8 = var4 + worldObj.rand.nextFloat();
					double var10 = var5 + worldObj.rand.nextFloat();
					double var12 = var6 + worldObj.rand.nextFloat();
					double var14 = var8 - explosionX;
					double var16 = var10 - explosionY;
					double var18 = var12 - explosionZ;
					double var20 = MathHelper.sqrt_double(var14 * var14 + var16 * var16 + var18 * var18);
					var14 /= var20;
					var16 /= var20;
					var18 /= var20;
					double var22 = 0.5D / (var20 / explosionSize + 0.1D);
					var22 *= worldObj.rand.nextFloat() * worldObj.rand.nextFloat() + 0.3F;
					var14 *= var22;
					var16 *= var22;
					var18 *= var22;
					worldObj.spawnParticle("explode", (var8 + explosionX * 1.0D) / 2.0D, (var10 + explosionY * 1.0D) / 2.0D, (var12 + explosionZ * 1.0D) / 2.0D, var14, var16, var18);
					worldObj.spawnParticle("smoke", var8, var10, var12, var14, var16, var18);
				}
				if(var7 > 0)
				{
					Block var25 = Block.blocksList[var7];
					if(var25.canDropFromExplosion(this))
					{
						var25.dropBlockAsItemWithChance(worldObj, var4, var5, var6, worldObj.getBlockMetadata(var4, var5, var6), 1.0F / explosionSize, 0);
					}
					worldObj.setBlock(var4, var5, var6, 0, 0, 3);
					var25.onBlockDestroyedByExplosion(worldObj, var4, var5, var6, this);
				}
			}
		}
		if(isFlaming)
		{
			var2 = affectedBlockPositions.iterator();
			while(var2.hasNext())
			{
				var3 = (ChunkPosition) var2.next();
				var4 = var3.x;
				var5 = var3.y;
				var6 = var3.z;
				var7 = worldObj.getBlockId(var4, var5, var6);
				int var24 = worldObj.getBlockId(var4, var5 - 1, var6);
				if(var7 == 0 && Block.opaqueCubeLookup[var24] && explosionRNG.nextInt(3) == 0)
				{
					worldObj.setBlock(var4, var5, var6, Block.fire.blockID);
				}
			}
		}
	}
	
	public Map func_77277_b()
	{
		return field_77288_k;
	}
	
	public EntityLiving func_94613_c()
	{
		return exploder == null ? null : exploder instanceof EntityTNTPrimed ? ((EntityTNTPrimed) exploder).getTntPlacedBy() : exploder instanceof EntityLiving ? (EntityLiving) exploder : null;
	}
}
