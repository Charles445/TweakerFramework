package tweakerframework.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import tweakerframework.TweakerFramework;
import tweakerframework.network.MessageSendVersion;
import tweakerframework.network.NetworkHandler;
import tweakerframework.network.PacketHandler;

public class TweakerCoreHandler extends Handler
{
	public TweakerCoreHandler()
	{
		super("Tweaker Core Handler");
	}
	
	@Override
	public void init() throws Exception
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onClientPlayerJoinWorldEvent(EntityJoinWorldEvent event)
	{
		if(!event.getWorld().isRemote)
			return;
		
		//Client logical
		
		if(event.getEntity() instanceof EntityPlayer)
		{
			//EntityPlayer
			
			EntityPlayer localPlayer = TweakerFramework.proxy.getClientMinecraftPlayer();
			
			if(localPlayer==null)
				return;
			
			//Client logical
			//Client physical
			
			EntityPlayer entPlayer = (EntityPlayer)event.getEntity();
			
			
			if(entPlayer.getGameProfile().getId().equals(localPlayer.getGameProfile().getId()))
			{
				//Player is you
				if(NetworkHandler.serverHasVersioning)
				{
					//Create a message to the server to let them know your version
					TweakerFramework.logger.debug("Sending MessageSendVersion to server");
					PacketHandler.instance.sendToServer(new MessageSendVersion(TweakerFramework.VERSION_DELIMITER));
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerLoggedOutEvent(PlayerLoggedOutEvent event)
	{
		if(event.player!=null)
		{
			NetworkHandler.removeClient(event.player.getGameProfile().getId());
		}
	}
}
