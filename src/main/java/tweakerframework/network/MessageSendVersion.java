package tweakerframework.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import tweakerframework.TweakerFramework;
import tweakerframework.util.ErrorUtil;
import tweakerframework.util.VersionDelimiter;

public class MessageSendVersion implements IMessage
{
	private int major;
	private int minor;
	private int patch;
	
	public MessageSendVersion()
	{
		this.major = 0;
		this.minor = 4;
		this.patch = 0;
	}
	
	public MessageSendVersion(VersionDelimiter vd)
	{
		this.major = vd.major;
		this.minor = vd.minor;
		this.patch = vd.patch;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		if(buf == null)
		{
			setPacketInvalid();
			return;
		}
		
		try
		{
			this.major = buf.readInt();
			this.minor = buf.readInt();
			this.patch = buf.readInt();
		}
		catch(IndexOutOfBoundsException e)
		{
			setPacketInvalid();
			return;
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.major);
		buf.writeInt(this.minor);
		buf.writeInt(this.patch);
	}
	
	private void setPacketInvalid()
	{
		this.major = 0;
		this.minor = 0;
		this.patch = 0;
	}
	
	public static class Handler implements IMessageHandler<MessageSendVersion, IMessage>
	{
		@Override
		public IMessage onMessage(MessageSendVersion message, MessageContext ctx) 
		{
			if(ctx.side == Side.SERVER)
			{
				TweakerFramework.logger.debug("Received version message: "+message.major+"."+message.minor+"."+message.patch);
				
				if(ctx.netHandler instanceof NetHandlerPlayServer)
				{
					NetHandlerPlayServer netHandler = (NetHandlerPlayServer)ctx.netHandler;
					if(netHandler.player!=null)
					{
						//NetworkHandler.helloQueue.add(netHandler.player.getGameProfile().getId());
						
						//TODO verify that handling all of this stuff in this netty thread is acceptable
						//Hey how come sponge doesn't complain about this?
						NetworkHandler.addClient(netHandler.player.getGameProfile().getId(), new VersionDelimiter(message.major, message.minor, message.patch));
					}
					else
					{
						TweakerFramework.logger.error("NetHandlerPlayServer had null player...");
						ErrorUtil.logSilent("NetHandlerPlayServer NULL PLAYER");
					}
				}
			}
			
			return null;
		}
	}
	
	
}
