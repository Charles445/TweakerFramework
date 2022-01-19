package tweakerframework.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import tweakerframework.TweakerFramework;

public class PacketHandler
{
	public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(TweakerFramework.MODID);
	
	public static void init()
	{
		instance.registerMessage(MessageSendVersion.Handler.class, MessageSendVersion.class, 0, Side.SERVER);
	}
	
	
}
