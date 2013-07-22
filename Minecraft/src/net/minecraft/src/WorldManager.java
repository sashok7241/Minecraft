package net.minecraft.src;

import java.util.Iterator;

import net.minecraft.server.MinecraftServer;

public class WorldManager implements IWorldAccess
{
	private MinecraftServer mcServer;
	private WorldServer theWorldServer;
	
	public WorldManager(MinecraftServer p_i3390_1_, WorldServer p_i3390_2_)
	{
		mcServer = p_i3390_1_;
		theWorldServer = p_i3390_2_;
	}
	
	@Override public void broadcastSound(int p_82746_1_, int p_82746_2_, int p_82746_3_, int p_82746_4_, int p_82746_5_)
	{
		mcServer.getConfigurationManager().sendPacketToAllPlayers(new Packet61DoorChange(p_82746_1_, p_82746_2_, p_82746_3_, p_82746_4_, p_82746_5_, true));
	}
	
	@Override public void destroyBlockPartially(int p_72705_1_, int p_72705_2_, int p_72705_3_, int p_72705_4_, int p_72705_5_)
	{
		Iterator var6 = mcServer.getConfigurationManager().playerEntityList.iterator();
		while(var6.hasNext())
		{
			EntityPlayerMP var7 = (EntityPlayerMP) var6.next();
			if(var7 != null && var7.worldObj == theWorldServer && var7.entityId != p_72705_1_)
			{
				double var8 = p_72705_2_ - var7.posX;
				double var10 = p_72705_3_ - var7.posY;
				double var12 = p_72705_4_ - var7.posZ;
				if(var8 * var8 + var10 * var10 + var12 * var12 < 1024.0D)
				{
					var7.playerNetServerHandler.sendPacketToPlayer(new Packet55BlockDestroy(p_72705_1_, p_72705_2_, p_72705_3_, p_72705_4_, p_72705_5_));
				}
			}
		}
	}
	
	@Override public void markBlockForRenderUpdate(int p_72711_1_, int p_72711_2_, int p_72711_3_)
	{
	}
	
	@Override public void markBlockForUpdate(int p_72710_1_, int p_72710_2_, int p_72710_3_)
	{
		theWorldServer.getPlayerManager().markBlockForUpdate(p_72710_1_, p_72710_2_, p_72710_3_);
	}
	
	@Override public void markBlockRangeForRenderUpdate(int p_72707_1_, int p_72707_2_, int p_72707_3_, int p_72707_4_, int p_72707_5_, int p_72707_6_)
	{
	}
	
	@Override public void onEntityCreate(Entity p_72703_1_)
	{
		theWorldServer.getEntityTracker().addEntityToTracker(p_72703_1_);
	}
	
	@Override public void onEntityDestroy(Entity p_72709_1_)
	{
		theWorldServer.getEntityTracker().removeEntityFromAllTrackingPlayers(p_72709_1_);
	}
	
	@Override public void playAuxSFX(EntityPlayer p_72706_1_, int p_72706_2_, int p_72706_3_, int p_72706_4_, int p_72706_5_, int p_72706_6_)
	{
		mcServer.getConfigurationManager().sendToAllNearExcept(p_72706_1_, p_72706_3_, p_72706_4_, p_72706_5_, 64.0D, theWorldServer.provider.dimensionId, new Packet61DoorChange(p_72706_2_, p_72706_3_, p_72706_4_, p_72706_5_, p_72706_6_, false));
	}
	
	@Override public void playRecord(String p_72702_1_, int p_72702_2_, int p_72702_3_, int p_72702_4_)
	{
	}
	
	@Override public void playSound(String p_72704_1_, double p_72704_2_, double p_72704_4_, double p_72704_6_, float p_72704_8_, float p_72704_9_)
	{
		mcServer.getConfigurationManager().sendToAllNear(p_72704_2_, p_72704_4_, p_72704_6_, p_72704_8_ > 1.0F ? (double) (16.0F * p_72704_8_) : 16.0D, theWorldServer.provider.dimensionId, new Packet62LevelSound(p_72704_1_, p_72704_2_, p_72704_4_, p_72704_6_, p_72704_8_, p_72704_9_));
	}
	
	@Override public void playSoundToNearExcept(EntityPlayer p_85102_1_, String p_85102_2_, double p_85102_3_, double p_85102_5_, double p_85102_7_, float p_85102_9_, float p_85102_10_)
	{
		mcServer.getConfigurationManager().sendToAllNearExcept(p_85102_1_, p_85102_3_, p_85102_5_, p_85102_7_, p_85102_9_ > 1.0F ? (double) (16.0F * p_85102_9_) : 16.0D, theWorldServer.provider.dimensionId, new Packet62LevelSound(p_85102_2_, p_85102_3_, p_85102_5_, p_85102_7_, p_85102_9_, p_85102_10_));
	}
	
	@Override public void spawnParticle(String p_72708_1_, double p_72708_2_, double p_72708_4_, double p_72708_6_, double p_72708_8_, double p_72708_10_, double p_72708_12_)
	{
	}
}
