package net.minecraft.src;

import java.util.List;

public class EntityLightningBolt extends EntityWeatherEffect
{
	private int lightningState;
	public long boltVertex = 0L;
	private int boltLivingTime;
	
	public EntityLightningBolt(World p_i3533_1_, double p_i3533_2_, double p_i3533_4_, double p_i3533_6_)
	{
		super(p_i3533_1_);
		setLocationAndAngles(p_i3533_2_, p_i3533_4_, p_i3533_6_, 0.0F, 0.0F);
		lightningState = 2;
		boltVertex = rand.nextLong();
		boltLivingTime = rand.nextInt(3) + 1;
		if(!p_i3533_1_.isRemote && p_i3533_1_.difficultySetting >= 2 && p_i3533_1_.doChunksNearChunkExist(MathHelper.floor_double(p_i3533_2_), MathHelper.floor_double(p_i3533_4_), MathHelper.floor_double(p_i3533_6_), 10))
		{
			int var8 = MathHelper.floor_double(p_i3533_2_);
			int var9 = MathHelper.floor_double(p_i3533_4_);
			int var10 = MathHelper.floor_double(p_i3533_6_);
			if(p_i3533_1_.getBlockId(var8, var9, var10) == 0 && Block.fire.canPlaceBlockAt(p_i3533_1_, var8, var9, var10))
			{
				p_i3533_1_.setBlock(var8, var9, var10, Block.fire.blockID);
			}
			for(var8 = 0; var8 < 4; ++var8)
			{
				var9 = MathHelper.floor_double(p_i3533_2_) + rand.nextInt(3) - 1;
				var10 = MathHelper.floor_double(p_i3533_4_) + rand.nextInt(3) - 1;
				int var11 = MathHelper.floor_double(p_i3533_6_) + rand.nextInt(3) - 1;
				if(p_i3533_1_.getBlockId(var9, var10, var11) == 0 && Block.fire.canPlaceBlockAt(p_i3533_1_, var9, var10, var11))
				{
					p_i3533_1_.setBlock(var9, var10, var11, Block.fire.blockID);
				}
			}
		}
	}
	
	@Override protected void entityInit()
	{
	}
	
	@Override public boolean isInRangeToRenderVec3D(Vec3 par1Vec3)
	{
		return lightningState >= 0;
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(lightningState == 2)
		{
			worldObj.playSoundEffect(posX, posY, posZ, "ambient.weather.thunder", 10000.0F, 0.8F + rand.nextFloat() * 0.2F);
			worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 2.0F, 0.5F + rand.nextFloat() * 0.2F);
		}
		--lightningState;
		if(lightningState < 0)
		{
			if(boltLivingTime == 0)
			{
				setDead();
			} else if(lightningState < -rand.nextInt(10))
			{
				--boltLivingTime;
				lightningState = 1;
				boltVertex = rand.nextLong();
				if(!worldObj.isRemote && worldObj.doChunksNearChunkExist(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ), 10))
				{
					int var1 = MathHelper.floor_double(posX);
					int var2 = MathHelper.floor_double(posY);
					int var3 = MathHelper.floor_double(posZ);
					if(worldObj.getBlockId(var1, var2, var3) == 0 && Block.fire.canPlaceBlockAt(worldObj, var1, var2, var3))
					{
						worldObj.setBlock(var1, var2, var3, Block.fire.blockID);
					}
				}
			}
		}
		if(lightningState >= 0)
		{
			if(worldObj.isRemote)
			{
				worldObj.lastLightningBolt = 2;
			} else
			{
				double var6 = 3.0D;
				List var7 = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getAABBPool().getAABB(posX - var6, posY - var6, posZ - var6, posX + var6, posY + 6.0D + var6, posZ + var6));
				for(int var4 = 0; var4 < var7.size(); ++var4)
				{
					Entity var5 = (Entity) var7.get(var4);
					var5.onStruckByLightning(this);
				}
			}
		}
	}
	
	@Override protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
	}
}
