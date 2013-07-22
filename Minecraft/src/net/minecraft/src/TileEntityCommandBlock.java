package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class TileEntityCommandBlock extends TileEntity implements ICommandSender
{
	private int succesCount = 0;
	private String command = "";
	private String commandSenderName = "@";
	
	@Override public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_)
	{
		return p_70003_1_ <= 2;
	}
	
	public int executeCommandOnPowered(World p_82351_1_)
	{
		if(p_82351_1_.isRemote) return 0;
		else
		{
			MinecraftServer var2 = MinecraftServer.getServer();
			if(var2 != null && var2.isCommandBlockEnabled())
			{
				ICommandManager var3 = var2.getCommandManager();
				return var3.executeCommand(this, command);
			} else return 0;
		}
	}
	
	public void func_96102_a(int p_96102_1_)
	{
		succesCount = p_96102_1_;
	}
	
	public int func_96103_d()
	{
		return succesCount;
	}
	
	public String getCommand()
	{
		return command;
	}
	
	@Override public String getCommandSenderName()
	{
		return commandSenderName;
	}
	
	@Override public Packet getDescriptionPacket()
	{
		NBTTagCompound var1 = new NBTTagCompound();
		writeToNBT(var1);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 2, var1);
	}
	
	@Override public ChunkCoordinates getPlayerCoordinates()
	{
		return new ChunkCoordinates(xCoord, yCoord, zCoord);
	}
	
	@Override public void readFromNBT(NBTTagCompound p_70307_1_)
	{
		super.readFromNBT(p_70307_1_);
		command = p_70307_1_.getString("Command");
		succesCount = p_70307_1_.getInteger("SuccessCount");
		if(p_70307_1_.hasKey("CustomName"))
		{
			commandSenderName = p_70307_1_.getString("CustomName");
		}
	}
	
	@Override public void sendChatToPlayer(String p_70006_1_)
	{
	}
	
	public void setCommand(String p_82352_1_)
	{
		command = p_82352_1_;
		onInventoryChanged();
	}
	
	public void setCommandSenderName(String p_96104_1_)
	{
		commandSenderName = p_96104_1_;
	}
	
	@Override public String translateString(String p_70004_1_, Object ... p_70004_2_)
	{
		return p_70004_1_;
	}
	
	@Override public void writeToNBT(NBTTagCompound p_70310_1_)
	{
		super.writeToNBT(p_70310_1_);
		p_70310_1_.setString("Command", command);
		p_70310_1_.setInteger("SuccessCount", succesCount);
		p_70310_1_.setString("CustomName", commandSenderName);
	}
}
