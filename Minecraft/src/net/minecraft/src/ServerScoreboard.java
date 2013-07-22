package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.server.MinecraftServer;

public class ServerScoreboard extends Scoreboard
{
	private final MinecraftServer field_96555_a;
	private final Set field_96553_b = new HashSet();
	private ScoreboardSaveData field_96554_c;
	
	public ServerScoreboard(MinecraftServer p_i10044_1_)
	{
		field_96555_a = p_i10044_1_;
	}
	
	@Override public void func_96513_c(ScorePlayerTeam p_96513_1_)
	{
		super.func_96513_c(p_96513_1_);
		field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet209SetPlayerTeam(p_96513_1_, 1));
		func_96551_b();
	}
	
	@Override public void func_96516_a(String p_96516_1_)
	{
		super.func_96516_a(p_96516_1_);
		field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet207SetScore(p_96516_1_));
		func_96551_b();
	}
	
	@Override public void func_96521_a(String p_96521_1_, ScorePlayerTeam p_96521_2_)
	{
		super.func_96521_a(p_96521_1_, p_96521_2_);
		field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet209SetPlayerTeam(p_96521_2_, Arrays.asList(new String[] { p_96521_1_ }), 3));
		func_96551_b();
	}
	
	@Override public void func_96522_a(ScoreObjective p_96522_1_)
	{
		super.func_96522_a(p_96522_1_);
		func_96551_b();
	}
	
	@Override public void func_96523_a(ScorePlayerTeam p_96523_1_)
	{
		super.func_96523_a(p_96523_1_);
		field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet209SetPlayerTeam(p_96523_1_, 0));
		func_96551_b();
	}
	
	@Override public void func_96530_a(int p_96530_1_, ScoreObjective p_96530_2_)
	{
		ScoreObjective var3 = func_96539_a(p_96530_1_);
		super.func_96530_a(p_96530_1_, p_96530_2_);
		if(var3 != p_96530_2_ && var3 != null)
		{
			if(func_96552_h(var3) > 0)
			{
				field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet208SetDisplayObjective(p_96530_1_, p_96530_2_));
			} else
			{
				func_96546_g(var3);
			}
		}
		if(p_96530_2_ != null)
		{
			if(field_96553_b.contains(p_96530_2_))
			{
				field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet208SetDisplayObjective(p_96530_1_, p_96530_2_));
			} else
			{
				func_96549_e(p_96530_2_);
			}
		}
		func_96551_b();
	}
	
	@Override public void func_96532_b(ScoreObjective p_96532_1_)
	{
		super.func_96532_b(p_96532_1_);
		if(field_96553_b.contains(p_96532_1_))
		{
			field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet206SetObjective(p_96532_1_, 2));
		}
		func_96551_b();
	}
	
	@Override public void func_96533_c(ScoreObjective p_96533_1_)
	{
		super.func_96533_c(p_96533_1_);
		if(field_96553_b.contains(p_96533_1_))
		{
			func_96546_g(p_96533_1_);
		}
		func_96551_b();
	}
	
	@Override public void func_96536_a(Score p_96536_1_)
	{
		super.func_96536_a(p_96536_1_);
		if(field_96553_b.contains(p_96536_1_.func_96645_d()))
		{
			field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet207SetScore(p_96536_1_, 0));
		}
		func_96551_b();
	}
	
	@Override public void func_96538_b(ScorePlayerTeam p_96538_1_)
	{
		super.func_96538_b(p_96538_1_);
		field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet209SetPlayerTeam(p_96538_1_, 2));
		func_96551_b();
	}
	
	public void func_96546_g(ScoreObjective p_96546_1_)
	{
		List var2 = func_96548_f(p_96546_1_);
		Iterator var3 = field_96555_a.getConfigurationManager().playerEntityList.iterator();
		while(var3.hasNext())
		{
			EntityPlayerMP var4 = (EntityPlayerMP) var3.next();
			Iterator var5 = var2.iterator();
			while(var5.hasNext())
			{
				Packet var6 = (Packet) var5.next();
				var4.playerNetServerHandler.sendPacketToPlayer(var6);
			}
		}
		field_96553_b.remove(p_96546_1_);
	}
	
	public void func_96547_a(ScoreboardSaveData p_96547_1_)
	{
		field_96554_c = p_96547_1_;
	}
	
	public List func_96548_f(ScoreObjective p_96548_1_)
	{
		ArrayList var2 = new ArrayList();
		var2.add(new Packet206SetObjective(p_96548_1_, 1));
		for(int var3 = 0; var3 < 3; ++var3)
		{
			if(func_96539_a(var3) == p_96548_1_)
			{
				var2.add(new Packet208SetDisplayObjective(var3, p_96548_1_));
			}
		}
		return var2;
	}
	
	public void func_96549_e(ScoreObjective p_96549_1_)
	{
		List var2 = func_96550_d(p_96549_1_);
		Iterator var3 = field_96555_a.getConfigurationManager().playerEntityList.iterator();
		while(var3.hasNext())
		{
			EntityPlayerMP var4 = (EntityPlayerMP) var3.next();
			Iterator var5 = var2.iterator();
			while(var5.hasNext())
			{
				Packet var6 = (Packet) var5.next();
				var4.playerNetServerHandler.sendPacketToPlayer(var6);
			}
		}
		field_96553_b.add(p_96549_1_);
	}
	
	public List func_96550_d(ScoreObjective p_96550_1_)
	{
		ArrayList var2 = new ArrayList();
		var2.add(new Packet206SetObjective(p_96550_1_, 0));
		for(int var3 = 0; var3 < 3; ++var3)
		{
			if(func_96539_a(var3) == p_96550_1_)
			{
				var2.add(new Packet208SetDisplayObjective(var3, p_96550_1_));
			}
		}
		Iterator var5 = func_96534_i(p_96550_1_).iterator();
		while(var5.hasNext())
		{
			Score var4 = (Score) var5.next();
			var2.add(new Packet207SetScore(var4, 0));
		}
		return var2;
	}
	
	protected void func_96551_b()
	{
		if(field_96554_c != null)
		{
			field_96554_c.markDirty();
		}
	}
	
	public int func_96552_h(ScoreObjective p_96552_1_)
	{
		int var2 = 0;
		for(int var3 = 0; var3 < 3; ++var3)
		{
			if(func_96539_a(var3) == p_96552_1_)
			{
				++var2;
			}
		}
		return var2;
	}
	
	@Override public void removePlayerFromTeam(String p_96512_1_, ScorePlayerTeam p_96512_2_)
	{
		super.removePlayerFromTeam(p_96512_1_, p_96512_2_);
		field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet209SetPlayerTeam(p_96512_2_, Arrays.asList(new String[] { p_96512_1_ }), 4));
		func_96551_b();
	}
}
