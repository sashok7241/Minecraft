package net.minecraft.src;

import java.net.SocketAddress;

public interface INetworkManager
{
	void addToSendQueue(Packet var1);
	
	void closeConnections();
	
	SocketAddress getSocketAddress();
	
	void networkShutdown(String var1, Object ... var2);
	
	int packetSize();
	
	void processReadPackets();
	
	void serverShutdown();
	
	void setNetHandler(NetHandler var1);
	
	void wakeThreads();
}
