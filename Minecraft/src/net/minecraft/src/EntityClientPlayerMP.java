package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class EntityClientPlayerMP extends EntityPlayerSP
{
	public NetClientHandler sendQueue;
	private double oldPosX;
	private double oldMinY;
	private double oldPosY;
	private double oldPosZ;
	private float oldRotationYaw;
	private float oldRotationPitch;
	private boolean wasOnGround = false;
	private boolean shouldStopSneaking = false;
	private boolean wasSneaking = false;
	private int field_71168_co = 0;
	private boolean hasSetHealth = false;
	
	public EntityClientPlayerMP(Minecraft p_i3101_1_, World p_i3101_2_, Session p_i3101_3_, NetClientHandler p_i3101_4_)
	{
		super(p_i3101_1_, p_i3101_2_, p_i3101_3_, 0);
		sendQueue = p_i3101_4_;
	}
	
	@Override public void addStat(StatBase p_71064_1_, int p_71064_2_)
	{
		if(p_71064_1_ != null)
		{
			if(p_71064_1_.isIndependent)
			{
				super.addStat(p_71064_1_, p_71064_2_);
			}
		}
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		return false;
	}
	
	@Override public void closeScreen()
	{
		sendQueue.addToSendQueue(new Packet101CloseWindow(openContainer.windowId));
		func_92015_f();
	}
	
	@Override protected void damageEntity(DamageSource p_70665_1_, int p_70665_2_)
	{
		if(!isEntityInvulnerable())
		{
			setEntityHealth(getHealth() - p_70665_2_);
		}
	}
	
	@Override public EntityItem dropOneItem(boolean p_71040_1_)
	{
		int var2 = p_71040_1_ ? 3 : 4;
		sendQueue.addToSendQueue(new Packet14BlockDig(var2, 0, 0, 0, 0));
		return null;
	}
	
	@Override public boolean func_71066_bF()
	{
		return true;
	}
	
	public void func_92015_f()
	{
		inventory.setItemStack((ItemStack) null);
		super.closeScreen();
	}
	
	@Override public void heal(int p_70691_1_)
	{
	}
	
	public void incrementStat(StatBase par1StatBase, int par2)
	{
		if(par1StatBase != null)
		{
			if(!par1StatBase.isIndependent)
			{
				super.addStat(par1StatBase, par2);
			}
		}
	}
	
	@Override protected void joinEntityItemWithWorld(EntityItem p_71012_1_)
	{
	}
	
	@Override public void onUpdate()
	{
		if(worldObj.blockExists(MathHelper.floor_double(posX), 0, MathHelper.floor_double(posZ)))
		{
			super.onUpdate();
			sendMotionUpdates();
		}
	}
	
	@Override public void respawnPlayer()
	{
		sendQueue.addToSendQueue(new Packet205ClientCommand(1));
	}
	
	public void sendChatMessage(String par1Str)
	{
		sendQueue.addToSendQueue(new Packet3Chat(par1Str));
	}
	
	public void sendMotionUpdates()
	{
		boolean var1 = isSprinting();
		if(var1 != wasSneaking)
		{
			if(var1)
			{
				sendQueue.addToSendQueue(new Packet19EntityAction(this, 4));
			} else
			{
				sendQueue.addToSendQueue(new Packet19EntityAction(this, 5));
			}
			wasSneaking = var1;
		}
		boolean var2 = isSneaking();
		if(var2 != shouldStopSneaking)
		{
			if(var2)
			{
				sendQueue.addToSendQueue(new Packet19EntityAction(this, 1));
			} else
			{
				sendQueue.addToSendQueue(new Packet19EntityAction(this, 2));
			}
			shouldStopSneaking = var2;
		}
		double var3 = posX - oldPosX;
		double var5 = boundingBox.minY - oldMinY;
		double var7 = posZ - oldPosZ;
		double var9 = rotationYaw - oldRotationYaw;
		double var11 = rotationPitch - oldRotationPitch;
		boolean var13 = var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4D || field_71168_co >= 20;
		boolean var14 = var9 != 0.0D || var11 != 0.0D;
		if(ridingEntity != null)
		{
			sendQueue.addToSendQueue(new Packet13PlayerLookMove(motionX, -999.0D, -999.0D, motionZ, rotationYaw, rotationPitch, onGround));
			var13 = false;
		} else if(var13 && var14)
		{
			sendQueue.addToSendQueue(new Packet13PlayerLookMove(posX, boundingBox.minY, posY, posZ, rotationYaw, rotationPitch, onGround));
		} else if(var13)
		{
			sendQueue.addToSendQueue(new Packet11PlayerPosition(posX, boundingBox.minY, posY, posZ, onGround));
		} else if(var14)
		{
			sendQueue.addToSendQueue(new Packet12PlayerLook(rotationYaw, rotationPitch, onGround));
		} else
		{
			sendQueue.addToSendQueue(new Packet10Flying(onGround));
		}
		++field_71168_co;
		wasOnGround = onGround;
		if(var13)
		{
			oldPosX = posX;
			oldMinY = boundingBox.minY;
			oldPosY = posY;
			oldPosZ = posZ;
			field_71168_co = 0;
		}
		if(var14)
		{
			oldRotationYaw = rotationYaw;
			oldRotationPitch = rotationPitch;
		}
	}
	
	@Override public void sendPlayerAbilities()
	{
		sendQueue.addToSendQueue(new Packet202PlayerAbilities(capabilities));
	}
	
	@Override public void setHealth(int par1)
	{
		if(hasSetHealth)
		{
			super.setHealth(par1);
		} else
		{
			setEntityHealth(par1);
			hasSetHealth = true;
		}
	}
	
	@Override public void swingItem()
	{
		super.swingItem();
		sendQueue.addToSendQueue(new Packet18Animation(this, 1));
	}
}
