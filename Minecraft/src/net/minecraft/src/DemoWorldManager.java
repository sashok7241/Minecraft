package net.minecraft.src;

public class DemoWorldManager extends ItemInWorldManager
{
	private boolean field_73105_c = false;
	private boolean demoTimeExpired = false;
	private int field_73104_e = 0;
	private int field_73102_f = 0;
	
	public DemoWorldManager(World p_i3387_1_)
	{
		super(p_i3387_1_);
	}
	
	@Override public boolean activateBlockOrUseItem(EntityPlayer p_73078_1_, World p_73078_2_, ItemStack p_73078_3_, int p_73078_4_, int p_73078_5_, int p_73078_6_, int p_73078_7_, float p_73078_8_, float p_73078_9_, float p_73078_10_)
	{
		if(demoTimeExpired)
		{
			sendDemoReminder();
			return false;
		} else return super.activateBlockOrUseItem(p_73078_1_, p_73078_2_, p_73078_3_, p_73078_4_, p_73078_5_, p_73078_6_, p_73078_7_, p_73078_8_, p_73078_9_, p_73078_10_);
	}
	
	@Override public void onBlockClicked(int p_73074_1_, int p_73074_2_, int p_73074_3_, int p_73074_4_)
	{
		if(demoTimeExpired)
		{
			sendDemoReminder();
		} else
		{
			super.onBlockClicked(p_73074_1_, p_73074_2_, p_73074_3_, p_73074_4_);
		}
	}
	
	private void sendDemoReminder()
	{
		if(field_73104_e > 100)
		{
			thisPlayerMP.sendChatToPlayer(thisPlayerMP.translateString("demo.reminder", new Object[0]));
			field_73104_e = 0;
		}
	}
	
	@Override public boolean tryHarvestBlock(int p_73084_1_, int p_73084_2_, int p_73084_3_)
	{
		return demoTimeExpired ? false : super.tryHarvestBlock(p_73084_1_, p_73084_2_, p_73084_3_);
	}
	
	@Override public boolean tryUseItem(EntityPlayer p_73085_1_, World p_73085_2_, ItemStack p_73085_3_)
	{
		if(demoTimeExpired)
		{
			sendDemoReminder();
			return false;
		} else return super.tryUseItem(p_73085_1_, p_73085_2_, p_73085_3_);
	}
	
	@Override public void uncheckedTryHarvestBlock(int p_73082_1_, int p_73082_2_, int p_73082_3_)
	{
		if(!demoTimeExpired)
		{
			super.uncheckedTryHarvestBlock(p_73082_1_, p_73082_2_, p_73082_3_);
		}
	}
	
	@Override public void updateBlockRemoving()
	{
		super.updateBlockRemoving();
		++field_73102_f;
		long var1 = theWorld.getTotalWorldTime();
		long var3 = var1 / 24000L + 1L;
		if(!field_73105_c && field_73102_f > 20)
		{
			field_73105_c = true;
			thisPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(5, 0));
		}
		demoTimeExpired = var1 > 120500L;
		if(demoTimeExpired)
		{
			++field_73104_e;
		}
		if(var1 % 24000L == 500L)
		{
			if(var3 <= 6L)
			{
				thisPlayerMP.sendChatToPlayer(thisPlayerMP.translateString("demo.day." + var3, new Object[0]));
			}
		} else if(var3 == 1L)
		{
			if(var1 == 100L)
			{
				thisPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(5, 101));
			} else if(var1 == 175L)
			{
				thisPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(5, 102));
			} else if(var1 == 250L)
			{
				thisPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(5, 103));
			}
		} else if(var3 == 5L && var1 % 24000L == 22000L)
		{
			thisPlayerMP.sendChatToPlayer(thisPlayerMP.translateString("demo.day.warning", new Object[0]));
		}
	}
}
