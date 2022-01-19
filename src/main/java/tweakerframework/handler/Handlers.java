package tweakerframework.handler;

import java.util.HashMap;
import java.util.Map;

import tweakerframework.TweakerFramework;

public class Handlers
{
	public static Map<String, Object> handlers = new HashMap<>();
	public static Map<String, Object> clientHandlers = new HashMap<>();
	
	public static void addHandler(Handler handler)
	{
		TweakerFramework.logger.info("Adding handler: "+handler.modName);
		handlers.put(handler.modName, handler);
	}
	
	public static void addClientHandler(Handler handler)
	{
		TweakerFramework.logger.info("Adding client handler: "+handler.modName);
		clientHandlers.put(handler.modName, handler);
	}
}
