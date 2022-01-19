package tweakerframework.asm.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tweakerframework.Reference;

public class ASMLogger
{
	public static Logger logger = LogManager.getLogger(Reference.NAME+" ASM");
	
	public static void info(String message)
	{
		logger.info(message);
	}
	
	public static void warn(String message)
	{
		logger.warn(message);
	}
	
	public static void error(String message)
	{
		logger.error(message);
	}
	
	public static void error(String message, Throwable t)
	{
		logger.error(message, t);
	}
}
