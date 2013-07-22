package net.minecraft.src;

public abstract class NetHandler
{
	public boolean canProcessPacketsAsync()
	{
		return false;
	}
	
	public void handleAnimation(Packet18Animation par1Packet18Animation)
	{
		unexpectedPacket(par1Packet18Animation);
	}
	
	public void handleAttachEntity(Packet39AttachEntity par1Packet39AttachEntity)
	{
		unexpectedPacket(par1Packet39AttachEntity);
	}
	
	public void handleAutoComplete(Packet203AutoComplete par1Packet203AutoComplete)
	{
		unexpectedPacket(par1Packet203AutoComplete);
	}
	
	public void handleBlockChange(Packet53BlockChange par1Packet53BlockChange)
	{
		unexpectedPacket(par1Packet53BlockChange);
	}
	
	public void handleBlockDestroy(Packet55BlockDestroy par1Packet55BlockDestroy)
	{
		unexpectedPacket(par1Packet55BlockDestroy);
	}
	
	public void handleBlockDig(Packet14BlockDig par1Packet14BlockDig)
	{
		unexpectedPacket(par1Packet14BlockDig);
	}
	
	public void handleBlockEvent(Packet54PlayNoteBlock par1Packet54PlayNoteBlock)
	{
		unexpectedPacket(par1Packet54PlayNoteBlock);
	}
	
	public void handleBlockItemSwitch(Packet16BlockItemSwitch par1Packet16BlockItemSwitch)
	{
		unexpectedPacket(par1Packet16BlockItemSwitch);
	}
	
	public void handleChat(Packet3Chat par1Packet3Chat)
	{
		unexpectedPacket(par1Packet3Chat);
	}
	
	public void handleClientCommand(Packet205ClientCommand par1Packet205ClientCommand)
	{
	}
	
	public void handleClientInfo(Packet204ClientInfo par1Packet204ClientInfo)
	{
		unexpectedPacket(par1Packet204ClientInfo);
	}
	
	public void handleClientProtocol(Packet2ClientProtocol par1Packet2ClientProtocol)
	{
		unexpectedPacket(par1Packet2ClientProtocol);
	}
	
	public void handleCloseWindow(Packet101CloseWindow par1Packet101CloseWindow)
	{
		unexpectedPacket(par1Packet101CloseWindow);
	}
	
	public void handleCollect(Packet22Collect par1Packet22Collect)
	{
		unexpectedPacket(par1Packet22Collect);
	}
	
	public void handleCreativeSetSlot(Packet107CreativeSetSlot par1Packet107CreativeSetSlot)
	{
		unexpectedPacket(par1Packet107CreativeSetSlot);
	}
	
	public void handleCustomPayload(Packet250CustomPayload par1Packet250CustomPayload)
	{
	}
	
	public void handleDestroyEntity(Packet29DestroyEntity par1Packet29DestroyEntity)
	{
		unexpectedPacket(par1Packet29DestroyEntity);
	}
	
	public void handleDoorChange(Packet61DoorChange par1Packet61DoorChange)
	{
		unexpectedPacket(par1Packet61DoorChange);
	}
	
	public void handleEnchantItem(Packet108EnchantItem par1Packet108EnchantItem)
	{
	}
	
	public void handleEntity(Packet30Entity par1Packet30Entity)
	{
		unexpectedPacket(par1Packet30Entity);
	}
	
	public void handleEntityAction(Packet19EntityAction par1Packet19EntityAction)
	{
		unexpectedPacket(par1Packet19EntityAction);
	}
	
	public void handleEntityEffect(Packet41EntityEffect par1Packet41EntityEffect)
	{
		unexpectedPacket(par1Packet41EntityEffect);
	}
	
	public void handleEntityExpOrb(Packet26EntityExpOrb par1Packet26EntityExpOrb)
	{
		unexpectedPacket(par1Packet26EntityExpOrb);
	}
	
	public void handleEntityHeadRotation(Packet35EntityHeadRotation par1Packet35EntityHeadRotation)
	{
		unexpectedPacket(par1Packet35EntityHeadRotation);
	}
	
