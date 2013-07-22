package net.minecraft.src;

public abstract class NetHandler
{
	public boolean canProcessPacketsAsync()
	{
		return false;
	}
	
	public void handleAnimation(Packet18Animation p_72524_1_)
	{
		unexpectedPacket(p_72524_1_);
	}
	
	public void handleAttachEntity(Packet39AttachEntity p_72484_1_)
	{
		unexpectedPacket(p_72484_1_);
	}
	
	public void handleAutoComplete(Packet203AutoComplete p_72461_1_)
	{
		unexpectedPacket(p_72461_1_);
	}
	
	public void handleBlockChange(Packet53BlockChange p_72456_1_)
	{
		unexpectedPacket(p_72456_1_);
	}
	
	public void handleBlockDestroy(Packet55BlockDestroy p_72465_1_)
	{
		unexpectedPacket(p_72465_1_);
	}
	
	public void handleBlockDig(Packet14BlockDig p_72510_1_)
	{
		unexpectedPacket(p_72510_1_);
	}
	
	public void handleBlockEvent(Packet54PlayNoteBlock p_72454_1_)
	{
		unexpectedPacket(p_72454_1_);
	}
	
	public void handleBlockItemSwitch(Packet16BlockItemSwitch p_72502_1_)
	{
		unexpectedPacket(p_72502_1_);
	}
	
	public void handleChat(Packet3Chat p_72481_1_)
	{
		unexpectedPacket(p_72481_1_);
	}
	
	public void handleClientCommand(Packet205ClientCommand p_72458_1_)
	{
	}
	
	public void handleClientInfo(Packet204ClientInfo p_72504_1_)
	{
		unexpectedPacket(p_72504_1_);
	}
	
	public void handleClientProtocol(Packet2ClientProtocol p_72500_1_)
	{
		unexpectedPacket(p_72500_1_);
	}
	
	public void handleCloseWindow(Packet101CloseWindow p_72474_1_)
	{
		unexpectedPacket(p_72474_1_);
	}
	
	public void handleCollect(Packet22Collect p_72475_1_)
	{
		unexpectedPacket(p_72475_1_);
	}
	
	public void handleCreativeSetSlot(Packet107CreativeSetSlot p_72464_1_)
	{
		unexpectedPacket(p_72464_1_);
	}
	
	public void handleCustomPayload(Packet250CustomPayload p_72501_1_)
	{
	}
	
	public void handleDestroyEntity(Packet29DestroyEntity p_72491_1_)
	{
		unexpectedPacket(p_72491_1_);
	}
	
	public void handleDoorChange(Packet61DoorChange p_72462_1_)
	{
		unexpectedPacket(p_72462_1_);
	}
	
	public void handleEnchantItem(Packet108EnchantItem p_72479_1_)
	{
	}
	
	public void handleEntity(Packet30Entity p_72482_1_)
	{
		unexpectedPacket(p_72482_1_);
	}
	
	public void handleEntityAction(Packet19EntityAction p_72473_1_)
	{
		unexpectedPacket(p_72473_1_);
	}
	
	public void handleEntityEffect(Packet41EntityEffect p_72503_1_)
	{
		unexpectedPacket(p_72503_1_);
	}
	
	public void handleEntityExpOrb(Packet26EntityExpOrb p_72514_1_)
	{
		unexpectedPacket(p_72514_1_);
	}
	
	public void handleEntityHeadRotation(Packet35EntityHeadRotation p_72478_1_)
	{
		unexpectedPacket(p_72478_1_);
	}
	
	public void handleEntityMetadata(Packet40EntityMetadata p_72493_1_)
	{
		unexpectedPacket(p_72493_1_);
	}
	
	public void handleEntityPainting(Packet25EntityPainting p_72495_1_)
	{
		unexpectedPacket(p_72495_1_);
	}
	
	public void handleEntityStatus(Packet38EntityStatus p_72485_1_)
	{
		unexpectedPacket(p_72485_1_);
	}
	
	public void handleEntityTeleport(Packet34EntityTeleport p_72512_1_)
	{
		unexpectedPacket(p_72512_1_);
	}
	
	public void handleEntityVelocity(Packet28EntityVelocity p_72520_1_)
	{
		unexpectedPacket(p_72520_1_);
	}
	
	public void handleErrorMessage(String p_72515_1_, Object[] p_72515_2_)
	{
	}
	
	public void handleExperience(Packet43Experience p_72522_1_)
	{
		unexpectedPacket(p_72522_1_);
	}
	
	public void handleExplosion(Packet60Explosion p_72499_1_)
	{
		unexpectedPacket(p_72499_1_);
	}
	
	public void handleFlying(Packet10Flying p_72498_1_)
	{
		unexpectedPacket(p_72498_1_);
	}
	
	public void handleGameEvent(Packet70GameEvent p_72488_1_)
	{
		unexpectedPacket(p_72488_1_);
	}
	
	public void handleKeepAlive(Packet0KeepAlive p_72477_1_)
	{
		unexpectedPacket(p_72477_1_);
	}
	
	public void handleKickDisconnect(Packet255KickDisconnect p_72492_1_)
	{
		unexpectedPacket(p_72492_1_);
	}
	
	public void handleLevelSound(Packet62LevelSound p_72457_1_)
	{
		unexpectedPacket(p_72457_1_);
	}
	
