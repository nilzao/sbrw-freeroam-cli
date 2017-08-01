package world.soapboxrace.cli.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import world.soapboxrace.cli.Sender;
import world.soapboxrace.cli.SenderState;
import world.soapboxrace.debug.UdpDebug;

public class HelloHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		DatagramPacket datagramPacket = (DatagramPacket) msg;
		ByteBuf buf = datagramPacket.content();
		if (isHelloPacket(buf)) {
			Sender.setSenderState(SenderState.INFO);
			String response = UdpDebug.byteArrayToHexString(ByteBufUtil.getBytes(buf));
			System.out.println("Hello " + response);
		}
		super.channelRead(ctx, msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.err.println(cause.getMessage());
		ctx.close();
	}

	private boolean isHelloPacket(ByteBuf buf) {
		return (buf.getByte(2) == (byte) 0x06);
	}

}
