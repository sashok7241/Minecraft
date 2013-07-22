package net.minecraft.src;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class LogFormatter extends Formatter
{
	private SimpleDateFormat field_98228_b;
	final LogAgent field_98229_a;
	
	private LogFormatter(LogAgent p_i11034_1_)
	{
		field_98229_a = p_i11034_1_;
		field_98228_b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	LogFormatter(LogAgent p_i11035_1_, LogAgentINNER1 p_i11035_2_)
	{
		this(p_i11035_1_);
	}
	
	@Override public String format(LogRecord p_format_1_)
	{
		StringBuilder var2 = new StringBuilder();
		var2.append(field_98228_b.format(Long.valueOf(p_format_1_.getMillis())));
		if(LogAgent.func_98237_a(field_98229_a) != null)
		{
			var2.append(LogAgent.func_98237_a(field_98229_a));
		}
		var2.append(" [").append(p_format_1_.getLevel().getName()).append("] ");
		var2.append(formatMessage(p_format_1_));
		var2.append('\n');
		Throwable var3 = p_format_1_.getThrown();
		if(var3 != null)
		{
			StringWriter var4 = new StringWriter();
			var3.printStackTrace(new PrintWriter(var4));
			var2.append(var4.toString());
		}
		return var2.toString();
	}
}
