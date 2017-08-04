package world.soapboxrace.cli.netty;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import world.soapboxrace.cli.Car;
import world.soapboxrace.cli.CarProtocol;
import world.soapboxrace.cli.MainBoard;
import world.soapboxrace.cli.Sender;
import world.soapboxrace.cli.SenderState;

public class PlayerInfoHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		DatagramPacket datagramPacket = (DatagramPacket) msg;
		ByteBuf buf = datagramPacket.content();
		if (isPlayerInfoPacket(buf)) {
			Sender.setSenderState(SenderState.INFO);
			byte[] bytes = ByteBufUtil.getBytes(buf);
			splitParse(bytes);
		}
		super.channelRead(ctx, msg);
	}

	private void splitParse(byte[] bytes) {
		byte[] bytesTmp = bytes.clone();
		List<byte[]> l = new LinkedList<>();
		int blockStart = 0;
		for (int i = 0; i < bytesTmp.length; i++) {
			if (bytesTmp[i] == (byte) 0xff) {
				l.add(Arrays.copyOfRange(bytesTmp, (blockStart + 1), i));
				blockStart = i + 1;
				i = blockStart;
			}
		}
		l.add(Arrays.copyOfRange(bytesTmp, blockStart, bytesTmp.length));
		for (int i = 1; i < (l.size() - 1); i++) {
			byte[] bs = l.get(i);
			if (bs.length > 0) {
				CarProtocol carProtocol = new CarProtocol();
				carProtocol.deserialize(bs);
				MainBoard.addUpdateCar(i, new Car(carProtocol.getPlayerId(), carProtocol.getX(), carProtocol.getY()));
			} else {
				MainBoard.addUpdateCar(i, null);
			}
		}
		bytesTmp = null;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.err.println(cause.getMessage());
		ctx.close();
	}

	private boolean isPlayerInfoPacket(ByteBuf buf) {
		return (buf.getByte(2) == (byte) 0x07);
	}

}
