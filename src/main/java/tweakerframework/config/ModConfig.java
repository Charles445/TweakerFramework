package tweakerframework.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tweakerframework.TweakerFramework;

@Config(modid = TweakerFramework.MODID)
public class ModConfig
{
	@Config.Comment("Client options")
	@Config.Name("Client")
	public static ClientConfig client = new ClientConfig();
	
	@Config.Comment("Patch options for the coremod")
	@Config.Name("patches")
	public static PatchConfig patches = new PatchConfig();
	
	@Config.Comment("Server options")
	@Config.Name("Server")
	public static ServerConfig server = new ServerConfig();
	
	public static class ServerConfig
	{
		@Config.Comment("Minecraft tweaks, or anything that isn't mod specific")
		@Config.Name("Minecraft")
		public ConfigMinecraft minecraft = new ConfigMinecraft();
		
		//Add server config here
	}
	
	public static class ClientConfig
	{
		@Config.Comment("Minecraft tweaks")
		@Config.Name("Minecraft")
		public ConfigMinecraftClient minecraft = new ConfigMinecraftClient();
		
		//Add client config here
	}
	
	@Mod.EventBusSubscriber(modid = TweakerFramework.MODID)
	private static class EventHandler
	{
		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
		{
			if(event.getModID().equals(TweakerFramework.MODID))
			{
				ConfigManager.sync(TweakerFramework.MODID, Config.Type.INSTANCE);
			}
		}
	}
}
