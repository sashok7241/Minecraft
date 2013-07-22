package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class TileEntityCommandBlock extends TileEntity implements ICommandSender
{
	private int succesCount = 0;
	private String command = "";
	private String commandSenderName = "@";
	
	@Override public boolean canCommandSenderUseCommand(int par1, String par2Str)
	{
		return par1 <= 2;
	}
	
	public int executeCommandOnPowered(World par1World)
	{
		if(par1World.isRemote) return 0;
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
	
	public void func_96102_a(int par1)
	{
		succesCount = par1;
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
	
	@Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		command = par1NBTTagCompound.getString("Command");
		succesCount = par1NBTTagCompound.getInteger("SuccessCount");
		if(par1NBTTagCompound.hasKey("CustomName"))
		{
			commandSenderName = par1NBTTagCompound.getString("CustomName");
		}
	}
	
	@Override public void sendChatToPlayer(String par1Str)
	{
	}
	
	public void setCommand(String par1Str)
	{
		command = par1Str;
		onInventoryChanged();
	}
	
	public void setCommandSenderName(String par1Str)
	{
		commandSenderName = par1Str;
	}
	
	@Override public String translateString(String par1Str, Object ... par2ArrayOfObj)
	{
		return par1Str;
	}
	
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setString("Command", command);
		par1NBTTagCompound.setInteger("SuccessCount", succesCount);
		par1NBTTagCompound.setString("CustomName", commandSenderName);
	}
}
