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
	private boolean wasOnGround;
	private boolean shouldStopSneaking;
	private boolean wasSneaking;
	private int field_71168_co;
	private boolean hasSetHealth;
	private String field_142022_ce;
	
	public EntityClientPlayerMP(Minecraft par1Minecraft, World par2World, Session par3Session, NetClientHandler par4NetClientHandler)
	{
		super(par1Minecraft, par2World, par3Session, 0);
		sendQueue = par4NetClientHandler;
	}
	
	@Override public void addStat(StatBase par1StatBase, int par2)
	{
		if(par1StatBase != null)
		{
			if(par1StatBase.isIndependent)
			{
				super.addStat(par1StatBase, par2);
			}
		}
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		return false;
	}
	
	@Override public void closeScreen()
	{
		sendQueue.addToSendQueue(new Packet101CloseWindow(openContainer.windowId));
		func_92015_f();
	}
	
	@Override protected void damageEntity(DamageSource par1DamageSource, float par2)
	{
		if(!isEntityInvulnerable())
		{
			setEntityHealth(func_110143_aJ() - par2);
		}
	}
	
	@Override public EntityItem dropOneItem(boolean par1)
	{
		int var2 = par1 ? 3 : 4;
		sendQueue.addToSendQueue(new Packet14BlockDig(var2, 0, 0, 0, 0));
		return null;
	}
	
	@Override protected void func_110318_g()
	{
		sendQueue.addToSendQueue(new Packet19EntityAction(this, 6, (int) (func_110319_bJ() * 100.0F)));
	}
	
	public void func_110322_i()
	{
		sendQueue.addToSendQueue(new Packet19EntityAction(this, 7));
	}
	
	public void func_142020_c(String par1Str)
	{
		field_142022_ce = par1Str;
	}
	
	public String func_142021_k()
	{
		return field_142022_ce;
	}
	
	public void func_92015_f()
	{
		inventory.setItemStack((ItemStack) null);
		super.closeScreen();
	}
	
	@Override public void heal(float par1)
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
	
	@Override protected void joinEntityItemWithWorld(EntityItem par1EntityItem)
	{
	}
	
	@Override public void onUpdate()
	{
		if(worldObj.blockExists(MathHelper.floor_double(posX), 0, MathHelper.floor_double(posZ)))
		{
			super.onUpdate();
			if(isRiding())
			{
				sendQueue.addToSendQueue(new Packet12PlayerLook(rotationYaw, rotationPitch, onGround));
				sendQueue.addToSendQueue(new Packet27PlayerInput(moveStrafing, moveForward, movementInput.jump, movementInput.sneak));
			} else
			{
				sendMotionUpdates();
			}
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
	
	@Override public void setHealth(float par1)
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