	public void handleLogin(Packet1Login p_72455_1_)
	{
		unexpectedPacket(p_72455_1_);
	}
	
	public void handleMapChunk(Packet51MapChunk p_72463_1_)
	{
	}
	
	public void handleMapChunks(Packet56MapChunks p_72453_1_)
	{
		unexpectedPacket(p_72453_1_);
	}
	
	public void handleMapData(Packet131MapData p_72494_1_)
	{
		unexpectedPacket(p_72494_1_);
	}
	
	public void handleMobSpawn(Packet24MobSpawn p_72519_1_)
	{
		unexpectedPacket(p_72519_1_);
	}
	
	public void handleMultiBlockChange(Packet52MultiBlockChange p_72496_1_)
	{
		unexpectedPacket(p_72496_1_);
	}
	
	public void handleNamedEntitySpawn(Packet20NamedEntitySpawn p_72518_1_)
	{
		unexpectedPacket(p_72518_1_);
	}
	
	public void handleOpenWindow(Packet100OpenWindow p_72516_1_)
	{
		unexpectedPacket(p_72516_1_);
	}
	
	public void handlePlace(Packet15Place p_72472_1_)
	{
		unexpectedPacket(p_72472_1_);
	}
	
	public void handlePlayerAbilities(Packet202PlayerAbilities p_72471_1_)
	{
		unexpectedPacket(p_72471_1_);
	}
	
	public void handlePlayerInfo(Packet201PlayerInfo p_72480_1_)
	{
		unexpectedPacket(p_72480_1_);
	}
	
	public void handlePlayerInventory(Packet5PlayerInventory p_72506_1_)
	{
		unexpectedPacket(p_72506_1_);
	}
	
	public void handleRemoveEntityEffect(Packet42RemoveEntityEffect p_72452_1_)
	{
		unexpectedPacket(p_72452_1_);
	}
	
	public void handleRespawn(Packet9Respawn p_72483_1_)
	{
		unexpectedPacket(p_72483_1_);
	}
	
	public void handleServerAuthData(Packet253ServerAuthData p_72470_1_)
	{
		unexpectedPacket(p_72470_1_);
	}
	
	public void handleServerPing(Packet254ServerPing p_72467_1_)
	{
		unexpectedPacket(p_72467_1_);
	}
	
	public void handleSetDisplayObjective(Packet208SetDisplayObjective p_96438_1_)
	{
		unexpectedPacket(p_96438_1_);
	}
	
	public void handleSetObjective(Packet206SetObjective p_96436_1_)
	{
		unexpectedPacket(p_96436_1_);
	}
	
	public void handleSetPlayerTeam(Packet209SetPlayerTeam p_96435_1_)
	{
		unexpectedPacket(p_96435_1_);
	}
	
	public void handleSetScore(Packet207SetScore p_96437_1_)
	{
		unexpectedPacket(p_96437_1_);
	}
	
	public void handleSetSlot(Packet103SetSlot p_72490_1_)
	{
		unexpectedPacket(p_72490_1_);
	}
	
	public void handleSharedKey(Packet252SharedKey p_72513_1_)
	{
		unexpectedPacket(p_72513_1_);
	}
	
	public void handleSleep(Packet17Sleep p_72460_1_)
	{
		unexpectedPacket(p_72460_1_);
	}
	
	public void handleSpawnPosition(Packet6SpawnPosition p_72466_1_)
	{
		unexpectedPacket(p_72466_1_);
	}
	
	public void handleStatistic(Packet200Statistic p_72517_1_)
	{
		unexpectedPacket(p_72517_1_);
	}
	
	public void handleTileEntityData(Packet132TileEntityData p_72468_1_)
	{
		unexpectedPacket(p_72468_1_);
	}
	
	public void handleTransaction(Packet106Transaction p_72476_1_)
	{
		unexpectedPacket(p_72476_1_);
	}
	
	public void handleUpdateHealth(Packet8UpdateHealth p_72521_1_)
	{
		unexpectedPacket(p_72521_1_);
	}
	
	public void handleUpdateProgressbar(Packet105UpdateProgressbar p_72505_1_)
	{
		unexpectedPacket(p_72505_1_);
	}
	
	public void handleUpdateSign(Packet130UpdateSign p_72487_1_)
	{
		unexpectedPacket(p_72487_1_);
	}
	
	public void handleUpdateTime(Packet4UpdateTime p_72497_1_)
	{
		unexpectedPacket(p_72497_1_);
	}
	
	public void handleUseEntity(Packet7UseEntity p_72507_1_)
	{
		unexpectedPacket(p_72507_1_);
	}
	
	public void handleVehicleSpawn(Packet23VehicleSpawn p_72511_1_)
	{
		unexpectedPacket(p_72511_1_);
	}
	
	public void handleWeather(Packet71Weather p_72508_1_)
	{
		unexpectedPacket(p_72508_1_);
	}
	
	public void handleWindowClick(Packet102WindowClick p_72523_1_)
	{
		unexpectedPacket(p_72523_1_);
	}
	
	public void handleWindowItems(Packet104WindowItems p_72486_1_)
	{
		unexpectedPacket(p_72486_1_);
	}
	
	public void handleWorldParticles(Packet63WorldParticles p_98182_1_)
	{
		unexpectedPacket(p_98182_1_);
	}
	
	public abstract boolean isServerHandler();
	
	public void unexpectedPacket(Packet p_72509_1_)
	{
	}
}
