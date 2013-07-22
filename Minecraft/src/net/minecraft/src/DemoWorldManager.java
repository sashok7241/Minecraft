package net.minecraft.src;

public class DemoWorldManager extends ItemInWorldManager
{
	private boolean field_73105_c = false;
	private boolean demoTimeExpired = false;
	private int field_73104_e = 0;
	private int field_73102_f = 0;
	
	public DemoWorldManager(World par1World)
	{
		super(par1World);
	}
	
	@Override public boolean activateBlockOrUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if(demoTimeExpired)
		{
			sendDemoReminder();
			return false;
		} else return super.activateBlockOrUseItem(par1EntityPlayer, par2World, par3ItemStack, par4, par5, par6, par7, par8, par9, par10);
	}
	
	@Override public void onBlockClicked(int par1, int par2, int par3, int par4)
	{
		if(demoTimeExpired)
		{
			sendDemoReminder();
		} else
		{
			super.onBlockClicked(par1, par2, par3, par4);
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
	
	@Override public boolean tryHarvestBlock(int par1, int par2, int par3)
	{
		return demoTimeExpired ? false : super.tryHarvestBlock(par1, par2, par3);
	}
	
	@Override public boolean tryUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack)
	{
		if(demoTimeExpired)
		{
			sendDemoReminder();
			return false;
		} else return super.tryUseItem(par1EntityPlayer, par2World, par3ItemStack);
	}
	
	@Override public void uncheckedTryHarvestBlock(int par1, int par2, int par3)
	{
		if(!demoTimeExpired)
		{
			super.uncheckedTryHarvestBlock(par1, par2, par3);
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