	public void handleEntityMetadata(Packet40EntityMetadata par1Packet40EntityMetadata)
	{
		unexpectedPacket(par1Packet40EntityMetadata);
	}
	
	public void handleEntityPainting(Packet25EntityPainting par1Packet25EntityPainting)
	{
		unexpectedPacket(par1Packet25EntityPainting);
	}
	
	public void handleEntityStatus(Packet38EntityStatus par1Packet38EntityStatus)
	{
		unexpectedPacket(par1Packet38EntityStatus);
	}
	
	public void handleEntityTeleport(Packet34EntityTeleport par1Packet34EntityTeleport)
	{
		unexpectedPacket(par1Packet34EntityTeleport);
	}
	
	public void handleEntityVelocity(Packet28EntityVelocity par1Packet28EntityVelocity)
	{
		unexpectedPacket(par1Packet28EntityVelocity);
	}
	
	public void handleErrorMessage(String par1Str, Object[] par2ArrayOfObj)
	{
	}
	
	public void handleExperience(Packet43Experience par1Packet43Experience)
	{
		unexpectedPacket(par1Packet43Experience);
	}
	
	public void handleExplosion(Packet60Explosion par1Packet60Explosion)
	{
		unexpectedPacket(par1Packet60Explosion);
	}
	
	public void handleFlying(Packet10Flying par1Packet10Flying)
	{
		unexpectedPacket(par1Packet10Flying);
	}
	
	public void handleGameEvent(Packet70GameEvent par1Packet70GameEvent)
	{
		unexpectedPacket(par1Packet70GameEvent);
	}
	
	public void handleKeepAlive(Packet0KeepAlive par1Packet0KeepAlive)
	{
		unexpectedPacket(par1Packet0KeepAlive);
	}
	
	public void handleKickDisconnect(Packet255KickDisconnect par1Packet255KickDisconnect)
	{
		unexpectedPacket(par1Packet255KickDisconnect);
	}
	
	public void handleLevelSound(Packet62LevelSound par1Packet62LevelSound)
	{
		unexpectedPacket(par1Packet62LevelSound);
	}
	
	public void handleLogin(Packet1Login par1Packet1Login)
	{
		unexpectedPacket(par1Packet1Login);
	}
	
	public void handleMapChunk(Packet51MapChunk par1Packet51MapChunk)
	{
	}
	
	public void handleMapChunks(Packet56MapChunks par1Packet56MapChunks)
	{
		unexpectedPacket(par1Packet56MapChunks);
	}
	
	public void handleMapData(Packet131MapData par1Packet131MapData)
	{
		unexpectedPacket(par1Packet131MapData);
	}
	
	public void handleMobSpawn(Packet24MobSpawn par1Packet24MobSpawn)
	{
		unexpectedPacket(par1Packet24MobSpawn);
	}
	
	public void handleMultiBlockChange(Packet52MultiBlockChange par1Packet52MultiBlockChange)
	{
		unexpectedPacket(par1Packet52MultiBlockChange);
	}
	
	public void handleNamedEntitySpawn(Packet20NamedEntitySpawn par1Packet20NamedEntitySpawn)
	{
		unexpectedPacket(par1Packet20NamedEntitySpawn);
	}
	
	public void handleOpenWindow(Packet100OpenWindow par1Packet100OpenWindow)
	{
		unexpectedPacket(par1Packet100OpenWindow);
	}
	
	public void handlePlace(Packet15Place par1Packet15Place)
	{
		unexpectedPacket(par1Packet15Place);
	}
	
	public void handlePlayerAbilities(Packet202PlayerAbilities par1Packet202PlayerAbilities)
	{
		unexpectedPacket(par1Packet202PlayerAbilities);
	}
	
	public void handlePlayerInfo(Packet201PlayerInfo par1Packet201PlayerInfo)
	{
		unexpectedPacket(par1Packet201PlayerInfo);
	}
	
	public void handlePlayerInventory(Packet5PlayerInventory par1Packet5PlayerInventory)
	{
		unexpectedPacket(par1Packet5PlayerInventory);
	}
	
	public void handleRemoveEntityEffect(Packet42RemoveEntityEffect par1Packet42RemoveEntityEffect)
	{
		unexpectedPacket(par1Packet42RemoveEntityEffect);
	}
	
