package net.minecraft.src;

import java.net.ConnectException;
import java.net.UnknownHostException;

class ThreadOnlineConnect extends Thread
{
	final String field_96595_a;
	final int field_96593_b;
	final TaskOnlineConnect field_96594_c;
	
	ThreadOnlineConnect(TaskOnlineConnect p_i10018_1_, String p_i10018_2_, int p_i10018_3_)
	{
		field_96594_c = p_i10018_1_;
		field_96595_a = p_i10018_2_;
		field_96593_b = p_i10018_3_;
	}
	
	@Override public void run()
	{
		try
		{
			TaskOnlineConnect.func_96583_a(field_96594_c, new NetClientHandler(field_96594_c.func_96578_b(), field_96595_a, field_96593_b, TaskOnlineConnect.func_98172_a(field_96594_c)));
			if(field_96594_c.func_96577_c()) return;
			field_96594_c.func_96576_b(StringTranslate.getInstance().translateKey("mco.connect.authorizing"));
			TaskOnlineConnect.func_96580_a(field_96594_c).addToSendQueue(new Packet2ClientProtocol(61, field_96594_c.func_96578_b().session.username, field_96595_a, field_96593_b));
		} catch(UnknownHostException var2)
		{
			if(field_96594_c.func_96577_c()) return;
			field_96594_c.func_96578_b().displayGuiScreen(new GuiScreenDisconnectedOnline(TaskOnlineConnect.func_98172_a(field_96594_c), "connect.failed", "disconnect.genericReason", new Object[] { "Unknown host \'" + field_96595_a + "\'" }));
		} catch(ConnectException var3)
		{
			if(field_96594_c.func_96577_c()) return;
			field_96594_c.func_96578_b().displayGuiScreen(new GuiScreenDisconnectedOnline(TaskOnlineConnect.func_98172_a(field_96594_c), "connect.failed", "disconnect.genericReason", new Object[] { var3.getMessage() }));
		} catch(Exception var4)
		{
			if(field_96594_c.func_96577_c()) return;
			var4.printStackTrace();
			field_96594_c.func_96578_b().displayGuiScreen(new GuiScreenDisconnectedOnline(TaskOnlineConnect.func_98172_a(field_96594_c), "connect.failed", "disconnect.genericReason", new Object[] { var4.toString() }));
		}
	}
}
