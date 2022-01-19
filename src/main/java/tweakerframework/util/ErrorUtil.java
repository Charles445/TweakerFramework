package tweakerframework.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.common.ICrashCallable;
import tweakerframework.Reference;
import tweakerframework.TweakerFramework;

public class ErrorUtil
{
	public static final Map<String, Integer> errorCount = new ConcurrentHashMap<String,Integer>();
	
	public static void logSilent(String key)
	{
		incrementError(key);
	}
	
	private static void incrementError(String key)
	{
		Integer got = errorCount.get(key);
		if(got==null)
		{
			errorCount.put(key, 1);
		}
		else
		{
			errorCount.put(key, got + 1);
		}
	}
	
	public static class CrashCallable implements ICrashCallable
	{
		@Override
		public String call() throws Exception
		{
			StringBuilder sb = new StringBuilder();
			sb.append("\n ");
			for(Map.Entry<String, Integer> entry : errorCount.entrySet())
			{
				sb.append(entry.getKey());
				sb.append(" : ");
				sb.append(entry.getValue());
				sb.append("\n ");
			}
			
			return sb.toString();
		}

		@Override
		public String getLabel()
		{
			return Reference.NAME+" Error Report";
		}
		
	}
}