	public void handleRespawn(Packet9Respawn par1Packet9Respawn)
	{
		unexpectedPacket(par1Packet9Respawn);
	}
	
	public void handleServerAuthData(Packet253ServerAuthData par1Packet253ServerAuthData)
	{
		unexpectedPacket(par1Packet253ServerAuthData);
	}
	
	public void handleServerPing(Packet254ServerPing par1Packet254ServerPing)
	{
		unexpectedPacket(par1Packet254ServerPing);
	}
	
	public void handleSetDisplayObjective(Packet208SetDisplayObjective par1Packet208SetDisplayObjective)
	{
		unexpectedPacket(par1Packet208SetDisplayObjective);
	}
	
	public void handleSetObjective(Packet206SetObjective par1Packet206SetObjective)
	{
		unexpectedPacket(par1Packet206SetObjective);
	}
	
	public void handleSetPlayerTeam(Packet209SetPlayerTeam par1Packet209SetPlayerTeam)
	{
		unexpectedPacket(par1Packet209SetPlayerTeam);
	}
	
	public void handleSetScore(Packet207SetScore par1Packet207SetScore)
	{
		unexpectedPacket(par1Packet207SetScore);
	}
	
	public void handleSetSlot(Packet103SetSlot par1Packet103SetSlot)
	{
		unexpectedPacket(par1Packet103SetSlot);
	}
	
	public void handleSharedKey(Packet252SharedKey par1Packet252SharedKey)
	{
		unexpectedPacket(par1Packet252SharedKey);
	}
	
	public void handleSleep(Packet17Sleep par1Packet17Sleep)
	{
		unexpectedPacket(par1Packet17Sleep);
	}
	
	public void handleSpawnPosition(Packet6SpawnPosition par1Packet6SpawnPosition)
	{
		unexpectedPacket(par1Packet6SpawnPosition);
	}
	
	public void handleStatistic(Packet200Statistic par1Packet200Statistic)
	{
		unexpectedPacket(par1Packet200Statistic);
	}
	
	public void handleTileEntityData(Packet132TileEntityData par1Packet132TileEntityData)
	{
		unexpectedPacket(par1Packet132TileEntityData);
	}
	
	public void handleTransaction(Packet106Transaction par1Packet106Transaction)
	{
		unexpectedPacket(par1Packet106Transaction);
	}
	
	public void handleUpdateHealth(Packet8UpdateHealth par1Packet8UpdateHealth)
	{
		unexpectedPacket(par1Packet8UpdateHealth);
	}
	
	public void handleUpdateProgressbar(Packet105UpdateProgressbar par1Packet105UpdateProgressbar)
	{
		unexpectedPacket(par1Packet105UpdateProgressbar);
	}
	
	public void handleUpdateSign(Packet130UpdateSign par1Packet130UpdateSign)
	{
		unexpectedPacket(par1Packet130UpdateSign);
	}
	
	public void handleUpdateTime(Packet4UpdateTime par1Packet4UpdateTime)
	{
		unexpectedPacket(par1Packet4UpdateTime);
	}
	
	public void handleUseEntity(Packet7UseEntity par1Packet7UseEntity)
	{
		unexpectedPacket(par1Packet7UseEntity);
	}
	
	public void handleVehicleSpawn(Packet23VehicleSpawn par1Packet23VehicleSpawn)
	{
		unexpectedPacket(par1Packet23VehicleSpawn);
	}
	
	public void handleWeather(Packet71Weather par1Packet71Weather)
	{
		unexpectedPacket(par1Packet71Weather);
	}
	
	public void handleWindowClick(Packet102WindowClick par1Packet102WindowClick)
	{
		unexpectedPacket(par1Packet102WindowClick);
	}
	
	public void handleWindowItems(Packet104WindowItems par1Packet104WindowItems)
	{
		unexpectedPacket(par1Packet104WindowItems);
	}
	
	public void handleWorldParticles(Packet63WorldParticles par1Packet63WorldParticles)
	{
		unexpectedPacket(par1Packet63WorldParticles);
	}
	
	public abstract boolean isServerHandler();
	
	public void unexpectedPacket(Packet par1Packet)
	{
	}
}
