package net.minecraft.src;

public class SoundUpdaterMinecart implements IUpdatePlayerListBox
{
	private final SoundManager theSoundManager;
	private final EntityMinecart theMinecart;
	private final EntityPlayerSP thePlayer;
	private boolean playerSPRidingMinecart;
	private boolean minecartIsDead;
	private boolean minecartIsMoving;
	private boolean silent;
	private float minecartSoundPitch;
	private float minecartMoveSoundVolume;
	private float minecartRideSoundVolume;
	private double minecartSpeed;
	
	public SoundUpdaterMinecart(SoundManager par1SoundManager, EntityMinecart par2EntityMinecart, EntityPlayerSP par3EntityPlayerSP)
	{
		theSoundManager = par1SoundManager;
		theMinecart = par2EntityMinecart;
		thePlayer = par3EntityPlayerSP;
	}
	
	@Override public void update()
	{
		boolean var1 = false;
		boolean var2 = playerSPRidingMinecart;
		boolean var3 = minecartIsDead;
		boolean var4 = minecartIsMoving;
		float var5 = minecartMoveSoundVolume;
		float var6 = minecartSoundPitch;
		float var7 = minecartRideSoundVolume;
		double var8 = minecartSpeed;
		playerSPRidingMinecart = thePlayer != null && theMinecart.riddenByEntity == thePlayer;
		minecartIsDead = theMinecart.isDead;
		minecartSpeed = MathHelper.sqrt_double(theMinecart.motionX * theMinecart.motionX + theMinecart.motionZ * theMinecart.motionZ);
		minecartIsMoving = minecartSpeed >= 0.01D;
		if(var2 && !playerSPRidingMinecart)
		{
			theSoundManager.stopEntitySound(thePlayer);
		}
		if(minecartIsDead || !silent && minecartMoveSoundVolume == 0.0F && minecartRideSoundVolume == 0.0F)
		{
			if(!var3)
			{
				theSoundManager.stopEntitySound(theMinecart);
				if(var2 || playerSPRidingMinecart)
				{
					theSoundManager.stopEntitySound(thePlayer);
				}
			}
			silent = true;
			if(minecartIsDead) return;
		}
		if(!theSoundManager.isEntitySoundPlaying(theMinecart) && minecartMoveSoundVolume > 0.0F)
		{
			theSoundManager.playEntitySound("minecart.base", theMinecart, minecartMoveSoundVolume, minecartSoundPitch, false);
			silent = false;
			var1 = true;
		}
		if(playerSPRidingMinecart && !theSoundManager.isEntitySoundPlaying(thePlayer) && minecartRideSoundVolume > 0.0F)
		{
			theSoundManager.playEntitySound("minecart.inside", thePlayer, minecartRideSoundVolume, 1.0F, true);
			silent = false;
			var1 = true;
		}
		if(minecartIsMoving)
		{
			if(minecartSoundPitch < 1.0F)
			{
				minecartSoundPitch += 0.0025F;
			}
			if(minecartSoundPitch > 1.0F)
			{
				minecartSoundPitch = 1.0F;
			}
			float var10 = MathHelper.clamp_float((float) minecartSpeed, 0.0F, 4.0F) / 4.0F;
			minecartRideSoundVolume = 0.0F + var10 * 0.75F;
			var10 = MathHelper.clamp_float(var10 * 2.0F, 0.0F, 1.0F);
			minecartMoveSoundVolume = 0.0F + var10 * 0.7F;
		} else if(var4)
		{
			minecartMoveSoundVolume = 0.0F;
			minecartSoundPitch = 0.0F;
			minecartRideSoundVolume = 0.0F;
		}
		if(!silent)
		{
			if(minecartSoundPitch != var6)
			{
				theSoundManager.setEntitySoundPitch(theMinecart, minecartSoundPitch);
			}
			if(minecartMoveSoundVolume != var5)
			{
				theSoundManager.setEntitySoundVolume(theMinecart, minecartMoveSoundVolume);
			}
			if(minecartRideSoundVolume != var7)
			{
				theSoundManager.setEntitySoundVolume(thePlayer, minecartRideSoundVolume);
			}
		}
		if(!var1 && (minecartMoveSoundVolume > 0.0F || minecartRideSoundVolume > 0.0F))
		{
			theSoundManager.updateSoundLocation(theMinecart);
			if(playerSPRidingMinecart)
			{
				theSoundManager.updateSoundLocation(thePlayer, theMinecart);
			}
		} else
		{
			if(theSoundManager.isEntitySoundPlaying(theMinecart))
			{
				theSoundManager.stopEntitySound(theMinecart);
			}
			if(playerSPRidingMinecart && theSoundManager.isEntitySoundPlaying(thePlayer))
			{
				theSoundManager.stopEntitySound(thePlayer);
			}
		}
	}
}
