package net.minecraft.src;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SoundManager implements ResourceManagerReloadListener
{
	private static final String[] field_130084_a = new String[] { "ogg" };
	private SoundSystem sndSystem;
	private boolean loaded;
	private final SoundPool soundPoolSounds;
	private final SoundPool soundPoolStreaming;
	private final SoundPool soundPoolMusic;
	private int latestSoundID;
	private final GameSettings options;
	private final File field_130085_i;
	private final Set playingSounds = new HashSet();
	private final List field_92072_h = new ArrayList();
	private Random rand = new Random();
	private int ticksBeforeMusic;
	
	public SoundManager(ResourceManager par1ResourceManager, GameSettings par2GameSettings, File par3File)
	{
		ticksBeforeMusic = rand.nextInt(12000);
		options = par2GameSettings;
		field_130085_i = par3File;
		soundPoolSounds = new SoundPool(par1ResourceManager, "sound", true);
		soundPoolStreaming = new SoundPool(par1ResourceManager, "records", false);
		soundPoolMusic = new SoundPool(par1ResourceManager, "music", true);
		try
		{
			SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
			SoundSystemConfig.setCodec("wav", CodecWav.class);
		} catch(SoundSystemException var5)
		{
			var5.printStackTrace();
			System.err.println("error linking with the LibraryJavaSound plug-in");
		}
		func_130083_h();
	}
	
	public void addMusic(String par1Str)
	{
		soundPoolMusic.addSound(par1Str);
	}
	
	public void addSound(String par1Str)
	{
		soundPoolSounds.addSound(par1Str);
	}
	
	public void addStreaming(String par1Str)
	{
		soundPoolStreaming.addSound(par1Str);
	}
	
	public void closeMinecraft()
	{
		if(loaded)
		{
			sndSystem.cleanup();
			loaded = false;
		}
	}
	
	@Override public void func_110549_a(ResourceManager par1ResourceManager)
	{
		stopAllSounds();
		closeMinecraft();
		tryToSetLibraryAndCodecs();
	}
	
	private void func_130081_a(File par1File)
	{
		String var2 = field_130085_i.toURI().relativize(par1File.toURI()).getPath();
		int var3 = var2.indexOf("/");
		if(var3 != -1)
		{
			String var4 = var2.substring(0, var3);
			var2 = var2.substring(var3 + 1);
			if("sound".equalsIgnoreCase(var4))
			{
				addSound(var2);
			} else if("records".equalsIgnoreCase(var4))
			{
				addStreaming(var2);
			} else if("music".equalsIgnoreCase(var4))
			{
				addMusic(var2);
			}
		}
	}
	
	private void func_130083_h()
	{
		if(field_130085_i.isDirectory())
		{
			Collection var1 = FileUtils.listFiles(field_130085_i, field_130084_a, true);
			Iterator var2 = var1.iterator();
			while(var2.hasNext())
			{
				File var3 = (File) var2.next();
				func_130081_a(var3);
			}
		}
	}
	
	public void func_92070_a(String par1Str, float par2, float par3, float par4, float par5, float par6, int par7)
	{
		field_92072_h.add(new ScheduledSound(par1Str, par2, par3, par4, par5, par6, par7));
	}
	
	public void func_92071_g()
	{
		if(!field_92072_h.isEmpty())
		{
			Iterator var1 = field_92072_h.iterator();
			while(var1.hasNext())
			{
				ScheduledSound var2 = (ScheduledSound) var1.next();
				--var2.field_92064_g;
				if(var2.field_92064_g <= 0)
				{
					playSound(var2.field_92069_a, var2.field_92067_b, var2.field_92068_c, var2.field_92065_d, var2.field_92066_e, var2.field_92063_f);
					var1.remove();
				}
			}
		}
	}
	
	public boolean isEntitySoundPlaying(Entity par1Entity)
	{
		if(par1Entity != null && loaded)
		{
			String var2 = "entity_" + par1Entity.entityId;
			return sndSystem.playing(var2);
		} else return false;
	}
	
	public void onSoundOptionsChanged()
	{
		if(loaded)
		{
			if(options.musicVolume == 0.0F)
			{
				sndSystem.stop("BgMusic");
				sndSystem.stop("streaming");
			} else
			{
				sndSystem.setVolume("BgMusic", options.musicVolume);
				sndSystem.setVolume("streaming", options.musicVolume);
			}
		}
	}
	
	public void pauseAllSounds()
	{
		Iterator var1 = playingSounds.iterator();
		while(var1.hasNext())
		{
			String var2 = (String) var1.next();
			sndSystem.pause(var2);
		}
	}
	
	public void playEntitySound(String par1Str, Entity par2Entity, float par3, float par4, boolean par5)
	{
		if(loaded && (options.soundVolume != 0.0F || par1Str == null) && par2Entity != null)
		{
			String var6 = "entity_" + par2Entity.entityId;
			if(playingSounds.contains(var6))
			{
				this.updateSoundLocation(par2Entity);
			} else
			{
				if(sndSystem.playing(var6))
				{
					sndSystem.stop(var6);
				}
				if(par1Str != null)
				{
					SoundPoolEntry var7 = soundPoolSounds.getRandomSoundFromSoundPool(par1Str);
					if(var7 != null && par3 > 0.0F)
					{
						float var8 = 16.0F;
						if(par3 > 1.0F)
						{
							var8 *= par3;
						}
						sndSystem.newSource(par5, var6, var7.func_110457_b(), var7.func_110458_a(), false, (float) par2Entity.posX, (float) par2Entity.posY, (float) par2Entity.posZ, 2, var8);
						sndSystem.setLooping(var6, true);
						sndSystem.setPitch(var6, par4);
						if(par3 > 1.0F)
						{
							par3 = 1.0F;
						}
						sndSystem.setVolume(var6, par3 * options.soundVolume);
						sndSystem.setVelocity(var6, (float) par2Entity.motionX, (float) par2Entity.motionY, (float) par2Entity.motionZ);
						sndSystem.play(var6);
						playingSounds.add(var6);
					}
				}
			}
		}
	}
	
	public void playRandomMusicIfReady()
	{
		if(loaded && options.musicVolume != 0.0F)
		{
			if(!sndSystem.playing("BgMusic") && !sndSystem.playing("streaming"))
			{
				if(ticksBeforeMusic > 0)
				{
					--ticksBeforeMusic;
				} else
				{
					SoundPoolEntry var1 = soundPoolMusic.getRandomSound();
					if(var1 != null)
					{
						ticksBeforeMusic = rand.nextInt(12000) + 12000;
						sndSystem.backgroundMusic("BgMusic", var1.func_110457_b(), var1.func_110458_a(), false);
						sndSystem.setVolume("BgMusic", options.musicVolume);
						sndSystem.play("BgMusic");
					}
				}
			}
		}
	}
	
	public void playSound(String par1Str, float par2, float par3, float par4, float par5, float par6)
	{
		if(loaded && options.soundVolume != 0.0F)
		{
			SoundPoolEntry var7 = soundPoolSounds.getRandomSoundFromSoundPool(par1Str);
			if(var7 != null && par5 > 0.0F)
			{
				latestSoundID = (latestSoundID + 1) % 256;
				String var8 = "sound_" + latestSoundID;
				float var9 = 16.0F;
				if(par5 > 1.0F)
				{
					var9 *= par5;
				}
				sndSystem.newSource(par5 > 1.0F, var8, var7.func_110457_b(), var7.func_110458_a(), false, par2, par3, par4, 2, var9);
				if(par5 > 1.0F)
				{
					par5 = 1.0F;
				}
				sndSystem.setPitch(var8, par6);
				sndSystem.setVolume(var8, par5 * options.soundVolume);
				sndSystem.play(var8);
			}
		}
	}
	
	public void playSoundFX(String par1Str, float par2, float par3)
	{
		if(loaded && options.soundVolume != 0.0F)
		{
			SoundPoolEntry var4 = soundPoolSounds.getRandomSoundFromSoundPool(par1Str);
			if(var4 != null && par2 > 0.0F)
			{
				latestSoundID = (latestSoundID + 1) % 256;
				String var5 = "sound_" + latestSoundID;
				sndSystem.newSource(false, var5, var4.func_110457_b(), var4.func_110458_a(), false, 0.0F, 0.0F, 0.0F, 0, 0.0F);
				if(par2 > 1.0F)
				{
					par2 = 1.0F;
				}
				par2 *= 0.25F;
				sndSystem.setPitch(var5, par3);
				sndSystem.setVolume(var5, par2 * options.soundVolume);
				sndSystem.play(var5);
			}
		}
	}
	
	public void playStreaming(String par1Str, float par2, float par3, float par4)
	{
		if(loaded && (options.soundVolume != 0.0F || par1Str == null))
		{
			String var5 = "streaming";
			if(sndSystem.playing(var5))
			{
				sndSystem.stop(var5);
			}
			if(par1Str != null)
			{
				SoundPoolEntry var6 = soundPoolStreaming.getRandomSoundFromSoundPool(par1Str);
				if(var6 != null)
				{
					if(sndSystem.playing("BgMusic"))
					{
						sndSystem.stop("BgMusic");
					}
					sndSystem.newStreamingSource(true, var5, var6.func_110457_b(), var6.func_110458_a(), false, par2, par3, par4, 2, 64.0F);
					sndSystem.setVolume(var5, 0.5F * options.soundVolume);
					sndSystem.play(var5);
				}
			}
		}
	}
	
	public void resumeAllSounds()
	{
		Iterator var1 = playingSounds.iterator();
		while(var1.hasNext())
		{
			String var2 = (String) var1.next();
			sndSystem.play(var2);
		}
	}
	
	public void setEntitySoundPitch(Entity par1Entity, float par2)
	{
		if(par1Entity != null && loaded && options.soundVolume != 0.0F)
		{
			String var3 = "entity_" + par1Entity.entityId;
			if(sndSystem.playing(var3))
			{
				sndSystem.setPitch(var3, par2);
			}
		}
	}
	
	public void setEntitySoundVolume(Entity par1Entity, float par2)
	{
		if(par1Entity != null && loaded && options.soundVolume != 0.0F)
		{
			String var3 = "entity_" + par1Entity.entityId;
			if(sndSystem.playing(var3))
			{
				sndSystem.setVolume(var3, par2 * options.soundVolume);
			}
		}
	}
	
	public void setListener(EntityLivingBase par1EntityLivingBase, float par2)
	{
		if(loaded && options.soundVolume != 0.0F && par1EntityLivingBase != null)
		{
			float var3 = par1EntityLivingBase.prevRotationPitch + (par1EntityLivingBase.rotationPitch - par1EntityLivingBase.prevRotationPitch) * par2;
			float var4 = par1EntityLivingBase.prevRotationYaw + (par1EntityLivingBase.rotationYaw - par1EntityLivingBase.prevRotationYaw) * par2;
			double var5 = par1EntityLivingBase.prevPosX + (par1EntityLivingBase.posX - par1EntityLivingBase.prevPosX) * par2;
			double var7 = par1EntityLivingBase.prevPosY + (par1EntityLivingBase.posY - par1EntityLivingBase.prevPosY) * par2;
			double var9 = par1EntityLivingBase.prevPosZ + (par1EntityLivingBase.posZ - par1EntityLivingBase.prevPosZ) * par2;
			float var11 = MathHelper.cos(-var4 * 0.017453292F - (float) Math.PI);
			float var12 = MathHelper.sin(-var4 * 0.017453292F - (float) Math.PI);
			float var13 = -var12;
			float var14 = -MathHelper.sin(-var3 * 0.017453292F - (float) Math.PI);
			float var15 = -var11;
			float var16 = 0.0F;
			float var17 = 1.0F;
			float var18 = 0.0F;
			sndSystem.setListenerPosition((float) var5, (float) var7, (float) var9);
			sndSystem.setListenerOrientation(var13, var14, var15, var16, var17, var18);
		}
	}
	
	public void stopAllSounds()
	{
		if(loaded)
		{
			Iterator var1 = playingSounds.iterator();
			while(var1.hasNext())
			{
				String var2 = (String) var1.next();
				sndSystem.stop(var2);
			}
			playingSounds.clear();
		}
	}
	
	public void stopEntitySound(Entity par1Entity)
	{
		if(par1Entity != null && loaded)
		{
			String var2 = "entity_" + par1Entity.entityId;
			if(playingSounds.contains(var2))
			{
				if(sndSystem.playing(var2))
				{
					sndSystem.stop(var2);
				}
				playingSounds.remove(var2);
			}
		}
	}
	
	private synchronized void tryToSetLibraryAndCodecs()
	{
		if(!loaded)
		{
			float var1 = options.soundVolume;
			float var2 = options.musicVolume;
			options.soundVolume = 0.0F;
			options.musicVolume = 0.0F;
			options.saveOptions();
			try
			{
				new Thread(new SoundManagerINNER1(this)).start();
				options.soundVolume = var1;
				options.musicVolume = var2;
			} catch(RuntimeException var4)
			{
				var4.printStackTrace();
				System.err.println("error starting SoundSystem turning off sounds & music");
				options.soundVolume = 0.0F;
				options.musicVolume = 0.0F;
			}
			options.saveOptions();
		}
	}
	
	public void updateSoundLocation(Entity par1Entity)
	{
		this.updateSoundLocation(par1Entity, par1Entity);
	}
	
	public void updateSoundLocation(Entity par1Entity, Entity par2Entity)
	{
		String var3 = "entity_" + par1Entity.entityId;
		if(playingSounds.contains(var3))
		{
			if(sndSystem.playing(var3))
			{
				sndSystem.setPosition(var3, (float) par2Entity.posX, (float) par2Entity.posY, (float) par2Entity.posZ);
				sndSystem.setVelocity(var3, (float) par2Entity.motionX, (float) par2Entity.motionY, (float) par2Entity.motionZ);
			} else
			{
				playingSounds.remove(var3);
			}
		}
	}
	
	static SoundSystem func_130080_a(SoundManager par0SoundManager, SoundSystem par1SoundSystem)
	{
		return par0SoundManager.sndSystem = par1SoundSystem;
	}
	
	static boolean func_130082_a(SoundManager par0SoundManager, boolean par1)
	{
		return par0SoundManager.loaded = par1;
	}
}
