package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class EntityOtherPlayerMP extends EntityPlayer
{
	private boolean isItemInUse = false;
	private int otherPlayerMPPosRotationIncrements;
	private double otherPlayerMPX;
	private double otherPlayerMPY;
	private double otherPlayerMPZ;
	private double otherPlayerMPYaw;
	private double otherPlayerMPPitch;
	
	public EntityOtherPlayerMP(World p_i3117_1_, String p_i3117_2_)
	{
		super(p_i3117_1_);
		username = p_i3117_2_;
		yOffset = 0.0F;
		stepHeight = 0.0F;
		if(p_i3117_2_ != null && p_i3117_2_.length() > 0)
		{
			skinUrl = "http://skins.minecraft.net/MinecraftSkins/" + StringUtils.stripControlCodes(p_i3117_2_) + ".png";
		}
		noClip = true;
		field_71082_cx = 0.25F;
		renderDistanceWeight = 10.0D;
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		return true;
	}
	
	@Override public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_)
	{
		return false;
	}
	
	@Override public float getEyeHeight()
	{
		return 1.82F;
	}
	
	@Override public ChunkCoordinates getPlayerCoordinates()
	{
		return new ChunkCoordinates(MathHelper.floor_double(posX + 0.5D), MathHelper.floor_double(posY + 0.5D), MathHelper.floor_double(posZ + 0.5D));
	}
	
	@Override public float getShadowSize()
	{
		return 0.0F;
	}
	
	@Override public void onLivingUpdate()
	{
		super.updateEntityActionState();
		if(otherPlayerMPPosRotationIncrements > 0)
		{
			double var1 = posX + (otherPlayerMPX - posX) / otherPlayerMPPosRotationIncrements;
			double var3 = posY + (otherPlayerMPY - posY) / otherPlayerMPPosRotationIncrements;
			double var5 = posZ + (otherPlayerMPZ - posZ) / otherPlayerMPPosRotationIncrements;
			double var7;
			for(var7 = otherPlayerMPYaw - rotationYaw; var7 < -180.0D; var7 += 360.0D)
			{
				;
			}
			while(var7 >= 180.0D)
			{
				var7 -= 360.0D;
			}
			rotationYaw = (float) (rotationYaw + var7 / otherPlayerMPPosRotationIncrements);
			rotationPitch = (float) (rotationPitch + (otherPlayerMPPitch - rotationPitch) / otherPlayerMPPosRotationIncrements);
			--otherPlayerMPPosRotationIncrements;
			setPosition(var1, var3, var5);
			setRotation(rotationYaw, rotationPitch);
		}
		prevCameraYaw = cameraYaw;
		float var9 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		float var2 = (float) Math.atan(-motionY * 0.20000000298023224D) * 15.0F;
		if(var9 > 0.1F)
		{
			var9 = 0.1F;
		}
		if(!onGround || getHealth() <= 0)
		{
			var9 = 0.0F;
		}
		if(onGround || getHealth() <= 0)
		{
			var2 = 0.0F;
		}
		cameraYaw += (var9 - cameraYaw) * 0.4F;
		cameraPitch += (var2 - cameraPitch) * 0.8F;
	}
	
	@Override public void onUpdate()
	{
		field_71082_cx = 0.0F;
		super.onUpdate();
		prevLimbYaw = limbYaw;
		double var1 = posX - prevPosX;
		double var3 = posZ - prevPosZ;
		float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3) * 4.0F;
		if(var5 > 1.0F)
		{
			var5 = 1.0F;
		}
		limbYaw += (var5 - limbYaw) * 0.4F;
		limbSwing += limbYaw;
		if(!isItemInUse && isEating() && inventory.mainInventory[inventory.currentItem] != null)
		{
			ItemStack var6 = inventory.mainInventory[inventory.currentItem];
			setItemInUse(inventory.mainInventory[inventory.currentItem], Item.itemsList[var6.itemID].getMaxItemUseDuration(var6));
			isItemInUse = true;
		} else if(isItemInUse && !isEating())
		{
			clearItemInUse();
			isItemInUse = false;
		}
	}
	
	@Override protected void resetHeight()
	{
		yOffset = 0.0F;
	}
	
	@Override public void sendChatToPlayer(String p_70006_1_)
	{
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(p_70006_1_);
	}
	
	@Override public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_)
	{
		if(p_70062_1_ == 0)
		{
			inventory.mainInventory[inventory.currentItem] = p_70062_2_;
		} else
		{
			inventory.armorInventory[p_70062_1_ - 1] = p_70062_2_;
		}
	}
	
	@Override public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		otherPlayerMPX = par1;
		otherPlayerMPY = par3;
		otherPlayerMPZ = par5;
		otherPlayerMPYaw = par7;
		otherPlayerMPPitch = par8;
		otherPlayerMPPosRotationIncrements = par9;
	}
	
	@Override public void updateCloak()
	{
		cloakUrl = "http://skins.minecraft.net/MinecraftCloaks/" + StringUtils.stripControlCodes(username) + ".png";
	}
}
