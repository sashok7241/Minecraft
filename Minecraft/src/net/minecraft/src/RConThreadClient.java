package net.minecraft.src;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class RConThreadClient extends RConThreadBase
{
	private boolean loggedIn = false;
	private Socket clientSocket;
	private byte[] buffer = new byte[1460];
	private String rconPassword;
	
	RConThreadClient(IServer p_i3407_1_, Socket p_i3407_2_)
	{
		super(p_i3407_1_);
		clientSocket = p_i3407_2_;
		try
		{
			clientSocket.setSoTimeout(0);
		} catch(Exception var4)
		{
			running = false;
		}
		rconPassword = p_i3407_1_.getStringProperty("rcon.password", "");
		logInfo("Rcon connection from: " + p_i3407_2_.getInetAddress());
	}
	
	private void closeSocket()
	{
		if(null != clientSocket)
		{
			try
			{
				clientSocket.close();
			} catch(IOException var2)
			{
				logWarning("IO: " + var2.getMessage());
			}
			clientSocket = null;
		}
	}
	
	@Override public void run()
	{
		try
		{
			while(true)
			{
				if(!running)
				{
					break;
				}
				BufferedInputStream var1 = new BufferedInputStream(clientSocket.getInputStream());
				int var2 = var1.read(buffer, 0, 1460);
				if(10 <= var2)
				{
					byte var3 = 0;
					int var4 = RConUtils.getBytesAsLEInt(buffer, 0, var2);
					if(var4 != var2 - 4) return;
					int var21 = var3 + 4;
					int var5 = RConUtils.getBytesAsLEInt(buffer, var21, var2);
					var21 += 4;
					int var6 = RConUtils.getRemainingBytesAsLEInt(buffer, var21);
					var21 += 4;
					switch(var6)
					{
						case 2:
							if(loggedIn)
							{
								String var8 = RConUtils.getBytesAsString(buffer, var21, var2);
								try
								{
									sendMultipacketResponse(var5, server.executeCommand(var8));
								} catch(Exception var16)
								{
									sendMultipacketResponse(var5, "Error executing: " + var8 + " (" + var16.getMessage() + ")");
								}
								continue;
							}
							sendLoginFailedResponse();
							continue;
						case 3:
							String var7 = RConUtils.getBytesAsString(buffer, var21, var2);
							int var10000 = var21 + var7.length();
							if(0 != var7.length() && var7.equals(rconPassword))
							{
								loggedIn = true;
								sendResponse(var5, 2, "");
								continue;
							}
							loggedIn = false;
							sendLoginFailedResponse();
							continue;
						default:
							sendMultipacketResponse(var5, String.format("Unknown request %s", new Object[] { Integer.toHexString(var6) }));
							continue;
					}
				}
			}
		} catch(SocketTimeoutException var17)
		{
		} catch(IOException var18)
		{
		} catch(Exception var19)
		{
			System.out.println(var19);
		} finally
		{
			this.closeSocket();
		}
	}
	
	private void sendLoginFailedResponse() throws IOException
	{
		sendResponse(-1, 2, "");
	}
	
	private void sendMultipacketResponse(int p_72655_1_, String p_72655_2_) throws IOException
	{
		int var3 = p_72655_2_.length();
		do
		{
			int var4 = 4096 <= var3 ? 4096 : var3;
			sendResponse(p_72655_1_, 0, p_72655_2_.substring(0, var4));
			p_72655_2_ = p_72655_2_.substring(var4);
			var3 = p_72655_2_.length();
		} while(0 != var3);
	}
	
	private void sendResponse(int p_72654_1_, int p_72654_2_, String p_72654_3_) throws IOException
	{
		ByteArrayOutputStream var4 = new ByteArrayOutputStream(1248);
		DataOutputStream var5 = new DataOutputStream(var4);
		var5.writeInt(Integer.reverseBytes(p_72654_3_.length() + 10));
		var5.writeInt(Integer.reverseBytes(p_72654_1_));
		var5.writeInt(Integer.reverseBytes(p_72654_2_));
		var5.writeBytes(p_72654_3_);
		var5.write(0);
		var5.write(0);
		clientSocket.getOutputStream().write(var4.toByteArray());
	}
}
